package threads;

import java.util.List;
import java.util.concurrent.*;

public class ThreadsExperiments {

    void main() {
        checkSimpleThreads();
        threadsWithExecutorService();
    }

    private List<Thread> listOfThreads() {
        Thread t1 = new MySimpleThread();

        Runnable r2 = new MySimpleRunnable();
        Thread t2 = new Thread(r2);

        Runnable r3 = MySimpleLambdaRunnable.getSimpleRunnable();
        Thread t3 = new Thread(r3);

        return List.of(t1, t2, t3);
    }

    private List<Runnable> listOfRunnable() {

        Runnable r2 = new MySimpleRunnable();
        Runnable r3 = MySimpleLambdaRunnable.getSimpleRunnable();

        return List.of(r2, r3);
    }

    void checkSimpleThreads() {
        List<Thread> threads = listOfThreads();

        listOfThreads()
                .forEach(Thread::start);

        IO.println("before join");

        try {
            for (Thread thread : listOfThreads()) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        IO.println("after join");
    }

    void threadsWithExecutorService() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())) {

            Runnable r2 = new MySimpleRunnable();
            Runnable r3 = MySimpleLambdaRunnable.getSimpleRunnable();

            var future2 = executorService.submit(r2);
            var future3 = executorService.submit(r3);

//        try {
//            var result = future2.get();
//            IO.println("R2 get finished");
//            var result3 = future3.get();
//            IO.println("R3 get finished");
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

            IO.println("waiting for executor service to finish");
            executorService.shutdown();
        }
        IO.println("Executor service finished");
    }
}
