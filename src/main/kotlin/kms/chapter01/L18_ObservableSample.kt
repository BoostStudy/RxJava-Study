package kms.chapter01

import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

private fun main() {
    val observable = Observable.create(
        ObservableOnSubscribe { emitter ->
            val strArr = arrayOf("Hello, World!", "안녕, RxJava!")

            for (str in strArr) {
                // 구독이 해제됐는지를 확인합니다.
                if (emitter.isDisposed) {
                    return@ObservableOnSubscribe
                }
                emitter.onNext(str)
            }
            emitter.onComplete()
        },
    )

    observable
        .observeOn(Schedulers.computation())
        .subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(item: String) {
                // 배압 기능이 없기 때문에 데이터를 요청하지 않는다
                println("${Thread.currentThread().name}: $item")
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }

            override fun onComplete() {
                println("${Thread.currentThread().name}: 완료")
            }
        })

    Thread.sleep(500L)
}
