package com.craig.itemgame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.craig.itemgame.model.Config;

@SpringBootApplication
public class ItemgameApplication {

	public static void main(String[] args) {
		Config.WORKING_DIR = "../desktop/";
		SpringApplication.run(ItemgameApplication.class, args);
	}

}
