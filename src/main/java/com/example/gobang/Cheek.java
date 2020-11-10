package com.example.gobang;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import static com.example.gobang.MainActivity.setAlpha;

public class Cheek extends View {
    public static Paint cheekPaint = new Paint();
    Paint  heartPaint = new Paint();
    public Cheek(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        cheekPaint.setStrokeWidth(3);
        cheekPaint.setStyle(Paint.Style.STROKE);


        heartPaint.setAntiAlias(true);//抗锯齿
        heartPaint.setStrokeWidth(8);//画笔宽度
        heartPaint.setColor(Color.RED);//画笔颜色
        heartPaint.setStyle(Paint.Style.STROKE);//画笔样式
        heartPaint.setAlpha(100-setAlpha);


        int width = getWidth();
        int height = getHeight()-40;
        // 绘制心形
        Path path = new Path();
        path.moveTo(width/2,height/4);
        path.cubicTo((width*6)/7,height/9,(width*12)/13,(height*2)/5,width/2,(height*7)/12);
        canvas.drawPath(path,heartPaint);
        Path path2 = new Path();
        path2.moveTo(width/2,height/4);
        path2.cubicTo(width / 7, height / 9, width / 13, (height * 2) / 5, width / 2, (height * 7) / 12);
        canvas.drawPath(path2,heartPaint);



        //画棋盘边框
        RectF rectF0 = new RectF(51,92,1032,1075);
        canvas.drawRoundRect(rectF0,25,25,cheekPaint);
        //画最外边的边框
        RectF rectF1 = new RectF(31,72,1052,1635);
        canvas.drawRoundRect(rectF1,25,25,cheekPaint);
        //画按钮边框
        RectF rectF2 = new RectF(101,1125,980,1336);
        canvas.drawRoundRect(rectF2,25,25,cheekPaint);
        //画显示边框
        RectF rectF3 = new RectF(101,1350,490,1506);
        RectF rectF4 = new RectF(600,1350,980,1506);
        canvas.drawRoundRect(rectF3,25,25,cheekPaint);
        canvas.drawRoundRect(rectF4,25,25,cheekPaint);
        //画进度条边框
        RectF rectF5 = new RectF(101,1520,980,1600);
        canvas.drawRoundRect(rectF5,25,25,cheekPaint);
    }
}