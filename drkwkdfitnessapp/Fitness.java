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
public interface Fitness {
    Double calculateBMI(Double weight, Integer height);
    Double getWeight();
    void setWeight(Double weight);
    Integer getHeight();
    void setHeight(Integer height);
}
