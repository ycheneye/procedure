package com.springbootmybatis.procedure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @作者 chenyi
 * @date 2019/5/5 15:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "xmcc_category")
public class Category {
    @Id
    private Integer id;
    private Integer parentId;
    private String name;
    private Integer status;
    private Integer sortOrder;
    private Date createTime;
    private Date updateTime;
}
