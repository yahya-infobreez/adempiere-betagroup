-- Dec 6, 2024 3:22:28 PM IST
-- Changes done for eInvoice support
UPDATE AD_Element SET ColumnName='ZATCA_STATUS', Name='ZATCA_STATUS', PrintName='ZATCA_STATUS',Updated=TO_DATE('2024-12-06 15:22:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1001024
;

-- Dec 6, 2024 3:22:28 PM IST
-- Changes done for eInvoice support
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=1001024
;

-- Dec 6, 2024 3:22:28 PM IST
-- Changes done for eInvoice support
UPDATE AD_Column SET ColumnName='ZATCA_STATUS', Name='ZATCA_STATUS', Description=NULL, Help=NULL WHERE AD_Element_ID=1001024
;

-- Dec 6, 2024 3:22:28 PM IST
-- Changes done for eInvoice support
UPDATE AD_Process_Para SET ColumnName='ZATCA_STATUS', Name='ZATCA_STATUS', Description=NULL, Help=NULL, AD_Element_ID=1001024 WHERE UPPER(ColumnName)='ZATCA_STATUS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- Dec 6, 2024 3:22:28 PM IST
-- Changes done for eInvoice support
UPDATE AD_Process_Para SET ColumnName='ZATCA_STATUS', Name='ZATCA_STATUS', Description=NULL, Help=NULL WHERE AD_Element_ID=1001024 AND IsCentrallyMaintained='Y'
;

-- Dec 6, 2024 3:22:28 PM IST
-- Changes done for eInvoice support
UPDATE AD_Field SET Name='ZATCA_STATUS', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1001024) AND IsCentrallyMaintained='Y'
;

-- Dec 6, 2024 3:22:29 PM IST
-- Changes done for eInvoice support
UPDATE AD_PrintFormatItem pi SET PrintName='ZATCA_STATUS', Name='ZATCA_STATUS' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1001024)
;

