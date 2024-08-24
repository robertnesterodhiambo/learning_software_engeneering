import java.io.*;

public class Customer {
    private String name;
    private String phone;

    public Customer(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public void reserveItem(FruitTree tree) throws IOException {
        String reservationDetails = "Order details:\n" +
                                    "Name: " + name + " (" + phone + ")\n" +
                                    "Item: '" + tree.getType() + "' Tree (" + tree.getItemId() + ")\n" +
                                    "Pot size (inch): " + tree.getPotSize() + "\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reservation.txt", true))) {
            writer.write(reservationDetails);
            writer.newLine();
        }
    }

  
}
