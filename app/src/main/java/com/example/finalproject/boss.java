package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class boss extends AppCompatActivity implements DialogInterface.OnClickListener, SensorEventListener {

    boolean isDialogShown = false;
    ImageView bullet0;
    ImageView bullet1;
    ImageView bullet2;
    float[] a = null;
    SensorManager sm;
    Sensor sr1;
    String[] BossId = {"拉機車", "123炒飯", "觸手大章魚"};
    //private int[] BossHp = {33333, 100, 99999};
    int[] BossHp_a = {3333, 12332, 9999};
    float chr_str = 1000;
    int now_boss;
    TextView tv_bossdata;
   // private TextView tv_test;
    int i = 0;
    float []bulletSpeed_hr= {0,0,0};
    float []bulletSpeed_vt={0,0,0};
    int win = 3;
    int level=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss);
        //tv_test = findViewById(R.id.tv_test);
        bullet0 = findViewById(R.id.bullet1);
        bullet1 = findViewById(R.id.bullet2);
        bullet2 = findViewById(R.id.bullet3);

        Intent it = getIntent();
        chr_str = it.getFloatExtra("攻擊力",0);
        now_boss=it.getIntExtra("boss",1);
        now_boss-=1;

        level = it.getIntExtra("level",0);

        setRandomBulletPosition(bullet0,0);
        setRandomBulletSize(bullet0);
        setRandomBulletPosition(bullet1,1);
        setRandomBulletSize(bullet1);
        setRandomBulletPosition(bullet2,2);
        setRandomBulletSize(bullet2);


        tv_bossdata = findViewById(R.id.tv_bossdata);
        tv_bossdata.setText("HP："+String.valueOf(BossHp_a[now_boss]));


        // 使用 Handler 執行定期任務
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateBulletPosition(bullet0,0);
                updateBulletPosition(bullet1,1);
                updateBulletPosition(bullet2,2);
                if( i == 0){
                startRotation(bullet0);
                startRotation(bullet1);
                startRotation(bullet2);}
                i++;
                //tv_test.setText(""+i);
                handler.postDelayed(this, 10); // 1秒後再次執行

            }
        }, 1500); // 第一次執行的延遲時間

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sr1 = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.unregisterListener(this);

        ImageView chr = findViewById(R.id.levelzero);
        ConstraintLayout.LayoutParams chrParams = (ConstraintLayout.LayoutParams) chr.getLayoutParams();
        chrParams.leftMargin = 100;
        chrParams.topMargin = 100;
        chr.setLayoutParams(chrParams);

        setView();
    }

    public void setView()
    {
        if(now_boss == 0) {
            bullet0 = findViewById(R.id.bullet1);
            bullet1 = findViewById(R.id.bullet2);
            bullet2 = findViewById(R.id.bullet3);
        }
        if(now_boss == 1) {
            bullet0 = findViewById(R.id.bullet4);
            bullet1 = findViewById(R.id.bullet5);
            bullet2 = findViewById(R.id.bullet6);
        }
        if(now_boss == 2) {
            bullet0 = findViewById(R.id.bullet7);
            bullet1 = findViewById(R.id.bullet8);
            bullet2 = findViewById(R.id.bullet9);
        }
        bullet0.setVisibility(View.VISIBLE);
        bullet1.setVisibility(View.VISIBLE);
        bullet2.setVisibility(View.VISIBLE);

        ImageView chr;
        if(level <3)
            chr = findViewById(R.id.levelzero);
        else if(level < 6)
            chr = findViewById(R.id.level3);
        else if(level < 9)
            chr = findViewById(R.id.levelsix);
        else
            chr = findViewById(R.id.level9);

        chr.setVisibility(View.VISIBLE);



        ImageView Boss;
        if(now_boss == 0)
            Boss = findViewById(R.id.bossone);
        else if(now_boss == 1)
            Boss = findViewById(R.id.bosstwo);
        else
            Boss = findViewById(R.id.bossthree);
        Boss.setVisibility(View.VISIBLE);
    }


    public void attack(View v) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        int HP_tmp = BossHp_a[now_boss] - (int) chr_str;
        BossHp_a[now_boss] = HP_tmp;
        tv_bossdata = findViewById(R.id.tv_bossdata);
        tv_bossdata.setText("HP：" + String.valueOf(BossHp_a[now_boss]));

        // 触发震动
        if (vibrator != null) {
            // 震动 500 毫秒
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                // deprecated in API 26
                vibrator.vibrate(50);
            }
        }

        if (BossHp_a[now_boss] < 0) {
            bulletSpeed_vt[0] = 0; bulletSpeed_vt[1] = 0; bulletSpeed_vt[2] = 0;
            bulletSpeed_hr[0] = 0; bulletSpeed_hr[1] = 0; bulletSpeed_hr[2] = 0;

            new AlertDialog.Builder(this)
                    .setTitle(BossId[now_boss] + "已被消滅")
                    .setMessage("按下確定返回")
                    .setPositiveButton("確定", this)
                    .setCancelable(false)
                    .show();

            win = 1;
            Intent returnIntent = new Intent();
            returnIntent.putExtra("win", win); // 添加得到的道具作为结果
            returnIntent.putExtra("boss", now_boss);
            setResult(RESULT_OK, returnIntent);
        }
    }


    @Override
    protected void onResume() {
        sm.registerListener(this,sr1,SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onPause() {
        sm.unregisterListener(this);
        super.onPause();
    }


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == DialogInterface.BUTTON_POSITIVE) {
            finish();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        ImageView chr = findViewById(R.id.levelzero);

        double speed = 0.01;
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) chr.getLayoutParams();



        if(level <3)
            chr = findViewById(R.id.levelzero);
        else if(level < 6)
            chr = findViewById(R.id.level3);
        else if(level < 9)
            chr = findViewById(R.id.levelsix);
        else
            chr = findViewById(R.id.level9);

        chr.setVisibility(View.VISIBLE);


        if ((isCollision(chr, bullet0) || isCollision(chr, bullet2) || isCollision(chr, bullet1)) && !isDialogShown) {

            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                // 震动 500 毫秒
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    // deprecated in API 26
                    vibrator.vibrate(50);
                }
            }


            isDialogShown = true; // 设置标志为 true 表示对话框将会显示

            // 显示对话框
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder dead = new AlertDialog.Builder(boss.this);
                    dead.setTitle("死了")
                            .setMessage("按下確定返回")
                            .setCancelable(false)
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    isDialogShown = false; // 用户关闭对话框时，重置标志
                                    // 执行需要的操作，如关闭 Activity
                                    finish();
                                }
                            })
                            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    isDialogShown = false; // 用户取消对话框时，重置标志
                                }
                            })
                            .show();


                    win = 2;
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("win",win); // 添加得到的道具作为结果
                    setResult(RESULT_OK, returnIntent);


                }
            });
        }






        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            a = sensorEvent.values;
            if (a[0] < 0 && params.horizontalBias + speed <= 1) {
                params.horizontalBias += speed;
            } else if (a[0] > 0 && params.horizontalBias - speed >= 0 ) {
                params.horizontalBias -= speed;
            }

            chr.setLayoutParams(params);
        }
    }




    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // 略過
    }

    private void setRandomBulletPosition(ImageView bullet,int i) {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) bullet.getLayoutParams();
        Random random = new Random();
        int maxX = getResources().getDisplayMetrics().widthPixels - bullet.getWidth();
        int maxY = getResources().getDisplayMetrics().heightPixels -bullet.getHeight();
        params.verticalBias = (float) (random.nextFloat()* (0.7)-0.3f);
        params.horizontalBias =  (float)random.nextFloat()*(1);

        bullet.setLayoutParams(params);
        bulletSpeed_vt[i] = random.nextFloat() * (0.01f - 0.007f) + 0.003f;
        bulletSpeed_hr[i] = random.nextFloat() * (0.01f)-0.005f ;

    }

    private void updateBulletPosition(ImageView bullet,int i) {

        ConstraintLayout.LayoutParams bulletParams = (ConstraintLayout.LayoutParams) bullet.getLayoutParams();
        // 判斷子彈是否超出螢幕底部
        if (bulletParams.verticalBias > 1||bulletParams.horizontalBias > 1||bulletParams.horizontalBias < 0) {
            setRandomBulletPosition(bullet,i);
            setRandomBulletSize(bullet);
            startRotation(bullet);
        }
        else{
            bulletParams.horizontalBias += bulletSpeed_hr[i];
            bulletParams.verticalBias += bulletSpeed_vt[i];
            bullet.setLayoutParams(bulletParams);

        }
    }



    private void setRandomBulletSize(ImageView bullet) {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) bullet.getLayoutParams();
        Random random = new Random();

        // 設定隨機的寬度和高度
        int randomSize= random.nextInt(200)+50;  // 請根據實際需求設定

        params.width = randomSize;
        params.height = randomSize;

        bullet.setLayoutParams(params);
    }



    private void startRotation(ImageView bullet) {
        int random = new Random().nextInt(600)+1000;
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(bullet, "rotation", 0f, random);
        rotationAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        rotationAnimator.setDuration(1000); // 设置旋转一周的时长，例如 1 秒
        rotationAnimator.start();
    }


    private boolean isCollision(View chr, View star) {
        int[] chrLocation = new int[2];
        int[] bulletLocation = new int[2];
        chr.getLocationOnScreen(chrLocation);
        star.getLocationOnScreen(bulletLocation);
        return chrLocation[0] < bulletLocation[0] + star.getWidth() &&
                chrLocation[0] + chr.getWidth() > bulletLocation[0] &&
                chrLocation[1] < bulletLocation[1] + star.getHeight() &&
                chrLocation[1] + chr.getHeight() > bulletLocation[1];
    }


}
