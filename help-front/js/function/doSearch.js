 var val = $('#cc').combobox('getValues');
 var deptIds = val.join(',')
 var param = {
 	stuId: $('#stuId').val(), //用户名
 	name: $('#name').val(), //用户名
 	bankName: $('#bankName').val(),
 	deptId: deptIds
 }
 doSearch('#tt', param)

 function doSearch(ttid, param) {
 	var val = $('#cc').combobox('getValues');
 	var deptIds = val.join(',')

 	$('#tt').datagrid('load', {
 		stuId: $('#stuId').val(), //用户名
 		name: $('#name').val(), //用户名
 		bankName: $('#bankName').val(),
 		deptId: deptIds //deptName
 	});
 }