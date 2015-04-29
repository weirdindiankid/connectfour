/* Title:  Player.java
 * Authors: Tyrone Hou <tyroneh@bu.edu>
 *          Dharmesh Tarapore <dharmesh@bu.edu> 
 * Date: 04/29/15
 * Purpose: This is a simple random player provided as part of the code distribution for HW 08 for CS 112, Fall 2014
 */

import java.util.*; 

public class Player {
    
    private Random R = new Random(); 
    
    public int move(int[][] B) {
        int m = 0;
        for(int c = 0; c < 8; c++) {
            m = move(B, c ,1);
            if( m == 1 )
                return m;
        }
        
        return 5;
    }
    
    private int move(int[][] B, int column, int player) {
        
    }

    // check for win by player, 4 in a sequence
    private static boolean checkWin(int player) {    // 1 = player, 10 = machine
        
        // check all horizontal rows
        for(int i = 0; i < 8; ++i)
            for(int j = 0; j < 5; ++j) {
            if(slot[i][j] == player && slot[i][j+1] == player && slot[i][j+2] == player && slot[i][j+3] == player) {
                return true;
            }
        }
        
        // check all vertical columns
        for(int i = 0; i < 5; ++i)
            for(int j = 0; j < 8; ++j) {
            if(slot[i][j] == player && slot[i+1][j] == player && slot[i+2][j] == player && slot[i+3][j] == player) {
                return true;
            }
        } 
        
        
        // check all lower-left to upper-right diagonals
        for(int i = 3; i < 8; ++i)
            for(int j = 0; j < 5; ++j) {
            
            if(slot[i][j] == player && slot[i-1][j+1] == player && slot[i-2][j+2] == player && slot[i-3][j+3] == player) {
                return true;
            }  
        }   
        
        // check all upper-left to lower-right diagonals
        for(int i = 0; i < 5; ++i)
            for(int j = 0; j < 5; ++j) {
            if(slot[i][j] == player && slot[i+1][j+1] == player && slot[i+2][j+2] == player && slot[i+3][j+3] == player) {
                return true;
            }
        }
        
        // no wins found
        return false;  
    }