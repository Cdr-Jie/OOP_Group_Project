import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.io.*;


public class Groceries {
    private String item_name;
    private String inventory_date; // the day the item entered inventory
    private int quantity;
    private double price;
    
    Groceries(String item_name, int quantity, double price){
        this.item_name = item_name;
        this.quantity = quantity;
        this.price = price;

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar today = Calendar.getInstance();
        this.inventory_date = dateFormat.format(today.getTime());
    }

    Groceries(String item_name, int quantity, double price, String inventory_date){
        this(item_name, quantity, price);
        this.inventory_date = inventory_date;
    }

    void openEditorMenu(){
        final int X_LABEL = 130;
        final int X_FIELD = 220;
        DecimalFormat df = new DecimalFormat("#.##");
        JDialog editorFrame = new JDialog(new JFrame(),"Edit item", true);
        editorFrame.setLayout(null);
        editorFrame.setSize(500,400);
        editorFrame.setResizable(false);
        JLabel nameLabel = new JLabel("Item Name:");
        nameLabel.setBounds(X_LABEL,20,120,30);
        JTextField nameTextField = new JTextField(item_name);
        nameTextField.setBounds(X_FIELD,20,120,30);
        JLabel quantLabel = new JLabel("Quantity:");
        quantLabel.setBounds(X_LABEL,70,120,30);
        JTextField quantTextField = new JTextField(df.format(quantity));
        quantTextField.setBounds(X_FIELD,70,120,30);
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(X_LABEL,120,120,30);
        JTextField priceTextField = new JTextField(df.format(price));
        priceTextField.setBounds(X_FIELD,120,120,30);
        JLabel invLabel = new JLabel("Date entered:");
        invLabel.setBounds(X_LABEL,170,120,30);
        JLabel invDateLabel = new JLabel(inventory_date);
        // invDateLabel.setBounds(180,170,120,30);
        JPanel invDatePanel = new JPanel();
        invDatePanel.add(invDateLabel);
        invDatePanel.setBounds(X_FIELD,170,120,30);
        invDatePanel.setBackground(Color.white);
        invDatePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        JButton changeDateButton = new JButton("Change Date");
        changeDateButton.setBounds(X_FIELD,210,120,20);
        changeDateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                changeInventoryDate(editorFrame);
                invDateLabel.setText(inventory_date);
            }
        });
        JButton doneButton = new JButton("Done");
        doneButton.setBounds(260,280,150,40);
        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                item_name = nameTextField.getText();
                quantity = Integer.parseInt(quantTextField.getText());
                price = Double.parseDouble(priceTextField.getText());
                editorFrame.dispose();
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(80,280,150,40);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                editorFrame.dispose();
            }
        });
        editorFrame.add(nameLabel);
        editorFrame.add(nameTextField);
        editorFrame.add(quantLabel);
        editorFrame.add(quantTextField);
        editorFrame.add(priceLabel);
        editorFrame.add(priceTextField);
        editorFrame.add(invLabel);
        editorFrame.add(invDatePanel);
        editorFrame.add(changeDateButton);
        editorFrame.add(doneButton);
        editorFrame.add(cancelButton);
        editorFrame.setVisible(true);
        editorFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    }

    void writeToFile(String filename){
        try{
            FileWriter fw = new FileWriter(filename,true);
            fw.write(item_name + "," + inventory_date + "," + quantity + "," + price + "," + "-\n");
            fw.close();
        }catch(IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    void changeInventoryDate(JDialog parent){
        DateChanger inventoryDateChanger = new DateChanger(parent,"Inventory");
        inventoryDateChanger.setVisible(true); // Show the dialog modally
        String selectedDate = inventoryDateChanger.getNewDate();

        if (selectedDate != null) {
            inventory_date = selectedDate; // Update the expiry date
        }
    }

    String getInventoryDate(){
        return inventory_date;
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

