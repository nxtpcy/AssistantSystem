function getStatus(argument) {
    console.log('getStatus')
    $.ajax({
        type: "GET",
        url: baseUrl + "usr/user/getUserStatus.do",
        contentType: "application/json; charset=utf-8",
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        async: false,
        success: function(result) {
            if (result) {
                if (result.status == 1) {
                    /*设置全局变量角色，deptID,LoginName*/
                    localStorage.role = result.body.role;
                    if (result.body.deptId) {
                        //设置部门
                        localStorage.deptId = result.body.deptId;

                    }
                    localStorage.loginName = result.body.loginName;
                    localStorage.id = result.body.id;

                    /*存储用户会话信息LoginName,id,teacherName,techerphone,teacherEmail,
                    以及学生的学号、姓名*/
                    sessionStorage.setItem('loginName', result.body.loginName);
                    sessionStorage.setItem('id', result.body.id);
                    sessionStorage.setItem('teacherName', result.body.teacherName);
                    sessionStorage.setItem('teacherPhone', result.body.teacherPhone);
                    sessionStorage.setItem('teacherEmail', result.body.teacherEmail);
                    if (result.body.stuId) {
                        sessionStorage.setItem('stuId', result.body.stuId);
                        sessionStorage.setItem('stuName', result.body.stuName);
                    }
                } else {
                    // 如果用户为空就打开登录界面
                    if (!localStorage.loginName) {
                        alert(result.message);
                        window.location.href = './login.html';
                    }
                    console.log(result);
                    if (result.status == -3) {
                        alert(result.message);
                        window.location.href = './login.html';
                    }
                    return false;
                }
            } else {
                console.log(result);
                alert(result);
            }
        },
        error: function(message) {
            alert(message);
        }
    });
}