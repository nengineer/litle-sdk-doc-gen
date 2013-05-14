package extracter;

import java.util.ArrayList;
import java.util.List;

public class DataExtracterForJava implements DataExtracter{

	String data;
	List<String> dataList = new ArrayList<String>();
	
	
	
	@Override
	public void extractData(Employee e) {
		// TODO Auto-generated method stub
		DataExtracterForJava dx = this;
		
		dx.getDList().add("Employee Id is : " + String.valueOf(e.getId()));
		
		dx.getDList().add("Employee first name is : " + e.getFirstName());
		
		dx.getDList().add("Employee last name is : " + e.getLastName());
		
		dx.getDList().add("Employee nick name is : " + e.getNickName());
		
		dx.getDList().add(String.valueOf("Employee salary is : " + e.getSalary()));

		
	}

	@Override
	public void createData() {
		// TODO Auto-generated method stub
		
		DataExtracterForJava dx = this;
		
		dx.setData("/**" + "\n");
		
		for(String s : dx.getDList()){
			
			dx.setData(dx.getData() + "  * " + s + "\n");
		}
		
		dx.setData(dx.getData() + "  **/");
	}
	
	public void setData(String d){
		this.data = d;
	}

	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return this.data;
	}
	
	public List<String> getDList(){
		return this.dataList;
	}

}
