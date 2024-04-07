package com.capgemini.jpa.tasks;

import com.capgemini.jpa.entities.Comment;
import com.capgemini.jpa.entities.Follower;
import com.capgemini.jpa.repositories.CommentRepository;
import com.capgemini.jpa.repositories.EventRepository;
import com.capgemini.jpa.repositories.FollowerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@DataJpaTest
class Task6 {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private FollowerRepository followerRepository;
    @Autowired
    private EventRepository eventRepository;

    @Test
    void shouldGetFollowersOneQuery() {
        commentRepository.saveAndFlush(new Comment(1L, "1111", eventRepository.findById(1L).orElse(null)));
        commentRepository.saveAndFlush(new Comment(2L, "2222", eventRepository.findById(2L).orElse(null)));
        commentRepository.saveAndFlush(new Comment(3L, "3333", eventRepository.findById(3L).orElse(null)));
        assertThat("Comments should be added", commentRepository.findAll().size(), is(3));

        followerRepository.saveAndFlush(new Follower(1L, "user1", LocalDateTime.now(), commentRepository.getById(3L)));
        followerRepository.saveAndFlush(new Follower(2L, "user2", LocalDateTime.now(), commentRepository.getById(1L)));
        followerRepository.saveAndFlush(new Follower(3L, "user2", LocalDateTime.now(), commentRepository.getById(2L)));
        assertThat("Followers should be added", followerRepository.findAll().size(), is(3));

        System.out.println("One query needed");
        List<Follower> followers = followerRepository.findAllByUserId("user2");
        assertThat(followers.size(), is(2));
        assertThat("Event duration should be equals", followers.get(0).getComment().getEvent().getDuration(), is(30));
        assertThat("Event duration should be equals", followers.get(1).getComment().getEvent().getDuration(), is(550));

        System.out.println("Another query");
        assertThat("Event duration should be equals", followers.get(1).getComment().getEvent().getServer().getIp(),
                is("111.112.113.114"));
    }
}
