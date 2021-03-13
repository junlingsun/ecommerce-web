package com.junling.online_mall.product.vo;

import lombok.Data;

@Data
public class AttrResponseVo extends AttrVo{

    private String cateName;
    private String groupName;
    private Long[] catelogPath;
    private Long attrGroupId;
}
