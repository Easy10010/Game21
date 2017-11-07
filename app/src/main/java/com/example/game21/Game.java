package com.example.game21;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.game21.other.gameStart;
import com.example.game21.other.senceManager;

/**
 * Created by Yuann72 on 2017/10/27.
 */

public class Game extends AppCompatActivity {
    private long exit = 0;
    private gameStart gameInstance;
    private senceManager senceManager;
    private Context self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        self = this;
        senceManager = new senceManager(self);
        findViewById(R.id.newGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.newGame).setVisibility(View.GONE);
                findViewById(R.id.progressBar).setAlpha(1);

                gameInstance = new gameStart(self, senceManager);

                staff();
            }
        });


    }

    private void staff() {
        senceManager.fm.get("game_result_sence").getView()
                .findViewById(R.id.btn_NewGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameInstance = new gameStart(self, senceManager);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (gameInstance == null || gameInstance.isGameover){
            finish();
            return;
        }
        if ((System.currentTimeMillis() - exit) > 2000) {
            Toast.makeText(this,
                    getResources().getString(R.string.confirm2exitGame), Toast.LENGTH_SHORT).show();
            exit = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (listener != null)
            listener.onDestroy();
    }

    onEvent listener;

    public void setListener(onEvent l) {
        listener = l;
    }

    public interface onEvent {
        void onDestroy();
    }
}

