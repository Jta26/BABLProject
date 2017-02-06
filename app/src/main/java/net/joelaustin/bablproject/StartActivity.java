package net.joelaustin.bablproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        String strPassword = edtUsername.getText().toString();

        Boolean boolVerify;
        BABLLoginVerify loginClass = new BABLLoginVerify();
        loginClass.VerifyLogin(strUsername, strPassword);



    }
}
