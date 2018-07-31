package com.project.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

@Getter
@Validated
@Setter
public class StandardQueryParams {

    @Min(0)
    @ApiModelProperty(value = "Page Number", required = false)
    private int fromRecords = 1;

    @Min(0)
    @ApiModelProperty(value = "Number of records to show", required = false)
    private int pageSize = Integer.MAX_VALUE;


    @ApiModelProperty(value = "Comma delimited list of sort by fields. Prefix a column with '-' for descending order", required = false, example = "-firstName,surname")
    private String sort = null;

    // private String filter; // TODO think about this one later

}
