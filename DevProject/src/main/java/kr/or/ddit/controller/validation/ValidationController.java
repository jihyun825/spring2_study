package kr.or.ddit.controller.validation;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.vo.Address;
import kr.or.ddit.vo.Card;
import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/validation")
@Slf4j
public class ValidationController {

	
	/*
	 * 1. 입력값 검증
	 *  - 스프링 MVC Bean Validation 기능을 이용해 요청 파라미터 값이 바인딩된 도메인 클래스(또는 커멘드클래스)의 입력값 검증을 한다.
	
	 * [환경설정
	 * #의존관계 정의
	 *  - 입력값 검즈을 위한 의존 라이브러리를 추가한다.
	 *  - pom.xml 에서 hidernate - validator추가
	 *  
	 *  #입력값 검증활성화
	 *   - Member클래스로 넘어가서 임시 테스르토  userId, userName에 규칙을 활성화한다.
	 *   - 이때, 규칙을 활성화 하기 위해서 사용할 어노테이션이 있다.
	 *   
	 *   @validated를 지정한다.
	 *    > 입력값 검증 대상의 도메인 클래스 직후에 BindingResult를 정의한다.
	 *    > Binding Result에는 요청 데이터의 바인딩 오류와 입력값 검증 오류 정보가 저장된다.
	 *    
	 *    #입력값 검증 환경설정 순서
	 *    
	 *    1. 입력값 검증을 위한 의존 라이브러리를 추가
	 *    2. 입력값 검증 활성화
	 *    	> 활성화를 할 도메인 클래스안에 @validated 어노테이션을 지정한다.
	 *    3. 도메인클래스 내 필드에다가 검증을 위한 어노테이션들로 데이터 검증을 설정한다.(@ NotBlank, @Size등등)
	 *    4. 에러를 받을 BindingResult를 설정한다.(컨트롤러 메서드 내에 설정합니다)
	 *    
	 */
	@RequestMapping(value="/registerValidationForm01",method=RequestMethod.GET)
	public String regsiterValidationForm01(Model model) {
		model.addAttribute("member", new Member());
		return "validation/registerValidationForm01";
		
	}
	@RequestMapping(value="/result", method=RequestMethod.POST)
	public String registerValidatedFormResult(@Validated Member member, BindingResult result) {
		log.info("member.userId: "+member.getUserId());
		log.info("member.userName: "+member.getUserName());
		
		if(result.hasErrors()) {
			return "validation/registerValidationForm01";
		}
		return "validation/success";
	}
	
	/*
	 * 2. 입력값 검증결과
	 *  - 입력값 검증 대상의 도메인 클래스 직후에 Bindingresult를 정의한다.
	 *  
	 *  	# BindingResult에는 요청데이터의 바인딩에러와 입력값 검증 에러정보가 저장된다.
	 *  
	 *  1) 에러정보확인을 위한 BindingResult메서드
	 *   	hasErrors()
	 *   - 에러가 발생한경우 true를 반환한다.
	 *   hasGlobalErrores()
	 *   - 객체 레벨의 에러가 발생한 경우 true를 반환한다.
	 *   hasFieldErrors()
	 *   - 필드레벨의 에러가 발생한경우 true를 반환한다.
	 *   hasFieldErrors(String)
	 *   - 인수에 지정한 필드에서 에러가 발생한 경우 true를 반환한다.
	 */
	@RequestMapping(value="/registerValidationForm02", method=RequestMethod.GET)
	public String registerValidationForm02(Model model) {
		model.addAttribute("member",new Member());
		return "validation/registerValidationForm02";
	}
	
	@RequestMapping(value="/result2", method=RequestMethod.POST)
	public String registerValidationForm02Result(@Validated Member member, BindingResult result) {
		log.info("registerValidationForm02Result()실행...!");
		log.info("result.hasError: "+ result.hasErrors());
		log.info("member.userName: "+member.getUserName());
		
		if(result.hasErrors()) {
			List<ObjectError> allErrors = result.getAllErrors();
			List<ObjectError> globalErrors = result.getGlobalErrors();
			List<FieldError> fieldErrors = result.getFieldErrors();
			
			log.info("allErrors.size():"+allErrors.size());
			log.info("globalErrors.size():"+globalErrors.size());
			log.info("fieldErrors.size():"+fieldErrors.size());
			
			//r객체와 필드레벨의 에러정보 모두를 출력한다.
			for(int i=0; i<allErrors.size(); i++) {
				ObjectError objectError = allErrors.get(i);
				log.info("allError= "+objectError+"\n");
			}
			//r객체레벨의 에러정보 모두를 출력한다.
			for(int i=0; i<globalErrors.size(); i++) {
				ObjectError objectError = globalErrors.get(i);
				log.info("globalErrors= "+objectError+"\n");
			}
			//필드레벨의 에러정보 모두를 출력한다.
			for(int i=0; i<fieldErrors.size(); i++) {
				FieldError fieldError = fieldErrors.get(i);
				log.info("fieldErrors= "+fieldError);
				log.info("fieldError.getDefaultMessage()= "+fieldError.getDefaultMessage());
				
			}
			return "validation/registerValidationForm02";
		}
		log.info("userId:"+member.getUserId());
		log.info("getUserName:"+member.getUserName());
		log.info("getEmail:"+member.getEmail());
		log.info("getGender:"+member.getGender());
		
		return "validation/success";
	}
	
