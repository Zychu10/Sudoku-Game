package pl.first.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LanguageTest {

    Language_en En = new Language_en();
    Language_pl Pl = new Language_pl();

    @Test
    public void language() {
        assertNotNull(En.getContents());
        assertNotNull(Pl.getContents());
    }
}
