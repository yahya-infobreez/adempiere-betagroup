-- Oct 24, 2024 6:46:05 AM AST
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Value='Changes done for eInvoice support',Updated=TO_DATE('2024-10-24 06:46:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=50003
;

-- Oct 24, 2024 6:52:51 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_EntityType (AD_Client_ID,AD_EntityType_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,ModelPackage,Name,Processing,Updated,UpdatedBy) VALUES (0,1000000,0,TO_DATE('2024-10-24 06:52:50','YYYY-MM-DD HH24:MI:SS'),100,'Customizations done for BETA Group','BETAG','Customizations done for BETA Group','Y','com.betagroup.model','BETA Group Changes','N',TO_DATE('2024-10-24 06:52:50','YYYY-MM-DD HH24:MI:SS'),100)
;


-- Oct 24, 2024 6:36:15 AM AST
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002571,1111,0,10,112,'Name2',TO_DATE('2024-10-24 06:36:15','YYYY-MM-DD HH24:MI:SS'),100,'Additional Name','BETAG',120,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','Name 2',0,TO_DATE('2024-10-24 06:36:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Oct 24, 2024 6:36:15 AM AST
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002571 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Oct 24, 2024 6:36:46 AM AST
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Client ADD Name2 NVARCHAR2(120) DEFAULT NULL 
;

-- Oct 24, 2024 6:54:16 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002572,1111,0,10,155,'Name2',TO_DATE('2024-10-24 06:54:16','YYYY-MM-DD HH24:MI:SS'),100,'Additional Name','BETAG',120,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','Name 2',0,TO_DATE('2024-10-24 06:54:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Oct 24, 2024 6:54:16 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002572 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Oct 24, 2024 6:54:29 AM AST
-- Changes done for eInvoice support
ALTER TABLE AD_Org ADD Name2 NVARCHAR2(120) DEFAULT NULL 
;

-- Oct 24, 2024 6:55:59 AM AST
-- Changes done for eInvoice support
UPDATE AD_Column SET EntityType='BETAG',Updated=TO_DATE('2024-10-24 06:55:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1002571
;

-- Oct 24, 2024 6:56:00 AM AST
-- Changes done for eInvoice support
ALTER TABLE AD_Client MODIFY Name2 NVARCHAR2(120) DEFAULT NULL 
;

-- Oct 24, 2024 11:03:52 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002573,1000789,0,10,155,'TRDLICENSENO',TO_DATE('2024-10-24 11:03:51','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',25,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','TRDLICENSENO',0,TO_DATE('2024-10-24 11:03:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Oct 24, 2024 11:03:52 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002573 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Oct 24, 2024 11:04:56 AM AST
-- Changes done for eInvoice support
ALTER TABLE AD_Org ADD TRDLICENSENO NVARCHAR2(25) DEFAULT NULL 
;

-- Oct 24, 2024 11:07:45 AM AST
-- Changes done for eInvoice support
UPDATE AD_Column SET EntityType='BETAG',Updated=TO_DATE('2024-10-24 11:07:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1002573
;

-- Oct 24, 2024 11:47:56 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,1001023,0,'DIGITALCERTIFICATE',TO_DATE('2024-10-24 11:47:56','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','DIGITAL CERTIFICATE','DIGITAL CERTIFICATE',TO_DATE('2024-10-24 11:47:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 24, 2024 11:47:56 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=1001023 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Oct 24, 2024 11:48:28 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,1001024,0,'PUBLICKEY',TO_DATE('2024-10-24 11:48:28','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','PUBLIC KEY','PUBLIC KEY',TO_DATE('2024-10-24 11:48:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 24, 2024 11:48:28 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=1001024 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Oct 24, 2024 11:48:50 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,1001025,0,'PRIVATEKEY',TO_DATE('2024-10-24 11:48:49','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','PRIVATE KEY','PRIVATE KEY',TO_DATE('2024-10-24 11:48:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 24, 2024 11:48:50 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=1001025 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Oct 24, 2024 11:49:21 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,1001026,0,'EGS_NAME',TO_DATE('2024-10-24 11:49:21','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','EGS NAME','EGS NAME',TO_DATE('2024-10-24 11:49:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 24, 2024 11:49:21 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=1001026 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Oct 24, 2024 11:50:11 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,1001027,0,'EGS_SERIALNO',TO_DATE('2024-10-24 11:50:11','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','EGS SERIAL NO','EGS SERIAL NO',TO_DATE('2024-10-24 11:50:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 24, 2024 11:50:11 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=1001027 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Oct 24, 2024 11:51:48 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002574,1001023,0,36,155,'DIGITALCERTIFICATE',TO_DATE('2024-10-24 11:51:48','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',2500,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','DIGITAL CERTIFICATE',0,TO_DATE('2024-10-24 11:51:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Oct 24, 2024 11:51:48 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002574 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Oct 24, 2024 11:52:34 AM AST
-- Changes done for eInvoice support. The field is already created. And cannot be changed. Hence drop and re-create
ALTER TABLE AD_Org DROP COLUMN IF EXISTS DIGITALCERTIFICATE;
ALTER TABLE AD_Org ADD DIGITALCERTIFICATE CLOB DEFAULT NULL 
;

-- Oct 24, 2024 1:46:43 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002575,1001024,0,10,155,'PUBLICKEY',TO_DATE('2024-10-24 13:46:43','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',256,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','PUBLIC KEY',0,TO_DATE('2024-10-24 13:46:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Oct 24, 2024 1:46:43 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002575 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Oct 24, 2024 1:46:48 PM AST
-- Changes done for eInvoice support
ALTER TABLE AD_Org ADD PUBLICKEY NVARCHAR2(256) DEFAULT NULL 
;

-- Oct 24, 2024 1:59:23 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002576,1001025,0,10,155,'PRIVATEKEY',TO_DATE('2024-10-24 13:59:23','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',256,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','PRIVATE KEY',0,TO_DATE('2024-10-24 13:59:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Oct 24, 2024 1:59:23 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002576 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Oct 24, 2024 1:59:25 PM AST
-- Changes done for eInvoice support
ALTER TABLE AD_Org ADD PRIVATEKEY NVARCHAR2(256) DEFAULT NULL 
;

-- Nov 18, 2024 3:48:23 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,1001037,0,'ZATCA_SECRET',TO_DATE('2024-11-18 15:48:22','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','ZATCA SECRET','ZATCA SECRET',TO_DATE('2024-11-18 15:48:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 18, 2024 3:48:23 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=1001037 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Nov 18, 2024 3:49:12 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002593,1001037,0,10,155,'ZATCA_SECRET',TO_DATE('2024-11-18 15:49:12','YYYY-MM-DD HH24:MI:SS'),100,'U',120,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','ZATCA SECRET',0,TO_DATE('2024-11-18 15:49:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Nov 18, 2024 3:49:12 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002593 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;



-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002577,1001026,0,10,155,'EGS_NAME',TO_DATE('2024-10-24 15:41:05','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',60,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','EGS NAME',0,TO_DATE('2024-10-24 15:41:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Oct 24, 2024 3:41:05 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002577 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Oct 24, 2024 3:41:07 PM AST
-- Changes done for eInvoice support
ALTER TABLE AD_Org ADD EGS_NAME NVARCHAR2(60) DEFAULT NULL 
;

-- Oct 24, 2024 3:58:34 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002578,1001027,0,10,155,'EGS_SERIALNO',TO_DATE('2024-10-24 15:58:34','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',120,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','EGS SERIAL NO',0,TO_DATE('2024-10-24 15:58:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Oct 24, 2024 3:58:34 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002578 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Oct 24, 2024 3:58:36 PM AST
-- Changes done for eInvoice support
ALTER TABLE AD_Org ADD EGS_SERIALNO NVARCHAR2(120) DEFAULT NULL 
;

-- Oct 24, 2024 4:00:40 PM AST
-- Changes done for eInvoice support
UPDATE AD_Element SET EntityType='BETAG',Updated=TO_DATE('2024-10-24 16:00:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1001021
;

-- Oct 24, 2024 4:02:23 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002579,1001021,0,10,155,'STREET',TO_DATE('2024-10-24 16:02:23','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',100,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','STREET',0,TO_DATE('2024-10-24 16:02:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Oct 24, 2024 4:02:23 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002579 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Oct 24, 2024 4:02:24 PM AST
-- Changes done for eInvoice support
ALTER TABLE AD_Org ADD STREET NVARCHAR2(100) DEFAULT NULL 
;

-- Oct 24, 2024 4:03:12 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002580,1001018,0,10,155,'BUILD_NO',TO_DATE('2024-10-24 16:03:11','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',10,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','BUILD_NO',0,TO_DATE('2024-10-24 16:03:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Oct 24, 2024 4:03:12 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002580 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Oct 24, 2024 4:03:14 PM AST
-- Changes done for eInvoice support
ALTER TABLE AD_Org ADD BUILD_NO NVARCHAR2(10) DEFAULT NULL 
;

-- Oct 24, 2024 4:04:26 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002581,1001019,0,10,155,'ADDL_NO',TO_DATE('2024-10-24 16:04:26','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',10,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','ADDL_NO',0,TO_DATE('2024-10-24 16:04:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Oct 24, 2024 4:04:26 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002581 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Oct 24, 2024 4:04:31 PM AST
-- Changes done for eInvoice support
ALTER TABLE AD_Org ADD ADDL_NO NVARCHAR2(10) DEFAULT NULL 
;

-- Oct 24, 2024 4:05:34 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002582,1001022,0,10,155,'POSTAL_ZIP',TO_DATE('2024-10-24 16:05:34','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',6,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','POSTAL_ZIP',0,TO_DATE('2024-10-24 16:05:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Oct 24, 2024 4:05:34 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002582 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Oct 24, 2024 4:05:36 PM AST
-- Changes done for eInvoice support
ALTER TABLE AD_Org ADD POSTAL_ZIP NVARCHAR2(6) DEFAULT NULL 
;

-- Oct 24, 2024 4:06:06 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002583,1001020,0,10,155,'DISTRICT',TO_DATE('2024-10-24 16:06:06','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',100,'Y','Y','N','N','N','N','N','N','N','N','N','Y','Y','DISTRICT',0,TO_DATE('2024-10-24 16:06:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Oct 24, 2024 4:06:06 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002583 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Oct 24, 2024 4:06:08 PM AST
-- Changes done for eInvoice support
ALTER TABLE AD_Org ADD DISTRICT NVARCHAR2(100) DEFAULT NULL 
;

-- Oct 24, 2024 4:06:46 PM AST
-- Changes done for eInvoice support
ALTER TABLE AD_Org MODIFY DISTRICT NVARCHAR2(100) DEFAULT NULL 
;

-- Oct 24, 2024 4:13:38 PM AST
-- Changes done for eInvoice support
ALTER TABLE AD_Org MODIFY DISTRICT NVARCHAR2(100) DEFAULT NULL 
;

-- Oct 24, 2024 4:27:30 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,1001028,0,'UUID',TO_DATE('2024-10-24 16:27:29','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','UUID','UUID',TO_DATE('2024-10-24 16:27:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 24, 2024 4:27:30 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=1001028 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Oct 24, 2024 4:30:17 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002584,1001028,0,10,318,'UUID',TO_DATE('2024-10-24 16:30:17','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',36,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','UUID',0,TO_DATE('2024-10-24 16:30:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Oct 24, 2024 4:30:17 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002584 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Oct 24, 2024 4:30:21 PM AST
-- Changes done for eInvoice support
ALTER TABLE C_Invoice ADD UUID NVARCHAR2(36) DEFAULT NULL 
;

-- Oct 24, 2024 4:33:03 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,1001029,0,'Invoice_IssueTime',TO_DATE('2024-10-24 16:33:03','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','Invoice Issue Time','Invoice Issue Time',TO_DATE('2024-10-24 16:33:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 24, 2024 4:33:03 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=1001029 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Oct 24, 2024 4:40:25 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002585,1001029,0,16,318,'Invoice_IssueTime',TO_DATE('2024-10-24 16:40:25','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',12,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','Invoice Issue Time',0,TO_DATE('2024-10-24 16:40:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Oct 24, 2024 4:40:25 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002585 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Oct 24, 2024 4:40:28 PM AST
-- Changes done for eInvoice support
ALTER TABLE C_Invoice ADD Invoice_IssueTime DATE DEFAULT NULL 
;

-- Oct 24, 2024 4:41:11 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,1001030,0,'IsSimplifiedInvoice',TO_DATE('2024-10-24 16:41:11','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','IsSimplifiedInvoice','IsSimplifiedInvoice',TO_DATE('2024-10-24 16:41:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 24, 2024 4:41:11 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=1001030 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Oct 24, 2024 4:41:26 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,1001031,0,'IsExportInvoice',TO_DATE('2024-10-24 16:41:26','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','IsExportInvoice','IsExportInvoice',TO_DATE('2024-10-24 16:41:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 24, 2024 4:41:26 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=1001031 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Oct 24, 2024 4:42:03 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,1001032,0,'PreviousInvoice_ID',TO_DATE('2024-10-24 16:42:03','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','Previous Invoice ID','Previous Invoice ID',TO_DATE('2024-10-24 16:42:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 24, 2024 4:42:03 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=1001032 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Oct 24, 2024 4:42:24 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,1001033,0,'InvoiceHash',TO_DATE('2024-10-24 16:42:24','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','Invoice Hash','Invoice Hash',TO_DATE('2024-10-24 16:42:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 24, 2024 4:42:24 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=1001033 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Oct 24, 2024 4:45:58 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,1001034,0,'DCNote_Reason',TO_DATE('2024-10-24 16:45:58','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','DCNote Reason','Credit/Debit Note Reason',TO_DATE('2024-10-24 16:45:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 24, 2024 4:45:58 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=1001034 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Oct 24, 2024 5:11:48 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,1000176,TO_DATE('2024-10-24 17:11:48','YYYY-MM-DD HH24:MI:SS'),100,'Credit/Debit Note Reason for Issuance','BETAG','Credit/Debit Note Reason for Issuance','Y','N','DCNote_Reason',TO_DATE('2024-10-24 17:11:48','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- Oct 24, 2024 5:11:49 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=1000176 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Oct 24, 2024 5:13:52 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000580,1000176,TO_DATE('2024-10-24 17:13:52','YYYY-MM-DD HH24:MI:SS'),100,'Cancellation or suspension of the supplies after its occurrence either wholly or partially ','BETAG','Y','Cancellation or suspension of the supplies',TO_DATE('2024-10-24 17:13:52','YYYY-MM-DD HH24:MI:SS'),100,'Cancellation')
;

-- Oct 24, 2024 5:13:52 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000580 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Oct 24, 2024 5:15:37 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000581,1000176,TO_DATE('2024-10-24 17:15:37','YYYY-MM-DD HH24:MI:SS'),100,'In case of essential change or amendment in the supply, which leads to the change of the VAT due','BETAG','Y','change or amendment in the supply',TO_DATE('2024-10-24 17:15:37','YYYY-MM-DD HH24:MI:SS'),100,'Item/Qty Change')
;

-- Oct 24, 2024 5:15:37 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000581 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Oct 24, 2024 5:16:16 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000582,1000176,TO_DATE('2024-10-24 17:16:16','YYYY-MM-DD HH24:MI:SS'),100,'Amendment of the supply value which is pre-agreed upon between the supplier and consumer','BETAG','Y','Amendment of the supply value',TO_DATE('2024-10-24 17:16:16','YYYY-MM-DD HH24:MI:SS'),100,'Rate Change')
;

-- Oct 24, 2024 5:16:16 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000582 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Oct 24, 2024 5:16:48 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000583,1000176,TO_DATE('2024-10-24 17:16:48','YYYY-MM-DD HH24:MI:SS'),100,'In case of goods or services refund','BETAG','Y','goods or services refund',TO_DATE('2024-10-24 17:16:48','YYYY-MM-DD HH24:MI:SS'),100,'Refund')
;

-- Oct 24, 2024 5:16:48 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000583 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Oct 24, 2024 5:17:23 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000584,1000176,TO_DATE('2024-10-24 17:17:23','YYYY-MM-DD HH24:MI:SS'),100,'In case of change in Seller''s or Buyer''s information','BETAG','Y','change in Seller''s or Buyer''s information',TO_DATE('2024-10-24 17:17:23','YYYY-MM-DD HH24:MI:SS'),100,'Party Change')
;

-- Oct 24, 2024 5:17:23 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000584 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Oct 24, 2024 5:17:58 PM AST
-- Changes done for eInvoice support
UPDATE AD_Ref_List SET Value='Value Change',Updated=TO_DATE('2024-10-24 17:17:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=1000582
;

-- Oct 24, 2024 5:18:19 PM AST
-- Changes done for eInvoice support
UPDATE AD_Ref_List SET Name='Goods or services refund',Updated=TO_DATE('2024-10-24 17:18:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=1000583
;

-- Oct 24, 2024 5:18:19 PM AST
-- Changes done for eInvoice support
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=1000583
;

-- Oct 24, 2024 5:22:50 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,1000177,TO_DATE('2024-10-24 17:22:50','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','N','VAT Exception Reason',TO_DATE('2024-10-24 17:22:50','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- Oct 24, 2024 5:22:51 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=1000177 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Oct 24, 2024 5:23:49 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000585,1000177,TO_DATE('2024-10-24 17:23:49','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','Financial services 	الخدمات المالية',TO_DATE('2024-10-24 17:23:49','YYYY-MM-DD HH24:MI:SS'),100,'VATEX-SA-29')
;

-- Oct 24, 2024 5:23:49 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000585 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Oct 24, 2024 5:24:10 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000586,1000177,TO_DATE('2024-10-24 17:24:10','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','Life insurance services	عقد تأمين على الحياة',TO_DATE('2024-10-24 17:24:10','YYYY-MM-DD HH24:MI:SS'),100,'VATEX-SA-29-7')
;

-- Oct 24, 2024 5:24:10 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000586 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Oct 24, 2024 5:25:03 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000587,1000177,TO_DATE('2024-10-24 17:25:03','YYYY-MM-DD HH24:MI:SS'),100,'التوريدات العقارية المعفاة من الضريبة','BETAG','Y','Real estate transactions',TO_DATE('2024-10-24 17:25:03','YYYY-MM-DD HH24:MI:SS'),100,'VATEX-SA-30')
;

-- Oct 24, 2024 5:25:03 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000587 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Oct 24, 2024 5:25:16 PM AST
-- Changes done for eInvoice support
UPDATE AD_Ref_List SET Description='عقد تأمين على الحياة', Name='Life insurance services',Updated=TO_DATE('2024-10-24 17:25:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=1000586
;

-- Oct 24, 2024 5:25:16 PM AST
-- Changes done for eInvoice support
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=1000586
;

-- Oct 24, 2024 5:25:37 PM AST
-- Changes done for eInvoice support
UPDATE AD_Ref_List SET Description='الخدمات المالية', Name='Financial services',Updated=TO_DATE('2024-10-24 17:25:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=1000585
;

-- Oct 24, 2024 5:25:37 PM AST
-- Changes done for eInvoice support
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=1000585
;

-- Oct 24, 2024 5:26:30 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000588,1000177,TO_DATE('2024-10-24 17:26:30','YYYY-MM-DD HH24:MI:SS'),100,'صادرات السلع من المملكة','BETAG','Y','Export of goods',TO_DATE('2024-10-24 17:26:30','YYYY-MM-DD HH24:MI:SS'),100,'VATEX-SA-32')
;

-- Oct 24, 2024 5:26:30 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000588 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Oct 24, 2024 5:27:02 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000589,1000177,TO_DATE('2024-10-24 17:27:02','YYYY-MM-DD HH24:MI:SS'),100,'صادرات الخدمات من المملكة','BETAG','Y','Export of services',TO_DATE('2024-10-24 17:27:02','YYYY-MM-DD HH24:MI:SS'),100,'VATEX-SA-33')
;

-- Oct 24, 2024 5:27:02 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000589 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Oct 24, 2024 5:27:32 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000590,1000177,TO_DATE('2024-10-24 17:27:32','YYYY-MM-DD HH24:MI:SS'),100,'النقل الدولي للسلع','BETAG','Y','The international transport of Goods',TO_DATE('2024-10-24 17:27:32','YYYY-MM-DD HH24:MI:SS'),100,'VATEX-SA-34-1')
;

-- Oct 24, 2024 5:27:32 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000590 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Oct 24, 2024 5:28:00 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000591,1000177,TO_DATE('2024-10-24 17:28:00','YYYY-MM-DD HH24:MI:SS'),100,'النقل الدولي للركاب','BETAG','Y','International transport of passengers',TO_DATE('2024-10-24 17:28:00','YYYY-MM-DD HH24:MI:SS'),100,'VATEX-SA-34-2')
;

-- Oct 24, 2024 5:28:00 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000591 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Oct 24, 2024 5:29:15 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000592,1000177,TO_DATE('2024-10-24 17:29:15','YYYY-MM-DD HH24:MI:SS'),100,'الخدمات المرتبطة مباشرة أو عرضيابتوريد النقل الدولي للركاب','BETAG','Y','Services connected international passenger transport',TO_DATE('2024-10-24 17:29:15','YYYY-MM-DD HH24:MI:SS'),100,'VATEX-SA-34-3')
;

-- Oct 24, 2024 5:29:15 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000592 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Oct 24, 2024 5:29:43 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000593,1000177,TO_DATE('2024-10-24 17:29:43','YYYY-MM-DD HH24:MI:SS'),100,'توريد وسائل النقل المؤهلة','BETAG','Y','Supply of a qualifying means of transport',TO_DATE('2024-10-24 17:29:43','YYYY-MM-DD HH24:MI:SS'),100,'VATEX-SA-34-4')
;

-- Oct 24, 2024 5:29:43 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000593 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Oct 24, 2024 5:30:38 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000594,1000177,TO_DATE('2024-10-24 17:30:38','YYYY-MM-DD HH24:MI:SS'),100,'الخدمات ذات الصلة بنقل السلع أوالركاب، وفقا ً للتعريف الوارد بالمادة الخامسة والعشرين من الالئحةالتنفيذية لنظام ضريبة القيامة','BETAG','Y','Any services relating to Goods or passenger transportation',TO_DATE('2024-10-24 17:30:38','YYYY-MM-DD HH24:MI:SS'),100,'VATEX-SA-34-5')
;

-- Oct 24, 2024 5:30:38 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000594 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Oct 24, 2024 5:51:04 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000595,1000177,TO_DATE('2024-10-24 17:51:04','YYYY-MM-DD HH24:MI:SS'),100,'ألدوية والمعدات الطبية','BETAG','Y','Medicines and medical equipment',TO_DATE('2024-10-24 17:51:04','YYYY-MM-DD HH24:MI:SS'),100,'VATEX-SA-35')
;

-- Oct 24, 2024 5:51:04 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000595 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Oct 24, 2024 5:51:48 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000596,1000177,TO_DATE('2024-10-24 17:51:48','YYYY-MM-DD HH24:MI:SS'),100,'المعادن المؤهلة','BETAG','Y','Qualifying metals',TO_DATE('2024-10-24 17:51:48','YYYY-MM-DD HH24:MI:SS'),100,'VATEX-SA-36')
;

-- Oct 24, 2024 5:51:49 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000596 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Oct 24, 2024 5:52:15 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000597,1000177,TO_DATE('2024-10-24 17:52:15','YYYY-MM-DD HH24:MI:SS'),100,'الخدمات التعليمية الخاصة للمواطنين','BETAG','Y','Private education to citizen',TO_DATE('2024-10-24 17:52:15','YYYY-MM-DD HH24:MI:SS'),100,'VATEX-SA-EDU')
;

-- Oct 24, 2024 5:52:15 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000597 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Oct 24, 2024 5:52:42 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000598,1000177,TO_DATE('2024-10-24 17:52:42','YYYY-MM-DD HH24:MI:SS'),100,'الخدمات الصحية الخاصة للمواطنين','BETAG','Y','Private healthcare to citizen',TO_DATE('2024-10-24 17:52:42','YYYY-MM-DD HH24:MI:SS'),100,'VATEX-SA-HEA')
;

-- Oct 24, 2024 5:52:42 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000598 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Oct 24, 2024 5:53:13 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000599,1000177,TO_DATE('2024-10-24 17:53:13','YYYY-MM-DD HH24:MI:SS'),100,'توريد السلع العسكرية المؤهلة','BETAG','Y','Supply of qualified military goods',TO_DATE('2024-10-24 17:53:13','YYYY-MM-DD HH24:MI:SS'),100,'VATEX-SA-MLTRY')
;

-- Oct 24, 2024 5:53:13 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000599 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Oct 24, 2024 5:53:59 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000600,1000177,TO_DATE('2024-10-24 17:53:59','YYYY-MM-DD HH24:MI:SS'),100,'التوريدات الغير خاضعة للضريبة','BETAG','Y','Not subject to VAT',TO_DATE('2024-10-24 17:53:59','YYYY-MM-DD HH24:MI:SS'),100,'VATEX-SA-OOS')
;

-- Oct 24, 2024 5:54:00 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000600 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Oct 24, 2024 7:35:29 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002586,1001030,0,20,318,'IsSimplifiedInvoice',TO_DATE('2024-10-24 19:35:28','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',1,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','IsSimplifiedInvoice',0,TO_DATE('2024-10-24 19:35:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Oct 24, 2024 7:35:29 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002586 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Oct 24, 2024 7:35:31 PM AST
-- Changes done for eInvoice support
ALTER TABLE C_Invoice ADD IsSimplifiedInvoice CHAR(1) DEFAULT NULL  CHECK (IsSimplifiedInvoice IN ('Y','N'))
;

-- Oct 24, 2024 7:41:33 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002587,1001031,0,20,318,'IsExportInvoice',TO_DATE('2024-10-24 19:41:33','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',1,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','IsExportInvoice',0,TO_DATE('2024-10-24 19:41:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Oct 24, 2024 7:41:33 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002587 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Oct 24, 2024 7:41:35 PM AST
-- Changes done for eInvoice support
ALTER TABLE C_Invoice ADD IsExportInvoice CHAR(1) DEFAULT NULL  CHECK (IsExportInvoice IN ('Y','N'))
;

-- Oct 24, 2024 7:53:06 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002588,1001032,0,13,318,'PreviousInvoice_ID',TO_DATE('2024-10-24 19:53:06','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',10,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','Previous Invoice ID',0,TO_DATE('2024-10-24 19:53:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Oct 24, 2024 7:53:06 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002588 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Oct 24, 2024 7:53:11 PM AST
-- Changes done for eInvoice support
ALTER TABLE C_Invoice ADD PreviousInvoice_ID NUMBER(10) DEFAULT NULL 
;

-- Oct 24, 2024 8:08:44 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version,Description) VALUES (0,1002589,1001033,0,10,318,'InvoiceHash',TO_DATE('2024-10-24 20:08:44','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',100,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','Invoice Hash',0,TO_DATE('2024-10-24 20:08:44','YYYY-MM-DD HH24:MI:SS'),100,0,'Base64 encoded Hash of Invoice XML')
;


-- Oct 24, 2024 8:08:44 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002589 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Oct 24, 2024 8:08:46 PM AST
-- Changes done for eInvoice support
ALTER TABLE C_Invoice ADD InvoiceHash NVARCHAR2(100) DEFAULT NULL 
;

UPDATE AD_Field SET Name='Invoice Hash', Description='Base64 encoded Hash of Invoice XML', Help=NULL WHERE AD_Column_ID=1002589 AND IsCentrallyMaintained='Y'
;

-- Oct 24, 2024 8:19:10 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002590,1001034,0,17,1000176,318,'DCNote_Reason',TO_DATE('2024-10-24 20:19:10','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',20,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','DCNote Reason',0,TO_DATE('2024-10-24 20:19:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Oct 24, 2024 8:19:10 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002590 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Oct 24, 2024 8:19:12 PM AST
-- Changes done for eInvoice support
ALTER TABLE C_Invoice ADD DCNote_Reason NVARCHAR2(20) DEFAULT NULL 
;

-- Oct 24, 2024 8:20:29 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002591,1000571,0,10,318,'VAT_NUMBER',TO_DATE('2024-10-24 20:20:29','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',15,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','VAT_NUMBER',0,TO_DATE('2024-10-24 20:20:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Oct 24, 2024 8:20:29 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002591 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Oct 24, 2024 8:20:32 PM AST
-- Changes done for eInvoice support
ALTER TABLE C_Invoice ADD VAT_NUMBER NVARCHAR2(15) DEFAULT NULL 
;

-- Oct 24, 2024 8:22:20 PM AST
-- Changes done for eInvoice support
UPDATE AD_Element SET PrintName='Credit/Debit Note Reason for Issuance',Updated=TO_DATE('2024-10-24 20:22:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1001034
;

-- Oct 24, 2024 8:22:20 PM AST
-- Changes done for eInvoice support
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=1001034
;

-- Oct 24, 2024 8:22:20 PM AST
-- Changes done for eInvoice support
UPDATE AD_PrintFormatItem pi SET PrintName='Credit/Debit Note Reason for Issuance', Name='DCNote Reason' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1001034)
;

-- Oct 24, 2024 8:26:06 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,1001035,0,'Vat_Exemption_Reason',TO_DATE('2024-10-24 20:26:06','YYYY-MM-DD HH24:MI:SS'),100,'Reason for VAT Exemption','BETAG','Y','Vat_Exemption_Reason','VAT Exemption Reason',TO_DATE('2024-10-24 20:26:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 24, 2024 8:26:06 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=1001035 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Oct 24, 2024 8:45:40 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002592,1001035,0,17,259,'Vat_Exemption_Reason',TO_DATE('2024-10-24 20:45:40','YYYY-MM-DD HH24:MI:SS'),100,'Reason for VAT Exemption','BETAG',20,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','Vat_Exemption_Reason',0,TO_DATE('2024-10-24 20:45:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Oct 24, 2024 8:45:40 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002592 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Oct 24, 2024 8:45:42 PM AST
-- Changes done for eInvoice support
ALTER TABLE C_Order ADD Vat_Exemption_Reason NVARCHAR2(20) DEFAULT NULL 
;

-- Oct 25, 2024 5:05:05 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002587,1006726,0,263,TO_DATE('2024-10-25 17:05:05','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','IsExportInvoice',350,0,TO_DATE('2024-10-25 17:05:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 5:05:05 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006726 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 5:07:02 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002586,1006727,0,263,TO_DATE('2024-10-25 17:07:02','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','IsSimplifiedInvoice',360,0,TO_DATE('2024-10-25 17:07:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 5:07:02 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006727 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 5:08:20 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002585,1006728,0,263,TO_DATE('2024-10-25 17:08:20','YYYY-MM-DD HH24:MI:SS'),100,16,'BETAG','Y','Y','Y','N','N','N','N','N','Invoice Issue Time',370,0,TO_DATE('2024-10-25 17:08:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 5:08:20 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006728 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 5:11:37 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002585,1006729,0,1000025,TO_DATE('2024-10-25 17:11:37','YYYY-MM-DD HH24:MI:SS'),100,16,'BETAG','Y','Y','Y','N','N','N','N','N','Invoice Issue Time',480,0,TO_DATE('2024-10-25 17:11:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 5:11:37 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006729 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 5:12:10 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002586,1006730,0,1000025,TO_DATE('2024-10-25 17:12:09','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','IsSimplifiedInvoice',490,0,TO_DATE('2024-10-25 17:12:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 5:12:10 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006730 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 5:12:28 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002587,1006731,0,1000025,TO_DATE('2024-10-25 17:12:28','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','IsExportInvoice',500,0,TO_DATE('2024-10-25 17:12:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 5:12:28 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006731 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 5:13:41 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002590,1006732,0,1000025,TO_DATE('2024-10-25 17:13:41','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','DCNote Reason',510,0,TO_DATE('2024-10-25 17:13:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 5:13:41 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006732 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 5:14:13 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002591,1006733,0,1000025,TO_DATE('2024-10-25 17:14:13','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','VAT_NUMBER',520,0,TO_DATE('2024-10-25 17:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 5:14:13 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006733 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 5:16:01 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002585,1006734,0,1000008,TO_DATE('2024-10-25 17:16:01','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','Invoice Issue Time',470,0,TO_DATE('2024-10-25 17:16:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 5:16:01 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006734 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 5:16:14 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002586,1006735,0,1000008,TO_DATE('2024-10-25 17:16:14','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','IsSimplifiedInvoice',480,0,TO_DATE('2024-10-25 17:16:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 5:16:14 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006735 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 5:16:26 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002587,1006736,0,1000008,TO_DATE('2024-10-25 17:16:26','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','IsExportInvoice',490,0,TO_DATE('2024-10-25 17:16:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 5:16:26 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006736 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 5:16:57 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002590,1006737,0,1000008,TO_DATE('2024-10-25 17:16:56','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','DCNote Reason',500,0,TO_DATE('2024-10-25 17:16:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 5:16:57 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006737 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 5:18:03 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002591,1006738,0,263,TO_DATE('2024-10-25 17:18:03','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','VAT_NUMBER',380,0,TO_DATE('2024-10-25 17:18:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 5:18:03 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006738 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 5:20:26 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002591,1006739,0,1000008,TO_DATE('2024-10-25 17:20:26','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','VAT_NUMBER',510,0,TO_DATE('2024-10-25 17:20:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 5:20:26 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006739 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 5:23:43 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002571,1006740,0,145,TO_DATE('2024-10-25 17:23:43','YYYY-MM-DD HH24:MI:SS'),100,'Additional Name',60,'BETAG','Y','Y','Y','N','N','N','N','N','Name 2',290,0,TO_DATE('2024-10-25 17:23:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 5:23:43 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006740 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 5:26:37 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002592,1006741,0,186,TO_DATE('2024-10-25 17:26:37','YYYY-MM-DD HH24:MI:SS'),100,'Reason for VAT Exemption',0,'BETAG','Y','Y','Y','N','N','N','N','N','Vat_Exemption_Reason',360,0,TO_DATE('2024-10-25 17:26:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 5:26:37 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006741 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 5:27:56 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002572,1006742,0,143,TO_DATE('2024-10-25 17:27:56','YYYY-MM-DD HH24:MI:SS'),100,'Additional Name',0,'BETAG','Y','Y','Y','N','N','N','N','N','Name 2',80,0,TO_DATE('2024-10-25 17:27:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 5:27:56 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006742 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 7:34:49 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002573,1006743,0,143,TO_DATE('2024-10-25 19:34:48','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','TRDLICENSENO',90,0,TO_DATE('2024-10-25 19:34:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 7:34:49 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006743 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 7:36:08 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002577,1006744,0,143,TO_DATE('2024-10-25 19:36:08','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','EGS NAME',100,0,TO_DATE('2024-10-25 19:36:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 7:36:08 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006744 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 7:36:29 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002578,1006745,0,143,TO_DATE('2024-10-25 19:36:29','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','EGS SERIAL NO',110,0,TO_DATE('2024-10-25 19:36:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 7:36:29 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006745 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 7:36:48 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002579,1006746,0,143,TO_DATE('2024-10-25 19:36:48','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','STREET',120,0,TO_DATE('2024-10-25 19:36:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 7:36:48 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006746 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 7:37:17 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002580,1006747,0,143,TO_DATE('2024-10-25 19:37:17','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','BUILD_NO',130,0,TO_DATE('2024-10-25 19:37:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 7:37:17 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006747 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 7:37:27 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002581,1006748,0,143,TO_DATE('2024-10-25 19:37:27','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','ADDL_NO',140,0,TO_DATE('2024-10-25 19:37:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 7:37:27 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006748 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 7:37:42 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002582,1006749,0,143,TO_DATE('2024-10-25 19:37:42','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','POSTAL_ZIP',150,0,TO_DATE('2024-10-25 19:37:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 7:37:42 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006749 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Oct 25, 2024 7:37:55 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002583,1006750,0,143,TO_DATE('2024-10-25 19:37:55','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','DISTRICT',160,0,TO_DATE('2024-10-25 19:37:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 25, 2024 7:37:55 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006750 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;



-- Nov 21, 2024 8:47:42 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,1000074,'C_Invoice.C_Project_ID=@C_Project_ID@ AND C_Invoice.DocStatus IN (''CO'', ''CL'')',TO_DATE('2024-11-21 20:47:41','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','C_Invoice of Project','S',TO_DATE('2024-11-21 20:47:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 21, 2024 8:49:00 PM AST
-- Changes done for eInvoice support
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=336, AD_Val_Rule_ID=1000074,Updated=TO_DATE('2024-11-21 20:49:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=10788
;

-- Nov 21, 2024 8:49:29 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,10788,1006751,0,1000025,TO_DATE('2024-11-21 20:49:29','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','Referenced Invoice',530,0,TO_DATE('2024-11-21 20:49:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 21, 2024 8:51:35 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,10788,1006752,0,1000025,TO_DATE('2024-11-21 20:51:35','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','Referenced Invoice',530,0,TO_DATE('2024-11-21 20:51:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 21, 2024 8:51:59 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,10788,1006753,0,1000025,TO_DATE('2024-11-21 20:51:59','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','N','N','Referenced Invoice',530,0,TO_DATE('2024-11-21 20:51:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 21, 2024 8:59:45 PM AST
-- Changes done for eInvoice support
UPDATE AD_Field SET SeqNo=490,IsDisplayed='Y' WHERE AD_Field_ID=1006729
;

-- Nov 21, 2024 8:59:45 PM AST
-- Changes done for eInvoice support
UPDATE AD_Field SET SeqNo=500,IsDisplayed='Y' WHERE AD_Field_ID=1006730
;

-- Nov 21, 2024 8:59:45 PM AST
-- Changes done for eInvoice support
UPDATE AD_Field SET SeqNo=510,IsDisplayed='Y' WHERE AD_Field_ID=1006731
;

-- Nov 21, 2024 8:59:45 PM AST
-- Changes done for eInvoice support
UPDATE AD_Field SET SeqNo=520,IsDisplayed='Y' WHERE AD_Field_ID=1006732
;

-- Nov 21, 2024 8:59:45 PM AST
-- Changes done for eInvoice support
UPDATE AD_Field SET SeqNo=530,IsDisplayed='Y' WHERE AD_Field_ID=1006733
;

-- Nov 21, 2024 9:01:19 PM AST
-- Changes done for eInvoice support
UPDATE AD_Field SET SeqNo=480,IsDisplayed='Y' WHERE AD_Field_ID=1006734
;

-- Nov 21, 2024 9:01:19 PM AST
-- Changes done for eInvoice support
UPDATE AD_Field SET SeqNo=490,IsDisplayed='Y' WHERE AD_Field_ID=1006735
;

-- Nov 21, 2024 9:01:19 PM AST
-- Changes done for eInvoice support
UPDATE AD_Field SET SeqNo=500,IsDisplayed='Y' WHERE AD_Field_ID=1006736
;

-- Nov 21, 2024 9:01:19 PM AST
-- Changes done for eInvoice support
UPDATE AD_Field SET SeqNo=510,IsDisplayed='Y' WHERE AD_Field_ID=1006737
;

-- Nov 21, 2024 9:01:19 PM AST
-- Changes done for eInvoice support
UPDATE AD_Field SET SeqNo=520,IsDisplayed='Y' WHERE AD_Field_ID=1006739
;

-- Nov 21, 2024 9:05:17 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,1001040,0,'EInvoiceStatus',TO_DATE('2024-11-21 21:05:17','YYYY-MM-DD HH24:MI:SS'),100,'EInvoice Status filing status','BETAG','Y','EInvoice Status','EInvoice Status',TO_DATE('2024-11-21 21:05:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 21, 2024 9:05:18 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=1001040 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Nov 21, 2024 9:14:19 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,1000178,TO_DATE('2024-11-21 21:14:19','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','N','EInvoice Status',TO_DATE('2024-11-21 21:14:19','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- Nov 21, 2024 9:14:19 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=1000178 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Nov 21, 2024 9:14:44 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000601,1000178,TO_DATE('2024-11-21 21:14:44','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','CLEARED',TO_DATE('2024-11-21 21:14:44','YYYY-MM-DD HH24:MI:SS'),100,'CLEARED')
;

-- Nov 21, 2024 9:14:44 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000601 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Nov 21, 2024 9:15:25 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000602,1000178,TO_DATE('2024-11-21 21:15:25','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','NOT CLEARED',TO_DATE('2024-11-21 21:15:25','YYYY-MM-DD HH24:MI:SS'),100,'NOT CLEARED')
;

-- Nov 21, 2024 9:15:25 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000602 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Nov 21, 2024 9:17:39 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000603,1000178,TO_DATE('2024-11-21 21:17:38','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','REPORTED',TO_DATE('2024-11-21 21:17:38','YYYY-MM-DD HH24:MI:SS'),100,'REPORTED')
;

-- Nov 21, 2024 9:17:39 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000603 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Nov 21, 2024 9:22:07 PM AST
-- Changes done for eInvoice support
UPDATE AD_Ref_List SET Description='CLEARED or REPORTED Successfully', Name='SUCCESS', Value='SUCCESS',Updated=TO_DATE('2024-11-21 21:22:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=1000601
;

-- Nov 21, 2024 9:22:07 PM AST
-- Changes done for eInvoice support
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=1000601
;

-- Nov 21, 2024 9:22:58 PM AST
-- Changes done for eInvoice support
UPDATE AD_Ref_List SET Description='CLEARING or REPORTING failed', Name='ERROR', Value='ERROR',Updated=TO_DATE('2024-11-21 21:22:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=1000602
;

-- Nov 21, 2024 9:22:58 PM AST
-- Changes done for eInvoice support
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=1000602
;

-- Nov 21, 2024 9:23:20 PM AST
-- Changes done for eInvoice support
UPDATE AD_Ref_List SET Description='CLEARING or REPORTING failed. NEED TO RE-SUBMIT',Updated=TO_DATE('2024-11-21 21:23:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=1000602
;

-- Nov 21, 2024 9:23:20 PM AST
-- Changes done for eInvoice support
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=1000602
;

-- Nov 21, 2024 9:26:59 PM AST
-- Changes done for eInvoice support
UPDATE AD_Ref_List SET Description='ACCEPTED WITH WARNING', Name='WARNING', Value='WARNING',Updated=TO_DATE('2024-11-21 21:26:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=1000603
;

-- Nov 21, 2024 9:26:59 PM AST
-- Changes done for eInvoice support
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=1000603
;

-- Nov 21, 2024 9:30:25 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000604,1000178,TO_DATE('2024-11-21 21:30:24','YYYY-MM-DD HH24:MI:SS'),100,'EInvoice not submitted. ','BETAG','Y','PENDING',TO_DATE('2024-11-21 21:30:24','YYYY-MM-DD HH24:MI:SS'),100,'PENDING')
;

-- Nov 21, 2024 9:30:25 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=1000604 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Nov 21, 2024 9:42:01 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002594,1001040,0,17,1000178,318,'EInvoiceStatus',TO_DATE('2024-11-21 21:42:01','YYYY-MM-DD HH24:MI:SS'),100,'EInvoice Status filing status','BETAG',15,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','EInvoice Status','1=1',0,TO_DATE('2024-11-21 21:42:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Nov 21, 2024 9:42:01 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002594 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Nov 21, 2024 9:42:36 PM AST
-- Changes done for eInvoice support
UPDATE AD_Column SET DefaultValue='PENDING',Updated=TO_DATE('2024-11-21 21:42:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1002594
;

-- Nov 21, 2024 9:43:36 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,1001041,0,'EInvoiceMessage',TO_DATE('2024-11-21 21:43:35','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','EInvoice Message','EInvoice Message',TO_DATE('2024-11-21 21:43:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 21, 2024 9:43:36 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=1001041 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Nov 21, 2024 9:44:14 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002595,1001041,0,14,318,'EInvoiceMessage',TO_DATE('2024-11-21 21:44:14','YYYY-MM-DD HH24:MI:SS'),100,'U',1000,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','EInvoice Message',0,TO_DATE('2024-11-21 21:44:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Nov 21, 2024 9:44:14 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002595 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Nov 21, 2024 9:44:24 PM AST
-- Changes done for eInvoice support
UPDATE AD_Column SET EntityType='BETAG',Updated=TO_DATE('2024-11-21 21:44:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1002595
;

-- Nov 21, 2024 9:44:56 PM AST
-- Changes done for eInvoice support
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_DATE('2024-11-21 21:44:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1002595
;

-- Nov 21, 2024 9:45:12 PM AST
-- Changes done for eInvoice support
ALTER TABLE C_Invoice ADD EInvoiceMessage NVARCHAR2(1000) DEFAULT NULL 
;

-- Nov 21, 2024 9:45:26 PM AST
-- Changes done for eInvoice support
ALTER TABLE C_Invoice ADD EInvoiceStatus NVARCHAR2(15) DEFAULT 'PENDING'
;

-- Nov 21, 2024 9:49:42 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,1001042,0,'ICV',TO_DATE('2024-11-21 21:49:42','YYYY-MM-DD HH24:MI:SS'),100,'Common Serial Number of the Documents (Invoice, Credit Note, Debit Note) for both Tax Invoice and Simplified Invoices combined','BETAG','Y','Invoice Counter Value','Invoice Counter Value',TO_DATE('2024-11-21 21:49:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 21, 2024 9:49:42 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=1001042 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Nov 21, 2024 9:51:34 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002596,1001042,0,11,318,'ICV',TO_DATE('2024-11-21 21:51:34','YYYY-MM-DD HH24:MI:SS'),100,'Common Serial Number of the Documents (Invoice, Credit Note, Debit Note) for both Tax Invoice and Simplified Invoices combined','BETAG',10,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','Invoice Counter Value','1=1',0,TO_DATE('2024-11-21 21:51:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Nov 21, 2024 9:51:34 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002596 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Nov 21, 2024 9:56:43 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002596,1006754,0,1000008,TO_DATE('2024-11-21 21:56:43','YYYY-MM-DD HH24:MI:SS'),100,'Common Serial Number of the Documents (Invoice, Credit Note, Debit Note) for both Tax Invoice and Simplified Invoices combined',10,'BETAG','Y','Y','Y','N','N','N','Y','N','Invoice Counter Value',35,0,TO_DATE('2024-11-21 21:56:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 21, 2024 9:56:43 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006754 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;



-- Nov 25, 2024 6:37:11 AM AST
-- Changes done for eInvoice support
ALTER TABLE C_Invoice ADD ICV NUMBER(10) DEFAULT NULL 
;

-- Nov 25, 2024 6:44:30 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002596,1006755,0,1000025,TO_DATE('2024-11-25 06:44:30','YYYY-MM-DD HH24:MI:SS'),100,'Common Serial Number of the Documents (Invoice, Credit Note, Debit Note) for both Tax Invoice and Simplified Invoices combined',0,'U','Y','Y','Y','N','N','N','N','N','Invoice Counter Value',540,0,TO_DATE('2024-11-25 06:44:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 25, 2024 6:44:30 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006755 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Nov 25, 2024 6:45:02 AM AST
-- Changes done for eInvoice support
UPDATE AD_Field SET EntityType='BETAG', IsReadOnly='Y',Updated=TO_DATE('2024-11-25 06:45:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=1006755
;

-- Nov 25, 2024 6:46:20 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002596,1006756,0,263,TO_DATE('2024-11-25 06:46:20','YYYY-MM-DD HH24:MI:SS'),100,'Common Serial Number of the Documents (Invoice, Credit Note, Debit Note) for both Tax Invoice and Simplified Invoices combined',0,'BETAG','Y','Y','Y','N','N','N','Y','N','Invoice Counter Value',390,0,TO_DATE('2024-11-25 06:46:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 25, 2024 6:46:20 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006756 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;



-- Nov 25, 2024 10:05:56 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002594,1006757,0,263,TO_DATE('2024-11-25 10:05:56','YYYY-MM-DD HH24:MI:SS'),100,'EInvoice Status filing status',0,'BETAG','Y','Y','Y','N','N','N','Y','N','EInvoice Status',400,0,TO_DATE('2024-11-25 10:05:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 25, 2024 10:05:56 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006757 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Nov 25, 2024 10:06:33 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002595,1006758,0,263,TO_DATE('2024-11-25 10:06:33','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','Y','N','EInvoice Message',410,0,TO_DATE('2024-11-25 10:06:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 25, 2024 10:06:33 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006758 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Nov 25, 2024 10:09:59 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002594,1006759,0,1000025,TO_DATE('2024-11-25 10:09:59','YYYY-MM-DD HH24:MI:SS'),100,'EInvoice Status filing status',0,'BETAG','Y','Y','Y','N','N','N','Y','N','EInvoice Status',550,0,TO_DATE('2024-11-25 10:09:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 25, 2024 10:09:59 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006759 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Nov 25, 2024 10:10:22 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002595,1006760,0,1000025,TO_DATE('2024-11-25 10:10:22','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','Y','N','EInvoice Message',560,0,TO_DATE('2024-11-25 10:10:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 25, 2024 10:10:22 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006760 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Nov 25, 2024 10:11:20 AM AST
-- Changes done for eInvoice support
UPDATE AD_Field SET SeqNo=530,Updated=TO_DATE('2024-11-25 10:11:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=1006754
;

-- Nov 25, 2024 10:11:46 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002594,1006761,0,1000008,TO_DATE('2024-11-25 10:11:46','YYYY-MM-DD HH24:MI:SS'),100,'EInvoice Status filing status',0,'BETAG','Y','Y','Y','N','N','N','Y','N','EInvoice Status',540,0,TO_DATE('2024-11-25 10:11:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 25, 2024 10:11:46 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006761 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Nov 25, 2024 10:12:50 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002595,1006762,0,1000008,TO_DATE('2024-11-25 10:12:50','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','Y','N','EInvoice Message',550,0,TO_DATE('2024-11-25 10:12:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 25, 2024 10:12:50 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006762 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Nov 25, 2024 10:15:55 AM AST
-- Changes done for eInvoice support
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,1000292,TO_DATE('2024-11-25 10:15:55','YYYY-MM-DD HH24:MI:SS'),100,1,100,'Sequence for Invoice Counter value (ICV)',1,'Y','N','Y','N','EInvoice_ICV','N',1000000,TO_DATE('2024-11-25 10:15:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 25, 2024 10:16:40 AM AST
-- Changes done for eInvoice support
UPDATE AD_Sequence SET Description='Sequence for Invoice Counter value (ICV). *** DO NOT Change Name***', Name='EINVOICE_ICV_SEQ',Updated=TO_DATE('2024-11-25 10:16:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=1000292
;


-- Nov 25, 2024 6:29:42 PM AST
-- Changes done for eInvoice support
UPDATE AD_Column SET EntityType='BETAG', FieldLength=1000,Updated=TO_DATE('2024-11-25 18:29:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1002505
;

-- Nov 25, 2024 6:29:49 PM AST
-- Changes done for eInvoice support
ALTER TABLE C_Invoice MODIFY BASE64STR NVARCHAR2(1000) DEFAULT NULL

