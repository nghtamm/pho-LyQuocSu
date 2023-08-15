package controllers;

import models.menu;

import helpers.userData;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import java.net.URL;

import java.util.ResourceBundle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class orderCardController implements Initializable{
    @FXML
    private Button orderAddButton;

    @FXML
    private AnchorPane orderCard;

    @FXML
    private ImageView orderImage;

    @FXML
    private Label orderName;

    @FXML
    private Label orderPrice;

    @FXML
    private Spinner<Integer> orderSpinner;

    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=phoLyQuocSu;encrypt=true;trustServerCertificate=true;";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "hoangtam2003";

    private menu orderData;
    public void setOrderData(menu orderData){
        this.orderData = orderData;

        orderName.setText(orderData.getMenuName());
        orderPrice.setText(orderData.getMenuPrice() + "VNĐ");
        Image image = new Image(orderData.getMenuImage(), 215, 120, false, true);
        orderImage.setImage(image);
    }

    protected int orderQuantity;
    private SpinnerValueFactory<Integer> ordSpin;
    public void setQuantity(){
        ordSpin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, 0);
        orderSpinner.setValueFactory(ordSpin);
    }

    public void orderCardAdd(){
        dashboardController dashboardC = new dashboardController();
        dashboardC.orderGenerator();
        orderQuantity = orderSpinner.getValue();
        int orderPrice = orderQuantity * orderData.getMenuPrice();

        if(orderQuantity > 0){
            try{
                Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                String orderInsert = "INSERT INTO Orders (cusID, ordID, ordName, ordQuantity, ordPrice, empUsername) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(orderInsert);
                statement.setString(1, userData.cID);
                statement.setString(2, orderData.getMenuID());
                statement.setString(3, orderData.getMenuName());
                statement.setInt(4, orderQuantity);
                statement.setInt(5, orderPrice);
                statement.setString(6, userData.username);
                statement.executeUpdate();

                conn.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi: Số lượng sản phẩm");
            alert.setHeaderText(null);
            alert.setContentText("Số lượng sản phẩm phải không được là 0!");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        setQuantity();
    }
}
