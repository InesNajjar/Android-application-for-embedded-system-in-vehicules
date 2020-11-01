package embeddedmajesty.driveawake10.pisleam.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import embeddedmajesty.driveawake10.pisleam.R;
import embeddedmajesty.driveawake10.pisleam.activities.BluetoothActivity;
import embeddedmajesty.driveawake10.pisleam.activities.DriveAwakeActivity;
import embeddedmajesty.driveawake10.pisleam.activities.MainActivity;
import embeddedmajesty.driveawake10.pisleam.activities.location_activity;

public class DashboardFragment extends Fragment {
    TextView heartRate,temp,alcohol,etat_text;
    int panda;
    String address = null;
    private ProgressDialog progress;
    private boolean isBtConnected = false;
    private Button  sendb;
    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    Context context;
    TextView myLabel;
    EditText myTextbox;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;
    EditText num;
    private static final String SHARED_PREFS ="sharedPrefs";
    private static final String TEXT ="text";
    String loadtext;
    String theaddress;
    LocationManager mLocationManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview;
        rootview = inflater.inflate(R.layout.dashboard_fragment, container, false);
        heartRate = rootview.findViewById(R.id.heartratevalue);
        temp = rootview.findViewById(R.id.tempvalue);
        alcohol = rootview.findViewById(R.id.alcoholvalue);
        etat_text = rootview.findViewById(R.id.state);
        //   address = getActivity().getIntent().getStringExtra(BluetoothActivity.EXTRA_ADDRESS);
       // sendb = rootview.findViewById(R.id.btnSend);
        num = rootview.findViewById(R.id.familynumber);

        return rootview;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getContext();
    }

    @Override
    public void onResume() {
        super.onResume();
        try{
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        loadtext =sharedPreferences.getString(TEXT,"");
        System.out.println("loadtext"+loadtext);
        try {

            Log.i("piw", "AHHH");

           mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Log.i("piw", "7LILIII");

            Log.i("piw", "No permission");

            Log.i("piw", "Permission available");

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1,
                    1, mLocationListener);

            Location location = getLastKnownLocation();
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getActivity(), Locale.getDefault());

            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
             theaddress = addresses.get(0).getAddressLine(0);
            System.out.println("the address"+theaddress);
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        //sendb.setEnabled(false);
      /*  if (chackPermission(Manifest.permission.SEND_SMS)) {
            sendb.setEnabled(true);
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }*/

        //bluetooth
        // Start bluetooth
        try {
            Log.i("tak", "1");
            findBT();
            Log.i("tak", "2");

            openBT();
            Log.i("tak", "3");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Location getLastKnownLocation() {
        LocationManager mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return null;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            try {

                Log.i("piw", "Loc changed");
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getActivity(), Locale.getDefault());

                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()


                //location_view.setText(address);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    void findBT() {
        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
                Toast.makeText(context,"No bluetooth adapter available",Toast.LENGTH_SHORT).show();
                System.out.println("No bluetooth adapter available");
            }

            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    if (device.getName().equals("raspberrypi")) // this name have to be
                    // replaced with your
                    // bluetooth device name
                    {
                        mmDevice = device;
                        Log.i("RaspberryBT",
                                "findBT found device named " + mmDevice.getName());
                        Log.i("RaspberryBT",
                                "device address is " + mmDevice.getAddress());
                        break;
                    }
                }
            }
            System.out.println("BT device found!");
            Toast.makeText(context,"BT device is found",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    void openBT() throws IOException {
        try {
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); // Standard
            // SerialPortService
            // ID
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();
            System.out.println("Bluetooth is opened");
            Toast.makeText(context,"Bluetooth is opened",Toast.LENGTH_SHORT).show();
            beginListenForData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void beginListenForData() {
        final Handler handler = new Handler();
        final byte delimiter = '\n'; // This is the ASCII code for a newline
        // character
        panda = 0;
        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable() {
            public void run() {
                while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                    try {
                        int bytesAvailable = mmInputStream.available();
                        if (bytesAvailable > 0) {
                            byte[] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);
                            for (int i = 0; i < bytesAvailable; i++) {
                                byte b = packetBytes[i];
                                if (b == delimiter) {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0,
                                            encodedBytes, 0,
                                            encodedBytes.length);
                                    final String data = new String(
                                            encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;
                                    System.out.println("Our DATA !!"+data);
                                    handler.post(new Runnable() {
                                        public void run() {
                                            if (data.charAt(0) == 'T') {
                                                //myLabel.setText(data);
                                                temp.setText(data.replace("T",""));
                                                panda = 1;
                                            } else if (data.charAt(0) == 'H') {
                                                heartRate.setText(data.replace("H",""));
                                                panda = 0;
                                            }else if (data.charAt(0) == 'A') {
                                                alcohol.setText(data.replace("A",""));
                                                panda = 0;
                                            } else {

                                                try {
                                                    Log.i("Bluetooth",data);
                                                    if (data.charAt(0)== '1') {
                                                        try {
                                                            if (chackPermission(Manifest.permission.SEND_SMS)) {
                                                                SmsManager smsManager = SmsManager.getDefault();
                                                                smsManager.sendTextMessage(loadtext, null, "URGHENT! Drive-Awake System detected that the driver's state is unstable please check on him in  "+theaddress, null, null);
                                                                Toast.makeText(getActivity(), "message Sent", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_SHORT).show();
                                                            }
                                                            etat_text.setText("Unstable");
                                                            etat_text.setTextColor(getResources().getColor(R.color.red));
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }else{
                                                        etat_text.setText("Stable");
                                                        etat_text.setTextColor(getResources().getColor(R.color.green));
                                                    }
                                                }catch(Exception e){
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    });
                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    } catch (IOException ex) {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }
    void sendData() throws IOException {
        String msg = myTextbox.getText().toString();
        msg += "";
        // myLabel.setText("Data Sent " + msg);
    }

    void onButton() throws IOException {
        mmOutputStream.write("1".getBytes());
    }

    void offButton() throws IOException {
        mmOutputStream.write("2".getBytes());
    }

    void closeBT() throws IOException {
        stopWorker = true;
        mmOutputStream.close();
        mmInputStream.close();
        mmSocket.close();
        myLabel.setText("Bluetooth Closed");
    }





    public boolean chackPermission(String permission)
    {
        int check= ContextCompat.checkSelfPermission(getActivity(),permission);
        return (check== PackageManager.PERMISSION_GRANTED);

    }



   /* public void loadData()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SettingsFragment.SHARED_PREFS, Context.MODE_PRIVATE);
        loadtext =sharedPreferences.getString(TEXT,"");
    }*/


}