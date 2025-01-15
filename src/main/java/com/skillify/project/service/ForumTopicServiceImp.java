package com.skillify.project.service;

import com.skillify.project.interfaces.ForumTopicService;
import com.skillify.project.model.Course;
import com.skillify.project.model.ForumTopic;
import com.skillify.project.repository.CourseRepository;
import com.skillify.project.repository.ForumTopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumTopicServiceImp implements ForumTopicService {

    private final ForumTopicRepository forumTopicRepository;
    private final CourseRepository courseRepository;

    public ForumTopicServiceImp(ForumTopicRepository forumTopicRepository, CourseRepository courseRepository) {
        this.forumTopicRepository = forumTopicRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<ForumTopic> getAllForumTopics() throws Exception {
        List<ForumTopic> forumTopics = forumTopicRepository.findAll();
        if (forumTopics.isEmpty()) {
            throw new Exception("No forum topics found.");
        }
        return forumTopics;
    }

    @Override
    public ForumTopic createForumTopic(ForumTopic forumTopic) throws Exception {
        List<Course> byInstructorId = courseRepository.findByInstructorId(forumTopic.getInstructorId());

        if (forumTopic == null) {
            throw new IllegalArgumentException("Forum topic cannot be null.");
        }
        if (forumTopic.getTitle() == null || forumTopic.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Forum topic title cannot be empty.");
        }
        if (forumTopic.getDescription() == null || forumTopic.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Forum topic description cannot be empty.");
        }
        if (forumTopic.getInstructorId() == null) {
            throw new IllegalArgumentException("Instructor ID cannot be null.");
        }
        if (byInstructorId.isEmpty()) {
            throw new Exception("Instructor is not available or does not have any courses.");
        }

        ForumTopic createdForumTopic = forumTopicRepository.save(forumTopic);
        return createdForumTopic;
    }

}
