package pl.first.sudoku;

import java.io.Serializable;
import java.util.ListResourceBundle;

public class Language_en extends ListResourceBundle implements Serializable {

    /*------------------------ METHODS REGION ------------------------*/
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"_wrongFieldValue", "Must be <1,9>"},
                {"_wrongLength", "Array length must be equals 9"},
                {"_loadWarning", "Can't load this file."},
                {"_writeWarning", "Can't write file."},
                {"_compareWarning", "Can't compare null."},
                {"_noMethodWarning", "Can't find required method."},
                {"_closeWarning", "Can't delete file which doesn't exist."},
                {"_cloneWarning", "Can't clone this object."},
                {"_loadBaseWarning", "Can't read data from base."},
                {"_insertFieldWarning", "Problem with inserting a field into the database."},
                {"_insertBoardWarning", "Problem with inserting a board into the database."},
                {"_createTableWarning", "Problem with create a table"},
                {"_notClassWarning", "No driver."},
                {"_connectionWarning", "Problem opening connection."},
                {"_closeConectWarning", "Problem closing connection."},
                {"_nameFromBaseWarning", "Problem with load names."},
                {"_noMethodException", "Cannot found particular method."},
                {"_deleteBaseWarning", "Cannot delete data from base."}
        };
    }
}