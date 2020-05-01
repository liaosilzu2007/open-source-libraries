package com.lzumetal.open.source.gson.bigjson;

import java.util.Date;
import java.util.List;

/**
 * @author liaosi
 * @date 2019-02-24
 */
public class User {

    private long id;

    private Date createTime;

    private String name;

    private String avator;

    private List<Address> receiveAddrs;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public List<Address> getReceiveAddrs() {
        return receiveAddrs;
    }

    public void setReceiveAddrs(List<Address> receiveAddrs) {
        this.receiveAddrs = receiveAddrs;
    }
}
