# Sudoku-Board-Game
 
A nice game for playing sudoku on your computer. You can choose the difficulty and the game will generate on the board a new sudoku with only one solution, of course.

When the mouse passes over the buttons, the relevant line, column and square are painted light blue, and if you have the same number in an illegal place, it is painted red.
# The algorithm 

Every time you create a new Sudoku, the program takes X (depending on the level) random numbers in X random places, in a way that no number placed in contradicts with the other numbers, and tries to solve it. At that stage, there are three options,

The best option is that we found just one solution; if so, we finished.

The second option is that there is no solution, for that option; we replace Randomly with one number and try to solve it again.

The third option is that there is more then one solution; in that case we stick to one of the solutions we have found, and carefully reduce numbers from it, each time making sure that the solution is the only one, until we've got exactly X numbers on the board with just one solution. We try the last step a maximum of 100 times, and if we didn't get our sudoku yet, we start from the beginning.



<img width="659" alt="SudokuScreen" src="https://user-images.githubusercontent.com/107894139/178349477-da19b7a0-512f-4985-af73-28926119daf0.png">
