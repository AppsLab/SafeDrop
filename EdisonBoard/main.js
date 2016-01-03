/*jslint node:true, vars:true, bitwise:true, unparam:true */
/*jshint unused:true */
// Leave the above lines for propper jshinting
//Type Node.js Here :)

var childProcess = require('child_process');
var express = require('express');
var http = require('http');

var mraa = require('mraa'); //require mraa
console.log('MRAA Version: ' + mraa.getVersion()); //write the mraa version to the Intel XDK console

var servoModule = require("jsupm_servo");
var upm_grove = require("jsupm_grove"); 

//Instantiate ES08A Servo module on GPIO 5 and grove rotary 

var servo = new servoModule.ES08A(5);
var groveRotary = new upm_grove.GroveRotary(0);
var led7 = new mraa.Gpio(7); 
led7.dir(mraa.DIR_OUT);

var buzzer3 = new mraa.Pwm(3);

//set the period in microseconds.
// buzzer3.period_us(2000);
var isOpenned = false;
buzzer3.enable(false);

function buzz() {

    buzzer3.write(0.03);
    console.log("buzz sounded");
    
    if( isOpenned ) {
        setTimeout(buzz, 2000);
    }
}


var isRecording = false;

//GROVE Kit A0 Connector --> Aio(0)
var lightA0 = new mraa.Aio(1);

/*
Function: startLightSensorWatch(socket)
Parameters: socket - client communication channel
Description: Read Temperature Sensor and send temperature in degrees of Fahrenheit every 1 seconds
*/
function startLightSensorWatch() {
    'use strict';
    setInterval(function () {
        var a = lightA0.read();
        console.log("Light: " + a);
        
        // the box is open, record video now
        if( a > 200 && isRecording == false && isOpenned == true ) {
            recordVideo();   
        }

        // var resistance = (1023 - a) * 10000 / a; //get the resistance of the sensor;
        // console.log("resistence: " + resistance);
        
        // socket.emit("message", "opened");
        // socket.emit("message", "closed");
        
    }, 2000);
}


var servoRange = 160;
var knobRange = 300;

// function to initialize servo

// timeOffset: how long after hitting "run"

// should we start this servo instance

// timeInterval: how frequently should this instance run after timeOffset

// angle: the  angle for this instance


function startServo(timeOffset, timeInterval)

{

    // Start running this instance after timeOffset milliseconds

    setTimeout(function()

    {

        // run this instance every timeInterval milliseconds

        setInterval(function()

        {
            var degrees = groveRotary.abs_deg();// Get absolute raw radians from AIO pin 
            turnSafeServo(degrees);
        }, timeInterval);

    }, timeOffset);

// timeOffset tells setTimeout when to execute the function specified in the first param

}

var mqtt    = require('mqtt');
var options = {
  port: 18149,
  host: 'm11.cloudmqtt.com',
  clientId: 'edisonserver',
  username: 'kqcagdjr',
  password: 'hKwYtJYpCzLt',
  keepalive: 120,
  reconnectPeriod: 10000,
  protocolId: 'MQIsdp',
  protocolVersion: 3,
  clean: true,
  encoding: 'utf8'
};
var mqttclient = mqtt.connect(options);

mqttclient.on('connect', function() { // When connected
  console.log("mqtt client connected");
  // subscribe to a topic
  /*
  mqttclient.subscribe('safelock', function() {
    console.log("mqtt subscribed");      
  });
  */
});



mqttclient.on('error', function(err){
  console.log("failed to connect to MQTT broker:" + err);
});

mqttclient.on('message', function(topic, message, packet) {
        console.log("Received '" + message + "' on '" + topic + "'");
        /*
        if( message == 'open' ) {
            turnSafeServo(180);
            console.log( "open safe" );
        }
        else if( message == 'close' ) {
            turnSafeServo(20);
            console.log( "close safe" );
        }
        */
});

var hasPublished = false;
function turnSafeServo(degrees)
{
    servo.setAngle(Math.round(degrees * servoRange/knobRange));        
    console.log("Servo: " + degrees);

    // degree 180 - open;  degree 20 - close
    if( degrees > 70 ) 
    {
        led7.write(1);
        isOpenned = true;
        buzzer3.enable(true);
        
        buzz();
        
        if( hasPublished == false ) {
            mqttclient.publish('safelock', 'open');
            console.log("mqtt publish open to safelock");
            hasPublished = true;
        }
    }
    else 
    {
        led7.write(0);
        isOpenned = false;   
        buzzer3.enable(false);

        mqttclient.publish('safelock', 'close');
        console.log("mqtt publish close to safelock");
        hasPublished = false;
    }
}

function recordVideo() 
{
    console.log("recording video");
    isRecording = true;
    
    childProcess.exec('/home/root/edi-cam/bin/record.sh', function (error, stdout, stderr) {
        if (error) {
            console.log(error.stack);
            console.log('error code: '+error.code);
            console.log('error signal: '+error.signal);
        }
        console.log('STDOUT: '+stdout);
    });
    
    setTimeout( function(){isRecording=false}, 20000);
}

// start immediately, run every 5000  miliseconds.
// startServo(0, 5000);

startLightSensorWatch();


// app parameters                                                               
var app = express();                                                            
app.set('port', 8086);

/*
var httpServer = http.createServer(function (req, res) {
    'use strict';
    res.writeHead(200, { 'Content-Type': 'text/plain' });
    res.end('<h1>Hello world from Intel IoT platform!</h1>');
}).listen(app.get('port'));
*/

var httpServer = http.createServer(app).listen(app.get('port'), function () {
  console.log('HTTP server listening on port ' + app.get('port'));
});

// To open safedrop by direct call
app.get("/lock", function (req, res) {
    console.log("http lock call");
    turnSafeServo(20);
    console.log( "close safe" );
    res.end('locked');
});
        
app.get("/unlock", function (req, res) {
    console.log("http unlock call");
    turnSafeServo(180);
    console.log( "open safe" );
    res.end('unlocked');
});
        
//Create Socket.io server        
var io = require('socket.io')(httpServer);

//Attach a 'connection' event handler to the server
io.on('connection', function (socket) {
    'use strict';
    console.log('a user connected');
    //Emits an event along with a message
    socket.emit('connected', 'Welcome');

    // startLightSensorWatch(socket);

    //Attach a 'disconnect' event handler to the socket
    socket.on('disconnect', function () {
        console.log('user disconnected');
    });
});






// Print message when exiting

process.on('SIGINT', function()

{

    console.log("Exiting...");

    process.exit(0);

});