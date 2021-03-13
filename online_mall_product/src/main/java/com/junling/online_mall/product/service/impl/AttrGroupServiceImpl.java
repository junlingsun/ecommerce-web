package com.junling.online_mall.product.service.impl;

import com.junling.online_mall.product.entity.AttrAttrgroupRelationEntity;
import com.junling.online_mall.product.entity.AttrEntity;
import com.junling.online_mall.product.service.AttrAttrgroupRelationService;
import com.junling.online_mall.product.service.AttrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.junling.common.utils.PageUtils;
import com.junling.common.utils.Query;

import com.junling.online_mall.product.dao.AttrGroupDao;
import com.junling.online_mall.product.entity.AttrGroupEntity;
import com.junling.online_mall.product.service.AttrGroupService;
import org.w3c.dom.Attr;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrService attrService;

    @Autowired
    private AttrAttrgroupRelationService relationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        if (catelogId == 0) {
            return queryPage(params);
        }

        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
        String key = (String)params.get("key");
        wrapper.eq("catelog_id", catelogId);
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((obj)->{
                obj.eq("attr_group_id", key).or().like("attr_group_name", key);
            });
        }

        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }


}