package GivenPlaces.Utilits.CustomExceptions;

public class NegativePopulationException extends Exception {
    public NegativePopulationException(){
        super();
    }
    public NegativePopulationException(String description){
        super(description);
    }
}
