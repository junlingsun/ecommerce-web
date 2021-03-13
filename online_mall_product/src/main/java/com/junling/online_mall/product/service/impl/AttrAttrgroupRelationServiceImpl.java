package com.junling.online_mall.product.service.impl;

import com.junling.online_mall.product.entity.AttrEntity;
import com.junling.online_mall.product.vo.AttrGroupRelationVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.junling.common.utils.PageUtils;
import com.junling.common.utils.Query;

import com.junling.online_mall.product.dao.AttrAttrgroupRelationDao;
import com.junling.online_mall.product.entity.AttrAttrgroupRelationEntity;
import com.junling.online_mall.product.service.AttrAttrgroupRelationService;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveRelation(AttrGroupRelationVo[] relationVos) {
        for (AttrGroupRelationVo relationVo: relationVos) {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(relationVo, relationEntity);
            this.save(relationEntity);
        }
    }
}