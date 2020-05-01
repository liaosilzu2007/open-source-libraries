package com.lzumetal.open.source.gson.bigjson;

/**
 * @author liaosi
 * @date 2019-02-24
 */
public class Address {


    private String province;

    private String city;

    private String district;

    private String StreetAddr;

    private String phone;

    public Address() {
    }

    public Address(String province, String city, String district) {
        this.province = province;
        this.city = city;
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreetAddr() {
        return StreetAddr;
    }

    public void setStreetAddr(String streetAddr) {
        StreetAddr = streetAddr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
