package site.board.boardtraining.domain.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.board.boardtraining.domain.auth.data.CustomUserPrincipal;
import site.board.boardtraining.domain.comment.dto.api.CreateArticleCommentRequest;
import site.board.boardtraining.domain.comment.dto.api.UpdateArticleCommentRequest;
import site.board.boardtraining.domain.comment.dto.business.ArticleCommentsDto;
import site.board.boardtraining.domain.comment.dto.business.DeleteArticleCommentDto;
import site.board.boardtraining.domain.comment.service.ArticleCommentReactionService;
import site.board.boardtraining.domain.comment.service.ArticleCommentService;
import site.board.boardtraining.global.response.success.MultipleSuccessApiResponse;
import site.board.boardtraining.global.response.success.SuccessApiResponse;

@RestController
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;
    private final ArticleCommentReactionService articleCommentReactionService;

    public ArticleCommentController(
            ArticleCommentService articleCommentService,
            ArticleCommentReactionService articleCommentReactionService
    ) {
        this.articleCommentService = articleCommentService;
        this.articleCommentReactionService = articleCommentReactionService;
    }

    @GetMapping("/api/articles/{article-id}/comments")
    public ResponseEntity<MultipleSuccessApiResponse<ArticleCommentsDto>> getAllArticleComments(
            @PathVariable("article-id") Long articleId
    ) {
        return ResponseEntity.ok(
                MultipleSuccessApiResponse.of(
                        "성공적으로 게시글 댓글이 조회되었습니다.",
                        articleCommentService.getArticleComments(articleId)
                )
        );
    }

    @PostMapping("/api/articles/{article-id}/comments")
    public ResponseEntity<SuccessApiResponse> createParentComment(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable("article-id") Long articleId,
            @RequestBody CreateArticleCommentRequest request
    ) {
        articleCommentService.createParentArticleComment(
                request.toCreateParentCommentDto(
                        articleId,
                        principal.getMemberId()
                )
        );

        return ResponseEntity.ok(
                SuccessApiResponse.of(
                        "성공적으로 댓글이 생성되었습니다."
                )
        );
    }

    @PostMapping("/api/comments/{comment-id}")
    public ResponseEntity<SuccessApiResponse> createChildComment(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable("comment-id") Long commentId,
            @RequestBody CreateArticleCommentRequest request
    ) {
        articleCommentService.createChildArticleComment(
                request.toCreateChildCommentDto(
                        principal.getMemberId(),
                        commentId
                )
        );

        return ResponseEntity.ok(
                SuccessApiResponse.of(
                        "성공적으로 댓글이 생성되었습니다."
                )
        );
    }

    @PatchMapping("/api/comments/{comment-id}")
    public ResponseEntity<SuccessApiResponse> updateArticleComment(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable("comment-id") Long commentId,
            @RequestBody UpdateArticleCommentRequest request
    ) {
        articleCommentService.updateArticleComment(
                request.toDto(
                        principal.getMemberId(),
                        commentId
                )
        );

        return ResponseEntity.ok(
                SuccessApiResponse.of(
                        "성공적으로 댓글이 수정되었습니다."
                )
        );
    }

    @DeleteMapping("/api/comments/{comment-id}")
    public ResponseEntity<SuccessApiResponse> deleteArticleComment(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable("comment-id") Long commentId
    ) {
        articleCommentService.deleteArticleComment(
                DeleteArticleCommentDto.of(
                        principal.getMemberId(),
                        commentId
                )
        );

        return ResponseEntity.ok(
                SuccessApiResponse.of(
                        "성공적으로 댓글이 삭제되었습니다."
                )
        );
    }

    @PostMapping("/api/comments/{comment-id}/like")
    public ResponseEntity<SuccessApiResponse> addArticleCommentLike(
            @PathVariable("comment-id") Long commentId,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        articleCommentReactionService.addArticleCommentLike(commentId, principal.getMemberId());
        return ResponseEntity.ok(
                SuccessApiResponse.of(
                        "성공적으로 게시글 댓글 좋아요가 등록되었습니다."
                )
        );
    }

    @DeleteMapping("/api/comments/{comment-id}/like")
    public ResponseEntity<SuccessApiResponse> deleteArticleCommentLike(
            @PathVariable("comment-id") Long commentId,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        articleCommentReactionService.deleteArticleCommentLike(commentId, principal.getMemberId());
        return ResponseEntity.ok(
                SuccessApiResponse.of(
                        "성공적으로 게시글 댓글 좋아요가 삭제되었습니다."
                )
        );
    }

    @PostMapping("/api/comments/{comment-id}/dislike")
    public ResponseEntity<SuccessApiResponse> addArticleCommentDislike(
            @PathVariable("comment-id") Long commentId,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        articleCommentReactionService.addArticleCommentDislike(commentId, principal.getMemberId());
        return ResponseEntity.ok(
                SuccessApiResponse.of(
                        "성공적으로 게시글 댓글 싫어요가 등록되었습니다."
                )
        );
    }

    @DeleteMapping("/api/comments/{comment-id}/dislike")
    public ResponseEntity<SuccessApiResponse> deleteArticleCommentDislike(
            @PathVariable("comment-id") Long commentId,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        articleCommentReactionService.deleteArticleCommentDislike(commentId, principal.getMemberId());
        return ResponseEntity.ok(
                SuccessApiResponse.of(
                        "성공적으로 게시글 댓글 싫어요가 삭제되었습니다."
                )
        );
    }
}