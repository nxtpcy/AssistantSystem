var test_menus = {
    "menus": [{
        "menuid": "1",
        "icon": "icon-sys",
        "menuname": "系统设置功能",
        "menus": [{
            "menuid": "12",
            "menuname": "学院／部门信息管理",
            "icon": "icon-role",
            "url": "dept-eayui.html"
        }, {
            "menuid": "13",
            "menuname": "用户信息管理",
            "icon": "icon-users",

            "url": "user-eayui.html"
        }, {
            "menuid": "14",
            "menuname": "日期与金额管理",
            "icon": "icon-role",
            // "url": "./front-end/subform-month.html"
            "url": "./month-eayui.html"

        }, {
            "menuid": "15",
            "menuname": "助管信息管理",
            "icon": "icon-set",
            "url": "./zhuguan-eayui.html"
        }, {
            "menuid": "31",
            "menuname": "学生登录信息管理",
            "icon": "icon-nav",
            "url": "./html_stu/stu_login.html"

        }]
    }, {
        "menuid": "8",
        "icon": "icon-sys",
        "menuname": "管理员功能",
        "menus": [{
            "menuid": "21",
            "menuname": "补助信息审核",
            "icon": "icon-nav",
            // "url": "shenhe.html"
            "url": "./shenhe-eayui.html"

        }, {
            "menuid": "22",
            "menuname": "审核数据导出",
            "icon": "icon-nav",
            // "url": "shenheStatus.html"
            "url": "./shenheStatus-eayui.html"

        }]
    }, {
        "menuid": "9",
        "icon": "icon-sys",
        "menuname": "日志管理",
        "menus": [{
            "menuid": "21",
            "menuname": "日志信息查询",
            "icon": "icon-nav",
            // "url": "shenhe.html"
            "url": "./log-eayui.html"

        }, {
            "menuid": "21",
            "menuname": "管理员日志管理",
            "icon": "icon-nav",
            // "url": "shenhe.html"
            "url": "./html_kaohe/adminLog.html"

        }]
    }, {
        "menuid": "56",
        "icon": "icon-sys",
        "menuname": "学院／部门用户",
        "menus": [{
            "menuid": "33",
            "menuname": "本部门助管信息管理",
            "icon": "icon-nav",
            // "url": "demo1.html"
            "url": "./zhuguanDept-eayui.html"

        }, {
            "menuid": "31",
            "menuname": "申请补助发放",
            "icon": "icon-nav",
            "url": "./buzhu-eayui.html"

        }, {
            "menuid": "32",
            "menuname": "历史补助补发",
            "icon": "icon-nav",
            "url": "./bufa-eayui.html"

        }, {
            "menuid": "33",
            "menuname": "查看审核状态",
            "icon": "icon-nav",
            // "url": "demo1.html"
            "url": "./shenheStatusDept-eayui.html"

        }]
    }, {
        "menuid": "56",
        "icon": "icon-sys",
        "menuname": "学生功能",
        "menus": [{
            "menuid": "30",
            "menuname": "岗位申请",
            "icon": "icon-nav",
            "url": "./html_stu/stu_job_apply.html"

        }, {
            "menuid": "30",
            "menuname": "已申请岗位查看",
            "icon": "icon-nav",
            "url": "./html_stu/stu_job_stu.html"

        },
         {
            "menuid": "32",
            "menuname": "调查问卷",
            "icon": "icon-nav",
            "url": "./questionaire.html"

        },
        {
            "menuid": "32",
            "menuname": "问卷预览",
            "icon": "icon-nav",
            "url": "./modal/techQuestionaire.html"

        }

        ]
    }, {
        "menuid": "56",
        "icon": "icon-sys",
        "menuname": "学生聘任管理(超级管理员)",
        "menus": [{
            "menuid": "30",
            "menuname": "聘任审核",
            "icon": "icon-nav",
            "url": "./html_stu/stu_job.html"

        }]
    }, {
        "menuid": "56",
        "icon": "icon-sys",
        "menuname": "学生聘任管理(部门管理员)",
        "menus": [{
            "menuid": "30",
            "menuname": "聘任审核",
            "icon": "icon-nav",
            "url": "./html_stu/stu_job_dept.html"

        }]
    }]
};
var admin_menus = {
    "menus": [{
        "menuid": "1",
        "icon": "icon-sys",
        "menuname": "系统设置功能",
        "menus": [{
            "menuid": "12",
            "menuname": "学院／部门信息管理",
            "icon": "icon-role",
            "url": "dept-eayui.html"
        }, {
            "menuid": "13",
            "menuname": "用户信息管理",
            "icon": "icon-users",
            // "url": "./front-end/subform-user.html"
            "url": "user-eayui.html"
        }, {
            "menuid": "14",
            "menuname": "日期与金额管理",
            "icon": "icon-role",
            // "url": "./front-end/subform-month.html"
            "url": "./month-eayui.html"

        }, {
            "menuid": "15",
            "menuname": "助管信息管理",
            "icon": "icon-set",
            "url": "./zhuguan-eayui.html"
        }, {
            "menuid": "31",
            "menuname": "学生登录信息管理",
            "icon": "icon-nav",
            "url": "./html_stu/stu_login.html"

        }]
    }, {
        "menuid": "8",
        "icon": "icon-sys",
        "menuname": "管理员功能",
        "menus": [{
            "menuid": "21",
            "menuname": "补助信息审核",
            "icon": "icon-nav",

            "url": "./shenhe-eayui.html"

        }, {
            "menuid": "22",
            "menuname": "审核状态查看",
            "icon": "icon-nav",
            "url": "./shenheStatus-eayui.html"

        }]
    }, {
        "menuid": "56",
        "icon": "icon-sys",
        "menuname": "学生聘任管理",
        "menus": [{
            "menuid": "30",
            "menuname": "聘任审核",
            "icon": "icon-nav",
            "url": "./html_stu/stu_job.html"

        }]
    }]
};
var teacher_menus = {
    "menus": [{
        "menuid": "56",
        "icon": "icon-sys",
        "menuname": "学院／部门用户",
        "menus": [{
            "menuid": "30",
            "menuname": "本部门助管信息管理",
            "icon": "icon-nav",
            // "url": "demo1.html"
            "url": "./zhuguanDept-eayui.html"

        }, {
            "menuid": "31",
            "menuname": "申请补助发放",
            "icon": "icon-nav",
            "url": "./buzhu-eayui.html"

        }, {
            "menuid": "32",
            "menuname": "历史补助补发",
            "icon": "icon-nav",
            "url": "./bufa-eayui.html"

        }, {
            "menuid": "33",
            "menuname": "查看审核状态",
            "icon": "icon-nav",
            // "url": "demo1.html"
            "url": "./shenheStatusDept-eayui.html"

        }, ]
    }, {
        "menuid": "9",
        "icon": "icon-sys",
        "menuname": "日志管理",
        "menus": [{
            "menuid": "21",
            "menuname": "日志信息查询",
            "icon": "icon-nav",
            // "url": "shenhe.html"
            "url": "./log-eayui.html"

        }]
    }, {
        "menuid": "56",
        "icon": "icon-sys",
        "menuname": "学生聘任管理",
        "menus": [{
            "menuid": "30",
            "menuname": "聘任审核",
            "icon": "icon-nav",
            "url": "./html_stu/stu_job_dept.html"

        }]
    }]
};
var stu_menus = {
    "menus": [{
        "menuid": "56",
        "icon": "icon-sys",
        "menuname": "学生功能",
        "menus": [{
            "menuid": "30",
            "menuname": "岗位申请",
            "icon": "icon-nav",
            "url": "./html_stu/stu_job_apply.html"

        }, {
            "menuid": "30",
            "menuname": "已申请岗位查看",
            "icon": "icon-nav",
            "url": "./html_stu/stu_job_stu.html"

        }, 
        // {
        //     "menuid": "32",
        //     "menuname": "调查问卷",
        //     "icon": "icon-nav",
        //     "url": "./modal/stuQuestionaire.html"

        // }
        ]
    }]
};