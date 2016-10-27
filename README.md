# Life-Simulator  ( Just for the final project of our homework )
The simulation of our lifes ! Provides several features to record basic things of life on the APP.

Features :

1. Memo 

2. CheckList

3. StudyPlanner

4. AutoPlanner

5. Schedule

6. Line Simulator

Development Regulation :

1. The "id" of every View inside each Activity should be named as "View discription + Activity Key Word" 
e.g. The button for closing the App in MainActivity should be named : "@+id/closeBTN_Main" !

2. We should declare the constant whenever we need a value to distinguish what to do !
e.g. Declaration at first !
private final static String MEMO_ID = "Memo_id";  // the primary key of each Memo
Then hand in it !
intent.putExtra(MEMO_ID,memo_id);

