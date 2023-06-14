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

    public User(int id, String name, String passHash, String salt, int postCount) {
        this.id = id;
        this.name = name;
        this.passHash = passHash;
        this.salt = salt;
        this.postCount = postCount;
        this.ranks = new ArrayList<>();
    }

    public User(String name, String passHash, String salt) {
        this.name = name;
        this.passHash = passHash;
        this.salt = salt;
        this.postCount = 0;
        this.ranks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return  name + ' ' + ranks;
    }

    private int id;
    private String name;
    private String passHash;
    private String salt;
    private int postCount;
    private List<String> ranks;
}
