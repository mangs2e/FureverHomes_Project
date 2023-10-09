package com.example.fureverhomes_project.service;

import com.example.fureverhomes_project.component.FileUtils;
import com.example.fureverhomes_project.dto.*;
import com.example.fureverhomes_project.entity.Board;
import com.example.fureverhomes_project.entity.File;
import com.example.fureverhomes_project.entity.Member;
import com.example.fureverhomes_project.repository.BoardRepository;
import com.example.fureverhomes_project.repository.FileRepository;
import com.example.fureverhomes_project.repository.MemberRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.example.fureverhomes_project.entity.QBoard.board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final FileRepository fileRepository;
    private final FileUtils fileUtils;
    private final EntityManager em;

    //게시글 등록
    @Transactional
    public Long insertBoard(final Long memberId, final Map<String, String> param, final List<MultipartFile> files) throws Exception{
        String title = param.get("title");
        String content = param.get("content");
        System.out.println(title+": "+content);
        BoardReqDTO boardReqDTO = BoardReqDTO.builder().title(title).content(content).build();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("Member 객체가 없음"));
        Board board = boardRepository.save(boardReqDTO.toEntity(member));

        addNewFiles(board, files);

        return board.getId();
    }

    //게시글 조회
    @Transactional
    public BoardResDTO selectBoard(final Long boardId, final Long memberId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new EntityNotFoundException("Board 객체가 없음"));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("Member 객체가 없음"));
        board.updateViews(); //조회하는 동시에 조회수 1 추가
        boardRepository.save(board);
        BoardResDTO boardResDTO = new BoardResDTO(board);
        boardResDTO.setFiles(board);
        boardResDTO.setComments(board);
        if (board.getMember().equals(member)) {
            boardResDTO.setEqualLogin();
        }
        return boardResDTO;
    }

    //게시글 전체 조회
    public BoardPageDTO selectAllBoard(final Map<String, String> params, Pageable pageable) {
        String searchType = params.get("searchType");
        String searchKeyword = params.get("searchKeyword");
        BoardReqDTO boardReqDTO = new BoardReqDTO();
        if (searchKeyword != null) {
            boardReqDTO.boardSearchKeyword(searchType, searchKeyword);
        }
        Page<Board> boardList = findAllBySearchKeyword(boardReqDTO, pageable);
        List<BoardResDTO> boardResDTOS = boardList.getContent().stream().map(BoardResDTO::new).collect(Collectors.toList());
        return new BoardPageDTO(boardResDTOS, boardList.getTotalPages(), boardList.getTotalElements());
    }

    //검색 조건으로 검색하기
    private Page<Board> findAllBySearchKeyword(final BoardReqDTO boardReqDTO, Pageable pageable) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        BooleanBuilder builder = new BooleanBuilder();

        if (boardReqDTO.getTitle() != null) {
            builder.and(board.title.likeIgnoreCase("%" + boardReqDTO.getTitle() + "%"));
        }
        if (boardReqDTO.getWriter() != null) {
            builder.and(board.member.name.likeIgnoreCase("%" + boardReqDTO.getWriter() + "%"));
        }
        if (boardReqDTO.getContent() != null) {
            builder.and(board.content.likeIgnoreCase("%" + boardReqDTO.getContent() + "%"));
        }

        List<Board> boardList = queryFactory.selectFrom(board)
                .where(builder).orderBy(board.createdTime.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
        long totalCount = queryFactory.select(board.count()).from(board).where(builder).fetchFirst();
        return new PageImpl<>(boardList, pageable, totalCount);
    }

    //게시글 삭제
    @Transactional
    public void deleteBoard(final Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new EntityNotFoundException("Board 객체가 없음"));
        if (!board.getFiles().isEmpty()) {
            for (File file : board.getFiles()) {
                fileUtils.deleteFile(file);
            }
        }
        boardRepository.delete(board);
    }

    //게시글 수정
    @Transactional
    public Long updateBoard(final Map<String, String> param, final List<MultipartFile> files) throws Exception{
        Long boardId = Long.valueOf(param.get("board_id"));
        String title = param.get("title");
        String content = param.get("content");
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new EntityNotFoundException("Board 객체가 없음"));
        // DB에 저장되어있는 파일 불러오기
        List<File> dbPhotoList = board.getFiles();
        // 새롭게 전달되어온 파일들의 목록을 저장할 List 선언
        if(CollectionUtils.isEmpty(dbPhotoList)) { // DB에 아예 존재 x
            if(!CollectionUtils.isEmpty(files)) { // 전달되어온 파일이 하나라도 존재
                addNewFiles(board, files);
            }
        }
        else {  // DB에 한 장 이상 존재
            if(CollectionUtils.isEmpty(files)) { // 전달되어온 파일 아예 x
                // 파일 삭제
                deleteExistingFiles(dbPhotoList);
            }
            else {  // 전달되어온 파일 한 장 이상 존재
                // 파일 삭제
                deleteExistingFiles(dbPhotoList);
                addNewFiles(board, files);
            }
        }
        if (title != null) {
            board.updateTitle(title);
        }
        if (content != null) {
            board.updateContent(content);
        }
        boardRepository.save(board);

        return board.getId();
    }

    private void deleteExistingFiles(List<File> dbPhotoList) {
        for (File dbPhoto : dbPhotoList) {
            fileUtils.deleteFile(dbPhoto);
            dbPhoto.updateDelete(true);
        }
    }

    private void addNewFiles(Board board, List<MultipartFile> files) throws Exception {
        List<File> newFiles = fileUtils.parseFileInfo(files);
        for (File file : newFiles) {
            board.addFile(fileRepository.save(file));
        }
    }
}
