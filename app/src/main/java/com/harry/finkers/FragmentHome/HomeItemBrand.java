package com.harry.finkers.FragmentHome;

public class HomeItemBrand {

    private String nama;
    private String image;
    private String detail;

    public HomeItemBrand(String nama, String image, String detail) {
        this.nama = nama;
        this.image = image;
        this.detail = detail;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
