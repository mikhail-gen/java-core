package pubsub;

import lombok.AllArgsConstructor;

import java.util.*;
import java.util.concurrent.*;

public class PubSubApp {
    private static final int PUB_THREAD_COUNT = 4;
    private static final int SUB_THREAD_COUNT = 3;
    private static final String EXIT_PHRASE = "exit";

    private static final BlockingQueue<String> inputQueue = new LinkedBlockingQueue<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final Object inputLock = new Object();
    private static volatile boolean shouldExit = false;
    private static volatile boolean queueFinished = false;

    public static void main(String[] args) {
        ExecutorService publisherThreadPool = Executors.newFixedThreadPool(PUB_THREAD_COUNT);
        for (int i = 0; i < PUB_THREAD_COUNT; i++) {
            publisherThreadPool.submit(new InputTask(i));
        }

        ExecutorService subscriberThreadPool = Executors.newFixedThreadPool(SUB_THREAD_COUNT);
        for (int i = 0; i < SUB_THREAD_COUNT; i++) {
            subscriberThreadPool.submit(new ConsumerTask(i));
        }

        publisherThreadPool.shutdown();
        try {
            publisherThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("\nMain thread interrupted while waiting for input executor.");
        }

        for (int i = 0; i < SUB_THREAD_COUNT; i++) {
            try {
                inputQueue.put(EXIT_PHRASE);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        subscriberThreadPool.shutdown();
        try {
            subscriberThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("\nSubscriber pool shutdown interrupted.");
        }

        System.out.println("\nProgram finished. Remaining queue items (if any):");
        inputQueue.forEach(System.out::println);
    }

    @AllArgsConstructor
    // Input task
    static class InputTask implements Runnable {
        private final int id;

        @Override
        public void run() {
            while (!shouldExit) {
                String input;

                synchronized (inputLock) {
                    if (shouldExit) break;

                    System.out.print("\nThread " + id + " waiting for input: ");
                    try {
                        if (scanner.hasNextLine()) {
                            input = scanner.nextLine();
                        } else {
                            break;
                        }
                    } catch (IllegalStateException | NoSuchElementException e) {
                        break;
                    }

                    if (input.equalsIgnoreCase(EXIT_PHRASE)) {
                        System.out.println("\nThread " + id + " received exit command.");
                        shouldExit = true;
                        synchronized (inputLock) {
                            try {
                                scanner.close();
                            } catch (Exception ignored) {}
                        }
                        break;
                    }
                }
                try {
                    inputQueue.put(input);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            System.out.println("\nInput - Thread " + id + " exiting...");
        }
    }

    @AllArgsConstructor
    static class ConsumerTask implements Runnable {
        private final int id;

        @Override
        public void run() {
            while (!queueFinished) {
                try {
                    String item = inputQueue.take();


                    if (EXIT_PHRASE.equals(item)) {
                        System.out.println("\nConsumer " + id + " received exit command. Exiting.");
                        break;
                    }

                    System.out.println("\n[Message]: " + item + " (extracted by " + Thread.currentThread().getName() + ")");
                    try {
                        Thread.sleep(1000); // Simulate processing
                    } catch (InterruptedException ignored) {}

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                if (shouldExit && inputQueue.isEmpty()) {
                    queueFinished = true;
                    System.out.println("queueFinished is set true");
                    break;
                }
            }
            System.out.println("Consumer thread " + id + " exiting.");
        }
    }
}