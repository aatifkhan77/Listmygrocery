package com.example.groceryapp;

import com.example.groceryapp.Item;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText itemEditText, quantityEditText, priceEditText;
    private Button addButton, deleteButton;
    private ListView itemListView;
    private TextView totalTextView;
    private ArrayList<Item> itemList = new ArrayList<>();
    private ArrayAdapter<Item> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemEditText = findViewById(R.id.itemEditText);
        quantityEditText = findViewById(R.id.quantityEditText);
        priceEditText = findViewById(R.id.priceEditText);
        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);
        itemListView = findViewById(R.id.itemListView);
        totalTextView = findViewById(R.id.totalTextView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        itemListView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = itemEditText.getText().toString();
                int quantity = Integer.parseInt(quantityEditText.getText().toString());
                double price = Double.parseDouble(priceEditText.getText().toString());

                Item newItem = new Item(item, quantity, price);
                itemList.add(newItem);
                adapter.notifyDataSetChanged();

                updateTotal();
            }
        });

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item selectedItem = itemList.get(position);
                itemList.remove(selectedItem);
                adapter.notifyDataSetChanged();

                updateTotal();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.clear();
                adapter.notifyDataSetChanged();

                updateTotal();
            }
        });
    }

    private void updateTotal() {
        double total = 0;
        for (Item item : itemList) {
            total += item.getPrice() * item.getQuantity();
        }

        String totalString = String.format("Total: \u20B9%.2f", total);
        totalTextView.setText(totalString);
    }
}
