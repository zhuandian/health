package com.zhuandian.health.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhuandian.health.App;
import com.zhuandian.health.entity.ScheduleEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiedong
 * @desc
 * @date 2020-04-10.
 */
public class DBManager {

    private DBOpenHelper helper;
    private SQLiteDatabase db;
    private static DBManager instance;

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }

        return instance;
    }

    private DBManager() {
        helper = new DBOpenHelper(App.getInstance());
        db = helper.getWritableDatabase();

    }


    /**
     * 插入数据
     *
     * @param entity
     */
    public void insertData(ScheduleEntity entity) {
        db.beginTransaction();
        ContentValues cv = new ContentValues();
        cv.put("title", entity.getTitle());
        cv.put("content", entity.getContent());
        cv.put("type", entity.getType());
        cv.put("day", entity.getDay());
        cv.put("hour", entity.getHour());
        cv.put("minute", entity.getMinute());
        db.insert("schedule", null, cv);
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    /**
     * 删除数据
     *
     * @param title
     */
    public void deleteDataByTitle(String title) {
        db.beginTransaction();
        db.delete("schedule", "title=?", new String[]{title});
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    /**
     * 查询所有数据
     *
     * @return
     */
    public List<ScheduleEntity> getAllData() {
        List<ScheduleEntity> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from schedule", null);
        while (cursor.moveToNext()) {
            ScheduleEntity entity = new ScheduleEntity();
            entity.setContent(cursor.getString(cursor.getColumnIndex("content")));
            entity.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            entity.setType(cursor.getInt(cursor.getColumnIndex("type")));
            entity.setDay(cursor.getString(cursor.getColumnIndex("day")));
            entity.setHour(cursor.getInt(cursor.getColumnIndex("hour")));
            entity.setMinute(cursor.getInt(cursor.getColumnIndex("minute")));
            list.add(entity);
        }

        return list;
    }


    /**
     * 查询所有数据
     *
     * @return
     */
    public List<ScheduleEntity> getDataByTime(String day, String hour, String minute) {
        List<ScheduleEntity> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from schedule where day = ? and hour = ? and minute = ?", new String[]{day, hour, minute});
        while (cursor.moveToNext()) {
            ScheduleEntity entity = new ScheduleEntity();
            entity.setContent(cursor.getString(cursor.getColumnIndex("content")));
            entity.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            entity.setType(cursor.getInt(cursor.getColumnIndex("type")));
            entity.setDay(cursor.getString(cursor.getColumnIndex("day")));
            entity.setHour(cursor.getInt(cursor.getColumnIndex("hour")));
            entity.setMinute(cursor.getInt(cursor.getColumnIndex("minute")));
            list.add(entity);
        }

        return list;
    }
}
