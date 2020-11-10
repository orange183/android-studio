package com.example.gobang;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.gobang.MainActivity;

import static com.example.gobang.MainActivity.isStart;

public class ChessBoard extends View {

    //定义变量来判断当前要下黑棋还是白棋
    public static boolean isBlackWin = false;
    public static boolean isWhiteWin = false;
    public static int chessNum;//定义变脸来了保存棋子的总数，应为棋盘的大小时13*13，最大能存放169个棋子
    public static String[][] allChess = new String[13][13];//定义数组来存储棋子的位置
    public static boolean isEnClick = true;
    public static int chessx, chessy;//定义变量来保存数组中棋子的位置
    public static Paint chessBoardPaint = new Paint();
    Paint chessPaint = new Paint();//定义用来绘制棋子的画笔
    Paint chesscheek = new Paint();
    public static Paint textpain = new Paint();//定义文字的画笔

    private int x, y;//定义变量用来获取当前所点击屏幕的位置
    public static int chessFiveCount = 1;//定一变量来获取棋子的连城数量

    public static int blackChessNum = 0;
    public static int whiteChessNum = 0;


    public ChessBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        chesscheek.setColor(Color.RED);
        chesscheek.setStyle(Paint.Style.STROKE);
        chessBoardPaint.setStrokeWidth(8);

        textpain.setTextSize(100);//设置画笔画的字体尺寸
        textpain.setTypeface(Typeface.DEFAULT_BOLD);//设置画笔字体

        //画棋盘的行
        for (int i = 104; i < 1144; i += 80) {
            canvas.drawLine(58, i, 1022, i, chessBoardPaint);
        }
        //画棋盘的列
        for (int i = 61; i < 1101; i += 80) {
            canvas.drawLine(i, 102, i, 1065, chessBoardPaint);
        }

