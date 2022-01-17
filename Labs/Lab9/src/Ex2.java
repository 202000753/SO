public class Ex2 {
    static class Worker extends Thread {
        Storage storage;

        Worker(Storage storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            while (true) {
                System.out.println(getName() + ": " + storage.get());
            }
        }
    }

    static class Storage {
        int value;

        synchronized void set(int value) {
            this.value = value;
            notifyAll();
        }

        synchronized int get() {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return this.value;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Storage storage = new Storage();

        new Worker(storage).start();
        new Worker(storage).start();

        for (int i = 0; i < 10; i++) {
            Thread.sleep(500);

            storage.set(i);
        }
    }
}