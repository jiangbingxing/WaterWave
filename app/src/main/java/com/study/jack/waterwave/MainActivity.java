package com.study.jack.waterwave;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private WaterWave waterWave;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        waterWave= (WaterWave) findViewById(R.id.waterWave);
        waterWave.startAnimation();

    }
}
