package com.junling.online_mall.product.service.impl;

import com.junling.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.junling.common.utils.PageUtils;
import com.junling.common.utils.Query;

import com.junling.online_mall.product.dao.CategoryDao;
import com.junling.online_mall.product.entity.CategoryEntity;
import com.junling.online_mall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> treeList() {
        List<CategoryEntity> entities = categoryDao.selectList(null);
        List<CategoryEntity> rootEntities = new ArrayList<>();
        for (CategoryEntity entity: entities) {
            if (entity.getParentCid() == 0) {
                rootEntities.add(entity);
            }
        }
        Collections.sort(rootEntities, new Comparator<CategoryEntity>() {
            @Override
            public int compare(CategoryEntity o1, CategoryEntity o2) {
                return o1.getSort()-o2.getSort();
            }
        });

        for (CategoryEntity entity: rootEntities) {
            buildTreeList(entity, entities);
        }


        return rootEntities;
    }

    private void buildTreeList(CategoryEntity rootEntity, List<CategoryEntity> entities) {
//        if (rootEntity == null) return;
        Long catId = rootEntity.getCatId();
        List<CategoryEntity> childrenEntities = new ArrayList<>();

        for (CategoryEntity entity: entities) {
            if (entity.getParentCid() == catId) {
                childrenEntities.add(entity);
                buildTreeList(entity, entities);
            }
        }
        Collections.sort(childrenEntities, new Comparator<CategoryEntity>() {
            @Override
            public int compare(CategoryEntity o1, CategoryEntity o2) {
                return o1.getSort()-o2.getSort();
            }
        });
        rootEntity.setChildren(childrenEntities);

    }
}