        canvas.drawText("黑棋:" + blackChessNum, 130, 1460, textpain);
        canvas.drawText("白棋:" + whiteChessNum, 630, 1460, textpain);
        //进行棋子的绘制
        for (int i = 0; i < allChess.length; i++) {
            for (int j = 0; j < allChess.length; j++) {
                if (allChess[i][j] == "black") {
                    chessPaint.setColor(Color.BLACK);
                    canvas.drawCircle(i * 80 + 58, j * 80 + 102, 30, chessPaint);
                } else if (allChess[i][j] == "white") {
                    chessPaint.setColor(Color.WHITE);
                    canvas.drawCircle(i * 80 + 58, j * 80 + 102, 30, chessPaint);
                }
            }
        }
        //当棋子数不为零时在当前棋子上添加一个边框
        if (chessNum > 0) {
            RectF cheek = new RectF(chessx * 80 + 28, chessy * 80 + 72, chessx * 80 + 88, chessy * 80 + 132);
            canvas.drawRoundRect(cheek, 10, 10, chesscheek);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isStart) {
            if (isEnClick) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    x = (int) event.getX();
                    y = (int) event.getY();
                    //判断点击的位置是否在棋盘之内
                    if ((x >= 40 && x <= 1045) && (y >= 95 && y <= 1090)) {
                        chessx = (x - 58) / 80;
                        chessy = (y - 102) / 80;
                        if (chessNum % 2 == 0) {
                            //检测当前位置是否右棋子
                            if (allChess[chessx][chessy] == null) {
                                allChess[chessx][chessy] = "black";


                                chessNum++;
                                blackChessNum++;

                            }
                        } else if (chessNum % 2 == 1) {
                            //检测当前位置是否有棋子
                            if (allChess[chessx][chessy] == null) {
                                allChess[chessx][chessy] = "white";


                                chessNum++;
                                whiteChessNum++;
                            }
                        }
                        checkFive();
                        invalidate();
                    }
                }
            }
        }
        else {
            MainActivity.myHandler.obtainMessage(0).sendToTarget();
        }
        if (chessNum > 169) {
            isEnClick = false;
        }


        if (isEnClick == false && event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isBlackWin) {
                MainActivity.myHandler.obtainMessage(2).sendToTarget();
            } else if (isWhiteWin) {
                MainActivity.myHandler.obtainMessage(1).sendToTarget();
            }
        }
        return true;
    }

    //定义方法用来检查是否有棋子连城五个
    public void checkFive() {

        String color = allChess[chessx][chessy];
        chessFiveCount = 1;
        //判断水平方向上是否有五个棋子连在一起
        if (chessx != 1 && chessx != 11) {//如果当chessx等于11 and 1时需要进行其他判断
            //首先向右边方向进行检测
            for (int i = 1; i < 5; i++) {
                if (x + i > 13) {
                    break;
                }
                if (color == allChess[chessx + i][chessy]) {
                    chessFiveCount++;
                } else {
                    break;
                }
            }
            if (chessFiveCount >= 5) {
                isEnClick = false;
                //判断为黑棋赢
                if (color == "black") {
                    isBlackWin = true;
                    isWhiteWin = false;
                }
                //判断为黑棋赢
                else if (color == "white") {
                    isBlackWin = false;
                    isWhiteWin = true;
                }
                chessFiveCount = 1;
            } else {
                for (int i = 1; i < 5; i++) {
                    if (chessx - i <= 0) {
                        break;
                    }
                    if (color == allChess[chessx - i][chessy]) {
                        chessFiveCount++;
                    } else {
                        break;
                    }
                    if (chessFiveCount >= 5) {
                        isEnClick = false;//失能下棋点击
                        //判断为黑棋赢
                        if (color == "black") {
                            isBlackWin = true;
                            isWhiteWin = false;
                        }
                        //判断为黑棋赢
                        else if (color == "white") {
                            isBlackWin = false;
                            isWhiteWin = true;
                        }
                        chessFiveCount = 1;
                        break;
                    }
                }
            }
            chessFiveCount = 1;
        } else if (chessx == 1) {//对chessx的位置进行重新判断

            for (int i = 1; i < 5; i++) {
                if (chessx + i >= 13) {
                    break;
                }
                if (color == allChess[chessx + i][chessy]) {
                    chessFiveCount++;
                } else {
                    break;
                }
                if (chessFiveCount > 3) {
                    isEnClick = false;//失能下棋点击
                    //判断为黑棋赢
                    if (color == "black") {
                        isBlackWin = true;
                        isWhiteWin = false;
                    }
                    //判断为黑棋赢
                    else if (color == "white") {
                        isBlackWin = false;
                        isWhiteWin = true;
                    }
                    chessFiveCount = 1;
                    break;
                }
            }
            chessFiveCount = 1;
        } else if (chessx == 11) {
            for (int i = 1; i < 5; i++) {
                if (chessx - i <= 0) {
                    break;
                }
                if (color == allChess[chessx - i][chessy]) {
                    chessFiveCount++;
                } else {
                    break;
                }
                if (chessFiveCount > 3) {
                    isEnClick = false;//失能下棋点击
                    //判断为黑棋赢
                    if (color == "black") {
                        isBlackWin = true;
                        isWhiteWin = false;
                    }
                    //判断为黑棋赢
                    else if (color == "white") {
                        isBlackWin = false;
                        isWhiteWin = true;
                    }
                    chessFiveCount = 1;
                    break;
                }
            }
            chessFiveCount = 1;
        }

        chessFiveCount = 1;

        //进行垂直方向上的判断
        if (chessy != 1 && chessy != 11) {
            for (int i = 1; i < 5; i++) {
                if (chessy + i >= 13) {
                    break;
                }
                if (color == allChess[chessx][chessy + i]) {
                    chessFiveCount++;
                } else {
                    break;
                }
            }

            if (chessFiveCount >= 5) {
                isEnClick = false;
                //判断为黑棋赢
                if (color == "black") {
                    isBlackWin = true;
                    isWhiteWin = false;
                }
                //判断为黑棋赢
                else if (color == "white") {
                    isBlackWin = false;
                    isWhiteWin = true;
                }
                chessFiveCount = 1;
            } else {
                for (int i = 1; i < 5; i++) {
                    if (chessy - i <= 0) {
                        break;
                    }
                    if (color == allChess[chessx][chessy - i]) {
                        chessFiveCount++;
                    } else {
                        break;
                    }
                    if (chessFiveCount >= 5) {
                        isEnClick = false;//失能下棋点击
                        //判断为黑棋赢
                        if (color == "black") {
                            isBlackWin = true;
                            isWhiteWin = false;
                        }
                        //判断为黑棋赢
                        else if (color == "white") {
                            isBlackWin = false;
                            isWhiteWin = true;
                        }
                        chessFiveCount = 1;
                        break;
                    }
                }
                chessFiveCount = 1;
            }
        } else if (chessy == 1) {
            for (int i = 1; i < 5; i++) {
                if (chessy + i >= 13) {
                    break;
                }
                if (color == allChess[chessx][chessy + i]) {
                    chessFiveCount++;
                } else {
                    break;
                }
                if (chessFiveCount > 3 && color == allChess[0][1]) {
                    isEnClick = false;//失能下棋点击
                    //判断为黑棋赢
                    if (color == "black") {
                        isBlackWin = true;
                        isWhiteWin = false;
                    }
                    //判断为黑棋赢
                    else if (color == "white") {
                        isBlackWin = false;
                        isWhiteWin = true;
                    }
                    chessFiveCount = 1;
                    break;
                }
            }
            chessFiveCount = 1;
        } else if (chessy == 11) {
            for (int i = 1; i < 5; i++) {
                if (chessy - i <= 0) {
                    break;
                }
                if (color == allChess[chessx][chessy - i]) {
                    chessFiveCount++;
                } else {
                    break;
                }
                if (chessFiveCount > 3 && color == allChess[1][12]) {
                    isEnClick = false;//失能下棋点击
                    //判断为黑棋赢
                    if (color == "black") {
                        isBlackWin = true;
                        isWhiteWin = false;
                    }
                    //判断为黑棋赢
                    else if (color == "white") {
                        isBlackWin = false;
                        isWhiteWin = true;
                    }
                    chessFiveCount = 1;
                    break;
                }
            }
            chessFiveCount = 1;
        }


        //对对角方向进行判断
        if ((chessx != 1 && chessx != 11) && (chessy != 1 && chessy != 11)) {
            for (int i = 1; i < 5; i++) {
                if (chessx + i >= 13 || chessy + i >= 13) {
                    break;
                }
                if (color == allChess[chessx + i][chessy + i]) {
                    chessFiveCount++;
                } else {
                    break;
                }
            }
            if (chessFiveCount >= 5) {
                isEnClick = false;//失能下棋点击
                //判断为黑棋赢
                if (color == "black") {
                    isBlackWin = true;
                    isWhiteWin = false;
                }
                //判断为黑棋赢
                else if (color == "white") {
                    isBlackWin = false;
                    isWhiteWin = true;
                }
                chessFiveCount = 1;
            } else {
                for (int i = 1; i < 5; i++) {
                    if (chessx - i <= 0 || chessy - i <= 0) {
                        break;
                    }
                    if (color == allChess[chessx - i][chessy - i]) {
                        chessFiveCount++;
                    } else {
                        break;
                    }
                    if (chessFiveCount >= 5) {
                        isEnClick = false;//失能下棋点击
                        //判断为黑棋赢
                        if (color == "black") {
                            isBlackWin = true;
                            isWhiteWin = false;
                        }
                        //判断为黑棋赢
                        else if (color == "white") {
                            isBlackWin = false;
                            isWhiteWin = true;
                        }
                        chessFiveCount = 1;
                        break;
                    }
                }
                chessFiveCount = 1;
            }

            chessFiveCount = 1;
            if (isEnClick) {
                for (int i = 1; i < 5; i++) {
                    if (chessx + i >= 13 || chessy - i <= 0) {
                        break;
                    }
                    if (color == allChess[chessx + i][chessy - i]) {
                        chessFiveCount++;
                    } else {
                        break;
                    }
                }

                if (chessFiveCount >= 5) {
                    isEnClick = false;//失能下棋点击
                    //判断为黑棋赢
                    if (color == "black") {
                        isBlackWin = true;
                        isWhiteWin = false;
                    }
                    //判断为黑棋赢
                    else if (color == "white") {
                        isBlackWin = false;
                        isWhiteWin = true;
                    }
                    chessFiveCount = 1;
                } else {
                    for (int i = 1; i < 5; i++) {
                        if (chessx - i <= 0 || chessy + i >= 13) {
                            break;
                        }
                        if (color == allChess[chessx - i][chessy + i]) {
                            chessFiveCount++;
                        } else {
                            break;
                        }
                        if (chessFiveCount >= 5) {
                            isEnClick = false;//失能下棋点击
                            //判断为黑棋赢
                            if (color == "black") {
                                isBlackWin = true;
                                isWhiteWin = false;
                            }
                            //判断为黑棋赢
                            else if (color == "white") {
                                isBlackWin = false;
                                isWhiteWin = true;
                            }
                            chessFiveCount = 1;
                            break;
                        }
                    }
                }
            }

        } else if (chessx == 1 && chessy == 1) {
            for (int i = 1; i < 5; i++) {
                if (chessx + i >= 13 || chessy >= 13) {
                    break;
                }
                if (color == allChess[chessx + i][chessy + i]) {
                    chessFiveCount++;
                } else {
                    break;
                }
                if (color == allChess[0][0] && chessFiveCount > 3) {
                    isEnClick = false;//失能下棋点击
                    //判断为黑棋赢
                    if (color == "black") {
                        isBlackWin = true;
                        isWhiteWin = false;
                    }
                    //判断为黑棋赢
                    else if (color == "white") {
                        isBlackWin = false;
                        isWhiteWin = true;
                    }
                    chessFiveCount = 1;
                    break;
                }
            }
        } else if (chessx == 11 && chessy == 11) {
            for (int i = 1; i < 5; i++) {
                if (chessx - i <= 0 && chessy - i <= 0) {
                    break;
                }
                if (color == allChess[chessx - i][chessy - i]) {
                    chessFiveCount++;
                } else {
                    break;
                }
                if (color == allChess[12][12] && chessFiveCount > 3) {
                    isEnClick = false;//失能下棋点击
                    //判断为黑棋赢
                    if (color == "black") {
                        isBlackWin = true;
                        isWhiteWin = false;
                    }
                    //判断为黑棋赢
                    else if (color == "white") {
                        isBlackWin = false;
                        isWhiteWin = true;
                    }
                    chessFiveCount = 1;
                    break;
                }
            }
            chessFiveCount = 1;
        } else if (chessx == 1 && chessy == 11) {
            for (int i = 1; i < 5; i++) {
                if (chessx + i >= 13 || chessy - i <= 0) {
                    break;
                }
                if (color == allChess[chessx + i][chessy - i]) {
                    chessFiveCount++;
                } else {
                    break;
                }
                if (color == allChess[0][12] && chessFiveCount > 3) {
                    isEnClick = false;//失能下棋点击
                    //判断为黑棋赢
                    if (color == "black") {
                        isBlackWin = true;
                        isWhiteWin = false;
                    }
                    //判断为黑棋赢
                    else if (color == "white") {
                        isBlackWin = false;
                        isWhiteWin = true;
                    }
                    chessFiveCount = 1;
                    break;
                }
            }
        } else if (chessx == 11 && chessy == 1) {
            for (int i = 1; i < 5; i++) {
                if (chessx - i <= 0 || chessy + i >= 13) {
                    break;
                }
                if (color == allChess[chessx - i][chessy + i]) {
                    chessFiveCount++;
                } else {
                    break;
                }
                if (color == allChess[12][0] && chessFiveCount > 3) {
                    isEnClick = false;//失能下棋点击
                    //判断为黑棋赢
                    if (color == "black") {
                        isBlackWin = true;
                        isWhiteWin = false;
                    }
                    //判断为黑棋赢
                    else if (color == "white") {
                        isBlackWin = false;
                        isWhiteWin = true;
                    }
                    chessFiveCount = 1;
                    break;
                }
            }
            chessFiveCount = 1;
        }
        if (isWhiteWin) {
            MainActivity.myHandler.obtainMessage(1).sendToTarget();
        } else if (isBlackWin) {
            MainActivity.myHandler.obtainMessage(2).sendToTarget();
        }
    }
}