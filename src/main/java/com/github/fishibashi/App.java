package com.github.fishibashi;

import org.apache.commons.lang3.SystemUtils;

public class App {
  public static void main(String[] args) {
    System.out.println("Hello, Ant");
    System.out.println("* Java version : " + SystemUtils.JAVA_VM_VERSION);
    System.out.println("* OS name :" + SystemUtils.OS_NAME);
    System.out.println("* OS version :" + SystemUtils.OS_VERSION);
  }
}