package com.digitusforum;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.digitusforum.chat.FactDadosEntity;
import com.digitusforum.chat.FactDadosRepository;

@SpringBootApplication
public class UserApplication {
	
	

	public static void main(String[] args) throws IOException {
		SpringApplication.run(UserApplication.class, args);
		
		
	}

}
