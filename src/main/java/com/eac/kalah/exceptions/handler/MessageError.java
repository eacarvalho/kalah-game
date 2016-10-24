package com.eac.kalah.exceptions.handler;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageError implements Serializable {

    private static final long serialVersionUID = -4728781351505492203L;

    private List<String> errors;

    public MessageError(List<String> errors) {
        this.errors = errors;
    }

    public MessageError(String error) {
        this(Collections.singletonList(error));
    }

    public MessageError(String... errors) {
        this(Arrays.asList(errors));
    }
}