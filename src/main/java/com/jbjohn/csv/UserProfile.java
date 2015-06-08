/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbjohn.csv;

import java.util.List;

/**
 * This class encapsulates user, userdetails and userprefchoice
 *
 */
public class UserProfile {

    private User user; // Corresponds to user model

    private UserDetails userDetails; // corresponds to userdetails model.

    private int localeId;

    private boolean emailUniqueCheckRequired; // flag indicating whether email has been altered during account maintenance

    private boolean aliasNameUniqueCheckRequired; //false by default, enabled to true for account maintenance

    private String partnerCode;

    private boolean aliasNameValidationRequired; //false by default,flag indicating whether aliasname validation  is required or not

    private boolean passwordValidationRequired; //false by default,flag indicating whether password validation  is required or not

    private SocialNetworkPartnerInfo socialNetworkPartnerInfo;

    public SocialNetworkPartnerInfo getSocialNetworkPartnerInfo() {
        return socialNetworkPartnerInfo;
    }

    public void setSocialNetworkPartnerInfo(
            SocialNetworkPartnerInfo socialNetworkPartnerInfo) {
        this.socialNetworkPartnerInfo = socialNetworkPartnerInfo;
    }

    public boolean isAgeValidationRequired() {
        return ageValidationRequired;
    }

    public void setAgeValidationRequired(boolean ageValidationRequired) {
        this.ageValidationRequired = ageValidationRequired;
    }

    public boolean isGenderValidationRequired() {
        return genderValidationRequired;
    }

    public void setGenderValidationRequired(boolean genderValidationRequired) {
        this.genderValidationRequired = genderValidationRequired;
    }

    private boolean ageValidationRequired; //false by default, flag indication whether  age validation is required or not

    private boolean genderValidationRequired; //false by default, flag indication whether  gender validation is required or not

    public boolean isPasswordValidationRequired() {
        return passwordValidationRequired;
    }

    public void setPasswordValidationRequired(boolean passwordValidationRequired) {
        this.passwordValidationRequired = passwordValidationRequired;
    }

    private boolean securityAnswerValidationRequired; //false by default,flag indicating whether security Answer and validation is required

    private List<UserPrefChoice> userPrefChoiceList;

    private List<UserPrefChoice> prevUserPrefChoiceList;

    private boolean userPrefChoiceUpdateRequired; //false by default,flag indicating whether userprefchoice update is required or not

    private boolean zipCodeStateValidationRequired = true; // true by default, flag indicating whether zipcode+state combination validation required or not

    private boolean isMobileUser = false;
    /* added for mobile user registrations, because we will be recieving 2 letter country codes rather than numberical CRM countryIds
     * and we need to map those to numberical values
     */
    private String countryOfResidence = "";

    public UserProfile() {
    }

    public UserProfile(User user, UserDetails userDetails) {
        this.user = user;
        this.userDetails = userDetails;
    }

    private String prefID;

    private String prefChoiceIDs;

    public String getPrefID() {
        return prefID;
    }

    public void setPrefID(String prefID) {
        this.prefID = prefID;
    }

    public String getPrefChoiceIDs() {
        return prefChoiceIDs;
    }

    public void setPrefChoiceIDs(String prefChoiceIDs) {
        this.prefChoiceIDs = prefChoiceIDs;
    }

    public boolean isUserPrefChoiceUpdateRequired() {
        return userPrefChoiceUpdateRequired;
    }

    public void setUserPrefChoiceUpdateRequired(boolean userPrefChoiceUpdateRequired) {
        this.userPrefChoiceUpdateRequired = userPrefChoiceUpdateRequired;
    }

    public List<UserPrefChoice> getUserPrefChoiceList() {
        return userPrefChoiceList;
    }

    public void setUserPrefChoiceList(List<UserPrefChoice> userPrefChoiceList) {
        this.userPrefChoiceList = userPrefChoiceList;
    }

    public List<UserPrefChoice> getPrevUserPrefChoiceList() {
        return prevUserPrefChoiceList;
    }

    public void setPrevUserPrefChoiceList(List<UserPrefChoice> prevUserPrefChoiceList) {
        this.prevUserPrefChoiceList = prevUserPrefChoiceList;
    }

    public boolean isSecurityAnswerValidationRequired() {
        return securityAnswerValidationRequired;
    }

    public void setSecurityAnswerValidationRequired(
            boolean securityAnswerValidationRequired) {
        this.securityAnswerValidationRequired = securityAnswerValidationRequired;
    }

    public boolean isAliasNameUniqueCheckRequired() {
        return aliasNameUniqueCheckRequired;
    }

    public void setAliasNameUniqueCheckRequired(boolean aliasNameUniqueCheckRequired) {
        this.aliasNameUniqueCheckRequired = aliasNameUniqueCheckRequired;
    }

    public boolean isAliasNameValidationRequired() {
        return aliasNameValidationRequired;
    }

    public void setAliasNameValidationRequired(boolean aliasNameValidationRequired) {
        this.aliasNameValidationRequired = aliasNameValidationRequired;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public boolean isMobileUser() {
        return isMobileUser;
    }

    public void setMobileUser(boolean isMobileUser) {
        this.isMobileUser = isMobileUser;
    }

    public boolean isEmailUniqueCheckRequired() {
        return emailUniqueCheckRequired;
    }

    public void setEmailUniqueCheckRequired(boolean emailUniqueCheckRequired) {
        this.emailUniqueCheckRequired = emailUniqueCheckRequired;
    }

    public int getLocaleId() {
        return localeId;
    }

    public void setLocaleId(int localeId) {
        this.localeId = localeId;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public User getUser() {
        return user;
    }

    public boolean isZipCodeStateValidationRequired() {
        return zipCodeStateValidationRequired;
    }

    public void setZipCodeStateValidationRequired(
            boolean zipCodeStateValidationRequired) {
        this.zipCodeStateValidationRequired = zipCodeStateValidationRequired;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserProfile [user=" + user + ", userDetails=" + userDetails +
                ", localeId=" + localeId + ", emailUniqueCheckRequired=" +
                emailUniqueCheckRequired + ", aliasNameUniqueCheckRequired=" +
                aliasNameUniqueCheckRequired + ", partnerCode=" + partnerCode +
                ", aliasNameValidationRequired=" +
                aliasNameValidationRequired + ", passwordValidationRequired=" +
                passwordValidationRequired +
                ", securityAnswerValidationRequired=" +
                securityAnswerValidationRequired + ", userPrefChoiceList=" +
                userPrefChoiceList + ", prevUserPrefChoiceList=" +
                prevUserPrefChoiceList + ", userPrefChoiceUpdateRequired=" +
                userPrefChoiceUpdateRequired +
                ", zipCodeStateValidationRequired=" +
                zipCodeStateValidationRequired + ", prefID=" + prefID +
                ", prefChoiceIDs=" + prefChoiceIDs + "SocialNetworkPartnerInfo:" + socialNetworkPartnerInfo + "]";
    }

}
