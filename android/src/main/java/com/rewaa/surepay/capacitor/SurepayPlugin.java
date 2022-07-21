package com.rewaa.surepay.capacitor;

import static com.sure.poslibrary.POSService.lastFinTransStr;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.sure.poslibrary.Constants;
import com.sure.poslibrary.POSService;
import com.sure.poslibrary.callback.ConnectionInterface;

import org.json.JSONObject;

@CapacitorPlugin(name = "Surepay")
public class SurepayPlugin extends Plugin implements ConnectionInterface {

    private Surepay implementation = new Surepay();
    private Context mContext;
    POSService lService;
    private String TAG = "SurepayPlugin";
    private PaymentStatusReceiver paymentStatusReceiver;


    @Override
    public void load() {
        super.load();
        this.mContext = getContext();
        lService = new POSService(this);
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

        paymentStatusReceiver = new PaymentStatusReceiver(call);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("surepay.mada.RESULT");
        this.mContext.registerReceiver(paymentStatusReceiver,intentFilter);

        String amount = call.getString("amount");
        Log.i(TAG, "submitTransaction: " + call);
        double a = 0;
        try{
             a = Double.parseDouble(amount);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(a>0) {
            sendAmountToMadaApplication(amount);
        }else{
            Toast.makeText(mContext, "Invalid amount", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendAmountToMadaApplication(String amount) {
        if (!isMadaAppInstalled()) {
            Toast.makeText(mContext, "Mada App Not Installed", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isMadaAppRunning()) {
            Toast.makeText(mContext, "Mada App Not Running", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent("surepay.mada.PAY_AMOUNT");
//        intent.putExtra("LICENCE", "YOUR_LICENCE_FROM_SURE_HERE");
        intent.putExtra("AMOUNT", amount);
        mContext.sendBroadcast(intent);
    }


    boolean isMadaAppRunning() {
        try {
            ActivityManager manager =
                    (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);

            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if ("com.surepay.mada.MadaIntegration.MadaIntegrationService".equals(service.service.getClassName()))
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isMadaAppInstalled() {
        PackageManager packageManager = mContext.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo("com.surepay.mada", PackageManager.GET_ACTIVITIES);
            return packageInfo != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void sendEvent(JSObject data) {
        notifyListeners("surepayPaymentRes", data);
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

    public static class PaymentStatusReceiver extends BroadcastReceiver {
        private PluginCall pluginCall = null;

        public PaymentStatusReceiver() {
        }

        public PaymentStatusReceiver(PluginCall call) {
            this.pluginCall = call;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String result = intent.getExtras().getString("RESULT");
            Log.e("RESULT","==================> "+result);
            try {
                JSONObject obj = new JSONObject(result);
                Log.d("My App", obj.toString());
                if(pluginCall!=null){
                    JSObject jsObject = new JSObject();
                    jsObject.put("status", true);
                    jsObject.put("result", result);
                    pluginCall.resolve(jsObject);
                }
                context.unregisterReceiver(this);
            } catch (Exception e) {
                e.printStackTrace();
                JSObject jsObject = new JSObject();
                jsObject.put("status", false);
                jsObject.put("result", result);
                pluginCall.resolve(jsObject);
                Log.e("My App", "Could not parse malformed JSON: \"" + result + "\"");
            }
        }
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
