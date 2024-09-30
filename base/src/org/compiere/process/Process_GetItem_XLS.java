package org.compiere.process;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Iterator;
import java.util.logging.Level;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;

import org.compiere.util.DB;
import org.compiere.util.Env;

public class Process_GetItem_XLS extends SvrProcess{

	public int record_id,AD_Client_ID,AD_Org_ID,quotLineID;
	POIFSFileSystem fs ;
    HSSFWorkbook wb;
    HSSFSheet sheet ;
    Iterator rows; 
    HSSFRow row;
    FormulaEvaluator evaluator ;
    
    private String vavType_Value="",output_Desc="";
    private BigDecimal output_Size=Env.ZERO;
    int qline_ID=0;
    
	protected void prepare() 
	{
		   record_id = getRecord_ID();
		   AD_Client_ID =Env.getAD_Client_ID(getCtx());
		   AD_Org_ID = Env.getAD_Org_ID(getCtx());
		   
		   String sqlqid ="SELECT c_quotationline_id FROM c_quotationline_itemsel WHERE c_quotationline_itemsel_id = ?";
		   quotLineID = DB.getSQLValue(get_TrxName(), sqlqid, record_id);
			    
	} // prepare
	
	protected String doIt() throws Exception
	{
		String Prod_Value="",isNoParam="N";
		BigDecimal	aValue=Env.ZERO;
		
		
		ExecuteProcess_VAV();
		
		if (output_Size.doubleValue() !=100 & output_Size.doubleValue()!=150 & output_Size.doubleValue()!=200 &
			output_Size.doubleValue()!=250 & output_Size.doubleValue()!=300  & output_Size.doubleValue()!=350 & output_Size.doubleValue()!=400)
		{
			addLog( "Wrong selection for the size. Please try again.");
			return "";
		}
	    try 
	    {
		    String sql_upd = " update c_quotationline_itemsel set ItemSize = " + output_Size.intValue() + 
		    " ,ItemDesc = '" + output_Desc.trim() + "' where c_quotationline_itemsel_id= " + record_id;
		    int no = DB.executeUpdate(sql_upd, get_TrxName());  
		  
		    //Update Quotation Item Tab
		    if (vavType_Value.equalsIgnoreCase("SDV") | vavType_Value.equalsIgnoreCase("SDVE"))
		    {
		    	Prod_Value = vavType_Value.trim()+ output_Size.intValue();
		    	aValue = Env.ZERO;
		    	isNoParam="Y";
		    }
		    else if (vavType_Value.equalsIgnoreCase("SDVBP") | vavType_Value.equalsIgnoreCase("SDVBPE"))
		    {
		    	Prod_Value = vavType_Value.trim();
		    	aValue = output_Size;
		    }
		    
		    String sql_prod = " Select m_product_id from m_product where value = " +
			" ? and ad_client_id = " + AD_Client_ID; 
		  	Integer m_product_id = DB.getSQLValue(get_TrxName(), sql_prod, Prod_Value);
		  	if (m_product_id == null || m_product_id <= 0 )
		  	{
		  		addLog( "Wrong selection for the Item. Please try again.");
				return "";
			}
		  	String sql_updQline = " update c_quotationline set m_product_id=" + m_product_id + " ,A=" + aValue + " ,B=0," +
		  	"IsNoParam='" + isNoParam + "',proddesc='',price=0,netprice=0,totalline=0,getprice='N',type='O',IsBom='Y' where c_quotationline_id=" + qline_ID;
		  	int noqline = DB.executeUpdate(sql_updQline, get_TrxName());  
		  	
				  	
	    }catch(Exception e){} 
		
		return "";	
	}
	
