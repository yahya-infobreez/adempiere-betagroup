/******************************************************************************
     * BETA CODE --------------AUTHOR -> SUBAIR P.K. 
	 * Date of Creation : 08 Feb 2011
     * Class for Create Doors from Fadi Table
 *****************************************************************************/


package org.compiere.process;

import java.math.BigDecimal;
import java.sql.CallableStatement;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.util.DB;

public class Process_Qtn_CreateDoor extends SvrProcess{

	private int AD_PInstance_ID=0;
    public PreparedStatement pstmt = null;
    private int	p_Beta_Doors_ID = 0;
    private int	p_Height = 0;
    private int	p_Width = 0;
    private int p_jamb = 0;
    private int p_Emb = 0;
    private int p_Uc = 0;
    
    private int	p_RH = 0;
    private int	p_LH = 0;
    private int	p_RHR = 0;
    private int p_LHR = 0;
    
    private int	p_RHA = 0;
    private int	p_LHA = 0;
    private int	p_RHRA = 0;
    private int p_LHRA = 0;
  //  private Double p_FRTHICK = 0.0;
  //  private Double p_LFTHICK = 0.0;
    private String p_FRTHICK = null;
    private String p_LFTHICK = null;
    
    private String	p_Location = null;
    private String	p_CustDoorNo = null;
    private String	p_FRLF = null;
    
    protected void prepare() 
    {
     AD_PInstance_ID = getAD_PInstance_ID();
     
     ProcessInfoParameter[] para = getParameter();
	 for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("Beta_Doors_ID"))
				p_Beta_Doors_ID = para[i].getParameterAsInt();
			else if (name.equals("Height"))
				p_Height = para[i].getParameterAsInt();
			else if (name.equals("Width"))
				p_Width = para[i].getParameterAsInt();
			else if (name.equals("Jamb"))
				p_jamb = para[i].getParameterAsInt();
			else if (name.equals("Location"))
				p_Location = (String)para[i].getParameter();
			else if (name.equals("RH"))
				p_RH = para[i].getParameterAsInt();
			else if (name.equals("LH"))
				p_LH = para[i].getParameterAsInt();
			else if (name.equals("RHR"))
				p_RHR = para[i].getParameterAsInt();
			else if (name.equals("LHR"))
				p_LHR = para[i].getParameterAsInt();
			else if (name.equals("RHA"))
				p_RHA = para[i].getParameterAsInt();
			else if (name.equals("LHA"))
				p_LHA = para[i].getParameterAsInt();
			else if (name.equals("RHRA"))
				p_RHRA = para[i].getParameterAsInt();
			else if (name.equals("LHRA"))
				p_LHRA = para[i].getParameterAsInt();
			else if (name.equals("CustDoorNo"))
				p_CustDoorNo = (String)para[i].getParameter();
			else if (name.equals("FRLF"))
				p_FRLF = (String)para[i].getParameter();
			else if (name.equals("EMB"))
				p_Emb = para[i].getParameterAsInt();
			else if (name.equals("UC"))
				p_Uc = para[i].getParameterAsInt();
			else if (name.equals("FRTHICK"))
				//p_FRTHICK = (Double)para[i].getParameter();
				p_FRTHICK = (String)para[i].getParameter();
			else if (name.equals("LFTHICK"))
				//p_LFTHICK = (Double)para[i].getParameter();
				p_LFTHICK = (String)para[i].getParameter();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
	    }
    } // prepare
    
    protected String doIt() throws Exception {
    	//Validation for fields
    	if (p_Width <=0 | p_Width >9999)
    	{
    		addLog(0, null, null, "The Value of Width Should be Between 0 and 9999");
    		return " ";
    	}
    	if (p_Height <=0 | p_Height >9999)
    	{
    		addLog(0, null, null, "The Value of Height Should be Between 0 and 9999");
    		return " ";
    	}
    	if (p_jamb <=0 | p_jamb >999)
    	{
    		addLog(0, null, null, "The Value of Jamb Should be Between 0 and 999");
    		return " ";
    	}
    	if (p_RH >9999)
    	{
    		addLog(0, null, null, "The Value of RH Should not be Greater than 9999");
    		return " ";
    	}
    	if (p_LH >9999)
    	{
    		addLog(0, null, null, "The Value of LH Should not be Greater than 9999");
    		return " ";
    	}
    	if (p_RHR >9999)
    	{
    		addLog(0, null, null, "The Value of RHR Should not be Greater than 9999");
    		return " ";
    	}
    	if (p_LHR >9999)
    	{
    		addLog(0, null, null, "The Value of LHR Should not be Greater than 9999");
    		return " ";
    	}
    	if (p_RHA >9999)
    	{
    		addLog(0, null, null, "The Value of RHA Should not be Greater than 9999");
    		return " ";
    	}
    	if (p_LHA >9999)
    	{
    		addLog(0, null, null, "The Value of LHA Should not be Greater than 9999");
    		return " ";
    	}
    	if (p_RHRA >9999)
    	{
    		addLog(0, null, null, "The Value of RHRA Should not be Greater than 9999");
    		return " ";
    	}
    	if (p_LHRA >9999)
    	{
    		addLog(0, null, null, "The Value of LHRA Should not be Greater than 9999");
    		return " ";
    	}
    	//Validation Ends here
    	
    	
    	String sql = "{call Beta_CreateDoorLines(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        try {
            CallableStatement cstmt = DB.prepareCall(sql);
            
            cstmt.setInt(1,AD_PInstance_ID);
            cstmt.setInt(2,p_Beta_Doors_ID);
            cstmt.setInt(3,p_Height);
            cstmt.setInt(4,p_Width);
            cstmt.setInt(5,p_jamb);
            cstmt.setString(6,p_Location);
            cstmt.setInt(7,p_RH);
            cstmt.setInt(8,p_LH);
            cstmt.setInt(9,p_RHR);
            cstmt.setInt(10,p_LHR);
            cstmt.setInt(11,p_RHA);
            cstmt.setInt(12,p_LHA);
            cstmt.setInt(13,p_RHRA);
            cstmt.setInt(14,p_LHRA);
            cstmt.setString(15,p_CustDoorNo);
            cstmt.setString(16,p_FRLF);
            cstmt.setInt(17,p_Emb);
            cstmt.setInt(18,p_Uc);
            //cstmt.setDouble(19,p_FRTHICK);
            //cstmt.setDouble(20,p_LFTHICK);
            cstmt.setString(19,p_FRTHICK);
            cstmt.setString(20,p_LFTHICK);
            
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
