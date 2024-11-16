package org.mavenproject1.employeemgtapp_javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class EmployeeMgtController implements Initializable {

    @FXML
    private Button ExitBtn;

    @FXML
    private Button LoginBtn;

    @FXML
    private AnchorPane Main_form;

    @FXML
    private PasswordField Password;

    @FXML
    private TextField Username;

    // Database Tools
    private Connection connection;
    private PreparedStatement  prepStatement;
    private ResultSet resultSet;

    private double x =0;
    private double y =0;

    public void LoginAdmin(){

        String sql = "SELECT * FROM employee_mgt_app_database.admin WHERE Username=? AND Password=?;";
        connection= Database.connectDb();

        try{

            prepStatement = connection.prepareStatement(sql);
            prepStatement.setString(1,Username.getText());
            prepStatement.setString(2,Password.getText());
            resultSet = prepStatement.executeQuery();

            Alert alert;
            if(Username.getText().isEmpty() || Password.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the fields");
                alert.showAndWait();
            }
            else {
                if(resultSet.next()){

                    getData.username=Username.getText();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informative Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully logged in");
                    alert.showAndWait();



                    LoginBtn.getScene().getWindow().hide();
                    Parent root= FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);


                    root.setOnMousePressed((MouseEvent event)->{
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });


                    root.setOnMouseDragged((MouseEvent event)->{
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();
                }
                else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username or Password");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  void close(){
        System.exit(0);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
