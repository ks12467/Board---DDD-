package com.hyeonlo.board.global.error;

import com.hyeonlo.board.global.apipayload.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BaseException extends RuntimeException{

    private final BaseCode code;
}
