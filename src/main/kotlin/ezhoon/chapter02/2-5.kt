package ezhoon.chapter02

class Consumer {

    @Volatile
    var count: Int = 0
        private set

    fun increment() {
        count++
    }
}