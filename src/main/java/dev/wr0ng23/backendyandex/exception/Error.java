package dev.wr0ng23.backendyandex.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Error {
    private int code;
    private String message;
}
