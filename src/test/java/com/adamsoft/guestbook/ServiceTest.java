package com.adamsoft.guestbook;


import com.adamsoft.guestbook.domain.GuestBookDTO;
import com.adamsoft.guestbook.domain.PageRequestDTO;
import com.adamsoft.guestbook.domain.PageResponseDTO;
import com.adamsoft.guestbook.entity.GuestBook;
import com.adamsoft.guestbook.service.GuestBookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTest {

    @Autowired
    private GuestBookService guestBookService;

  //  @Test
    public void insertTest(){
        GuestBookDTO dto = GuestBookDTO.builder()
                .title("삽입 테스트")
                .content("서비스에서 테스트")
                .writer("hoy")
                .build();

        Long gno = guestBookService.register(dto);
        System.out.println("삽입된 번호" + gno);


    }


    //@Test
    public void testList(){
        PageRequestDTO pageRequestDTO =
                PageRequestDTO.builder().page(1).size(10).build();

    //메서드 호출
        PageResponseDTO<GuestBookDTO, GuestBook> resultDTO = guestBookService.
                getList(pageRequestDTO);

        //확인
        for (GuestBookDTO guestbookDTO : resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        }

        //이전 페이지 번호 와 다음 페이지 존재 여부
        System.out.println("이전 여부 : " + resultDTO.isPrev());
        System.out.println("다음 여부 : " + resultDTO.isNext());

        //전체 페이지 개수
        System.out.println("전체 페이지 개수 : " + resultDTO.getTotalPage());

        //페이지 번호 목록
        resultDTO.getPageList().forEach(i-> System.out.println(i));

    }

 }
