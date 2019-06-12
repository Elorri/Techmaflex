package com.techmaflex.app.util;

public class StacktraceUtil {

    public String printStack(StackTraceElement[] stackTrace) {
        String stackTraceString="";
        for(StackTraceElement aTrace : stackTrace){
            stackTraceString+=aTrace.toString();
        }
        return stackTraceString;
    }
}
