package com.example.game21.other;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;

import com.example.game21.R;

import java.util.ArrayList;

/**
 * Created by Yuann72 on 2017/10/25.
 */

public class pokeCard extends View {
    static final int CardType_Backfaces = 0;
    static final int CardType_HeiTao = 1;
    static final int CardType_HongTao = 2;
    static final int CardType_MeiHua = 3;
    static final int CardType_FangKuai = 4;

    static final boolean Show_Backfaces = true;
    static final boolean NOshow_Backfaces = false;

    int cardWidth, cardHeight;
    public String text;
    public int cardType;  // 绘制扑克牌类型 1 黑桃 2 红桃 3 梅花 4 方块
    public boolean visibility = false; // 为真则绘制牌背
    int cardFlowerResID = 0;

    Paint paint = new Paint();
    TextPaint textPaint = new TextPaint();

    public pokeCard(Context context, String t, int type, int maxWidth, int maxHeight) {
        super(context);

        text = t;
        cardType = type;

        setSuitableSize(maxWidth, maxHeight);

        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(cardWidth, cardHeight);
//      设置牌的外边距
        lp.setMargins(0, 0, 10, 0);
        super.setLayoutParams(lp);
    }

    public pokeCard setMargin(int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(cardWidth, cardHeight);
        lp.setMargins(left, top, right, bottom);
        super.setLayoutParams(lp);
        return this;
    }
    public pokeCard setVisibility(Boolean v) {
        visibility = v;
        final pokeCard self = this;
        this.post(new Runnable() {
                @Override
                public void run() {
                    self.invalidate();
                }
            });
        return this;
    }

