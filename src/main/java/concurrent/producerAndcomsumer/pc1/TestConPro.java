package concurrent.producerAndcomsumer.pc1;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TestConPro {
    public static void main(String[] args) {
//        BlockingQueue blockingQueue = new LinkedBlockingDeque(5);
        BlockingQueue blockingQueue = new ArrayBlockingQueue(5);

        Producer producer = new Producer(blockingQueue);
        Consumer consumer = new Consumer(blockingQueue);

        Thread tp = new Thread(producer);
        Thread tc = new Thread(consumer);
        tp.start();
        tc.start();
    }
}
