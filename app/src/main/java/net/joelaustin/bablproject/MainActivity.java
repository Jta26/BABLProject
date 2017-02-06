package net.joelaustin.bablproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BABLDataLocal dataLocal = new BABLDataLocal();
        TextView txvWelcome = (TextView) findViewById(R.id.txvWelcome);

        txvWelcome.setText(txvWelcome.getText() + " " + dataLocal.get_strFirstName());



    }
}
