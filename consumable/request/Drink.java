package consumable.request;
import enums.DrinkCategory;

public class Drink extends Request {
    private DrinkCategory category;

    /**
     * @param price
     * @param name
     * @param cookingTime
     * @param quantity
     * @param category
     * @param consumeTime
     * Drink class which customers can give order of
     */
    public Drink(int price, String name, int cookingTime, int quantity, DrinkCategory category, int consumeTime) {
        super(price, name, cookingTime, quantity , consumeTime);
        this.category= category;
    }

    public DrinkCategory getCategory() {
        return category;
    }

    public void setCategory(DrinkCategory category) {
        this.category = category;
    }


}
