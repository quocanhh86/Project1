package entity;

import annotation.Column;
import annotation.Id;
import annotation.Table;

@Table(name = "DANHMUC")
public class Category {
    @Column(name = "DANHMUCID")
    @Id
    private Integer id;
    @Column(name = "TENDANHMUC")
    private String name;
    @Column(name = "TRANGTHAI")
    private String status;

    public Category() {
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
