package kms.chapter01

import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

private fun main() {
    Completable.create { emitter ->
        emitter.onComplete()
    }.subscribeOn(Schedulers.computation())
        .subscribe(object : CompletableObserver {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onComplete() {
                println("완료")
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }
        })

    Thread.sleep(100L)
}
