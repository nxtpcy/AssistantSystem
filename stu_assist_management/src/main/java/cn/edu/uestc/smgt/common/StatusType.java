package cn.edu.uestc.smgt.common;

public enum StatusType {

	SUCCESS(1, "成功"), ERROR(-1, "失败"), EXCEPTION(-2, "系统操作异常"), USER_IS_NULL(
			-3, "用户为空"), OBJECT_NULL(-4, "对象为空"), EXISTS(-5, "重复记录"), NOT_EXISTS(
			-6, "记录不存在"), PASSWD_NOT_MATCH(-7, "密码不匹配"), DATE_ERROR(-8,
			"日期不符合规范"), DATA_PARSE_ERROR(-9, "数据解析错误"), DATE_INVALID(-10,
			"不符合有效日期"), MONEY_INVALID(-11, "不符合有效的金额"), PARAMETER_IS_NULL(-12,
			"参数为空"), ROLE_INVALID(-13, "用户角色错误"), UNAUTHORIZED(-14, "验证未通过"), PERMISSION_DENIED(
			-15, "权限不足"), OPERATION_FORBID(-16, "不允许的操作"), KICKOUT(-17,
			"其他设备登录，你被强制退出"), DEADLINE_SUBMIT(-18, "当前提交时间已过期"), DATA_INVALID(
			-19, "输入数据不合法");

	private int val;

	private String msg;

	private StatusType(int val, String desc) {
		this.val = val;
		this.msg = desc;
	}

	public int getVal() {
		return this.val;
	}

	public String getMessage() {
		return this.msg;
	}

	@Override
	public String toString() {
		return super.toString() + "(" + val + "," + msg + ")";
	}

	public static StatusType value(int val) {
		StatusType[] dsTypes = StatusType.values();
		for (int i = 0; i < dsTypes.length; i++) {
			StatusType dsType = dsTypes[i];
			if (val == dsType.getVal()) {
				return dsType;
			}
		}
		return null;
	}
}
