package org.forum.controllers.utils;

import org.forum.User;

import java.util.List;

public class Helpers {
    public static final String ListToString(List<String> list)
    {
        String val  = list.get(0);
        for(int i = 1; i < list.size();i++)
        {
            val += list.get(i);
        }
        return val;
    }
    public static boolean hasAccesToChangeStandard(User logedUser,User owningUser, List<String> ranks)
    {
        return (owningUser.getId() != logedUser.getId() && (ranks != null || (ranks.stream().filter(st -> "admin".equals(st)).findAny().orElse(null) != null)));
    }
}
