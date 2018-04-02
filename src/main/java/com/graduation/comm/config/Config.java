package com.graduation.comm.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class Config{
		
	@Value("${favorites.file.save.path}")
	private String savePath;
	@Value("${favorites.file.access.url}")
	private String accessUrl;

}