package org.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ForumThread {
    public ForumThread(int id, String title, long timestamp, int userID) {
        this.id = id;
        this.title = title;
        this.timestamp = timestamp;
        this.userId = userID;
    }

    private int id;
    private String title;
    private long timestamp;
    private int userId;
    private boolean closed;
//    private boolean archived;     maybe
}
