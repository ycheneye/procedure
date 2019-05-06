package com.springbootmybatis.procedure.repository;

import com.springbootmybatis.procedure.entity.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @作者 chenyi
 * @date 2019/5/5 16:13
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category,String> {
    Category findById(@Param("categoryId") Integer categoryId);
}
