package com.example.waleed.fibonacci;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class Fibonacci extends ActionBarActivity {

    TextView ansView;
    EditText inTxt;
    Button calcBtn, clearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fibonacci);

        ansView = (TextView) findViewById(R.id.ansView);
        inTxt = (EditText) findViewById(R.id.inTxt);
        calcBtn = (Button) findViewById(R.id.calcBtn);
        clearBtn = (Button) findViewById(R.id.clearBtn);
        initStuff();
    }

    private void initStuff(){
        inTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcBtn.setEnabled(s.length() > 0);
                clearBtn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        calcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputNum = inTxt.getText().toString();
                int n = Integer.parseInt(inputNum);
                String sequence = getFibSequence(n);
                ansView.setText(sequence.subSequence(0, sequence.length()));
                try{
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e){}
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inTxt.setText("");
                ansView.setText("");
                calcBtn.setEnabled(false);
                clearBtn.setEnabled(false);
            }
        });
    }

    private String getFibSequence(int n){
        ArrayList<Long> fib = new ArrayList<Long>();
        int count = 0;
        if (n > 0) { fib.add((long)1); count++; }
        if (n > 1) { fib.add((long)1); count++; }
        while (count < n) {
            fib.add(fib.get(count - 1) + fib.get(count - 2));
            count++;
        }
        String sequence = "";
        for (int i=0; i < count; i++){
            sequence = sequence + fib.get(i).toString() + " ";
        }
        return sequence;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fibonacci, menu);
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
}

class BigInteger{
    private int length;
    private int [] value = new int [100];

    public void setNumber( String number ){
        length = number.length();
        int j=0;
        for( int i=number.length()-1; i>=0; i-- ){
            char cr = number.charAt(i);
            value[j] = Integer.parseInt("" + cr);
            j++;
        }
    }

    public String getNumber() {
        String s = "";
        for( int i=length-1; i>=0; i-- )
            s = s + (char)(value[i]+'0');
        return s;
    }

    public BigInteger sum( final BigInteger a, final BigInteger b){
        BigInteger c = new BigInteger();
        int carry = 0;
        int i;
        for( i = 0 ; i<a.length || i<b.length; i++ ){
            c.value[i] = ( (i<a.length?a.value[i]:0) + (i<b.length?b.value[i]:0) + carry )%10;
            carry = ( (i<a.length?a.value[i]:0) + (i<b.length?b.value[i]:0) + carry )/10;
        }
        if( carry > 0 ){
            c.value[i] = carry;
            i++;
        }
        c.length = i;
        return c;
    }

};