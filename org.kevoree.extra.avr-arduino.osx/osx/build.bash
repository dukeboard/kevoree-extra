#!/bin/bash

# general setup
ROOT="`pwd`"
rm -rf $ROOT
mkdir -p $ROOT

cd $ROOT
mkdir arduino
cd arduino
curl -o arduino-1.0.zip http://arduino.googlecode.com/files/arduino-1.0-macosx.zip
unzip arduino-1.0.zip
cp -R Arduino.app/Contents/Resources/Java/hardware .
cp -R Arduino.app/Contents/Resources/Java/libraries .
echo `pwd`
rm arduino-1.0.zip
rm -Rf Arduino.app
rm -Rf hardware/__MACOSX
rm -Rf firmwares
rm -Rf bootloaders
rm -Rf hardware/tools/avr/share
rm -Rf hardware/tools/avr/manual
rm -Rf hardware/tools/avr/man
rm -Rf hardware/tools/avr/info
rm -Rf hardware/tools/avr/avr-3
rm -Rf hardware/tools/avr/doc
rm -Rf hardware/tools/avr/libexec/gcc/avr/3.4.6
rm -Rf hardware/tools/avr/lib/gcc/avr/3.4.6
rm -rf hardware/tools/avr/etc/options