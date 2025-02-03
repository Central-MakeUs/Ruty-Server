package com.ruty.rutyserver.entity;

import com.ruty.rutyserver.common.BaseEntity;
import com.ruty.rutyserver.entity.e.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "level")
@NoArgsConstructor
@Getter
public class Level extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_id")
    private Long id;

    @Column(nullable = false)
    private Category category;

    @ColumnDefault("1")
    private Long level;

    @ColumnDefault("0")
    private Long totalPoints;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
