package kms.chapter01

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

private fun main() {
    val flowable = Flowable.create(
        object : FlowableOnSubscribe<String> {

            // FlowableEmitter 가 Subscriber 에게 데이터를 통지한다
            // create 의 구현을 따라가면
            // 에러가 발생하면 catch 해서 onError 로 전달하는 부분이 존재함
            // 단, 치명적인 에러라면 다시 Throw 를 던짐
            // https://github.com/ReactiveX/RxJava/issues/748#issuecomment-32471495
            override fun subscribe(emitter: FlowableEmitter<String>) {
                val strArr = arrayOf("Hello, World!", "안녕, RxJava!")
                for (str in strArr) {
                    // 구동 해지 상태에서 종료하지 않고 onNext 를 진행해도 데이터를 통지하지 않는다
                    // 단, Rx 에서 해주는 것은 통지하지 않는 것이지 계속 진행은 하므로 직접 처리하는게 좋다
                    if (emitter.isCancelled) {
                        return
                    }
                    // 데이터를 통지한다
                    // 만약 null 을 전달하면 NullPointException 이 발생한다
                    emitter.onNext(str)
                }
                // onComplete 를 통지하면 그 이후엔 아무것도 통지하면 안된다
                emitter.onComplete()
            }
        },
        // BackpressureStrategy 에 따라 다른 Emitter 를 생성
        BackpressureStrategy.BUFFER,
    )
    flowable
        // 데이터를 받는 측의 쓰레드를 변경할 때 사용
        .observeOn(Schedulers.computation())
        // Flowable 는 Publisher 인터페이스를 구현했기 때문에 Subscriber 와의 상호작용을 외부에서 영향을 받지 않는다
        .subscribe(object : Subscriber<String> {

            // Subscriber 가 받을 데이터의 개수를 요청 및 구독 해지할 수 있는 인터페이스
            // onNext에서 직접 배압을 처리하기 위해서 subscription 을 멤버 변수로 저장
            private var subscription: Subscription? = null

            override fun onSubscribe(s: Subscription?) {
                subscription = s
                // 요청 데이터의 개수를 MAX 로 처리하면 onNext 에서 더 이상 요청하지 않아도 됨
                // onSubscribe 에서 request를 호출하지 않으면 데이터르 받을 수 없다
                // request는 onSubscribe 의 가장 마지막에서 호출 해야함
                subscription?.request(1L)
            }

            // Flowable 에서 데이터를 받으면 호출 되는 메서드
            override fun onNext(data: String?) {
                println("${Thread.currentThread().name}: $data")
                subscription?.request(1L)
            }

            // 에러가 발생했거나 에러를 통지할 때 실행되는 메서드
            // onError 이후에는 onNext 나 onComplete 가 실행되지 않는다
            override fun onError(error: Throwable?) {
                error?.printStackTrace()
            }

            // 모든 데이터의 통지를 끝내고 처리가 완료됐을 때 실행되는 메서드
            override fun onComplete() {
                println("${Thread.currentThread().name}: 완료")
            }
        })

    Thread.sleep(500L)
}
