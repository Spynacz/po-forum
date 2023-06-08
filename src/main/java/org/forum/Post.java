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
    public Post(String body, int userId, int threadId) {
        this.body = body;
        this.userId = userId;
        this.timestamp = System.currentTimeMillis();
        this.threadId = threadId;
    }

    private int id;
    private String body;
    private long timestamp;
    private int threadId;
    private int userId;
    private int noInThread;
}
