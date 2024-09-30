package org.compiere.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;

public class MProjectReceipt extends X_C_ProjectReceipt
{	
	public MProjectReceipt(Properties ctx, int C_ProjectReceipt_ID, String trxName) {
		super(ctx, C_ProjectReceipt_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	private String		m_processMsg = null;
	/**************************************************************************
	 * 	Process document
	 *	@param processAction document action
	 *	@return true if performed
	 */
/*	public boolean processIt (String processAction)
	{
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (processAction, getDocAction());
	}	//	processIt
*/
	/**	Just Prepared Flag			*/
	private boolean 		m_justPrepared = false;
	
	public MProjectReceipt (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MInvoice

	private MProjectReceiptLine[] 	m_lines = null;
	/**************************************************************************
	 *	Prepare Document
	 * 	@return new status (In Progress or Invalid) 
	 */
	/*public String prepareIt()
	{
		log.info("Prepared It");
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_justPrepared = true;
		return DocAction.STATUS_InProgress;
		
	}	//	prepareIt
	*/
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
		setIsApproved(true);
		return true;
	}

	public boolean rejectIt() {
		log.info("rejectIt - " + toString());
		setIsApproved(false);
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
		sb.append(getDocumentNo());
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
//		MDESPADVICEDETAIL mdd = new MDESPADVICEDETAIL(getCtx(),getXX_DESP_ADVICE_HDR_ID(),null);
//		return mdd.getQUANTITY();
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
		
		if (is_ValueChanged("AD_Org_ID"))
		{
			String sql = "UPDATE C_ProjectReceiptLine ol"
				+ " SET AD_Org_ID ="
					+ "(SELECT AD_Org_ID"
					+ " FROM C_ProjectReceipt o " +
			   		  " WHERE ol.C_ProjectReceipt_ID=o.C_ProjectReceipt_ID) "
				+ "WHERE C_ProjectReceipt_ID=" + getC_ProjectReceipt_ID();
			int no = DB.executeUpdate(sql, get_TrxName());
			log.fine("Lines -> #" + no);
		}		
		return true;
	}	//	afterSave


	public MProjectReceiptLine[] getLines (String whereClause, String orderClause)
	{
		//red1 - using new Query class from Teo / Victor's MDDOrder.java implementation
		StringBuffer whereClauseFinal = new StringBuffer(MProjectReceiptLine.COLUMNNAME_C_ProjectReceipt_ID+"=? ");
		if (!Util.isEmpty(whereClause, true))
			whereClauseFinal.append(whereClause);
		if (orderClause.length() == 0)
			orderClause = MProjectReceiptLine.COLUMNNAME_Line;
		//
		List<MProjectReceiptLine> list = new Query(getCtx(), I_C_ProjectReceiptLine.Table_Name, whereClauseFinal.toString(), get_TrxName())
										.setParameters(get_ID())
										.setOrderBy(orderClause)
										.list();
		//for (MProjectReceiptLine ol : list) {
			//ol.setHeaderInfo(this);
	//	}
		//
		return list.toArray(new MProjectReceiptLine[list.size()]);		
	}	//	getLines
	
	public MProjectReceiptLine[] getLines (boolean requery, String orderBy)
	{
		if (m_lines != null && !requery) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		//
		String orderClause = "";
		if (orderBy != null && orderBy.length() > 0)
			orderClause += orderBy;
		else
			orderClause += "Line";
		m_lines = getLines(null, orderClause);
		return m_lines;
	}	//	getLines

	/**
	 * 	Get Lines of Order.
	 * 	(used by web store)
	 * 	@return lines
	 */
	public MProjectReceiptLine[] getLines()
	{
		return getLines(false, null);
	}	//	getLines
	
	
}
