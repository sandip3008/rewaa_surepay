package com.rewaa.surepay.capacitor;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "Surepay")
public class SurepayPlugin extends Plugin {

    private Surepay implementation = new Surepay();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");
        Log.e(TAG, "echo: test Surepay" );
        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }
}
