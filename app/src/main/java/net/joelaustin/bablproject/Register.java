package net.joelaustin.bablproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Register extends Activity implements OnItemSelectedListener{
    //Facebook Objects
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private TextView txvFacebook;

    //String Array for Storing the Languages
    String[] strArr;
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
            strArr = new String[5];
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


        BABLDatabase DbEnter = new BABLDatabase();
        EditText edtUsername = (EditText) findViewById(R.id.edtUsername);
        EditText edtPassword = (EditText) findViewById(R.id.edtPassword);
        EditText edtPasswordConfirm = (EditText) findViewById(R.id.edtPasswordConfirm);
        String strUsername = edtUsername.getText().toString();
        String strPassword = edtPassword.getText().toString();
        String strPasswordConfirm = edtPasswordConfirm.getText().toString();
        if (strPassword.equals(strPasswordConfirm)) {
            DbEnter.DbLoginInput(strUsername, strPassword);
        }
        else {
            Toast.makeText(getApplication().getBaseContext(), "Database Entry Method Here", Toast.LENGTH_SHORT).show();
        }



    }
}
