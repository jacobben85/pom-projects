/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbjohn.csv;

/**
 * @author vsankar
 *
 */
public class SocialNetworkPartnerInfo {

    private String signature;
    private String UIDSig;
    private String timestamp;
    private String UIDSignature;
    private long signatureTimestamp;
    private String UID;
    private String photoURL;
    private String thumbnailURL;
    private String profileURL;
    private boolean isSiteUID;
    private boolean isLoggedIn;
    private boolean isConnected;
    private boolean isSiteUser;
    private String loginProvider;
    private String loginProviderUID;
    private boolean isTempUser;
    private String country;
    private String state;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUIDSig() {
        return UIDSig;
    }

    public void setUIDSig(String uIDSig) {
        UIDSig = uIDSig;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUIDSignature() {
        return UIDSignature;
    }

    public void setUIDSignature(String uIDSignature) {
        UIDSignature = uIDSignature;
    }

    public long getSignatureTimestamp() {
        return signatureTimestamp;
    }

    public void setSignatureTimestamp(long signatureTimestamp) {
        this.signatureTimestamp = signatureTimestamp;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String uID) {
        UID = uID;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public boolean isSiteUID() {
        return isSiteUID;
    }

    public void setSiteUID(boolean isSiteUID) {
        this.isSiteUID = isSiteUID;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isSiteUser() {
        return isSiteUser;
    }

    public void setSiteUser(boolean isSiteUser) {
        this.isSiteUser = isSiteUser;
    }

    public String getLoginProvider() {
        return loginProvider;
    }

    public void setLoginProvider(String loginProvider) {
        this.loginProvider = loginProvider;
    }

    public String getLoginProviderUID() {
        return loginProviderUID;
    }

    public void setLoginProviderUID(String loginProviderUID) {
        this.loginProviderUID = loginProviderUID;
    }

    public boolean isTempUser() {
        return isTempUser;
    }

    public void setTempUser(boolean isTempUser) {
        this.isTempUser = isTempUser;
    }

    @Override
    public String toString() {
        return "SocialNetworkPartnerInfo [signature=" + signature + ", UIDSig=" +
                UIDSig + ", timestamp=" + timestamp + ", UIDSignature=" +
                UIDSignature + ", signatureTimestamp=" + signatureTimestamp +
                ", UID=" + UID + ", photoURL=" + photoURL + ", thumbnailURL=" +
                thumbnailURL + ", profileURL=" + profileURL + ", isSiteUID=" +
                isSiteUID + ", isLoggedIn=" + isLoggedIn + ", isConnected=" +
                isConnected + ", isSiteUser=" + isSiteUser +
                ", loginProvider=" + loginProvider + ", loginProviderUID=" +
                loginProviderUID + ", isTempUser=" + isTempUser +
                ", country=" + country + ", state=" + state + "]";
    }

}
