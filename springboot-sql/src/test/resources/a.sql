IF NOT EXISTS (SELECT 1 FROM DSM_ENTABLE WHERE ENTABLE_ID='20d6efd7-697a-4b60-bcd1-37b319da206f')
BEGIN
    INSERT INTO DSM_ENTABLE VALUES ('20d6efd7-697a-4b60-bcd1-37b319da206f', 'DMP_ZWKMLINK', '科目关联关系表', 6, 'DMP_ZWKMLINK', '', NULL, NULL, 'ID', 'ID', '4',getdate(),getdate(), 'scott', NULL, NULL, NULL, 1, ',', 'TEXTFILE', '', 0, '', '', '', '');
    INSERT INTO DSM_ENTABLEGROUPMAPPING VALUES ('08ae946d-9aaa-42a3-ab66-e0d82ad084b8', '20d6efd7-697a-4b60-bcd1-37b319da206f', 'df0300b9-205a-45d4-8620-f9b9b11ccd6a');
    INSERT INTO DSM_ENTITYFIELD VALUES ('feac973c-d0bb-48d5-a692-2d9d80a70534', '20d6efd7-697a-4b60-bcd1-37b319da206f', 'GZWKMMC', '科目名称（国资委）', '0', 200, NULL, '国资委统一制定的科目名称', '1', '0', '2',getdate(), 'scott',getdate(), '', '', '');
    INSERT INTO DSM_ENTITYFIELD VALUES ('32851103-0a89-4a58-b0d8-cbfebfa3323a', '20d6efd7-697a-4b60-bcd1-37b319da206f', 'QYKMBH', '科目编码（企业）', '0', 50, NULL, '最大50位编号。企业科目编码', '1', '0', '3',getdate(), 'scott',getdate(), '', '', '');
    INSERT INTO DSM_ENTITYFIELD VALUES ('40204468-c3da-48d0-8961-7506f2e183a5', '20d6efd7-697a-4b60-bcd1-37b319da206f', 'GZWKMBH', '科目编码（国资委）', '0', 50, NULL, '最大50位编号。国资委统一制定的科目编码', '1', '0', '1',getdate(), 'scott',getdate(), '', '', '');
    INSERT INTO DSM_ENTITYFIELD VALUES ('42fce203-2c62-48e3-b1f3-3844e0bac386', '20d6efd7-697a-4b60-bcd1-37b319da206f', 'QYKMMC', '科目名称（企业）', '0', 200, NULL, '企业科目名称', '1', '0', '4',getdate(), 'scott',getdate(), '', '', '');
    INSERT INTO DSM_ENTITYFIELD VALUES ('7aac4630-c4a2-4fd0-8e24-b52e7d72fa66', '20d6efd7-697a-4b60-bcd1-37b319da206f', 'ID', '唯一标识', '0', 36, NULL, '科目唯一标识', '1', '0', '0',getdate(), 'scott',getdate(), '', '', '');
END
go

