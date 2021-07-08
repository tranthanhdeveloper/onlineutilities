package net.onlineutilities.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
public class RestEnvelopeResponse {
    private Map<String, String> errors;
    private Object data;
    private String statusMessage;
    private int statusCode;
}
