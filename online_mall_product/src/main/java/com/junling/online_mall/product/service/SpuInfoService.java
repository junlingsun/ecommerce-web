package com.junling.online_mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.junling.common.utils.PageUtils;
import com.junling.online_mall.product.entity.SpuInfoEntity;
import com.junling.online_mall.product.vo.SpuSaveVo;

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

    void saveSpuInfo(SpuSaveVo spuSaveVo);

    PageUtils queryList(Map<String, Object> params);
}

