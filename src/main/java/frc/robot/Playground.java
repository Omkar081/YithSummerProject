/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Add your docs here.
 */
public class Playground {
  public static void main(String[] args) {
 /*
    String s1 = "hi";
    String s2 = "hi";
    String s3 = new String("hi");
    String s4 = new String("hi");
    
   
    System.out.println("Does s1 = s2? ::::: " + (s1 == s2));
    System.out.println("Does s1 = s2? ::::: " + (s1.equals(s2)));

    System.out.println("Does s3 = s4? ::::: " + (s3 == s4));
    System.out.println("Does s3 = s4? ::::: " + (s3.equals(s4))); */


    /*ArrayList<String> basketballRoster = new ArrayList<>();

    basketballRoster.add("Zach");
    System.out.println(basketballRoster);

    basketballRoster.add("Pranav");
    basketballRoster.add("Andrew");
    System.out.println("New Roster ::::: " + basketballRoster);
    */
        //  Key,   Value
  TreeMap<String, Integer> scoreCard = new TreeMap<>(); //Player Name, Player's Score
    scoreCard.put("Pranav Vogeti", 1000);
    scoreCard.put("Zach Mastrosimone", 1001);
    scoreCard.put("Andrew Huang", 0);

    System.out.println(scoreCard); 

    System.out.println(scoreCard.get("Pranav Vogeti"));
    


    


  }   
}
