package com.cedar.cp.utc.struts.admin;

import java.sql.Date;

public class RoadMapDTO {
    private Integer id;
    private Integer revision;
    private Date versionDate;
    private String description;
    
    public RoadMapDTO(Integer id, Integer revision, Date versionDate, String description) {
        super();
        this.id = id;
        this.revision = revision;
        this.versionDate = versionDate;
        this.description = description;
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getRevision() {
        return revision;
    }
    public void setRevision(Integer revision) {
        this.revision = revision;
    }
    public Date getVersionDate() {
        return versionDate;
    }
    public void setVersionDate(Date versionDate) {
        this.versionDate = versionDate;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
