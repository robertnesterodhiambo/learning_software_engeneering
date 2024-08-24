import java.io.*;
import java.util.*;

public class ItemSearcher {
    private Inventory inventory;

    public ItemSearcher(Inventory inventory) {
        this.inventory = inventory;
    }

    public List<FruitTree> search(String category, String type, Boolean dwarf, String trainingSystem, String[] pollinators, int potSize, double minPrice, double maxPrice) {
        List<FruitTree> results = new ArrayList<>();
        for (FruitTree item : inventory.getItems()) {
            if (!item.getCategory().equalsIgnoreCase(category)) continue;
            if (type != null && !item.getType().equalsIgnoreCase(type)) continue;
            if (dwarf != null && item.isDwarf() != dwarf) continue;
            if (trainingSystem != null && (item.getTrainingSystem() == null || !item.getTrainingSystem().equalsIgnoreCase(trainingSystem))) continue;
            if (pollinators != null && !Arrays.asList(item.getPollinators()).containsAll(Arrays.asList(pollinators))) continue;
            if (item.getPotSize() != potSize) continue;
            if (item.getPrice() < minPrice || item.getPrice() > maxPrice) continue;
            results.add(item);
        }
        return results;
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
                              "Pot size (inch): " + tree.getPotSize() + "\n";
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

            // Example search
            String category = "citrus"; // Example input
            String type = null; // Example input
            Boolean dwarf = null; // Example input
            String trainingSystem = null; // Example input
            String[] pollinators = null; // Example input
            int potSize = 10; // Example input
            double minPrice = 10.0; // Example input
            double maxPrice = 50.0; // Example input

            List<FruitTree> results = searcher.search(category, type, dwarf, trainingSystem, pollinators, potSize, minPrice, maxPrice);
            searcher.displayResults(results);

            if (!results.isEmpty()) {
                // Example reservation
                FruitTree treeToReserve = results.get(0); // Just an example
                String customerName = "Dr. Walter Shepman";
                String contactNumber = "0486756465";
                searcher.reserveTree(treeToReserve, customerName, contactNumber);
            } else {
                searcher.suggestAlternatives(results);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
