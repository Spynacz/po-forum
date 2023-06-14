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
<<<<<<< HEAD
        setCurrentTime();
=======
        this.timestamp = System.currentTimeMillis();
>>>>>>> master
        this.threadId = threadId;
    }
    public void setCurrentTime()
    {
        this.timestamp = System.currentTimeMillis();
    }
    private int id;
    private String body;
    private long timestamp;
    private int threadId;
    private int userId;
    private int noInThread;
}
