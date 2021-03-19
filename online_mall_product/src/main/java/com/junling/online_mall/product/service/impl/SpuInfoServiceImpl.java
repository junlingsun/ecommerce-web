package com.junling.online_mall.product.service.impl;

import com.junling.common.to.BoundsTo;
import com.junling.common.to.SkuReductionTo;
import com.junling.online_mall.product.entity.*;
import com.junling.online_mall.product.feign.CouponFeignService;
import com.junling.online_mall.product.service.*;
import com.junling.online_mall.product.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.junling.common.utils.PageUtils;
import com.junling.common.utils.Query;

import com.junling.online_mall.product.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private SpuImagesService spuImagesService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    @Autowired
    private AttrService attrService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    private CouponFeignService couponFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveSpuInfo(SpuSaveVo spuSaveVo) {
        //save spu info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuSaveVo, spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.save(spuInfoEntity);

        //save spu descipt
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        spuInfoDescEntity.setDecript(String.join(",", spuSaveVo.getDecript()));
        spuInfoDescService.save(spuInfoDescEntity);

        //save spu images
        List<String> imageUrls = spuSaveVo.getImages();
        List<SpuImagesEntity> spuImagesEntities = new ArrayList<>();
        for (String url: imageUrls) {
            SpuImagesEntity imagesEntity = new SpuImagesEntity();
            imagesEntity.setSpuId(spuInfoEntity.getId());
            imagesEntity.setImgUrl(url);
            spuImagesEntities.add(imagesEntity);
        }
        spuImagesService.saveBatch(spuImagesEntities);

        //save spur attr values
        List<BaseAttrs> baseAttrsList = spuSaveVo.getBaseAttrs();
        List<ProductAttrValueEntity> productAttrValueEntities = new ArrayList<>();
        for (BaseAttrs baseAttrs: baseAttrsList) {
            ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
            BeanUtils.copyProperties(baseAttrs, productAttrValueEntity);
            productAttrValueEntity.setSpuId(spuInfoEntity.getId());
            productAttrValueEntity.setQuickShow(baseAttrs.getShowDesc());
            productAttrValueEntity.setAttrName(attrService.getById(baseAttrs.getAttrId()).getAttrName());
            productAttrValueEntities.add(productAttrValueEntity);

        }
        productAttrValueService.saveBatch(productAttrValueEntities);

        //save bounds
        Bounds bounds = spuSaveVo.getBounds();
        BoundsTo boundsTo = new BoundsTo();
        boundsTo.setSpuId(spuInfoEntity.getId());
        BeanUtils.copyProperties(bounds, boundsTo);
        couponFeignService.saveBounds(boundsTo);


        //save skus
        List<Skus> skusList = spuSaveVo.getSkus();

        for (Skus skus: skusList) {
            List<Images> imagesList = skus.getImages();
            String defaultImageUrl = "";
            List<SkuImagesEntity> skuImagesEntities = new ArrayList<>();

            //find default image url and save it in SkuInfoEntity
            for (Images images : imagesList) {
                if (images.getDefaultImg() == 1) {
                    defaultImageUrl = images.getImgUrl();
                }
            }

            //construct SkuInfoEntity from skus
            SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
            BeanUtils.copyProperties(skus, skuInfoEntity);
            skuInfoEntity.setSpuId(spuInfoEntity.getId());
            skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
            skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
            skuInfoEntity.setSaleCount(0L);
            skuInfoEntity.setSkuDefaultImg(defaultImageUrl);
            skuInfoService.save(skuInfoEntity); //save SkuInfoEntity in database. It will generate sku_id, which will be used for image

            //construct SkuImagesEntity and save to database
            for (Images images : imagesList) {
                SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                BeanUtils.copyProperties(images, skuImagesEntity);
                skuImagesEntity.setSkuId(skuInfoEntity.getSkuId());
                skuImagesEntities.add(skuImagesEntity);
            }

            //TODO images without url don't need to save
            skuImagesService.saveBatch(skuImagesEntities);

            //save attr info
            List<Attr> attrList = skus.getAttr();
            List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = new ArrayList<>();

            for (Attr attr : attrList) {
                SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                BeanUtils.copyProperties(attr, skuSaleAttrValueEntity);
                skuSaleAttrValueEntity.setSkuId(skuInfoEntity.getSkuId());
                skuSaleAttrValueEntities.add(skuSaleAttrValueEntity);
            }
            skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

            //save full reduction
            SkuReductionTo skuReductionTo = new SkuReductionTo();
            skuReductionTo.setSkuId(skuInfoEntity.getSkuId());
            BeanUtils.copyProperties(skus, skuReductionTo);
            couponFeignService.saveReductionInfo(skuReductionTo);
        }
    }

    @Override
    public PageUtils queryList(Map<String, Object> params) {

        String status = (String)params.get("status");
        String key = (String)params.get("key");
        String brandId = (String)params.get("brandId");
        String catId = (String)params.get("catelogId");

        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(obj->{
                obj.eq("id", key).or().like("spu_name", key);
            });
        }

        if (!StringUtils.isEmpty(status)) {
            wrapper.and(obj->{
                obj.eq("publish_status", status);
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

        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }
}