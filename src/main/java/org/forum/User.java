package org.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    }

    private int id;
    private String name;
    private String password;
    private int postCount;
    private List<String> ranks;
}
