package com.springbootmybatis.procedure.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.springbootmybatis.procedure.beans.PageBean;
import com.springbootmybatis.procedure.beans.ProductEnum;
import com.springbootmybatis.procedure.beans.RedisEnum;
import com.springbootmybatis.procedure.beans.ResultEnums;
import com.springbootmybatis.procedure.dto.ProductDto;
import com.springbootmybatis.procedure.entity.Category;
import com.springbootmybatis.procedure.entity.Product;
import com.springbootmybatis.procedure.exception.CustomException;
import com.springbootmybatis.procedure.mapper.ProductMapper;
import com.springbootmybatis.procedure.repository.CategoryRepository;
import com.springbootmybatis.procedure.repository.ProductRepository;
import com.springbootmybatis.procedure.utils.RedisUtil;
import com.springbootmybatis.procedure.vo.ProductVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @作者 chenyi
 * @date 2019/5/5 15:43
 */
@Slf4j
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 通过产品id展示产品信息
     * @param productId
     * @return
     */
    public ProductDto getProductInfo(Integer productId){
        Product product = productRepository.findAllByIdAndStatus(productId, ProductEnum.PRODUCT_UP.getCode());
        if (product == null){
            throw new CustomException(ResultEnums.FAIL.getMessage());
        }
        Category category = categoryRepository.findById(product.getCategoryId());
        ProductDto productDto = ProductDto.transfer(product);
        productDto.setCategoryName(category.getName());
        return productDto;
    }

    /**
     * 根据分类id查询商品列表并分页
     * @param cid 分类id
     * @param currentPage 当前页码
     * @param pageSize 每页显示行数
     * @return
     */
    public PageBean<ProductVo> getProductByCid(Integer cid ,Integer currentPage ,Integer pageSize) {
        HashMap<String, Object> hashMap = Maps.newHashMap();
        hashMap.put("cid", cid);
        hashMap.put("currentPage", currentPage);
        hashMap.put("pageSize", pageSize);

        //先从缓存读
        List<ProductVo> posFromRedis =
                (List<ProductVo>) redisUtil.get(RedisEnum.PRODUCT_PREFIX.name()
                        .concat("_productVos_").concat(String.valueOf(currentPage)));
        List<ProductVo> productVos = null;
        if (CollectionUtils.isEmpty(posFromRedis)) {
            productVos = productMapper.findById(hashMap);
            if (CollectionUtils.isEmpty(productVos)) {
                throw new CustomException(ResultEnums.FAIL.getMessage());
            }
            redisUtil.setex(RedisEnum.PRODUCT_PREFIX.name().
                            concat("_productVos_").concat(String.valueOf(currentPage))
                    , productVos, (long) 86400);
        }else {
            productVos = posFromRedis;
        }

        PageBean<ProductVo> pageBean = new PageBean<>();
        pageBean.setTotal((Integer) hashMap.get("total"));
        pageBean.setData(productVos);
        pageBean.setPageCount((Integer) hashMap.get("pageCount"));
        pageBean.setPageNo(currentPage);
        pageBean.setPageSize(pageSize);
        return pageBean;
    }
}
