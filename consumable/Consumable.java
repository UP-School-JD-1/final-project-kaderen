package consumable;
public interface Consumable {
    
     int getPrice();

     String getName() ;

     int getCookingTime();

     int getQuantity() ;

     void setPrice(int price) ;

     void setName(String name) ;

     void setCookingTime(int time) ;

     void setQuantity(int quantity) ;

}
