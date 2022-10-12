package models.employee;

import java.util.ArrayList;
import java.util.Date;

import consumable.request.Order;
import enums.Gender;
import test.Main;

public class Chef extends Employee {

    private int reward;

    /**
     * @param name
     * @param ssn
     * @param birth_Date
     * @param gender
     * @param startDateOfWork
     * @param salary
     * @param reward
     * chef class who cooks the orders 
     */
    public Chef(String name, String ssn, Date birth_Date, Gender gender, Date startDateOfWork, int salary, int reward) {
        super(name, ssn, birth_Date, gender, startDateOfWork, salary);
        this.reward = reward;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     * cooks the order trigers the customer thread
     */
    @Override
    public void run() {

        Main main = Main.getInstance();

        // ! Poll = Alır ve kuyruktan çıkartır.

        Order order = main.orderList.poll();

        if (order.isStarted() == false) {
            order.setStarted((true));
            System.out.println("!!! " + getName() + " NAMED CHEF STARTED TO WORK ON ORDER FROM CUSTOMER NAMED "
                    + order.getCustomer().getName() + " (ESTIMATED COOKING TIME:" + order.getTotalTime(true)
                    + " millisecond) : " + order);

            // The thread will sleep until the order is ready.
            try {
                Thread.sleep(order.getTotalTime(true));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            order.getCustomer().setFinished(true);
            ;
            order.getCustomer().run();

        }

    }

    public static ArrayList<Chef> getList() {
        ArrayList<Chef> chefList = new ArrayList<Chef>();

        chefList.add(new Chef("Ceyhun", "123459678943", Main.getDate("16.05.1999"), Gender.male,
                Main.getDate("08.12.2022"), 4500, 4));

        chefList.add(new Chef("Yagmur", "1234594587943", Main.getDate("04.06.2002"), Gender.female,
                Main.getDate("02.11.2022"), 2200, 6));

        return chefList;

    }

}