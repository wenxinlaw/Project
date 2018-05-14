package com.sp.wow_x;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Button bHome = (Button) findViewById(R.id.bHome);
        TextView NumP = (TextView) findViewById(R.id.tvNumP);
        TextView Results = (TextView) findViewById(R.id.tvResults);
        TextView Points = (TextView) findViewById(R.id.tvPoints);

        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Results.this, UserAreaActivity.class);
                startActivity(intent);
            }
        });
    }
}
