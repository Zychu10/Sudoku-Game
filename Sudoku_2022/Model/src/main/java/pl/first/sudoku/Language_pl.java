package pl.first.sudoku;

import java.io.Serializable;
import java.util.ListResourceBundle;

public class Language_pl extends ListResourceBundle implements Serializable {

    /*------------------------ METHODS REGION ------------------------*/
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"_wrongFieldValue", "Musi być <1,9>"},
                {"_wrongLength", "Długość tablicy musi być równa 9"},
                {"_loadWarning", "Nie udało się odczytać pliku."},
                {"_writeWarning", "Nie udało się zapisać pliku."},
                {"_compareWarning", "Nie można porównać wartości null."},
                {"_noMethodWarning", "Nie można znaleźć potrzebnej metody."},
                {"_closeWarning", "Nie można usunąć pliku który nie istnieje."},
                {"_cloneWarning", "Nie można sklonować tego obiektu."},
                {"_loadBaseWarning", "Nie można odczytac danych z bazy."},
                {"_insertFieldWarning", "Problem ze wstawieniem fielda do bazy."},
                {"_insertBoardWarning", "Problem ze wstawieniem boarda do bazy."},
                {"_createTableWarning", "Problem ze stworzeniem tablicy."},
                {"_notClassWarning", "Brak sterownika."},
                {"_connectionWarning", "Problem z otwarciem połączenia."},
                {"_closeConectWarning", "Problem z zamknięciem połączenia."},
                {"_nameFromBaseWarning", "Problem z załadowniem nazw."},
                {"_noMethodException", "Nie mogę znaleźć konkretnej metody."},
                {"_deleteBaseWarning", "Nie można usunąć danych z bazy."}
        };
    }
}
