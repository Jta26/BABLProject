package net.joelaustin.bablproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
//The activity after being logged in.
public class MainActivity extends AppCompatActivity {

    BABLDataLocal dataLocal = new BABLDataLocal();
    BABLMatchesDataLocal matchDataLocal = new BABLMatchesDataLocal();
    ExternalFunctions extFunc = new ExternalFunctions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnMatches = (Button) findViewById(R.id.btnMatches);
        Button btnFindMatches = (Button) findViewById(R.id.btnViewMatches);
        Button btnSettings = (Button) findViewById(R.id.btnAccountSettings);
        Button btnLogout = (Button) findViewById(R.id.btnMainActivityLogout);
        extFunc.buttonEffect(btnMatches);
        extFunc.buttonEffect(btnFindMatches);
        extFunc.buttonEffect(btnSettings);
        extFunc.buttonEffect(btnLogout);

        TextView txvWelcome = (TextView) findViewById(R.id.txvWelcome);
        if (dataLocal.get_strFirstName().length() >= 11 ) {
            txvWelcome.setTextSize(30);
        }
        txvWelcome.setText(txvWelcome.getText() + " " + dataLocal.get_strFirstName());

        ImageView imgSign = (ImageView) findViewById(R.id.imgMain);

        switch (dataLocal.get_intCampusAttend()){

            case 0:
                imgSign.setImageResource(R.drawable.mainsign);
                break;
            case 1:
                imgSign.setImageResource(R.drawable.upjsign);
                break;
            case 2:
                imgSign.setImageResource(R.drawable.bradfordsign);
                break;
            case 3:
                imgSign.setImageResource(R.drawable.uptsign);
                break;
            case 4:
                imgSign.setImageResource(R.drawable.upgsign);
                break;

        }

    }

    public void btnFindMatchesOnClick(View v) {
        Intent intent = new Intent(getApplication().getBaseContext(), MatchesActivity.class);
        startActivity(intent);
    }
    public void btnViewMatchesOnClick(View v) {
        //TODO
        //Start ConfirmedMatches Activity
        Intent intent = new Intent(getApplication().getBaseContext(), ViewConfirmedMatches.class);
        startActivity(intent);

    }
    public void btnAccountSettingsOnClick(View v) {
        Intent intent = new Intent(getApplication().getBaseContext(), SettingsActivity.class);
        startActivity(intent);
    }
    //Logout button event handler
    public void btnLogoutOnClick(View v) {
        dataLocal.LocalDataLogout();
        matchDataLocal.LocalDataLogout();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(getApplication().getBaseContext(), StartActivity.class);
        startActivity(intent);
        Toast.makeText(this, R.string.logoutsucceess, Toast.LENGTH_SHORT).show();


    }
    public void onBackPressed(){
        btnLogoutOnClick(findViewById(R.id.activity_start));
    }


}
