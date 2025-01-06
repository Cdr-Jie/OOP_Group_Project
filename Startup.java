import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.Scanner;

public class Startup{
  public static void login() {
    try {
        File users = new File("UserList.txt");
        Scanner scan = new Scanner(users);
        /* Frontend goes here */
        JFrame loginFrame = new JFrame("Login");
        JPanel loginPanel = new JPanel();
        JPanel usernamePanel = new JPanel();
        JPanel passwordPanel = new JPanel();
        JLabel labelUsername = new JLabel("Username: ");
        JTextField fieldUsername = new JTextField(10);
        JLabel labelPassword = new JLabel("Password: ");
        JPasswordField fieldPassword = new JPasswordField(10);
        JButton submit = new JButton("Submit");
        JButton cancel = new JButton("Cancel");
        JButton register = new JButton("Register New User");
        JPanel buttonPanel = new JPanel();
        JPanel registerPanel = new JPanel();

        loginPanel.setLayout(new BoxLayout(loginPanel, 1));

        usernamePanel.add(labelUsername);
        usernamePanel.add(fieldUsername);

        passwordPanel.add(labelPassword);
        passwordPanel.add(fieldPassword);

        buttonPanel.add(submit);
        buttonPanel.add(cancel);

        registerPanel.add(register);

        loginPanel.add(usernamePanel);
        loginPanel.add(passwordPanel);
        loginPanel.add(buttonPanel);
        loginPanel.add(registerPanel);

        loginFrame.add(loginPanel);
        loginFrame.setVisible(true);
        loginFrame.setSize(450,250);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Buttons here */
        
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent ae){
                /*check login credentials and move to the next window if successful */
                Boolean logged_in = false;
                try{
                    System.out.println("Starting...");
                    String inputUsername = fieldUsername.getText();
                    String inputPassword = new String(fieldPassword.getPassword());
                    File users = new File("UserList.txt");
                    Scanner scan = new Scanner(users);
                    while(true){
                        if(!scan.hasNextLine()){
                            JOptionPane.showMessageDialog(loginFrame, "Invalid username or password", "An error occured", JOptionPane.ERROR_MESSAGE);
                            break;
                        }
                        String username = scan.nextLine();
                        String password = scan.nextLine();
                        if (inputUsername.equals(username) && inputPassword.equals(password)){
                            System.out.println("Login success");
                            logged_in = true;
                            break;
                        }
                    }
                    
                }catch (FileNotFoundException e){
                    System.out.println("An error occured");
                    e.printStackTrace();
                }
                if(logged_in == true){
                    loginFrame.dispose();
                    new Main_Menu();
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                System.exit(0);
            }
        });

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){

                /* Registration panel frontend */
                loginFrame.setVisible(false);

                JFrame registerFrame = new JFrame("Register New User");
                JPanel registerUserPanel = new JPanel();
                JPanel registerPasswordPanel = new JPanel();
                JPanel registerButtonPanel = new JPanel();
                JPanel registerPanel = new JPanel();
                registerPanel.setLayout(new BoxLayout(registerPanel, 1));
                JLabel newUsernameLabel = new JLabel("Enter new username: ");
                JTextField newUsernameField = new JTextField(10);
                JLabel newPasswordLabel = new JLabel("Enter new password: ");
                JPasswordField newPasswordField = new JPasswordField(10);
                JButton create = new JButton("Create");
                JButton cancel = new JButton("Cancel");

                registerUserPanel.add(newUsernameLabel);
                registerUserPanel.add(newUsernameField);
                registerPasswordPanel.add(newPasswordLabel);
                registerPasswordPanel.add(newPasswordField);

                registerButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
                registerButtonPanel.add(create);
                registerButtonPanel.add(cancel);

                registerPanel.add(registerUserPanel);
                registerPanel.add(registerPasswordPanel);
                registerPanel.add(registerButtonPanel);

                registerFrame.add(registerPanel);
                registerFrame.setSize(450,250);
                registerFrame.setVisible(true);
                registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                create.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae){
                        try{
                            FileWriter fw = new FileWriter("UserList.txt", true);
                            fw.write("\n" + newUsernameField.getText() + "\n" + new String(newPasswordField.getPassword()));
                            fw.close();
                            JOptionPane.showMessageDialog(registerFrame, "Successfully created user: " + newUsernameField.getText());
                            registerFrame.dispose();
                            loginFrame.setVisible(true);
                        }catch(IOException e){
                            System.out.println("An error occurred.");
                            e.printStackTrace();
                        }
                    }   
                });
                cancel.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent ae){
                        loginFrame.setVisible(true);
                        registerFrame.dispose();
                    }
                });
            }
        });
        /* Input reception goes here */

        // while(scan.hasNextLine()){
        //     //compare inputed username and password with the UserList.txt file
        // }    
        scan.close();
    } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }
  }
}