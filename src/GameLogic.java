import java.util.Random;

public class GameLogic {
    private static int solutions=0;
    private static boolean deadEndExit=false;
    private static int[] sudoku = new int[81];
    private static int[] customerSudoku = new int[81];
    private static int[] solved = new int[81];
    private  static int[] sudokuForCreation = new int[81];


    public static void solveSudoku(int n)
    {
        int i;
        if(n==81)
        {
            //setSolved(sudoku);
            //setSudoku(sudokuForCreation);
            solutions++;
            return;
        }
        else if(sudoku[n]!=0)
        {
            solveSudoku(n+1);
            if(solutions==2)
            {
                return;
            }
        }
        else
        {
            for(i=1;i<10;i++)
            {
                sudoku[n]=i;
                if(checkingSudoku(n)==true)
                {
                    solveSudoku(n+1);
                }
                if(solutions==2)
                {
                    return;
                }
            }
            sudoku[n]=0;
            return;
        }

    }
    public static boolean checkingSudoku(int n)
    {
        int i,x,x1,x2,y,z,z1,z2;

        // raw
        x1=(n/9)*9;
        x2=x1+9;
        for(x=x1;x<x2;x++)
        {
            if(sudoku[x] == sudoku[n] && n!=x)
            {
                return false;
            }
        }

        // column
        for(y=n%9;y<81;y=y+9)
        {
            if(sudoku[y] == sudoku[n] && n!=y)
            {
                return false;
            }
        }

        //Square
        z1 = ((n/27)*27)+(((n%9)/3)*3);
        z2 = z1+21;
        for(z=z1;z<z2;z=z+9)
        {
            for(i=0;i<3;i++)
            {
                if(sudoku[i+z] == sudoku[n] && n!=(i+z))
                {
                    return false;
                }
            }
        }
        //if it's all right
        return true;
    }
    public static boolean checkingSudokuToFindError(int n)
    {
        int i,x,x1,x2,y,z,z1,z2;

        // raw
        x1=(n/9)*9;
        x2=x1+9;
        for(x=x1;x<x2;x++)
        {
            for (int j = x+1; j < x2; j++) {
                if(customerSudoku[x] == customerSudoku[j] && customerSudoku[x]!=0)
                {
                    return false;
                }
            }
        }

        // column
        for(y=n%9;y<81;y=y+9)
        {
            for (int j = y; j < 81; j+=9) {
                if(customerSudoku[y] == customerSudoku[j] && customerSudoku[y]!=0 && y!=j)
                {
                    return false;
                }
            }
        }

        //Square
        z1 = ((n/27)*27)+(((n%9)/3)*3);
        z2 = z1+21;
        for(z=z1;z<z2;z=z+9)
        {
            for(i=0;i<3;i++)
            {
                for (int j = z; j < z2; j+=9) {
                    for (int k = i; k < 3; k++) {
                        if(customerSudoku[i+z] == customerSudoku[j+k] && (i+z)!=(j+k) && customerSudoku[i+z]!=0)
                        {
                            return false;
                        }
                    }
                }
            }
        }
        //if it's all right
        return true;
    }

    public static boolean recognizeTheRaw(int n,int chosenPlace,int value){
        int x,x1,x2;
        // raw
        x1=(n/9)*9;
        x2=x1+9;
        boolean theCorrectLine=false;
        for(x=x1;x<x2;x++){
            if (x==chosenPlace){
                theCorrectLine=true;}
        }
        if (!theCorrectLine)
            return true;
        for(x=x1;x<x2;x++)
        {
            if(customerSudoku[x]==value && chosenPlace!=x)
            {
                return false;
            }
        }

        return true;
    }

    public static boolean recognizeTheColumn(int n,int chosenPlace,int value){
        // column
        boolean theCorrectLine=false;
        for(int y=n%9;y<81;y=y+9){
            if (y==chosenPlace){
                theCorrectLine=true;}
        }
        if (!theCorrectLine)
            return true;
        for(int y=n%9;y<81;y=y+9)
        {
            if(customerSudoku[y]==value && chosenPlace!=y)
            {
                return false;
            }
        }
        return true;
    }

