package com.junling.online_mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.junling.common.utils.PageUtils;
import com.junling.online_mall.ware.entity.WareOrderTaskDetailEntity;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author Junling
 * @email junlingsun1983@gmail.com
 * @date 2021-02-19 23:48:50
 */
public interface WareOrderTaskDetailService extends IService<WareOrderTaskDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

