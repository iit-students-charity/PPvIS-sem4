package controller;

import model.Point2D;
import view.Table;

import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.Thread.currentThread;

public class Function2 implements Runnable {

    private Integer x;
    private Integer n;
    private final Integer rightLimit;
    private final ConcurrentLinkedQueue<Integer> queue;
    private final Table table;

    public Function2(Integer leftThreshold, Integer rightThreshold,
                     ConcurrentLinkedQueue<Integer> queue, Integer n, Table table) {

        this.x = leftThreshold;
        this.rightLimit = rightThreshold;
        this.queue = queue;
        this.n = n;
        this.table = table;
    }

    @Override
    public void run() {
        while (x <= rightLimit && !Thread.interrupted()) {

            int z = 0;
            for (int i = 0; i < n; i++) {
                z = (int) (z + ((Math.pow(-1, (i * x + 1))) / (i * (i + 1) * (i + 2))));
            }

            table.updateTable(new Point2D(x, z));
            queue.add((int) (z));

            queue.add((int) z);

            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                break;
            }

            x += 1;
        }
        currentThread().interrupt();
    }

}
