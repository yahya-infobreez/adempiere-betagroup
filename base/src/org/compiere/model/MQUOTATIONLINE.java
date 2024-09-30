package org.compiere.model;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

public class MQUOTATIONLINE extends X_C_QUOTATIONLINE{
	
	
	public MQUOTATIONLINE (Properties ctx, int C_ProjectReceiptLine_ID, String trxName)
	{
		super (ctx, C_ProjectReceiptLine_ID, trxName);
		 if (C_ProjectReceiptLine_ID == 0)
		{
		setM_Product_ID (0);
		setQty(Env.ZERO);
//		setC_ProjectReceiptLine_ID (0);
//		setC_ProjectReceipt_ID (0);
		}
	}	//	

	/**
	 *  Load Constructor
	 *  @param ctx context
	 *  @param rs result set record 
	 */
	public MQUOTATIONLINE (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	
	
	protected boolean beforeSave (boolean newRecord)
	{
		String PrdVal = null,NoParam = null;
		Double mCost=0.0,stdCost=0.0;
		Integer ProdId;
		
		//If Type of the item is T (Text) then no validation is required
		String Type = getType();
		if (Type.equalsIgnoreCase("T"))
			return true;
		
		//Get Value from Product Table
		ProdId = getM_Product_ID();
		String sqlprd = " select Value,nvl(noparam,'N') as NoParam from m_product where m_product_id = ? ";
		PreparedStatement pstmtPrd = null;
		ResultSet rsPrd = null;
		try 
		{
			pstmtPrd = DB.prepareStatement(sqlprd, "DSPL");
			pstmtPrd.setInt(1, ProdId);
			rsPrd = pstmtPrd.executeQuery();
		
			if(rsPrd.next()) 
			{	
				PrdVal = rsPrd.getString("Value");
				NoParam=rsPrd.getString("NoParam");
			}
		} catch(Exception e) { log.saveError("Error", e.getMessage());}
		 finally {try {DB.close(rsPrd, pstmtPrd);} catch (Exception e) {}}
		
		 //End of Get Value from Product Table
		 
		//Check Ways is blank for SAD & RAD items   Dated:20Jul2011
	 	if (PrdVal.equalsIgnoreCase("SADB") | PrdVal.equalsIgnoreCase("SADM") | 
	 			PrdVal.equalsIgnoreCase("RAD") | PrdVal.equalsIgnoreCase("SADM-EG") | 
			    PrdVal.equalsIgnoreCase("SADB-EG")	)
	 	{
	 		String Ways = getWAYS();
	 		if (Ways == null)
	 		{
 				log.saveError("Error", Msg.getMsg(getCtx(), "Ways should not be Blank for RAD & SAD"));
 				return false;
 			}	
	 		else
	 		{
	 			if (Ways.equalsIgnoreCase("0") | Ways.equalsIgnoreCase(""))
	 			{
	 				log.saveError("Error", Msg.getMsg(getCtx(), "Ways should not be Blank for RAD & SAD"));
	 				return false;
	 			}
	 		}
	 	}		
		
	 	
	 	//Check the Unit for different items
	 	String Unit = getUNIT();
	 	
	 	if (PrdVal.equalsIgnoreCase("RLD") | PrdVal.equalsIgnoreCase("RLG16") | PrdVal.equalsIgnoreCase("RLRB16") | PrdVal.equalsIgnoreCase("RLRB16/RLG16")
		 		| PrdVal.equalsIgnoreCase("RLRM16") | PrdVal.equalsIgnoreCase("RLRM16/RLG16")	| PrdVal.equalsIgnoreCase("SLD") | 
		 		PrdVal.equalsIgnoreCase("SLD/RLD") | PrdVal.equalsIgnoreCase("SLG16") | PrdVal.equalsIgnoreCase("SLRB16") | 
		 		PrdVal.equalsIgnoreCase("SLRB16/SLG16") | PrdVal.equalsIgnoreCase("SLRM16") | PrdVal.equalsIgnoreCase("SLRM16/SLG16") |
		 		PrdVal.equalsIgnoreCase("RLG12") | PrdVal.equalsIgnoreCase("RLRM12") | PrdVal.equalsIgnoreCase("RLRB12") | 
	    		PrdVal.equalsIgnoreCase("RLRB12/RLG12") | PrdVal.equalsIgnoreCase("RLRM12/RLG12") | PrdVal.equalsIgnoreCase("SLG12") |
    		    PrdVal.equalsIgnoreCase("SLRM12") | PrdVal.equalsIgnoreCase("SLRB12") | 
			    PrdVal.equalsIgnoreCase("SLRB12/SLG12") | PrdVal.equalsIgnoreCase("SLRM12/SLG12") |
			    PrdVal.equalsIgnoreCase("RLDF") | PrdVal.equalsIgnoreCase("RLD45") | PrdVal.equalsIgnoreCase("RLD-CRV") |
			    PrdVal.equalsIgnoreCase("SLDF") | PrdVal.equalsIgnoreCase("SLD45") |
			    PrdVal.equalsIgnoreCase("RLG12-20") | PrdVal.equalsIgnoreCase("RLG16-20") | PrdVal.equalsIgnoreCase("SLG12-12.5") |
			    PrdVal.equalsIgnoreCase("SLG12-20") | PrdVal.equalsIgnoreCase("SLG16-12.5") | PrdVal.equalsIgnoreCase("SLG16-20") | 
				PrdVal.equalsIgnoreCase("RLRB12-20") | PrdVal.equalsIgnoreCase("RLRB16-20") | PrdVal.equalsIgnoreCase("RLRM12-20") | 
				PrdVal.equalsIgnoreCase("RLRM16-20") | PrdVal.equalsIgnoreCase("SLRB12-12.5") | PrdVal.equalsIgnoreCase("SLRB12-20") | 
				PrdVal.equalsIgnoreCase("SLRB16-12.5") | PrdVal.equalsIgnoreCase("SLRB16-20") | PrdVal.equalsIgnoreCase("SLRM12-12.5") | 
				PrdVal.equalsIgnoreCase("SLRM12-20") | PrdVal.equalsIgnoreCase("SLRM16-12.5") | PrdVal.equalsIgnoreCase("SLRM16-20") |
				PrdVal.equalsIgnoreCase("SLDF/RLDF") | PrdVal.equalsIgnoreCase("SLD45/RLD45") | PrdVal.equalsIgnoreCase("RLRB12-20/RLG12-20") |
				PrdVal.equalsIgnoreCase("RLRB16-20/RLG16-20") | PrdVal.equalsIgnoreCase("SLRB12-12.5/SLG12-12.5") | PrdVal.equalsIgnoreCase("SLRB12-20/SLG12-20") |
				PrdVal.equalsIgnoreCase("SLRB16-12.5/SLG16-12.5") | PrdVal.equalsIgnoreCase("SLRB16-20/SLG16-20") |
				PrdVal.equalsIgnoreCase("SLRM12-12.5/SLG12-12.5") | PrdVal.equalsIgnoreCase("SLRM12-20/SLG12-20") |
				PrdVal.equalsIgnoreCase("SLRM16-12.5/SLG16-12.5") | PrdVal.equalsIgnoreCase("SLRM16-20/SLG16-20") |
				PrdVal.equalsIgnoreCase("RLRM12-20/RLG12-20") | PrdVal.equalsIgnoreCase("RLRM16-20/RLG16-20") |
				PrdVal.equalsIgnoreCase("RLD2F") | PrdVal.equalsIgnoreCase("RLD45-20") | PrdVal.equalsIgnoreCase("SLD2F") |
				PrdVal.equalsIgnoreCase("SLD45-20") | PrdVal.equalsIgnoreCase("SLD2F/RLD2F") | 
				PrdVal.equalsIgnoreCase("SLD45-20/RLD45-20")
	 			)
		{
	 		if (Unit == null | Unit.equalsIgnoreCase("")) 
	 		{
	 			log.saveError("Error", Msg.getMsg(getCtx(), "Unit should not be blank"));
				return false;
	 		}
	 		if (Unit.equalsIgnoreCase("M"))
	 		{
	 			log.saveError("Error", Msg.getMsg(getCtx(), "Invalid Unit. The possible units for this item are METER/FEET/INCH"));
				return false;
	 		}
		}
	 	else if (PrdVal.equalsIgnoreCase("OBDB") | PrdVal.equalsIgnoreCase("OBDM") | PrdVal.equalsIgnoreCase("RAD") | 
	 			PrdVal.equalsIgnoreCase("RAG") | PrdVal.equalsIgnoreCase("RARB") |
		 		PrdVal.equalsIgnoreCase("RARB/RAG") | PrdVal.equalsIgnoreCase("RARM")	| PrdVal.equalsIgnoreCase("RARM/RAG") |
		 		PrdVal.equalsIgnoreCase("RAG-45") | PrdVal.equalsIgnoreCase("RARM-45")	| PrdVal.equalsIgnoreCase("RARB-45") |
		 		PrdVal.equalsIgnoreCase("RARB-45/RAG-45") | PrdVal.equalsIgnoreCase("RARM-45/RAG-45") |
		 		PrdVal.equalsIgnoreCase("SAG20") | PrdVal.equalsIgnoreCase("SAG12.5") | PrdVal.equalsIgnoreCase("RAG20") |
		 		PrdVal.equalsIgnoreCase("RARB20") | PrdVal.equalsIgnoreCase("RARM20") |
		 		PrdVal.equalsIgnoreCase("SARB12.5") | PrdVal.equalsIgnoreCase("SARB20") |
		 		PrdVal.equalsIgnoreCase("SARM12.5") | PrdVal.equalsIgnoreCase("SARM20") |
		 		PrdVal.equalsIgnoreCase("SADB") | PrdVal.equalsIgnoreCase("SADM") |
		 		PrdVal.equalsIgnoreCase("SADM-EG") | PrdVal.equalsIgnoreCase("SADB-EG") |
		 		PrdVal.equalsIgnoreCase("SAG") | PrdVal.equalsIgnoreCase("SARB") |
		 		PrdVal.equalsIgnoreCase("SARB/SAG") | PrdVal.equalsIgnoreCase("SARM")	| PrdVal.equalsIgnoreCase("SARM/SAG")|
	 			PrdVal.equalsIgnoreCase("GAL") | PrdVal.equalsIgnoreCase("GAL-IS")	| PrdVal.equalsIgnoreCase("GAL-CR")|
	 			PrdVal.equalsIgnoreCase("GALB") | PrdVal.equalsIgnoreCase("GALB-IS")	| PrdVal.equalsIgnoreCase("GALB-CR")|
	 			PrdVal.equalsIgnoreCase("NRDE") | PrdVal.equalsIgnoreCase("NRDE-CR")	| PrdVal.equalsIgnoreCase("NRD") | 
	 			PrdVal.equalsIgnoreCase("NRD-CR")	| PrdVal.equalsIgnoreCase("GALB-CR-IS") |
	 			PrdVal.equalsIgnoreCase("GAL-CR-IS")| PrdVal.equalsIgnoreCase("EAL2")| PrdVal.equalsIgnoreCase("EAL2-IS")|
	 			PrdVal.equalsIgnoreCase("EAL4")| PrdVal.equalsIgnoreCase("EAL4-IS")|
	 			PrdVal.equalsIgnoreCase("FALH") | PrdVal.equalsIgnoreCase("FALHDB")	| PrdVal.equalsIgnoreCase("FALHDM")|
	 			PrdVal.equalsIgnoreCase("FAL") | PrdVal.equalsIgnoreCase("FALDB")	| PrdVal.equalsIgnoreCase("FALDM") |
	 			PrdVal.equalsIgnoreCase("STLA") | PrdVal.equalsIgnoreCase("STLA-SF")	| PrdVal.equalsIgnoreCase("STLA-IS-SF")|
	 			PrdVal.equalsIgnoreCase("STLA-IS") | PrdVal.equalsIgnoreCase("STLCA")	| PrdVal.equalsIgnoreCase("STLCA-SF")|
	 			PrdVal.equalsIgnoreCase("STLCA-IS") | PrdVal.equalsIgnoreCase("STLCA-IS-SF") | PrdVal.equalsIgnoreCase("AF")|
	 			PrdVal.equalsIgnoreCase("FECH") | PrdVal.equalsIgnoreCase("FAGH") | PrdVal.equalsIgnoreCase("FAG")|
	 			PrdVal.equalsIgnoreCase("FAG-45") | PrdVal.equalsIgnoreCase("FAGH-45") | PrdVal.equalsIgnoreCase("ECG")|
	 			PrdVal.equalsIgnoreCase("ECR") | PrdVal.equalsIgnoreCase("DG") | PrdVal.equalsIgnoreCase("DVR") |
	 			PrdVal.equalsIgnoreCase("BVCD") | PrdVal.equalsIgnoreCase("FVCD") | PrdVal.equalsIgnoreCase("VDR")|
	 			PrdVal.equalsIgnoreCase("SVCD") | PrdVal.equalsIgnoreCase("MVCD") | PrdVal.equalsIgnoreCase("FDF")|
	 			PrdVal.equalsIgnoreCase("FDC") | PrdVal.equalsIgnoreCase("FDCA") | PrdVal.equalsIgnoreCase("ACC2L")|
	 			PrdVal.equalsIgnoreCase("ACC2LD") | PrdVal.equalsIgnoreCase("ACCSH") | PrdVal.equalsIgnoreCase("ACCSHD") |
	 			PrdVal.equalsIgnoreCase("ACCPH")  | PrdVal.equalsIgnoreCase("ACCPHD") | PrdVal.equalsIgnoreCase("ACCSHLK") |
	 			PrdVal.equalsIgnoreCase("ACCPHLK") | PrdVal.equalsIgnoreCase("ACC2L2") |
	 			PrdVal.equalsIgnoreCase("EBPA") | PrdVal.equalsIgnoreCase("EBPW")|
	 			PrdVal.equalsIgnoreCase("SLVA09")| PrdVal.equalsIgnoreCase("SLVA12") | PrdVal.equalsIgnoreCase("SLVA15") | PrdVal.equalsIgnoreCase("SLVA20")|
	 			PrdVal.equalsIgnoreCase("SLVC09")| PrdVal.equalsIgnoreCase("SLVC12") | PrdVal.equalsIgnoreCase("SLVC15") | PrdVal.equalsIgnoreCase("SLVC20")|
	 			PrdVal.equalsIgnoreCase("SLVF12")| PrdVal.equalsIgnoreCase("CSA") | PrdVal.equalsIgnoreCase("S16") | PrdVal.equalsIgnoreCase("S18")|
	 			PrdVal.equalsIgnoreCase("S20")| PrdVal.equalsIgnoreCase("BVI")| PrdVal.equalsIgnoreCase("OD8") | PrdVal.equalsIgnoreCase("OD9") |
	 			PrdVal.equalsIgnoreCase("PAG")| PrdVal.equalsIgnoreCase("PARM")| PrdVal.equalsIgnoreCase("PARB") | 
	 			PrdVal.equalsIgnoreCase("ECG")| PrdVal.equalsIgnoreCase("ECRM")| PrdVal.equalsIgnoreCase("ECRB") |
	 			PrdVal.equalsIgnoreCase("RRD")| PrdVal.equalsIgnoreCase("SRDM")| PrdVal.equalsIgnoreCase("SRD") |
	 			PrdVal.equalsIgnoreCase("FVCDSSBR") | PrdVal.equalsIgnoreCase("SSFVCD") | 
	 			PrdVal.equalsIgnoreCase("SSSVCD") | PrdVal.equalsIgnoreCase("SSBVCD") | PrdVal.equalsIgnoreCase("SSMVCD") |
	 			PrdVal.equalsIgnoreCase("AF2") | PrdVal.equalsIgnoreCase("STLA-SF2") | PrdVal.equalsIgnoreCase("STLA-IS-SF2") |
    	        PrdVal.equalsIgnoreCase("STLCA-SF2") | PrdVal.equalsIgnoreCase("STLCA-IS-SF2") |
    	        PrdVal.equalsIgnoreCase("STLA-SSWM-SF2") | PrdVal.equalsIgnoreCase("STLCA-SSWM-SF2") |
    	        PrdVal.equalsIgnoreCase("SSWM") | PrdVal.equalsIgnoreCase("EAL2-SSWM") | PrdVal.equalsIgnoreCase("EAL4-SSWM") |
    	        PrdVal.equalsIgnoreCase("GAL-SSWM") | PrdVal.equalsIgnoreCase("GALB-CR-SSWM") | PrdVal.equalsIgnoreCase("GAL-CR-SSWM") |
    	        PrdVal.equalsIgnoreCase("STLCA-SSWM") | PrdVal.equalsIgnoreCase("STLCA-SSWM-SF") | PrdVal.equalsIgnoreCase("STLA-SSWM") |
    	        PrdVal.equalsIgnoreCase("STLA-SSWM-SF") | PrdVal.equalsIgnoreCase("RAD-GNR") |
			    PrdVal.equalsIgnoreCase("SADB-GNR") | PrdVal.equalsIgnoreCase("SADM-GNR") |
			    PrdVal.equalsIgnoreCase("SADB-EG-GNR") | PrdVal.equalsIgnoreCase("SADM-EG-GNR") | PrdVal.equalsIgnoreCase("RAD-GNS") |
			    PrdVal.equalsIgnoreCase("SADB-GNS") | PrdVal.equalsIgnoreCase("SADM-GNS") |
			    PrdVal.equalsIgnoreCase("SADB-EG-GNS") | PrdVal.equalsIgnoreCase("SADM-EG-GNS") | PrdVal.equalsIgnoreCase("PRD") |
			    PrdVal.equalsIgnoreCase("DPRD") | PrdVal.equalsIgnoreCase("IPRD") | PrdVal.equalsIgnoreCase("FVCDBB") |
			    PrdVal.equalsIgnoreCase("RARB-45-EG")  | PrdVal.equalsIgnoreCase("P4WB") | PrdVal.equalsIgnoreCase("FVCD-SMC") |
			    PrdVal.equalsIgnoreCase("VDR-SMC") | PrdVal.equalsIgnoreCase("16FVCD") | PrdVal.equalsIgnoreCase("P4WM") | 
			    PrdVal.equalsIgnoreCase("P4W") | PrdVal.equalsIgnoreCase("CJN") | PrdVal.equalsIgnoreCase("BMSD") |
			    PrdVal.equalsIgnoreCase("BMFSD-T") | PrdVal.equalsIgnoreCase("BEMFSD-T") |
			    PrdVal.equalsIgnoreCase("BMSD/R") | PrdVal.equalsIgnoreCase("BMFSD/R-T") | PrdVal.equalsIgnoreCase("BEMFSD/R-T") |
			    PrdVal.equalsIgnoreCase("BMFD-T") | PrdVal.equalsIgnoreCase("BEMFD-T") |
			    PrdVal.equalsIgnoreCase("BMFD/R-T") | PrdVal.equalsIgnoreCase("BEMFD/R-T") |
			    PrdVal.equalsIgnoreCase("BFD-F") | PrdVal.equalsIgnoreCase("BEFD-F") |
			    PrdVal.equalsIgnoreCase("BFD/R-F") | PrdVal.equalsIgnoreCase("BEFD/R-F") |
			    PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-F") |
			    PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F") |
			    PrdVal.equalsIgnoreCase("BMFD-TF") | PrdVal.equalsIgnoreCase("BEMFD-TF") |
			    PrdVal.equalsIgnoreCase("BMFD/R-TF") | PrdVal.equalsIgnoreCase("BEMFD/R-TF") | PrdVal.equalsIgnoreCase("VDRBB") |
			    PrdVal.equalsIgnoreCase("AUVCDF") | PrdVal.equalsIgnoreCase("AUVCDB") |
			    PrdVal.equalsIgnoreCase("AUVCDH") | PrdVal.equalsIgnoreCase("AUVCDS") |
			    PrdVal.equalsIgnoreCase("AGVCDF") | PrdVal.equalsIgnoreCase("AGVCDB") | PrdVal.equalsIgnoreCase("AGVCDS") |
				PrdVal.equalsIgnoreCase("LVCDF") | PrdVal.equalsIgnoreCase("LVCDH") |
				PrdVal.equalsIgnoreCase("3VCDF") | PrdVal.equalsIgnoreCase("3VCDB") |
				PrdVal.equalsIgnoreCase("3VCDH") | PrdVal.equalsIgnoreCase("3VCDS") | PrdVal.equalsIgnoreCase("BJN") |
			    PrdVal.equalsIgnoreCase("SDVBP") | PrdVal.equalsIgnoreCase("SDVBPE") |
			    PrdVal.equalsIgnoreCase("PI3VCD") | PrdVal.equalsIgnoreCase("PIAVCD") | PrdVal.equalsIgnoreCase("GALB-SSWM") | 
			    PrdVal.equalsIgnoreCase("RARB20/RAG20") |  PrdVal.equalsIgnoreCase("RARM20/RAG20") | PrdVal.equalsIgnoreCase("SARB12.5/SAG12.5") | 
			    PrdVal.equalsIgnoreCase("SARB20/SAG20") | PrdVal.equalsIgnoreCase("SARM20/SAG20") | PrdVal.equalsIgnoreCase("SARM12.5/SAG12.5") |
			    PrdVal.equalsIgnoreCase("RARM20-45") | PrdVal.equalsIgnoreCase("RARB20-45")	| PrdVal.equalsIgnoreCase("RAG20-45") |
		 		PrdVal.equalsIgnoreCase("RARM20-45/RAG20-45") | PrdVal.equalsIgnoreCase("RARB20-45/RAG20-45") |
		 		PrdVal.equalsIgnoreCase("STLG") | PrdVal.equalsIgnoreCase("STLCG") | PrdVal.equalsIgnoreCase("BMSD-NEW") |
		 		PrdVal.equalsIgnoreCase("BMFSD-NEW") | PrdVal.equalsIgnoreCase("BEMFSD-NEW") |
		 		PrdVal.equalsIgnoreCase("BMFD-NEW") | PrdVal.equalsIgnoreCase("BEMFD-NEW") | 
		 		PrdVal.equalsIgnoreCase("FDC/SLV") | PrdVal.equalsIgnoreCase("FDCA/SLV") | PrdVal.equalsIgnoreCase("FDF/SLV") |
		 		PrdVal.equalsIgnoreCase("SDV") | PrdVal.equalsIgnoreCase("SDVE") |
		 		PrdVal.equalsIgnoreCase("FBR") | PrdVal.equalsIgnoreCase("FBF") | PrdVal.equalsIgnoreCase("MPRD")
	 			)
 	 			 			 		
	 	{
		 		if (Unit == null | Unit.equalsIgnoreCase("")) 
		 		{
		 			log.saveError("Error", Msg.getMsg(getCtx(), "Unit should not be blank"));
					return false;
		 		}
		 		if (Unit.equalsIgnoreCase("F") | Unit.equalsIgnoreCase("N"))
		 		{
		 			log.saveError("Error", Msg.getMsg(getCtx(), "Invalid Unit. The possible units for this item are MM/INCH"));
					return false;
		 		}
	 	} 		
	 	else if (PrdVal.equalsIgnoreCase("PGL") | PrdVal.equalsIgnoreCase("RSDS") | PrdVal.equalsIgnoreCase("RSA") |
	 			PrdVal.equalsIgnoreCase("RMDS") |PrdVal.equalsIgnoreCase("RS") | PrdVal.equalsIgnoreCase("RM") |
	 			 PrdVal.equalsIgnoreCase("CJNP200") | PrdVal.equalsIgnoreCase("CJNP250") |
				 PrdVal.equalsIgnoreCase("CJNP300") | PrdVal.equalsIgnoreCase("CJNP350") |
				 PrdVal.equalsIgnoreCase("CJNP400") | PrdVal.equalsIgnoreCase("BJNP125") |
				 PrdVal.equalsIgnoreCase("BJNP160") | PrdVal.equalsIgnoreCase("BJNP200") |
				 PrdVal.equalsIgnoreCase("BJNP250") | PrdVal.equalsIgnoreCase("BJNP315") |
				 PrdVal.equalsIgnoreCase("BJNP400") | PrdVal.equalsIgnoreCase("AL1") |
				 PrdVal.equalsIgnoreCase("AL2") | PrdVal.equalsIgnoreCase("SILAX") | 
				 PrdVal.equalsIgnoreCase("SILAX-P") | PrdVal.equalsIgnoreCase("FANCASING") |
				 PrdVal.equalsIgnoreCase("CT-HD") | PrdVal.equalsIgnoreCase("CT-MD") |
				 PrdVal.equalsIgnoreCase("STERILAIRE") | PrdVal.equalsIgnoreCase("EDH") |
				 PrdVal.equalsIgnoreCase("COWL") | PrdVal.equalsIgnoreCase("CTC") | PrdVal.equalsIgnoreCase("CTCDS"))	
	 	{
	 			if (Unit == null | Unit.equalsIgnoreCase("")) 
		 		{
		 			log.saveError("Error", Msg.getMsg(getCtx(), "Unit should not be blank"));
					return false;
		 		}	
	 			if (Unit.equalsIgnoreCase("F") | Unit.equalsIgnoreCase("N") | Unit.equalsIgnoreCase("I")) 
	 			{
	 				log.saveError("Error", Msg.getMsg(getCtx(), "Invalid Unit. The possible units for this item is MM"));
					return false;
	 			}
	 		
	 	}
	 	
	 	//Validate the Size Limits (added on 06.02.2018)
	 	BigDecimal Avalue = getA();
	 	BigDecimal Bvalue = getB();
	 	Boolean setBUnit=getSETBUNIT();
	 	String BUnit=getBUNIT();
	 	boolean lvArea = isLVBYAREAYN();
	 	String ret2=null;
	 	Integer ret1=0;
	 	
	 	String sqlSize = "select beta_sizevalid_func_new(?,?,?,?,?,?,?) from dual "; //ProdId
		PreparedStatement pstmt_size = null;
		ResultSet rs_size = null;
	   	try // to get From Cell addr
	   	{
	   		pstmt_size = DB.prepareStatement(sqlSize, null);
	   		pstmt_size.setInt(1, ProdId);
	   		pstmt_size.setBigDecimal(2, Avalue);
	   		pstmt_size.setBigDecimal(3, Bvalue);
	   		pstmt_size.setString(4,Unit);
	   		if (setBUnit==true)
	   			pstmt_size.setString(5,"Y");
	   		else
	   			pstmt_size.setString(5,"N");
	   		pstmt_size.setString(6,BUnit);
	   		if (lvArea==true)
	   			pstmt_size.setString(7,"Y");
	   		else
	   			pstmt_size.setString(7,"N");
	   		rs_size = pstmt_size.executeQuery();
		  
		    if (rs_size.next())
		    {
		    	String retmsg = rs_size.getString(1);
		    	int length = retmsg.length();
	 	
		    	String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
			    ret1 = DB.getSQLValue(get_TrxName(), s1, retmsg,retmsg);
			      
    			String s2 = " select substr(?,instr(?,',')+1,"+length+") from dual";
			    ret2 = DB.getSQLValueString(get_TrxName(), s2, retmsg,retmsg);
		    }
		    rs_size.close();
		    pstmt_size.close();
		 }
	   	 catch(Exception e){} 
	   	 finally {try {DB.close(rs_size, pstmt_size);} catch (Exception e) {}}
	 	
	   	 if (ret1==0)
	   	 {
	   		log.saveError("Error", Msg.getMsg(getCtx(), ret2));
			return false;
	   	 }
			
	 	//End of Validate the Size Limits
	 	
	 	//*********************************Check A & B > 0
	 	boolean Bomyn = isBOM();
	 	boolean isOD = ISOD();
	 	
	 	if (Bomyn && !isOD)
	 	{
	 		if (NoParam.equalsIgnoreCase("N") && !(PrdVal.equalsIgnoreCase("SEC")) &&
	 	 		   !(PrdVal.equalsIgnoreCase("CJNP200")) && !(PrdVal.equalsIgnoreCase("CJNP250")) &&
		 		   !(PrdVal.equalsIgnoreCase("CJNP300")) && !(PrdVal.equalsIgnoreCase("CJNP350")) &&
		 		   !(PrdVal.equalsIgnoreCase("CJNP400")) && !(PrdVal.equalsIgnoreCase("BJNP125")) &&
		 		   !(PrdVal.equalsIgnoreCase("BJNP160")) && !(PrdVal.equalsIgnoreCase("BJNP200")) &&
				   !(PrdVal.equalsIgnoreCase("BJNP250")) && !(PrdVal.equalsIgnoreCase("BJNP315")) &&
				   !(PrdVal.equalsIgnoreCase("BJNP400"))) 
	 		{
	 			if (Avalue == null | Avalue.doubleValue()<=0)
	 			{
	 				log.saveError("Error", Msg.getMsg(getCtx(), "A value should be greater than zero"));
	 				return false;
	 			}
	 			else if (PrdVal.equalsIgnoreCase("RRD") | PrdVal.equalsIgnoreCase("SRDM") | PrdVal.equalsIgnoreCase("SRD"))
	 			{
	 				if (Unit.equalsIgnoreCase("M"))
	 				{
	 					if (Avalue.doubleValue() !=100 & Avalue.doubleValue()!=150 & Avalue.doubleValue()!=200 &
	 							Avalue.doubleValue()!=250 & Avalue.doubleValue()!=300 & Avalue.doubleValue()!= 350 )		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be either 100,150,200,250,300 or 350 (MM)"));
	 						return false;
	 					}
	 				}
	 				else if (Unit.equalsIgnoreCase("I"))
	 				{
	 					if (Avalue.doubleValue() !=4 & Avalue.doubleValue()!=6 & Avalue.doubleValue()!=8 &
	 							Avalue.doubleValue()!=10 & Avalue.doubleValue()!=12 & Avalue.doubleValue()!= 14 & Avalue.doubleValue()!= 24 )		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be either 4,6,8,10,12 or 14 (INCH)"));
	 						return false;
	 					}
	 				}
	 			}
	 			else if (PrdVal.equalsIgnoreCase("CJN"))
	 			{
	 				if (Unit.equalsIgnoreCase("M"))
	 				{
	 					if (Avalue.doubleValue() !=200 & Avalue.doubleValue()!=250 & Avalue.doubleValue()!=300 &
	 							Avalue.doubleValue()!=350 & Avalue.doubleValue()!=400 )		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be either 200,250,300,350 or 400 (MM)"));
	 						return false;
	 					}
	 				}
	 				else if (Unit.equalsIgnoreCase("I"))
	 				{
	 					if (Avalue.doubleValue() !=8 & Avalue.doubleValue()!=10 & Avalue.doubleValue()!=12 &
	 							Avalue.doubleValue()!=14 & Avalue.doubleValue()!=16 )		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be either 8,10,12,14 or 16 (INCH)"));
	 						return false;
	 					}
	 				}
	 			}
	 			else if (PrdVal.equalsIgnoreCase("BJN"))
	 			{
	 				if (Unit.equalsIgnoreCase("M"))
	 				{
	 					if (Avalue.doubleValue() !=125 & Avalue.doubleValue()!=160 & Avalue.doubleValue()!=200 &
	 							Avalue.doubleValue()!=250 & Avalue.doubleValue()!=315  & Avalue.doubleValue()!=400)		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be either 125,160,200,250,315,400 (MM)"));
	 						return false;
	 					}
	 				}
	 				else if (Unit.equalsIgnoreCase("I"))
	 				{
	 					if (Avalue.doubleValue() !=5 & Avalue.doubleValue()!=6.4 & Avalue.doubleValue()!=8 &
	 							Avalue.doubleValue()!=10 & Avalue.doubleValue()!=12.6 & Avalue.doubleValue()!=16)		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be either 5,6.4,8,10,12.6,16 (INCH)"));
	 						return false;
	 					}
	 				}
	 			}
	 			else if (PrdVal.equalsIgnoreCase("BMSD-NEW") |
	 			 		PrdVal.equalsIgnoreCase("BMFSD-NEW") | PrdVal.equalsIgnoreCase("BEMFSD-NEW"))
	 			{
	 				Boolean ul_spigotyn=isUL_SPIGOT();
	 				if (ul_spigotyn==false)
	 				{
	 					String ul_Act=getUL_ACT();
	 					String ul_intext=getUL_INTEXTACT();
	 					
	 					if (ul_Act.equalsIgnoreCase("B") && ul_intext.equalsIgnoreCase("I") && PrdVal.equalsIgnoreCase("BMSD-NEW"))
	 					{
		 						if (Unit.equalsIgnoreCase("M"))
				 				{
				 					if (Avalue.doubleValue()<203 | Avalue.doubleValue()>7315.2)		
				 					{
				 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be between 203 and 7315.2 (MM)"));
				 						return false;
				 					}
				 				}
				 				else if (Unit.equalsIgnoreCase("I"))
				 				{
				 					if (Avalue.doubleValue()<8 | Avalue.doubleValue()>288)		
				 					{
				 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be between 8 and 288 (INCH)"));
				 						return false;
				 					}
				 				}
	 					}
	 					else
	 					{
			 					if (Unit.equalsIgnoreCase("M"))
				 				{
				 					if (Avalue.doubleValue()<203 | Avalue.doubleValue()>1830)		
				 					{
				 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be between 203 and 1830 (MM)"));
				 						return false;
				 					}
				 				}
				 				else if (Unit.equalsIgnoreCase("I"))
				 				{
				 					if (Avalue.doubleValue()<8 | Avalue.doubleValue()>72)		
				 					{
				 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be between 8 and 72 (INCH)"));
				 						return false;
				 					}
				 				}
	 					}
	 					if (ul_Act.equalsIgnoreCase("B"))
	 					{
	 						String ul_cltype=getUL_CLASSTYPE();
	 						String ul_temp=getUL_TEMP();
	 						if (ul_cltype.equalsIgnoreCase("CLASS I") && ul_temp.equalsIgnoreCase("250T"))
	 						{
	 							if (Unit.equalsIgnoreCase("M"))
	 							{
	 								if (Avalue.doubleValue()<203 | Avalue.doubleValue()>1220)		
	 								{
	 									log.saveError("Error", Msg.getMsg(getCtx(), "For Class I-250 for Belimo,A value should be between 203 and 1220 (MM)"));
	 									return false;
	 								}
	 							}
	 							else if (Unit.equalsIgnoreCase("I"))
	 							{
	 								if (Avalue.doubleValue()<8 | Avalue.doubleValue()>48)		
	 								{
	 									log.saveError("Error", Msg.getMsg(getCtx(), "For Class I-250 for Belimo,A value should be between 8 and 48 (INCH)"));
	 									return false;
	 								}
	 							}
	 						}
	 					}
	 				}
	 				else
	 				{
	 					if (Unit.equalsIgnoreCase("M"))
		 				{
		 					if (Avalue.doubleValue()<203 | Avalue.doubleValue()>915)		
		 					{
		 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be between 203 and 915 (MM)"));
		 						return false;
		 					}
		 				}
		 				else if (Unit.equalsIgnoreCase("I"))
		 				{
		 					if (Avalue.doubleValue()<8 | Avalue.doubleValue()>36)		
		 					{
		 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be between 8 and 36 (INCH)"));
		 						return false;
		 					}
		 				}
	 				}
	 			}

	 			else if (PrdVal.equalsIgnoreCase("BMFSD-T") | PrdVal.equalsIgnoreCase("BMSD") |
	 					PrdVal.equalsIgnoreCase("BEMFSD-T"))
	 			{
	 				if (Unit.equalsIgnoreCase("M"))
	 				{
	 					if (Avalue.doubleValue()<203 | Avalue.doubleValue()>1830)		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be between 203 and 1830 (MM)"));
	 						return false;
	 					}
	 				}
	 				else if (Unit.equalsIgnoreCase("I"))
	 				{
	 					if (Avalue.doubleValue()<8 | Avalue.doubleValue()>72)		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be between 8 and 72 (INCH)"));
	 						return false;
	 					}
	 				}
	 			}
	 			else if (PrdVal.equalsIgnoreCase("BMSD/R") | PrdVal.equalsIgnoreCase("BMFSD/R-T") | PrdVal.equalsIgnoreCase("BEMFSD/R-T"))
	 			{
	 				if (Unit.equalsIgnoreCase("M"))
	 				{
	 					if (Avalue.doubleValue()<203 | Avalue.doubleValue()>915)		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be between 203 and 915 (MM)"));
	 						return false;
	 					}
	 				}
	 				else if (Unit.equalsIgnoreCase("I"))
	 				{
	 					if (Avalue.doubleValue()<8 | Avalue.doubleValue()>36)		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be between 8 and 36 (INCH)"));
	 						return false;
	 					}
	 				}
	 			}
	 			else if (PrdVal.equalsIgnoreCase("BMFD-NEW") | PrdVal.equalsIgnoreCase("BEMFD-NEW"))
	 			{
	 				Boolean ul_spigotyn=isUL_SPIGOT();
	 				if (ul_spigotyn==false)
	 				{
	 					if (Unit.equalsIgnoreCase("M"))
		 				{
		 					if (Avalue.doubleValue()<102 | Avalue.doubleValue()>1830)		
		 					{
		 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be between 102 and 1830 (MM)"));
		 						return false;
		 					}
		 				}
		 				else if (Unit.equalsIgnoreCase("I"))
		 				{
		 					if (Avalue.doubleValue()<4 | Avalue.doubleValue()>72)		
		 					{
		 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be between 4 and 72 (INCH)"));
		 						return false;
		 					}
		 				}
	 				}
	 				else
	 				{
	 					if (Unit.equalsIgnoreCase("M"))
		 				{
		 					if (Avalue.doubleValue()<102 | Avalue.doubleValue()>915)		
		 					{
		 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be between 102 and 915 (MM)"));
		 						return false;
		 					}
		 				}
		 				else if (Unit.equalsIgnoreCase("I"))
		 				{
		 					if (Avalue.doubleValue()<4 | Avalue.doubleValue()>36)		
		 					{
		 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be between 4 and 36 (INCH)"));
		 						return false;
		 					}
		 				}
	 				}
	 			}
	 			else if (PrdVal.equalsIgnoreCase("BMFD-T") | PrdVal.equalsIgnoreCase("BEMFD-T") |
	 					 PrdVal.equalsIgnoreCase("BFD-F") | PrdVal.equalsIgnoreCase("BEFD-F") |
	 					 PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-F") |
	 					 PrdVal.equalsIgnoreCase("BMFD-TF") | PrdVal.equalsIgnoreCase("BEMFD-TF"))
	 			{
	 				if (Unit.equalsIgnoreCase("M"))
	 				{
	 					if (Avalue.doubleValue()<102 | Avalue.doubleValue()>1830)		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be between 102 and 1830 (MM)"));
	 						return false;
	 					}
	 				}
	 				else if (Unit.equalsIgnoreCase("I"))
	 				{
	 					if (Avalue.doubleValue()<4 | Avalue.doubleValue()>72)		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be between 4 and 72 (INCH)"));
	 						return false;
	 					}
	 				}
	 			}
	 			else if (PrdVal.equalsIgnoreCase("BMFD/R-T") | PrdVal.equalsIgnoreCase("BEMFD/R-T") |
	 					PrdVal.equalsIgnoreCase("BFD/R-F") | PrdVal.equalsIgnoreCase("BEFD/R-F") |
	 					PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F") |
	 					PrdVal.equalsIgnoreCase("BMFD/R-TF") | PrdVal.equalsIgnoreCase("BEMFD/R-TF"))
	 			{
	 				if (Unit.equalsIgnoreCase("M"))
	 				{
	 					if (Avalue.doubleValue()<102 | Avalue.doubleValue()>915)		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be between 102 and 915 (MM)"));
	 						return false;
	 					}
	 				}
	 				else if (Unit.equalsIgnoreCase("I"))
	 				{
	 					if (Avalue.doubleValue()<4 | Avalue.doubleValue()>36)		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be between 4 and 36 (INCH)"));
	 						return false;
	 					}
	 				}
	 			}
	 			else if (PrdVal.equalsIgnoreCase("SDVBP") | PrdVal.equalsIgnoreCase("SDVBPE") |
	 					 PrdVal.equalsIgnoreCase("SDV") | PrdVal.equalsIgnoreCase("SDVE"))
	 			{
	 				if (Unit.equalsIgnoreCase("M"))
	 				{
	 					if (Avalue.doubleValue() !=100 & Avalue.doubleValue()!=150 & Avalue.doubleValue()!=200 &
	 							Avalue.doubleValue()!=250 & Avalue.doubleValue()!=300  & Avalue.doubleValue()!=350 & Avalue.doubleValue()!=400)		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be either 100,150,200,250,300,350,400 (MM)"));
	 						return false;
	 					}
	 				}
	 				else if (Unit.equalsIgnoreCase("I"))
	 				{
	 					if (Avalue.doubleValue() !=4 & Avalue.doubleValue()!=6 & Avalue.doubleValue()!=8 &
	 							Avalue.doubleValue()!=10 & Avalue.doubleValue()!=12 & Avalue.doubleValue()!=14 & Avalue.doubleValue()!=16)		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be either 4,6,8,10,12,14,16 (INCH)"));
	 						return false;
	 					}
	 				}
	 			}
	 			else if (PrdVal.equalsIgnoreCase("CTC") | PrdVal.equalsIgnoreCase("CTCDS"))
	 			{
	 				if (Unit.equalsIgnoreCase("M"))
	 				{
	 					if (Avalue.doubleValue() !=100 & Avalue.doubleValue()!=150 & Avalue.doubleValue()!=200 &
	 						Avalue.doubleValue()!=250 & Avalue.doubleValue()!=300  & Avalue.doubleValue()!=350 & 
	 						Avalue.doubleValue()!=400 & Avalue.doubleValue()!=500 & Avalue.doubleValue()!=600)		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be either 100,150,200,250,300,350,400,500,600 (MM)"));
	 						return false;
	 					}
	 				}
	 			}
	 			else if (PrdVal.equalsIgnoreCase("AL1") | PrdVal.equalsIgnoreCase("AL2"))
	 			{
	 				if (Avalue.doubleValue() !=450 & Avalue.doubleValue()!=600 & Avalue.doubleValue()!=750 &
 						Avalue.doubleValue()!=900 & Avalue.doubleValue()!=1050 & Avalue.doubleValue()!=1200 &
 						Avalue.doubleValue()!=1350 & Avalue.doubleValue()!=1500 & Avalue.doubleValue()!=1650 &
 						Avalue.doubleValue()!=1800 & Avalue.doubleValue()!=1950 & Avalue.doubleValue()!=2100 &
 						Avalue.doubleValue()!=2250 & Avalue.doubleValue()!=2400)
 					{
 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be either 450,600,750,900,1050,1200,1350,1500,1650," +
 								" 1800,1950,2100,2250,2400 (MM)"));
 						return false;
 					}
	 			}
	 			
	 			else if (PrdVal.equalsIgnoreCase("STERILAIRE"))
	 			{
	 				if (Avalue.doubleValue() !=300 & Avalue.doubleValue()!=600)
	 				{
	 					log.saveError("Error", Msg.getMsg(getCtx(), "Size should be either 300 or 600 (MM)"));
 						return false;
	 				}
	 			}
	 			
	 			else if (PrdVal.equalsIgnoreCase("EDH"))
	 			{
	 				if (Avalue.doubleValue() !=100 & Avalue.doubleValue()!=150 & Avalue.doubleValue()!=200 &
	 					Avalue.doubleValue()!=250 & Avalue.doubleValue()!=300 & Avalue.doubleValue()!=350 & 
	 					Avalue.doubleValue()!=400)
	 				{
	 					log.saveError("Error", Msg.getMsg(getCtx(), "Size should be either 100,150,200,250,300,350 or 400 (MM)"));
 						return false;
	 				}
	 			}
	 			 			
	 			else if (PrdVal.equalsIgnoreCase("SILAX") | PrdVal.equalsIgnoreCase("SILAX-P"))
	 			{
	 				if (Avalue.doubleValue()!=250 & Avalue.doubleValue()!=315 & Avalue.doubleValue()!=400 &
 						Avalue.doubleValue()!=500 & Avalue.doubleValue()!=560 & Avalue.doubleValue()!=630 &
 						Avalue.doubleValue()!=710 & Avalue.doubleValue()!=800 & Avalue.doubleValue()!=900 &
 						Avalue.doubleValue()!=1000 & Avalue.doubleValue()!=1120 & Avalue.doubleValue()!=1250 &
 						Avalue.doubleValue()!=1400 & Avalue.doubleValue()!=1600 & Avalue.doubleValue()!=1800 &
 						Avalue.doubleValue()!=2000)
 					{
 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should be either 250,315,400,500,560,630,710,800," +
 								" 900,1000,1120,1250,1400,1600,1800,2000 (MM)"));
 						return false;
 					}
	 			}
	 			
	 			else if (PrdVal.equalsIgnoreCase("FANCASING"))
	 			{
	 				String ca_model = getCA_MODEL();
	 				if (ca_model.equalsIgnoreCase("Q"))
	 				{
	 					if (Avalue.doubleValue()!=100 & Avalue.doubleValue()!=150)
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "Size should be either 100 OR 150 (MM)"));
		 					return false;
	 					}
	 				}
	 				else if (ca_model.equalsIgnoreCase("M"))
	 				{
	 					if (Avalue.doubleValue()!=100 & Avalue.doubleValue()!=125 & Avalue.doubleValue()!=150 &
	 						Avalue.doubleValue()!=160 & Avalue.doubleValue()!=200 & Avalue.doubleValue()!=250 &
	 						Avalue.doubleValue()!=315)
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "Size should be either 100,125,150,160,250 OR 315 (MM)"));
	 						return false;
	 					}
	 				}
	 			}
	 			else if (PrdVal.equalsIgnoreCase("FDC") | PrdVal.equalsIgnoreCase("FDCA"))
	 			{
	 				String ul_stad=getSTADYN();
	 				if (ul_stad.equalsIgnoreCase("D"))
	 				{
	 					if (Unit.equalsIgnoreCase("M"))
		 				{
		 					if (Avalue.doubleValue()>915)		
		 					{
		 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should not be greater than 915  (MM)"));
		 						return false;
		 					}
		 				}
		 				else if (Unit.equalsIgnoreCase("I"))
		 				{
		 					if (Avalue.doubleValue()>36.6)		
		 					{
		 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should not be greater than 915  (MM)"));
		 						return false;
		 					}
		 				}
	 				}
	 				else if (ul_stad.equalsIgnoreCase("S"))
	 				{
	 					if (PrdVal.equalsIgnoreCase("FDC"))
	 					{
	 						if (Unit.equalsIgnoreCase("M"))
			 				{
			 					if (Avalue.doubleValue()>915)		
			 					{
			 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should not be greater than 915  (MM)"));
			 						return false;
			 					}
			 				}
			 				else if (Unit.equalsIgnoreCase("I"))
			 				{
			 					if (Avalue.doubleValue()>36.6)		
			 					{
			 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should not be greater than 915  (MM)"));
			 						return false;
			 					}
			 				}
	 					}
	 					else if (PrdVal.equalsIgnoreCase("FDCA"))
	 					{
	 						if (Unit.equalsIgnoreCase("M"))
			 				{
			 					if (Avalue.doubleValue()>1830)		
			 					{
			 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should not be greater than 1830  (MM)"));
			 						return false;
			 					}
			 				}
			 				else if (Unit.equalsIgnoreCase("I"))
			 				{
			 					if (Avalue.doubleValue()>73.2)		
			 					{
			 						log.saveError("Error", Msg.getMsg(getCtx(), "A value should not be greater than 1830  (MM)"));
			 						return false;
			 					}
			 				}
	 					}
	 				}
	 			}
	 			else if (PrdVal.equalsIgnoreCase("COWL"))
	 			{
	 				if (Avalue.doubleValue()!=400 & Avalue.doubleValue()!=500 & Avalue.doubleValue()!=560 &
	 					Avalue.doubleValue()!=630 & Avalue.doubleValue()!=710 & Avalue.doubleValue()!=800 &	
	 					Avalue.doubleValue()!=900 & Avalue.doubleValue()!=1000)
 					{
 						log.saveError("Error", Msg.getMsg(getCtx(), "Size should be either 400,500,560,630,710,800,900 OR 1000 (MM)"));
	 					return false;
 					}
	 			}
	 			
	 		}	
	 		
	 		//Check Fan Casing BD Type  IF type='T', then CA_BD should be BD
	 		if (PrdVal.equalsIgnoreCase("FANCASING"))
	 		{
	 			String cabd=getCA_BD();
	 			String catype=getCA_TYPE();
	 			if (catype.equalsIgnoreCase("T") && !(cabd.equalsIgnoreCase("BD")))
	 			{
	 				log.saveError("Error", Msg.getMsg(getCtx(), "Twin type should be always Back Draft Damper"));
	 				return false;
	 			}
	 		}	
	 		//End of Check Fan Casing BD
	 		
	 		if ( !(PrdVal.equalsIgnoreCase("VDR")) && NoParam.equalsIgnoreCase("N") &&
		 			 !(PrdVal.equalsIgnoreCase("RRD")) && !(PrdVal.equalsIgnoreCase("SRDM")) && 
		 			 !(PrdVal.equalsIgnoreCase("SRD"))&& !(PrdVal.equalsIgnoreCase("SEC")) &&
		 			 !(PrdVal.equalsIgnoreCase("VDR-SMC")) && !(PrdVal.equalsIgnoreCase("CJN")) &&
		 			 !(PrdVal.equalsIgnoreCase("VDRBB")) && !(PrdVal.equalsIgnoreCase("CJNP200")) && 
		 			 !(PrdVal.equalsIgnoreCase("CJNP250")) && !(PrdVal.equalsIgnoreCase("CJNP300")) && 
		 			 !(PrdVal.equalsIgnoreCase("CJNP350")) && !(PrdVal.equalsIgnoreCase("CJNP400")) && 
		 			 !(PrdVal.equalsIgnoreCase("BJNP125")) && !(PrdVal.equalsIgnoreCase("BJNP160")) && 
		 			 !(PrdVal.equalsIgnoreCase("BJNP200")) && !(PrdVal.equalsIgnoreCase("BJNP250")) && 
		 			 !(PrdVal.equalsIgnoreCase("BJNP315")) && !(PrdVal.equalsIgnoreCase("BJNP400")) &&
		 			 !(PrdVal.equalsIgnoreCase("BJN")) && !(PrdVal.equalsIgnoreCase("SDVBP")) && 
		 			 !(PrdVal.equalsIgnoreCase("SDVBPE")) && !(PrdVal.equalsIgnoreCase("FANCASING")) &&
		 			 !(PrdVal.equalsIgnoreCase("CT-HD")) && !(PrdVal.equalsIgnoreCase("CT-MD")) && 
		 			 !(PrdVal.equalsIgnoreCase("STERILAIRE")) && !(PrdVal.equalsIgnoreCase("EDH")) &&
		 			 !(PrdVal.equalsIgnoreCase("COWL")) && !(PrdVal.equalsIgnoreCase("SDV")) && !(PrdVal.equalsIgnoreCase("SDVE")) && 
		 			 !(PrdVal.equalsIgnoreCase("CTC")) && !(PrdVal.equalsIgnoreCase("CTCDS")) &&
		 			 !(PrdVal.equalsIgnoreCase("FBR")) && !(PrdVal.equalsIgnoreCase("FBF")))
 			{
	 			if (Bvalue == null | Bvalue.doubleValue()<=0) 
	 			{
	 				log.saveError("Error", Msg.getMsg(getCtx(), "B value should be greater than zero"));
	 				return false;
	 			}
	 			else if (PrdVal.equalsIgnoreCase("FDC") | PrdVal.equalsIgnoreCase("FDCA"))
	 			{
	 				String ul_stad=getSTADYN();
	 				if (ul_stad.equalsIgnoreCase("D"))
	 				{
	 					if (Unit.equalsIgnoreCase("M"))
		 				{
		 					if (Bvalue.doubleValue()>825)		
		 					{
		 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should not be greater than 825  (MM)"));
		 						return false;
		 					}
		 				}
		 				else if (Unit.equalsIgnoreCase("I"))
		 				{
		 					if (Bvalue.doubleValue()>33)		
		 					{
		 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should not be greater than 825  (MM)"));
		 						return false;
		 					}
		 				}
	 				}
	 				else if (ul_stad.equalsIgnoreCase("S"))
	 				{
	 					if (PrdVal.equalsIgnoreCase("FDC"))
	 					{
	 						if (Unit.equalsIgnoreCase("M"))
			 				{
			 					if (Bvalue.doubleValue()>825)		
			 					{
			 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should not be greater than 825  (MM)"));
			 						return false;
			 					}
			 				}
			 				else if (Unit.equalsIgnoreCase("I"))
			 				{
			 					if (Bvalue.doubleValue()>33)		
			 					{
			 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should not be greater than 825  (MM)"));
			 						return false;
			 					}
			 				}
	 					}
	 					else if (PrdVal.equalsIgnoreCase("FDCA"))
	 					{
	 						if (Unit.equalsIgnoreCase("M"))
			 				{
			 					if (Bvalue.doubleValue()>1830)		
			 					{
			 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should not be greater than 1830  (MM)"));
			 						return false;
			 					}
			 				}
			 				else if (Unit.equalsIgnoreCase("I"))
			 				{
			 					if (Bvalue.doubleValue()>73.2)		
			 					{
			 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should not be greater than 1830  (MM)"));
			 						return false;
			 					}
			 				}
	 					}
	 				}
	 			}
	 			else if (PrdVal.equalsIgnoreCase("RAD") | PrdVal.equalsIgnoreCase("SADM") | PrdVal.equalsIgnoreCase("SADB") |
	 					PrdVal.equalsIgnoreCase("SADM-EG")	| PrdVal.equalsIgnoreCase("SADB-EG") | PrdVal.equalsIgnoreCase("RAD-GNR") |
	    			    PrdVal.equalsIgnoreCase("SADB-GNR") | PrdVal.equalsIgnoreCase("SADM-GNR") |
	    			    PrdVal.equalsIgnoreCase("SADB-EG-GNR") | PrdVal.equalsIgnoreCase("SADM-EG-GNR") | PrdVal.equalsIgnoreCase("RAD-GNS") |
					    PrdVal.equalsIgnoreCase("SADB-GNS") | PrdVal.equalsIgnoreCase("SADM-GNS") |
					    PrdVal.equalsIgnoreCase("SADB-EG-GNS") | PrdVal.equalsIgnoreCase("SADM-EG-GNS"))
		 		{
		 				if (Unit.equalsIgnoreCase("M"))
		 				{
		 					if (Bvalue.doubleValue() !=150 & Bvalue.doubleValue()!=225 & Bvalue.doubleValue()!=300 &
		 					Bvalue.doubleValue()!=375 & Bvalue.doubleValue()!=450 & Bvalue.doubleValue()!= 525 & Bvalue.doubleValue()!= 600 )		
		 					{
		 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should be either 150,225,300,375,450,525 or 600 (MM)"));
		 						return false;
		 					}
		 				}
		 				else if (Unit.equalsIgnoreCase("I"))
		 				{
		 					if (Bvalue.doubleValue() !=6 & Bvalue.doubleValue()!=9 & Bvalue.doubleValue()!=12 &
		 					Bvalue.doubleValue()!=15 & Bvalue.doubleValue()!=18 & Bvalue.doubleValue()!= 21 & Bvalue.doubleValue()!= 24 )		
		 					{
		 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should be either 6,9,12,15,18,21 or 24 (INCH)"));
		 						return false;
		 					}
		 				}
		 		}
	 			else if (PrdVal.equalsIgnoreCase("BMSD-NEW") |
	 			 		 PrdVal.equalsIgnoreCase("BMFSD-NEW") | PrdVal.equalsIgnoreCase("BEMFSD-NEW"))
	 			{
	 				Boolean ul_spigotyn=isUL_SPIGOT();
	 				if (ul_spigotyn==false)
	 				{
	 					String ul_Act=getUL_ACT();
	 					String ul_intext=getUL_INTEXTACT();
	 					
	 					if (ul_Act.equalsIgnoreCase("B") && ul_intext.equalsIgnoreCase("I") && PrdVal.equalsIgnoreCase("BMSD-NEW"))
	 					{
		 						if (Unit.equalsIgnoreCase("M"))
				 				{
				 					if (Bvalue.doubleValue()<203 | Bvalue.doubleValue()>7315.2)		
				 					{
				 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should be between 203 and 7315.2 (MM)"));
				 						return false;
				 					}
				 				}
				 				else if (Unit.equalsIgnoreCase("I"))
				 				{
				 					if (Bvalue.doubleValue()<8 | Bvalue.doubleValue()>288)		
				 					{
				 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should be between 8 and 288 (INCH)"));
				 						return false;
				 					}
				 				}
	 					}
	 					else
	 					{
			 					if (Unit.equalsIgnoreCase("M"))
				 				{
				 					if (Bvalue.doubleValue()<203 | Bvalue.doubleValue()>1830)		
				 					{
				 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should be between 203 and 1830 (MM)"));
				 						return false;
				 					}
				 				}
				 				else if (Unit.equalsIgnoreCase("I"))
				 				{
				 					if (Bvalue.doubleValue()<8 | Bvalue.doubleValue()>72)		
				 					{
				 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should be between 8 and 72 (INCH)"));
				 						return false;
				 					}
				 				}
	 					}
	 					if (ul_Act.equalsIgnoreCase("B"))
	 					{
	 						String ul_cltype=getUL_CLASSTYPE();
	 						String ul_temp=getUL_TEMP();
	 						if (ul_cltype.equalsIgnoreCase("CLASS I") && ul_temp.equalsIgnoreCase("250T"))
	 						{
	 							if (Unit.equalsIgnoreCase("M"))
	 			 				{
	 			 					if (Bvalue.doubleValue()<203 | Bvalue.doubleValue()>1220)		
	 			 					{
	 			 						log.saveError("Error", Msg.getMsg(getCtx(), "For Class I-250 for Belimo,B value should be between 203 and 1220 (MM)"));
	 			 						return false;
	 			 					}
	 			 				}
	 			 				else if (Unit.equalsIgnoreCase("I"))
	 			 				{
	 			 					if (Bvalue.doubleValue()<8 | Bvalue.doubleValue()>48)		
	 			 					{
	 			 						log.saveError("Error", Msg.getMsg(getCtx(), "For Class I-250 for Belimo,B value should be between 8 and 48 (INCH)"));
	 			 						return false;
	 			 					}
	 			 				}
	 						}
	 					}
	 				}
	 				else
	 				{
	 					if (Unit.equalsIgnoreCase("M"))
		 				{
		 					if (Bvalue.doubleValue()<203 | Bvalue.doubleValue()>915)		
		 					{
		 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should be between 203 and 915 (MM)"));
		 						return false;
		 					}
		 				}
		 				else if (Unit.equalsIgnoreCase("I"))
		 				{
		 					if (Bvalue.doubleValue()<8 | Bvalue.doubleValue()>36)		
		 					{
		 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should be between 8 and 36 (INCH)"));
		 						return false;
		 					}
		 				}
	 				}
	 			}

	 			else if (PrdVal.equalsIgnoreCase("BMFSD-T") | PrdVal.equalsIgnoreCase("BMSD") |
	 					 PrdVal.equalsIgnoreCase("BEMFSD-T"))
	 			{
	 				if (Unit.equalsIgnoreCase("M"))
	 				{
	 					if (Bvalue.doubleValue()<203 | Bvalue.doubleValue()>1830)		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should be between 203 and 1830 (MM)"));
	 						return false;
	 					}
	 				}
	 				else if (Unit.equalsIgnoreCase("I"))
	 				{
	 					if (Bvalue.doubleValue()<8 | Bvalue.doubleValue()>72)		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should be between 8 and 72 (INCH)"));
	 						return false;
	 					}
	 				}
	 			}
	 			else if (PrdVal.equalsIgnoreCase("BMSD/R") | PrdVal.equalsIgnoreCase("BMFSD/R-T") | PrdVal.equalsIgnoreCase("BEMFSD/R-T"))
	 			{
	 				if (Unit.equalsIgnoreCase("M"))
	 				{
	 					if (Bvalue.doubleValue()<203 | Bvalue.doubleValue()>915)		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should be between 203 and 915 (MM)"));
	 						return false;
	 					}
	 				}
	 				else if (Unit.equalsIgnoreCase("I"))
	 				{
	 					if (Bvalue.doubleValue()<8 | Bvalue.doubleValue()>36)		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should be between 8 and 36 (INCH)"));
	 						return false;
	 					}
	 				}
	 			}
	 			else if (PrdVal.equalsIgnoreCase("BMFD-NEW") | PrdVal.equalsIgnoreCase("BEMFD-NEW"))
	 			{
	 				Boolean ul_spigotyn=isUL_SPIGOT();
	 				if (ul_spigotyn==false)
	 				{
	 					if (Unit.equalsIgnoreCase("M"))
		 				{
		 					if (Bvalue.doubleValue()<102 | Bvalue.doubleValue()>1830)		
		 					{
		 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should be between 102 and 1830 (MM)"));
		 						return false;
		 					}
		 				}
		 				else if (Unit.equalsIgnoreCase("I"))
		 				{
		 					if (Bvalue.doubleValue()<4 | Bvalue.doubleValue()>72)		
		 					{
		 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should be between 4 and 72 (INCH)"));
		 						return false;
		 					}
		 				}
	 				}
	 				else
	 				{
	 					if (Unit.equalsIgnoreCase("M"))
		 				{
		 					if (Bvalue.doubleValue()<102 | Bvalue.doubleValue()>915)		
		 					{
		 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should be between 102 and 915 (MM)"));
		 						return false;
		 					}
		 				}
		 				else if (Unit.equalsIgnoreCase("I"))
		 				{
		 					if (Bvalue.doubleValue()<4 | Bvalue.doubleValue()>36)		
		 					{
		 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should be between 4 and 36 (INCH)"));
		 						return false;
		 					}
		 				}
	 				}
	 			}
	 			else if (PrdVal.equalsIgnoreCase("BMFD-T") | PrdVal.equalsIgnoreCase("BEMFD-T") |
	 					PrdVal.equalsIgnoreCase("BFD-F") | PrdVal.equalsIgnoreCase("BEFD-F") |
	 					PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-F") |
	 					PrdVal.equalsIgnoreCase("BMFD-TF") | PrdVal.equalsIgnoreCase("BEMFD-TF"))
	 			{
	 				if (Unit.equalsIgnoreCase("M"))
	 				{
	 					if (Bvalue.doubleValue()<102 | Bvalue.doubleValue()>1830)		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should be between 102 and 1830 (MM)"));
	 						return false;
	 					}
	 				}
	 				else if (Unit.equalsIgnoreCase("I"))
	 				{
	 					if (Bvalue.doubleValue()<4 | Bvalue.doubleValue()>72)		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should be between 4 and 72 (INCH)"));
	 						return false;
	 					}
	 				}
	 			}
	 			else if (PrdVal.equalsIgnoreCase("BMFD/R-T") | PrdVal.equalsIgnoreCase("BEMFD/R-T") |
	 					PrdVal.equalsIgnoreCase("BFD/R-F") | PrdVal.equalsIgnoreCase("BEFD/R-F") |
	 					PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F") |
	 					PrdVal.equalsIgnoreCase("BMFD/R-TF") | PrdVal.equalsIgnoreCase("BEMFD/R-TF"))
	 			{
	 				if (Unit.equalsIgnoreCase("M"))
	 				{
	 					if (Bvalue.doubleValue()<102 | Bvalue.doubleValue()>915)		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should be between 102 and 915 (MM)"));
	 						return false;
	 					}
	 				}
	 				else if (Unit.equalsIgnoreCase("I"))
	 				{
	 					if (Bvalue.doubleValue()<4 | Bvalue.doubleValue()>36)		
	 					{
	 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should be between 4 and 36 (INCH)"));
	 						return false;
	 					}
	 				}
	 			}
	 			else if (PrdVal.equalsIgnoreCase("AL1") | PrdVal.equalsIgnoreCase("AL2"))
	 			{
	 				if (Bvalue.doubleValue() !=450 & Bvalue.doubleValue()!=600 & Bvalue.doubleValue()!=750 &
	 					Bvalue.doubleValue()!=900 & Bvalue.doubleValue()!=1050 & Bvalue.doubleValue()!=1200 &
	 					Bvalue.doubleValue()!=1350 & Bvalue.doubleValue()!=1500 & Bvalue.doubleValue()!=1650 &
	 					Bvalue.doubleValue()!=1800 & Bvalue.doubleValue()!=1950 & Bvalue.doubleValue()!=2100 &
	 					Bvalue.doubleValue()!=2250 & Bvalue.doubleValue()!=2400)
 					{
 						log.saveError("Error", Msg.getMsg(getCtx(), "B value should be either 450,600,750,900,1050,1200,1350,1500,1650, 1800,1950,2100,2250,2400 (MM)"));
 						return false;
 					}
	 			}
 			}
	 		
	 /*		//Added as per Shankar mail on 18 Sept 2016 for HONEYWELL - MS4604F1210
	 		if (PrdVal.equalsIgnoreCase("BEMFSD-T") | PrdVal.equalsIgnoreCase("BMFSD-T") | PrdVal.equalsIgnoreCase("BMSD/R") |
	 			PrdVal.equalsIgnoreCase("BEMFSD/R-T") | PrdVal.equalsIgnoreCase("BMFSD/R-T") | PrdVal.equalsIgnoreCase("BMSD"))
 			{ 
					Integer actIDLT=getBeta_Qtn_Actuator_ID();
					String sqlLTYN="Select nvl(LTSERIESYN,'N') from beta_qtn_actuator where beta_qtn_actuator_id=?" ;
					String ltSeriesYN = DB.getSQLValueString(get_TrxName(), sqlLTYN,actIDLT);
					if (ltSeriesYN.equalsIgnoreCase("Y"))  //HONEYWELL - MS4604F1210  1000045
					{
						if (Unit.equalsIgnoreCase("M"))
		 				{
		 					if (Avalue.doubleValue()>508 | Bvalue.doubleValue()>508)		
		 					{
		 						log.saveError("Error", Msg.getMsg(getCtx(), "A & B value should not be greater than 508 (MM) for LT series"));
		 						return false;
		 					}
		 					
		 				}
		 				else if (Unit.equalsIgnoreCase("I"))
		 				{
		 					if (Avalue.doubleValue()>20 | Bvalue.doubleValue()>20)		
		 					{
		 						log.saveError("Error", Msg.getMsg(getCtx(), "A & B value should not be greater than 20 (INCH) for LT series"));
		 						return false;
		 					}
		 				}	
					}
 			  }  */
	 		
	 		//Added on 22/12/2016 for LT series actuators size should be 1-20 OR 36-40
	 		Integer actIDLT=getBeta_Qtn_Actuator_ID();
	 		if (actIDLT > 0)
	 		{
	 			if (PrdVal.equalsIgnoreCase("BEMFSD-NEW") | PrdVal.equalsIgnoreCase("BMSD-NEW") | 
	 					 PrdVal.equalsIgnoreCase("BMFSD-NEW") | PrdVal.equalsIgnoreCase("BMFD-NEW") | 
	 					 PrdVal.equalsIgnoreCase("BEMFD-NEW")) 
	 			{
		 			String sqlLTYN="Select nvl(LTSERIESYN,'N') from beta_qtn_actuator where beta_qtn_actuator_id=?" ;
					String ltSeriesYN = DB.getSQLValueString(get_TrxName(), sqlLTYN,actIDLT);
					if (ltSeriesYN.equalsIgnoreCase("Y"))  //HONEYWELL - MS4604F1210  & HONEYWELL - MS8104F1210
					{
						double aInchLT=0,bInchLT=0;
						String ul_Act=getUL_ACT();
	 					String ul_intext=getUL_INTEXTACT();
	 					
	 					if (ul_Act.equalsIgnoreCase("B") && ul_intext.equalsIgnoreCase("I") && PrdVal.equalsIgnoreCase("BMSD-NEW"))
	 					{
							;
	 					}
	 					else
	 					{
	 						if (Unit.equalsIgnoreCase("M"))
			 				{
								aInchLT = Math.round(Avalue.doubleValue()/25.4);
								bInchLT = Math.round(Bvalue.doubleValue()/25.4);
			 				}
							else if (Unit.equalsIgnoreCase("I"))
							{
								aInchLT = Avalue.doubleValue();
								bInchLT = Bvalue.doubleValue();
							}
							if (aInchLT<=20 & bInchLT<=20) 
								;
							else if (aInchLT>36 & aInchLT<=40 & bInchLT>36 & bInchLT<=40)
								;
							else if ((aInchLT<=20 | bInchLT<=20) & ((aInchLT>36 & aInchLT<=40) | (bInchLT>36 & bInchLT<=40)))
								;
							else
							{
								log.saveError("Error", Msg.getMsg(getCtx(), "A & B value should be between (1 to 20) or (36 to 40) (INCH) for LT series"));
								return false;
							}
	 					}
					}
				}
				//End of Added on 22/12/2016 for LT series actuators size should be 1-20 OR 36-40
				
				//Added on 23.10.2017 for VAV & VCD Actuators HONEYWELL - CN7510A2001 & CN7505A2001
			/*	String sqlVavYN="Select nvl(USEFORVAV,'N') from beta_qtn_actuator where beta_qtn_actuator_id=?" ;
				String VavActYN = DB.getSQLValueString(get_TrxName(), sqlVavYN,actIDLT);
				if (VavActYN.equalsIgnoreCase("Y"))  //HONEYWELL - CN7510A2001 & CN7505A2001
				{
					if (!(PrdVal.equalsIgnoreCase("EBP1") | PrdVal.equalsIgnoreCase("EBP2") | PrdVal.equalsIgnoreCase("EBP3") |
						  PrdVal.equalsIgnoreCase("EBP4") | PrdVal.equalsIgnoreCase("EBP5") | PrdVal.equalsIgnoreCase("EBP6") |
						  PrdVal.equalsIgnoreCase("EBP7") | PrdVal.equalsIgnoreCase("EBP8") | PrdVal.equalsIgnoreCase("SDVBP") |
						  PrdVal.equalsIgnoreCase("SDVBPE") | PrdVal.equalsIgnoreCase("PI3VCD") | PrdVal.equalsIgnoreCase("PIAVCD") |
						  PrdVal.equalsIgnoreCase("AUVCDB") | PrdVal.equalsIgnoreCase("AUVCDH") | PrdVal.equalsIgnoreCase("AUVCDF") |
						  PrdVal.equalsIgnoreCase("AUVCDS") | PrdVal.equalsIgnoreCase("3VCDB") | PrdVal.equalsIgnoreCase("3VCDH") |
						  PrdVal.equalsIgnoreCase("3VCDF") | PrdVal.equalsIgnoreCase("3VCDS") | PrdVal.equalsIgnoreCase("LVCDH") |
						  PrdVal.equalsIgnoreCase("LVCDF") | PrdVal.equalsIgnoreCase("AGVCDB") | PrdVal.equalsIgnoreCase("AGVCDF") | 
						  PrdVal.equalsIgnoreCase("AGVCDS") | PrdVal.equalsIgnoreCase("VDR")))
					{
						log.saveError("Error", Msg.getMsg(getCtx(), "The selected actuator model is not suitable for this product"));
		 				return false;
					}  */
					
					if (PrdVal.equalsIgnoreCase("EBP1") | PrdVal.equalsIgnoreCase("EBP2") | PrdVal.equalsIgnoreCase("EBP3") |
						PrdVal.equalsIgnoreCase("EBP4"))
					{
						String sqlActN1="Select name from beta_qtn_actuator where beta_qtn_actuator_id=?" ;
						String ActN1 = DB.getSQLValueString(get_TrxName(), sqlActN1,actIDLT);
						if (ActN1.equalsIgnoreCase("HONEYWELL - CN7510A2001"))
						{
							log.saveError("Error", Msg.getMsg(getCtx(), "The selected actuator model is not suitable for this product"));
			 				return false;
						}
					}
					else if (PrdVal.equalsIgnoreCase("EBP5") | PrdVal.equalsIgnoreCase("EBP6") | PrdVal.equalsIgnoreCase("EBP7") |
						PrdVal.equalsIgnoreCase("EBP8"))
					{
						String sqlActN1="Select name from beta_qtn_actuator where beta_qtn_actuator_id=?" ;
						String ActN1 = DB.getSQLValueString(get_TrxName(), sqlActN1,actIDLT);
						if (ActN1.equalsIgnoreCase("HONEYWELL - CN7505A2001"))
						{
							log.saveError("Error", Msg.getMsg(getCtx(), "The selected actuator model is not suitable for this product"));
			 				return false;
						}
					}
				
				//End of Checking for VAV & VCD Actuators HONEYWELL - CN7510A2001 & CN7505A2001
	 		}
	 		//End of LT Series Act
	 		
	 		//Check Slot No for item:SEC   **Added on 03Mar2014
	 		if (PrdVal.equalsIgnoreCase("SEC"))
	 		{
	 			BigDecimal slotNo = getSLOTNO();
	 			if (slotNo == null | slotNo.doubleValue()<=0) 
	 			{
	 				log.saveError("Error", Msg.getMsg(getCtx(), "Slot No. should be greater than zero"));
	 				return false;
	 			}
	 			else if (slotNo.doubleValue() !=1 & slotNo.doubleValue()!=2 & slotNo.doubleValue()!=3 &
	 					slotNo.doubleValue()!=4 & slotNo.doubleValue()!=5 & slotNo.doubleValue()!= 6 )		
				{
	 				log.saveError("Error", Msg.getMsg(getCtx(), "Slot No. should be either 1,2,3,4,5 or 6"));
					return false;
				}
	 		}
	 		//End of Check Slot No for item:SEC   **Added on 03Mar2014
	 		
	 		//Check Neck Dia is Zero
	 		if (PrdVal.equalsIgnoreCase("RAD-GNR") |
				    PrdVal.equalsIgnoreCase("SADB-GNR") | PrdVal.equalsIgnoreCase("SADM-GNR") |
				    PrdVal.equalsIgnoreCase("SADB-EG-GNR") | PrdVal.equalsIgnoreCase("SADM-EG-GNR"))
	 		{
	 			BigDecimal neckDia = getNECKDIA();
	 			if (neckDia == null | neckDia.doubleValue()<=0) 
	 			{
	 				log.saveError("Error", Msg.getMsg(getCtx(), "Neck Diameter should be greater than zero"));
	 				return false;
	 			}
	 		}
	 		else if (PrdVal.equalsIgnoreCase("BMSD/R") | PrdVal.equalsIgnoreCase("BMFSD/R-T") | PrdVal.equalsIgnoreCase("BEMFSD/R-T") |
	 				 PrdVal.equalsIgnoreCase("BMFD/R-T") | PrdVal.equalsIgnoreCase("BEMFD/R-T") |
	 				 PrdVal.equalsIgnoreCase("BFD/R-F") | PrdVal.equalsIgnoreCase("BEFD/R-F") |
	 				 PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F") |
	 				 PrdVal.equalsIgnoreCase("BMFD/R-TF") | PrdVal.equalsIgnoreCase("BEMFD/R-TF"))
	 		{
	 			BigDecimal neckDia = getNECKDIA();
	 			if(neckDia == null | neckDia.doubleValue()<4 | neckDia.doubleValue()>32)
	 			{
	 				log.saveError("Error", Msg.getMsg(getCtx(), "Neck Diameter (/R) should be between 4 and 32 (INCH)"));
	 				return false;
	 			}
	 			if (Unit.equalsIgnoreCase("I"))
 				{
	 				if (neckDia.doubleValue()+2>Bvalue.doubleValue() | neckDia.doubleValue()+2>Avalue.doubleValue())
	 				{
	 					log.saveError("Error", Msg.getMsg(getCtx(), "Neck Diameter (/R) should be 2(INCH) less than  A & B"));
		 				return false;
	 				}
 				}
	 			else if (Unit.equalsIgnoreCase("M"))
 				{
	 				double aInchVal = Math.round(Avalue.doubleValue()/25.4);
	 				double bInchVal = Math.round(Bvalue.doubleValue()/25.4);
	 				if (neckDia.doubleValue()+2>aInchVal | neckDia.doubleValue()+2>bInchVal)
	 				{
	 					log.saveError("Error", Msg.getMsg(getCtx(), "Neck Diameter (/R) should be 2(INCH) less than  A & B"));
		 				return false;
	 				}
 				}
	 		}
	 		else if (PrdVal.equalsIgnoreCase("BMFSD-NEW") | PrdVal.equalsIgnoreCase("BEMFSD-NEW") |
					 PrdVal.equalsIgnoreCase("BMSD-NEW") | PrdVal.equalsIgnoreCase("BEMFD-NEW") |
					 PrdVal.equalsIgnoreCase("BMFD-NEW"))
	 		{
	 			Boolean ul_spigotyn=isUL_SPIGOT();
	 			if (ul_spigotyn==true)
	 			{
	 				BigDecimal neckDia = getNECKDIA();
		 			if(neckDia == null | neckDia.doubleValue()<4 | neckDia.doubleValue()>32)
		 			{
		 				log.saveError("Error", Msg.getMsg(getCtx(), "Neck Diameter (/R) should be between 4 and 32 (INCH)"));
		 				return false;
		 			}
		 			if (Unit.equalsIgnoreCase("I"))
	 				{
		 				if (neckDia.doubleValue()+2>Bvalue.doubleValue() | neckDia.doubleValue()+2>Avalue.doubleValue())
		 				{
		 					log.saveError("Error", Msg.getMsg(getCtx(), "Neck Diameter (/R) should be 2(INCH) less than  A & B"));
			 				return false;
		 				}
	 				}
		 			else if (Unit.equalsIgnoreCase("M"))
	 				{
		 				double aInchVal = Math.round(Avalue.doubleValue()/25.4);
		 				double bInchVal = Math.round(Bvalue.doubleValue()/25.4);
		 				if (neckDia.doubleValue()+2>aInchVal | neckDia.doubleValue()+2>bInchVal)
		 				{
		 					log.saveError("Error", Msg.getMsg(getCtx(), "Neck Diameter (/R) should be 2(INCH) less than  A & B"));
			 				return false;
		 				}
	 				}
	 			}
	 		}
	 		//End of Check Neck Dia is Zero
	 		
	 		//Check Neck Parameter is Zero
	 		if (PrdVal.equalsIgnoreCase("RAD-GNS") |
				    PrdVal.equalsIgnoreCase("SADB-GNS") | PrdVal.equalsIgnoreCase("SADM-GNS") |
				    PrdVal.equalsIgnoreCase("SADB-EG-GNS") | PrdVal.equalsIgnoreCase("SADM-EG-GNS"))
	 		{
	 			BigDecimal neckA = getGNA();
	 			BigDecimal neckB = getGNB();
	 			if (neckA == null | neckA.doubleValue()<=0) 
	 			{
	 				log.saveError("Error", Msg.getMsg(getCtx(), "G.Neck Width should be greater than zero"));
	 				return false;
	 			}
	 			if (neckB == null | neckB.doubleValue()<=0) 
	 			{
	 				log.saveError("Error", Msg.getMsg(getCtx(), "G.Neck Height should be greater than zero"));
	 				return false;
	 			}
	 			
	 		}
	 		//End of Check Neck Parameter is Zero
	 		
	 		
	 		//Check Supply/H is Zero for type C items
			String Type1 = getType();
			Type1 = Type1.toUpperCase();
			if (Type1.contentEquals("C"))  
			{
				BigDecimal sh = getSUPPLY();
				Double shv = new Double(""+(BigDecimal)sh);
				if (shv <= 0) 
				{
					log.saveError("Error", Msg.getMsg(getCtx(), "Supply/H should not be zero"));
					return false;
				}
				if (shv> Avalue.doubleValue())
				{
					log.saveError("Error", Msg.getMsg(getCtx(), "Supply/H should not be greater than A value"));
					return false;
				}	
			}
			
			//Check Length is Zero
			if (PrdVal.equalsIgnoreCase("PGL") | PrdVal.equalsIgnoreCase("RSA") | PrdVal.equalsIgnoreCase("RSDS") |
				PrdVal.equalsIgnoreCase("RMDS") |PrdVal.equalsIgnoreCase("RS") | PrdVal.equalsIgnoreCase("RM")	)
			{
				BigDecimal LenValue = getLENGTH(); 
				Double lv = new Double(""+(BigDecimal)LenValue);
				if (lv<=0) 
				{
					log.saveError("Error", Msg.getMsg(getCtx(), "Length should not be zero"));
				    return false;
				}
			}
			
			//Check Airway is blank
			if (PrdVal.equalsIgnoreCase("PGL") | PrdVal.equalsIgnoreCase("RSA")) 
			{
				String aw = getAIRWAY();
		 		if (aw == null)
		 		{
	 				log.saveError("Error", Msg.getMsg(getCtx(), "AirWay should not be Blank"));
	 				return false;
	 			}	
		 		else
		 		{
		 			if (aw.equalsIgnoreCase("0") | aw.equalsIgnoreCase(""))
		 			{
		 				log.saveError("Error", Msg.getMsg(getCtx(), "AirWay should not be Blank"));
		 				return false;
		 			}
		 		}
			}
			
			//Check TBAR Ceiling  Added on 23.02.2014
			boolean tBarYN = isTBARYN();
			if (tBarYN)
			{
				if (Unit.equalsIgnoreCase("M"))
 				{
 					if ((Avalue.doubleValue() ==150 & Bvalue.doubleValue()==150) | 
 						(Avalue.doubleValue() ==225 & Bvalue.doubleValue()==225) |
 						(Avalue.doubleValue() ==300 & Bvalue.doubleValue()==300) |
 						(Avalue.doubleValue() ==375 & Bvalue.doubleValue()==375) )
 						tBarYN = true;
 					else
 					{
 						log.saveError("Error", Msg.getMsg(getCtx(), "T-Bar Ceiling can applied for sizes 150X150,225X225,300X300,375X375 (MM) Only"));
 						return false;
 					}
 				}
 				else if (Unit.equalsIgnoreCase("I"))
 				{
 					if ((Avalue.doubleValue() ==6 & Bvalue.doubleValue()==6) | 
 	 						(Avalue.doubleValue() ==9 & Bvalue.doubleValue()==9) |
 	 						(Avalue.doubleValue() ==12 & Bvalue.doubleValue()==12) |
 	 						(Avalue.doubleValue() ==15 & Bvalue.doubleValue()==15) )
 	 						tBarYN = true;
 					else
 					{
 						log.saveError("Error", Msg.getMsg(getCtx(), "T-Bar Ceiling can applied for sizes 6X6,9X9,12X12,15X15 (INCH) Only"));
 						return false;
 					}
 				}
			}
			//End for Check TBAR Ceiling
			
			//Check LVBYAREA Added on 09.04.2014
			boolean lvByAreaYN = isLVBYAREAYN();
			if (lvByAreaYN)
			{
				if (Bvalue.doubleValue() !=1000)
				{
					log.saveError("Error", Msg.getMsg(getCtx(), "For Louvers By Area, B Value should be always 1000"));
					return false;
				}
			}
			//End of Check LVBYAREA Added on 09.04.2014
			
			//Check Actuator Quantity Added on 04.06.2014
			if (PrdVal.equalsIgnoreCase("MVCD") | PrdVal.equalsIgnoreCase("SSMVCD"))
			{
				Integer actID=getBeta_Qtn_Actuator_ID();
				if (actID.doubleValue()>0)
				{
					BigDecimal actQty=getACTQTY();
					if (actQty.doubleValue()<=0)
				 	{
				 		log.saveError("Error", Msg.getMsg(getCtx(), "Actuator Qty should be greater than zero"));
						return false;
				 	}
				 	if (actQty.intValue() != actQty.doubleValue())
				 	{
				 		log.saveError("Error", Msg.getMsg(getCtx(), "Actuator Qty with decimal digits not allowed."));
						return false;
				 	}
				}
			}
			//End of Check Actuator Quantity Added on 04.06.2014
			
			//Set Actuator Quantity for display Purpose
			if (PrdVal.equalsIgnoreCase("AUVCDF") | PrdVal.equalsIgnoreCase("AUVCDB") |
				PrdVal.equalsIgnoreCase("AUVCDH") | PrdVal.equalsIgnoreCase("AUVCDS") |
				PrdVal.equalsIgnoreCase("AGVCDF") | PrdVal.equalsIgnoreCase("AGVCDB") | PrdVal.equalsIgnoreCase("AGVCDS") |
				PrdVal.equalsIgnoreCase("LVCDF") | PrdVal.equalsIgnoreCase("LVCDH") |
				PrdVal.equalsIgnoreCase("3VCDF") | PrdVal.equalsIgnoreCase("3VCDB") |
				PrdVal.equalsIgnoreCase("3VCDH") | PrdVal.equalsIgnoreCase("3VCDS") | 
				PrdVal.equalsIgnoreCase("VDR") | PrdVal.equalsIgnoreCase("PI3VCD") | PrdVal.equalsIgnoreCase("PIAVCD") | 
				PrdVal.equalsIgnoreCase("MPRD"))
			{
				Integer actID=getBeta_Qtn_Actuator_ID();
				String drv=getDRIVE();
			/*	if (actID.doubleValue()==0 & drv.equalsIgnoreCase("M"))
				{
			 		log.saveError("Error", Msg.getMsg(getCtx(), "Actuator should not be blank"));
					return false;
			 	}   */
				
				if (actID.doubleValue()>0)
				{
					if (Bvalue.doubleValue()>0 &  Avalue.doubleValue()>0)
					{
						double aMM=0,bMM=0;
						BigDecimal actQty=Env.ZERO;
						if (Unit.equalsIgnoreCase("I"))
		 				{
			 				aMM = Math.round(Avalue.doubleValue()*25);
			 				bMM = Math.round(Bvalue.doubleValue()*25);
		 				}
						else if (Unit.equalsIgnoreCase("M"))
						{
							aMM = Avalue.doubleValue();
							bMM = Bvalue.doubleValue();
						}
						//=IF(AND(B15<=1200,(C15<=1200)),(1),IF(AND(B15>1200,(C15<=1200)),(2),IF(AND(B15<=1200,(C15>1200)),(2),(IF(AND(B15>1200,(C15>1200)),(4))))))
	
						if (aMM<=1200 & bMM<=1200)
							actQty = new BigDecimal(1);
						else if (aMM>1200 & bMM<=1200)
							actQty = new BigDecimal(2);
						else if (aMM<=1200 & bMM>1200)
							actQty = new BigDecimal(2);
						else if (aMM>1200 & bMM>1200)
							actQty = new BigDecimal(4);
						
						setACTQTY(actQty);
					}
				}
			}
			//End of Set Actuator Quantity for display Purpose
			
			//Check Bush/Bearing Value for PI3VCD & PIAVCD & LVCDF & LVCDH
			if (PrdVal.equalsIgnoreCase("PI3VCD") | PrdVal.equalsIgnoreCase("PIAVCD"))
			{
				String bushBear = getBUSHBEAR();
				if (bushBear.equalsIgnoreCase("S"))
				{
					log.saveError("Error", Msg.getMsg(getCtx(), "The option 'SS bearing ID 12MM' is not applicable for " + PrdVal));
					return false;
				}
			}
			else if (PrdVal.equalsIgnoreCase("LVCDF") | PrdVal.equalsIgnoreCase("LVCDH"))
			{
				String bushBear = getBUSHBEAR();
				if (bushBear.equalsIgnoreCase("P"))
				{
					log.saveError("Error", Msg.getMsg(getCtx(), "The option 'Plastic Bush' is not applicable for " + PrdVal));
					return false;
				}
			} 
			//End of Check Bush/Bearing Value for PI3VCD & PIAVCD & LVCDF & LVCDH
			
			//Check Mechanism Value for LVCDF & LVCDH
			if (PrdVal.equalsIgnoreCase("LVCDF") | PrdVal.equalsIgnoreCase("LVCDH") |
				PrdVal.equalsIgnoreCase("3VCDH") | PrdVal.equalsIgnoreCase("AUVCDH"))
			{
				String mech = getMECHANISM();
				if (mech.equalsIgnoreCase("G"))
				{
					log.saveError("Error", Msg.getMsg(getCtx(), "The option 'Gear' is not applicable for " + PrdVal));
					return false;
				}
			} 
			//End of Check Mechanism Value for LVCDF & LVCDH
			//Check Metal Thickness Value for PI3VCD
			if (PrdVal.equalsIgnoreCase("PI3VCD"))
			{
				String metalThick = getMETALTHICK();
				if (metalThick.equalsIgnoreCase("G1"))
				{
					log.saveError("Error", Msg.getMsg(getCtx(), "The GI thickness 0.9MM is not applicable for PI3VCD"));
					return false;
				}
			}
			//End of Check Metal Thickness Value for PI3VCD
			
			//Check Heater Std. Cost=0
			if (PrdVal.equalsIgnoreCase("SDVBPE") | PrdVal.equalsIgnoreCase("SDVE") | PrdVal.equalsIgnoreCase("SDVE100") |
			 	PrdVal.equalsIgnoreCase("SDVE150") | PrdVal.equalsIgnoreCase("SDVE200") |
			 	PrdVal.equalsIgnoreCase("SDVE250") | PrdVal.equalsIgnoreCase("SDVE300") |
			 	PrdVal.equalsIgnoreCase("SDVE350") | PrdVal.equalsIgnoreCase("SDVE400"))
			{
				Integer heaterID=getBeta_Qtn_Heater_ID();
				if (heaterID.doubleValue()>0)
				{
					String sqlHeater = "Select nvl(stdcost,0) from beta_qtn_heater where beta_qtn_heater_id=?";
					BigDecimal heaterCost = DB.getSQLValueBD(get_TrxName(), sqlHeater, heaterID); 
					if (heaterCost.doubleValue()==0)
					{
						 log.saveError("Error", Msg.getMsg(getCtx(), "Cost for the Heater should not be zero. Please check with Accounts/IT dept."));
						 return false;
					}
				}
			}
			//End of Check Heater Std. Cost=0
			
			//Checking Actuator model for Non-Bypass VAV (SDV,SDVE,CTC,CTCDS,RM,RMDS,RS,RSDS)
			boolean IsVavYn =isSETVAVYN();
			Integer VAVactID=getBeta_Qtn_Actuator_ID();
			if (IsVavYn==true && VAVactID.doubleValue()>0)
			{
				String sqlvavact="Select nvl(area,0) from beta_qtn_actuator where beta_qtn_actuator_id=?";
				BigDecimal actar = DB.getSQLValueBD(get_TrxName(), sqlvavact,VAVactID);
				if (PrdVal.equalsIgnoreCase("RM") | PrdVal.equalsIgnoreCase("RMDS") |
						PrdVal.equalsIgnoreCase("RS") | PrdVal.equalsIgnoreCase("RSDS"))
				{
					Double VAVarea=(Avalue.doubleValue()*.001)*(Bvalue.doubleValue()*.001);
					//BigDecimal vavar=new BigDecimal(VAVarea);
					if (VAVarea>actar.doubleValue())
					{
						log.saveError("Error", Msg.getMsg(getCtx(), "Wrong Actuator. The selected actuator is not suitable for this size. Please select the model as per the area."));
						return false;
					}
					else
					{
						String sqlvavact1="Select count(*) from beta_qtn_actuator where area<? and useforsdv='Y' and ?<=area ";
						BigDecimal actcnt = DB.getSQLValueBD(get_TrxName(), sqlvavact1,actar,new BigDecimal(VAVarea));
						if (actcnt.doubleValue()>0)
						{
							log.saveError("Error", Msg.getMsg(getCtx(), "Wrong Actuator. The selected actuator is not suitable for this size. Please select the model as per the area."));
							return false;
						}
					}
				}
				/*else   Commented this part as per the request from Shankar on 30.08.23
				{
					if (actar.doubleValue()>1)
					{
						log.saveError("Error", Msg.getMsg(getCtx(), "Wrong Actuator. The selected actuator is not suitable for this dimension"));
						return false;
					}
				}*/
			}
			//End of Checking Actuator model for Non-Bypass VAV

			//Checking Thermostat selected for combo actuator cases		#02May2019
			Integer thermID=getBETA_QTN_THERMOSTAT_ID();
			if (thermID.doubleValue()>0 && VAVactID.doubleValue()>0)
			{
				String sqlactcomb="Select combomodelyn from beta_qtn_actuator where beta_qtn_Actuator_id=? ";
				String actcomb = DB.getSQLValueString(get_TrxName(), sqlactcomb,VAVactID);
				if (actcomb.equalsIgnoreCase("Y"))
				{
					log.saveError("Error", Msg.getMsg(getCtx(), "For combo actuators (Actuator+Therm), no need to select thermostat separately."));
					return false;
				}
			}
			//End of Checking Thermostat selected for combo actuator cases
			
			//Check Actuator selected or not
			if (PrdVal.equalsIgnoreCase("BMSD") | PrdVal.equalsIgnoreCase("BMFSD-T") |
				PrdVal.equalsIgnoreCase("BEMFSD-T") | PrdVal.equalsIgnoreCase("BMSD/R") |
				PrdVal.equalsIgnoreCase("BMFSD/R-T") | PrdVal.equalsIgnoreCase("BEMFSD/R-T") |
				PrdVal.equalsIgnoreCase("BMFD-T") | PrdVal.equalsIgnoreCase("BEMFD-T") |
				PrdVal.equalsIgnoreCase("BMFD/R-T") | PrdVal.equalsIgnoreCase("BEMFD/R-T") |
				PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-F") |
				PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F") |
				PrdVal.equalsIgnoreCase("BMFD-TF") | PrdVal.equalsIgnoreCase("BEMFD-TF") |
				PrdVal.equalsIgnoreCase("BMFD/R-TF") | PrdVal.equalsIgnoreCase("BEMFD/R-TF"))
			{
				Integer actID=getBeta_Qtn_Actuator_ID();
				if (actID.doubleValue()<=0)
				{
					log.saveError("Error", Msg.getMsg(getCtx(), "Actuator should not be blank"));
					return false;
				}
				else if (actID.doubleValue()>0)
				{
					//ActCost Vs EstCost for actuator (UL Damper Items only) (Added on 20.09.2015)
					String sqlmCost = "Select nvl(mc.currentcostprice,0) as mCost,act.stdcost as stdCost" +
							" from m_cost mc inner join beta_qtn_actuator act ON " +
							"mc.m_product_id= act.m_product_id AND mc.m_costelement_id=1000001 WHERE act.beta_qtn_actuator_id=?";
					
					PreparedStatement pstmtact = null;
		 	    	ResultSet rsact = null;
		 	    	try 
		 	    	{
					    pstmtact = DB.prepareStatement(sqlmCost, "DSPL");
					    pstmtact.setInt(1, actID);
					    rsact = pstmtact.executeQuery();
					      
					    if(rsact.next()) 
					    {
					    	mCost = rsact.getDouble("mCost");	
					    	stdCost = rsact.getDouble("stdCost");
					    }
					    rsact.close();
					    pstmtact.close();
		 	    	 }catch(Exception e){log.log(Level.SEVERE, sqlmCost, e.getMessage());return false; } 
		 	    	 finally {DB.close(rsact, pstmtact); }
					 if (mCost > stdCost)
					 {
						 log.saveError("Error", Msg.getMsg(getCtx(), "Wrong cost. Please check with Accounts/IT dept."));
						 return false;
					 }
					//End of ActCost Vs EstCost for actuator (UL Damper Items only)
					if (PrdVal.equalsIgnoreCase("BMSD") | PrdVal.equalsIgnoreCase("BMFSD-T") |
							PrdVal.equalsIgnoreCase("BEMFSD-T") | PrdVal.equalsIgnoreCase("BMSD/R") |
							PrdVal.equalsIgnoreCase("BMFSD/R-T") | PrdVal.equalsIgnoreCase("BEMFSD/R-T"))
					{
							String sqlactType="Select nvl(shortname,'N') from beta_qtn_actuator where beta_qtn_actuator_id=?" +
									" and nvl(useforsd,'N')='Y'";
							String SN = DB.getSQLValueString(get_TrxName(), sqlactType,actID);
							if (SN==null)
								SN = "N";
							//NEW CODE ON 22.01.2018
							//Added BELIMO-FSAF230SUS & BELIMO-24SUS DTD:22.01.2018 (FROM SHAKAR MAIL ON 10.01.2018)
							//FOR CLASS 350 ONLY
							//FOR ALL BMSD & BMFSD
							if (SN.equalsIgnoreCase("B")==true)
							{
								String ulClass1 = getULCLASS();
								if (ulClass1==null)
									ulClass1 = "N";
								
							    if (ulClass1.equalsIgnoreCase("Class I-350"))
							    	;
								else
								{
									log.saveError("Error", Msg.getMsg(getCtx(), "Class should be I-350"));
									return false;
								}
										
							 }
							//END OF CODE ON 22.01.2018
							
							else if (SN.equalsIgnoreCase("H")==false)
							{
								log.saveError("Error", Msg.getMsg(getCtx(), "Actuator should be of model - Honeywell with specified volt"));
								return false;
							}
					}
					//Validation for Actuator model and Get Qty from Beta_Actuator_Valid table
					else if (PrdVal.equalsIgnoreCase("BMFD-T") | PrdVal.equalsIgnoreCase("BEMFD-T") |
							PrdVal.equalsIgnoreCase("BMFD/R-T") | PrdVal.equalsIgnoreCase("BEMFD/R-T") |
							PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-F") |
							PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F")  |
							PrdVal.equalsIgnoreCase("BMFD-TF") | PrdVal.equalsIgnoreCase("BEMFD-TF") |
							PrdVal.equalsIgnoreCase("BMFD/R-TF") | PrdVal.equalsIgnoreCase("BEMFD/R-TF"))
					{
						if (Bvalue.doubleValue()>0 &  Avalue.doubleValue()>0)
						{
							double aInch=0,bInch=0;
							BigDecimal actQty=Env.ZERO;
							String sqlactQty=null;
							if (Unit.equalsIgnoreCase("M"))
			 				{
				 				aInch = Math.round(Avalue.doubleValue()/25.4);
				 				bInch = Math.round(Bvalue.doubleValue()/25.4);
			 				}
							else if (Unit.equalsIgnoreCase("I"))
							{
								aInch = Avalue.doubleValue();
								bInch = Bvalue.doubleValue();
							}
							Integer actIDLT1=getBeta_Qtn_Actuator_ID();
							String sqlLTYN1="Select nvl(LTSERIESYN,'N') from beta_qtn_actuator where beta_qtn_actuator_id=?" ;
							String ltSeriesYN1 = DB.getSQLValueString(get_TrxName(), sqlLTYN1,actIDLT1);
							if (ltSeriesYN1.equalsIgnoreCase("Y"))  //HONEYWELL - MS4604F1210  & HONEYWELL - MS8104F1210
							{  
								if (aInch<=20 & bInch<=20) 
									//As discussed with Mr. Shankar, 20'' checking only for LT actutors
								{
									sqlactQty="select nvl((Select qty from beta_actuator_valid where grp ='E1' and beta_qtn_actuator_id= ?),0) from dual";
									actQty = DB.getSQLValueBD(get_TrxName(), sqlactQty,actID);
									if (actQty.doubleValue()<= 0)
									{
										log.saveError("Error", Msg.getMsg(getCtx(), "Wrong Actuator. The selected actuator is not suitable for this dimension"));
										return false;
									}
								}
								else if (aInch>36 & aInch<=40 & bInch>36 & bInch<=40)
								{

									sqlactQty="select nvl((Select qty from beta_actuator_valid where grp ='E2' and beta_qtn_actuator_id= ?),0) from dual";
									actQty = DB.getSQLValueBD(get_TrxName(), sqlactQty,actID);
									if (actQty.doubleValue()<= 0)
									{
										log.saveError("Error", Msg.getMsg(getCtx(), "Wrong Actuator. The selected actuator is not suitable for this dimension"));
										return false;
									}
								}  
								else if ((aInch<=20 | bInch<=20) & ((aInch>36 & aInch<=40) | (bInch>36 & bInch<=40)))
								{
									sqlactQty="select nvl((Select qty from beta_actuator_valid where grp ='E3' and beta_qtn_actuator_id= ?),0) from dual";
									actQty = DB.getSQLValueBD(get_TrxName(), sqlactQty,actID);
									if (actQty.doubleValue()<= 0)
									{
										log.saveError("Error", Msg.getMsg(getCtx(), "Wrong Actuator. The selected actuator is not suitable for this dimension"));
										return false;
									}
								}
								else
								{
									log.saveError("Error", Msg.getMsg(getCtx(), "A & B value should be between (1 to 20) or (36 to 40) (INCH) for LT series"));
									return false;
								}
							}
							else if (aInch<=36 & bInch<=36)
							{
								sqlactQty="select nvl((Select qty from beta_actuator_valid where grp in ('A1','A2') and beta_qtn_actuator_id= ?),0) from dual";
								actQty = DB.getSQLValueBD(get_TrxName(), sqlactQty,actID);
								if (actQty.doubleValue()<= 0)
								{
									log.saveError("Error", Msg.getMsg(getCtx(), "Wrong Actuator. The selected actuator is not suitable for this dimension"));
									return false;
								}
							}
							else if (aInch<=72 & bInch<=36)
							{
								sqlactQty="select nvl((Select qty from beta_actuator_valid where grp in ('B1','B2','B3') and beta_qtn_actuator_id= ?),0) from dual";
								actQty = DB.getSQLValueBD(get_TrxName(), sqlactQty,actID);
								if (actQty.doubleValue()<=0 )
								{
									log.saveError("Error", Msg.getMsg(getCtx(), "Wrong Actuator. The selected actuator is not suitable for this dimension"));
									return false;
								}
							}
							else if (aInch<=36 & bInch<=72)
							{
								sqlactQty="select nvl((Select qty from beta_actuator_valid where grp in ('C1','C2') and beta_qtn_actuator_id= ?),0) from dual";
								actQty = DB.getSQLValueBD(get_TrxName(), sqlactQty,actID);
								if (actQty.doubleValue()<=0 )
								{
									log.saveError("Error", Msg.getMsg(getCtx(), "Wrong Actuator. The selected actuator is not suitable for this dimension"));
									return false;
								}
							}
							else if (aInch<=72 & bInch<=72)
							{
								sqlactQty="select nvl((Select qty from beta_actuator_valid where grp in ('D1','D2','D3') and beta_qtn_actuator_id= ?),0) from dual";
								actQty = DB.getSQLValueBD(get_TrxName(), sqlactQty,actID);
								if (actQty.doubleValue()<=0 )
								{
									log.saveError("Error", Msg.getMsg(getCtx(), "Wrong Actuator. The selected actuator is not suitable for this dimension"));
									return false;
								}
							}
							setACTQTY(actQty);
						}
						else
						{
							String sqlactType1="Select nvl(useforsd,'N') from beta_qtn_actuator where beta_qtn_actuator_id=?";
							String SN1 = DB.getSQLValueString(get_TrxName(), sqlactType1,actID);
							if (SN1==null)
								SN1 = "N";
							if (SN1.equalsIgnoreCase("Y")==false)
							{
								log.saveError("Error", Msg.getMsg(getCtx(), "Actuator should be specified model and volt"));
								return false;
							}
							setACTQTY(Env.ONE);
						}
					} //End of Validation for Actuator model and Get Qty from Beta_Actuator_Valid table
					
					//Validate Class I-350 with actuators
					String ulClass = getULCLASS();
					boolean setULClass =isSETULCLASS();
					String ltYN=null,class350YN=null;
					
					if (ulClass==null)
						ulClass = "N";
					
				    if (ulClass.equalsIgnoreCase("Class I-350") & setULClass==true)
				    {
				    	String sqlUL = "Select nvl(LTSERIESYN,'N'),nvl(CLASS350YN,'N') FROM beta_qtn_actuator " +
						" WHERE beta_qtn_actuator_id=?";
				
						PreparedStatement pstmtUL = null;
			 	    	ResultSet rsUL = null;
			 	    	try 
			 	    	{
			 	    		pstmtUL = DB.prepareStatement(sqlUL, "DSPL");
			 	    		pstmtUL.setInt(1, actID);
			 	    		rsUL = pstmtUL.executeQuery();
						      
						    if(rsUL.next()) 
						    {
						    	ltYN = rsUL.getString(1);	
						    	class350YN = rsUL.getString(2);
						    }
						    rsUL.close();
						    pstmtUL.close();
			 	    	 }catch(Exception e){log.log(Level.SEVERE, sqlUL, e.getMessage());return false; } 
			 	    	 finally {DB.close(rsUL, pstmtUL); }
			 	    	 
			 	    	 if (ltYN.equalsIgnoreCase("N"))
			 	    	 {
			 	    		 if (class350YN.equalsIgnoreCase("N"))
			 	    		 {
			 	    			log.saveError("Error", Msg.getMsg(getCtx(), "For Class I-350, the actuator should be of model MSXX20 (Ex: MS4620,MS4120 etc..)"));
			 	    			return false;
			 	    		 }
			 	    	 }
			 	    	 else if (ltYN.equalsIgnoreCase("Y"))  //Validate Leakage Class for LT series (It should be Class I-250/Class II-250)
			 	    	 {
			 	    		log.saveError("Error", Msg.getMsg(getCtx(), "For LT Series, the leakage class should be either Class I-250 or Class II-250"));
		 	    			return false;
			 	    	 }
				    }  //End of Validate Class I-350 with actuators
					
			    } //End of actID.doubleValue()>0
			} //End of Check Actuator selected or not
			
		/*	//Check for Belimo model should be selected for UL_Tem=350 for BMSD-NEW & BMFSD-NEW/BEMFSD-NEW
			if (PrdVal.equalsIgnoreCase("BMSD-NEW"))
			{
				String ul_Temp = getUL_TEMP();
				String ul_Act=getUL_ACT();
				String ul_cltype=getUL_CLASSTYPE();
				if (ul_Temp==null)
					ul_Temp = "N";
				if (ul_Act==null)
					ul_Act = "N";
				if (ul_Act.equalsIgnoreCase("B"))
				{
					if (ul_Temp.equalsIgnoreCase("350T"))
						;
					else
					{
						log.saveError("Error", Msg.getMsg(getCtx(), "For Belimo models, the temperature rate should be 350"));
		 	    		return false;
					}
				}
				if (ul_cltype.equalsIgnoreCase("CLASS III"))
				{
					log.saveError("Error", Msg.getMsg(getCtx(), "For BMSD models, CLASS III is not allowed"));
	 	    		return false;
				}
			}	
			//End of Check for Belimo model should be selected for UL_Tem=350 for BMSD-NEW & BMFSD-NEW/BEMFSD-NEW
			*/
			//Check for new Belimo Excel sheet
			if (PrdVal.equalsIgnoreCase("BMFSD-NEW") | PrdVal.equalsIgnoreCase("BEMFSD-NEW") |
				PrdVal.equalsIgnoreCase("BMSD-NEW"))
			{
				String ul_Act=getUL_ACT();
				String ul_cltype=getUL_CLASSTYPE();
				String ul_Temp = getUL_TEMP();
				String ul_intext=getUL_INTEXTACT();
								
				if (ul_Temp==null)
					ul_Temp = "N";
				if (ul_Act==null)
					ul_Act = "N";
				if (ul_intext==null)
					ul_intext="N";
				if (ul_cltype==null)
					ul_cltype="N";
				
				if (ul_Act.equalsIgnoreCase("B"))
				{
					if (ul_cltype.equalsIgnoreCase("CLASS I"))
					{
						if (ul_Temp.equalsIgnoreCase("350T") && ul_intext.equalsIgnoreCase("I"))
						{
							log.saveError("Error", Msg.getMsg(getCtx(), "For Belimo Class I-350, Only External mounting is possible"));
							return false;
						}
					}
					else if (ul_cltype.equalsIgnoreCase("CLASS II"))
					{
						if (ul_Temp.equalsIgnoreCase("250T"))
						{
							log.saveError("Error", Msg.getMsg(getCtx(), "For Belimo Class II, Temp.rating 350 is only possible"));
							return false;
						}
						if (ul_intext.equalsIgnoreCase("E"))
						{
							log.saveError("Error", Msg.getMsg(getCtx(), "For Belimo Class II, Only Internal mounting is possible"));
							return false;
						}
					}
					else if (ul_cltype.equalsIgnoreCase("CLASS III"))
					{
						if (ul_Temp.equalsIgnoreCase("350T"))
						{
							log.saveError("Error", Msg.getMsg(getCtx(), "For Belimo Class III, Temp.rating 250 is only possible"));
							return false;
						}
					}
					
					
				}
				else if (ul_Act.equalsIgnoreCase("H"))
				{
					if (ul_cltype.equalsIgnoreCase("CLASS III"))
					{
						log.saveError("Error", Msg.getMsg(getCtx(), "For Honeywell actuators, CLASS III is not allowed"));
		 	    		return false;
					}
				}
			}

			
	 	}//End of BOMYN	
		
	 	//Check for Blade Thickness for Non-Std 3VCD  (24.02.2021)
		if (PrdVal.equalsIgnoreCase("3VCDB") | PrdVal.equalsIgnoreCase("3VCDF") | PrdVal.equalsIgnoreCase("3VCDS"))
		{
			String vcd_blade = getVCD_BLADE();
			Boolean vcd_nonstd=isVCD_NONSTD_YN();
			if (vcd_nonstd==true)
			{
				if (vcd_blade==null | vcd_blade=="")
				{
					log.saveError("Error", Msg.getMsg(getCtx(), "Blade Thickness should not be blank"));
					return false;
				}
			}
		}
	 	//End of Check for Blade Thickness for Non-Std 3VCD  (24.02.2021)

		//Check for Internal/External Act- for Belimo models of BMFSD & BMFD
		if (PrdVal.equalsIgnoreCase("BMFSD-NEW") | PrdVal.equalsIgnoreCase("BEMFSD-NEW") |
			PrdVal.equalsIgnoreCase("BMFD-NEW") | PrdVal.equalsIgnoreCase("BEMFD-NEW") |
			PrdVal.equalsIgnoreCase("BMSD-NEW"))
		{
			String ul_Act=getUL_ACT();
			String ul_intext=getUL_INTEXTACT();
			
			if (ul_Act==null)
				ul_Act = "N";
			if (ul_intext==null)
				ul_intext="";
			
			if (ul_Act.equalsIgnoreCase("B") && ul_intext.equalsIgnoreCase(""))
			{
				log.saveError("Error", Msg.getMsg(getCtx(), "Actuator mounting option should not be blank"));
				return false;
			}
			
			if (ul_Act.equalsIgnoreCase("B") && ul_intext.equalsIgnoreCase(""))
			{
				log.saveError("Error", Msg.getMsg(getCtx(), "Actuator mounting option should not be blank"));
				return false;
			}
			
			//Validate height should be greater or equal to 300mm if mounting is internal Dtd:06.09.23
			if (ul_Act.equalsIgnoreCase("B") && ul_intext.equalsIgnoreCase("I"))
			{
				double ht=0;
				if (Unit.equalsIgnoreCase("I"))
					ht =  Bvalue.doubleValue()*25.4;
				else if (Unit.equalsIgnoreCase("M"))
					ht = Bvalue.doubleValue();
				if (ht<300)
				{
					log.saveError("Error", Msg.getMsg(getCtx(), "For Internal mounting, height should be greater or equal to 300MM"));
					return false;
				}
			}  
			//End of Validate height should be greater or equal to 300mm
			
		}
		//End of Check for Int/Ext
		
		//Check for Round Neck Size (FDC/FDCA)
		if (PrdVal.equalsIgnoreCase("FDC") | PrdVal.equalsIgnoreCase("FDCA")) 
		{
			Boolean fdc_rn_yn=isFDC_RN_YN();
			if (fdc_rn_yn==true)
			{
				BigDecimal fdc_rn_size = getFDC_RN_SIZE();
				if (fdc_rn_size.doubleValue()<100 | fdc_rn_size.doubleValue()>1800)
				{
					log.saveError("Error", Msg.getMsg(getCtx(), "Round Neck size value should be between 100 MM & 1800 MM"));
					return false;
				}
			}
			
		}
		//End of Check for Round Neck Size (FDC/FDCA)		
				
	 	
	 	BigDecimal Qty = getQty();
	 	if (Qty.doubleValue()<=0)
	 	{
	 		log.saveError("Error", Msg.getMsg(getCtx(), "Qty should be greater than zero"));
			return false;
	 	}
	 	if (Qty.intValue() != Qty.doubleValue())
	 	{
	 		log.saveError("Error", Msg.getMsg(getCtx(), "Qty with decimal digits not allowed."));
			return false;
	 	}
	 	
	 	BigDecimal Disc = getDISC_PCNT();
	 	//Validate the disc% for H/w Items
	 	String sqlIsDoor = "Select nvl(ISDOOR,'N') from c_quotation where c_quotation_id = ? ";
		String IsDoor = DB.getSQLValueString(null, sqlIsDoor, getC_QUOTATION_ID());
		Boolean IsBOM = isBOM();
		if (IsDoor.equalsIgnoreCase("Y") && IsBOM==false)
		{
			String sqlDisc = "Select nvl(maxdisc,0) from beta_hwlist where m_product_id = ?";
			BigDecimal hwDisc = DB.getSQLValueBD(null, sqlDisc, getM_Product_ID());
			if (hwDisc ==null)
				hwDisc = Env.ZERO;
			if (Disc.doubleValue() > hwDisc.doubleValue())
			{
				log.saveError("Error", Msg.getMsg(getCtx(), "Max. discount % allowed for this product is: " + hwDisc));
				return false;
			}
		}
	 	//End of Validate the disc% for H/w Items
	 	
	 	
	 	//Make Ceiling & Rounding for Price
	 	BigDecimal Price = getPrice();
	 	Price = Price.setScale(0, BigDecimal.ROUND_CEILING);
	 	
	 	//BigDecimal pcnt = (new BigDecimal(100).subtract(Disc)).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
	 	BigDecimal pcnt = (new BigDecimal(100).subtract(Disc)).divide(new BigDecimal(100));
	 	BigDecimal NetPrice = Price.multiply(pcnt);
	 	NetPrice = NetPrice.setScale(10, BigDecimal.ROUND_HALF_UP);
	 	BigDecimal TotalLine = NetPrice.multiply(Qty);
	 	TotalLine = TotalLine.setScale(10,BigDecimal.ROUND_HALF_UP);
	 	
	 	setPrice(Price);
	 	setNETPRICE(NetPrice);
	 	setTOTALLINE(TotalLine);
	 	
		//*******************************************************************************************************************
		
		
	/*	System.out.println("calling proc_upd_quotationhdr ");
		String sql = "{call proc_upd_quotationhdr(?) }";
		try {
		    CallableStatement cstmt = DB.prepareCall(sql);
            cstmt.setInt(1,getC_QUOTATION_ID());
            cstmt.executeUpdate();
            cstmt.close();
        	} 
        catch (Exception e) 
        	{System.out.println("Inside exception calling proc_upd_quotationhdr ");
        	  String s = e.getMessage();
              JOptionPane.showMessageDialog(null,s);
              String s1[] = s.split(":");
        	}  */
	 	
		return true;
	}
	
	protected boolean afterSave (boolean newRecord, boolean success)
	{
	  //  System.out.println(" client : " + getAD_Client_ID() +
	    //		           " org : " + getAD_Org_ID() + 
	    	//	           " get_ID() :  " + get_ID() + 
	    		//           " success : " + success);
	    if (!success)
			return success;
		SaveNow();
        return updhdr();
	}	//	afterSave
	
	
	protected boolean afterDelete (boolean success)
	{
		if (!success)
			return success;
		System.out.println("Delete");
		SaveNow();
		return updhdr();
	}	//	afterDelete
	
	
	private boolean updhdr()
	{		
		String sql = " select nvl(sum(totalline),0) from C_QuotationLine " +
		" where C_Quotation_ID = ?";
		BigDecimal totalline = DB.getSQLValueBD(get_TrxName(), sql, getC_QUOTATION_ID());
		//System.out.println(" totalline : " + totalline);
		totalline = totalline.setScale(2,BigDecimal.ROUND_UP);
		
		//Line Grand Total
		String upd_grtot = " select nvl(sum(price*qty),0) from c_quotationline where c_quotation_id = ?";
		BigDecimal line_grtot = DB.getSQLValueBD(get_TrxName(), upd_grtot, getC_QUOTATION_ID());
		//End of Line Grand Total
		
		//Tax Amt
        String upd_taxamt1 = " SELECT nvl(sum(case c_tax.rate when 0 then 0 else round(c_quotationline.totalline*(rate/100),2) end),0) "
           + "FROM c_quotationline INNER JOIN c_tax ON c_tax.c_tax_id=c_quotationline.c_tax_id"
           + " WHERE c_quotationline.c_quotation_id= ?";
        BigDecimal taxamt1 = DB.getSQLValueBD(get_TrxName(), upd_taxamt1, getC_QUOTATION_ID());
        //End of Tax Amt
        Double netAmt = totalline.doubleValue()+taxamt1.doubleValue();
		//BigDecimal netbd=new BigDecimal(netAmt);
		//netbd = netbd.setScale(2, BigDecimal.ROUND_UP);
		String sql1 = " update c_quotation set grandtotal =  " + totalline + ",taxamt=" + taxamt1 +
		" , netamt =  " + netAmt + ",LineGrAmt= " + line_grtot + 
		" where c_quotation_id =  " + getC_QUOTATION_ID();
		
		int no = DB.executeUpdate(sql1, null);

        return true;
	}	
	
	
	
	private boolean SaveNow()
	{
	 return true;
	}
	

}