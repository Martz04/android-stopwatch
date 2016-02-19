package mx.itesm.mario.stopwatch;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class StopWatchActivity extends AppCompatActivity {

    private boolean isTimerStarted = false;

    Button startButton;
    Button stopButton;
    TextView minutesLabel;
    TextView secondsLabel;
    TextView millisLabel;

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
        minutesLabel = (TextView) findViewById(R.id.minutes_label);
        secondsLabel = (TextView) findViewById(R.id.seconds_label);
        millisLabel = (TextView) findViewById(R.id.millis_label);
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
        toggleStartButton();
    }



    public void stopTimer(View view) {
        isTimerStarted = true;
        toggleStartButton();
    }

    private void toggleStartButton() {
        if(!isTimerStarted) {
            String resetText = getResources().getText(R.string.reset_text_button).toString();
            startButton.setText(resetText);
            isTimerStarted = true;
        }else {
            isTimerStarted = false;
            String startText = getResources().getText(R.string.start_text_button).toString();
            startButton.setText(startText);
        }
    }
}
