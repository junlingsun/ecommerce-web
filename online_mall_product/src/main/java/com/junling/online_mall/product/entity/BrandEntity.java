package com.junling.online_mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @author Junling
 * @email junlingsun1983@gmail.com
 * @date 2021-02-19 23:40:57
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * brand Id
	 */
	@TableId
	private Long brandId;
	/**
	 *name
	 */
	private String name;
	/**
	 * logo address
	 */
	private String logo;
	/**
	 * description
	 */
	private String descript;
	/**
	 * status: 0->show, 1-hidden
	 */
	@TableLogic
	private Integer showStatus;
	/**
	 * First Letter
	 */
	private String firstLetter;
	/**
	 * sort
	 */
	private Integer sort;

}
