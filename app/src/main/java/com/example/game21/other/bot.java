package com.example.game21.other;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Yuann72 on 2017/10/27.
 */

public class bot {
    cardManager cardManager;
    ArrayList points;
    int expectPoint = 18; // 大于等于这个值就不在要牌
    Random rand = new Random();

    public bot(cardManager cm, ArrayList p) {
        cardManager = cm;
        points = p;
        runnable.run();
    }

    //  返回真表示还要牌
    private boolean nextAction() {
        int AcardNum = 0;
        int basicPoint = 0;
        int max = 0;
        ArrayList<Integer> resultList = new ArrayList();

        for (Object point : points) {
            int p = (int) point;
            if (p == 1) {
                AcardNum++;
            } else {
                basicPoint += p;
            }
        }
        if (AcardNum == 0) return check(basicPoint);
        // 但手里有 A 这种牌时,分析可能的点数 比如 (A,5) 有 16 和 6 两种情况
        nextAction_loop(basicPoint, AcardNum, resultList); // 分析可能的点数

        if (resultList.size() == 0) return false; // 出错
        int getOneMore = 0; // 大于0就再要牌  小于0就不要  等于0看情况
        for (Integer p : resultList) {
            getOneMore += check(p) ? 1 : -1;
        }
        if (getOneMore > 0)
            return true;
        else if (getOneMore < 0)
            return false;
        return false; // 爆炸和不爆炸的概率一样 保守派就返回false
    }

    private void nextAction_loop(int basic, int left, ArrayList<Integer> resultList) {
        if (basic <= 21 && left == 0) {
            resultList.add(basic);
            return;
        } else if (basic > 21) {
            return;
        }
        nextAction_loop(basic + 1, left - 1, resultList);
        nextAction_loop(basic + 11, left - 1, resultList);
    }

    private boolean check(int p) {
        if (p >= expectPoint) return false;
        if (p > 14) // 手上的牌点数大于14 随机0.5的概率要牌还是不要
            return rand.nextInt(2) > 0 ? true : false;
        else
            return true;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
        int postDelay = 0;
        for (int i = 0; i < 3; i++) {
            postDelay += rand.nextInt(1000);
            if (nextAction()) { // 返回真就要牌
                cardManager.oneMore(cardManager.BOT,postDelay);
            } else {
                break;
            }
        }
        cardManager.setReady(cardManager.BOT);
        return;
        }
    };
}
