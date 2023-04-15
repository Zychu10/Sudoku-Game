package pl.first.sudoku;

import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.first.sudoku.exceptions.CantCloneException;


public class SudokuBox extends SudokuFieldGroup {

    public SudokuBox(SudokuField[] sudokuFields) {
        super(sudokuFields);
    }

    private final Logger log = LoggerFactory.getLogger(SudokuBoard.class);

    @Override
    protected SudokuBox clone() throws CantCloneException {
        try {
            try {
                SudokuBox suBox = new SudokuBox(new SudokuField[SudokuFieldGroup.SIZE]);
                for (int i = 0; i < 9;i++) {
                    suBox.sudokuFields.set(i, this.sudokuFields.get(i).clone());
                }
                return suBox;
            } catch (CloneNotSupportedException e) {
                throw new CantCloneException(ResourceBundle.getBundle("Language")
                        .getString("_cloneWarning"));
            }

        } catch (CloneNotSupportedException e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            throw e;
        }


    }
}
