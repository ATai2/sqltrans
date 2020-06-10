
IF NOT EXISTS (SELECT 1 FROM SYSSERVICE WHERE ID='24')
BEGIN
    insert into SYSSERVICE (id, code, caption, description, stype, requesturl, createtime, updatetime, creator, requestway, system, groupid) values ('24', 'dezjWebService', '接受大额资金上报文件（webservice）', '接受大额资金上报文件（webservice）', 0, '/api/ws/dezj/acceptdata',  getdate(), getdate(), 'scott', 2, 1, '1210');
    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('24', 1, 'filename', 1, null, 'binary', '文件名');
	insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('24', 2, 'RESCODE', 1, null, 'string', '资源代号');
	insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('24', 3, 'USER', 1, null, 'string', '用户名');
	insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('24', 4, 'PASSWORD', 1, null, 'string', '用户密码');
	insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('24', 5, 'Ver', 1, null, 'string', '版本号');
	insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('24', 6, 'dataHandler', 1, null, 'binary', '文件');
END
go

