package com.example.game21;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch(view.getId()){
            case R.id.button:
                intent = new Intent(this, History.class);
                startActivity(intent);
                break;
            case R.id.button1:
                intent = new Intent(this, Rules.class);
                startActivity(intent);
                break;
            case R.id.button2:
                intent = new Intent(this, Game.class);
                startActivity(intent);
                break;
            case R.id.button3:
                finish();
                break;
        }
    }
}
