package consumable;
import java.util.HashMap;

import models.Customer;

public class Table {

    private Customer customer;

    public Table(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isEmpty() {
        return getCustomer() == null;
    }

    @Override
    public String toString() {
        return "table customer= " + (getCustomer() == null ? "null" : getCustomer().getName());
    }

    public  static HashMap<Integer, Table> getList() {
        HashMap<Integer, Table> tableList = new HashMap<Integer, Table>();

        for (int i = 0; i < 4; i++) {
            tableList.put(i, new Table(null));
        }
        return tableList;
    }

}
