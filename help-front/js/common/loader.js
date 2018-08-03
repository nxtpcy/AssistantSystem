/*与loader相关的函数*/

// datagrid 
var dloader = function(param, success, error) {
    // console.log($(this))
    var opts = $(this).datagrid('options');
    if (!opts.url) return false;
    $.ajax({
        type: opts.method,
        url: opts.url,
        contentType: "application/json; charset=utf-8",
        // data: JSON.stringify(query),
        dataType: 'json',
        success: function(data) {
            if (data) {
                console.log(data);
                success(data.body); // used the data.rows array to fill combobox list
            } else {
                success(data);
            }
        },
        error: function() {
            error.apply(this, arguments);
        }
    });
}
var jloader = function(param, success, error) {
    var opts = $(this).datagrid('options');
    console.log($(this));
    if (!opts.url) return false;
    $.ajax({
        type: opts.method,
        url: opts.url,
        contentType: "application/json; charset=utf-8",
        // data: JSON.stringify(query),
        dataType: 'json',
        success: function(data) {
            if (data.status == 1) {
                console.log(data);
                var temp = []
                temp.push(data.body)
                success(temp); // used the data.rows array to fill combobox list
            } else {
                success(data);
            }
        },
        error: function() {
            error.apply(this, arguments);
        }
    });
}

//combobox-loader
/*用来处理dept查询和*/
var cloader = function(param, success, error) {
    var opts = $(this).combobox('options');
    if (!opts.url) return false;
    var query = {
        rows: 200,
        page: 1
    }
    param = param || JSON.stringify(query)
    $.ajax({
        type: opts.method,
        url: opts.url,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(query),
        dataType: 'json',
        success: function(data) {
            if (data.result) {
                var temp = [{
                    "deptId": "",
                    "deptName": "全部"
                }];
                $.each(data.result, function(i, val) {
                    val.deptName = val.deptId + val.deptName;
                    temp.push(val);
                })
                success(temp);
            } else {
                success(data);
            }
        },
        error: function() {
            error.apply(this, arguments);
        }
    });
}

var dateloader = function(param, success, error) {
    // var opts = $(this).datagrid('options');
    var opts = $(this).combobox('options');
    var helpDate = [];
    if (!opts.url) return false;
    $.ajax({
        type: opts.method,
        url: opts.url,
        contentType: "application/json; charset=utf-8",
        // data: JSON.stringify(query),
        dataType: 'json',
        success: function(data) {
            if (data) {
                $.each(data.body, function(i, val) {
                    if (val.helpMonth < 10) {
                        var temp = {
                            "helpDate": val.helpYear + '0' + val.helpMonth
                        }
                    } else {
                        var temp = {
                            "helpDate": val.helpYear + '' + val.helpMonth
                        }
                    }
                    helpDate.push(temp);
                })
                success(helpDate); // used the data.rows array to fill combobox list
            } else {
                success(data);
            }
        },
        error: function() {
            error.apply(this, arguments);
        }
    });
}