package com.app.utils;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.domain.GoogleGeoResult;
import com.app.domain.Shop;
import com.app.domain.ShopAddress;

public class UtilShopTest {

	private static final String THE_NEAREST_SHOP = "The nearest shop adress is Barlow, Kentucky 42024, EE. UU., with Latitude 39.4471372 and Longitude -0.3348372";
	private static final Logger log = LoggerFactory.getLogger(UtilShopTest.class);
	static final String SHOP_ID = "shop1";
	static final String SHOP_ID2 = "shop2";
	static final String SHOP_ID3 = "shop3";
	static final String SHOP_NUMBER = "1";
	static final String SHOP_NUMBER2 = "2";
	static final String SHOP_NUMBER89 = "89";
	static final String SHOP_POST_CODE = "46022";
	static final String SHOP_POST_CODE2 = "46023";
	static final String SHOP_POST_CODE3 = "46021";
	static final String INITIAL_LAT ="39.4729669";
	static final String INITIAL_LNG ="-0.3536548";
	static final String FINAL_LAT ="39.4471372";
	static final String FINAL_LNG ="-0.3348372";
	static final String OTHER_LAT ="37.149022";
	static final String OTHER_LNG ="-88.943163";
	static final String OTHER_LAT2 ="36.9245629";
	static final String OTHER_LNG2 ="-88.6144839";
	static final String ADRESS ="Barlow, Kentucky 42024, EE. UU.";


	@Test
	public void addOrUpdateShopAddingShopTest() {

		ShopAddress address = new ShopAddress(SHOP_NUMBER, SHOP_POST_CODE);
		HashMap<String, Shop> shopList = new HashMap<>();
		String shopStatus = UtilsShop.addOrUpdateShop(SHOP_ID, address, shopList);
		assertEquals(ShopStateEnum.NEW.getshopStatus(), shopStatus);
	}

	@Test
	public void addOrUpdateShopUpdatingShopTest() {

		HashMap<String, Shop> shopList = new HashMap<>();
		ShopAddress initialshopAddress = new ShopAddress(SHOP_NUMBER, SHOP_POST_CODE);
		Shop initialShop = new Shop(SHOP_ID, initialshopAddress);
		shopList.put(SHOP_ID, initialShop);

		ShopAddress newAddress = new ShopAddress(SHOP_NUMBER2, SHOP_POST_CODE2);

		String shopStatus = UtilsShop.addOrUpdateShop(SHOP_ID, newAddress, shopList);
		assertEquals(ShopStateEnum.UPDATE.getshopStatus(), shopStatus);
		assertEquals(shopList.get(SHOP_ID).getShopAddress().getPostCode(), SHOP_POST_CODE2);
		assertEquals(shopList.get(SHOP_ID).getShopAddress().getNumber(), SHOP_NUMBER2);
	}

	@Test
	public void updateLongAndLatTest() {

		GoogleGeoResult shopPointMaps = new GoogleGeoResult("1600 Amphitheatre Pkwy, Mountain View, CA 94043, EE. UU.",
				null, null);
		ShopAddress shopAddress = new ShopAddress(SHOP_NUMBER89, SHOP_POST_CODE3);
		Shop newshop = new Shop(SHOP_ID, shopAddress, shopPointMaps);
		UtilsShop.updateGeolocation(newshop);
		assertEquals(newshop.getGoogleGeoResult().getLat(), INITIAL_LAT);
		assertEquals(newshop.getGoogleGeoResult().getLng(), INITIAL_LNG);

	}
	
	@Test
	public void findTheNearestShopTest() {
		HashMap<String, Shop> shopList = new HashMap<>();
		
		ShopAddress initialshopAddress = new ShopAddress(SHOP_NUMBER, SHOP_POST_CODE);
		Shop initialShop = new Shop(SHOP_ID, initialshopAddress);
		GoogleGeoResult geoResultSHop = new GoogleGeoResult(ADRESS, OTHER_LAT2, OTHER_LNG2);
		initialShop.setGoogleGeoResult(geoResultSHop);
		shopList.put(SHOP_ID, initialShop);
		
		ShopAddress shopAddress2 = new ShopAddress(SHOP_NUMBER2, SHOP_POST_CODE);
		Shop shop2 = new Shop(SHOP_ID2, shopAddress2);
		GoogleGeoResult geoResultSHop2 = new GoogleGeoResult(ADRESS, FINAL_LAT, FINAL_LNG);
		shop2.setGoogleGeoResult(geoResultSHop2);
		shopList.put(SHOP_ID2, shop2);
	
		
		ShopAddress shopAddress3 = new ShopAddress(SHOP_NUMBER89, SHOP_POST_CODE);
		Shop shop3 = new Shop(SHOP_ID3, shopAddress3);
		GoogleGeoResult geoResultSHop3 = new GoogleGeoResult(ADRESS, OTHER_LAT, OTHER_LNG);
		shop3.setGoogleGeoResult(geoResultSHop3);
		shopList.put(SHOP_ID3, shop3);
		
		String nearestShop = UtilsShop.nearestShop(INITIAL_LAT,INITIAL_LNG,shopList);
	
		assertEquals(nearestShop, THE_NEAREST_SHOP);
		
	}


}
