import java.io.*;
import java.util.*;

public class Inventory {
    private List<FruitTree> items;

    public Inventory(String inventoryFile) throws IOException {
        items = new ArrayList<>();
        loadInventory(inventoryFile);
    }

    private void loadInventory(String inventoryFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inventoryFile));
        String line;
        while ((line = reader.readLine()) != null) {
            try {
                String[] parts = line.split(","); // Assuming CSV format
                if (parts.length < 8) {
                    System.err.println("Skipping invalid line: " + line);
                    continue; // Skip this line if it's not valid
                }

                String category = parts[0];
                String type = parts[1];
                boolean dwarf = Boolean.parseBoolean(parts[2]);
                String trainingSystem = parts[3].isEmpty() ? null : parts[3];
                String[] pollinators = parts[4].isEmpty() ? new String[] {} : parts[4].split(";");
                int potSize = Integer.parseInt(parts[5]);
                double price = Double.parseDouble(parts[6]);
                String itemId = parts[7];

                FruitTree tree = new FruitTree(category, type, dwarf, trainingSystem, pollinators, potSize, price, itemId);
                items.add(tree);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing number from line: " + line);
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Error with line format: " + line);
                e.printStackTrace();
            }
        }
        reader.close();
    }

    public List<FruitTree> getItems() {
        return items;
    }
}
