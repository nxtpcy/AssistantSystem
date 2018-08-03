/*学生模块
baseUrl在url.js中
*/
var Student = {
    url: {
        addUser: baseUrl + 'student/user/addUser.do',
        delUser: baseUrl + 'student/user/deleteUser.do',
        listUser: baseUrl + 'student/listOffice.do',
        adminAgree: baseUrl + 'stu/application/adminAgree.do',
        adminDeny: baseUrl + 'stu/application/adminDisagree.do',
        deptAgree: baseUrl + 'stu/application/deptAgree.do',
        deptDeny: baseUrl + 'stu/application/deptDisagree.do',
        finalAgree: baseUrl + 'stu/application/adminFinalAgree.do',
        finalDeny: baseUrl + 'stu/application/adminFinalDisagree.do',
        apply: baseUrl + 'stu/apply.do',
        query: baseUrl + 'stu/appliedList.do',
        exportFile: baseUrl + 'stu/stu_info_xls_download.do',
        checkFile: baseUrl + 'stu/checkFileExist.do'
    },

    /*研究生院审核*/
    admin_agree: function(ids) {
        $.ajax({
            type: 'POST',
            async: false,
            url: this.url.adminAgree + "?id=" + ids,
            contentType: "application/json; charset=utf-8",
            // data: JSON.stringify(query),
            dataType: 'json',
            success: function(data) {
                $.messager.alert('Warning', data.message);
                return data;
            },
            error: function(XMLHttpRequest, textStatus, errorThrowna) {
                $.messager.alert('Warning', XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);

            }
        });
    },
    admin_disagree: function(ids) {
        $.ajax({
            type: 'POST',
            async: false,
            url: this.url.adminDeny + "?id=" + ids,
            contentType: "application/json; charset=utf-8",
            // data: JSON.stringify(query),
            dataType: 'json',
            success: function(data) {
                console.log(data);
                return data;
            },
            error: function(XMLHttpRequest, textStatus, errorThrowna) {
                $.messager.alert('Warning', XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);

            }
        });
    },
    /*部门管理员审核*/
    dept_agree: function(ids) {
        $.ajax({
            type: 'POST',
            async: false,
            url: this.url.deptAgree + "?id=" + ids,
            contentType: "application/json; charset=utf-8",
            // data: JSON.stringify(query),
            dataType: 'json',
            success: function(data) {
                console.log(data);
                $.messager.alert('Warning', data.message);
                return data;
            },
            error: function(XMLHttpRequest, textStatus, errorThrowna) {
                $.messager.alert('Warning', XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);

            }
        });
    },
    dept_disagree: function(ids) {
        $.ajax({
            type: 'POST',
            async: false,
            url: this.url.deptDeny + "?id=" + ids,
            contentType: "application/json; charset=utf-8",
            // data: JSON.stringify(query),
            dataType: 'json',
            success: function(data) {
                $.messager.alert('Warning', data.message);
                return data;
            },
            error: function(XMLHttpRequest, textStatus, errorThrowna) {
                $.messager.alert('Warning', XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);

            }
        });
    },
    /*学生申请岗位*/
    apply: function(query) {
        $.ajax({
            type: 'POST',
            async: false,
            url: this.url.apply,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(query),
            dataType: 'json',
            success: function(data) {
                $.messager.alert('Warning', data.message);
                $('#dlg').dialog('close');
                $('#tt').datagrid('reload');
            },
            error: function(XMLHttpRequest, textStatus, errorThrowna) {
                $.messager.alert('Warning', XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
            }
        });
    },
    /*判断状态，并返回*/
    getStatus: function(status, rows, type) {
        if (rows.length === 0) {
            return false;
        }
        var res = {
            url: '',
            flag: true,
            tips: ''
        };
        var index = '';
        switch (type) {
            case 0:
                res.url = this.url.adminAgree + '?' + "stuApplicationList=";
                res.tips = '请确认是否为待审核状态';
                index = 'adminStatus';
                break;
            case 1:
                res.url = this.url.adminDeny + '?' + "stuApplicationList=";
                res.tips = '请确认是否为待审核状态';
                index = 'adminStatus';
                break;
            case 2:
                res.url = this.url.deptAgree + '?' + "stuApplicationList=";
                res.tips = '请确认是否为待审核状态';
                index = 'deptStatus';
                break;
            case 3:
                res.url = this.url.deptDeny + '?' + "stuApplicationList=";
                res.tips = '请确认是否为待审核状态';
                index = 'deptStatus';
                break;
            case 4:
                res.url = this.url.finalAgree + '?' + "stuApplicationList=";
                res.tips = '请确认是否为最终待审核状态';
                index = 'statusName';
                break;
            case 5:
                res.url = this.url.finalDeny + '?' + "stuApplicationList=";
                res.tips = '请确认是否为最终待审核状态';
                index = 'statusName';
                break;
        }
        var res_flag = this.getFlag(status, rows, index);
        res.tips = res_flag.tips;
        res.flag = res_flag.flag;
        res.url = res.url + res_flag.ids;
        //res.url = res.url + res_flag.ids.substring(0, res_flag.ids.length - 1);
        //console.log(res);
        return res;
    },
    getFlag: function(status, rows, index) {
        var res = {
            tips: '',
            flag: true,
            ids: ''
        }
        //console.log(rows);
        $.each(rows, function(i, val) {
            res.ids = val.id + ',' + res.ids
            if (status.length === 1) {
                if (val.adminStatus !== 0) {
                    res.tips = '请确认资料审核状态是否为待审核状态！';
                    res.flag = false;
                }
            } else if (status.length === 2) {
                if(val.adminStatus !== 2 || val.deptStatus !== 1){
                    res.tips = '请确认资料审核状态是否通过或者学院/部门状态是否为待审核状态！';
                    res.flag = false;
                }
            } else if(status.length === 3){
                if(val.adminStatus !== 2 || val.deptStatus !== 4 || val.status !== 6){
                    res.tips = '请确认资料审核状态是否通过或者学院/部门状态是否通过，或者终审状态是否为待审核状态！';
                    res.flag = false;
                }
            }
        });
        return res;
    },
    /*查看学生是否上传了简历*/
    checkFile: function(appId) {
        var dtd = new $.Deferred();
        var _self = this;
        var result = null;
        $.ajax({
            type: 'POST',
            async: false,
            url: this.url.checkFile + '?appId=' + appId,
            contentType: "application/json; charset=utf-8",
            // data: JSON.stringify(query),
            dataType: 'json',
        }).done(
            function(data) {
                //console.log(data)
                return result = data;
            }
        );
        return result;
    }
}
var result = Student.checkFile(24)
console.log(result);