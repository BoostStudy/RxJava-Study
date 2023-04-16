package ezhoon.chapter01

import io.reactivex.Maybe
import io.reactivex.MaybeObserver
import io.reactivex.disposables.Disposable
import java.time.DayOfWeek
import java.time.LocalDate

fun main() {

    Maybe.create { emitter ->
        emitter.onSuccess(LocalDate.now().dayOfWeek)
    }.apply {
        subscribe(object : MaybeObserver<DayOfWeek> {
            override fun onSubscribe(d: Disposable) = Unit

            override fun onError(e: Throwable) {
                throw e
            }

            override fun onComplete() {
                println("완료") // 데이터를 통지하지 않고 처리를 끝내므로 출력x
            }

            override fun onSuccess(t: DayOfWeek) {
                println(t)
            }
        })
    }
}