    public static boolean recognizeTheSquare(int n,int chosenPlace,int value){
        //Square
        boolean theCorrectSquare=false;
        int z1 = ((n/27)*27)+(((n%9)/3)*3);
        int z2 = z1+21;

        for(int z=z1;z<z2;z=z+9)
        {
            for(int i=0;i<3;i++) {
                if (z+i == chosenPlace) {
                    theCorrectSquare = true;
                }
            }
        }
        if (!theCorrectSquare)
            return true;

        for(int z=z1;z<z2;z=z+9)
        {
            for(int i=0;i<3;i++)
            {
                if(customerSudoku[i+z]==value && chosenPlace!=(i+z))
                {
                    return false;
                }
            }
        }
        //if it's all right
        return true;
    }

    public static boolean recognizeTheLCS(int n,int chosenPlace){
        int i,x,x1,x2,y,z,z1,z2;
        // raw
        x1=(n/9)*9;
        x2=x1+9;
        for(x=x1;x<x2;x++)
        {
            if(x==chosenPlace)
            {
                return false;
            }
        }

        // column
        for(y=n%9;y<81;y=y+9)
        {
            if(y == chosenPlace)
            {
                return false;
            }
        }

        //Square
        z1 = ((n/27)*27)+(((n%9)/3)*3);
        z2 = z1+21;
        for(z=z1;z<z2;z=z+9)
        {
            for(i=0;i<3;i++)
            {
                if((i+z) == chosenPlace)
                {
                    return false;
                }
            }
        }
        //if it's all right
        return true;
    }
    public static boolean checkingTheCustomerSudoku(int index,int value)
    {
       if(solved[index]!=value)
           return false;
        return true;
    }

