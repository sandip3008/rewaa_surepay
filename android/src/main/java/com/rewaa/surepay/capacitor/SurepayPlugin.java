package com.rewaa.surepay.capacitor;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.sure.poslibrary.Constants;
import com.sure.poslibrary.POSService;
import com.sure.poslibrary.callback.ConnectionInterface;

@CapacitorPlugin(name = "Surepay")
public class SurepayPlugin extends Plugin implements ConnectionInterface {

    private Surepay implementation = new Surepay();
    private Context mContext;
    POSService lService;
    private String TAG = "SurepayPlugin";


    @Override
    public void load() {
        super.load();
        this.mContext = getContext();
//        grantPermission();
    }

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");
        Log.i(TAG, "echo: test Surepay: " + call );
        JSObject ret = new JSObject();
        ret.put("value1", "Abc##");
        ret.put("status", "connected");
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void enableBluetoothListenerService(PluginCall call) {
//        String value = call.getString("value");
        Log.i(TAG, "enableBluetoothListenerService: " + call );
        if(lService.StartListenService(mContext, "BT", Constants.SERVICE_NAME, null, null,true)==0){
            Log.w(TAG,"Service started to listen");
            JSObject ret = new JSObject();
            ret.put("result", true);
            call.resolve(ret);
        }else{
            Log.w(TAG,"Failed to start service listener");
            JSObject ret = new JSObject();
            ret.put("result", false);
            call.resolve(ret);
        }
    }

    @PluginMethod
    public void disableBluetoothListnerService(PluginCall call) {
//        String value = call.getString("value");
        Log.i(TAG, "disableBluetoothListnerService: " + call );
        if(lService.ShutdownListenService()==0){
            Log.w(TAG,"service is shutdown");
            JSObject ret = new JSObject();
            ret.put("result", true);
            call.resolve(ret);
        }else{
            Log.w(TAG,"Failed to shutdown service");
            JSObject ret = new JSObject();
            ret.put("result", false);
            call.resolve(ret);
        }
    }

    @PluginMethod
    public void getSurepayConnectionStatus(PluginCall call) {
//        String value = call.getString("value");
        Log.i(TAG, "getSurepayConnectionStatus: " + call );
        if(lService.checkConnectionStatus()==0){
            Log.w(TAG,"Device is connected");
            JSObject ret = new JSObject();
            ret.put("result", true);
            call.resolve(ret);
        }else{
            Log.w(TAG,"Device is not connected");
            JSObject ret = new JSObject();
            ret.put("result", false);
            call.resolve(ret);
        }
    }

    @PluginMethod
    public void getConnectedDeviceInfo(PluginCall call) {
//        String value = call.getString("value");
        Log.i(TAG, "getConnectedDeviceInfo: " + call );
        if(lService.GetConnectedDeviceInfo()==0){
            Log.w(TAG,POSService.deviceInfoStr);
            JSObject ret = new JSObject();
            ret.put("deviceInfo", POSService.deviceInfoStr);
            call.resolve(ret);
        }else{
            Log.w(TAG,"No device is connected\"");
            JSObject ret = new JSObject();
            ret.put("result", false);
            call.resolve(ret);
        }
    }

    @Override
    public void OnNewReceipt(String s) {

    }

    @Override
    public void OnConnectionLost() {

    }

    @Override
    public void OnConnectionEstablished() {

    }

    @Override
    public void OnError(int i) {

    }

//    private void grantPermission(){
//        Dexter.withActivity(mContext)
//                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .withListener(new PermissionListener() {
//                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
//
//                    }
//                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {
//
//                    }
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//
//                    }
//                }).check();
//    }
}
