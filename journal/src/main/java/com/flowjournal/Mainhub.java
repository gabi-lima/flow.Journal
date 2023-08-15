package com.flowjournal;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Mainhub {

    @FXML
    private Button BTN_arquivos;

    @FXML
    private Button BTN_fechar;

    @FXML
    private Button BTN_novo;

    @FXML
    private VBox VBOXhub;

    @FXML
    private void fecharApp() {
        Platform.exit();

    }

    @FXML
    private void iniciarPopup() {
        Popup pop = new Popup();
        pop.exibirPop();

    }

    @FXML
    private ToolBar barra;

    @FXML
    private void abrirDiario() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("diario.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        Stage diario = new Stage();

        diario.setTitle("flow.Journal");
        diario.setResizable(false);

        diario.setScene(scene);
        diario.show();
    }

    @FXML
    private void abrirSettings() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settings.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        Stage settings = new Stage();

        settings.setScene(scene);
        settings.show();

    }
}
