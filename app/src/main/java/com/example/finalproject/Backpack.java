package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Backpack extends AppCompatActivity implements DialogInterface.OnClickListener, AdapterView.OnItemClickListener {

    String item[] = {"寶劍", "負重器", "隱形斗篷"};
    int lastchoose;
    ListView lvitem;
    ArrayAdapter<String> bag;
    ArrayList<Boolean> bagifuse = new ArrayList<>();
    ArrayList<String> bagitem = new ArrayList<>();

    String item_tmp_a="";
    String item_tmp_b="";
    String item_tmp_c="";

    int a_wear = 0;
    int b_wear = 0;
    int c_wear = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backpack);

        Intent it =getIntent();
        item_tmp_a = it.getStringExtra("隱形斗篷");
        item_tmp_b = it.getStringExtra("負重器");
        item_tmp_c = it.getStringExtra("寶劍");

        a_wear = it.getIntExtra("a_wear",0);
        b_wear = it.getIntExtra("b_wear",0);
        c_wear = it.getIntExtra("c_wear",0);



        lvitem = findViewById(R.id.lvitem);
        bag = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);


        ImageView a_in_back = (ImageView) findViewById(R.id.a_in_back);
        ImageView b_in_back = findViewById(R.id.b_in_back);
        ImageView c_in_back = findViewById(R.id.c_in_back);

        lvitem.setAdapter(bag);
        lvitem.setOnItemClickListener(this);
        if(!item_tmp_a.equals("")) {
            bag.add(item_tmp_a);
            bag.notifyDataSetChanged();
            bagifuse.add(false);
            bagitem.add(item_tmp_a);
            a_in_back.setVisibility(View.VISIBLE);
        }
        else
        {
            a_in_back.setVisibility(View.INVISIBLE);
        }

        if(!item_tmp_b.equals("")) {
            bag.add(item_tmp_b);
            bag.notifyDataSetChanged();
            bagifuse.add(false);
            bagitem.add(item_tmp_b);
            b_in_back.setVisibility(View.VISIBLE);
        }
        else
            b_in_back.setVisibility(View.INVISIBLE);

        if(!item_tmp_c.equals("")) {
            bag.add(item_tmp_c);
            bag.notifyDataSetChanged();
            bagifuse.add(false);
            bagitem.add(item_tmp_c);
            c_in_back.setVisibility(View.VISIBLE);
            //Toast.makeText(this,"TEST",Toast.LENGTH_SHORT).show();
        }
        else
            c_in_back.setVisibility(View.INVISIBLE);

        lvitem.setAdapter(bag);


        lvitem.post(new Runnable() {
            @Override
            public void run() {
                if (a_wear == 1 && !item_tmp_a.equals("")) {
                    lvitem.getChildAt(bag.getPosition(item_tmp_a)).setBackgroundColor(Color.GREEN);
                }
                if (b_wear == 1 && !item_tmp_b.equals("")) {
                    lvitem.getChildAt(bag.getPosition(item_tmp_b)).setBackgroundColor(Color.GREEN);
                }
                if (c_wear == 1 && !item_tmp_c.equals("")) {
                    lvitem.getChildAt(bag.getPosition(item_tmp_c)).setBackgroundColor(Color.GREEN);
                }
            }
        });

        check_wearing();



    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        lastchoose = i;
        useitem(bag.getItem(i).toString());
    }

    private void useitem(String itemName) {
        String effect="";
        AlertDialog.Builder itemdata = new AlertDialog.Builder(this);
        if (itemName.equals("寶劍")) {
            effect="戰鬥力+500";
        }
        if (itemName.equals("負重器")) {
            effect="里程數加程";
        }
        if (itemName.equals("隱形斗篷")) {
            effect="不會遭怪獸襲擊";

        }


        itemdata.setTitle(itemName)
                .setMessage("效果："+effect)
                .setNegativeButton("卸下", this)
                .setPositiveButton("穿戴", this)
                .setNeutralButton("取消", this)
                .show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            bagifuse.set(lastchoose, true);
            //effect_update();
            // 改變ListView中所選項目的文字顏色
            String tmp;
            tmp = bagitem.get(lastchoose);
            if(tmp.equals("隱形斗篷"))
            {
                a_wear = 1;
            }
            if(tmp.equals("負重器"))
            {
                b_wear = 1;
            }
            if(tmp.equals("寶劍"))
            {
                c_wear = 1;
            }


            lvitem.getChildAt(lastchoose).setBackgroundColor(Color.GREEN); // 這裡使用綠色作為示例
        } else if (which == DialogInterface.BUTTON_NEGATIVE) {
            bagifuse.set(lastchoose, false);
            lvitem.getChildAt(lastchoose).setBackgroundColor(Color.BLACK);
            //effect_update();

            String tmp;
            tmp = bagitem.get(lastchoose);
            if(tmp.equals("隱形斗篷"))
            {
                a_wear = 0;
            }
            if(tmp.equals("負重器"))
            {
                b_wear = 0;
            }
            if(tmp.equals("寶劍"))
            {
                c_wear = 0;
            }
        }
        // 如果是取消，不需要做任何事情
        check_wearing();
    }


    public void back(View view)
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("a_wear",a_wear);
        returnIntent.putExtra("b_wear",b_wear);
        returnIntent.putExtra("c_wear",c_wear);
        setResult(RESULT_OK, returnIntent);



        finish();
    }

    public void check_wearing()
    {
        ImageView a_in_back = findViewById(R.id.a_in_back);
        ImageView b_in_back = findViewById(R.id.b_in_back);
        ImageView c_in_back = findViewById(R.id.c_in_back);

        ImageView a_wearing = findViewById(R.id.a_wearing);
        ImageView b_wearing = findViewById(R.id.b_wearing);
        ImageView c_wearing = findViewById(R.id.c_wearing);

        if(a_wear == 1)
        {
            a_wearing.setVisibility(View.VISIBLE);
            a_in_back.setAlpha(0.4f);
        }
        else
        {
            a_wearing.setVisibility(View.GONE);
            if(!item_tmp_a.equals(""))
            {
                a_in_back.setVisibility(View.VISIBLE);
                a_in_back.setAlpha(1.0f);
            }
            else
            {
                a_in_back.setVisibility(View.GONE);
            }
        }

        if(b_wear == 1)
        {
            b_wearing.setVisibility(View.VISIBLE);
            b_in_back.setAlpha(0.4f);
        }
        else
        {
            b_wearing.setVisibility(View.GONE);
            if(!item_tmp_b.equals(""))
            {
                b_in_back.setVisibility(View.VISIBLE);
                b_in_back.setAlpha(1.0f);
            }
            else
            {
                b_in_back.setVisibility(View.GONE);
            }
        }

        if(c_wear == 1)
        {
            c_wearing.setVisibility(View.VISIBLE);
            c_in_back.setAlpha(0.4f);
        }
        else
        {
            c_wearing.setVisibility(View.GONE);
            if(!item_tmp_c.equals(""))
            {
                c_in_back.setVisibility(View.VISIBLE);
                c_in_back.setAlpha(1.0f);
            }
            else
            {
                c_in_back.setVisibility(View.GONE);
            }
        }
    }





/*
    public void effect_update(){

        if (bagitem.contains("寶劍")&&bagifuse.get(bagitem.indexOf("寶劍"))==true)
            tvsit.append("寶劍已發動\n");
        if (bagitem.contains("負重器")&&bagifuse.get(bagitem.indexOf("負重器"))==true)
            tvsit.append("負重器已發動\n");
        if (bagitem.contains("隱形斗篷")&&bagifuse.get(bagitem.indexOf("隱形斗篷"))==true)
            tvsit.append("隱形斗篷已發動\n");

    }
*/



}


/*
 if (bagitem.contains(item[randomNumber])==false) {

            bag.notifyDataSetChanged();
            bagifuse.add(false);
            bagitem.add(item[randomNumber]);
        }
 */