    public static void setVisibility(final Boolean v, ArrayList cards, int duration, int duration2) {
        duration = duration / 2;
        int i = 0;
        for (Object card2 : cards) {
            final pokeCard card = (pokeCard)card2;
            ValueAnimator.AnimatorUpdateListener Listener = new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    card.setRotationY(value);
                }
            };
            ValueAnimator va = ValueAnimator.ofInt(0, 90);
            final ValueAnimator va2 = ValueAnimator.ofInt(270, 360);
            va.setDuration(duration);
            va.addUpdateListener(Listener);
            va2.setDuration(duration);
            va2.addUpdateListener(Listener);
            va.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    card.setVisibility(v);
                    card.setRotationY(270);
                    va2.start();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            va2.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
//                c();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            va.setStartDelay( i++ * duration2 );
            va.start();
        }
    }

    public pokeCard setMaxWidth() {

        return this;
    }

    public pokeCard setMaxHeight() {

        return this;
    }

    //  传入最大宽度,最大高度,计算出合适的宽高
    private void setSuitableSize(int maxWidth, int maxHeight) {
        cardHeight = (int) (maxWidth / 5.7 * 8.8);
        if (cardHeight < maxHeight) {
            cardWidth = maxWidth;
        } else {
            cardHeight = maxHeight;
            cardWidth = (int) (maxHeight / 8.8 * 5.7);
        }
    }

    protected void onDraw(Canvas canvas) {
        Camera camera = new Camera();


        paint.setAntiAlias(true);
        textPaint.setAntiAlias(true);
        //      扑克牌白色背景
        RectF rect = new RectF(0, 0, cardWidth, cardHeight);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(rect,
                cardWidth / 10,
                cardWidth / 10,
                paint);

        //      扑克牌灰色边框
        int strokeWidth = 5;
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(rect,
                cardWidth / 10,
                cardWidth / 10,
                paint);

        if (visibility) {
            drawBackground(canvas, rect);
            return;
        }

//      字
        if (cardType == 1 || cardType == 3) {//当为黑桃和梅花时数字为黑色，当为红桃和方块时数字为红色
            textPaint.setColor(Color.BLACK);
        } else {
            textPaint.setColor(Color.parseColor("#d40000"));
        }
        textPaint.setFakeBoldText(true);


//        style1(canvas);
        style2(canvas);

    }

    private void style1(Canvas canvas) {
        if (text.length() > 1) {
            textPaint.setTextSize((float) (cardWidth * 0.12));
        } else {
            textPaint.setTextSize((float) (cardWidth * 0.15));
        }
        Rect textRect = new Rect();
        textRect.left = (int) (cardWidth * 0.03);
        textRect.top = (int) (cardHeight * 0.08);
        textRect.right = (int) (textRect.left + cardWidth * 0.15);
        textRect.bottom = (int) (textRect.top + cardWidth * 0.15);

        Rect picRect = new Rect();
        picRect.left = (int) (cardWidth * 0.04);
        picRect.top = (int) (cardHeight * 0.15);
        picRect.right = (int) (picRect.left + cardWidth * 0.15);
        picRect.bottom = (int) (picRect.top + cardWidth * 0.15);

        Rect centerRect = new Rect();
        centerRect.left = (int) (cardWidth * 0.3);
        centerRect.top = (int) (cardHeight * 0.35);
        centerRect.right = (int) (centerRect.left + cardWidth * 0.4);
        centerRect.bottom = (int) (centerRect.top + cardHeight * 0.3);

        drawText(canvas, textRect);
        drawFlower(canvas, picRect);
        drawFlower(canvas, centerRect);
        canvas.rotate(180);
        canvas.translate(-cardWidth, -cardHeight);
        drawText(canvas, textRect);
        drawFlower(canvas, picRect);
    }

    private void style2(Canvas canvas) {
        textPaint.setTextSize((float) (cardWidth * 0.28));

        Rect textRect = new Rect();

        textRect.top = (int) (cardHeight * 0.1);
        if (text.length() == 2) { // 10
            textRect.left = -(cardWidth / 2 - cardWidth / 3);
            textRect.right = cardWidth / 2;
        } else {
            textRect.left = 0;
            textRect.right = cardWidth / 3;
        }
        textRect.bottom = (int) (textRect.top + (cardHeight * 0.2));

        Rect picRect = new Rect();
        picRect.left = (int) (cardWidth * 0.1);
        picRect.top = (int) (textRect.top + cardHeight * 0.15);
        picRect.right = (int) (picRect.left + cardWidth * 0.15);
        picRect.bottom = (int) (picRect.top + cardWidth * 0.15);

        Rect centerRect = new Rect();
        int[] LRTB = computeLRTB((int) (cardWidth * 0.5), (int) (cardWidth * 0.5));
        centerRect.left = LRTB[0];
        centerRect.right = LRTB[1];
        centerRect.top = LRTB[2];
        centerRect.bottom = LRTB[3];

        drawText(canvas, textRect);
        drawFlower(canvas, picRect);
        drawFlower(canvas, centerRect);
    }

    private void drawLine(Canvas c, Rect r) {
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        float l = 500;
        c.drawLine(r.left, 0f, r.left, l, p);
        c.drawLine(r.right, 0f, r.right, l, p);
        c.drawLine(0, r.top, l, r.top, p);
        c.drawLine(0, r.bottom, l, r.bottom, p);

    }


    private int[] computeLRTB(int w, int h) {
        int halfCardWidth = cardWidth / 2, halfCardHeight = cardHeight / 2;
        int halfWidth = w / 2, halfHeight = h / 2;
        int[] result = new int[4];
        result[0] = halfCardWidth - halfWidth;
        result[1] = halfCardWidth + halfWidth;
        result[2] = halfCardHeight - halfHeight;
        result[3] = halfCardHeight + halfHeight;
        return result;
    }

    //    绘制扑克牌左上角文字
    private void drawText(Canvas canvas, Rect r) {
        textPaint.setTextAlign(Paint.Align.CENTER);
        int width = r.width();
        int numOfChars = textPaint.breakText(text, true, width, null);
        int start = (text.length() - numOfChars) / 2;
        canvas.drawText(text, start, start + numOfChars, r.exactCenterX(), r.exactCenterY(), textPaint);
    }

    //    绘制扑克牌花色
    private void drawFlower(Canvas canvas, Rect r) {
        if (cardFlowerResID == 0) {
            switch (cardType) {
                case 1:
                    cardFlowerResID = R.drawable.spade;
                    break;
                case 2:
                    cardFlowerResID = R.drawable.heart;
                    break;
                case 3:
                    cardFlowerResID = R.drawable.club;
                    break;
                case 4:
                    cardFlowerResID = R.drawable.diamond;
                    break;
            }
        }

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), cardFlowerResID);
        canvas.drawBitmap(bmp, null, r, paint);
    }

    //    绘制扑克牌背面图片
    private void drawBackground(Canvas canvas, RectF rect) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        canvas.drawBitmap(bmp, null, rect, paint);
    }
}
