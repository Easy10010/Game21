package com.example.game21.other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.game21.Game;
import com.example.game21.R;
import com.example.game21.History;

import java.util.Map;

/**
 * Created by Yuann72 on 2017/10/29.
 */

public class gameStart implements View.OnClickListener {
    private Context mContext;
    private Activity mActivity;
    private cardManager mCardManager;
    private senceManager mSenceManager;

    private TextView text_CurrPoints,
            text_Result;
    private Button btn_OneMore, btn_SetReady,
            btn_History, btn_Leave;
    private int countDownMillis = 10000;
    private timer mTimer;
    public boolean isGameover = false;
    public gameStart(Context ctx, senceManager sm) {
        mContext = ctx;
        mActivity = ((Activity) (ctx));
        mSenceManager = sm;

        ((Game) ctx).setListener(new Game.onEvent() {
            @Override
            public void onDestroy() {
                mTimer.end();
            }
        });

        text_CurrPoints = (TextView) mSenceManager.fm.get("gaming_sence").getView().findViewById(R.id.text_CurrPoints);
        btn_OneMore = (Button) mSenceManager.fm.get("gaming_sence").getView().findViewById(R.id.btn_OneMore);
        btn_SetReady = (Button) mSenceManager.fm.get("gaming_sence").getView().findViewById(R.id.btn_SetReady);
        btn_OneMore.setOnClickListener(this);
        btn_SetReady.setOnClickListener(this);

        text_Result = (TextView) mSenceManager.fm.get("game_result_sence").getView().findViewById(R.id.text_Result);
        btn_History = (Button) mSenceManager.fm.get("game_result_sence").getView().findViewById(R.id.btn_History);
        btn_Leave = (Button) mSenceManager.fm.get("game_result_sence").getView().findViewById(R.id.btn_Leave);
        btn_History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, History.class);
                mActivity.startActivity(intent);
            }
        });
        btn_Leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) mContext).finish();
            }
        });
        beforeGameStart();


    }
    private void beforeGameStart() {
        mSenceManager.toGamingSence();
        mCardManager = new cardManager(mContext, cardManagerEventListener);
    }

    private void afterGameOver() {
        isGameover = true;
        if (mActivity.isDestroyed()) return;
        try {
            mCardManager.setBotCardsShow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Map result = mCardManager.getResult();

        int GAME_STATUS = (int) result.get("GAME_STATUS");
        String GAME_STATUS_str = mContext.getResources().getStringArray(R.array.GAME_STATUS)[GAME_STATUS];
        String gameResult = mContext.getResources().getString(R.string.gameResult);
        gameResult = String.format(gameResult,
                GAME_STATUS_str,
                result.get("bot_points"),
                result.get("gamer_points"));

        text_Result.setText(gameResult);

        Log.d("onGameEnd", gameResult);

        database db = new database(mContext);
        mSenceManager.toResultSence().progressBarEXP(db.getEXP(), Integer.parseInt((String) result.get("addEXP")));
        db.close();
        mTimer.end();

//        保存结果到数据库
        new database(mContext).insertData(new Object[]{
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                result.get("GAME_STATUS"),
                result.get("bot_points"),
                result.get("bot_cards"),
                result.get("gamer_points"),
                result.get("gamer_cards"),
                result.get("addEXP")
        }).addEXP(Integer.parseInt((String) result.get("addEXP"))).close();
    }

    //    cardManager 的事件监听器
    private cardManager.OnEvent cardManagerEventListener = new cardManager.OnEvent() {
        @Override
        public void PointsChanged(int points) {
            text_CurrPoints.setText(
                    String.format(
                            mContext.getResources().getString(R.string.showCurrPoints),
                            points
                    ));
        }

        @Override
        public void GameStart() {
            mTimer = new timer(
                    (ProgressBar) mActivity.findViewById(R.id.progressBar),
                    countDownMillis,
                    timerEventListener).setGradientColor(
                    mContext.getResources().getColor(R.color.progressBarGradientColor1),
                    mContext.getResources().getColor(R.color.progressBarGradientColor2)
            ).start();
        }

        @Override
        public void GameEnd() {
            afterGameOver();
        }
    };
    //    timer 的事件监听器
    private timer.OnEvent timerEventListener = new timer.OnEvent() {
        @Override
        public void end() {
            btn_click_setReady();
        }
    };

    private void btn_click_oneMore() {
        int res = mCardManager.oneMore(cardManager.GAMER);
        switch (res) {
            case cardManager.GAME_IS_OVER:
                Toast.makeText(mContext, R.string.GAME_IS_OVER, Toast.LENGTH_SHORT).show();
                break;
            case cardManager.POINT_GREATER_THEN_21:
                Toast.makeText(mContext, R.string.POINT_GREATER_THEN_21, Toast.LENGTH_SHORT).show();
                break;
            case cardManager.MORE_THEN_FIVE_CARD:
                Toast.makeText(mContext, R.string.MORE_THEN_FIVE_CARD, Toast.LENGTH_SHORT).show();
                break;
            case cardManager.YOU_ARE_READIED:
                Toast.makeText(mContext, R.string.YOU_ARE_READIED, Toast.LENGTH_SHORT).show();
                break;
            default:
                mTimer.start();
        }
    }

    private void btn_click_setReady() {
        mCardManager.setReady(cardManager.GAMER);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_OneMore) {
            btn_click_oneMore();
        } else if (v == btn_SetReady) {
            btn_click_setReady();
        }
    }
}
