package net.joelaustin.bablproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        BABLDatabase dbConn = new BABLDatabase();
        dbConn.DbLoginInput("User1", "Pass");
    }
    public void btnRegisterOnClick(View v){
        Intent intentRegister = new Intent(this, Register.class);
        startActivity(intentRegister);
    }
}
