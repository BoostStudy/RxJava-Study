package kms.chapter01

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

private fun main() {
    val disposable = CompositeDisposable()
    disposable.add(
        Flowable.range(1, 3)
            .doOnCancel { println("No.1 canceled") }
            .observeOn(Schedulers.computation())
            .subscribe {
                Thread.sleep(100L)
                println("No.1: $it")
            },
    )
    disposable.add(
        Flowable.range(1, 3)
            .doOnCancel { println("No.2 canceled") }
            .observeOn(Schedulers.computation())
            .subscribe {
                Thread.sleep(100L)
                println("No.2: $it")
            },
    )
    Thread.sleep(150L)
    disposable.dispose()
}
