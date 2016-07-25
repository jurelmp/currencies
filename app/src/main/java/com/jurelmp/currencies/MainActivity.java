package com.jurelmp.currencies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // defines members that correspond to Views in our layout
    private Button mCalcButton;
    private TextView mConvertedTextView;
    private EditText mAmountEditText;
    private Spinner mForSpinner, mHomSpinner;
    private String[] mCurrencies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // unpack ArrayList from the bundle and convert to array
        ArrayList<String> arrayList = ((ArrayList<String>) getIntent().getSerializableExtra(SplashActivity.KEY_ARRAYLIST));
        Collections.sort(arrayList);
        mCurrencies = arrayList.toArray(new String[arrayList.size()]);

        // assign references to our Views
        mConvertedTextView = (TextView) findViewById(R.id.txt_converted);
        mAmountEditText = (EditText) findViewById(R.id.edit_amount);
        mCalcButton = (Button) findViewById(R.id.btn_calc);
        mForSpinner = (Spinner) findViewById(R.id.spn_for);
        mHomSpinner = (Spinner) findViewById(R.id.spn_hom);

        // controller: mediates model and view
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                // context
                this,
                // view layout you see when the spinner is closed
                R.layout.spinner_closed,
                mCurrencies
        );

        // view: layout youe see when the spinner is open
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // assign adapters to spinner
        mHomSpinner.setAdapter(arrayAdapter);
        mForSpinner.setAdapter(arrayAdapter);

        mHomSpinner.setOnItemSelectedListener(this);
        mForSpinner.setOnItemSelectedListener(this);
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

        switch (id) {
            case R.id.mnu_invert:
                invertCurrencies();
                break;
            case R.id.mnu_codes:
                launchBrowser(SplashActivity.URL_CODES);
                break;
            case R.id.mnu_exit:
                finish();
                break;
        }
        return true;
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void launchBrowser(String url) {
        if (isOnline()) {
            Uri uri = Uri.parse(url);
            // call an implicit intent
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    private void invertCurrencies() {
        int nFor = mForSpinner.getSelectedItemPosition();
        int nHom = mHomSpinner.getSelectedItemPosition();

        mForSpinner.setSelection(nHom);
        mHomSpinner.setSelection(nFor);

        mConvertedTextView.setText("");
    }

    private int findPositionGivenCode(String code, String[] mCurrencies) {
        for (int i = 0; i < mCurrencies.length; i++) {
            if ((mCurrencies[i]).substring(0, 3).equalsIgnoreCase(code)) {
                return i;
            }
        }
        // default
        return 0;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spn_for:
                // TODO for spinner foreign currency value
                break;
            case R.id.spn_hom:
                // TODO for spinner home currency value
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
