#!/bin/bash

# general setup
ROOT="`pwd`/avr_build"
rm -rf $ROOT
mkdir -p $ROOT

BINARY="$ROOT/../binary"
mkdir -p $BINARY

export PATH=$PATH:$BINARY

# binutils-2.19
cd $ROOT
mkdir binutils
cd binutils
wget ftp://ftp.gnu.org/gnu/binutils/binutils-2.19.1a.tar.bz2
tar jxvf binutils-2.19.1a.tar.bz2
cd binutils-2.19.1
mkdir build
cd build
export CC=gcc
../configure --target=avr --prefix=$BINARY --disable-nsl --enable-install-libbfd --disable-werror --disable-shared
make
make install


# gcc-core-4.2.3
cd $ROOT
mkdir gcc-core
cd gcc-core
wget ftp://ftp.gnu.org/gnu/gcc/gcc-4.3.2/gcc-core-4.3.2.tar.gz
tar xvzf gcc-core-4.3.2.tar.gz
wget ftp://ftp.gnu.org/gnu/gcc/gcc-4.3.2/gcc-g++-4.3.2.tar.gz
tar xvzf gcc-g++-4.3.2.tar.gz
cd gcc-4.3.2
mkdir build
cd build
../configure --target=avr --prefix=$BINARY --disable-nsl --enable-languages=c,c++ --disable-libssp --disable-shared 
make
make install

# avr-libc-1.6.4
cd $ROOT
mkdir avr-libc
cd avr-libc
wget http://download.savannah.gnu.org/releases/avr-libc/avr-libc-1.6.4.tar.bz2
tar xvjf avr-libc-1.6.4.tar.bz2
cd avr-libc-1.6.4/
mkdir build
cd build
export CC=$BINARY/bin/avr-gcc
../configure --build=`../config.guess` --host=avr --prefix=$BINARY --enable-languages=c,c++ --disable-shared
make
make install

# arduino 0022
cd $ROOT
mkdir arduino
cd arduino
wget http://arduino.googlecode.com/files/arduino-0022.tgz
tar xvzf arduino-0022.tgz
