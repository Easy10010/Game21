package com.example.game21;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.game21.other.pokeCard;

/**
 * Created by Administrator on 2017/10/25 0025.
 */

public class Rules extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rules);

        //游戏说明
        TextView t5= (TextView) findViewById(R.id.textView5);
        t5.setText("抽取若干张牌(上限为5张),与机器人进行比拼,根据点数和牌数得出胜负(与花色无关).");

        //点数说明
        //第一行卡牌
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.layout1);
        pokeCard v1 = new pokeCard(this,"A",1,100,150);
        layout1.addView(v1);
        TextView t6 = (TextView) findViewById(R.id.textView6);
        t6.setText("A,算作1点,也算作11点."
                    +"\n"+"当A算作11时总和大于21,则A算为1."
                    +"\n"+"例如:");
//第二行卡牌,每一行的卡牌pokeCard用v加行数加张数表示,
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.layout2);
        pokeCard v21 = new pokeCard(this,"A",3,100,150);
        layout2.addView(v21);

        TextView tx1 = new TextView(this);
        tx1.setText("+");
        tx1.setWidth(30);
        tx1.setTextSize(30);
        tx1.setTextColor(Color.WHITE);
        tx1.setGravity(Gravity.CENTER);
        layout2.setGravity(Gravity.CENTER_VERTICAL);
        layout2.addView(tx1);

        pokeCard v22 = new pokeCard(this,"7",2,100,150);
        layout2.addView(v22);

        TextView tx2 = new TextView(this);
        tx2.setText("=18点");
        tx2.setWidth(300);
        tx2.setTextSize(30);
        tx2.setTextColor(Color.WHITE);
        tx2.setGravity(Gravity.CENTER);
        layout2.setGravity(Gravity.CENTER_VERTICAL);
        layout2.addView(tx2);

        TextView t7 = (TextView) findViewById(R.id.textView7);
        t7.setText("(A+7)是18点");
//第三行卡牌
        LinearLayout layout3 = (LinearLayout) findViewById(R.id.layout3);
        pokeCard v31 = new pokeCard(this,"A",2,100,150);
        layout3.addView(v31);

        TextView tx3 = new TextView(this);
        tx3.setText("+");
        tx3.setWidth(30);
        tx3.setTextSize(30);
        tx3.setTextColor(Color.WHITE);
        tx3.setGravity(Gravity.CENTER);
        layout3.setGravity(Gravity.CENTER_VERTICAL);
        layout3.addView(tx3);

        pokeCard v32 = new pokeCard(this,"7",4,100,150);
        layout3.addView(v32);

        TextView tx4 = new TextView(this);
        tx4.setText("+");
        tx4.setWidth(30);
        tx4.setTextSize(30);
        tx4.setTextColor(Color.WHITE);
        tx4.setGravity(Gravity.CENTER);
        layout3.setGravity(Gravity.CENTER_VERTICAL);
        layout3.addView(tx4);

        pokeCard v33 = new pokeCard(this,"K",1,100,150);
        layout3.addView(v33);

        TextView tx5 = new TextView(this);
        tx5.setText("=18点");
        tx5.setWidth(300);
        tx5.setTextSize(30);
        tx5.setTextColor(Color.WHITE);
        tx5.setGravity(Gravity.CENTER);
        layout3.setGravity(Gravity.CENTER_VERTICAL);
        layout3.addView(tx5);

        TextView t8 = (TextView) findViewById(R.id.textView8);
        t8.setText("(A,7,K)也是18点");
        //第四行卡牌
        ConstraintLayout layout4 = (ConstraintLayout) findViewById(R.id.layout4);
        //大布局嵌套小布局，每个布局放一张卡牌
        LinearLayout l1 = (LinearLayout) findViewById(R.id.l1);
        pokeCard v41 = new pokeCard(this,"2",1,100,150);
        l1.addView(v41);
        LinearLayout l2 = (LinearLayout) findViewById(R.id.l2);
        pokeCard v42 = new pokeCard(this,"3",1,100,150);
        l2.addView(v42);
        LinearLayout l3 = (LinearLayout) findViewById(R.id.l3);
        pokeCard v43 = new pokeCard(this,"4",1,100,150);
        l3.addView(v43);
        LinearLayout l4 = (LinearLayout) findViewById(R.id.l4);
        pokeCard v44 = new pokeCard(this,"5",1,100,150);
        l4.addView(v44);
        LinearLayout l5 = (LinearLayout) findViewById(R.id.l5);
        pokeCard v45 = new pokeCard(this,"6",1,100,150);
        l5.addView(v45);
        LinearLayout l6 = (LinearLayout) findViewById(R.id.l6);
        pokeCard v46 = new pokeCard(this,"7",1,100,150);
        l6.addView(v46);
        LinearLayout l7 = (LinearLayout) findViewById(R.id.l7);
        pokeCard v47 = new pokeCard(this,"8",1,100,150);
        l7.addView(v47);
        LinearLayout l8 = (LinearLayout) findViewById(R.id.l8);
        pokeCard v48 = new pokeCard(this,"9",1,100,150);
        l8.addView(v48);
        LinearLayout l9 = (LinearLayout) findViewById(R.id.l9);
        pokeCard v49 = new pokeCard(this,"10",1,100,150);
        l9.addView(v49);
        TextView t9 = (TextView) findViewById(R.id.textView9);
        t9.setText("2-10,对应点数2-10");
