package site.board.boardtraining.domain.hashtag.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.board.boardtraining.domain.article.entity.Article;
import site.board.boardtraining.domain.board.entity.Board;
import site.board.boardtraining.domain.hashtag.entity.ArticleHashtag;
import site.board.boardtraining.domain.hashtag.entity.BoardHashtag;
import site.board.boardtraining.domain.hashtag.entity.Hashtag;
import site.board.boardtraining.domain.hashtag.repository.ArticleHashtagRepository;
import site.board.boardtraining.domain.hashtag.repository.BoardHashtagRepository;
import site.board.boardtraining.domain.hashtag.repository.HashtagRepository;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Transactional
@Service
public class HashtagServiceImpl
        implements HashtagService {

    private final HashtagRepository hashtagRepository;
    private final BoardHashtagRepository boardHashtagRepository;
    private final ArticleHashtagRepository articleHashtagRepository;

    public HashtagServiceImpl(
            HashtagRepository hashtagRepository,
            BoardHashtagRepository boardHashtagRepository,
            ArticleHashtagRepository articleHashtagRepository
    ) {
        this.hashtagRepository = hashtagRepository;
        this.boardHashtagRepository = boardHashtagRepository;
        this.articleHashtagRepository = articleHashtagRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public LinkedHashSet<String> getAllBoardHashtags(Board board) {
        return boardHashtagRepository.findAllByBoard(board)
                .stream()
                .map(BoardHashtag::getHashtag)
                .collect(Collectors.toCollection( LinkedHashSet::new ));
    }

    @Transactional(readOnly = true)
    @Override
    public void addBoardHashtags(LinkedHashSet<String> boardHashtags, Board board) {
        boardHashtags
                .forEach(
                        hashtag -> {
                            Hashtag hashtagEntity = hashtagRepository.findByTitle(hashtag)
                                    .orElseGet(() -> createHashtag(hashtag));

                            boardHashtagRepository.save(
                                    BoardHashtag.of(
                                            hashtagEntity,
                                            board
                                    )
                            );
                        }
                );
    }

    @Override
    public void updateBoardHashtags(LinkedHashSet<String> boardHashtags, Board board) {
        deleteBoardHashtags(board);
        addBoardHashtags(boardHashtags, board);
    }

    @Override
    public void deleteBoardHashtags(Board board) {
        boardHashtagRepository.deleteAllByBoard(board);
    }

    @Transactional(readOnly = true)
    @Override
    public LinkedHashSet<String> getAllArticleHashtags(Article article) {
        return articleHashtagRepository.findAllByArticle(article)
                .stream()
                .map(ArticleHashtag::getHashtag)
                .collect(Collectors.toCollection( LinkedHashSet::new ));
    }

    @Override
    public void addArticleHashtags(LinkedHashSet<String> articleHashtags, Article article) {
        articleHashtags
                .forEach(
                        hashtag -> {
                            Hashtag hashtagEntity = hashtagRepository.findByTitle(hashtag)
                                    .orElseGet(() -> createHashtag(hashtag));

                            articleHashtagRepository.save(
                                    ArticleHashtag.of(
                                            hashtagEntity,
                                            article
                                    )
                            );
                        }
                );
    }

    @Override
    public void updateArticleHashtags(LinkedHashSet<String> articleHashtags, Article article) {
        deleteArticleHashtags(article);
        addArticleHashtags(articleHashtags, article);
    }

    @Override
    public void deleteArticleHashtags(Article article) {
        articleHashtagRepository.deleteAllByArticle(article);
    }

    private Hashtag createHashtag(String hashtagTitle) {
        return hashtagRepository.save(
                Hashtag.of(hashtagTitle)
        );
    }
}