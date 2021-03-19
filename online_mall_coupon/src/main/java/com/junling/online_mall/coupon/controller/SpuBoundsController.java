package com.junling.online_mall.coupon.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.junling.common.to.MemberPrice;
import com.junling.common.to.SkuReductionTo;
import com.junling.online_mall.coupon.entity.MemberPriceEntity;
import com.junling.online_mall.coupon.entity.SkuFullReductionEntity;
import com.junling.online_mall.coupon.entity.SkuLadderEntity;
import com.junling.online_mall.coupon.service.MemberPriceService;
import com.junling.online_mall.coupon.service.SkuFullReductionService;
import com.junling.online_mall.coupon.service.SkuLadderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.junling.online_mall.coupon.entity.SpuBoundsEntity;
import com.junling.online_mall.coupon.service.SpuBoundsService;
import com.junling.common.utils.PageUtils;
import com.junling.common.utils.R;



/**
 * ??Ʒspu???????
 *
 * @author Junling
 * @email junlingsun1983@gmail.com
 * @date 2021-02-19 23:44:00
 */
@RestController
@RequestMapping("coupon/spubounds")
public class SpuBoundsController {
    @Autowired
    private SpuBoundsService spuBoundsService;

    @Autowired
    private SkuFullReductionService skuFullReductionService;

    @Autowired
    private SkuLadderService skuLadderService;

    @Autowired
    private MemberPriceService memberPriceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("coupon:spubounds:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = spuBoundsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("coupon:spubounds:info")
    public R info(@PathVariable("id") Long id){
		SpuBoundsEntity spuBounds = spuBoundsService.getById(id);

        return R.ok().put("spuBounds", spuBounds);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("coupon:spubounds:save")
    public R save(@RequestBody SpuBoundsEntity spuBounds){
		spuBoundsService.save(spuBounds);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("coupon:spubounds:update")
    public R update(@RequestBody SpuBoundsEntity spuBounds){
		spuBoundsService.updateById(spuBounds);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("coupon:spubounds:delete")
    public R delete(@RequestBody Long[] ids){
		spuBoundsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @Transactional
    @PostMapping("/reductioninfo")
    R saveReductionInfo(@RequestBody SkuReductionTo skuReductionTo) {
        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
        BeanUtils.copyProperties(skuReductionTo, skuFullReductionEntity);
        skuFullReductionService.save(skuFullReductionEntity);

        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        BeanUtils.copyProperties(skuReductionTo, skuLadderEntity);
        skuLadderService.save(skuLadderEntity);

        List<MemberPrice> memberPriceList = skuReductionTo.getMemberPrice();
        List<MemberPriceEntity> memberPriceEntities = new ArrayList<>();

        if (memberPriceList == null || memberPriceList.size()==0) return R.ok();
        for (MemberPrice price: memberPriceList) {
            MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
            memberPriceEntity.setMemberPrice(price.getPrice());
            memberPriceEntity.setMemberLevelId(price.getId());
            memberPriceEntity.setMemberLevelName(price.getName());
            memberPriceEntity.setSkuId(price.getId());
            memberPriceEntity.setAddOther(1);
            memberPriceEntities.add(memberPriceEntity);
        }
        memberPriceService.saveBatch(memberPriceEntities);


        return R.ok();
    }

}
