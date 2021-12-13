package io.github.julianobrl.cardinalhooks.annotation.enablewebhooks;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.ComponentScan;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ComponentScan
@ComponentScan("io.github.julianobrl.cardinalhooks.webhooks")
public @interface EnableWebhooks {}
