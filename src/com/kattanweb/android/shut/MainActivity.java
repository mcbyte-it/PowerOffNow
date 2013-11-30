package com.kattanweb.android.shut;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.stericson.RootTools.RootTools;

import java.util.List;

import eu.chainfire.libsuperuser.Shell;

public class MainActivity extends Activity {

    public static final long SECONDS_TO_WAIT = 10;

    private List<String> suResult = null;
    protected TextView counter = null;
    protected TextView debug = null;
    protected Button btnAbort = null;
    protected PowerOffCounter powerOffCounter;

    private class Startup extends AsyncTask<Void, Void, Void> {
        private boolean suAvailable = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            // Let's do some SU stuff
            suAvailable = RootTools.isAccessGiven();
            if (suAvailable) {
                suResult = Shell.SU.run(new String[]{"id", "reboot -p"});
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // output
            StringBuilder sb = new StringBuilder();

            if (suResult != null) {
                for (String line : suResult) {
                    sb.append(line).append((char) 10);
                }

                debug.setText(sb.toString());
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean suAvailable = RootTools.isRootAvailable() && RootTools.isAccessGiven();
        if (!suAvailable) {
            Intent noSU = new Intent(this, NoSuperUser.class);
            startActivity(noSU);
        }

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        setContentView(R.layout.activity_main);

        counter = (TextView) findViewById(R.id.txtCounter);
        debug = (TextView) findViewById(R.id.txtDebug);

        btnAbort = (Button) findViewById(R.id.btnAbort);
        btnAbort.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (powerOffCounter != null) {
                    powerOffCounter.cancel();
                }
                v.setEnabled(false);
                //finish();
            }
        });

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 100, 100, 100};
        v.vibrate(pattern, -1);

        powerOffCounter = new PowerOffCounter(SECONDS_TO_WAIT);
        powerOffCounter.start();
    }

    public class PowerOffCounter extends CountDownTimer {

        public PowerOffCounter(long seconds) {
            super(seconds * 1000, 1000);
        }

        @Override
        public void onFinish() {
            counter.setText("0");
            Log.d("KW-PO", "Tada... Counter done.... shutting off device");

            (new Startup()).execute();
        }

        @Override
        public void onTick(long millisUntilFinished) {

            String secs = String.valueOf(millisUntilFinished / 1000);
            counter.setText(secs);

            // some script here

        }
    }
}
