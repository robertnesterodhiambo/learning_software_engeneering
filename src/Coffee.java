import java.util.List;

/**
 * Represents a coffee item on the menu.
 */
public class Coffee {
    private int id;
    private String name;
    private String description;
    private int numberOfShots;
    private boolean sugar;
    private List<MilkType> milkOptions;
    private List<Extra> extras;
    private double price;

    /**
     * Constructs a new Coffee instance with the specified details.
     *
     * @param id the unique identifier for the coffee item
     * @param name the name of the coffee
     * @param description a brief description of the coffee
     * @param numberOfShots the number of shots of espresso in the coffee
     * @param sugar true if the coffee contains sugar, false otherwise
     * @param milkOptions a list of milk types available for the coffee
     * @param extras a list of extras that can be added to the coffee
     * @param price the price of the coffee
     */
    public Coffee(int id, String name, String description, int numberOfShots, boolean sugar, List<MilkType> milkOptions, List<Extra> extras, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.numberOfShots = numberOfShots;
        this.sugar = sugar;
        this.milkOptions = milkOptions;
        this.extras = extras;
        this.price = price;
    }

    /**
     * Returns the unique identifier for the coffee item.
     *
     * @return the coffee item ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the coffee.
     *
     * @return the coffee name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a brief description of the coffee.
     *
     * @return the coffee description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the number of shots of espresso in the coffee.
     *
     * @return the number of shots
     */
    public int getNumberOfShots() {
        return numberOfShots;
    }

    /**
     * Returns whether the coffee contains sugar.
     *
     * @return true if the coffee contains sugar, false otherwise
     */
    public boolean isSugar() {
        return sugar;
    }

    /**
     * Returns the list of milk types available for the coffee.
     *
     * @return a list of milk types
     */
    public List<MilkType> getMilkOptions() {
        return milkOptions;
    }

    /**
     * Returns the list of extras that can be added to the coffee.
     *
     * @return a list of extras
     */
    public List<Extra> getExtras() {
        return extras;
    }

    /**
     * Returns the price of the coffee.
     *
     * @return the coffee price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns a string representation of the coffee, including its name and price.
     *
     * @return a string representation of the coffee
     */
    @Override
    public String toString() {
        return name + " (" + price + ")";
    }
}

