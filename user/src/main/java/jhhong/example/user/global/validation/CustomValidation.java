package jhhong.example.user.global.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class CustomValidation {

    private final Validator validator;

    public <T> Mono<T> validate(T target) {
        return Mono.just(new BeanPropertyBindingResult(target, "target"))
                .doOnNext(err -> validator.validate(target, err))
                .filter(err -> !err.hasErrors())
                .flatMap(err -> Mono.just(target))
                .switchIfEmpty(Mono.error(BadRequestException::new));
    }

}
