package Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import Consumable.Table;
import Consumable.Request.Order;
import Models.Customer;
import Models.Employee.Chef;
import Models.Employee.Waiter;
import PoolHandlers.ChefPoolHandler;
import PoolHandlers.WaiterPoolHandler;

public class Main {

    /*
     * AS A NOUN ANALYSIS OF OUR SYSTEM WE HAVE THE KEYWORDS AS
     * CUSTOMER_LIST -> LIST OF THE CUSTOMERS
     * TABLE_LIST -> LIST OF THE TABLES WHICH CUSTOMERS WILL SIT ON
     * WAITING_CUSTOMERS -> THE CUSTOMERS WHO COULDN'T FIND EMPTY TABLE
     * ORDER_LIST -> THE LIST OF ORDERS WHICH CUSTOMERS GIVE
     * WAITER_LIST -> THE WAITERS WHO RECIEVE ORDERS FROM CUSTOMERS
     * CHEF_LIST -> THE LIST OF CHEFS WHO COOKS THE ORDERS
     */

    public ArrayList<Waiter> waiterList = Waiter.getList();

    public ArrayList<Chef> chefList = Chef.getList();

    public HashMap<Integer, Table> tableList = Table.getList();

    public Queue<Customer> waitingCustomers = new LinkedList<Customer>();

    public Queue<Order> orderList = new LinkedList<Order>();

    /**
     * The pool which the waiter threads exist.
     */
    public WaiterPoolHandler waiters = new WaiterPoolHandler(waiterList);

    /**
     * The pool which the chef threads exist.
     */
    public ChefPoolHandler chefs = new ChefPoolHandler(chefList);

    // Created a static object so that all the collections can be accessed from
    // other classes with the same object.
    private static Main singleObject = null;

    // SINGLETON ILE NESNE OLUŞUMU
    /**
     * Return an object which is singular.
     * 
     * @return
     */
    public static Main getInstance() {
        if (singleObject == null) {
            singleObject = new Main();
        }
        return singleObject;
    }

    /**
     * @param date
     * @return Date
     * This method generates date object from a formatted string.
     */
    public static Date getDate(String date) {
        SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
        Date rdate;
        try {
            rdate = dateformat.parse(date);
            return rdate;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {

        Thread newCustomerThread = new Thread(new Runnable() {

            @Override
            public void run() {
                Main main = Main.getInstance();
                // the list of customers
                ArrayList<Customer> list = Customer.getList();
                list.sort((o1, o2) -> o1.getOrder().getTotalTime(true) - o2.getOrder().getTotalTime(true));
                Random rand = new Random();
                // ın every 5 second 5 new customers come to the system.
                for (int i = 0; i < 3; i++) {
                    System.out.println("%%% NEW CUSTOMERS ARRIVED");
                    for (int j = 0; j < 5; j++) {
                        // some delays.
                        try {
                            Thread.sleep(rand.nextInt(1500));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Customer customer = list.get(i * 5 + j);
                        System.out
                                .println("+++ " + customer.getName() + " NAMED CUSTOMER HAS ARRIVED TO THE RESTAURANT");
                        // emptyIndex holds the value of the empty table ındex.
                        int emptyIndex = -1;
                        boolean isEmptyPlaceExist = false;
                        // looping the tables.
                        for (int k = 0; k < main.tableList.size(); k++) {
                            // if an empty table exist.
                            if (main.tableList.get(k).isEmpty()) {
                                isEmptyPlaceExist = true;
                                emptyIndex = k;
                                break;
                            }
                        }
                        if (isEmptyPlaceExist) {
                            customer.setTableNumber(emptyIndex);
                            // sitting the customer.
                            main.tableList.put(emptyIndex, new Table(customer));

                            // WAITER THREAD WILL WORK

                            main.waiters.work();

                        } else {
                            // The customers who don't want to wait may be exist so we have a boolean
                            // variable named isWaiting.
                            boolean isWaiting = rand.nextBoolean();
                            if (isWaiting) {
                                main.waitingCustomers.add(customer);
                                System.out.println(
                                        "### " + customer.getName() + " NAMED CUSTOMER IS WAITING FOR AN EMPTY TABLE");

                            } else {
                                System.out.println(
                                        "--- " + customer.getName()
                                                + " NAMED CUSTOMER DIDN'T WANT TO WAIT AND HAS LEFT FROM THE RESTAURANT");

                            }

                        }
                    }

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }

                }
            }
        });
        newCustomerThread.start();

    }

}
