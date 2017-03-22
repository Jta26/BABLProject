package net.joelaustin.bablproject;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

import pl.droidsonroids.gif.GifImageView;

//the class to display that find matches function of the system.
public class MatchesActivity extends AppCompatActivity implements AsyncResponse {

    BABLDataLocal localdata = new BABLDataLocal();
    BABLMatchesDataLocal localmatchdata = new BABLMatchesDataLocal();
    ExternalFunctions extfunc = new ExternalFunctions();

    ImageButton btnConfirm;
    ImageButton rejectButton;
    ImageButton backButton;
    GifImageView imgLoading;
    private ProfilePictureView matchprofilepic;
    Context context;
    TextView suggestionName;
    TextView suggestionLang;

    boolean matchChoice = false;

    public MatchesActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        btnConfirm = (ImageButton) findViewById(R.id.confirmButton);
        rejectButton = (ImageButton) findViewById(R.id.rejectButton);
        matchprofilepic = (ProfilePictureView) findViewById(R.id.suggestionImage);
        suggestionName = (TextView) findViewById(R.id.suggestionName);
        suggestionLang = (TextView) findViewById(R.id.suggestionLang);

        imgLoading = (GifImageView) findViewById(R.id.imgLoading);

        suggestionName.setText(R.string.findingmatches);
        extfunc.buttonEffect(btnConfirm);
        extfunc.buttonEffect(rejectButton);
        btnConfirm.setVisibility(View.GONE);
        rejectButton.setVisibility(View.GONE);
        matchprofilepic.setVisibility(View.GONE);
        suggestionLang.setVisibility(View.GONE);



        try {

        } catch (Exception e) {
            Toast.makeText(this, "You Might Need to Login to Facebook", Toast.LENGTH_LONG).show();

        }
        BABLMatchDataRetrieve matchdata = new BABLMatchDataRetrieve(this);
        matchdata.delegate = this;
        matchdata.execute();
    }
    @Override
    public void processFinish(String output) {
        if (localmatchdata.isEmpty()) {
            matchprofilepic.setVisibility(View.GONE);
            suggestionLang.setVisibility(View.GONE);
            imgLoading.setVisibility(View.GONE);
            ImageButton btnConfirm = (ImageButton) findViewById(R.id.confirmButton);
            btnConfirm.setVisibility(View.GONE);
            ImageButton btnReject = (ImageButton) findViewById(R.id.rejectButton);
            btnReject.setVisibility(View.GONE);
            suggestionName.setGravity(View.TEXT_ALIGNMENT_CENTER);
            suggestionName.setText(R.string.nomatches);
        } else {
            try {
                btnConfirm.setVisibility(View.VISIBLE);
                rejectButton.setVisibility(View.VISIBLE);
                suggestionName.setVisibility(View.VISIBLE);
                suggestionLang.setVisibility(View.VISIBLE);
                matchprofilepic.setVisibility(View.VISIBLE);
                imgLoading.setVisibility(View.GONE);
                matchprofilepic.setProfileId(localmatchdata.get_strFacebookID());
                suggestionName.setText(localmatchdata.get_strFirstName() + " " + getResources().getString(R.string.speaks));
                if (localmatchdata.get_strLang1() == null) {
                    localmatchdata.set_strLang1("");

                }
                if (localmatchdata.get_strLang2() == null) {
                    localmatchdata.set_strLang2("");
                }
                if (localmatchdata.get_strLang3() == null) {
                    localmatchdata.set_strLang3("");
                }
                if (localmatchdata.get_strLang4() == null) {
                    localmatchdata.set_strLang4("");
                }
                if (localmatchdata.get_strLang5() == null) {
                    localmatchdata.set_strLang5("");
                }

                suggestionLang.setText(localmatchdata.get_strLang1() + "\n" + localmatchdata.get_strLang2() + "\n" +
                        localmatchdata.get_strLang3() + "\n" + localmatchdata.get_strLang4() + "\n" +
                        localmatchdata.get_strLang5());
            } catch (Exception e) {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void returnMainActivity(View v){
        Intent intent = new Intent(getBaseContext().getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void confirmMatch(View v) {

        try {
            matchChoice = true;
            new BABLEnterMatchResult(this).execute(matchChoice);
            BABLMatchDataRetrieve matchdata = new BABLMatchDataRetrieve(this);
            matchdata.delegate = this;
            matchdata.execute();
            btnConfirm.setVisibility(View.GONE);
            ImageButton btnReject = (ImageButton) findViewById(R.id.rejectButton);
            btnReject.setVisibility(View.GONE);
            matchprofilepic.setVisibility(View.GONE);
            suggestionLang.setVisibility(View.GONE);
            imgLoading.setVisibility(View.VISIBLE);
            suggestionName.setText(getResources().getString(R.string.pleasewait));
        }
        catch (Exception e) {

        }
    }
    public void rejectMatch(View v) {
        try {
            matchChoice = false;
            new BABLEnterMatchResult(this).execute(matchChoice);
            BABLMatchDataRetrieve matchdata = new BABLMatchDataRetrieve(this);
            matchdata.delegate = this;
            matchdata.execute();
            btnConfirm.setVisibility(View.GONE);
            ImageButton btnReject = (ImageButton) findViewById(R.id.rejectButton);
            btnReject.setVisibility(View.GONE);
            matchprofilepic.setVisibility(View.GONE);
        }
        catch (Exception e) {

        }
    }
}
