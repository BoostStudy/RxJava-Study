package kms.chapter02;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PointSample {

    public static void main(String[] args) throws Exception {
        final Point pos = new Point();

        Runnable task = () -> {
            for(int i = 0; i < 10000 ; i++) {
                pos.rightUp();
            }
        };

        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<Boolean> future1 = executorService.submit(task, true);
        Future<Boolean> future2 = executorService.submit(task, true);

        if(future1.get() && future2.get()) {
            System.out.println(pos);
        } else {
            System.out.println("failed");
        }

        executorService.shutdown();
    }
}
