import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.concurrent.Flow;
import java.text.*;

public class Main_Menu extends JFrame implements ActionListener{
    JLabel currentTime;
    JPanel time_panel = new JPanel();

    JPanel option_select = new JPanel();
    JLabel greet = new JLabel("Group 2 POS System");
    JPanel greet_panel = new JPanel();
    
    MyButton inventory = new MyButton("Inventory");
    MyButton exit = new MyButton("Exit");
    MyButton cashier = new MyButton("Cashier");

    Main_Menu(){
        System.out.println("Creating Main Menu");
        this.setLayout(new GridLayout(4,1));

        Calendar now = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        currentTime = new JLabel(dateFormat.format(now.getTime()));
        new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar now = Calendar.getInstance();
                currentTime.setText(dateFormat.format(now.getTime()));
            }
        }).start();



        greet.setBounds(50,100,200,30);
        greet.setFont(new Font("Serif", Font.BOLD, 56));
        greet.setForeground(Color.BLUE);

        currentTime.setFont(new Font("Serif", Font.BOLD, 56));
        currentTime.setForeground(Color.BLUE);

        greet_panel.add(greet);
        greet_panel.setBackground(Color.WHITE);

        time_panel.add(currentTime);
        time_panel.setBackground(Color.WHITE);


        inventory.addActionListener(this);
        exit.addActionListener(this);
        cashier.addActionListener(this);


        inventory.setPreferredSize(new Dimension(150,100));
        option_select.add(inventory);
        cashier.setPreferredSize(new Dimension(150,100));
        option_select.add(cashier);
        exit.setPreferredSize(new Dimension(150,100));
        option_select.add(exit);

        this.add(greet_panel);
        this.add(time_panel);
        this.add(option_select);


        this.setVisible(true);
        this.setSize(1280,720);
        this.setMinimumSize(new Dimension(1280,720));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        System.out.println("Main menu created");
    }
    @Override
    public void actionPerformed(ActionEvent ae){
        if (ae.getSource() == inventory){
            /* Enter inventory interface */
            this.dispose();
            new Inventory_Menu();
        }
        else if (ae.getSource() == exit){
            /* Goes back to login */
            this.dispose();
            Startup.login();
        }
        else if (ae.getSource() == cashier){
            /* Enter cashier interface */
        }
    }
}
