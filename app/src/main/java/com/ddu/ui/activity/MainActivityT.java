package com.ddu.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ddu.R;
import com.ddu.icore.ui.activity.BaseActivity;
import com.ddu.widget.NumberProgressBar;
import com.ddu.widget.OnProgressBarListener;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivityT extends BaseActivity implements OnProgressBarListener {

    NumberProgressBar bnp;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_number);

        bnp = (NumberProgressBar)findViewById(R.id.numberbar1);
        bnp.setOnProgressBarListener(this);

        Button btn = (Button) findViewById(R.id.btnClick);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bnp.incrementProgressBy(1);
                            }
                        });
                    }
                }, 1000, 100);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProgressChange(int current, int max) {
        if(current == max){
            bnp.setProgress(0);
            bnp.setReachedBarColor(0xff3498DB);
            bnp.setProgressTextColor(0xff3498DB);
            timer.cancel();
        }

        if(current == 20){
            bnp.setReachedBarColor(0xff70A800);
            bnp.setProgressTextColor(0xff70A800);
        }else if(current == 30){
            bnp.setReachedBarColor(0xffFF3D7F);
            bnp.setProgressTextColor(0xffFF3D7F);
        }
        else if(current ==40){
            bnp.setReachedBarColor(0xffE74C3C);
            bnp.setProgressTextColor(0xffE74C3C);
        }
        else if(current ==50){
            bnp.setReachedBarColor(0xff6DBCDB);
            bnp.setProgressTextColor(0xff6DBCDB);
        }else if(current == 60){
            bnp.setReachedBarColor(0xffFFC73B);
            bnp.setProgressTextColor(0xffFFC73B);
        }
        else if(current == 70){
            bnp.setReachedBarColor(0xffFF530D);
            bnp.setProgressTextColor(0xffFF530D);
        }else if(current == 80){
            bnp.setReachedBarColor(0xffECF0F1);
            bnp.setProgressTextColor(0xffECF0F1);
        }
    }
}
