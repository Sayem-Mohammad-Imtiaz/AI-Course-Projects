Project Langauge: Java

This project has been executed with following system spec:
JDK: version 8
Operating System: Windows 10 64-bit

1. To compile the project, open command line inside src folder and run following commands:
	javac Driver.java

2. To run, type following command:
	java Driver

3. After running the Driver, you will be propmted following:
Provide your in-game name:
[Enter your name]
Who will make first move? [0=Computer, 1=You]
[Enter choice]
Enter search depth:
[Enter depth]

4. If you choose to turn first, you will be asinged white pieces. White always begins. Board layout will be like this:
Turn No# 0

---Current Board State---
----------------
|w|-|w|-|w|-|w|-|
----------------
|-|w|-|w|-|w|-|w|
----------------
|w|-|w|-|w|-|w|-|
----------------
|-| |-| |-| |-| |
----------------
| |-| |-| |-| |-|
----------------
|-|b|-|b|-|b|-|b|
----------------
|b|-|b|-|b|-|b|-|
----------------
|-|b|-|b|-|b|-|b|
----------------

Here, turn no. indicates which turn it is. 'w' stands for white gamepice, 'b' stands for black gamepice, '-' stands for illegal cell where gamepieces 
cannot move, and empty cells represent available for move cell.

5. When your turn comes, game will show following information:
[User], it's your turn...
All available moves: (Please enter a move ID to choose a move or enter -1 to quit game)
Move 1: (2,6) -> (3,7)
Move 2: (2,8) -> (3,7)
Move 3: (3,1) -> (4,2)
Move 4: (3,3) -> (4,4)
Move 5: (3,3) -> (4,2)
Move 6: (3,5) -> (4,4)
Move 7: (4,6) -> (5,5)
Move 8: (4,6) -> (6,8)
[Enter move ID]

It will show all moves you can make at this moment. For convenience of the user, jump moves will be shown in green color.
you need to enter move ID (1 to 8 for above case). Enter -1 to quit game.

6. After game finishes, it will show result in following format:

Total turn needed to end the game: 3 
Computer took : 16.0ms on average
Sorry Sayem, you have lost