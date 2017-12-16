package com.example.game21.other;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.example.game21.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class cardManager {
    final static int MORE_THEN_FIVE_CARD = 101;
    final static int POINT_GREATER_THEN_21 = 102;
    final static int GAME_IS_OVER = 103;
    final static int YOU_ARE_READIED = 104;
    final static int GAME_STATUS_BOT_WIN = 0;
    final static int GAME_STATUS_GAMER_WIN = 1;
    final static int GAME_STATUS_BOTH_WIN = 2;
    final static int GAME_STATUS_BOTH_LOSE = 3;
    final static int GAMER = 1;
    final static int BOT = 0;

    private final Context mContext;
    private Activity mActivity;
    private final LinearLayout bot_layout;
    private final LinearLayout gamer_layout;
    private int cardWidth = 0;
    private int cardHeight = 0;
    private final Random rand = new Random();
    private final ArrayList cards_history = new ArrayList();
    private final ArrayList bot_cards = new ArrayList(5);
    private final ArrayList gamer_cards = new ArrayList(5);
    private final ArrayList bot_points = new ArrayList(5);
    private final ArrayList gamer_points = new ArrayList(5);
    private int bot_curr_points = 0;
    private int gamer_curr_points = 0;
    private boolean bot_readied = false;
    private boolean gamer_readied = false;
    private boolean game_over = false;
    private Map game_result;


    cardManager(Context ctx, OnEvent l) {
        mContext = ctx;
        mActivity = ((Activity) (ctx));
        listener = l;
        bot_layout = (LinearLayout) mActivity.findViewById(R.id.layout_bot);
        gamer_layout = (LinearLayout) mActivity.findViewById(R.id.layout_gamer);

        removeLayoutViews();

    }

    private cardManager gameStart() {
        oneMore(GAMER);
        oneMore(GAMER);
        oneMore(BOT);
        oneMore(BOT);

        new bot(this, bot_points);
        callListener("GameStart", 0);
        return this;
    }

    //   游戏正式开始前,移除上一局在layout中的牌
    private void removeLayoutViews() {
        if (bot_layout.getChildCount() == 0 && gamer_layout.getChildCount() == 0) {
            gameStart();
            return;
        }
        Animation fade_out = AnimationUtils.loadAnimation(mContext, R.anim.faded_out_translate_x);
        fade_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bot_layout.removeAllViews();
                gamer_layout.removeAllViews();
                gameStart();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        bot_layout.startAnimation(fade_out);
        gamer_layout.startAnimation(fade_out);
    }

    private void setScreenWidthPixels() {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);
        cardWidth = (int) (metric.widthPixels / 5 * 0.9);
        cardHeight = (int) (metric.heightPixels * 0.9);
    }

    //    通过扑克牌点数获取扑克牌左上角显示的字符
    private String getCardStr(int cardPoint) {
        String output;
        if (cardPoint < 11 && cardPoint != 1) {
            output = cardPoint + "";
        } else {
            switch (cardPoint) {
                case 1:
                    output = "A";
                    break;
                case 11:
                    output = "J";
                    break;
                case 12:
                    output = "Q";
                    break;
                case 13:
                    output = "K";
                    break;
                default:
                    output = "ERROR";
                    Log.e("RANDOM ERROR", "RANDOM ERROR");
                    break;
            }
        }
        return output;
    }

    //    随机获得扑克牌点数
    private int randCardPoint() {
        return rand.nextInt(13) + 1;
    }

    //    随机一种扑克牌类型
    private int randCardType() {
        return (int) (Math.random() * 4) + 1;
    }

    //    检查扑克牌是否已经被使用
    private Boolean checkCanNotUse(String cardStr, int cardType) {
        String id = cardStr + "|" + cardType;
        if (cards_history.indexOf(id) > -1) {
            return false;
        } else {
            cards_history.add(id);
            return true;
        }
    }

    //    检查还能不能再要一张牌
    private int checkCanNotGetMore(int identity) {
        if (game_over) return GAME_IS_OVER;
        int point, cardNum = 0;
        boolean ready;
        if (identity == GAMER) {
            point = gamer_curr_points;
            cardNum = gamer_cards.size();
            ready = gamer_readied;
        } else {
            point = bot_curr_points;
            cardNum = bot_cards.size();
            ready = bot_readied;
        }
        if (point >= 21) {
            return POINT_GREATER_THEN_21;
        } else if (cardNum > 5) {
            setReady(identity);
            return MORE_THEN_FIVE_CARD;
        } else if (ready) {
            return YOU_ARE_READIED;
        } else {
            return 0;
        }
    }

    //    计算最大点数
    private int calcMaxPoint(ArrayList points) {
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
        if (AcardNum == 0) return basicPoint;

        __calcMaxPoint_loop(basicPoint, AcardNum, resultList);
        if (resultList.size() == 0) {
            max = basicPoint + AcardNum;
        } else {
            for (Integer i : resultList) {
                if (max < i) {
                    max = i;
                }
            }
        }
        return max;
    }

    private void __calcMaxPoint_loop(int basic, int left, ArrayList<Integer> resultList) {
        if (basic <= 21 && left == 0) {
            resultList.add(basic);
            return;
        } else if (basic > 21) {
            return;
        }
        __calcMaxPoint_loop(basic + 1, left - 1, resultList);
        __calcMaxPoint_loop(basic + 11, left - 1, resultList);
    }

    private int calcWhichWin() {
        int gamerPoint = calcMaxPoint(gamer_points),
                botPoint = calcMaxPoint(bot_points),
                gamerCardsNum = gamer_cards.size(), botCardsNum = bot_cards.size();

        if (botPoint <= 21 && gamerPoint <= 21) {
            if (botCardsNum == 5 && gamerCardsNum < 5) {
                return GAME_STATUS_BOT_WIN;
            } else if (botCardsNum < 5 && gamerCardsNum == 5) {
                return GAME_STATUS_GAMER_WIN;
            }
            if (botPoint > gamerPoint) {
                return GAME_STATUS_BOT_WIN;
            } else if (botPoint < gamerPoint) {
                return GAME_STATUS_GAMER_WIN;
            } else if (botCardsNum > gamerCardsNum) {
                return GAME_STATUS_BOT_WIN;
            } else if (botCardsNum < gamerCardsNum) {
                return GAME_STATUS_GAMER_WIN;
            } else {
                return GAME_STATUS_BOTH_WIN;
            }
        } else if (botPoint > 21 && gamerPoint <= 21) {
            return GAME_STATUS_GAMER_WIN;
        } else if (botPoint <= 21 && gamerPoint > 21) {
            return GAME_STATUS_BOT_WIN;
        } else {
            return GAME_STATUS_BOTH_LOSE;
        }
    }

    private int calcAddEXP() {
        return exp.calcEXP(bot_cards.size(), bot_curr_points, gamer_cards.size(), gamer_curr_points);
    }

    public void setReady(int identity) {
        if (identity == GAMER) {
            if (gamer_readied) return;
            gamer_readied = true;
        } else {
            if (bot_readied) return;
            bot_readied = true;
        }

        if (gamer_readied && bot_readied) {
            game_over = true;
            callListener("GameEnd", 0);
        }
    }

    public Map getResult() {
        if (game_result == null) {
            String bot_cards_str = "", gamer_cards_str = "";
            for (Object bot_card : bot_cards) {
                pokeCard card = ((pokeCard) bot_card);
                bot_cards_str += card.text + card.cardType + "|";
            }
            bot_cards_str = bot_cards_str.substring(0, bot_cards_str.length() - 1);

            for (Object gamer_card : gamer_cards) {
                pokeCard card = ((pokeCard) gamer_card);
                gamer_cards_str += card.text + card.cardType + "|";
            }
            gamer_cards_str = gamer_cards_str.substring(0, gamer_cards_str.length() - 1);

            game_result = new HashMap();
            game_result.put("GAME_STATUS", calcWhichWin());
            game_result.put("bot_cards", bot_cards_str);
            game_result.put("bot_points", bot_curr_points);
            game_result.put("gamer_cards", gamer_cards_str);
            game_result.put("gamer_points", gamer_curr_points);
            game_result.put("addEXP", calcAddEXP() + "");
        }
        return game_result;
    }

    public int getPoint(int identity) {
        if (identity == GAMER) {
            return calcMaxPoint(gamer_points);
        } else {
            return calcMaxPoint(bot_points);
        }
    }

    public int oneMore(int identity) {
        return oneMore(identity, 0);
    }

    public int oneMore(int identity, int postDelay) {
        if (cardWidth == 0 || cardHeight == 0) setScreenWidthPixels();

        int CanNotGetMore = checkCanNotGetMore(identity);
        if (CanNotGetMore != 0) return CanNotGetMore;


        int cardPoint = randCardPoint(), cardType = randCardType();
        String cardStr = getCardStr(cardPoint);
        cardPoint = cardPoint > 10 ? 10 : cardPoint; // J，Q，K都算作10点。

        if (!checkCanNotUse(cardStr, cardType)) {
//            随机到重复牌,重新抽
            return oneMore(identity, postDelay);
        }
        final Animation faded_in = AnimationUtils.loadAnimation(mContext, R.anim.faded_in_translate_x);
        final pokeCard newCard = new pokeCard(mContext, cardStr, cardType, cardWidth, cardHeight);
        if (identity == GAMER) { // gamer
            gamer_layout.addView(newCard);
            newCard.startAnimation(faded_in);
            gamer_cards.add(newCard);
            gamer_points.add(cardPoint);
            gamer_curr_points = calcMaxPoint(gamer_points);
            callListener("PointsChanged", gamer_curr_points);
            return gamer_curr_points;
        } else { // bot
            newCard.setVisibility(pokeCard.Show_Backfaces);
            bot_layout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bot_layout.addView(newCard);
                    newCard.startAnimation(faded_in);
                }
            }, postDelay);
            bot_cards.add(newCard);
            bot_points.add(cardPoint);
            bot_curr_points = calcMaxPoint(bot_points);
            return bot_curr_points;
        }
    }

    public void setBotCardsShow() throws InterruptedException {
        pokeCard.setVisibility(pokeCard.NOshow_Backfaces, bot_cards, 1000, 400);
    }

    OnEvent listener;

    public interface OnEvent {
        void PointsChanged(int points);

        void GameStart();

        void GameEnd();
    }

    public void setOnEvent(OnEvent l) {
        listener = l;
    }

    private void callListener(String msg, Object extra) {
        if (listener == null) return;
        switch (msg) {
            case "PointsChanged":
                listener.PointsChanged((Integer) extra);
                break;
            case "GameStart":
                listener.GameStart();
                break;
            case "GameEnd":
                listener.GameEnd();
                break;
        }

    }
}
