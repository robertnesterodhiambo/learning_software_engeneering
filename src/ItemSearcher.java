import java.io.*;
import java.util.*;

public class ItemSearcher {
    private Inventory inventory;
    private Scanner scanner;

    public ItemSearcher(Inventory inventory) {
        this.inventory = inventory;
        this.scanner = new Scanner(System.in);
    }

    public void interactiveSearch() {
        String category = promptForCategory();
        String type = promptForType();
        Boolean dwarf = promptForDwarf();
        String trainingSystem = promptForTrainingSystem();
        String[] pollinators = promptForPollinators();
        int potSize = promptForPotSize();
        double[] priceRange = promptForPriceRange();

        List<FruitTree> results = search(category, type, dwarf, trainingSystem, pollinators, potSize, priceRange[0], priceRange[1]);
        displayResults(results);

        if (!results.isEmpty()) {
            // Prompt for reservation
            System.out.print("Would you like to reserve a tree from the results? (yes/no): ");
            String reserveChoice = scanner.nextLine().trim().toLowerCase();
            if (reserveChoice.equals("yes")) {
                System.out.print("Enter the product code of the tree you'd like to reserve: ");
                String productCode = scanner.nextLine().trim();
                FruitTree treeToReserve = findTreeByProductCode(results, productCode);
                if (treeToReserve != null) {
                    System.out.print("Enter your name: ");
                    String customerName = scanner.nextLine().trim();
                    System.out.print("Enter your contact number: ");
                    String contactNumber = scanner.nextLine().trim();
                    try {
                        reserveTree(treeToReserve, customerName, contactNumber);
                        System.out.println("Reservation successful!");
                    } catch (IOException e) {
                        System.out.println("Error saving reservation: " + e.getMessage());
                    }
                } else {
                    System.out.println("No tree found with the specified product code.");
                }
            }
        } else {
            suggestAlternatives(results);
        }
    }

    private String promptForCategory() {
        System.out.print("Enter category (e.g., citrus, pome, vine, stone fruit): ");
        return scanner.nextLine().trim();
    }

    private String promptForType() {
        System.out.print("Enter type (e.g., apple, lemon) or press Enter to skip: ");
        String type = scanner.nextLine().trim();
        return type.isEmpty() ? null : type;
    }

    private Boolean promptForDwarf() {
        System.out.print("Is it a dwarf variety? (yes/no) or press Enter to skip: ");
        String dwarfInput = scanner.nextLine().trim().toLowerCase();
        if (dwarfInput.equals("yes")) return true;
        if (dwarfInput.equals("no")) return false;
        return null;
    }

    private String promptForTrainingSystem() {
        System.out.print("Enter training system (e.g., trellis, pergola) or press Enter to skip: ");
        String trainingSystem = scanner.nextLine().trim();
        return trainingSystem.isEmpty() ? null : trainingSystem;
    }

    private String[] promptForPollinators() {
        System.out.print("Enter recommended pollinators separated by commas or press Enter to skip: ");
        String pollinatorsInput = scanner.nextLine().trim();
        return pollinatorsInput.isEmpty() ? null : pollinatorsInput.split(",");
    }

    private int promptForPotSize() {
        System.out.print("Enter pot size (inch): ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    private double[] promptForPriceRange() {
        System.out.print("Enter minimum price: ");
        double minPrice = Double.parseDouble(scanner.nextLine().trim());

        System.out.print("Enter maximum price: ");
        double maxPrice = Double.parseDouble(scanner.nextLine().trim());

        return new double[] {minPrice, maxPrice};
    }

    private FruitTree findTreeByProductCode(List<FruitTree> results, String productCode) {
        for (FruitTree tree : results) {
            if (tree.getProductCode().equalsIgnoreCase(productCode)) {
                return tree;
            }
        }
        return null;
    }

    public List<FruitTree> search(String category, String type, Boolean dwarf, String trainingSystem, String[] pollinators, int potSize, double minPrice, double maxPrice) {
        List<FruitTree> results = new ArrayList<>();
        for (FruitTree item : inventory.getItems()) {
            if (!item.getCategory().equalsIgnoreCase(category)) continue;
            if (type != null && !item.getType().equalsIgnoreCase(type)) continue;
            if (dwarf != null && item.isDwarf() != dwarf) continue;
            if (trainingSystem != null && (item.getTrainingSystem() == null || !item.getTrainingSystem().equalsIgnoreCase(trainingSystem))) continue;
            if (pollinators != null && !Arrays.asList(item.getPollinators()).containsAll(Arrays.asList(pollinators))) continue;
            if (!Arrays.asList(item.getPotSizeOptions()).contains(potSize)) continue;
            if (!isPriceInRange(item.getPrices(), minPrice, maxPrice)) continue;
            results.add(item);
        }
        return results;
    }

    private boolean isPriceInRange(double[] prices, double minPrice, double maxPrice) {
        for (double price : prices) {
            if (price >= minPrice && price <= maxPrice) {
                return true;
            }
        }
        return false;
    }

    public void displayResults(List<FruitTree> results) {
        if (results.isEmpty()) {
            System.out.println("No items found matching the search criteria.");
        } else {
            for (FruitTree item : results) {
                item.displayDetails();
                System.out.println();
            }
        }
    }

    public void reserveTree(FruitTree tree, String customerName, String contactNumber) throws IOException {
        String orderDetails = "Order details:\n" +
                              "Name: " + customerName + " (" + contactNumber + ")\n" +
                              "Item: '" + tree.getType() + "' Tree (" + tree.getItemId() + ")\n" +
                              "Pot size (inch): " + Arrays.toString(tree.getPotSizeOptions()) + "\n";
        saveReservation(orderDetails);
    }

    private void saveReservation(String orderDetails) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("reservations.txt", true));
        writer.write(orderDetails);
        writer.newLine();
        writer.close();
    }

    public void suggestAlternatives(List<FruitTree> results) {
        if (results.isEmpty()) {
            System.out.println("No items match your exact search criteria. Consider broadening your search or exploring other categories.");
        } else {
            System.out.println("Here are some alternatives you might consider:");
            displayResults(results);
        }
    }

    public static void main(String[] args) {
        try {
            // Initialize Inventory and ItemSearcher
            Inventory inventory = new Inventory("inventory_v2.txt");
            ItemSearcher searcher = new ItemSearcher(inventory);

            // Start the interactive search
            searcher.interactiveSearch();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
