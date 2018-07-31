package com.project.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
@Setter
public class StandardQueryParamsWithFilter extends StandardQueryParams {
    @ApiModelProperty(value = "fields to filter via a sample of expected result in a json format.", required = false)
    private String filterByExample = "";
}
