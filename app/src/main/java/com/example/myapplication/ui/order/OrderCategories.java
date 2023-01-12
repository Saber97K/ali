package com.example.myapplication.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.databinding.FragmentOrderCategoriesBinding;
import com.example.myapplication.ui.Utils.CategoryAdapter;
import com.example.myapplication.ui.Utils.CategoryManage;
import com.example.myapplication.ui.Utils.OrdersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

public class OrderCategories extends Fragment {

    private FragmentOrderCategoriesBinding binding;
    private ListView noteListView;
    private View root;
    private int currentUser;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrderCategoriesBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        CategoryManage.categoryManages.clear();
        noteListView = binding.categoriesListView;
        currentUser = ((Session) this.getActivity().getApplication()).getSomeVariable();


        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(getActivity());
        sqLiteManager.populateCategoryListArray();
        if(CategoryManage.categoryManages.size() == 0 ){
            ToDBInitialSettings();
            sqLiteManager.populateCategoryListArray();
        }
        setCategoryAdapter();
        setOnClickListener();
        return root;
    }
    private void setCategoryAdapter( ) {
        CategoryAdapter noteAdapter = new CategoryAdapter(getActivity().getApplicationContext(), CategoryManage.categoryManages);
        noteListView.setAdapter(noteAdapter);
    }
    private void ToDBInitialSettings() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(getActivity());
        CategoryManage categoryManage1 = new CategoryManage(0,"Logistic");
        CategoryManage categoryManage2 = new CategoryManage(1,"Admin");
        CategoryManage categoryManage3 = new CategoryManage(2,"Marketing");
        CategoryManage categoryManage4 = new CategoryManage(3,"Operations");
        CategoryManage categoryManage5 = new CategoryManage(4,"Entertainment");
        CategoryManage categoryManage6 = new CategoryManage(5,"Sports");
        CategoryManage categoryManage7 = new CategoryManage(6,"Others");
        sqLiteManager.addCategoryToDatabase(categoryManage1);
        sqLiteManager.addCategoryToDatabase(categoryManage2);
        sqLiteManager.addCategoryToDatabase(categoryManage3);
        sqLiteManager.addCategoryToDatabase(categoryManage4);
        sqLiteManager.addCategoryToDatabase(categoryManage5);
        sqLiteManager.addCategoryToDatabase(categoryManage6);
        sqLiteManager.addCategoryToDatabase(categoryManage7);
    }
      private void setOnClickListener() {
          noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
          {
              @Override
              public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
              {
                  CategoryManage selectedNode = (CategoryManage) noteListView.getItemAtPosition(position);
                  Intent editNodeIntent = new Intent(getActivity().getApplicationContext(), OrderCreate.class);
                  editNodeIntent.putExtra("category", selectedNode.getText());

                  startActivity(editNodeIntent);
              }
          });
      }

    @Override
    public void onResume()
    {
        super.onResume();
        setCategoryAdapter();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}