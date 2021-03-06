var test_menus = {
    "menus": [{
            "menuid": "1",
            "icon": "icon-sys",
            "menuname": "系统管理",
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
                    "menuid": "15",
                    "menuname": "助管信息管理",
                    "icon": "icon-set",
                    "url": "./zhuguan-eayui.html"
                }, {
                    "menuid": "16",
                    "menuname": "设岗招聘阶段日期控制",
                    "icon": "icon-set",
                    "url": "gw_deadline.html"
                }

            ]
        }, {
            "menuid": "2",
            "icon": "icon-sys",
            "menuname": "月考核管理",
            "menus": [{
                "menuid": "8",
                "icon": "icon-nav",
                "menuname": "管理员功能",
                "menus": [{
                    "menuid": "14",
                    "menuname": "日期与金额管理",
                    "icon": "icon-role",
                    // "url": "./front-end/subform-month.html"
                    "url": "./month-eayui.html"

                }, {
                    "menuid": "21",
                    "menuname": "补助信息审核",
                    "icon": "icon-nav",
                    // "url": "shenhe.html"
                    "url": "./shenhe-eayui.html"

                }, {
                    "menuid": "22",
                    "menuname": "审核数据导出",
                    "icon": "icon-nav",

                    "url": "./shenheStatus-eayui.html"

                }]
            }, {
                "menuid": "56",
                "icon": "icon-nav",
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
            }]
        }, {
            "menuid": "56",
            "icon": "icon-sys",
            "menuname": "设岗管理",
            "menus": [{
                    "menuid": "1",
                    "icon": "icon-nav",
                    "menuname": "管理员功能",
                    "menus": [{
                        "menuid": "12",
                        "menuname": "学院／部门岗位指导",
                        "icon": "icon-role",
                        "url": "gang-wei.html"
                    }, {
                        "menuid": "13",
                        "menuname": "设岗信息审核与导出",
                        "icon": "icon-role",
                        "url": "gw_shenhe.html"
                    }, ]
                }, {
                    "menuid": "2",
                    "icon": "icon-nav",
                    "menuname": "学院／部门用户",
                    "menus": [{
                        "menuid": "13",
                        "menuname": "岗位申请",
                        "icon": "icon-users",
                        "url": "she-gangDeptAdmin.html"
                    }, {
                        "menuid": "13",
                        "menuname": "本部门已有岗位",
                        "icon": "icon-users",
                        "url": "shegang-fabu.html"
                    }]
                }

            ]
        },
        /*
        {
          "menuid": "56",
        "icon": "icon-sys",
        "menuname": "招聘录用管理",
        "menus": [{
         "menuid": "1",
       
        "icon": "icon-nav",
        "menuname": "管理员功能",
        "menus":[{

        }]},{
        "menuid": "1",
       
        "icon": "icon-nav",
        "menuname": "学院/部门用户",
        "menus":[{

        }]},{
        "menuid": "1",
       
        "icon": "icon-nav",
        "menuname": "学生",
        "menus":[{

        }]}

        ] 
         
        },*/

        {
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
                "menuname": "管理员日志信息查询",
                "icon": "icon-nav",
                // "url": "shenhe.html"
                "url": "./html_kaohe/adminLog-eayui.html"

            }]
        },
    ]
};
var admin_menus = {
    "menus": [{
            "menuid": "1",
            "icon": "icon-sys",
            "menuname": "系统管理",
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
                    "menuid": "15",
                    "menuname": "助管信息管理",
                    "icon": "icon-set",
                    "url": "./zhuguan-eayui.html"
                }, {
                    "menuid": "16",
                    "menuname": "设岗招聘阶段日期控制",
                    "icon": "icon-set",
                    "url": "gw_deadline.html"
                }

            ]
        }, {
            "menuid": "2",
            "icon": "icon-sys",
            "menuname": "月考核管理",
            "menus": [{
                    "menuid": "8",
                    "icon": "icon-nav",
                    "menuname": "管理员功能",
                    "menus": [{
                        "menuid": "14",
                        "menuname": "日期与金额管理",
                        "icon": "icon-role",
                        // "url": "./front-end/subform-month.html"
                        "url": "./month-eayui.html"

                    }, {
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
                },

            ]
        }, {
            "menuid": "56",
            "icon": "icon-sys",
            "menuname": "设岗管理",
            "menus": [{
                    "menuid": "1",
                    "icon": "icon-nav",
                    "menuname": "管理员功能",
                    "menus": [{
                            "menuid": "12",
                            "menuname": "学院／部门设岗管理",
                            "icon": "icon-role",
                            "url": "gang-wei.html"
                        }, {
                            "menuid": "13",
                            "menuname": "设岗信息审核管理",
                            "icon": "icon-role",
                            "url": "gw_shenhe.html"
                        },

                    ]
                },

            ]
        },

        /*{
          "menuid": "56",
        "icon": "icon-sys",
        "menuname": "招聘录用管理",
        "menus": [{
         "menuid": "1",
       
        "icon": "icon-nav",
        "menuname": "管理员功能",
        "menus":[{

        }]},{
        "menuid": "1",
       
        "icon": "icon-nav",
        "menuname": "学生",
        "menus":[{

        }]}

        ] 
         
        },*/
    ]
};
var teacher_menus = {
    "menus": [{
            "menuid": "2",
            "icon": "icon-sys",
            "menuname": "月考核管理",
            "menus": [

                {
                    "menuid": "56",
                    "icon": "icon-nav",
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
                }
            ]
        }, {
            "menuid": "56",
            "icon": "icon-sys",
            "menuname": "设岗管理",
            "menus": [{
                    "menuid": "2",
                    "icon": "icon-nav",
                    "menuname": "学院／部门用户",
                    "menus": [{
                        "menuid": "13",
                        "menuname": "岗位申请",
                        "icon": "icon-users",
                        "url": "she-gangDept.html"
                    }, {
                        "menuid": "13",
                        "menuname": "本部门已有岗位",
                        "icon": "icon-users",
                        "url": "shegang-fabu.html"
                    }]
                }

            ]
        },
        /*{
          "menuid": "56",
        "icon": "icon-sys",
        "menuname": "招聘录用管理",
        "menus": [{
        "menuid": "1",
       
        "icon": "icon-nav",
        "menuname": "学院/部门用户",
        "menus":[{

        }]},{
        "menuid": "1",
       
        "icon": "icon-nav",
        "menuname": "学生",
        "menus":[{

        }]}

        ] 
         
        },*/

        {
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
        },
    ]
};