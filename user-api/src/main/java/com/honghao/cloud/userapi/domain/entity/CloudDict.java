package com.honghao.cloud.userapi.domain.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;

public class CloudDict {
    @NotBlank
    private String id;
    @NotBlank
    private String parentid;
    @NotBlank
    private String path;
    @NotBlank(groups = Person.class)
    private String name;

    private String code;

    private Integer location;

    private Boolean haschildren;

    private String opby;

    private Integer opat;

    private Boolean delflag;

    public interface Person extends Default {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public Boolean getHaschildren() {
        return haschildren;
    }

    public void setHaschildren(Boolean haschildren) {
        this.haschildren = haschildren;
    }

    public String getOpby() {
        return opby;
    }

    public void setOpby(String opby) {
        this.opby = opby == null ? null : opby.trim();
    }

    public Integer getOpat() {
        return opat;
    }

    public void setOpat(Integer opat) {
        this.opat = opat;
    }

    public Boolean getDelflag() {
        return delflag;
    }

    public void setDelflag(Boolean delflag) {
        this.delflag = delflag;
    }
}