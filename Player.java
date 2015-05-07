/* Title:  Player.java
 * Authors: Tyrone Hou <tyroneh@bu.edu>
 *          Dharmesh Tarapore <dharmesh@bu.edu> 
 * Date: 04/29/15
 * Purpose: This is a simple Connect4 AI
 */

import java.util.*; 

public class Player {
    final int HUMAN = 1, MACHINE = 10;
    
    // Scores array
    int [] scores = new int[8];
    
    public int move(int[][] B) {
        //System.out.println("\t\t\t\t\t\tmove");
        return moveHelper(B, MACHINE, 5);
    }
    
    private static boolean colFull(int[][] B, int col) {
        return B[0][col] != 0;
    }
    
    private static boolean rowFull(int[][] B, int row) {
        return B[row][0] != 0;   
    }
    
    private static boolean rowEmpty(int[][] B, int row) { return !rowFull(B, row); }
    
    private static boolean colEmpty(int[][] B, int col) {
        return B[7][col] == 0;
    }
    
    private static void addChecker(int[][] B, int col, int player) {
        int row = 0;
        while(row < 8 && B[row][col] == 0) {
            ++row;
        }
        --row;
        
        B[row][col] = player;
        //System.out.println("add B[" + row + "][" + col + "] = " + B[row][col]);
    }
    
    private static void removeChecker(int[][] B, int col) {
        int row = 7;
        while(row >= 0 && B[row][col] != 0) {
            --row;
        }
        ++row;
        
        B[row][col] = 0;
        //System.out.println("remove B[" + row + "][" + col + "] = " + B[row][col]);
    }
    
    private int[] getScores(int[][] B, int player, int depth) {
        int other;
        if( player == HUMAN ) {
            //System.out.println("HUMAN");
            other = MACHINE;
        }
        else {
            //System.out.println("MACHINE");
            other = HUMAN;
        }
        

        int[] colScore = new int[8];
        int[] rowScore = new int[8];
        
        for(int i = 0; i < 8; i++) {
            //System.out.println(i);
            if( colFull(B, i) ) {
                colScore[i] = -1;
            } else if(rowFull(B, i)) { }
            else if(checkWin(B, MACHINE)) {
                //System.out.println("Machine win");
                colScore[i] = 100;
            } else if(checkWin(B, HUMAN)) {
                //System.out.println("Human win");
                colScore[i] = -100;
            } else if(depth == 0) {
                //System.out.println("Depth zero");
                colScore[i] = 0;
            } else if (player == MACHINE) {
                //System.out.println("Machine turn");
                addChecker(B, i, player);
                int[] humanScores = getScores(B, other, depth - 1);
                //System.out.println(depth);
                //System.out.print("humanScores = ");
                //printArray(humanScores);
                colScore[i] = min( humanScores );
                removeChecker(B, i);
            } else { // player == HUMAN
                //System.out.println("User turn");
                addChecker(B, i, player);
                int [] machineScores = getScores(B, other, depth - 1);
                //System.out.println(depth);
                //System.out.print("machineScores = ");
                //printArray(machineScores);
                colScore[i] = max( machineScores );
                removeChecker(B, i);
            }
            
        }

        return colScore;
    }
    
    private int min(int [] a) {
        int minimum = a[0];
        for(int i = 1; i < a.length; i++) {
            if(a[i] < minimum && a[i] != -1) {
                minimum = a[i];
            }
        }
        return minimum;
    }
    
    // Max method for array score
    private int max(int [] a) {
        int maximum = a[0];
        int maximum_index = 0;
        for(int i = 1; i < a.length; i++) {
            if(a[i] > maximum && a[i] != -1) {
                maximum = a[i];
                maximum_index = i;
            }
        }
        return maximum;
    }
    
    private int maxIndex(int [] a) {
        int maximum = a[0];
        int maximum_index = 0;
        for(int i = 1; i < a.length; i++) {
            if(a[i] > maximum) {
                maximum = a[i];
                maximum_index = i;
            }
        }
        return maximum_index;
    }
    
    // Skeleton moveHelper method -- doesn't do 
    // anything too sensible just yet
    // 1 = human, 10 = machine
    private int moveHelper(int[][] B, int player, int depth) {
        scores = getScores(B, player, depth);
        //printArray(scores);
        int maxScore = maxIndex(scores);
        return maxScore;
        
    }
    
    // check for win by player, 4 in a sequence
    private static boolean checkWin(int[][] B, int player) {    // 1 = player, 10 = machine
        
        // check all horizontal rows
        for(int i = 0; i < 8; ++i)
            for(int j = 0; j < 5; ++j) {
            if(B[i][j] == player && B[i][j+1] == player && B[i][j+2] == player && B[i][j+3] == player) {
                return true;
            }
        }
        
        
        // check all vertical columns
        for(int i = 0; i < 5; ++i)
            for(int j = 0; j < 8; ++j) {
            if(B[i][j] == player && B[i+1][j] == player && B[i+2][j] == player && B[i+3][j] == player) {
                return true;
            }
        } 
        
        
        // check all lower-left to upper-right diagonals
        for(int i = 3; i < 8; ++i)
            for(int j = 0; j < 5; ++j) {
            
            if(B[i][j] == player && B[i-1][j+1] == player && B[i-2][j+2] == player && B[i-3][j+3] == player) {
                return true;
            }  
        }   
        
        
        // check all upper-left to lower-right diagonals
        for(int i = 0; i < 5; ++i)
            for(int j = 0; j < 5; ++j) {
            if(B[i][j] == player && B[i+1][j+1] == player && B[i+2][j+2] == player && B[i+3][j+3] == player) {
                return true;
            }
        }
        
        return false;  
    }
    
    private void printArray(int[] A) {
       System.out.print("[");
       for(int i = 0; i < A.length; i++) {
           System.out.print(A[i] + " ");
       }
       System.out.println("]");
    }
    public static void main(String[] args) {
        Player P = new Player();
        int[] T = {1, 2, -1, 4};
        
        System.out.println(P.min(T));
    }
}
