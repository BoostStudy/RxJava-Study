package kms.chapter01

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

fun main() {
    val flowable = Flowable.create(
        object : FlowableOnSubscribe<String> {
            override fun subscribe(emitter: FlowableEmitter<String>) {
                val strArr = arrayOf("Hello, World!", "안녕, RxJava!")
                for (str in strArr) {
                    if (emitter.isCancelled) {
                        return
                    }
                    emitter.onNext(str)
                }
                emitter.onComplete()
            }
        },
        BackpressureStrategy.BUFFER,
    )
    flowable
        .observeOn(Schedulers.computation())
        .subscribe(object : Subscriber<String> {

            private var subscription: Subscription? = null

            override fun onSubscribe(s: Subscription?) {
                subscription = s
                subscription?.request(1L)
            }

            override fun onNext(data: String?) {
                println("${Thread.currentThread().name}: $data")
                subscription?.request(1L)
            }

            override fun onError(error: Throwable?) {
                error?.printStackTrace()
            }

            override fun onComplete() {
                println("${Thread.currentThread().name}: 완료")
            }
        })

    Thread.sleep(500L)
}
