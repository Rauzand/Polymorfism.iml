import GivenPlaces.Utilits.*;
import GivenPlaces.Utilits.CustomExceptions.NotExistingCommandException;


public class Main {
    public static void main(String[] args) {
        try{
            Menu.mainMenu();
        }
        catch (NotExistingCommandException e){
            e.printStackTrace();
        }
    }
}