package HomeWork_lesson_11_12_ThreadPool.Source;
import javax.swing.plaf.TableHeaderUI;
import java.util.LinkedList;

public class ScalableThreadPool implements ThreadPool{
    private final int minCountOfThreads;
    private final int maxCountOfThreads;
    private final LinkedList queue;
    private final LinkedList<ThreadPoolWorker> workers;

    public ScalableThreadPool ( int minCountOfThreads, int maxCountOfThreads ) {
        if (minCountOfThreads < 1 || maxCountOfThreads < minCountOfThreads) throw new IllegalArgumentException ();
        this.minCountOfThreads = minCountOfThreads;
        this.maxCountOfThreads = maxCountOfThreads;
        queue = new LinkedList ();

        workers = new LinkedList <> ();
        for (int i = 0; i < minCountOfThreads; ++i) {
            workers.add (new ThreadPoolWorker ());
        }
    }

    @Override
    public void start () {
        for (int i = 0 ; i < minCountOfThreads ; ++i) {
            workers.get (i).start ();
        }
    }

    @Override
    public void execute ( Runnable runnable ) {
        synchronized (queue) {
            queue.addLast (runnable);
            if (queue.size () > minCountOfThreads)
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
