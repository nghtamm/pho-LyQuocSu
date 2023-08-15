package controllers;

import models.customer;
import models.employee;
import models.menu;
import models.orders;
import models.receipt;

import helpers.userData;
import helpers.UUIDGenerator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;

import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;

import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.net.URL;

import java.util.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class dashboardController implements Initializable{
    @FXML
    private Label currentUsername;

    @FXML
    private Button customerButton;

    @FXML
    private AnchorPane customerDashboard;

    @FXML
    private TextField customerSearchBox;

    @FXML
    private TableView<customer> customerTable;

    @FXML
    private TableColumn<customer, String> customerTable_ID;

    @FXML
    private TableColumn<customer, String> customerTable_gender;

    @FXML
    private TableColumn<customer, String> customerTable_name;

    @FXML
    private TableColumn<customer, String> customerTable_tel;

    @FXML
    private Button employeeButton;

    @FXML
    private AnchorPane employeeDashboard;

    @FXML
    private Button employeeInput_addButton;

    @FXML
    private TextField employeeInput_address;

    @FXML
    private DatePicker employeeInput_birth;

    @FXML
    private Button employeeInput_delButton;

    @FXML
    private ComboBox<String> employeeInput_gender;

    @FXML
    private ComboBox<String> employeeInput_pos;

    @FXML
    private ImageView employeeInput_image;

    @FXML
    private Button employeeInput_importButton;

    @FXML
    private TextField employeeInput_name;

    @FXML
    private TextField employeeInput_tel;

    @FXML
    private Button employeeInput_updateButton;

    @FXML
    private TableView<employee> employeeTable;

    @FXML
    private TableColumn<employee, String> employeeTable_ID;

    @FXML
    private TableColumn<employee, String> employeeTable_address;

    @FXML
    private TableColumn<employee, String> employeeTable_birth;

    @FXML
    private TableColumn<employee, String> employeeTable_gender;

    @FXML
    private TableColumn<employee, String> employeeTable_name;

    @FXML
    private TableColumn<employee, String> employeeTable_pos;

    @FXML
    private TableColumn<employee, String> employeeTable_tel;

    @FXML
    private ImageView logoutButton;

    @FXML
    private Button menuButton;

    @FXML
    private AnchorPane menuDashboard;

    @FXML
    private Button menuInput_addButton;

    @FXML
    private Button menuInput_delButton;

    @FXML
    private ImageView menuInput_image;

    @FXML
    private Button menuInput_importButton;

    @FXML
    private TextField menuInput_name;

    @FXML
    private TextField menuInput_price;

    @FXML
    private ComboBox<String> menuInput_type;

    @FXML
    private Button menuInput_updateButton;

    @FXML
    private TableView<menu> menuTable;

    @FXML
    private TableColumn<menu, String> menuTable_ID;

    @FXML
    private TableColumn<menu, String> menuTable_name;

    @FXML
    private TableColumn<menu, String> menuTable_price;

    @FXML
    private TableColumn<menu, String> menuTable_type;

    @FXML
    private TextField orderAmount;

    @FXML
    private Button orderButton;

    @FXML
    private Label orderChange;

    @FXML
    private Button orderClearButton;

    @FXML
    private AnchorPane orderDashboard;

    @FXML
    private GridPane orderGridPane;

    @FXML
    private Button orderPayButton;

    @FXML
    private Button orderReceiptButton;

    @FXML
    private ScrollPane orderScrollPane;

    @FXML
    private TableView<orders> orderTable;

    @FXML
    private TableColumn<orders, String> orderTable_name;

    @FXML
    private TableColumn<orders, String> orderTable_price;

    @FXML
    private TableColumn<orders, String> orderTable_quantity;

    @FXML
    private ImageView orderTable_refresh;

    @FXML
    private Label orderTotal;

    @FXML
    private Button receiptButton;

    @FXML
    private AnchorPane receiptDashboard;

    @FXML
    private TextField receiptSearchBox;

    @FXML
    private TableView<receipt> receiptTable;

    @FXML
    private TableColumn<receipt, String> receiptTable_ID;

    @FXML
    private TableColumn<receipt, String> receiptTable_cusID;

    @FXML
    private TableColumn<receipt, String> receiptTable_cusName;

    @FXML
    private TableColumn<receipt, String> receiptTable_date;

    @FXML
    private TableColumn<receipt, String> receiptTable_total;

    @FXML
    private TableColumn<receipt, String> receiptTable_username;

    @FXML
    private Button reportButton;

    @FXML
    private BarChart<?, ?> reportCustomerChart;

    @FXML
    private AnchorPane reportDashboard;

    @FXML
    private AreaChart<?, ?> reportIncomeChart;

    @FXML
    private Label reportMonthlyIncome;

    @FXML
    private Label reportTodayIncome;

    @FXML
    private AnchorPane dashboard;

    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=phoLyQuocSu;encrypt=true;trustServerCertificate=true;";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "hoangtam2003";

    public void displayUsername(){
        String username = userData.username;
        currentUsername.setText(username);
    }

    public void logout(){
        try{
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Thông báo: Đăng xuất");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc chắn muốn đăng xuất hay không?");
            Optional<ButtonType> option = alert.showAndWait();
            if(option.get().equals(ButtonType.OK)){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/loginForm.fxml"));
                StackPane root = loader.load();

                Stage current = (Stage) logoutButton.getScene().getWindow();
                current.close();

                Stage stage = new Stage();
                stage.setTitle("Chuỗi cửa hàng phở Lý Quốc Sư: Đăng nhập");
                stage.setResizable(false);

                Scene scene = new Scene(root, 1280, 720);
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void dashboardSwitch(ActionEvent event){
        if(event.getSource() == employeeButton){
            employeeDashboard.setVisible(true);
            menuDashboard.setVisible(false);
            orderDashboard.setVisible(false);
            customerDashboard.setVisible(false);
            receiptDashboard.setVisible(false);
            reportDashboard.setVisible(false);

            employeeGenderList();
            employeePositionList();
            employeeShowData();
        }
        else if(event.getSource() == menuButton){
            employeeDashboard.setVisible(false);
            menuDashboard.setVisible(true);
            orderDashboard.setVisible(false);
            customerDashboard.setVisible(false);
            receiptDashboard.setVisible(false);
            reportDashboard.setVisible(false);

            menuTypeList();
            menuShowData();
        }
        else if(event.getSource() == orderButton){
            employeeDashboard.setVisible(false);
            menuDashboard.setVisible(false);
            orderDashboard.setVisible(true);
            customerDashboard.setVisible(false);
            receiptDashboard.setVisible(false);
            reportDashboard.setVisible(false);

            orderDisplayCard();
            orderShowData();
            orderTotal();
        }
        else if(event.getSource() == customerButton){
            employeeDashboard.setVisible(false);
            menuDashboard.setVisible(false);
            orderDashboard.setVisible(false);
            customerDashboard.setVisible(true);
            receiptDashboard.setVisible(false);
            reportDashboard.setVisible(false);

            customerShowData();
            customerSearch();
        }
        else if(event.getSource() == receiptButton){
            employeeDashboard.setVisible(false);
            menuDashboard.setVisible(false);
            orderDashboard.setVisible(false);
            customerDashboard.setVisible(false);
            receiptDashboard.setVisible(true);
            reportDashboard.setVisible(false);

            receiptShowData();
            receiptSearch();
        }
        else if(event.getSource() == reportButton){
            employeeDashboard.setVisible(false);
            menuDashboard.setVisible(false);
            orderDashboard.setVisible(false);
            customerDashboard.setVisible(false);
            receiptDashboard.setVisible(false);
            reportDashboard.setVisible(true);

            reportTIncome();
            reportMIncome();
            reportIChart();
            reportCChart();
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

    public void restrictDatePicker(DatePicker datePicker){
        datePicker.setDayCellFactory(picker -> new DateCell(){
            @Override
            public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty);
                if (date.isAfter(LocalDate.now())){
                    setDisable(true);
                    setStyle("-fx-background-color: #808080;");
                }
            }
        });
    }





    // DASHBOARD: BÁO CÁO THỐNG KÊ
    public void reportTIncome(){
        int todayIncome = 0;
        try{
                Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                String tIncomeFetch = "SELECT SUM(total) FROM Receipt WHERE date = ?";
                PreparedStatement statement = conn.prepareStatement(tIncomeFetch);
                statement.setString(1, LocalDate.now().toString());
                ResultSet resultSet = statement.executeQuery();

                if(resultSet.next()){
                    todayIncome = resultSet.getInt(1);
                }

                reportTodayIncome.setText(todayIncome + "VNĐ");

                conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void reportMIncome(){
        int monthlyIncome = 0;
        try{
                Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                String mIncomeFetch = "SELECT SUM(total) FROM Receipt WHERE MONTH(date) = MONTH(GETDATE()) AND YEAR(date) = YEAR(GETDATE())";
                PreparedStatement statement = conn.prepareStatement(mIncomeFetch);
                ResultSet resultSet = statement.executeQuery();

                if(resultSet.next()){
                    monthlyIncome = resultSet.getInt(1);
                }

                reportMonthlyIncome.setText(monthlyIncome + "VNĐ");

                conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void reportIChart(){
        reportIncomeChart.getData().clear();
        XYChart.Series chart = new XYChart.Series();

        try{
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            String iChartFetch = "SELECT date, SUM(total) FROM Receipt GROUP BY date ORDER BY date";
            PreparedStatement statement = conn.prepareStatement(iChartFetch);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                chart.getData().add(new XYChart.Data<>(resultSet.getString(1), resultSet.getInt(2)));
            }

            reportIncomeChart.getData().add(chart);

            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void reportCChart(){
        reportCustomerChart.getData().clear();
        XYChart.Series chart = new XYChart.Series();

        try{
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            String cChartFetch = "SELECT date, COUNT(ID) FROM Receipt GROUP BY date ORDER BY date";
            PreparedStatement statement = conn.prepareStatement(cChartFetch);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                chart.getData().add(new XYChart.Data<>(resultSet.getString(1), resultSet.getInt(2)));
            }

            reportCustomerChart.getData().add(chart);

            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }






    // DASHBOARD: QUẢN LÝ NHÂN VIÊN
    private final String[] genderList = {"Nam", "Nữ"};
    public void employeeGenderList(){
        List<String> genderL = new ArrayList<>();
        for(String data: genderList)
            genderL.add(data);
        ObservableList listData = FXCollections.observableArrayList(genderL);
        employeeInput_gender.setItems(listData);
    }

    private final String[] positionList = {"Quản lý", "Bếp trưởng", "Phụ bếp", "Bồi bàn", "Nhân viên vệ sinh"};
    public void employeePositionList(){
        List<String> positionL = new ArrayList<>();
        for(String data: positionList)
            positionL.add(data);
        ObservableList listData = FXCollections.observableArrayList(positionL);
        employeeInput_pos.setItems(listData);
    }

    public ObservableList<employee> employeeDataList(){
        ObservableList<employee> listData = FXCollections.observableArrayList();

        try{
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            String empFetch = "SELECT * FROM Employee";
            PreparedStatement statement = conn.prepareStatement(empFetch);
            ResultSet resultSet = statement.executeQuery();

            employee empData;
            while(resultSet.next()){
                empData = new employee(resultSet.getString("empName"),
                        resultSet.getString("empGender"),
                        resultSet.getString("empTel"),
                        resultSet.getString("empID"),
                        resultSet.getDate("empBirth"),
                        resultSet.getString("empAddress"),
                        resultSet.getString("empPos"),
                        resultSet.getString("empImage"));
                listData.add(empData);
            }

            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return listData;
    }

    public void employeeShowData(){
        ObservableList<employee> empDataList = employeeDataList();

        employeeTable_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        employeeTable_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        employeeTable_tel.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        employeeTable_ID.setCellValueFactory(new PropertyValueFactory<>("empID"));
        employeeTable_birth.setCellValueFactory(new PropertyValueFactory<>("empBirth"));
        employeeTable_address.setCellValueFactory(new PropertyValueFactory<>("empAddress"));
        employeeTable_pos.setCellValueFactory(new PropertyValueFactory<>("empPos"));

        employeeTable.setItems(empDataList);
    }

    public void employeeSelectData(){
        employee empData = employeeTable.getSelectionModel().getSelectedItem();

        employeeInput_name.setText(empData.getFullName());
        employeeInput_gender.getSelectionModel().select(empData.getGender());
        employeeInput_birth.setValue(LocalDate.parse(empData.getEmpBirth().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        employeeInput_address.setText(empData.getEmpAddress());
        employeeInput_tel.setText(empData.getPhoneNumber());
        employeeInput_pos.getSelectionModel().select(empData.getEmpPos());
        userData.path = empData.getEmpImage();
        employeeInput_image.setImage(new Image(empData.getEmpImage(), 160, 200, false, true));
    }

    public void employeeImportButton(){
        FileChooser fileCh = new FileChooser();
        fileCh.getExtensionFilters().add(new ExtensionFilter("Chọn file hình ảnh...", "*png", "*jpg"));

        File file = fileCh.showOpenDialog(dashboard.getScene().getWindow());
        if(file != null){
            userData.path = file.getAbsolutePath();
            Image image = new Image(file.toURI().toString(), 160, 200, false, true);
            employeeInput_image.setImage(image);
        }
    }

    public void employeeAdd(){
        if(employeeInput_name.getText().isEmpty()
                || employeeInput_gender.getSelectionModel().isEmpty()
                || employeeTable_tel.getText().isEmpty()
                || employeeInput_birth.getValue() == null
                || employeeInput_address.getText().isEmpty()
                || employeeInput_pos.getSelectionModel().isEmpty()
                || userData.path == null){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Lỗi: Nhập liệu thông tin nhân viên");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ tất cả các thông tin!");
            alert.showAndWait();
        }
        else{
            try{
                Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                String empInsert = "INSERT INTO Employee (empID, empName, empGender, empTel, empAddress, empPos, empImage, empBirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(empInsert);
                statement.setString(1, "NV" + UUIDGenerator.shortenUUID());
                statement.setString(2, employeeInput_name.getText());
                statement.setString(3, employeeInput_gender.getSelectionModel().getSelectedItem());
                statement.setString(4, employeeInput_tel.getText());
                statement.setString(5, employeeInput_address.getText());
                statement.setString(6, employeeInput_pos.getSelectionModel().getSelectedItem());
                statement.setString(7, userData.path);
                statement.setString(8, employeeInput_birth.getValue().toString());

                statement.executeUpdate();
                conn.close();

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Thông báo: Thêm thông tin nhân viên");
                alert.setHeaderText(null);
                alert.setContentText("Thêm thông tin nhân viên thành công!");
                alert.showAndWait();

                employeeShowData();
                employeeClearInput();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void employeeUpdate(){
        employee empData = employeeTable.getSelectionModel().getSelectedItem();

        if(empData != null){
            if(employeeInput_name.getText().isEmpty()
                    || employeeInput_gender.getSelectionModel().isEmpty()
                    || employeeTable_tel.getText().isEmpty()
                    || employeeInput_birth.getValue() == null
                    || employeeInput_address.getText().isEmpty()
                    || employeeInput_pos.getSelectionModel().isEmpty()
                    || userData.path == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi: Nhập liệu thông tin nhân viên");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng điền đầy đủ tất cả các thông tin!");
                alert.showAndWait();
            }
            else{
                try{
                    Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                    String empUpdate = "UPDATE Employee SET empName = ?, empGender = ?, empTel = ?, empAddress= ?, empPos = ?, empImage = ?, empBirth = ? WHERE empID = ?";
                    PreparedStatement statement = conn.prepareStatement(empUpdate);
                    statement.setString(1, employeeInput_name.getText());
                    statement.setString(2, employeeInput_gender.getSelectionModel().getSelectedItem());
                    statement.setString(3, employeeInput_tel.getText());
                    statement.setString(4, employeeInput_address.getText());
                    statement.setString(5, employeeInput_pos.getSelectionModel().getSelectedItem());
                    statement.setString(6, userData.path);
                    statement.setString(7, employeeInput_birth.getValue().toString());
                    statement.setString(8, empData.getEmpID());

                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Thông báo: Cập nhật thông tin nhân viên");
                    alert.setHeaderText(null);
                    alert.setContentText("Bạn có chắc chắn muốn cập nhật lại thông tin của nhân viên này không?");
                    Optional<ButtonType> option = alert.showAndWait();
                    if(option.get().equals(ButtonType.OK)){
                        statement.executeUpdate();
                        conn.close();

                        Alert success = new Alert(AlertType.INFORMATION);
                        success.setTitle("Thông báo: Cập nhật thông tin nhân viên");
                        success.setHeaderText(null);
                        success.setContentText("Cập nhật thông tin nhân viên thành công!");
                        success.showAndWait();

                        employeeShowData();
                        employeeClearInput();
                    }
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void employeeDelete(){
        employee empData = employeeTable.getSelectionModel().getSelectedItem();

        if(empData != null){
            try{
                Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                String empDelete = "DELETE FROM Employee WHERE empID = ?";
                PreparedStatement statement = conn.prepareStatement(empDelete);
                statement.setString(1, empData.getEmpID());

                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Thông báo: Xóa thông tin nhân viên");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có chắc chắn muốn xóa thông tin của nhân viên này không?");
                Optional<ButtonType> option = alert.showAndWait();
                if(option.get().equals(ButtonType.OK)){
                    statement.executeUpdate();
                    conn.close();

                    Alert success = new Alert(AlertType.INFORMATION);
                    success.setTitle("Thông báo: Xóa thông tin nhân viên");
                    success.setHeaderText(null);
                    success.setContentText("Xóa thông tin nhân viên thành công!");
                    success.showAndWait();

                    employeeShowData();
                    employeeClearInput();
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void employeeClearInput(){
        employeeInput_name.clear();
        employeeInput_gender.getSelectionModel().clearSelection();
        employeeInput_tel.clear();
        employeeInput_birth.setValue(null);
        employeeInput_address.clear();
        employeeInput_pos.getSelectionModel().clearSelection();
        userData.path = null;
        employeeInput_image.setImage(null);
    }





    //DASHBOARD: GIAO DIỆN ORDER
    protected String cusID;
    public void orderGenerator(){
        try{
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            String existence = "SELECT * FROM Orders";
            PreparedStatement existStatement = conn.prepareStatement(existence);
            ResultSet existResult = existStatement.executeQuery();
            if(existResult.next()){
                String lastFetch = "SELECT TOP 1 cusID FROM Orders ORDER BY ID DESC";
                PreparedStatement lastFetchStatement = conn.prepareStatement(lastFetch);
                ResultSet lastFetchResult = lastFetchStatement.executeQuery();
                if(lastFetchResult.next()){
                    cusID = lastFetchResult.getString(1);
                    userData.cID = cusID;
                }
            }
            else{
                String checkOrder = "SELECT COUNT(DISTINCT cusID) FROM Orders";
                PreparedStatement orderCheckStatement = conn.prepareStatement(checkOrder);
                ResultSet orderCheckResult = orderCheckStatement.executeQuery();
                int countOrders = 0;
                if(orderCheckResult.next()) {
                    countOrders = orderCheckResult.getInt(1);
                }

                String checkReceipt = "SELECT COUNT(DISTINCT cusID) FROM Receipt";
                PreparedStatement receiptCheckStatement = conn.prepareStatement(checkReceipt);
                ResultSet receiptCheckResult = receiptCheckStatement.executeQuery();
                int countReceipt = 0;
                if(receiptCheckResult.next()){
                    countReceipt = receiptCheckResult.getInt(1);
                }

                if(countOrders <= countReceipt){
                    cusID = "KH" + UUIDGenerator.shortenUUID();
                    userData.cID = cusID;
                }
            }

            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ObservableList<menu> orderCardDataList(){
        ObservableList<menu> listData = FXCollections.observableArrayList();

        try{
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            String orderCardFetch = "SELECT * FROM Menu";
            PreparedStatement statement = conn.prepareStatement(orderCardFetch);
            ResultSet resultSet = statement.executeQuery();

            menu orderCardData;
            while(resultSet.next()){
                orderCardData = new menu(resultSet.getString("menuID"),
                        resultSet.getString("menuName"),
                        resultSet.getString("menuType"),
                        resultSet.getInt("menuPrice"),
                        resultSet.getString("menuImage"));
                listData.add(orderCardData);
            }

            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return listData;
    }

    public void orderDisplayCard(){
        ObservableList<menu> listData = FXCollections.observableArrayList();
        listData.addAll(orderCardDataList());

        int gridRow = 0;
        int gridColumn = 0;

        orderGridPane.getChildren().clear();
        orderGridPane.getRowConstraints().clear();
        orderGridPane.getColumnConstraints().clear();

        for(int i = 0; i < listData.size(); i++){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/orderProductCard.fxml"));
                StackPane root = loader.load();
                orderCardController orderC = loader.getController();
                orderC.setOrderData(listData.get(i));

                if(gridColumn == 3){
                    gridColumn = 0;
                    gridRow += 1;
                }
                orderGridPane.add(root, gridColumn++, gridRow);
                GridPane.setMargin(root, new Insets(1));
            } catch (Exception e){
                throw new RuntimeException("Có vấn đề xảy ra khi load giao diện thông qua file .fxml!");
            }
        }
    }

    public ObservableList<orders> orderDataList(){
        ObservableList<orders> listData = FXCollections.observableArrayList();

        try{
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            String orderFetch = "SELECT * FROM Orders";
            PreparedStatement statement = conn.prepareStatement(orderFetch);
            ResultSet resultSet = statement.executeQuery();

            orders orderData;
            while(resultSet.next()){
                orderData = new orders(resultSet.getString("ordID"),
                        resultSet.getString("ordName"),
                        resultSet.getInt("ordPrice"),
                        resultSet.getInt("ordQuantity"));
                listData.add(orderData);
            }

            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return listData;
    }

    public void orderShowData(){
        ObservableList<orders> ordDataList = orderDataList();

        orderTable_name.setCellValueFactory(new PropertyValueFactory<>("menuName"));
        orderTable_price.setCellValueFactory(new PropertyValueFactory<>("menuPrice"));
        orderTable_quantity.setCellValueFactory(new PropertyValueFactory<>("ordQuantity"));

        orderTable.setItems(ordDataList);
    }

    private int total;
    public void orderTotal(){
        orderGenerator();
        orderChange.setText(amount + "VNĐ");

        try{
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            String calcTotal = "SELECT SUM(ordPrice) FROM Orders WHERE cusID = ?";
            PreparedStatement statement = conn.prepareStatement(calcTotal);
            statement.setString(1, cusID);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                total = resultSet.getInt(1);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        orderTotal.setText(total + "VNĐ");
    }

    private int change;
    private int amount;
    public void orderChange(){
        try{
            amount = Integer.parseInt(orderAmount.getText());
            if(amount < total){
                orderChange.setText("");
                orderChange.setText("");
            }
            else{
                change = amount - total;
                orderChange.setText(change + "VNĐ");
            }
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
    }

    public void orderPay(){
        if(total == 0 || orderAmount.getText().isEmpty()){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Lỗi: Tạo hóa đơn thanh toán");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng thêm sản phẩm và nhập đầy đủ thông tin trước khi tạo hóa đơn thanh toán!");
            alert.showAndWait();
        }
        else{
            try{
                Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                String receiptInsert = "INSERT INTO Receipt (receiptID, cusID, total, date, empUsername) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(receiptInsert);
                statement.setString(1, "HĐ" + UUIDGenerator.shortenUUID());
                statement.setString(2, cusID);
                statement.setInt(3, total);
                statement.setString(4, LocalDate.now().toString());
                statement.setString(5, userData.username);

                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/customerInformation.fxml"));
                    StackPane root = loader.load();

                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("Thông tin khách hàng");
                    stage.setResizable(false);

                    Scene scene = new Scene(root, 420, 600);
                    stage.setScene(scene);
                    stage.setOnHidden(event -> {
                        try{
                            statement.executeUpdate();
                            conn.close();
                            orderClear();
                        } catch (SQLException e){
                            e.printStackTrace();
                        }
                    });
                    stage.show();
                } catch (Exception e){
                    throw new RuntimeException("Có vấn đề xảy ra khi load giao diện thông qua file .fxml!");
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void orderClear(){
        try{
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            String clearOrder = "DELETE FROM Orders";
            PreparedStatement statement = conn.prepareStatement(clearOrder);
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void orderRefresh(){
        orderDisplayCard();
        orderShowData();
        orderTotal();
    }





    //DASHBOARD: QUẢN LÝ THỰC ĐƠN
    private final String[] typeList = {"Thức ăn", "Thức uống", "Món ăn kèm"};
    public void menuTypeList(){
        List<String> typeL = new ArrayList<>();
        for(String data: typeList)
            typeL.add(data);
        ObservableList listData = FXCollections.observableArrayList(typeL);
        menuInput_type.setItems(listData);
    }

    public ObservableList<menu> menuDataList(){
        ObservableList<menu> listData = FXCollections.observableArrayList();

        try{
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            String menuFetch = "SELECT * FROM Menu";
            PreparedStatement statement = conn.prepareStatement(menuFetch);
            ResultSet resultSet = statement.executeQuery();

            menu menuData;
            while(resultSet.next()){
                menuData = new menu(resultSet.getString("menuID"),
                        resultSet.getString("menuName"),
                        resultSet.getString("menuType"),
                        resultSet.getInt("menuPrice"),
                        resultSet.getString("menuImage"));
                listData.add(menuData);
            }

            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return listData;
    }

    public void menuShowData(){
        ObservableList<menu> menuDataList = menuDataList();

        menuTable_ID.setCellValueFactory(new PropertyValueFactory<>("menuID"));
        menuTable_name.setCellValueFactory(new PropertyValueFactory<>("menuName"));
        menuTable_type.setCellValueFactory(new PropertyValueFactory<>("menuType"));
        menuTable_price.setCellValueFactory(new PropertyValueFactory<>("menuPrice"));

        menuTable.setItems(menuDataList);
    }

    public void menuSelectData(){
        menu menuData = menuTable.getSelectionModel().getSelectedItem();

        menuInput_name.setText(menuData.getMenuName());
        menuInput_type.getSelectionModel().select(menuData.getMenuType());
        menuInput_price.setText(String.valueOf(menuData.getMenuPrice()));
        userData.path = menuData.getMenuImage();
        menuInput_image.setImage(new Image(menuData.getMenuImage(), 360, 200, false, true));
    }

    public void menuImportButton(){
        FileChooser fileCh = new FileChooser();
        fileCh.getExtensionFilters().add(new ExtensionFilter("Chọn file hình ảnh...", "*png", "*jpg"));

        File file = fileCh.showOpenDialog(dashboard.getScene().getWindow());
        if(file != null){
            userData.path = file.getAbsolutePath();
            Image image = new Image(file.toURI().toString(), 360, 200, false, true);
            menuInput_image.setImage(image);
        }
    }

    public void menuAdd(){
        if(menuInput_name.getText().isEmpty()
                || menuInput_type.getSelectionModel().isEmpty()
                || menuInput_price.getText().isEmpty()
                || userData.path == null){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Lỗi: Nhập liệu thông tin sản phẩm");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ tất cả các thông tin!");
            alert.showAndWait();
        }
        else{
            try{
                Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                String menuInsert = "INSERT INTO Menu (menuID, menuName, menuType, menuPrice, menuImage) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(menuInsert);
                statement.setString(1, "SP" + UUIDGenerator.shortenUUID());
                statement.setString(2, menuInput_name.getText());
                statement.setString(3, menuInput_type.getSelectionModel().getSelectedItem());
                statement.setString(4, menuInput_price.getText());
                statement.setString(5, userData.path);

                statement.executeUpdate();
                conn.close();

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Thông báo: Thêm thông tin sản phẩm");
                alert.setHeaderText(null);
                alert.setContentText("Thêm thông tin sản phẩm thành công!");
                alert.showAndWait();

                menuShowData();
                menuClearInput();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void menuUpdate(){
        menu menuData = menuTable.getSelectionModel().getSelectedItem();

        if(menuData != null){
            if(menuInput_name.getText().isEmpty()
                || menuInput_type.getSelectionModel().isEmpty()
                || menuInput_price.getText().isEmpty()
                || userData.path == null){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi: Nhập liệu thông tin sản phẩm");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng điền đầy đủ tất cả các thông tin!");
                alert.showAndWait();
            }
            else{
                try{
                    Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                    String menuUpdate = "UPDATE Menu SET menuName = ?, menuType = ?, menuPrice = ?, menuImage= ? WHERE menuID = ?";
                    PreparedStatement statement = conn.prepareStatement(menuUpdate);
                    statement.setString(1, menuInput_name.getText());
                    statement.setString(2, menuInput_type.getSelectionModel().getSelectedItem());
                    statement.setString(3, menuInput_price.getText());
                    statement.setString(4, userData.path);
                    statement.setString(5, menuData.getMenuID());

                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Thông báo: Cập nhật thông tin sản phẩm");
                    alert.setHeaderText(null);
                    alert.setContentText("Bạn có chắc chắn muốn cập nhật lại thông tin của sản phẩm này không?");
                    Optional<ButtonType> option = alert.showAndWait();
                    if(option.get().equals(ButtonType.OK)){
                        statement.executeUpdate();
                        conn.close();

                        Alert success = new Alert(AlertType.INFORMATION);
                        success.setTitle("Thông báo: Cập nhật thông tin sản phẩm");
                        success.setHeaderText(null);
                        success.setContentText("Cập nhật thông tin sản phẩm thành công!");
                        success.showAndWait();

                        menuShowData();
                        menuClearInput();
                    }
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void menuDelete(){
        menu menuData = menuTable.getSelectionModel().getSelectedItem();

        if(menuData != null){
            try{
                Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                String menuDelete = "DELETE FROM Menu WHERE menuID = ?";
                PreparedStatement statement = conn.prepareStatement(menuDelete);
                statement.setString(1, menuData.getMenuID());

                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Thông báo: Xóa thông tin sản phẩm");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có chắc chắn muốn xóa thông tin của sản phẩm này không?");
                Optional<ButtonType> option = alert.showAndWait();
                if(option.get().equals(ButtonType.OK)){
                    statement.executeUpdate();
                    conn.close();

                    Alert success = new Alert(AlertType.INFORMATION);
                    success.setTitle("Thông báo: Xóa thông tin sản phẩm");
                    success.setHeaderText(null);
                    success.setContentText("Xóa thông tin sản phẩm thành công!");
                    success.showAndWait();

                    menuShowData();
                    menuClearInput();
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void menuClearInput(){
        menuInput_name.clear();
        menuInput_type.getSelectionModel().clearSelection();
        menuInput_price.clear();
        userData.path = null;
        menuInput_image.setImage(null);
    }





    //DASHBOARD: THÔNG TIN KHÁCH HÀNG
    public ObservableList<customer> customerDataList(){
        ObservableList<customer> listData = FXCollections.observableArrayList();

        try{
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            String customerFetch = "SELECT * FROM Customers";
            PreparedStatement statement = conn.prepareStatement(customerFetch);
            ResultSet resultSet = statement.executeQuery();

            customer cusData;
            while(resultSet.next()){
                cusData = new customer(resultSet.getString("cusName"),
                        resultSet.getString("cusGender"),
                        resultSet.getString("cusTel"),
                        resultSet.getString("cusID"));
                listData.add(cusData);
            }

            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return listData;
    }

    public void customerShowData(){
        ObservableList<customer> customerDataList = customerDataList();

        customerTable_ID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerTable_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        customerTable_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        customerTable_tel.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        customerTable.setItems(customerDataList);
    }

    public void customerSearch(){
        FilteredList<customer> listData = new FilteredList<>(customerDataList(), b -> true);

        customerSearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            listData.setPredicate(customer -> {
                if(newValue.isEmpty()
                        || newValue.isBlank()
                        || newValue == null){
                    return true;
                }
                String keyword = newValue.toLowerCase();
                if(customer.getFullName().toLowerCase().contains(keyword)){
                    return true;
                }
                else{
                    return false;
                }
            });
        });

        SortedList<customer> sortedData = new SortedList<>(listData);
        sortedData.comparatorProperty().bind(customerTable.comparatorProperty());
        customerTable.setItems(sortedData);
    }





    //DASHBOARD: LỊCH SỬ ĐƠN HÀNG
    public ObservableList<receipt> receiptDataList(){
        ObservableList<receipt> listData = FXCollections.observableArrayList();

        try{
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            String receiptFetch = "SELECT DISTINCT * FROM Receipt JOIN Customers ON Receipt.cusID = Customers.cusID";
            PreparedStatement statement = conn.prepareStatement(receiptFetch);
            ResultSet resultSet = statement.executeQuery();

            receipt receiptData;
            while(resultSet.next()){
                receiptData = new receipt(resultSet.getString("cusName"),
                        resultSet.getString("cusID"),
                        resultSet.getString("receiptID"),
                        resultSet.getDate("date"),
                        resultSet.getInt("total"),
                        resultSet.getString("empUsername"));
                listData.add(receiptData);
            }

            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return listData;
    }

    public void receiptShowData(){
        ObservableList<receipt> receiptDataList = receiptDataList();

        receiptTable_ID.setCellValueFactory(new PropertyValueFactory<>("receiptID"));
        receiptTable_cusID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        receiptTable_cusName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        receiptTable_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        receiptTable_total.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        receiptTable_username.setCellValueFactory(new PropertyValueFactory<>("empUsername"));

        receiptTable.setItems(receiptDataList);
    }

    public void receiptSearch(){
        FilteredList<receipt> listData = new FilteredList<>(receiptDataList(), b -> true);

        receiptSearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            listData.setPredicate(receipt -> {
                if(newValue.isEmpty()
                        || newValue.isBlank()
                        || newValue == null){
                    return true;
                }
                String keyword = newValue.toLowerCase();
                if(receipt.getFullName().toLowerCase().contains(keyword)){
                    return true;
                }
                else{
                    return false;
                }
            });
        });

        SortedList<receipt> sortedData = new SortedList<>(listData);
        sortedData.comparatorProperty().bind(receiptTable.comparatorProperty());
        receiptTable.setItems(sortedData);
    }





    //INITIALIZE
    @Override
    public void initialize(URL location, ResourceBundle resources){
        displayUsername();
        restrictNumericInput(employeeInput_tel);
        restrictNumericInput(menuInput_price);
        restrictNumericInput(orderAmount);
        restrictDatePicker(employeeInput_birth);

        reportTIncome();
        reportMIncome();
        reportIChart();
        reportCChart();

        employeeGenderList();
        employeePositionList();
        employeeShowData();

        menuTypeList();
        menuShowData();

        orderDisplayCard();
        orderShowData();
        orderTotal();

        customerShowData();
        customerSearch();

        receiptShowData();
        receiptSearch();
    }
}