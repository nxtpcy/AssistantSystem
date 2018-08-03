var Reg = (function() {
	var tel_reg = /^1[0-9]\d{9}$/,
		email_reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
		CHS_reg = /^[\u0391-\uFFE5]+$/
	return {
		check: function(str, type) {
			switch (type) {
				case 'tel':
					return tel_reg.test(str)
					break;
				case 'email':
					return email_reg.test(str)
					break;
				case 'CHS':
					return CHS_reg.test(str)
					break;
			}
		}
	}
})();

//调用
var email = '113@11';
var x = Reg.check(email, 'email')
console.log(x); //false