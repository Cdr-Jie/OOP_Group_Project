import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.*;
import javax.tools.JavaFileManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.time.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.*;
import java.util.*;
import javax.swing.*;


public class Tester extends JFrame implements ActionListener{
    static void StartUpMenu(){
        new Main_Menu();
    }
    Tester(){
        JButton button = new JButton();
        this.add(button);
        button.addActionListener(this);
        this.setVisible(true);
        this.setSize(100,100);
    }
    public void actionPerformed(ActionEvent ae){
        StartUpMenu();
    }

    public static void main(String[] args) {
        //new Tester();
        // new Main_Menu();
        //StartUpMenu();

        // try {
        //     // Step 1: Create a DocumentBuilderFactory
        //     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //     // Step 2: Create a DocumentBuilder
        //     DocumentBuilder builder = factory.newDocumentBuilder();

        //     // Step 3: Create a new Document
        //     Document document = builder.newDocument();

        //     // Step 4: Create the root element and append it to the document
        //     Element rootElement = document.createElement("Groceries");
        //     document.appendChild(rootElement);

        //     // Step 5: Create child elements as sub-elements
        //     Element childElement = document.createElement("Fruits");
        //     rootElement.appendChild(childElement);

        //     // Sub-element 1
        //     Element subElement1 = document.createElement("Apples");
        //     subElement1.appendChild(document.createTextNode("100"));
        //     childElement.appendChild(subElement1);

        //     // Sub-element 2
        //     Element subElement2 = document.createElement("Grapes");
        //     subElement2.appendChild(document.createTextNode("500"));
        //     childElement.appendChild(subElement2);

        //     // Step 6: Create another child element with its sub-elements
        //     Element anotherChild = document.createElement("AnotherChild");
        //     rootElement.appendChild(anotherChild);

        //     Element anotherSubElement = document.createElement("AnotherSubElement");
        //     anotherSubElement.appendChild(document.createTextNode("Another sub-element content"));
        //     anotherChild.appendChild(anotherSubElement);

        //     // Step 7: Create a Transformer for writing the document to an XML file
        //     TransformerFactory transformerFactory = TransformerFactory.newInstance();
        //     Transformer transformer = transformerFactory.newTransformer();
        //     DOMSource domSource = new DOMSource(document);

        //     // Specify the file path where the XML will be saved
        //     File outputFile = new File("output.xml");
        //     StreamResult streamResult = new StreamResult(outputFile);

        //     // Step 8: Write the DOM document to the file
        //     transformer.transform(domSource, streamResult);

        //     System.out.println("XML file created successfully: " + outputFile.getAbsolutePath());
        // } catch (ParserConfigurationException | TransformerException e) {
        //     e.printStackTrace();
        // }
    }
}

//public class Tester {
    //public static void main(String args[]){
        // MyButton newbutton = new MyButton("Confirm");
        // JFrame testFrame = new JFrame();
        // JPanel testpanel = new JPanel();
        // testpanel.add(newbutton);
        // testFrame.add(testpanel);
        // testFrame.setVisible(true);
        // testFrame.setSize(200,400);
        // Main_Menu_Refactor menu = new Main_Menu_Refactor();
    //}
//}
