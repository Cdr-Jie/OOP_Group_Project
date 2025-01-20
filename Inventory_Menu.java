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
    JButton delete_item = new JButton("Delete Item");
    JButton search;
    JButton backButton = new JButton("Back");
    JTextField search_field;
    JTextArea console_field = new JTextArea();

    List<List<String>> data = new ArrayList<List<String>>();  

    final String[] columnNames = { "Name", "Inventory date", "Quantity", "Price",  "Expiry date" };

    JTable table;

    JScrollPane scrollPane;

    Inventory_Menu() {
        this.setVisible(true);
        this.setSize(1280, 720);
        this.setLayout(null);
    
        add_item.addActionListener(this);
        add_item.setBounds(1050, 40, 150, 45);
        this.add(add_item);

        edit_item.addActionListener(this);
        edit_item.setBounds(1050,100,150,45);
        this.add(edit_item);

        delete_item.addActionListener(this);
        delete_item.setBounds(1050,160,150,45);
        this.add(delete_item);

        backButton.addActionListener(this);
        backButton.setBounds(1050,400,150,45);
        this.add(backButton);

        List<List<String>> data = new ArrayList<>();
    
        try {
            Scanner inventory_scanner = new Scanner(new File("Inventory.txt"));
            while (inventory_scanner.hasNextLine()) {
                List<String> arr = new ArrayList<>();
                for (String x : inventory_scanner.nextLine().split(",")){
                    arr.add(x);
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

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(10, 40, 1000, 400);
        this.add(tableScrollPane);

        console_field.setForeground(Color.blue);
        console_field.setText("Inventory view mode\n");
        JScrollPane consoleScrollPane = new JScrollPane(console_field);
        consoleScrollPane.setBounds(10,450,1000,200);

        this.add(consoleScrollPane);

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
            console_field.append("New item \"" + name + "\" has been added\n");
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
                    fileWriter.write(fileReader.readLine() + "\n");
                }

                String[] changing_item = fileReader.readLine().split(",");

                name = changing_item[0];
                inventory_date = changing_item[1];
                quantity = Integer.parseInt(changing_item[2]);
                price = Double.parseDouble(changing_item[3]);
                expiry_date = changing_item[4];

                if (expiry_date.equals("-")){
                    Groceries item = new Groceries(name, quantity, price, inventory_date);
                    item.openEditorMenu();
                    DecimalFormat df = new DecimalFormat("0.00");
                    fileWriter.write(item.getItemName() + "," + item.getInventoryDate() + "," + item.getQuantity() + "," + df.format(item.getPrice()) + "," + "-" + "\n");
                    String line;
                    while ((line = fileReader.readLine()) != null) {
                        fileWriter.write(line + "\n");
                    }
                    fileWriter.close();
                    fileReader.close();
                    originalfile.delete();
                    System.out.println(item);
                    System.out.println(tempfile.renameTo(originalfile));
                    updateTable();
                    console_field.append("Item \"" + name + "\" has been edited\n");
                }
                else{
                    PerishableGoods item = new PerishableGoods(name, quantity, price);
                    /* call perishableGoods.editorMenu() */
                    item.openEditorMenu();
                    DecimalFormat df = new DecimalFormat("0.00");
                    fileWriter.write(item.getItemName() + "," + item.getInventoryDate() + "," + item.getQuantity() + "," + df.format(item.getPrice()) + "," + item.getExpiryDate() + "\n");
                    String line;
                    while ((line = fileReader.readLine()) != null) {
                        fileWriter.write(line + "\n");
                    }
                    fileWriter.close();
                    fileReader.close();
                    originalfile.delete();
                    System.out.println(item);
                    System.out.println(tempfile.renameTo(originalfile));
                    updateTable();
                    console_field.append("Item \"" + name + "\" has been edited\n");
                }
            } catch (FileNotFoundException e){
                System.out.println("Inventory file not found");
                e.printStackTrace();
            } catch (IOException e){
                System.out.println("IO error");
                e.printStackTrace();
            }
        }
        else if (ae.getSource() == delete_item){
            try{
                File tempfile = new File("tempfile.txt");
                File originalfile = new File("Inventory.txt");
                BufferedWriter fileWriter = new BufferedWriter(new FileWriter(tempfile));
                BufferedReader fileReader = new BufferedReader(new FileReader(originalfile));
                int item_index = table.getSelectedRow();
                int i = 0;
                String line, name = "";
                while((line = fileReader.readLine()) != null){
                    if (i != item_index){
                        fileWriter.write(line + "\n");
                    }
                    else{
                        name = line.split(",")[0];
                    }
                    i++;
                }
                fileWriter.close();
                fileReader.close();
                originalfile.delete();
                System.out.println(tempfile.renameTo(originalfile));
                updateTable();
                console_field.append("Item \"" + name + "\" has been deleted\n");
            } catch (FileNotFoundException e){
                System.out.println("Inventory file not found");
                e.printStackTrace();
            } catch (IOException e){
                System.out.println("IO error");
                e.printStackTrace();
            }
        }
        else if(ae.getSource() == backButton){
            this.dispose();
            new Main_Menu();
        }
    }

    void updateTable(){
        List<List<String>> new_data = new ArrayList<>();
        try {
            Scanner file_scanner = new Scanner(new File("Inventory.txt"));
            while (file_scanner.hasNextLine()) {
                List<String> temp_arr = new ArrayList<>();
                for (String x : file_scanner.nextLine().split(",")) {
                    temp_arr.add(x);
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