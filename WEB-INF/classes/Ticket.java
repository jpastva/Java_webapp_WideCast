import java.io.Serializable;
/* 
	Ticket class contains variables ticketId, custUsername,technician, and description.

	Ticket class has a constructor with arguments ticketId, custUsername, technician, and description.
	  
	Ticket class contains getters and setters for ticketId, custUsername, technician, and description.
*/

public class Ticket implements Serializable {
	private int ticketId;
	private String custUsername;
	private String technician;
	private String description;
	private boolean activeStatus;
	
	public Ticket(int ticketId, String custUsername, String technician, String description, boolean activeStatus) {
		this.ticketId = ticketId;
		this.custUsername = custUsername;
		this.technician = technician;
		this.description = description;
		this.activeStatus = activeStatus;
	}
	
	public Ticket() {
		ticketId = 0;
		custUsername = "";
		technician = "";
		description = "";
		activeStatus = true;	
	}
	
	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	
	public String getCustUsername() {
		return custUsername;
	}

	public void setCustUsername(String custUsername) {
		this.custUsername = custUsername;
	}

	public String getTechnician() {
		return technician;
	}

	public void setTechnician(String technician) {
		this.technician = technician;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	public boolean getActiveStatus() {
		return activeStatus;
	}
}
