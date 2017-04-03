package com.app.domain;

public class GoogleGeoResult {
	private String formatted_address;
	private String lat;
    private String lng;

	
	public GoogleGeoResult(String formatted_address, String lat,String lng) {
		super();
		this.formatted_address = formatted_address;
		this.lat = lat;
		this.lng = lng;
	}
	
	
	public String getFormatted_address() {
		return formatted_address;
	}
	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	
}
