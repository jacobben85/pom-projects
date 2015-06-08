/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbjohn.csv;

import java.util.Date;
import java.util.Map;

public class User {

    private int userID;

    private String email; //'Lower case only'

    private String aliasName; //'Only Lowercase, non-spanish, alpha and numerics (effective for new users only)';

    private String password; // 'Null is allowed when C2810_Registration_Type=N or S';

    private String displayAliasName;

    private String registrationType; //'P=Portal, N=Newsletter, S=NonRegistered, X=Canceled, W=Wireless(not used).
    //The reg-type can be changed from N to P, S to N, S to P, X to P or W to P)';

    private String status; // 'I=Inactive, A=Active, N=iNcomplete, B=Banned from interactive services, D=cancel pending, X=cancelled;
    //(Note: Currently I, A and N all mean active user)';

    private Date statusDate;

    private Integer localeID; // 'User's preference for site language, currently used only by login and registration: Null or 1=Spanish, 2=English';

    private String emailStatus; //'V=Valid, I=Invalid, 1-9=Soft Errors (incremental count).
    //This status is initialized to V when adding a new user or when a user changes his/her email address';

    private Date emailStatusDate;

    private String userName;

    private String ngUserID;

    private String mwcUID;

    private String ipAgent;

    private String confirmStatus; //'U=Unconfirmed, C=Confirmed';

    private Date confirmStatusDate;

    private String lastActivityType; //'R=portal Registration, N=Newsletter registration, C=email Confirmation, L=Login, P=Profile change, A=Admin Update, V=conVersion, O=cOllector update';

    private Date lastActivityDate;

    private Integer lastActivityCount; //Incremented by 1 for each user activity (excluding IT and background activity)

    private String socialActiveIndicator;

    private Map<String, UserPartner> userPartnerMap;

    public Map<String, UserPartner> getUserPartnerMap() {
        return userPartnerMap;
    }

    public void setUserPartnerMap(Map<String, UserPartner> userPartnerMap) {
        this.userPartnerMap = userPartnerMap;
    }

    @Override
    public String toString() {
        return "User [userID=" + userID + ", email=" + email + ", aliasName=" +
                aliasName + ", displayAliasName=" +
                displayAliasName + ", registrationType=" + registrationType +
                ", status=" + status + ", statusDate=" + statusDate +
                ", localeID=" + localeID + ", emailStatus=" + emailStatus +
                ", emailStatusDate=" + emailStatusDate + ", userName=" +
                userName + ", ngUserID=" + ngUserID + ", mwcUID=" + mwcUID +
                ", ipAgent=" + ipAgent + ", confirmStatus=" + confirmStatus +
                ", confirmStatusDate=" + confirmStatusDate +
                ", lastActivityType=" + lastActivityType +
                ", lastActivityDate=" + lastActivityDate +
                ", lastActivityCount=" + lastActivityCount + "]";
    }

    public String getSocialActiveIndicator() {
        return socialActiveIndicator;
    }

    public void setSocialActiveIndicator(String socialActiveIndicator) {
        this.socialActiveIndicator = socialActiveIndicator;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status != null) {
            this.status = status.toUpperCase();
        } else {
            this.status = status;
        }
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        if (confirmStatus != null) {
            this.confirmStatus = confirmStatus.toUpperCase();
        } else {
            this.confirmStatus = confirmStatus;
        }

    }

    public String getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(String emailStatus) {
        if (emailStatus != null) {
            this.emailStatus = emailStatus.toUpperCase();
        } else {
            this.emailStatus = emailStatus;
        }
    }

    public Date getEmailStatusDate() {
        return emailStatusDate;
    }

    public void setEmailStatusDate(Date emailStatusDate) {
        this.emailStatusDate = emailStatusDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNgUserID() {
        return ngUserID;
    }

    public void setNgUserID(String ngUserID) {
        this.ngUserID = ngUserID;
    }

    public String getMwcUID() {
        return mwcUID;
    }

    public void setMwcUID(String mwcUID) {
        this.mwcUID = mwcUID;
    }

    public String getIpAgent() {
        return ipAgent;
    }

    public void setIpAgent(String ipAgent) {
        this.ipAgent = ipAgent;
    }

    public Date getConfirmStatusDate() {
        return confirmStatusDate;
    }

    public void setConfirmStatusDate(Date confirmStatusDate) {
        this.confirmStatusDate = confirmStatusDate;
    }

    public String getLastActivityType() {
        return lastActivityType;
    }

    public void setLastActivityType(String lastActivityType) {
        this.lastActivityType = lastActivityType;
    }

    public Date getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(Date lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayAliasName() {
        return displayAliasName;
    }

    public void setDisplayAliasName(String displayAliasName) {
        this.displayAliasName = displayAliasName;
    }

    public Integer getLocaleID() {
        return localeID;
    }

    public void setLocaleID(Integer localeID) {
        this.localeID = localeID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        if (email != null) {
            return email.toLowerCase().trim();
        } else {
            return email;
        }
    }

    public void setEmail(String email) {
        if (email != null) {
            this.email = email.toLowerCase().trim();
        } else {
            this.email = email;
        }
    }

    public String getAliasName() {
        if (aliasName != null) {
            return aliasName.toLowerCase().trim();
        } else {
            return aliasName;
        }
    }

    public void setAliasName(String aliasName) {
        if (aliasName != null) {
            this.aliasName = aliasName.toLowerCase();
        } else {
            this.aliasName = aliasName;
        }

    }

    public Integer getLastActivityCount() {
        return lastActivityCount;
    }

    public void setLastActivityCount(Integer lastActivityCount) {
        this.lastActivityCount = lastActivityCount;
    }
}