    public static int[] createSudoku( int givenNum) {
        Random rand = new Random();
        int randomIndex, randomValue, i, eraseNum, givenNumCounter = 0, lastOne;
        int deadlock = 0;
        int[] sudoku1 = new int[81]; //i need to duplicate the arr sudoku in order not to change him

        for (i = 0; i < 81; i++) {
            sudoku[i] = 0;
        }
        System.out.println("help");
        solutions=0;
        setDeadEndExit();
        while ((solutions != 1 && !getDeadEndExit())){
            solutions = 0;
            //adding a random number in a random place
            randomIndex = rand.nextInt(81);
            randomValue = rand.nextInt(9) + 1;
            if (sudoku[randomIndex] == 0) {
                sudoku[randomIndex] = randomValue;
                sudoku1[randomIndex] = randomValue;
                givenNumCounter++;
                //if the random number that had been added is in wrong place, a new number is placing him
                while (checkingSudoku(randomIndex) == false) {
                    sudoku[randomIndex] = 0;
                    sudoku1[randomIndex] = 0;
                    givenNumCounter--;
                    randomValue = rand.nextInt(9) + 1;
                    randomIndex = rand.nextInt(81);
                    sudoku[randomIndex] = randomValue;
                    sudoku1[randomIndex] = randomValue;
                    givenNumCounter++;
                }
            }
            //just if enough numbers had been added
            if (givenNumCounter > givenNum) {
                solveSudoku(0);
                //System.out.println(solutions+"h");

                if (solutions == 1) {
                    //i 'clean' sudoku[] by paste him to sudoku1[]
                    for (i = 0; i < 81; i++) {
                        sudoku[i] = sudoku1[i];
                    }
                    if (givenNumCounter > givenNum && givenNumCounter < (givenNum+4)) {
                        System.out.println("there is just " + solutions + " solution!");
                        System.out.println("there are " + givenNumCounter + " numbers in this Sudoku!");
                        setCustomerSudoku(sudoku);
                        return getSudoku();
                    }
                    for (i = 0; i < 81; i++) {
                        sudoku1[i] = 0;
                        sudoku[i] = 0;
                    }
                } else if (solutions == 0) {
                    //i 'clean' sudoku[] by paste him to sudoku1[]
                    for (i = 0; i < 81; i++) {
                        sudoku[i] = sudoku1[i];
                    }
                    if (givenNumCounter == givenNum) {
                        while (sudoku[randomIndex] != 0) {
                            sudoku[randomIndex] = 0;
                            sudoku1[randomIndex] = 0;
                            givenNumCounter--;
                        }
                    }

                } else if (solutions == 2) {
                    givenNumCounter = 81;
                    for (i = 0; i < 81; i++) {
                        sudoku1[i] = sudoku[i];
                    }
                    //Erase from the solved Sudoku until it has just the Given number and one solution
                    for (eraseNum = 0; eraseNum < (81 - (givenNum)); eraseNum++) {
                        solutions = 0;
                        randomIndex = rand.nextInt(81);
                        while (sudoku1[randomIndex] == 0) {
                            randomIndex = rand.nextInt(81);
                        }
                        lastOne = sudoku1[randomIndex];
                        sudoku1[randomIndex] = 0;
                        sudoku[randomIndex] = 0;
                        givenNumCounter--;

                        solveSudoku(0);
                        deadlock++;
                        //System.out.println(solutions+"r");
                        //i 'clean' sudoku[] by paste him to sudoku1[]
                        for (i = 0; i < 81; i++) {
                            sudoku[i] = sudoku1[i];
                        }
                        if (solutions != 1) {
                            sudoku[randomIndex] = lastOne;
                            sudoku1[randomIndex] = lastOne;
                            eraseNum--;
                            givenNumCounter++;
                        } else if (givenNumCounter > givenNum && givenNumCounter < (givenNum+4)) {
                            //i 'clean' sudoku[] by paste him to sudoku1[]
                            for (i = 0; i < 81; i++) {
                                sudoku[i] = sudoku1[i];
                            }
                            System.out.println("there is just " + solutions + " solution!");
                            System.out.println("there are " + givenNumCounter + " numbers in this Sudoku!");
                            System.out.println(deadlock+"r");
                            setCustomerSudoku(sudoku);
                            return getSudoku();
                        }
                        if (deadlock >= 100) {
                            eraseNum = 81;
                            deadEndExit=true;
                            for (i = 0; i < 81; i++) {
                                //sudoku1[i] = 0;
                                sudoku[i] = 0;
                            }
                            System.out.println("hold on a sec... it will come");
                            givenNumCounter = 0;
                            deadlock = 0;
                        }
                    }
                    System.out.println("fini");
                }
            }
        }
        deadEndExit=false;
        sudoku[0]=10;
        return getSudoku();
    }
    public static boolean getDeadEndExit() {
        return deadEndExit;
    }

    private static void setDeadEndExit() {
        deadEndExit=false;
    }

    public static void Solutions(int solutions) {
        GameLogic.solutions = solutions;
    }

    public static void setSudoku(int[] input) {
        for (int i = 0; i < 81; i++) {
            sudoku[i]=input[i];
        }
    }
    public static void ZeroPointInSudoku(int index) {
        sudoku[index]=0;
    }

    public static void setCustomerSudoku(int[] input) {
        for (int i = 0; i < 81; i++) {
            customerSudoku[i]=input[i];
        }
    }
    public static void setCustomer(int index,int value){
        customerSudoku[index]=value;
    }

    public static void setSolved(int[] input) {
        for (int i = 0; i < 81; i++) {
            solved[i]=input[i];
        }
    }

    public static int getSolutions() {
        return solutions;
    }

    public static int[] getSudoku() {
        return sudoku;
    }


    public static int[] getSudokuForCreation() {
        return sudokuForCreation;
    }

    public static int[] getSolved(int[] sudokuB) {
        setCustomerSudoku(sudokuB);
        solveSudoku(0);
        setSolved(sudoku);
        setSudoku(customerSudoku);
        return solved;
    }
}
