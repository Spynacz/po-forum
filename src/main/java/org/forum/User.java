package org.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    private int id;
    private String name;
    private String password;
}
