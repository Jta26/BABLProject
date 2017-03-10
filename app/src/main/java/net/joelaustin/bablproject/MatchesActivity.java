package net.joelaustin.bablproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

public class MatchesActivity extends AppCompatActivity {

    BABLDataLocal localdata = new BABLDataLocal();
    private ProfilePictureView profilepic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        try {
            profilepic = (ProfilePictureView) findViewById(R.id.profPicFacebook);
            profilepic.setProfileId(localdata.get_strFacebookId());
        }
        catch (Exception e){
            Toast.makeText(this, "You Might Need to Login to Facebook", Toast.LENGTH_LONG).show();

        }



    }
}
