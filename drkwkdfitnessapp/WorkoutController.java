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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author danielking
 */
public class WorkoutController extends Switch implements Initializable {
    public Stage stage;
    private Reps rep;

    @FXML
    private ToggleButton weightType;
    @FXML 
    private Label label;
    @FXML 
    private Label label2;
    @FXML 
    private Label label3;
    @FXML 
    private Label label4;
    @FXML 
    private TextField weight;
    @FXML
    private TextField text1;
    @FXML
    private TextField text2;
    @FXML
    private TextField text3;
    @FXML
    private TextField text4;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rep = new Reps();
    }    

    void start(Stage stage) {
        this.stage = stage;
    }
    @FXML
    public void handleToggleOn() {
        if(weightType.isSelected()) {
            weightType.setText("Lbs");
            label.setText("Lbs");
            label2.setText("Lbs");
            label3.setText("Lbs");
            label4.setText("Lbs");
            
            
        } else {
            weightType.setText("Kg");
            label.setText("Kg");
            label2.setText("Kg");
            label3.setText("Kg");
            label4.setText("Kg");
        }   
    }
    @FXML 
    public void calculate1RM() {
        rep.setWeight(Integer.parseInt(weight.getText()));
        if(weightType.isSelected()) {
            text1.setText(String.valueOf((int)rep.findMaxlbs(rep.getWeight())));
            text2.setText(String.valueOf((int)(.7*(rep.findMaxlbs(rep.getWeight())))));
            text3.setText(String.valueOf((int)(.8*(rep.findMaxlbs(rep.getWeight())))));
            text4.setText(String.valueOf((int)(.95*(rep.findMaxlbs(rep.getWeight())))));
        } else {
            text1.setText(String.valueOf((int)rep.findMax(rep.getWeight())));
            text2.setText(String.valueOf((int)(.7*(rep.findMax(rep.getWeight())))));
            text3.setText(String.valueOf((int)(.8*(rep.findMax(rep.getWeight())))));
            text4.setText(String.valueOf((int)(.95*(rep.findMax(rep.getWeight())))));
        }
    }
    @FXML 
    public void returnToMenu() {
        Switch.switchTo("menu");
    }
    @FXML
    public void handleAbout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Max Weight Calculator");
        alert.setContentText("Designed by Daniel King");
        
        TextArea textArea = new TextArea("You can use this calculator to calculate what your one rep max is."
                + " All you have to do is enter a weight that you can do for 4-6 reps at max, and it will tell"
                + " you how much you should be able to pump out for one rep. It will also give you an idea of how"
                + " much 70%, 80%, and 90% of your one rep max is to give you ideas for your workout."
                + " You can switch between pounds and kilograms.");
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 0);
        alert.getDialogPane().setExpandableContent(expContent);
        
        alert.showAndWait();
    }
}
