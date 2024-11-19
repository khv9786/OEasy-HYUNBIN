package com.OEzoa.OEasy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalExceptionCode {
    //---------------global-------------------
    EXAMPLE_EXCEPTION(HttpStatus.BAD_REQUEST, "사용자에게 보여줄 메시지", "개발자가 볼 메시지"),
    DB_LOAD_FAILURE(HttpStatus.SERVICE_UNAVAILABLE, "[알 수 없는 오류] 잠시 후 다시 시도해 주세요.", "데이터 베이스에서 값을 가져오는 것을 실패"),

    //---------------index--------------------
    INDEX_API_REQUEST_FAILURE(HttpStatus.SERVICE_UNAVAILABLE, "[일시적 오류] 잠시 후 다시 시도해 주세요.", "API 호출 실패"),

    //---------------tip----------------------

    //---------------member---------------------
    NOT_FIND_MEMBER(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다", "잘못 들어온 pk"),

    //---------------sign up-------------------
    EMAIL_DUPLICATION(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다.", "중복된 이메일 요청"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호는 필수 항목입니다.", "비밀번호 입력 누락"),
    INVALID_VALID_SENTENCE(HttpStatus.BAD_REQUEST, "탈퇴 문구를 정확히 입력하세요.", "올바르지 않은 탈퇴 문구"),
    //---------------nickname-------------------
    NICKNAME_EMPTY(HttpStatus.BAD_REQUEST, "닉네임을 입력해주세요.", "닉네임이 null이거나 빈 문자열"),
    NICKNAME_TOO_LONG(HttpStatus.BAD_REQUEST, "닉네임은 최대 8글자까지 가능합니다.", "닉네임의 길이가 허용된 범위를 초과"),
    NICKNAME_DUPLICATION(HttpStatus.CONFLICT, "이미 사용 중인 닉네임입니다.", "이미 있는 닉네임으로 변경 요청"),
    INVALID_OLD_PASSWORD(HttpStatus.BAD_REQUEST, "기존 비밀번호가 일치하지 않습니다.", "사용자가 입력한 기존 비밀번호가 틀림"),
    //---------------recipe-------------------
    RECIPE_OUT_OF_VALID_RANGE(HttpStatus.BAD_REQUEST, "범위를 벗어났습니다", "범위를 벗어난 데이터를 요청"),
    RECIPE_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "id를 확인해 주세요", "요청한 id에 해당하는 값이 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final String description;



}
