/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbjohn.csv;

import java.io.Serializable;
import java.util.Date;

public class UserDetails implements Serializable {

    public UserDetails() {

    }

    public UserDetails copy(UserDetails userDetails) {

        UserDetails cpUserDetails = new UserDetails();
        cpUserDetails.setAlternateEmail(userDetails.getAlternateEmail());
        cpUserDetails.setBirthDate(userDetails.getBirthDate());
        cpUserDetails.setBirthDay(userDetails.getBirthDay());
        cpUserDetails.setBirthMonth(userDetails.getBirthMonth());
        cpUserDetails.setBirthYear(userDetails.getBirthYear());
        cpUserDetails.setChildren(userDetails.getChildren());
        cpUserDetails.setCity(userDetails.getCity());
        cpUserDetails.setCountryID(userDetails.getCountryID());
        cpUserDetails.setCreatedBy(userDetails.getCreatedBy());
        cpUserDetails.setCreatedDate(userDetails.getCreatedDate());
        cpUserDetails.setDetailType(userDetails.getDetailType());
        cpUserDetails.setEducationLevel(userDetails.getEducationLevel());
        cpUserDetails.setEnglishSpoken(userDetails.getEnglishSpoken());
        cpUserDetails.setFirstName(userDetails.getFirstName());
        cpUserDetails.setGender(userDetails.getGender());
        cpUserDetails.setHomePhone(userDetails.getHomePhone());
        cpUserDetails.setIncomeLevel(userDetails.getIncomeLevel());
        cpUserDetails.setLastName(userDetails.getLastName());
        cpUserDetails.setMiddleName(userDetails.getMiddleName());
        cpUserDetails.setMobileEmail(userDetails.getMobileEmail());
        cpUserDetails.setModifiedBy(userDetails.getModifiedBy());
        cpUserDetails.setModifiedDate(userDetails.getModifiedDate());
        cpUserDetails.setOptinThirdParty(userDetails.getOptinThirdParty());
        cpUserDetails.setOptinUOL(userDetails.getOptinUOL());
        cpUserDetails.setOriginCountryID(userDetails.getOriginCountryID());
        cpUserDetails.setPrefLanguage(userDetails.getPrefLanguage());
        cpUserDetails.setProvider(userDetails.getProvider());
        cpUserDetails.setRegistrationDate(userDetails.getRegistrationDate());
        cpUserDetails.setSecurityAnswer(userDetails.getSecurityAnswer());
        cpUserDetails.setSecurityQuestion(userDetails.getSecurityQuestion());
        cpUserDetails.setSourceID(userDetails.getSourceID());
        cpUserDetails.setStateCode(userDetails.getStateCode());
        cpUserDetails.setStreet1(userDetails.getStreet1());
        cpUserDetails.setStreet2(userDetails.getStreet2());
        cpUserDetails.setTitle(userDetails.getTitle());
        cpUserDetails.setWorkPhone(userDetails.getWorkPhone());
        cpUserDetails.setZipCode(userDetails.getZipCode());
        cpUserDetails.setSource(userDetails.getSource());
        cpUserDetails.setSourceAppId(userDetails.getSourceAppId());
        cpUserDetails.setSourceDeviceId(userDetails.getSourceDeviceId());
        cpUserDetails.setSourceProcessId(userDetails.getSourceProcessId());
        return cpUserDetails;

    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int userDetailID;

    private int userID;

    private int detailType; //'1=Registration/Newsletter, 2=Shadow';

    private Integer sourceID; //'On registration records (detail_type=1): Null; On shadow records (detail_type=2): Valid source number)';

    private String userType; //'O=Ordinary, A=Administrator, V=Vip, E=Executive';

    private String alternateEmail; //'Lower case only';

    private String mobileEmail; //'Lower case only'

    private String title;

    private String firstName;

    private String lastName;

    private String middleName;

    private String city;

    private String street1;

    private String street2;

    private String stateCode;

    private String zipCode;

    private Integer countryID;

    private Integer originCountryID;

    private String homePhone;

    private String workPhone;

    private Date birthDate;

    private String prefLanguage; //'Used by "¿Qué idioma dominas mejor?": S=Spanish, E=English, B=Both, Null=Never prompted';

    private String gender; // 'Value taken from C2778_Tag_Value (M=Male, F=Female, K=Kid under 13, T=Teen 13-17, A=Adult, over 17, gender unknown)';

    private Integer children; // Amount of children

    private String englishSpoken;

    private Integer provider;

    private String securityQuestion; //'Value taken from C2778_Tag_Value'

    private String securityAnswer;

    private String createdBy;

    private String modifiedBy;

    private Date createdDate;

    private Date modifiedDate;

    private String optinUOL; //'Y=Opted-in, N=Opted-out, Null=Never prompted';

    private String optinThirdParty; //'Y=Opted-in, N=Opted-out, Null=Never prompted';

    private String optinNfl; // NFL Optin - 'Y=Opted-in, N=Opted-out, Null=Never prompted';

    private Integer birthDay;

    private Integer birthMonth;

    private Integer birthYear;

    private Integer incomeLevel;

    private Integer educationLevel;

    private Date registrationDate;

    private String profileImageUrl; // The profile image for the user (For now this is the mipagina image)

    private Integer sourceAppId; //C2814_SOURCE_APP_ID: used for storing mobileApp sourceId (uvideos, noticias, radio etc.)

    private Integer sourceDeviceId; //C2814_SOURCE_DEVICE_ID: used for storing platform sourceId (online, xbox, smartTV etc.)

    private Integer sourceProcessId; //C2814_SOURCE_PROCESS_ID: used for storing registration type sourceId (Univision, Facebook, Twitter etc.)

    private String source; //the site name if from univision, otherwise it's the partner site name

    @Override
    public String toString() {
        StringBuilder userDetailBuffer = new StringBuilder();
        userDetailBuffer.append("firstName ").append(firstName);
        userDetailBuffer.append(" | lastName ").append(lastName);
        userDetailBuffer.append(" | gender ").append(gender);
        userDetailBuffer.append(" | birthDay ").append(birthDay);
        userDetailBuffer.append(" | birthMonth ").append(birthMonth);
        userDetailBuffer.append(" | birthYear ").append(birthYear);
        userDetailBuffer.append(" | birthDate ").append(birthDate);
        userDetailBuffer.append(" | securityQuestion ").append(securityQuestion);
        userDetailBuffer.append(" | securityAnswer ").append(securityAnswer);
        userDetailBuffer.append(" | countryof residence ").append(countryID);
        userDetailBuffer.append(" | country of origin ").append(originCountryID);
        userDetailBuffer.append(" | city ").append(city);
        userDetailBuffer.append(" | state ").append(stateCode);
        userDetailBuffer.append(" | zip ").append(zipCode);
        userDetailBuffer.append(" | incomeLevel ").append(incomeLevel);
        userDetailBuffer.append(" | registrationDate ").append(registrationDate);
        userDetailBuffer.append(" | source ").append(source);
        userDetailBuffer.append(" | sourceId ").append(sourceID);
        userDetailBuffer.append(" | sourceAppId ").append(sourceAppId);
        userDetailBuffer.append(" | sourceDeviceId ").append(sourceDeviceId);
        userDetailBuffer.append(" | sourceProcessId ").append(sourceProcessId);
        return userDetailBuffer.toString();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Integer birthDay) {
        this.birthDay = birthDay;
    }

    public Integer getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(Integer birthMonth) {
        this.birthMonth = birthMonth;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public int getUserDetailID() {
        return userDetailID;
    }

    public void setUserDetailID(int userDetailID) {
        this.userDetailID = userDetailID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getDetailType() {
        return detailType;
    }

    public void setDetailType(int detailType) {
        this.detailType = detailType;
    }

    public Integer getSourceID() {
        return sourceID;
    }

    public void setSourceID(Integer sourceID) {
        this.sourceID = sourceID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
    }

    public String getMobileEmail() {
        return mobileEmail;
    }

    public void setMobileEmail(String mobileEmail) {
        this.mobileEmail = mobileEmail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Integer getIncomeLevel() {
        return incomeLevel;
    }

    public void setIncomeLevel(Integer incomeLevel) {
        this.incomeLevel = incomeLevel;
    }

    public Integer getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(Integer educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        if (street1 != null && street1.length() > 30) {
            this.street1 = street1.substring(0, 30);
        } else {
            this.street1 = street1;
        }
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        if (street2 != null && street2.length() > 30) {
            this.street2 = street2.substring(0, 30);
        } else {
            this.street2 = street2;
        }
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getCountryID() {
        return countryID;
    }

    public void setCountryID(Integer countryID) {
        this.countryID = countryID;
    }

    public Integer getOriginCountryID() {
        return originCountryID;
    }

    public void setOriginCountryID(Integer originCountryID) {
        this.originCountryID = originCountryID;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPrefLanguage() {
        return prefLanguage;
    }

    public void setPrefLanguage(String prefLanguage) {
        this.prefLanguage = prefLanguage;
    }

    public String getGender() {
        if (gender != null) {
            return gender.toUpperCase();
        } else {
            return gender;
        }
    }

    public void setGender(String gender) {
        if (gender != null) {
            this.gender = gender.toUpperCase();
        } else {
            this.gender = gender;
        }
    }

    public Integer getChildren() {
        return children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }

    public String getEnglishSpoken() {
        return englishSpoken;
    }

    public void setEnglishSpoken(String englishSpoken) {
        this.englishSpoken = englishSpoken;
    }

    public Integer getProvider() {
        return provider;
    }

    public void setProvider(Integer provider) {
        this.provider = provider;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
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

    public String getOptinUOL() {
        return optinUOL;
    }

    public void setOptinUOL(String optinUOL) {
        if (optinUOL != null) {
            this.optinUOL = optinUOL.toUpperCase();
        } else {
            this.optinUOL = optinUOL;
        }
    }

    public String getOptinThirdParty() {
        return optinThirdParty;
    }

    public void setOptinThirdParty(String optinThirdParty) {
        if (optinThirdParty != null) {
            this.optinThirdParty = optinThirdParty.toUpperCase();
        } else {
            this.optinThirdParty = optinThirdParty;
        }
    }

    public String getOptinNfl() {
        return optinNfl;
    }

    public void setOptinNfl(String optinNfl) {
        this.optinNfl = optinNfl;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Integer getSourceAppId() {
        return sourceAppId;
    }

    public void setSourceAppId(Integer sourceAppId) {
        this.sourceAppId = sourceAppId;
    }

    public Integer getSourceDeviceId() {
        return sourceDeviceId;
    }

    public void setSourceDeviceId(Integer sourceDeviceId) {
        this.sourceDeviceId = sourceDeviceId;
    }

    public Integer getSourceProcessId() {
        return sourceProcessId;
    }

    public void setSourceProcessId(Integer sourceProcessId) {
        this.sourceProcessId = sourceProcessId;
    }
}
