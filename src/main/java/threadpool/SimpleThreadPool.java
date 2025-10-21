package threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleThreadPool {
    private final int THREAD_COUNT;
    private final BlockingQueue<Runnable> poolQueue;
    private final AtomicInteger currentThreadCount = new AtomicInteger(0);
    private volatile boolean isShutdown = false;

    public SimpleThreadPool(int threadCount) {
        this.THREAD_COUNT = threadCount;
        this.poolQueue = new LinkedBlockingQueue<>();
    }

    public BlockingQueue<Runnable> getTaskQueue() {
        return poolQueue;
    }

    public void addToPoolQueue(Runnable task) {
        if (isShutdown) {
            throw new IllegalStateException("Thread pool is shutdown. Cannot accept new tasks.");
        }

        poolQueue.offer(task);

        if (currentThreadCount.get() < THREAD_COUNT) {
            createThread();
        }
    }

    private synchronized void createThread() {
        if (currentThreadCount.get() >= THREAD_COUNT) return;

        Thread worker = new Thread(() -> {
            try {
                while (!isShutdown) {
                    Runnable task = poolQueue.take();
                    try {
                        task.run();
                    } catch (Exception e) {
                        System.out.println("Task failed: " + e.getMessage());
                    }
                    break;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                System.out.println("currentThreadCount is " + currentThreadCount);
                currentThreadCount.decrementAndGet();
            }
        });

        currentThreadCount.incrementAndGet();
        worker.start();
    }

    public void shutdown() {
        isShutdown = true;
        System.out.println("Thread pool shutting down...");
    }
}
