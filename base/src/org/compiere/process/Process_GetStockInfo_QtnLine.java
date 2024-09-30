package org.compiere.process;


import java.math.BigDecimal;

import java.sql.CallableStatement;
import org.compiere.util.DB;
public class Process_GetStockInfo_QtnLine extends SvrProcess{
	
	private int AD_PInstance_ID = 0;
	
	protected void prepare()
	{
		AD_PInstance_ID = getAD_PInstance_ID();
		
	}
	
	protected String doIt() throws Exception 
	{
		try {
				String sql_rec = "Select Record_ID FROM ad_pinstance where ad_pinstance_id = ? ";
		    	int Record_Id = DB.getSQLValue(get_TrxName(), sql_rec, AD_PInstance_ID);
		    	
		    	String  prdSQL= "Select m_product_id FROM c_quotationline where c_quotationline_id=?";
		    	int prdID=DB.getSQLValue(get_TrxName(), prdSQL, Record_Id);
		    	
		    /*	String  qtnSQL= "Select c_quotation_id FROM c_quotationline where c_quotationline_id=?";
		    	int qtnID=DB.getSQLValue(get_TrxName(), qtnSQL, Record_Id);
		    	
		    	String  warSQL= "Select m_warehouse_id FROM c_quotation where c_quotation_id=?";
		    	int warID=DB.getSQLValue(get_TrxName(), warSQL, qtnID);   */
		    	
		    	String  stkSQL= "select nvl((select sum(qtyonhand) from m_storage where m_product_id=?),0) from dual";
		    	BigDecimal stkVal=DB.getSQLValueBD(get_TrxName(), stkSQL, prdID);
		    	
		    	String  soSQL= " select nvl(sum(cpl.plannedqty-(select nvl(sum(col.qtyordered),0) from c_orderline col "+
		    	" where col.c_projectline_id=cpl.c_projectline_id and col.processed='Y')),0) from c_projectline cpl inner join "+
		    	" c_project cp on cpl.c_project_id=cp.c_project_id where cp.isactive='Y' and cpl.m_product_id=? ";
		    	BigDecimal soVal=DB.getSQLValueBD(get_TrxName(), soSQL, prdID);
		    			    	
		    	
		    	String  poSQL= "SELECT NVL((SELECT SUM(ol.qtyordered-ol.qtydelivered) FROM c_orderline ol INNER JOIN c_order oh ON " +
		    		"oh.c_order_id=ol.c_order_id WHERE oh.issotrx='N' AND ol.qtyordered>ol.qtydelivered AND " +
		    		"oh.docstatus='CO' AND oh.POSTED='Y' AND ol.m_product_id=?),0) FROM dual";
		    	BigDecimal poVal=DB.getSQLValueBD(get_TrxName(), poSQL, prdID);
		    	
		    	double netQty = stkVal.doubleValue()+ poVal.doubleValue()-soVal.doubleValue();
		    	
		    	addLog("<html><br>Stock Info:<br><br>Qty on Stock : " + stkVal + 
		    			"<br> Qty on the Way : " + poVal + "<br> Pending Sales Order : " + soVal + "<br>=======================<br>Net Qty : " + 
		    			netQty +"<br>=======================<br><br></html>");
		    	
		    	//Update Stock Info on OrderLine
		    	/*String txtStkInfo = "Qty on Stock:" + stkVal + " ** Qty on the Way:" + poVal + " ** Pending SO:" + soVal + " ** Net Qty:" + netQty + "";
		    	String sqlUpd = "Update c_quotationline set TxtStkInfo='" + txtStkInfo + "' Where c_quotationline_id=?";
				try {
					CallableStatement cstmt = DB.prepareCall(sqlUpd);
					cstmt.setInt(1, Record_Id);
					Integer no=cstmt.executeUpdate();
					cstmt.close();
					//addLog(0, null, null, "Process Completed Successfully");
				} catch (Exception e) {
					String s = e.getLocalizedMessage();
					System.out.println("Error : " + s);
					String s1[] = s.split(":");
					addLog(0, null, null, "Process Stopped on Update");
					addLog(0, null, null, s1[1]);
				}*/
				//End of Update Stock Info on OrderLine
				
		} catch (Exception e) {
			String s = e.getLocalizedMessage();
			System.out.println("Error : " + s);
			String s1[] = s.split(":");
			addLog(0, null, null, "Process Stopped");
			addLog(0, null, null, s1[1]);
		}
		
		return "";
	}

}