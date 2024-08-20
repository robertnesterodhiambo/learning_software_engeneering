import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main class for searching the menu and processing orders.
 */
public class MenuSearcher {

    private static final String MENU_FILE = "menu.txt";
    private static final String ORDER_FILE = "output.txt";

    public static void main(String[] args) {
        Menu menu = loadMenuFromFile(MENU_FILE);

        if (menu != null) {
            Coffee dreamCoffee = getUserCoffeePreferences();
            double minPrice = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter minimum price:"));
            double maxPrice = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter maximum price:"));

            List<Coffee> matchingCoffees = menu.searchCoffees(dreamCoffee, minPrice, maxPrice);

            StringBuilder details = new StringBuilder();
            if (matchingCoffees != null && !matchingCoffees.isEmpty()) {
                for (Coffee coffee : matchingCoffees) {
                    details.append(String.format("Name: %s\n", coffee.getName()));
                    details.append(String.format("Item ID: %d\n", coffee.getId()));
                    details.append(String.format("Description: %s\n", coffee.getDescription()));
                    details.append(String.format("Number of Shots: %d\n", coffee.getNumberOfShots()));
                    details.append(String.format("Sugar: %s\n", coffee.isSugar() ? "Yes" : "No"));
                    details.append(String.format("Milk Options: %s\n", coffee.getMilkOptions().isEmpty() ? "No Milk" : coffee.getMilkOptions()));

                    // Format extras explicitly
                    if (coffee.getExtras().isEmpty()) {
                        details.append("Extras: No Extras\n");
                    } else {
                        details.append("Extras: ");
                        details.append(coffee.getExtras().stream()
                                .map(Extra::toString) // Convert each Extra enum to its string representation
                                .collect(Collectors.joining(", "))); // Join them with a comma
                        details.append("\n");
                    }

                    details.append(String.format("Price: $%.2f\n", coffee.getPrice()));
                    details.append("------------------------------------------------------\n");
                }


                Coffee selectedCoffee = (Coffee) JOptionPane.showInputDialog(null,
                        "Select a coffee:\n\n" + details.toString(),
                        "Coffee Selection",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        matchingCoffees.toArray(),
                        matchingCoffees.get(0));

                if (selectedCoffee != null) {
                    Geek user = getUserInfo();
                    writeOrderToFile(user, selectedCoffee, dreamCoffee.getMilkOptions().isEmpty() ? null : dreamCoffee.getMilkOptions().get(0), dreamCoffee.getExtras().toArray(new Extra[0]));
                    JOptionPane.showMessageDialog(null, "Order placed successfully!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No matching coffees found.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Failed to load menu.");
        }
    }

    /**
     * Loads the menu from the specified file.
     *
     * @param filename the name of the file to load the menu from
     * @return the menu loaded from the file, or null if an error occurs
     */
    public static Menu loadMenuFromFile(String filename) {
        Menu menu = new Menu();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            // Skip header line
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                Coffee coffee = parseCoffee(line);
                menu.addCoffee(coffee);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return menu;
    }

    /**
     * Parses a line of text to create a Coffee object.
     *
     * @param line the line of text to parse
     * @return the Coffee object created from the line of text
     */
    private static Coffee parseCoffee(String line) {
        String[] parts = line.split(",", -1);

        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        double price = Double.parseDouble(parts[2]);
        int numberOfShots = Integer.parseInt(parts[3]);
        boolean sugar = parts[4].equalsIgnoreCase("yes");
        List<MilkType> milkOptions = parseMilkOptions(parts[5]);
        List<Extra> extras = parseExtras(parts[6]);
        String description = parts[7];

        return new Coffee(id, name, description, numberOfShots, sugar, milkOptions, extras, price);
    }

    /**
     * Parses a string to create a list of MilkType options.
     *
     * @param milkString the string representing milk options
     * @return a list of MilkType options
     */
    private static List<MilkType> parseMilkOptions(String milkString) {
        // If the string is empty or just "[]", return an empty list
        if (milkString.equals("[]") || milkString.trim().isEmpty()) {
            return Collections.emptyList();
        }

        // Remove brackets and spaces
        String[] milkArray = milkString.replace("[", "").replace("]", "").trim().split(",");

        // Create a list to store the parsed MilkType options
        List<MilkType> milkOptions = new ArrayList<>();

        // Loop through each milk option string
        for (String milk : milkArray) {
            // Convert the string to uppercase and replace hyphens with underscores
            String formattedMilk = milk.toUpperCase().replace("-", "_");
            try {
                // Try to convert the string to a MilkType enum value
                MilkType milkType = MilkType.valueOf(formattedMilk);
                // Add the MilkType to the list
                milkOptions.add(milkType);
            } catch (IllegalArgumentException e) {
                // If the string does not match any MilkType, ignore it
            }
        }

        // Return the list of MilkType options
        return milkOptions;
    }


    /**
     * Parses the extras from a comma-separated string.
     *
     * @param extrasString the string representing extras
     * @return a list of Extra options
     */
    private static List<Extra> parseExtras(String extrasString) {
        // If the string is empty or just "[]", return an empty list
        if (extrasString.equals("[]") || extrasString.trim().isEmpty()) {
            return Collections.emptyList();
        }

        // Remove brackets and spaces, then split by commas to get individual extra options
        String[] extraArray = extrasString.replace("[", "").replace("]", "").trim().split(",");

        // Create a list to store the parsed Extra options
        List<Extra> extras = new ArrayList<>();

        // Loop through each extra option string
        for (String extra : extraArray) {
            // Convert the string to uppercase and replace spaces with underscores
            String formattedExtra = extra.toUpperCase().replace(" ", "_");
            try {
                // Try to convert the string to an Extra enum value
                Extra extraOption = Extra.valueOf(formattedExtra);
                // Add the Extra to the list
                extras.add(extraOption);
            } catch (IllegalArgumentException e) {
                // If the string does not match any Extra, ignore it
            }
        }

        // Return the list of Extra options
        return extras;
    }


    /**
     * Gets the user's coffee preferences.
     *
     * @return a Coffee object representing the user's coffee preferences
     */
    public static Coffee getUserCoffeePreferences() {
        // Use JComboBox for milk selection
        MilkType[] milkTypes = MilkType.values();
        MilkType selectedMilkType = (MilkType) JOptionPane.showInputDialog(null, "Select a milk type (or skip for no milk)",
                "Milk Selection", JOptionPane.QUESTION_MESSAGE, null, milkTypes, null);

        int numberOfShots = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter number of shots:"));
        boolean sugar = JOptionPane.showConfirmDialog(null, "Do you want sugar?", "Sugar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

        // Allow user to add multiple extras
        List<Extra> selectedExtras = new ArrayList<>();
        while (true) {
            Extra extra = (Extra) JOptionPane.showInputDialog(null, "Select an extra (or skip)",
                    "Extra Selection", JOptionPane.QUESTION_MESSAGE, null, Extra.values(), null);
            if (extra == null) {
                break;  // User chose to skip
            }
            selectedExtras.add(extra);
            int addMore = JOptionPane.showConfirmDialog(null, "Do you want to add another extra?", "Add More Extras", JOptionPane.YES_NO_OPTION);
            if (addMore == JOptionPane.NO_OPTION) {
                break;
            }
        }

        return new Coffee(0, "Dream Coffee", "", numberOfShots, sugar,
                selectedMilkType == null ? Collections.emptyList() : Arrays.asList(selectedMilkType), selectedExtras, 0);
    }

    /**
     * Gets the user's information.
     *
     * @return a Geek object containing the user's name and phone number
     */
    public static Geek getUserInfo() {
        String name = JOptionPane.showInputDialog(null, "Enter your name:");
        String phoneNumber = JOptionPane.showInputDialog(null, "Enter your phone number:");
        return new Geek(name, phoneNumber);
    }

    /**
     * Writes the details of an order to a file.
     *
     * @param user the user placing the order
     * @param coffee the coffee item being ordered
     * @param milkType the type of milk for the coffee, or null if no milk is added
     * @param extras an array of extras added to the coffee
     */
    public static void writeOrderToFile(Geek user, Coffee coffee, MilkType milkType, Extra[] extras) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(ORDER_FILE, true)))) {
            out.printf("Order details:\n");
            out.printf("Name: %s (%s)\n", user.name(), user.phoneNumber());
            out.printf("Item: %s (%d)\n", coffee.getName(), coffee.getId());
            out.printf("Milk: %s\n", milkType == null ? "No Milk" : milkType);
            out.printf("Sugar: %s\n", coffee.isSugar() ? "Yes" : "No");
            out.printf("Extras: %s\n", Arrays.toString(extras));
            out.printf("Price: $%s\n", coffee.getPrice());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
