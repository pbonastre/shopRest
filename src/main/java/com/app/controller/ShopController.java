package com.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.domain.ErrorResponse;
import com.app.domain.Shop;
import com.app.exception.ShopException;
import com.app.utils.UtilsShop;

@RestController
public class ShopController {

	private static HashMap<String, Shop> shopHash = new HashMap<>();

	@RequestMapping(value = "/shops", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Shop> shopList() {
		return shopHash;
	}

	@RequestMapping(value = "/shops", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseStatus(code=HttpStatus.CREATED)
	@ResponseBody
	public String add(@RequestBody Shop shop) {
		String shopStatus = new String("Shop is has not been created");
		if (shop != null) {
			shopStatus = UtilsShop.addOrUpdateShop(shop.getNameId(), shop.getShopAddress(), shopHash);
			UtilsShop.updateGeolocation(shopHash.get(shop.getNameId()));
		}

		
		return shopStatus;

	}

	@RequestMapping(value = "/distance", method = RequestMethod.GET)
	@ResponseBody
	public String nearestShop(@RequestParam Map<String, String> requestParams) {
		String latitude = requestParams.get("latitude");
		String longitude = requestParams.get("longitude");
		
		return(UtilsShop.nearestShop(latitude, longitude, shopHash));
	}

	@ExceptionHandler(ShopException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);

	}

}
