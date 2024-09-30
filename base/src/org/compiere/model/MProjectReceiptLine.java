package org.compiere.model;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.Properties;


import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

public class MProjectReceiptLine extends X_C_ProjectReceiptLine{
	
	
	public MProjectReceiptLine (Properties ctx, int C_ProjectReceiptLine_ID, String trxName)
	{
		super (ctx, C_ProjectReceiptLine_ID, trxName);
		 if (C_ProjectReceiptLine_ID == 0)
		{
		setM_Product_ID (0);
		setQTYRECEIVED (Env.ZERO);
//		setC_ProjectReceiptLine_ID (0);
//		setC_ProjectReceipt_ID (0);
		}
	}	//	//	MDESPADVICEDETAIL

	
	private MProjectReceipt	m_parent = null;
	
	/**
	 *  Load Constructor
	 *  @param ctx context
	 *  @param rs result set record
	 */
	public MProjectReceiptLine (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MDESPADVICEDETAIL
	
	
	public MProjectReceipt getParent()
	{
		if (m_parent == null)
			m_parent = new MProjectReceipt(getCtx(), getC_ProjectReceipt_ID(), get_TrxName());
		return m_parent;
	}
	
protected boolean beforeSave (boolean newRecord)
	{
//		log.fine("New=" + newRecord);
//		System.out.println(" Project ID : " + getC_ProjectLine_ID());
//		
//		//	Get Line No
//		if (getLine() == 0)
//		{
//			String sql = "SELECT COALESCE(MAX(Line),0)+10 FROM C_ProjectReceiptLibe WHERE C_ProjectReceipt_ID=?";
//			int ii = DB.getSQLValue (get_TrxName(), sql, getC_ProjectReceipt_ID());
//			setLine (ii);
//		}
//		
//		
	//Validate Qty not Exceed Planned Qty
	BigDecimal QtyRecvd = (BigDecimal)getQTYRECEIVED();
	Integer PrjLine = (Integer)getC_ProjectLine_ID();
	Integer C_PROJECTRECEIPTLINE_ID = (Integer)getC_ProjectReceiptLine_ID();
	
	String sql1 = "Select nvl(Sum(QTYRECEIVED),0) from C_PROJECTRECEIPTLINE Where C_ProjectLine_ID = ? " +
	"and C_PROJECTRECEIPTLINE_ID != ?";;
	BigDecimal TotRecvd = DB.getSQLValueBD(get_TrxName(),sql1,PrjLine,C_PROJECTRECEIPTLINE_ID);
	
	String sql2 = "Select nvl(PlannedQty,0) from C_PROJECTLINE Where C_ProjectLine_ID = ? " ;
	BigDecimal PlannedQty = DB.getSQLValueBD(get_TrxName(),sql2,PrjLine);
	
	if((TotRecvd.add(QtyRecvd)).compareTo(PlannedQty) == 1) 
	{
		log.saveError("Error", Msg.getMsg(getCtx(), "Total Qty received exceeds the Planned Qty (" + PlannedQty + ") for this project" ));
		return false;
	}
	
	if (QtyRecvd.doubleValue()==0)
	{
		log.saveError("Error", Msg.getMsg(getCtx(), "Qty Received Should be Greater than Zero" ));
		return false;
	}	
	
	
	
	//*************************** BETA CODE Checking Duplicated Items OCT 06 2010 ***********************
	
	if (newRecord || is_ValueChanged("M_Product_ID"))
	{
		//	Prevent duplicated items in PO lines in a single PO document
		boolean isDuplicated = false;
		MProjectReceiptLine[] rcptLines = getParent().getLines();
		for (MProjectReceiptLine oLine : rcptLines)
		{
			if (oLine.getM_Product_ID() == getM_Product_ID())
			{
				isDuplicated = true;
				break;   
			}
		}
		if (isDuplicated)
		{
			log.saveError("Error", Msg.getMsg(getCtx(), "The Product can't appear 2 times in a Document " ));
			return false;
		}
	}
	
	/*
	//Validate Product with Prod.Category Vs Warehouse  Dated:21Jul2011
			Integer ProdID = (Integer)getM_Product_ID();
			Integer ParentId = (Integer)getC_ProjectReceipt_ID();
			BigDecimal Sqlcnt = Env.ZERO;
			BigDecimal Wid = Env.ZERO;
			
			String SqlWH = "Select M_Warehouse_ID from C_ProjectReceipt where C_ProjectReceipt_ID = ?";
			try {
					Wid = DB.getSQLValueBD(get_TrxName(), SqlWH, ParentId);
				} 	catch (Exception e) {
				//
			}
			
			String SqlValid = "Select count(*) from m_product mp inner join m_product_Category mc " +
			"on mc.m_product_category_id=mp.m_product_category_id where mp.m_product_id=? and " +
			"mc.m_warehouse_id=?";
			try {
					Sqlcnt = DB.getSQLValueBD(get_TrxName(), SqlValid,ProdID,Wid);
				} 	catch (Exception e) {
				//
			}
			if (Sqlcnt.doubleValue() <= 0)
			{
				log.saveError("Error", Msg.getMsg(getCtx(), "Selected product and warehouse is not matching." +
						" Please check the warehouse in the main tab." ));
				return false;
			}
	*/
	//*************************** BETA CODE END *******************************
	
	
	
	
		return true;
	 }	//	beforeSave

	

	
//	protected boolean afterSave (boolean newRecord, boolean success)
//	{
//	    System.out.println(" client : " + getAD_Client_ID() +
//	    		           " org : " + getAD_Org_ID() + 
//	    		           " get_ID() :  " + get_ID() +
//		                   " prjline : " + getC_ProjectLine_ID());
//	    
//	    
//	    String sql = " select nvl(sum(AVGPO_PRICE),0) from C_ProjectRcptLine_BOM " +
//	    		" where c_projectreceiptline_id = ?";
//	    BigDecimal avgpo = DB.getSQLValueBD(null, sql, get_ID());
//	    
//	    System.out.println(" avgpo : " + avgpo);
//	    
//	    
//	    String sql1 = " update c_projectreceiptline set plannedprice = " + avgpo +
//	    " , TOTALLINE = " + avgpo + " * qtyreceived " +
//		" where c_projectreceiptline_id =  " + get_ID();
//	    
//	    int upd = DB.executeUpdate(sql1, null);
	    
//		
//		String sql = "{call proc_projectrcptLine_bom(?,?,?,?) }";
//		CallableStatement cstmt = null;
//		try {
//		    cstmt = DB.prepareCall(sql);
//            cstmt.setInt(1,getAD_Client_ID());
//            cstmt.setInt(2,getAD_Org_ID());
//            cstmt.setInt(3,get_ID());
//            cstmt.setInt(4,getC_ProjectLine_ID());
//            System.out.println("after passing params");
//            cstmt.executeUpdate();
//            System.out.println("after execution");
//            cstmt.close();
//        	} 
//        catch (Exception e) 
//        	{
//        	  String s = e.getMessage();
//        	  
//              String s1[] = s.split(":");
//        	}
//        finally
//        {
//        	DB.close(cstmt);
//        }
////        
//        return updhdr();
//	}	//	afterSave

	/**
	 * 	After Delete
	 *	@param success success
	 *	@return deleted
	 */
//	protected boolean afterDelete (boolean success)
//	{
//		if (!success)
//			return success;
//		System.out.println("Delete");
//		String sql = "{call PROC_DELETE_PROJRCPTLINE(?) }";
//		try {
//		    CallableStatement cstmt = DB.prepareCall(sql);
//            cstmt.setInt(1, get_ID());
//            cstmt.executeUpdate();
//            cstmt.close();
//        	} 
//        catch (Exception e) 
//        	{
//        	  String s = e.getMessage();
//              
//              String s1[] = s.split(":");
//        	}
//	
//		return updhdr();
//	}	//	afterDelete
//	
	private boolean updhdr()
	{
		int no = 1;
//		String sql = "UPDATE C_ProjectReceipt"
//			+ " SET description = 'test' "
//			+ "WHERE C_ProjectReceipt_ID=" + getC_ProjectReceipt_ID();
//		int no = DB.executeUpdate(sql, get_TrxName());
		
		return no == 1;
	}	//	updateHeaderTax

	
}