IF NOT EXISTS (SELECT 1 FROM DSM_ENTABLE WHERE ENTABLE_ID='2cb35a10-37d6-47ff-b59f-275dc0e66bf0')
BEGIN
    INSERT INTO DSM_ENTABLE VALUES ('2cb35a10-37d6-47ff-b59f-275dc0e66bf0', 'SASAORGRELATION', '国企组织关联业务系统组织表', 6, 'SASAORGRELATION', '', NULL, NULL, 'ID', 'ID', '4',getdate(),getdate(), 'scott', NULL, NULL, NULL, 1, ',', 'TEXTFILE', '', 0, '', '', '', '');
    INSERT INTO DSM_ENTABLEGROUPMAPPING VALUES ('b846d48f-0642-44cb-ac1c-a3aa8c0bfcda', '2cb35a10-37d6-47ff-b59f-275dc0e66bf0', 'df0300b9-205a-45d4-8620-f9b9b11ccd6a');
    INSERT INTO DSM_ENTITYFIELD VALUES ('1785ee37-36a8-4a4a-a4f5-2ba575c9eb55', '2cb35a10-37d6-47ff-b59f-275dc0e66bf0', 'RELATIONORGID', '国企组织内码 关联组织内码', '0', 36, NULL, '', '1', '0', '2',getdate(), 'scott',getdate(), '', '', '');
    INSERT INTO DSM_ENTITYFIELD VALUES ('337879c5-462b-4410-8e43-9717b805802d', '2cb35a10-37d6-47ff-b59f-275dc0e66bf0', 'SYSTEMID', '所属系统', '0', 36, NULL, '', '1', '0', '7',getdate(), 'scott',getdate(), '', '', '');
    INSERT INTO DSM_ENTITYFIELD VALUES ('34664994-9718-4635-b97f-71766e437d45', '2cb35a10-37d6-47ff-b59f-275dc0e66bf0', 'ORGGUID', '国企组织内码', '0', 36, NULL, '', '1', '0', '1',getdate(), 'scott',getdate(), '', '', '');
    INSERT INTO DSM_ENTITYFIELD VALUES ('4e7e7975-9644-43cc-b745-8c496d145692', '2cb35a10-37d6-47ff-b59f-275dc0e66bf0', 'RELATIONORGNAME', '关联组织名称', '0', 512, NULL, '', '1', '0', '4',getdate(), 'scott',getdate(), '', '', '');
    INSERT INTO DSM_ENTITYFIELD VALUES ('754fab9a-c828-4040-90ea-a9379b14ab71', '2cb35a10-37d6-47ff-b59f-275dc0e66bf0', 'ISHISTORY', '停用标识', '0', 1, NULL, '1 是，0否', '1', '0', '8',getdate(), 'scott',getdate(), '', '', '');
    INSERT INTO DSM_ENTITYFIELD VALUES ('9471b272-9d4a-4961-9df3-9c4b59f83a43', '2cb35a10-37d6-47ff-b59f-275dc0e66bf0', 'REALTIONTYPE', '关联类型', '0', 1, NULL, '', '1', '0', '6',getdate(), 'scott',getdate(), '', '', '');
    INSERT INTO DSM_ENTITYFIELD VALUES ('bd5bd945-df1d-459f-b5f2-ec16a1efc49d', '2cb35a10-37d6-47ff-b59f-275dc0e66bf0', 'ID', '内码', '0', 36, NULL, '', '1', '0', '0',getdate(), 'scott',getdate(), '', '', '');
    INSERT INTO DSM_ENTITYFIELD VALUES ('d7683df6-a8c5-410f-b9cf-e2eb93222d97', '2cb35a10-37d6-47ff-b59f-275dc0e66bf0', 'REMARK', '备注', '0', 1024, NULL, '', '1', '0', '5',getdate(), 'scott',getdate(), '', '', '');
    INSERT INTO DSM_ENTITYFIELD VALUES ('f9bd922c-f8dd-42d8-9915-9cdc3ada0c09', '2cb35a10-37d6-47ff-b59f-275dc0e66bf0', 'RELATIONORGCODE', '关联组织编号', '0', 256, NULL, '', '1', '0', '3',getdate(), 'scott',getdate(), '', '', '');
END
go

IF NOT EXISTS (SELECT 1 FROM DSM_ENTABLE WHERE ENTABLE_ID='78896e6a-261e-4b0d-9790-f93a2ed79e11')
BEGIN
    INSERT INTO DSM_ENTABLE VALUES ('78896e6a-261e-4b0d-9790-f93a2ed79e11', 'DMP_ZJUSELINK', '资金用途关联关系表', 6, 'DMP_ZJUSELINK', '', NULL, NULL, 'ID', 'ID', '4',getdate(),getdate(), 'scott', NULL, NULL, NULL, 1, ',', 'TEXTFILE', '', 0, '', '', '', '');
    INSERT INTO DSM_ENTABLEGROUPMAPPING VALUES ('6fb3b954-86ee-4622-9576-22e6368ccf0a', '78896e6a-261e-4b0d-9790-f93a2ed79e11', 'df0300b9-205a-45d4-8620-f9b9b11ccd6a');
    INSERT INTO DSM_ENTITYFIELD VALUES ('20e0362b-4d3c-4f6b-9149-f81751b6fc7a', '78896e6a-261e-4b0d-9790-f93a2ed79e11', 'QYUSECODE', '资金用途编码（企业）', '0', 6, NULL, '最大6位编号。企业资金用途编码', '1', '0', '3',getdate(), 'scott',getdate(), '', '', '');
    INSERT INTO DSM_ENTITYFIELD VALUES ('8b271694-3897-4d65-b46f-38ba097438fc', '78896e6a-261e-4b0d-9790-f93a2ed79e11', 'GZWUSENAME', '资金用途名称（国资委）', '0', 500, NULL, '国资委统一制定的资金用途名称', '1', '0', '2',getdate(), 'scott',getdate(), '', '', '');
    INSERT INTO DSM_ENTITYFIELD VALUES ('99a65daa-9567-4d30-b95d-d7a78f0e067e', '78896e6a-261e-4b0d-9790-f93a2ed79e11', 'GZWUSECODE', '资金用途编码（国资委）', '0', 6, NULL, '最大6位编号。国资委统一制定的资金用途编码', '1', '0', '1',getdate(), 'scott',getdate(), '', '', '');
    INSERT INTO DSM_ENTITYFIELD VALUES ('c7ec2008-df76-4d2b-b302-a40b0c2eb8ce', '78896e6a-261e-4b0d-9790-f93a2ed79e11', 'QYUSENAME', '资金用途名称（企业）', '0', 500, NULL, '企业资金用途名称', '1', '0', '4',getdate(), 'scott',getdate(), '', '', '');
    INSERT INTO DSM_ENTITYFIELD VALUES ('e007cba1-849d-4d94-a6bc-d8e84deeecdf', '78896e6a-261e-4b0d-9790-f93a2ed79e11', 'ID', '唯一标识', '0', 36, NULL, '资金用途唯一标识', '1', '0', '0',getdate(), 'scott',getdate(), '', '', '');
