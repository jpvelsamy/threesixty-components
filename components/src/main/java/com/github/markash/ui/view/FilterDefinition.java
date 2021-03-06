package com.github.markash.ui.view;

import java.util.ArrayList;
import java.util.List;

public class FilterDefinition {
    private String property;
    private String heading;
    private List<String> options = new ArrayList<>();

    public FilterDefinition(
            final String heading,
            final String property) {

        this.property = property;
        this.heading = heading;
    }

    public String getProperty() { return property; }
    public String getHeading() { return heading; }
    public List<String> getOptions() { return options; }
    public boolean hasOptions() { return this.options != null && this.options.size() > 0; }

}
