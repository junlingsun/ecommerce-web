package com.junling.online_mall.product.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.junling.online_mall.product.entity.AttrAttrgroupRelationEntity;
import com.junling.online_mall.product.entity.AttrEntity;
import com.junling.online_mall.product.service.AttrAttrgroupRelationService;
import com.junling.online_mall.product.service.AttrService;
import com.junling.online_mall.product.service.CategoryService;
import com.junling.online_mall.product.vo.AttrGroupRelationVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.junling.online_mall.product.entity.AttrGroupEntity;
import com.junling.online_mall.product.service.AttrGroupService;
import com.junling.common.utils.PageUtils;
import com.junling.common.utils.R;


/**
 * ???Է??
 *
 * @author Junling
 * @email junlingsun1983@gmail.com
 * @date 2021-02-19 23:40:58
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrAttrgroupRelationService relationService;

    @Autowired
    private AttrService attrService;

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    @RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params, @PathVariable("catelogId") Long catId){
//        PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPage(params,catId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    @RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
		Long catId = attrGroup.getCatelogId();
		Long[] catIds = categoryService.getPath(catId);

		attrGroup.setCatelogIds(catIds);

        return R.ok().put("attrGroup", attrGroup);
    }


    @GetMapping("/{attrGroupId}/attr/relation")
    public R relation(@PathVariable("attrGroupId") Long attrGroupId) {
        List<AttrEntity> attrEntities = new ArrayList<>();

        List<AttrAttrgroupRelationEntity> relationList = relationService.list(
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrGroupId));

        for (AttrAttrgroupRelationEntity relationEntity: relationList) {
            Long attrId = relationEntity.getAttrId();
            AttrEntity attrEntity = attrService.getById(attrId);

            attrEntities.add(attrEntity);

        }


        return R.ok().put("data", attrEntities);
    }

    @GetMapping("/{attrGroupId}/noattr/relation")
    public R nonrelation(@RequestParam Map<String, Object> params, @PathVariable("attrGroupId") Long attrGroupId){

        PageUtils page = attrService.queryNoAttr(params,attrGroupId);

        return R.ok().put("page", page);

    }

    //save new relation
    @PostMapping("attr/relation")
    public R saveRelation(@RequestBody AttrGroupRelationVo[] relationVos){
        relationService.saveRelation(relationVos);

        return R.ok();
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[] relationVos){
        for (AttrGroupRelationVo relationVo: relationVos) {
            Long attrId = relationVo.getAttrId();
            Long attrGroupId = relationVo.getAttrGroupId();
            relationService.remove(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId).eq("attr_group_id", attrGroupId));

        }

        return R.ok();

    }

}
