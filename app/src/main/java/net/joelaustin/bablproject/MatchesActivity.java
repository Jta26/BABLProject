package net.joelaustin.bablproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

public class MatchesActivity extends AppCompatActivity {

    BABLDataLocal localdata = new BABLDataLocal();

    private ProfilePictureView profilepic;
    private ProfilePictureView matchprofilepic;
    Context context;
    TextView suggestionName;
    TextView suggestionLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        ImageButton rejectButton = (ImageButton) findViewById(R.id.rejectButton);
        ImageButton backButton = (ImageButton) findViewById(R.id.backButton);
        suggestionName = (TextView) findViewById(R.id.suggestionName);
        suggestionLang = (TextView) findViewById(R.id.suggestionLang);

        try {
            profilepic = (ProfilePictureView) findViewById(R.id.matchesButton);
            profilepic.setProfileId(localdata.get_strFacebookId());
        } catch (Exception e) {
            Toast.makeText(this, "You Might Need to Login to Facebook", Toast.LENGTH_LONG).show();

        }
    }
//
//        if(localdata.checkMatchesEmpty()){
//            Toast.makeText(this, "You Have No Matches Currently. Check Again Later.", Toast.LENGTH_LONG).show();
//        }
//        else {
//            try {
//                matchprofilepic = (ProfilePictureView) findViewById(R.id.suggestionImage);
//                matchprofilepic.setProfileId(localdata.getNextMatchFBID());
//                suggestionName.setText(localdata.getNextMatchName());
//                suggestionLang.setText(localdata.getNextMatchLang());
//            }
//            catch (Exception e){
//                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
//            }
//        }
//
//
//    }
//
//
//
//    public void returnMainActivity(View v){
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//    }
//
//    public void viewMatches(View v){
//
//    }
//
//    public void confirmMatch(View v){
//
//
//        localdata.removeMatch();
//        if(localdata.checkMatchesEmpty()){
//            Toast.makeText(this, "You Have No Matches Currently. Check Again Later.", Toast.LENGTH_LONG).show();
//        }
//        else {
//            try {
//                matchprofilepic = (ProfilePictureView) findViewById(R.id.suggestionImage);
//                matchprofilepic.setProfileId(localdata.getNextMatchFBID());
//                suggestionName.setText(localdata.getNextMatchName());
//                suggestionLang.setText(localdata.getNextMatchLang());
//            }
//            catch (Exception e){
//                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//
//    public void rejectMatch(View v){
//
//
//        localdata.removeMatch();
//        if(localdata.checkMatchesEmpty()){
//            Toast.makeText(this, "You Have No Matches Currently. Check Again Later.", Toast.LENGTH_LONG).show();
//        }
//        else {
//            try {
//                matchprofilepic = (ProfilePictureView) findViewById(R.id.suggestionImage);
//                matchprofilepic.setProfileId(localdata.getNextMatchFBID());
//                suggestionName.setText(localdata.getNextMatchName());
//                suggestionLang.setText(localdata.getNextMatchLang());
//            }
//            catch (Exception e){
//                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}
