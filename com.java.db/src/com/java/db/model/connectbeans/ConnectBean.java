/**
 * =============================================================================
 * @copyright    Copyright 2011
 * @filename     ConnectBean.java
 * @description  
 * @version      V001.00  
 * @history  	 日期      修改人     版本        操作
 *              2011-10-26   Slive     V001.00		创建文件
 *
 * ============================================================================= 
 */

package com.java.db.model.connectbeans;

import java.io.Serializable;

/**
 * 连接属性
********************
*SSL Information
********************
使用 SSL: False
使用验证: False
客户端密钥: 
客户端证书: 
CA 证书: 

********************
*SSH Information
********************
使用 SSH 通道: False
主机名或 IP 地址: 
端口: 22
用户名: 
验证方法: 密码
保存密码: False

********************
*HTTP Information
********************
使用 HTTP 通道: False
通道地址: 
使用密码验证: False
用户名: 
保存密码: False
使用证书验证: False
客户端密钥: 
客户端证书: 
CA 证书: 
Use Proxy: False
代理服务器 主机: 
代理服务器 端口: 0
代理服务器 用户名: 
代理服务器 保存密码: False

********************
*Other Information
********************
服务器版本: 5.5.13
通讯协定: 10
讯息: 10.14.147.106 via TCP/IP
 */
public class ConnectBean implements Serializable
{
	/**
	 * general VersionID
	 */
	private static final long serialVersionUID = 8261628204429244953L;
	/*********************
	 * General Information
	 ********************/
	private GeneralInforBean generalInfor;

	/*********************
	 * Advanced Information
	 *********************
	*/
	private AdvancedInforBean advancedInfor;
	
	/**
	 * @description 构造函数，初始化各个数据
	 */
	public ConnectBean()
	{
		// 初始化
		generalInfor = new GeneralInforBean();
		advancedInfor = new AdvancedInforBean();
	}

	
	public GeneralInforBean getGeneralInfor()
	{
		return generalInfor;
	}

	
	public AdvancedInforBean getAdvancedInfor()
	{
		return advancedInfor;
	}
}
