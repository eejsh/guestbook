package com.adamsoft.guestbook.controller;


import com.adamsoft.guestbook.domain.GuestBookDTO;
import com.adamsoft.guestbook.domain.PageRequestDTO;
import com.adamsoft.guestbook.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
public class GuestBookController {

  private final GuestBookService guestBookService;

  /*
  @GetMapping({"/", "/guestbook/list"}) //404? 여기에 주소가 없거나..

    public String list(){
      log.info("list.....");

      return "guestbook/list";  //return 이 틀리거나 경로확인.


  }*/


  //기본요청 처리하는 곳과 목록 보기 요청 처리 나누기!

  @GetMapping({"/"})
  public String main(){
    log.info("/");

    return "redirect:/guestbook/list";

  }

//컨트롤러에서 view 리턴 시 앞에 / 붙으면 경로 못찾을 경우가 있을수있음..

  @GetMapping({"/guestbook/list"})
  public void list(PageRequestDTO dto, Model model){
  log.info("list..");

    //서비스 메서드 호출
    //result 의 dtoList에 DTO 의 List가 있고
    //result 의 pagalist에 페이지번호의 List 가 존재.
    model.addAttribute("result", guestBookService.getList(dto));

  }

  //등록 요청을 get방식으로 처리하는 메서드 - 등록페이지로 이동
  @GetMapping("/guestbook/register")
  public void register(){
    log.info("register GET.......");
  }

  //등록 요청을 POST 방식으로 처리하는 메서드 - 실제로 등록 수행
  @PostMapping( "/guestbook/register")
  public String registerPost(GuestBookDTO dto, RedirectAttributes redirectAttributes){ //삽입은 dto로..
    log.info("register post.......");
    Long gno = guestBookService.register(dto);

    //데이터 저장 .. 세션과 유사하지만 1번만사용되는 redirectattribute사용.
    redirectAttributes.addFlashAttribute("msg", gno+"등록");

  //목록보기로 이동...
    return "redirect:/guestbook/list";

  }



  //상세보기 요청 처리
  //ModelAttribute : 매개변수를 결과 페이지에 넘겨줄 때 사용. 어떤 처리를 안하고 바로 넘겨줄 경우 modelattribute 사용가능

  //@modelAttribute : pagerequest에서 받은 변수를 requestDTO로 자동으로 전달됨!

  @GetMapping("/guestbook/read")
  public void read(long gno,
                   @ModelAttribute("requestDTO")
                   PageRequestDTO requestDTO, Model model){

    GuestBookDTO dto = guestBookService.read(gno);
    model.addAttribute("dto", dto);

  }


}


//URL 과 view 파일을 먼저 확인 후 코드작성하는게 좋습니다..



