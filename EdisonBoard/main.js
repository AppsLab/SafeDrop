/*jslint node:true, vars:true, bitwise:true, unparam:true */
/*jshint unused:true */
// Leave the above lines for propper jshinting
//Type Node.js Here :)
var mraa = require('mraa'); //require mraa
console.log('MRAA Version: ' + mraa.getVersion()); //write the mraa version to the Intel XDK console

var servoModule = require("jsupm_servo");
var upm_grove = require("jsupm_grove"); 

//Instantiate ES08A Servo module on GPIO 5 and grove rotary 

var servo = new servoModule.ES08A(5);
var groveRotary = new upm_grove.GroveRotary(0);
var led7 = new mraa.Gpio(7); 
led7.dir(mraa.DIR_OUT);


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
        
        var resistance = (1023 - a) * 10000 / a; //get the resistance of the sensor;
        // console.log("resistence: " + resistance);
        
        // socket.emit("message", "opened");
        // socket.emit("message", "closed");
        
    }, 3000);
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
  mqttclient.subscribe('safelock', function() {
    console.log("mqtt subscribed");      
  });
});



mqttclient.on('error', function(err){
  console.log("failed to connect to MQTT broker:" + err);
});

mqttclient.on('message', function(topic, message, packet) {
        console.log("Received '" + message + "' on '" + topic + "'");
        if( message == 'open' ) {
            turnSafeServo(180);
            console.log( "open safe" );
        }
        else if( message == 'close' ) {
            turnSafeServo(20);
            console.log( "close safe" );
        }
});


function turnSafeServo(degrees)
{
    servo.setAngle(Math.round(degrees * servoRange/knobRange));        
    console.log("Servo: " + degrees);

    // degree 180 - open;  degree 20 - close
    if( degrees > 70 ) 
    {
        led7.write(1);                
    }
    else 
    {
        led7.write(0);
    }
    
}

// start immediately, run every 5000  miliseconds.
// startServo(0, 5000);

startLightSensorWatch();


//Create Socket.io server
var http = require('http');
var app = http.createServer(function (req, res) {
    'use strict';
    res.writeHead(200, {'Content-Type': 'text/plain'});
    res.end('<h1>Hello world from Intel IoT platform!</h1>');
}).listen(1337);

var io = require('socket.io')(app);

//Attach a 'connection' event handler to the server
io.on('connection', function (socket) {
    'use strict';
    console.log('a user connected');
    //Emits an event along with a message
    socket.emit('connected', 'Welcome');

    //Start watching Sensors connected to Galileo board
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