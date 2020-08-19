
#ifndef LORAD_LOGGER_H
#define LORAD_LOGGER_H

#include <android/log.h>

#ifndef TAG
#define TAG "lorad"
#endif

// some phone can't print INFO Log
#define LOGI(...) __android_log_print(ANDROID_LOG_ERROR, TAG, " ---INFO--- " __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, " ---ERROR--- " __VA_ARGS__)
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, TAG, " ---ERROR--- " __VA_ARGS__)

#endif //LORAD_LOGGER_H
