package com.skillify.project.interfaces;

import com.skillify.project.model.ForumTopic;

import java.util.List;

public interface ForumTopicService {
    List<ForumTopic> getAllForumTopics() throws Exception;
    ForumTopic createForumTopic(ForumTopic forumTopic) throws Exception;
}
