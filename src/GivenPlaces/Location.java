    package GivenPlaces;

    import GivenPlaces.Utilits.CustomExceptions.*;
    import GivenPlaces.Utilits.Interaction;

    import java.util.*;

    public class Location extends Place {
        private static final HashMap<String, Location> places = new HashMap<>();

        public Location(String name, String description){
            super(name, description);
        }

        public static String getPlaceType() {
            return "Локация";
        }

        public static class LocationInteraction extends Interaction {
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
                places.put(name, new Location(name, handleDescription()));
                return String.format("Новое место \"%s %s\" успешно добавлено", getPlaceType(), name);
            }
        }

        public static void showChangeOptions(){
            System.out.print("""
                    Изменить:
                    1. Имя
                    2. Описание
                    Выбор:\040"""
            );
        }

        public void handleChange() throws EmptyStringException {
            Scanner scan = new Scanner(System.in);

            showChangeOptions();

            switch (scan.nextLine()) {
                case "1" -> setName(Interaction.handleName());
                case "2" -> setDescription(Interaction.handleDescription());
                default -> {
                    System.out.println("Неверно заданная команда. Попробуйте еще раз");
                    handleChange();
                }
            }
        }

        public String toString(){
            return String.format("%s: %s\nОписание: %s\n", getPlaceType(), name, description);
        }
    }
