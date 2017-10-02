package HomeWork_lesson_11_12_ThreadPool.Source;

import java.util.LinkedList;

public class FixedThreadPool implements ThreadPool {
    private int countOfThreads;
    private final LinkedList queue;
    private final ThreadPoolWorker[] workers;

    public FixedThreadPool ( int countOfThreads ) {
        this.countOfThreads = countOfThreads;
        queue = new LinkedList ();
        workers = new ThreadPoolWorker[countOfThreads];
        for (int i = 0; i < countOfThreads; ++i) {
            workers[i] = new ThreadPoolWorker ();
        }
    }

    @Override
    public void start () {
        for (int i = 0; i < countOfThreads; ++i) {
            workers[i].start ();
        }
    }

    @Override
    public void execute ( Runnable runnable ) {
        synchronized (queue) {
            queue.addLast (runnable);
            queue.notify ();
        }
    }

    private class ThreadPoolWorker extends Thread {
        public void run() {
            Runnable r;

            while (true) {
                synchronized(queue) {
                    while (queue.isEmpty()) {
                        try
                        {
                            queue.wait();
                        }
                        catch (InterruptedException ignored)
                        {
                        }
                    }

                    r = (Runnable) queue.removeFirst();
                }

                // Если мы не получим ошибки (RuntimeException)
                // может произойти утечка потоков в пуле.
                try {
                    r.run();
                }
                catch (RuntimeException e) {
                    e.printStackTrace ();
                }
            }
        }
    }
}
