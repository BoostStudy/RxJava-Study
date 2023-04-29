package kms.chapter01

import io.reactivex.Maybe
import io.reactivex.MaybeObserver
import io.reactivex.disposables.Disposable
import java.time.DayOfWeek

private fun main() {
    Maybe.create<DayOfWeek> { emitter ->
        // 데이터를 통지할 때는 onSuccess 만 통지
//        emitter.onSuccess(LocalDate.now().dayOfWeek)
        // 데이터를 통지 하지 않을 때는 onComplete 만 통지
        emitter.onComplete()
    }.subscribe(object : MaybeObserver<DayOfWeek> {
        override fun onSubscribe(d: Disposable) {
        }

        override fun onSuccess(dayOfWeek: DayOfWeek) {
            println(dayOfWeek)
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
        }

        override fun onComplete() {
            println("완료")
        }
    })
}