	void ExecuteProcess_VAV()
	{
	 	 String vavType_Cell="",unit_Cell="",min_Cell="",max_Cell="",minPer_Cell="",excel_File="",size_Cell="",desc_Cell="";
	 	 int vavType_r=0,unit_r=0,min_r=0,max_r=0,minPer_r=0,vavType_c=0,unit_c=0,min_c=0,max_c=0,minPer_c=0,
	 	 	 size_r=0,size_c=0,desc_r=0,desc_c=0;
	 	 double min_Value=0 ,max_Value=0 , minPer_Value=0;
	 	 String unit_Value="";
			
		 excel_File = "/opt/Adempiere/Grills/SelectionItem/Vav_Sel.xls";
	 	 //excel_File = "//beta03-fs001/Shared Folder/Subair/SelectionItem/Vav_sel.xls"; 
		 vavType_Cell="B5";unit_Cell="C3";min_Cell="C5";max_Cell="D5";minPer_Cell="E5";size_Cell="Q5";desc_Cell="S5";
		 
		 //Get Input Values from Table
		 String sql_InputVal = "select vavtype,unit,vmin,vmax,vminper,c_quotationline_id " +
		 		 			   "from c_quotationline_itemsel where c_quotationline_itemsel_id=?";
		 PreparedStatement pstmt_Input = null;
		 ResultSet rs_Input = null;
		 try 
		 {
			  pstmt_Input = DB.prepareStatement(sql_InputVal, "DSPL");
			  pstmt_Input.setInt(1, record_id);
			  rs_Input = pstmt_Input.executeQuery();
			
			  if(rs_Input.next()) 
			  {	
				  vavType_Value = rs_Input.getString("vavtype");
				  unit_Value = rs_Input.getString("unit");
				  min_Value = rs_Input.getDouble("vmin");
				  max_Value = rs_Input.getDouble("vmax");
				  minPer_Value = rs_Input.getDouble("vminper");
				  qline_ID = rs_Input.getInt("c_quotationline_id");
			  }
			  rs_Input.close();
			  pstmt_Input.close();
		 }catch(Exception e){} 
		 finally {try {DB.close(rs_Input, pstmt_Input);} catch (Exception e) {}}
		 //End of Get Input Values from Table
		
		 // TO Get Cell address for vavType 
    	 String sql_row_col1 = "select fn_getcelladdres(?) from dual ";
		 PreparedStatement pstmt1 = null;
		 ResultSet rs1 = null;
		 try // fn to get cell address
	   	 {
			 pstmt1 = DB.prepareStatement(sql_row_col1, null);
	   	     pstmt1.setString(1,vavType_Cell);
	   	  	 rs1 = pstmt1.executeQuery();
	   		
	   	     if (rs1.next())
	   	     {
	   	    	 String celladdr = rs1.getString(1);
	   	    	 int length = celladdr.length();
	  		 
	   	    	 String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	   	    	 vavType_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
		    		    	    			
	   	    	 String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	   	    	 vavType_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	   	     } //end of if
	   	     rs1.close();
	   	     pstmt1.close();
		  }
		  catch(Exception e){  log.log(Level.SEVERE, sql_row_col1, e);  } // end of fn_getcelladdr
		  finally { DB.close(rs1, pstmt1);   }
		  // End of TO Get Cell address for vavType 
		  
		 // TO Get Cell address for Unit
    	 String sql_row_col2 = "select fn_getcelladdres(?) from dual ";
		 PreparedStatement pstmt2 = null;
		 ResultSet rs2 = null;
		 try // fn to get cell address
	   	 {
			 pstmt2 = DB.prepareStatement(sql_row_col2, null);
	   	     pstmt2.setString(1,unit_Cell);
	   	  	 rs2 = pstmt2.executeQuery();
	   		
	   	     if (rs2.next())
	   	     {
	   	    	 String celladdr = rs2.getString(1);
	   	    	 int length = celladdr.length();
	  		 
	   	    	 String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	   	    	 unit_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
		    		    	    			
	   	    	 String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	   	    	 unit_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	   	     } //end of if
	   	     rs2.close();
	   	     pstmt2.close();
		  }
		  catch(Exception e){  log.log(Level.SEVERE, sql_row_col2, e);  } // end of fn_getcelladdr
		  finally { DB.close(rs2, pstmt2);   }
		  // End of TO Get Cell address for Unit		
		  
		  // TO Get Cell address for Min
    	 String sql_row_col3 = "select fn_getcelladdres(?) from dual ";
		 PreparedStatement pstmt3 = null;
		 ResultSet rs3 = null;
		 try // fn to get cell address
	   	 {
			 pstmt3 = DB.prepareStatement(sql_row_col3, null);
	   	     pstmt3.setString(1,min_Cell);
	   	  	 rs3 = pstmt3.executeQuery();
	   		
	   	     if (rs3.next())
	   	     {
	   	    	 String celladdr = rs3.getString(1);
	   	    	 int length = celladdr.length();
	  		 
	   	    	 String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	   	    	 min_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
		    		    	    			
	   	    	 String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	   	    	 min_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	   	     } //end of if
	   	     rs3.close();
	   	     pstmt3.close();
		  }
		  catch(Exception e){  log.log(Level.SEVERE, sql_row_col3, e);  } // end of fn_getcelladdr
		  finally { DB.close(rs3, pstmt3);   }
		  // End of TO Get Cell address for Min	
		  
		  // TO Get Cell address for Max
    	 String sql_row_col4 = "select fn_getcelladdres(?) from dual ";
		 PreparedStatement pstmt4 = null;
		 ResultSet rs4 = null;
		 try // fn to get cell address
	   	 {
			 pstmt4 = DB.prepareStatement(sql_row_col4, null);
	   	     pstmt4.setString(1,max_Cell);
	   	  	 rs4 = pstmt4.executeQuery();
	   		
	   	     if (rs4.next())
	   	     {
	   	    	 String celladdr = rs4.getString(1);
	   	    	 int length = celladdr.length();
	  		 
	   	    	 String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	   	    	 max_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
		    		    	    			
	   	    	 String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	   	    	 max_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	   	     } //end of if
	   	     rs4.close();
	   	     pstmt4.close();
		  }
		  catch(Exception e){  log.log(Level.SEVERE, sql_row_col4, e);  } // end of fn_getcelladdr
		  finally { DB.close(rs4, pstmt4);   }
		  // End of TO Get Cell address for Max
		  
		  // TO Get Cell address for MinPer
    	 String sql_row_col5 = "select fn_getcelladdres(?) from dual ";
		 PreparedStatement pstmt5 = null;
		 ResultSet rs5 = null;
		 try // fn to get cell address
	   	 {
			 pstmt5 = DB.prepareStatement(sql_row_col5, null);
	   	     pstmt5.setString(1,minPer_Cell);
	   	  	 rs5 = pstmt5.executeQuery();
	   		
	   	     if (rs5.next())
	   	     {
	   	    	 String celladdr = rs5.getString(1);
	   	    	 int length = celladdr.length();
	  		 
	   	    	 String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	   	    	 minPer_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
		    		    	    			
	   	    	 String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	   	    	 minPer_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	   	     } //end of if
	   	     rs5.close();
	   	     pstmt5.close();
		  }
		  catch(Exception e){  log.log(Level.SEVERE, sql_row_col5, e);  } // end of fn_getcelladdr
		  finally { DB.close(rs5, pstmt5);   }
		  // End of TO Get Cell address for MinPer
		  
		  //Set Values into Excel Sheet
		  try 
		  {
			  InputStream input = new FileInputStream(excel_File);
			  fs = new POIFSFileSystem( input );
			  wb = new HSSFWorkbook(fs);
			  sheet = wb.getSheetAt(0);
			  rows = sheet.rowIterator(); 
			  row = (HSSFRow) rows.next();
			   	    
			  evaluator = wb.getCreationHelper().createFormulaEvaluator();
			   	 
			  //Set Vav Type
		      HSSFRow vavTypeRow = sheet.getRow(vavType_r); 
			  HSSFCell vavCell = vavTypeRow.getCell(vavType_c);
			  vavCell.setCellValue(vavType_Value);
			  
			  //Set Unit
		      HSSFRow unitRow = sheet.getRow(unit_r); 
			  HSSFCell unitCell = unitRow.getCell(unit_c);
			  unitCell.setCellValue(unit_Value);
			  
			  //Set Min
		      HSSFRow minRow = sheet.getRow(min_r); 
			  HSSFCell minCell = minRow.getCell(min_c);
			  minCell.setCellValue(min_Value);
			  
			  //Set Max
		      HSSFRow maxRow = sheet.getRow(max_r); 
			  HSSFCell maxCell = maxRow.getCell(max_c);
			  maxCell.setCellValue(max_Value);
			  
			  //Set MinPer
		      HSSFRow minPerRow = sheet.getRow(minPer_r); 
			  HSSFCell minPerCell = minPerRow.getCell(minPer_c);
			  minPerCell.setCellValue(minPer_Value);
		  }catch(Exception e){}
		  
		  //End of Set Values into Excel Sheet
		  
		  //Get Output from Excel
		  try 
		  {
			  FileOutputStream fileOut = new FileOutputStream(excel_File);
		  	  wb.write(fileOut);
		  	  fileOut.flush();
			    	 
		  	  //Output Size
		  	  String sql_row_col_size = "select fn_getcelladdres(?) from dual ";
		  	  PreparedStatement pstmt_Size = null;
			  ResultSet rs_Size = null;
		   	  try
		   	  {
		   		  pstmt_Size = DB.prepareStatement(sql_row_col_size, null);
		   		  pstmt_Size.setString(1,size_Cell);
		   		  rs_Size = pstmt_Size.executeQuery();
		   	
		   		  if (rs_Size.next())
		   		  {
		   			  String celladdr = rs_Size.getString(1);
		   			  int length = celladdr.length();
		   		    	 
		   			  String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
		   			  size_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
		   		    	 
		   			  String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
		   			  size_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
		   		  }
		   		  rs_Size.close();
		   		  pstmt_Size.close();
		   	  }catch(Exception e){} 
		   	  finally {try {DB.close(rs_Size, pstmt_Size);} catch (Exception e) {}} 
		   	  
			  row = sheet.getRow(size_r);
			  HSSFCell cell_Size = row.getCell(size_c);
		 
			  CellValue cellValue_Size = evaluator.evaluate(cell_Size);
		 	  output_Size =  new BigDecimal(cellValue_Size.getNumberValue());
		 	  //End of Output Size
		 	  
		  	  //Output Description
		  	  String sql_row_col_desc = "select fn_getcelladdres(?) from dual ";
		  	  PreparedStatement pstmt_Desc = null;
			  ResultSet rs_Desc = null;
		   	  try
		   	  {
		   		  pstmt_Desc = DB.prepareStatement(sql_row_col_desc, null);
		   		  pstmt_Desc.setString(1,desc_Cell);
		   		  rs_Desc = pstmt_Desc.executeQuery();
		   	
		   		  if (rs_Desc.next())
		   		  {
		   			  String celladdr = rs_Desc.getString(1);
		   			  int length = celladdr.length();
		   		    	 
		   			  String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
		   			  desc_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
		   		    	 
		   			  String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
		   			  desc_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
		   		  }
		   		  rs_Desc.close();
		   		  pstmt_Desc.close();
		   	  }catch(Exception e){} 
		   	  finally {try {DB.close(rs_Desc, pstmt_Desc);} catch (Exception e) {}} 
		   	  
			  row = sheet.getRow(desc_r);
			  HSSFCell cell_Desc = row.getCell(desc_c);
		 
			  CellValue cellValue_Desc = evaluator.evaluate(cell_Desc);
		 	  output_Desc = cellValue_Desc.getStringValue();
		 	  //End of Output Description
		 	  
		  } catch(Exception e){} 
	 	  //End of Get Output from Excel
		  
		  //Update the Output Value to Table
		  System.out.println("Size : " + output_Size);
		  System.out.println("Desc : " + output_Desc);
	 
	}
	  
}
