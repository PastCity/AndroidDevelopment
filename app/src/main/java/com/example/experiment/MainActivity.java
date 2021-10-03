package com.example.experiment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.experiment.ex1.CalculatorActivity;

public class MainActivity extends AppCompatActivity {

    Button btnEX1,btnEX2,btnEX3,btnEX4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEX1 = findViewById(R.id.ex1);
        btnEX2 = findViewById(R.id.ex2);
        btnEX3 = findViewById(R.id.ex3);
        btnEX4 = findViewById(R.id.ex4);

        btnEX1.setOnClickListener(new onClick());
        btnEX2.setOnClickListener(new onClick());
        btnEX3.setOnClickListener(new onClick());
        btnEX4.setOnClickListener(new onClick());
    }

    class onClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = null;
            switch (view.getId()){
                case  R.id.ex1:
                    intent = new Intent(MainActivity.this, CalculatorActivity.class);
                    break;
                default:
                    intent = new Intent(MainActivity.this, MainActivity.class);
            }
            startActivity(intent);
        }
    }
}