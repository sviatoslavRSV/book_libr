package com.example.demo.utils;


import java.util.Set;

public class Extensions {
    private static Set<String> extensionsImageSet;
    private static Set<String> extensionsBookSet;

    public static Set<String> getExtensionsImageSet() {
        return extensionsImageSet;
    }

    public static void setExtensionsImageSet(Set<String> extensionsImage) {
        extensionsImageSet = extensionsImage;
    }

    public static Set<String> getExtensionsBookSet() {
        return extensionsBookSet;
    }

    public static void setExtensionsBookSet(Set<String> extensionsBook) {
        extensionsBookSet = extensionsBook;
    }
}
