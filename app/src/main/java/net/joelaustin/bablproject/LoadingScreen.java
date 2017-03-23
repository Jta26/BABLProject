package net.joelaustin.bablproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LoadingScreen extends AppCompatActivity implements AsyncResponse{

    public static String strUsername;
    public static String strPassword;
    public TextView txvProgressReport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        txvProgressReport = (TextView) findViewById(R.id.txvProgressReport);

        BABLLoginVerify loginVerify = new BABLLoginVerify(this, strUsername, strPassword);
        loginVerify.delegate = this;
        loginVerify.execute();
    }

    @Override
    public void processFinish(String output) {
        txvProgressReport.setText(output);
    }
}
