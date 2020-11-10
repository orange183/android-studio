package com.example.gobang;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import static com.example.gobang.MainActivity.isStart;

public class FallingView extends View {

    private Context mContext;
    private AttributeSet mAttrs;




    private static final int defaultWidth = 600;//默认宽度
    private static final int defaultHeight = 1000;//默认高度
    private static final int intervalTime = 20;//重绘间隔时间

    public static Paint sonwrectF1Paint;
    public static Paint sonwrectF2Paint;
    public static Paint sonwrectF3Paint;
    public static Paint sonwrectF4Paint;
    public static Paint sonwrectF5Paint;

    private int snowY1;
    private int sonwX1=31;

    private int snowY2=1125;
    private int sonwX2=101;

    private int snowY3=1350;
    private int sonwX3=101;

    private int snowY4=1350;
    private int sonwX4=600;

    private int snowY5=1520;
    private int sonwX5=101;


    private static int roadFlag=1;
    private static int roadFlag1=1;
    private int roadFlag2=1;
    private int roadFlag3=1;
    private int roadFlag4=1;

    public FallingView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public FallingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mAttrs = attrs;
        init();
    }

    private void init(){
        sonwrectF1Paint = new Paint();
        sonwrectF1Paint.setColor(Color.WHITE);
        sonwrectF1Paint.setStyle(Paint.Style.FILL);
        sonwrectF1Paint.setAntiAlias(true);
        snowY1 = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = measureSize(defaultHeight, heightMeasureSpec);
        int width = measureSize(defaultWidth, widthMeasureSpec);
        setMeasuredDimension(width, height);


    }

    private int measureSize(int defaultSize,int measureSpec) {
        int result = defaultSize;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == View.MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isStart) {
            canvas.drawCircle(sonwX1, snowY1, 15, sonwrectF1Paint);
            canvas.drawCircle(sonwX2, snowY2, 11, sonwrectF1Paint);
            canvas.drawCircle(sonwX3, snowY3, 8, sonwrectF1Paint);
            canvas.drawCircle(sonwX4, snowY4, 8, sonwrectF1Paint);
            canvas.drawCircle(sonwX5, snowY5, 8, sonwrectF1Paint);
            getHandler().postDelayed(runnable, intervalTime);//间隔一段时间再进行重绘
        }
    }

    // 重绘线程
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(roadFlag==1){
                sonwrectF1Road1();
            }else if(roadFlag==2){
                sonwrectF1Road2();
            }
            if(roadFlag1==1){
                sonwrectF2Road1();
            }else if(roadFlag1==2){
                sonwrectF2Road2();
            }
            if(roadFlag2==1){
                sonwrectF3Road1();
            }else if(roadFlag2==2){
                sonwrectF3Road2();
            }
            if(roadFlag3==1){
                sonwrectF4Road1();
            }else if(roadFlag3==2){
                sonwrectF4Road2();
            }
            if(roadFlag4==1){
                sonwrectF5Road1();
            }else if(roadFlag4==2){
                sonwrectF5Road2();
            }
            invalidate();
        }
    };


    //绘制最外边边框路线
    private void sonwrectF1Road1(){
        snowY1+=10;
        if(snowY1>=1635){
            snowY1=1635;
        }

        if(snowY1==1635){
            sonwX1+=10;
            if(sonwX1>=1052){
                sonwX1=1052;
            }
        }
        if(sonwX1==1052&&snowY1==1635){
            roadFlag=2;
        }
    }

    //绘制最外边边框路线
    private void sonwrectF1Road2(){
        snowY1-=10;
        if(snowY1<=72){
            snowY1=72;
        }
        if(snowY1==72){
            sonwX1-=10;
            if(sonwX1<=31){
                sonwX1=31;
            }
        }
        if(snowY1==72&&sonwX1==31){
            roadFlag=1;
        }
    }



    //绘制按钮边框路线
    private void sonwrectF2Road1(){
        snowY2+=12;
        if(snowY2>=1336){
            snowY2=1336;
        }

        if(snowY2==1336){
            sonwX2+=12;
            if(sonwX2>=980){
                sonwX2=980;
            }
        }
        if(sonwX2==980&&snowY2==1336){
            roadFlag1=2;
        }
    }
    //绘制按钮边框路线
    private void sonwrectF2Road2(){
        snowY2-=12;
        if(snowY2<=1125){
            snowY2=1125;
        }
        if(snowY2==1125){
            sonwX2-=12;
            if(sonwX2<=101){
                sonwX2=101;
            }
        }
        if(snowY2==1125&&sonwX2==101){
            roadFlag1=1;
        }
    }


    //绘制显示边框一路线
    private void sonwrectF3Road1(){
        snowY3+=9;
        if(snowY3>=1506){
            snowY3=1506;
        }

        if(snowY3==1506){
            sonwX3+=9;
            if(sonwX3>=490){
                sonwX3=490;
            }
        }
        if(sonwX3==490&&snowY3==1506){
            roadFlag2=2;
        }
    }

    //绘制显示边框一路线
    private void sonwrectF3Road2(){
        snowY3-=9;
        if(snowY3<=1350){
            snowY3=1350;
        }
        if(snowY3==1350){
            sonwX3-=9;
            if(sonwX3<=101){
                sonwX3=101;
            }
        }
        if(snowY3==1350&&sonwX3==101){
            roadFlag2=1;
        }
    }

    //绘制变框二显示路线
    private void sonwrectF4Road1(){
        snowY4+=9;
        if(snowY4>=1506){
            snowY4=1506;
        }

        if(snowY4==1506){
            sonwX4+=9;
            if(sonwX4>=980){
                sonwX4=980;
            }
        }
        if(sonwX4==980&&snowY4==1506){
            roadFlag3=2;
        }
    }

    //绘制显示边框二显示路线
    private void sonwrectF4Road2(){
        snowY4-=9;
        if(snowY4<=1350){
            snowY4=1350;
        }
        if(snowY4==1350){
            sonwX4-=9;
            if(sonwX4<=600){
                sonwX4=600;
            }
        }
        if(snowY4==1350&&sonwX4==600){
            roadFlag3=1;
        }
    }


    //绘制进度条显示路线
    private void sonwrectF5Road1(){
        sonwX5+=5;
        if(sonwX5>=980){
            sonwX5=980;
        }
        if(sonwX5==980){
            snowY5+=5;
            if(snowY5>=1600){
                snowY5=1600;
            }
        }
        if(snowY5==1600&&sonwX5==980){
            roadFlag4=2;
        }
    }

    private void sonwrectF5Road2(){
        sonwX5-=5;
        if(sonwX5<=101){
            sonwX5=101;
        }
        if(sonwX5==101){
            snowY5-=5;
            if(snowY5<=1520){
                snowY5=1520;
            }
        }
        if(snowY5==1520&&sonwX5==101){
            roadFlag4=1;
        }
    }
}