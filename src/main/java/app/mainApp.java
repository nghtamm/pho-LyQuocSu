package app;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import javafx.stage.Stage;

public class mainApp extends Application{
    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/loginForm.fxml"));
        StackPane root = loader.load();

        Scene scene = new Scene(root, 1280, 720);
        stage.setTitle("Chuỗi cửa hàng phở Lý Quốc Sư: Đăng nhập");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
