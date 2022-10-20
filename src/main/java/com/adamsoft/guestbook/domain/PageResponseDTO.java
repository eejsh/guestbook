package com.adamsoft.guestbook.domain;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


//목록보기 응답을 위한 클래스
@Data
public class PageResponseDTO<DTO, EN> {


    //응답 목록을 저장할 List
    private List<DTO> dtoList;

    //총 페이지 번호
    private int totalPage;

    //현재 페이지 번호
    private int page;

    //목록 사이즈
    private int size;

    //시작 페이지 번호, 끝 페이지 번호
    private int start, end;

    //이전, 다음
    private boolean prev, next;

    //페이지 번호 목록
    private List<Integer> pageList;

    //페이지 번호 목록을 만들어주는 메서드 -내부에서만 씀
    private void makePageList(Pageable pageable) {

        //현재 페이지 번호와 페이지 당 데이터 개수 가져오기
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        //임시 종료 페이지
        //페이지 번호를 10개 출력할 것이라서 10으로 나누고 곱합
        //페이지 번호 갯수를 다르게 하고자 하면 숫자를 변경!
        int tempEnd = (int) (Math.ceil(page / 10.0)) * 10;

        //시작페이지 번호
        start = tempEnd - 9;

        //이전 존재 여부 ,, 1이면 이전 없음..
        prev = start > 1;

        //종료페이지 번호
        end = totalPage > tempEnd ? tempEnd : totalPage;
        next = totalPage > tempEnd;

        //페이지 번호 목록 만들기
        pageList = IntStream.rangeClosed(start, end)
                .boxed().collect(Collectors.toList());
    }


    //Page 객체와 변환 함수를 넘겨받아서 dtoList를 만들어주는 메서드
    public PageResponseDTO(Page<EN> result,
                           Function<EN, DTO> fn) {
        //page객체를 순회하면서 fn함수로 변환한 후 List로 만들기

        dtoList = result.stream().map(fn).collect(Collectors.toList());

        //페이지 번호 목록 만들기
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());


    }

}


