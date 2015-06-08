/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbjohn.csv;

import java.io.Serializable;
import java.util.Date;

/**
 * User partner model
 *
 */
public class UserPartner implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private UserPartner id;

    private int userID;

    private int partnerID;

    private String aliasName;

    private String status; //'A=Active, X=Canceled;  Additional codes for Outblaze/partner=12: S=Sunset(suspended);
    //A=Basic service; B=10 MB additional storage; C=Correo Max with 30MB; H=Web Hosting;
    //1=10MB storage + Web Hosting; 2=Correo Max+Web Hosting; 3=10MB storage + Correo Max;
    //4=10MB storage + Correo Max + Web Hosting;'

    private Date statusDate;

    private Date registrationDate;

    private Date lastActivityDate;

    private String lastActivityType; //'R=Registration, C=email Confirmation, L=Login, S=other Status changes,
    //V=conVersion'

    private int activityCount; //'Incremented by 1 for each user activity (excluding IT and background activity)

    private Date createdDate;

    private Date modifiedDate;

    private String modifiedBy;

    private String optinNewsLetter; //Y=Use C9522_Alias_Name@univision.com for newsletters.
    //The flag is used only for "hidden univision.com" emails

    private String userType;

    private String vendorUserID; //Outside vendor user id (currently used by partners Onesite or Facebook)

    public UserPartner() {

    }

    public UserPartner getId() {
        return id;
    }

    public void setId(UserPartner id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getPartnerID() {
        return partnerID;
    }

    public void setPartnerID(int partnerID) {
        this.partnerID = partnerID;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    @Override
    public String toString() {
        return "UserPartner [userID=" + userID + ", partnerID=" +
                partnerID + ", aliasName=" + aliasName + ", status=" + status +
                ", statusDate=" + statusDate + ", registrationDate=" +
                registrationDate + ", lastActivityDate=" + lastActivityDate +
                ", lastActivityType=" + lastActivityType + ", activityCount=" +
                activityCount + ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate + ", modifiedBy=" +
                modifiedBy + ", optinNewsLetter=" + optinNewsLetter +
                ", userType=" + userType + ", vendorUserID=" + vendorUserID +
                "]";
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(Date lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    public String getLastActivityType() {
        return lastActivityType;
    }

    public void setLastActivityType(String lastActivityType) {
        this.lastActivityType = lastActivityType;
    }

    public int getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(int activityCount) {
        this.activityCount = activityCount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getOptinNewsLetter() {
        return optinNewsLetter;
    }

    public void setOptinNewsLetter(String optinNewsLetter) {
        this.optinNewsLetter = optinNewsLetter;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getVendorUserID() {
        return vendorUserID;
    }

    public void setVendorUserID(String vendorUserID) {
        this.vendorUserID = vendorUserID;
    }

}
