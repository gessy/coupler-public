# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)
project(lorad LANGUAGES C)
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -std=c99 -pthread")

set(APP_PATH ${CMAKE_CURRENT_SOURCE_DIR}/../../..)
set(EXECUTABLE_OUTPUT_PATH ${APP_PATH}/target/${ANDROID_ABI})


add_library(native-lib SHARED native-lib.c)

find_library(log-lib log)

target_link_libraries(native-lib ${log-lib})

set_target_properties(native-lib PROPERTIES LIBRARY_OUTPUT_DIRECTORY ${APP_PATH}/target/${ANDROID_ABI})
