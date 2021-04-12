package com.jye.hiandroid.base.cache.spcache;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jye.hiandroid.base.cache.MCCache;
import com.jye.hiandroid.base.json.HiJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * SharedPreferences缓存管理
 *
 * @author jye
 * @since 1.0
 */
public class SPCache implements MCCache {

    private SharedPreferences mSharedPreferences;

    public SPCache(@NonNull SharedPreferences sharedPreferences) {
        this.mSharedPreferences = sharedPreferences;
    }

    @Nullable
    @Override
    public ValueWrapper get(String key) {
        String data = mSharedPreferences.getString(key, null);
        if (data != null) {
            if (data.startsWith("{")) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.has("data") && jsonObject.has("ttl") && jsonObject.has("time")) {
                        SaveModel saveModel = HiJson.parseObject(data, SaveModel.class);
                        if (saveModel.checkValidity()) {
                            return new SPValueWrapper(saveModel.data);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return new SPValueWrapper(data);
        }
        return null;
    }

    public int getInt(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return mSharedPreferences.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return mSharedPreferences.getFloat(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }

    public String getString(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    @Override
    public void put(String key, @Nullable Object value) {
        put(key, value, SaveModel.TTL_PERMANENT);
    }

    @Override
    public void put(String key, @Nullable Object value, int ttl) {
        SaveModel saveModel = new SaveModel(value, ttl);
        String json = HiJson.toJsonString(saveModel);
        mSharedPreferences.edit().putString(key, json).apply();
    }

    public void put(String key, int value) {
        mSharedPreferences.edit().putInt(key, value).apply();
    }

    public void put(String key, long value) {
        mSharedPreferences.edit().putLong(key, value).apply();
    }

    public void put(String key, float value) {
        mSharedPreferences.edit().putFloat(key, value).apply();
    }

    public void put(String key, boolean value) {
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }

    public void put(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    @Override
    public void remove(String key) {
        mSharedPreferences.edit().remove(key).apply();
    }

    @Override
    public Map<String, ValueWrapper> getAll() {
        Map<String, ValueWrapper> result = new HashMap<>();

        try {
            Map<String, ?> all = mSharedPreferences.getAll();
            if (all != null) {
                for (String s : all.keySet()) {
                    result.put(s, new SPValueWrapper(all.get(s)));
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }

    private static class SaveModel implements Serializable {

        //存活时间-永久有效
        static final int TTL_PERMANENT = -1;

        Object data;
        int ttl;
        long time;

        public SaveModel(Object data, int ttl) {
            this.data = data;
            this.ttl = ttl;
            this.time = System.currentTimeMillis();
        }

        /**
         * 检查是否有效
         *
         * @return true表示还有效，false表示已过期
         */
        public boolean checkValidity() {
            if (ttl == -1) {
                return true;
            }
            return (System.currentTimeMillis() - time) / 1000 < ttl;
        }
    }
}