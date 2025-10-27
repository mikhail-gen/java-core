package threadpool;

public class SimpleThreadPoolTest {
    public static void main(String[] args) throws InterruptedException {
        int threadCount = 3;
        int taskCount = 20;

        SimpleThreadPool pool = new SimpleThreadPool(threadCount);

        for (int i = 1; i <= taskCount; i++) {
            int taskNumber = i;
            pool.addToPoolQueue(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("Task " + taskNumber + " is running on " + threadName);
                try {
                    Thread.sleep(500); // simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Task " + taskNumber + " completed on " + threadName);
            });

            Thread.sleep(100); // Optional: delay to simulate real-world task submission
        }

        // Wait for tasks to complete (not ideal but fine for this simple test)
        Thread.sleep(5000);

        pool.shutdown();
    }
}
