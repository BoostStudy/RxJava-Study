package ezhoon

import io.reactivex.Flowable

fun main() {
    Flowable.just("Hello world").subscribe { x: String? ->
        println(x)
    }
}