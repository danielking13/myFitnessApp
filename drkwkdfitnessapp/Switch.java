/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drkwkdfitnessapp;

import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *
 * @author danielking
 */
public abstract class Switch {
    public Parent root;
    public static final HashMap<String, Switch> controllers = new HashMap<>();
    public static Scene scene;
    
    public void setRoot(Parent root) {
        this.root = root;
    }
    public Parent getRoot() {
        return root;
    }
    public static Switch getControllerByName(String name) {
        return controllers.get(name);
    }
    public static Switch add(String name){
        Switch controller;
        controller = controllers.get(name);
        
        if(controller == null) {
          
            try {
                FXMLLoader loader = new FXMLLoader(Switch.class.getResource(name + ".fxml"));
                Parent root = (Parent)loader.load();
                controller = (Switch) loader.getController();
                controller.setRoot(root);
                controllers.put(name, controller);
            } catch(IOException ioex) {
                System.out.println("Error loading " + name + ".fxml \n" + ioex);
                controller = null;
            } catch(Exception ex) {
                System.out.println("Error loading " + name + ".fxml \n" + ex);
                controller = null;
            }
        }
        return controller;
    }
    public static void switchTo(String name) {
        Switch controller = controllers.get(name);
        //search through hashmap, saves controller if necessary
        if(controller == null) {
            controller = add(name);
        }
        if(controller != null){
            if(scene != null){
                //get the fxml file
                scene.setRoot(controller.getRoot());
            }
        }
    }
}
