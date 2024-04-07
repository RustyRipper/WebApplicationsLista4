package com.capgemini.jpa.repositories;

import com.capgemini.jpa.entities.Follower;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
    @EntityGraph(value = "Follower.comment")
    List<Follower> findAllByUserId(String userId);
}
