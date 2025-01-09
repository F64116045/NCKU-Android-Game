package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.finalproject.databinding.MiniMapsBinding;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , LocationListener , DialogInterface.OnClickListener {

    static final int MIN_TIME = 50;
    static final float MIN_DIST = 0;
    LatLng currLocation;

    private LocationManager locationManager;
    private Location lastLocation;
    private float totalDistance = 0;

    int lvl = 0;
    float damage = 0;
    private GoogleMap mMap;
    private MiniMapsBinding binding;

    boolean a = false;
    boolean b = false;
    boolean c = false;

    int a_wear;
    int b_wear;
    int c_wear;

    boolean isSafe = false;


    LatLng P1;
    LatLng P2;
    LatLng P3;

    boolean p1_defeat = false;
    boolean p2_defeat = false;
    boolean p3_defeat = false;
    float speed;
    Marker BOSS_1;
    Marker BOSS_2;
    Marker BOSS_3;
    float max_speed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MiniMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 1, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }







        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        random_place();


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ifBossClicked();

        if (lastLocation != null) {
            LatLng currentLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));


        }

        mMap.setMapStyle(new MapStyleOptions("[" +
                "{" +
                "  \"featureType\": \"road\"," +
                "  \"elementType\": \"geometry\"," +
                "  \"stylers\": [" +
                "    {" +
                "      \"color\": \"#38414e\"" +
                "    }" +
                "  ]" +
                "}," +
                "{" +
                "  \"featureType\": \"road\"," +
                "  \"elementType\": \"geometry.stroke\"," +
                "  \"stylers\": [" +
                "    {" +
                "      \"color\": \"#212a37\"" +
                "    }" +
                "  ]" +
                "}," +
                "{" +
                "  \"featureType\": \"road\"," +
                "  \"elementType\": \"labels.text.fill\"," +
                "  \"stylers\": [" +
                "    {" +
                "      \"color\": \"#9ca5b3\"" +
                "    }" +
                "  ]" +
                "}," +
                "{" +
                "  \"featureType\": \"road.highway\"," +
                "  \"elementType\": \"geometry\"," +
                "  \"stylers\": [" +
                "    {" +
                "      \"color\": \"#746855\"" +
                "    }" +
                "  ]" +
                "}," +
                "{" +
                "  \"featureType\": \"road.highway\"," +
                "  \"elementType\": \"geometry.stroke\"," +
                "  \"stylers\": [" +
                "    {" +
                "      \"color\": \"#1f2835\"" +
                "    }" +
                "  ]" +
                "}," +
                "{" +
                "  \"featureType\": \"road.highway\"," +
                "  \"elementType\": \"labels.text.fill\"," +
                "  \"stylers\": [" +
                "    {" +
                "      \"color\": \"#f3d19c\"" +
                "    }" +
                "  ]" +
                "}," +
                "{" +
                "  \"featureType\": \"transit\"," +
                "  \"elementType\": \"geometry\"," +
                "  \"stylers\": [" +
                "    {" +
                "      \"color\": \"#2f3948\"" +
                "    }" +
                "  ]" +
                "}," +
                "{" +
                "  \"featureType\": \"transit.station\"," +
                "  \"elementType\": \"labels.text.fill\"," +
                "  \"stylers\": [" +
                "    {" +
                "      \"color\": \"#d59563\"" +
                "    }" +
                "  ]" +
                "}," +
                "{" +
                "  \"featureType\": \"water\"," +
                "  \"elementType\": \"geometry\"," +
                "  \"stylers\": [" +
                "    {" +
                "      \"color\": \"#17263c\"" +
                "    }" +
                "  ]" +
                "}," +
                "{" +
                "  \"featureType\": \"water\"," +
                "  \"elementType\": \"labels.text.fill\"," +
                "  \"stylers\": [" +
                "    {" +
                "      \"color\": \"#515c6d\"" +
                "    }" +
                "  ]" +
                "}," +
                "{" +
                "  \"featureType\": \"water\"," +
                "  \"elementType\": \"labels.text.stroke\"," +
                "  \"stylers\": [" +
                "    {" +
                "      \"color\": \"#17263c\"" +
                "    }" +
                "  ]" +
                "}" +
                // ... 可以添加更多样式定义
                "]"));



    }

    float DIT_TMP;

    /*
    等級:
        100m -> 1等
        150m -> 2等
        200m -> 3等
        300m -> 4等
        ...
     */
    int temp = 3200;
    private int calculateLevel(float totalDistance) {
        int level = 0;
        DIT_TMP = 100-totalDistance;
        if(totalDistance > 100 && totalDistance < 250) {
            level = 1;
            DIT_TMP = 250-totalDistance;
        }
        else if (totalDistance > 250 && totalDistance < 450) {
            level = 2;
            DIT_TMP = 450-totalDistance;
        }
        else if (totalDistance > 450 && totalDistance < 700) {
            level = 3;
            DIT_TMP = 700-totalDistance;
        }
        else if (totalDistance > 700 && totalDistance < 1050) {
            level = 4;
            DIT_TMP = 1050-totalDistance;
        }
        else if (totalDistance > 1050 && totalDistance < 1450) {
            level = 5;
            DIT_TMP = 1450-totalDistance;
        }
        else if (totalDistance > 1450 && totalDistance < 1900) {
            level = 6;
            DIT_TMP = 1900-totalDistance;
        }
        else if (totalDistance > 1900 && totalDistance < 2400) {
            level = 7;
            DIT_TMP = 2400-totalDistance;
        }
        else if (totalDistance > 2400 && totalDistance < 2950) {
            level = 8;
            DIT_TMP = 2950-totalDistance;
        }
        else if (totalDistance > 2950 && totalDistance < 3200) {
            level = 9;
            DIT_TMP = 3200-totalDistance;
        }
        else if (totalDistance > 3200) {
            level = 9 + (int)((totalDistance - 3200) / 100);
            DIT_TMP = 100 - (totalDistance % 100);
        }



        return level;
    }

    public void back(View view) {
        String item = "";
        Intent it = new Intent(this, Backpack.class);
        it.putExtra("道具",item);
        if(a == true)
            it.putExtra("隱形斗篷","隱形斗篷");
        else
            it.putExtra("隱形斗篷","");

        if(b == true)
            it.putExtra("負重器","負重器");
        else
            it.putExtra("負重器","");

        if(c == true)
            it.putExtra("寶劍","寶劍");
        else
            it.putExtra("寶劍","");

        if(a_wear == 1 )
            it.putExtra("a_wear",a_wear);
        else
            it.putExtra("a_wear",0);

        if(b_wear == 1 )
            it.putExtra("b_wear",b_wear);
        else
            it.putExtra("b_wear",0);

        if(c_wear == 1 )
            it.putExtra("c_wear",c_wear);
        else
            it.putExtra("c_wear",0);

        startActivityForResult(it,2);
    }

    public void bigmap(View view) {
        Intent it = new Intent(this, BigMap.class);
        startActivity(it);
    }


    Marker myMarker;
    float lastRandomMilestone = 0;
    int currentLevel;
    @Override
    public void onLocationChanged(@NonNull Location location) {

        if (lastLocation != null) {
            totalDistance += location.distanceTo(lastLocation);
        }
        lastLocation = location;

        //初始化地圖
        initiaMAP();

        // 更新地圖上的用戶位置
        if (mMap != null) {
            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.clear(); // 清除舊標記

            check_marker(currentLocation);
            currLocation = currentLocation;
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));




            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mark);


            Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 60, 80, false);


            BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(resizedBitmap);

            LatLng point1 = new LatLng(23.00059659876266, 120.22215505654759);
            LatLng point2 = new LatLng(22.996030381344138, 120.22180136180299);
            LatLng point3 = new LatLng(23.000834613196588, 120.21859322527105);
            LatLng point4 = new LatLng(22.99628392387553, 120.21822147129949);

            mMap.addMarker(new MarkerOptions().position(point1).title("右上角").icon(icon));
            mMap.addMarker(new MarkerOptions().position(point2).title("右下").icon(icon));
            mMap.addMarker(new MarkerOptions().position(point3).title("左上角").icon(icon));
            mMap.addMarker(new MarkerOptions().position(point4).title("左下").icon(icon));

            PolylineOptions polylineOptions = new PolylineOptions()
                    .add(point1)
                    .add(point2)
                    .add(point4)
                    .add(point3)
                    .add(point1);

            polylineOptions.width(5);
            polylineOptions.color(Color.GREEN);

            mMap.addPolyline(polylineOptions);
            setzone();



            //設定魔王位置;
            setBoss();

        }

        //設定等級
        currentLevel = calculateLevel(totalDistance);
        TextView lvl_txv = (TextView) findViewById(R.id.lvl_txv);
        String DIT_str = String.format(Locale.US, "%.1f", DIT_TMP);
        if(totalDistance < 2950)
            lvl_txv.setText("目前等級 : "+currentLevel+"\n距離下一級 : "+DIT_str+"m");
        else
            lvl_txv.setText("目前等級 : "+currentLevel+"\n距離下一級 : "+DIT_str+"m");

        lvl = currentLevel;
        damage = lvl * 20;
        TextView attack_txv = (TextView) findViewById(R.id.attack_txv);
        attack_txv.setText("戰鬥力:"+damage);






        //每跑400m 生成隨機怪物

        if (lastLocation != null) {
            totalDistance += location.distanceTo(lastLocation);

            // 檢查是否達到下一個400米里程碑
            if (totalDistance - lastRandomMilestone >= 100 && isSafe == false && a_wear == 0) {
                eventMonster();
                // 更新最後的里程碑
                lastRandomMilestone = totalDistance;
            }
        }
        lastLocation = location;

        TextView milage = (TextView) findViewById(R.id.milage_txv);
        String totalDistance_string = String.format("%.1f",totalDistance);
        milage.setText("目前總里程數" + totalDistance);







        //判斷是否在安全區域

        //右上角 23.00059659876266, 120.22215505654759
        //右下 22.996030381344138, 120.22180136180299
        //左上 23.000834613196588, 120.21859322527105
        //左下 22.99628392387553, 120.21822147129949
        double nowLongtitude = location.getLongitude();
        double nowLatitude = location.getLatitude();
        if((nowLatitude > 22.996030381344138 && nowLatitude < 23.00059659876266) && (nowLongtitude < 120.22180136180299 && nowLongtitude > 120.21822147129949))
            {
                isSafe = true;
            }
        else
            {
                isSafe = false;
            }

        safe_text();

        //速度
        speed = location.getSpeed();
        if(speed > max_speed)
            max_speed = speed;
        TextView speed_txv = (TextView) findViewById(R.id.speed_txv);
        String speed_string = String.format("%.1f",max_speed*200);
        speed_txv.setText("速度加成:"+ speed_string);




        if(mMap!= null) {
                    if(wtf == 0){
                        wtf =1;
            insert_image();
            // 創建一個新的 CameraPosition
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(),location.getLongitude())) // 設置中心點坐標
                    .zoom(19) // 設置縮放級別
                    .bearing(0) // 設置方位角度
                    .tilt(60) // 設置傾斜度為 45 度
                    .build();

            // 將攝像機位置更新到地圖
                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));}
        }

        if(mMap!= null)
        {
            if(a_wear == 1)
                myMarker.setAlpha(0.3f);
        }


        check_effect();


    }
    int wtf =0;
    public void safe_text()
    {
        TextView saf = findViewById(R.id.safe_txv);
        if(isSafe == true)
        {
            saf.setText("位於安全區域");
            saf.setTextColor(Color.GREEN);
        }
        else
        {
            saf.setText("位於危險區域");
            saf.setTextColor(Color.RED);
        }
    }


    public void initiaMAP()
    {
        if (mMap != null) {
            try {
                String mapStyleJson = "[" +
                        "{" +
                        "  \"featureType\": \"poi.business\"," +
                        "  \"stylers\": [{" +
                        "      \"visibility\": \"off\"" +
                        "  }]" +
                        "}," +
                        "{" +
                        "  \"featureType\": \"road\"," +
                        "  \"elementType\": \"geometry\"," +
                        "  \"stylers\": [{" +
                        "      \"color\": \"#38414e\"" +
                        "  }]" +
                        "}," +
                        // ... 其他样式定义
                        "{" +
                        "  \"featureType\": \"water\"," +
                        "  \"elementType\": \"labels.text.stroke\"," +
                        "  \"stylers\": [{" +
                        "      \"color\": \"#17263c\"" +
                        "  }]" +
                        "}" +
                        // ... 更多样式可以在这里添加
                        "]";

                boolean success = mMap.setMapStyle(new MapStyleOptions(mapStyleJson));

                if (!success) {
                    Log.e("MapsActivity", "Style parsing failed.");
                }
            } catch (Exception e) {
                Log.e("MapsActivity", "Can't apply style. Error: ", e);
            }
        }
    }

    protected void onResume() {
        super.onResume();
        enableLocationUpdates();
        ;
    }

    private void enableLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,MIN_DIST,this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME,MIN_DIST,this);
        //Toast.makeText(this,"TEST",Toast.LENGTH_LONG).show();
    }

    protected void onPause() {
        super.onPause();
        ;

    }


    public float getTotalDistance() {
        return totalDistance;
    }

    private void insert_image()
        {
            //疊加安全區域圖片
            /*
            BitmapDescriptor image = BitmapDescriptorFactory.fromResource(R.drawable.safezone); // 替换成你的图像资源

            LatLng northEast = new LatLng(23.00059659876266, 120.22215505654759);
            LatLng southWest = new LatLng(22.99628392387553, 120.21859322527105);

            LatLngBounds bounds = new LatLngBounds(southWest, northEast);

            GroundOverlayOptions groundOverlayOptions = new GroundOverlayOptions()
                    .image(image)
                    .positionFromBounds(bounds)
                    .transparency(0.5f);
            mMap.addGroundOverlay(groundOverlayOptions);*/


            LatLng topRight = new LatLng(23.00059659876266, 120.22215505654759);
            LatLng bottomRight = new LatLng(22.996030381344138, 120.22180136180299);
            LatLng topLeft = new LatLng(23.000834613196588, 120.21859322527105);
            LatLng bottomLeft = new LatLng(22.99628392387553, 120.21822147129949);

            //
            PolygonOptions polygonOptions = new PolygonOptions()
                    .add(topLeft, topRight, bottomRight, bottomLeft, topLeft) // 添加点来形成闭合多边形
                    .strokeColor(Color.GREEN) // 边框颜色
                    .fillColor(Color.argb(128, 0, 255, 0)); // 填充颜色和透明度

            //
            mMap.addPolygon(polygonOptions);
        }




        String msid;
    private void eventMonster() {
        // 抽怪物，根據機率從A到F抽取一個
        String monsterId = randomdrawMonsterId();
        msid = monsterId;
        // 跳提示框，讓玩家選擇進入戰鬥或逃跑
        if(isSafe == false){
        AlertDialog.Builder monster = new AlertDialog.Builder(this);
        monster.setMessage("怪獸"+monsterId+"來襲！！");
        monster.setNegativeButton("逃跑",this);
        monster.setPositiveButton("迎擊",this);
        monster.setCancelable(false);
        monster.show();}
    }

    private String randomdrawMonsterId() {
        Random random = new Random();
        // 生成一個在1到200之間的隨機數（包括1和200）
        int randomNumber = random.nextInt(200) + 1;
        // 回傳隨機怪獸ID
        if (randomNumber<6)
            return "A";
        if (randomNumber>=6&&randomNumber<16)
            return "B";
        if (randomNumber>=16&&randomNumber<31)
            return "C";
        if (randomNumber>=31&&randomNumber<81)
            return "D";
        if (randomNumber>=81&&randomNumber<131)
            return "E";
        if (randomNumber>=131&&randomNumber<201)
            return "F";


        return "";
    }

    @Override
    public void onClick(DialogInterface dialog, int fight) {
            if(fight == DialogInterface.BUTTON_POSITIVE) {
                Intent chrdata = new Intent(this,monsterfight.class);
                if(c_wear == 1)
                    chrdata.putExtra("戰鬥力",damage_tmp);
                else
                    chrdata.putExtra("戰鬥力",damage);

                chrdata.putExtra("monster",msid);
                chrdata.putExtra("level",currentLevel);
                startActivityForResult(chrdata,1);

            }
            if(fight == DialogInterface.BUTTON_NEGATIVE){


        }
    }


    int win;
    int now_boss;
    int timedown;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            String receivedItem ="0";
            //int success = data.getIntExtra("success",5);
            receivedItem = data.getStringExtra("rewardItem");

            if(receivedItem.equals("隱形斗篷") && a == true)
            {
                a = true;
                Toast.makeText(this, receivedItem + "(已獲得不再放入背包)", Toast.LENGTH_LONG).show();
            }
            if(receivedItem.equals("負重器") && b == true)
            {
                b = true;
                Toast.makeText(this, receivedItem + "(已獲得不再放入背包)", Toast.LENGTH_LONG).show();
            }
            if(receivedItem.equals("寶劍") && c == true)
            {
                c = true;
                Toast.makeText(this, receivedItem + "(已獲得不再放入背包)", Toast.LENGTH_LONG).show();
            }



            if(receivedItem.equals("隱形斗篷") && a == false)
            {
                a = true;
                Toast.makeText(this, receivedItem + "已放入背包", Toast.LENGTH_LONG).show();
            }
            if(receivedItem.equals("負重器") && b == false)
            {
                b = true;
                Toast.makeText(this, receivedItem + "已放入背包", Toast.LENGTH_LONG).show();
            }
            if(receivedItem.equals("寶劍") && c == false)
            {
                c = true;
                Toast.makeText(this, receivedItem + "已放入背包", Toast.LENGTH_LONG).show();
            }




            if(!receivedItem.equals("無") && !receivedItem.equals("")) {
                Intent it = new Intent(this, Backpack.class);
                it.putExtra("道具", receivedItem);
             }
            if(receivedItem.equals("") )
            {
                Toast.makeText(this, "噴裝" , Toast.LENGTH_LONG).show();
                dead();
            }

        }



        if(requestCode == 2 && resultCode == RESULT_OK)
        {
            a_wear = data.getIntExtra("a_wear",0);
            b_wear = data.getIntExtra("b_wear",0);
            c_wear = data.getIntExtra("c_wear",0);
            TextView tvsit = findViewById(R.id.tvsit);
            tvsit.setText("");
            effect_update();

        }

        if(requestCode == 3 && resultCode == RESULT_OK)
        {
            win = data.getIntExtra("win",0);
            now_boss = data.getIntExtra("boss",5);
            now_boss++;
            //win == 1 贏了
            if(win == 1)
            {
                if(now_boss == 1)
                {
                    p1_defeat = true;
                    Toast.makeText(this,"打敗拉機車",Toast.LENGTH_LONG).show();
                    setBoss();
                    BOSS_1.setAlpha(0.3f);
                }

                if(now_boss == 2)
                {
                    p2_defeat = true;
                    Toast.makeText(this,"打敗123炒飯",Toast.LENGTH_LONG).show();
                    setBoss();
                    BOSS_2.setAlpha(0.3f);
                }
                if(now_boss == 3)
                {
                    p3_defeat = true;
                    Toast.makeText(this,"打敗大章魚",Toast.LENGTH_LONG).show();
                    BOSS_3.setAlpha(0.3f);
                }
            }
            //win == 2 輸了

            if(win == 2)
                {
                    dead();
                    TextView tvsit = findViewById(R.id.tvsit);
                    tvsit.setText("");
                }
            ifgameend();
        }
    }

    public void dead()
    {
        a = false;
        b = false;
        c = false;
        a_wear = 0;
        b_wear = 0;
        c_wear = 0;
        TextView tvsit = findViewById(R.id.tvsit);
        tvsit.setText("");

    }

    public void setzone()
    {
        //23.003051918376574, 120.21864821513901
        //23.003589622955015, 120.21505224597337
        //23.001550524120773, 120.21471844292846
        //23.001145493972654, 120.21842062215384
        //力行校區
        LatLng topRight0 = new LatLng(23.003057664444533, 120.21864986484735);
        LatLng bottomRight0 = new LatLng(23.001090040727224, 120.21843309603793);
        LatLng topLeft0 = new LatLng(23.003600834390102, 120.2150490940685);
        LatLng bottomLeft0 = new LatLng(23.001511280893947, 120.21469383407526);

        //
        PolygonOptions polygonOptions0 = new PolygonOptions()
                .add(topLeft0, topRight0, bottomRight0, bottomLeft0, topLeft0) // 添加点来形成闭合多边形
                .strokeColor(Color.RED) // 边框颜色
                .fillColor(Color.argb(128, 255, 0 , 0)); // 填充颜色和透明度

        //
        mMap.addPolygon(polygonOptions0);

        //成杏
        LatLng topRight1 = new LatLng(23.002499534221577, 120.22231194397195);
        LatLng bottomRight1 = new LatLng(23.000831205964143, 120.22217345278814);
        LatLng topLeft1 = new LatLng(23.00303716385108, 120.21878945081872);
        LatLng bottomLeft1 = new LatLng(23.001086167763663, 120.2185787033651);

        //
        PolygonOptions polygonOptions1 = new PolygonOptions()
                .add(topLeft1, topRight1, bottomRight1, bottomLeft1, topLeft1) // 添加点来形成闭合多边形
                .strokeColor(Color.RED) // 边框颜色
                .fillColor(Color.argb(128, 255, 0, 0)); // 填充颜色和透明度


        mMap.addPolygon(polygonOptions1);

        //自強
        LatLng topRight2 = new LatLng(23.000399888831698, 120.22440016531793);
        LatLng bottomRight2 = new LatLng(22.995885229512588, 120.22396301500278);
        LatLng topLeft2 = new LatLng(23.00057575334704, 120.22238614896985);
        LatLng bottomLeft2 = new LatLng(22.996019001777473, 120.22202330180892);

        //
        PolygonOptions polygonOptions2 = new PolygonOptions()
                .add(topLeft2, topRight2, bottomRight2, bottomLeft2, topLeft2) // 添加点来形成闭合多边形
                .strokeColor(Color.RED) // 边框颜色
                .fillColor(Color.argb(128, 255, 0, 0)); // 填充颜色和透明度

        //
        mMap.addPolygon(polygonOptions2);

        //敬業
        LatLng topRight3 = new LatLng(23.002194859238802, 120.22450849849174);
        LatLng bottomRight3 = new LatLng(23.00064846580264, 120.22443624222194);
        LatLng topLeft3 = new LatLng(23.002466446221085, 120.22254553649525);
        LatLng bottomLeft3 = new LatLng(23.000809202863014, 120.22240704531144);

        //
        PolygonOptions polygonOptions3 = new PolygonOptions()
                .add(topLeft3, topRight3, bottomRight3, bottomLeft3, topLeft3) // 添加点来形成闭合多边形
                .strokeColor(Color.RED) // 边框颜色
                .fillColor(Color.argb(128, 255, 0, 0)); // 填充颜色和透明度

        //
        mMap.addPolygon(polygonOptions3);


        //勝利
        LatLng topRight4 = new LatLng(22.99594992892852, 120.22178504192843);
        LatLng bottomRight4 = new LatLng(22.992142855457743, 120.22139593161967);
        LatLng topLeft4 = new LatLng(22.996211234887653, 120.21822817198458);
        LatLng bottomLeft4 = new LatLng(22.99242510609935, 120.2179060857089);

        //
        PolygonOptions polygonOptions4 = new PolygonOptions()
                .add(topLeft4, topRight4, bottomRight4, bottomLeft4, topLeft4) // 添加点来形成闭合多边形
                .strokeColor(Color.RED) // 边框颜色
                .fillColor(Color.argb(128, 255, 0, 0)); // 填充颜色和透明度

        //
        mMap.addPolygon(polygonOptions4);


        //光復
        //
        LatLng topRight5 = new LatLng(23.000841797475154, 120.21846450764227);
        LatLng bottomRight5 = new LatLng(22.996297184512486, 120.21809861178686);
        LatLng topLeft5 = new LatLng(23.001271972369143, 120.21464041373322);
        LatLng bottomLeft5 = new LatLng(22.996904683580823, 120.21367487628149);

        //
        PolygonOptions polygonOptions5 = new PolygonOptions()
                .add(topLeft5, topRight5, bottomRight5, bottomLeft5, topLeft5) // 添加点来形成闭合多边形
                .strokeColor(Color.RED) // 边框颜色
                .fillColor(Color.argb(128, 255, 0, 0)); // 填充颜色和透明度

        //
        mMap.addPolygon(polygonOptions5);


    }


    public void effect_update(){

        TextView tvsit = findViewById(R.id.tvsit);
        if(a_wear == 1)
        {
            tvsit.append("隱形斗篷已發動\n");
        }
        if(b_wear == 1)
        {
            tvsit.append("負重器已發動\n");
        }
        if(c_wear == 1)
        {
            tvsit.append("寶劍已發動\n");
        }

    }

    float damage_tmp;
    public void check_effect(){
        TextView attack_txv = findViewById(R.id.attack_txv);
        if(c_wear == 1)
        {
            damage_tmp = damage+500;
            String damagestr = String.format("%.1f",damage_tmp);
            attack_txv.setText("戰鬥力"+damagestr);
            attack_txv.setTextColor(Color.RED);
            attack_txv.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            attack_txv.setText("戰鬥力"+damage);
            attack_txv.setTextColor(Color.WHITE);
            attack_txv.setTypeface(null, Typeface.BOLD);
        }
        if(b_wear == 1)
        {
            totalDistance+=7;
        }


    }

    public void check_marker(LatLng currentLocation){
        Bitmap originalBitmap1;
        Bitmap resizedBitmap1;
        if(currentLevel < 3) {
            originalBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.people);
            resizedBitmap1 = Bitmap.createScaledBitmap(originalBitmap1, 100, 150, false);
        }
        else if(currentLevel < 6) {
            originalBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.levelthree);
            resizedBitmap1 = Bitmap.createScaledBitmap(originalBitmap1, 120, 170, false);
        }
        else if(currentLevel < 9) {
            originalBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.levelsix);
            resizedBitmap1 = Bitmap.createScaledBitmap(originalBitmap1, 120, 190, false);
        }
        else {
            originalBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.levelnine);
            resizedBitmap1 = Bitmap.createScaledBitmap(originalBitmap1, 130, 230, false);
        }

        //resizedBitmap1 = Bitmap.createScaledBitmap(originalBitmap1, 130, 150, false);


        BitmapDescriptor icon1 = BitmapDescriptorFactory.fromBitmap(resizedBitmap1);

        LatLng tmp = new LatLng(P1.latitude+5,P1.latitude+5);
        myMarker = mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location").icon(icon1));
    }

    public void random_place() {
        LatLng point1 = new LatLng(23.000914437857773, 120.22306213429444);//敬業怪 group 1

        LatLng point4 = new LatLng(23.001924837011888, 120.22143160596548);//成杏

        LatLng point2 = new LatLng(23.00182649660691, 120.2155195295635);//力行 group 1

        LatLng point5 = new LatLng(23.00051848991574, 120.21576514321934);//光復

        LatLng point3 = new LatLng(22.994286779132487, 120.21997988421741);//勝利 group 1

        LatLng point6 = new LatLng(22.99670404803527, 120.22278104780467);//自強

        Random random = new Random();
        int randomNumber = random.nextInt(2) + 1;

        Random random0 = new Random();
        int randomNumber0 = random.nextInt(6) + 1;

        if (randomNumber == 1) {
            //group 1
            if (randomNumber0 == 1) {
                P1 = point1;
                P2 = point2;
                P3 = point3;
            }
            if (randomNumber0 == 2) {
                P1 = point1;
                P2 = point3;
                P3 = point2;
            }
            if (randomNumber0 == 3) {
                P1 = point3;
                P2 = point2;
                P3 = point1;
            }
            if (randomNumber0 == 4) {
                P1 = point2;
                P2 = point1;
                P3 = point3;
            }
            if (randomNumber0 == 5) {
                P1 = point2;
                P2 = point3;
                P3 = point1;
            }
            if (randomNumber0 == 6) {
                P1 = point3;
                P2 = point1;
                P3 = point2;
            }

        }

        if(randomNumber == 2)
        {
            //group 2
            if (randomNumber0 == 1) {
                P1 = point4;
                P2 = point5;
                P3 = point6;
            }
            if (randomNumber0 == 2) {
                P1 = point4;
                P2 = point6;
                P3 = point5;
            }
            if (randomNumber0 == 3) {
                P1 = point6;
                P2 = point5;
                P3 = point4;
            }
            if (randomNumber0 == 4) {
                P1 = point5;
                P2 = point4;
                P3 = point6;
            }
            if (randomNumber0 == 5) {
                P1 = point5;
                P2 = point6;
                P3 = point4;
            }
            if (randomNumber0 == 6) {
                P1 = point6;
                P2 = point4;
                P3 = point5;
            }
        }

    }
    public void setBoss()
    {
        Bitmap originalBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.bossone);


        Bitmap resizedBitmap1 = Bitmap.createScaledBitmap(originalBitmap1, 200, 200, false);


        BitmapDescriptor icon1 = BitmapDescriptorFactory.fromBitmap(resizedBitmap1);


        Bitmap originalBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.bosstwo);


        Bitmap resizedBitmap2 = Bitmap.createScaledBitmap(originalBitmap2, 200, 200, false);


        BitmapDescriptor icon2 = BitmapDescriptorFactory.fromBitmap(resizedBitmap2);

        Bitmap originalBitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.bossthree);


        Bitmap resizedBitmap3 = Bitmap.createScaledBitmap(originalBitmap3, 200, 200, false);


        BitmapDescriptor icon3 = BitmapDescriptorFactory.fromBitmap(resizedBitmap3);

        Marker marker1;
        Marker marker2;
        Marker marker3;
        marker1 = mMap.addMarker(new MarkerOptions().position(P1).title("拉機車").icon(icon1));
        marker2 = mMap.addMarker(new MarkerOptions().position(P2).title("123炒飯").icon(icon2));
        marker3 = mMap.addMarker(new MarkerOptions().position(P3).title("觸角大章魚").icon(icon3));

        BOSS_1 = marker1;
        BOSS_2 = marker2;
        BOSS_3 = marker3;
        if(p1_defeat == false)
            marker1.setAlpha(1.0f);
        else
            marker1.setAlpha(0.3f);

        if(p2_defeat == false)
            marker2.setAlpha(1.0f);
        else
            marker2.setAlpha(0.3f);

        if(p3_defeat == false)
            marker3.setAlpha(1.0f);
        else {
            marker3.setAlpha(0.3f);
        }
    }

    public void meetBoss(int num)
    {
        Intent it = new Intent(this,boss.class);
        if(c_wear == 1)
            it.putExtra("攻擊力",damage_tmp);
        else
            it.putExtra("攻擊力",damage);
        it.putExtra("boss",num);
        it.putExtra("speed",speed);
        it.putExtra("level",currentLevel);
        startActivityForResult(it,3);
    }

    public int ifmeetBoss(LatLng currentLocation)
    {
        Location currLocation = new Location("");
        currLocation.setLatitude(currentLocation.latitude);
        currLocation.setLongitude(currentLocation.longitude);

        //"P1拉機車", "P2 123炒飯", "P3觸手大章魚"


        Location L1 = new Location("");
        L1.setLatitude(P1.latitude);
        L1.setLongitude(P1.longitude);

        Location L2 = new Location("");
        L1.setLatitude(P2.latitude);
        L1.setLongitude(P2.longitude);

        Location L3 = new Location("");
        L1.setLatitude(P3.latitude);
        L1.setLongitude(P3.longitude);


        if(currLocation.distanceTo(L1) < 5000)
        {
            //遇到拉機車
            //meetBoss(1);
            return 1;
        }

        if(currLocation.distanceTo(L2) < 5000)
        {
            //遇到123炒飯
            //meetBoss(2);
            return 1;
        }

        if(currLocation.distanceTo(L3) < 5000)
        {
            //遇到觸手大章魚
            //meetBoss(3);
            return 1;
        }

        return 0;
    }
    String boss_name = "";
    int boss_num=0;
    public void ifBossClicked() {

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                // 检查vibrator是否可用
                if (vibrator != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        // 对于Android O及以上版本
                        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        // 对于Android O以下版本
                        vibrator.vibrate(5000); // 震动500毫秒
                    }
                }







                //Toast.makeText(MapsActivity.this,"CLICK!",Toast.LENGTH_LONG).show();
                if (marker.equals(BOSS_1)) {
                    boss_name = "???";
                    boss_num = 1;

                } else if (marker.equals(BOSS_2)) {
                    boss_name = "123炒飯";
                    boss_num = 2;
                    //Toast.makeText(MapsActivity.this,"CLICK!",Toast.LENGTH_LONG).show();

                } else if (marker.equals(BOSS_3)) {
                    boss_name = "觸角大章魚";
                    boss_num = 3;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setTitle("☣           ☣           ☣             ☣\n☣           ☣           ☣             ☣");
                builder.setMessage("即將挑戰" + marker.getTitle());
                builder.setNegativeButton("不要",MapsActivity.this);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(ifmeetBoss(currLocation) == 1)
                        {
                            if(boss_num == 1 && p1_defeat == false)
                                meetBoss(1);
                            else if(p1_defeat == true)
                                Toast.makeText(MapsActivity.this,"已被消滅",Toast.LENGTH_LONG).show();
                            if(boss_num == 2 && p2_defeat == false)
                                meetBoss(2);
                            else if(p2_defeat == true)
                                Toast.makeText(MapsActivity.this,"已被消滅",Toast.LENGTH_LONG).show();
                            if(boss_num == 3 && p3_defeat == false)
                                meetBoss(3);
                            else if(p3_defeat == true)
                                Toast.makeText(MapsActivity.this,"已被消滅",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(MapsActivity.this,"請先到魔王位置再挑戰",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.show();


                return true;
            }
        });
    }

    public void ifgameend()
    {
        if(p1_defeat == true && p2_defeat == true && p3_defeat == true) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("恭喜");
            builder.setMessage("你贏了遊戲！");
            builder.setPositiveButton("回到初始畫面", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
