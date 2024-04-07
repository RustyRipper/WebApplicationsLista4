package com.capgemini.jpa.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NamedEntityGraph(
        name = "Follower.comment",
        attributeNodes = {
                @NamedAttributeNode(value = "comment", subgraph = "subgraph.comment"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "subgraph.comment",
                        attributeNodes = {
                                @NamedAttributeNode("event")
                        }
                )
        }
)
public class Follower {

    @Id
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column
    private LocalDateTime subscriptionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID", nullable = false)
    private Comment comment;

    public Follower(Long id, String userId, LocalDateTime subscriptionDate, Comment comment) {
        this.id = id;
        this.userId = userId;
        this.subscriptionDate = subscriptionDate;
        this.comment = comment;
    }
}