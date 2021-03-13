package com.junling.online_mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.junling.common.utils.PageUtils;
import com.junling.online_mall.product.entity.AttrAttrgroupRelationEntity;
import com.junling.online_mall.product.vo.AttrGroupRelationVo;

import java.util.Map;

/**
 * ????&???Է???????
 *
 * @author Junling
 * @email junlingsun1983@gmail.com
 * @date 2021-02-19 23:40:58
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveRelation(AttrGroupRelationVo[] relationVos);
}

