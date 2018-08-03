var baseUrl = 'http://115.29.136.190:8080/help/';

/*金额相关*/
var je_url = {
        "get": baseUrl + 'mgr/je/get.do'
    }
    /*补助相关的url*/
var buzhu_url = {
    'delBuzhu': baseUrl + 'usr/buzhu/remove.do',
    'addBuzhu': baseUrl + 'usr/buzhu/addBuZhuCheck.do',
    'addBuzhu_without': baseUrl + 'usr/buzhu/addBuZhuWithoutCheck.do',
    'queryBuZhu': baseUrl + "usr/buzhu/searchBuzhuByPage1.do",
    'submit': baseUrl + 'usr/buzhu/submit.do',
    'agree': baseUrl + 'usr/buzhu/agree.do',
    'disagree': baseUrl + 'usr/buzhu/disagree.do',
    'reedit': baseUrl + 'usr/buzhu/reedit.do'
};
/*助管信息的URL*/
var zhuguan_url = {
    "addZhuguan": baseUrl + "base/stu/insertStuToAst.do",
    "queryZhuguan": baseUrl + "base/stu/list.do",
    "delZhuguan": baseUrl + "base/stu/delete.do",
    "editZhuguan": baseUrl + "base/stu/update.do",
    "queryStu": baseUrl + "base/stu/searchStudent.do",
};

var url_editZG = baseUrl + 'base/stu/update.do'
var url_adduser = baseUrl + 'base/user/add.do';
var url_delUser = baseUrl + 'base/user/delete.do';
var url_delZG = baseUrl + 'base/stu/delete.do';
var url_queryZhuguan = baseUrl + 'base/stu/list1.do';
var url_queryDept = baseUrl + 'base/dept/list.do';
var url_uploadFile = baseUrl + 'base/stu/upload.do';

// 开放/关闭调查问卷
var url_openSurvey = baseUrl + 'base/stu/openStatus.do';
var url_closeSurvey = baseUrl + 'base/stu/closeStatus.do';
var url_initSurvey = baseUrl + 'base/stu/surveyInit.do';

/*部门查询相关的URL*/
var url_queryDept = baseUrl + 'base/dept/list.do';

/*用户管理相关的URL*/
var url_adduser = baseUrl + 'base/user/add.do';
var url_delUser = baseUrl + 'base/user/delete.do';
var url_queryUser = baseUrl + 'base/user/list1.do';
var url_queryDept = baseUrl + 'base/dept/list.do'

// 月份查询相关的URL
var url_delDate = baseUrl + "usr/date/remove.do"
var url_addDate = baseUrl + "usr/date/add.do"
var url_editDate = baseUrl + "usr/date/update.do"
var url_queryDate = baseUrl + 'usr/date/getAll.do'
var url_addJe = baseUrl + "mgr/je/add.do"
var url_setDeadlin = baseUrl + ""

//日志管理
// http://localhost:8080/stu_assist_management/usr/log/list1.do
var url_log = baseUrl + "usr/log/list1.do"

//在职离职
var url_eWork = {
    "change": baseUrl + 'base/stu/currToHist.do',
    "get": baseUrl + 'base/ast/history/list.do'
};
//岗位审核相关
var gw_query = baseUrl + 'office/searchByPage.do';
var gw_query_agree = baseUrl + 'office/officeReview/agree.do';
var gw_query_disagree = baseUrl + 'office/officeReview/disagree.do',
    gw_query_publish = baseUrl + 'office/officePublish.do',
    gw_deadline = baseUrl + 'office/check/listDeadlines.do';

var gw_renewDL = baseUrl + 'office/check/updateDeadline.do';

//模块管理(刘道浩)
var url_module_query = baseUrl + 'module/control/list.do'
var url_module_update = baseUrl + 'module/control/update.do'

//模块管理(HB)
var url_module_query = baseUrl + 'module/control/getConfig.do'
var url_module_update = baseUrl + 'module/control/updateConfig.do'
    // 助管文件下载相关的URL
    // var url_uploadFile = baseUrl + '/base/stu/upload.do';
//问卷调查
var ques="http://115.29.136.190:8080/help-front/modal/stuQuestionaire.html";