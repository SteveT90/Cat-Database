package com.steve.crud;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;


public class Controller {

    @FXML
    private TextField textFieldCatsName;

    @FXML
    private DatePicker textFieldDOB;

    @FXML
    private TextField textFieldBreed;

    @FXML
    private TextField textFieldWeight;

    @FXML
    private TextField textFieldColor;

    @FXML
    private TableView<Cat> tableViewCats;

    @FXML
    private TableColumn<Cat, String> columnCatsName;

    @FXML
    private TableColumn<Cat, Date> columnDOB;

    @FXML
    private TableColumn<Cat, String> columnBreed;

    @FXML
    private TableColumn<Cat, Integer> columnWeight;

    @FXML
    private TableColumn<Cat, String> columnColor;

    @FXML
    private Button buttonInsert;

    @FXML
    private Button buttonUpdate;

    @FXML
    private Button buttonDelete;


    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == buttonInsert) {
            insertRecord();
        }
    }

    @FXML
    public void initialize() {
        showCats();
        textFieldDOB.getEditor().setDisable(true);
    }

    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cats", "root", "root");
            return conn;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }

    public ObservableList<Cat> getCatsList() {
        ObservableList<Cat> catsList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM cats";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Cat cats;
            while (rs.next()) {
                cats = new Cat(rs.getString("name"), rs.getDate("dob"),
                        rs.getString("breed"), rs.getInt("weight"),
                        rs.getString("color"));
                catsList.add(cats);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Comparator<Cat> catComparator = Comparator.comparing(Cat::getName, String.CASE_INSENSITIVE_ORDER);
        catsList.sort(catComparator);
        return catsList;
    }

    public void showCats() {
        ObservableList<Cat> list = getCatsList();

        columnCatsName.setCellValueFactory(new PropertyValueFactory<Cat, String>("name"));
        columnDOB.setCellValueFactory(new PropertyValueFactory<Cat, Date>("dob"));
        columnBreed.setCellValueFactory(new PropertyValueFactory<Cat, String>("breed"));
        columnWeight.setCellValueFactory(new PropertyValueFactory<Cat, Integer>("weight"));
        columnColor.setCellValueFactory(new PropertyValueFactory<Cat, String>("color"));

        tableViewCats.setItems(list);
    }

    private void insertRecord() {
        // Use single quotes for mySQL statements.
        Date getDateFromPicker = java.sql.Date.valueOf(textFieldDOB.getValue());
        String query = "INSERT INTO cats VALUES ('" + UUID.randomUUID().toString() + "','" + textFieldCatsName.getText() + "','" +
                getDateFromPicker + "','" + textFieldBreed.getText() + "','" + textFieldWeight.getText() + "','" +
                textFieldColor.getText() + "')";
        executeQuery(query);
        showCats();
        System.out.println(query);
    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
