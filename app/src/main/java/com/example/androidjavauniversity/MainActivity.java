package com.example.androidjavauniversity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnRead = findViewById(R.id.btn_read);
        Button btnAdd = findViewById(R.id.btn_add);
        TableLayout tableLayout = findViewById(R.id.tbl_layout);

        DatabaseHelper dbHelper = new DatabaseHelper(this);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addUser("علی", "ali@example.com");
                dbHelper.addUser("زهرا", "zahra@example.com");
            }
        });


        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tableLayout.removeAllViews();

                for (User user : dbHelper.readUsers()) {

                    TableRow tableRow = new TableRow(MainActivity.this);

                    TextView idTextView = new TextView(MainActivity.this);
                    idTextView.setText(String.valueOf(user.getId()));
                    tableRow.addView(idTextView);

                    TextView nameTextView = new TextView(MainActivity.this);
                    nameTextView.setText(user.getName());
                    tableRow.addView(nameTextView);

                    TextView emailTextView = new TextView(MainActivity.this);
                    emailTextView.setText(user.getEmail());
                    tableRow.addView(emailTextView);

                    tableLayout.addView(tableRow);
                    
                }
            }
        });

        dbHelper.deleteUser(1);
    }
}
