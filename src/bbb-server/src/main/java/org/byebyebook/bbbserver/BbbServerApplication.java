package org.byebyebook.bbbserver;

import org.byebyebook.bbbserver.config.ImageStorageConfig;
import org.byebyebook.bbbserver.config.PostingsPermissionConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		ImageStorageConfig.class,
		PostingsPermissionConfig.class
})
public class BbbServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BbbServerApplication.class, args);
	}

}
