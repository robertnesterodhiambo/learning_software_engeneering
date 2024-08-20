/**
 * Represents a user (geek) placing an order.
 */
public class Geek {
    private String name;
    private String phoneNumber;

    /**
     * Constructs a new Geek instance with the specified name and phone number.
     *
     * @param name the name of the user
     * @param phoneNumber the phone number of the user
     */
    public Geek(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the name of the user.
     *
     * @return the user's name
     */
    public String name() {
        return name;
    }

    /**
     * Returns the phone number of the user.
     *
     * @return the user's phone number
     */
    public String phoneNumber() {
        return phoneNumber;
    }
}

