package kr.or.ddit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.vo.Address;
import kr.or.ddit.vo.Card;
import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/ajax")
@Slf4j
public class AjaxMemberController {
	@RequestMapping(value="/registerForm", method=RequestMethod.GET)
	public String ajaxRegisterForm() {
		log.info("ajaxRegisterForm() 실행...!");
		return "member/ajaxRegisterForm";
	}
	
	@RequestMapping(value="/register/{userId}")
	public ResponseEntity<String> ajaxRegister01(@PathVariable("userId")String userId){
		log.info("ajaxRegister01() 실행...!");
		log.info("userId : "+userId);
		
		ResponseEntity<String> entity = new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		return entity;
	}
	
	@RequestMapping(value="/register/{userId}/{password}", method = RequestMethod.POST)
	public ResponseEntity<String> ajaxRegister02(@PathVariable("userId")String userId, @PathVariable("password")String password){
		log.info("ajaxRegister02() 실행...!");
		log.info("userId : "+userId);
		log.info("password : "+password);
		
		return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
	}
	
	//객체타입의 JSon요청데이터 @RequestBody어노테이션을 지정하여 자바빈즈 매개변수로 처리한다.
	@RequestMapping(value="/register03", method = RequestMethod.POST)
	public ResponseEntity<String> ajaxRegister03(@RequestBody Member member){
		log.info("ajaxRegister03() 실행...!");
		log.info("userId : "+ member.getUserId());
		log.info("password : "+member.getPassword());
		
		return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
	}
	//객체타입의 JSon요청데이터는 문자열 매개변수로 처리할 수 없다.
	@RequestMapping(value="/register04", method = RequestMethod.POST)
	public ResponseEntity<String> ajaxRegister04(String userId ){
		// 요청 본문에서 데이터가 바인딩 되지않아 userId가 null이나온다.
		
		log.info("ajaxRegister04() 실행...!");
		log.info("userId : "+ userId);
		
		return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
	}
	//요청 URL에 쿼리파라미터를 붙여서 전달하면 문자열 매개변수로 처리한다.
	@RequestMapping(value="/register05", method = RequestMethod.POST)
	public ResponseEntity<String> ajaxRegister05(String userId,String password ){
		//userId는 쿼리스트링에 담겨져오는 데이터이기 떄문에, 일반적인 방식으로도 데이터를 받을 수 잇지만,
		//password는 요청본문에 데이터를 바인디해 받아오지 못하므로 null이 출력된다.
		log.info("ajaxRegister05() 실행...!");
		log.info("userId : "+ userId);
		log.info("password : "+ password);
		
		return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
	}
	//객체타입의 JSON요청 데이터를 @Pathvariable 어노테이션을 지정한 문자열 매개변수와 @RequestBody 어노테이션을 지정한 자바빈즈 매개변수로 처리한다.
	@RequestMapping(value="/register06/{userId}", method = RequestMethod.POST)
	public ResponseEntity<String> ajaxRegister06(@PathVariable("userId")String userId,@RequestBody Member member ){
		log.info("ajaxRegister06() 실행...!");
		log.info("userId : "+ userId);
		log.info("member.getUserId : "+ member.getUserId());
		log.info(" member.getPassword(): "+ member.getPassword());
		
		return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
	}
	//객체배열타입의 JSON요청 데이터를 자바빈즈 요소를 가진 리스트 컬렉션 매개변수에 @RequestBody어노테이션을 지정하여 처리한다.
	@RequestMapping(value="/register07", method = RequestMethod.POST)
	public ResponseEntity<String> ajaxRegister07(@RequestBody List<Member> memberList ){
		log.info("ajaxRegister07() 실행...!");
		for (Member member : memberList) {
			log.info("member.userId : "+member.getUserId());
			log.info("member.getPassword() : "+member.getPassword());
		}		
		return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
	}
	
	@RequestMapping(value="/register08", method = RequestMethod.POST)
	public ResponseEntity<String> ajaxRegister08(@RequestBody Member member ){
		log.info("ajaxRegister08() 실행...!");
		log.info("userId : "+member.getUserId());
		log.info("Password() : "+member.getPassword());
		
		Address address = member.getAddress();
		if(address != null) {
			log.info("postCode : "+address.getPostCode());
			log.info("location : "+address.getLocation());
		}else {
			log.info("address is null");
		}
		return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);		
	}
	
	@RequestMapping(value="/register09", method = RequestMethod.POST)
	public ResponseEntity<String> ajaxRegister09(@RequestBody Member member ){
		log.info("ajaxRegister09() 실행...!");
		log.info("userId : "+member.getUserId());
		log.info("Password() : "+member.getPassword());
		
		List<Card> cardList = member.getCardList();
		if(cardList != null) {
			log.info("cardList.size (): "+ cardList.size());
		for(int i=0; i< cardList.size(); i++) {
			log.info("cardNo () : "+cardList.get(i).getNo());
			log.info("vaildMonth () : "+cardList.get(i).getValidMonth());
		}
		}else {
			log.info("cardList is null");
		}
		return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);		
	}


}
