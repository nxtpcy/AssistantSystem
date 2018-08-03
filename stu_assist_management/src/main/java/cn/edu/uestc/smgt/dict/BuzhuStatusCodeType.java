package cn.edu.uestc.smgt.dict;

public enum BuzhuStatusCodeType {
	NEW(0,"未提交"),
	REVIEW(1,"审核中"),
	AGREE(2,"通过"),
	REJECT(3,"未通过");
	
	private int value;
	private String desc;
	private BuzhuStatusCodeType(int value,String desc){
       this.value = value;
       this.desc = desc;
	}
	public int getVal(){
		return this.value;
	}
    public String getDesc(){
    	return this.desc;
    }
    public static BuzhuStatusCodeType value(int value){
    	BuzhuStatusCodeType[] types = BuzhuStatusCodeType.values();
    	for(BuzhuStatusCodeType type : types){
    		if(type.getVal() == value)
    			return type;
    	}
    	return null;
    }
}
