package entity;

import annotation.Column;
import annotation.Id;
import annotation.Table;

/**
 * @author Admin
 */
@Table(name = "NHANVIEN")
public class Staff {
    @Id
    @Column(name = "NHANVIENID")
    private Integer id;
    @Column(name = "MANHANVIEN")
    private String code;
    @Column(name = "TENNHANVIEN")
    private String name;
    @Column(name = "MATKHAU")
    private String password;
    @Column(name = "SODIENTHOAI")
    private String phoneNumber;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "VAITRO")
    private String role;

    public Staff() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
