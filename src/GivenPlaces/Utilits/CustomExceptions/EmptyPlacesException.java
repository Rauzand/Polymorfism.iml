package GivenPlaces.Utilits.CustomExceptions;

public class EmptyPlacesException extends Exception{
    public EmptyPlacesException(){
        super();
    }
    public EmptyPlacesException(String description){
        super(description);
    }
}
