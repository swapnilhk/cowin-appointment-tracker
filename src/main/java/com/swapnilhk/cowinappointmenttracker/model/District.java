package com.swapnilhk.cowinappointmenttracker.model;

public enum District {

	PUNE(363),
	NAGPUR(365),
	NASHIK(389);
	
	private int id;

	District(int id){
		this.id = id;
	}

	public int getId() {
		return id;
	}
}

