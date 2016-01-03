package com.appslab.oracle.atthackthon;

import android.test.AndroidTestCase;

import com.appslab.oracle.HttpConnect;

import org.junit.Test;

/**
 * Created by Osvaldo Villagrana on 12/23/15.
 */
public class NetworkTest extends AndroidTestCase {

    @Test
    public void networkTest() {
        HttpConnect connection = HttpConnect.getInstance();
        connection.request("https://apex.oracle.com/pls/apex/oratweet/smartoffice/selected", null, null);
        //http://jsonplaceholder.typicode.com/posts
        //http://jsonplaceholder.typicode.com/
        //http://www.jsonschema2pojo.org/
    }
}
