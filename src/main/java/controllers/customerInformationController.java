package controllers;

import helpers.userData;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.*;

import javafx.stage.Stage;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.util.converter.IntegerStringConverter;

import java.net.URL;

public class customerInformationController implements Initializable{

    @FXML
    private ComboBox<String> customerGender;

    @FXML
    private TextField customerName;

    @FXML
    private TextField customerTel;

    @FXML
    private Button doneButton;

    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=phoLyQuocSu;encrypt=true;trustServerCertificate=true;";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "hoangtam2003";

    private final String[] genderList = {"Nam", "Nữ"};
    public void customerGenderList(){
        List<String> genderL = new ArrayList<>();
        for(String data: genderList)
            genderL.add(data);
        ObservableList listData = FXCollections.observableArrayList(genderL);
        customerGender.setItems(listData);
    }

    public void customerAdd(){
        dashboardController dashboardC = new dashboardController();
        dashboardC.orderGenerator();

        if(customerName.getText().isEmpty()
                || customerGender.getSelectionModel().isEmpty()
                || customerTel.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi: Nhập liệu thông tin khách hàng");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ tất cả các thông tin!");
            alert.showAndWait();
        }
        else{
            try{
                Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                String customerInsert = "INSERT INTO Customers (cusID, cusName, cusGender, cusTel) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(customerInsert);
                statement.setString(1, userData.cID);
                statement.setString(2, customerName.getText());
                statement.setString(3, customerGender.getSelectionModel().getSelectedItem());
                statement.setString(4, customerTel.getText());

                statement.executeUpdate();
                conn.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo: Tạo hóa đơn thanh toán");
                alert.setHeaderText(null);
                alert.setContentText("Thêm thông tin hóa đơn thanh toán thành công!");
                alert.showAndWait();

                Stage stage = (Stage)doneButton.getScene().getWindow();
                stage.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void restrictNumericInput(TextField textField){
        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (!newText.matches("\\d*") || newText.startsWith("00")) {
                return null;
            } else {
                return change;
            }
        });
        textField.setTextFormatter(formatter);
    }

    public void initialize(URL location, ResourceBundle resources){
        customerGenderList();
        restrictNumericInput(customerTel);
    }
}
