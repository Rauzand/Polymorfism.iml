package GivenPlaces;


import GivenPlaces.Utilits.CustomExceptions.*;

import java.io.Serializable;


public abstract class Place {
    protected String name;
    protected String description;

    public Place(String name, String description){
        this.name = name;
        this.description = description;
    }

    protected Place() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // У каждого наследника будут свои возможности для изменения
    public abstract void handleChange() throws EmptyStringException;

    // И описание тоже
    public abstract String toString();
}
