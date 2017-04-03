package com.app.domain;


public class Shop {

	private String nameId;
	private ShopAddress shopAddress;
	private GoogleGeoResult googleGeoResult;

	public Shop() {
	};


	public Shop( String nameId,  ShopAddress shopAddress, GoogleGeoResult googleGeoResult) {
		this.nameId = nameId;
		this.shopAddress = shopAddress;
		this.googleGeoResult = googleGeoResult;
	};


	public Shop(String nameId, ShopAddress shopAddress) {
		super();
		this.nameId = nameId;
		this.shopAddress = shopAddress;
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public ShopAddress getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(ShopAddress shopAddress) {
		this.shopAddress = shopAddress;
	}

	public GoogleGeoResult getGoogleGeoResult() {
		return googleGeoResult;
	}

	public void setGoogleGeoResult(GoogleGeoResult googleGeoResult) {
		this.googleGeoResult = googleGeoResult;
	}

}
