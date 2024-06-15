package com.example.newsapp;
import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class CategoryBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private List<CategoryItemDataModel> itemList;
    private CategoryItemAdapter itemAdapter;

    public OnDismissListener onDismissListener;

    public interface OnDismissListener {
        void onDismiss(List<CategoryItemDataModel> selectedItems);
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_bottom_sheet, container, false);

        itemList = new ArrayList<>();
        itemList.add(new CategoryItemDataModel("Business","business" , false));
        itemList.add(new CategoryItemDataModel("Crime","crime" , false));
        itemList.add(new CategoryItemDataModel("Domestic","domestic" , false));
        itemList.add(new CategoryItemDataModel("Education","education" , false));
        itemList.add(new CategoryItemDataModel("Entertainment","entertainment" , false));
        itemList.add(new CategoryItemDataModel("Environment","environment" , false));
        itemList.add(new CategoryItemDataModel("Food","food" , false));
        itemList.add(new CategoryItemDataModel("Health","health" , false));
        itemList.add(new CategoryItemDataModel("Lifestyle","lifestyle" , false));
        itemList.add(new CategoryItemDataModel("Other","other" , false));
        itemList.add(new CategoryItemDataModel("Politics","politics" , false));
        itemList.add(new CategoryItemDataModel("Science","science" , false));
        itemList.add(new CategoryItemDataModel("Sports","sports" , false));
        itemList.add(new CategoryItemDataModel("Technology","technology" , false));
        itemList.add(new CategoryItemDataModel("Top","top" , false));
        itemList.add(new CategoryItemDataModel("Tourism","tourism" , false));
        itemList.add(new CategoryItemDataModel("World","world" , false));

        ListView listView = view.findViewById(R.id.category_list_view);
        itemAdapter = new CategoryItemAdapter(getContext(), itemList);
        listView.setAdapter(itemAdapter);

        return view;
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
}
