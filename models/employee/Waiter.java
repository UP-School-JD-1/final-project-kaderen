package Models.Employee;
import java.util.ArrayList;
import java.util.Date;

import Consumable.Table;
import Enums.Gender;
import Test.Main;

public class Waiter extends Employee {

    private int tipCount;

   
    /**
     * @param name
     * @param ssn
     * @param birth_Date
     * @param gender
     * @param startDateOfWork
     * @param salary
     * @param tipCount
     */
    public Waiter(String name, String ssn, Date birth_Date, Gender gender, Date startDateOfWork, int salary,
            int tipCount) {
        super(name, ssn, birth_Date, gender, startDateOfWork, salary);
        this.tipCount = tipCount;
    }

    public int getTipCount() {
        return tipCount;
    }

    public void setTipCount(int tipCount) {
        this.tipCount = tipCount;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     * looking for the suitable table if there is one the customer thread will work.
     */
    @Override
    public void run() {

        Table lookingTable = null;
        Main main = Main.getInstance();

        // Looping all the tables to find a table which is suitable.

        for (int i = 0; i < main.tableList.size(); i++) {
            Table temp = main.tableList.get(i);
            if (temp.isEmpty() == false && temp.getCustomer().isOrdered() == false) {

                lookingTable = temp;
                break;
            }
        }

        // if there is no suitable table we end the method.

        if (lookingTable == null) {
            return;
        }

        // trigger the customer thread to give the order.
        lookingTable.getCustomer().run();

    }

    /**
     * triggers chef thread.
     */
    public static void triggerChef(){
        Main main = Main.getInstance();
        main.chefs.work();
    }

    public static ArrayList<Waiter> getList() {
        ArrayList<Waiter> waiterList = new ArrayList<Waiter>();
        waiterList.add(new Waiter("Kezban", "12334567765", Main.getDate("01.24.1989"), Gender.female,
                Main.getDate("08.12.2020"), 3000, 50));
        waiterList.add(new Waiter("Emre", "12345678943", Main.getDate("01.24.1876"), Gender.male,
                Main.getDate("08.12.2022"), 2500, 50));
        waiterList.add(new Waiter("Mustafa", "12765678943", Main.getDate("01.12.1896"), Gender.male,
                Main.getDate("04.12.2022"), 5500, 80));
        waiterList.add(new Waiter("Selin", "10985678943", Main.getDate("11.24.1976"), Gender.male,
                Main.getDate("10.05.2022"), 8500, 50));

        return waiterList;
    }
}


