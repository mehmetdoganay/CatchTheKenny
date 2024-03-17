package com.mehmetdoganay.catchthekenny;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    TextView textView2;
    GridLayout gridLayout;
    Runnable runnable;
    Handler handler;
    int scoreCount = 0;
    int previousRandomNumber = -1;
    boolean isCountdownFinished = false;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        // Alert Gösteririmi start
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("New game");
        alert.setPositiveButton("Start", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timer(10);
                startGame();
            }
        } );
        alert.show();
        // Alert Gösteririmi end

    }

    public void startGame()
    {
        scoreCount = 0;
        textView.setText("Score: " + scoreCount);
        Random random = new Random();

        String[] imageArr= {"image","image2","image3","image4","image5","image6","image7","image8","image9"};

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (scoreCount < 500 || isCountdownFinished == true)
                {
                    if (imageView != null) {
                        imageView.setVisibility(View.INVISIBLE);
                    }

                    int randomNumber;
                    do
                    {
                        randomNumber = random.nextInt(imageArr.length);
                    }
                    while (randomNumber == previousRandomNumber);
                    {
                        random.nextInt(imageArr.length);
                        int resId = getResources().getIdentifier(imageArr[randomNumber],"id",getPackageName());
                        imageView = findViewById(resId);
                        imageView.setVisibility(View.VISIBLE);

                        changeBackgroundColor(scoreCount);
                        handler.postDelayed(runnable, getDelay(scoreCount));
                    }

                    previousRandomNumber = randomNumber;
                }
                else {
                    endGame();
                }
            }
        };
        handler.post(runnable);

    }
    public void imageClick(View view) {
        scoreCount +=5;
        textView.setText("Score: " + scoreCount);
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 0);
    }

    public void endGame()
    {


        handler.removeCallbacks(runnable);

        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("New game");
        alert.setMessage("Score: " + scoreCount);

        scoreCount = 0;
        previousRandomNumber = -1;

        alert.setPositiveButton("Start", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timer(10);
                startGame();
            }
        } );

        alert.setPositiveButton("30 seconds", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               timer(30);
                startGame();
            }
        });
        alert.setNeutralButton("60 seconds", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timer(60);
                startGame();
            }
        });

        alert.show();

        imageView.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(),"Oyun bitti tebrikler.", Toast.LENGTH_LONG).show();
    }

    public void changeBackgroundColor(int score) {
        int color;
        if (score >= 0 && score <= 100) {
            color = getResources().getColor(com.google.android.material.R.color.abc_tint_default);
        } else if (score > 100 && score <= 200) {
            color = getResources().getColor(com.google.android.material.R.color.button_material_light);
        } else if (score > 200 && score <= 300) {
            color = getResources().getColor(com.google.android.material.R.color.design_dark_default_color_on_secondary);
        } else if (score > 300 && score <= 400) {
            color = getResources().getColor(com.google.android.material.R.color.m3_button_foreground_color_selector);
        } else {
            color = getResources().getColor(com.google.android.material.R.color.design_default_color_on_surface);
        }
        gridLayout.setBackgroundColor(color);
    }

    public int getDelay(int score) {
        if (score >= 0 && score <= 100) {
            return 800;
        } else if (score > 100 && score <= 200) {
            return 700;
        } else if (score > 200 && score <= 300) {
            return 600;
        } else if (score > 300 && score <= 400) {
            return 500;
        } else {
            return 400;
        }
    }

    public void timer(int second)
    {
        long millisInFuture = 1 * second * 1000; // 1 dakika (1 dakika * 60 saniye * 1000 milisaniye)
        long countDownInterval = 1000;

        new CountDownTimer(millisInFuture,countDownInterval)
        {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                textView2.setText("Timer: " + secondsRemaining);
            }

            @Override
            public void onFinish() {
                isCountdownFinished = true;
                endGame();
            }
        }.start();

    }
}



