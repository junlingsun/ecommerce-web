package com.junling.online_mall.product;

//import com.aliyun.oss.OSS;
//import com.aliyun.oss.OSSClientBuilder;
//import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.OSS;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootTest
class OnlineMallProductApplicationTests {

	@Autowired
	private OSS ossClient;

	@Test
	void contextLoads() {
	}

//	@Test
//	public void CSSTest(){
//		// The endpoint of the China (Hangzhou) region is used in this example. Specify the actual endpoint.
//		String endpoint = "oss-us-east-1.aliyuncs.com";
//		// Security risks may arise if you use the AccessKey pair of an Alibaba Cloud account to log on to OSS because the account has permissions on all API operations. We recommend that you use your RAM user's credentials to call API operations or perform routine operations and maintenance. To create your RAM user, log on to the RAM console.
//		String accessKeyId = "LTAI4G3KcF4VofgJbrTeLqRU";
//		String accessKeySecret = "pWorNbRIemus0eAeq6UTuS5DtemQnD";
//
//		// <yourObjectName> indicates the complete path of the object you want to upload to OSS, and must include the file extension of the object. Example: abc/efg/123.jpg.
//		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//		// Create a PutObjectRequest object.
//		PutObjectRequest putObjectRequest = new PutObjectRequest("mall-jl", "oss test", new File("/Users/junlingsun/Desktop/nane.gif"));
//
//		// Optional. Specify the storage class and ACL.
//		// ObjectMetadata metadata = new ObjectMetadata();
//		// metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
//		// metadata.setObjectAcl(CannedAccessControlList.Private);
//		// putObjectRequest.setMetadata(metadata);
//
//		// Upload a local file.
//		ossClient.putObject(putObjectRequest);
//
//		// Shut down the OSSClient instance.
//		ossClient.shutdown();
//	}

	@Test
	public void OSSTest2() throws FileNotFoundException {
		ossClient.putObject("mall-jl", "oss test2", new FileInputStream("/Users/junlingsun/Desktop/nane.gif"));

	}


}
