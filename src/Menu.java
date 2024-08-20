import java.util.*;

/**
 * Represents a menu of coffee items.
 */
public class Menu {
    private List<Coffee> coffees;

    /**
     * Constructs a new Menu instance with an empty list of coffees.
     */
    public Menu() {
        this.coffees = new ArrayList<>();
    }

    /**
     * Adds a coffee item to the menu.
     *
     * @param coffee the coffee item to be added
     */
    public void addCoffee(Coffee coffee) {
        coffees.add(coffee);
    }


    /**
     * Searches for coffee items on the menu that match the given criteria.
     *
     * @param dreamCoffee the coffee item with desired attributes
     * @param minPrice the minimum price of the coffee
     * @param maxPrice the maximum price of the coffee
     * @return a list of coffee items that match the specified criteria
     */
    public List<Coffee> searchCoffees(Coffee dreamCoffee, double minPrice, double maxPrice) {
        // Create a new list to store matching coffees
        List<Coffee> matchingCoffees = new ArrayList<>();

        // Loop through each coffee in the menu
        for (Coffee coffee : coffees) {
            // Check if the coffee's price is within the desired range
            if (coffee.getPrice() >= minPrice && coffee.getPrice() <= maxPrice) {
                // Check if the coffee has the desired number of shots
                if (coffee.getNumberOfShots() == dreamCoffee.getNumberOfShots()) {
                    // Check if the coffee has the desired sugar option
                    if (coffee.isSugar() == dreamCoffee.isSugar()) {
                        // Check if the coffee has the desired milk options
                        if (coffee.getMilkOptions().isEmpty() || coffee.getMilkOptions().containsAll(dreamCoffee.getMilkOptions())) {
                            // Check if the coffee has the desired extras
                            if (coffee.getExtras().isEmpty() || coffee.getExtras().containsAll(dreamCoffee.getExtras())) {
                                // If all criteria match, add the coffee to the list
                                matchingCoffees.add(coffee);
                            }
                        }
                    }
                }
            }
        }

        // Return the list of matching coffees
        return matchingCoffees;
    }

}




