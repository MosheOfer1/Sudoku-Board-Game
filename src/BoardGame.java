import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class BoardGame extends JPanel{
    private final int SquareSize = 50;
    private static boolean grid=true;
    private List<Object> fields = new ArrayList<>();
    private final int[] saveCostumerSudoku = new int[81];
    private  int[] ButtonSudoku = new int[81];
    boolean solved=false;

    private static boolean isNumeric(String str){
        return str != null && str.matches("[0-9.]+");
    }
    BoardGame(){super();}

    public void startNewBoard(int givenNumber){
        fields.clear();
        //solved=false;

        int[] sudoku = new int[81];
        sudoku[0]=10;
        while (sudoku[0]==10){
            System.gc();
            sudoku = GameLogic.createSudoku(givenNumber);
        }
        setObjectArrayList(sudoku,givenNumber);
    }

    public void showSolution(){

        JButton b1=new JButton("show solution");
        b1.setBounds(45,SquareSize * 9 + 100,150,50);
        solved=false;
        for (int i = 0; i < 81; i++) {
            saveCostumerSudoku[i] = 0;
            ButtonSudoku[i] = 0;
        }
        add(b1);


        b1.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                for (int i = 0; i < 81; i++) {
                    if (fields.get(i) instanceof JTextField) {
                        if (isNumeric(((JTextField) fields.get(i)).getText()))
                            saveCostumerSudoku[i] = Integer.valueOf(((JTextField) fields.get(i)).getText());
                    } else if (fields.get(i) instanceof JButton && !solved) {
                        ButtonSudoku[i] = Integer.valueOf(((JButton) fields.get(i)).getText());
                    }
                }
                if(!solved){
                    int[] f = GameLogic.getSolved(ButtonSudoku);
                    for (int i = 0; i < 81; i++) {
                        ButtonSudoku[i]=f[i];
                   }
                }
                solved=true;

                for (int i = 0; i < 81; i++) {
                    if (fields.get(i) instanceof JTextField) {
                            ((JTextField) fields.get(i)).setText(String.valueOf(ButtonSudoku[i]));
                        }
                }
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                for (int i = 0; i < 81; i++) {
                    if (fields.get(i) instanceof JTextField){
                        if (saveCostumerSudoku[i]!=0)
                            ((JTextField) fields.get(i)).setText(String.valueOf(saveCostumerSudoku[i]));
                        else
                            ((JTextField) fields.get(i)).setText(null);
                    }
                }
                for (int i = 0; i < 81; i++) {
                    saveCostumerSudoku[i]=0;
                }

                repaint();
            }
        });
    }
    public void setObjectArrayList(int[] sudoku,int givenNumber) {

        for (int i = 0; i < 81; i++) {
            if (sudoku[i] == 0) {
                fields.add(i, new JTextField());
                ((JTextField) fields.get(i)).setName(null);
            } else {
                fields.add(i, new JButton());
            }
        }
            int lineGrid=0;
            for (int counter=0; counter < 81; counter++) {
                if(counter%3==0){
                    lineGrid+=8;
                }
                if(counter%9==0){
                    lineGrid=0;
                }
                if (fields.get(counter) instanceof JTextField) {
                  ((JTextField) fields.get(counter)).setBounds(((counter%9+1) * SquareSize) + lineGrid, (counter/9 + 1) * SquareSize + counter/27*8, SquareSize, SquareSize);
                  add((Component) fields.get(counter));
                    ((JTextField) fields.get(counter)).setFont(new Font("Arial", Font.BOLD, 18));
                    ((JTextField) fields.get(counter)).setHorizontalAlignment(JTextField.CENTER);

                    int finalCounter = counter;
                    ((JTextField) fields.get(counter)).addKeyListener(new KeyAdapter(){
                    public void keyReleased(KeyEvent e) {
                        String s=((JTextField) fields.get(finalCounter)).getText();
                        if(!isNumeric(s) || Integer.valueOf(s)>9 || Integer.valueOf(s)<1){
                            ((JTextField) fields.get(finalCounter)).setText("");
                            GameLogic.setCustomer(finalCounter,0);
                            GameLogic.ZeroPointInSudoku(finalCounter);
                            if((((JTextField) fields.get(finalCounter)).getName())!=null) {
                                for (int i = 0; i < 81; i++) {
                                    if (fields.get(i) instanceof JTextField) {
                                        if (!GameLogic.recognizeTheLCS(i, finalCounter)) {
                                            if (GameLogic.checkingSudokuToFindError(i)) {
                                                if ((((JTextField) fields.get(i)).getName()) != null) {
                                                    if (Integer.parseInt(((JTextField) fields.get(i)).getName()) == 1) {
                                                        ((JTextField) fields.get(i)).setName(null);
                                                        ((JTextField) fields.get(i)).setBackground(Color.white);
                                                    } else if (Integer.parseInt(((JTextField) fields.get(i)).getName()) > 1) {
                                                        int reducedName = Integer.parseInt(((JTextField) fields.get(i)).getName()) - 1;
                                                        ((JTextField) fields.get(i)).setName(String.valueOf(reducedName));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else{
                            GameLogic.setCustomer(finalCounter,Integer.valueOf(s));
                            //((JTextField) fields.get(finalCounter)).setName("s");
                            for (int i = 0; i < 81; i++) {
                                int blocks=1;
                                if (fields.get(i) instanceof JTextField){
                                    if (!GameLogic.recognizeTheRaw(i, finalCounter,Integer.valueOf(s))){
                                        ((JTextField) fields.get(i)).setBackground(new Color(255, 88, 88));
                                        if(((JTextField) fields.get(i)).getName()==null){
                                            blocks=1;
                                        }
//
                                        ((JTextField) fields.get(i)).setName(String.valueOf(blocks));
                                    }
                                    else if(!GameLogic.recognizeTheColumn(i, finalCounter,Integer.valueOf(s)))
                                    {
                                        ((JTextField) fields.get(i)).setBackground(new Color(255, 88, 88));
                                        if(((JTextField) fields.get(i)).getName()==null){
                                            blocks=1;
                                        }
//
                                        ((JTextField) fields.get(i)).setName(String.valueOf(blocks));
                                    }
                                    else if(!GameLogic.recognizeTheSquare(i, finalCounter,Integer.valueOf(s)))
                                    {
                                        ((JTextField) fields.get(i)).setBackground(new Color(255, 88, 88));
                                        if(((JTextField) fields.get(i)).getName()==null){
                                            blocks=1;
                                        }

                                        ((JTextField) fields.get(i)).setName(String.valueOf(blocks));
                                    }

                                }
                            }
                            boolean finish=true;
                            for (int i = 0; i < 81; i++) {
                                if (fields.get(i) instanceof JTextField) {
                                    String value=(((JTextField) fields.get(i)).getText());
                                    if(isNumeric(value)){
                                        if (!GameLogic.checkingTheCustomerSudoku(i,Integer.valueOf(value))){
                                            finish=false;}
                                    }else {
                                            finish=false;
                                        }

                                }
                            }
                                if (finish){
                                    fields.clear();
                                    removeAll();
                                    JLabel label=new JLabel("congratulations!!!");
                                    label.setBounds(50,0,400,70);
                                    label.setFont(new Font("ARIEL", Font.PLAIN, 32));
                                    add(label);
                                    grid=false;
                                    repaint();
                                    System.out.println("congratulations");
                                }

                        }
                    }
                    });

                }
                else if (fields.get(counter) instanceof JButton) {
                    ((JButton) fields.get(counter)).setBounds(((counter%9+1) * SquareSize) + lineGrid, (counter/9 + 1) * SquareSize + counter/27*8, SquareSize, SquareSize);
                    ((JButton) fields.get(counter)).setFont(new Font("Arial", Font.BOLD, 18));
                    ((JButton) fields.get(counter)).setText(String.valueOf(sudoku[counter]));
                    ((JButton) fields.get(counter)).setName(String.valueOf(counter));

                    ((JButton) fields.get(counter)).addMouseListener(new MouseAdapter(){
                    public void mouseEntered(MouseEvent evt){
                        for (int i = 0; i < 81; i++) {
                            if (fields.get(i) instanceof JTextField)
                                    if(!GameLogic.recognizeTheLCS(i, Integer.parseInt(((JButton)evt.getSource()).getName()))
                                    && (((JTextField) fields.get(i)).getName()==null
                                    || ((JTextField) fields.get(i)).getName()=="s")){
                                        ((JTextField) fields.get(i)).setBackground(new Color(176, 229, 245));}
                        }

                    }
                    public void mouseExited(MouseEvent evt){
                        for (int i = 0; i < 81; i++) {
                            if (fields.get(i) instanceof JTextField)
                                    if(!GameLogic.recognizeTheLCS(i,Integer.parseInt(((JButton)evt.getSource()).getName()))
                                    && (((JTextField) fields.get(i)).getName()==null
                                    || ((JTextField) fields.get(i)).getName()=="s")){
                                        ((JTextField) fields.get(i)).setBackground(Color.white);}

                        }
                    }});
                    add((JButton) fields.get(counter));
            }
        }
            setVisible(true);
            this.repaint();
    }

    public void paint(Graphics g){
        super.paint(g);
        if(grid) {
            g.drawRect(45, 45, SquareSize * 9 + 24, SquareSize * 9 + 24);
            g.drawLine(53 + SquareSize * 3, 45, 53 + SquareSize * 3, 45 + SquareSize * 9 + 24);
            g.drawLine(61 + SquareSize * 6, 45, 61 + SquareSize * 6, 45 + SquareSize * 9 + 24);
            g.drawLine(45, 54 + SquareSize * 3, 45 + SquareSize * 9 + 24, 54 + SquareSize * 3);
            g.drawLine(45, 62 + SquareSize * 6, 45 + SquareSize * 9 + 24, 62 + SquareSize * 6);
        }
        grid=true;
        setVisible(true);
    }
    public List<Object> getFields() {
        return fields;
    }

    public void setFields(List<Object> fields) {
        this.fields = fields;
    }
}
