/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drkwkdfitnessapp;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author danielking
 */
public class PersonData implements java.io.Serializable, Fitness {
    
    private String name;
    private Double weight;
    private Integer age;
    private String gender;
    private Integer waistSize;
    private Integer heartRate;
    private Integer height;
    private double BMI;
    
    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    
    @Override
    public Double getWeight() {
        return weight;
    }
    @Override
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public Integer getWaistSize() {
        return waistSize;
    }
    public void setWaistSize(Integer waistSize) {
        this.waistSize = waistSize;
    }
    public Integer getHeartRate() {
        return heartRate;
    }
    public void setHeartRate(Integer heartRate){
        this.heartRate = heartRate;
    }
    @Override
    public Integer getHeight() {
        return height;
    }
    @Override
    public void setHeight(Integer height) {
        this.height = height;
    }
    @Override
    public Double calculateBMI(Double weight, Integer height) {
        BMI = (weight *703)/(height * height);
        return BMI;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof PersonData)) {
            return false;
        }
        
        PersonData other = (PersonData) obj;
        
        if (name == null) {
            if (!(other.getName() == null)) {
                return false;
            }
        }
        else if (!name.equals(other.getName())) {
            return false;
        }
        
        if(weight == null) {
            if(!(other.getWeight() == null)){
                return false;
            }
        } else if(!(weight.equals(other.getWeight()))) {
            return false;
        }
        if(height == null) {
            if(!(other.getHeight() == null)){
                return false;
            }
        }  else if(!(height.equals(other.getHeight()))) {
                return false;
            
        }
        
        if (age == null) {
             if (!(other.getAge() == null)) {
                return false;
            }           
        } else if (! (age.equals(other.getAge()))) {
            return false;
        }
        
        if (gender == null) {
            if (!(other.getGender() == null)) {
                return false;
            }            
        }
        else if (!gender.equals(other.getGender())) {
            return false;
        }
            
        return true;
    }
    
    public String toJsonString() {
        JSONObject obj = new JSONObject();
        if(name != null) obj.put("name", name);
        if(gender != null) obj.put("gender", gender);
        if(age != null) obj.put("age", age);
        if(heartRate != null) obj.put("heartRate", heartRate);
        if(waistSize != null) obj.put("waistSize", waistSize);
        if(weight != null) obj.put("weight", weight);
        if(height != null) obj.put("height", height);
        
        return obj.toJSONString();
    }
    public void initFromJsonString(String jsonString) {
        name = "";
        age = null;
        heartRate = null;
        waistSize = null;
        gender = null;
        height = null;
        weight = null;

        
        if (jsonString == null || jsonString == "") return;
            JSONObject jsonObj;
            try {
            jsonObj = (JSONObject) JSONValue.parse(jsonString);
            } catch(Exception ex) {
                return;
            }
            
            if(jsonObj == null) {
                return;
            }
            name = (String) jsonObj.getOrDefault("name", "");           
            gender = (String) jsonObj.getOrDefault("gender", null);
            
            Object weightObj = jsonObj.getOrDefault("weight", null);
            if(weightObj != null) {
                if(weightObj instanceof Double) {
                    Double doubleNum = (Double)weightObj;
                    weight = new Double(doubleNum.doubleValue());
                } else {
                    weight = null;
                } 
            }
        Object heightObj = jsonObj.getOrDefault("height", null);
        if(heightObj != null) {
            if(heightObj instanceof Long) {
                Long intNum = (Long)heightObj;
                height = new Integer(intNum.intValue());
            } else {
                height = null;
            } 
        }
         
            Object ageObj = jsonObj.getOrDefault("age", null);
            
        if (ageObj != null) {
            if (ageObj instanceof Long) {
                Long longAge = (Long)ageObj;
                age = new Integer(longAge.intValue());
            } else {
                age = null;
            } 
        }
        
        Object hrObj = jsonObj.getOrDefault("heartRate", null);
        if(hrObj != null) {
            if(hrObj instanceof Long) {
                Long longNum = (Long)hrObj;
                heartRate = new Integer(longNum.intValue());
            } else {
                heartRate = null;
            } 
        }
        Object wsObj = jsonObj.getOrDefault("waistSize", null);
        if(wsObj != null) {
            if(wsObj instanceof Long) {
                Long longNum = (Long)wsObj;
                waistSize = new Integer(longNum.intValue());
            } else {
                waistSize = null;
            } 
        }
    }
    
}
