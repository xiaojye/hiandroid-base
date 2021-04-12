package com.jye.hiandroid.base;

import com.jye.hiandroid.log.HiLogConfig;

/**
 * @author jye
 * @since 1.0
 */
public abstract class HiBaseConfig {

    public boolean openIoc() {
        return true;
    }

    public HiLogConfig logConfig() {
        return new HiLogConfig() {
        };
    }
}
