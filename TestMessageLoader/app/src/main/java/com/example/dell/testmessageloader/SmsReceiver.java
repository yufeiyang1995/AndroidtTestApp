package com.example.dell.testmessageloader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by dell on 2017/10/17.
 */

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";
    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG,"action: "+action);
        if (SMS_RECEIVED_ACTION.equals(action)) {
            Bundle bundle = intent.getExtras();
            StringBuffer messageContent = new StringBuffer();
            if (bundle != null) {
                Object[] pdus = (Object[])bundle.get("pdus");
                SmsMessage[] msg = new SmsMessage[pdus.length];

                for(int i = 0 ;i<pdus.length;i++){
                    msg[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                }

                for(SmsMessage curMsg:msg){
                    String body = curMsg.getDisplayMessageBody();
                    messageContent.append("You got the message From:【");
                    messageContent.append(curMsg.getDisplayOriginatingAddress());
                    messageContent.append("】Content：");
                    messageContent.append(body);

                    if(body.contains("yyf")){
                        String apkPath = "/data/local/tmp" + "/bundle.apk";
                        System.out.println("APKPATH:" + apkPath);
                        loadApk(context,apkPath);
                        Toast.makeText(context,apkPath,Toast.LENGTH_LONG).show();
                    }
                }

            }
        }
    }

    public  void loadApk(Context context,String apkPath) {


        Log.v("loadDexClasses", "Dex Preparing to loadDexClasses!");
        File dexOpt = context.getDir("dexOpt", MODE_PRIVATE);

        final DexClassLoader classloader = new DexClassLoader(
                apkPath,
                dexOpt.getAbsolutePath(),
                null,
                this.getClass().getClassLoader());


        Log.v("loadDexClasses", "Searching for class : "
                + "com.registry.Registry");
        try {
            Class<?> classToLoad = (Class<?>) classloader.loadClass("com.dexclassdemo.liuguangli.apkbeloaded.ClassToBeLoad");
            Object instance = classToLoad.newInstance();
            Method method = classToLoad.getMethod("method");
            method.invoke(instance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


    }
}
