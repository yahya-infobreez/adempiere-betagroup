
-- Nov 18, 2024 4:46:37 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,1001038,0,'CSRFile',TO_DATE('2024-11-18 16:46:37','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','CSR File','CSR File',TO_DATE('2024-11-18 16:46:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 18, 2024 4:46:37 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=1001038 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Nov 18, 2024 4:47:49 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,1001039,0,'PrivateKeyFile',TO_DATE('2024-11-18 16:47:49','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','Private Key File','Private Key File',TO_DATE('2024-11-18 16:47:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 18, 2024 4:47:49 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=1001039 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Nov 18, 2024 4:39:51 PM AST
-- Changes done for eInvoice support
INSERT INTO AD_Process_Para (
AD_PROCESS_PARA_ID,AD_CLIENT_ID,AD_ORG_ID,ISACTIVE,CREATED,CREATEDBY,UPDATED,UPDATEDBY,NAME,DESCRIPTION,HELP,AD_PROCESS_ID,SEQNO,AD_REFERENCE_ID,AD_REFERENCE_VALUE_ID,AD_VAL_RULE_ID,COLUMNNAME,ISCENTRALLYMAINTAINED,FIELDLENGTH,ISMANDATORY,ISRANGE,DEFAULTVALUE,DEFAULTVALUE2,VFORMAT,VALUEMIN,VALUEMAX,AD_ELEMENT_ID,ENTITYTYPE,READONLYLOGIC,DISPLAYLOGIC) VALUES
         (1000713,            0,         0, 'Y',        2024-11-18 15:55:38.0,       100, 2024-11-18 16:39:51.0,       100, 'AD_Org_ID',          null,                                       null,          1000255,    10,              19,                   130,       null, 'AD_Org_ID',      'Y',                               0, 'Y',           'N',       @#AD_Org_ID@, null,      null,  null,   null,          null  BETAG,      null,        null)
         (1000714,            0,         0, 'Y',        2024-11-18 15:58:31.0,       100, 2024-11-18 16:40:48.0,       100, 'UserName',          'User Name (Key) received from Fatoora portal',  null,      1000255,   20,              14,                null,         null, 'UserName',       'Y',                            1024 ,Y,           'N',       null,       null,        null,  null,   null,          null, BETAG,      null,        null)
         (1000715,            0,         0, 'Y',        2024-11-18 16:01:02.0,       100, 2024-11-18 16:41:01.0,       100, 'Password',          'Passord received from fatoora portal',         null        1000255,  30,              14,                null,         null, 'Password',       'Y',                             256, 'Y',           'N',       null,       null,        null,  null,   null,          null, BETAG,      null,        null)
         (1000716,            0,         0, 'Y',        2024-11-18 16:02:07.0,       100, 2024-11-18 16:47:16.0,       100, 'CSRFile'         null,                                       null,              1000255,    40,              39,                null,         null, 'CSRFile',        'Y',                             256, 'Y',           'N',       null,       null,        null,  null,   null,       1001038, BETAG,      null,        null)
         (1000717,            0,         0, 'Y',        2024-11-18 16:05:10.0,       100, 2024-11-18 16:47:58.0,       100, 'PrivateKeyFile',  null,                                       null,             1000255,    50,              39,                null,         null, 'PrivateKeyFile', 'Y',                             256, 'Y',           'N',       null,       null,        null,  null,   null,       1001039, BETAG,      null,        null);


