import java.util.*;
import java.util.concurrent.Flow;

import javax.swing.*;
import javax.swing.event.*;
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
        c.add(Calendar.DATE, 7); // Adding 7 days as default
        expiry_date = sdf.format(c.getTime());
    }

    PerishableGoods(String item_name, String inv_date, int quantity, double price, String expiry){
        super(item_name, quantity, price, inv_date);
        this.expiry_date = expiry;
    }

    void openEditorMenu(){
        final int X_LABEL = 130;
        final int X_FIELD = 220;
        DecimalFormat df = new DecimalFormat("#.##");
        JDialog editorFrame = new JDialog(new JFrame(),"Edit item",true);
        editorFrame.setLayout(null);
        editorFrame.setSize(500,500);
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
        JPanel invDatePanel = new JPanel();
        invDatePanel.add(invDateLabel);
        invDatePanel.setBounds(X_FIELD,170,120,30);
        invDatePanel.setBackground(Color.white);
        invDatePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        JButton changeinvDateButton = new JButton("Change Date");
        changeinvDateButton.setBounds(X_FIELD,210,120,20);
        changeinvDateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                changeInventoryDate();
                invDateLabel.setText(inventory_date);
            }
        });

        JLabel expLabel = new JLabel("Date entered:");
        expLabel.setBounds(X_LABEL,250,120,30);

        JLabel expDateLabel = new JLabel(expiry_date);
        JPanel expDatePanel = new JPanel();
        expDatePanel.add(expDateLabel);
        expDatePanel.setBounds(X_FIELD,250,120,30);
        expDatePanel.setBackground(Color.white);
        expDatePanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JButton changeexpDateButton = new JButton("Change Expiry");
        changeexpDateButton.setBounds(X_FIELD,290,120,20);
        changeexpDateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                changeExpiryDate();
                expDateLabel.setText(expiry_date);
            }
        });
        
        JButton doneButton = new JButton("Done");
        doneButton.setBounds(260,350,150,40);
        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                item_name = nameTextField.getText();
                quantity = Integer.parseInt(quantTextField.getText());
                price = Double.parseDouble(priceTextField.getText());
                editorFrame.dispose();
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(80,350,150,40);
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
        editorFrame.add(changeinvDateButton);
        editorFrame.add(expLabel);
        editorFrame.add(expDatePanel);
        editorFrame.add(changeexpDateButton);
        editorFrame.add(doneButton);
        editorFrame.add(cancelButton);
        editorFrame.setVisible(true);
        editorFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    void soldItem(Receipt new_receipt, JFrame parent){
        if(quantity == 0){
            JOptionPane.showMessageDialog(parent,"Out of stock");
        }
        DecimalFormat df = new DecimalFormat("0.00");
        JDialog add_item = new JDialog(parent,true);
        add_item.setLayout(null);
        add_item.setSize(500,400);
        add_item.setResizable(false);
        JSpinner quantity_sold = new JSpinner(new SpinnerNumberModel(1,0,quantity,1));
        JLabel nameLabel1 = new JLabel("Item: ");
        JLabel nameLabel2 = new JLabel(item_name, SwingConstants.RIGHT);
        JLabel quantityLabel = new JLabel("Amount: ");
        JLabel price_per_itemLabel1 = new JLabel("Price per item: ");
        JLabel price_per_itemLabel2 = new JLabel("RM " + df.format(price));
        JLabel totalLabel1  = new JLabel("Total: ");
        JLabel totalLabel2  = new JLabel("RM " + df.format(((int)quantity_sold.getValue() * price)));
        quantity_sold.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                totalLabel2.setText("RM "+ df.format(((int)quantity_sold.getValue() * price)));
            }
        });

        JButton add_to_receipt = new JButton("Add to receipt");
        add_to_receipt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new_receipt.addItem(item_name, (int)quantity_sold.getValue(), price);
                new_receipt.showWorking();
                add_item.dispose();
            }
        });

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                add_item.dispose();
            }
        });

        nameLabel1.setBounds(150,10,100,30);
        add_item.add(nameLabel1);
        nameLabel2.setBounds(200,10,150,30);
        add_item.add(nameLabel2);
        quantityLabel.setBounds(150,60,100,30);
        add_item.add(quantityLabel);
        quantity_sold.setBounds(300,60,50,30);
        add_item.add(quantity_sold);
        price_per_itemLabel1.setBounds(150,110,100,30);
        add_item.add(price_per_itemLabel1);
        price_per_itemLabel2.setBounds(300,110,100,30);
        add_item.add(price_per_itemLabel2);
        totalLabel1.setBounds(150,160,100,30);
        add_item.add(totalLabel1);
        totalLabel2.setBounds(300,160,100,30);
        add_item.add(totalLabel2);
        add_to_receipt.setBounds(250, 240, 150, 50);
        add_item.add(add_to_receipt);
        cancel.setBounds(100, 240, 130, 50);
        add_item.add(cancel);

        add_item.setVisible(true);

        add_item.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    void changeExpiryDate(){
        expiryDateChanger expiryDialog = new expiryDateChanger(null);
        expiryDialog.setVisible(true); // Show the dialog modally
        String selectedDate = expiryDialog.getNewExpiryDate();

        if (selectedDate != null) {
            expiry_date = selectedDate; // Update the expiry date
        }

    }

    void writeToFile(String filename){
        try{
            changeExpiryDate();
            FileWriter fw = new FileWriter(filename,true);
            fw.write(item_name + "," + inventory_date + "," + quantity + "," + price + "," + expiry_date + "\n");
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
    public static void main (String args[]){
        new Cashier();
    }
}