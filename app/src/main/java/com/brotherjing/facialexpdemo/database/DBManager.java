package com.brotherjing.facialexpdemo.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.greendao.dao.DaoMaster;
import com.greendao.dao.DaoSession;
import com.greendao.dao.Tag;
import com.greendao.dao.TagDao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.Query;
import rx.Observable;

/**
 * Created by Brotherjing on 2015-12-31.
 */
public class DBManager {

    private static SQLiteDatabase db_write;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private static TagDao tagDao;

    public DBManager(Context context){
    }

    public static void init(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context,"tag_db",null);
        db_write = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db_write);
        daoSession = daoMaster.newSession();
        tagDao = daoSession.getTagDao();
    }

    public static boolean addTag(Tag tag){
        if(isRowExist(tag.getName()))return false;
        tagDao.insert(tag);
        return true;
    }

    public static boolean addTag(String name){
        Tag tag = new Tag();
        tag.setName(name);
        tag.setCount(0);
        if(isRowExist(name))return false;
        tagDao.insert(tag);
        return true;
    }

    private static boolean isRowExist(String name){
        return tagDao.queryBuilder().where(TagDao.Properties.Name.eq(name)).count()>0;
    }

    public static Tag getMaxTag(){
        return tagDao.queryBuilder().orderDesc(TagDao.Properties.Count).limit(1).list().get(0);
    }

    public static Tag getGoodTag(){
        List<Tag> tags = tagDao.queryBuilder().orderDesc(TagDao.Properties.Count).limit(10).list();
        return tags.get((int)(Math.random()*tags.size()));
    }

    public static Observable<Tag> getTag(){
        return Observable.just(getGoodTag());
    }

    public static boolean updateTag(Tag tag){
        tagDao.update(tag);
        return true;
    }

    public static void increaseTagCount(String name,int cnt){
        Tag tag = findByName(name);
        if(tag!=null){
            tag.setCount(tag.getCount()+cnt);
            updateTag(tag);
        }else{
            tag = new Tag();
            tag.setCount(1);
            tag.setName(name);
            tagDao.insert(tag);
        }
    }

    public static void decreaseTagCount(String name,int cnt){
        Tag tag = findByName(name);
        if(tag!=null){
            tag.setCount(tag.getCount()-cnt);
            updateTag(tag);
        }else{
            tag = new Tag();
            tag.setCount(0);
            tag.setName(name);
            tagDao.insert(tag);
        }
    }


    public static Tag findByName(String name){
        return tagDao.queryBuilder().where(TagDao.Properties.Name.eq(name)).unique();
    }

    public static void printDB(){
        List<Tag> tags =  tagDao.queryBuilder().list();
        for(Tag t:tags){
            Log.i("yj",t.getName()+":"+t.getCount() );
        }
    }

}
