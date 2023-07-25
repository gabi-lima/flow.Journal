module com.flowjournal {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.dustinredmond.fxtrayicon;
    requires java.sql;

    opens com.flowjournal to javafx.fxml;

    exports com.flowjournal;
}
