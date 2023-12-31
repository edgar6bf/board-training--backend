package site.board.boardtraining.domain.board.dto.api;

import site.board.boardtraining.domain.board.dto.business.CreateBoardDto;

import java.util.LinkedHashSet;

public record CreateBoardRequest(
        String title,
        String description,
        String thumbnailImageUrl,
        LinkedHashSet<String> hashtags
) {
    public CreateBoardDto toDto(
            Long memberId
    ) {
        return CreateBoardDto.of(
                title,
                description,
                thumbnailImageUrl,
                hashtags,
                memberId
        );
    }
}