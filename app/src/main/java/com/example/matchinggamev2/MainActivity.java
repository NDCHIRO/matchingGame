package com.example.matchinggamev2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //LinearLayout linear = new LinearLayout(this);
    Button[] btn=new Button[8];
    int []imagesValues=new int[]{0,1,2,3,0,1,2,3};
    //ArrayList<Button> btn = new ArrayList<Button>(8);
    List<Integer> drawImages =
            Arrays.asList(R.drawable.orange, R.drawable.banana, R.drawable.lemon, R.drawable.apple,
                    R.drawable.orange, R.drawable.banana, R.drawable.lemon, R.drawable.apple);

    List<String> greatWords =Arrays.asList("nice !","well play", "great work",
            "you are so great","how good you are !!","amazing work","perfect work");
    int i=0;
    int firstClicked=-1;
    public int gameeEnded=0;
    int secondClicked=-1;
    int noOfClicked=0;
    boolean first=false;
    Button firstBtn=null;
    Button secondBtn=null;
    Button btnV=null;
    Button btnUseless;
    public String x="card";


    MediaPlayer apple;
    MediaPlayer banana;
    MediaPlayer lemon;
    MediaPlayer orange;
    TextView time;
    String end;
     Timer timer;
    Intent gameEndedIntent;
     int increment=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Collections.shuffle(drawImages);


        end=getIntent().getStringExtra("message")+"";
        if(end.equals("end")) {
            onDestroy();
        }


        setBtnsId();
        setMedia();
        time=findViewById(R.id.textViewTimer);
        //txtViewGameEnded=findViewById(R.id.textViewGameEnded);
        setTime();
        for (i=0;i<=7;i++)
            setTextCardBack(i);

        for (i=0;i<=7;i++)
            btn[i].setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {

        btnV = (Button) v;
        if(noOfClicked<2) {
            if (btnV.getText().equals("cardBack")) {
                //flip the question image and show a fruit image
                setFruitImageAndText();
                //first button clicked
                if (first == false) {
                    first = true;
                    firstBtn = (Button) v;
                    playAudio((firstBtn));
                    firstBtn.setClickable(false);
                    noOfClicked++;
                } else {
                    secondBtn = (Button) v;
                    secondBtn.setClickable(false);
                    noOfClicked++;
                    playAudio(secondBtn);
                    //if they are the same image
                    if (secondBtn.getText().equals(firstBtn.getText())) {
                        firstBtn.setVisibility(View.VISIBLE);
                        secondBtn.setVisibility(View.VISIBLE);
                        firstBtn.setClickable(false);
                        secondBtn.setClickable(false);
                        delayForCorrectBtns();
                        first = false;
                        gameeEnded++;
                        if(gameeEnded==4)
                        {
                            Intent intent=new Intent(MainActivity.this, gameFinished.class);
                            intent.putExtra("message","you won, Good job!!");
                            startActivity(intent);
                        }
                    }
                    //different images
                    else {
                        normalDelay();
                        first = false;
                    }
                }
            }
            else {
                setQuestionBackground(btnV);
                btnV.setText("cardBack");
                first = false;
                noOfClicked=0;
            }
        }
        else
        {
            setQuestionBackground(btnV);
            btnV.setText("cardBack");
        }

    }


    public void setTime()
    {
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                time.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Intent intent=new Intent(MainActivity.this, gameFinished.class);
                intent.putExtra("message","you lost but nice try");
                startActivity(intent);

            }
        }.start();
    }


    public void setMedia()
    {
        apple = MediaPlayer.create(MainActivity.this,R.raw.apple);
        banana= MediaPlayer.create(MainActivity.this,R.raw.banana01);
        lemon= MediaPlayer.create(MainActivity.this,R.raw.lemon);
        orange= MediaPlayer.create(MainActivity.this,R.raw.orange);
    }


    public void setBtnsId()
    {
        btn[0]=findViewById(R.id.button0);
        btn[1]=findViewById(R.id.button1);
        btn[2]=findViewById(R.id.button2);
        btn[3]=findViewById(R.id.button3);
        btn[4]=findViewById(R.id.button4);
        btn[5]=findViewById(R.id.button5);
        btn[6]=findViewById(R.id.button6);
        btn[7]=findViewById(R.id.button7);
    }


    public void setFruitImageAndText()
    {
        switch (btnV.getId()) {
            case R.id.button0:
                btn[0].setBackgroundResource(drawImages.get(0));
                setTextCard(0);
                break;

            case R.id.button1:
                btn[1].setBackgroundResource(drawImages.get(1));
                setTextCard(1);
                break;

            case R.id.button2:
                btn[2].setBackgroundResource(drawImages.get(2));
                setTextCard(2);
                break;

            case R.id.button3:
                btn[3].setBackgroundResource(drawImages.get(3));
                setTextCard(3);
                break;

            case R.id.button4:
                btn[4].setBackgroundResource(drawImages.get(4));
                setTextCard(4);
                break;

            case R.id.button5:
                btn[5].setBackgroundResource(drawImages.get(5));
                setTextCard(5);
                break;

            case R.id.button6:
                btn[6].setBackgroundResource(drawImages.get(6));
                setTextCard(6);
                break;

            case R.id.button7:
                btn[7].setBackgroundResource(drawImages.get(7));
                setTextCard(7);
                break;
        }
    }


    public void setTextCard(int x)
    {
        btn[x].setText("card"+drawImages.get(x));
        btn[x].setTextSize(0.0f);
    }


    public void setTextCardBack(int x)
    {
        btn[x].setText("cardBack");
        btn[x].setTextSize(0.0f);
    }

    public void setQuestionBackground(Button bt)
    {
        bt.setBackgroundResource(R.drawable.question);
    }


    public void delayForCorrectBtns()
    {
        Collections.shuffle(greatWords);
        Runnable r = new Runnable() {
            @Override
            public void run(){
                noOfClicked = 0;
                firstBtn.setVisibility(View.INVISIBLE);
                secondBtn.setVisibility(View.INVISIBLE);
            }
        };
        Handler h = new Handler();
        h.postDelayed(r, 1000); // <-- the "1000" is the delay time in miliseconds.
        Toast.makeText(getApplicationContext(),greatWords.get(0),Toast.LENGTH_SHORT).show();
    }


    public void normalDelay()
    {
        Runnable r = new Runnable() {
            @Override
            public void run(){
                noOfClicked=0;
                firstBtn.setBackgroundResource(R.drawable.question);
                secondBtn.setBackgroundResource(R.drawable.question);
                firstBtn.setText("cardBack");
                secondBtn.setText("cardBack");
                firstBtn.setClickable(true);
                secondBtn.setClickable(true);
            }
        };
        Handler h = new Handler();
        h.postDelayed(r, 1000); // <-- the "1000" is the delay time in miliseconds.
        Toast.makeText(getApplicationContext(),"wrong answer",Toast.LENGTH_SHORT).show();
    }


    public void playAudio(Button btn)
    {
        x=btn.getText().charAt(btn.length()-2)+btn.getText().charAt(btn.length()-1)+"";
        switch (x)
        {
            case ("104"):
                apple.start();
                break;
            case("105"):
                banana.start();
                break;
            case("102"):
                orange.start();
                break;
            case("107"):
                lemon.start();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }
}