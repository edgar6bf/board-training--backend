package site.board.boardtraining.domain.hashtag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.hashtag.entity.BoardHashtag;

import java.util.Set;

public interface BoardHashtagRepository
        extends JpaRepository<BoardHashtag, Long> {

    Set<BoardHashtag> findAllByBoard(Board board);

    void deleteAllByBoard(Board board);
}