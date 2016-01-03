Servrotary
============================
A simple node.js application that demonstrates when rotary angle sensor is being moved it will make servo motor rotate.

Servo is absolutely a fun motion control device, it can be located at any position between 0 to 180 degrees. With 4 shafts in different shapes, this servo is ready to drive a little fan, lift an object, or mimic a clock hand.
Rotary angle sensor is a 10Kohm linear rotary potentiometer. It has 300 degrees active range. Also designed as a HID device, “panel mount” feature is added.

First, you need to verify the mapping between these two sensors because they have different angles limit, which will help to make sure when Rotary angle sensor moves, the servo rotates. 

```javascript
var degrees = groveRotary.abs_deg();// Get absolute raw radians from AIO pin 
servo.setAngle(Math.round(degrees * servoRange/knobRange));
console.log("Set angle to " + degrees);
```
Intel(R) XDK IoT Edition
-------------------------------------------
This template is part of the Intel(R) XDK IoT Edition. 
Download the Intel(R) XDK IoT Edition at https://software.intel.com/en-us/html5/xdk-iot. To see the technical details of the sample, 
please visit the sample article page at https://software.intel.com/en-us/creating-an-app-that-rotates-a-servo-motor.


Important App Files
---------------------------
* main.js
* package.json
* icon.png
* README.md

License Information Follows
---------------------------
Copyright (c) 2014, Intel Corporation. All rights reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

- Redistributions of source code must retain the above copyright notice, 
  this list of conditions and the following disclaimer.

- Redistributions in binary form must reproduce the above copyright notice, 
  this list of conditions and the following disclaimer in the documentation 
  and/or other materials provided with the distribution.

- Neither the name of Intel Corporation nor the names of its contributors 
  may be used to endorse or promote products derived from this software 
  without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT 
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
