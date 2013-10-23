package softeng206.A3;

public class Contact {
	private String firstName;
	private String lastName;
	private String mobilePh;
	private String homePh;
	private String workPh;
	private String emailAddress;
	private int birthYear;
	private int birthMonth;
	private int birthDay;
	
	Contact(String firstName){
		this.firstName=firstName;
	}
	Contact(String firstName, String lastName){
		this.firstName=firstName;
		this.lastName=lastName;
	}
	Contact(String firstName, String lastName, String mobilePh, String homePh, String workPh, String emailAddress, int birthYear, int birthMonth, int birthDay){
		this.firstName=firstName;
		this.lastName=lastName;
		this.mobilePh=mobilePh;
		this.homePh=homePh;
		this.workPh=workPh;
		this.emailAddress=emailAddress;
		this.birthYear=birthYear;
		this.birthMonth=birthMonth;
		this.birthDay=birthDay;
	}
	public String getName() {
		
		return firstName+"   "+lastName;
	}

}
