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
                    xhttp.onreadystatechange = function() {
                        if (xhttp.readyState == 4 && xhttp.status == 200) {
//                          document.getElementById("demo").innerHTML = xhttp.responseText;
                            if (xhttp.responseText == 'valid') {
                                green();
                                setTimeout(grey, 10000);
                            } else {
                                red();
                                setTimeout(grey, 10000);
                            }
                        } else {
                            red();
                            setTimeout(grey, 10000);
                        }
                    };
                    var url = "http://.../valid?tracking_number=" + result.text; 
                    xhttp.open("GET", url, true);
                    xhttp.send();
                    
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
    
    
}

function grey() {
    var button = document.getElementById( 'id_btnScan' );
    button.style.backgroundColor = 'rgb(224,224,224)';
    button.style.color = 'rgb(84,84,84)';
    button.innerHTML = "Scan";
}

function red() {
    var button = document.getElementById( 'id_btnScan' );
    button.style.backgroundColor = 'red';
    button.style.color = 'rgb(238,238,238)';
    button.style.button.innerHTML = "Error!";
}

