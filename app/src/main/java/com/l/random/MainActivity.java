package com.l.random;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    RelativeLayout number_container;
    RelativeLayout dice_container;
    RelativeLayout coin_container;
    RelativeLayout card_container;
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_number:
                    number_container.setVisibility(View.VISIBLE);
                    dice_container.setVisibility(View.GONE);
                    coin_container.setVisibility(View.GONE);
                    card_container.setVisibility(View.VISIBLE);
                    //doNumberRoll();
                    return true;
                case R.id.navigation_dice:
                    number_container.setVisibility(View.GONE);
                    dice_container.setVisibility(View.VISIBLE);
                    coin_container.setVisibility(View.GONE);
                    card_container.setVisibility(View.VISIBLE);
                    //doDiceRoll();
                    return true;
                case R.id.navigation_coin:
                    number_container.setVisibility(View.GONE);
                    dice_container.setVisibility(View.GONE);
                    coin_container.setVisibility(View.VISIBLE);
                    card_container.setVisibility(View.VISIBLE);
                    //doCoinFlip();
                    return true;
                case R.id.navigation_card:
                    number_container.setVisibility(View.GONE);
                    dice_container.setVisibility(View.GONE);
                    coin_container.setVisibility(View.GONE);
                    card_container.setVisibility(View.VISIBLE);
                    //doDrawCard();
                    return true;
            }
            return false;
        }
    };
    TextView num;
    EditText et_min;
    EditText et_max;
    LinearLayout number_results;
    TextView number_rolled;
    ImageView dice;
    LinearLayout dice_results;
    TextView dice_rolled;
    ImageView coin;
    LinearLayout coin_results;
    TextView coin_flipped;
    ImageView card;
    LinearLayout card_results;
    TextView card_drawn;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                handleShakeEvent(count);
            }
        });

        number_container = findViewById(R.id.number_container);
        num = findViewById(R.id.tv_number);
        et_min = findViewById(R.id.min);
        et_max = findViewById(R.id.max);
        number_results = findViewById(R.id.number_results);
        number_rolled = findViewById(R.id.tv_number_rolled);


        //doNumberRoll();

        et_min.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {

                    getWindow().getDecorView().clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_min.getWindowToken(), 0);

                }
                return false;
            }
        });

        et_max.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {

                    getWindow().getDecorView().clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_max.getWindowToken(), 0);

                }
                return false;
            }
        });

        number_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getWindow().getDecorView().clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_min.getWindowToken(), 0);

                doNumberRoll();

            }
        });

        dice_container = findViewById(R.id.dice_container);
        dice = findViewById(R.id.img_dice);
        dice_results = findViewById(R.id.dice_results);
        dice_rolled = findViewById(R.id.tv_dice_rolled);

        dice_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doDiceRoll();

            }
        });

        coin_container = findViewById(R.id.coin_container);
        coin = findViewById(R.id.img_coin);
        coin_results = findViewById(R.id.coin_results);
        coin_flipped = findViewById(R.id.tv_coin_flipped);

        coin_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doCoinFlip();

            }
        });

        card_container = findViewById(R.id.card_container);
        card = findViewById(R.id.img_card);
        card_results = findViewById(R.id.card_results);
        card_drawn = findViewById(R.id.tv_card_drawn);

        card_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doDrawCard();

            }
        });
    }

    public void doNumberRoll() {

        if (et_min.getText().toString().matches("")) {
            et_min.setText("0");
        }

        int min = Integer.parseInt(et_min.getText().toString());

        if (et_max.getText().toString().matches("")) {
            if (min < 100) {
                et_max.setText("100");
            } else {
                et_max.setText(String.valueOf(min));
            }
        }

        int max = Integer.parseInt(et_max.getText().toString());

        if (min > max) {
            min = max;
            et_min.setText(String.valueOf(max));
        }

        Random rn = new Random();
        int rand = rn.nextInt(max - min + 1) + min;

        num.setText(String.valueOf(rand));

        TextView new_number_result = new TextView(this);
        new_number_result.setText(String.valueOf(rand));
        new_number_result.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        number_results.addView(new_number_result, 1);

    }

    public void doDiceRoll() {

        Random rn = new Random();
        int rand = rn.nextInt(6) + 1;

        switch (rand) {
            case 1:
                dice.setImageResource(getResources().getIdentifier("dice_1", "drawable", getPackageName()));
                break;
            case 2:
                dice.setImageResource(getResources().getIdentifier("dice_2", "drawable", getPackageName()));
                break;
            case 3:
                dice.setImageResource(getResources().getIdentifier("dice_3", "drawable", getPackageName()));
                break;
            case 4:
                dice.setImageResource(getResources().getIdentifier("dice_4", "drawable", getPackageName()));
                break;
            case 5:
                dice.setImageResource(getResources().getIdentifier("dice_5", "drawable", getPackageName()));
                break;
            case 6:
                dice.setImageResource(getResources().getIdentifier("dice_6", "drawable", getPackageName()));
                break;
        }

        TextView new_dice_result = new TextView(this);
        new_dice_result.setText(String.valueOf(rand));
        new_dice_result.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        dice_results.addView(new_dice_result, 1);

    }

    public void doCoinFlip() {

        Random rn = new Random();
        int rand = rn.nextInt(2);

        switch (rand) {
            case 0:
                coin.setImageResource(getResources().getIdentifier("coin_head", "drawable", getPackageName()));
                break;
            case 1:
                coin.setImageResource(getResources().getIdentifier("coin_tail", "drawable", getPackageName()));
                break;
        }

        TextView new_coin_result = new TextView(this);
        if (rand == 0) {
            new_coin_result.setText("Heads");
        } else {
            new_coin_result.setText("Tails");
        }

        new_coin_result.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        coin_results.addView(new_coin_result, 1);

    }

    public void doDrawCard() {

        Random rn = new Random();
        int rand_joker = rn.nextInt(28);
        int rand_suit = 0;
        int rand_number = 0;
        String card_suit = "";
        String card_number = "";
        String card_result_text = "";

        if (rand_joker == 0) {
            card.setImageResource(getResources().getIdentifier("card_xjoker", "drawable", getPackageName()));
        } else {
            //rn = new Random();
            rand_suit = rn.nextInt(4);
            rand_number = rn.nextInt(13) + 1;

            if (rand_number == 1) {
                card_number = "a";
                card_result_text = "Aces of ";
            } else if (rand_number == 11) {
                card_number = "j";
                card_result_text = "Jack of ";
            } else if (rand_number == 12) {
                card_number = "q";
                card_result_text = "Queen of ";
            } else if (rand_number == 13) {
                card_number = "k";
                card_result_text = "King of ";
            } else {
                card_number = Integer.toString(rand_number);
                card_result_text = rand_number + " of ";
            }

            switch (rand_suit) {
                case 0:
                    card_suit = "c";
                    card_result_text = card_result_text + "Clubs";
                    break;
                case 1:
                    card_suit = "d";
                    card_result_text = card_result_text + "Diamonds";
                    break;
                case 2:
                    card_suit = "h";
                    card_result_text = card_result_text + "Hearts";
                    break;
                case 3:
                    card_suit = "s";
                    card_result_text = card_result_text + "Spades";
                    break;
            }

            card.setImageResource(getResources().getIdentifier("card_" + card_number + card_suit, "drawable", getPackageName()));
        }

        TextView new_card_result = new TextView(this);


        if (rand_joker == 0) {
            new_card_result.setText("Joker");
        } else {
            new_card_result.setText(card_result_text);
        }

        new_card_result.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        card_results.addView(new_card_result, 1);

    }

    public void handleShakeEvent(int count) {

        //if (count == 1){
        if (number_container.getVisibility() == View.VISIBLE) {
            doNumberRoll();
        } else if (dice_container.getVisibility() == View.VISIBLE) {
            doDiceRoll();
        } else if (coin_container.getVisibility() == View.VISIBLE) {
            doCoinFlip();
        } else if (card_container.getVisibility() == View.VISIBLE) {
            doDrawCard();
        }
        //}
    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

}
