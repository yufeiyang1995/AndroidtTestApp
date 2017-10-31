package com.example.dell.testloader1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = (EditText) findViewById(R.id.editText);
        String Message = editText.getText().toString();

        Button button = (Button) findViewById(R.id.button);

        /*int[] location = new int[2];
        button.getLocationInWindow(location);
        int x = location[0];
        int y = location[1];
        System.out.println("x: " + x + ",y: " + y);*/

        // 1-initial-toast
            /*button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Intent intent = new Intent(MainActivity.this,ShowActivity.class);
                    //startActivity(intent);
                    String apkPath = "/data/local/tmp" + "/bundle.apk";
                    System.out.println("APKPATH:" + apkPath);
                    loadApk(apkPath);
                    Toast.makeText(MainActivity.this,apkPath,Toast.LENGTH_LONG).show();
                }
            });*/

        // 3-button-if-toast
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.editText);
                String Message = editText.getText().toString();
                if(Message.contains("yyf")) {
                        String apkPath = "/data/local/tmp" + "/bundle.apk";
                        System.out.println("APKPATH:" + apkPath);
                        loadApk(apkPath);
                        Toast.makeText(MainActivity.this,apkPath,Toast.LENGTH_LONG).show();
                    }
                    else{
                    Toast.makeText(MainActivity.this,"No Loading",Toast.LENGTH_LONG).show();
                    }
            }
        });

        //2-if-button-toast
        /*if(Message.contains("yyf")){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Intent intent = new Intent(MainActivity.this,ShowActivity.class);
                    //startActivity(intent);
                    String apkPath = "/data/local/tmp" + "/bundle.apk";
                    System.out.println("APKPATH:" + apkPath);
                    loadApk(apkPath);
                    Toast.makeText(MainActivity.this,apkPath,Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            Toast.makeText(MainActivity.this,"No Loading",Toast.LENGTH_LONG).show();
        }*/
    }

    public  void loadApk(String apkPath) {


        Log.v("loadDexClasses", "Dex Preparing to loadDexClasses!");

        File dexOpt = this.getDir("dexOpt", MODE_PRIVATE);
        final DexClassLoader classloader = new DexClassLoader(
                apkPath,
                dexOpt.getAbsolutePath(),
                null,
                this.getClassLoader());


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
