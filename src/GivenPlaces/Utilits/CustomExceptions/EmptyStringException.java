package GivenPlaces.Utilits.CustomExceptions;

public class EmptyStringException extends Exception{
    public EmptyStringException(){
        super();
    }
    public EmptyStringException(String description){
        super(description);
    }
}
