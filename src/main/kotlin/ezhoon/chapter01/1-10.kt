package ezhoon.chapter01

import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

sealed interface State {
    object ADD : State
    object MULTIPLY : State
}

private var calcMethod: State = State.ADD

fun main() {
    // 300 milliseconds마다 데이터를 통지하는 Flowable 생성
    val flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
        .take(7) // 7건까지 통지
        .scan { sum, data -> // 계산
            when (calcMethod) {
                State.ADD -> sum + data
                State.MULTIPLY -> sum * data
            }
        }

    flowable.subscribe { data -> println("data -> $data") }
    Thread.sleep(1000L)
    println("계산 방법 변경")
    /**
     * 비동기 처리 도중에 외부 변수를 참조한다면 프로그램이 복잡해질수록 의도하지 않은 일이 일어난다.
     * 지금도 계속 실행마다 값이 달라지고 있고 interval 값에 따라서 결과가 완전히 달라진다.
     * 그래서 함수형 프로그래밍이 아니다. 라고 한건가?
     */
    calcMethod = State.MULTIPLY
    Thread.sleep(1000L)
}