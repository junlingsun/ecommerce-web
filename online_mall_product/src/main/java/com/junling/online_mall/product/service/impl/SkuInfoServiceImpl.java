package com.junling.online_mall.product.service.impl;

import com.junling.online_mall.product.entity.SpuInfoEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.junling.common.utils.PageUtils;
import com.junling.common.utils.Query;

import com.junling.online_mall.product.dao.SkuInfoDao;
import com.junling.online_mall.product.entity.SkuInfoEntity;
import com.junling.online_mall.product.service.SkuInfoService;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        System.out.println("wrong path");
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryList(Map<String, Object> params) {

        String key = (String)params.get("key");
        String brandId = (String)params.get("brandId");
        String catId = (String)params.get("catelogId");
        String min = (String)params.get("min");
        String max = (String)params.get("max");

        QueryWrapper<SkuInfoEntity> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(obj->{
                obj.eq("id", key).or().like("spu_name", key);
            });
        }

        if (!StringUtils.isEmpty(brandId) && !brandId.equals("0")) {
            wrapper.and(obj->{
                obj.eq("brand_id", brandId);
            });
        }

        if (!StringUtils.isEmpty(catId) && !catId.equals("0")) {
            wrapper.and(obj->{
                obj.eq("catalog_id", catId);
            });
        }
        if (!StringUtils.isEmpty(min) && !catId.equals("0")) {
            wrapper.and(obj->{
                obj.ge("price", min);
            });
        }

        if (!StringUtils.isEmpty(min) && !catId.equals("0")) {
            wrapper.and(obj->{
                obj.le("price", max);
            });
        }

        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }
}