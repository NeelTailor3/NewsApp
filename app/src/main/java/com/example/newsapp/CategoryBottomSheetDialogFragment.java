package com.example.newsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class CategoryBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private static final String PREF_NAME = "SelectedCategory";
    public OnDismissListener onDismissListener;
    private List<CategoryItemDataModel> itemList;
    private CategoryItemAdapter itemAdapter;

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_bottom_sheet, container, true);
        String selectedCategories = getSelectedCategories();
        itemList = new ArrayList<>();

        itemList.add(new CategoryItemDataModel("Business", "business", isChecked(selectedCategories, "business")));
        itemList.add(new CategoryItemDataModel("Crime", "crime", isChecked(selectedCategories, "crime")));
        itemList.add(new CategoryItemDataModel("Domestic", "domestic", isChecked(selectedCategories, "domestic")));
        itemList.add(new CategoryItemDataModel("Education", "education", isChecked(selectedCategories, "education")));
        itemList.add(new CategoryItemDataModel("Entertainment", "entertainment", isChecked(selectedCategories, "entertainment")));
        itemList.add(new CategoryItemDataModel("Environment", "environment", isChecked(selectedCategories, "environment")));
        itemList.add(new CategoryItemDataModel("Food", "food", isChecked(selectedCategories, "food")));
        itemList.add(new CategoryItemDataModel("Health", "health", isChecked(selectedCategories, "health")));
        itemList.add(new CategoryItemDataModel("Lifestyle", "lifestyle", isChecked(selectedCategories, "lifestyle")));
        itemList.add(new CategoryItemDataModel("Other", "other", isChecked(selectedCategories, "other")));
        itemList.add(new CategoryItemDataModel("Politics", "politics", isChecked(selectedCategories, "politics")));
        itemList.add(new CategoryItemDataModel("Science", "science", isChecked(selectedCategories, "science")));
        itemList.add(new CategoryItemDataModel("Sports", "sports", isChecked(selectedCategories, "sports")));
        itemList.add(new CategoryItemDataModel("Technology", "technology", isChecked(selectedCategories, "technology")));
        itemList.add(new CategoryItemDataModel("Top", "top", isChecked(selectedCategories, "top")));
        itemList.add(new CategoryItemDataModel("Tourism", "tourism", isChecked(selectedCategories, "tourism")));
        itemList.add(new CategoryItemDataModel("World", "world", isChecked(selectedCategories, "world")));

        ListView listView = view.findViewById(R.id.category_list_view);
        itemAdapter = new CategoryItemAdapter(getContext(), itemList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategoryItemDataModel item = itemList.get(position);
                item.setSelected(!item.isSelected());
                itemAdapter.notifyDataSetChanged();
            }
        });
        listView.setAdapter(itemAdapter);

        return view;
    }

    private boolean isChecked(String categories, String category) {
        return categories.contains(category);
    }

    private String getSelectedCategories() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("selected_categories", "");
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(getSelectedItems());
        }
    }

    public List<CategoryItemDataModel> getSelectedItems() {
        return itemAdapter.getSelectedItems();
    }

    public interface OnDismissListener {
        void onDismiss(List<CategoryItemDataModel> selectedItems);
    }
}
