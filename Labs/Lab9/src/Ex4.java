import java.util.concurrent.Semaphore;

public class Ex4 {
    private static Semaphore doPing = new Semaphore(1,true);
    private static Semaphore doPong = new Semaphore(0,true);

    static class Ping extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    doPing.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Ping");

                doPong.release();
            }
        }
    }

    static class Pong extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    doPong.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Pong");

                doPing.release();
            }
        }
    }

    public static void main(String[] args) {
        new Ping().start();
        new Pong().start();
    }
}
