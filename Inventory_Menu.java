import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Inventory_Menu extends JFrame implements ActionListener{
    // JLabel nameLabel = new JLabel("Name");
    // JLabel quantityLabel = new JLabel("Quantity");
    // JLabel priceLabel = new JLabel("Price");
    // JLabel dateLabel = new JLabel("Inventory date"); 
    // JLabel expiryLabel = new JLabel("Expiry date");

    JButton add_item = new JButton("Add Item");
    JButton edit_item;
    JButton delete_item;

    JButton search;
    JTextField search_field;

    List<List<String>> data = new ArrayList<List<String>>();  

    String[] columnNames = { "Name", "Quantity", "Price", "Inventory date", "Expiry date" };

    JTable table; // Name, expiry date, manufacture date, quantity, price
    JScrollPane scrollPane;

    Inventory_Menu() {
        this.setVisible(true);
        this.setSize(1280, 720);
        this.setLayout(null);
    
        add_item.addActionListener(this);
        add_item.setBounds(1100, 40, 100, 30);
        this.add(add_item);

        List<List<String>> data = new ArrayList<>();
        String[] columnNames = {"Item Name", "Date", "Quantity", "Price", "Expiry Date"}; // Example column names
    
        try {
            Scanner inventory_scanner = new Scanner(new File("Inventory.txt"));
    
            while (inventory_scanner.hasNextLine()) {
                List<String> arr = new ArrayList<>();
                for (int i = 0; i < 5 && inventory_scanner.hasNextLine(); i++) {
                    arr.add(inventory_scanner.nextLine());
                }
                data.add(arr);
            }
            inventory_scanner.close();
    
            String[][] data_arr = new String[data.size()][];
            for (int i = 0; i < data.size(); i++) {
                data_arr[i] = data.get(i).toArray(new String[0]);
            }
    
            JTable table = new JTable(data_arr, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBounds(10, 40, 1000, 400);
            this.add(scrollPane);
    
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Inventory file not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    
        
    
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    

    // Inventory_Menu(){
    //     this.setVisible(true);
    //     this.setSize(1280,720);
    //     this.setLayout(null);

    //     try {
    //         Scanner inventory_scanner = new Scanner(new File("Inventory.txt"));
    //         while (inventory_scanner.hasNextLine()){
    //             List<String> arr = new ArrayList<String>();
    //             for(int i = 0; i < 5; i++){
    //                 arr.add(inventory_scanner.nextLine());
    //             }
    //             data.add(arr);
    //         }
    //     } catch (FileNotFoundException e) {
    //         e.printStackTrace();
    //         JOptionPane.showMessageDialog(this, "Inventory file not found.", "Error", JOptionPane.ERROR_MESSAGE);
    //     }
       

    //     String[][] data_arr = new String[data.size()][];
    //     for (int i = 0; i < data.size(); i++) {
    //         data_arr[i] = data.get(i).toArray(new String[0]);
    //     }

    //     table = new JTable(data_arr, columnNames);

    //     scrollPane = new JScrollPane(table);
    //     scrollPane.setBounds(10,40,1000,400);

    //     add_item.addActionListener(this);
    //     add_item.setBounds(1050,40,100,30);
    //     this.add(add_item);

    //     this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    //     this.add(scrollPane);
    // }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == add_item){
            String name = JOptionPane.showInputDialog("Enter item name");
            int quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter quantity"));
            double price = Double.parseDouble(JOptionPane.showInputDialog("Enter item price"));
            int expiry = JOptionPane.showConfirmDialog(this, "Has Expiry?", "New Item", JOptionPane.YES_NO_OPTION);
            if (expiry == 0){
                System.out.println("Perishable");
                PerishableGoods new_item = new PerishableGoods(name, quantity, price);
                new_item.writeToFile();
            }
            else if (expiry == 1){
                System.out.println("Non Perishable");
                Groceries new_item = new Groceries(name,quantity,price);
                new_item.writeToFile();
            }
            /*Filewrite details into a text file probably */
        }
    }
    public static void main(String[] args) {
        new Inventory_Menu();
    }
}