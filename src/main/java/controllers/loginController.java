package controllers;

import helpers.userData;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import javafx.stage.Stage;
import javafx.stage.Modality;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class loginController{
    @FXML
    private Button logButton;

    @FXML
    private AnchorPane logForm;

    @FXML
    private PasswordField logPassword;

    @FXML
    private Hyperlink logRegister;

    @FXML
    private TextField logUsername;

    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=phoLyQuocSu;encrypt=true;trustServerCertificate=true;";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "hoangtam2003";

    public void pressRegister(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/registerForm.fxml"));
            StackPane root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Chuỗi cửa hàng phở Lý Quốc Sư: Đăng ký");
            stage.setResizable(false);

            Scene scene = new Scene(root, 460, 640);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void login(){
        String username = logUsername.getText();
        String password = logPassword.getText();

        if (username.isEmpty() || password.isEmpty()){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Thông báo: Đăng nhập thất bại");
            alert.setHeaderText(null);
            alert.setContentText("Tài khoản và mật khẩu không được để trống!");
            alert.showAndWait();
        }
        else{
            try{
                Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                String infoFetch = "SELECT * FROM Account WHERE username = ? AND password = ?";
                PreparedStatement statement = conn.prepareStatement(infoFetch);
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()){
                    userData.username = username;

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Thông báo: Đăng nhập thành công");
                    alert.setHeaderText(null);
                    alert.setContentText("Đăng nhập tài khoản thành công!");
                    alert.showAndWait();

                    Stage current = (Stage)logButton.getScene().getWindow();
                    current.close();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dashboard.fxml"));
                    StackPane root = loader.load();

                    Stage stage = new Stage();
                    stage.setTitle("Chuỗi cửa hàng phở Lý Quốc Sư: Menu chính");
                    stage.setResizable(false);

                    Scene scene = new Scene(root, 1366, 768);
                    stage.setScene(scene);
                    stage.show();

                    conn.close();
                }
                else{
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Thông báo: Đăng nhập thất bại");
                    alert.setHeaderText(null);
                    alert.setContentText("Tên đăng nhập/ mật khẩu sai hoặc không tồn tại!");
                    alert.showAndWait();

                    logPassword.clear();
                    conn.close();
                }
            } catch (SQLException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
