package threadpool;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleThreadPoolTests {

    @Test
    @DisplayName("Scenario 1: 2 threads, 20 tasks")
    void testTwoThreadsTwentyTasks() throws InterruptedException {
        runScenario(2, 20);
    }

    @Test
    @DisplayName("Scenario 2: 10 threads, 20 tasks")
    void testTenThreadsTwentyTasks() throws InterruptedException {
        runScenario(10, 20);
    }

    @Test
    @DisplayName("Scenario 3: 10 threads, 5 tasks")
    void testTenThreadsFiveTasks() throws InterruptedException {
        runScenario(10, 5);
    }

    @Test
    @DisplayName("Scenario 4: 1 thread, 1 task")
    void testOneThreadOneTask() throws InterruptedException {
        runScenario(1, 1);
    }

    private void runScenario(int threadCount, int taskCount) throws InterruptedException {
        System.out.printf("\n--- Running Scenario: %d threads, %d tasks ---\n", threadCount, taskCount);

        SimpleThreadPool pool = new SimpleThreadPool(threadCount);

        for (int i = 1; i <= taskCount; i++) {
            final int taskNumber = i;
            pool.addToPoolQueue(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.printf("Task %d is running on %s\n", taskNumber, threadName);
                try {
                    Thread.sleep(300); // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.printf("Task %d completed on %s\n", taskNumber, threadName);
            });

            Thread.sleep(20); // Optional: staggered submission
        }

        // Estimate total time for tasks to complete
        int estimatedMs = (int) Math.ceil((taskCount * 300.0) / threadCount) + 500;
        Thread.sleep(estimatedMs);

        assertThat(pool.getTaskQueue())
            .as("Task queue should be empty after all tasks have been processed")
            .isEmpty();

        pool.shutdown();
    }
}
