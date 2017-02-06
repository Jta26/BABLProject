package net.joelaustin.bablproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }
    public void btnRegisterOnClick(View v){
        Intent intentRegister = new Intent(this, RegisterActivity.class);
        startActivity(intentRegister);
    }
    public void btnLoginOnClick(View v) {

        EditText edtUsername = (EditText) findViewById(R.id.edtLoginUsername);
        EditText edtPassword = (EditText) findViewById(R.id.edtLoginPassword);
        String strUsername = edtUsername.getText().toString();
        String strPassword = edtPassword.getText().toString();

        Boolean boolVerify;
        BABLLoginVerify loginClass = new BABLLoginVerify();
        boolVerify = loginClass.VerifyLogin(strUsername, strPassword);

        if (boolVerify == true) {
            Intent intentMainActivity = new Intent(this, MainActivity.class);
            startActivity(intentMainActivity);
        }
        else {
            Toast.makeText(getApplication().getBaseContext(), R.string.incorrectPass, Toast.LENGTH_LONG);
        }



    }
}
