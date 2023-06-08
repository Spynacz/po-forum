package org.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    public User(int id, String name, String password, int postCount) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.postCount = postCount;
        this.ranks = new ArrayList<>();
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.postCount = 0;
        this.ranks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return  name + ' ' + ranks;
    }

    private int id;
    private String name;
    private String password;
    private int postCount;
    private List<String> ranks;
}
