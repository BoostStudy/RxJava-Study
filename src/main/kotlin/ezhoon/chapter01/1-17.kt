package ezhoon.chapter01

import io.reactivex.Flowable
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.TimeUnit

fun main() {
    Flowable.interval(200L, TimeUnit.MILLISECONDS)
        .subscribe(object : Subscriber<Long> {
            private var subscription: Subscription? = null
            private var startTime: Long = 0L

            override fun onSubscribe(s: Subscription?) {
                subscription = s.also {
                    it?.request(Long.MAX_VALUE)
                }
                startTime = System.currentTimeMillis()
            }

            override fun onError(t: Throwable?) {
                t?.let { throw t }
            }

            override fun onComplete() = Unit

            override fun onNext(t: Long?) {
                if (System.currentTimeMillis() - startTime > 500) {
                    subscription?.cancel()
                    println("구독 해지")
                    return
                }
                println("data -> $t")
            }
        })

    Thread.sleep(1000L)
}
