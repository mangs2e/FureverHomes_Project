package com.example.fureverhomes_project.service;

import com.example.fureverhomes_project.component.FileUtils;
import com.example.fureverhomes_project.dto.BoardPageDTO;
import com.example.fureverhomes_project.dto.BoardReqDTO;
import com.example.fureverhomes_project.dto.BoardResDTO;
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
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final FileRepository fileRepository;
    private final FileUtils fileUtils;
    private final EntityManager em;

//    //게시글 등록
//    @Transactional
//    public Long insertBoard(final Long memberId, final BoardReqDTO boardReqDTO) {
//        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("Member 객체가 없음"));
//        Board board = boardRepository.save(boardReqDTO.toEntity(member));
//        return board.getId();
//    }

    //게시글 등록
    @Transactional
    public Long insertBoard(final Long memberId, final Map<String, String> param, final List<MultipartFile> files) throws Exception{
        String title = param.get("title");
        String content = param.get("content");
        System.out.println(title+": "+content);
        BoardReqDTO boardReqDTO = BoardReqDTO.builder().title(title).content(content).build();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("Member 객체가 없음"));
        Board board = boardRepository.save(boardReqDTO.toEntity(member));

        List<File> fileList = fileUtils.parseFileInfo(files);
        if (!fileList.isEmpty()) {
            for (File file : fileList) {
                board.addFile(fileRepository.save(file));
            }
        }
        return board.getId();
    }

    //게시글 조회
    @Transactional
    public BoardResDTO selectBoard(final Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new EntityNotFoundException("Board 객체가 없음"));
        board.updateViews(); //조회하는 동시에 조회수 1 추가
        boardRepository.save(board);
        return new BoardResDTO(board);
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
}
