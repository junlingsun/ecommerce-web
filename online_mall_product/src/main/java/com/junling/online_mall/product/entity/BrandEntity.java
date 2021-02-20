package com.junling.online_mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * Ʒ?
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
	 * Ʒ??id
	 */
	@TableId
	private Long brandId;
	/**
	 * Ʒ???
	 */
	private String name;
	/**
	 * Ʒ??logo??ַ
	 */
	private String logo;
	/**
	 * ???
	 */
	private String descript;
	/**
	 * ??ʾ״̬[0-????ʾ??1-??ʾ]
	 */
	private Integer showStatus;
	/**
	 * ????????ĸ
	 */
	private String firstLetter;
	/**
	 * ???
	 */
	private Integer sort;

}
