/*
 * Please see the included README.md file for license terms and conditions.
 */


// This file is a suggested starting place for your code.
// It is completely optional and not required.
// Note the reference that includes it in the index.html file.


/*jslint browser:true, devel:true, white:true, vars:true */
/*global $:false, intel:false app:false, dev:false, cordova:false */



// This file contains your event handlers, the center of your application.
// NOTE: see app.initEvents() in init-app.js for event handler initialization code.


function myEventHandler() {
    "use strict";

    var ua = navigator.userAgent;
    var str;

    if (window.Cordova && dev.isDeviceReady.c_cordova_ready__) {
        str = "It worked! Cordova device ready detected at " + dev.isDeviceReady.c_cordova_ready__ + " milliseconds!";
    } else if (window.intel && intel.xdk && dev.isDeviceReady.d_xdk_ready______) {
        str = "It worked! Intel XDK device ready detected at " + dev.isDeviceReady.d_xdk_ready______ + " milliseconds!";
    } else {
        str = "Bad device ready, or none available because we're running in a browser.";
    }

    console.log(str);
}


// ...additional event handlers here...

function thirdPartyEmulator() {
    alert("This feature uses a third party barcode scanner plugin. Third party plugins are not supported on emulator or app preview. Please build app to test.");
}

function scan() {
    "use strict";
    var fName = "scan():";
    console.log(fName, "entry");
    try {
        if (window.tinyHippos) {
            thirdPartyEmulator();
            console.log(fName, "emulator alert");
        } else {
            cordova.plugins.barcodeScanner.scan(
                function (result) {
                    var code = result.text;
                    var url = "https://apex.oracle.com/pls/apex/m2m.safedropapi.verify?tracker_number=" + code;
                    console.log(code, " and ", url);
                    cordovaHTTP.get(url, {}, {}, function(response) {
                        response.data = JSON.parse(response.data);
                        console.log(response.data.stringify(), " and ", response.status);
                        if (response.data.status == 'success') {
                            green();
                            setTimeout(grey, 10000);
                        } else {
                            red();
                            setTimeout(grey, 10000);
                        }
                    }, function(response) {
                        console.error(response);
                        red();
                        setTimeout(grey, 10000);
                    });
                    
                    
                    
//                        console.log(fName, "Scanned result found!");
//                        alert("We got a barcode!\n" +
//                        "Result: " + result.text + "\n" +
//                        "Format: " + result.format + "\n" +
//                        "Cancelled: " + result.cancelled + "\n\n");
                },
                function (error) {
                    red();
                }
            );
        }
    } catch (e) {
        console.log(fName, "catch, failure");
    }

    console.log(fName, "exit");
}

function green() {
    var button = document.getElementById( 'id_btnScan' );
    button.style.backgroundColor = 'green';
    button.style.color = 'rgb(238,238,238)';
    button.innerHTML = "Open!";
    httpOpen();
}

function grey() {
    var button = document.getElementById( 'id_btnScan' );
    button.style.backgroundColor = 'rgb(224,224,224)';
    button.style.color = 'rgb(84,84,84)';
    button.innerHTML = "Scan";
    httpClose();
}

function red() {
    var button = document.getElementById( 'id_btnScan' );
    button.style.backgroundColor = 'red';
    button.style.color = 'rgb(238,238,238)';
    button.innerHTML = "Error!";
}

function httpOpen() {
    cordovaHTTP.get("http://192.168.1.185:8086/unlock", {}, {}, function(response) {
        console.log(response.status);
    }, function(response) {
        console.error(response.error);
    });
}

function httpClose() {
    cordovaHTTP.get("http://192.168.1.185:8086/lock", {}, {}, function(response) {
        console.log(response.status);
    }, function(response) {
        console.error(response.error);
    });
}

//function mqttConnect() {
//    console.log("mqtt", "calling");
//    cordova.plugins.CordovaMqTTPlugin.connect({
//          port:18149,
//          url:"m11.cloudmqtt.com",
//          success:function(s){
//              console.log("mqtt", "connected");
//          },
//          error:function(e){
//              console.log("mqtt", "error");
//          }
//    })
//}
//
//function mqttPublish() {
//    cordova.plugins.CordovaMqTTPlugin.publish({
//    url:"m11.cloudmqtt.com",
//    topic:"safelock",
//    secure:false,
//    qos:"2",
//    clientId:"scannerapp",
//    portNo:"18149",
//    message:"open",
//    cleanSession:true,
//    username:"kqcagdjr",
//    password:"hKwYtJYpCzLt",
//    debug:false,
//    success:function(data){
//        alert(data);
//        console.log("mqtt published");
//    },
//    error:function(data){
//         alert(data);
//        console.log("mqtt not published");
//    }
//});
//}
