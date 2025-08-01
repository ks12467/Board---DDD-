package com.hyeonlo.board.global.apipayload;

import com.hyeonlo.board.global.apipayload.status.SuccessStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseResponse<T> {

    private final boolean success;
    private final T data;
    private final String code;
    private final String message;
    private final int status;

    public static <T> BaseResponse<T> success(SuccessStatus successStatus, T data) {
        return new BaseResponse<>(
                true,
                data,
                successStatus.getCode(),
                successStatus.getMessage(),
                successStatus.getStatus()
        );
    }

    public static <T> BaseResponse<T> fail(BaseCode code) {
        return new BaseResponse<>(
                false,
                null,
                code.getCode(),
                code.getMessage(),
                code.getStatus()
        );
    }
}
