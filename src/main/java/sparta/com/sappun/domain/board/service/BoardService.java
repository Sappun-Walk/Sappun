package sparta.com.sappun.domain.board.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sparta.com.sappun.domain.board.dto.request.BoardSaveReq;
import sparta.com.sappun.domain.board.dto.request.BoardUpdateReq;
import sparta.com.sappun.domain.board.dto.response.*;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.entity.Image;
import sparta.com.sappun.domain.board.entity.RegionEnum;
import sparta.com.sappun.domain.board.repository.BoardRepository;
import sparta.com.sappun.domain.board.repository.ImageRepository;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.global.validator.BoardValidator;
import sparta.com.sappun.global.validator.S3Validator;
import sparta.com.sappun.global.validator.UserValidator;
import sparta.com.sappun.infra.s3.S3Util;
import sparta.com.sappun.infra.s3.S3Util.FilePath;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final S3Util s3Util;
    private static final Integer REPORT_HIDDEN_COUNT = 5;
    private static final Integer BOARD_POINT = 100;
    private static final Integer DEFAULT_LIKE_COUNT = 0;
    private static final Integer DEFAULT_REPORT_COUNT = 0;
    private static final String EMPTY_FILE_TITLE = "empty.txt";

    @Transactional(readOnly = true)
    public BoardGetRes getBoard(Long boardId) {
        Board board = getBoardById(boardId);

        return BoardServiceMapper.INSTANCE.toBoardGetRes(board);
    }

    @Transactional(readOnly = true)
    public Page<BoardToListGetRes> getBoardList(
            RegionEnum region, int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Board> boardList =
                boardRepository.findAllByReportCountLessThanAndRegion(
                        REPORT_HIDDEN_COUNT, region, pageable); // 신고된 횟수가 5회 미만인 게시글 찾기
        return boardList.map(BoardServiceMapper.INSTANCE::toBoardToListGetRes);
    }

    @Transactional(readOnly = true)
    public Page<BoardToListGetRes> getBoardAllList(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return boardRepository
                .findAllByReportCountLessThan(REPORT_HIDDEN_COUNT, pageable)
                .map(BoardServiceMapper.INSTANCE::toBoardToListGetRes);
    }

    @Transactional(readOnly = true)
    public Page<BoardToReportGetRes> getBoardUserList(
            Long userId, int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return boardRepository
                .findAllUserBoardByUserId(userId, pageable)
                .map(BoardServiceMapper.INSTANCE::toBoardUserListGetRes);
    }

    @Transactional(readOnly = true)
    public BoardBestListGetRes getBoardBestList() {
        List<BoardToListGetRes> boardGetRes =
                BoardServiceMapper.INSTANCE.toBoardBestListGetRes(
                        boardRepository.findTop3ByReportCountLessThanOrderByLikeCountDesc(
                                REPORT_HIDDEN_COUNT)); // 신고된 횟수가 5회 미만인 게시글 찾기
        return BoardBestListGetRes.builder().boards(boardGetRes).build();
    }

    public String saveMapImage(MultipartFile mapImage) {
        S3Validator.isProfileImageFile(mapImage);
        return s3Util.uploadFile(mapImage, S3Util.FilePath.BOARD);
    }

    public void deleteMapImage(String mapImage) {
        s3Util.deleteFile(mapImage, FilePath.BOARD); // 기존 이미지 삭제
    }

    @Transactional
    public BoardSaveRes saveBoard(BoardSaveReq boardSaveReq, List<MultipartFile> photoImages) {
        User user = getUserById(boardSaveReq.getUserId());
        user.updateScore(BOARD_POINT); // 게시글 작성하면 점수 +100

        List<Image> images = new ArrayList<>();
        if (photoImages != null) {
            for (MultipartFile image : photoImages) {
                S3Validator.isProfileImageFile(image);
                String imageUrl = s3Util.uploadFile(image, S3Util.FilePath.BOARD);
                images.add(new Image(imageUrl, null));
            }
        }

        Board board =
                Board.builder()
                        .title(boardSaveReq.getTitle())
                        .content(boardSaveReq.getContent())
                        .fileURL(boardSaveReq.getImage())
                        .departure(boardSaveReq.getDeparture())
                        .destination(boardSaveReq.getDestination())
                        .stopover(boardSaveReq.getStopover())
                        .region(boardSaveReq.getRegion())
                        .likeCount(DEFAULT_LIKE_COUNT)
                        .reportCount(DEFAULT_REPORT_COUNT)
                        .user(user)
                        .images(images)
                        .build();

        // Board 인스턴스가 생성된 이후에 Image에 Board 인스턴스를 설정
        for (Image image : images) {
            image.setBoard(board);
        }

        boardRepository.save(board);

        return new BoardSaveRes();
    }

    @Transactional
    public BoardUpdateRes updateBoard(
            BoardUpdateReq boardUpdateReq, List<MultipartFile> photoImages) {
        Board board = getBoardById(boardUpdateReq.getBoardId());
        User user = getUserById(boardUpdateReq.getUserId());
        BoardValidator.checkBoardUser(board.getUser().getId(), user.getId()); // 수정 가능한 사용자인지 확인

        // 입력 파일이 없는 경우 기존 이미지 파일로
        String imageURL = board.getFileURL();
        if (!boardUpdateReq.getImage().isBlank()) {
            imageURL = boardUpdateReq.getImage();
        }

        imageRepository.deleteAllByBoard(board);

        List<Image> images = new ArrayList<>();
        if (photoImages != null) {
            for (MultipartFile image : photoImages) {
                S3Validator.isProfileImageFile(image);
                String imageUrl = s3Util.uploadFile(image, S3Util.FilePath.BOARD);
                images.add(new Image(imageUrl, board));
            }
        }

        // Board 인스턴스가 생성된 이후에 Image에 Board 인스턴스를 설정
        for (Image image : images) {
            image.setBoard(board);
        }

        board.update(boardUpdateReq, imageURL, images);

        return new BoardUpdateRes();
    }

    @Transactional
    public BoardDeleteRes deleteBoard(Long boardId, Long userId) {
        Board board = getBoardById(boardId);
        User user = getUserById(userId);
        BoardValidator.checkBoardUser(user, board.getUser()); // 삭제 가능한 사용자인지 확인

        user.updateScore(-BOARD_POINT); // 게시글 삭제하면 점수 -100

        String boardImage = board.getFileURL();
        if (boardImage != null && !boardImage.isEmpty()) {
            s3Util.deleteFile(boardImage, S3Util.FilePath.BOARD);
        }

        boardRepository.delete(board);
        return new BoardDeleteRes();
    }

    private Board getBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId);
        BoardValidator.validate(board);
        return board;
    }

    private User getUserById(Long userId) {
        User user = userRepository.findById(userId);
        UserValidator.validate(user);
        return user;
    }
}
