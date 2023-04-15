package pl.comp.viewproject;

import java.util.ListResourceBundle;

public class AuthorsBundle extends ListResourceBundle {

    private final Object[][] resources = {
            {"autor_1", "Sebastian Zych"}, {"autor_2", "Maciej Raczyński"}
    };

    @Override
    protected Object[][] getContents() {
        return resources;
    }
}

