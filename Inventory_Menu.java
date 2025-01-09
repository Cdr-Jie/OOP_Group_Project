import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

public class Inventory_Menu extends JFrame implements ActionListener{
    JButton add_item = new JButton("Add Item");
    JButton edit_item = new JButton("Edit Item");
    JButton delete_item;
    JButton search;
    JTextField search_field;

    List<List<String>> data = new ArrayList<List<String>>();  

    final String[] columnNames = { "Name", "Inventory date", "Quantity", "Price",  "Expiry date" };

    JTable table;

    JScrollPane scrollPane;

    Inventory_Menu() {
        this.setVisible(true);
        this.setSize(1280, 720);
        this.setLayout(null);
    
        add_item.addActionListener(this);
        add_item.setBounds(1100, 40, 100, 30);
        this.add(add_item);

        edit_item.addActionListener(this);
        edit_item.setBounds(1100,100,100,30);
        this.add(edit_item);

        List<List<String>> data = new ArrayList<>();
    
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Inventory file not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        String[][] data_arr = new String[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            data_arr[i] = data.get(i).toArray(new String[0]);
        }

        DefaultTableModel tableModel = new DefaultTableModel(data_arr, columnNames){
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 40, 1000, 400);
        this.add(scrollPane);
    
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == add_item){
            String name = JOptionPane.showInputDialog("Enter item name");
            int quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter quantity"));
            double price = Double.parseDouble(JOptionPane.showInputDialog("Enter item price"));
            int expiry = JOptionPane.showConfirmDialog(this, "Has Expiry?", "New Item", JOptionPane.YES_NO_OPTION);
            if (expiry == 0){
                System.out.println("Perishable");
                PerishableGoods new_item = new PerishableGoods(name, quantity, price);
                new_item.writeToFile("Inventory.txt");
            }
            else if (expiry == 1){
                System.out.println("Non Perishable");
                Groceries new_item = new Groceries(name,quantity,price);
                new_item.writeToFile("Inventory.txt");
            }
            String[][] data_arr = new String[data.size()][];
            updateTable();
        }
        else if (ae.getSource() == edit_item){
            try{
                String name;
                String inventory_date;
                int quantity;
                double price;
                String expiry_date;
                File tempfile = new File("tempfile.txt");
                File originalfile = new File("Inventory.txt");
                BufferedWriter fileWriter = new BufferedWriter(new FileWriter(tempfile));
                BufferedReader fileReader = new BufferedReader(new FileReader(originalfile));
                int item_index = table.getSelectedRow();

                for (int i = 0; i < item_index; i++) {
                    for (int j = 0; j < 5; j++){
                        if (i == 0 && j == 0){
                            fileWriter.write(fileReader.readLine());
                        }
                        else{
                            fileWriter.write(System.getProperty("line.separator") + fileReader.readLine());
                        }
                    }
                }
                name = fileReader.readLine();
                inventory_date = fileReader.readLine();
                quantity = Integer.parseInt(fileReader.readLine());
                price = Double.parseDouble(fileReader.readLine());
                expiry_date = fileReader.readLine();

                if (expiry_date.equals("-")){
                    Groceries item = new Groceries(name, quantity, price, inventory_date);
                    item.openEditorMenu();
                    DecimalFormat df = new DecimalFormat("#.##");
                    fileWriter.write("\n" + item.getItemName());
                    fileWriter.write("\n" + item.getInventoryDate());
                    fileWriter.write("\n" + item.getQuantity());
                    fileWriter.write("\n" + df.format(item.getPrice()));
                    fileWriter.write("\n" +"-");
                    String line;
                    while ((line = fileReader.readLine()) != null) {
                        fileWriter.write("\n" + line);
                    }
                    fileWriter.close();
                    fileReader.close();
                    originalfile.delete();
                    System.out.println(item);
                    System.out.println(tempfile.renameTo(originalfile));
                    updateTable();
                }
                else{
                    PerishableGoods item = new PerishableGoods(name, quantity, price);
                    /* call perishableGoods.editorMenu() */
                    System.out.println(item);
                }
            } catch (FileNotFoundException e){
                System.out.println("Inventory file not found");
                e.printStackTrace();
            } catch (IOException e){
                System.out.println("IO error");
                e.printStackTrace();
            }

            JFrame editor  = new JFrame();
            JLabel nameLabel, inventory_dateLabel, quantityLabel, expiryLabel, priceLabel;
            JTextField nameTextField, inventory_dateTextField, quantityTextField, priceTextField;
            JButton changeButton = new JButton("Change");

            // instatiate another perishable good to store the new item's info
            changeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae){

                }
            });
        }
    }

    void updateTable(){
        List<List<String>> new_data = new ArrayList<>();
        try {
            Scanner file_scanner = new Scanner(new File("Inventory.txt"));
            while (file_scanner.hasNextLine()) {
                List<String> temp_arr = new ArrayList<>();
                for (int i = 0; i < 5 && file_scanner.hasNextLine(); i++) {
                    temp_arr.add(file_scanner.nextLine());
                }
                new_data.add(temp_arr);
            }
            file_scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Inventory file not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        String[][] new_data_arr = new String[new_data.size()][];
        for (int i = 0; i < new_data.size(); i++) {
            new_data_arr[i] = new_data.get(i).toArray(new String[0]);
        }
        DefaultTableModel newModel = new DefaultTableModel(new_data_arr, columnNames){
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };
        table.setModel(newModel);
    }
    public static void main(String[] args) {
        new Inventory_Menu();
    }
}