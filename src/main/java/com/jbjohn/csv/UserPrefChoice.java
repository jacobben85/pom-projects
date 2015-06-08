/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbjohn.csv;

/**
 * model for UserPrefChoice
 *
 */
public class UserPrefChoice {

    private int userPrefChoiceId;

    private int prefId;

    private int prefChoiceId;

    public int getUserPrefChoiceId() {
        return userPrefChoiceId;
    }

    public void setUserPrefChoiceId(int userPrefChoiceId) {
        this.userPrefChoiceId = userPrefChoiceId;
    }

    public int getPrefId() {
        return prefId;
    }

    public void setPrefId(int prefId) {
        this.prefId = prefId;
    }

    public int getPrefChoiceId() {
        return prefChoiceId;
    }

    public void setPrefChoiceId(int prefChoiceId) {
        this.prefChoiceId = prefChoiceId;
    }

    @Override
    public String toString() {
        return "UserPrefChoice [userPrefChoiceId=" + userPrefChoiceId + ",prefId=" + prefId + ",prefChoiceId=" + prefChoiceId + "]";
    }
}
