package pl.first.sudoku;

import com.google.common.base.MoreObjects;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Objects;
import java.util.ResourceBundle;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.first.sudoku.exceptions.BadFieldvalueException;
import pl.first.sudoku.exceptions.CantCloneException;
import pl.first.sudoku.exceptions.CompareNullException;

public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {

    private int value;
    private boolean isEditable = false;
    private final PropertyChangeSupport changeSupport;
    private final Logger log = LoggerFactory.getLogger(SudokuField.class);
    private ResourceBundle listBundle = ResourceBundle.getBundle("pl.first.sudoku.Language");

    public SudokuField() {
        this.changeSupport = new PropertyChangeSupport(this);
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable() {
        isEditable = true;
    }

    public int getFieldValue() {
        return this.value;
    }

    public void setFieldValue(int value) {
        int oldVal = this.value;
        this.value = value;
        this.changeSupport.firePropertyChange("value", oldVal, value);
    }

    public String getJajo() {
        return String.valueOf(value);
    }

    public void setJajo(String s) {
        try {
            try {
                if (s == "") {
                    this.value = 0;
                }
                this.value = Integer.parseInt(s);
            } catch (Exception e) {
                throw new BadFieldvalueException(listBundle
                        .getObject("_wrongFieldValue").toString(), e);
            }
        } catch (BadFieldvalueException e) {
            if (log.isErrorEnabled() && !Objects.equals(s, "")) {

                log.error(e.getMessage());
            }
        }

    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(int index) {
        changeSupport.removePropertyChangeListener(
                changeSupport.getPropertyChangeListeners()[index]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SudokuField that)) {
            return false;
        }

        return new EqualsBuilder().append(value, that.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17,
                37).append(value).toHashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .add("changeSupport", changeSupport)
                .add("listBundle", listBundle)
                .add("isEditable", isEditable)
                .toString();
    }

    @Override
    public int compareTo(@NotNull SudokuField o) throws CompareNullException {
        try {
            try {
                return Integer.compare(this.getFieldValue(), o.getFieldValue());
            } catch (NullPointerException e) {
                throw new CompareNullException(listBundle.getObject("_compareWarning").toString());
            }
        } catch (CompareNullException e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            throw e;
        }


    }

    @Override
    protected SudokuField clone() throws CantCloneException {
        try {
            try {
                return (SudokuField) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new CantCloneException(listBundle.getObject("_cloneWarning").toString());
            }
        } catch (CantCloneException e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            throw e;
        }
    }
}
