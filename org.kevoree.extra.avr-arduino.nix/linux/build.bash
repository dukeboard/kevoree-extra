#!/bin/bash

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



# install newer versions of MPFR and GMP dev packages
# required to build newer versions of gcc (ubuntu packages)
# also should pull in 4.1 versions of gcc
# The texinfo package is used while building binutils.
#sudo apt-get install libgmp3-dev libmpfr-dev texinfo

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
# to test binutils installation:
avr-as --help


# gcc-core-4.2.3
cd $ROOT
mkdir gcc-core
cd gcc-core
wget ftp://ftp.gnu.org/gnu/gcc/gcc-4.3.2/gcc-core-4.3.2.tar.gz
tar xvzf gcc-core-4.3.2.tar.gz
#cd gcc-4.3.2
#mkdir build
#cd build
#../configure --target=avr --prefix=/tmp/avr --disable-nsl --enable-languages=c,c++ --disable-libssp --disable-shared
#make
# sometimes there are PATH issues with "sudo make install",
# so you might try "sudo su" and then "make install".
# be sure to 'exit' from the root account before continuing!
#make install
# to test:
#avr-gcc --version


# gcc-g++-4.2.3
#cd $ROOT
#mkdir gcc-core
#cd gcc-g++
wget ftp://ftp.gnu.org/gnu/gcc/gcc-4.3.2/gcc-g++-4.3.2.tar.gz
tar xvzf gcc-g++-4.3.2.tar.gz
cd gcc-4.3.2
mkdir build
cd build
../configure --target=avr --prefix=$BINARY --disable-nsl --enable-languages=c,c++ --disable-libssp --disable-shared 
make
# sometimes there are PATH issues with "sudo make install",
# so you might try "sudo su" and then "make install".
# be sure to 'exit' from the root account before continuing!
make install
# to test:
avr-gcc --version
avr-g++ --version

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
# again, it might work best to "sudo su" then "make install"
# be sure to 'exit' from the root account before continuing!
make install

# avrdude-5.8
#sudo apt-get install flex byacc libusb-dev
#cd $ROOT
#mkdir avrdude
#cd avrdude
#wget http://nongnu.askapache.com/avrdude/avrdude-5.8.tar.gz
#tar xvzf avrdude-5.8.tar.gz
#cd avrdude-5.8
#export CC=gcc
#./configure --prefix=$BINARY --disable-shared
#make
#make install
#export CC=
# TODO add udev magic to fix usb permissions for non-root user using avrdude.


cd $ROOT
mkdir arduino
cd arduino
wget http://arduino.googlecode.com/files/arduino-0022.tgz
tar xvzf arduino-0022.tgz
