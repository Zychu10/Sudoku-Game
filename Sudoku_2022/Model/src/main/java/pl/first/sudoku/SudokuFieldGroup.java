package pl.first.sudoku;

import com.google.common.base.MoreObjects;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.first.sudoku.exceptions.BadGroupSizeException;

public abstract class SudokuFieldGroup implements Serializable, Cloneable {

    public static final int SIZE = 9;
    protected List<SudokuField> sudokuFields;
    private final Logger log = LoggerFactory.getLogger(SudokuFieldGroup.class);
    private ResourceBundle listBundle = ResourceBundle.getBundle("pl.first.sudoku.Language");


    public SudokuFieldGroup(SudokuField[] field) throws BadGroupSizeException {
        if (field.length != 9) {
            try {
                throw new BadGroupSizeException(listBundle.getObject("_wrongLength").toString());
            } catch (BadGroupSizeException e) {
                log.error(e.getMessage());
                throw e;
            }
        }
        sudokuFields = Arrays.asList(new SudokuField[SIZE]);
        for (int i = 0; i < SIZE; i++) {
            sudokuFields.set(i, field[i]);
        }
    }

    public boolean verify() {
        for (int i = 0; i < SudokuFieldGroup.SIZE - 1; i++) {
            if (sudokuFields.get(i).getFieldValue() == 0) {
                return true;
            }
            for (int i2 = i + 1; i2 < SudokuFieldGroup.SIZE; i2++) {
                if (sudokuFields.get(i).getFieldValue() == sudokuFields.get(i2).getFieldValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SudokuFieldGroup that)) {
            return false;
        }

        return new EqualsBuilder().append(sudokuFields, that.sudokuFields).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(sudokuFields).toHashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("sudokuFields", sudokuFields)
                .toString();
    }

}

