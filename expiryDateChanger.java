import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class expiryDateChanger extends JDialog implements ActionListener{
    String newExpiryDate;
    JLabel expiryLabel = new JLabel("Change expiry date ");
    JPanel expiryPanel = new JPanel();
    Integer days[] = new Integer[31];
    final int big_month[] = {0,2,4,6,7,9,11};
    final int small_month[] = {3,5,8,10,-1,-1};
    Integer years[] = {2025, 2026, 2027, 2028, 2029, 2030};
    JComboBox<Integer> yearsComboBox;
    JComboBox<String> monthsComboBox;
    JComboBox<Integer> daysComboBox = new JComboBox<>();
    JButton setButton = new JButton("Set");
    boolean dateSet = false;

    expiryDateChanger(Frame parent) {
        super(parent, "Change Expiry Date", true);
        this.setLayout(new FlowLayout());

        expiryPanel.add(expiryLabel);

        for (int i = 1; i <= 31; i++) {
            days[i - 1] = i;
        }
        daysComboBox.setModel(new DefaultComboBoxModel<>(days));

        yearsComboBox = new JComboBox<>(years);
        yearsComboBox.addActionListener(this);

        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        monthsComboBox = new JComboBox<>(months);
        monthsComboBox.addActionListener(this);

        expiryPanel.add(yearsComboBox);
        expiryPanel.add(monthsComboBox);
        expiryPanel.add(daysComboBox);
        this.add(expiryPanel);

        setButton.addActionListener(e -> {
            newExpiryDate = getSelectedDate();
            dateSet = true;
            dispose(); // Close the dialog
        });
        this.add(setButton);

        this.setSize(300, 200);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private String getSelectedDate() {
        DecimalFormat df = new DecimalFormat("00");
        int day = (int) daysComboBox.getSelectedItem();
        int month = monthsComboBox.getSelectedIndex() + 1;
        int year = (int) yearsComboBox.getSelectedItem();
        return df.format(day) + "/" + df.format(month) + "/" + year;
    }

    public void actionPerformed(ActionEvent ae){
        if (monthsComboBox.getSelectedItem() == "Feb"){
            if(is_leap_year()){
                Integer febdays[] = new Integer[29];
                for (int i = 1; i<= 29; i++){
                    febdays[i-1] = i;
                }
                DefaultComboBoxModel feb_model = new DefaultComboBoxModel<>(febdays);
                daysComboBox.setModel(feb_model);
            }
            else{
                Integer febdays[] = new Integer[28];
                for (int i = 1; i<= 28; i++){
                    febdays[i-1] = i;
                }
                DefaultComboBoxModel feb_model = new DefaultComboBoxModel<>(febdays);
                daysComboBox.setModel(feb_model);
            }
            System.out.println("February");
        }
        else{
            for (int i = 0; i < big_month.length; i++){
                if (monthsComboBox.getSelectedIndex() == big_month[i]){
                    DefaultComboBoxModel big_month_model = new DefaultComboBoxModel<>(days);
                    daysComboBox.setModel(big_month_model);
                    System.out.println("Big month");
                    break;
                }
                else if (monthsComboBox.getSelectedIndex() == small_month[i]){
                    Integer small_month[] = new Integer[30];
                    for (int j = 1; j<= 30; j++){
                        small_month[j-1] = j;
                    }
                    DefaultComboBoxModel small_month_model = new DefaultComboBoxModel<>(small_month);
                    daysComboBox.setModel(small_month_model);
                    System.out.println("Small month");
                    break;
                }
            }
        }
    }

    boolean is_leap_year(){
        if (years[yearsComboBox.getSelectedIndex()] % 4 == 0){
            return true;
        }
        else{
            return false;
        }
    }


    public String getNewExpiryDate() {
        return dateSet ? newExpiryDate : null;
    }
}
