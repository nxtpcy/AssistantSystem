﻿$(function() {
    

});

// 校验用户
function checkUserAJax() {
    return $.ajax({
        type: "GET",
        url: "http://115.29.136.190:8080/help/usr/user/getUserStatus.do",
        contentType: "application/json; charset=utf-8",
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
    }).done(
        handleSessionTimeOut
    );
}

//处理超时连接
function handleSessionTimeOut(data) {
    if (data && data.retcode === '202') {
        location.reload();
    }
}
    var index_tabs;
    var layout_west_tree;
    var indexTabsMenu;

    function tree(){

        index_tabs = $('#index_tabs').tabs(
                {
                    fit : true,
                    border : false,
                    onContextMenu : function(e, title) {
                        e.preventDefault();
                        indexTabsMenu.menu('show', {
                            left : e.pageX,
                            top : e.pageY
                        }).data('tabTitle', title);
                    },
                    tools : [
                            {
                                iconCls : 'fi-home',
                                handler : function() {
                                    index_tabs.tabs('select', 0);
                                }
                            },
                            {
                                iconCls : 'fi-loop',
                                handler : function() {
                                    refreshTab();
                                }
                            },
                            {
                                iconCls : 'fi-x',
                                handler : function() {
                                    var index = index_tabs.tabs('getTabIndex',
                                            index_tabs.tabs('getSelected'));
                                    var tab = index_tabs.tabs('getTab', index);
                                    if (tab.panel('options').closable) {
                                        index_tabs.tabs('close', index);
                                    }
                                }
                            } ]
                });
        // 选项卡菜单
        indexTabsMenu = $('#tabsMenu').menu(
                {
                    onClick : function(item) {
                        var curTabTitle = $(this).data('tabTitle');
                        var type = $(item.target).attr('type');
                        if (type === 'refresh') {
                            refreshTab();
                            return;
                        }
                        if (type === 'close') {
                            var t = index_tabs.tabs('getTab', curTabTitle);
                            if (t.panel('options').closable) {
                                index_tabs.tabs('close', curTabTitle);
                            }
                            return;
                        }
                        var allTabs = index_tabs.tabs('tabs');
                        var closeTabsTitle = [];
                        $.each(allTabs, function() {
                            var opt = $(this).panel('options');
                            if (opt.closable && opt.title != curTabTitle
                                    && type === 'closeOther') {
                                closeTabsTitle.push(opt.title);
                            } else if (opt.closable && type === 'closeAll') {
                                closeTabsTitle.push(opt.title);
                            }
                        });
                        for (var i = 0; i < closeTabsTitle.length; i++) {
                            index_tabs.tabs('close', closeTabsTitle[i]);
                        }
                    }
                });

        layout_west_tree = $('#layout_west_tree')
                .tree(
                    {
                        url : baseUrl+'resource/tree.do?role='+localStorage.role,
                            parentField : 'pid',
                            lines : true,
                            onClick : function(node) {
                                var opts = {
                                    title : node.text,
                                    border : false,
                                    closable : true,
                                    fit : true,
                                    iconCls : node.iconCls
                                };
                                var url = node.attributes;
                               
                                if(url!=""){
                                    opts.content = '<iframe src="'
                                            + url
                                            + '" frameborder="0" style="border:0;width:100%;height:99.5%;"></iframe>';
                                    addTab(opts);
                                }
                            }
                        });
    }

    function addTab(opts) {
        var t = $('#index_tabs');
        if (t.tabs('exists', opts.title)) {
            t.tabs('select', opts.title);
        } else {
            t.tabs('add', opts);
        }
    }

    function refreshTab() {
        var index = index_tabs.tabs('getTabIndex', index_tabs
                .tabs('getSelected'));
        var tab = index_tabs.tabs('getTab', index);
        var options = tab.panel('options');
        if (options.content) {
            index_tabs.tabs('update', {
                tab : tab,
                options : {
                    content : options.content
                }
            });
        } else {
            tab.panel('refresh', options.href);
        }
    }

// function GetMenuList(data, menulist) {
//     if (data.menus == null)
//         return menulist;
//     else {
//         menulist += '<ul>';
//         $.each(data.menus, function(i, sm) {
//             if (sm.url != null) {

//                 menulist += '<li><a ref="' + sm.menuid + '" href="#" rel="' + sm.url + '" ><span class="nav">' + sm.menuname + '</span></a></li> '
//             }
//             else {
//                 menulist += '<li state="closed"><span class="nav">' + sm.menuname + '</span>'
//             }
//             menulist = GetMenuList(sm, menulist);
//         })
//         menulist += '</ul>';
//     }
//     return menulist;
// }


// //初始化左侧
// function InitLeftMenu(_menus) {
//     $("#nav").accordion({
//         animate: false
//     });
// console.log(_menus);
//     $.each(_menus.menus, function(i, n) {
        
