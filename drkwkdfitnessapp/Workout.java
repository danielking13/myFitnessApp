/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drkwkdfitnessapp;

/**
 *
 * @author danielking
 */
public abstract class Workout {
  
   public abstract void setWeight(double weight);
   public abstract double getWeight();
   public abstract double findMax(double weight);
}
