#!/bin/bash
set -m
echo ""
echo "-----------------------------[ Building Arduino nix32 ]-----------------------------"
echo ""

# general setup
ROOT="`pwd`/avr_build"
rm -rf $ROOT
mkdir -p $ROOT

#echo $ROOT
BINARY="$ROOT/../binary"
mkdir -p $BINARY


# add /usr/local/avr/bin to PATH, by adding this line to ~/.bashrc
export PATH=$PATH:$BINARY
# you should also do this for the root user, since using sudo can
# reset your PATH to root's default PATH
#source ~/.bashrc

export loginfo=" >> messages.log 2>&1"


cd $ROOT
mkdir arduino
cd arduino
echo -n "Downloading arduino-1.0"
wget http://arduino.googlecode.com/files/arduino-1.0.1-linux.tgz
if  [ -f "arduino-1.0.1-linux.tgz" ]; then
echo "[OK]"
else
echo "[FAIL]"
exit -1
fi
tar xzf arduino-1.0.1-linux.tgz
