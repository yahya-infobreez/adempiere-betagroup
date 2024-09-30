package org.compiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Env;

public class CalloutSubmittal extends CalloutEngine{

	public String CatalogueDtl (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if ( value == null || value.equals(Env.ZERO)) 
			return "";
		
		String sqlStr = null;
		Integer RowNo =null;
		
		sqlStr = "Select value,name,prdbulletin,catdesc,cattechdata from beta_catalogue where " +
				"beta_catalogue_id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sqlStr, null);
			pstmt.setInt(1,(Integer)mTab.getValue("Beta_Catalogue_ID"));
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
			mTab.setValue("Name", rs.getString("Name"));
			mTab.setValue("CatDesc", rs.getString("CatDesc"));
			mTab.setValue("CatTechData", rs.getString("CatTechData"));
			mTab.setValue("PrdBulletin", rs.getString("PrdBulletin"));
			
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
		return "";
	}
}
	

