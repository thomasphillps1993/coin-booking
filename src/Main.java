import dao.UserDAO;
import dao.CountryDAO;
import dao.CoinDAO;
import models.User;
import models.Country;
import models.Coin;
import java.util.Scanner;  // Import Scanner

public class Main {
    public static void main(String[] args) throws Exception {
        // Get or add user
        System.out.print("Enter user's name: ");
        Scanner scanner = new Scanner(System.in);
        String userName = scanner.nextLine();
        
        UserDAO userDAO = new UserDAO();
        int userId = userDAO.getUserIdByName(userName);
        
        if (userId == -1) {
            // User doesn't exist, create a new user
            User user = new User(userName);
            userId = userDAO.addUser(user);
            System.out.println("User added successfully!");
        } else {
            System.out.println("User already exists.");
        }

        System.out.print("Enter country's name: ");
        String countryName = scanner.nextLine();
        
        CountryDAO countryDAO = new CountryDAO();
        int countryId = countryDAO.getCountryIdByName(countryName);
        
        if (countryId == -1) {
            // Country doesn't exist, get more details and add it
            System.out.print("Enter year established: ");
            int yearEstablished = scanner.nextInt();
            System.out.print("Enter population: ");
            int population = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        
            Country country = new Country(countryName, yearEstablished, population);
            countryId = countryDAO.addCountry(country.getName(), country.getYearEstablished(), country.getPopulation());
            System.out.println("Country added successfully!");
        } else {
            System.out.println("Country already exists.");
        }

        // Get coin info
        System.out.print("Enter coin's name: ");
        String coinName = scanner.next();
        System.out.print("Enter coin's year: ");
        int coinYear = scanner.nextInt();
        System.out.print("Enter coin's value: ");
        double coinValue = scanner.nextDouble(); // Use double for coin value
        System.out.print("Enter coin's weight: ");
        int coinWeight = scanner.nextInt();
        
        Coin coin = new Coin(coinName, coinYear, coinValue, coinWeight, userId, countryId);
        CoinDAO coinDAO = new CoinDAO();
        coinDAO.addCoin(coin);
        System.out.println("Coin added successfully!");
    }
}
