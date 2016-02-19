package mx.itesm.mario.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class StopWatchActivity extends AppCompatActivity {

    private boolean isTimerStarted = false;

    private Handler mHandler = new Handler();

    private Button startButton;
    private Button stopButton;

    private TextView hoursLabel;
    private TextView minutesLabel;
    private TextView secondsLabel;
    private TextView millisLabel;

    private ListView listView;

    private long milliseconds;
    private long seconds;
    private long minutes;
    private long hours;

    private long startTime;
    private long elapsedTime;
    private final int REFRESH_RATE = 10;
    private List<String> timeList;
    ArrayAdapter<String> adapter;

    private Runnable startTimerThread = new Runnable() {
        @Override
        public void run() {
            elapsedTime = System.currentTimeMillis() - startTime;
            updateTimer(elapsedTime);
            mHandler.postDelayed(this, REFRESH_RATE);
        }
    };
    private boolean isTimerStopped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        startButton = (Button)findViewById(R.id.start_button);
        stopButton = (Button)findViewById(R.id.stop_button);
        hoursLabel = (TextView) findViewById(R.id.hours_label);
        minutesLabel = (TextView) findViewById(R.id.minutes_label);
        secondsLabel = (TextView) findViewById(R.id.seconds_label);
        millisLabel = (TextView) findViewById(R.id.millis_label);

        timeList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, timeList);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stop_watch, menu);
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

    public void startTimer(View view) {
        if(isTimerStarted) {
            addElapsedTimeToList();
            clearTimerLabels();
            mHandler.removeCallbacks(startTimerThread);
            isTimerStarted = false;
        }else {
            isTimerStarted = true;
            startTime = System.currentTimeMillis();
            mHandler.removeCallbacks(startTimerThread);
            mHandler.postDelayed(startTimerThread, 0);
        }
        toggleStartButton();
    }

    private void addElapsedTimeToList() {
        StringBuilder builder = new StringBuilder();
        builder.append(hoursLabel.getText().toString());
        builder.append(":");
        builder.append(minutesLabel.getText().toString());
        builder.append(":");
        builder.append(secondsLabel.getText().toString());
        builder.append(".");
        builder.append(millisLabel.getText().toString());

        String result = new String(builder);
        timeList.add(result);
        adapter.notifyDataSetChanged();
    }

    private void clearTimerLabels() {
        updateTimer(0);
    }


    public void stopTimer(View view) {
        if(isTimerStarted) {
            if(isTimerStopped) {
                startTime = System.currentTimeMillis() - elapsedTime;
                mHandler.postDelayed(startTimerThread, 0);
                isTimerStopped = false;
            }else {
                mHandler.removeCallbacks(startTimerThread);
                isTimerStopped = true;
            }
            toogleStopButton();
        }

    }

    private void toogleStopButton() {
        if(isTimerStarted) {
            stopButton.setText(getResources().getText(R.string.resume_text_button));
        }else {
            stopButton.setText(getResources().getText(R.string.stop_text_button));
        }
    }

    private void toggleStartButton() {
        if(isTimerStarted) {
            startButton.setText(getResources().getText(R.string.reset_text_button));
        }else {
            startButton.setText(getResources().getText(R.string.start_text_button));
        }
    }

    private void updateTimer(float time){
        milliseconds = (long)(time % 1000);
        seconds = (long)(time/1000);
        minutes = (long)((time/1000)/60);
        hours = (long) (((time/1000)/60)/60);

        /*Log.d("update Timer", "time: " + time +
                " millis: " + milliseconds +
                " seconds: " + seconds +
                " minutes: " + minutes +
                " hours: " + hours); */
        if(milliseconds == 0) {
            millisLabel.setText("000");
        }else if(milliseconds < 100 && milliseconds > 0){
            millisLabel.setText("0" + Long.toString(milliseconds));
        }else {
            millisLabel.setText(Long.toString(milliseconds));
        }
        seconds = seconds % 60;
        if(seconds == 0) {
            secondsLabel.setText("00");
        }else if(seconds < 10 && seconds > 0) {
            secondsLabel.setText("0" + Long.toString(seconds));
        }else {
            secondsLabel.setText(Long.toString(seconds));
        }

        minutes = minutes % 60;
        if(minutes == 0) {
            minutesLabel.setText("00");
        }else if(minutes < 10 && minutes > 0){
            minutesLabel.setText("0" + Long.toString(minutes));
        }else {
            minutesLabel.setText(Long.toString(minutes));
        }
    }
}
