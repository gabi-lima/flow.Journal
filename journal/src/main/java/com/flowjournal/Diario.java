package com.flowjournal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Diario {
    @FXML
    private void atualizarBD() {
        try {
            String url = "jdbc:sqlite:flow.db";
            Connection connection = DriverManager.getConnection(url);

            String sql = "SELECT text_content, date_time FROM popup_data ORDER BY date_time DESC";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            StringBuilder stringBuilder = new StringBuilder();

            while (resultSet.next()) {
                String textContent = resultSet.getString("text_content");
                String dateTimeString = resultSet.getString("date_time");

                // Formatar a data e hora usando DateTimeFormatter
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

                // Formatar novamente para exibição na label
                DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                String formattedDateTime = dateTime.format(displayFormatter);

                // Adicionar os dados ao StringBuilder
                stringBuilder.append(formattedDateTime).append(" - ").append(textContent).append("\n");
            }

            if (stringBuilder.length() > 0) {
                diarioTexto.setText(stringBuilder.toString());
            } else {
                diarioTexto.setText("Nenhum dado encontrado no banco de dados.");
            }

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Erro ao buscar dados no banco de dados!");
            e.printStackTrace();
        }

    }

    @FXML
    private VBox VBOXdiario;

    @FXML
    private Text diarioTexto;

    @FXML
    private void initialize() {
        atualizarBD();

    }

}
