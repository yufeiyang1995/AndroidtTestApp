package com.example.young.testclassloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
