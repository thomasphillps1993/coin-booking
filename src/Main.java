import dao.UserDAO;
import dao.CountryDAO;
import dao.CoinDAO;
import models.User;
import models.Country;
import models.Coin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAO(); // Assuming you already have a connection object
        CoinDAO coinDAO = new CoinDAO();
        CountryDAO countryDAO = new CountryDAO();
        Integer userId = loadLastUserId();
        if (userId != null) {
            System.out.println("Welcome back! Your user ID: " + userId);
        } else {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();

            // Try to find user by name
            userId = userDAO.getUserIdByName(name);

            if (userId < 0) {
                User user = new User(name);
                userId = userDAO.addUser(user);
            }
            saveLastUserId(userId);
            System.out.println("Hello, " + name + "! Your user ID: " + userId);
        }

        System.out.println("1. Add coin");
        System.out.println("2. Find coins");
        System.out.println("3. Clear last user");
        System.out.println("4. Get all coins for all");
        System.out.println("5. Get all countries for all");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (option) {
            case 1:
                System.out.print("Enter country's name: ");
                String countryName = scanner.nextLine();
                int countryId = countryDAO.getCountryIdByName(countryName);
                if (countryId == -1) {
                    System.out.print("Enter year established: ");
                    int yearEstablished = scanner.nextInt();
                    System.out.print("Enter population: ");
                    int population = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    Country country = new Country(countryName, yearEstablished, population);
                    countryId = countryDAO.addCountry(country.getName(), country.getYearEstablished(),
                            country.getPopulation());
                    System.out.println("Country added.");
                } else {
                    System.out.println("Country found.");
                }

                System.out.print("Enter coin name: ");
                String coinName = scanner.nextLine();
                System.out.print("Enter coin year: ");
                int coinYear = scanner.nextInt();
                System.out.print("Enter coin value: ");
                double coinValue = scanner.nextDouble();

                Coin coin = new Coin(coinName, coinYear, coinValue, userId, countryId);
                coinDAO.addCoin(coin);
                System.out.println("Coin added successfully.");
                break;

            case 2:
                if (userId == -1) {
                    System.out.println("User not found.");
                } else {
                    List<Coin> coins = coinDAO.getCoinsByUserId(userId);
                    if (coins.isEmpty()) {
                        System.out.println("No coins found.");
                    } else {
                        for (Coin c : coins) {
                            System.out.printf("Name: %s, Year: %d, Value: %.2f \n",
                                    c.getName(), c.getYear(), c.getValue());
                        }
                    }
                }
                break;

            case 3:
                clearLastUser();
                break;

            case 4:
                List<Coin> allCoins = coinDAO.getAllCoins();
                if (allCoins.isEmpty()) {
                    System.out.println("No coins found.");
                } else {
                    for (Coin c : allCoins) {
                        System.out.printf("Name: %s, Year: %d, Value: %.2f, User ID: %d, Country ID: %d\n",
                                c.getName(), c.getYear(), c.getValue(), c.getUserId(), c.getCountryId());
                    }
                }
                break;

            case 5:
            List<Country> allCountries = countryDAO.getAllCountries();
            if (allCountries.isEmpty()) {
                System.out.println("No countries found.");
            } else {
                for (Country c : allCountries) {
                    System.out.printf("Name: %s\n",
                            c.getName());
                }
            }
            break;

            default:
                System.out.println("Invalid option.");
        }
    }

    public static void saveLastUserId(int userId) {
        try (FileWriter fw = new FileWriter("last_user.txt")) {
            fw.write(String.valueOf(userId));
        } catch (IOException e) {
            System.out.println("Failed to save user.");
        }
    }

    public static Integer loadLastUserId() {
        File file = new File("last_user.txt");
        if (!file.exists())
            return null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return Integer.parseInt(br.readLine());
        } catch (IOException | NumberFormatException e) {
            return null;
        }
    }

    public static void clearLastUser() {
        File file = new File("last_user.txt");
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Last user cleared.");
            } else {
                System.out.println("Failed to clear last user.");
            }
        } else {
            System.out.println("No saved user to clear.");
        }
    }

}
