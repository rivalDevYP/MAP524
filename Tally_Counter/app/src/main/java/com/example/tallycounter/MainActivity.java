package com.example.tallycounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView counter;
    private int count ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        count = 0;
        counter = findViewById(R.id.counter);

        Button incrementButton = findViewById(R.id.increment);
        Button decrementButton = findViewById(R.id.decrement);
        Button resetButton = findViewById(R.id.resetButton);

        incrementButton.setOnClickListener(onIncrement);
        decrementButton.setOnClickListener(onDecrement);
        resetButton.setOnClickListener(onReset);
    }

    private View.OnClickListener onIncrement = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            count++;
            setCount();
        }
    };

    private View.OnClickListener onDecrement = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (count >= 1)
                count--;
            setCount();
        }
    };

    private View.OnClickListener onReset = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            count = 0;
            setCount();
        }
    };

    private void setCount()
    {
        counter.setText(Integer.toString(count));
    }
}
