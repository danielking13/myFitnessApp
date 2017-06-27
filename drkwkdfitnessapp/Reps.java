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
public class Reps extends Workout {
    private double weight;
    private double max;
    
    @Override
    public void setWeight(double weight) {
       this.weight = weight;
   }
    @Override
   public double getWeight() {
       return weight;
   }
   
    @Override
   public double findMax(double weight) {
       max = (weight*1.1307) + .6998;
       return max;
   }
   public double findMaxlbs(double weight) {
       max = weight/(1.02780 - (.0278*5));
       return max;
   }
   
}
