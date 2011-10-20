#!/bin/bash

# general setup
ROOT="`pwd`"
rm -rf $ROOT
mkdir -p $ROOT

cd $ROOT
curl -o arduino-0022.zip http://arduino.googlecode.com/files/arduino-0022.zip
unzip arduino-0022.zip
echo `pwd`
rm arduino-0022.zip
cd arduino-0022
rm -Rf java
rm -Rf lib
rm -Rf hardware/tools/avr/share
rm -Rf hardware/tools/avr/manual
rm -Rf hardware/tools/avr/man
rm -Rf hardware/tools/avr/info
rm -Rf hardware/tools/avr/avr-3
rm -Rf hardware/tools/avr/doc
rm -Rf hardware/tools/avr/libexec/gcc/avr/3.4.6
rm -Rf hardware/tools/avr/lib/gcc/avr/3.4.6
rm -rf hardware/tools/avr/etc/options
