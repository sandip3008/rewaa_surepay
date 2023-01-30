package com.rewaa.surepay.capacitor;

import static com.rewaa.surepay.capacitor.ReceiptBase64Activity.base64;
import static com.sure.poslibrary.POSService.lastFinTransStr;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.ActivityCallback;
import com.sure.poslibrary.Constants;
import com.sure.poslibrary.POSService;
import com.sure.poslibrary.callback.ConnectionInterface;
import androidx.activity.result.ActivityResult;


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
        this.mContext = getActivity();
        lService = new POSService(this);
    }

    @PluginMethod
    public void getBase64(PluginCall call) {
        String content = call.getString("content");
        openReceiptActivity(content, "base64", call);
    }

    @PluginMethod
    public void printOnSurepay(PluginCall call) {
        String content = call.getString("content");
        String type = call.getString("type");
        openReceiptActivity(content, type, call);
    }

    private void openReceiptActivity(String data, String type, PluginCall call) {
        if (type.equalsIgnoreCase("base64")) {
            Intent intent = new Intent(mContext, ReceiptBase64Activity.class);
            intent.putExtra("data", data);
            intent.putExtra("type", type);
            startActivityForResult(call, intent, "getbase64String");
        } else {
            Intent intent = new Intent(mContext, ReceiptActivity.class);
            intent.putExtra("data", data);
            intent.putExtra("type", type);
            mContext.startActivity(intent);
        }
    }

    @ActivityCallback
    private void getbase64String(PluginCall call, ActivityResult result) {
        if (call == null) {
            return;
        }
        JSObject ret = new JSObject();
        ret.put("base64", base64);
        call.resolve(ret);
        base64 = null;
    }

    @PluginMethod
    public void enableBluetoothListenerService(PluginCall call) {
//        String value = call.getString("value");
        if (lService.StartListenService(mContext, "BT", Constants.SERVICE_NAME, null, null, true) == 0) {
            JSObject ret = new JSObject();
            ret.put("result", true);
            call.resolve(ret);
        } else {
            JSObject ret = new JSObject();
            ret.put("result", false);
            call.resolve(ret);
        }
    }

    @PluginMethod
    public void disableBluetoothListnerService(PluginCall call) {
//        String value = call.getString("value");
        if (lService.ShutdownListenService() == 0) {
            JSObject ret = new JSObject();
            ret.put("result", true);
            call.resolve(ret);
        } else {
            JSObject ret = new JSObject();
            ret.put("result", false);
            call.resolve(ret);
        }
    }

    @PluginMethod
    public void getSurepayConnectionStatus(PluginCall call) {
//        String value = call.getString("value");
        if (lService.checkConnectionStatus() == 0) {
            JSObject ret = new JSObject();
            ret.put("result", true);
            call.resolve(ret);
        } else {
            JSObject ret = new JSObject();
            ret.put("result", false);
            call.resolve(ret);
        }
    }

    @PluginMethod
    public void getConnectedDeviceInfo(PluginCall call) {
        if (lService.GetConnectedDeviceInfo() == 0) {
            Log.w(TAG, POSService.deviceInfoStr);
            JSObject ret = new JSObject();
            ret.put("deviceInfo", POSService.deviceInfoStr);
            call.resolve(ret);
        } else {
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
        this.mContext.registerReceiver(paymentStatusReceiver, intentFilter);

        String amount = call.getString("amount");
        if (amount == null) {
            call.reject("amount_not_valid");
            mContext.unregisterReceiver(paymentStatusReceiver);
            return;
        }
        double d = 0.00d;
        try {
            d = Double.parseDouble(amount);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (d > 0) {
            sendAmountToMadaApplication(call, String.format("%.2f", d));
        } else {
            call.reject("amount_not_valid");
            mContext.unregisterReceiver(paymentStatusReceiver);
            return;
        }
    }

    private void sendAmountToMadaApplication(PluginCall call, String amount) {
        // if (!isMadaAppInstalled()) {
        //     call.reject("mada_app_not_installed");
        //     mContext.unregisterReceiver(paymentStatusReceiver);
        //     return;
        // } else if (!isMadaAppRunning()) {
        //     call.reject("mada_app_not_running");
        //     mContext.unregisterReceiver(paymentStatusReceiver);
        //     return;
        // }

        Intent intent = new Intent("surepay.mada.PAY_AMOUNT");
        intent.putExtra("LICENCE", "kyNjYtNmIOGIwYzY1OTYtNYjUtNjU0MC00MGZPiQ2GH00GMV");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public void sendEvent(JSObject data) {
        notifyListeners("surepayPaymentRes", data);
    }


    @PluginMethod
    public void showLastTransactionDetails(PluginCall call) {
        if (lService.ShowReceipt(mContext) == 0) {
            JSObject ret = new JSObject();
            ret.put("trxInfo", "Showing transaction");
            call.resolve(ret);
        } else {
            JSObject ret = new JSObject();
            ret.put("result", "No transactions");
            call.resolve(ret);
        }
    }

    @PluginMethod
    public void getLastTransactionDetails(PluginCall call) {
        if (lService.GetLastTrxResult(mContext) == 0) {
            JSObject ret = new JSObject();
            ret.put("trxInfo", lastFinTransStr);
            call.resolve(ret);
        } else {
            JSObject ret = new JSObject();
            ret.put("result", false);
            call.resolve(ret);
        }
    }

    @PluginMethod
    public void exportLastTransaction(PluginCall call) {
        if (lService.ExportReceipt(mContext) == 0) {
            JSObject ret = new JSObject();
            ret.put("trxInfo", "File saved on Downloads folder");
            call.resolve(ret);
        } else {
            JSObject ret = new JSObject();
            ret.put("result", "Error");
            call.resolve(ret);
        }
    }

    @PluginMethod
    public void clearLastTransaction(PluginCall call) {
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
            if (result == null) {
                JSObject jsObject = new JSObject();
                jsObject.put("status", false);
                pluginCall.resolve(jsObject);
                return;
            }
            try {
                if (pluginCall != null) {
                    JSObject jsObject = new JSObject();
                    jsObject.put("status", true);
                    jsObject.put("result", result);
                    pluginCall.resolve(jsObject);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JSObject jsObject = new JSObject();
                jsObject.put("status", false);
                jsObject.put("result", result);
                pluginCall.resolve(jsObject);
            }
            context.unregisterReceiver(this);
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
