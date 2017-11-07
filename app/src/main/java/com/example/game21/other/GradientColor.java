package com.example.game21.other;

import android.graphics.Color;

public class GradientColor{
    float fR,fG,fB;
    float addR,addG,addB;
    float tR,tG,tB;

    private int count = 100;

    GradientColor(String fromColor, String toColor){
        parseColor(Color.parseColor(fromColor), Color.parseColor(toColor));
    }
    GradientColor(int fromColor,int toColor){
        parseColor(fromColor,toColor);
    }
    void parseColor(int fromColor,int toColor){
        fR = Color.red(fromColor);
        fG = Color.green(fromColor);
        fB = Color.blue(fromColor);

        tR = Color.red(toColor);
        tG = Color.green(toColor);
        tB = Color.blue(toColor);

        addR = (tR - fR)/count;
        addG = (tG - fG)/count;
        addB = (tB - fB)/count;
    }
    public int getColor(float progress){
        int r = inRange((int)(fR + addR * progress),addR,tR),
                g = inRange((int)(fG + addG * progress),addG,tG),
                b = inRange((int)(fB + addB * progress),addB,tB);

        return Color.rgb(r,g,b);
    }
    private int inRange(int r,float add,float T){
        if (add > 0){
            if (r > T){
                r = (int) T;
            }
        }else{
            if (r < T){
                r = (int) T;
            }
        }
        return r;
    }
}