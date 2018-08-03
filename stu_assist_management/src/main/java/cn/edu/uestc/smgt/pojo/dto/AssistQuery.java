package cn.edu.uestc.smgt.pojo.dto;

public class AssistQuery {
     public String getName() {
		return name;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	private String name;
     private String studentId;
}
