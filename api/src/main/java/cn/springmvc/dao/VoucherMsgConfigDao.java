package cn.springmvc.dao;

import cn.springmvc.model.voucher.VoucherMessageModel;

/**
 * 
 * @author johnson
 *
 */
public interface VoucherMsgConfigDao {

	/**
	 * get model of voucher message
	 * 
	 * @return
	 * @throws Exception
	 */
	public VoucherMessageModel getConfig() throws Exception;

	/**
	 * set model of voucher message
	 * 
	 * @param voucherConfig
	 * @throws Exception
	 */
	public void setConfig(VoucherMessageModel voucherConfig) throws Exception;
}
