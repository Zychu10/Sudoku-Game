module pl.first.sudoku {
    requires org.jetbrains.annotations;
    requires org.apache.commons.lang3;
    requires java.desktop;
    requires com.google.common;
    requires org.slf4j;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens pl.first.sudoku; // Open module for reflective access in tests


    exports pl.first.sudoku;
    exports pl.first.sudoku.exceptions;
}