package entity;

import annotation.Column;
import annotation.Id;
import annotation.Table;

@Table(name = "KHACHHANG")
public class Customer {
    @Id
    @Column(name = "KHACHHANGID")
    private Integer id;
    @Column(name = "MAKHACHANG")
    private String code;
    @Column(name = "TENKHACHHANG")
    private String name;
    @Column(name = "SODIENTHOAI")
    private String phoneNumber;

    public Customer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
