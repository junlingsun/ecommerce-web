package com.junling.online_mall.product.service.impl;

import com.junling.online_mall.product.service.CategoryBrandRelationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.junling.common.utils.PageUtils;
import com.junling.common.utils.Query;

import com.junling.online_mall.product.dao.BrandDao;
import com.junling.online_mall.product.entity.BrandEntity;
import com.junling.online_mall.product.service.BrandService;
import org.springframework.transaction.annotation.Transactional;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {
    @Autowired
    private BrandDao brandDao;

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String)params.get("key");

        if (StringUtils.isEmpty(key)) {
            IPage<BrandEntity> page = this.page(
                    new Query<BrandEntity>().getPage(params),
                    null
            );
            return new PageUtils(page);
        }

        QueryWrapper<BrandEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("brand_id", key).or().like("name",key);
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                wrapper
        );


        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void updateDetail(BrandEntity brand) {

        this.updateById(brand);
        String brandName = brand.getName();

        if (!StringUtils.isEmpty(brandName)) {
            categoryBrandRelationService.updateBrand(brand.getBrandId(), brandName);
        }
    }
}