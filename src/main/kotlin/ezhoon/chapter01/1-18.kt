package ezhoon.chapter01

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun main() {
    Observable.create { emitter ->
        listOf("Hello", "World", "안녕, RxJava!").forEach { data ->
            if (emitter.isDisposed) return@forEach
            emitter.onNext(data)
        }
        emitter.onComplete()
    }
        .observeOn(Schedulers.computation())
        .subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) = Unit

            override fun onError(e: Throwable) {
                throw e
            }

            override fun onComplete() {
                println("${Thread.currentThread().name} : 완료")
            }

            override fun onNext(t: String) {
                println("${Thread.currentThread().name} : $t")
            }
        })

    Thread.sleep(1000L)
}
