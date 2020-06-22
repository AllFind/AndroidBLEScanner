package com.example.uid.androidble_lescanner;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // Sensor
    private SensorManager sensorManager;
    private Sensor sGyro, sMagnet, sAccel;


    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private HashMap mScanResults;
    private ScanCallback mScanCallback;
    private BluetoothLeScanner mBluetoothLeScanner;
    private boolean mScanning;
    private Handler mHandler;
    private List<ScanResult> beaconValueList;
    private List<ScanResult> tempValueList;
    private Runnable BLEScanner;
    private Runnable StopScanning;
    private Handler BLEHandler;
    private List<ScanFilter> filters;
    private ScanSettings settings;

    //variables for UI
    private TextView textFieldResult;
    private EditText textX;
    private EditText textY;
    private Button button,button2,button3;
    private ProgressBar foundDeviceBar;
    private TextView foundDevicetext;
    private ProgressBar dataScannedBar;
    private TextView dataScannedText;
    private int counter;
    private int insertCounter;
    private int dataCounter;
    private int dataCollectionLimit = 100000;

    //static values
    private final static int REQUEST_ENABLE_BT = 1;
    private final static int REQUEST_FINE_LOCATION = 0;
    private static final String TAG = "MainActivity";
    //prev: 170000 , 1000
