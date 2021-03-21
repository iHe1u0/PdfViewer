cmake_minimum_required(VERSION 3.10.2)

project("pdfviewer")

add_library(fileutils
        SHARED
        fileutils.cpp)
target_link_libraries(fileutils)