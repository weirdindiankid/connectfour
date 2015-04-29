/* Title:  Player.java
 * Authors: Tyrone Hou <tyroneh@bu.edu>
 *          Dharmesh Tarapore <dharmesh@bu.edu> 
 * Date: 04/29/15
 * Purpose: This is a simple random player provided as part of the code distribution for HW 08 for CS 112, Fall 2014
 */

import java.util.*; 

public class Player {

    public int move(int[][] B) {
        return 1;
        /*
         int m = 0;
         for(int c = 0; c < 8; c++) {
         m = move(B, c, 1);
         if( m == 1 )
         return c;
         }
         
         return 5;
         */
    }
    
    private int eval(int[][] B, int player) {
        int other = 0;
        if( player == 1 ) {
            other = 10;
        } else {
            other = 1;
        }
        if(checkWin(B, player)) {
            return 1;
        } else if(checkWin(B, other)) {
            return -1;
        } else { // here the board is neither a win or a loss
            return 0;
        }
    }
    
    // Skeleton moveHelper method -- doesn't do 
    // anything too sensible just yet
    // 1 = human, 10 = machine
    private int moveHelper(int[][] B, int player, int depth) {
        
        // Base case
        if(depth == 1) {
            return 0; // eval(B);
        }
        // This keeps track of the high score
        int highScore = 0;
        for(int i = 0; i < B.length; i++) {
            for(int j = 0; j < i; j++) {
                int col = B[i][j];
                // If the column is full
                if(col != 0) {
                    continue;
                }
                // Make a move here
                int moveResult = moveHelper(B, player, --depth);
                if(moveResult > highScore) {
                    // Update high score
                    highScore = moveResult;
                }
                // Delete the move here
                return col;
                
            }
        }
        return 0; // Just to get it to compile
    }
    
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
    
}
