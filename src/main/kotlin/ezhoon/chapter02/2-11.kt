package ezhoon.chapter02

import java.util.concurrent.atomic.AtomicInteger

class AtomicConsumer {

    @Volatile
    var count = AtomicInteger(0)
        private set

    fun increment() {
        count.incrementAndGet()
    }
}