package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Image;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import android.os.Handler;

import com.google.android.gms.maps.model.LatLng;

public class monsterfight extends AppCompatActivity implements DialogInterface.OnClickListener {

    float damage;
    String monsterId;
    int go = 0;
    String getWeapon="無";
    String item[] = {"寶劍", "負重器", "隱形斗篷"};

    CountDownTimer countDownTimer;
    int level;
    LatLng P1;
    LatLng P2;
    LatLng P3;

    float speed;
    int success = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mosterfight);

        Intent it =getIntent();

        damage = it.getFloatExtra("戰鬥力",0);
        monsterId = it.getStringExtra("monster");
        level = it.getIntExtra("level",0);
        speed = it.getFloatExtra("speed",0.0f);
        if(speed >= 1)
            damage*=speed;

        //!!!!!!!!!!!!!!!!



        TextView user_damage_txv = (TextView) findViewById(R.id.user_damage_txv);
        String damage_str = String.format("%.1f",damage);
        user_damage_txv.setText(damage_str);
        fight();
        //Toast.makeText(this,monsterId,Toast.LENGTH_SHORT).show();

        ImageView fight = findViewById(R.id.start);
        Button escape = findViewById(R.id.escape_btn);
        fight.setVisibility(View.VISIBLE);
        escape.setVisibility(View.GONE);

        setView();
        TextView timerTextView = findViewById(R.id.timerTextView);
        setView();
        Toast.makeText(this,"10秒內擊殺怪物",Toast.LENGTH_SHORT).show();

        countDownTimer = new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerTextView.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerTextView.setText("結束");
                end();
            }
        }.start();

        setView();


    }



    public void setView()
    {
        ImageView level0 = findViewById(R.id.level0);
        ImageView level3 = findViewById(R.id.level3);
        ImageView level6 = findViewById(R.id.level6);
        ImageView level9 = findViewById(R.id.levelnine);

        if(level < 3)
        {
            level0.setVisibility(View.VISIBLE);
        }
        else
            level0.setVisibility(View.INVISIBLE);

        if(level >= 3 && level < 6)
        {
            level3.setVisibility(View.VISIBLE);
        }
        else
            level3.setVisibility(View.INVISIBLE);

        if(level >= 6 && level < 9)
        {
            level6.setVisibility(View.VISIBLE);
        }
        else
            level6.setVisibility(View.INVISIBLE);

        if(level >= 9)
        {
            level9.setVisibility(View.VISIBLE);
        }
        else
            level9.setVisibility(View.INVISIBLE);
    }
