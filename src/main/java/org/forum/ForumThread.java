package org.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ForumThread {
    public ForumThread(String title, int userID) {
        this.title = title;
        setCurrentTime();
        this.userId = userID;
        this.closed = false;
    }
    public void setCurrentTime()
    {
        this.timestamp = System.currentTimeMillis();
    }
    private int id;
    private String title;
    private long timestamp;
    private int userId;
    private boolean closed;
//    private boolean archived;     maybe
}
