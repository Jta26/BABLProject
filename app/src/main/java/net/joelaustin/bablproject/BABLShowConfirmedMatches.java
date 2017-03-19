package net.joelaustin.bablproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;

import org.w3c.dom.Text;

/**
 * Created by Joel on 3/18/2017.
 */

public class BABLShowConfirmedMatches extends AsyncTask<Void, String, String>{
    BABLDataLocal localdata = new BABLDataLocal();
    BABLMatchesDataLocal matchlocaldata = new BABLMatchesDataLocal();
    Integer intMatchId = (Integer) localdata.stkConfirmedMatches.pop();
    Context context;
    String[] strArr = new String[9];

    public BABLShowConfirmedMatches(Context context){
        this.context = context;
    }


    protected String doInBackground(Void...voids) {

            String ip = "babldatabase2.cpngtl6yxjrl.us-west-2.rds.amazonaws.com:1433";
            String Dbclass = "net.sourceforge.jtds.jdbc.Driver";
            String db = "BABLdb";
            String un = "gregmckibbin";
            String password = "password";

            Connection conn;
            String ConnURL;
            PreparedStatement pstmt;
            ResultSet rs;
            try {
                Class.forName(Dbclass).newInstance();
                ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                        + "databaseName=" + db + ";user=" + un + ";password="
                        + password + ";";
                conn = DriverManager.getConnection(ConnURL);

                String query = "SELECT * FROM Users Where UserId=?";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, intMatchId);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    String strUsername = rs.getString("Username");
                    strArr[0] = strUsername;
                    String strFirstName = rs.getString("FirstName");
                    strArr[1] = strFirstName;
                    String strFacebookId = rs.getString("FacebookId");
                    strArr[2] = strFacebookId;
                    Integer intCampusAttend = rs.getInt("Attending");
                    switch(intCampusAttend){
                        case 0:
                            strArr[3] = "Pittsburgh";
                            break;
                        case 1:
                            strArr[3] = "Johnstown";
                            break;
                        case 2:
                            strArr[3] = "Bradford";
                            break;
                        case 3:
                            strArr[3] = "Titusville";
                            break;
                        case 4:
                            strArr[3] = "Greensburg";
                            break;
                    }

                }
                query = "SELECT Language FROM UserLanguages WHERE UserId=?";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, intMatchId);

                rs = pstmt.executeQuery();
                int i = 4;
                while (rs.next()) {
                    String strCurrentLanguage = rs.getString("Language");
                    strArr[i] = strCurrentLanguage;
                    i++;
                }
                publishProgress(strArr);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                Log.e("ERRO", e.getMessage());
            } catch (Exception e) {
                Log.e("ERRO", e.getMessage());
            }
           return"hi";
    }

    protected void onProgressUpdate(String... strings){
        LinearLayout linVertLayout = new LinearLayout(context);
        LinearLayout.LayoutParams paramsMain = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsMain.setMargins(0, 50, 0, 0);
        linVertLayout.setLayoutParams(paramsMain);
        linVertLayout.setOrientation(LinearLayout.VERTICAL);
        linVertLayout.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));

        LinearLayout linHorizLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params1.setMargins(20,20,20,10);
        linHorizLayout.setLayoutParams(params1);
        linHorizLayout.setOrientation(LinearLayout.HORIZONTAL);




        ProfilePictureView imgProfilePic = new ProfilePictureView(context);
        ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imgProfilePic.setLayoutParams(params2);
        imgProfilePic.setProfileId(strings[2]);


        LinearLayout linNameUserName = new LinearLayout(context);
        LinearLayout.LayoutParams paramsUsername = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        paramsUsername.setMargins(20,0,0,20);
        linNameUserName.setLayoutParams(paramsUsername);

        linNameUserName.setOrientation(LinearLayout.VERTICAL);
        linNameUserName.setBackground(context.getResources().getDrawable(R.drawable.findfacebook));

        TextView txvFirstName = new TextView(context);
        LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params4.setMargins(20, 5, 0,0);
        txvFirstName.setLayoutParams(params4);
        txvFirstName.setText(strings[1]);
        if (strings[1].length() > 10) {
            txvFirstName.setTextSize(20);
        }
        else {
            txvFirstName.setTextSize(25);
        }

        txvFirstName.setTextColor(context.getResources().getColor(R.color.colorAccent));


        TextView txvUsername = new TextView(context);
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(350, ViewGroup.LayoutParams.WRAP_CONTENT);
        params3.setMargins(20, 5, 0,0);
        txvUsername.setLayoutParams(params3);
        txvUsername.setText(strings[0] + context.getString(R.string.pittEmail));
        txvUsername.setTextColor(context.getResources().getColor(R.color.colorAccent));
        txvUsername.setTextSize(15);

        TextView txvCampus = new TextView(context);
        txvCampus.setLayoutParams(params3);
        txvCampus.setText(strings[3]);
        txvCampus.setTextColor(context.getResources().getColor(R.color.colorAccent));
        txvCampus.setTextSize(15);


        Button btnConnectOnFacebook = new Button(context);
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnParams.setMargins(20,10,20,20);

        btnConnectOnFacebook.setLayoutParams(btnParams);
        btnConnectOnFacebook.setText(context.getResources().getString(R.string.findonFacebook));
        btnConnectOnFacebook.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        btnConnectOnFacebook.setTextSize(20);
        btnConnectOnFacebook.setTextColor(context.getResources().getColor(R.color.colorAccent));
        btnConnectOnFacebook.setBackground(context.getResources().getDrawable(R.drawable.findfacebook));
        btnConnectOnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/app_scoped_user_id/" + strArr[2])));
            }
        });

        for (int i = 0; i < strings.length; i++) {
            if (strings[i] == null) {
                strings[i] = "";
            }
        }
        TextView txvLanguages = new TextView(context);
        txvLanguages.setLayoutParams(params3);
        txvLanguages.setText(strings[4] + "\n" + strings[5] + "\n" + strings[6] + "\n" + strings[7] + "\n" + strings[8]);
        txvLanguages.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        txvLanguages.setTextSize(15);

        linNameUserName.addView(txvFirstName);
        linNameUserName.addView(txvCampus);
        linNameUserName.addView(txvUsername);
        linHorizLayout.addView(imgProfilePic);
        linHorizLayout.addView(linNameUserName);
        linHorizLayout.addView(txvLanguages);

        linVertLayout.addView(linHorizLayout);
        linVertLayout.addView(btnConnectOnFacebook);


        ((ViewConfirmedMatches) context).linVertLayout.addView(linVertLayout);

    }

}
