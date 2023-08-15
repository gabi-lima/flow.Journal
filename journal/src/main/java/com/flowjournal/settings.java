package com.flowjournal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;

public class settings {

    @FXML
    private ToolBar barra;

    @FXML
    private TextField inputMinutos;

    @FXML
    private Button btnConfirmar;

    @FXML
    private void salvarBD() {
        String minutos = inputMinutos.getText();
        int minutosInt = Integer.parseInt(minutos);

        try {

            String url = "jdbc:sqlite:flow.db";
            Connection connection = DriverManager.getConnection(url);
            String sql = "INSERT INTO settings (timer) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, minutosInt);
            System.out.println(minutosInt);

            // Faz a pesquisa
            preparedStatement.executeUpdate();
            System.out.println("Data saved to the database!");

        } catch (SQLException e) {
            System.out.println("Failed to save data to the database!");
            e.printStackTrace();
        }
        Stage currentStage = (Stage) btnConfirmar.getScene().getWindow();
        currentStage.close();
    }

}
