import com.fathzer.soft.javaluator.DoubleEvaluator;
//please note that this is a third party package. i provided The package zip file with my project folder.
// Please unzip Javaluator and add the jars to the library.
//Steps: File>Project Structure>Libraries> '+'> select the unzipped javaluator folder> confirm

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static javax.swing.JOptionPane.showMessageDialog; //static import just for using the message dialog method


public class CalcUI extends JFrame implements ActionListener {

    /*


     ___________________ _______________
     |                 |                |
     -------------------                |
    |(    )   CLR  Del  |               |
    |7    8    9    /   |    History    |
    |4    5    6    *   |               |
    |1    2    3    -   |               |
    |.    0    =    +   |               |
    ___________________  _______________

     */

    private final JFrame frame; //mainframe
    private JTextArea historyField;
    private final JPanel panel; //this will hold the buttons in a grid layout
    private final JTextField textField;
    private final JButton[] numBtn = new JButton[10]; //using array to implement actions/functions easily later.
    private final JButton[] functionBtn = new JButton[10];

    private final JButton leftBracket, rightBracket, clrButton, delButton, dotButton;
    private final JButton addButton, minusButton, divButton, mulButton, equalButton;


    public CalcUI() {

        frame = new JFrame();
        frame.setTitle("Calculator"); //title
        frame.setSize(720, 490);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); //move window to center of display
        frame.setLayout(null); // if this is not used ,it will mess up components

        Font textFont = new Font("Lucida Sans", Font.PLAIN, 30); //for the textfield
        Font btnFont = new Font("Lucida Sans", Font.PLAIN, 20); //for the buttons

        textField = new JTextField(2);
        textField.setBounds(10, 10, 310, 80);
        textField.setEditable(true); //so that it cant be edited manually with keyboard
        textField.setFont(textFont);
        frame.add(textField);

        historyField = new JTextArea();
        historyField.setBounds(350, 10, 310, 400);
        historyField.setEditable(true);

        frame.add(historyField);

        try {
            File file = new File("Calculator_History.txt");
            Scanner reader = new Scanner(file);
            int counter = 0;
            while (reader.hasNextLine()) {
                reader.nextLine();
                counter++;
            }
            reader.close();
            reader = new Scanner(file);
            for (int i = 0; i < counter; i++) {
                String s = reader.nextLine() + '\n';
                historyField.setText(historyField.getText().concat(s));

            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //instantiating function buttons
        leftBracket = new JButton("(");
        rightBracket = new JButton(")");
        clrButton = new JButton("Clr");
        delButton = new JButton("Del");
        dotButton = new JButton(".");
        addButton = new JButton("+");
        minusButton = new JButton("-");
        divButton = new JButton("/");
        mulButton = new JButton("*");
        equalButton = new JButton("=");

        //adding to array for easier operations
        functionBtn[0] = leftBracket;
        functionBtn[1] = rightBracket;
        functionBtn[2] = clrButton;
        functionBtn[3] = delButton;
        functionBtn[4] = dotButton;
        functionBtn[5] = addButton;
        functionBtn[6] = minusButton;
        functionBtn[7] = divButton;
        functionBtn[8] = mulButton;
        functionBtn[9] = equalButton;


        //adding action listener
        for (int i = 0; i < 10; i++) {
            functionBtn[i].addActionListener(this);
            functionBtn[i].setFont(btnFont);
            functionBtn[i].setFocusable(false); //if this is not added it focuses on the char on the button
        }

        //instantiating number buttons and adding action listener
        for (int i = 0; i < 10; i++) {
            numBtn[i] = new JButton(String.valueOf(i));
            numBtn[i].addActionListener(this);
            numBtn[i].setFont(btnFont);
            numBtn[i].setFocusable(false);
        }

        panel = new JPanel();
        panel.setBounds(10, 110, 310, 300);
        panel.setLayout(new GridLayout(5, 4, 10, 10)); //5by4 grid with 10px gaps

        //now add the buttons to panel.since it's a grid layout,we need to add buttons sequentially ,and it will add them to the grid
        panel.add(leftBracket);
        panel.add(rightBracket);
        panel.add(clrButton);
        panel.add(delButton);
        panel.add(numBtn[7]);
        panel.add(numBtn[8]);
        panel.add(numBtn[9]);
        panel.add(divButton);
        panel.add(numBtn[4]);
        panel.add(numBtn[5]);
        panel.add(numBtn[6]);
        panel.add(mulButton);
        panel.add(numBtn[1]);
        panel.add(numBtn[2]);
        panel.add(numBtn[3]);
        panel.add(minusButton);
        panel.add(dotButton);
        panel.add(numBtn[0]);
        panel.add(equalButton);
        panel.add(addButton);

        //add the panel to the frame
        frame.add(panel);


    }


    private static double evaluateExpression(String s) {
        DoubleEvaluator evaluator = new DoubleEvaluator();
        Double result = evaluator.evaluate(s);
        return (double) result;
    }


    @Override
    public void actionPerformed(ActionEvent e) {//catches the action event invoked by actionListener

        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numBtn[i]) {
                textField.setText(textField.getText().concat(String.valueOf(i))); //sets the text.if text already there then gets that text and concatenates it to a new string and shows it
            }
        }
        if (e.getSource() == dotButton) {
            textField.setText(textField.getText().concat("."));
        }
        if (e.getSource() == leftBracket) {
            textField.setText(textField.getText().concat("("));
        }
        if (e.getSource() == rightBracket) {
            textField.setText(textField.getText().concat(")"));
        }
        if (e.getSource() == addButton) {
            textField.setText(textField.getText().concat("+"));
        }
        if (e.getSource() == minusButton) {
            textField.setText(textField.getText().concat("-"));
        }
        if (e.getSource() == mulButton) {
            textField.setText(textField.getText().concat("*"));
        }
        if (e.getSource() == divButton) {
            textField.setText(textField.getText().concat("/"));
        }
        if (e.getSource() == equalButton) {
            String expression = textField.getText();
            if (expression.isBlank()) {
                textField.setText("");
            } else try {
                double result = evaluateExpression(expression);
                textField.setText(String.valueOf(result));

                File file = new File("Calculator_History.txt");
                FileWriter writer = new FileWriter(file, true);

                if (!file.exists()) {
                    file.createNewFile();
                }

                writer.write(expression + '=' + result + '\n');
                writer.close();


                historyField.setText(historyField.getText().concat(expression + '=' + result + '\n'));

            } catch (Exception ex) { //exceptions are already defined in DoubleEvaluator
                System.out.println(ex.getMessage());
                showMessageDialog(null, ex.getMessage(), "Alert", JOptionPane.WARNING_MESSAGE);//shows alerts
            }
        }
        if (e.getSource() == delButton) {

            String s = textField.getText();
            if (s.isBlank()) { //if this is not added shows error since substring cannot be calculated for an empty string
                textField.setText("");
            } else
                textField.setText(s.substring(0, s.length() - 1));
        }
        if (e.getSource() == clrButton) {
            textField.setText("");
        }


    }


    public void launchCalculator() {
        try {
            CalcUI window = new CalcUI();
            window.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close when close button clocked
            window.frame.setVisible(true);//shows the frame
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
