package site.board.boardtraining.domain.comment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.board.boardtraining.domain.comment.constant.ArticleCommentStatus;
import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.comment.constant.ArticleCommentType;
import site.board.boardtraining.global.audit.BaseEntity;
import site.board.boardtraining.domain.member.entity.Member;

import java.util.Objects;

import static site.board.boardtraining.domain.comment.constant.ArticleCommentStatus.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "article_comment",
        indexes = {
                @Index(columnList = "createdAt")
        }
)
@Entity
public class ArticleComment
        extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private ArticleCommentStatus status = ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false, updatable = false)
    private ArticleCommentType commentType;

    @Column(updatable = false)
    private Long parentCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private ArticleComment(
            String content,
            ArticleCommentType commentType,
            Long parentCommentId,
            Article article,
            Member member
    ) {
        this.content = content;
        this.commentType = commentType;
        this.parentCommentId = parentCommentId;
        this.article = article;
        this.member = member;
    }

    public static ArticleComment of(
            String content,
            ArticleCommentType articleCommentType,
            Long parentCommentId,
            Article article,
            Member member
    ) {
        return new ArticleComment(
                content,
                articleCommentType,
                parentCommentId,
                article,
                member
        );
    }

    public void update(
            String content
    ) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return this.getId() != null && this.getId().equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}