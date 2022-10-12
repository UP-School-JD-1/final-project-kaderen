package poolHandlers;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import models.employee.Waiter;

import java.util.Random;

public class WaiterPoolHandler {
    private ArrayList<Waiter> waiters;
    private ThreadPoolExecutor threadPool;

    public WaiterPoolHandler(ArrayList<Waiter> waiters) {
        this.waiters = waiters;
        // ! Thread havuzu oluşturuluyor
        this.threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(waiters.size());

    }

    public void work() {
        Random rand = new Random();
        Waiter availableWaiter = waiters.get(rand.nextInt(waiters.size()));

        threadPool.execute(availableWaiter);
    }

}
