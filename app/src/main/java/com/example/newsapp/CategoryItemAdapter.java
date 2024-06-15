package com.example.newsapp;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

public class CategoryItemAdapter extends ArrayAdapter<CategoryItemDataModel> {
    private final List<CategoryItemDataModel> itemList;

    public CategoryItemAdapter(Context context, List<CategoryItemDataModel> itemList) {
        super(context, 0, itemList);
        this.itemList = itemList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_list_item_layout, parent, false);
        }

        CategoryItemDataModel item = getItem(position);
        CheckBox checkBox = convertView.findViewById(R.id.check_box);
        checkBox.setText(item.getName());
        checkBox.setChecked(item.isSelected());

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> item.setSelected(isChecked));

        return convertView;
    }

    public List<CategoryItemDataModel> getSelectedItems() {
        List<CategoryItemDataModel> selectedItems = new ArrayList<>();
        for (CategoryItemDataModel item : itemList) {
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }
        return selectedItems;
    }
}
