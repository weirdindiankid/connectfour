/* Title:  Player.java
 * Author: Wayne Snyder (waysnyder@gmail.com)
 * Date: 11/29/13
 * Purpose: This is a simple random player provided as part of the code distribution for HW 08 for CS 112, Fall 2014
 */

import java.util.*; 

public class Player {
    
    private Random R = new Random(); 
    
    // Constants
    private final int Human = 1;
    private final int Machine = 10;
    private final int Blank = 0;
    
    // We will use this to represent Infinity
    private final int Inf = 1000000;
    
    // This will be the depth our program will look up to
    private final int D = 8;
    
    // Method borrowed from Connect4.java, code by Professor Snyder
    // Check to see if the game is a win for the given player
    
    private static boolean isWinFor(int[][] B, int player) {    // 1 = player, 10 = machine
        
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
    
    // This method checks if the given node is a leaf node
    // Code borrowed from the TicTacToe file on the CS112 website
    private boolean isLeaf(int[][] B) {
        
        if(isWinFor(B, Human) || isWinFor(B, Machine)) {
            return true;
        }
        for(int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++) {
                if(B[row][col] == Blank)
                    return false;
            }
        }
        return true; // just to get it to compile
    }
    
    // The get scores methods are very poorly written -- clean them up perhaps?
    // X = 1, O = 10
    private int getScores(int a, int b, int c, int d, int e, int f, int g, int h) {
        
        int n = a + b + c + d + e + f + g + h;
        int scoreMachine = n / 10;
        int scoreHuman = n % 10;
        if(scoreMachine > 0 && scoreHuman > 0)
            return 0; // No moves left
        else if(scoreHuman == 0)
            return scoreMachine;
        else if(scoreMachine == 0)
            return -scoreHuman;
        return 0; // needed to compile
    }
    
    private int getScoresDiagonally(int a, int b, int c, int d) {
    
        int n = a + b + c + d;
        int scoreMachine = n / 10;
        int scoreHuman = n % 10;
        if(scoreMachine > 0 && scoreHuman > 0)
            return 0; // No moves left
        else if(scoreHuman == 0)
            return scoreMachine;
        else if(scoreMachine == 0)
            return -scoreHuman;
        return 0; // needed to compile
        
    }
    // The magic method
    private int eval(int[][] B) {
        
        if(isWinFor(B, Human))
            return -Inf;
        else if(isWinFor(B, Machine))
            return Inf;
        
        int sum = 0;
        
        // Count rows
        for(int r = 0; r < 8; r++)
            sum += getScores(B[r][0], B[r][1], B[r][2], B[r][3], B[r][4], B[r][5], B[r][6], B[r][7]);
        
        // Count columns
        for(int c = 0; c < 8; c++)
            sum += getScores(B[0][c], B[1][c], B[2][c], B[3][c], B[4][c], B[5][c], B[6][c], B[7][c]);
        
        
        // Count diagonals
        // check all lower-left to upper-right diagonals        
        for(int i = 3; i < 8; ++i) {
            for(int j = 0; j < 5; ++j) {
                sum += getScoresDiagonally(B[i][j], B[i-1][j+1], B[i-2][j+2], B[i-3][j+3]);
            }
        }
        
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                sum += getScoresDiagonally(B[i][j], B[i+1][j+1], B[i+2][j+2], B[i+3][j+3]);
            }
        }
        
        //sum += getScoresDiagonally(B[0][0], B[1][1], B[2][2], B[3][3]);
        //sum += getScoresDiagonally(B[3][0], B[2]);
        return sum;
    }
    
    private int column(int move) {
        return move % 8;
    }
    
    private int row(int move) {
        return move / 8;    
    }
    
    // This move helper method will do all the heavy lifting
    private int minMax(int[][] B, int depth, int alpha, int beta) {
        //System.out.println("Minmax called");
        if(isLeaf(B) || depth == D)
            return eval(B);
        else if(depth % 2 == 0) {
             int val = -Inf;
             
             for(int c = 0; c < 8; c++) {
                 int row = 0;
                 if(B[row][c] != Blank)
                     continue; 
                 while(row < 8 && B[row][c] == 0) {
                     ++row;
                 } 
                 --row;
                 B[row][c] = Machine;
                 alpha = Math.max(alpha, val);
                 if(beta < alpha) {
                     B[row][c] = Blank;
                     break;
                 }
                 val = Math.max(val, minMax(B, depth + 1, alpha, beta));
                 B[row][c] = Blank;
                 
             }
             
             return val;
             
        } else {
            
            int val = Inf;
            
            
            for(int c =0; c < 8; c++) {
                int row = 0;
                if(B[row][c] != Blank)
                    continue;
                while(row < 8 && B[row][c] == 0) {
                    ++row;
                    //System.out.println("Row is: " + row);
                } 
                --row;
                 B[row][c] = Human;
                 beta = Math.min(beta, val);
                 if(beta < alpha) {
                     B[row][c] = Blank;
                     break;
                 }
                 val = Math.min( val, minMax(B, depth + 1, alpha, beta)); 
                 B[row][c] = Blank;       // undo the move and try next move    
                 
            }
            
            return val; 
        }
    }
        
    
    public int move(int[][] B) {
        
        int max = -Inf;
        int bestMove = -1;
        for(int c = 0; c < 8; c++) {
            
            int row = 0;
            if(B[row][c] != Blank) {
                continue;
            }
            while(row < 8 && B[row][c] == 0) {
                ++row;
            }
            --row;
            //System.out.println("Now on: B[" + r + "][" + c + "]");
                 
            B[row][c] = Machine;
            int val = minMax(B, 1, -Inf, Inf);
            System.out.println("Val is: " + val);
            if(val > max) {
                bestMove = c;  
                max = val;
            }
            
            B[row][c] = Blank; // undo move
            
        }
        
        return bestMove;
    }  
        /*
        int m = 0; 
        do {
            m = R.nextInt(8);
        } while(B[0][m] != 0);
        
        return m; 
    }
    */
    
}

