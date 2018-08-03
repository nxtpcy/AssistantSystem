function checkInfoComplete() {
	if (parseInt(localStorage.isComplete) === 0 && parseInt(localStorage.needComleteInfo) === 1) {
		return false
	}
	return true;
}

//更新是否需要完成信息
function updateNeedComplete(status) {
	localStorage.needComleteInfo = status;
	console.log(localStorage.needComleteInfo);

	var query = {
		// id: "1",
		key: "DeptUserInfoComplete",
		value: String(status)
	}
	$.ajax({
		type: 'POST',
		async: false,
		url: url_module_update + "?key=" + query.key + "&value=" + query.value,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: JSON.stringify(query),
		dataType: 'json',
		success: function(data) {
			$.messager.alert('Warning', data.message);
		},
		error: function() {
			// error.apply(this, arguments);
		}
	});
}
// 查询是否强制信息完善
function checkNeedComplete() {
	console.log('checkInfoComplete');
	var query = {
		key: "DeptUserInfoComplete"
	}
	$.ajax({
		type: 'POST',
		async: false,
		url: url_module_query + "?key=" + "DeptUserInfoComplete",
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: JSON.stringify(query),
		dataType: 'json',
		success: function(data) {

			console.log(data.status);
			localStorage.needComleteInfo = data.body;
			return data.body;
		},
		error: function() {
			// error.apply(this, arguments);
		}
	});
}