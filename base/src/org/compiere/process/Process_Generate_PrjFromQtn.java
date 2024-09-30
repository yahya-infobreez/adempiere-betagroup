/******************************************************************************
     * BETA CODE --------------AUTHOR -> SUBAIR P.K. 
	 * Date of Creation : 06 Dec 2010
     * Class for Generate Project from Quotation
 *****************************************************************************/
package org.compiere.process;

import java.sql.CallableStatement;

import java.sql.PreparedStatement;
import java.util.logging.Level;

import org.compiere.util.DB;

public class Process_Generate_PrjFromQtn extends SvrProcess{

	private int AD_PInstance_ID=0;
    public PreparedStatement pstmt = null;
    
    private int	p_customer_ID = 0;
    
    protected void prepare() {
    	
     AD_PInstance_ID = getAD_PInstance_ID();
     
     ProcessInfoParameter[] para = getParameter();
	 for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_BPartner_ID"))
				p_customer_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
    } // prepare
   
    
    protected String doIt() throws Exception {
    	//Check the Selected BPartner is 'No Customer'
    	String sql_ChkBp = "Select nvl(substr(value,1,2),' ') from c_bpartner where c_bpartner_id = ? " + "" ;
    	String chk_BpValue = DB.getSQLValueString(get_TrxName(), sql_ChkBp, p_customer_ID);
    	
    	if (chk_BpValue.equalsIgnoreCase("NO"))
    	{
    		addLog(0, null, null, "Process Stopped. Please select a valid customer");
    		return " ";
    	}
    	
    	//Check the selected customer is valid or not
    	String sql_ChkBp1 = "Select nvl(c_bpartner_id,0) from c_bpartner where IsCustomer='Y' and c_bpartner_id = ? " + "" ;
    	int chk_BpID = DB.getSQLValue(get_TrxName(), sql_ChkBp1, p_customer_ID);
    	
    	if (chk_BpID == 0)
    	{
    		addLog(0, null, null, "Process Stopped. Please select a valid customer");
    		return " ";
    	}
    	
    	//Check Proper BPartner is selected in the Qtn or not
    	String sql_rec = "Select Record_ID FROM ad_pinstance where ad_pinstance_id = ? ";
    	int Record_Id = DB.getSQLValue(get_TrxName(), sql_rec, AD_PInstance_ID);

    	String sql_bp = "select nvl(substr(value,1,2),' ') from c_bpartner inner join c_order on c_bpartner.c_bpartner_id= " 
    		+ "c_order.c_bpartner_id where c_order_id= ? ";
    	String bp_value = DB.getSQLValueString(get_TrxName(), sql_bp, Record_Id);
    	
    	if (bp_value.equalsIgnoreCase("NO"))
 	    {
    		try 
    		{
    			String sql_BpLoc = "select nvl(MAX(c_bpartner_location_id),0) FROM c_bpartner_location WHERE c_bpartner_id = " + p_customer_ID + "";
    			int loc_ID = DB.getSQLValue(get_TrxName(), sql_BpLoc);
    			if (loc_ID !=0)
        		{
    				String sql_upd = "Update C_Order set C_BPartner_ID = " + p_customer_ID + " ,bill_BPartner_id = " + p_customer_ID + ""
    				+ " ,c_bpartner_location_id = " + loc_ID + " ,bill_location_id = " + loc_ID + " where c_order_id= " + Record_Id + " ";
    				int no = DB.executeUpdate(sql_upd, get_TrxName()); 
    				boolean com = DB.commit(true, get_TrxName());
        		}
    			else
    			{
    				addLog(0, null, null, "Process Stopped. The selected customer has no location defined");
    	    		return " ";
    	    	}
    		}catch (Exception e) { }
   	    }
    	
    	
    	String sql = "{call proc_gen_prjfromqtn(?)}";
        try {
            CallableStatement cstmt = DB.prepareCall(sql);
            cstmt.setInt(1,AD_PInstance_ID);
			cstmt.executeUpdate();
            cstmt.close();
            addLog(0, null, null, "Process Completed Successfully");
            } catch (Exception e) {
             String s=e.getLocalizedMessage();
             System.out.println("Error : " + s);
             String s1[] = s.split(":");
             addLog(0, null, null, "Process Stopped");
             addLog(0, null, null, s1[1]);
            }
        return " ";
     }
}
