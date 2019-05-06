package com.springbootmybatis.procedure.mapper;

import com.springbootmybatis.procedure.vo.ProductVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


/**
 * @作者 chenyi
 * @date 2019/5/5 17:31
 */
@Mapper
public interface ProductMapper {
    List<ProductVo> findById(Map<String, Object> map);
}
