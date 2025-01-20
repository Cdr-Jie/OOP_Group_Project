import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class DateChanger extends JDialog implements ActionListener{
    private String newDate;
    private JLabel dateLabel;
    private JPanel datePanel = new JPanel();
    private Integer days[] = new Integer[31];
    private final int big_month[] = {0,2,4,6,7,9,11};
    private final int small_month[] = {3,5,8,10,-1,-1};
    private Integer years[] = {2025, 2026, 2027, 2028, 2029, 2030};
    private JComboBox<Integer> yearsComboBox;
    private JComboBox<String> monthsComboBox;
    private JComboBox<Integer> daysComboBox = new JComboBox<>();
    private JButton setButton = new JButton("Set");
    private boolean dateSet = false;

    DateChanger(JDialog parent, String date_type) {
        super(parent, "Change " + date_type + " Date", true);

        dateLabel = new JLabel("Change " + date_type + " date");

        this.setLayout(new FlowLayout());

        datePanel.add(dateLabel);

        for (int i = 1; i <= 31; i++) {
            days[i - 1] = i;
        }
        daysComboBox.setModel(new DefaultComboBoxModel<>(days));

        yearsComboBox = new JComboBox<>(years);
        yearsComboBox.addActionListener(this);

        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        monthsComboBox = new JComboBox<>(months);
        monthsComboBox.addActionListener(this);

        datePanel.add(yearsComboBox);
        datePanel.add(monthsComboBox);
        datePanel.add(daysComboBox);
        this.add(datePanel);

        setButton.addActionListener(e -> {
            newDate = getSelectedDate();
            dateSet = true;
            dispose(); // Close the dialog
        });
        this.add(setButton);

        this.setSize(400, 200);
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

    private boolean is_leap_year(){
        if (years[yearsComboBox.getSelectedIndex()] % 4 == 0){
            return true;
        }
        else{
            return false;
        }
    }


    public String getNewDate() {
        return dateSet ? newDate : null;
    }
}
