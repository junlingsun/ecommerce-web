package com.junling.online_mall.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.junling.common.valid.AddGroup;
import com.junling.common.valid.ListValue;
import com.junling.common.valid.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

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
	@NotNull (message = "id must not be null when update the item", groups = {UpdateGroup.class})
	@Null (message = "id must be null when adding new item", groups = {AddGroup.class})
	@TableId (type = IdType.AUTO)
	private Long brandId;
	/**
	 *name
	 */
	@NotBlank (message = "name cannot be null", groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * logo address
	 */
	@NotEmpty (groups = {AddGroup.class})
	@URL(message = "logo need to be an url", groups = {AddGroup.class, UpdateGroup.class})
	private String logo;
	/**
	 * description
	 */
	@NotEmpty(groups = {AddGroup.class})
	private String descript;
	/**
	 * status: 0->show, 1-hidden
	 */
	@TableLogic
	@ListValue(values = {0,1})
	private Integer showStatus;
	/**
	 * First Letter
	 */
	@NotNull(groups = {AddGroup.class})
	@Pattern(regexp = "^[a-zA-Z]$", message = "initial letter need to be a single letter a-z or A-Z", groups = {AddGroup.class, UpdateGroup.class})
	private String firstLetter;
	/**
	 * sort
	 */
	@NotNull(groups = {AddGroup.class})
	@Min(value=0, message = "sort must be a non-negative integer", groups = {AddGroup.class, UpdateGroup.class})
	private Integer sort;

}
