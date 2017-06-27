/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drkwkdfitnessapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author danielking
 */
public class DataController extends Switch implements Initializable {
    
    public Stage stage;
    private static DecimalFormat df2 = new DecimalFormat(".##");
 
    private PersonData person;
    
    @FXML
    private TextField nameText;
    @FXML
    private TextField weightText;
    @FXML
    private TextField heightText;
    @FXML
    private TextField ageText;
    @FXML
    private TextArea resultBox;
    @FXML
    private TextField waistSizeText;
    @FXML
    private TextField heartRateText;
    @FXML
    private Label feedback;
    @FXML
    private ComboBox gender;
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        person = new PersonData();
        gender.getItems().add("Male");
        gender.getItems().add("Female");
    }

    void start(Stage stage) {
        this.stage = stage;
  
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override 
            public void handle(WindowEvent we) {
                PersonData formPerson = createPersonFromData();
                if(person != null) {
                    if(!person.equals(formPerson)){
                        if(!confirmContinueOnUnsavedData()) {
                            we.consume();// this stops the closing from happening 
                        }
                    }
                }
            }
        });
    }
    
    @FXML
    private void getBMI(ActionEvent event) {
        
        person.setName(nameText.getText());
        person.setWeight(Double.parseDouble(weightText.getText()));
        person.setHeight(Integer.parseInt(heightText.getText()));
        double BMI = person.calculateBMI(person.getWeight(), person.getHeight());
        resultBox.setText("Hi, " + person.getName() + ", your BMI is " + df2.format(BMI));
        if(BMI < 18.5 ){
            resultBox.appendText("\nYour BMI category is Underweight");
        } else if(BMI >=18.5 && BMI < 24.9) {
            resultBox.appendText("\nYour BMI category is Normal");
        } else if(BMI >= 25 && BMI < 29.9) {
            resultBox.appendText("\nYour BMI category is Overweight");
        } else {
            resultBox.appendText("\nYour BMI category is Obese");
        }
    }
    
    @FXML
    private void saveButton(ActionEvent event) {
        
        person = createValidatedPersonFromData();
        if (person == null) {
            return;
        }
        
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                String jsonString = person.toJsonString();
                PrintWriter out = new PrintWriter(file.getPath());
                out.print(jsonString);
                out.close();
            } catch(IOException ioex) {
                String message = "Exception occurred while saving to " + file.getPath();
                displayExceptionAlert(message, ioex);
            }
        }   
        feedback.setText("Your data has been saved");
    }
    @FXML
    public void handleOpen(ActionEvent event) {
        PersonData formPerson = createPersonFromData();
        if (person != null) {
            if (!person.equals(formPerson)) {
                if (!confirmContinueOnUnsavedData()) {
                    return;
                }
            }
        }       
        
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try{
                FileReader fileReader = new FileReader(file.getPath());
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                //read through json
                String json = "";
                String line = null;
                while((line = bufferedReader.readLine()) != null) {
                    json+= line;
                }
                bufferedReader.close();
                fileReader.close();
                person.initFromJsonString(json);
                fillFormFromPerson(person);
                
            } catch(IOException ioex) {
                String message = "Exception occurred while opening " + file.getPath();
                displayExceptionAlert(message, ioex);
            }
        }
        feedback.setText("");
    }
    @FXML 
    public void returnToMenu() {
        Switch.switchTo("menu");
    }
    
    private PersonData createValidatedPersonFromData() {
        PersonData p = new PersonData();
        
        p.setName(nameText.getText());
        
        String wStr = weightText.getText();
        Double w;
        try {
            w = Double.parseDouble(wStr);
        } catch (Exception ex) {
            displayErrorAlert("The weight value must be a double.");
            return null;
        }
        p.setWeight(w);
        
        String heightStr = heightText.getText();
        Integer h;
        try {
            h = Integer.parseInt(heightStr);
        } catch(Exception ex) {
            displayErrorAlert("The height value must be an int");
            return null;
        }
        p.setHeight(h);
 
        String ageStr = ageText.getText();
        Integer age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (Exception ex) {
            displayErrorAlert("The age value must be an Integer.");
            return null;
        }
        p.setAge(age);
        
        String hrStr = heartRateText.getText();
        Integer hr;
        try {
            hr = Integer.parseInt(hrStr);
        } catch(Exception ex) {
            displayErrorAlert("The heartRate value must be an Integer.");
            return null;
        }
        p.setHeartRate(hr);
        
        String wsStr = waistSizeText.getText();
        Integer ws;
        try {
            ws = Integer.parseInt(wsStr);
        } catch(Exception ex) {
            displayErrorAlert("The waistSize value must be an Integer.");
            return null;
        }
        p.setWaistSize(ws);
        
        if (gender.getValue() != null &&  !gender.getValue().toString().isEmpty()) {
            p.setGender(gender.getValue().toString());
        } else {
            displayErrorAlert("A gender must be selected.");
            return null;
        }

        return p;
    }
    private PersonData createPersonFromData() {
        
        PersonData p = new PersonData();
        p.setName(nameText.getText());
        
        String wStr = weightText.getText();
        Double w;
        try {
            w = Double.parseDouble(wStr);
        } catch (Exception ex) {
            w = null;
        }
        p.setWeight(w);
        
        String heightStr = heightText.getText();
        Integer h;
        try {
            h = Integer.parseInt(heightStr);
        } catch(Exception ex) {
            h = null;
        }
        p.setHeight(h);
        
        
        String ageStr = ageText.getText();
        Integer age;
        
        try {
            age = Integer.parseInt(ageStr);
        } catch (Exception ex) {
            age = null;
        }
        p.setAge(age);
        
        String hrStr = heartRateText.getText();
        Integer hr;
        try {
            hr = Integer.parseInt(hrStr);
        } catch(Exception ex) {
            hr = null;
        }
        p.setHeartRate(hr);
        
        String wsStr = waistSizeText.getText();
        Integer ws;
        try {
            ws = Integer.parseInt(wsStr);
        } catch(Exception ex) { 
            ws = null;
        }
        p.setWaistSize(ws);
        
        
        if (gender.getValue() != null &&  !gender.getValue().toString().isEmpty()) {
            p.setGender(gender.getValue().toString());
        } else {
            p.setGender(null);
        }

        return p;
    }
    
    private void fillFormFromPerson(PersonData person) {  
        if (person.getName() != null) {
            nameText.setText(person.getName());
        } else {
            nameText.setText("");
        }
        
        if(person.getWeight() != null) {
            weightText.setText(Double.toString(person.getWeight()));
        } else {
            weightText.setText("");
        }
        if(person.getHeight() != null) {
            heightText.setText(Integer.toString(person.getHeight()));
        } else {
            nameText.setText("");
        }
        
        if (person.getAge() != null) {
            ageText.setText(Integer.toString(person.getAge()));
        } else {
            ageText.setText("");
        }
        
        if(person.getHeartRate() != null) {
            heartRateText.setText(Integer.toString(person.getHeartRate()));
        } else {
            heartRateText.setText("");
        }
        if(person.getWaistSize() != null) {
            waistSizeText.setText(Integer.toString(person.getWaistSize()));
        } else {
            waistSizeText.setText("");
        }
        
        if (person.getGender() != null) {
            gender.setValue(person.getGender());
        } else {
            gender.getSelectionModel().clearSelection();
        }   
    }
    
    private void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        //alert.setHeaderText("Error!");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void displayExceptionAlert(String message, Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("Exception!");
        alert.setContentText(message);

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }
    
    private boolean confirmContinueOnUnsavedData() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Unsaved Data");
        alert.setHeaderText("Changes have not been saved.");
        alert.setContentText("Are you sure you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            return true;
        } else {
            // ... user chose CANCEL or closed the dialog
            return false;
        }
    }  
}
