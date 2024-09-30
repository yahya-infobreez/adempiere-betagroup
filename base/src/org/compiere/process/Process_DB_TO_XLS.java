package org.compiere.process;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.logging.Level;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class Process_DB_TO_XLS
  extends SvrProcess
{
  private int r = 0;
  private int c = 0;
  private int fr_r = 0;
  private int to_r = 0;
  private int fr_c = 0;
  private int to_c = 0;
  private int M_Product_ID;
  private String Path_of_XLFile;
  private String FGCost_Cell_Addr;
  private String Lock_Status;
  private String Prod_Value;
  private String noparam;
  private String v_description;
  private int M_Attribute_ID;
  private String attr_value;
  private String Attr_Cell_Addr;
  private String Attr_Name;
  private int AD_Client_ID;
  private String bom_cell_addr;
  private int AD_Org_ID;
  private String qty_cell_addr;
  private String costfact_cell_addr;
  private String stdcost_cell_addr;
  private String unitcost_cell_addr;
  private String fr_rmprod_cell_addr;
  private String to_rmprod_cell_addr;
  private String fr_rmQty_cell_addr;
  private String to_rmQty_cell_addr;
  private String fr_rmCF_cell_addr;
  private String to_rmCF_cell_addr;
  private String fr_rmStdCost_cell_addr;
  private String to_rmStdCost_cell_addr;
  private String fr_rmUnitCost_cell_addr;
  private String to_rmUnitCost_cell_addr;
  private String fr_rmCost_cell_addr;
  private String to_rmCost_cell_addr;
  private double m_RawMat_cost;
  private int record_id;
  private int XX_Quotationline_RMCost_ID;
  POIFSFileSystem fs;
  HSSFWorkbook wb;
  HSSFSheet sheet;
  Iterator rows;
  HSSFRow row;
  FormulaEvaluator evaluator;
  private double prod_cost;
  private String v_quotline_desc = "";
  private String a_cell_addr;
  private String b_cell_addr;
  private String supplyh_cell_addr;
  private String prod_desc = "";
  private String unit_symbol;
  BigDecimal price;
  private int quotID; 
  
  protected void prepare()
  {
    this.record_id = getRecord_ID();
    this.AD_Client_ID = Env.getAD_Client_ID(getCtx());
    this.AD_Org_ID = Env.getAD_Org_ID(getCtx());
    
    String sqlqid ="SELECT c_quotation_id FROM c_quotationline WHERE c_quotationline_id = ?";
    quotID = DB.getSQLValue(get_TrxName(), sqlqid, record_id);
  }
  
  protected String doIt()
    throws Exception
  {
    String sql_cnt = " select count(1) from XX_QUOTATIONLINE_RMCOST where c_quotationline_id = ?";
    
    int count = DB.getSQLValue(get_TrxName(), sql_cnt, this.record_id);
    if (count > 0)
    {
      String sql_del = " delete from xx_quotationline_rmcost where c_quotationline_id = " + this.record_id;
      int del_rmcost = DB.executeUpdate(sql_del, get_TrxName());
    
      String upd_gtotal = " select sum(totalline) from c_quotationline where c_quotation_id = ?";

      BigDecimal line_total = DB.getSQLValueBD(get_TrxName(), upd_gtotal, quotID);
      BigDecimal RndGrAmt = line_total.setScale(2,BigDecimal.ROUND_UP);
      
      //Tax Amt
		 String upd_taxamt1 = " SELECT nvl(sum(case rate when 0 then 0 else round(totalline*(rate/100),2) end),0) "
         + "FROM c_quotationline INNER JOIN c_tax ON c_tax.c_tax_id=c_quotationline.c_tax_id"
         + " WHERE c_quotationline.c_quotation_id= ?";
		 BigDecimal taxamt1 = DB.getSQLValueBD(get_TrxName(), upd_taxamt1, quotID);
	  //End of Tax Amt
		 Double netAmt = RndGrAmt.doubleValue()+taxamt1.doubleValue();
		 //BigDecimal netbd=new BigDecimal(netAmt);
		 //netbd = netbd.setScale(2, BigDecimal.ROUND_UP);
      
      String sql_hdr = " update c_quotation set grandtotal =  " + RndGrAmt + ",taxamt=" + taxamt1 +  
        ",netamt = " + netAmt + " where c_quotation_id = " + quotID + "";
      int i = DB.executeUpdate(sql_hdr, get_TrxName());
    }
    ExecuteProcess(this.record_id);
    
    String sql1 = "select qla.m_attribute_id,qla.ATTRIBUTE_VALUE,pa.attribute_cell_addr,ma.name, cql.m_product_id,mp.EXCEL_FILE_NAME," +
    		"mp.FGCOST_CELL_ADDR,mp.LOCK_STATUS,mp.value,mp.originalname from c_quotationline cql " +
    		" inner join m_product mp on cql.m_product_id = mp.m_product_id " +
    		" inner join xx_quotationline_attrib qla on cql.c_quotationline_id = qla.c_quotationline_id  " +
    		"inner join xx_product_attribute pa on pa.m_product_id = cql.m_product_id\t\t\t\t\t\t\t\t and qla.m_attribute_id = pa.m_attribute_id " +
    		"inner join m_attribute ma on qla.m_attribute_id = ma.m_attribute_id  where cql.c_quotationline_id = ? ";
    
    PreparedStatement pstmt1 = null;
    ResultSet rs1 = null;
    try
    {
      pstmt1 = DB.prepareStatement(sql1, "DSPL");
      pstmt1.setInt(1, this.record_id);
      rs1 = pstmt1.executeQuery();
      while (rs1.next())
      {
        this.M_Attribute_ID = rs1.getInt(1);
        this.attr_value = rs1.getString(2);
        this.Attr_Cell_Addr = rs1.getString(3);
        this.Attr_Name = rs1.getString(4);
        this.M_Product_ID = rs1.getInt(5);
        this.Path_of_XLFile = rs1.getString(6);
        this.FGCost_Cell_Addr = rs1.getString(7);
        this.Lock_Status = rs1.getString(8);
        this.Prod_Value = rs1.getString(9);
        this.v_description = rs1.getString(10);
        int lock_status;
        if (this.Lock_Status.equalsIgnoreCase("N"))
        {
          String sql_prod = " update m_product set lock_status = 'Y' where m_product_id = " + this.M_Product_ID + 
            "  and ad_client_id = " + this.AD_Client_ID;
          lock_status = DB.executeUpdate(sql_prod, get_TrxName());
        }
        else
        {
          Thread.sleep(5000L);
          addLog("Excel file of Product ' " + this.Prod_Value + 
            " ' being accessed by another user. Waiting for him to close'");
        }
        this.Path_of_XLFile = this.Path_of_XLFile;
        InputStream input = new FileInputStream(this.Path_of_XLFile);
        this.fs = new POIFSFileSystem(input);
        this.wb = new HSSFWorkbook(this.fs);
        this.sheet = this.wb.getSheetAt(0);
        this.rows = this.sheet.rowIterator();
        this.row = ((HSSFRow)this.rows.next());
        

        this.evaluator = this.wb.getCreationHelper().createFormulaEvaluator();
        

        String sql_row_col1 = "select fn_getcelladdres(?) from dual ";
        PreparedStatement pstmt2 = null;
        ResultSet rs2 = null;
        try
        {
          pstmt2 = DB.prepareStatement(sql_row_col1, null);
          pstmt2.setString(1, this.Attr_Cell_Addr);
          rs2 = pstmt2.executeQuery();
          if (rs2.next())
          {
            String celladdr = rs2.getString(1);
            int length = celladdr.length();
            
            String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
            this.r = DB.getSQLValue(get_TrxName(), s1, new Object[] { celladdr, celladdr });
            
            String s2 = " select to_number(substr(?,instr(?,',')+1," + length + ")) from dual";
            this.c = DB.getSQLValue(get_TrxName(), s2, new Object[] { celladdr, celladdr });
          }
          rs2.close();
          pstmt2.close();
        }
        catch (Exception e)
        {
          String s = e.getLocalizedMessage();
          System.out.println("Error cell addr: " + s);
        }
        finally
        {
          DB.close(rs2, pstmt2);
        }
        HSSFRow row1 = this.sheet.getRow(this.r);
        HSSFCell attr_cost = row1.getCell(this.c);
        


        attr_cost.setCellValue(this.attr_value);
        if (this.v_quotline_desc == "") {
          this.v_quotline_desc = (this.v_quotline_desc + this.attr_value + " ");
        } else {
          this.v_quotline_desc = (this.v_quotline_desc + "* " + this.attr_value + " ");
        }
        FileOutputStream fileOut = new FileOutputStream(this.Path_of_XLFile);
        this.wb.write(fileOut);
        fileOut.flush();
        

        String sql_row_col = "select fn_getcelladdres(?) from dual ";
        Object pstmt3 = null;
        ResultSet rs3 = null;
        try
        {
          pstmt3 = DB.prepareStatement(sql_row_col, null);
          ((PreparedStatement)pstmt3).setString(1, this.FGCost_Cell_Addr);
          rs3 = ((PreparedStatement)pstmt3).executeQuery();
          if (rs3.next())
          {
            String celladdr = rs3.getString(1);
            int length = celladdr.length();
            
            String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
            this.r = DB.getSQLValue(get_TrxName(), s1, new Object[] { celladdr, celladdr });
            
            String s2 = " select to_number(substr(?,instr(?,',')+1," + length + ")) from dual";
            this.c = DB.getSQLValue(get_TrxName(), s2, new Object[] { celladdr, celladdr });
          }
          rs3.close();
          ((PreparedStatement)pstmt3).close();
        }
        catch (Exception e)
        {
          String s = e.getLocalizedMessage();
          System.out.println("Error FG cell addr: " + s);
        }
        finally
        {
          DB.close(rs3, (Statement)pstmt3);
        }
        this.row = this.sheet.getRow(this.r);
        HSSFCell cell = this.row.getCell(this.c);
        if (cell == null) {
          return "";
        }
        CellValue cellValue1 = this.evaluator.evaluate(cell);
        this.prod_cost = cellValue1.getNumberValue();
      }
      String sql_1 = "select MARKUP_PCNT from c_acctschema where ad_client_id = ? ";
      BigDecimal markup_cost = DB.getSQLValueBD(get_TrxName(), sql_1, this.AD_Client_ID);
      


      this.price = new BigDecimal(this.prod_cost).multiply(markup_cost);
      this.price = this.price.setScale(0, 2);
      
      String FinishDesc = "";
      String sqlIsDoor = " select NVL(ISDOOR,'N') from c_quotation cqh,c_quotationline cql where  " +
      		"cql.ad_client_id=cqh.ad_client_id and cql.c_quotation_id=cqh.c_quotation_id and c_quotationline_id = ? ";
      
      String IsDoorYn = DB.getSQLValueStringEx(get_TrxName(), sqlIsDoor, new Object[] { Integer.valueOf(this.record_id) });
      if (IsDoorYn.equalsIgnoreCase("Y"))
      {
        String sqlFinish = "Select nvl(FINISH,' ') from c_quotationline where c_quotationline_id = ? ";
        String StrFinish = DB.getSQLValueStringEx(get_TrxName(), sqlFinish, new Object[] { Integer.valueOf(this.record_id) });
        if (StrFinish.equalsIgnoreCase(" ")) {
          FinishDesc = "";
        } 
        else {
          FinishDesc = " (" + StrFinish + ")";
        }
      }
      String sql_upd = " update c_quotationline set price = " + this.price + 
        " ,PRODDESC = '" + this.v_description + " ' || ' " + this.prod_desc + this.v_quotline_desc + FinishDesc + 
        " ',totalline = " + this.price + " * QTY where m_product_id = " + this.M_Product_ID + 
        " and c_quotationline_id = " + this.record_id;
      
      int no = DB.executeUpdate(sql_upd, get_TrxName());
      

      rs1.close();
      pstmt1.close();
    }
    catch (Exception e)
    {
      String s = e.getLocalizedMessage();
      System.out.println("Error : " + s);
    }
    finally
    {
      DB.close(rs1, pstmt1);
    }
    String sql_3 = "select mpb.FROM_RMPROD_CELL_ADDR,mpb.TO_RMPROD_CELL_ADDR,mpb.QTY_CELL_ADDR,mpb.TO_QTY_CELL_ADDR, mpb.COSTFACTOR_CELL_ADDR," +
    		"mpb.TO_COSTFACTOR_CELL_ADDR,mpb.STDCOST_CELL_ADDR,mpb.TO_STDCOST_CELL_ADDR, mpb.UNITCOST_CELL_ADDR,mpb.TO_UNITCOST_CELL_ADDR," +
    		"mpb.RMCOST_CELL_ADDR,mpb.TO_RMCOST_CELL_ADDR from m_product_bom mpb inner join c_quotationline cql  on mpb.m_product_id = " +
    		"cql.m_product_id where mpb.m_product_id = ? and cql.c_quotationline_id = ? ";
    
    PreparedStatement pstmt5 = null;
    ResultSet rs5 = null;
    try
    {
      pstmt5 = DB.prepareStatement(sql_3, null);
      pstmt5.setInt(1, this.M_Product_ID);
      pstmt5.setInt(2, this.record_id);
      rs5 = pstmt5.executeQuery();
      if (rs5.next())
      {
        this.fr_rmprod_cell_addr = rs5.getString(1);
        this.to_rmprod_cell_addr = rs5.getString(2);
        
        this.fr_rmQty_cell_addr = rs5.getString(3);
        this.fr_rmCF_cell_addr = rs5.getString(5);
        this.fr_rmStdCost_cell_addr = rs5.getString(7);
        this.fr_rmUnitCost_cell_addr = rs5.getString(9);
        this.fr_rmCost_cell_addr = rs5.getString(11);
        


        String from = "select fn_getcelladdres(?) from dual ";
        PreparedStatement pstmt_gen = null;
        ResultSet rs_gen = null;
        try
        {
          pstmt_gen = DB.prepareStatement(from, null);
          pstmt_gen.setString(1, this.fr_rmprod_cell_addr);
          rs_gen = pstmt_gen.executeQuery();
          if (rs_gen.next())
          {
            String celladdr = rs_gen.getString(1);
            int length = celladdr.length();
            String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
            this.fr_r = DB.getSQLValue(get_TrxName(), s1, new Object[] { celladdr, celladdr });
            
            String s2 = " select to_number(substr(?,instr(?,',')+1," + length + ")) from dual";
            this.fr_c = DB.getSQLValue(get_TrxName(), s2, new Object[] { celladdr, celladdr });
          }
          rs_gen.close();
          pstmt_gen.close();
        }
        catch (Exception localException1) {}finally
        {
          DB.close(rs_gen, pstmt_gen);
        }
        String to = "select fn_getcelladdres(?) from dual ";
        try
        {
          pstmt_gen = DB.prepareStatement(to, null);
          pstmt_gen.setString(1, this.to_rmprod_cell_addr);
          rs_gen = pstmt_gen.executeQuery();
          if (rs_gen.next())
          {
            String celladdr = rs_gen.getString(1);
            int length = celladdr.length();
            
            String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
            this.to_r = DB.getSQLValue(get_TrxName(), s1, new Object[] { celladdr, celladdr });
            
            String s2 = " select to_number(substr(?,instr(?,',')+1," + length + ")) from dual";
            this.to_c = DB.getSQLValue(get_TrxName(), s2, new Object[] { celladdr, celladdr });
          }
          rs_gen.close();
          pstmt_gen.close();
        }
        catch (Exception localException2) {}finally
        {
          DB.close(rs_gen, pstmt_gen);
        }
        int X = 1;
        int k = 0;int l = 0;int m = 0;int n = 0;int o = 0;
        int k1 = 0;int l1 = 0;int m1 = 0;int n1 = 0;int o1 = 0;
        for (int i = this.fr_r; i <= this.to_r; i++)
        {
          int j = this.fr_c;
          

          this.row = this.sheet.getRow(i);
          HSSFCell prod_cell = this.row.getCell(j);
          
          CellValue prod_cellValue = this.evaluator.evaluate(prod_cell);
          String bom_prod = prod_cellValue.getStringValue().toUpperCase().trim();
          
          String sql_prod = " Select m_product_id from m_product where value =  ? and ad_client_id = " + 
            this.AD_Client_ID;
          Integer m_product_id = Integer.valueOf(DB.getSQLValue(get_TrxName(), sql_prod, bom_prod));
          


          String sql_prod_Cost = " Select currentcostprice from m_cost where m_product_id =  ? and m_costelement_id = 1000001 and ad_client_id = " + 
            this.AD_Client_ID;
          BigDecimal m_RawMat_cost = DB.getSQLValueBD(get_TrxName(), sql_prod_Cost, m_product_id.intValue());
          if ((m_product_id == null) || (m_product_id.intValue() <= 0))
          {
            String sql_del = " delete from xx_quotationline_rmcost where c_quotationline_id = " + this.record_id;
            int del_rmcost = DB.executeUpdate(sql_del, get_TrxName());
            
            String sql_upd = " update c_quotationline set price = 0   ,totalline = 0 where m_product_id = " + 
              this.M_Product_ID + 
              " and c_quotationline_id = " + this.record_id;
            int no = DB.executeUpdate(sql_upd, get_TrxName());
            
            String sql_gt = "select sum(totalline) from c_quotationline where c_quotation_id =?";
            BigDecimal totline_amt = DB.getSQLValueBD(get_TrxName(), sql_gt, quotID);
            BigDecimal RndAmt = totline_amt.setScale(2,BigDecimal.ROUND_UP);
            
            //Tax Amt
   		    String upd_taxamt2 = " SELECT nvl(sum(case rate when 0 then 0 else round(totalline*(rate/100),2) end),0) "
               + "FROM c_quotationline INNER JOIN c_tax ON c_tax.c_tax_id=c_quotationline.c_tax_id"
               + " WHERE c_quotationline.c_quotation_id= ?";
   		    BigDecimal taxamt2 = DB.getSQLValueBD(get_TrxName(), upd_taxamt2, quotID);
   		    //End of Tax Amt
   		    Double netAmt = RndAmt.doubleValue()+taxamt2.doubleValue();
   		    //BigDecimal netbd=new BigDecimal(netAmt);
   		    //netbd = netbd.setScale(2, BigDecimal.ROUND_UP);
            
            String sql_hdr = " update c_quotation set grandtotal = " + RndAmt + ",taxamt=" + taxamt2 + 
              ",NETAMT = " + netAmt + "  where c_quotation_id =" + quotID + "";
            int grand_total = DB.executeUpdate(sql_hdr, get_TrxName());
            
            String sql_upd_prod = " update m_product set lock_status = 'N' where m_product_id = " + this.M_Product_ID + 
              " and ad_client_id = " + this.AD_Client_ID;
            int lock_status = DB.executeUpdate(sql_upd_prod, get_TrxName());
            
            addLog( " Product MisMatch Between Excel Sheet & Product Master (Excel Item : " + bom_prod + ")");
            return "";
          }
          int qty_r = 0;
          int qty_c = 0;
          
          String from_qty = "select fn_getcelladdres(?) from dual ";
          try
          {
            pstmt_gen = DB.prepareStatement(from_qty, null);
            pstmt_gen.setString(1, this.fr_rmQty_cell_addr);
            rs_gen = pstmt_gen.executeQuery();
            if (rs_gen.next())
            {
              String celladdr = rs_gen.getString(1);
              int length = celladdr.length();
              String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
              qty_r = DB.getSQLValue(get_TrxName(), s1, new Object[] { celladdr, celladdr });
              
              String s2 = " select to_number(substr(?,instr(?,',')+1," + length + ")) from dual";
              qty_c = DB.getSQLValue(get_TrxName(), s2, new Object[] { celladdr, celladdr });
            }
            rs_gen.close();
            pstmt_gen.close();
          }
          catch (Exception localException3) {}finally
          {
            DB.close(rs_gen, pstmt_gen);
          }
          k = qty_c;
          
          int CF_r = 0;
          int CF_c = 0;
          String from_CF = "select fn_getcelladdres(?) from dual ";
          try
          {
            pstmt_gen = DB.prepareStatement(from_CF, null);
            pstmt_gen.setString(1, this.fr_rmCF_cell_addr);
            rs_gen = pstmt_gen.executeQuery();
            if (rs_gen.next())
            {
              String celladdr = rs_gen.getString(1);
              int length = celladdr.length();
              String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
              CF_r = DB.getSQLValue(get_TrxName(), s1, new Object[] { celladdr, celladdr });
              
              String s2 = " select to_number(substr(?,instr(?,',')+1," + length + ")) from dual";
              CF_c = DB.getSQLValue(get_TrxName(), s2, new Object[] { celladdr, celladdr });
            }
            rs_gen.close();
            pstmt_gen.close();
          }
          catch (Exception localException4) {}finally
          {
            DB.close(rs_gen, pstmt_gen);
          }
          l = CF_c;
          
          int SC_r = 0;
          int SC_c = 0;
          String from_SC = "select fn_getcelladdres(?) from dual ";
          try
          {
            pstmt_gen = DB.prepareStatement(from_SC, null);
            pstmt_gen.setString(1, this.fr_rmStdCost_cell_addr);
            rs_gen = pstmt_gen.executeQuery();
            if (rs_gen.next())
            {
              String celladdr = rs_gen.getString(1);
              int length = celladdr.length();
              String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
              SC_r = DB.getSQLValue(get_TrxName(), s1, new Object[] { celladdr, celladdr });
              
              String s2 = " select to_number(substr(?,instr(?,',')+1," + length + ")) from dual";
              SC_c = DB.getSQLValue(get_TrxName(), s2, new Object[] { celladdr, celladdr });
            }
            rs_gen.close();
            pstmt_gen.close();
          }
          catch (Exception localException5) {}finally
          {
            DB.close(rs_gen, pstmt_gen);
          }
          m = SC_c;
          
          int UC_r = 0;
          int UC_c = 0;
          String from_UC = "select fn_getcelladdres(?) from dual ";
          try
          {
            pstmt_gen = DB.prepareStatement(from_UC, null);
            pstmt_gen.setString(1, this.fr_rmUnitCost_cell_addr);
            rs_gen = pstmt_gen.executeQuery();
            if (rs_gen.next())
            {
              String celladdr = rs_gen.getString(1);
              int length = celladdr.length();
              String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
              UC_r = DB.getSQLValue(get_TrxName(), s1, new Object[] { celladdr, celladdr });
              
              String s2 = " select to_number(substr(?,instr(?,',')+1," + length + ")) from dual";
              UC_c = DB.getSQLValue(get_TrxName(), s2, new Object[] { celladdr, celladdr });
            }
            rs_gen.close();
            pstmt_gen.close();
          }
          catch (Exception localException6) {}finally
          {
            DB.close(rs_gen, pstmt_gen);
          }
          n = UC_c;
          
          int RMC_r = 0;
          int RMC_c = 0;
          String from_RMC = "select fn_getcelladdres(?) from dual ";
          try
          {
            pstmt_gen = DB.prepareStatement(from_RMC, null);
            pstmt_gen.setString(1, this.fr_rmCost_cell_addr);
            rs_gen = pstmt_gen.executeQuery();
            if (rs_gen.next())
            {
              String celladdr = rs_gen.getString(1);
              int length = celladdr.length();
              String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
              RMC_r = DB.getSQLValue(get_TrxName(), s1, new Object[] { celladdr, celladdr });
              
              String s2 = " select to_number(substr(?,instr(?,',')+1," + length + ")) from dual";
              RMC_c = DB.getSQLValue(get_TrxName(), s2, new Object[] { celladdr, celladdr });
            }
            rs_gen.close();
            pstmt_gen.close();
          }
          catch (Exception localException7) {}finally
          {
            DB.close(rs_gen, pstmt_gen);
          }
          o = RMC_c;
          

          this.row = this.sheet.getRow(i);
          HSSFCell Qty_cell = this.row.getCell(k);
          
          this.row = this.sheet.getRow(i);
          HSSFCell costfact_cell = this.row.getCell(l);
          
          this.row = this.sheet.getRow(i);
          HSSFCell sc_cell = this.row.getCell(m);
          
          this.row = this.sheet.getRow(i);
          HSSFCell uc_cell = this.row.getCell(n);
          
          this.row = this.sheet.getRow(i);
          HSSFCell cost_cell = this.row.getCell(o);
          if ((cost_cell == null) || (Qty_cell == null) || (costfact_cell == null) || (sc_cell == null) || (uc_cell == null)) {
            return "";
          }
          CellValue cost_cellValue = this.evaluator.evaluate(cost_cell);
          double bom_cost = cost_cellValue.getNumberValue();
          Double bom = new Double(cost_cellValue.getNumberValue());
          
          CellValue Qty_cellValue = this.evaluator.evaluate(Qty_cell);
          double bom_Qty = Qty_cellValue.getNumberValue();
          
          CellValue cf_cellvalue = this.evaluator.evaluate(costfact_cell);
          double bom_cf = cf_cellvalue.getNumberValue();
          
          CellValue sc_cellvalue = this.evaluator.evaluate(sc_cell);
          double bom_sc = sc_cellvalue.getNumberValue();
          
          CellValue uc_cellvalue = this.evaluator.evaluate(uc_cell);
          double bom_uc = uc_cellvalue.getNumberValue();
          

          String sql_2 = "insert into XX_QUOTATIONLINE_RMCOST(AD_CLIENT_ID,AD_ORG_ID,ISACTIVE,CREATED,CREATEDBY,UPDATED,UPDATEDBY," +
          		"C_QUOTATIONLINE_ID, RM_PRODUCT_ID,RM_QUANTITY,RM_AMOUNT,COST_FACTOR, STDCOST,UNITCOST,RAWMAT_AVG_COST) values(" + 
          
            this.AD_Client_ID + "," + 
            this.AD_Org_ID + ",'Y',sysdate,100,sysdate,100," + this.record_id + 
            "," + m_product_id + "," + bom_Qty + "," + bom_cost + "," + bom_cf + 
            "," + bom_sc + "," + bom_uc + "," + m_RawMat_cost + ")";
          int jj = DB.executeUpdate(sql_2, get_TrxName());
        }
      }
      rs5.close();
      pstmt5.close();
    }
    catch (Exception localException9) {}finally
    {
      DB.close(rs5, pstmt5);
    }
    String sql_disc = " select DISC_PCNT from c_quotationline where c_quotationline_id = ? ";
    BigDecimal disc_pcnt = DB.getSQLValueBD(null, sql_disc, this.record_id);
    disc_pcnt = (new BigDecimal(100).subtract(disc_pcnt)).divide(new BigDecimal(100));
    //disc_pcnt = new BigDecimal(100).subtract(disc_pcnt).divide(new BigDecimal(100), 2, 4);  commented and added the above line on 01/10/2019 for decimal issue.
    
    BigDecimal netprice = this.price.multiply(disc_pcnt);
    
    netprice = netprice.setScale(2, 4);
    

    BigDecimal Est_Cost = new BigDecimal(this.prod_cost);
    String sql_netprice = " update c_quotationline set netprice =  " + netprice + 
      " , totalline = " + netprice + " * Qty " + 
      ",EstCost = " + Est_Cost.setScale(2, 4) + " where c_quotationline_id =  " + this.record_id;
    
    int np = DB.executeUpdate(sql_netprice, get_TrxName());
    
    String sql_gt = "select sum(totalline) from c_quotationline where c_quotation_id=?";
    
    BigDecimal totline_amt = DB.getSQLValueBD(get_TrxName(), sql_gt, quotID);
    BigDecimal RndAmt = totline_amt.setScale(2,BigDecimal.ROUND_UP);
    
    //Tax Amt
	 String upd_taxamt3 = " SELECT nvl(sum(case rate when 0 then 0 else round(totalline*(rate/100),2) end),0) "
       + "FROM c_quotationline INNER JOIN c_tax ON c_tax.c_tax_id=c_quotationline.c_tax_id"
       + " WHERE c_quotationline.c_quotation_id= ?";
	 BigDecimal taxamt3 = DB.getSQLValueBD(get_TrxName(), upd_taxamt3, quotID);
	 //End of Tax Amt
	 Double netAmt = RndAmt.doubleValue()+taxamt3.doubleValue();
	 //BigDecimal netbd=new BigDecimal(netAmt);
	 //netbd = netbd.setScale(2, BigDecimal.ROUND_UP);
    
    String sql_hdr = " update c_quotation set grandtotal =  " + RndAmt + ",taxamt=" + taxamt3 +  
      " ,netamt = " + netAmt + " where " + " c_quotation_id = " + quotID + "";
    int grand_total = DB.executeUpdate(sql_hdr, get_TrxName());
    
    String sql_prod = " update m_product set lock_status = 'N' where m_product_id = " + this.M_Product_ID + 
      " and ad_client_id = " + this.AD_Client_ID;
    int lock_status = DB.executeUpdate(sql_prod, get_TrxName());
    
    return "";
  }
  
  void ExecuteProcess(int record_id)
  {
    int r1 = 0;int c1 = 0;int r2 = 0;int c2 = 0;int r3 = 0;int c3 = 0;int r4 = 0;int c4 = 0;int r5 = 0;int c5 = 0;
    int r = 0;int c = 0;
    
    double inch = 25.0D;
    
    String sql1 = "SELECT cql.m_product_id,mp.EXCEL_FILE_NAME, mp.FGCOST_CELL_ADDR,mp.LOCK_STATUS,mp.VALUE, nvl(mp.a_cell_addr,0)," +
    		"nvl(mp.b_cell_addr,0),nvl(mp.supplyh_cell_addr,0) , trim(cql.unit) as unit ,cql.a,cql.b,cql.supply,mp.originalname,nvl(trim(cql.bunit),' ') as bunit," +
    		"nvl(mp.noparam,'N') as noparam ,nvl(cql.SETLENGTH,'N'),nvl(cql.SETAIRWAY,'N'),nvl(trim(mp.length_cell_addr),' ')," +
    		"nvl(trim(mp.airway_cell_addr),' '),nvl(cql.length,0),nvl(cql.airway,' ')  from c_quotationline cql INNER JOIN m_product mp ON " +
    		"cql.m_product_id = mp.m_product_id where cql.c_quotationline_id = ?  and ((mp.a_cell_addr is not null or mp.b_cell_addr is not null " +
    		"or mp.supplyh_cell_addr is not null) or (nvl(mp.noparam,'N') ='Y')) ";
    

    PreparedStatement pstmt1 = null;
    ResultSet rs1 = null;
    try
    {
      pstmt1 = DB.prepareStatement(sql1, "DSPL");
      pstmt1.setInt(1, record_id);
      rs1 = pstmt1.executeQuery();
      if (rs1.next())
      {
        this.M_Product_ID = rs1.getInt(1);
        this.Path_of_XLFile = rs1.getString(2);
        this.FGCost_Cell_Addr = rs1.getString(3);
        this.Lock_Status = rs1.getString(4);
        this.Prod_Value = rs1.getString(5);
        this.a_cell_addr = rs1.getString(6);
        this.b_cell_addr = rs1.getString(7);
        this.supplyh_cell_addr = rs1.getString(8);
        String unit = rs1.getString(9);
        double a = rs1.getDouble(10);
        double b = rs1.getDouble(11);
        double supply = rs1.getDouble(12);
        this.v_description = rs1.getString(13);
        String b_unit = rs1.getString(14);
        this.noparam = rs1.getString(15);
        String lenYN = rs1.getString(16);
        String awYN = rs1.getString(17);
        String len_addr = rs1.getString(18);
        String aw_addr = rs1.getString(19);
        double len = rs1.getDouble(20);
        String aw = rs1.getString(21);
        BigDecimal len_disp = rs1.getBigDecimal(20);
        

        BigDecimal a_disp = rs1.getBigDecimal(10);
        BigDecimal b_disp = rs1.getBigDecimal(11);
        BigDecimal s_disp = rs1.getBigDecimal(12);
        int lock_status;
        if (this.Lock_Status.equalsIgnoreCase("N"))
        {
          String sql_prod = " update m_product set lock_status = 'Y' where m_product_id = " + this.M_Product_ID + 
            " and ad_client_id = " + this.AD_Client_ID;
          lock_status = DB.executeUpdate(sql_prod, get_TrxName());
        }
        else
        {
          Thread.sleep(5000L);
          addLog("Excel file of Product ' " + this.Prod_Value + 
            " ' being accessed by another user. Waiting for him to close'");
        }
        this.Path_of_XLFile = this.Path_of_XLFile;
        
        InputStream input = new FileInputStream(this.Path_of_XLFile);
        this.fs = new POIFSFileSystem(input);
        this.wb = new HSSFWorkbook(this.fs);
        this.sheet = this.wb.getSheetAt(0);
        this.rows = this.sheet.rowIterator();
        this.row = ((HSSFRow)this.rows.next());
        
        this.evaluator = this.wb.getCreationHelper().createFormulaEvaluator();
        
        String sql_row_col1 = "select fn_getcelladdres(?) from dual ";
        PreparedStatement pstmt2 = null;
        ResultSet rs2 = null;
        try
        {
          pstmt2 = DB.prepareStatement(sql_row_col1, null);
          pstmt2.setString(1, this.a_cell_addr);
          rs2 = pstmt2.executeQuery();
          if (rs2.next())
          {
            String celladdr = rs2.getString(1);
            int length = celladdr.length();
            
            String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
            r1 = DB.getSQLValue(get_TrxName(), s1, new Object[] { celladdr, celladdr });
            
            String s2 = " select to_number(substr(?,instr(?,',')+1," + length + ")) from dual";
            c1 = DB.getSQLValue(get_TrxName(), s2, new Object[] { celladdr, celladdr });
          }
          rs2.close();
          pstmt2.close();
        }
        catch (Exception e)
        {
          this.log.log(Level.SEVERE, sql_row_col1, e);
        }
        finally
        {
          DB.close(rs2, pstmt2);
        }
        String sql_row_col2 = "select fn_getcelladdres(?) from dual ";
        pstmt2 = null;
        rs2 = null;
        try
        {
          pstmt2 = DB.prepareStatement(sql_row_col2, null);
          pstmt2.setString(1, this.b_cell_addr);
          rs2 = pstmt2.executeQuery();
          if (rs2.next())
          {
            String celladdr = rs2.getString(1);
            int length = celladdr.length();
            
            String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
            r2 = DB.getSQLValue(get_TrxName(), s1, new Object[] { celladdr, celladdr });
            
            String s2 = " select to_number(substr(?,instr(?,',')+1," + length + ")) from dual";
            c2 = DB.getSQLValue(get_TrxName(), s2, new Object[] { celladdr, celladdr });
          }
          rs2.close();
          pstmt2.close();
        }
        catch (Exception e)
        {
          this.log.log(Level.SEVERE, sql_row_col2, e);
        }
        finally
        {
          DB.close(rs2, pstmt2);
        }
        String sql_row_col3 = "select fn_getcelladdres(?) from dual ";
        pstmt2 = null;
        rs2 = null;
        try
        {
          pstmt2 = DB.prepareStatement(sql_row_col3, null);
          pstmt2.setString(1, this.supplyh_cell_addr);
          rs2 = pstmt2.executeQuery();
          if (rs2.next())
          {
            String celladdr = rs2.getString(1);
            int length = celladdr.length();
            
            String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
            r3 = DB.getSQLValue(get_TrxName(), s1, new Object[] { celladdr, celladdr });
            
            String s2 = " select to_number(substr(?,instr(?,',')+1," + length + ")) from dual";
            c3 = DB.getSQLValue(get_TrxName(), s2, new Object[] { celladdr, celladdr });
          }
          rs2.close();
          pstmt2.close();
        }
        catch (Exception e)
        {
          this.log.log(Level.SEVERE, sql_row_col3, e);
        }
        finally
        {
          DB.close(rs2, pstmt2);
        }
        if (lenYN.equalsIgnoreCase("Y"))
        {
          String sql_row_col4 = "select fn_getcelladdres(?) from dual ";
          pstmt2 = null;
          rs2 = null;
          try
          {
            pstmt2 = DB.prepareStatement(sql_row_col4, null);
            pstmt2.setString(1, len_addr);
            rs2 = pstmt2.executeQuery();
            if (rs2.next())
            {
              String celladdr = rs2.getString(1);
              int length = celladdr.length();
              
              String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
              r4 = DB.getSQLValue(get_TrxName(), s1, new Object[] { celladdr, celladdr });
              
              String s2 = " select to_number(substr(?,instr(?,',')+1," + length + ")) from dual";
              c4 = DB.getSQLValue(get_TrxName(), s2, new Object[] { celladdr, celladdr });
            }
            rs2.close();
            pstmt2.close();
          }
          catch (Exception e)
          {
            this.log.log(Level.SEVERE, sql_row_col4, e);
          }
          finally
          {
            DB.close(rs2, pstmt2);
          }
        }
        if (awYN.equalsIgnoreCase("Y"))
        {
          String sql_row_col5 = "select fn_getcelladdres(?) from dual ";
          pstmt2 = null;
          rs2 = null;
          try
          {
            pstmt2 = DB.prepareStatement(sql_row_col5, null);
            pstmt2.setString(1, aw_addr);
            rs2 = pstmt2.executeQuery();
            if (rs2.next())
            {
              String celladdr = rs2.getString(1);
              int length = celladdr.length();
              
              String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
              r5 = DB.getSQLValue(get_TrxName(), s1, new Object[] { celladdr, celladdr });
              
              String s2 = " select to_number(substr(?,instr(?,',')+1," + length + ")) from dual";
              c5 = DB.getSQLValue(get_TrxName(), s2, new Object[] { celladdr, celladdr });
            }
            rs2.close();
            pstmt2.close();
          }
          catch (Exception e)
          {
            this.log.log(Level.SEVERE, sql_row_col5, e);
          }
          finally
          {
            DB.close(rs2, pstmt2);
          }
        }
        String sqlprd = " select Value from m_product where m_product_id = ? ";
        String PrdVal = DB.getSQLValueStringEx(get_TrxName(), sqlprd, new Object[] { Integer.valueOf(this.M_Product_ID) });
        if ((r1 != 0) && (c1 != 0))
        {
          HSSFRow row1 = this.sheet.getRow(r1);
          HSSFCell attr_cost1 = row1.getCell(c1);
          Double x = new Double(a);
          
          double a_value = 0.0D;
          if ((PrdVal.equalsIgnoreCase("SLD") | PrdVal.equalsIgnoreCase("RLD") | PrdVal.equalsIgnoreCase("SLD/RLD")))
          {
            if (unit.equalsIgnoreCase("I")) {
              a_value = x.doubleValue() / 40.0D;
            } else if (unit.equalsIgnoreCase("F")) {
              a_value = x.doubleValue() / 3.33D;
            } else if (unit.equalsIgnoreCase("N")) {
              a_value = x.doubleValue();
            } else {
              a_value = x.doubleValue();
            }
          }
          else if ((PrdVal.equalsIgnoreCase("RLG") | PrdVal.equalsIgnoreCase("RLRM") | PrdVal.equalsIgnoreCase("RLRB") | PrdVal.equalsIgnoreCase("RLRB/RLG") | PrdVal.equalsIgnoreCase("RLRM/RLG") | PrdVal.equalsIgnoreCase("SLG") | PrdVal.equalsIgnoreCase("SLRM") | PrdVal.equalsIgnoreCase("SLRB") | PrdVal.equalsIgnoreCase("SLRB/SLG") | PrdVal.equalsIgnoreCase("SLRM/SLG")))
          {
            if (unit.equalsIgnoreCase("I")) {
              a_value = x.doubleValue() * 25.0D;
            } else if (unit.equalsIgnoreCase("F")) {
              a_value = x.doubleValue() * 12.0D * 25.0D;
            } else if (unit.equalsIgnoreCase("N")) {
              a_value = x.doubleValue() * 1000.0D;
            } else {
              a_value = x.doubleValue();
            }
          }
          else if (unit.equalsIgnoreCase("I")) {
            a_value = x.doubleValue() * new Double(inch).doubleValue();
          } else {
            a_value = x.doubleValue();
          }
          attr_cost1.setCellValue(a_value);
        }
        if ((r2 != 0) && (c2 != 0))
        {
          HSSFRow row11 = this.sheet.getRow(r2);
          HSSFCell attr_cost2 = row11.getCell(c2);
          
          double b_value = 0.0D;
          if ((PrdVal.equalsIgnoreCase("SLD") | PrdVal.equalsIgnoreCase("RLD") | PrdVal.equalsIgnoreCase("SLD/RLD"))) {
            b_value = b;
          } else if ((PrdVal.equalsIgnoreCase("RLG") | PrdVal.equalsIgnoreCase("RLRM") | PrdVal.equalsIgnoreCase("RLRB") | PrdVal.equalsIgnoreCase("RLRB/RLG") | PrdVal.equalsIgnoreCase("RLRM/RLG") | PrdVal.equalsIgnoreCase("SLG") | PrdVal.equalsIgnoreCase("SLRM") | PrdVal.equalsIgnoreCase("SLRB") | PrdVal.equalsIgnoreCase("SLRB/SLG") | PrdVal.equalsIgnoreCase("SLRM/SLG")))
          {
            if (unit.equalsIgnoreCase("I")) {
              b_value = b * 25.0D;
            } else if (unit.equalsIgnoreCase("F")) {
              b_value = b * 25.0D;
            } else if (unit.equalsIgnoreCase("N"))
            {
              if (b_unit.equalsIgnoreCase("I")) {
                b_value = b * 25.0D;
              } else {
                b_value = b;
              }
            }
            else {
              b_value = b;
            }
          }
          else if (unit.equalsIgnoreCase("I")) {
            b_value = b * new Double(inch).doubleValue();
          } else {
            b_value = b;
          }
          attr_cost2.setCellValue(b_value);
        }
        if ((r3 != 0) && (c3 != 0))
        {
          HSSFRow row12 = this.sheet.getRow(r3);
          HSSFCell attr_cost3 = row12.getCell(c3);
          
          double s_value = 0.0D;
          if (PrdVal.equalsIgnoreCase("SLD/RLD"))
          {
            if (unit.equalsIgnoreCase("I")) {
              s_value = supply / 40.0D;
            } else if (unit.equalsIgnoreCase("F")) {
              s_value = supply / 3.33D;
            } else if (unit.equalsIgnoreCase("N")) {
              s_value = supply;
            }
            attr_cost3.setCellValue(s_value);
          }
          else if ((PrdVal.equalsIgnoreCase("RLRB/RLG") | PrdVal.equalsIgnoreCase("RLRM/RLG") | PrdVal.equalsIgnoreCase("SLRB/SLG") | PrdVal.equalsIgnoreCase("SLRM/SLG")))
          {
            if (unit.equalsIgnoreCase("I")) {
              s_value = supply * 25.0D;
            } else if (unit.equalsIgnoreCase("F")) {
              s_value = supply * 12.0D * 25.0D;
            } else if (unit.equalsIgnoreCase("N")) {
              s_value = supply * 1000.0D;
            } else {
              s_value = supply;
            }
            attr_cost3.setCellValue(s_value);
          }
          else if (unit.equalsIgnoreCase("I"))
          {
            attr_cost3.setCellValue(supply * new Double(inch).doubleValue());
          }
          else
          {
            attr_cost3.setCellValue(supply);
          }
        }
        if ((r4 != 0) && (c4 != 0))
        {
          HSSFRow row14 = this.sheet.getRow(r4);
          HSSFCell attr_cost4 = row14.getCell(c4);
          
          attr_cost4.setCellValue(len);
        }
        if ((r5 != 0) && (c5 != 0))
        {
          HSSFRow row15 = this.sheet.getRow(r5);
          HSSFCell attr_cost5 = row15.getCell(c5);
          
          attr_cost5.setCellValue(aw);
        }
        String sqlcor = " select NVL(CORNERYN,'N') from c_quotationline where c_quotationline_id = ? ";
        String coryn = DB.getSQLValueStringEx(get_TrxName(), sqlcor, new Object[] { Integer.valueOf(record_id) });
        if (coryn.equalsIgnoreCase("Y"))
        {
          this.prod_desc = this.prod_desc.trim();
          if ((unit.equalsIgnoreCase("I") | unit.equalsIgnoreCase("F"))) {
            this.prod_desc = (this.prod_desc + "/COR 12" + "\" " + "* 12" + "\" " + "(90) * ");
          } else {
            this.prod_desc += "/COR 300MM * 300MM (90) * ";
          }
        }
        String sqlways = " select NVL(WAYS,'0') from c_quotationline where c_quotationline_id = ? ";
        String ways = DB.getSQLValueStringEx(get_TrxName(), sqlways, new Object[] { Integer.valueOf(record_id) });
        if (ways.equalsIgnoreCase("1")) {
          this.prod_desc += "1W ";
        } else if (ways.equalsIgnoreCase("2")) {
          this.prod_desc += "2W ";
        } else if (ways.equalsIgnoreCase("3")) {
          this.prod_desc += "3W ";
        } else if (ways.equalsIgnoreCase("4")) {
          this.prod_desc += "4W ";
        }
        String sqlaw = " select NVL(AIRWAY,'N') from c_quotationline where c_quotationline_id = ? ";
        String awstr = DB.getSQLValueStringEx(get_TrxName(), sqlaw, new Object[] { Integer.valueOf(record_id) });
        if (awstr.equalsIgnoreCase("N")) {
          this.prod_desc = this.prod_desc.trim();
        } else {
          this.prod_desc = (this.prod_desc.trim() + "(" + awstr + ") -");
        }
        if ((supply == 0.0D) && (a > 0.0D) && (b > 0.0D))
        {
          if ((PrdVal.equalsIgnoreCase("SLD") | PrdVal.equalsIgnoreCase("RLD")))
          {
            if (coryn.equalsIgnoreCase("Y"))
            {
              if (unit.equalsIgnoreCase("I")) {
                this.prod_desc = (this.prod_desc + b_disp.intValue() + " SLT");
              } else if (unit.equalsIgnoreCase("F")) {
                this.prod_desc = (this.prod_desc + b_disp.intValue() + " SLT");
              } else if (unit.equalsIgnoreCase("N")) {
                this.prod_desc = (this.prod_desc + b_disp.intValue() + " SLT");
              } else {
                this.prod_desc = (this.prod_desc + b_disp.intValue() + " SLT");
              }
            }
            else if (unit.equalsIgnoreCase("I")) {
              this.prod_desc = (this.prod_desc + a + "\" * " + b_disp.intValue() + " SLT");
            } else if (unit.equalsIgnoreCase("F")) {
              this.prod_desc = (this.prod_desc + a + " F * " + b_disp.intValue() + " SLT");
            } else if (unit.equalsIgnoreCase("N")) {
              this.prod_desc = (this.prod_desc + a + " M * " + b_disp.intValue() + " SLT");
            } else {
              this.prod_desc = (this.prod_desc + a + " MM * " + b_disp.intValue() + " SLT");
            }
          }
          else if ((PrdVal.equalsIgnoreCase("RLG") | PrdVal.equalsIgnoreCase("RLRM") | PrdVal.equalsIgnoreCase("RLRB") | PrdVal.equalsIgnoreCase("RLRB/RLG") | PrdVal.equalsIgnoreCase("RLRM/RLG") | PrdVal.equalsIgnoreCase("SLG") | PrdVal.equalsIgnoreCase("SLRM") | PrdVal.equalsIgnoreCase("SLRB") | PrdVal.equalsIgnoreCase("SLRB/SLG") | PrdVal.equalsIgnoreCase("SLRM/SLG")))
          {
            if (coryn.equalsIgnoreCase("Y"))
            {
              if (unit.equalsIgnoreCase("I")) {
                this.prod_desc = (this.prod_desc + b + "\" ");
              } else if (unit.equalsIgnoreCase("F")) {
                this.prod_desc = (this.prod_desc + b + "\" ");
              } else if (unit.equalsIgnoreCase("N"))
              {
                if (b_unit.equalsIgnoreCase("I")) {
                  this.prod_desc = (this.prod_desc + b + "\" ");
                } else {
                  this.prod_desc = (this.prod_desc + b_disp.intValue() + " MM");
                }
              }
              else {
                this.prod_desc = (this.prod_desc + b_disp.intValue() + " MM");
              }
            }
            else if (unit.equalsIgnoreCase("I")) {
              this.prod_desc = (this.prod_desc + a + "\" * " + b + "\" ");
            } else if (unit.equalsIgnoreCase("F")) {
              this.prod_desc = (this.prod_desc + a + " F * " + b + "\" ");
            } else if (unit.equalsIgnoreCase("N"))
            {
              if (b_unit.equalsIgnoreCase("I")) {
                this.prod_desc = (this.prod_desc + a + " M * " + b + "\" ");
              } else {
                this.prod_desc = (this.prod_desc + a + " M * " + b_disp.intValue() + " MM");
              }
            }
            else {
              this.prod_desc = (this.prod_desc + a + " MM * " + b_disp.intValue() + " MM");
            }
          }
          else if ((PrdVal.equalsIgnoreCase("SADPB") | PrdVal.equalsIgnoreCase("SADPM") | PrdVal.equalsIgnoreCase("SADSB") | PrdVal.equalsIgnoreCase("SADSM") | PrdVal.equalsIgnoreCase("RADP") | PrdVal.equalsIgnoreCase("RADS")))
          {
            if (coryn.equalsIgnoreCase("Y"))
            {
              if (unit.equalsIgnoreCase("I")) {
                this.prod_desc = (this.prod_desc + b + "\" ");
              } else if (unit.equalsIgnoreCase("F")) {
                this.prod_desc = (this.prod_desc + b + " F");
              } else if (unit.equalsIgnoreCase("N")) {
                this.prod_desc = (this.prod_desc + b + " M");
              } else {
                this.prod_desc = (this.prod_desc + b + " MM");
              }
            }
            else if (unit.equalsIgnoreCase("I")) {
              this.prod_desc = (this.prod_desc + a_disp.intValue() + "\" * " + b_disp.intValue() + "\" ");
            } else if (unit.equalsIgnoreCase("F")) {
              this.prod_desc = (this.prod_desc + a + " F * " + b + " F");
            } else if (unit.equalsIgnoreCase("N")) {
              this.prod_desc = (this.prod_desc + a + " M * " + b + " M");
            } else {
              this.prod_desc = (this.prod_desc + a_disp.intValue() + " MM * " + b_disp.intValue() + " MM");
            }
          }
          else if ((PrdVal.equalsIgnoreCase("SARB") | PrdVal.equalsIgnoreCase("SARM") | PrdVal.equalsIgnoreCase("SAG") | PrdVal.equalsIgnoreCase("RARB") | PrdVal.equalsIgnoreCase("RARM") | PrdVal.equalsIgnoreCase("RAG")))
          {
            if (coryn.equalsIgnoreCase("Y"))
            {
              if (unit.equalsIgnoreCase("I")) {
                this.prod_desc = (this.prod_desc + b + "\" ");
              } else if (unit.equalsIgnoreCase("F")) {
                this.prod_desc = (this.prod_desc + b + " F");
              } else if (unit.equalsIgnoreCase("N")) {
                this.prod_desc = (this.prod_desc + b + " M");
              } else {
                this.prod_desc = (this.prod_desc + b + " MM");
              }
            }
            else if (unit.equalsIgnoreCase("I")) {
              this.prod_desc = (this.prod_desc + a + "\" * " + b + "\" ");
            } else if (unit.equalsIgnoreCase("F")) {
              this.prod_desc = (this.prod_desc + a + " F * " + b + " F");
            } else if (unit.equalsIgnoreCase("N")) {
              this.prod_desc = (this.prod_desc + a + " M * " + b + " M");
            } else {
              this.prod_desc = (this.prod_desc + a_disp.intValue() + " MM * " + b_disp.intValue() + " MM");
            }
          }
          else if ((PrdVal.equalsIgnoreCase("GAL-IS") | PrdVal.equalsIgnoreCase("EAL-IS") | PrdVal.equalsIgnoreCase("STL-IS")))
          {
            if (unit.equalsIgnoreCase("I")) {
              this.prod_desc = (this.prod_desc + a + "\" * " + b_disp.intValue() + "\" " + " + IS");
            } else {
              this.prod_desc = (this.prod_desc + a + " MM * " + b_disp.intValue() + " MM" + " + IS");
            }
          }
          else if (PrdVal.equalsIgnoreCase("GAL-CR-IS"))
          {
            if (unit.equalsIgnoreCase("I")) {
              this.prod_desc = (this.prod_desc + a + "\" * " + b_disp.intValue() + "\" " + " + CR-IS");
            } else {
              this.prod_desc = (this.prod_desc + a + " MM * " + b_disp.intValue() + " MM" + " + CR-IS");
            }
          }
          else if (PrdVal.equalsIgnoreCase("NRD-CR"))
          {
            if (unit.equalsIgnoreCase("I")) {
              this.prod_desc = (this.prod_desc + a + "\" * " + b_disp.intValue() + "\" " + " + CR");
            } else {
              this.prod_desc = (this.prod_desc + a + " MM * " + b_disp.intValue() + " MM" + " + CR");
            }
          }
          else if (PrdVal.equalsIgnoreCase("STL-SF"))
          {
            if (unit.equalsIgnoreCase("I")) {
              this.prod_desc = (this.prod_desc + a + "\" * " + b_disp.intValue() + "\" " + " + FILTER");
            } else {
              this.prod_desc = (this.prod_desc + a + " MM * " + b_disp.intValue() + " MM" + " + FILTER");
            }
          }
          else if (PrdVal.equalsIgnoreCase("STL-IS-SF"))
          {
            if (unit.equalsIgnoreCase("I")) {
              this.prod_desc = (this.prod_desc + a + "\" * " + b_disp.intValue() + "\" " + " + IS" + " + FILTER");
            } else {
              this.prod_desc = (this.prod_desc + a + " MM * " + b_disp.intValue() + " MM" + " + IS" + " + FILTER");
            }
          }
          else if ((PrdVal.equalsIgnoreCase("PGL") | PrdVal.equalsIgnoreCase("RSA") | PrdVal.equalsIgnoreCase("BVRE"))) {
            this.prod_desc = (this.prod_desc + a_disp.intValue() + " MM * " + b_disp.intValue() + " MM * " + len_disp.intValue() + " MM");
          } else if (coryn.equalsIgnoreCase("Y"))
          {
            if (unit.equalsIgnoreCase("I")) {
              this.prod_desc = (this.prod_desc + b + "\" ");
            } else if (unit.equalsIgnoreCase("F")) {
              this.prod_desc = (this.prod_desc + b + " F");
            } else if (unit.equalsIgnoreCase("N")) {
              this.prod_desc = (this.prod_desc + b + " M");
            } else {
              this.prod_desc = (this.prod_desc + b + " MM");
            }
          }
          else if (unit.equalsIgnoreCase("I")) {
            this.prod_desc = (this.prod_desc + a + "\" * " + b + "\" ");
          } else if (unit.equalsIgnoreCase("F")) {
            this.prod_desc = (this.prod_desc + a + " F * " + b + " F");
          } else if (unit.equalsIgnoreCase("N")) {
            this.prod_desc = (this.prod_desc + a + " M * " + b + " M");
          } else {
            this.prod_desc = (this.prod_desc + a + " MM * " + b + " MM");
          }
        }
        else if (supply > 0.0D) {
          if (PrdVal.equalsIgnoreCase("SLD/RLD"))
          {
            if (unit.equalsIgnoreCase("I")) {
              this.prod_desc = (this.prod_desc + a + "\" * " + b_disp.intValue() + " SLT" + " S: " + supply + "\" ");
            } else if (unit.equalsIgnoreCase("F")) {
              this.prod_desc = (this.prod_desc + a + " F * " + b_disp.intValue() + " SLT" + " S: " + supply + " F");
            } else if (unit.equalsIgnoreCase("N")) {
              this.prod_desc = (this.prod_desc + a + " M * " + b_disp.intValue() + " SLT" + " S: " + supply + " M");
            } else {
              this.prod_desc = (this.prod_desc + a + " MM * " + b_disp.intValue() + " SLT" + " S: " + supply + " MM");
            }
          }
          else if ((PrdVal.equalsIgnoreCase("RLRB/RLG") | PrdVal.equalsIgnoreCase("RLRM/RLG") | PrdVal.equalsIgnoreCase("SLRB/SLG") | PrdVal.equalsIgnoreCase("SLRM/SLG")))
          {
            if (unit.equalsIgnoreCase("I")) {
              this.prod_desc = (this.prod_desc + a + "\" * " + b + "\" " + " S: " + supply + "\" ");
            } else if (unit.equalsIgnoreCase("F")) {
              this.prod_desc = (this.prod_desc + a + " F * " + b + "\" " + " S: " + supply + " F");
            } else if (unit.equalsIgnoreCase("N"))
            {
              if (b_unit.equalsIgnoreCase("I")) {
                this.prod_desc = (this.prod_desc + a + " M * " + b + "\" " + " S: " + supply + " M");
              } else {
                this.prod_desc = (this.prod_desc + a + " M * " + b_disp.intValue() + " MM" + " S: " + supply + " M");
              }
            }
            else {
              this.prod_desc = (this.prod_desc + a + " MM * " + b_disp.intValue() + " MM" + " S: " + supply + " MM");
            }
          }
          else if ((PrdVal.equalsIgnoreCase("SARB/SAG") | PrdVal.equalsIgnoreCase("SARM/SAG") | PrdVal.equalsIgnoreCase("RARB/RAG") | PrdVal.equalsIgnoreCase("RARM/RAG")))
          {
            if (unit.equalsIgnoreCase("I")) {
              this.prod_desc = (this.prod_desc + a + "\" * " + b + "\" " + " S: " + supply + "\" ");
            } else if (unit.equalsIgnoreCase("F")) {
              this.prod_desc = (this.prod_desc + a + " F * " + b + " F" + " S: " + supply + " F");
            } else if (unit.equalsIgnoreCase("N")) {
              this.prod_desc = (this.prod_desc + a + " M * " + b + " M" + " S: " + supply + " M");
            } else {
              this.prod_desc = (this.prod_desc + a_disp.intValue() + " MM * " + b_disp.intValue() + " MM" + " S: " + s_disp.intValue() + " MM");
            }
          }
          else if (unit.equalsIgnoreCase("I")) {
            this.prod_desc = (this.prod_desc + a + "\" * " + b + "\" " + " S: " + supply + "\" ");
          } else if (unit.equalsIgnoreCase("F")) {
            this.prod_desc = (this.prod_desc + a + " F * " + b + " F" + " S: " + supply + " F");
          } else if (unit.equalsIgnoreCase("N")) {
            this.prod_desc = (this.prod_desc + a + " M * " + b + " M" + " S: " + supply + " M");
          } else {
            this.prod_desc = (this.prod_desc + a + " MM * " + b + " MM" + " S: " + supply + " MM");
          }
        }
        if ((a > 0.0D) && (supply == 0.0D) && (b == 0.0D)) {
          if (PrdVal.equalsIgnoreCase("RVCD")) {
            if (unit.equalsIgnoreCase("I")) {
              this.prod_desc = (this.prod_desc + a + "\" Ø");
            } else {
              this.prod_desc = (this.prod_desc + a + " MM Ø");
            }
          }
        }
        String sqlcur = " select NVL(CURVEYN,'N') from c_quotationline where c_quotationline_id = ? ";
        String curyn = DB.getSQLValueStringEx(get_TrxName(), sqlcur, new Object[] { Integer.valueOf(record_id) });
        if (curyn.equalsIgnoreCase("Y")) {
          this.prod_desc += " (CURVE)";
        }
        String sqleq = " select NVL(EQUGRIDYN,'N') from c_quotationline where c_quotationline_id = ? ";
        String eqyn = DB.getSQLValueStringEx(get_TrxName(), sqleq, new Object[] { Integer.valueOf(record_id) });
        if (eqyn.equalsIgnoreCase("Y")) {
          this.prod_desc += " EG";
        }
        FileOutputStream fileOut = new FileOutputStream(this.Path_of_XLFile);
        this.wb.write(fileOut);
        fileOut.flush();
       
        String sql_row_col = "select fn_getcelladdres(?) from dual ";
        PreparedStatement pstmt3 = null;
        ResultSet rs3 = null;
        try
        {
          pstmt3 = DB.prepareStatement(sql_row_col, null);
          pstmt3.setString(1, this.FGCost_Cell_Addr);
          rs3 = pstmt3.executeQuery();
          if (rs3.next())
          {
            String celladdr = rs3.getString(1);
            int length = celladdr.length();
            
            String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
            r = DB.getSQLValue(get_TrxName(), s1, new Object[] { celladdr, celladdr });
            
            String s2 = " select to_number(substr(?,instr(?,',')+1," + length + ")) from dual";
            c = DB.getSQLValue(get_TrxName(), s2, new Object[] { celladdr, celladdr });
          }
          rs3.close();
          pstmt3.close();
        }
        catch (Exception localException1) {}finally
        {
          DB.close(rs3, pstmt3);
        }
        this.row = this.sheet.getRow(r);
        HSSFCell cell = this.row.getCell(c);
        
        CellValue cellValue1 = this.evaluator.evaluate(cell);
        this.prod_cost = cellValue1.getNumberValue();
        if (curyn.equalsIgnoreCase("Y")) {
          this.prod_cost *= 2.85D;
        }
        if ((ways.equalsIgnoreCase("1") | ways.equalsIgnoreCase("2") | ways.equalsIgnoreCase("3"))) {
          this.prod_cost *= 1.1D;
        }
        if (eqyn.equalsIgnoreCase("Y")) {
          this.prod_cost *= 1.092D;
        }
        System.out.println(" prod_cost : " + this.prod_cost);
      }
      rs1.close();
      pstmt1.close();
    }
    catch (Exception e)
    {
      this.log.saveError("Error", e.getMessage());
      try
      {
        DB.close(rs1, pstmt1);
      }
      catch (Exception localException2) {}
    }
    finally
    {
      try
      {
        DB.close(rs1, pstmt1);
      }
      catch (Exception localException3) {}
    }
  }
}
