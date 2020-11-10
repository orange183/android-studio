
package com.example.gobang;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import static com.example.gobang.Cheek.cheekPaint;
import static com.example.gobang.ChessBoard.allChess;
import static com.example.gobang.ChessBoard.blackChessNum;
import static com.example.gobang.ChessBoard.chessBoardPaint;
import static com.example.gobang.ChessBoard.chessNum;
import static com.example.gobang.ChessBoard.chessx;
import static com.example.gobang.ChessBoard.chessy;
import static com.example.gobang.ChessBoard.isBlackWin;
import static com.example.gobang.ChessBoard.isEnClick;
import static com.example.gobang.ChessBoard.isWhiteWin;
import static com.example.gobang.ChessBoard.textpain;
import static com.example.gobang.ChessBoard.whiteChessNum;
import static com.example.gobang.FallingView.sonwrectF1Paint;

public class MainActivity extends Activity implements SeekBar.OnSeekBarChangeListener {
    ChessBoard chessborad;
    Cheek cheek;
    private SeekBar sekbar;
    private Button back;
    private Button restart;
    private Button start;
    public static boolean blackWin = false;
    public static boolean whiteWin = false;
    private static Context mContext;
    public static int setAlpha = 0;
    private MediaPlayer mediaPlayer;

    public static boolean isStart = false;
    private FallingView snow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化画笔颜色
        cheekPaint.setColor(Color.rgb(0, (int) (152  / 1.2), 199 ));
        chessBoardPaint.setColor(Color.rgb( 0, (int) (152 / 1.2), 199 ));
        textpain.setColor(Color.rgb( 0, (int) (152 / 1.2), 199 ));
        sonwrectF1Paint.setColor(Color.rgb(0, (int) (152  / 1.2), 199 ));


        chessborad = findViewById(R.id.chessboard);
        cheek = findViewById(R.id.cheek);
        sekbar = (SeekBar) findViewById(R.id.sekbar);
        back = (Button) findViewById(R.id.back);
        restart = (Button) findViewById(R.id.restart);
        start = (Button) findViewById(R.id.start);
        snow = findViewById(R.id.snow);

        back.setTextColor(Color.rgb( 0, (int) (152  / 1.2), 199 ));
        start.setTextColor(Color.rgb(0, (int) (152  / 1.2), 199 ));
        restart.setTextColor(Color.rgb( 0, (int) (152  / 1.2), 199 ));
        sekbar.setOnSeekBarChangeListener(this);

        mContext = this;

    }


    public void Start(View view) {
        isStart = !isStart;
        if (isStart) {
            snow.invalidate();
            start.setText("暂停");
            try {
                mediaPlayer = MediaPlayer.create(this, R.raw.bg);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                        mediaPlayer.setLooping(isStart);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            start.setText("开始");
            mediaPlayer.stop();
        }

    }

    public void Back(View view) {
        allChess[chessx][chessy] = null;
        chessborad.invalidate();
    }


    //重开按钮点击事件
    public void Restart(View view) {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                allChess[i][j] = null;
            }
        }
        blackChessNum = 0;
        whiteChessNum = 0;
        chessNum = 0;//进行棋子总数清零
        isEnClick = true;
        isBlackWin = isWhiteWin = false;
        chessborad.invalidate();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        setAlpha = i;
        cheekPaint.setColor(Color.rgb(i * 2, (int) (152 - i / 1.2), 199 - i));
        chessBoardPaint.setColor(Color.rgb(i * 2, (int) (152 - i / 1.2), 199 - i));
        textpain.setColor(Color.rgb(i * 2, (int) (152 - i / 1.2), 199 - i));
        sonwrectF1Paint.setColor(Color.rgb(i * 2, (int) (152 - i / 1.2), 199 - i));
        back.setTextColor(Color.rgb(i * 2, (int) (152 - i / 1.2), 199 - i));
        start.setTextColor(Color.rgb(i * 2, (int) (152 - i / 1.2), 199 - i));
        restart.setTextColor(Color.rgb(i * 2, (int) (152 - i / 1.2), 199 - i));

        chessborad.invalidate();
        cheek.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    public static Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                whiteWin();
            } else if (msg.what == 2) {
                blackWin();

            }else if(msg.what==0){
                pleaseStart();
            }
        }
    };

    private static void blackWin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("GoBang");
        builder.setIcon(R.drawable.icon);
        builder.setMessage("黑方获胜,请点击重开按钮重新开始");
        builder.show();
    }

    private static void whiteWin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("GoBang");
        builder.setIcon(R.drawable.icon);
        builder.setMessage("白方获胜,请点击重开按钮重新开始");
        builder.show();
    }
    private static void pleaseStart(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("GoBang");
        builder.setIcon(R.drawable.icon);
        builder.setMessage("请点击开始按钮开始下棋");
        builder.show();
    }
}
