package org.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private int id;
    private String body;
    private long timestamp;
    private int threadId;
    private int userId;
    private int noInThread;
}
