package org.compiere.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.compiere.db.CConnection;
import org.compiere.model.MClient;
import org.compiere.util.DB;
import org.compiere.util.EMail;

public class Process_SendEmail_PM_CC extends SvrProcess {
    private int AD_PInstance_ID = 0;
    private int Record_ID = 0;
    private int AD_Client_ID = 0;
    private BigDecimal ord_id = null;
    private BigDecimal prjtype_id = null;

    protected void prepare() {
        AD_PInstance_ID = getAD_PInstance_ID();
        AD_Client_ID = getAD_Client_ID();
        Record_ID = getRecord_ID();
    }

    @Override
    protected String doIt() throws Exception {
        String emailFrom = null;
        String fromPWD = null;
        String emailTo = null;
        String salesmanName = null;
        String documentno = null;
        String orderno = null;
        BigDecimal salesrep_id = null;
        String custname = null;
        String description = null;
        String custno = null;
        String email_cc=null; 
        String user = null;
        BigDecimal createdbyid= null;
        
        String usernameSQL = "select name from ad_user where ad_user_id = ?";
        String emailToSQL = "SELECT prodemail FROM c_projecttype WHERE c_projecttype_id = ?";
        String emailFromSQL = "SELECT requestuser, requestuserpw FROM AD_client WHERE ad_client_id = ?";
        String invoiceSQL = "SELECT i.documentno, i.salesrep_id, bp.name, p.value, i.c_order_id, "
                + "p.c_projecttype_id, d.description, d.documentno AS custno,d.createdby "
                + "FROM c_invoice i "
                + "JOIN beta_custcomp d ON i.c_invoice_id = d.c_invoice_id "
                + "LEFT OUTER JOIN c_bpartner bp ON i.c_bpartner_id = bp.c_bpartner_id "
                + "LEFT OUTER JOIN c_project p ON i.c_project_id = p.c_project_id "
                + "WHERE d.beta_custcomp_id = ?"; 
       
        try (PreparedStatement ps = DB.prepareStatement(invoiceSQL, "DSPL")) {
            ps.setInt(1, Record_ID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    documentno = rs.getString(1);
                    salesrep_id = rs.getBigDecimal(2);
                    custname = rs.getString(3);
                    orderno = rs.getString(4);
                    ord_id = rs.getBigDecimal(5);
                    prjtype_id = rs.getBigDecimal(6);
                    description = rs.getString(7);
                    custno = rs.getString(8);
                    createdbyid=rs.getBigDecimal(9);
                }
            }
        } catch (Exception e) {
            handleError("Error fetching invoice data: ", e);
        }

        try (PreparedStatement pstmntCl = DB.prepareStatement(emailFromSQL, "DSPL")) {
            pstmntCl.setInt(1, AD_Client_ID);
            try (ResultSet rsCl = pstmntCl.executeQuery()) {
                if (rsCl.next()) {
                    emailFrom = rsCl.getString(1);
                    fromPWD = rsCl.getString(2);
                }
            }
        } catch (Exception e) {
            handleError("Error fetching email configuration: ", e);
        }

        try (PreparedStatement ps = DB.prepareStatement(emailToSQL, "DSPL")) {
            ps.setBigDecimal(1, prjtype_id); 
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    emailTo = rs.getString(1);
                }
            }
        } catch (Exception e) {
            handleError("Error fetching recipient email: ", e);
        }
        try (PreparedStatement ps = DB.prepareStatement(usernameSQL, "DSPL")) {
            ps.setBigDecimal(1, createdbyid); 
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                	user = rs.getString(1);
                }
            }
        } catch (Exception e) {
            handleError("Error fetching user name: ", e);
        }

        String mailContent = "Attn : Production Team,\n\nPlease process the below Requested Customer Compliant: \n"
                + "\n Customer Compliant No : " + custno
                + "\n Customer Name : " + custname
                + "\n Order  : " + orderno
                + "\n Invoice : " + documentno
                + "\n Requested Service : " + description;

        email_cc=DB.getSQLValueString(get_TrxName(),"select nvl(EMAIL,EMAILUSER) "
        		+ "from AD_User where AD_User_ID=?",salesrep_id);
        MClient client = MClient.get(getCtx());

        if (emailFrom != null && emailTo != null) {
            EMail email = client.createEMail(emailFrom.trim(), emailTo.trim(),
                    "Customer Compliant "+custno,
                    mailContent + "\n\nThanks & Regards \n"
                            + "\n "+user
                            + "\n (Auto generated mail from Adempiere ERP)", false, fromPWD);

            email.addCc(email_cc);
            email.send();
            addLog(0, null, null, "Email sent successfully");
        }

        String updateSQL = "UPDATE beta_custcomp SET cur_stat = 'A' WHERE beta_custcomp_id = ?";
        try (PreparedStatement updatePs = DB.prepareStatement(updateSQL, get_TrxName())) {
            updatePs.setInt(1, Record_ID);
            updatePs.executeUpdate();
        } catch (Exception e) {
            handleError("Error updating customer complaint status: ", e);
        }
        return " ";
    }

    private void handleError(String message, Exception e) {
        String errorMessage = e.getLocalizedMessage();
        System.out.println(message + errorMessage);
        String[] errorParts = errorMessage.split(":");
        addLog(0, null, null, "Process Stopped");
        if (errorParts.length > 1) {
            addLog(0, null, null, errorParts[1]);
        }
    }
}
