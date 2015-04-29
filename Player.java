/* Title:  Player.java
 * Authors: Tyrone Hou <tyroneh@bu.edu>
 *          Dharmesh Tarapore <dharmesh@bu.edu> 
 * Date: 11/29/13
 * Purpose: This is a simple random player provided as part of the code distribution for HW 08 for CS 112, Fall 2014
 */

import java.util.*; 

public class Player {

 private Random R = new Random(); 
 
 public int move(int[][] B) {
   int m = 0; 
   do {
     m = R.nextInt(8);
   } while(B[0][m] != 0);
   
   return m; 
 }
  
  
}

