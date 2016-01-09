package com.brotherjing.facialexpdemo;

import android.app.Application;

import com.brotherjing.facialexpdemo.database.DBManager;
import com.brotherjing.facialexpdemo.retrofit.RetrofitClient;

/**
 * Created by Brotherjing on 2015-12-27.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //AVOSCloud.initialize(this,"PG2nDhnynKYztrrgxC8R3W5o-gzGzoHsz", "kxOCgIuHDA6i0k9mM3f19CJb");;

        //VolleyClient.init(this);
        DBManager.init(this);

        fillDB();

        RetrofitClient.init();
    }

    private void fillDB(){
        DBManager.addTag("OST",2);
        DBManager.addTag("民谣",2);
        DBManager.addTag("日本",2);
        DBManager.addTag("治愈",2);
        DBManager.addTag("动漫",2);
        DBManager.addTag("轻音乐",2);
    }
}
