package site.board.boardtraining.domain.board.dto.business;

import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.member.dto.business.MemberDto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;

public record BoardDto(
        Long boardId,
        String title,
        String description,
        String thumbnailImageUrl,
        LinkedHashSet<String> hashtags,
        Integer likeCount,
        Integer dislikeCount,
        MemberDto memberDto,
        LocalDateTime createAt
) {
    public static BoardDto from(
            Board board,
            LinkedHashSet<String> hashtags,
            Integer likeCount,
            Integer dislikeCount
    ) {
        return new BoardDto(
                board.getId(),
                board.getTitle(),
                board.getDescription(),
                board.getThumbnailImageUrl(),
                hashtags,
                likeCount,
                dislikeCount,
                MemberDto.from(board.getMember()),
                board.getCreatedAt()
        );
    }
}