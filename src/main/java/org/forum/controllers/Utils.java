package org.forum.controllers;

import java.util.List;

public class Utils {
    public static final String ListToString(List<String> list)
    {
        String val  = list.get(0);
        for(int i = 1; i < list.size();i++)
        {
            val += list.get(i);
        }
        return val;
    }
}
