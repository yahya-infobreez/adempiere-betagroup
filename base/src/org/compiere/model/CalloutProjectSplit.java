package org.compiere.model;

import org.compiere.util.DB;
import org.compiere.util.Env;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class CalloutProjectSplit extends CalloutEngine
{
	public String getPrjDetails (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		String sql = null;
		Integer RowNo =null;
		if ((Integer)mTab.getValue("C_Project_ID")!= null)
		{
			sql= "SELECT name,salesrep_id,c_bpartner_id from C_Project where C_Project_ID= ? " ;
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
				String nam = rs.getString("name");
				BigDecimal sal = rs.getBigDecimal("salesrep_id");
				BigDecimal cust = rs.getBigDecimal("c_bpartner_id");
				mTab.setValue("name",nam);
				mTab.setValue("salesrep_id",sal);
				mTab.setValue("c_bpartner_id",cust);
				
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
				return "";
			}
			catch(Exception e)
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
	             
			}	
		}
		return "";
	}
}
