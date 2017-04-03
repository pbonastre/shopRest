package com.app.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.domain.GoogleGeoResult;
import com.app.domain.Shop;
import com.app.domain.ShopAddress;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

public class UtilsShop {

	private static final Logger log = LoggerFactory.getLogger(UtilsShop.class);
	static final String YOUR_API_KEY = "AIzaSyBciQ0h0KlwGtLoG9my9u2xmiaUBiCmg0w";
	private static final String template = "The nearest shop adress is %s, with Latitude %s and Longitude %s";

	public static String addOrUpdateShop(final String shopId, final ShopAddress address,
			final HashMap<String, Shop> shopList) {
		return addOrUpdateShopToHash(shopId, address, shopList);

	}

	public static void updateGeolocation(final Shop shop) {
		updateLongAndLat(shop);

	}

	public static String nearestShop(final String latitude, final String longitude,
			final HashMap<String, Shop> shopList) {
		return (findTheNearestShop(latitude, longitude, shopList));
	}

	private static String addOrUpdateShopToHash(final String shopId, final ShopAddress address,
			final HashMap<String, Shop> shopList) {
		Shop shop = shopList.get(shopId);
		String status = new String();
		if (shop == null) {
			createNewShop(shopId, address, shopList);
			status = ShopStateEnum.NEW.getshopStatus();
		} else {
			updateAddressFromShop(shop, address);
			status = ShopStateEnum.UPDATE.getshopStatus();
		}
		return status;
	}

	private static void createNewShop(final String shopId, final ShopAddress address,
			final HashMap<String, Shop> shopList) {
		shopList.put(shopId, new Shop(shopId, address));
	}

	private static void updateAddressFromShop(Shop shop, ShopAddress address) {
		shop.setShopAddress(address);

	}

	private static void updateLongAndLat(Shop shop) {
		GeoApiContext context = new GeoApiContext().setApiKey(YOUR_API_KEY);
		GeocodingResult[] results;

		try {

			results = GeocodingApi.geocode(context, shop.getShopAddress().getPostCode()).await();
			GoogleGeoResult geoResult = new GoogleGeoResult(results[0].formattedAddress,
					String.valueOf(results[0].geometry.location.lat), String.valueOf(results[0].geometry.location.lng));
			shop.setGoogleGeoResult(geoResult);

		} catch (ApiException e) {
			log.error("ApiException in UtilsShop updateLongAndLat obtaining the coordinate values", e);
		} catch (InterruptedException e) {
			log.error("InterruptedException in UtilsShop updateLongAndLat obtaining the coordinate values", e);
		} catch (IOException e) {
			log.error("IOException in UtilsShop updateLongAndLat obtaining the coordinate values", e);
		}

	}

	private static String findTheNearestShop(String latitude, String longitude, HashMap<String, Shop> shopList) {
		String nearestShop = new String("There is no shops in the shop List");
		String shopKeyFinal = null;
		double distance;
		Double minimunDistanceBetweenShops = null;

		Iterator<Entry<String, Shop>> iterator = shopList.entrySet().iterator();
		while (iterator.hasNext()) {

			Entry<String, Shop> entry = iterator.next();
			Shop shop = (Shop) entry.getValue();

			distance = calculateDistance(latitude, longitude, shop);
			boolean isNotDefinedDistance = minimunDistanceBetweenShops == null;
			if (isNotDefinedDistance) {
				minimunDistanceBetweenShops = distance;
				shopKeyFinal = entry.getKey();
			} else if (distance < minimunDistanceBetweenShops) {
				minimunDistanceBetweenShops = distance;
				shopKeyFinal = entry.getKey();
			}
		}

		nearestShop = buildShopMessage(shopList, nearestShop, shopKeyFinal);

		return nearestShop;

	}

	private static String buildShopMessage(HashMap<String, Shop> shopList, String nearestShop, String shopKeyFinal) {
		if (shopKeyFinal != null) {
			nearestShop = prepareShopMessage(shopList.get(shopKeyFinal));
		}
		return nearestShop;
	}

	private static String prepareShopMessage(Shop shop) {

		return String.format(template, shop.getGoogleGeoResult().getFormatted_address(),
				shop.getGoogleGeoResult().getLat(), shop.getGoogleGeoResult().getLng());
	}

	private static double calculateDistance(String latitude, String longitude, Shop shop) {
		return Haversine.distance(Double.parseDouble(latitude), Double.parseDouble(longitude),
				Double.parseDouble(shop.getGoogleGeoResult().getLat()),
				Double.parseDouble(shop.getGoogleGeoResult().getLng()));
	}
}