END
go

IF NOT EXISTS (SELECT 1 FROM DSM_ENTABLE WHERE ENTABLE_ID='f2402661-b917-422b-8aad-15c1b247ca16')
BEGIN
    INSERT INTO DSM_ENTABLE VALUES ('f2402661-b917-422b-8aad-15c1b247ca16', 'DMP_ZWBZLINK', '币种关联关系表', 6, 'DMP_ZWBZLINK', '', NULL, NULL, 'ID', 'ID', '4',getdate(),getdate(), 'scott', NULL, NULL, NULL, 1, ',', 'TEXTFILE', '', 0, '', '', '', '');
    INSERT INTO DSM_ENTABLEGROUPMAPPING VALUES ('428a5ecb-4ae7-4f18-8648-d728f27ec9b1', 'f2402661-b917-422b-8aad-15c1b247ca16', 'df0300b9-205a-45d4-8620-f9b9b11ccd6a');
    INSERT INTO DSM_ENTITYFIELD VALUES ('3696cc40-40d7-47b4-aeaa-2923ca2bc5b5', 'f2402661-b917-422b-8aad-15c1b247ca16', 'GZWWBBH', '币种编码（国资委）', '0', 20, NULL, '最大20位编号。资委统一制定的币种编码', '1', '0', '1',getdate(), 'scott',getdate(), '', '', '');
    INSERT INTO DSM_ENTITYFIELD VALUES ('5db08515-ba49-4a5c-a4b6-79f02257ac5f', 'f2402661-b917-422b-8aad-15c1b247ca16', 'QYWBBH', '币种编码（企业）', '0', 20, NULL, '最大20位编号。企业币种编码', '1', '0', '3',getdate(), 'scott',getdate(), '', '', '');
    INSERT INTO DSM_ENTITYFIELD VALUES ('9cb56686-ce13-427d-9bb1-f2e03d850673', 'f2402661-b917-422b-8aad-15c1b247ca16', 'ID', '唯一标识', '0', 36, NULL, '币种唯一标识', '1', '0', '0',getdate(), 'scott',getdate(), '', '', '');
    INSERT INTO DSM_ENTITYFIELD VALUES ('cf33890c-95e8-4837-b77c-7c148e336c64', 'f2402661-b917-422b-8aad-15c1b247ca16', 'QYWBMC', '币种名称（企业）', '0', 200, NULL, '企业币种名称', '1', '0', '4',getdate(), 'scott',getdate(), '', '', '');
    INSERT INTO DSM_ENTITYFIELD VALUES ('fd8ea63b-f8cb-46f1-bc87-1444164e7a6f', 'f2402661-b917-422b-8aad-15c1b247ca16', 'GZWWBMC', '币种名称（国资委）', '0', 200, NULL, '国资委统一制定的币种名称', '1', '0', '2',getdate(), 'scott',getdate(), '', '', '');
END
go