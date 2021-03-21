#include <jni.h>
#include <string>
#include <iostream>

#ifndef _Included_com_imorning_pdf_utils_NormalFileUtils
#define _Included_com_imorning_pdf_utils_NormalFileUtils
#ifdef __cplusplus
extern "C" {
#endif
JNIEXPORT jstring JNICALL Java_com_imorning_pdf_utils_NormalFileUtils_getFileName
        (JNIEnv *env, jclass, jstring _filePath) {
    if (env->GetStringUTFLength(_filePath) <= 0) {
        const char *err = "filepath is null!";
        return env->NewStringUTF(err);
    }
    const char *charFilePath = env->GetStringUTFChars(_filePath, 0);
    std::string filePath = std::string(charFilePath);
    int pos = filePath.find_last_of('/');
    std::string fileName = filePath.substr(pos + 1);
    return env->NewStringUTF(fileName.c_str());
}

#ifdef __cplusplus
}
#endif
#endif
