package com.junling.online_mall.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.alibaba.spring.util.ObjectUtils;
import com.junling.online_mall.product.entity.AttrAttrgroupRelationEntity;
import com.junling.online_mall.product.service.AttrAttrgroupRelationService;
import com.junling.online_mall.product.vo.AttrResponseVo;
import com.junling.online_mall.product.vo.AttrVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.junling.online_mall.product.entity.AttrEntity;
import com.junling.online_mall.product.service.AttrService;
import com.junling.common.utils.PageUtils;
import com.junling.common.utils.R;



/**
 * ??Ʒ?
 *
 * @author Junling
 * @email junlingsun1983@gmail.com
 * @date 2021-02-19 23:40:58
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    @Autowired
    private AttrAttrgroupRelationService relationService;

    /**
     * 列表
     * attr-type = 1: base
     * attr-type = 0: sale
     */
    @RequestMapping("/{attrType}/list/{catId}")
    @RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params, @PathVariable("attrType") int attrType, @PathVariable("catId") Long catId){
        PageUtils page = attrService.queryPageAttr(params, attrType, catId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    @RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId){

        AttrResponseVo responseVo = attrService.getAttrVo(attrId);

        return R.ok().put("attr", responseVo);
    }

    /**
     * 保存
     */

    @Transactional
    @RequestMapping("/save")
    @RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attrVo){
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo, attrEntity);
        attrService.save(attrEntity);

        int attrType = attrVo.getAttrType();
        if(attrType == 1) {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationEntity.setAttrGroupId(attrVo.getAttrGroupId());
            relationService.save(relationEntity);
        }




        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrEntity attr){
		attrService.updateById(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
