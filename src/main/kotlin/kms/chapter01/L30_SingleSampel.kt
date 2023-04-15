package kms.chapter01

import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import java.time.DayOfWeek
import java.time.LocalDate

private fun main() {
    Single.create { emitter ->
        emitter.onSuccess(LocalDate.now().dayOfWeek)
    }.subscribe(object : SingleObserver<DayOfWeek> {
        override fun onSubscribe(d: Disposable) {
        }

        override fun onSuccess(dayOfWeek: DayOfWeek) {
            println(dayOfWeek)
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
        }
    })
}
