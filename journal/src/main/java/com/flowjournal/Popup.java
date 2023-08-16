package com.flowjournal;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Popup {

    @FXML
    private Button BTN_addpopup;

    @FXML
    private TextArea FIELD_popup;

    @FXML
    private Label LABEL_popup;

    @FXML
    private VBox VBOX_popup;

    @FXML
    private Label labeldata;

    @FXML
    private Label LABEL_horas;

    @FXML
    public void initialize() {
        updateTimeLabels();
        // Update time labels every second
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTimeLabels()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        carregarConfig();

    }

    private int carregarConfig() {
        int latest_id = 0;
        int timer = 60;
        try {
            String url = "jdbc:sqlite:flow.db";
            Connection connection = DriverManager.getConnection(url);
            String sqlID = "SELECT MAX(id) AS latest_id FROM settings";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlID);
            preparedStatement.executeQuery();
            int latestId = preparedStatement.executeQuery().getInt("latest_id");

            String recordQuery = "SELECT * FROM settings WHERE id = ?";
            PreparedStatement recordStatement = connection.prepareStatement(recordQuery);
            recordStatement.setInt(1, latestId);

            ResultSet recordResultSet = recordStatement.executeQuery();
            timer = recordStatement.executeQuery().getInt("timer");

            System.out.println("Data fetch from database!");

            preparedStatement.close();
            recordStatement.close();
            recordResultSet.close();

        } catch (SQLException e) {
            System.out.println("Failed to fetch data to the database!");
            e.printStackTrace();
        }
        return timer;

    }

    @FXML
    private void salvarBD() {
        String text = FIELD_popup.getText();

        if (!text.isEmpty()) {
            LocalDateTime now = LocalDateTime.now();

            // Formata a data e a hora usando DateTimeFormatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);

            try {
                String url = "jdbc:sqlite:flow.db";
                Connection connection = DriverManager.getConnection(url);
                String sql = "INSERT INTO popup_data (text_content, date_time) VALUES (?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, text);
                preparedStatement.setString(2, formattedDateTime);

                // Faz a pesquisa
                preparedStatement.executeUpdate();
                System.out.println("Data saved to the database!");

            } catch (SQLException e) {
                System.out.println("Failed to save data to the database!");
                e.printStackTrace();
            }
            Stage currentStage = (Stage) BTN_addpopup.getScene().getWindow();
            currentStage.close();

        }

    }

    private void updateTimeLabels() {
        LocalDateTime now = LocalDateTime.now();

        // Formata a data e a hora usando DateTimeFormatter
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Define o texto das labels com a data e hora formatadas
        labeldata.setText(now.format(dateFormatter));
        LABEL_horas.setText(now.format(timeFormatter));
    }

    public void exibirPop() {
        int timer = carregarConfig();
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("popup.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            Stage popup = new Stage();
            popup.setResizable(false);
            popup.setScene(scene);
            popup.setTitle("flow.Journal");
            popup.show();

            PauseTransition delay = new PauseTransition(Duration.minutes(timer));
            delay.setOnFinished(Event -> exibirPop());
            delay.play();
            System.out.println(timer);

        } catch (IOException e) {
            System.out.println("Erro ao abrir POP-UP!");
            e.printStackTrace();
        }
    }

}
