package com.android.sarina.prokiller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ListActivity;
import android.net.TrafficStats;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.sarina.prokiller.ListAdapter;

public class MainActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get running processes
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> runningProcesses = manager.getRunningAppProcesses();
        if (runningProcesses != null && runningProcesses.size() > 0) {
            // Set data to the list adapter
            setListAdapter(new ListAdapter(this, runningProcesses));
        } else {
            // In case there are no processes running (not a chance :))
            Toast.makeText(getApplicationContext(), "No application is running", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        long send 		= 0;
        long received 	= 0;
        // Get UID of the selected process
        int uid = ((RunningAppProcessInfo)getListAdapter().getItem(position)).uid;

        // Get traffic data
        received = TrafficStats.getUidRxBytes(uid);
        send = TrafficStats.getUidTxBytes(uid);

        /* Kill process
        String processName = ((RunningAppProcessInfo)getListAdapter().getItem(position)).processName;
        ActivityManager activityManager = (ActivityManager)getSystemService("activity");
        activityManager.killBackgroundProcesses(processName);
*/
        // Display data
        Toast.makeText(getApplicationContext(), "UID " + uid + " details...\n send: " + send/1000 + "kB" + " \n received: " + received/1000 + "kB", Toast.LENGTH_LONG).show();
    }

}