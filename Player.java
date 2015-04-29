/* Title:  Player.java
 * Authors: Tyrone Hou <tyroneh@bu.edu>
 *          Dharmesh Tarapore <dharmesh@bu.edu> 
 * Date: 04/29/15
 * Purpose: This is a simple random player provided as part of the code distribution for HW 08 for CS 112, Fall 2014
 */

import java.util.*; 

public class Player {
    final int HUMAN = 1, MACHINE = 10;
    
    public int move(int[][] B) {
         return moveHelper(B, 10, 5);
    }
    
    private int eval(int[][] B, int player) {
        int other = 0;
        if( player == HUMAN ) {
            other = MACHINE;
        }
        else {
            other = HUMAN;
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
        if(depth == 0) {
            return eval(B, player);
        }
        // This keeps track of the high score
        int highScore = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                int col = B[i][j];
                // If the column is full
                if(col != 0) {
                    continue;
                }
                
                // Make a move here
                B[i][j] = player;
                
                int other_player = 0;
                other_player = (player == HUMAN) ? MACHINE : HUMAN;
                
                int moveResult = moveHelper(B, other_player, --depth);
                
                if(moveResult > highScore) {
                    // Update high score
                    highScore = moveResult;
                }
                
                // Delete the move here
                B[i][j] = 0;
                
                return col;
            }
        }
        
        return 0; // Return shit (think ascii and mod...)
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
    
}
