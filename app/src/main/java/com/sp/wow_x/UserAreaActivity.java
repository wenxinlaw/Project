package com.sp.wow_x;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class UserAreaActivity extends AppCompatActivity {

    private TabHost host;
    Button btnPaired, bRedeem1, bRedeem2;
    ListView devicelist;
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    public static String EXTRA_ADDRESS = "device_address";
    TextView tvPneeded1, tvPneeded2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        TextView point1 = (TextView) findViewById(R.id.tvPneeded1);
        TextView point2 = (TextView) findViewById(R.id.tvPneeded2);
        bRedeem1 = (Button) findViewById(R.id.bRedeem1);
        bRedeem2 = (Button) findViewById(R.id.bRedeem2);

        final TextView welcomeMessage = (TextView) findViewById(R.id.tvWelcomeMsg);
        btnPaired = (Button) findViewById(R.id.bPaired);
        devicelist = (ListView) findViewById(R.id.lvPaired);
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        if (myBluetooth == null) {
            //Show a mensag. that the device has no bluetooth adapter
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();

            //finish apk
            finish();
        } else if (!myBluetooth.isEnabled()) {
            //Ask to the user turn the bluetooth on
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon, 1);
        }

        btnPaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pairedDevicesList();
            }
        });

        host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Details");
        spec.setContent(R.id.details_tab);
        spec.setIndicator("Details");
        host.addTab(spec);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        String message = "Hi " + name + "!" ;
        welcomeMessage.setText(message);

        bRedeem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redeem Successful", Toast.LENGTH_LONG).show();
            }
        });
        bRedeem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redeem Successful", Toast.LENGTH_LONG).show();
            }
        });

        //Tab 2
        spec = host.newTabSpec("WoW-X");
        spec.setContent(R.id.recycle_tab);
        spec.setIndicator("WoW-X");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Rewards");
        spec.setContent(R.id.rewards_tab);
        spec.setIndicator("Rewards");
        host.addTab(spec);

        host.setCurrentTab(0);

    }
    private void pairedDevicesList() {
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice bt : pairedDevices) {
                list.add(bt.getName() + "\n" + bt.getAddress()); //Get the device's name and the address
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        devicelist.setAdapter(adapter);
        devicelist.setOnItemClickListener(myListClickListener); //Method called when the device from the list is clicked

    }

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // Get the device MAC address, the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            // Make an intent to start next activity.
            Intent i = new Intent(UserAreaActivity.this, RecycleChoice.class);

            //Change the activity.
            i.putExtra(EXTRA_ADDRESS, address); //this will be received at ledControl (class) Activity
            startActivity(i);
        }
    };

}
