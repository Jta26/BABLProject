package net.joelaustin.bablproject;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;

//the activity to show matches where both users have confirmed.
public class ViewConfirmedMatches extends AppCompatActivity implements AsyncResponse{

    BABLDataLocal localdata = new BABLDataLocal();
    BABLMatchesDataLocal matchlocaldata = new BABLMatchesDataLocal();
    LinearLayout linVertLayout;
    ProfilePictureView imgProfilePic;
    ProgressBar prgBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_confirmed_matches);

        BABLConfirmMatches bablConfirmMatches = new BABLConfirmMatches(this);
        bablConfirmMatches.delegate = this;
        bablConfirmMatches.execute();
        linVertLayout = (LinearLayout) findViewById(R.id.linMatches);
        prgBar = (ProgressBar) findViewById(R.id.prgbar);

        }

    @Override
    public void processFinish(String output) {
        prgBar.setVisibility(View.GONE);
        TextView txvNoMatches = (TextView) findViewById(R.id.txvNoMatches);
        if (output.equals("No Matches Found")){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(0,500,0,0);

            txvNoMatches.setText(output);
            txvNoMatches.setLayoutParams(params);
            txvNoMatches.setGravity(Gravity.CENTER);
        }
        else {
            txvNoMatches.setVisibility(View.GONE);
        }
    }
}
