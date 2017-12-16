package com.example.game21.other;

import android.content.Context;

import com.example.game21.R;

/**
 * Created by Yuann72 on 2017/11/3.
 */

public class exp {
    private int CurrEXP, CurrLevelEXP, NextLevelEXP;
    private String CurrLevelName;
    private Context mContext;

    public exp(Context ctx) {
        mContext = ctx;
        database db = new database(ctx);
        CurrEXP = db.getEXP();
        db.close();
        calcMaxEXP();
    }

    public exp(Context ctx, int _currEXP) {
        mContext = ctx;
        CurrEXP = _currEXP;
        calcMaxEXP();
    }

    private void calcMaxEXP() {
        int[] LEVEL_EXP = mContext.getResources().getIntArray(R.array.LEVEL_EXP);
        String[] LEVEL_NAME = mContext.getResources().getStringArray(R.array.LEVEL_NAME);
        for (int i = 0; i < LEVEL_EXP.length; i++) {
            if (CurrEXP < LEVEL_EXP[i]) {
                CurrLevelEXP = LEVEL_EXP[i];
                CurrLevelName = LEVEL_NAME[i];
                if (i < LEVEL_EXP.length - 1) {
                    NextLevelEXP = LEVEL_EXP[i + 1];
                } else {
                    NextLevelEXP = 9999;
                }
                break;
            }
        }
        if (CurrLevelEXP == 0){
            CurrLevelEXP = LEVEL_EXP[0];
            CurrLevelName = LEVEL_NAME[0];
        }
    }

    public int getCurrLevelEXP() {
        return CurrLevelEXP;

    }

    public String getCurrLevelName() {
        return CurrLevelName;
    }

    public int getNextLevelEXP() {
        return NextLevelEXP;
    }

    public int getCurrEXP() {
        return CurrEXP;
    }

    public static int calcEXP(int botCards, int botPoints, int gamerCards, int gamerPoints) {
        if (gamerPoints > 21) {
            return 21-gamerPoints;
        }
        if (botPoints <= 21 && gamerPoints < botPoints && gamerCards < 5) {
            return gamerPoints - botPoints;
        }
        if (botPoints <= 21 && gamerPoints <= 21 && gamerPoints == botPoints && gamerCards == botCards) {
//            同点数同牌数+100
            return 100;
        }
        if (gamerPoints == 21) {
            switch (gamerCards) {
                case 5:
                    return 150 + gamerPoints - botPoints;
                case 4:
                    return 90 + gamerPoints - botPoints;
                case 3:
                    return 50 + gamerPoints - botPoints;
                case 2:
                    return 30 + gamerPoints - botPoints;
            }
        }
        switch (gamerCards) {
            case 5:
                return 100 + gamerPoints - botPoints;
            case 4:
                return 80 + gamerPoints - botPoints;
            case 3:
                return 40 + gamerPoints - botPoints;
            case 2:
                return 20 + gamerPoints - botPoints;
        }
        return 10;
    }
}
