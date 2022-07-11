import javax.swing.*;
import java.awt.*;


public class main{

    static BoardGame panel = new BoardGame();

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        Label l1;
        l1 = new Label("Sudoku");
        l1.setBounds(75,20, 150,30);
        l1.setFont(new Font("ARIEL", Font.PLAIN, 18));
        frame.add(l1);

        JButton b=new JButton("play sudoku");
        b.setBounds(30,50,150,50);
        frame.add(b);


        String options1[]={"very easy","easy","medium","difficult","very difficult"};
        JComboBox cb1=new JComboBox(options1);
        cb1.setBounds(30, 100,150,20);
        frame.add(cb1);

        frame.setSize(900, 900);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setBounds(250,20,600,800);
        panel.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
        panel.removeAll();
        frame.add(panel);

        panel.startNewBoard(40);

        b.addActionListener(e -> {
            panel.removeAll();
            panel.showSolution();


            String s=((String)cb1.getItemAt(cb1.getSelectedIndex()));
            if (s=="very easy"){
                panel.startNewBoard(40);
            } else if (s=="easy") {
                panel.startNewBoard(32);
            } else if (s=="medium") {
                panel.startNewBoard(28);
            } else if (s=="difficult") {
                panel.startNewBoard(24);
            } else if (s=="very difficult") {
                panel.startNewBoard(20);
            }else {
                int givenNumber = Integer.valueOf((String) cb1.getItemAt(cb1.getSelectedIndex()));
                panel.startNewBoard(givenNumber);
            }

        });

        panel.setLayout(null);
        panel.setOpaque(false);
        frame.setVisible(true);
    }

}
