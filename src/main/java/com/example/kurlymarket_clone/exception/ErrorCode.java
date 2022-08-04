package com.example.kurlymarket_clone.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    ID_DUPLICATION_CODE(400, "C001", "중복된 id가 있습니다"),
    ID_LENGTH_CODE(400, "C002", "아이디는 3자 이상 입력해주세요"),
    ID_FORM_CODE(400, "C003", "아이디는 알파벳 대소문자와 숫자로만 입력해주세요"),
    //response.message -> then
    PASSWORD_CHECK_CODE(400, "C004", "패스워드와 패스워드 확인이 일치하지 않습니다"),
    PASSWORD_LENGTH_CODE(400, "C005", "패스워드는 4글자 이상 입력해주세요"),
    PASSWORD_INCLUDE_CODE(400, "C006", "비밀번호에 아이디가 포함될 수 없습니다"),
    LOGIN_CODE(400, "C007", "아이디 비밀번호를 확인해 주세요"),
    NOT_FOUND_USER(404, "C008", "유저를 찾을 수 없습니다. 로그인을 다시 해주세요."),
    NOT_FOUND_POST(404, "C009", "해당 물건이 존재하지 않습니다."),
    NOT_FOUND_ITEM(404, "C010", "장바구니에 물건이 존재하지 않습니다.");
    private final int status;
    private final String code;
    private final String message;
    ErrorCode(int status, String code, String message){ //enum 은 생성자가 존재하지만 Default 생성자는 private 로 되어 있으며 public 으로 변경하는 경우 컴파일 에러가 발생
                                                                //다른 클래스나 인터페이스에서의 상수선언이 클래스 로드 시점에서 생성되는 것 처럼 Enum 또한 생성자가 존재하지만
                                                                // 클래스가 로드되는 시점에서 생성되기 때문에 임의로 생성하여 사용 할 수 없다
        this.status = status;
        this.code = code;
        this.message = message;
    }

}

