package org.example.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yangxy
 * @since 2023-08-17
 */

@ApiModel(value = "资源实例查询实体")
public class ResourceInstanceQueryVO extends BaseQueryVO {

    /**
     * 元数据
     */
    @ApiModelProperty("元数据")
    private String metaData;

    /**
     * 实例名称
     */
    @ApiModelProperty("实例名称")
    private String name;

    /**
     * 一级资源实例类型
     */
    @ApiModelProperty("一级资源实例类型")
    private String resourceType;

    /**
     * 二级资源实例类型
     */
    @ApiModelProperty("二级资源实例类型")
    private String productType;

    /**
     * 产品uuid
     */
    @ApiModelProperty("产品uuid")
    private String productUuid;

    /**
     * 产品名称
     */
    @ApiModelProperty("产品名称")
    private String productName;

    /**
     * 是否收费  1：收费  0：不收费
     */
    @ApiModelProperty("是否收费  1：收费  0：不收费")
    private Integer charged;

    /**
     * 政务云单位uuid
     */
    @ApiModelProperty("政务云单位uuid")
    private String unitUuid;

    /**
     * 政务云单位名称
     */
    @ApiModelProperty("政务云单位名称")
    private String unitName;

    /**
     * 资源实例字段表uuid
     */
    @ApiModelProperty("资源实例字段表uuid")
    private String resourceInstanceFieldUuid;

    /**
     * 表单配置过滤条件json字符串
     */
    @ApiModelProperty("表单配置过滤条件json字符串")
    private String filterCondition;

    /**
     * 关键词搜索
     */
//    @ApiModelProperty("关键词搜索")
//    private String keyword;

    /**
     * 搜索条件，多个用，连接
     */
    @ApiModelProperty("搜索条件，多个用，连接")
    private String searchCriteria;

    /**
     * 导出列表字段 多个用，连接
     */
    private String exportColumn;

    /**
     * 其他条件  1：个人  2：政务单位
     */
    @ApiModelProperty("其他条件  1：个人  2：政务单位")
    private Integer otherCondition;

    /**
     * 导出类型 excel csv
     */
    @ApiModelProperty("导出类型 excel csv")
    private String exportType;

    /**
     * 工作日 默认 5天
     */
    @ApiModelProperty("工作日")
    private Integer dutyDays;
    
    @ApiModelProperty("资源关联系统uuid")
    private String busSysUuid;

    /**
     * 0：正常   1：撤销
     */
    private Integer cancel;

    /**
     * 所属用户 所属用户名称搜索
     */
    private String creatorName;

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductUuid() {
        return productUuid;
    }

    public void setProductUuid(String productUuid) {
        this.productUuid = productUuid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getCharged() {
        return charged;
    }

    public void setCharged(Integer charged) {
        this.charged = charged;
    }

    public String getUnitUuid() {
        return unitUuid;
    }

    public void setUnitUuid(String unitUuid) {
        this.unitUuid = unitUuid;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getResourceInstanceFieldUuid() {
        return resourceInstanceFieldUuid;
    }

    public void setResourceInstanceFieldUuid(String resourceInstanceFieldUuid) {
        this.resourceInstanceFieldUuid = resourceInstanceFieldUuid;
    }

    public String getFilterCondition() {
        return filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

//    public String getKeyword() {
//        return keyword;
//    }
//
//    public void setKeyword(String keyword) {
//        this.keyword = keyword;
//    }

    public String getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public String getExportColumn() {
        return exportColumn;
    }

    public void setExportColumn(String exportColumn) {
        this.exportColumn = exportColumn;
    }

    public Integer getOtherCondition() {
        return otherCondition;
    }

    public void setOtherCondition(Integer otherCondition) {
        this.otherCondition = otherCondition;
    }

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public Integer getDutyDays() {
        return dutyDays;
    }

    public void setDutyDays(Integer dutyDays) {
        this.dutyDays = dutyDays;
    }

    public String getBusSysUuid() {
        return busSysUuid;
    }

    public void setBusSysUuid(String busSysUuid) {
        this.busSysUuid = busSysUuid;
    }

    public Integer getCancel() {
        return cancel;
    }

    public void setCancel(Integer cancel) {
        this.cancel = cancel;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @Override
    public String toString() {
        return "ResourceInstanceQueryVO{" +
                "metaData='" + metaData + '\'' +
                ", name='" + name + '\'' +
                ", resourceType='" + resourceType + '\'' +
                ", productType='" + productType + '\'' +
                ", productUuid='" + productUuid + '\'' +
                ", productName='" + productName + '\'' +
                ", charged=" + charged +
                ", unitUuid='" + unitUuid + '\'' +
                ", unitName='" + unitName + '\'' +
                ", resourceInstanceFieldUuid='" + resourceInstanceFieldUuid + '\'' +
                ", filterCondition='" + filterCondition + '\'' +
                ", searchCriteria='" + searchCriteria + '\'' +
                ", exportColumn='" + exportColumn + '\'' +
                ", otherCondition=" + otherCondition +
                ", exportType='" + exportType + '\'' +
                ", dutyDays=" + dutyDays +
                ", busSysUuid='" + busSysUuid + '\'' +
                ", cancel=" + cancel +
                ", creatorName='" + creatorName + '\'' +
                '}';
    }
}