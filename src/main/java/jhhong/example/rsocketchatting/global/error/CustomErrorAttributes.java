package jhhong.example.rsocketchatting.global.error;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errors = new HashMap<>();

        Throwable error = getError(request);
        if(error instanceof GlobalException exception) {
            errors.put("message", exception.getMessage());
            errors.put("status", exception.getErrorCode());
        }

        errors.put("message", error.getCause());
        errors.put("status", 500);

        return errors;
    }
}
