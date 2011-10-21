#!/bin/bash

# general setup
ROOT="`pwd`"
rm -rf $ROOT
mkdir -p $ROOT

cd $ROOT
mkdir arduino
cd arduino
curl -o arduino-0022.dmg http://arduino.googlecode.com/files/arduino-0022.dmg
hdiutil attach arduino-0022.dmg
cp -R /Volumes/Arduino/Arduino.app/Contents/Resources/Java/hardware .
cp -R /Volumes/Arduino/Arduino.app/Contents/Resources/Java/libraries .
echo `pwd`
rm arduino-0022.dmg
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
hdiutil detach -force /Volumes/Arduino/