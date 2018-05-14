package com.sp.wow_x;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class RecycleChoice extends AppCompatActivity {


    Button Go, Finish;
    TextView Plastic, Alum;
    Switch aSwitch;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    String switchSelect = "1";


    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        address = newint.getStringExtra(UserAreaActivity.EXTRA_ADDRESS); //receive the address of the bluetooth device
        setContentView(R.layout.activity_toggle_switch);

        Plastic = (TextView) findViewById(R.id.tvPlastic);
        Alum = (TextView) findViewById(R.id.tvAlum);
        Go = (Button) findViewById(R.id.bGo);
        Finish = (Button) findViewById(R.id.bFinish);
        aSwitch = (Switch) findViewById(R.id.switch1);

        new ConnectBT().execute(); //Call the class to connect

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    switchSelect = "1";
                } else {
                    switchSelect = "0";
                }


            }

        });

        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (switchSelect == "1") {

                    turnOnMotor2();


                } else {
                    turnOnMotor1();
                }
            }
        });

        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchSelect == "1") {

                    turnOffMotor2();
                    Disconnect();
                } else {
                    turnOffMotor1();
                    Disconnect();
                }



                Intent intent = new Intent(RecycleChoice.this, Results.class);
                startActivity(intent);
            }
        });


    }


    private void Disconnect() {
        if (btSocket != null) //If the btSocket is busy
        {
            try {
                btSocket.close(); //close connection
            } catch (IOException e) {
                msg("Error");
            }
        }


    }

    private void turnOnMotor2() {
        if (btSocket != null) {
            try {
                btSocket.getOutputStream().write("1".toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    private void turnOffMotor2() {
        if (btSocket != null) {
            try {
                btSocket.getOutputStream().write("0".toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    private void turnOnMotor1() {
        if (btSocket != null) {
            try {
                btSocket.getOutputStream().write("3".toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    private void turnOffMotor1() {
        if (btSocket != null) {
            try {
                btSocket.getOutputStream().write("2".toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    // fast way to call Toast
    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }


    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(RecycleChoice.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            } catch (IOException e) {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            } else {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}

