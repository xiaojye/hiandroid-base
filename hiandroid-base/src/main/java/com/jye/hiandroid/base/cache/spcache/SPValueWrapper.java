package com.jye.hiandroid.base.cache.spcache;

import androidx.annotation.Nullable;

import com.jye.hiandroid.base.cache.MCCache;

/**
 * @author jye
 * @since 1.0
 */
public class SPValueWrapper extends MCCache.ValueWrapper {

    public SPValueWrapper(@Nullable Object value) {
        super(value);
    }

    @Override
    public int asInt(int defValue) {
        String value = asString(null);
        if (value != null) {
            return Integer.parseInt(value);
        }
        return defValue;
    }

    @Override
    public long asLong(long defValue) {
        String value = asString(null);
        if (value != null) {
            return Long.parseLong(value);
        }
        return defValue;
    }

    @Override
    public float asFloat(float defValue) {
        String value = asString(null);
        if (value != null) {
            return Float.parseFloat(value);
        }
        return defValue;
    }

    @Override
    public boolean asBoolean(boolean defValue) {
        String value = asString(null);
        if (value != null) {
            return Boolean.parseBoolean(value);
        }
        return defValue;
    }

    @Nullable
    @Override
    public String asString(@Nullable String defValue) {
        return value != null ? value.toString() : defValue;
    }
}
