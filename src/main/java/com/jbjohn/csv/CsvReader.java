/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbjohn.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

import com.jbjohn.csv.CharacterEncoder.MODE;

/**
 * Created by jbjohn on 5/28/15.
 */

public class CsvReader {

    public void readUserList() {

        String csvFile = "/Users/jbjohn/Downloads/userlist.csv";
        BufferedReader br = null;
        String line;
        String cvsSplitBy = ",";
        ObjectFormatConverter objectFormatConverter = new ObjectFormatConverter();

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] userInfo = line.split(cvsSplitBy);
                //System.out.println(Arrays.toString(userInfo));

                final UserProfile profile = new UserProfile();
                User user = new User();
                UserDetails userDetails = new UserDetails();

                String password = UUID.randomUUID().toString();

                /**
                 * 0  => first name
                 * 1  => last name
                 * 2
                 * 3
                 * 4  => Secondary Address
                 * 5  => Primary Address
                 * 6  => City
                 * 7  => State
                 * 8  => Zip code
                 * 9  => Email
                 * 10
                 * 11
                 * 12 => Cell phone
                 * 13
                 * 14 => DMA
                 * 15
                 * 16
                 * 17 => Email
                 */
                try {
//                    user.setAliasName(userInfo[9]);
//                    user.setDisplayAliasName(userInfo[0] + " " + userInfo[1]);
//                    user.setRegistrationType("S");
//                    user.setStatus("N");
//                    user.setEmail(userInfo[17]);
//                    user.setPassword(password);
//                    user.setLocaleID(1);
//
//                    userDetails.setDetailType(2);
//                    userDetails.setCity(userInfo[6]);
//                    userDetails.setStreet2(userInfo[4]);
//                    userDetails.setCountryID(223);
//                    userDetails.setStateCode(userInfo[7]);
//                    userDetails.setZipCode(userInfo[8]);
//                    userDetails.setFirstName(userInfo[0]);
//                    userDetails.setLastName(userInfo[1]);
//                    profile.setUserDetails(userDetails);
//                    profile.setUser(user);

                    user.setAliasName("test-" + userInfo[9].replaceAll("^\"(.*)\"$", "$1"));
                    user.setDisplayAliasName("test-" + userInfo[1].replaceAll("^\"(.*)\"$", "$1"));
                    user.setRegistrationType("S");
                    user.setStatus("N");
                    user.setEmail("test-" + userInfo[17].replaceAll("^\"(.*)\"$", "$1"));
                    user.setPassword(password);
                    user.setLocaleID(1);

                    userDetails.setDetailType(2);
                    userDetails.setCity(userInfo[6].replaceAll("^\"(.*)\"$", "$1"));
                    userDetails.setStreet2(userInfo[4].replaceAll("^\"(.*)\"$", "$1").replace(" ", ""));
                    userDetails.setCountryID(223);
                    userDetails.setStateCode(userInfo[7].replaceAll("^\"(.*)\"$", "$1"));
                    userDetails.setZipCode(userInfo[8].replaceAll("^\"(.*)\"$", "$1"));
                    userDetails.setFirstName(userInfo[0].replaceAll("^\"(.*)\"$", "$1"));
                    userDetails.setLastName(userInfo[1].replaceAll("^\"(.*)\"$", "$1"));
                    profile.setUserDetails(userDetails);
                    profile.setUser(user);

                    String requestData = objectFormatConverter.objectToJson(profile, MODE.ENCODE_TO_UTF8);
                    System.out.println(requestData);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(e);
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
        System.out.println("Done");
    }
}
