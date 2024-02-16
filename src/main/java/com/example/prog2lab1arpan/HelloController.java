package com.example.prog2lab1arpan;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    public TextField userInput;
    @FXML
    public PasswordField passwordInput;
    @FXML
    public Label errorMessage;
    public int logCount;
    @FXML
    public void onHelloButtonClick(ActionEvent actionEvent) {
        String userNameText = userInput.getText();
        String passwordText = passwordInput.getText();
        if (!validator(userNameText, passwordText)) {
            errorMessage.setText("Please Provide Username or Password.");
        } else {
            errorMessage.setText(""); // Clear error message
            if (logCount==0) {
                errorMessage.setText("Sorry, Your Account is Locked!!!");
            }
            checkCredentials(userInput.getText(), passwordInput.getText());
        }
    }

    public boolean validator(String userName, String password) {
        return userName != null && !userName.isEmpty() && password != null && !password.isEmpty();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchInDb();
    }
    private ArrayList<logincred> list = new ArrayList<>();
    public void searchInDb() {
        list.clear(); // Clear the ArrayList before adding new items
        // establish a database connection
        String jdbcUrl = "jdbc:mysql://localhost:3306/user_tbl";
        String dbUser = "root";
        String dbPassword = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            // Execute a SQL query to retrieve data from the database
            String query = "SELECT * FROM `logincred`";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            // populate the ArrayList with data from the database
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String user = resultSet.getString("user");
                String password = resultSet.getString(("password"));
                System.out.println(id+user+password);
                // add the data to the ArrayList
                list.add(new logincred(id, user, password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean checkCredentials(String userName, String password) {
        for (logincred cred : list) {
            if (cred.getUser().equals(userName) && cred.getPassword().equals(password)) {
                errorMessage.setText("Success!!!");
                logCount=0;
                return true; // found matching credentials
            }
        }
        logCount++;
        errorMessage.setText("You can not log in. You do not exist in the database");
        return false; // no matching credentials found
    }
}
