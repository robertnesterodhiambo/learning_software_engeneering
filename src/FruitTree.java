public class FruitTree {
    private String category;
    private String type;
    private boolean dwarf;
    private String trainingSystem; // For vines only
    private String[] pollinators;  // For pome and stone fruits
    private int potSize;
    private double price;
    private String itemId;

    public FruitTree(String category, String type, boolean dwarf, String trainingSystem, String[] pollinators, int potSize, double price, String itemId) {
        this.category = category;
        this.type = type;
        this.dwarf = dwarf;
        this.trainingSystem = trainingSystem;
        this.pollinators = pollinators;
        this.potSize = potSize;
        this.price = price;
        this.itemId = itemId;
    }

    // Getters and Setters
    public String getCategory() { return category; }
    public String getType() { return type; }
    public boolean isDwarf() { return dwarf; }
    public String getTrainingSystem() { return trainingSystem; }
    public String[] getPollinators() { return pollinators; }
    public int getPotSize() { return potSize; }
    public double getPrice() { return price; }
    public String getItemId() { return itemId; }

    public void displayDetails() {
        System.out.println("Category: " + category);
        System.out.println("Type: " + type);
        System.out.println("Dwarf: " + (dwarf ? "Yes" : "No"));
        if (trainingSystem != null) {
            System.out.println("Training System: " + trainingSystem);
        }
        if (pollinators.length > 0) {
            System.out.print("Pollinators: ");
            for (String pollinator : pollinators) {
                System.out.print(pollinator + " ");
            }
            System.out.println();
        }
        System.out.println("Pot Size: " + potSize + " inches");
        System.out.println("Price: $" + price);
        System.out.println("Item ID: " + itemId);
    }
}
