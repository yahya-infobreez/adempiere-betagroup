package org.compiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.DB;

/**
 *	Order Callouts.
 *	
 *  @author Jorg Janke
 *  @version $Id: CalloutOrder.java,v 1.5 2006/10/08 06:57:33 comdivision Exp $
 */
public class Callout_CCL extends CalloutEngine
{
	
	
	
	public String getPrjDetails (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		String sql = null;
		Integer RowNo =null;
		if ((Integer)mTab.getValue("C_Project_ID")!= null)
		{
			sql= "SELECT salesrep_id,c_projecttype_id,poreference,c_bpartner_id FROM C_Project  where C_Project_ID= ? ";
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1,(Integer)mTab.getValue("C_Project_ID"));
				rs = pstmt.executeQuery();
				rs.next();
				RowNo=rs.getRow();
				if (RowNo == 0)
				{
					DB.close(rs, pstmt);
					rs = null;
					pstmt = null;
					return "";
				}
				mTab.setValue("c_bpartner_id",rs.getBigDecimal("c_bpartner_id"));
				mTab.setValue("salesrep_id",rs.getBigDecimal("salesrep_id"));
				mTab.setValue("poreference",rs.getString("poreference"));
				mTab.setValue("c_projecttype_id",rs.getBigDecimal("c_projecttype_id"));
											
			}
			catch(Exception e)
			{
					             
			}	
			finally 
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
			
		}
		return "";
	}
}
