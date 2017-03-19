package net.joelaustin.bablproject;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.LinearLayout;

import com.facebook.login.widget.ProfilePictureView;

public class ViewConfirmedMatches extends AppCompatActivity{

    BABLDataLocal localdata = new BABLDataLocal();
    BABLMatchesDataLocal matchlocaldata = new BABLMatchesDataLocal();
    LinearLayout linVertLayout;
    ProfilePictureView imgProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_confirmed_matches);
        
        new BABLConfirmMatches(this).execute();
        linVertLayout = (LinearLayout) findViewById(R.id.linMatches);


        }






    }
