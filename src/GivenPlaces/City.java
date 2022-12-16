package GivenPlaces;

import GivenPlaces.Utilits.CustomExceptions.*;
import GivenPlaces.Utilits.Interaction;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;


public class City extends Place {
    // Население
    protected int population;
    // Принадлежность к региону
    protected String regionAttachment;
    // Существующие города
    private static final HashMap<String, City> places = new HashMap<>();

    public City(String name, int population, String description, String regionAttachment){
        this.name = name;
        this.population = population;
        this.description = description;
        this.regionAttachment = regionAttachment;
    }
    public static String getPlaceType() {
        return "Город";
    }
    public static HashMap<String, City> getCities(){
        return places;
    }
    public int getPopulation() {
        return population;
    }
    public void setPopulation(int population) {
        this.population = population;
    }
    public String getRegionAttachment(){
        // Если регион еще существует, то все хорошо
        if (Region.getRegions().containsKey(regionAttachment))
            return regionAttachment;
        // Иначе регион удален и у города нету принадлежности региону
        else
            return regionAttachment = "Не принадлежит какому-либо региону";
    }
    public void setRegionAttachment(String regionAttachment){
        this.regionAttachment = regionAttachment;
    }


    public static class CityInteraction extends Interaction {
        // Задаем население и проверяем, что оно целое число больше 0
        protected static int handlePopulation(){
            Scanner scan = new Scanner(System.in);

            System.out.print("Население: ");
            try {
                int population = scan.nextInt();
                if (population < 1)
                    throw new NegativePopulationException();

                return population;
            }
            catch (InputMismatchException | NegativePopulationException e){
                System.out.println("Население должно быть целым числом больше 0");
                return handlePopulation();
            }
        }

        // Прикрепляем город к региону
        protected static String handleRegion(){
            System.out.printf(" Доступные регионы: %s Название региона, которому принадлежит город:\040",
                    String.join(", ", Region.getRegions().keySet()));

            String regionName = scan.nextLine();
            if (Region.getRegions().containsKey(regionName))
                return regionName;
            else{
                System.out.println("Регион не найден. Выберите из списка доступных регионов");
                return handleCityToRegion();
            }
        }

        // Хотим ли прикреплять город к региону
        protected static String handleCityToRegion(){
            // Это возможно, если существует хотя бы один регион
            try{
                emptyPlaces(Region.getRegions());
            }
            catch (EmptyPlacesException e){
                return "Не принадлежит какому-либо региону";
            }

            System.out.print(" Город принадлежит региону? 1. Да 2. Нет Выбор:\040");

            String choice = scan.nextLine();
            if ("1".equals(choice)) {
                return handleRegion();
            }
            else if ("2".equals(choice)) {
                return "Не принадлежит какому-либо региону";
            }
            else {
                System.out.println("Неверно заданная команда. Попробуйте еще раз");
                return handleCityToRegion();
            }
        }

        public static void handleOption(String option) throws EmptyStringException, EmptyPlacesException, NotExistingCommandException {
            System.out.println(
                    switch (option) {
                        case "Создать" -> createObject();
                        case "Удалить" -> deleteCity();
                        case "Изменить" -> changeObject(places);
                        case "Показать" -> showObjects(places);
                        default -> throw new NotExistingCommandException(
                                String.format("Системная ошибка: команда \"%s\" не обрабатывается", option));
                    }
            );
        }

        private static String createObject() throws EmptyStringException {
            City createdCity = new City(handleName(), handlePopulation(), handleDescription(), handleCityToRegion());
            Region.attachCityToRegion(createdCity);

            places.put(createdCity.getName(), createdCity);
            return String.format("Новое место \"%s %s\" успешно добавлено", getPlaceType(), createdCity.getName());
        }

        protected static String deleteCity() throws EmptyPlacesException {
            emptyPlaces(places);
            String message = "Выбранное место не найдено";
            System.out.print("Название места для удаления: ");
            String name = scan.nextLine().toLowerCase();

            if (places.containsKey(name)) {
                Region.unattachCityFrom(places.get(name));
                places.remove(name);
                message = "Выбранное место успешно удалено";
            }

            return message;
        }
    }

    public static void showChangeOptions(){
        System.out.print("""
                Изменить:
                1. Имя
                2. Население
                3. Описание
                4. Принадлежность региону
                Выбор:\040"""
        );
    }
    public void handleChange() throws EmptyStringException {
        Scanner scan = new Scanner(System.in);
        showChangeOptions();

        switch (scan.nextLine()) {
            case "1" -> setName(Interaction.handleName());
            case "2" -> setPopulation(CityInteraction.handlePopulation());
            case "3" -> setDescription(Interaction.handleDescription());
            case "4" -> {
                // Сперва открепим город от текущего региона, затем запросим новый
                Region.unattachCityFrom(places.get(name));
                setRegionAttachment(CityInteraction.handleCityToRegion());
            }
            default -> {
                System.out.println("Неверно заданная команда. Попробуйте еще раз");
                handleChange();
            }
        }
    }

    public String toString(){
        return String.format("%s: %s\nНаселение: %d\nОписание: %s\nРегион: %s\n",
                getPlaceType(), name, population, description, getRegionAttachment());
    }
}
