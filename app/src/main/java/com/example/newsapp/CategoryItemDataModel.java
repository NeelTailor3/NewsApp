package com.example.newsapp;

public class CategoryItemDataModel {
    private String name;
    private String value;
    private boolean isSelected;

    public CategoryItemDataModel(String name,String value, boolean isSelected) {
        this.name = name;
        this.value = value;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
