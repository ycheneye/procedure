package com.springbootmybatis.procedure.dto;

import com.springbootmybatis.procedure.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者 chenyi
 * @date 2019/5/5 17:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto extends Category{
    private List<CategoryDto> categoryList = new ArrayList<>();
    //把SysDept的数据封装到SysDeptDto
    public static CategoryDto transfer(Category category){
        CategoryDto categoryDto = new CategoryDto();
        BeanUtils.copyProperties(category, categoryDto);
        return categoryDto;
    }
}
