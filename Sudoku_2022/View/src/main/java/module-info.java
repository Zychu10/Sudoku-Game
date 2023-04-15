module ViewProject {
    requires javafx.controls;
    requires javafx.fxml;
    requires pl.first.sudoku;
    requires org.apache.commons.lang3;
    requires org.jetbrains.annotations;
    requires org.slf4j;


    opens pl.comp.viewproject to javafx.fxml;
    exports pl.comp.viewproject;
}