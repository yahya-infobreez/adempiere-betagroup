package org.compiere.process;

import java.sql.CallableStatement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

public class Complete_Quotation extends SvrProcess{

	private int record_id = 0;

    protected void prepare() {
    	
     record_id = getRecord_ID();
		
    } // prepare

    
    protected String doIt() throws Exception {

    	
    	//Check GetPrice is done for all BOM items
    	String sql_cnt = "select count(*) from c_quotationline ql inner join m_product mp on mp.ad_client_id=ql.ad_client_id " +
    	"and ql.m_product_id=mp.m_product_id  where nvl(ql.type,'S')<>'T' and ql.price=0 and nvl(mp.isbom,'Y') = 'Y' and ql.c_quotation_id= ?" ;
    	
    	int count = DB.getSQLValue(get_TrxName(), sql_cnt, record_id);
    	
    	if (count > 0)
 	    {
    		//JOptionPane.showMessageDialog(null, "Price for Non Hardware Items should not be zero, Please create the price using GET PRICE button");
    		addLog("Price for Non Hardware Items should not be zero, Please create the price using GET PRICE button");
    		return " ";
 	    }
    	//End of Check GetPrice is done for all BOM items
    	
    	//Check Sample with no actuator ticked and price not zero  (Dtd:17.03.21)
    	String sql_cnt_actyn = "select count(*) from c_quotationline ql  " +
    	"where nvl(ql.ul_noact_yn,'N')='Y' and nvl(ql.netprice,0)<>0 and ql.c_quotation_id= ?" ;
    	
    	int count_actyn = DB.getSQLValue(get_TrxName(), sql_cnt_actyn, record_id);
    	
    	if (count_actyn > 0)
 	    {    		
    		addLog(" Net Price should be zero if the item is selected with the option:Sample with no actuator.");
    		return " ";
 	    }
    	//End of Check Sample with no actuator ticked and price not zero 
    	
    	//Check Disc % for quotations with UL Items (22.09.2015)
    	String sql_ULcnt = "SELECT count(*) FROM m_product mp INNER JOIN c_quotationline cql ON cql.m_product_id=mp.m_product_id WHERE" + 
        " cql.c_quotation_id=? AND (mp.gd_type='UL' OR mp.value='LVCDH' OR mp.value='LVCDF') ";
    	int ULcount = DB.getSQLValue(get_TrxName(), sql_ULcnt, record_id);
    	String sql_disc = "SELECT nvl(discper,0) FROM c_quotation WHERE c_quotation_id=?";
    	double ULdisc = DB.getSQLValue(get_TrxName(), sql_disc, record_id);
    	if (ULcount > 0 && ULdisc>20)
    	{
    		//JOptionPane.showMessageDialog(null, "Discount should not be greater than 20% for the quotations having UL Damper items & LVCD");
    		addLog("Discount should not be greater than 20% for the quotations having UL Damper items & LVCD");
    		return " ";
    	}
    	//End of Check Disc % for quotations with UL Items
    	   	
    	String sql1 = "update c_quotation set docstatus = 'CO', docaction = 'CL', " +
    			"processed = 'Y', processing = 'N', Posted='Y'" +
    			"  where c_quotation_id = " + getRecord_ID();
	

		try
		{
			int updatercpt = DB.executeUpdate(sql1, null);
			
		}catch (Exception e ){ 	
	    	    };
        return " ";
    }
}
