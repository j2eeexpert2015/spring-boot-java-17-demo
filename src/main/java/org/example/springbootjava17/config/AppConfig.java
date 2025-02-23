package org.example.springbootjava17.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@ConfigurationProperties(prefix = "app")
public record AppConfig(int number, @NotNull Page page) {

    public record Page(@Positive int maxSize) {   }
}