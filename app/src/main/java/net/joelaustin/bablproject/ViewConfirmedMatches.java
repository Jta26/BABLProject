package net.joelaustin.bablproject;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewConfirmedMatches extends AppCompatActivity {

    BABLDataLocal localdata = new BABLDataLocal();
    BABLMatchesDataLocal matchlocaldata = new BABLMatchesDataLocal();

    LinearLayout linVertLayout;
    ProfilePictureView imgProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_confirmed_matches);


        linVertLayout = (LinearLayout) findViewById(R.id.linMatches);
        for (int i=0;i < localdata.stkConfirmedMatches.size(); i++) {
            new BABLShowConfirmedMatches(this).execute();
        }

        }






    }
