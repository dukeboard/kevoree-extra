# NDK SerialPosix
#

LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := serialposix
LOCAL_C_INCLUDES := ../serialposix.h
LOCAL_SRC_FILES := ../serialposix.c
include $(BUILD_EXECUTABLE)