int timedown = 0;
    public void end(){
        timedown = 1;

        Toast.makeText(this,"你被怪物拖進深淵",Toast.LENGTH_LONG).show();
        win = false;
        solve();
        finish();
    }

    @SuppressLint("MissingSuperCall")
    public void onBackPressed() {
        //super.onBackPressed();

    }

    public void escape(View view)
    {
        finish();
    }
    public void fight()
    {
        TextView mnst = (TextView) findViewById(R.id.monster_damage_txv);
        ImageView F = (ImageView) findViewById(R.id.F);
        ImageView E = (ImageView) findViewById(R.id.E);
        ImageView D = (ImageView) findViewById(R.id.D);
        ImageView C = (ImageView) findViewById(R.id.C);
        ImageView B = (ImageView) findViewById(R.id.B);
        ImageView A = (ImageView) findViewById(R.id.A);

        if(monsterId.equals("F"))
        {
            F.setVisibility(View.VISIBLE);
            mnst.setText("80");
        }
        else
            F.setVisibility(View.GONE);

        if(monsterId.equals("E"))
        {
            //Toast.makeText(this,"EE",Toast.LENGTH_SHORT).show();
            E.setVisibility(View.VISIBLE);
            mnst.setText("18");
        }
        else
            E.setVisibility(View.GONE);

        if(monsterId.equals("D"))
        {
            D.setVisibility(View.VISIBLE);
            mnst.setText("55");
        }
        else
            D.setVisibility(View.GONE);

        if(monsterId.equals("C"))
        {
            C.setVisibility(View.VISIBLE);
            mnst.setText("500");
        }
        else
            C.setVisibility(View.GONE);

        if(monsterId.equals("B"))
        {
            B.setVisibility(View.VISIBLE);
            mnst.setText("150");
        }
        else
            B.setVisibility(View.GONE);

        if(monsterId.equals("A"))
        {
            A.setVisibility(View.VISIBLE);
            mnst.setText("100");
        }
        else
            A.setVisibility(View.GONE);

    }

    boolean win = false;

    boolean check = false;
    public void start_fight(View view) {
        TextView timerTextView = (TextView) findViewById(R.id.timerTextView);
        countDownTimer.cancel();
        //計時



            ImageView user ;

            if(level < 3)
                user = (ImageView)findViewById(R.id.level0);
            else if(level < 6)
                user = (ImageView)findViewById(R.id.level3);
            else if(level < 9)
                user = (ImageView)findViewById(R.id.level6);
            else
                user = (ImageView)findViewById(R.id.levelnine);




            ImageView F = (ImageView) findViewById(R.id.F);
            ImageView E = (ImageView) findViewById(R.id.E);
            ImageView D = (ImageView) findViewById(R.id.D);
            ImageView C = (ImageView) findViewById(R.id.C);
            ImageView B = (ImageView) findViewById(R.id.B);
            ImageView A = (ImageView) findViewById(R.id.A);
            Random random = new Random();
            int randomNumber = random.nextInt(100)+1;
            Random random2 = new Random();
            int type = random.nextInt(3)+1;
            if(monsterId.equals("A"))
            {
                if(damage < 100)
                {
                    moveToTopLeftAndRotate(A);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            user.setVisibility(View.GONE);
                            win = true;
                            go = 1;solve();
                        }
                    }, 2000);



                }
                else
                {
                    //Toast.makeText(this,"你贏了",Toast.LENGTH_SHORT).show();

                    moveImageAndReturnResult1(user);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            A.setVisibility(View.GONE);
                            win = false;
                            go = 1;solve();
                        }
                    }, 2000);

                    if(type == 1)
                    {
                        getWeapon = "寶劍";
                    }
                    if(type == 2)
                    {
                        getWeapon = "負重器";
                    }
                    if(type == 3)
                    {
                        getWeapon = "隱形斗篷";
                    }
                }
            }
            if(monsterId.equals("B"))//70%掉落
            {
                if(damage < 150)
                {
                    moveToTopLeftAndRotate(B);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            user.setVisibility(View.GONE);
                            win = true;
                            go = 1;solve();
                        }
                    }, 2000);
                }
                else
                {
                    //Toast.makeText(this,"你贏了",Toast.LENGTH_SHORT).show();
                    moveImageAndReturnResult1(user);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            B.setVisibility(View.GONE);
                            win = false;
                            go = 1;solve();
                        }
                    }, 2000);
                    if(randomNumber>0)
                    {
                        if(type == 1)
                        {
                            getWeapon = "寶劍";
                        }
                        if(type == 2)
                        {
                            getWeapon = "負重器";
                        }
                        if(type == 3)
                        {
                            getWeapon = "隱形斗篷";
                        }
                    }
                }
            }
            if(monsterId.equals("C"))
            {
                if(damage < 500)
                {
                    moveToTopLeftAndRotate(C);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            user.setVisibility(View.GONE);
                            win = true;
                            go = 1;solve();
                        }
                    }, 2000);
                }
                else
                {
                    //Toast.makeText(this,"你贏了",Toast.LENGTH_SHORT).show();
                    moveImageAndReturnResult1(user);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            C.setVisibility(View.GONE);
                            win = false;
                            go = 1;solve();
                        }
                    }, 2000);
                }

            }
            if(monsterId.equals("D"))//50%掉落
            {
                if(damage < 55)
                {
                    moveToTopLeftAndRotate(D);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            user.setVisibility(View.GONE);
                            win = true;
                            go = 1;solve();
                        }
                    }, 2000);
                }
                else
                {
                   // Toast.makeText(this,"你贏了",Toast.LENGTH_SHORT).show();
                    moveImageAndReturnResult1(user);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            D.setVisibility(View.GONE);
                            win = false;
                            go = 1;solve();
                        }
                    }, 2000);
                    if(randomNumber > 50)
                    {
                        if(type == 1)
                        {
                            getWeapon = "寶劍";
                        }
                        if(type == 2)
                        {
                            getWeapon = "負重器";
                        }
                        if(type == 3)
                        {
                            getWeapon = "隱形斗篷";
                        }
                    }
                }
            }
            if(monsterId.equals("E"))//30%掉落
            {
                if(damage < 18)
                {
                    moveToTopLeftAndRotate(E);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            user.setVisibility(View.GONE);
                            win = true;
                            go = 1;solve();
                        }
                    }, 2000);
                }
                else
                {
                    //Toast.makeText(this,"你贏了",Toast.LENGTH_SHORT).show();
                    moveImageAndReturnResult1(user);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            E.setVisibility(View.GONE);
                            win = false;
                            go = 1;solve();
                        }
                    }, 2000);
                    if(randomNumber > 0)
                    {
                        if(type == 1)
                        {
                            getWeapon = "寶劍";
                        }
                        if(type == 2)
                        {
                            getWeapon = "負重器";
                        }
                        if(type == 3)
                        {
                            getWeapon = "隱形斗篷";
                        }
                    }
                }
            }
            if(monsterId.equals("F"))//15%掉落
            {
                if(damage < 80)
                {
                    moveToTopLeftAndRotate(F);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            user.setVisibility(View.GONE);
                            win = true;
                            go = 1;
                            solve();
                        }
                    }, 2000);
                }
                else
                {
                    //Toast.makeText(this,"你贏了",Toast.LENGTH_SHORT).show();
                    moveImageAndReturnResult1(user);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            F.setVisibility(View.GONE);
                            win = false;
                            go = 1;
                            solve();
                        }
                    }, 2000);
                    if(randomNumber > 0)
                    {
                        if(type == 1)
                        {
                            getWeapon = "寶劍";
                        }
                        if(type == 2)
                        {
                            getWeapon = "負重器";
                        }
                        if(type == 3)
                        {
                            getWeapon = "隱形斗篷";
                        }
                    }
                }
            }










        }

        public void solve()
        {
            countDownTimer.cancel();
            if(go == 1) {
                countDownTimer.cancel();
                if (win == false) {


                    countDownTimer.cancel();


                    AlertDialog.Builder monster = new AlertDialog.Builder(this);
                    monster.setMessage("你獲得 :" + getWeapon);
                    monster.setTitle("你贏了");
                    //monster.setNegativeButton("確認",this);
                    monster.setPositiveButton("確認", this);
                    monster.setCancelable(false);
                    monster.show();
                    success = 1;
                    Intent returnIntent = new Intent();
                    if(success == 1)
                        returnIntent.putExtra("rewardItem", getWeapon);
                    else
                        returnIntent.putExtra("rewardItem", "死了");
                    returnIntent.putExtra("success", success);
                    setResult(RESULT_OK, returnIntent);


                } else {
                    //Toast.makeText(this, "你死了!!!!!!!!!噴裝!!!!!!!", Toast.LENGTH_LONG).show();
                    countDownTimer.cancel();

                    //ImageView fight = findViewById(R.id.start);
                    //Button escape = findViewById(R.id.escape_btn);
                    //fight.setVisibility(View.GONE);
                    //escape.setVisibility(View.VISIBLE);
                    success = 0;
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("rewardItem", "");
                    returnIntent.putExtra("success", success);
                    setResult(RESULT_OK, returnIntent);
                    finish();

                }
            }
        }



    private void moveImageAndReturnResult1(ImageView image) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;

        float xTranslation = screenWidth - image.getWidth() - image.getX();
        float yTranslation = screenHeight - image.getHeight() - image.getY();

        ObjectAnimator xAnimator = ObjectAnimator.ofFloat(image, "translationX", image.getTranslationX(), xTranslation);
        ObjectAnimator yAnimator = ObjectAnimator.ofFloat(image, "translationY", image.getTranslationY(), yTranslation);

        AnimatorSet moveAnimatorSet = new AnimatorSet();
        moveAnimatorSet.playTogether(xAnimator, yAnimator);
        moveAnimatorSet.setDuration(1000);

        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(image, "rotation", 0f, 3600f);
        rotateAnimator.setDuration(1000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(moveAnimatorSet, rotateAnimator);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                onImageMovedAndRotated();
            }
        });

        animatorSet.start();
    }

    private void onImageMovedAndRotated() {
        vibrate();
    }





    private void moveToTopLeftAndRotate(final ImageView image) {
        ObjectAnimator xAnimator = ObjectAnimator.ofFloat(image, "translationX", image.getTranslationX(), 0);
        ObjectAnimator yAnimator = ObjectAnimator.ofFloat(image, "translationY", image.getTranslationY(), 0);

        AnimatorSet moveAnimatorSet = new AnimatorSet();
        moveAnimatorSet.playTogether(xAnimator, yAnimator);
        moveAnimatorSet.setDuration(1000);


        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(image, "rotation", 0f, 3600f);
        rotateAnimator.setDuration(1000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(moveAnimatorSet, rotateAnimator);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                onImageMovedAndRotated2();
            }
        });

        animatorSet.start();
    }

    private void onImageMovedAndRotated2() {
        vibrate();

    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == DialogInterface.BUTTON_POSITIVE) {
            finish();
        }
        if(which == DialogInterface.BUTTON_NEGATIVE){

        }
    }

    public void vibrate()
    {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (vibrator != null && vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                VibrationEffect effect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE);
                vibrator.vibrate(effect);
            } else {
                vibrator.vibrate(1000);
            }
        }
        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
        toneGen1.startTone(ToneGenerator.TONE_PROP_BEEP);
    }

    public void delay()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, 2000);
    }


}