import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Cashier extends JFrame implements ActionListener{
    JPanel selection_panel = new JPanel();
    static ArrayList<PerishableGoods> inv = new ArrayList<>();
    JPanel buttonsPanel = new JPanel();
    JScrollPane buttonsPane;

    JScrollPane receiptPane  = new JScrollPane();
    DecimalFormat df = new DecimalFormat("0.00");
    JPanel receipt_itemsPanel = new JPanel();
    //initialize an array of labels
    ArrayList<JLabel> name_labelList = new ArrayList<>();
    ArrayList<JLabel> price_labelList = new ArrayList<>();
    ArrayList<JLabel> quant_labelList = new ArrayList<>();
    ArrayList<JLabel> total_labelList = new ArrayList<>();

    JPanel optionPanel = new JPanel();
    JButton complete_order = new JButton("Complete Order");
    JButton delete_order = new JButton("Delete Order");
    JButton exit_button = new JButton("Exit");

    Receipt new_receipt = new Receipt();

    Cashier(){
        this.setLayout(null);
        try {
            BufferedReader fr = new BufferedReader(new FileReader(new File("Inventory.txt")));
            String line;
            while ((line = fr.readLine()) != null) {
                String[] item_details = line.split(",");
                PerishableGoods item = new PerishableGoods(item_details[0], item_details[1], Integer.parseInt(item_details[2]), Double.parseDouble(item_details[3]), item_details[4]);
                inv.add(item);
            }
        } catch (Exception e){
            System.out.println("Error" + e);
        }

        buttonsPanel.setLayout(new GridLayout(0,4));
        
        buttonsPane = new JScrollPane(buttonsPanel);
        buttonsPane.setBounds(10,10,600,600);

        for (int i = 0 ; i < inv.size(); i++){
            PerishableGoods itemSold = inv.get(i);
            JButton itemButton = new JButton("<html>" + inv.get(i).getItemName() + "<br> RM " + df.format(inv.get(i).getPrice()) + "</html>" );
            itemButton.addActionListener((e) -> {
                itemSold.soldItem(new_receipt, this);
                updateReceipt();
            });
            buttonsPanel.add(itemButton);
        }

        this.add(buttonsPane);

        for (int i = 0 ; i <= inv.size(); i++){
            JPanel panel = new JPanel(new GridLayout(0,4));
            name_labelList.add(new JLabel());
            quant_labelList.add(new JLabel());
            price_labelList.add(new JLabel());
            total_labelList.add(new JLabel());

            panel.add(name_labelList.get(i));
            panel.add(quant_labelList.get(i));
            panel.add(price_labelList.get(i));
            panel.add(total_labelList.get(i));

            receipt_itemsPanel.add(panel);
        }
        receipt_itemsPanel.setLayout(new BoxLayout(receipt_itemsPanel, BoxLayout.Y_AXIS));
        receiptPane = new JScrollPane(receipt_itemsPanel);

        receiptPane.setBounds(850,10,400,600);

        this.add(receiptPane);

        optionPanel.setLayout(new GridLayout(6,1,0,20));
        optionPanel.setBounds(650,10,150,600);

        complete_order.addActionListener(this);
        optionPanel.add(complete_order);

        delete_order.addActionListener(this);
        optionPanel.add(delete_order);

        exit_button.addActionListener(this);
        optionPanel.add(exit_button);

        this.add(optionPanel);

        this.setVisible(true);
        this.setSize(1920,1080);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    void updateReceipt(){
        int next = -1;
        for (int i = 0; i < new_receipt.numberOfItems(); i++){
            name_labelList.get(i).setText(new_receipt.getNameAtIndex(i));
            quant_labelList.get(i).setText("" + new_receipt.getQuantAtIndex(i));
            price_labelList.get(i).setText(df.format(new_receipt.getPriceAtIndex(i)));
            total_labelList.get(i).setText(df.format(new_receipt.getTotalAtIndex(i)));
            next = i + 1;
        }
        if(next > 0){
            double sum = 0;
            for(int i = 0; i < new_receipt.numberOfItems(); i++){
                sum += new_receipt.getTotalAtIndex(i);
            }
            name_labelList.get(next).setText("Total");
            total_labelList.get(next).setText("RM " + df.format(sum));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == exit_button){
            this.dispose();
            new Main_Menu();
        }
        else if(e.getSource() == complete_order){
            new_receipt.orderCompleted();
            new_receipt = new Receipt(new_receipt.getReceiptNumber());
            JOptionPane.showMessageDialog(this, "Receipt saved");
            for(int i = 0; i < inv.size(); i++){
                name_labelList.get(i).setText("");
                quant_labelList.get(i).setText("");
                price_labelList.get(i).setText("");
                total_labelList.get(i).setText("");
            }
        }
        else if (e.getSource() == delete_order){
            new_receipt = new Receipt(new_receipt.getReceiptNumber());
            JOptionPane.showMessageDialog(this, "Receipt deleted");
            for(int i = 0; i < inv.size(); i++){
                name_labelList.get(i).setText("");
                quant_labelList.get(i).setText("");
                price_labelList.get(i).setText("");
                total_labelList.get(i).setText("");
            }
        }
    }

    public static void main (String args[]){
        new Cashier();
    }
}
