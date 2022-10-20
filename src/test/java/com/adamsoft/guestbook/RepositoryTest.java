package com.adamsoft.guestbook;


import com.adamsoft.guestbook.entity.GuestBook;
import com.adamsoft.guestbook.entity.QGuestBook;
import com.adamsoft.guestbook.persistence.GuestBookRepository;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Query;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class RepositoryTest {
    @Autowired
    private GuestBookRepository guestBookRepository;

    //데이터삽입 테스트
    //@Test
    public void insertTest(){
        //300개의 가상의 데이터 삽입
        IntStream.rangeClosed(1, 300).forEach(i -> {
            GuestBook guestBook = GuestBook.builder()
                    .title("title...." +  i)
                    .content("Content_" + i)
                    .writer("user" + (i % 10) )
                    .build();

            guestBookRepository.save(guestBook);
        });

    }

    //데이터 수정 테스트 
    //@Test
    public void updateTest(){
        //데이터 1개 찾아오기
        Optional <GuestBook> result =
                guestBookRepository.findById(602L);

        //데이터가 존재하는 경우 수정
        if(result.isPresent()){
            GuestBook guestBook = result.get();
            guestBook.changeTitle("제목변경");
            guestBook.changeContent("내용변경");
            guestBookRepository.save(guestBook);

        }else{
            System.out.println("데이터가 존재하지 않습니다.");
        }

    }

    //데이터 삭제 테스트
    //@Test
    public void deleteTest(){
        //데이터 1개 찾아오기
        Optional <GuestBook> result =
                guestBookRepository.findById(601L);

        //nullpoint 없애주기..ㅠ

        //데이터가 존재하는 경우 수정
        if(result.isPresent()){
            guestBookRepository.delete(result.get());
        }else{
            System.out.println("데이터가 존재하지 않습니다.");
        }

    }


    //데이터 전체 가져오기 테스트 : findAll.
   //@Test
    public void selectAllTest(){
        List<GuestBook> list =
                guestBookRepository.findAll();

        for(GuestBook guestBook : list){
            System.out.println(guestBook);
        }

    }

    //querydsl//////////////////////////////////

    //데이터 querydsl 을 이용한 조회
    //gno의 내림차순 정렬 후 0 페이지 10 을 가져오기
    //pageable 객체 생성

    //@Test
    public void testQuery1() {
        Pageable pageable = PageRequest.of(
                    0, 10, Sort.by("gno").descending());

        /*Entity에 동적 쿼리를 수행할 수 있는 Domain-Class를 찾아오기
        Querydsl 설정을 해야 사용가능 - 컬럼들을 속성으로 포함시켜 조건을 설정할수 있는게 장점.
        */

        QGuestBook qGuestBook = QGuestBook.guestBook;
        String keyword = "1";

        //검색을 적용하기 위한 Builder객체 생성
        BooleanBuilder builder = new BooleanBuilder();

        //조건 표현식 생성
        BooleanExpression expression = qGuestBook.title.contains(keyword); //내용 검색 시 title 부분은 context로 변경


        //검색 객체에 표현식 추가
        builder.and(expression);


        //fina all에 builder, pageable ~
        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }


   // @Test
    public void testQuery2() {
        Pageable pageable = PageRequest.of(
                0, 10, Sort.by("gno").descending());  //데이터갯수 내림차순


        QGuestBook qGuestBook = QGuestBook.guestBook;
        String keyword = "5";
        String keyword2 = "8";

        //검색을 적용하기 위한 Builder객체 생성
        BooleanBuilder builder = new BooleanBuilder();


        //조건 표현식 생성
        BooleanExpression ex1 = qGuestBook.title.contains(keyword); //내용 검색 시 title 부분은 context로 변경
        BooleanExpression ex2 = qGuestBook.content.contains(keyword2);

        BooleanExpression expression = ex1.or(ex2); //2개의 표현식을 or로 연결함 ex1 or ex2


        //검색 객체에 표현식 추가
        builder.and(expression);


        //fina all에 builder, pageable ~
        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }






}
