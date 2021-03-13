package com.junling.online_mall.product.service.impl;

import com.junling.common.utils.R;
import com.junling.online_mall.product.service.CategoryBrandRelationService;
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
import org.springframework.transaction.annotation.Transactional;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

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
                int sort1 = o1.getSort()==null?0:o1.getSort();
                int sort2 = o2.getSort()==null?0:o2.getSort();
                return sort1-sort2;
            }
        });
        rootEntity.setChildren(childrenEntities);

    }

    @Override
    public Long[] getPath(Long catId) {
        List<Long> list = new ArrayList<>();
        CategoryEntity categoryEntity = categoryDao.selectById(catId);
        list.add(categoryEntity.getCatId());

        while (categoryEntity.getParentCid() != 0) {
            Long parentCid = categoryEntity.getParentCid();
            list.add(parentCid);
            categoryEntity = categoryDao.selectById(parentCid);
        }


        Long[] path = new Long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            path[i] = list.get(list.size()-i-1);
        }

//        for (long l: path) {
//            System.out.print( l + " ");
//        }
        return path;
    }

    @Override
    @Transactional
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category);

    }
}