package consumable.request;
import java.util.ArrayList;
import java.util.Random;

import consumable.Consumable;
import enums.DrinkCategory;
import enums.FoodCategory;

public abstract class Request implements Consumable {
    private int price;
    private String name;
    private int cookingTime;
    private int quantity;
    private int consumeTime;

    /**
     * @param price
     * @param name
     * @param cookingTime
     * @param quantity
     * @param consumeTime
     */
    public Request(int price, String name, int cookingTime, int quantity, int consumeTime) {
        this.price = price;
        this.name = name;
        this.cookingTime = cookingTime;
        this.quantity = quantity;
        this.consumeTime = consumeTime;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCookingTime(int time) {
        this.cookingTime = time;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(int consumeTime) {
        this.consumeTime = consumeTime;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + "\tPrice: " + getPrice();

    }

    // ! Main classta çok kalabalık olmaması için yiyecek ve içecekleri burada
    // !listeledik ve müşteriler buradan random olarak seçebilecekler

    /**
     * @return Food
     * return randomly selected food
     */
    public static Food randomFood() {
        ArrayList<Food> list = new ArrayList<Food>();
        list.add(new Food(45, "makarna", 1500, 4, FoodCategory.main, 2000));
        list.add(new Food(30, "Köfte", 3000, 4, FoodCategory.main,1800));
        list.add(new Food(50, "Pizza", 4000, 2, FoodCategory.hot,3100));
        list.add(new Food(35, "Magnolya", 3500, 2, FoodCategory.desert,1200));
        list.add(new Food(129, "Balık", 2000, 1, FoodCategory.seafood,2050));
        list.add(new Food(300, "Falafel", 5000, 3, FoodCategory.vegan,1500));
        list.add(new Food(120, "Pilav", 2300, 1, FoodCategory.main, 1450));
        list.add(new Food(70, "Hamburger", 3200, 6, FoodCategory.main, 1250));
        list.add(null);
        list.add(null);
        list.add(null);

        Random rand = new Random();
        Food food = list.get(rand.nextInt(list.size()));
        return food;
    }

    /**
     * @return Drink
     * return randomly selected drink
     * 
     */
    public static Drink randomDrink() {
        ArrayList<Drink> list = new ArrayList<Drink>();
        list.add(new Drink(23, "Churchill", 200, 1, DrinkCategory.cold,1000));
        list.add(new Drink(13, "Bira", 300, 1, DrinkCategory.cold,1100));
        list.add(new Drink(33, "Frozen", 400, 1, DrinkCategory.cold,1200));
        list.add(new Drink(43, "Soda", 220, 1, DrinkCategory.cold,1000));
        list.add(new Drink(33, "Meyve SUyu", 240, 1, DrinkCategory.cold, 1010));
        list.add(new Drink(23, "Çay", 220, 1, DrinkCategory.hot,1080));
        list.add(new Drink(3, "Su", 1000, 1, DrinkCategory.cold,1100));
        list.add(null);
        list.add(null);
        list.add(null);

        Random rand = new Random();
        Drink drink = list.get(rand.nextInt(list.size()));
        return drink;
    }

}