package com.example.game21;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.game21.fragments.history_item;
import com.example.game21.other.exp;
import com.example.game21.other.database;

public class History extends AppCompatActivity {
    ProgressBar progressBar_EXP;
    TextView textView_LEVELNAME,textView_EXP;
    LinearLayout history_list;
    database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        progressBar_EXP = (ProgressBar) findViewById(R.id.progressBar_EXP);
        textView_LEVELNAME = (TextView) findViewById(R.id.textView_LEVELNAME);
        textView_EXP = (TextView) findViewById(R.id.textView_EXP);
        history_list = (LinearLayout) findViewById(R.id.history_list);
        db = new database(this);
        exp exp = new exp(this,db.getEXP());
        progressBar_EXP.setProgress(exp.getCurrEXP());
        progressBar_EXP.setMax(exp.getCurrLevelEXP());
        textView_LEVELNAME.setText(exp.getCurrLevelName());
        textView_EXP.setText(
                String.format("%d/%d",exp.getCurrEXP(),exp.getCurrLevelEXP())
        );

        Cursor cursor = db.getData();
        if (cursor.getCount() != 0) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            while(cursor.moveToNext()){
                Fragment frag = new history_item();
                ((history_item) frag).setData(new String[]{
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)
                },getApplicationContext());
                fragmentTransaction.add(R.id.history_list,frag);
            }
            db.close();
            fragmentTransaction.commit();

            findViewById(R.id.btn_delHistory).setOnClickListener(
                    new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            showAlertToDeleteData();
                        }
                    }
            );
        } else {
            findViewById(R.id.btn_delHistory).setVisibility(View.INVISIBLE);
        }

    }
    void showAlertToDeleteData(){
        new AlertDialog.Builder(this)
            .setTitle("将删除游戏数据?")
            .setMessage("确定删除游戏数据?")
            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db.removeHistoryData().resetExp();
                    history_list.removeAllViews();
                    Toast.makeText(History.this, "游戏数据已删除", Toast.LENGTH_SHORT).show();
                    finish();
                }
            })
            .setNegativeButton("取消", null).show();
//            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                }
//            }).show();
    }
}