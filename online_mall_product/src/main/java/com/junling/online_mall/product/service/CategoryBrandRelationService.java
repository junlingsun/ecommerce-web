package com.junling.online_mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.junling.common.utils.PageUtils;
import com.junling.online_mall.product.entity.CategoryBrandRelationEntity;
import com.junling.online_mall.product.entity.CategoryEntity;
import com.junling.online_mall.product.vo.BrandVo;

import java.util.List;
import java.util.Map;

/**
 * Ʒ?Ʒ???????
 *
 * @author Junling
 * @email junlingsun1983@gmail.com
 * @date 2021-02-19 23:40:58
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

    void updateBrand(Long brandId, String brandName);

    void updateCategory(CategoryEntity category);

    List<BrandVo> getBrands(Long catId);

}

