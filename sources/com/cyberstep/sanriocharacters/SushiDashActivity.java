package com.cyberstep.sanriocharacters;

import android.os.Bundle;
import ls.sakana.SakanaActivity;

public class SushiDashActivity extends SakanaActivity {
    public void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("smartar");
        requestWindowFeature(1);
        getWindow().addFlags(1024);
        super.onCreate(savedInstanceState);
    }
}
