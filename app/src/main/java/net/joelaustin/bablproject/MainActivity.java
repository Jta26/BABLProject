package net.joelaustin.bablproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.login.LoginManager;

public class MainActivity extends AppCompatActivity {



    BABLDataLocal dataLocal = new BABLDataLocal();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new BABLMatchRetrieve(this).execute();

        TextView txvWelcome = (TextView) findViewById(R.id.txvWelcome);

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
    public void btnAccountSettingsOnClick(View v) {
        Intent intent = new Intent(getApplication().getBaseContext(), SettingsActivity.class);
        startActivity(intent);
    }
    //Logout button event handler
    public void btnLogoutOnClick(View v) {


        dataLocal.set_strFirstName(null);
        dataLocal.set_strUsername(null);
        dataLocal.set_strFacebookId(null);
        dataLocal.set_strLang1(null);
        dataLocal.set_strLang2(null);
        dataLocal.set_strLang3(null);
        dataLocal.set_strLang4(null);
        dataLocal.set_strLang5(null);
        dataLocal.set_intCampusAttend(null);
        dataLocal.set_boolMain(null);
        dataLocal.set_boolJohnstown(null);
        dataLocal.set_boolBradford(null);
        dataLocal.set_boolTitusville(null);
        dataLocal.set_boolGreensburg(null);
        dataLocal.set_intUserID(null);

        LoginManager.getInstance().logOut();

        Intent intent = new Intent(getApplication().getBaseContext(), StartActivity.class);
        startActivity(intent);


    }

}
