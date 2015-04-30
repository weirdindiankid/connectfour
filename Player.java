/* Title:  Player.java
 * Authors: Tyrone Hou <tyroneh@bu.edu>
 *          Dharmesh Tarapore <dharmesh@bu.edu> 
 * Date: 04/29/15
 * Purpose: This is a simple Connect4 AI
 */

import java.util.*; 

public class Player {
    final int HUMAN = 1, MACHINE = 10;
    
    public int move(int[][] B) {
        return moveHelper(B, MACHINE, 5);
    }

    private int getScore(int[][] B, int player, int depth, int col) {
        int other;
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
        } else if(depth == 0) {
            return 0;
        } else {
            int row = 0;
            while(row < 8 && B[row][col] == 0) {
                ++row;
            }
            --row;
            
            B[row][col] = player;
            for(int i = 0; i < 8; i++) {
                int score = getScore(B, other, depth - 1, i);
            }
            B[row][col] = 0;
            return score;
        }
    }
    
    // Skeleton moveHelper method -- doesn't do 
    // anything too sensible just yet
    // 1 = human, 10 = machine
    private int moveHelper(int[][] B, int player, int depth) {
        
        // Base cases
        if(depth == 0) {
            return eval(B, player);
        }
        // This keeps track of the high score
        for(int j = 0; j < 8; j++) {
            
            // If the column is full
            if(B[0][j] != 0) {
                System.out.println("Column full, B[0][j] = " + B[0][j]);
                continue;
            }
            
            // Make a move here
            int r = 0;
            while(r < 8 && B[r][j] == 0) {
                ++r;
            }
            
            --r;
            
            B[r][j] = player;
            
            int other_player = 0;
            other_player = (player == HUMAN) ? MACHINE : HUMAN;
            
            int moveResult = moveHelper(B, other_player, depth - 1);
            
            // Delete the move here
            B[r][j] = 0;
            
            if(moveResult == -1 || moveResult == 1) {
                return j;
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
