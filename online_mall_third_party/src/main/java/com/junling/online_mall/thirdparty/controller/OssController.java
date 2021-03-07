package com.junling.online_mall.thirdparty.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.junling.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.tags.EditorAwareTag;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/thirdparty")
public class OssController {

    @Autowired
    private OSS ossClient;

    @Value("${spring.cloud.alicloud.oss.endpoint}")
    private String endpoint;

    @Value("${spring.cloud.alicloud.oss.bucket}")
    private String bucket;

    @Value(("${spring.cloud.alicloud.access-key}"))
    private String accessId;

    @RequestMapping("/oss/policy")
    @ResponseBody
    public R policy(){


        String host = "https://" + bucket + "." + endpoint; // Set host to a value in the format of bucketname.endpoint.
        // Set the URL of the server to which an upload callback request is sent. Replace the IP address and port number with your actual information.
        //String callbackUrl = "http://88.88.88.88:8888";
        String simpleDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dir = simpleDate; // Specify the prefix for the name of the object to be uploaded.

        Map<String, String> respMap = new LinkedHashMap<String, String>();

        // Create an OSSClient instance.

        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            // You can upload an object up to 5 GB in size by using PostObject. To upload an object that is 5 GB in size, set CONTENT_LENGTH_RANGE to 5*1024*1024*1024.
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);


            respMap.put("accessid", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            // respMap.put("expire", formatISO8601Date(expiration));



        } catch (Exception e) {
            // Assert.fail(e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            ossClient.shutdown();
        }

        return R.ok().put("data", respMap);
    }
}
