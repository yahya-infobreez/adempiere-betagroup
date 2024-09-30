package org.compiere.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

public class MQUOTATION extends X_C_QUOTATION
{	
	public MQUOTATION(Properties ctx, int C_Quotation_ID, String trxName) {
		super(ctx, C_Quotation_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	private String		m_processMsg = null;
	/**************************************************************************
	 * 	Process document
	 *	@param processAction document action
	 *	@return true if performed
	 */

	/**	Just Prepared Flag			*/
	private boolean 		m_justPrepared = false;
	
	public MQUOTATION (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	
	/**
	 * 	Get Process Message
	 *	@return clear text error message
	 */
	public String getProcessMsg()
	{
		return m_processMsg;
	}	//	getProcessMsg

	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}	//	unlockIt

	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		return true;
	}	//	invalidateIt

	public boolean approveIt() {
		log.info("approveIt - " + toString());
//		setIsApproved(true);
		return true;
	}

	public boolean rejectIt() {
		log.info("rejectIt - " + toString());
//		setIsApproved(false);
		return true;
	}

	/*public String completeIt() {
		// TODO Auto-generated method stub
		//	Re-Check
		System.out.println("m_justPrepared" +m_justPrepared);
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
//		Implicit Approval
		if (!isApproved())
			approveIt();
		log.info(toString());
		//	User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}
		//
		setProcessed(true);
		setDocAction(ACTION_Close);
		return DocAction.STATUS_Completed;
	}
*/
	public boolean voidIt() {
		log.info("voidIt - " + toString());
		return closeIt();
	}

	public boolean closeIt() {
		log.info("closeIt - " + toString());
		return true;
	}

	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		log.info("reverseCorrectIt - " + toString());
		return false;
	}

	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		log.info("reverseAccrualIt - " + toString());
		return false;
	}

	public boolean reActivateIt() {
		log.info("reActivateIt - " + toString());
		//	setProcessed(false);
			if (reverseCorrectIt())
				return true;
			return false;
		}	//	reActivateIt

	public String getSummary() {
//		String sql = "Select sum(Quantity) from XX_DESP_ADVICE_DETAIL where XX_DESP_ADVICE_HDR_ID="+getXX_DESP_ADVICE_HDR_ID();
//		 BigDecimal Quantity = new BigDecimal(DB.getSQLValue(null,sql));
		BigDecimal Quantity  = null;
		StringBuffer sb = new StringBuffer();
//		sb.append(getDocumentNo());
		//	 - User
//		sb.append(" - ").append(getUserName());
		//	: Total Lines = 123.00 (#1)
		sb.append(": ").
			append(Msg.translate(getCtx(),"QUANTITY")).append("=").append(Quantity)
			.append(")");
		//	 - Description
		return sb.toString();
	}
	
	

	/*public String getDocumentNo() {
		// TODO Auto-generated method stub
		return getDocumentNo();
	}*/

	/*public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return Msg.getElement(getCtx(), "XX_DESP_ADVICE_HDR_ID") + " " + getDocumentNo();
	}
*/
	public File createPDF() {
		try
		{
			File temp = File.createTempFile(get_TableName()+get_ID()+"_", ".pdf");
			return createPDF (temp);
		}
		catch (Exception e)
		{
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 	Create PDF file
	 *	@param file output file
	 *	@return file if success
	 */
	public File createPDF (File file)
	{
			return null;
	}	//	createPDF

	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}


	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub

		return null;
	}

	/**
	 * 	Get User Name
	 *	@return user name
	 */
//	public String getUserName()
//	{
//		return MUser.get(getCtx(), getAD_User_ID()).getName();
//	}	//	getUserName
	protected boolean beforeSave (boolean newRecord)
	{
		BigDecimal GD_Disc = getDISCPER();
		BigDecimal DR_Disc = getDISCPERLF();
		BigDecimal HW_Disc = getDISCPERHW();
		
		if (GD_Disc.doubleValue()>100 | DR_Disc.doubleValue()>100 | HW_Disc.doubleValue()>100) 
		{
			log.saveError("Error", Msg.getMsg(getCtx(), "Discount should not be greater than 100"));
			return false;
		}
		return true;
	}
	
	protected boolean beforeDelete ()
	{
		/*if (getC_Project_ID() != 0)
		{
			log.saveError("Error", Msg.getMsg(getCtx(), "CannotDelete"));
			return false;
		}*/
		return true;
	}	//	beforeDelete
	
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success || newRecord)
			return success;
		

		return true;
	}	//	afterSave


	
	

}
