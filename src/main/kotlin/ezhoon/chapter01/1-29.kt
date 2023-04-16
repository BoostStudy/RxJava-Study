package ezhoon.chapter01

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

fun main() {

    CompositeDisposable().apply {
        add(
            Flowable.range(1, 3)
                .doOnCancel { println("No.1 canceled") }
                .observeOn(Schedulers.computation())
                .subscribe {
                    Thread.sleep(100L)
                    println("No.1: $it")
                },
        )

        add(
            Flowable.range(1, 3)
                .doOnCancel { println("No.2 canceled") }
                .observeOn(Schedulers.computation())
                .subscribe {
                    Thread.sleep(100L)
                    println("No.2: $it")
                },
        )
    }.also {
        Thread.sleep(150L)
        it.dispose()
    }
}