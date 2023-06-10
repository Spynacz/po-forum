package org.forum.controllers.utils;

import org.forum.controllers.PostTemplate;
import org.forum.controllers.ThreadPreview;

public interface CallBack {
    void invoked(PostTemplate source);
    void invoked(ThreadPreview source);
    void invoked(Object source);
}
