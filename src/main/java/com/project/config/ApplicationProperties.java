package com.project.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Component
@ConfigurationProperties(prefix = "custom.prop", ignoreUnknownFields = false, ignoreNestedProperties = true)
@Validated
public class ApplicationProperties {

    @NotNull
    @Min(1)
    private int maxPageSize;


    private String supportEmail;
}
