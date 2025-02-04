package com.ruty.rutyserver.entity;

import com.ruty.rutyserver.common.BaseEntity;
import com.ruty.rutyserver.entity.e.Category;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "category_levels")
@NoArgsConstructor
@DynamicInsert // 자동으로 insert문에 null값을 배제하고 쿼리문을 날려줌.
@Getter
public class CategoryLevel extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_id")
    private Long id;

    @Column(nullable = false)
    private Category category;

    @ColumnDefault("0")
    private Long level;

    @ColumnDefault("0")
    private Long totalPoints;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public CategoryLevel(Category category, Long level, Long totalPoints, Member member) {
        this.category = category;
        this.level = level;
        this.totalPoints = totalPoints;
        this.member = member;
    }

    public void pointUp() {
        this.totalPoints += 5l;
        if(this.totalPoints > 155l)
            this.totalPoints = 155L;
    }

    public void pointDown() {
        this.totalPoints -= 5l;
        if(this.totalPoints < 0l)
            this.totalPoints = 0L;
    }

    public void levelUp() {
        this.level += 1l;
        if(this.level > 6l)
            this.level = 6l;
    }

    public void levelDown() {
        this.level -= 1l;
        if(this.level < 0l)
            this.level = 0l;
    }


}
