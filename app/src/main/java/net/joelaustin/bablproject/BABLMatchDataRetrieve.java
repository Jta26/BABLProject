package net.joelaustin.bablproject;

import android.os.AsyncTask;

/**
 * Created by gregm on 3/16/2017.
 */

public class BABLMatchDataRetrieve extends AsyncTask<Void, Void, String> {


    private String ip = "babldatabase2.cpngtl6yxjrl.us-west-2.rds.amazonaws.com:1433";
    private String Dbclass = "net.sourceforge.jtds.jdbc.Driver";
    private String db = "BABLdb";
    private String un = "gregmckibbin";
    private String password = "password";

    

    public BABLMatchDataRetrieve(){

    }
    protected String doInBackground(Void...voids) {



        return "";
    }

    protected void onPostExecute(String results){

    }
}
