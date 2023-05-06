package ezhoon.chapter02

import java.util.concurrent.Executors

fun main() {

    val counter = AtomicConsumer()

    val task = Runnable {
        for (i in 0..9999) {
            counter.increment()
        }
    }

    val executorService = Executors.newCachedThreadPool()

    val future1 = executorService.submit(task, true)
    val future2 = executorService.submit(task, true)

    if (future1.get() && future2.get()) {
        println("${counter.count}")
    } else {
        println("실패")
    }

    executorService.shutdown()
}