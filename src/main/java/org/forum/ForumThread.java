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
        this.timestamp = System.currentTimeMillis();
        this.userId = userID;
        this.closed = false;
    }

    private int id;
    private String title;
    private long timestamp;
    private int userId;
    private boolean closed;
//    private boolean archived;     maybe
}
