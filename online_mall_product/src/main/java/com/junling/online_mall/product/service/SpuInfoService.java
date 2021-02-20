package com.junling.online_mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.junling.common.utils.PageUtils;
import com.junling.online_mall.product.entity.SpuInfoEntity;

import java.util.Map;

/**
 * spu??Ϣ
 *
 * @author Junling
 * @email junlingsun1983@gmail.com
 * @date 2021-02-19 23:40:58
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