//         var menulist1 = "";
//         //sm 常用菜单  邮件 列表
//         menulist1 = GetMenuList(n, menulist1);
//         menulist1 = "<ul id='tt1' class='easyui-tree' animate='true' dnd='true'>" + menulist1.substring(3); 
    
       

//         $('#nav').accordion('add', {
//             title: n.menuname,
//             content: menulist1,
//             iconCls: 'icon ' + n.icon
//         });

//     });

//     $('.easyui-accordion li a').click(function() {
//         var tabTitle = $(this).children('.nav').text();

//         var url = $(this).attr("rel");
//         var menuid = $(this).attr("ref");
//         var icon = getIcon(menuid, icon);

//         addTab(tabTitle, url, icon);
//         $('.easyui-accordion li div').removeClass("selected");
//         $(this).parent().addClass("selected");
//     }).hover(function() {
//         $(this).parent().addClass("hover");
//     }, function() {
//         $(this).parent().removeClass("hover");
//     });

//     //选中第一个
//     var panels = $('#nav').accordion('panels');
//     var t = panels[0].panel('options').title;
//     $('#nav').accordion('select', t);
// }
// //获取左侧导航的图标
// function getIcon(menuid) {
//     var icon = 'icon ';
//     $.each(_menus.menus, function(i, n) {
//         $.each(n.menus, function(j, o) {
//             if (o.menuid == menuid) {
//                 icon += o.icon;
//             }
//         })
//     })

//     return icon;
// }

// function addTab(subtitle, url, icon) {
//     if (!$('#tabs').tabs('exists', subtitle)) {
//         $('#tabs').tabs('add', {
//             title: subtitle,
//             content: createFrame(url),
//             closable: true,
//             icon: icon
//         });
//     } else {
//         $('#tabs').tabs('select', subtitle);
//         $('#mm-tabupdate').click();
//     }
//     tabClose();
// }

// function createFrame(url) {
//     var s = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
//     return s;
// }

// function tabClose() {
//     /*双击关闭TAB选项卡*/
//     $(".tabs-inner").dblclick(function() {
//             var subtitle = $(this).children(".tabs-closable").text();
//             $('#tabs').tabs('close', subtitle);
//         })
//         /*为选项卡绑定右键*/
//     $(".tabs-inner").bind('contextmenu', function(e) {
//         $('#mm').menu('show', {
//             left: e.pageX,
//             top: e.pageY
//         });

//         var subtitle = $(this).children(".tabs-closable").text();

//         $('#mm').data("currtab", subtitle);
//         $('#tabs').tabs('select', subtitle);
//         return false;
//     });
// }
// //绑定右键菜单事件
// function tabCloseEven() {
//     //刷新
//     $('#mm-tabupdate').click(function() {
//             var currTab = $('#tabs').tabs('getSelected');
//             var url = $(currTab.panel('options').content).attr('src');
//             $('#tabs').tabs('update', {
//                 tab: currTab,
//                 options: {
//                     content: createFrame(url)
//                 }
//             })
//         })
//         //关闭当前
//     $('#mm-tabclose').click(function() {
//             var currtab_title = $('#mm').data("currtab");
//             $('#tabs').tabs('close', currtab_title);
//         })
//         //全部关闭
//     $('#mm-tabcloseall').click(function() {
//         $('.tabs-inner span').each(function(i, n) {
//             var t = $(n).text();
//             $('#tabs').tabs('close', t);
//         });
//     });
//     //关闭除当前之外的TAB
//     $('#mm-tabcloseother').click(function() {
//         $('#mm-tabcloseright').click();
//         $('#mm-tabcloseleft').click();
//     });
//     //关闭当前右侧的TAB
//     $('#mm-tabcloseright').click(function() {
//         var nextall = $('.tabs-selected').nextAll();
//         if (nextall.length == 0) {
//             //msgShow('系统提示','后边没有啦~~','error');
//             alert('后边没有啦~~');
//             return false;
//         }
//         nextall.each(function(i, n) {
//             var t = $('a:eq(0) span', $(n)).text();
//             $('#tabs').tabs('close', t);
//         });
//         return false;
//     });
//     //关闭当前左侧的TAB
//     $('#mm-tabcloseleft').click(function() {
//         var prevall = $('.tabs-selected').prevAll();
//         if (prevall.length == 0) {
//             alert('到头了，前边没有啦~~');
//             return false;
//         }
//         prevall.each(function(i, n) {
//             var t = $('a:eq(0) span', $(n)).text();
//             $('#tabs').tabs('close', t);
//         });
//         return false;
//     });

//     //退出
//     $("#mm-exit").click(function() {
//         $('#mm').menu('hide');
//     })
// }

//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
    $.messager.alert(title, msgString, msgType);
}
