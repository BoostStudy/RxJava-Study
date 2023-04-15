package ezhoon.chapter01

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

fun main() {
    val flowable = Flowable.create(
        { emitter ->
            listOf("Hello", "World", "안녕, RxJava!").forEach { data ->
                if (emitter.isCancelled) return@forEach
                emitter.onNext(data)
            }

            emitter.onComplete()
        },
        BackpressureStrategy.BUFFER,
    ) // 초과한 데이터 Buffer 처리

    flowable
        .observeOn(Schedulers.computation())
        .subscribe(object : Subscriber<String> {
            private var subscription: Subscription? = null
            override fun onSubscribe(s: Subscription?) {
                subscription = s.also {
                    it?.request(1L)
                }
            }

            override fun onError(t: Throwable?) {
                t?.printStackTrace()
            }

            override fun onComplete() {
                println("${Thread.currentThread().name} : 완료")
            }

            override fun onNext(t: String?) {
                println("${Thread.currentThread().name} : $t")
                subscription?.request(1L)
            }
        })

    Thread.sleep(1000L)
}
