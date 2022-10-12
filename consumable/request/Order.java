package consumable.request;
import java.util.ArrayList;
import java.util.List;

import exception.IllegalOrderException;
import models.Customer;

public class Order {

    private List<Request> foodAndDrink;
    private boolean isStarted;
    private Customer customer;

    /**
     * @param foodAndDrink
     * @param customer
     * @throws IllegalOrderException
     * 
     * Customers can give the order, waiters can receive and chefs can cook
     */
    public Order(List<Request> foodAndDrink, Customer customer) throws IllegalOrderException {

        int foodCount = getFoodCount(foodAndDrink);
        int drinkCount = getDrinkCount(foodAndDrink);
        if (foodCount > 2 || drinkCount > 1) {
            throw new IllegalOrderException();
        }
        this.foodAndDrink = foodAndDrink;
        this.isStarted = false;
        this.customer = customer;
    }

    public List<Request> getFoodAndDrink() {
        return foodAndDrink;
    }

    public void setFoodAndDrink(List<Request> foodAndDrink) throws IllegalOrderException {
        int foodCount = getFoodCount(foodAndDrink);
        int drinkCount = getDrinkCount(foodAndDrink);
        if (foodCount > 2 && drinkCount > 1) {
            throw new IllegalOrderException();
        }
        this.foodAndDrink = foodAndDrink;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // Return total time of the order

    public int getTotalTime(boolean isCookingTime) {
        int temp = 0;
        for (Request request : foodAndDrink) {
            temp += (isCookingTime == true ? request.getCookingTime() : request.getConsumeTime());

        }
        return temp;
    }

    // Generate FoodCount from the requestList
    public static int getFoodCount(List<Request> rawList) {
        int total = 0;
        // for each request
        for (Request request : rawList) {
            try {
                // trying to downcasting the request
                // if the request is drink error will happen
                Food food = (Food) request;
                total++;
            } catch (Exception e) {
                continue;

            }

        }

        return total;
    }

    public static int getDrinkCount(List<Request> rawList) {
        int total = 0;
        for (Request request : rawList) {
            try {
                Drink drink = (Drink) request;

                total++;
            } catch (Exception e) {
                continue;
            }
        }
        return total;
    }

    public static Order generateOrder(Customer customer) throws IllegalOrderException {
        ArrayList<Request> list = new ArrayList<Request>();
        Food food1 = Request.randomFood();
        Food food2 = Request.randomFood();
        Drink drink = Request.randomDrink();

        if (food1 != null)
            list.add(food1);
        if (food2 != null)
            list.add(food2);
        if (drink != null)
            list.add(drink);

        if (list.isEmpty()) {
            while (true) {
                Food newFood = Request.randomFood();
                if (newFood!= null) {
                    list.add(newFood);
                    return new Order(list, customer); 
                }   
            }
            
        }
        return new Order(list, customer);

    }

    @Override
    public String toString() {
        String temp = " ";
        for (Request request : foodAndDrink) {
            temp += "\n\t" + request;

        }
        return temp;
    }
}
