import java.util.*;
import javax.swing.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class PerishableGoods extends Groceries{
    String expiry_date;
    PerishableGoods(String item_name, int quantity, double price){
        super(item_name, quantity, price);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Using today's date
        c.add(Calendar.DATE, 7); // Adding 7 days
        expiry_date = sdf.format(c.getTime());
    }

    void changeExpiryDate(){
        expiryDateChanger expiryDialog = new expiryDateChanger(null);
        expiryDialog.setVisible(true); // Show the dialog modally
        String selectedDate = expiryDialog.getNewExpiryDate();

        if (selectedDate != null) {
            expiry_date = selectedDate; // Update the expiry date
        }

    }

    void writeToFile(){
        try{
            changeExpiryDate();
            FileWriter fw = new FileWriter("Inventory.txt",true);
            fw.write("\n" + item_name + "\n" + inventory_date + "\n" + quantity + "\n" + price + "\n" + expiry_date);
            fw.close();
        }catch(IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    String getExpiryDate(){
        return expiry_date;
    }

    public String toString(){
        return "Item name: " + item_name + "\nDate entered inventory: " + inventory_date + "\nQuantity: " + quantity + "\nPrice: " + price + "\nExpiry date: " + expiry_date;
    }
}