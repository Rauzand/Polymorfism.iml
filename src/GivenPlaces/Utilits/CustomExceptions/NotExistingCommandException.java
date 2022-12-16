package GivenPlaces.Utilits.CustomExceptions;

public class NotExistingCommandException extends Exception{
    public NotExistingCommandException(){
        super();
    }
    public NotExistingCommandException(String description){
        super(description);
    }
}
