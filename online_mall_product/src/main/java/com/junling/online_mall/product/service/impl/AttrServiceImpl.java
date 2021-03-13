package com.junling.online_mall.product.service.impl;

import com.junling.online_mall.product.dao.AttrAttrgroupRelationDao;
import com.junling.online_mall.product.dao.AttrGroupDao;
import com.junling.online_mall.product.dao.CategoryDao;
import com.junling.online_mall.product.entity.AttrAttrgroupRelationEntity;
import com.junling.online_mall.product.entity.AttrGroupEntity;
import com.junling.online_mall.product.entity.CategoryEntity;
import com.junling.online_mall.product.service.CategoryService;
import com.junling.online_mall.product.vo.AttrResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.junling.common.utils.PageUtils;
import com.junling.common.utils.Query;

import com.junling.online_mall.product.dao.AttrDao;
import com.junling.online_mall.product.entity.AttrEntity;
import com.junling.online_mall.product.service.AttrService;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private AttrGroupDao attrGroupDao;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrAttrgroupRelationDao  relationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageAttr(Map<String, Object> params, int attrType, Long catId) {
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_type", attrType);

        if (catId != 0) {
            wrapper.eq("catelog_id", catId);
        }
        String key = (String)params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((object)->{
                object.eq("attr_id", key).or().like("attr_name", key);
            });
        }

        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                wrapper
        );

        List<AttrEntity> records = page.getRecords();
        List<AttrResponseVo> responseVos = new ArrayList<>();

        for (AttrEntity attrEntity: records) {
            AttrResponseVo responseVo = new AttrResponseVo();
            BeanUtils.copyProperties(attrEntity, responseVo);

            CategoryEntity cateEntity = categoryDao.selectById(catId);

            if (cateEntity != null) {
                responseVo.setCateName(cateEntity.getName());
            }

            if(attrType == 1) {
                Long attrId = attrEntity.getAttrId();
                AttrAttrgroupRelationEntity relationEntity = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));

                if (relationEntity != null && relationEntity.getAttrGroupId() != null) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                    if (cateEntity != null) {
                        responseVo.setGroupName(attrGroupEntity.getAttrGroupName());
                    }
                }



            }





            responseVos.add(responseVo);
        }

        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(responseVos);

        return pageUtils;
    }

    @Override
    public AttrResponseVo getAttrVo(Long attrId) {
        AttrResponseVo responseVo = new AttrResponseVo();
        AttrEntity attrEntity = this.getById(attrId);
        BeanUtils.copyProperties(attrEntity, responseVo);
        Long catId = attrEntity.getCatelogId();

        AttrGroupEntity attrGroupEntity = attrGroupDao.selectOne(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catId));
        responseVo.setAttrGroupId(attrGroupEntity.getAttrGroupId());
        responseVo.setCatelogPath(categoryService.getPath(catId));



        return responseVo;
    }

    @Override
    public PageUtils queryNoAttr(Map<String, Object> params, Long attrGroupId) {
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();

        //find the categoryId related to the groupId
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
        Long catId = attrGroupEntity.getCatelogId();

        //find all other related group entities under the current category (not include current group entity)
        List<AttrGroupEntity> attrGroupEntities = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catId));
        List<Long> groupIds = attrGroupEntities.stream().map(item->{
            return item.getAttrGroupId();
        }).collect(Collectors.toList());

        //find all related attr_id from above group_ids
        List<AttrAttrgroupRelationEntity> relationEntities = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", groupIds));
        List<Long> attrIds = relationEntities.stream().map(item->{
            return item.getAttrId();
        }).collect(Collectors.toList());



        wrapper.eq("catelog_id", catId);
        if (attrIds !=null && attrIds.size()>0) {
            wrapper.notIn("attr_id", attrIds);
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }
}