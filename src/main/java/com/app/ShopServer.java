package com.app;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.domain.Shop;


@SpringBootApplication
public class ShopServer {
	
		Collection<Shop> shops = new ArrayList<Shop>();

	    public static void main(String[] args) {
	    	 SpringApplication.run(ShopServer.class, args);
	    }
}

