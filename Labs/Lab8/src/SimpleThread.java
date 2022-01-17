public class SimpleThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(this.getName() + ": " + i);

            //ex1 b
            /*try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleThread thread0 = new SimpleThread();
        SimpleThread thread1 = new SimpleThread();

        //ex1 c
        /*thread1.setPriority(Thread.MIN_PRIORITY);
        thread0.setPriority(Thread.MAX_PRIORITY);*/

        thread0.start();

        //ex1 d
        thread0.join();
        thread1.start();
    }
}

/*
Ex1
b
No resultado anterior cada thread conseguia escrever varios numeros de seguida
Depois da alteração cada thread apenas consegue escrever numero de cada vez
 */