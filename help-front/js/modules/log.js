/*处理与开发者日志相关的增删查改*/
// var baseUrl = 'http://115.29.136.190:8080/help/';

var adminLog = {
    url: {
        query: baseUrl + 'usr/developlog/list.do',
        add: baseUrl + 'usr/developlog/add.do',
        update: baseUrl + 'usr/developlog/update.do',
        delete: baseUrl + 'usr/developlog/delete.do'
    },
    getFunc: function() {
        var query = {
            "rows": 20,
            "page": 1,
        }
        $.ajax({
            type: 'POST',
            async: false,
            url: this.url.query,
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            data: JSON.stringify(query),
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
    addFuc: function(loginName, role, logDesc, dg) {
        console.log(this.url.add);
        var query = {
            loginName: loginName,
            logDesc: logDesc,
            userRole: role,
            logDate: dg
        };
        console.log(query)
        $.ajax({
            type: 'POST',
            async: false,
            // url: this.url.add + '?loginName=' + loginName + '&userRole=' + role + '&logDesc=' + logDesc,
            url: this.url.add,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(query),
            dataType: 'json',
            success: function(data) {
                $.messager.alert('Warning', data.message);
            },
            error: function(XMLHttpRequest, textStatus, errorThrowna) {
                $.messager.alert('Warning', XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
            }
        });

    },
    deleteFunc: function(id) {
        var query = {
            id: id
        }
        $.ajax({
            type: 'POST',
            async: false,
            url: this.url.delete + "?id=" + id,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(query),
            dataType: 'json',
            success: function(data) {
                $.messager.alert('Warning', data.message);
            },
            error: function(XMLHttpRequest, textStatus, errorThrowna) {
                $.messager.alert('Warning', XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
            }
        });
    },
    updateFuc: function(id, loginName, logDesc) {
        var query = {
            id: id,
            loginName: loginName,
            logDesc: logDesc
        }
        $.ajax({
            type: 'POST',
            async: false,
            // url: this.url.update + "?id=" + id + "&loginName=" + loginName + "&logDesc=" + logDesc,
            url: this.url.update,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(query),
            dataType: 'json',
            success: function(data) {
                $.messager.alert('Warning', data.message);
            },
            error: function(XMLHttpRequest, textStatus, errorThrowna) {
                $.messager.alert('Warning', XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
            }
        });
    }
}

// adminLog.addFuc("test", "增加用户")