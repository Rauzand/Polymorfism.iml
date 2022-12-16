package GivenPlaces;

import GivenPlaces.Utilits.CustomExceptions.*;
import GivenPlaces.Utilits.Interaction;

import java.util.HashMap;
import java.util.Scanner;

public class Region extends Place {
    private static final HashMap<String, Region> places = new HashMap<>();

    private final HashMap<String, City> cities = new HashMap<>();

    public Region(String name, String description){
        super(name, description);
    }

    public static HashMap<String, Region> getRegions(){
        return places;
    }

    public HashMap<String, City> getCities(){
        return cities;
    }

    public static String getPlaceType() {
        return "Регион";
    }

    public static class RegionInteraction extends Interaction {
        // Если поменяли имя региона, то даем его городам знать, что имя региона поменялось
        public static String handleRegionName(HashMap<String, City> regionCities) throws  EmptyStringException {
            String newName = handleName();
            for (City city : regionCities.values())
                city.setRegionAttachment(newName);
            return newName;
        }

        public static void handleOption(String option) throws EmptyStringException, EmptyPlacesException, NotExistingCommandException {
            System.out.println(
                    switch (option) {
                        case "Создать" -> createObject();
                        case "Удалить" -> deleteObject(places);
                        case "Изменить" -> changeObject(places);
                        case "Показать" -> showObjects(places);
                        default -> throw new NotExistingCommandException(
                                String.format("Системная ошибка: команда \"%s\" не обрабатывается", option));
                    }
            );
        }

        private static String createObject() throws EmptyStringException {
            String name = handleName();
            places.put(name, new Region(name, handleDescription()));
            return String.format("Новое место \"%s %s\" успешно добавлено", getPlaceType(), name);
        }
    }

    public static void showChangeOptions(){
        System.out.print("""
                Изменить:
                1. Имя
                2. Описание
                Выбор:\040""");
    }
    public void handleChange() throws EmptyStringException {
        Scanner scan = new Scanner(System.in);
        showChangeOptions();

        switch (scan.nextLine()) {
            case "1" -> setName(RegionInteraction.handleRegionName(getCities()));
            case "2" -> setDescription(Interaction.handleDescription());
            default -> {
                System.out.println("Неверно заданная команда. Попробуйте еще раз");
                handleChange();
            }
        }
    }

    // Нужен, чтобы вытащить регион, которому принадлежит город
    public static Region getRegion(City city){
        return places.get(city.getRegionAttachment());
    }

    // Для прикрепления города к региону
    public static void attachCityToRegion(City city){
        // Если уже сказали, что у города нет региона, то от нас не требуют его прикреплять
        if (!city.getRegionAttachment().equals("Не принадлежит какому-либо региону"))
            getRegion(city).addCityToRegion(city);
    }
    //
    public static void unattachCityFrom(City city) {
        // Если не нашли заданный регион, то ничего откреплять не надо
        if (getRegion(city) != null)
            getRegion(city).removeCityFromRegion(city);
    }

    public void addCityToRegion(City city){
        cities.put(city.getName(), city);
    }

    public void removeCityFromRegion(City city){
        cities.remove(city.getName());
    }

    public int calculatePopulation(){
        int totalPopulation = 0;
        for (City city : cities.values())
            totalPopulation += city.getPopulation();

        return totalPopulation;
    }

    public String toString(){
        return String.format("%s: %s\nНаселение %d\nОписание: %s\nГорода: %s\n",
                getPlaceType(), name, calculatePopulation(), description, Interaction.showNames(cities));
    }
}