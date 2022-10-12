package models;

import java.util.ArrayList;
import java.util.Date;

import consumable.request.Order;
import enums.Gender;
import exception.IllegalOrderException;
import models.employee.Waiter;
import test.Main;

public class Customer extends Person {
    private boolean isOrdered;
    private Order order;
    private int tableNumber;
    private boolean isFinished;

    /**
     * @param name
     * @param ssn
     * @param birth_Date
     * @param gender
     * @throws IllegalOrderException
     */
    public Customer(String name, String ssn, Date birth_Date, Gender gender) throws IllegalOrderException {
        super(name, ssn, birth_Date, gender);
        this.isOrdered = false;
        this.order = Order.generateOrder(this);
        this.tableNumber = -1;
        this.isFinished = false;

    }

    public boolean isOrdered() {
        return isOrdered;
    }

    public void setOrdered(boolean isOrdered) {
        this.isOrdered = isOrdered;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    // thred
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     * with the condition of isFinished, the customer can give order or consume the order which is finished.
     */
    @Override
    public void run() {
        // the main obj
        Main main = Main.getInstance();
        if (isFinished == false) {

            // checking if the order created
            if (isOrdered == false) {

                // adding the order
                main.orderList.add(order);
                isOrdered = true;
                System.out.println("*** " + getName() + " NAMED CUSTOMER GIVING ORDER");
                // Trigger the chef threads to cook the order.
                Waiter.triggerChef();
            }

        } else {

            // Customer received the order and started to consume.
            System.out.println("@@@ " + getName() + " NAMED CUSTOMER EATING "
                    + (getGender() == Gender.female ? "HER " : " HIS ") + "ORDER");
            try {
                Thread.sleep(order.getTotalTime(false));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("--- " + order.getCustomer().getName()
                    + " NAMED CUSTOMER FINISHED AND EXITTING THE SYSTEM (CONSUME TIME: " + order.getTotalTime(false)
                    + "  millisecond)");
            // Removing the customer from the table.
            int key = order.getCustomer().getTableNumber();
            Customer customer = null;

            // Polling a customer from the queue.
            try {
                customer = main.waitingCustomers.poll();
                customer.setTableNumber(key);
            } catch (Exception e) {
            }

            // updating the customer in the table which key is equal to the last order's
            // key.

            main.tableList.get(key).setCustomer(customer);
            

            if (customer != null)
                main.waiters.work();

        }

    }

    public static ArrayList<Customer> getList() {
        ArrayList<Customer> customerList = new ArrayList<Customer>();
        try {
            customerList.add(new Customer("Zehra", "23456789876", Main.getDate("12.12.2000"), Gender.female));
            customerList.add(new Customer("Sedat", "23456098906", Main.getDate("28.10.2000"), Gender.male));
            customerList.add(new Customer("Kader", "23456789098", Main.getDate("16.10.1997"), Gender.female));
            customerList.add(new Customer("Ali", "0987654332", Main.getDate("24.06.1999"), Gender.male));
            customerList.add(new Customer("Selim", "0987654134", Main.getDate("24.06.1000"), Gender.male));
            customerList.add(new Customer("Muhammet", "0987354334", Main.getDate("24.06.2004"), Gender.male));
            customerList.add(new Customer("Mustafa", "0987644334", Main.getDate("24.06.2003"), Gender.male));
            customerList.add(new Customer("Irem", "0987654354", Main.getDate("24.06.2002"), Gender.female));
            customerList.add(new Customer("IremMelisa", "0987654334", Main.getDate("24.04.2000"), Gender.female));
            customerList.add(new Customer("Keder", "0987654734", Main.getDate("24.06.2020"), Gender.female));
            customerList.add(new Customer("Aydın", "0987654834", Main.getDate("24.06.2010"), Gender.male));
            customerList.add(new Customer("Bearkuan", "0987954334", Main.getDate("24.06.2009"), Gender.male));
            customerList.add(new Customer("Mehmet", "0987650334", Main.getDate("24.06.1996"), Gender.male));
            customerList.add(new Customer("Ayse", "0287650334", Main.getDate("24.06.1978"), Gender.female));
            customerList.add(new Customer("Özgür", "4987650334", Main.getDate("24.06.1994"), Gender.male));
        } catch (IllegalOrderException e) {
        }

        return customerList;
    }

}
