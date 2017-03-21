package net.joelaustin.bablproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StartActivity extends AppCompatActivity {

    ExternalFunctions extFunc = new ExternalFunctions();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button btnLogin = (Button) findViewById(R.id.btnSubmit);
        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        extFunc.buttonEffect(btnLogin);
        extFunc.buttonEffect(btnRegister);

    }
    //Register Button Event Handler
    public void btnRegisterOnClick(View v){
        Intent intentRegister = new Intent(this, RegisterActivity.class);
        startActivity(intentRegister);

    }
    //Login Button Event Handler
    public void btnLoginOnClick(View v) {
        final Button btnLogin = (Button) findViewById(R.id.btnSubmit);



        EditText edtUsername = (EditText) findViewById(R.id.edtLoginUsername);
        EditText edtPassword = (EditText) findViewById(R.id.edtLoginPassword);
        String strUsername = edtUsername.getText().toString();
        String strPassword = edtPassword.getText().toString();

        new BABLLoginVerify(StartActivity.this, strUsername, strPassword).execute();


    }

}
