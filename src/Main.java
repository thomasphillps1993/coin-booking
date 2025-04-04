import dao.UserDAO;
import dao.CountryDAO;
import dao.CoinDAO;
import models.User;
import models.Country;
import models.Coin;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Input for User
        System.out.print("Enter user's name: ");
        String userName = scanner.nextLine();
        User user = new User(userName);
        new UserDAO().addUser(user);
        System.out.println("User added successfully!\n");

        // Input for Country
        System.out.print("Enter country's name: ");
        String countryName = scanner.nextLine();
        System.out.print("Enter country's population: ");
        int countryPopulation = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        Country country = new Country(countryName, countryPopulation);
        new CountryDAO().addCountry(country);
        System.out.println("Country added successfully!\n");

        // Input for Coin
        System.out.print("Enter coin's name: ");
        String coinName = scanner.nextLine();
        System.out.print("Enter coin's year: ");
        int coinYear = scanner.nextInt();
        System.out.print("Enter coin's value: ");
        int coinValue = scanner.nextInt();
        System.out.print("Enter coin's weight: ");
        int coinWeight = scanner.nextInt();
        Coin coin = new Coin(coinName, coinYear, coinValue, coinWeight);
        new CoinDAO().addCoin(coin);
        System.out.println("Coin added successfully!\n");

        scanner.close();
    }
}
