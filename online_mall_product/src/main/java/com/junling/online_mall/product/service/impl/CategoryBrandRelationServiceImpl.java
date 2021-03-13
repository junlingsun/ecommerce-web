package com.junling.online_mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.junling.online_mall.product.dao.BrandDao;
import com.junling.online_mall.product.dao.CategoryDao;
import com.junling.online_mall.product.entity.CategoryEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.junling.common.utils.PageUtils;
import com.junling.common.utils.Query;

import com.junling.online_mall.product.dao.CategoryBrandRelationDao;
import com.junling.online_mall.product.entity.CategoryBrandRelationEntity;
import com.junling.online_mall.product.service.CategoryBrandRelationService;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private BrandDao brandDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        Long catId = categoryBrandRelation.getCatelogId();
        Long brandId = categoryBrandRelation.getBrandId();
        String catName = categoryDao.selectById(catId).getName();
        String brandName = brandDao.selectById(brandId).getName();
        categoryBrandRelation.setBrandName(brandName);
        categoryBrandRelation.setCatelogName(catName);
        this.save(categoryBrandRelation);

    }

    @Override
    public void updateBrand(Long brandId, String brandName) {
        UpdateWrapper<CategoryBrandRelationEntity> wrapper = new UpdateWrapper<>();
        wrapper.eq("brand_id", brandId);
        CategoryBrandRelationEntity entity = new CategoryBrandRelationEntity();
        entity.setBrandId(brandId);
        entity.setBrandName(brandName);
        this.update(entity,wrapper);
    }

    @Override
    public void updateCategory(CategoryEntity category) {
        this.baseMapper.updateCategory(category.getCatId(), category.getName());
    }
}