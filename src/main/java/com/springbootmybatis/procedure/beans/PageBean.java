package com.springbootmybatis.procedure.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Min;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class PageBean<T> {

    @Min(value = 1,message = "大于等于1")
    @JsonProperty("currentPage")
    private int pageNo = 1;//当前页

    @JsonProperty("totalCount")
    private Integer total;

    @Min(value = 2,message = "大于等于2")
    private int pageSize = 2;//每页显示几行

    private int pageCount;

    private List<T> data;
}
