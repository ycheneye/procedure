package com.springbootmybatis.procedure.repository;

import com.springbootmybatis.procedure.entity.Product;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @作者 chenyi
 * @date 2019/5/5 15:44
 */
@Repository
public interface ProductRepository extends JpaRepository<Product,String> {
    Product findAllByIdAndStatus(Integer productId,Integer status);
}
