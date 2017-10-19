package HomeWork_lesson_11_12_ThreadPool.Testing;

import HomeWork_lesson_11_12_ThreadPool.Source.FixedThreadPool;
import HomeWork_lesson_11_12_ThreadPool.Source.ThreadPool;

public class UsingThreadPools {
    public static void main ( String[] args ) {
        ThreadPool thrpool = new FixedThreadPool (5);
        thrpool.start ();
        for (int i = 0; i < 5; ++i) {
            Task task = new Task ();
            thrpool.execute (task);
        }

        Task task = new Task ();
        thrpool.execute (task);

        task = new Task ();
        thrpool.execute (task);



    }

    private static class Task implements Runnable{
        @Override
        public void run () {
            try {
                Thread.sleep (2000);
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
            System.out.println (Thread.currentThread ().getName ());
        }
    }
}