//第五行卡牌
        LinearLayout layout5 = (LinearLayout) findViewById(R.id.layout5);
        pokeCard v51 = new pokeCard(this,"J",1,100,150);
        layout5.addView(v51);
        pokeCard v52 = new pokeCard(this,"Q",1,100,150);
        layout5.addView(v52);
        pokeCard v53 = new pokeCard(this,"K",1,100,150);
        layout5.addView(v53);
        TextView t10 = (TextView) findViewById(R.id.textView10);
        t10.setText("J,Q,K都算作10点");

        //比牌说明
        //第六行卡牌
        LinearLayout layout6 = (LinearLayout) findViewById(R.id.layout6);
        TextView t11 = (TextView) findViewById(R.id.textView11);
        t11.setText("I.越接近或等于21点,牌越大,超过21点则爆炸"
                    +"\n"+"例如:");
        LinearLayout l10 = (LinearLayout) findViewById(R.id.l10);
        pokeCard v61 = new pokeCard(this,"3",4,100,150);
        l10.addView(v61);
        LinearLayout l11 = (LinearLayout) findViewById(R.id.l11);
        pokeCard v62 = new pokeCard(this,"8",2,100,150);
        l11.addView(v62);
        LinearLayout l12 = (LinearLayout) findViewById(R.id.l12);
        pokeCard v63 = new pokeCard(this,"10",1,100,150);
        l12.addView(v63);

        LinearLayout l13 = (LinearLayout) findViewById(R.id.l13);
        pokeCard v64 = new pokeCard(this,"10",2,100,150);
        l13.addView(v64);
        LinearLayout l14 = (LinearLayout) findViewById(R.id.l14);
        pokeCard v65 = new pokeCard(this,"8",3,100,150);
        l14.addView(v65);

        LinearLayout l15 = (LinearLayout) findViewById(R.id.l15);
        pokeCard v66 = new pokeCard(this,"8",2,100,150);
        l15.addView(v66);
        LinearLayout l16 = (LinearLayout) findViewById(R.id.l16);
        pokeCard v67 = new pokeCard(this,"10",3,100,150);
        l16.addView(v67);
        LinearLayout l17 = (LinearLayout) findViewById(R.id.l17);
        pokeCard v68 = new pokeCard(this,"K",4,100,150);
        l17.addView(v68);

        TextView t14 = (TextView) findViewById(R.id.textView14);
        t14.setText("(3,8,10)21点  >  (10,8)18点  >  (8,10,K)28点(爆炸)");
        //第七行卡牌
        TextView t15 = (TextView) findViewById(R.id.textView15);
        t15.setText("II.点数一样时,牌多者胜"
                    +"\n"+"例如:");
        LinearLayout l18 = (LinearLayout) findViewById(R.id.l18);
        pokeCard v71 = new pokeCard(this,"3",3,100,150);
        l18.addView(v71);
        LinearLayout l19 = (LinearLayout) findViewById(R.id.l19);
        pokeCard v72 = new pokeCard(this,"7",2,100,150);
        l19.addView(v72);
        LinearLayout l20 = (LinearLayout) findViewById(R.id.l20);
        pokeCard v73 = new pokeCard(this,"8",4,100,150);
        l20.addView(v73);
        LinearLayout l21 = (LinearLayout) findViewById(R.id.l21);
        pokeCard v74 = new pokeCard(this,"3",1,100,150);
        l21.addView(v74);

        LinearLayout l22 = (LinearLayout) findViewById(R.id.l22);
        pokeCard v75 = new pokeCard(this,"A",2,100,150);
        l22.addView(v75);
        LinearLayout l23 = (LinearLayout) findViewById(R.id.l23);
        pokeCard v76 = new pokeCard(this,"K",3,100,150);
        l23.addView(v76);

        TextView t17 = (TextView) findViewById(R.id.textView17);
        t17.setText("(3,7,8,3)21点        >       (A,K)21点");
//第八行卡牌
        TextView t18 = (TextView) findViewById(R.id.textView18);
        t18.setText("III.五张非爆牌,大于非五张牌"
                +"\n"+"例如:");
        LinearLayout l24 = (LinearLayout) findViewById(R.id.l24);
        pokeCard v81 = new pokeCard(this,"2",3,100,150);
        l24.addView(v81);
        LinearLayout l25 = (LinearLayout) findViewById(R.id.l25);
        pokeCard v82 = new pokeCard(this,"8",2,100,150);
        l25.addView(v82);
        LinearLayout l26 = (LinearLayout) findViewById(R.id.l26);
        pokeCard v83 = new pokeCard(this,"3",4,100,150);
        l26.addView(v83);
        LinearLayout l27 = (LinearLayout) findViewById(R.id.l27);
        pokeCard v84 = new pokeCard(this,"2",2,100,150);
        l27.addView(v84);
        LinearLayout l28 = (LinearLayout) findViewById(R.id.l28);
        pokeCard v85 = new pokeCard(this,"1",1,100,150);
        l28.addView(v85);

        LinearLayout l29 = (LinearLayout) findViewById(R.id.l29);
        pokeCard v86 = new pokeCard(this,"5",2,100,150);
        l29.addView(v86);
        LinearLayout l30 = (LinearLayout) findViewById(R.id.l30);
        pokeCard v87 = new pokeCard(this,"5",4,100,150);
        l30.addView(v87);
        LinearLayout l31 = (LinearLayout) findViewById(R.id.l31);
        pokeCard v88 = new pokeCard(this,"A",1,100,150);
        l31.addView(v88);

        TextView t20 = (TextView) findViewById(R.id.textView20);
        t20.setText("(2,8,3,2,1)21点            >         (5,5,A)21点");
    }
}

