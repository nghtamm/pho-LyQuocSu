module phoLyQuocSu{
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;

    opens app;
    opens controllers;
    opens models;
    opens helpers;
}