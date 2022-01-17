public class Counter {
    static class CounterThread extends Thread {
        Counter counter;
        public CounterThread(Counter counter) {
            this.counter = counter;
        }
        @Override
        public void run() {
            counter.count(this.getName());
        }
    }
    public synchronized void count(String name) {
        for (int i=0; i<10; i++) {
            notify();

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(name + ": " + i);
        }
    }
    public static void main(String[] args) {
        Counter counter = new Counter();
        CounterThread thread0 = new CounterThread(counter);
        CounterThread thread1 = new CounterThread(counter);
        thread0.start();
        thread1.start();
    }
}