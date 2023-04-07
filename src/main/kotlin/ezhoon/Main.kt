package ezhoon


fun main() {
    Flowable.just("Hello world").subscribe { x: String? ->
        println(x)
    }
}