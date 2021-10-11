package com.base.communication;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommunicationHandler {

	private List<Communication> pendingData;
	
	public CommunicationHandler() {
		this.pendingData = new ArrayList<>();
	}

	public void communicate(UUID user, Input input) {
		Communication communicate = new Communication(user, input);
		this.communicate(communicate);
	}
	
	public void communicate(Communication communication) {
		this.pendingData.add(communication);
	}

	public List<Communication> getPendingData() {
		
		//remove data if no longer valid;
		this.pendingData.removeIf(i -> !i.isActive());
		
		return pendingData;
	}

	public void setPendingData(List<Communication> pendingData) {
		this.pendingData = pendingData;
	}
	
}
