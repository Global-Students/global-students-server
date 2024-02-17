package com.example.globalStudents.global.apiPayload.code.status;

import com.example.globalStudents.global.apiPayload.code.BaseErrorCode;
import com.example.globalStudents.global.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // JOIN Error
    JOIN_NICKNAME_ALREADY_EXIST(HttpStatus.CONFLICT, "CHECK409_2", "이미 존재하는 닉네임입니다 - 중복된 닉네임인 경우"),
    JOIN_ID_ALREADY_EXIST(HttpStatus.CONFLICT, "CHECK409_1", "이미 존재하는 아이디입니다 - 중복된 아이디인 경우"),
    JOIN_ID_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "JOIN400_1", "아이디는 8자 이상의 영문 대소문자/숫자/특수문자입니다 - id 파라미터 형식이 잘못된 경우"),
    JOIN_TERMS_INCOMPLETE(HttpStatus.BAD_REQUEST, "JOIN400_5", "동의하지 않은 필수 약관이 존재합니다 - 필수 약관을 동의하지 않은 경우"),
    JOIN_PASSWORD_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "JOIN400_2", "비밀번호는 8자 이상의 영문 대소문자/숫자/특수문자입니다 - password 파라미터 형식이 잘못된 경우"),
    JOIN_PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "JOIN400_3", "비밀번호가 일치하지 않습니다 - confirmPassword와 password 파라미터가 일치하지 않은 경우"),
    JOIN_INFORMATION_INCOMPLETE(HttpStatus.BAD_REQUEST, "JOIN400_4", "입력하지 않은 필수 정보가 존재합니다 - 필수 정보가 누락된 경우"),
    JOIN_EMAIL_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "VERIFY400_1", "잘못된 이메일 형식입니다 - email 파라미터를 재입력하는 경우"),
    JOIN_EMAIL_INVALID_SOURCE(HttpStatus.BAD_REQUEST, "VERIFY400_2", "학생 혹은 교육기관의 이메일이 아닙니다 - email 파라미터를 재입력하는 경우"),
    JOIN_CODE_INVALID(HttpStatus.BAD_REQUEST, "VERIFY400_2", "잘못된 인증번호입니다 - code 파라미터를 재입력하는 경우"),
    LOGIN_ERROR(HttpStatus.BAD_REQUEST, "LOGIN400_1", "아이디 혹은 비밀번호를 재입력하세요 - 아이디 혹은 비밀번호가 잘못된 경우"),
    FIND_PASSWORD_EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "VERIFY400_3", "등록된 이메일이 존재하지 않습니다 - 이메일을 재입력하는 경우"),
    FIND_PASSWORD_INVALID_CODE(HttpStatus.BAD_REQUEST, "VERIFY400_2", "잘못된 인증번호입니다 - code 파라미터를 재입력하는 경우"),
    FIND_PASSWORD_EXPIRED_CODE(HttpStatus.BAD_REQUEST, "VERIFY400_2", "잘못된 인증번호입니다 - code 파라미터를 재입력하는 경우"),
    RESET_PASSWORD_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "LOGIN400_6", "잘못된 형식입니다 - confirm_password가 잘못된 형식인 경우"),
    FIND_ID_EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "LOGIN400_2", "등록된 이메일이 존재하지 않습니다 - email이 등록되어 있지 않은 경우"),
    FIND_ID_EMAIL_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "LOGIN400_3", "잘못된 형식입니다 - email이 잘못된 형식인 경우"),
    LOGGED_OUT(HttpStatus.BAD_REQUEST, "LOGOUT400_1", "이미 로그아웃된 상태입니다"),

    // Auth error
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN401_1", "만료된 토큰입니다"),
    TOKEN_MALFUNCTION(HttpStatus.UNAUTHORIZED, "TOKEN401_2", "부적절한 토큰입니다"),
    TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "TOKEN401_3", "토큰 에러"),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN401_4", "만료된 refresh 토큰입니다"),
    COOKIE_NOT_FOUND(HttpStatus.UNAUTHORIZED, "COOKIE400_1", "쿠키에 refresh 토큰이 없습니다"),
    TOKEN_NOT_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN400_1", "유효한 토큰입니다"),
    BANNED(HttpStatus.UNAUTHORIZED, "ACCOUNT400_1", "잠긴 계정입니다"),

    // Chat error
    CHAT_NOT_FOUND(HttpStatus.BAD_REQUEST, "CHAT400_1", "채팅방을 불러올 수 없습니다. - 채팅방이 조회되지 않는 경우"),
    CHAT_MESSAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "CHAT400_2", "메세지를 입력하세요 - 빈 메세지를 전송하려할 때"),
    CHAT_MESSAGE_FAIL(HttpStatus.BAD_REQUEST, "CHAT400_3", "메세지 전송에 실패하였습니다 - chat_room_id, user_id, friend_id를 조회할 수 없을 때"),
    CHAT_EMPTY_MESSAGE(HttpStatus.BAD_REQUEST, "CHAT400_4", "채팅방 메세지 전송 실패 - 메세지 내용이 비어 있습니다"),

    // Mypage error
    MYPAGE_INTRODUCE_NOT_EXIST(HttpStatus.BAD_REQUEST, "MYPAGE401_1", "프로필 수정 실패 - 소개글 항목 누락"),
    MYPAGE_SKILL_NOT_EXIST(HttpStatus.BAD_REQUEST, "MYPAGE401_2", "프로필 수정 실패 - Skill 항목 누락"),
    MYPAGE_PASSWORD_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "MYPAGE401_3", "내 정보 수정 실패 - 비밀번호는 8자 이상의 영문 대소문자/숫자/특수문자입니다 - password 파라미터 형식이 잘못된 경우"),
    MYPAGE_NICKNAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "MYPAGE401_4", "내 정보 수정 실패 - 닉네임이 중복됨"),
    MYPAGE_PHOTO_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "MYPAGE401_5", "사진 업로드 실패 - 사진 파일 형식 오류"),
    MYPAGE_PHOTO_INVALID_SIZE(HttpStatus.PAYLOAD_TOO_LARGE, "MYPAGE401_6", "사진 업로드 실패 - 사진 파일 용량 제한"),

    // Board error
    BOARD_BOARD_ID_INVALID(HttpStatus.BAD_REQUEST, "BOARD400_1", "잘못된 게시판 ID입니다 - board_id가 잘못된 형식일 때"),
    BOARD_QUERY_PARAMETER_INVALID(HttpStatus.BAD_REQUEST, "BOARD400_2", "잘못된 쿼리 형식입니다 - query parameter가 잘못된 형식일 때"),
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "BOARD404_1", "존재하지 않는 게시판입니다 - 삭제된 게시판 또는 요청한 게시판을 찾을 수 없는 경우"),
    COMMENT_POST_ID_INVALID(HttpStatus.BAD_REQUEST, "COMMENT400_1", "잘못된 게시글 ID입니다 - post_id가 잘못된 형식일 때"),
    COMMENT_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "COMMENT400_2", "댓글은 최소 1자 이상 300자 이내여야 합니다 - 댓글 내용이 비어있거나 글자수가 초과될 때"),
    COMMENT_COMMENT_ID_INVALID(HttpStatus.BAD_REQUEST, "COMMENT400_3", "잘못된 댓글 ID입니다 - comment_id가 잘못된 형식일 때"),
    COMMENT_ALREADY_LIKED(HttpStatus.BAD_REQUEST, "COMMENT403_2", "이미 좋아요한 댓글입니다 - 이미 좋아요한 경우"),
    REPORT_COMMENT_ID_INVALID(HttpStatus.BAD_REQUEST, "REPORT400_1", "잘못된 댓글 ID입니다 - comment_id가 잘못된 형식일 때"),
    REPORT_ALREADY_REPORTED(HttpStatus.BAD_REQUEST, "REPORT403_2", "이미 신고한 내용입니다 - 댓글 또는 게시글을 이미 신고한 경우"),
    POST_IMAGE_ID_INVALID(HttpStatus.BAD_REQUEST, "IMAGE400_1", "잘못된 이미지 ID 입니다 - image id 가 잘못된 형식인 경우"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST404_1", "존재하지 않는 게시글입니다 - 삭제된 게시글 또는 요청한 게시글을 찾을 수 없는 경우"),
    POST_ALREADY_REACTED(HttpStatus.BAD_REQUEST, "POST403_2", "이미 좋아요/즐겨찾기 한 게시글입니다 - 중복된 요청인 경우"),

    //Admin error
    ADMIN_ALREADY_HANDLED_REPORT(HttpStatus.BAD_REQUEST, "ADMIN403_1", "이미 처리한 신고입니다 - 신고 처리를 이미 한 경우"),

    //search error
    SEARCH_UNIVERSITY_ISEMPTY(HttpStatus.NOT_FOUND,"SEARCH404_1","조회된 학교가 없습니다."),

    SEARCH_TOTAL_ISEMPTY(HttpStatus.NOT_FOUND,"SEARCH404_2","조회된 검색 결과가 없습니다.");



    // ~~~ 관련 응답 ....


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
