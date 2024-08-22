import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemSearcher {

    private static final String filePath = "./inventory_v1.txt";
    private static final Icon icon = new ImageIcon("./the_greenie_geek.png");
    private static Inventory inventory;
    private static final String appName = "Greenie Geek";

    public static void main(String[] args) {
        inventory = loadInventory(filePath);
        CitrusTree dreamCitrusTree = getFilters();
        processSearchResults(dreamCitrusTree);
        System.exit(0);
    }

    public static CitrusTree getFilters(){

        String type = (String) JOptionPane.showInputDialog(null, "Please select your preferred type", appName, JOptionPane.QUESTION_MESSAGE, icon, inventory.getAllTypes().toArray(new String[0]), null);
        if (type == null) System.exit(0);

        boolean dwarf=false;
        int chooseDwarf = JOptionPane.showConfirmDialog(null,"Would you like a dwarf tree?",appName, JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,icon);
        if(chooseDwarf==-1) System.exit(0);
        else if(chooseDwarf==0) dwarf=true;

        int potSize = Integer.parseInt((String) JOptionPane.showInputDialog(null,"Pot size (inch)? **min 8 inch",appName, JOptionPane.QUESTION_MESSAGE,icon,null,null));
        if(potSize < 8) {
            JOptionPane.showMessageDialog(null,"Invalid input. Please enter a positive number greater than 8.",appName, JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        int minPrice=-1,maxPrice = -1;
        while(minPrice<0) {
            String userInput = (String) JOptionPane.showInputDialog(null, "Enter min price range value:", appName, JOptionPane.QUESTION_MESSAGE, icon, null, null);
            if(userInput==null)System.exit(0);
            try {
                minPrice = Integer.parseInt(userInput);
                if(minPrice<0) JOptionPane.showMessageDialog(null,"Price must be >= 0.",appName, JOptionPane.ERROR_MESSAGE);
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Invalid input. Please try again.", appName, JOptionPane.ERROR_MESSAGE);
            }
        }
        while(maxPrice<minPrice) {
            String userInput = (String) JOptionPane.showInputDialog(null, "Enter max price range value:", appName, JOptionPane.QUESTION_MESSAGE,icon,null, null);
            if(userInput==null)System.exit(0);
            try {
                maxPrice = Integer.parseInt(userInput);
                if(maxPrice<minPrice) JOptionPane.showMessageDialog(null,"Price must be >= "+minPrice,appName, JOptionPane.ERROR_MESSAGE);
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Invalid input. Please try again.", appName, JOptionPane.ERROR_MESSAGE);
            }
        }

        CitrusTree dreamCitrusTree = new CitrusTree("", "", "", type, dwarf, potSize, new HashMap<>());
        dreamCitrusTree.setMaxPrice(maxPrice);
        dreamCitrusTree.setMinPrice(minPrice);

        return dreamCitrusTree;
    }

    public static void processSearchResults(CitrusTree dreamCitrusTree){
        List<CitrusTree> matching = inventory.findMatch(dreamCitrusTree);
        if(matching.size() > 0) {
            Map<String, CitrusTree> options = new HashMap<>();
            StringBuilder infoToShow = new StringBuilder("Matches found!! The following citrus trees meet your criteria: \n");
            for (CitrusTree match : matching) {
                infoToShow.append(match.getItemInformation());
                options.put(match.getProductName(), match);
            }
            String choice = (String) JOptionPane.showInputDialog(null, infoToShow + "\n\nPlease select which item you'd like to order:", appName, JOptionPane.INFORMATION_MESSAGE, icon, options.keySet().toArray(), "");
            if(choice == null) System.exit(0);
            CitrusTree chosenTree = options.get(choice);
            submitOrder(getContactInfo(),chosenTree, dreamCitrusTree.getPotSize());
            JOptionPane.showMessageDialog(null,"Thank you! Your order has been submitted. Please head to your local Greenie Geek to pay and pick up!",appName, JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null,"Unfortunately none of our citrus trees meet your criteria :("+
                    "\n\tTo exit, click OK.",appName, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static Customer getContactInfo(){
        String name;
        do{
            name = (String) JOptionPane.showInputDialog(null,"Please enter your full name (in format firstname surname): ",appName,JOptionPane.QUESTION_MESSAGE, icon, null,null);
            if(name==null) System.exit(0);
        } while(!isValidFullName(name));
        String phoneNumber;
        do{
            phoneNumber = (String) JOptionPane.showInputDialog(null,"Please enter your phone number (10-digit number in the format 0412345678): ",appName,JOptionPane.QUESTION_MESSAGE, icon, null,null);
            if(phoneNumber==null) System.exit(0);}
        while(!isValidPhoneNumber(phoneNumber));
        return new Customer(name,phoneNumber);
    }

    public static boolean isValidFullName(String fullName) {
        String regex = "^[A-Z][a-z]+\\s[A-Z][a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fullName);
        return matcher.matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^0\\d{9}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public static void submitOrder(Customer customer, CitrusTree citrusTree, int potSize) {
        String filePath = customer.name().replace(" ", "_") + "_" + citrusTree.getProductCode() + ".txt";
        Path path = Path.of(filePath);
        String lineToWrite = "Order details:" +
                "\n\tName: " + customer.name() + " ("+customer.phoneNumber() +")"+
                "\n\tItem: " + citrusTree.getProductName() + " (" + citrusTree.getProductCode() + ")" +
                "\n\tPot size (inch): "+potSize;

        try {
            Files.writeString(path, lineToWrite);
        } catch (IOException io) {
            System.out.println("Order could not be placed. \nError message: " + io.getMessage());
            System.exit(0);
        }
    }

    public static Inventory loadInventory(String filePath) {
        Inventory inventory = new Inventory();
        Path path = Path.of(filePath);
        List<String> fileContents = null;
        try {
            fileContents = Files.readAllLines(path);
        } catch (IOException io) {
            System.out.println("File could not be found");
            System.exit(0);
        }

        for (int i = 1; i < fileContents.size(); i++) {
            String[] info = fileContents.get(i).split("\\[");
            String[] singularInfo = info[0].split(",");
            String pricesRaw = info[1].replace("],","");
            String potSizesRaw = info[2].replace("],", "");
            String description = info[3].replace("]","");

            String productCode = singularInfo[0];
            String productName = singularInfo[1];
            String type = singularInfo[2].trim();

            boolean dwarf = singularInfo[3].equalsIgnoreCase("yes");

            Map<Integer,Float> potSizeToPrice = new LinkedHashMap<>();

            if(potSizesRaw.length()>0) {
                String[] optionsPotSizes = potSizesRaw.split(",");
                String[] optionsPrices = pricesRaw.split(",");
                for (int j=0;j<optionsPrices.length;j++){
                    int potSize = 0;
                    float price = 0f;
                    try {
                        potSize = Integer.parseInt(optionsPotSizes[j]);
                        price = Float.parseFloat(optionsPrices[j]);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error in file. Pot size/price option could not be parsed for item on line " + (i + 1) + ". Terminating. \nError message: " + e.getMessage());
                        System.exit(0);
                    }
                    potSizeToPrice.put(potSize,price);
                }
            }

            CitrusTree citrusTree = new CitrusTree(productName, productCode, description,type,dwarf,0,potSizeToPrice);
            inventory.addItem(citrusTree);
        }
        return inventory;
    }
}
