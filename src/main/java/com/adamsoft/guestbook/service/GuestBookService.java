package com.adamsoft.guestbook.service;

import com.adamsoft.guestbook.domain.GuestBookDTO;
import com.adamsoft.guestbook.domain.PageRequestDTO;
import com.adamsoft.guestbook.domain.PageResponseDTO;
import com.adamsoft.guestbook.entity.GuestBook;

//DTO를 Entity로 변환해주는 메서드
//인터페이스에 메소드를 붙일때 항상 default 붙여주기

public interface GuestBookService {

    //데이터 삽입을 위한 메서드

    public Long register(GuestBookDTO dto);


    //목록보기를 위한 메서드
    public PageResponseDTO<GuestBookDTO, GuestBook> getList (PageRequestDTO requestDTO);


    //상세보기를 위한 메서드
    public GuestBookDTO read(Long gno);


    //DTO -> Entity 변환해주는 메서드
    default GuestBook dtoToEntity(GuestBookDTO dto){
        GuestBook entity = GuestBook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    //Entity -> DTO 변환해주는 메서드
    default GuestBookDTO entityToDTO(GuestBook entity){
        GuestBookDTO dto = GuestBookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
        return dto;
    }

    }
