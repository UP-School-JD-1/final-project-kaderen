package consumable.request;
import enums.FoodCategory;

public class Food extends Request {

    private FoodCategory category;

    /**
     * @param price
     * @param name
     * @param cookingTime
     * @param quantity
     * @param category
     * @param consumeTime
     * 
     * Food class which customers can give order of
     */
    public Food(int price, String name, int cookingTime, int quantity, FoodCategory category,int consumeTime) {
        super(price, name, cookingTime, quantity, consumeTime);
        this.category = category;
    }

    public FoodCategory getCategory() {
        return category;
    }

    public void setCategory(FoodCategory category) {
        this.category = category;
    }

}
