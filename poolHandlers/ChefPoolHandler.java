package poolHandlers;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import models.employee.Chef;



public class ChefPoolHandler {
    private ArrayList<Chef> chefs;
    private ThreadPoolExecutor threadPool;

    public ChefPoolHandler(ArrayList<Chef> chefs) {
        this.chefs = chefs;
        this.threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(chefs.size());
    }

    // ! Müsait olan garsonlardan rastgele bir tanesi çalıştırtılacak
    public   void work() {
        Random rand = new Random();

        Chef availableChef = chefs.get(rand.nextInt(chefs.size()));

        threadPool.execute(availableChef);
    }

}
