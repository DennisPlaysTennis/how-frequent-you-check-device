package com.mysandbox.howfrequentyoucheckdevice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

/**
 * Created by dhong on 8/2/15.
 */
public class UserPresentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        new saveCountTask(context).execute();
    }

    private class saveCountTask extends AsyncTask<Void, Void, Void> {
        private Context mContext;

        public saveCountTask(Context context) {
            mContext = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            CountDatabase db = new CountDatabase(mContext);
            if (db.currentDateExistsInDb()) {
                db.updateRow(db.getCountForCurrentDate() + 1);
            } else {
                db.insertRow(0);
            }
            return null;
        }
    }
}
