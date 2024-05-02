package com.mirae.commerce.auth.jwt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Controller 내 메소드의 Parameter에 사용 시, JWT토큰으로부터 파싱된 정보가 해당 Parameter의 username필드로 주입됨
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface JwtUsernameInject {
}
