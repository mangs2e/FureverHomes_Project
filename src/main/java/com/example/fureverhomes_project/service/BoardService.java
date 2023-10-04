package com.example.fureverhomes_project.service;

import com.example.fureverhomes_project.dto.BoardReqDTO;
import com.example.fureverhomes_project.entity.Board;
import com.example.fureverhomes_project.entity.Member;
import com.example.fureverhomes_project.repository.BoardRepository;
import com.example.fureverhomes_project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    //게시글 등록
    @Transactional
    public Long insertBoard(final Long memberId, final BoardReqDTO boardReqDTO) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("Member 객체가 없음"));
        Board board = boardRepository.save(boardReqDTO.toEntity(member));
        return board.getId();
    }
}
