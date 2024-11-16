package org.mavenproject1.employeemgtapp_javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class EmployeeMgt extends Application {
    private double x =0;
    private double y =0;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EmployeeMgt.class.getResource("EmployeeMgt.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Employee Management System");

        scene.setOnMousePressed((MouseEvent event)->{
            x = event.getSceneX();
            y = event.getSceneY();
        });

        scene.setOnMouseDragged((MouseEvent event)->{
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);

            stage.setOpacity(.8);
        });


        scene.setOnMouseReleased((MouseEvent event)->{
            stage.setOpacity(1);
        });

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}