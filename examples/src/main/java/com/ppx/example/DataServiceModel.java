/**
 *
 * @Package: com.inspur.dmp.dafy.monitor.dataservice.po
 * @author: zhuanghuan
 * @date: 2019年7月2日 下午5:11:43
 */

package com.ppx.example;

import lombok.Builder;

import java.util.List;

/**
 * @ClassName: DataServiceModel
 * @Description: TODO
 * @author: zhuanghuan
 * @date: 2019年7月2日 下午5:11:43
 */
@Builder
public class DataServiceModel {
	/**
	 * 主键ID
	 */
	String id;
	/**
	 * 代号
	 */
	private String code;
	/**
	 * 标题
	 */
	private String caption;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 描述
	 */
	private String desc;
	/**
	 * 请求URL
	 */
	private String requestUrl;
	/**
	 * 请求方式
	 */
	private String requestWay;
	/**
	 * 详细信息页请求参数
	 */
	private String requestParams;
	/**
	 * api授权页新增的授权
	 */
	private String apiInsertAuthorization;
	/**
	 * api授权页修改的授权
	 */
	private String apiUpdateAuthorization;
	/**
	 * api授权页删除的授权
	 */
	private String apiDeleteAuthorization;

	/**
	 * 创建时间
	 */
	private java.sql.Timestamp createTime;
	/**
	 * 更新时间
	 */
	private java.sql.Timestamp updateTime;
	/**
	 * 创建者
	 */
	private String creator;

	private String system;

    /**
     * 发布状态， 0：未发布， 1：已发布
     */
	private int publish;


	public DataServiceModel(String id, String code, String caption, String type, String desc, String requestUrl) {
		this.id = id;
		this.code = code;
		this.caption = caption;
		this.type = type;
		this.desc = desc;
		this.requestUrl = requestUrl;
	}

	/**
	 * @return 返回代号
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            设置代号
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return 返回标题
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @param caption
	 *            设置标题
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * @return 返回类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            设置类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return 返回描述
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            设置描述
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return 返回请求url
	 */
	public String getRequestUrl() {
		return requestUrl;
	}

	/**
	 * @param requestUrl
	 *            设置请求url
	 */
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}



	/**
	 * @return 返回请求参数
	 */
	public String getRequestParams() {
		return requestParams;
	}

	/**
	 * @param requestParams 设置请求参数
	 */
	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}



	/**
	 * @return 返回主键id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            设置主键ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return 返回创建时间
	 */
	public java.sql.Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            设置创建时间
	 */
	public void setCreateTime(java.sql.Timestamp createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return 返回更新时间
	 */
	public java.sql.Timestamp getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            设置更新时间
	 */
	public void setUpdateTime(java.sql.Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return 返回创建者
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * @param creator
	 *            设置创建者
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}



	/**
	 * @return 返回api授权页新增的授权
	 */
	public String getApiInsertAuthorization() {
		return apiInsertAuthorization;
	}

	/**
	 * @param apiInsertAuthorization
	 *            设置api授权页新增的授权
	 */
	public void setApiInsertAuthorization(String apiInsertAuthorization) {
		this.apiInsertAuthorization = apiInsertAuthorization;
	}

	/**
	 * @return 返回api授权页更新的授权
	 */
	public String getApiUpdateAuthorization() {
		return apiUpdateAuthorization;
	}

	/**
	 * @param apiUpdateAuthorization
	 *            设置api授权页删除的授权
	 */
	public void setApiUpdateAuthorization(String apiUpdateAuthorization) {
		this.apiUpdateAuthorization = apiUpdateAuthorization;
	}

	/**
	 * @return 返回api授权页删除的授权
	 */
	public String getApiDeleteAuthorization() {
		return apiDeleteAuthorization;
	}

	/**
	 * @param apiDeleteAuthorization
	 *
	 */
	public void setApiDeleteAuthorization(String apiDeleteAuthorization) {
		this.apiDeleteAuthorization = apiDeleteAuthorization;
	}

	/**
	 * @return 返回请求方式
	 */
	public String getRequestWay() {
		return requestWay;
	}

	/**
	 * @param requestWay 设置请求方式
	 */
	public void setRequestWay(String requestWay) {
		this.requestWay = requestWay;
	}

	/**
	 * @return 是否系统预置接口，1系统接口；0：非系统接口
	 */
	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

    public int getPublish() {
        return publish;
    }

    public void setPublish(int publish) {
        this.publish = publish;
    }
}
