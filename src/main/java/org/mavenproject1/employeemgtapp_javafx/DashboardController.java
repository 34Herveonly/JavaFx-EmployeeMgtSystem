package org.mavenproject1.employeemgtapp_javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class DashboardController implements Initializable {

        @FXML
        public Button LogoutBtn;
        @FXML
        private AnchorPane Main_form;

        @FXML
        private Button AddEmployeeBtn;

        @FXML
        private Button EmployeeSalaryBtn;

        @FXML
        private Button HomeBtn;

        @FXML
        private BarChart<?, ?> Home_Chart;

        @FXML
        private AnchorPane Home_Form;

        @FXML
        private Label Home_TotalEmployees;

        @FXML
        private Label Home_TotalInactiveEmployees;

        @FXML
        private Label Home_TotalPresent;

        @FXML
        private Button addEmployee_AddBtn;

        @FXML
        private Button addEmployee_ClearBtn;

        @FXML
        private Button addEmployee_DeleteBtn;

        @FXML
        private TextField addEmployee_EmployeeId;

        @FXML
        private TextField addEmployee_FirstName;
        @FXML
        private Button logout;
        @FXML
        private AnchorPane addEmployee_Form;

        @FXML
        private ComboBox<?> addEmployee_Gender;
        @FXML
        private ImageView addEmployee_Image;

        @FXML
        private Button addEmployee_ImportBtn;

        @FXML
        private TextField addEmployee_LastName;

        @FXML
        private TextField addEmployee_PhoneNum;

        @FXML
        private ComboBox<?> addEmployee_Position;

        @FXML
        private TableView<EmployeeData> addEmployee_TableView;

        @FXML
        private Button addEmployee_UpdateBtn;

        @FXML
        private TableColumn<EmployeeData, String> addEmployee_col_Date;

        @FXML
        private TableColumn<EmployeeData, String> addEmployee_col_EmployeeId;

        @FXML
        private TableColumn<EmployeeData, String> addEmployee_col_Gender;

        @FXML
        private TableColumn<EmployeeData, String> addEmployee_col_LastName;

        @FXML
        private TableColumn<EmployeeData, String> addEmployee_col_PhoneNum;

        @FXML
        private TableColumn<EmployeeData, String> addEmployee_col_Position;

        @FXML
        private TableColumn<EmployeeData, String> addEmployee_col_firstName;

        @FXML
        private TextField addEmployee_search;

        @FXML
        private Label username;
        @FXML
        private Button close;

        @FXML
        private Button minimize;

        @FXML
        private Button salary_ClearBtn;

        @FXML
        private TableView<EmployeeData> salary_TableView;

        @FXML
        private Button salary_UpdateBtn;

        @FXML
        private TableColumn<EmployeeData,String> salary_col_employeeId;

        @FXML
        private TableColumn<EmployeeData,String> salary_col_firstname;

        @FXML
        private TableColumn<EmployeeData,String> salary_col_lastname;

        @FXML
        private TableColumn<EmployeeData,String> salary_col_position;

        @FXML
        private TableColumn<EmployeeData,String> salary_col_salary;

        @FXML
        private TextField salary_employeeId;

        @FXML
        private Label salary_firstname;

        @FXML
        private AnchorPane salary_form;

        @FXML
        private Label salary_lastname;

        @FXML
        private Label salary_position;

        @FXML
        private TextField salary_salary;

        private Connection connect;
        private Statement statement;
        private PreparedStatement prepare;
        private ResultSet resultSet;
        private Image image;

        public void homeTotalEmployees(){

                String sql="SELECT COUNT(id) FROM employees_salary";
                connect=Database.connectDb();
                int countData=0;

                try {
                        prepare=connect.prepareStatement(sql);
                        resultSet=prepare.executeQuery();

                       while(resultSet.next()){
                               countData=resultSet.getInt("COUNT(id)");
                       }
                        Home_TotalEmployees.setText(String.valueOf(countData));

                }catch (Exception e) {
                        e.printStackTrace();
                }
        }

        public void addEmployeeTotalEmployees(){

                String sql="SELECT COUNT(id) FROM employees_salary";
                connect=Database.connectDb();
                int countData=0;

                try {
                        statement=connect.prepareStatement(sql);
                        resultSet=statement.executeQuery(sql);

                        while(resultSet.next()){
                                countData=resultSet.getInt("COUNT(id)");
                        }
                        Home_TotalPresent.setText(String.valueOf(countData));

                }catch (Exception e) {e.printStackTrace();}
        }

        public void defaultNav(){
                username.setText(getData.username);
                HomeBtn.setStyle("-fx-background-color: linear-gradient(to bottom right,#3a4368,#28966c )");

        }

        public void homeTotalInactiveEmployees(){


                String sql="SELECT COUNT(id) FROM employees_salary WHERE salary='0'";
                connect=Database.connectDb();
                int countData=0;

                try {
                        prepare=connect.prepareStatement(sql);
                        resultSet=prepare.executeQuery();

                        while(resultSet.next()){
                                countData=resultSet.getInt("COUNT(id)");
                        }
                        Home_TotalInactiveEmployees.setText(String.valueOf(countData));
                }catch (Exception e) {e.printStackTrace();}
        }

        public void homeChart(){
                Home_Chart.getData().clear();

                String sql="SELECT date,COUNT(id) FROM employees_salary GROUP BY date ORDER BY TIMESTAMP(date) ASC limit 7";
                connect=Database.connectDb();

                try {
                        XYChart.Series chart=new XYChart.Series();

                        while(resultSet.next()){
                                chart.getData().add(new XYChart.Data(resultSet.getString(1),resultSet.getInt(2)));
                        }
                        Home_Chart.getData().add(chart);

                }catch (Exception e) {e.printStackTrace();}
        }


        public void AddEmployeeAdd() {
                java.util.Date date = new java.util.Date(); // Create a new instance of java.util.Date
                java.sql.Date sqlDate = new java.sql.Date(date.getTime()); // Convert to java.sql.Date

                // Updated table name to employeesdata
                String sql = "INSERT INTO employeesdata (employee_id, first_name, last_name, gender, phone_num, position, image, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                connect = Database.connectDb();

                try {
                        Alert alert;

                        if (addEmployee_EmployeeId.getText().isEmpty()
                                || addEmployee_FirstName.getText().isEmpty()
                                || addEmployee_LastName.getText().isEmpty()
                                || addEmployee_Gender.getSelectionModel().getSelectedItem() == null
                                || addEmployee_PhoneNum.getText().isEmpty()
                                || addEmployee_Position.getSelectionModel().getSelectedItem() == null
                                || getData.path == null || getData.path.isEmpty()) {

                                alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error Message");
                                alert.setHeaderText(null);
                                alert.setContentText("Please enter all the fields correctly");
                                alert.showAndWait();

                        } else {
                                String check = "SELECT employee_id FROM employeesdata WHERE employee_id='" + addEmployee_EmployeeId.getText() + "'";

                                statement = connect.createStatement();
                                resultSet = statement.executeQuery(check);

                                if (resultSet.next()) {
                                        alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("Error Message");
                                        alert.setHeaderText(null);
                                        alert.setContentText("Employee ID: " + addEmployee_EmployeeId.getText() + " already exists");
                                        alert.showAndWait();
                                } else {

                                  prepare = connect.prepareStatement(sql);
                                  prepare.setString(1, addEmployee_EmployeeId.getText()); // Employee ID
                                  prepare.setString(2, addEmployee_FirstName.getText());  // First Name
                                  prepare.setString(3, addEmployee_LastName.getText());   // Last Name
                                  prepare.setString(4, (String) addEmployee_Gender.getSelectionModel().getSelectedItem()); // Gender
                                  prepare.setString(5, addEmployee_PhoneNum.getText());  // Phone Number
                                  prepare.setString(6, (String) addEmployee_Position.getSelectionModel().getSelectedItem()); // Position

                                        String uri = getData.path;
                                        uri = uri.replaceAll("/", "\\\\");

                                        prepare.setString(7, uri); // Image Path
                                        prepare.setDate(8, sqlDate); // Date
                                        prepare.executeUpdate();

                                        String insertInfo="INSERT INTO employees_salary"+
                                                "(employee_id,first_name,last_name,position,salary,date)"
                                                +"VALUES (?,?,?,?,?,?)";

                                        prepare=connect.prepareStatement(insertInfo);
                                        prepare.setString(1, addEmployee_EmployeeId.getText());
                                        prepare.setString(2, addEmployee_FirstName.getText());
                                        prepare.setString(3, addEmployee_LastName.getText());
                                        prepare.setString(4, (String) addEmployee_Position.getSelectionModel().getSelectedItem());
                                        prepare.setString(5,"0.0");
                                        prepare.setDate(6, sqlDate);
                                        prepare.executeUpdate();


                                        alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Information Message");
                                        alert.setHeaderText(null);
                                        alert.setContentText("Successfully added employee");
                                        alert.showAndWait();

                                        addEmployeeShowListData();
                                        addEmployeeReset();
                                }
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
        public void addEmployeeUpdate() {
                String uri = getData.path;
                uri = uri.replaceAll("/", "\\\\");

                java.util.Date date = new java.util.Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                String sql = "UPDATE employeesdata SET first_name = ?, last_name = ?, gender = ?, phone_num = ?, position = ?, image = ?, date = ? WHERE employee_id = ?";

                connect = Database.connectDb();

                try {
                        Alert alert;

                        if (addEmployee_EmployeeId.getText().isEmpty()
                                || addEmployee_FirstName.getText().isEmpty()
                                || addEmployee_LastName.getText().isEmpty()
                                || addEmployee_Gender.getSelectionModel().getSelectedItem() == null
                                || addEmployee_PhoneNum.getText().isEmpty()
                                || addEmployee_Position.getSelectionModel().getSelectedItem() == null
                                || getData.path == null || getData.path.isEmpty()) {

                                alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error Message");
                                alert.setHeaderText(null);
                                alert.setContentText("Please complete all the fields to update");
                                alert.showAndWait();

                        } else {
                                alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation Message");
                                alert.setHeaderText(null);
                                alert.setContentText("Are you sure you want to update the EmployeeId: " +
                                        addEmployee_EmployeeId.getText() + "?");
                                Optional<ButtonType> option = alert.showAndWait();

                                if (option.isPresent() && option.get() == ButtonType.OK) {
                                        PreparedStatement preparedStatement = connect.prepareStatement(sql);
                                        preparedStatement.setString(1, addEmployee_FirstName.getText());
                                        preparedStatement.setString(2, addEmployee_LastName.getText());
                                        preparedStatement.setString(3, (String) addEmployee_Gender.getSelectionModel().getSelectedItem());
                                        preparedStatement.setString(4, addEmployee_PhoneNum.getText());
                                        preparedStatement.setString(5, (String) addEmployee_Position.getSelectionModel().getSelectedItem());
                                        preparedStatement.setString(6, uri);
                                        preparedStatement.setDate(7, sqlDate);
                                        preparedStatement.setString(8, addEmployee_EmployeeId.getText()); // Corrected to match the employee_id condition
                                        int rowsAffected = preparedStatement.executeUpdate();

                                        if (rowsAffected > 0) {
                                                // Update employees_salary table
                                                double salary = 0;
                                                String checkData = "SELECT salary FROM employees_salary WHERE employee_id = ?";
                                                preparedStatement = connect.prepareStatement(checkData);
                                                preparedStatement.setString(1, addEmployee_EmployeeId.getText());
                                                resultSet = preparedStatement.executeQuery();

                                                if (resultSet.next()) {
                                                        salary = resultSet.getDouble("salary");
                                                }

                                                String updateInfo = "UPDATE employees_salary SET first_name = ?, last_name = ?, position = ? WHERE employee_id = ?";
                                                PreparedStatement prepare = connect.prepareStatement(updateInfo);
                                                prepare.setString(1, addEmployee_FirstName.getText());
                                                prepare.setString(2, addEmployee_LastName.getText());
                                                prepare.setString(3, (String) addEmployee_Position.getSelectionModel().getSelectedItem());
                                                prepare.setString(4, addEmployee_EmployeeId.getText());
                                                prepare.executeUpdate();

                                                alert = new Alert(Alert.AlertType.INFORMATION);
                                                alert.setTitle("Information Message");
                                                alert.setHeaderText(null);
                                                alert.setContentText("Successfully updated employee");
                                                alert.showAndWait();

                                                // Update the TableView with the new data
                                                addEmployeeShowListData(); // Fetch the updated data
                                                addEmployee_TableView.refresh(); // Refresh the TableView to display the updated data
                                                addEmployeeReset();
                                        } else {
                                                alert = new Alert(Alert.AlertType.ERROR);
                                                alert.setTitle("Error Message");
                                                alert.setHeaderText(null);
                                                alert.setContentText("Failed to update employee in the database.");
                                                alert.showAndWait();
                                        }
                                }
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }

        public void setAddEmployeeDelete() {
                String sql = "DELETE FROM employeesdata WHERE employee_id = ?";

                connect = Database.connectDb();

                try {
                        Alert alert;

                        if (addEmployee_EmployeeId.getText().isEmpty()) {
                                alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error Message");
                                alert.setHeaderText(null);
                                alert.setContentText("Please enter the Employee ID");
                                alert.showAndWait();
                        } else {
                                alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation Message");
                                alert.setHeaderText(null);
                                alert.setContentText("Are you sure you want to delete the EmployeeId: " +
                                        addEmployee_EmployeeId.getText() + "?");
                                Optional<ButtonType> option = alert.showAndWait();

                                if (option.isPresent() && option.get() == ButtonType.OK) {
                                        PreparedStatement preparedStatement = connect.prepareStatement(sql);
                                        preparedStatement.setString(1, addEmployee_EmployeeId.getText());
                                        preparedStatement.executeUpdate();

                                        String deleteInfo = "DELETE FROM employees_salary WHERE employee_id = ?";
                                        preparedStatement = connect.prepareStatement(deleteInfo);
                                        preparedStatement.setString(1, addEmployee_EmployeeId.getText());
                                        preparedStatement.executeUpdate();

                                        alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Information Message");
                                        alert.setHeaderText(null);
                                        alert.setContentText("Successfully deleted employee");
                                        alert.showAndWait();

                                        addEmployeeShowListData();
                                        addEmployeeReset();
                                }
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }

        public void addEmployeeSearch(){
        FilteredList<EmployeeData> filteredData = new FilteredList<>(addEmployeeList,e ->true);

        addEmployee_search.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(predicateEmployeeData-> {

                                if (newValue == null || newValue.isEmpty()) {
                                        return true;
                                }
                                String searchKey = newValue.toLowerCase();

                       if (predicateEmployeeData.getEmployee_id().toString().contains(searchKey)) {
                                        return true;
                       } else if (predicateEmployeeData.getFirst_name().toLowerCase().contains(searchKey)) {
                                        return true;
                       } else if (predicateEmployeeData.getLast_name().toLowerCase().contains(searchKey)) {
                                        return true;
                       } else if (predicateEmployeeData.getGender().toLowerCase().contains(searchKey)) {
                                        return true;
                       } else if (predicateEmployeeData.getPhone_num().toLowerCase().contains(searchKey)){
                                        return true;
                       }else if (predicateEmployeeData.getPosition().toLowerCase().contains(searchKey)){
                                        return true;
                       }else if (predicateEmployeeData.getDate().toString().contains(searchKey)){
                               return true;
                       }
                       else  return false;

                }
                );
        });

                SortedList<EmployeeData> sortList = new SortedList<>(filteredData);
                sortList.comparatorProperty().bind(addEmployee_TableView.comparatorProperty());
                addEmployee_TableView.setItems(sortList);

        }

        public void addEmployeeReset(){
                addEmployee_EmployeeId.setText("");
                addEmployee_FirstName.setText("");
                addEmployee_LastName.setText("");
                addEmployee_Gender.getSelectionModel().clearSelection();
                addEmployee_PhoneNum.setText("");
                addEmployee_Position.getSelectionModel().clearSelection();
                addEmployee_Image.setImage(null);
                getData.path ="";
        }

        public void addEmployeeInsertImage(){
                FileChooser fileChooser = new FileChooser();
                File file=fileChooser.showOpenDialog(Main_form.getScene().getWindow());

        if (file!=null){
                getData.path=file.getAbsolutePath();

                image = new Image(file.toURI().toString(),145,175,false,true);
                addEmployee_Image.setImage(image);
        }

        }


        private String[] positionList={"Market Coordinator","Web Developer(Back End)","Web Developer(Front End)","Real Estate Agent","Business Manager"
        ,"App Developer","Video Editor","Film Maker","Actor"};

        public void AddEmployeePositionList(){
                List<String> listP=new ArrayList<>();

                for (String data : positionList) {
                        listP.add(data);
                }
                ObservableList listData=FXCollections.observableList(listP);
                addEmployee_Position.setItems(listData);
        }

        private String[] listGender={"Male","Female"};

        public void AddEmployeeGenderList(){
                List<String> listG=new ArrayList<>();

                for (String data : listGender) {
                        listG.add(data);
                }
                ObservableList listData=FXCollections.observableList(listG);
                addEmployee_Gender.setItems(listData);
        }
        public ObservableList<EmployeeData> addEmployeeListData(){

                ObservableList<EmployeeData> listData= FXCollections.observableArrayList();
                String sql="select * from employeesData";


                 connect= Database.connectDb();

                try {
                        prepare=connect.prepareStatement(sql);
                        resultSet=prepare.executeQuery();
                        EmployeeData employeeData;

                        while(resultSet.next()){
                                employeeData= new EmployeeData(resultSet.getInt("employee_id"),
                                        resultSet.getString("first_name"),resultSet.getString("last_name"),resultSet.getString("gender"),
                                        resultSet.getString("position"),resultSet.getString("image"),
                                        resultSet.getDate("date"));

                                listData.add(employeeData);
                        }
                }
                catch (Exception e){
                        e.printStackTrace();
                }
                return listData;
        }

        private ObservableList<EmployeeData> addEmployeeList;

        public void addEmployeeShowListData(){
                addEmployeeList = addEmployeeListData();

                addEmployee_col_EmployeeId.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
                addEmployee_col_firstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
                addEmployee_col_LastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
                addEmployee_col_Gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
                addEmployee_col_PhoneNum.setCellValueFactory(new PropertyValueFactory<>("phone_num"));
                addEmployee_col_Position.setCellValueFactory(new PropertyValueFactory<>("position"));
                addEmployee_col_Date.setCellValueFactory(new PropertyValueFactory<>("date"));

                // Set the items of the TableView using the ObservableList
                addEmployee_TableView.setItems(addEmployeeList);
        }


        public void addEmployeeSelect(){

                EmployeeData employeeData=addEmployee_TableView.getSelectionModel().getSelectedItem();
                int num=addEmployee_TableView.getSelectionModel().getSelectedIndex();

                if ((num -1)< -1){return;}

                addEmployee_EmployeeId.setText(String.valueOf(employeeData.getEmployee_id()));
                addEmployee_FirstName.setText(employeeData.getFirst_name());
                addEmployee_LastName.setText(employeeData.getLast_name());
                addEmployee_PhoneNum.setText(employeeData.getPhone_num());

                getData.path=employeeData.getImage();

                String uri="file:"+EmployeeData.getImage();
                image=new Image(uri,145,175,false,true);
                addEmployee_Image.setImage(image);
        }

        public void salaryUpdate(){

                String sql="UPDATE employees_salary SET first_name='"
                        +salary_firstname.getText()+"',last_name='"
                        +salary_lastname.getText()+"',position='"
                        +salary_position.getText()+"',salary='"
                        +salary_salary.getText()+"'WHERE employee_id='"
                        +salary_employeeId.getText()+"'";

                        connect=Database.connectDb();
                try{
                        Alert alert;

                        if (salary_employeeId.getText().isEmpty()||
                             salary_firstname.getText().isEmpty()||
                             salary_lastname.getText().isEmpty()||
                             salary_position.getText().isEmpty())
                        {
                                alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText(null);
                                alert.setContentText("Please select item first");
                                alert.showAndWait();
                        }else {
                                statement=connect.createStatement();
                                statement.executeUpdate(sql);

                                alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information Message");
                                alert.setHeaderText(null);
                                alert.setContentText("Successfully updated");
                                alert.showAndWait();

                                salaryShowListData();
                        }

                }catch (Exception e){e.printStackTrace();}

        }

        public void salaryReset(){
                salary_employeeId.setText("");
                salary_firstname.setText("");
                salary_lastname.setText("");
                salary_position.setText("");
                salary_salary.setText("");
        }

        public ObservableList<EmployeeData> salaryListData() {
                ObservableList<EmployeeData> listData = FXCollections.observableArrayList();
        String sql="select * from employees_salary";


        connect=Database.connectDb();

        try {
        prepare=connect.prepareStatement(sql);
        resultSet=prepare.executeQuery();

        EmployeeData employeeData;

        while(resultSet.next()){
        employeeData= new EmployeeData(resultSet.getInt("employee_id"),resultSet.getString("first_name"),
                resultSet.getString("last_name"),resultSet.getString("position"),
                resultSet.getDouble("salary"));

        listData.add(employeeData);
                }
          } catch (Exception e){e.printStackTrace();}

                return listData;

        }



        private ObservableList<EmployeeData> salaryList;

        public void salaryShowListData(){
                salaryList = salaryListData();
                salary_col_employeeId.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
                salary_col_firstname.setCellValueFactory(new PropertyValueFactory<>("first_name"));
                salary_col_lastname.setCellValueFactory(new PropertyValueFactory<>("last_name"));
                salary_col_position.setCellValueFactory(new PropertyValueFactory<>("position"));
                salary_col_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));


                salary_TableView.setItems(salaryList);
        }

        public void salarySelect(){

                EmployeeData employeeData=salary_TableView.getSelectionModel().getSelectedItem();
                int num=salary_TableView.getSelectionModel().getSelectedIndex();

                if ((num -1)< -1){return;}

                salary_employeeId.setText(String.valueOf(employeeData.getEmployee_id()));
                salary_firstname.setText(employeeData.getFirst_name());
                salary_lastname.setText(employeeData.getLast_name());
                salary_position.setText(employeeData.getPosition());
                salary_salary.setText(String.valueOf(employeeData.getSalary()));


        }

        public void displayUsername(){

                username.setText(getData.username);
        }


        public void switchForm(ActionEvent event) throws IOException {

                if (event.getSource() ==HomeBtn) {
                        Home_Form.setVisible(true);
                        addEmployee_Form.setVisible(false);
                        salary_form.setVisible(false);

                        HomeBtn.setStyle("-fx-background-color: linear-gradient(to bottom right,#3a4368,#28966c )");
                        AddEmployeeBtn.setStyle("-fx-background-color:transparent");
                        EmployeeSalaryBtn.setStyle("-fx-background-color:transparent");

                        homeTotalEmployees();
                        addEmployeeTotalEmployees();
                        homeTotalInactiveEmployees();
                        homeChart();

                }
                else if (event.getSource() ==AddEmployeeBtn) {
                        Home_Form.setVisible(false);
                        addEmployee_Form.setVisible(true);
                        salary_form.setVisible(false);

                        AddEmployeeBtn.setStyle("-fx-background-color: linear-gradient(to bottom right,#3a4368,#28966c )");
                        HomeBtn.setStyle("-fx-background-color:transparent");
                        EmployeeSalaryBtn.setStyle("-fx-background-color:transparent");

                        AddEmployeePositionList();
                        AddEmployeeGenderList();
                        addEmployeeSearch();

                }else if(event.getSource() ==EmployeeSalaryBtn){
                        Home_Form.setVisible(false);
                        addEmployee_Form.setVisible(false);
                        salary_form.setVisible(true);

                        EmployeeSalaryBtn.setStyle("-fx-background-color: linear-gradient(to bottom right,#3a4368,#28966c )");
                        AddEmployeeBtn.setStyle("-fx-background-color:transparent");
                        HomeBtn.setStyle("-fx-background-color:transparent");

                        salaryShowListData();
                }
        }

        public void close(){
                System.exit(0);
        }

        public void minimize(){
                Stage stage = (Stage) Main_form.getScene().getWindow();
                stage.setIconified(true);
        }

        private double x=0;
        private double y=0;


        public void logout() {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to Logout?");
                Optional<ButtonType> result = alert.showAndWait();

                try {

                        if (result.get().equals(ButtonType.OK)) {

                                LogoutBtn.getScene().getWindow().hide();
                                Parent root = FXMLLoader.load(getClass().getResource("EmployeeMgt.fxml"));
                                Stage stage = new Stage();
                                Scene scene = new Scene(root);

                                scene.setOnMousePressed((MouseEvent event) -> {
                                        x = event.getSceneX();
                                        y = event.getSceneY();
                                });

                                scene.setOnMouseDragged((MouseEvent event) -> {
                                        stage.setX(event.getScreenX() - x);
                                        stage.setY(event.getScreenY() - y);

                                        stage.setOpacity(.8);
                                });


                                scene.setOnMouseReleased((MouseEvent event) -> {
                                        stage.setOpacity(1);
                                });

                                stage.initStyle(StageStyle.TRANSPARENT);


                                stage.setScene(scene);
                                stage.show();
                        }

                } catch (IOException e) {
                        throw new RuntimeException(e);
                }
                System.out.println("Logout button: " + logout);

        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                displayUsername();
                defaultNav();

                homeTotalEmployees();
                addEmployeeTotalEmployees();
                homeTotalInactiveEmployees();
                homeChart();

                addEmployeeShowListData();
                AddEmployeeGenderList();
                AddEmployeePositionList();
                salaryShowListData();
        }
    }
