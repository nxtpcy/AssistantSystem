require.config({
    paths: {
        "common": "common/common",
        // "md5":"dist/md5.min.js"
    }
    // baseUrl: 'js/common'
});

require(['common'], function(common) {
    var hash = md5("value"); // "2063c1608d6e0baf80249c42e2be5804"
    console.log('hash' + hash);
    var baseUrl = common.defaultStr();
    var month = new Vue({
        el: '#login-container',
        data: {
            url: {
                // 'login': baseUrl + '/usr/user/login.do'
                'login': baseUrl + '/usr/user/shiroLogin.do',
                'logout': baseUrl + '/usr/user/shiroLogout.do'
            },
            user: {
                "role": 0,
                "LoginName": '',
                "pwd": ''
            }
        },
        methods: {
            login: function() {
                var _self = this;
                // _self.user.pwd = md5(_self.user.pwd);
                // console()
                _self.delAllCookie();

                // var b = new Base64();
                // var str = b.btoa("admin:admin");

                test = {
                    LoginName: _self.user.LoginName,
                    pwd: btoa(md5(_self.user.pwd)),
                    role: _self.user.role
                }
                console.log(test)
                $.ajax({
                    type: "POST",
                    url: _self.url.login + "?loginName=" + test.LoginName + "&password=" + test.pwd + "&role=" + test.role,
                    contentType: "application/json; charset=utf-8",
                    dataType: 'json',
                    xhrFields: {
                        withCredentials: true
                    },
                    crossDomain: true,
                    success: function(result) {
                        if (result.status == 1) {
                            alert(result.message);
                            // alert(result.body);
                            // _self.setCookie("uuid", result.body);
                            // alert(document.cookie)
                            setTimeout(function() {
                                _self.checkStatus()
                                window.location.replace("./index.html");
                            }, 1000);
                        } else {
                            alert(result.message);
                        }

                    }
                });
            },
            checkStatus: function() {
                console.log(document.cookie)
                $.ajax({
                    type: "GET",
                    url: "http://115.29.136.190:8080/help/usr/user/getUserStatus.do",
                    contentType: "application/json; charset=utf-8",
                    dataType: 'json',
                    xhrFields: {
                        withCredentials: true
                    },
                    crossDomain: true,
                    success: function(result) {
                        if (result.status == 1) {
                            alert(result.message);

                        } else {
                            alert(result.message);
                        }

                    }
                });
            },
            delAllCookie: function() {
                var myDate = new Date();
                myDate.setTime(-1000); //设置时间    
                var data = document.cookie;
                var dataArray = data.split("; ");
                for (var i = 0; i < dataArray.length; i++) {
                    var varName = dataArray[i].split("=");
                    document.cookie = varName[0] + "=''; expires=" + myDate.toGMTString();
                }

            },
            setCookie: function(c_name, value, expiredays) {
                var exdate = new Date()
                exdate.setDate(exdate.getDate() + expiredays)
                document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
            }
        }
    })
})