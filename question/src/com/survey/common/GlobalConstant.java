// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GlobalConstant.java

package com.survey.common;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("all")
public class GlobalConstant
{

    public GlobalConstant()
    {
    }

    public static final String SESSION_INFO = "sessionInfo";
    public static final Integer ENABLE = Integer.valueOf(0);
    public static final Integer DISABLE = Integer.valueOf(1);
    public static final Integer DEFAULT = Integer.valueOf(0);
    public static final Integer NOT_DEFAULT = Integer.valueOf(1);
    public static final Map sexlist = new LinkedHashMap() {

            
            {
                put("0", "\u7537");
                put("1", "\u5973");
            }
    }
;
    public static final Map statelist = new LinkedHashMap() {

            
            {
                put("0", "\u542F\u7528");
                put("1", "\u505C\u7528");
            }
    }
;
}
