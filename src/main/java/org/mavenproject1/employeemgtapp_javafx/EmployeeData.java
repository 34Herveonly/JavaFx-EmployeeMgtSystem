package org.mavenproject1.employeemgtapp_javafx;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;

import java.sql.Date;

public class EmployeeData {
    private Integer employee_id;
    private String first_name;
    private String last_name;
    private String gender;
    private String phone_num;
    private String position;
    private static String image;
    private Date date;
    private Double salary;


    public EmployeeData(Integer employee_id, String first_name, String last_name, String gender,
                        String phone_num, String position, Date date) {

        this.employee_id = employee_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.phone_num = phone_num;
        this.position = position;
        this.image = image;
        this.date = date;
    }

    public EmployeeData(Integer employee_id,String first_name,String last_name,String position,Double salary){
        this.employee_id = employee_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.position = position;
        this.salary = salary;

    }

    public Integer getEmployee_id() {
        return employee_id;
    }
    public String getFirst_name() {
        return first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public String getGender() {
        return gender;
    }
    public String getPhone_num() {
        return phone_num;
    }
    public String getPosition() {
        return position;
    }
    public static String getImage() {
        return image;
    }
    public Date getDate() {
        return date;
    }
    public Double getSalary() {
        return salary;
    }

}