-- Dec 6, 2024 3:23:07 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,1001043,0,'ZATCA_REQUESTID',TO_DATE('2024-12-06 15:23:07','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','ZATCA REQUEST ID','ZATCA REQUEST ID',TO_DATE('2024-12-06 15:23:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Dec 6, 2024 3:23:07 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=1001043 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Dec 6, 2024 3:23:53 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,1001044,0,'ZATCA_ISPRODUCTION',TO_DATE('2024-12-06 15:23:53','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','ZATCA IS PRODUCTION','ZATCA IS PRODUCTION',TO_DATE('2024-12-06 15:23:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Dec 6, 2024 3:23:53 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=1001044 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Dec 6, 2024 3:26:28 PM IST
-- Changes done for eInvoice support
UPDATE AD_Column SET FieldLength=32,Updated=TO_DATE('2024-12-06 15:26:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1002575
;

-- Dec 6, 2024 3:26:43 PM IST
-- Changes done for eInvoice support
ALTER TABLE AD_Org ADD ZATCA_STATUS NVARCHAR2(32) DEFAULT NULL 
;

-- Dec 6, 2024 3:28:19 PM IST
-- Changes done for eInvoice support
ALTER TABLE AD_Org MODIFY ZATCA_STATUS NVARCHAR2(32) DEFAULT NULL 
;

-- Dec 6, 2024 3:28:37 PM IST
-- Changes done for eInvoice support
UPDATE AD_Column SET EntityType='BETAG',Updated=TO_DATE('2024-12-06 15:28:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1002593
;

-- Dec 6, 2024 3:31:16 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002597,1001043,0,10,155,'ZATCA_REQUESTID',TO_DATE('2024-12-06 15:31:16','YYYY-MM-DD HH24:MI:SS'),100,'U',20,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','ZATCA REQUEST ID','1=1',0,TO_DATE('2024-12-06 15:31:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Dec 6, 2024 3:31:16 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002597 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Dec 6, 2024 3:31:39 PM IST
-- Changes done for eInvoice support
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_DATE('2024-12-06 15:31:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1002575
;

-- Dec 6, 2024 3:31:45 PM IST
-- Changes done for eInvoice support
UPDATE AD_Column SET EntityType='BETAG',Updated=TO_DATE('2024-12-06 15:31:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1002597
;

-- Dec 6, 2024 3:32:40 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002598,1001044,0,20,155,'ZATCA_ISPRODUCTION',TO_DATE('2024-12-06 15:32:40','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',1,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','ZATCA IS PRODUCTION','1=1',0,TO_DATE('2024-12-06 15:32:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Dec 6, 2024 3:32:40 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002598 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Dec 6, 2024 3:32:43 PM IST
-- Changes done for eInvoice support
ALTER TABLE AD_Org ADD ZATCA_ISPRODUCTION CHAR(1) DEFAULT NULL  CHECK (ZATCA_ISPRODUCTION IN ('Y','N'))
;

-- Dec 6, 2024 3:32:51 PM IST
-- Changes done for eInvoice support
ALTER TABLE AD_Org ADD ZATCA_REQUESTID NVARCHAR2(20) DEFAULT NULL 
;

-- Dec 6, 2024 3:34:06 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002575,1006763,0,143,TO_DATE('2024-12-06 15:34:06','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','Y','N','ZATCA_STATUS',170,0,TO_DATE('2024-12-06 15:34:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Dec 6, 2024 3:34:06 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006763 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Dec 6, 2024 3:34:18 PM IST
-- Changes done for eInvoice support
UPDATE AD_Field SET Name='ZATCA STATUS',Updated=TO_DATE('2024-12-06 15:34:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=1006763
;

-- Dec 6, 2024 3:34:18 PM IST
-- Changes done for eInvoice support
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=1006763
;

-- Dec 6, 2024 3:35:22 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002598,1006764,0,143,TO_DATE('2024-12-06 15:35:22','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','Y','N','ZATCA IS PRODUCTION',180,0,TO_DATE('2024-12-06 15:35:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Dec 6, 2024 3:35:22 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006764 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Dec 6, 2024 3:37:05 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,1001045,0,'ZATCA_EXPIRY',TO_DATE('2024-12-06 15:37:05','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','ZATCA REGISTRATION EXPIRY','ZATCA REGISTRATION EXPIRY',TO_DATE('2024-12-06 15:37:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Dec 6, 2024 3:37:05 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=1001045 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Dec 6, 2024 3:38:24 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy,Version) VALUES (0,1002599,1001045,0,24,155,'ZATCA_EXPIRY',TO_DATE('2024-12-06 15:38:24','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',20,'Y','Y','N','N','N','N','N','N','N','N','N','N','Y','ZATCA REGISTRATION EXPIRY','1=1',0,TO_DATE('2024-12-06 15:38:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Dec 6, 2024 3:38:24 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=1002599 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Dec 6, 2024 3:38:34 PM IST
-- Changes done for eInvoice support
ALTER TABLE AD_Org ADD ZATCA_EXPIRY DATE DEFAULT NULL 
;

-- Dec 6, 2024 3:40:44 PM IST
-- Changes done for eInvoice support
UPDATE AD_Column SET AD_Reference_ID=16,Updated=TO_DATE('2024-12-06 15:40:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1002599
;

-- Dec 6, 2024 3:40:46 PM IST
-- Changes done for eInvoice support
ALTER TABLE AD_Org MODIFY ZATCA_EXPIRY DATE DEFAULT NULL 
;

-- Dec 6, 2024 3:43:12 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,1002599,1006765,0,143,TO_DATE('2024-12-06 15:43:12','YYYY-MM-DD HH24:MI:SS'),100,0,'BETAG','Y','Y','Y','N','N','N','Y','N','ZATCA REGISTRATION EXPIRY',190,0,TO_DATE('2024-12-06 15:43:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Dec 6, 2024 3:43:12 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=1006765 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Dec 6, 2024 4:11:33 PM IST
-- Changes done for eInvoice support
UPDATE AD_Process SET Classname='com.betagroup.einvoice.process.Process_EInvoiceComplianceRegistration', Name='Beta EInvoice Registration - Compliance',Updated=TO_DATE('2024-12-06 16:11:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=1000255
;

-- Dec 6, 2024 4:11:33 PM IST
-- Changes done for eInvoice support
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=1000255
;

-- Dec 6, 2024 4:11:33 PM IST
-- Changes done for eInvoice support
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Beta EInvoice Registration - Compliance',Updated=TO_DATE('2024-12-06 16:11:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=1000219
;

-- Dec 6, 2024 4:11:33 PM IST
-- Changes done for eInvoice support
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=1000219
;

-- Dec 6, 2024 4:28:49 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,1000255,1000718,10,'OTP',TO_DATE('2024-12-06 16:28:49','YYYY-MM-DD HH24:MI:SS'),100,'Please provide one of the OTPs from the list provided by ZATCA','BETAG',10,'Provide one of the OTPs from the list provided by ZATCA','Y','Y','Y','N','OTP',60,TO_DATE('2024-12-06 16:28:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Dec 6, 2024 4:28:49 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=1000718 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Dec 6, 2024 4:29:53 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AccessLevel,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsReport,IsServerProcess,Name,ShowHelp,Statistic_Count,Statistic_Seconds,Updated,UpdatedBy,Value) VALUES (0,0,1000256,'3','com.betagroup.einvoice.process.Process_EInvoiceRenewRegistration','N',TO_DATE('2024-12-06 16:29:53','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','N','N','N','N','Beta EInvoice Registration - Renewal','Y',7,628,TO_DATE('2024-12-06 16:29:53','YYYY-MM-DD HH24:MI:SS'),100,'Beta EInvoice Registration')
;

-- Dec 6, 2024 4:29:53 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=1000256 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Dec 6, 2024 4:31:57 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,1000256,1000719,19,130,'AD_Org_ID',TO_DATE('2024-12-06 16:31:56','YYYY-MM-DD HH24:MI:SS'),100,'@#AD_Org_ID@','BETAG',0,'Y','Y','N','N','AD_Org_ID',10,TO_DATE('2024-12-06 16:31:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Dec 6, 2024 4:31:57 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=1000719 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Dec 6, 2024 4:32:26 PM IST
-- Changes done for eInvoice support
UPDATE AD_Process_Para SET Name='CSRFile',Updated=TO_DATE('2024-12-06 16:32:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=1000716
;

-- Dec 6, 2024 4:32:26 PM IST
-- Changes done for eInvoice support
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=1000716
;

-- Dec 6, 2024 4:33:21 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1001038,0,1000256,1000720,39,'CSRFile',TO_DATE('2024-12-06 16:33:21','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',256,'Y','Y','Y','N','CSRFile',20,TO_DATE('2024-12-06 16:33:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Dec 6, 2024 4:33:21 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=1000720 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Dec 6, 2024 4:33:36 PM IST
-- Changes done for eInvoice support
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_DATE('2024-12-06 16:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=1000719
;

-- Dec 6, 2024 4:34:24 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,1000256,1000721,10,'OTP',TO_DATE('2024-12-06 16:34:23','YYYY-MM-DD HH24:MI:SS'),100,'BETAG',10,'Y','Y','Y','N','OTP',30,TO_DATE('2024-12-06 16:34:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Dec 6, 2024 4:34:24 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=1000721 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Dec 6, 2024 4:35:08 PM IST
-- Changes done for eInvoice support
UPDATE AD_Process SET Value='Beta EInvoice Registration - Renewal',Updated=TO_DATE('2024-12-06 16:35:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=1000256
;

-- Dec 6, 2024 4:35:24 PM IST
-- Changes done for eInvoice support
UPDATE AD_Process SET Value='Beta EInvoice Registration - Compliance',Updated=TO_DATE('2024-12-06 16:35:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=1000255
;

-- Dec 6, 2024 4:36:28 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AccessLevel,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsReport,IsServerProcess,Name,ShowHelp,Statistic_Count,Statistic_Seconds,Updated,UpdatedBy,Value) VALUES (0,0,1000257,'3','com.betagroup.einvoice.process.Process_EInvoiceProductionRegistration','N',TO_DATE('2024-12-06 16:36:28','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','N','N','N','N','Beta EInvoice Registration - Production','Y',7,628,TO_DATE('2024-12-06 16:36:28','YYYY-MM-DD HH24:MI:SS'),100,'Beta EInvoice Registration - Production')
;

-- Dec 6, 2024 4:36:28 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=1000257 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Dec 6, 2024 4:37:40 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,1000257,1000722,19,130,'AD_Org_ID',TO_DATE('2024-12-06 16:37:40','YYYY-MM-DD HH24:MI:SS'),100,'@#AD_Org_ID@',NULL,'BETAG',0,'Y','Y','Y','N','AD_Org_ID',10,TO_DATE('2024-12-06 16:37:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Dec 6, 2024 4:37:40 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=1000722 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

