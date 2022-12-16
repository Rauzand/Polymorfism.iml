package GivenPlaces.Utilits;

import GivenPlaces.*;
import GivenPlaces.Utilits.CustomExceptions.*;

import java.util.Scanner;

public class Menu {

    private static final Scanner scan = new Scanner(System.in);

    private static String inputChoice(){
        return scan.nextLine();
    }

    private static void interactWithObject(String option) throws NotExistingCommandException {
        boolean goBack = false;
        System.out.printf("""
                %s из категории:
                1. "Локация"
                2. "Город"
                3. "Столица"
                4. "Регион"
                5. Назад
                Выбор:\040""", option);

        try {
            String choice = inputChoice();
            System.out.println();

            switch (choice) {
                case "1" -> Location.LocationInteraction.handleOption(option);
                case "2" -> City.CityInteraction.handleOption(option);
                case "3" -> Capital.CapitalInteraction.handleOption(option);
                case "4" -> Region.RegionInteraction.handleOption(option);
                case "5" -> goBack = true;
                default -> System.out.println("Неверно заданная команда. Попробуйте еще раз");
            }
        }
        catch (EmptyStringException e){
            System.out.println("Название не может быть пустым");
        }
        catch (EmptyPlacesException e){
            System.out.printf("Пока что нету таких мест, которые можно было бы %s\n", option.toLowerCase());
        }

        if (goBack)
            mainMenu();
        else
            interactWithObject(option);

    }

    public static void mainMenu() throws NotExistingCommandException {
        System.out.print("""

                1. Создать объект
                2. Удалить объект
                3. Изменить объект
                4. Посмотреть все существующие объекты
                5. Выход
                Выбор:\040""");

        switch (inputChoice()) {
            case "1" -> interactWithObject("Создать");
            case "2" -> interactWithObject("Удалить");
            case "3" -> interactWithObject("Изменить");
            case "4" -> interactWithObject("Показать");
            case "5" -> System.exit(0);
            default -> System.out.println("Неверно заданная команда. Попробуйте еще раз");
        }
        mainMenu();
    }
}
