import java.util.*;
import javax.swing.*;
import java.text.*;
import java.io.*;


public class Groceries {
    String item_name;
    String inventory_date; // the day the item entered inventory
    int quantity;
    double price;

    Groceries(String item_name, int quantity, double price){
        this.item_name = item_name;
        this.quantity = quantity;
        this.price = price;

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar today = Calendar.getInstance();
        this.inventory_date = dateFormat.format(today.getTime());
    }

    void writeToFile(){
        try{
            FileWriter fw = new FileWriter("Inventory.txt",true);
            fw.write("\n" + item_name + "\n" + inventory_date + "\n" + quantity + "\n" + price + "\n" + "-");
            fw.close();
        }catch(IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    void changeItemName(String item_name){
        this.item_name = item_name;
    }

    void changeQuantity(int quantity){
        this.quantity = quantity;
    }

    void changePrice(double price){
        this.price = price;
    }

    void changeInventoryDate(String inventory_date){
        this.inventory_date = inventory_date;
    }

    String getItemName(){
        return item_name;
    }

    int getQuantity(){
        return quantity;
    }

    double getPrice(){
        return price;
    }

    public String toString(){
        return "Item name: " + item_name + "\nDate entered inventory: " + inventory_date + "\nQuantity: " + quantity + "\nPrice: " + price + "\n";
    }
}

