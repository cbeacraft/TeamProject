/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;
import java.util.*;
//import java.sql.*;
/**
 *
 * @author Brooke
 */
public class TASLogic {
    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift){
         int clockedMins = 0;
            if (dailypunchlist.size() < 2){
        return 0;
    }
            
    for (int i = 0; i < dailypunchlist.size(); i = i + 2){
        Punch clockIn = (Punch) dailypunchlist.get(i);
        Punch clockOut = (Punch) dailypunchlist.get(i+1);
    System.err.println("I: " + i);
    System.err.println("Clock In Punch: " + clockIn.printAdjustedTimestamp());
    System.err.println("Clock Out Punch: " + clockOut.printAdjustedTimestamp());
    System.err.println("Daily Punch List: " + dailypunchlist.size());
           
    

    if (clockIn.getPunchtypeid()!=2 && clockOut.getPunchtypeid()!=2){
       
        clockedMins = ((int)clockOut.getAdjustedtimestamp() - (int)clockIn.getAdjustedtimestamp())/60000;
                
    }
    
    if ((clockedMins > shift.getLunchdeduct()) && ((clockIn.getTookLunch()) == true)){
        int LunchMins = clockedMins - shift.getLunchdeduct();
        return LunchMins;
    }
 } 
    return clockedMins; 
   
}
}