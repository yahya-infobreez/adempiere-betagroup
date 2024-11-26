- Nov 18, 2024 10:13:13 PM AST
-- Changes done for eInvoice support
INSERT INTO C_TaxCategory (AD_Client_ID,AD_Org_ID,C_TaxCategory_ID,Created,CreatedBy,Description,IsActive,IsDefault,Name,Updated,UpdatedBy) VALUES (1000000,1000000,1000005,TO_DATE('2024-11-18 22:13:13','YYYY-MM-DD HH24:MI:SS'),100,'*** For Internal Use for Round Off etc. Do not change the name. ***','Y','N','x Not Applicable',TO_DATE('2024-11-18 22:13:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Nov 18, 2024 10:13:13 PM AST
-- Changes done for eInvoice support
INSERT INTO C_TaxCategory_Trl (AD_Language,C_TaxCategory_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_TaxCategory_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_TaxCategory t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_TaxCategory_ID=1000005 AND NOT EXISTS (SELECT * FROM C_TaxCategory_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_TaxCategory_ID=t.C_TaxCategory_ID)
;

-- Nov 18, 2024 10:16:27 PM AST
-- Changes done for eInvoice support
INSERT INTO C_Tax (AD_Client_ID,AD_Org_ID,C_TaxCategory_ID,C_Tax_ID,Created,CreatedBy,Description,IsActive,IsDefault,IsDocumentLevel,IsSalesTax,IsSummary,IsTaxExempt,Name,Parent_Tax_ID,Rate,RequiresTaxCertificate,SOPOType,Updated,UpdatedBy,ValidFrom) VALUES (1000000,1000000,1000005,1000005,TO_DATE('2024-11-18 22:16:27','YYYY-MM-DD HH24:MI:SS'),100,'*** For Internal Use for Round Off etc. Do not change the name.  ***','Y','N','N','N','N','N','x Not Applicable',1000000,0,'N','B',TO_DATE('2024-11-18 22:16:27','YYYY-MM-DD HH24:MI:SS'),100,TO_DATE('2024-01-01','YYYY-MM-DD'))
;

-- Nov 18, 2024 10:16:27 PM AST
-- Changes done for eInvoice support
INSERT INTO C_Tax_Trl (AD_Language,C_Tax_ID, Description,Name,TaxIndicator, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_Tax_ID, t.Description,t.Name,t.TaxIndicator, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Tax t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_Tax_ID=1000005 AND NOT EXISTS (SELECT * FROM C_Tax_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Tax_ID=t.C_Tax_ID)
;

-- Nov 18, 2024 10:16:27 PM AST
-- Changes done for eInvoice support
INSERT INTO C_Tax_Acct (C_Tax_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy ,T_Credit_Acct,T_Due_Acct,T_Expense_Acct,T_Liability_Acct,T_Receivables_Acct) SELECT 1000005, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', SysDate,100,SysDate,100,p.T_Credit_Acct,p.T_Due_Acct,p.T_Expense_Acct,p.T_Liability_Acct,p.T_Receivables_Acct FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000 AND NOT EXISTS (SELECT * FROM C_Tax_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_Tax_ID=1000005)
;

