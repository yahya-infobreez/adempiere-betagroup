/**
 * 
 */
package id.web.sistematika.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ArrayList;
import java.util.logging.Level;

import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.MWindow;
import org.compiere.model.MTab;
import org.compiere.model.MField;
import org.compiere.model.MColumn;
import org.compiere.model.MWindowAccess;
import org.compiere.model.MProcessAccess;
import org.compiere.model.MRole;
import org.compiere.util.CLogger;
import org.compiere.util.DB;

/**
 * @author Bayu Cahya P
 *         http://sistematika.web.id
 * Purpose
 * 		#1 Set window access and automatically assign its processes
 *
 */
public class BetaITValidator implements ModelValidator {
	
	/**	Logger			*/
	private CLogger log = CLogger.getCLogger(getClass());
	
	private Collection<Integer> listProcess = new ArrayList<Integer>();
	
	private String betaInfo = "[BetaIT]: ";
	
	/** Client			*/
	private int		m_AD_Client_ID = -1;
	
	/** User			*/
	private int		m_AD_User_ID = -1;
	
	private int 	processCount = 0;


	/**
	 * 
	 */
	public BetaITValidator() {
		super();
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#docValidate(org.compiere.model.PO, int)
	 */
	public String docValidate(PO po, int timing) {
		// do nothing at the moment
		return null;
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#getAD_Client_ID()
	 */
	public int getAD_Client_ID() {
		return m_AD_Client_ID;
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#initialize(org.compiere.model.ModelValidationEngine, org.compiere.model.MClient)
	 */
	public void initialize(ModelValidationEngine engine, MClient client) {
		m_AD_Client_ID = client.getAD_Client_ID();

		// listening on this table changes
		log.config(betaInfo + "registering MWindowAccess");
		engine.addModelChange(MWindowAccess.Table_Name, this);

	}
	
	public String modelChange(PO po, int type) throws Exception {
		boolean isChange = (TYPE_AFTER_CHANGE == type);
		boolean isNew    = (TYPE_AFTER_NEW == type);
		boolean isDelete = (TYPE_BEFORE_DELETE == type || TYPE_DELETE == type);
		
		log.info(betaInfo + "Start to process model change");
		listProcess.clear();
		
		if (po instanceof MWindowAccess && (isNew || isChange || isDelete))
		{
			MWindowAccess wAccess = (MWindowAccess)po;
			
			int windowID = wAccess.getAD_Window_ID();
			MWindow targetWindow = new MWindow(po.getCtx(), windowID, null);
			MTab[] tabs = targetWindow.getTabs(true, null);
			
			for (MTab tab : tabs)
			{
				// no need to proceed advanced tab
				if (tab.isAdvancedTab())
					continue;
				// process(reports) in tab level
				if (tab.getAD_Process_ID() != 0)
					listProcess.add(tab.getAD_Process_ID());
				
				// process in the field level
				MField[] fields = tab.getFields(true, null);
				for (MField field : fields)
				{
					MColumn column = new MColumn(po.getCtx(), field.getAD_Column_ID(), null);
					if (column.getAD_Process_ID() != 0)
						// register this process
						listProcess.add(column.getAD_Process_ID());
					
				}
			}
			
			int clientId = m_AD_Client_ID;
			int roleId = wAccess.getAD_Role_ID();
			
			MRole targetRole = new MRole(po.getCtx(), roleId, null);
			
			log.info(betaInfo + targetWindow.getName() +
					" window - Is active : " +
					wAccess.isActive() +
					" Is deleted : " +
					isDelete);
			
			processCount = 0;

			if (wAccess.isActive() && !isDelete)
			{
				// set all processes
				for (int i=0; i<=listProcess.size()-1; i++)
				{
					int processId = ((ArrayList<Integer>)listProcess).get(i);
					if (checkAvailableProcessAccess(roleId, processId, clientId, po.get_TrxName()) == null)
					{
						processCount++;
						MProcessAccess newProcess =
							new MProcessAccess(po.getCtx(), 0, po.get_TrxName());
						newProcess.setAD_Org_ID(0);
						newProcess.setAD_Role_ID(roleId);
						newProcess.setAD_Process_ID(processId);
						newProcess.setIsActive(true);
						newProcess.setIsReadWrite(true);
						newProcess.save(po.get_TrxName());
						
					}
				}
				log.info(betaInfo + targetWindow.getName() + 
						" window - successful registering " + processCount +
						" processes for [" +
						targetRole.getName() +
						"] role");
			} 
			else
			if (!wAccess.isActive() || isDelete)
			{
				// delete this records from the process access window
				for (int i=0; i<=listProcess.size()-1; i++)
				{
					int processId = ((ArrayList<Integer>)listProcess).get(i);
					
					String sql = 
						"DELETE FROM ad_process_access " +
						"WHERE ad_client_id = " + clientId +
						"AND ad_org_id = 0 " +
						"AND ad_role_id = " + roleId +
						"AND ad_process_id = " + processId;
					int no = DB.executeUpdate(sql, po.get_TrxName());
					if (no == 1) processCount++;
					
				}
				log.info(betaInfo + targetWindow.getName() + 
						" window - successful deleted " + processCount + 
						" processes for [" +
						targetRole.getName() +
						"] role");

			}
		}
		return null;
		
	}

	private String checkAvailableProcessAccess(int roleId, int processId,
			int clientId, String trxName) 
	{
		// check an existance of process access for
		// certain role and process
		String testResults = null;
		
		String sqlProcessAccess = 
			"SELECT 1 " +
			"FROM ad_process_access " +
			"WHERE ad_client_id = ? " +
			"AND ad_org_id = 0 " +
			"AND ad_role_id = ? " +
			"AND ad_process_id = ? ";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try
		{
			pstmt = DB.prepareStatement(sqlProcessAccess, trxName);
			pstmt.setInt(1, clientId);
			pstmt.setInt(2, roleId);
			pstmt.setInt(3, processId);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				testResults = "OK";
				
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sqlProcessAccess, e);
			testResults = e.getLocalizedMessage();
			
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
			
		}
		return testResults;
		
	}




	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) {
		m_AD_User_ID = AD_User_ID;
		return null;
		
	}

}