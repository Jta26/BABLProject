package net.joelaustin.bablproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//the class to edit account settings.
public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AsyncResponse {

    BABLMatchesDataLocal matchesDataLocal = new BABLMatchesDataLocal();

    private Integer intCampusSelect = 6;
    private Boolean boolMain = false;
    private Boolean boolJohnstown = false;
    private Boolean boolBradford = false;
    private Boolean boolTitusville = false;
    private Boolean boolGreensburg = false;

    BABLDataLocal localdata = new BABLDataLocal();
    ExternalFunctions extFunc = new ExternalFunctions();

    //Facebook Objects
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private TextView txvFacebook;
    private String strFacebookId;

    //String Array for Storing the Languages
    public String[] strArr = new String[5];
    //Spinner Objects
    List<String> listLang;
    ArrayAdapter<String> adapterLang;
    Spinner spinnerLang;


    RadioButton rdbSettingsPittsburgh;
    RadioButton rdbSettingsJohnstown;
    RadioButton rdbSettingsBradford;
    RadioButton rdbSettingsTitusville;
    RadioButton rdbSettingsGreensburg;

    CheckBox chkSettingsMain;
    CheckBox chkSettingsJohnstown;
    CheckBox chkSettingsBradford;
    CheckBox chkSettingsTitusville;
    CheckBox chkSettingsGreensburg;

    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        extFunc.buttonEffect(btnSubmit);
        //Spinner List Things
        listLang = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.LanguagesArray)));
        java.util.Collections.sort(listLang);
        Collections.reverse(listLang);
        listLang.add("Select");
        Collections.reverse(listLang);
        spinnerLang = (Spinner) findViewById(R.id.spinnerLang);
        adapterLang = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listLang);
        adapterLang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLang.setAdapter(adapterLang);
        spinnerLang.setOnItemSelectedListener(this);
        //Facebook Things
        callbackManager = CallbackManager.Factory.create();
        txvFacebook = (TextView) findViewById(R.id.txvFacebookDesc);
        loginButton = (LoginButton) findViewById(R.id.btnFacebook);
        FacebookSdk.sdkInitialize(getApplicationContext());
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                txvFacebook.setText(R.string.facebooksuccess);

            }

            @Override
            public void onCancel() {
                txvFacebook.setText("Login Attempt Canceled");
            }

            @Override
            public void onError(FacebookException error) {
                txvFacebook.setText("Login Attempt Failed");
            }
        });

        //Reads Data from BABLDataLocal
        EditText edtSettingsUsername = (EditText) findViewById(R.id.edtSettingsUsername);
        EditText edtSettingsFirstName = (EditText) findViewById(R.id.edtSettingsFirstName);

        strArr[0] = localdata.get_strLang1();
        strArr[1] = localdata.get_strLang2();
        strArr[2] = localdata.get_strLang3();
        strArr[3] = localdata.get_strLang4();
        strArr[4] = localdata.get_strLang5();

        edtSettingsFirstName.setText(localdata.get_strFirstName());
        edtSettingsUsername.setText(localdata.get_strUsername());

        rdbSettingsPittsburgh = (RadioButton) findViewById(R.id.rdbSettingsPittsburgh);
        rdbSettingsJohnstown = (RadioButton) findViewById(R.id.rdbSettingsJohnstown);
        rdbSettingsBradford = (RadioButton) findViewById(R.id.rdbSettingsBradford);
        rdbSettingsTitusville = (RadioButton) findViewById(R.id.rdbSettingsTitusville);
        rdbSettingsGreensburg = (RadioButton) findViewById(R.id.rdbSettingsGreensburg);

        chkSettingsMain = (CheckBox) findViewById(R.id.chkSettingsMain);
        chkSettingsJohnstown = (CheckBox) findViewById(R.id.chkSettingsJohnstown);
        chkSettingsBradford = (CheckBox) findViewById(R.id.chkSettingsBradford);
        chkSettingsTitusville = (CheckBox) findViewById(R.id.chkSettingsTitusville);
        chkSettingsGreensburg = (CheckBox) findViewById(R.id.chkSettingsGreensburg);


        for (int i = 0; i < strArr.length; i++) {

            if (strArr[i] == null) {

            } else {
                final String strLang = strArr[i];
                //Some XML Formatting
                final LinearLayout linLang = (LinearLayout) findViewById(R.id.linLang);
                final LinearLayout linLangX = new LinearLayout(this);
                linLangX.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams linXMarginParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                linXMarginParams.setMargins(0, 0, 0, 0);
                linLangX.setLayoutParams(linXMarginParams);


                //The TextView with the name of the Language
                final TextView txvLang = new TextView(this);
                txvLang.setText(strLang);
                LinearLayout.LayoutParams txvLangParams = new LinearLayout.LayoutParams(800, ViewGroup.LayoutParams.WRAP_CONTENT);
                txvLang.setLayoutParams(txvLangParams);
                txvLang.setTextSize(25);
                txvLang.setTextColor(getResources().getColor(R.color.colorAccent));
                linLangX.addView(txvLang);
                linLang.addView(linLangX);

                //The Button to remove the Language
                final ImageButton btnRemoveLang = new ImageButton(this);
                btnRemoveLang.setImageResource(R.drawable.remove);
                btnRemoveLang.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                LinearLayout.LayoutParams btnRemoveLangParams = new LinearLayout.LayoutParams(120, 120);
                btnRemoveLangParams.setMargins(0, 0, 400, 0);
                btnRemoveLang.setLayoutParams(btnRemoveLangParams);
                btnRemoveLang.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        linLang.removeView(linLangX);
                        listLang.remove(0);
                        listLang.add(txvLang.getText().toString());
                        java.util.Collections.sort(listLang);
                        Collections.reverse(listLang);
                        listLang.add("Select");
                        Collections.reverse(listLang);
                        adapterLang.notifyDataSetChanged();
                        index--;
                        strArr[Arrays.asList(strArr).indexOf(strLang)] = null;


                    }
                });
                linLangX.addView(btnRemoveLang);

                //Adds to An Array


                listLang.remove(strArr[i]);
                adapterLang.notifyDataSetChanged();
                spinnerLang.setSelection(0);
                index++;
            }
        }

        //User's Campus
        switch(localdata.get_intCampusAttend())

        {
            case 0:
                rdbSettingsPittsburgh.setChecked(true);
                intCampusSelect = 0;
                break;
            case 1:
                rdbSettingsJohnstown.setChecked(true);
                intCampusSelect = 1;
                break;
            case 2:
                rdbSettingsBradford.setChecked(true);
                intCampusSelect = 2;
                break;
            case 3:
                rdbSettingsTitusville.setChecked(true);
                intCampusSelect = 3;
                break;
            case 4:
                rdbSettingsGreensburg.setChecked(true);
                intCampusSelect = 4;
                break;
        }

        if(localdata.get_boolMain()==true)
        {
            chkSettingsMain.setChecked(true);
            boolMain = true;
        }

        if(localdata.get_boolJohnstown()==true)
        {
            chkSettingsJohnstown.setChecked(true);
            boolJohnstown = true;
        }
        if(localdata.get_boolBradford()==true)
        {
            chkSettingsBradford.setChecked(true);
            boolBradford = true;
        }
        if(localdata.get_boolTitusville()==true)
        {
            chkSettingsTitusville.setChecked(true);
            boolTitusville = true;
        }
        if(localdata.get_boolGreensburg()==true)
        {
            chkSettingsGreensburg.setChecked(true);
            boolGreensburg = true;
        }
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    //When The Spinner is Clicked
    public void onItemSelected(final AdapterView<?> parent, View v, final int pos, long id) {


        if (index == 5) {
            String strMaxLang = getResources().getString(R.string.maxLang);
            Toast.makeText(getApplication().getBaseContext(), strMaxLang, Toast.LENGTH_SHORT).show();
        } else if (pos == 0) {

        } else {
            //Some XML Formatting
            final LinearLayout linLang = (LinearLayout) findViewById(R.id.linLang);
            final LinearLayout linLangX = new LinearLayout(this);
            linLangX.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams linXMarginParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linXMarginParams.setMargins(0, 0, 0, 0);
            linLangX.setLayoutParams(linXMarginParams);

            //The TextView with the name of the Language
            final TextView txvLang = new TextView(this);
            final String strLang = parent.getItemAtPosition(pos).toString();
            txvLang.setText(strLang);
            LinearLayout.LayoutParams txvLangParams = new LinearLayout.LayoutParams(800, ViewGroup.LayoutParams.WRAP_CONTENT);
            txvLang.setLayoutParams(txvLangParams);
            txvLang.setTextSize(25);
            txvLang.setTextColor(getResources().getColor(R.color.colorAccent));
            linLangX.addView(txvLang);
            linLang.addView(linLangX);

            //The Button to remove the Language
            final ImageButton btnRemoveLang = new ImageButton(this);
            btnRemoveLang.setImageResource(R.drawable.remove);
            btnRemoveLang.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            LinearLayout.LayoutParams btnRemoveLangParams = new LinearLayout.LayoutParams(120, 120);
            btnRemoveLangParams.setMargins(0, 0, 400, 0);
            btnRemoveLang.setLayoutParams(btnRemoveLangParams);
            btnRemoveLang.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    linLang.removeView(linLangX);
                    listLang.remove(0);
                    listLang.add(txvLang.getText().toString());
                    java.util.Collections.sort(listLang);
                    Collections.reverse(listLang);
                    listLang.add("Select");
                    Collections.reverse(listLang);
                    adapterLang.notifyDataSetChanged();
                    index--;
                    strArr[Arrays.asList(strArr).indexOf(strLang)] = null;
                }
            });
            linLangX.addView(btnRemoveLang);

            //Adds to An Array
            for (int i = 0; i <= strArr.length; i++) {

                if (strArr[i] == null) {
                    strArr[i] = strLang;
                    listLang.remove(pos);
                    adapterLang.notifyDataSetChanged();
                    spinnerLang.setSelection(0);
                    index++;
                    break;
                }
            }


        }
    }
    public void onNothingSelected(AdapterView<?> parent){

    }

    public void btnSubmitSettingsOnClick(View v) {


        //Checks if there is at least 1 language in strArr; if so Returns True
        Boolean boolHasLanguage = false;
        for(int i = 0; i < strArr.length; i++)
        {
            if(strArr[i] != null) {
                boolHasLanguage = true;
                break;
            }
        }
        if (boolHasLanguage == true) {
            //Checks if Which campus Radio button is Selected, Sets Attending Integer accordingly.
            if (rdbSettingsPittsburgh.isChecked()) {
                intCampusSelect = 0;
            } else if (rdbSettingsJohnstown.isChecked()) {
                intCampusSelect = 1;
            } else if (rdbSettingsBradford.isChecked()) {
                intCampusSelect = 2;
            } else if (rdbSettingsTitusville.isChecked()) {
                intCampusSelect = 3;
            } else if (rdbSettingsGreensburg.isChecked()) {
                intCampusSelect = 4;
            }
            //Determines if the user wants to find settings from the Main Campus
            if (chkSettingsMain.isChecked()) {
                boolMain = true;
            }
            else {
                boolMain = false;
            }
            //Determines if the user wants to find settings from Johnstown Campus
            if (chkSettingsJohnstown.isChecked()) {
                boolJohnstown = true;
            }
            else {
                boolJohnstown = false;
            }
            //Determines if the user wants to find settings from the Bradford Campus
            if (chkSettingsBradford.isChecked()) {
                boolBradford = true;
            }
            else {
                boolBradford = false;
            }
            //Determines if the user wants to find settings from the Titusville Campus
            if (chkSettingsTitusville.isChecked()) {
                boolTitusville = true;
            }
            else {
                boolTitusville = false;
            }
            //Determines if the user wants to find settings from the Greensburg Campus
            if (chkSettingsGreensburg.isChecked()) {
                boolGreensburg = true;
            }
            else {
                boolGreensburg = false;
            }

            if (strFacebookId == null) {
                //Gets the Users UserId from Facebook, then Logs the user out of Facebook.
                try {
                    strFacebookId = Profile.getCurrentProfile().getId();
                    LoginManager.getInstance().logOut();
                    Boolean boolNewUser = false;
                    BABLDatabase bablDatabase = new BABLDatabase(getApplication().getBaseContext(),boolNewUser,localdata.get_strUsername(),localdata.get_strHashedPass() , localdata.get_strFirstName(), intCampusSelect, boolMain, boolJohnstown, boolBradford, boolTitusville, boolGreensburg,strFacebookId);
                    matchesDataLocal.stackMatchID.clear();
                    bablDatabase.delegate = this;
                    bablDatabase.execute(strArr);
                    BABLUpdateMatches bablUpdateMatches = new BABLUpdateMatches(this);
                    bablUpdateMatches.delegate = this;
                    bablUpdateMatches.execute();
                }
                catch (Exception e){
                    Toast.makeText(this, R.string.connectfacebook, Toast.LENGTH_LONG).show();
                }
            }
            else {
                Boolean boolNewUser = false;
                BABLDatabase bablDatabase = new BABLDatabase(getApplication().getBaseContext(),boolNewUser,localdata.get_strUsername(),localdata.get_strHashedPass() , localdata.get_strFirstName(), intCampusSelect, boolMain, boolJohnstown, boolBradford, boolTitusville, boolGreensburg,strFacebookId);
                bablDatabase.delegate = this;
                bablDatabase.execute(strArr);
                matchesDataLocal.stackMatchID.clear();
                BABLUpdateMatches bablUpdateMatches = new BABLUpdateMatches(this);
                bablUpdateMatches.delegate = this;
                bablUpdateMatches.execute();
            }

        }
        else {
            Toast.makeText(getApplication().getBaseContext(), R.string.nolanguageselected, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void processFinish(String output) {

    }
}

