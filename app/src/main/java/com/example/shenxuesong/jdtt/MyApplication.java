package com.example.shenxuesong.jdtt;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by shenxuesong on 2017/9/14.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration con=new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(getoptin())
                .build();
         ImageLoader.getInstance().init(con);
    }
    //自定义图片
    private DisplayImageOptions getoptin(){
        DisplayImageOptions  option=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .build();
        return option;
    }
}
