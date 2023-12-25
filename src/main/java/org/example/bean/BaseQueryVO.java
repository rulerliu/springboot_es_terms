package org.example.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "查询列表入参基本实体类")
public class BaseQueryVO implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "当前页")
	private Integer current;

	@ApiModelProperty(value = "每页显示条数")
	private Integer size;

	@ApiModelProperty(value = "排序(顺序对应sort):createTime,upload_time")
	private String order;
	@ApiModelProperty(value = "排序(顺序对应order):asc,desc")
	private String sort;

	@ApiModelProperty(value = "展开更多字段(0.不展开 1.展开)")
	private Integer expand;

	@ApiModelProperty(value = "关键字")
	private String keyword;

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Integer getExpand() {
		return expand;
	}

	public void setExpand(Integer expand) {
		this.expand = expand;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


}
