-- Dec 6, 2024 5:07:30 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Menu (AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Action,Created,CreatedBy,EntityType,IsActive,IsCentrallyMaintained,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,1000220,0,1000257,'P',TO_DATE('2024-12-06 17:07:30','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','Y','N','N','N','Beta EInvoice Registration - Production',TO_DATE('2024-12-06 17:07:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Dec 6, 2024 5:07:30 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=1000220 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- Dec 6, 2024 5:07:30 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID, 0, 'Y', SysDate, 100, SysDate, 100,t.AD_Tree_ID, 1000220, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.TreeType='MM' AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=1000220)
;

-- Dec 6, 2024 5:07:34 PM IST
-- Changes done for eInvoice support
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=0, Updated=SysDate WHERE AD_Tree_ID=10 AND Node_ID=441
;

-- Dec 6, 2024 5:07:34 PM IST
-- Changes done for eInvoice support
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=1, Updated=SysDate WHERE AD_Tree_ID=10 AND Node_ID=149
;

-- Dec 6, 2024 5:07:34 PM IST
-- Changes done for eInvoice support
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=2, Updated=SysDate WHERE AD_Tree_ID=10 AND Node_ID=50010
;

-- Dec 6, 2024 5:07:34 PM IST
-- Changes done for eInvoice support
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=3, Updated=SysDate WHERE AD_Tree_ID=10 AND Node_ID=171
;

-- Dec 6, 2024 5:07:34 PM IST
-- Changes done for eInvoice support
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=4, Updated=SysDate WHERE AD_Tree_ID=10 AND Node_ID=437
;

-- Dec 6, 2024 5:07:34 PM IST
-- Changes done for eInvoice support
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=5, Updated=SysDate WHERE AD_Tree_ID=10 AND Node_ID=240
;

-- Dec 6, 2024 5:07:34 PM IST
-- Changes done for eInvoice support
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=6, Updated=SysDate WHERE AD_Tree_ID=10 AND Node_ID=1000219
;

-- Dec 6, 2024 5:07:34 PM IST
-- Changes done for eInvoice support
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=7, Updated=SysDate WHERE AD_Tree_ID=10 AND Node_ID=1000220
;

-- Dec 6, 2024 5:07:34 PM IST
-- Changes done for eInvoice support
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=8, Updated=SysDate WHERE AD_Tree_ID=10 AND Node_ID=361
;

-- Dec 6, 2024 5:08:00 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Menu (AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Action,Created,CreatedBy,EntityType,IsActive,IsCentrallyMaintained,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,1000221,0,1000256,'P',TO_DATE('2024-12-06 17:08:00','YYYY-MM-DD HH24:MI:SS'),100,'BETAG','Y','Y','N','N','N','Beta EInvoice Registration - Renewal',TO_DATE('2024-12-06 17:08:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Dec 6, 2024 5:08:00 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=1000221 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- Dec 6, 2024 5:08:00 PM IST
-- Changes done for eInvoice support
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID, 0, 'Y', SysDate, 100, SysDate, 100,t.AD_Tree_ID, 1000221, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.TreeType='MM' AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=1000221)
;

-- Dec 6, 2024 5:08:05 PM IST
-- Changes done for eInvoice support
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=0, Updated=SysDate WHERE AD_Tree_ID=10 AND Node_ID=441
;

-- Dec 6, 2024 5:08:05 PM IST
-- Changes done for eInvoice support
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=1, Updated=SysDate WHERE AD_Tree_ID=10 AND Node_ID=149
;

-- Dec 6, 2024 5:08:05 PM IST
-- Changes done for eInvoice support
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=2, Updated=SysDate WHERE AD_Tree_ID=10 AND Node_ID=50010
;

-- Dec 6, 2024 5:08:05 PM IST
-- Changes done for eInvoice support
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=3, Updated=SysDate WHERE AD_Tree_ID=10 AND Node_ID=171
;

-- Dec 6, 2024 5:08:05 PM IST
-- Changes done for eInvoice support
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=4, Updated=SysDate WHERE AD_Tree_ID=10 AND Node_ID=437
;

-- Dec 6, 2024 5:08:05 PM IST
-- Changes done for eInvoice support
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=5, Updated=SysDate WHERE AD_Tree_ID=10 AND Node_ID=240
;

-- Dec 6, 2024 5:08:05 PM IST
-- Changes done for eInvoice support
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=6, Updated=SysDate WHERE AD_Tree_ID=10 AND Node_ID=1000219
;

-- Dec 6, 2024 5:08:05 PM IST
-- Changes done for eInvoice support
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=7, Updated=SysDate WHERE AD_Tree_ID=10 AND Node_ID=1000220
;

-- Dec 6, 2024 5:08:05 PM IST
-- Changes done for eInvoice support
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=8, Updated=SysDate WHERE AD_Tree_ID=10 AND Node_ID=1000221
;

-- Dec 6, 2024 5:08:05 PM IST
-- Changes done for eInvoice support
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=9, Updated=SysDate WHERE AD_Tree_ID=10 AND Node_ID=361
;

