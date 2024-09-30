/******************************************************************************
 * BETA CODE --------------AUTHOR -> SUBAIR P.K. 
 * Date of Creation : 05 Dec 2010
 * Call Out Class for Quotation
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;


import org.compiere.util.DB;
import org.compiere.util.Env;

public class CallOutQuotation extends CalloutEngine{

	public String DiscPer (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if ( value == null || value.equals(Env.ZERO)) 
			return "";
		BigDecimal GrTot = (BigDecimal)mTab.getValue("GrandTotal");
		Double GAmt = new Double (""+GrTot);

		if (GAmt == 0)
			return "";
		BigDecimal DiscPer = (BigDecimal)mTab.getValue("DISCPER");
		BigDecimal taxAmt = (BigDecimal)mTab.getValue("TaxAmt");
		Double DtaxAmt = new Double (""+taxAmt);
		BigDecimal DAmt =  GrTot.multiply(DiscPer);
		Double DiscAmt = new Double(""+DAmt);
		DiscAmt = DiscAmt/100;
		Double NetAmt = GAmt - DiscAmt+DtaxAmt;
		BigDecimal da= new BigDecimal(DiscAmt);
		BigDecimal na= new BigDecimal(NetAmt);
		na = na.setScale(2,BigDecimal.ROUND_UP);
		mTab.setValue("DISCAMT",da);
		mTab.setValue("NETAMT",na);


		return "";
	}

	public String DiscAmt (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		BigDecimal GrTot = (BigDecimal)mTab.getValue("GrandTotal");
		Double GAmt = new Double (""+GrTot);

		if (GAmt == 0)
			return "";

		BigDecimal DAmt = (BigDecimal)mTab.getValue("DISCAMT");
		Double DiscAmt = new Double(""+DAmt);
		BigDecimal taxAmt = (BigDecimal)mTab.getValue("TaxAmt");
		Double DtaxAmt = new Double (""+taxAmt);
		Double NetAmt = GAmt - DiscAmt+DtaxAmt;
		BigDecimal na= new BigDecimal(NetAmt);
		Double DiscPer = (DiscAmt * 100)/GAmt;
		BigDecimal dp= new BigDecimal(DiscPer);
		mTab.setValue("DISCPER",dp);
		mTab.setValue("NETAMT",na);

		return "";
	}

	public String SetHwPrice (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null)
			return "";
		setCalloutActive(true);

		Integer prdID = (Integer)mTab.getValue("M_Product_ID");
		Integer qtnID = (Integer)mTab.getValue("C_QUOTATION_ID");
		if (prdID!= null)
		{
			String sqlIsDoor = "Select nvl(ISDOOR,'N') from c_quotation where c_quotation_id = ? ";
			String IsDoor = DB.getSQLValueString(null, sqlIsDoor, qtnID);
			String sqlIsBOM = "Select nvl(ISBOM,'N') from m_product where m_product_id = ? ";
			String IsBOM = DB.getSQLValueString(null, sqlIsBOM, prdID);

			if (IsDoor.equalsIgnoreCase("Y") && IsBOM.equalsIgnoreCase("N"))
			{
				String sqlPrice = "Select nvl(price,0) from beta_hwlist where m_product_id = ?";
				BigDecimal hwPrice = DB.getSQLValueBD(null, sqlPrice, prdID);

				if (hwPrice !=null)
				{
					hwPrice=hwPrice.setScale(0, BigDecimal.ROUND_CEILING);
					mTab.setValue("Price", hwPrice);
					mTab.setValue("Qty", Env.ONE);
				}
				else
				{
					BigDecimal Price = (BigDecimal)mTab.getValue("Price");
							if (Price == BigDecimal.ZERO)
						mTab.setValue("Price",Env.ZERO);
				}

			}
			try {
				BigDecimal Price = (BigDecimal)mTab.getValue("Price");
				BigDecimal disc_pcnt = (BigDecimal)mTab.getValue("DISC_PCNT");
				BigDecimal Qty = (BigDecimal)mTab.getValue("Qty");

				//BigDecimal pcnt = (new BigDecimal(100).subtract(disc_pcnt)).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
				BigDecimal pcnt = (new BigDecimal(100).subtract(disc_pcnt)).divide(new BigDecimal(100));
				BigDecimal netprice = Price.multiply(pcnt);
				netprice = netprice.setScale(10,BigDecimal.ROUND_HALF_UP);
				mTab.setValue("NETPRICE", netprice); 

				BigDecimal Totalline = Qty.multiply(netprice);
				Totalline = Totalline.setScale(10,BigDecimal.ROUND_HALF_UP);
				mTab.setValue("TOTALLINE", Totalline);
			}catch (Exception e) {
				return "";
			}  
		}
		setCalloutActive(false);
		return "";
	}

	public String ProdValid (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		String sql = null;
		Integer RowNo =null;
		if ((Integer)mTab.getValue("M_Product_ID")!= null)
		{
			sql= "SELECT NVL(DAMPERYN,'N') as DAMPERYN,MANTYPE,NVL(DEFLECTION,'N') as DEFLECTION,NAME,ISBOM," +
					"NVL(WAYSYN,'N') as WAYSYN,NVL(CORNERYN,'N') as CORNERYN,NVL(CURVEYN,'N') as CURVEYN," +
					"NVL(EQGRID,'N') as EQGRID,NVL(DEFLECTIONYN,'N') as DEFLECTIONYN,NVL(COMBTYPE,'O') as COMBTYPE" +	
					",VALUE as PRDVAL,NOPARAM,nvl(LENGTH_CELL_ADDR,'N') as LENGTH_CELL_ADDR,nvl(AIRWAY_CELL_ADDR,'N') as AIRWAY_CELL_ADDR" +
					",nvl(ISOD,'N') as ISOD,nvl(SUPPLYH_CELL_ADDR,' ') as SUPPLYHCELL," +
					"nvl(ALFILTERYN,'N') as ALFILTER,nvl(rmcost,0) as RMCOST,nvl(pricefactor,0) as PF,nvl(tbaryn,'N') as tbar" +
					",nvl(GNA_CELL_ADDR,'A') as GNA,nvl(LVBYAREA,'N') as LVBYAREA,nvl(ISULCLASS,'N') as ISULCLASS " +
					",ISVAVMODEL,ISSPCL_YN,ISGASKET_YN from M_Product where M_Product_ID= ? " ;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1,(Integer)mTab.getValue("M_Product_ID"));
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
				String Dmp = rs.getString("DAMPERYN");
				String Def = rs.getString("DEFLECTION");
				String Bom = rs.getString("ISBOM");
				String WaysYN = rs.getString("WAYSYN");
				String EQGridYN = rs.getString("EQGRID");
				String DeflectionYN = rs.getString("DEFLECTIONYN");
				String CornerYN = rs.getString("CORNERYN");
				String CurveYN = rs.getString("CURVEYN");
				String CombType = rs.getString("COMBTYPE");
				String PrdVal	= rs.getString("PRDVAL");
				String NoParam = rs.getString("NOPARAM");
				String ln	= rs.getString("LENGTH_CELL_ADDR");
				String aw = rs.getString("AIRWAY_CELL_ADDR");
				String od = rs.getString("ISOD");	
				String supplyhcell = rs.getString("SUPPLYHCELL"); 
				String alf = rs.getString("ALFILTER");
				BigDecimal rmcost = rs.getBigDecimal("RMCOST");
				BigDecimal pf = rs.getBigDecimal("PF");
				String tBarYN = rs.getString("tbar");
				String gNA = rs.getString("GNA");
				String lvByArea = rs.getString("LVBYAREA");
				String ulClass = rs.getString("ISULCLASS");
				String IsVav = rs.getString("ISVAVMODEL");
				String IsSpcl = rs.getString("ISSPCL_YN");
				String IsGasket = rs.getString("ISGASKET_YN");

				//Set Prod.Desc for IsBOM='N' items
				if (Bom.trim().contentEquals("N"))
					mTab.setValue("PRODDESC", rs.getString("NAME"));
				else	
					mTab.setValue("PRODDESC","");

				//Set Manufacture Type & Damper
				if (Bom.trim().contentEquals("Y"))
				{
					if (Dmp.trim().contentEquals("Y"))
					{
						mTab.setValue("DAMPERYN", "Y");
						mTab.setValue("MANTYPE", rs.getString("MANTYPE"));
					}
					else
					{
						mTab.setValue("DAMPERYN", "N");
						mTab.setValue("MANTYPE",null);  
					}    
				}
				//Set Deflection Type
				if (Def.equalsIgnoreCase("D"))
					mTab.setValue("DEFLECTION", "D");
				else if (Def.equalsIgnoreCase("S"))
					mTab.setValue("DEFLECTION", "S");

				if (Def.equalsIgnoreCase("D") | Def.equalsIgnoreCase("S"))
				{
					Integer Qid = (Integer)mTab.getValue("C_QUOTATION_ID");
					String sqlhv = "Select nvl(frontblade,' ') from c_quotation where c_quotation_id = ? ";
					String hv = DB.getSQLValueString(null, sqlhv, Qid);
					if (hv.equalsIgnoreCase("H") | hv.equalsIgnoreCase("V"))
						mTab.setValue("HORVER", hv);
					else
						mTab.setValue("HORVER", "");
				}
				else
					mTab.setValue("HORVER", "");

				//Set Combination Type
				if (CombType.equalsIgnoreCase("S"))
				{
					mTab.setValue("TYPE", "S");
					mTab.setValue("SUPPLY", 0);
				}
				else if (CombType.equalsIgnoreCase("C"))
					mTab.setValue("TYPE", "C");
				else if (CombType.equalsIgnoreCase("O"))
				{
					mTab.setValue("TYPE", "O");
					mTab.setValue("SUPPLY", 0);
				}
				else if (CombType.equalsIgnoreCase("T"))
				{
					mTab.setValue("TYPE", "T");
					mTab.setValue("SUPPLY", 0);
				}
				//Check Ways Required or Not
				if (WaysYN.equalsIgnoreCase("Y"))
				{
					mTab.setValue("SETWAYS", "Y");
					mTab.setValue("WAYS", "4");
				}
				else
				{
					mTab.setValue("SETWAYS", "N");
				}

				//Check EQGrid Required or Not
				if (EQGridYN.equalsIgnoreCase("Y"))
				{
					mTab.setValue("SETEQGRID", "Y");
					mTab.setValue("EQUGRIDYN","Y");
				}
				else
				{
					mTab.setValue("SETEQGRID", "N");
					mTab.setValue("EQUGRIDYN","N");
				}

				//Check Corner Required or Not
				if (CornerYN.equalsIgnoreCase("Y"))
					mTab.setValue("SETCORNER", "Y");
				else
				{
					mTab.setValue("SETCORNER", "N");
					mTab.setValue("CORNERYN","N");
				}

				//Check Curve Required or Not
				if (CurveYN.equalsIgnoreCase("Y"))
					mTab.setValue("SETCURVE", "Y");
				else
				{
					mTab.setValue("SETCURVE", "N");
					mTab.setValue("CURVEYN","N");
				}

				//Check Deflection & Horizon/Vertical Required or Not
				if (DeflectionYN.equalsIgnoreCase("Y"))
					mTab.setValue("SETDEFLECTION", "Y");
				else
				{
					mTab.setValue("SETDEFLECTION", "N");
					mTab.setValue("HORVER",null);
					mTab.setValue("DEFLECTION", null);
				}

				//Added for Sp.color on 05/11/2020
				if (IsSpcl.equalsIgnoreCase("Y"))
					mTab.setValue("SETSPCLYN", "Y");
				else
					mTab.setValue("SETSPCLYN", "N");

				//Make NON-STDVCD=Y for AGVCDF (08/04/2021)
				if (PrdVal.equalsIgnoreCase("AGVCDF"))
					mTab.setValue("VCD_NONSTD_YN", "Y");
				else	
					mTab.setValue("VCD_NONSTD_YN", "N");
				//End of Make NON-STDVCD=Y for AGVCDF (08/04/2021)



				// Modfied by Subair & Abdulla on 23-12-2012 
				//Check For Insect Screen
				if (PrdVal.equalsIgnoreCase("GAL-IS")|PrdVal.equalsIgnoreCase("GAL-CR-IS")|
						PrdVal.equalsIgnoreCase("EAL-IS")|PrdVal.equalsIgnoreCase("STL-IS-SF")|
						PrdVal.equalsIgnoreCase("STL-IS")|PrdVal.equalsIgnoreCase("STLC-IS")|
						PrdVal.equalsIgnoreCase("STL-IS-SF")|PrdVal.equalsIgnoreCase("EAL-IS"))

					mTab.setValue("INSSCRNYN","Y");
				else
					mTab.setValue("INSSCRNYN","N");
				//End Check For Insect Screen

				//Check for No Parameter
				if (NoParam.equalsIgnoreCase("Y"))
					mTab.setValue("ISNOPARAM","Y");
				else
					mTab.setValue("ISNOPARAM","N");

				//Check for Length
				if (ln.length()>1)
					mTab.setValue("SETLENGTH", "Y");
				else	
					mTab.setValue("SETLENGTH", "N");

				//Check for Airway
				if (aw.length()>1)
					mTab.setValue("SETAIRWAY", "Y");
				else	
					mTab.setValue("SETAIRWAY", "N");

				//Check OD Item
				if (od.equalsIgnoreCase("Y"))
				{
					mTab.setValue("ISOD", "Y");
					if (supplyhcell.length()>1) 
						mTab.setValue("ISODAPP", "Y");
					else
						mTab.setValue("ISODAPP", "N");
				}	
				else
				{
					mTab.setValue("ISOD", "N");	
					mTab.setValue("ISODAPP", "N");
				}

				//Check for Alum.Filter
				if (alf.equalsIgnoreCase("Y"))
					mTab.setValue("SETALFILTER", "Y");
				else
					mTab.setValue("SETALFILTER", "N");

				//Check T-Bar Required or Not   Added on 23.02.2014
				if (tBarYN.equalsIgnoreCase("Y"))
					mTab.setValue("SETTBAR", "Y");
				else
					mTab.setValue("SETTBAR", "N");

				//Check GN Parameter
				if (gNA.equalsIgnoreCase("A"))
					mTab.setValue("SETGNPARA", "N");
				else
					mTab.setValue("SETGNPARA", "Y");

				//Check Louvers By Area
				if (lvByArea.equalsIgnoreCase("N"))
					mTab.setValue("SETLVBYAREA", "N");
				else
					mTab.setValue("SETLVBYAREA", "Y");

				//Check UL Leakage Class
				if (ulClass.equalsIgnoreCase("Y"))
					mTab.setValue("SETULCLASS", "Y");
				else
					mTab.setValue("SETULCLASS", "N");

				//Check for Vav Model Y/N
				if (IsVav.equalsIgnoreCase("Y"))
					mTab.setValue("SETVAVYN", "Y");
				else	
					mTab.setValue("SETVAVYN", "N");	

				//Check for Gasket (use for grills & diffusers)
				if (IsGasket.equalsIgnoreCase("Y"))
					mTab.setValue("SETGASKET", "Y");
				else	
					mTab.setValue("SETGASKET", "N");


				//Set Value for SETDRIVEYN  (Added on 28/09/2015)
				if (PrdVal.equalsIgnoreCase("AUVCDF") | PrdVal.equalsIgnoreCase("AUVCDB") |
						PrdVal.equalsIgnoreCase("AUVCDH") | PrdVal.equalsIgnoreCase("AUVCDS") |
						PrdVal.equalsIgnoreCase("AGVCDF") | PrdVal.equalsIgnoreCase("AGVCDB") | PrdVal.equalsIgnoreCase("AGVCDS") |
						PrdVal.equalsIgnoreCase("LVCDF") | PrdVal.equalsIgnoreCase("LVCDH") |
						PrdVal.equalsIgnoreCase("3VCDF") | PrdVal.equalsIgnoreCase("3VCDB") |
						PrdVal.equalsIgnoreCase("3VCDH") | PrdVal.equalsIgnoreCase("3VCDS") | 
						PrdVal.equalsIgnoreCase("VDR") | PrdVal.equalsIgnoreCase("PI3VCD") | PrdVal.equalsIgnoreCase("PIAVCD"))
					mTab.setValue("SETDRIVEYN", "Y");
				else
					mTab.setValue("SETDRIVEYN", "N");
				//End of Set Value for SETDRIVEYN

				//Set Mechanism to Linkage for VDR
				if (PrdVal.equalsIgnoreCase("VDR"))
					mTab.setValue("MECHANISM_VDR", "L");
				//End of Set Mechanism to Linkage for VDR

				//Get Price non standard items
				if (Bom.trim().contentEquals("N"))
				{	
					BigDecimal sprice = new BigDecimal(rmcost.doubleValue()* pf.doubleValue());
					if (sprice.doubleValue() !=0)
					{
						sprice = sprice.setScale(0,BigDecimal.ROUND_UP);
						BigDecimal disc_pcnt = (BigDecimal)mTab.getValue("DISC_PCNT");
						BigDecimal pcnt = (new BigDecimal(100).subtract(disc_pcnt)).divide(new BigDecimal(100));
						BigDecimal netprice = sprice.multiply(pcnt);
						netprice = netprice.setScale(10,BigDecimal.ROUND_HALF_UP);
						mTab.setValue("Price", sprice);
						mTab.setValue("NETPRICE", netprice); 

						if ((BigDecimal)mTab.getValue("Qty")!= null)
						{
							BigDecimal Qty = (BigDecimal)mTab.getValue("Qty");
							BigDecimal Totalline = Qty.multiply(netprice);
							Totalline = Totalline.setScale(10,BigDecimal.ROUND_HALF_UP);
							mTab.setValue("TOTALLINE", Totalline);
						}

					}
					else
					{
						mTab.setValue("price", Env.ZERO);
						mTab.setValue("NETPRICE", Env.ZERO); 
						mTab.setValue("TOTALLINE", Env.ZERO);
					}
				}
				//END of Get Price non standard items


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

	public String SetBforLV (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		try
		{
			Boolean lv = (Boolean) mTab.getValue("LVBYAREAYN");
			if (lv)
				mTab.setValue("B", new BigDecimal(1000));
		}
		catch(Exception e) {}
		return "";
	}

	public String SetBUnit (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if ((Integer)mTab.getValue("M_Product_ID")!= null)
		{
			Integer ProdId = (Integer)mTab.getValue("M_Product_ID");
			String SqlProd = "Select value from m_product where m_product_id = ?" ;
			String PrdVal = DB.getSQLValueString(null, SqlProd, ProdId);
			String unit = (String)mTab.get_ValueAsString("UNIT");
			if (PrdVal.equalsIgnoreCase("RLG16") | PrdVal.equalsIgnoreCase("RLRM16") | PrdVal.equalsIgnoreCase("RLRB16") | 
					PrdVal.equalsIgnoreCase("RLRB16/RLG16") | PrdVal.equalsIgnoreCase("RLRM16/RLG16") | PrdVal.equalsIgnoreCase("SLG16")
					| PrdVal.equalsIgnoreCase("SLRM16") | PrdVal.equalsIgnoreCase("SLRB16") | 
					PrdVal.equalsIgnoreCase("SLRB16/SLG16") | PrdVal.equalsIgnoreCase("SLRM16/SLG16") |
					PrdVal.equalsIgnoreCase("RLG12") | PrdVal.equalsIgnoreCase("RLRM12") | PrdVal.equalsIgnoreCase("RLRB12") | 
					PrdVal.equalsIgnoreCase("RLRB12/RLG12") | PrdVal.equalsIgnoreCase("RLRM12/RLG12") | PrdVal.equalsIgnoreCase("SLG12")
					| PrdVal.equalsIgnoreCase("SLRM12") | PrdVal.equalsIgnoreCase("SLRB12") | 
					PrdVal.equalsIgnoreCase("SLRB12/SLG12") | PrdVal.equalsIgnoreCase("SLRM12/SLG12") |

					PrdVal.equalsIgnoreCase("RLG12-20") | PrdVal.equalsIgnoreCase("RLG16-20") | PrdVal.equalsIgnoreCase("SLG12-12.5") |
					PrdVal.equalsIgnoreCase("SLG12-20") | PrdVal.equalsIgnoreCase("SLG16-12.5") | PrdVal.equalsIgnoreCase("SLG16-20") | 
					PrdVal.equalsIgnoreCase("RLRB12-20") | PrdVal.equalsIgnoreCase("RLRB16-20") | PrdVal.equalsIgnoreCase("RLRM12-20") | 
					PrdVal.equalsIgnoreCase("RLRM16-20") | PrdVal.equalsIgnoreCase("SLRB12-12.5") | PrdVal.equalsIgnoreCase("SLRB12-20") | 
					PrdVal.equalsIgnoreCase("SLRB16-12.5") | PrdVal.equalsIgnoreCase("SLRB16-20") | PrdVal.equalsIgnoreCase("SLRM12-12.5") | 
					PrdVal.equalsIgnoreCase("SLRM12-20") | PrdVal.equalsIgnoreCase("SLRM16-12.5") | PrdVal.equalsIgnoreCase("SLRM16-20")
					| PrdVal.equalsIgnoreCase("RLRB12-20/RLG12-20") | PrdVal.equalsIgnoreCase("RLRB16-20/RLG16-20")
					| PrdVal.equalsIgnoreCase("RLRM12-20/RLG12-20") |PrdVal.equalsIgnoreCase("RLRM16-20/RLG16-20")
					| PrdVal.equalsIgnoreCase("SLRB12-12.5/SLG12-12.5") | PrdVal.equalsIgnoreCase("SLRB12-20/SLG12-20") |
					PrdVal.equalsIgnoreCase("SLRB16-12.5/SLG16-12.5") | PrdVal.equalsIgnoreCase("SLRB16-20/SLG16-20") 
					| PrdVal.equalsIgnoreCase("SLRM12-12.5/SLG12-12.5") | PrdVal.equalsIgnoreCase("SLRM12-20/SLG12-20") |
					PrdVal.equalsIgnoreCase("SLRM16-12.5/SLG16-12.5") | PrdVal.equalsIgnoreCase("SLRM16-20/SLG16-20")
					)
			{
				if (unit.equalsIgnoreCase("N"))
				{
					mTab.setValue("SETBUNIT", "Y");
					return "";
				}
			}
		}
		mTab.setValue("SETBUNIT", "N");
		return "";

	}

	public String PcType (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		String sql = null;
		Integer RowNo =null;

		String pcValue = (String)mTab.getValue("POWDERCOATTYPE");
		sql = "SELECT AD_REF_LIST.NAME PCNAME FROM AD_REF_LIST WHERE AD_REF_LIST.AD_REFERENCE_ID IN (SELECT AD_REFERENCE.AD_REFERENCE_ID FROM "
				+ "AD_REFERENCE WHERE AD_REFERENCE.NAME LIKE 'Beta_Qtn_PCType') AND AD_REF_LIST.VALUE =?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1,pcValue);
			rs = pstmt.executeQuery();
			rs.next();
			RowNo=rs.getRow();
			if (RowNo == 0)
				mTab.setValue("FINISHDESC1","");
			else			
				mTab.setValue("FINISHDESC1",rs.getString("PCNAME"));

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

	//Added By Subair on 09.09.2015
	//For Setting the Actuator by checking Drive type (Set Blank if Drive=Quadrant)
	public String SetActuator (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		String driveVal = (String)mTab.get_ValueAsString("DRIVE");

		if (driveVal.equalsIgnoreCase("Q"))
		{
			mTab.setValue("Beta_Qtn_Actuator_ID", null);
			//mTab.setValue("ACTQTY", new BigDecimal(0));
		}
		return "";
	}
	//End of For Setting the Actuator by checking Drive type (Set Blank if Drive=Quadrant)

	//Added by Subair on 13/12/2016
	//For Setting B value by multiplying A * Length Dia (for the items: SILAX & SILAX-P)
	public String SetBforSILAX (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		BigDecimal lnValue=Env.ZERO;

		Integer ProdId = (Integer)mTab.getValue("M_Product_ID");
		if (ProdId == null) return "";
		String SqlProd = "Select value from m_product where m_product_id = ?" ;
		String PrdVal = DB.getSQLValueString(null, SqlProd, ProdId);
		if (PrdVal.equalsIgnoreCase("SILAX") | PrdVal.equalsIgnoreCase("SILAX-P"))
		{
			String lnDia = (String)mTab.getValue("SILAXLENDIA");
			BigDecimal aVal = (BigDecimal)mTab.getValue("A");

			if (lnDia == null) return "";
			if (lnDia.equalsIgnoreCase("1D"))
				lnValue = new BigDecimal(1);
			else if (lnDia.equalsIgnoreCase("1.5D"))
				lnValue = new BigDecimal(1.5);
			else if (lnDia.equalsIgnoreCase("2D"))
				lnValue = new BigDecimal(2);

			if (aVal.doubleValue()!=0 && lnValue.doubleValue()!=0)
			{
				if (aVal.doubleValue()==315 && lnValue.doubleValue()==1.5)
					mTab.setValue("B", new BigDecimal(475));
				else
					mTab.setValue("B", new BigDecimal(aVal.doubleValue()*lnValue.doubleValue()));
			}
		}
		return "";
	}
	//End of For Setting B value by multiplying A * Length Dia (for the items: SILAX & SILAX-P)



	//Added By Subair on 23.02.2015
	//For UL Dampers Items with option /R
	//Get A & B Value from Neck Dia
	public String SetABValue (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		BigDecimal abValue=Env.ZERO;
		if ((Integer)mTab.getValue("M_Product_ID")!= null)
		{
			Integer ProdId = (Integer)mTab.getValue("M_Product_ID");
			String SqlProd = "Select value from m_product where m_product_id = ?" ;
			String PrdVal = DB.getSQLValueString(null, SqlProd, ProdId);
			BigDecimal neckDia = (BigDecimal)mTab.getValue("NECKDIA");
			String unit = (String)mTab.get_ValueAsString("UNIT");
			if (PrdVal.equalsIgnoreCase("BMSD/R") | PrdVal.equalsIgnoreCase("BMFSD/R-T") | 
					PrdVal.equalsIgnoreCase("BEMFSD/R-T") |PrdVal.equalsIgnoreCase("BMFD/R-T") | 
					PrdVal.equalsIgnoreCase("BEMFD/R-T") | PrdVal.equalsIgnoreCase("BFD/R-F") | 
					PrdVal.equalsIgnoreCase("BEFD/R-F") | PrdVal.equalsIgnoreCase("BMFD/R-F") | 
					PrdVal.equalsIgnoreCase("BEMFD/R-F")| PrdVal.equalsIgnoreCase("BMFD/R-TF") | 
					PrdVal.equalsIgnoreCase("BEMFD/R-TF"))
			{
				if (neckDia.doubleValue()==0)
				{
					mTab.fireDataStatusEEvent("Neck Dia", "Neck Diameter should not be zero",true);
					return "";
				}
				else
				{
					if (unit.equalsIgnoreCase("I"))
						abValue = new BigDecimal(neckDia.doubleValue()+4);
					else if (unit.equalsIgnoreCase("M"))
						abValue = new BigDecimal((Math.round(neckDia.doubleValue()*25.4)+102));

					mTab.setValue("A", abValue);
					mTab.setValue("B", abValue);
				}
			}
			else if (PrdVal.equalsIgnoreCase("BMFSD-NEW") | PrdVal.equalsIgnoreCase("BEMFSD-NEW") |
					PrdVal.equalsIgnoreCase("BMSD-NEW") | PrdVal.equalsIgnoreCase("BEMFD-NEW") |
					PrdVal.equalsIgnoreCase("BMFD-NEW"))
			{
				String ul_spigotyn = (String)mTab.get_ValueAsString("UL_SPIGOT");
				if (ul_spigotyn.equalsIgnoreCase("Y"))
				{
					if (neckDia.doubleValue()==0)
					{
						mTab.fireDataStatusEEvent(" Diameter ", "Neck Diameter should not be zero", true);
						abValue=Env.ZERO;
					}
					else
					{
						if (unit.equalsIgnoreCase("I"))
							abValue = new BigDecimal(neckDia.doubleValue()+4);
						else if (unit.equalsIgnoreCase("M"))
							abValue = new BigDecimal((Math.round(neckDia.doubleValue()*25.4)+102));
					}
					mTab.setValue("A", abValue);
					mTab.setValue("B", abValue);
				}
			}
		}
		return "";

	}

	//Add Slot No value into A  **Added on 03Mar2014 
	public String SetSlotNo (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		BigDecimal Avalue = (BigDecimal)mTab.getValue("SLOTNO");
		mTab.setValue("A",Avalue);
		return "";
	}
	//End of //Add Slot No value into A  **Added on 03Mar2014

	//Add Neck Dia value into Length  **Added on 03Apr2014 
	public String SetNeckDia (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		BigDecimal Lvalue = (BigDecimal)mTab.getValue("NECKDIA");
		mTab.setValue("LENGTH",Lvalue);
		return "";
	}
	//End of Add Neck Dia value into Length  **Added on 03Apr2014 

	public String SetDoor (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		String sql = null;
		Integer RowNo =null;

		if ((Integer)mTab.getValue("C_ProjectType_ID")!= null)
		{
			sql = "Select C_ProjectType_ID from C_ProjectType where Name Like 'Doors%' and C_ProjectType_ID =?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1,(Integer)mTab.getValue("C_ProjectType_ID"));
				rs = pstmt.executeQuery();
				rs.next();
				RowNo=rs.getRow();
				if (RowNo==0)
					mTab.setValue("ISDOOR","N");
				else
					mTab.setValue("ISDOOR","Y");

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


	//CallOut -- ML
	public String SetQtyML (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Double MLQty = new Double(""+(BigDecimal)mTab.getValue("ML"));
		Double SetQty = new Double(""+(BigDecimal)mTab.getValue("PQTY"));
		Double NetPrice = new Double(""+(BigDecimal)mTab.getValue("NETPRICE"));
		Double Qty = MLQty * SetQty;
		Double Amt = Qty * NetPrice;

		mTab.setValue("QTY",Qty);
		mTab.setValue("TOTALLINE",Amt);
		return "";
	}

	//CallOut -- PP
	public String SetQtyPP (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Double PPQty = new Double(""+(BigDecimal)mTab.getValue("PP"));
		Double SetQty = new Double(""+(BigDecimal)mTab.getValue("PQTY"));
		Double NetPrice = new Double(""+(BigDecimal)mTab.getValue("NETPRICE"));
		Double Qty = PPQty * SetQty;
		Double Amt = Qty * NetPrice;

		mTab.setValue("QTY",Qty);
		mTab.setValue("TOTALLINE",Amt);
		return "";
	}

	//CallOut -- DL
	public String SetQtyDL (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Double DLQty = new Double(""+(BigDecimal)mTab.getValue("DL"));
		Double SetQty = new Double(""+(BigDecimal)mTab.getValue("PQTY"));
		Double NetPrice = new Double(""+(BigDecimal)mTab.getValue("NETPRICE"));
		Double Qty = DLQty * SetQty;
		Double Amt = Qty * NetPrice;

		mTab.setValue("QTY",Qty);
		mTab.setValue("TOTALLINE",Amt);
		return "";
	}

	//CallOut -- I
	public String SetQtyI (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Double IQty = new Double(""+(BigDecimal)mTab.getValue("I"));
		Double SetQty = new Double(""+(BigDecimal)mTab.getValue("PQTY"));
		Double NetPrice = new Double(""+(BigDecimal)mTab.getValue("NETPRICE"));
		Double Qty = IQty * SetQty;
		Double Amt = Qty * NetPrice;

		mTab.setValue("QTY",Qty);
		mTab.setValue("TOTALLINE",Amt);
		return "";
	}

	//CallOut -- PD
	public String SetQtyPD (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Double PDQty = new Double(""+(BigDecimal)mTab.getValue("PD"));
		Double SetQty = new Double(""+(BigDecimal)mTab.getValue("PQTY"));
		Double NetPrice = new Double(""+(BigDecimal)mTab.getValue("NETPRICE"));
		Double Qty = PDQty * SetQty;
		Double Amt = Qty * NetPrice;

		mTab.setValue("QTY",Qty);
		mTab.setValue("TOTALLINE",Amt);
		return "";
	}

	//CallOut -- PV
	public String SetQtyPV (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Double PVQty = new Double(""+(BigDecimal)mTab.getValue("PV"));
		Double SetQty = new Double(""+(BigDecimal)mTab.getValue("PQTY"));
		Double NetPrice = new Double(""+(BigDecimal)mTab.getValue("NETPRICE"));
		Double Qty = PVQty * SetQty;
		Double Amt = Qty * NetPrice;

		mTab.setValue("QTY",Qty);
		mTab.setValue("TOTALLINE",Amt);
		return "";
	}

	//CallOut -- H
	public String SetQtyH (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Double HQty = new Double(""+(BigDecimal)mTab.getValue("H"));
		Double SetQty = new Double(""+(BigDecimal)mTab.getValue("PQTY"));
		Double NetPrice = new Double(""+(BigDecimal)mTab.getValue("NETPRICE"));
		Double Qty = HQty * SetQty;
		Double Amt = Qty * NetPrice;

		mTab.setValue("QTY",Qty);
		mTab.setValue("TOTALLINE",Amt);
		return "";
	}

	//CallOut -- FB
	public String SetQtyFB (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Double FBQty = new Double(""+(BigDecimal)mTab.getValue("FB"));
		Double SetQty = new Double(""+(BigDecimal)mTab.getValue("PQTY"));
		Double NetPrice = new Double(""+(BigDecimal)mTab.getValue("NETPRICE"));
		Double Qty = FBQty * SetQty;
		Double Amt = Qty * NetPrice;

		mTab.setValue("QTY",Qty);
		mTab.setValue("TOTALLINE",Amt);
		return "";
	}

	//CallOut -- CL
	public String SetQtyCL (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Double CLQty = new Double(""+(BigDecimal)mTab.getValue("CL"));
		Double SetQty = new Double(""+(BigDecimal)mTab.getValue("PQTY"));
		Double NetPrice = new Double(""+(BigDecimal)mTab.getValue("NETPRICE"));
		Double Qty = CLQty * SetQty;
		Double Amt = Qty * NetPrice;

		mTab.setValue("QTY",Qty);
		mTab.setValue("TOTALLINE",Amt);
		return "";
	}

	//CallOut -- ST
	public String SetQtyST (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Double STQty = new Double(""+(BigDecimal)mTab.getValue("ST"));
		Double SetQty = new Double(""+(BigDecimal)mTab.getValue("PQTY"));
		Double NetPrice = new Double(""+(BigDecimal)mTab.getValue("NETPRICE"));
		Double Qty = STQty * SetQty;
		Double Amt = Qty * NetPrice;

		mTab.setValue("QTY",Qty);
		mTab.setValue("TOTALLINE",Amt);
		return "";
	}

	//CallOut -- SL
	public String SetQtySL (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Double SLQty = new Double(""+(BigDecimal)mTab.getValue("SL"));
		Double SetQty = new Double(""+(BigDecimal)mTab.getValue("PQTY"));
		Double NetPrice = new Double(""+(BigDecimal)mTab.getValue("NETPRICE"));
		Double Qty = SLQty * SetQty;
		Double Amt = Qty * NetPrice;

		mTab.setValue("QTY",Qty);
		mTab.setValue("TOTALLINE",Amt);
		return "";
	}
	//CallOut -- AB  Added by Subair On 20.02.2013
	public String SetQtyAB (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Double ABQty = new Double(""+(BigDecimal)mTab.getValue("AB"));
		Double SetQty = new Double(""+(BigDecimal)mTab.getValue("PQTY"));
		Double NetPrice = new Double(""+(BigDecimal)mTab.getValue("NETPRICE"));
		Double Qty = ABQty * SetQty;
		Double Amt = Qty * NetPrice;

		mTab.setValue("QTY",Qty);
		mTab.setValue("TOTALLINE",Amt);
		return "";
	}

}
