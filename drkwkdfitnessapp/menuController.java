/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drkwkdfitnessapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Daniel King
 */
public class menuController extends Switch implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void goToProfileData(ActionEvent event) {
        Switch.switchTo("DataPage");
    }
    @FXML
    private void goToWorkout(ActionEvent event){
          Switch.switchTo("workout");

     }
    @FXML
    private void goToAbout(ActionEvent event){
        Switch.switchTo("Open");
    }
}