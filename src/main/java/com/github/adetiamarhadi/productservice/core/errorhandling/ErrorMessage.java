package com.github.adetiamarhadi.productservice.core.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class ErrorMessage {

    private final Date timestamp;
    private final String message;
}
