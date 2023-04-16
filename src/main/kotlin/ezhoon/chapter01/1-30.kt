package ezhoon.chapter01

import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import java.time.DayOfWeek
import java.time.LocalDate

fun main() {

    Single.create { emitter ->
        emitter.onSuccess(LocalDate.now().dayOfWeek)
        emitter.onSuccess(LocalDate.now().dayOfWeek)
        emitter.onSuccess(LocalDate.now().dayOfWeek)
    }.apply {
        subscribe(object : SingleObserver<DayOfWeek> {
            override fun onSubscribe(d: Disposable) = Unit

            override fun onError(e: Throwable) {
                throw e
            }

            override fun onSuccess(t: DayOfWeek) {
                println(t)
            }
        })
    }
}