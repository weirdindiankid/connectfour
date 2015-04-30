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
    
    // Max method for array score
    private int max(int [] a) {
        int maximum = 0;
        int maximum_index = 0;
        for(int i = 0; i < a.length; i++) {
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

        // For each column, get score
        for(int i = 0; i < 8; i++) {
            scores[i] = getScore(B, player, depth, i);
        }
        // Get max score
        int maxScore = max(scores);
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
    
}
