package net.joelaustin.bablproject;

import android.os.AsyncTask;

/**
 * Created by Joel on 3/17/2017.
 */
//This Class is for Entering the user's chosen match result, default 0, Yes 1, No 2;
public class BABLEnterMatchResult extends AsyncTask<Boolean, Void, String>{

    protected String doInBackground(Boolean...boolMatchType) {

        return "Yes";
    }
    protected void onPostExecute(String results) {

    }
}
