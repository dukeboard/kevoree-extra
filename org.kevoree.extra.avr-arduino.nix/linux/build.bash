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

# install newer versions of MPFR and GMP dev packages
# required to build newer versions of gcc (ubuntu packages)
# also should pull in 4.1 versions of gcc
# The texinfo package is used while building binutils.
#sudo apt-get install libgmp3-dev libmpfr-dev texinfo
echo "INIT" > messages.log
# binutils-2.19
cd $ROOT
mkdir binutils
cd binutils
echo -n "Downloading binutils-2.19 "
wget --quiet ftp://ftp.gnu.org/gnu/binutils/binutils-2.19.1a.tar.bz2
if  [ -f "binutils-2.19.1a.tar.bz2" ]; then
echo "[OK]"
else
echo "[FAIL]"
exit -1
fi
echo -n "Decompression of binutils-2.19 "
tar jxf binutils-2.19.1a.tar.bz2
if  [ -d "binutils-2.19.1" ]; then
echo "[OK]"
else
echo "[FAIL]"
exit -1
fi

echo "Build binutils-2.19 "
cd binutils-2.19.1
mkdir build
cd build
export CC=gcc
../configure --target=avr --prefix=$BINARY --disable-nsl --enable-install-libbfd --disable-werror --disable-shared --quiet
make >> messages.log 2>&1
echo '0
0' | make install >> messages.log
if [ "$?" != "0" ]; then
	exit -1
else
echo "[OK]"
fi


# to test binutils installation:
#avr-as --help


# gcc-core-4.2.3
cd $ROOT
mkdir gcc-core
cd gcc-core
echo -n "Downloading gcc-core-4.2.3 "
wget --quiet ftp://ftp.gnu.org/gnu/gcc/gcc-4.3.2/gcc-core-4.3.2.tar.gz
if  [ -f "gcc-core-4.3.2.tar.gz" ]; then
echo "[OK]"
else
echo "[FAIL]"
exit -1
fi
echo -n "Decompression of gcc-core-4.2.3 "
tar xzf gcc-core-4.3.2.tar.gz
if  [ -d "gcc-4.3.2" ]; then
echo "[OK]"
else
echo "[FAIL]"
exit -1
fi

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
echo -n "Downloading gcc-g++-4.3.2 "
wget --quiet ftp://ftp.gnu.org/gnu/gcc/gcc-4.3.2/gcc-g++-4.3.2.tar.gz
if  [ -f "gcc-g++-4.3.2.tar.gz" ]; then
echo "[OK]"
else
echo "[FAIL]"
exit -1
fi
echo -n "Decompression of gcc-g++-4.3.2 "
tar xzf gcc-g++-4.3.2.tar.gz
if  [ -d "gcc-4.3.2/libstdc++-v3" ]; then
echo "[OK]"
else
echo "[FAIL]"
exit -1
fi
echo "Build gcc-g++-4.2.3 "
cd gcc-4.3.2
mkdir build
cd build
../configure --target=avr --prefix=$BINARY --disable-nsl --enable-languages=c,c++ --disable-libssp --disable-shared --quiet
make >> messages.log
#2>&1
# sometimes there are PATH issues with "sudo make install",
# so you might try "sudo su" and then "make install".
# be sure to 'exit' from the root account before continuing!
echo '0
0' | make install  >> messages.log
if [ "$?" != "0" ]; then
echo "[FATAL ERROR]--> You may have to install on ubuntu sudo apt-get install libgmp3-dev libmpfr-dev"
	exit -1
fi

# to test:
#avr-gcc --version
#avr-g++ --version

# avr-libc-1.6.4
cd $ROOT
mkdir avr-libc
cd avr-libc
echo -n "Downloading avr-libc-1.6.4 "
wget --quiet http://download.savannah.gnu.org/releases/avr-libc/avr-libc-1.6.4.tar.bz2
if  [ -f "avr-libc-1.6.4.tar.bz2" ]; then
echo "[OK]"
else
echo "[FAIL]"
exit -1
fi
tar xjf avr-libc-1.6.4.tar.bz2
cd avr-libc-1.6.4/
mkdir build
cd build
echo "Build avr-libc-1.6.4 "
export CC=$BINARY/bin/avr-gcc
export "PATH=$PATH:$BINARY/bin"
../configure --build=`../config.guess` --host=avr --prefix=$BINARY --enable-languages=c,c++ --disable-shared
make >> messages.log
#2>&1
# again, it might work best to "sudo su" then "make install"
# be sure to 'exit' from the root account before continuing!
echo '0
0' | make install >> messages.log
if [ "$?" != "0" ]; then
	exit -1
fi


cd $ROOT
mkdir arduino
cd arduino
echo -n "Downloading arduino-1.0"
wget  http://arduino.googlecode.com/files/arduino-0022.tgz
if  [ -f "arduino-0022.tgz" ]; then
echo "[OK]"
else
echo "[FAIL]"
exit -1
fi
tar xzf arduino-0022.tgz
