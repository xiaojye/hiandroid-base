package com.jye.hiandroid.base.cache;

import androidx.annotation.Nullable;

import com.google.gson.JsonParseException;
import com.jye.hiandroid.base.json.HiJson;

import java.util.List;
import java.util.Map;

/**
 * @author jye
 * @since 1.0
 */
public interface MCCache {

    @Nullable
    ValueWrapper get(String key);

    void put(String key, @Nullable Object value);

    void put(String key, @Nullable Object value, int ttl);

    void remove(String key);

    Map<String, ValueWrapper> getAll();

    void clear();

    abstract class ValueWrapper {

        @Nullable
        protected final Object value;

        public ValueWrapper(@Nullable Object value) {
            this.value = value;
        }

        @Nullable
        public Object value() {
            return this.value;
        }

        public abstract int asInt(int defValue);

        public abstract long asLong(long defValue);

        public abstract float asFloat(float defValue);

        public abstract boolean asBoolean(boolean defValue);

        @Nullable
        public abstract String asString(@Nullable String defValue);

        @Nullable
        public <T> T asObject(Class<T> clz) {
            String json = asString(null);
            try {
                return HiJson.parseObject(json, clz);
            } catch (JsonParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Nullable
        public <T> List<T> asList(Class<T> clz) {
            String json = asString(null);
            try {
                return HiJson.parseArray(json, clz);
            } catch (JsonParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
