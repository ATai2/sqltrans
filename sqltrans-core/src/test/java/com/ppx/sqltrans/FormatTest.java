package com.ppx.sqltrans;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.util.JdbcConstants;
import org.junit.Test;

public class FormatTest {


    @Test
    public void test() {

        String sql = "IF NOT EXISTS (SELECT 1 FROM SYSSERVICE WHERE ID='24')\n" +
                "BEGIN\n" +
                "    insert into SYSSERVICE (id, code, caption, description, stype, requesturl, createtime, updatetime, creator, requestway, system, groupid) values ('24', 'dezjWebService', '接受大额资金上报文件（webservice）', '接受大额资金上报文件（webservice）', 0, '/api/ws/dezj/acceptdata',  getdate(), getdate(), 'scott', 2, 1, '1210');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('24', 1, 'filename', 1, null, 'binary', '文件名');\n" +
                "\tinsert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('24', 2, 'RESCODE', 1, null, 'string', '资源代号');\n" +
                "\tinsert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('24', 3, 'USER', 1, null, 'string', '用户名');\n" +
                "\tinsert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('24', 4, 'PASSWORD', 1, null, 'string', '用户密码');\n" +
                "\tinsert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('24', 5, 'Ver', 1, null, 'string', '版本号');\n" +
                "\tinsert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('24', 6, 'dataHandler', 1, null, 'binary', '文件');\n" +
                "END\n" +
                "go\n" +
                "\n" +
                "IF NOT EXISTS (SELECT 1 FROM SYSSERVICE WHERE ID='25')\n" +
                "BEGIN\n" +
                "    insert into SYSSERVICE (id, code, caption, description, stype, requesturl, createtime, updatetime, creator, requestway, system, groupid) values ('25', 'downLoadFile', '静态数据下载', '静态数据下载', 0, '/api/dataCollectorEnterpriseWeb/dataPushAction/downLoadFile', getdate(), getdate(), 'scott', 2, 1, '1210');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('25', 1, 'API_CODE', 1, '', 'string', '接口编码');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('25', 2, 'USER', 1, '', 'string', '用户名');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('25', 3, 'PASSWORD', 1, '', 'string', '密码');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('25', 4, 'BUSINESS_TYPE', 1, '', 'string', '业务类型编码');\n" +
                "END\n" +
                "go\n" +
                "\n" +
                "IF NOT EXISTS (SELECT 1 FROM SYSSERVICE WHERE ID='26')\n" +
                "BEGIN\n" +
                "    insert into SYSSERVICE (id, code, caption, description, stype, requesturl, createtime, updatetime, creator, requestway, system, groupid) values ('26', 'downLoadStaticFile', '静态反馈数据下载', '静态反馈数据下载', 0, '/api/dataPushAction/downLoadFile', getdate(), getdate(), 'scott', 2, 1, '1210');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('26', 1, 'API_CODE', 1, '', 'string', '接口编码');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('26', 2, 'USER', 1, '', 'string', '用户名');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('26', 3, 'PASSWORD', 1, '', 'string', '密码');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('26', 4, 'BUSINESS_TYPE', 1, '', 'string', '业务类型编码');\n" +
                "END\n" +
                "go\n" +
                "\n" +
                "IF NOT EXISTS (SELECT 1 FROM SYSSERVICE WHERE ID='27')\n" +
                "BEGIN\n" +
                "    insert into SYSSERVICE (id, code, caption, description, stype, requesturl, createtime, updatetime, creator, requestway, system, groupid) values ('27', 'downLoadTaskFile', '采集任务文件下载', '采集任务文件下载', 0, '/api/dataPushAction/downLoadTaskFile', getdate(), getdate(), 'scott', 2, 1, '1210');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('27', 1, 'USER', 1, '', 'string', '用户名');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('27', 2, 'PASSWORD', 1, '', 'string', '密码');\n" +
                "END\n" +
                "go\n" +
                "\n" +
                "\n" +
                "IF NOT EXISTS (SELECT 1 FROM SYSSERVICE WHERE ID='1a4bd66f-3edc-47d7-aff4-43dbcbd6f0d1')\n" +
                "BEGIN\n" +
                "    insert into SYSSERVICE (id, code, caption, description, stype, requesturl, createtime, updatetime, creator, requestway, system, groupid) values ('1a4bd66f-3edc-47d7-aff4-43dbcbd6f0d1', 'uploadFile', '三重一大数据报送接口', '老通道', 0, '/api/dataPushAction/uploadFile', getdate(), getdate(), 'scott', 2, 1, '1210');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('1a4bd66f-3edc-47d7-aff4-43dbcbd6f0d1', 1, 'Files', 2, '', 'binary', '必须，报送文件');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('1a4bd66f-3edc-47d7-aff4-43dbcbd6f0d1', 2, 'API_CODE', 1, 'SZ01', 'string', '接口编码');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('1a4bd66f-3edc-47d7-aff4-43dbcbd6f0d1', 3, 'BUSINESS_TYPE', 1, '', 'string', '必须，业务类型编码');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('1a4bd66f-3edc-47d7-aff4-43dbcbd6f0d1', 4, 'FILE_NAME', 1, '', 'string', '必须，文件名，含文件后缀');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('1a4bd66f-3edc-47d7-aff4-43dbcbd6f0d1', 5, 'USER', 1, 'admin', 'string', '必须，调用接口用户名');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('1a4bd66f-3edc-47d7-aff4-43dbcbd6f0d1', 6, 'PASSWORD', 1, 'admin', 'string', '必须，调用接口密码');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('1a4bd66f-3edc-47d7-aff4-43dbcbd6f0d1', 7, 'TASK_ID', 1, '', 'string', '补传任务ID');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('1a4bd66f-3edc-47d7-aff4-43dbcbd6f0d1', 8, 'Accept-charset', 3, 'utf-8', 'string', '');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('1a4bd66f-3edc-47d7-aff4-43dbcbd6f0d1', 9, 'Content-Type', 3, 'multipart/form-data', 'string', '');\n" +
                "END\n" +
                "go\n" +
                "\n" +
                "IF NOT EXISTS (SELECT 1 FROM SYSSERVICE WHERE ID='2f7f0ba5-f6ae-4305-b264-a6cabff2e34f')\n" +
                "BEGIN\n" +
                "    insert into SYSSERVICE (id, code, caption, description, stype, requesturl, createtime, updatetime, creator, requestway, system, groupid) values ('2f7f0ba5-f6ae-4305-b264-a6cabff2e34f', 'logDownload', '获取国资委日志列表', '老通道', 0, '/api/dataPushAction/logDownload', getdate(), getdate(), 'scott', 1, 1, '1210');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('2f7f0ba5-f6ae-4305-b264-a6cabff2e34f', 1, 'STARTDATE', 1, '', 'string', '必须，时间区间开始值;  格式：yyyyMMdd');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('2f7f0ba5-f6ae-4305-b264-a6cabff2e34f', 2, 'ENDDATE', 1, '', 'string', '必须，时间区间结束值;  格式：yyyyMMdd');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('2f7f0ba5-f6ae-4305-b264-a6cabff2e34f', 3, 'SIDE', 1, 'ENTERPRISE', 'string', '必须，表示中央企业内网数据动态监测采集服务器');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('2f7f0ba5-f6ae-4305-b264-a6cabff2e34f', 4, 'USER', 1, 'admin', 'string', '必须，调用接口用户名');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('2f7f0ba5-f6ae-4305-b264-a6cabff2e34f', 5, 'PASSWORD', 1, 'admin', 'string', '必须，调用接口密码');\n" +
                "END\n" +
                "go\n" +
                "\n" +
                "IF NOT EXISTS (SELECT 1 FROM SYSSERVICE WHERE ID='c1d2cada-6247-49e7-b5e0-d44ebb3d2944')\n" +
                "BEGIN\n" +
                "    insert into SYSSERVICE (id, code, caption, description, stype, requesturl, createtime, updatetime, creator, requestway, system, groupid) values ('c1d2cada-6247-49e7-b5e0-d44ebb3d2944', 'forward/uploadFile', '数据报送', '企业自建系统采集业务相关数据。将采集到的结构化数据转换为XML文件，文档附件转换成PDF/OFD文件，并将XML文件与PDF/OFD文件合并为ZIP数据包，然后调用数据报送系统上传接口进行上报。', 0, 'api/forward/uploadFile',  getdate(), getdate(), 'scott', 2, 1, '1210');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('c1d2cada-6247-49e7-b5e0-d44ebb3d2944', 1, 'files', 3, null, 'string', '\"文件名\"（文件名需包含后缀）');\n" +
                "\tinsert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('c1d2cada-6247-49e7-b5e0-d44ebb3d2944', 2, 'Accept-Charset', 3, null, 'string', '');\n" +
                "\tinsert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('c1d2cada-6247-49e7-b5e0-d44ebb3d2944', 3, 'Content-Type', 3, 'multipart/form-data', 'string', '');\n" +
                "\n" +
                "\tinsert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('c1d2cada-6247-49e7-b5e0-d44ebb3d2944', 4, 'PASSWORD', 2, null, 'string', '必填，用户密码');\n" +
                "\tinsert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('c1d2cada-6247-49e7-b5e0-d44ebb3d2944', 5, 'USER', 2, null, 'string', '必填，用户');\n" +
                "\tinsert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('c1d2cada-6247-49e7-b5e0-d44ebb3d2944', 6, 'API_CODE', 2, 'A001', 'string', '必填，接口编码必须是A001');\n" +
                "\tinsert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('c1d2cada-6247-49e7-b5e0-d44ebb3d2944', 7, 'BUSINESS_TYPE', 2, null, 'string', '必填，业务类型编码');\n" +
                "\tinsert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('c1d2cada-6247-49e7-b5e0-d44ebb3d2944', 8, 'files', 2, null, 'binary', '必填，报送文件格式：zip,名称规则：业务类型编码_1000_yyyyMMdd.zip');\n" +
                "\tinsert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('c1d2cada-6247-49e7-b5e0-d44ebb3d2944', 9, 'FILE_NAME', 2, null, 'string', '必填，文件名');\n" +
                "\tinsert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('c1d2cada-6247-49e7-b5e0-d44ebb3d2944', 10, 'KEY_CODE', 2, null, 'string', '必填，密码加密使用的密钥，值：M4D3');\n" +
                "END\n" +
                "go\n" +
                "\n" +
                "\n" +
                "\n" +
                "IF NOT EXISTS (SELECT 1 FROM SYSSERVICE WHERE ID='ab447507-9be9-40d4-8876-25e35bcc428d')\n" +
                "BEGIN\n" +
                "    insert into SYSSERVICE (id, code, caption, description, stype, requesturl, createtime, updatetime, creator, requestway, system, groupid) values ('ab447507-9be9-40d4-8876-25e35bcc428d', 'forward/downLoadFile', '静态数据下载', '下载从国资委端下发的文件，默认只将未下载过的数据文件打包返回给调用方。', 0, 'api/forward/downLoadFile',  getdate(), getdate(), 'scott', 2, 1, '1210');\n" +
                "\tinsert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('ab447507-9be9-40d4-8876-25e35bcc428d', 1, 'Accept-Charset', 3, null, 'string', '');\n" +
                "\tinsert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('ab447507-9be9-40d4-8876-25e35bcc428d', 2, 'PASSWORD', 2, null, 'string', '必填，用户密码');\n" +
                "\tinsert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('ab447507-9be9-40d4-8876-25e35bcc428d', 3, 'USER', 2, null, 'string', '必填，用户');\n" +
                "\tinsert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('ab447507-9be9-40d4-8876-25e35bcc428d', 4, 'API_CODE', 2, 'A001', 'string', '必填，接口编码与业务类型编码一致');\n" +
                "\tinsert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('ab447507-9be9-40d4-8876-25e35bcc428d', 5, 'BUSINESS_TYPE', 2, null, 'string', '必填，业务类型编码与接口编码一致');\n" +
                "\tinsert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('ab447507-9be9-40d4-8876-25e35bcc428d', 6, 'FORCE_DOWN', 2, null, 'string', '强制下载参数，默认不传。');\n" +
                "\tinsert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('ab447507-9be9-40d4-8876-25e35bcc428d', 7, 'KEY_CODE', 2, null, 'string', '必填，用来使用指定的密钥，定期变更密钥');\n" +
                "END\n" +
                "go\n" +
                "\n" +
                "IF NOT EXISTS (SELECT 1 FROM SYSSERVICE WHERE ID='245c1503-5219-4e4a-89fa-81ea49bc37e4')\n" +
                "BEGIN\n" +
                "    insert into SYSSERVICE (id, code, caption, description, stype, requesturl, createtime, updatetime, creator, requestway, system, groupid) values ('245c1503-5219-4e4a-89fa-81ea49bc37e4', 'dezjUploadFile', '大额资金数据报送接口', '老通道', 0, '/api/dataPushAction/dezjUploadFile', getdate(), getdate(), 'scott', 2, 1, '1210');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('245c1503-5219-4e4a-89fa-81ea49bc37e4', 1, 'Files', 2, '', 'binary', '必须，报送文件');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('245c1503-5219-4e4a-89fa-81ea49bc37e4', 2, 'API_CODE', 1, 'SZ01', 'string', '接口编码');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('245c1503-5219-4e4a-89fa-81ea49bc37e4', 3, 'BUSINESS_TYPE', 1, '', 'string', '必须，业务类型编码');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('245c1503-5219-4e4a-89fa-81ea49bc37e4', 4, 'FILE_NAME', 1, '', 'string', '必须，文件名，含文件后缀');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('245c1503-5219-4e4a-89fa-81ea49bc37e4', 5, 'USER', 1, 'admin', 'string', '必须，调用接口用户名');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('245c1503-5219-4e4a-89fa-81ea49bc37e4', 6, 'PASSWORD', 1, 'admin', 'string', '必须，调用接口密码');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('245c1503-5219-4e4a-89fa-81ea49bc37e4', 7, 'TASK_ID', 1, '', 'string', '补传任务ID');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('245c1503-5219-4e4a-89fa-81ea49bc37e4', 8, 'Accept-charset', 3, 'utf-8', 'string', '');\n" +
                "    insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values ('245c1503-5219-4e4a-89fa-81ea49bc37e4', 9, 'Content-Type', 3, 'multipart/form-data', 'string', '');\n" +
                "END\n" +
                "go";


        String format = SQLUtils.format(sql, JdbcConstants.SQL_SERVER);
        System.out.println(format);


    }
}
