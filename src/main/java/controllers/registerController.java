package controllers;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class registerController{
    @FXML
    private Button regButton;

    @FXML
    private AnchorPane regForm;

    @FXML
    private Hyperlink regLogin;

    @FXML
    private PasswordField regPassword;

    @FXML
    private TextField regUsername;

    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=phoLyQuocSu;encrypt=true;trustServerCertificate=true;";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "hoangtam2003";

    public void exit(){
        Stage stage = (Stage)regLogin.getScene().getWindow();
        stage.close();
    }

    public void register(){
        String username = regUsername.getText();
        String password = regPassword.getText();

        if (username.isEmpty() || password.isEmpty()){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Thông báo: Đăng ký thất bại");
            alert.setHeaderText(null);
            alert.setContentText("Tài khoản và mật khẩu không được để trống!");
            alert.showAndWait();
        }
        else{
            try{
                Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

                String infoFetch = "SELECT * FROM Account WHERE username = ? AND password = ?";
                PreparedStatement fetchStatement = conn.prepareStatement(infoFetch);
                fetchStatement.setString(1, username);
                fetchStatement.setString(2, password);
                ResultSet resultSet = fetchStatement.executeQuery();

                if (resultSet.next()){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Thông báo: Đăng ký thất bại");
                    alert.setHeaderText(null);
                    alert.setContentText("Tài khoản đã tồn tại!");
                    alert.showAndWait();

                    conn.close();
                }
                else{
                    String infoInsert = "INSERT INTO Account (username, password) VALUES (?, ?)";
                    PreparedStatement insertStatement = conn.prepareStatement(infoInsert);
                    insertStatement.setString(1, username);
                    insertStatement.setString(2, password);
                    insertStatement.executeUpdate();
                    conn.close();

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Thông báo: Đăng ký thành công");
                    alert.setHeaderText(null);
                    alert.setContentText("Đăng ký tài khoản thành công!");
                    alert.showAndWait();

                    Stage stage = (Stage)regButton.getScene().getWindow();
                    stage.close();
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
