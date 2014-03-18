package com.codepath.bootcamp.tipcalculator;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
    private EditText etAmount;
    private TextView tvPercentVal, tvTipAmount, tvTotalAmount;
    private Button btn15, btn18, btn20;
    private TextView tvFifteenAmount, tvEighteenAmount, tvTwentyAmount;
    private SeekBar sbPercentage;
    private float billAmount;
    private int tipPercent;
    private float tip;
    DecimalFormat df = new DecimalFormat("$#,###,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize bill amount
        billAmount = 0.0f;
        etAmount = (EditText) findViewById(R.id.etAmount);
        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    billAmount = Float.parseFloat(s.toString());
                    recalcTip();
                }
                catch (NumberFormatException nfe) {
                    // TODO: handle this later
                    Log.e("kuoj", "number format exception!");
                }
            }
        });

        // initialize tip percentage
        tvPercentVal = (TextView) findViewById(R.id.tvPercentVal);
        setTipPercentage(15); // 15% tip, by default

        // initialize tip slider
        sbPercentage = (SeekBar) findViewById(R.id.sbPercentage);
        sbPercentage.setProgress(tipPercent);
        sbPercentage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if ( sbPercentage == seekBar ) {
                    updateTipPercentage(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // do nothing
            }
         });

        // initialize tip, suggested, and total amounts
        tvFifteenAmount = (TextView) findViewById(R.id.tvFifteenAmount);
        tvEighteenAmount = (TextView) findViewById(R.id.tvEighteenAmount);
        tvTwentyAmount = (TextView) findViewById(R.id.tvTwentyAmount);
        btn15 = (Button) findViewById(R.id.btnFifteen);
        btn15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sbPercentage.setProgress(15);
                updateTipPercentage(15);
            }
        });
        btn18 = (Button) findViewById(R.id.btnEighteen);
        btn18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sbPercentage.setProgress(18);
                updateTipPercentage(18);
            }
        });
        btn20 = (Button) findViewById(R.id.btnTwenty);
        btn20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sbPercentage.setProgress(20);
                updateTipPercentage(20);
            }
        });
        tvTipAmount = (TextView) findViewById(R.id.tvTipAmount);
        tvTotalAmount = (TextView) findViewById(R.id.tvTotalAmount);
        recalcTip();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Sets tip percent and updates the text on the screen.
     * 
     * @param percent Integer value between 0 and 100
     */
    private void setTipPercentage(int percent) {
        tipPercent = percent;
        if ( null != tvPercentVal ) {
            tvPercentVal.setText(" " + String.valueOf(tipPercent) + "%");
        }
    }

    private void updateTipPercentage(int percent) {
        setTipPercentage(percent);
        recalcTip();
    }

    private void recalcTip() {
        tip = billAmount * tipPercent / 100.0f;
        tvFifteenAmount.setText(df.format(billAmount * 0.15d));
        tvEighteenAmount.setText(df.format(billAmount * 0.18d));
        tvTwentyAmount.setText(df.format(billAmount * 0.2d));
        tvTipAmount.setText(df.format(tip));
        tvTotalAmount.setText(df.format(billAmount + tip));
    }
}
