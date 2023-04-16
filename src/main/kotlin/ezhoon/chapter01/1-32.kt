package ezhoon.chapter01

import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun main() {

    Completable.create { emitter ->
        emitter.onComplete()
    }
        .subscribeOn(Schedulers.computation())
        .subscribe(object : CompletableObserver {
            override fun onSubscribe(d: Disposable) = Unit

            override fun onComplete() {
                println("완료")
            }

            override fun onError(e: Throwable) {
                throw e
            }
        })
}