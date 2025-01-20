import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class Receipt {
    int receipt_number;
    ArrayList<String> item_name_list = new ArrayList<>();
    ArrayList<Integer> item_quant_list = new ArrayList<>();
    ArrayList<Double> item_price_list = new ArrayList<>();
    ArrayList<Double> total_item_list = new ArrayList<>();

    ArrayList<PerishableGoods> groceries_list = new ArrayList<>();

    Receipt(){
        receipt_number = 1;
        try {
            BufferedReader fr = new BufferedReader(new FileReader(new File("Inventory.txt")));
            String line;
            while ((line = fr.readLine()) != null) {
                String[] item_details = line.split(",");
                PerishableGoods item = new PerishableGoods(item_details[0], item_details[1], Integer.parseInt(item_details[2]), Double.parseDouble(item_details[3]), item_details[4]);
                groceries_list.add(item);
            }
            fr.close();
        } catch (Exception e){
            System.out.println("Error" + e);
        }
    }

    Receipt(int receipt_number){
        this();
        this.receipt_number = receipt_number;
    }

    void addItem(String name, int quant, double price_per_item){
        Boolean new_item = true;
        int index = -1;
        for (int i = 0; i < item_name_list.size(); i++){
            if (name == item_name_list.get(i) && price_per_item  == item_price_list.get(i)){
                index = i;
                new_item = false;
            }
        }
        if(new_item){
            item_name_list.add(name);
            item_quant_list.add(quant);
            item_price_list.add(price_per_item);
            total_item_list.add(quant * price_per_item);
        }
        else{
            item_quant_list.set(index, item_quant_list.get(index) + quant);
            total_item_list.set(index, item_quant_list.get(index) * price_per_item);
        }
    }

    int numberOfItems(){
        return item_name_list.size();
    }

    String getNameAtIndex(int index){
        return item_name_list.get(index);
    }

    int getQuantAtIndex(int index){
        return item_quant_list.get(index);
    } 

    double getPriceAtIndex(int index){
        return item_price_list.get(index);
    } 
    double getTotalAtIndex(int index){
        return total_item_list.get(index);
    }

    void saveToFile(){
        try{
            double total = 0;
            File receipt = new File("./ReceiptFolder/Receipt" + receipt_number + ".txt");
            BufferedWriter fw = new BufferedWriter(new FileWriter(receipt));
            for(int i = 0; i < item_name_list.size(); i++){
                fw.write(item_name_list.get(i) + "\t\t\t" + item_quant_list.get(i) + "\t\t" + item_price_list.get(i) + "\t\t" + total_item_list.get(i) + "\n");
                total += item_quant_list.get(i) * item_price_list.get(i);
            }
            fw.write("\t\t\t\t\t\t\t\tTotal: RM" + total);
            fw.close();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    void updateInventory(){
        // save all details into a file
        try{
            File tempfile = new File("tempfile.txt");
            File originalfile = new File("Inventory.txt");

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(tempfile));
            DecimalFormat df = new DecimalFormat("0.00");

            for(int i = 0; i < groceries_list.size(); i++){
                for(int j = 0; j < item_name_list.size(); j++){
                    if (groceries_list.get(i).getItemName().equals(item_name_list.get(j)) && groceries_list.get(i).getPrice()  == item_price_list.get(j)){
                        groceries_list.get(i).changeQuantity(groceries_list.get(i).getQuantity() - item_quant_list.get(j));
                        System.out.println("Item " + groceries_list.get(i).getItemName() + " has changed quantity to " + groceries_list.get(i).getQuantity());
                        break;
                    }
                }
                fileWriter.write(groceries_list.get(i).getItemName() + "," + groceries_list.get(i).getInventoryDate() + "," + groceries_list.get(i).getQuantity() + "," + df.format(groceries_list.get(i).getPrice()) + "," + groceries_list.get(i).getExpiryDate() + "\n");
            }
            fileWriter.close();
            originalfile.delete();
            tempfile.renameTo(originalfile);
        }catch(IOException e){
                e.printStackTrace();
        }
        // clear all arraylists
        receipt_number++;
    }

    void orderCompleted(){
        saveToFile();
        updateInventory();
    }

    int getReceiptNumber(){
        return receipt_number;
    }
}
