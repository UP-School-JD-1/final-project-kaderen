package Exception;
public class IllegalOrderException extends Exception {

    @Override
    public String getMessage() {

        return "Illegal Food or Drink Amount! Maximum Food Amount is 2 and Maximum drink amount is 1.";
    }

}
