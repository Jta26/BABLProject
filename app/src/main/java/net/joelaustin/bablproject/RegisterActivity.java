package net.joelaustin.bablproject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RegisterActivity extends Activity implements OnItemSelectedListener{
    //Facebook Objects
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private TextView txvFacebook;

    //String Array for Storing the Languages
    public String[] strArr = new String[5];
    //Spinner Objects
    List<String> listLang;
    ArrayAdapter<String> adapterLang;
    Spinner spinnerLang;

    int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        //Spinner List Things
        listLang = new ArrayList<>(Arrays.asList("Nothing","English", "Japanese", "German", "Chinese", "Latin", "Cantonese", "French"));
        spinnerLang = (Spinner) findViewById(R.id.spinnerLang);
        adapterLang = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listLang);
        adapterLang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLang.setAdapter(adapterLang);
        spinnerLang.setOnItemSelectedListener(this);
        //Facebook Things
        callbackManager = CallbackManager.Factory.create();
        txvFacebook = (TextView)findViewById(R.id.txvFacebookDesc);
        loginButton = (LoginButton)findViewById(R.id.btnFacebook);
        FacebookSdk.sdkInitialize(getApplicationContext());
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                txvFacebook.setText("User ID: " + loginResult.getAccessToken().getUserId() + "\n" + "Login Token: " + loginResult.getAccessToken().getToken());
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
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    //When The Spinner is Clicked
    public void onItemSelected(final AdapterView<?> parent, View v, final int pos, long id){


        if (index > 4){

            String strMaxLang = getResources().getString(R.string.maxLang);
            Toast.makeText(getApplication().getBaseContext(), strMaxLang, Toast.LENGTH_SHORT).show();
        }
        else if (pos == 0) {

        }
        else {
            //Some XML Formatting
            final LinearLayout linLang = (LinearLayout) findViewById(R.id.linLang);
            final LinearLayout linLangX = new LinearLayout(this);
            linLangX.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams linXMarginParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linXMarginParams.setMargins(0,0,0,0);
            linLangX.setLayoutParams(linXMarginParams);

            //The TextView with the name of the Language
            final TextView txvLang = new TextView(this);
            String strLang = parent.getItemAtPosition(pos).toString();
            txvLang.setText(Integer.toString(index + 1) + ". " + strLang);
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
            LinearLayout.LayoutParams btnRemoveLangParams = new LinearLayout.LayoutParams(120,120);
            btnRemoveLangParams.setMargins(0,0,400,0);
            btnRemoveLang.setLayoutParams(btnRemoveLangParams);
            btnRemoveLang.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    linLang.removeView(linLangX);
                    index--;
                    listLang.add(txvLang.getText().toString().substring(3));


                }
            });
            linLangX.addView(btnRemoveLang);

            //Adds to An Array

            strArr[index] = strLang;
            listLang.remove(pos);
            adapterLang.notifyDataSetChanged();
            spinnerLang.setSelection(0);
            index++;

        }

    }
    public void onNothingSelected(AdapterView<?> parent){

    }

    public void btnSubmitOnClick(View v) {
        //Hash Passwords Here

        //Calls the Database Class

        //Strings for base User data
        EditText edtUsername = (EditText) findViewById(R.id.edtUsername);
        EditText edtPassword = (EditText) findViewById(R.id.edtPassword);
        EditText edtPasswordConfirm = (EditText) findViewById(R.id.edtPasswordConfirm);
        EditText edtFirstName = (EditText) findViewById(R.id.edtFirstName);

        String strUsername = edtUsername.getText().toString();
        String strPassword = edtPassword.getText().toString();
        String strPasswordConfirm = edtPasswordConfirm.getText().toString();
        String strFirstName = edtFirstName.getText().toString();

        if (TextUtils.isEmpty(edtUsername.getText().toString())){
            Toast.makeText(getApplication().getBaseContext(), R.string.FieldCantEmpty, Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(edtPassword.getText().toString())) {
            Toast.makeText(getApplication().getBaseContext(), R.string.FieldCantEmpty, Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(edtFirstName.getText().toString())) {
            Toast.makeText(getApplication().getBaseContext(), R.string.FieldCantEmpty, Toast.LENGTH_LONG).show();
        }
        else {


            //User's Campus
            RadioButton rdbPittsburgh = (RadioButton) findViewById(R.id.rdbPittsburgh);
            RadioButton rdbJohnstown = (RadioButton) findViewById(R.id.rdbJohnstown);
            RadioButton rdbBradford = (RadioButton) findViewById(R.id.rdbBradford);
            RadioButton rdbTitusville = (RadioButton) findViewById(R.id.rdbTitusville);
            RadioButton rdbGreensburg = (RadioButton) findViewById(R.id.rdbGreensburg);
            Integer intCampusSelect = 6;

            if (rdbPittsburgh.isChecked()) {
                intCampusSelect = 0;
            } else if (rdbJohnstown.isChecked()) {
                intCampusSelect = 1;
            } else if (rdbBradford.isChecked()) {
                intCampusSelect = 2;
            } else if (rdbTitusville.isChecked()) {
                intCampusSelect = 3;
            } else if (rdbGreensburg.isChecked()) {
                intCampusSelect = 4;
            }

            //Bools for Campuses User wants to find people from
            Boolean boolMain = false;
            Boolean boolJohnstown = false;
            Boolean boolBradford = false;
            Boolean boolTitusville = false;
            Boolean boolGreensburg = false;

            CheckBox chkMain = (CheckBox) findViewById(R.id.chkMain);
            CheckBox chkJohnstown = (CheckBox) findViewById(R.id.chkJohnstown);
            CheckBox chkBradford = (CheckBox) findViewById(R.id.chkBradford);
            CheckBox chkTitusville = (CheckBox) findViewById(R.id.chkTitusville);
            CheckBox chkGreensburg = (CheckBox) findViewById(R.id.chkGreensburg);
            if (chkMain.isChecked()) {
                boolMain = true;
            }
            if (chkJohnstown.isChecked()) {
                boolJohnstown = true;
            }
            if (chkBradford.isChecked()) {
                boolBradford = true;
            }
            if (chkTitusville.isChecked()) {
                boolTitusville = true;
            }
            if (chkGreensburg.isChecked()) {
                boolGreensburg = true;
            }

            //Checks if passwords are equal
            if (strPassword.equals(strPasswordConfirm)) {
                new BABLDatabase(getApplication().getBaseContext(), strUsername, strPassword, strFirstName, intCampusSelect, boolMain, boolJohnstown, boolBradford, boolTitusville, boolGreensburg).execute(strArr);

            } else {
                Toast.makeText(getApplication().getBaseContext(), "Passwords to not match", Toast.LENGTH_LONG);
            }

        }
    }
}
