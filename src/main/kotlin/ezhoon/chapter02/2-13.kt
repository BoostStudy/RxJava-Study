package ezhoon.chapter02

import java.util.concurrent.atomic.AtomicInteger

class Point {

    private val x = AtomicInteger(0)
    private val y = AtomicInteger(0)

    fun rightUp() {
        x.incrementAndGet()
        y.incrementAndGet()
    }

    fun getX() = x.get()

    fun getY() = y.get()
}