package kms.chapter02;

import java.util.concurrent.atomic.AtomicInteger;

public class Point {
    private final AtomicInteger x = new AtomicInteger(0);
    private final AtomicInteger y = new AtomicInteger(0);

    void rightUp() {
        x.incrementAndGet();
        y.incrementAndGet();
    }

    int getX() {
        return x.get();
    }

    int getY() {
        return y.get();
    }

    @Override
    public String toString() {
        return String.format("%d, %d", getX(), getY());
    }
}
