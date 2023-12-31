package site.board.boardtraining.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.board.boardtraining.domain.comment.entity.ArticleComment;
import site.board.boardtraining.domain.member.entity.Member;
import site.board.boardtraining.domain.comment.constant.ArticleCommentReactionType;
import site.board.boardtraining.domain.comment.entity.ArticleCommentReaction;

import java.util.Optional;

public interface ArticleCommentReactionRepository
        extends JpaRepository<ArticleCommentReaction, Long> {

    int countAllByTypeAndArticleComment(ArticleCommentReactionType type, ArticleComment articleComment);

    boolean existsByArticleCommentAndMember(ArticleComment articleComment, Member member);

    Optional<ArticleCommentReaction> findByArticleCommentAndMember(ArticleComment articleComment, Member member);
}