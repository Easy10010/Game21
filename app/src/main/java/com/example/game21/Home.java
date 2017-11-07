package com.example.game21;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity implements View.OnClickListener {
    Button b1, b2, b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);

//        new database(this).reset();
    }

    @Override
    public void onClick(View view) {
        if (view == b1) {
            Intent intent = new Intent(this, Rules.class);
            startActivity(intent);
        }
        if (view == b2) {
            Intent intent = new Intent(this, Game.class);
            startActivity(intent);
        }
        if (view == b3) {
            finish();
        }
        if (view.getId() == R.id.button) {
            Intent intent = new Intent(this, History.class);
            startActivity(intent);
        }
    }
}