	/*
	 * 3. 입력값 검증 규칙
	 *  -  입력값 검증 규칙은 bean validation이 제공하는 제약 어노테이션으로 설정한다.
	 *  
	 *  검사 규칙은 크게 다음 세가지로 분류할수있다.
	 *   - Bean Validation 표준 제약 어노테이션
	 *   - 서드파티에서 구현한 제약 어노테이션(서트파티란 제3자에서 만든...)
	 *   - 직접 구현한 제약 어노테이션
	 *   
	 *   1) Member 클래스에서 테스르틑 위한 어노테이션으로 설정(아래 명시되어있는 내용들을 가지고 테스트 해보세요들!!!)
	 *   @NotNull : 빈값이 아닌지를 검사한다.
	 *   @Null : null인지를 검사한다.
	 *   @Not Blank : 문자열이null이 아니고 trim한 길이가 0보다크다는것을 검사한다.
	 *   @Not empty :  문자열이 null이거나 비어있는지 검사한다.
	 *   @size 		: 글자수나 컬렉션의 요소 개수를 검사한다.
	 *   	>@Size(max=, min=) 
	 *   @Max(valud=) : value보다 작거나 같은걸 검사한다.
	 *   @Min(valud=) : value보다 크거나 같은걸 검사한다.
	 *   @Email :email주소형식인지 검사한다.
	 *   @Past : 과거 날짜인지를 검사한다.
	 *   @Futuer : 미래 날짜인지를 검사한다.
	 *   @Pattern : CharSequence 는 지정된 정규식과 일치해야하고, 정규식은 JAVA 정규식 규칙을 따른다.
	 *   @Positive :  양수여야합니다.
	 *   @PositiveOrZero : 양수또는 0이어야합ㄴ디ㅏ.
	 *   @Length(min=,max=) : 문자열의 길이가 min과 max사이인지확인합니다.
	 *   
	 *   
	 *   [테스트]
	 *   -Member 클래스의 검증 활성화 추가
	 *    > userId, password, userName, email , dateOfBirth
	 *    - 테스트 uRL : /validation/registernvalidationForm02로 요청해서 테스트를 진행해봅시다.
	 *    
	 *    4. 중첩된 자바빈즈 입력값 검증
	 *    - 중첩된 자바빈즈와 자바빈즈의 컬렉션에서 정의한 프로퍼티에대해 입력값 검증을 할때 @valid를 지정한다.
	 *    
	 *    1) 중첩된 자바빈즈 클래스를 정의하고 @valid를 지정한다.
	 *     - Member 클래스 내 Address address 필드에 @Valid 어노테이션을 지정
	 *     - Member 클래스 내 List<card> cardlist필드에 @valid어노테이션 지정
	 *     
	 *     2)Address클래스엗 validation을 설정한다.'
	 *     3)Card클래스에도 validation을 설정한다.
	 *     
	 *     
	 */
	
	
	@RequestMapping(value="/registerValidationForm03", method=RequestMethod.GET)
	public String registerValidationForm(Model model) {
		log.info("registerValidationForm()실행...!");
		model.addAttribute("member", new Member());
		return "validation/registerValidationForm03";
		
	}
	
	@RequestMapping(value="/result3", method = RequestMethod.POST)
	public String registerValidationResult3(@Validated Member member, BindingResult result) {
		log.info("registerValidationResult3()실행...!");
		if(result.hasErrors()) {
			return "validation/registerValidationForm03";
		}
		log.info("userId: "+ member.getUserId());
		log.info("dateOfBirth: "+ member.getDateOfBirth());
		
		Address address = member.getAddress();
		if(address != null) {
			log.info("postCode: "+ address.getPostCode());
			log.info("location: "+ address.getLocation());
			
		}
		
		List<Card> cardList = member.getCardList();
		
		if(cardList != null) {
			for(int i=0; i<cardList.size(); i++) {
				Card card = cardList.get(i);
				log.info("no:"+card.getNo());
				log.info("validMonth:"+card.getValidMonth());
			}
		}
		return "validation/success";
}
	
	
	
	
	
}
	
	
	
	
	
	
	
	
	
	
