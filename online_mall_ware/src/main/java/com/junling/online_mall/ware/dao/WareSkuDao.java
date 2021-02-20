package com.junling.online_mall.ware.dao;

import com.junling.online_mall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author Junling
 * @email junlingsun1983@gmail.com
 * @date 2021-02-19 23:48:50
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
	
}
