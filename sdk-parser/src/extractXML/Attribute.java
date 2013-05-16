package extractXML;

public class Attribute {
	
	private String name;
	private String type;
	private boolean required;
	private String description;
	private String minLength;
	private String maxLength;
	private String totalDigits;
	private String validValues;
	
	public Attribute(){
		name = "";
		type = "";
		required = false;
		description = "";
		minLength = "";
		maxLength = "";
		totalDigits = "";
		validValues = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMinLength() {
		return minLength;
	}

	public void setMinLength(String minLength) {
		this.minLength = minLength;
	}

	public String getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}

	public String getTotalDigits() {
		return totalDigits;
	}

	public void setTotalDigits(String totalDigits) {
		this.totalDigits = totalDigits;
	}

	public String getValidValues() {
		return validValues;
	}

	public void setValidValues(String validValues) {
		this.validValues = validValues;
	}
	
	@Override
	public String toString() {
		return "Attribute [name=" + name + ", type=" + type + ", required="
				+ required + ", description=" + description + ", minLength="
				+ minLength + ", maxLength=" + maxLength + ", totalDigits="
				+ totalDigits + ", validValues=" + validValues + "]";
	}
	

}
