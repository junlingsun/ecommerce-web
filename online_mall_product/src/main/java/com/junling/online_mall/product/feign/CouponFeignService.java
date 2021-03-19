package com.junling.online_mall.product.feign;

import com.junling.common.to.BoundsTo;
import com.junling.common.to.SkuReductionTo;
import com.junling.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("coupon")
public interface CouponFeignService {


    @PostMapping("coupon/spubounds/save")
    R saveBounds(@RequestBody BoundsTo boundsTo);

    @PostMapping("coupon/spubounds/reductioninfo")
    R saveReductionInfo(@RequestBody SkuReductionTo skuReductionTo);
}
