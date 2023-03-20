package ezhoon

import io.reactivex.rxjava3.core.Flowable


fun main() {
    Flowable.just("Hello world").subscribe { x: String? ->
        println(x)
    }
}