//    private static final long SCAN_TOTAL_PERIOD = 175000;
    private static final long SCAN_PERIOD = 1000;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //list of beacon address
    private String beaconAddress1 = "D5:70:BC:F4:83:FA";
    /*private String beaconAddress10 = "E9:35:09:E4:0A:14"; Hilang*/
    private String beaconAddress10 = "C6:16:71:78:24:8B"; /*Tukar Masi ditangan Alvin*/
    private String beaconAddress11 = "D1:0D:AD:59:E8:6A";
    private String beaconAddress12 = "EA:2C:88:79:8A:86";
    private String beaconAddress13 = "C5:D9:51:0D:20:C5";
    private String beaconAddress14 = "C4:D9:07:D9:A2:15";
    private String beaconAddress15 = "F0:18:5C:82:B8:F3";
    private String beaconAddress16 = "D3:E9:01:4D:95:89";
    private String beaconAddress17 = "C8:B5:3D:46:5D:39";
    private String beaconAddress18 = "DD:AF:51:AB:8C:E6";
    private String beaconAddress19 = "F3:4E:74:21:E0:FB";
    private String beaconAddress2 = "D8:8F:D4:81:B3:26";
    private String beaconAddress20 = "EB:7F:E5:60:FA:DC";
    /*private String beaconAddress21 = "F8:D3:FB:65:7E:CE"; Removed*/
    private String beaconAddress22 = "EA:35:B7:36:96:12";
    private String beaconAddress23 = "CB:78:48:32:AD:52";
    private String beaconAddress21 = "EE:CD:0F:01:2A:FA";
    private String beaconAddress3 = "D7:87:B3:E3:6B:82";
    private String beaconAddress4 = "EF:55:2F:7A:63:25";
    private String beaconAddress5 = "D9:1B:72:4A:1A:DF";
    private String beaconAddress6 = "EF:2D:EB:72:D0:A3";
    private String beaconAddress7 = "D9:41:FA:1F:14:2A";
    private String beaconAddress8 = "EF:C0:78:D1:38:FA";
    private String beaconAddress9 = "D5:9B:2C:0E:85:9D";

    //list of beacons
    private ScanFilter Beacon1;
    private ScanFilter Beacon2;
    private ScanFilter Beacon3;
    private ScanFilter Beacon4;
    private ScanFilter Beacon5;
    private ScanFilter Beacon6;
    private ScanFilter Beacon7;
    private ScanFilter Beacon8;
    private ScanFilter Beacon9;
    private ScanFilter Beacon10;
    private ScanFilter Beacon11;
    private ScanFilter Beacon12;
    private ScanFilter Beacon13;
    private ScanFilter Beacon14;
    private ScanFilter Beacon15;
    private ScanFilter Beacon16;
    private ScanFilter Beacon17;
    private ScanFilter Beacon18;
    private ScanFilter Beacon19;
    private ScanFilter Beacon20;
    private ScanFilter Beacon21;
    private ScanFilter Beacon22;
    private ScanFilter Beacon23;
    //private ScanFilter Beacon24;


    private boolean temp1 = false;
    private boolean temp2 = false;
    private boolean temp3 = false;
    private boolean temp4 = false;
    private boolean temp5 = false;
    private boolean temp6 = false;
    private boolean temp7 = false;
    private boolean temp8 = false;
    private boolean temp9 = false;
    private boolean temp10 = false;
    private boolean temp11 = false;
    private boolean temp12 = false;
    private boolean temp13 = false;
    private boolean temp14 = false;
    private boolean temp15 = false;
    private boolean temp16 = false;
    private boolean temp17 = false;
    private boolean temp18 = false;
    private boolean temp19 = false;
    private boolean temp20 = false;
    private boolean temp21 = false;
    private boolean temp22 = false;
    private boolean temp23 = false;

    private void setDefaultTemp(){
        temp1 = false;
        temp2 = false;
        temp3 = false;
        temp4 = false;
        temp5 = false;
        temp6 = false;
        temp7 = false;
        temp8 = false;
        temp9 = false;
        temp10 = false;
        temp11 = false;
        temp12 = false;
        temp13 = false;
        temp14 = false;
        temp15 = false;
        temp16 = false;
        temp17 = false;
        temp18 = false;
        temp19 = false;
        temp20 = false;
        temp21 = false;
        temp22 = false;
        temp23 = false;
    }

    //csv file export base directory (to DOWNLOAD folder)
    private List<List<String>> outputList;
    private List<String> outputListRow;
    private String outputFile;
    private File baseDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsoluteFile();
    private String fileName = "ble-scan-result";
    private File f;

    //csv file export sensor file
    private String baseDir1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsoluteFile().getAbsolutePath();
    private String fileName1 = "sensor-scan-result";
    private String filePath;
    File f1;
    String outputSensor ="Type,X,Y,Z,Time\n";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(MainActivity.this);
        mScanResults = new HashMap<String, ScanResult>(100,1);
        beaconValueList = new ArrayList<>();

        Beacon1 = new ScanFilter.Builder().setDeviceAddress(beaconAddress1).build();
        Beacon2 = new ScanFilter.Builder().setDeviceAddress(beaconAddress2).build();
        Beacon3 = new ScanFilter.Builder().setDeviceAddress(beaconAddress3).build();
        Beacon4 = new ScanFilter.Builder().setDeviceAddress(beaconAddress4).build();
        Beacon5 = new ScanFilter.Builder().setDeviceAddress(beaconAddress5).build();
        Beacon6 = new ScanFilter.Builder().setDeviceAddress(beaconAddress6).build();
        Beacon7 = new ScanFilter.Builder().setDeviceAddress(beaconAddress7).build();
        Beacon8 = new ScanFilter.Builder().setDeviceAddress(beaconAddress8).build();
        Beacon9 = new ScanFilter.Builder().setDeviceAddress(beaconAddress9).build();
        Beacon10 = new ScanFilter.Builder().setDeviceAddress(beaconAddress10).build();
        Beacon11 = new ScanFilter.Builder().setDeviceAddress(beaconAddress11).build();
        Beacon12 = new ScanFilter.Builder().setDeviceAddress(beaconAddress12).build();
        Beacon13 = new ScanFilter.Builder().setDeviceAddress(beaconAddress13).build();
        Beacon14 = new ScanFilter.Builder().setDeviceAddress(beaconAddress14).build();
        Beacon15 = new ScanFilter.Builder().setDeviceAddress(beaconAddress15).build();
        Beacon16 = new ScanFilter.Builder().setDeviceAddress(beaconAddress16).build();
        Beacon17 = new ScanFilter.Builder().setDeviceAddress(beaconAddress17).build();
        Beacon18 = new ScanFilter.Builder().setDeviceAddress(beaconAddress18).build();
        Beacon19 = new ScanFilter.Builder().setDeviceAddress(beaconAddress19).build();
        Beacon20 = new ScanFilter.Builder().setDeviceAddress(beaconAddress20).build();
        Beacon21 = new ScanFilter.Builder().setDeviceAddress(beaconAddress21).build();
        Beacon22 = new ScanFilter.Builder().setDeviceAddress(beaconAddress22).build();
        Beacon23 = new ScanFilter.Builder().setDeviceAddress(beaconAddress23).build();
        //Beacon24 = new ScanFilter.Builder().setDeviceAddress(beaconAddress24).build();

        // Initialize Sensor
        SensorManager sensorManager =
                (SensorManager) getSystemService(SENSOR_SERVICE);
        sGyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sMagnet = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sGyro != null) {
            sensorManager.registerListener(MainActivity.this, sGyro, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sMagnet != null) {
            sensorManager.registerListener(MainActivity.this, sMagnet, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sAccel != null) {
            sensorManager.registerListener(MainActivity.this, sAccel, SensorManager.SENSOR_DELAY_NORMAL);
        }

        filters = new ArrayList<>();
        filters.add(Beacon1);
        filters.add(Beacon2);
        filters.add(Beacon3);
        filters.add(Beacon4);
        filters.add(Beacon5);
        filters.add(Beacon6);
        filters.add(Beacon7);
        filters.add(Beacon8);
        filters.add(Beacon9);
        filters.add(Beacon10);
        filters.add(Beacon11);
        filters.add(Beacon12);
        filters.add(Beacon13);
        filters.add(Beacon14);
        filters.add(Beacon15);
        filters.add(Beacon16);
        filters.add(Beacon17);
        filters.add(Beacon18);
        filters.add(Beacon19);
        filters.add(Beacon20);
        filters.add(Beacon21);
        filters.add(Beacon22);
        filters.add(Beacon23);
        //filters.add(Beacon24);

        BLEScanner = new Runnable() {
            @Override
            public void run() {
                startScan();
            }
        };

        StopScanning = new Runnable() {
            @Override
            public void run() {
                stopScan();
            }
        };

        mScanning = false;
        mHandler = new Handler();
        BLEHandler = new Handler();
        mBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        mScanCallback = new bleScanCallback(mScanResults);


        textFieldResult = findViewById(R.id.textFieldResult);
        textFieldResult.setMovementMethod(new ScrollingMovementMethod());
        textX = findViewById(R.id.textX);
        textY = findViewById(R.id.textY);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        foundDevicetext = findViewById(R.id.deviceFoundText);
        foundDeviceBar = findViewById(R.id.deviceFound);
        dataScannedText = findViewById(R.id.dataScannedText);
        dataScannedBar = findViewById(R.id.dataScanned);
        dataScannedBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateScanning();
                filePath = baseDir1+ File.separator + fileName1+File.separator+textX.getText()+File.separator+textY.getText();
                f1 = new File(filePath);
                if(!f1.getParentFile().exists()){
                    f1.getParentFile().mkdirs();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mBluetoothLeScanner.stopScan(mScanCallback);
                stopScan();
                destroyScanning();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScanCallback = null;
        BLEHandler = null;
        mHandler = null;
    }

    private void initiateScanning(){
        outputList = new ArrayList<List<String>>(100);
        dataScannedBar.setProgress(0);
        if(hasPermissions() && hasLocationPermissions()) {

            if(mScanning == false){
                beaconValueList.clear();
                textFieldResult.setText("");
                textFieldResult.setText(textFieldResult.getText().toString() + "\n\n" + "START SCAN");
                counter = 1;
                outputFile = "X,Y,Beacon1,Beacon2,Beacon3,Beacon4,Beacon5,Beacon6,Beacon7,Beacon8,Beacon9,Beacon10,Beacon11,Beacon12,Beacon13,Beacon14,Beacon15,Beacon16,Beacon17,Beacon18,Beacon19,Beacon20,Beacon21,Beacon22,Beacon23,Time\n";
                BLEHandler.postDelayed(BLEScanner,SCAN_PERIOD);
            }
            else{
                stopScan();
                destroyScanning();
                beaconValueList.clear();
                textFieldResult.setText("");
                textFieldResult.setText(textFieldResult.getText().toString() + "\n\n" + "START SCAN");
                counter = 1;
                outputFile = "X,Y,Beacon1,Beacon2,Beacon3,Beacon4,Beacon5,Beacon6,Beacon7,Beacon8,Beacon9,Beacon10,Beacon11,Beacon12,Beacon13,Beacon14,Beacon15,Beacon16,Beacon17,Beacon18,Beacon19,Beacon20,Beacon21,Beacon22,Beacon23,Time\n";
                BLEHandler.postDelayed(BLEScanner,SCAN_PERIOD);
            }
        }
        else{
            requestBluetoothEnable();
            requestLocationPermission();
        }
    }

    private void destroyScanning(){
        textFieldResult.setText(textFieldResult.getText().toString() + "\n\n" + "SAVING SCAN RESULT");
        if(!baseDir.getParentFile().exists()){
            baseDir.getParentFile().mkdirs();
        }
        try {
            BLEHandler.removeCallbacks(BLEScanner);
            textFieldResult.setText(textFieldResult.getText().toString() + "\n\n" + baseDir.toString() + fileName+"X" + textX.getText().toString() + "Y" + textY.getText().toString()+".csv");

            //print result to csv file
            for (List<String> row : outputList) {
                outputFile = outputFile + textX.getText().toString() + ",";
                outputFile = outputFile + textY.getText().toString() + ",";
                dataCounter = 0;
                for(String data : row){
                    if(dataCounter!=23) {
                        outputFile = outputFile + data + ",";
                    }
                    else{
                        outputFile = outputFile + data + "\n";
                    }
                    dataCounter++;
                }
            }

            f = new File(baseDir,File.separator+ "resultTest" + File.separator + fileName+"X" + textX.getText().toString() + "Y" + textY.getText().toString()+".csv");
            if(!f.getParentFile().exists()){
                f.getParentFile().mkdirs();
            }

            if(f.exists()){
                f.delete();
                f.createNewFile();
                f.setWritable(true);
                f.setReadable(true);
                FileWriter wr = new FileWriter(f);
                wr.write(outputFile);
                wr.flush();
                wr.close();
            }
            else{
                f.createNewFile();
                f.setWritable(true);
                f.setReadable(true);
                FileWriter wr = new FileWriter(f);
                wr.write(outputFile);
                wr.flush();
                wr.close();
            }
            if(f1.exists()){
                f1.delete();
                f1.createNewFile();
                f1.setWritable(true);
                f1.setReadable(true);
                FileWriter wr = new FileWriter(f1);
                wr.write(outputSensor);
                wr.flush();
                wr.close();
            }
            else{
                f1.createNewFile();
                f1.setWritable(true);
                f1.setReadable(true);
                FileWriter wr = new FileWriter(f1);
                wr.write(outputSensor);
                wr.flush();
                wr.close();
            }

        }
        catch (Exception e){
            textFieldResult.setText(textFieldResult.getText().toString() + "\n\n" + e);
        }
        textFieldResult.setText(textFieldResult.getText().toString() + "\n\n" + "FINISH SCAN");
    }

    private void startScan() {
        if(counter>dataCollectionLimit){
            BLEHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    destroyScanning();
                }
            }, 0);
        }
        else{
            if (!hasPermissions() || mScanning) {
                return;
            }
            // TODO start the scan
//            mScanResults.clear();
            mScanResults.put(beaconAddress1, null);
            mScanResults.put(beaconAddress2, null);
            mScanResults.put(beaconAddress3, null);
            mScanResults.put(beaconAddress4, null);
            mScanResults.put(beaconAddress5, null);
            mScanResults.put(beaconAddress6, null);
            mScanResults.put(beaconAddress7, null);
            mScanResults.put(beaconAddress8, null);
            mScanResults.put(beaconAddress9, null);
            mScanResults.put(beaconAddress10, null);
            mScanResults.put(beaconAddress11, null);
            mScanResults.put(beaconAddress12, null);
            mScanResults.put(beaconAddress13, null);
            mScanResults.put(beaconAddress14, null);
            mScanResults.put(beaconAddress15, null);
            mScanResults.put(beaconAddress16, null);
            mScanResults.put(beaconAddress17, null);
            mScanResults.put(beaconAddress18, null);
            mScanResults.put(beaconAddress19, null);
            mScanResults.put(beaconAddress20, null);
            mScanResults.put(beaconAddress21, null);
            mScanResults.put(beaconAddress22, null);
            mScanResults.put(beaconAddress23, null);
            //mScanResults.put(beaconAddress24, null);
            outputListRow = new ArrayList<String>(23);

            mScanning = true;
            settings = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build();
            mBluetoothLeScanner.startScan(filters, settings, mScanCallback);
            mHandler.postDelayed(StopScanning, SCAN_PERIOD);
            button2.setEnabled(true);
            button3.setEnabled(true);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        synchronized (this){
            String outputFile1 = "";
            long time= System.currentTimeMillis();
            if(sensor.getType() == Sensor.TYPE_GYROSCOPE){
                outputFile1 += "GyroScope," + sensorEvent.values[0]+","+sensorEvent.values[1]+","+sensorEvent.values[2]+","+time+"\n";
                outputSensor += outputFile1;
            }
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                outputFile1 += "Accelerometer," + sensorEvent.values[0]+","+sensorEvent.values[1]+","+sensorEvent.values[2]+","+time+"\n";
                outputSensor += outputFile1;
            }
            if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                outputFile1 += "Magnetic," + sensorEvent.values[0]+","+sensorEvent.values[1]+","+sensorEvent.values[2]+","+time+"\n";
                outputSensor += outputFile1;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class bleScanCallback extends ScanCallback {
        public bleScanCallback(HashMap mScanResults) {
        }

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            addScanToList(result);
        }
        @Override
        public void onScanFailed(int errorCode) {
            final int e = Log.e(TAG, "BLE Scan Failed with code " + errorCode);
            textFieldResult.setText(textFieldResult.getText().toString() + "\n\n" + counter + ". Scan Failed : " + errorCode);
        }
        private void addScanToList(ScanResult result){
            beaconValueList.add(result);
        }
    };


    private boolean checkRssi(final List<ScanResult> list, final String mac){
        return list.stream().filter(o -> o.getDevice().getAddress().equals(mac)).findFirst().isPresent();
    }

    private void stopScan() {
        if (mScanning && mBluetoothAdapter != null && mBluetoothAdapter.isEnabled() && mBluetoothLeScanner != null) {
            mBluetoothLeScanner.stopScan(mScanCallback);
        }
        foundDeviceBar.setProgress(0);
        insertCounter = 0;
        mScanning = false;

        if(counter <= dataCollectionLimit){

            for (ScanResult res : beaconValueList){
                String bleAddress = res.getDevice().getAddress();
                if (bleAddress.equals(beaconAddress1)) {
                    if (checkRssi(beaconValueList, beaconAddress1)) {
                        mScanResults.replace(beaconAddress1, res);
                    } else {
                        temp1 = true;
                    }
                }
                else if (bleAddress.equals(beaconAddress2)){
                    if (checkRssi(beaconValueList, beaconAddress2)) {
                        mScanResults.replace(beaconAddress2, res);
                    } else {
                        temp2 = true;
                    }
                }
                else if (bleAddress.equals(beaconAddress3)){
                    if (checkRssi(beaconValueList, beaconAddress3)) {
                        mScanResults.replace(beaconAddress3, res);
                    } else {
                        temp3 = true;
                    }
                }
                else if (bleAddress.equals(beaconAddress4)){
                    if (checkRssi(beaconValueList, beaconAddress4)) {
                        mScanResults.replace(beaconAddress4, res);
                    } else {
                        temp4 = true;
                    }
                }
                else if (bleAddress.equals(beaconAddress5)){
                    if (checkRssi(beaconValueList, beaconAddress5)) {
                        mScanResults.replace(beaconAddress5, res);
                    } else {
                        temp5 = true;
                    }
                }
                else if (bleAddress.equals(beaconAddress6)){
                    if (checkRssi(beaconValueList, beaconAddress6)) {
                        mScanResults.replace(beaconAddress6, res);
                    } else {
                        temp6 = true;
                    }
                }
                else if (bleAddress.equals(beaconAddress7)){
                    if (checkRssi(beaconValueList, beaconAddress7)) {
                        mScanResults.replace(beaconAddress7, res);
                    } else {
                        temp7 = true;
                    }
                }
                else if (bleAddress.equals(beaconAddress8)){
                    if (checkRssi(beaconValueList, beaconAddress8)) {
                        mScanResults.replace(beaconAddress8, res);
                    } else {
                        temp8 = true;
                    }
                }
                else if (bleAddress.equals(beaconAddress9)){
                    if (checkRssi(beaconValueList, beaconAddress9)) {
                        mScanResults.replace(beaconAddress9, res);
                    } else {
                        temp9 = true;
                    }
                }
                else if (bleAddress.equals(beaconAddress10)){
                    if (checkRssi(beaconValueList, beaconAddress10)) {
                        mScanResults.replace(beaconAddress10, res);
                    } else {
                        temp10 = true;
                    }
                }
                else if (bleAddress.equals(beaconAddress11)){
                    if (checkRssi(beaconValueList, beaconAddress11)) {
                        mScanResults.replace(beaconAddress11, res);
                    } else {
                        temp11 = true;
                    }
                }
                else if (bleAddress.equals(beaconAddress12)){
                    if (checkRssi(beaconValueList, beaconAddress12)) {
                        mScanResults.replace(beaconAddress12, res);
                    } else {
                        temp12 = true;
                    }
                }
                else if (bleAddress.equals(beaconAddress13)){
                    if (checkRssi(beaconValueList, beaconAddress13)) {
                        mScanResults.replace(beaconAddress13, res);
                    } else {
                        temp13 = true;
                    }
                }
                else if (bleAddress.equals(beaconAddress14)){
                    if (checkRssi(beaconValueList, beaconAddress14)) {
                        mScanResults.replace(beaconAddress14, res);
                    } else {
                        temp14 = true;
                    }
                }
                else if (bleAddress.equals(beaconAddress15)){
                    if (checkRssi(beaconValueList, beaconAddress15)) {
                        mScanResults.replace(beaconAddress15, res);
                    } else {
                        temp15 = true;
                    }
                }
                else if (bleAddress.equals(beaconAddress16)){
                    if (checkRssi(beaconValueList, beaconAddress16)) {
                        mScanResults.replace(beaconAddress16, res);
                    } else {
                        temp16 = true;
                    }
                }
                else if (bleAddress.equals(beaconAddress17)){
                    if (checkRssi(beaconValueList, beaconAddress17)) {
                        mScanResults.replace(beaconAddress17, res);
                    } else {
                        temp17 = true;
                    }
                }
                else if (bleAddress.equals(beaconAddress18)){
                    if (checkRssi(beaconValueList, beaconAddress18)) {
                        mScanResults.replace(beaconAddress18, res);
                    } else {
                        temp18 = true;
                    }
                }
                else if (bleAddress.equals(beaconAddress19)){
                    if (checkRssi(beaconValueList, beaconAddress19)) {
                        mScanResults.replace(beaconAddress19, res);
                    } else {
                        temp19 = true;
                    }
                }
                else if (bleAddress.equals(beaconAddress20)){
                    if (checkRssi(beaconValueList, beaconAddress20)) {
                        mScanResults.replace(beaconAddress20, res);
                    } else {
                        temp20 = true;
                    }
                }

                else if (bleAddress.equals(beaconAddress21)){
                    if (checkRssi(beaconValueList, beaconAddress21)) {
                        mScanResults.replace(beaconAddress21, res);
                    } else {
                        temp21 = true;
                    }
                }
                else if (bleAddress.equals(beaconAddress22)){
                    if (checkRssi(beaconValueList, beaconAddress22)) {
                        mScanResults.replace(beaconAddress22, res);
                    } else {
                        temp22 = true;
                    }

                }
                else if (bleAddress.equals(beaconAddress23)){
                    if (checkRssi(beaconValueList, beaconAddress23)) {
                        mScanResults.replace(beaconAddress23, res);
                    } else {
                        temp23 = true;
                    }

                }
//                else if (bleAddress.equals(beaconAddress24)){
//                    mScanResults.replace(beaconAddress24, res);
//                }
            }

            Iterator<Map.Entry<String, Integer>> it = mScanResults.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Integer> pair = it.next();
                if(pair.getValue()!=null){
                    insertCounter = insertCounter + 1;
                }
            }
            foundDeviceBar.setProgress(insertCounter);
            foundDevicetext.setText("Device Found : " + insertCounter);

            if(insertCounter > 22){
                if(mScanResults.get(beaconAddress1) != null) {
                    ScanResult beacon1 = (ScanResult) mScanResults.get(beaconAddress1);
                        outputListRow.add(beacon1.getRssi()+"");
                }else if (mScanResults.get(beaconAddress1) == null || temp1 == true) {
                    outputListRow.add(0+"N/A");
                }
                if(mScanResults.get(beaconAddress2) != null) {
                    ScanResult beacon2 = (ScanResult) mScanResults.get(beaconAddress2);
                        outputListRow.add(beacon2.getRssi()+"");
                }else if (mScanResults.get(beaconAddress2) == null || temp2 == true){
                    outputListRow.add(0+"N/A");
                }
                if(mScanResults.get(beaconAddress3) != null) {
                    ScanResult beacon3 = (ScanResult) mScanResults.get(beaconAddress3);
                        outputListRow.add(beacon3.getRssi()+"");
                }else if (mScanResults.get(beaconAddress3) == null || temp3 == true){
                    outputListRow.add(0+"N/A");
                }
                if(mScanResults.get(beaconAddress4) != null) {
                    ScanResult beacon4 = (ScanResult) mScanResults.get(beaconAddress4);
                        outputListRow.add(beacon4.getRssi()+"");
                }else if (mScanResults.get(beaconAddress4) == null || temp4 == true){
                    outputListRow.add(0+"N/A");
                }
                if(mScanResults.get(beaconAddress5) != null) {
                    ScanResult beacon5 = (ScanResult) mScanResults.get(beaconAddress5);

                        outputListRow.add(beacon5.getRssi()+"");
                }else if (mScanResults.get(beaconAddress5) == null || temp5 == true){
                    outputListRow.add(0+"N/A");
                }
                if(mScanResults.get(beaconAddress6) != null) {
                    ScanResult beacon6 = (ScanResult) mScanResults.get(beaconAddress6);

                        outputListRow.add(beacon6.getRssi()+"");
                }else if (mScanResults.get(beaconAddress6) == null || temp6 == true){
                    outputListRow.add(0+"N/A");
                }
                if(mScanResults.get(beaconAddress7) != null) {
                    ScanResult beacon7 = (ScanResult) mScanResults.get(beaconAddress7);

                        outputListRow.add(beacon7.getRssi()+"");
                }else if (mScanResults.get(beaconAddress7) == null || temp7 == true){
                    outputListRow.add(0+"N/A");
                }
                if(mScanResults.get(beaconAddress8) != null) {
                    ScanResult beacon8 = (ScanResult) mScanResults.get(beaconAddress8);
                        outputListRow.add(beacon8.getRssi()+"");
                }else if (mScanResults.get(beaconAddress8) == null || temp8 == true){
                    outputListRow.add(0+"N/A");
                }
                if(mScanResults.get(beaconAddress9) != null) {
                    ScanResult beacon9 = (ScanResult) mScanResults.get(beaconAddress9);
                        outputListRow.add(beacon9.getRssi()+"");
                }else if (mScanResults.get(beaconAddress9) == null || temp9 == true){
                    outputListRow.add(0+"N/A");
                }
                if(mScanResults.get(beaconAddress10) != null) {
                    ScanResult beacon10 = (ScanResult) mScanResults.get(beaconAddress10);
                        outputListRow.add(beacon10.getRssi()+"");
                }else if (mScanResults.get(beaconAddress10) == null || temp10 == true){
                    outputListRow.add(0+"N/A");
                }
                if(mScanResults.get(beaconAddress11) != null) {
                    ScanResult beacon11 = (ScanResult) mScanResults.get(beaconAddress11);
                        outputListRow.add(beacon11.getRssi()+"");
                 }else if (mScanResults.get(beaconAddress11) == null || temp11 == true){
                    outputListRow.add(0+"N/A");
                }
                if(mScanResults.get(beaconAddress12) != null) {
                    ScanResult beacon12 = (ScanResult) mScanResults.get(beaconAddress12);
                        outputListRow.add(beacon12.getRssi()+"");
                }else if (mScanResults.get(beaconAddress12) == null || temp12 == true){
                    outputListRow.add(0+"N/A");
                }
                if(mScanResults.get(beaconAddress13) != null) {
                    ScanResult beacon13 = (ScanResult) mScanResults.get(beaconAddress13);
                        outputListRow.add(beacon13.getRssi()+"");
                }else if (mScanResults.get(beaconAddress13) == null || temp13 == true){
                    outputListRow.add(0+"N/A");
                }
                if(mScanResults.get(beaconAddress14) != null) {
                    ScanResult beacon14 = (ScanResult) mScanResults.get(beaconAddress14);
                        outputListRow.add(beacon14.getRssi()+"");
                }else if (mScanResults.get(beaconAddress14) == null || temp14 == true){
                    outputListRow.add(0+"N/A");
                }
                if(mScanResults.get(beaconAddress15) != null) {
                    ScanResult beacon15 = (ScanResult) mScanResults.get(beaconAddress15);
                        outputListRow.add(beacon15.getRssi()+"");
                }else if (mScanResults.get(beaconAddress15) == null || temp15 == true){
                    outputListRow.add(0+"N/A");
                }
                if(mScanResults.get(beaconAddress16) != null) {
                    ScanResult beacon16 = (ScanResult) mScanResults.get(beaconAddress16);
                        outputListRow.add(beacon16.getRssi()+"");
                }else if (mScanResults.get(beaconAddress16) == null || temp16 == true){
                    outputListRow.add(0+"N/A");
                }
                if(mScanResults.get(beaconAddress17) != null) {
                    ScanResult beacon17 = (ScanResult) mScanResults.get(beaconAddress17);
                        outputListRow.add(beacon17.getRssi()+"");
                }else if (mScanResults.get(beaconAddress17) == null || temp17 == true){
                    outputListRow.add(0+"N/A");
                }
                if(mScanResults.get(beaconAddress18) != null) {
                    ScanResult beacon18 = (ScanResult) mScanResults.get(beaconAddress18);
                        outputListRow.add(beacon18.getRssi()+"");
                }else if (mScanResults.get(beaconAddress18) == null || temp18 == true){
                    outputListRow.add(0+"N/A");
                }
                if(mScanResults.get(beaconAddress19) != null) {
                    ScanResult beacon19 = (ScanResult) mScanResults.get(beaconAddress19);
                        outputListRow.add(beacon19.getRssi()+"");
                }else if (mScanResults.get(beaconAddress19) == null || temp19 == true){
                    outputListRow.add(0+"N/A");
                }
                if(mScanResults.get(beaconAddress20) != null) {
                    ScanResult beacon20 = (ScanResult) mScanResults.get(beaconAddress20);
                        outputListRow.add(beacon20.getRssi()+"");
                }else if (mScanResults.get(beaconAddress20) == null || temp20 == true){
                    outputListRow.add(0+"N/A");
                }
                if(mScanResults.get(beaconAddress21) != null) {
                    ScanResult beacon21 = (ScanResult) mScanResults.get(beaconAddress21);
                        outputListRow.add(beacon21.getRssi()+"");
                }else if (mScanResults.get(beaconAddress21) == null || temp21 == true){
                    outputListRow.add(0+"N/A");
                }
                if(mScanResults.get(beaconAddress22) != null) {
                    ScanResult beacon22 = (ScanResult) mScanResults.get(beaconAddress22);
                        outputListRow.add(beacon22.getRssi()+"");
                }else if (mScanResults.get(beaconAddress22) == null || temp22 == true){
                    outputListRow.add(0+"N/A");
                }
                if(mScanResults.get(beaconAddress23) != null) {
                    ScanResult beacon23 = (ScanResult) mScanResults.get(beaconAddress23);
                        outputListRow.add(beacon23.getRssi()+"");
                }else if (mScanResults.get(beaconAddress23) == null || temp23 == true){
                    outputListRow.add(0+"N/A");
                }
//                if(mScanResults.get(beaconAddress24) != null) {
//                    ScanResult beacon24 = (ScanResult) mScanResults.get(beaconAddress24);
//                    outputListRow.add(beacon24.getRssi());
//                }
//                else {
//                    outputListRow.add(0);
//                }
                long time= System.currentTimeMillis();
                outputListRow.add(time+"");
                outputList.add(outputListRow);
                dataScannedText.setText("Data Scanned : " + counter);
                dataScannedBar.setProgress(counter);
                counter++;
            }
            setDefaultTemp();
            BLEHandler.postDelayed(BLEScanner, 1000);
        }
    }

    private boolean hasPermissions() {
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            requestBluetoothEnable();
            return false;
        } else if (!hasLocationPermissions()) {
            requestLocationPermission();
            return false;
        }
        return true;
    }
    private void requestBluetoothEnable() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        Log.d(TAG, "Requested user enables Bluetooth. Try starting the scan again.");
    }
    private boolean hasLocationPermissions() {
        return checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
    private void requestLocationPermission() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
