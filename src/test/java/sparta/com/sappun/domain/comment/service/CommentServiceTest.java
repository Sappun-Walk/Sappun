// package sparta.com.sappun.domain.comment.service;
//
// import static org.mockito.Mockito.*;
//
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.ArgumentCaptor;
// import org.mockito.Captor;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.web.multipart.MultipartFile;
// import sparta.com.sappun.domain.board.repository.BoardRepository;
// import sparta.com.sappun.domain.comment.entity.Comment;
// import sparta.com.sappun.domain.comment.repository.CommentRepository;
// import sparta.com.sappun.domain.user.repository.UserRepository;
// import sparta.com.sappun.infra.s3.S3Util;
// import sparta.com.sappun.test.CommentTest;
//
// @ExtendWith(MockitoExtension.class)
// class CommentServiceTest implements CommentTest {
//
//    @Mock CommentRepository commentRepository;
//    @Mock UserRepository userRepository;
//    @Mock BoardRepository boardRepository;
//    @Mock S3Util s3Util;
//    @InjectMocks CommentService commentService;
//    @Captor ArgumentCaptor<Comment> argumentCaptor;
//
//    static MultipartFile multipartFile;
//
//    //        @Test
//    //        @DisplayName("댓글 저장 테스트")
//    //        void saveCommentTest() {
//    //            // Mock data
//    //            CommentSaveReq commentSaveReq =
//    //                    CommentUpdateReq.builder().content(TEST_COMMENT_UPDATE_CONTENT).build();
//    //            MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.jpg",
//    // "image/jpeg", "mockImageData".getBytes());
//    //
//    //            Board mockBoard = mock(Board.class);
//    //            User mockUser = mock(User.class);
//    //            when(boardRepository.findById(anyLong())).thenReturn(mockBoard);
//    //            when(userRepository.findById(anyLong())).thenReturn(mockUser);
//    //            when(s3Util.uploadFile(any(), any())).thenReturn("mockedImageURL");
//    //
//    //            // Call the method
//    //            CommentSaveRes result = commentService.saveComment(commentSaveReq,
//    // mockMultipartFile);
//    //
//    //            // Verify interactions and assertions
//    //            verify(mockUser).updateScore(50);
//    //            verify(commentRepository).save(any());
//    //            assertEquals("mockedImageURL", result.getFileURL());
//    //        }
//
//    //                @Test
//    //                @DisplayName("댓글 저장 실패 테스트 - 사용자 권한 없음")
//    //                void invalidSaveCommentTest() {
//    //                    // given
//    // CommentUpdateReq TEST_COMMENT_UPDATE =
//    //        CommentUpdateReq.builder().content(TEST_COMMENT_UPDATE_CONTENT).build();
//
//    //
//    //                    // when
//    //                    GlobalException exception =
//    //                            assertThrows(
//    //                                    GlobalException.class,
//    //                                    () -> {
//    //                                        commentService.saveComment(TEST_COMMENT_SAVE);
//    //                                    });
//    //
//    //                    // then
//    //                    assertEquals(NOT_FOUND_BOARD.getMessage(),
//    //     exception.getResultCode().getMessage());
//    //                }
//    //
//    //            @Test
//    //            @DisplayName("댓글 수정 테스트")
//    //            void updateCommentTest() {
//    //                // given
//    //
//    //
//    //
//    //
// given(commentRepository.findById(TEST_COMMENT_UPDATE.getCommentId())).willReturn(TEST_COMMENT);
//    //
//    //                // when
//    //                commentService.updateComment(TEST_COMMENT_UPDATE);
//    //
//    //                // then
//    //                verify(commentRepository).save(argumentCaptor.capture());
//    //                assertEquals(TEST_COMMENT_UPDATE_CONTENT,
//    // argumentCaptor.getValue().getContent());
//    //            }
// }
