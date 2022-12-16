package GivenPlaces;


import GivenPlaces.Utilits.CustomExceptions.EmptyPlacesException;
import GivenPlaces.Utilits.CustomExceptions.EmptyStringException;
import GivenPlaces.Utilits.CustomExceptions.NotExistingCommandException;

public class Capital extends City {

    public Capital(String name, int population, String description, String regionAttachment){
        super(name, population, description, regionAttachment);
    }

    public static String getPlaceType() {
        return "Столица";
    }

    public static class CapitalInteraction extends CityInteraction{
        public static void handleOption(String option) throws EmptyStringException, EmptyPlacesException, NotExistingCommandException {
            System.out.println(
                    switch (option) {
                        case "Создать" -> createObject();
                        case "Удалить" -> deleteCity();
                        case "Изменить" -> changeObject(City.getCities());
                        case "Показать" -> showObjects(City.getCities());
                        default -> throw new NotExistingCommandException(
                                String.format("Системная ошибка: команда \"%s\" не обрабатывается", option));
                    }
            );
        }
        private static String createObject() throws EmptyStringException {
            Capital createdCapital = new Capital(handleName(), handlePopulation(), handleDescription(), handleCityToRegion());

            if (!createdCapital.getRegionAttachment().equals("Не принадлежит какому-либо региону"))
                Region.attachCityToRegion(createdCapital);

            City.getCities().put(createdCapital.getName(), createdCapital);
            return String.format("Новое место \"%s %s\" успешно добавлено", getPlaceType(), createdCapital.getName());
        }
    }

    public String toString(){
        return String.format("%s: %s\nНаселение: %d\nОписание: %s\nРегион: %s\n", getPlaceType(), name, population, description, regionAttachment);
    }
}
