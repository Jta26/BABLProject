package net.joelaustin.bablproject;

import android.content.Intent;
import android.graphics.Color;
import android.icu.util.TimeUnit;
import android.os.CountDownTimer;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Time;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


    }
    //Register Button Event Handler
    public void btnRegisterOnClick(View v){
        Intent intentRegister = new Intent(this, RegisterActivity.class);
        startActivity(intentRegister);
    }
    //Login Button Event Handler
    public void btnLoginOnClick(View v) {
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        


        EditText edtUsername = (EditText) findViewById(R.id.edtLoginUsername);
        EditText edtPassword = (EditText) findViewById(R.id.edtLoginPassword);
        String strUsername = edtUsername.getText().toString();
        String strPassword = edtPassword.getText().toString();

        new BABLLoginVerify(StartActivity.this, strUsername, strPassword).execute();













    }
}
