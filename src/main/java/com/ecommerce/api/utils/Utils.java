package com.ecommerce.api.utils;

public class Utils {
    public String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return ""; // Or handle the case where there's no extension
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
