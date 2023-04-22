package ezhoon.chapter02

import java.util.concurrent.Executors

fun main() {

    val point = Point()

    val task = Runnable {
        for (i in 0..9999) {
            point.rightUp()
        }
    }

    val executorService = Executors.newCachedThreadPool()

    val future1 = executorService.submit(task, true)
    val future2 = executorService.submit(task, true)
    val future3 = executorService.submit(task, true)
    val future4 = executorService.submit(task, true)
    val future5 = executorService.submit(task, true)
    val future6 = executorService.submit(task, true)

    if (future1.get() && future2.get() && future3.get() && future4.get() && future5.get() && future6.get()) {
        println("${point.getX()},${point.getY()}")
    } else {
        println("실패")
    }

    executorService.shutdown()
}