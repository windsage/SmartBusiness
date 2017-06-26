package com.tairui.smartcommunity.tenant.model;

/**
 * Created by Jeffery on 2017/5/7.
 */

public class FileLoadingBean {

    /**
     * 文件大小
     */
    long total;
    /**
     * 已下载大小
     */
    long progress;

    public long getProgress() {
        return progress;
    }

    public long getTotal() {
        return total;
    }

    public FileLoadingBean(long total, long progress) {
        this.total = total;
        this.progress = progress;
    }
}
