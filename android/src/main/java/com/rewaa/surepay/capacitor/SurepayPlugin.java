package com.rewaa.surepay.capacitor;

import static com.sure.poslibrary.POSService.lastFinTransStr;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
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
        Log.i(TAG, "echo: test Surepay: " + call);
        JSObject ret = new JSObject();
        ret.put("value1", "Abc##");
        ret.put("status", "connected");
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void printOnSurepay(PluginCall call) {
        String content = call.getString("content");
        String type = call.getString("type");
        Log.i(TAG, "printOnSurepay: " + call);
        openReceiptActivity(content,type);
    }

    private void openReceiptActivity(String data,String type) {
        Intent intent = new Intent(mContext, ReceiptActivity.class);
        intent.putExtra("data",data);
        intent.putExtra("type",type);
        mContext.startActivity(intent);
    }

    @PluginMethod
    public void enableBluetoothListenerService(PluginCall call) {
//        String value = call.getString("value");
        Log.i(TAG, "enableBluetoothListenerService: " + call);
        if (lService.StartListenService(mContext, "BT", Constants.SERVICE_NAME, null, null, true) == 0) {
            Log.w(TAG, "Service started to listen");
            JSObject ret = new JSObject();
            ret.put("result", true);
            call.resolve(ret);
        } else {
            Log.w(TAG, "Failed to start service listener");
            JSObject ret = new JSObject();
            ret.put("result", false);
            call.resolve(ret);
        }
    }

    @PluginMethod
    public void disableBluetoothListnerService(PluginCall call) {
//        String value = call.getString("value");
        Log.i(TAG, "disableBluetoothListnerService: " + call);
        if (lService.ShutdownListenService() == 0) {
            Log.w(TAG, "service is shutdown");
            JSObject ret = new JSObject();
            ret.put("result", true);
            call.resolve(ret);
        } else {
            Log.w(TAG, "Failed to shutdown service");
            JSObject ret = new JSObject();
            ret.put("result", false);
            call.resolve(ret);
        }
    }

    @PluginMethod
    public void getSurepayConnectionStatus(PluginCall call) {
//        String value = call.getString("value");
        Log.i(TAG, "getSurepayConnectionStatus: " + call);
        if (lService.checkConnectionStatus() == 0) {
            Log.w(TAG, "Device is connected");
            JSObject ret = new JSObject();
            ret.put("result", true);
            call.resolve(ret);
        } else {
            Log.w(TAG, "Device is not connected");
            JSObject ret = new JSObject();
            ret.put("result", false);
            call.resolve(ret);
        }
    }

    @PluginMethod
    public void getConnectedDeviceInfo(PluginCall call) {
        Log.i(TAG, "getConnectedDeviceInfo: " + call);
        if (lService.GetConnectedDeviceInfo() == 0) {
            Log.w(TAG, POSService.deviceInfoStr);
            JSObject ret = new JSObject();
            ret.put("deviceInfo", POSService.deviceInfoStr);
            call.resolve(ret);
        } else {
            Log.w(TAG, "No device is connected\"");
            JSObject ret = new JSObject();
            ret.put("result", false);
            call.resolve(ret);
        }
    }

    @PluginMethod


    public void submitTransaction(PluginCall call) {
        String amount = call.getString("amount");
        Log.i(TAG, "submitTransaction: " + call);
        lService.PerformFinTrx(mContext, 0, amount, null, null, null);
    }


    @PluginMethod
    public void showLastTransactionDetails(PluginCall call) {
        Log.i(TAG, "showLastTransactionDetails: " + call);
        if (lService.ShowReceipt(mContext) == 0) {
            Log.w(TAG, lastFinTransStr);
            JSObject ret = new JSObject();
            ret.put("trxInfo", "Showing transaction");
            call.resolve(ret);
        } else {
            Log.w(TAG, "No transactions");
            JSObject ret = new JSObject();
            ret.put("result", "No transactions");
            call.resolve(ret);
        }
    }

    @PluginMethod
    public void getLastTransactionDetails(PluginCall call) {
        Log.i(TAG, "getLastTransactionDetails: " + call);
        if (lService.GetLastTrxResult(mContext) == 0) {
            Log.w(TAG, lastFinTransStr);
            JSObject ret = new JSObject();
            ret.put("trxInfo", lastFinTransStr);
            call.resolve(ret);
        } else {
            Log.w(TAG, "No transactions");
            JSObject ret = new JSObject();
            ret.put("result", false);
            call.resolve(ret);
        }
    }

    @PluginMethod
    public void exportLastTransaction(PluginCall call) {
        Log.i(TAG, "exportLastTransaction: " + call);
        if (lService.ExportReceipt(mContext) == 0) {
            Log.w(TAG, lastFinTransStr);
            JSObject ret = new JSObject();
            ret.put("trxInfo", "File saved on Downloads folder");
            call.resolve(ret);
        } else {
            Log.w(TAG, "No transactions");
            JSObject ret = new JSObject();
            ret.put("result", "Error");
            call.resolve(ret);
        }
    }

    @PluginMethod
    public void clearLastTransaction(PluginCall call) {
        Log.i(TAG, "clearLastTransaction: " + call);
        if (lService.ClearTransactions(mContext) == 0) {
            Log.w(TAG, lastFinTransStr);
            JSObject ret = new JSObject();
            ret.put("trxInfo", "Transactions cleared");
            call.resolve(ret);
        }
    }


    @Override
    public void OnNewReceipt(String s) {
        System.out.println("callback function: new receipt -----> " + s);
    }

    @Override
    public void OnConnectionLost() {
        System.out.println("callback function: Connection lost :(");
    }

    @Override
    public void OnConnectionEstablished() {
        System.out.println("callback function: Connection established :)");
    }

    @Override
    public void OnError(int iError) {
        System.out.println("callback function: OnError .. Error code = " + iError);
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
