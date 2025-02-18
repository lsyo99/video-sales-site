package org.ItBridge.Config.JpaConfig;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "org.ItBridge.db")
@EnableJpaRepositories(basePackages = "org.ItBridge.db")
public class JpaConfig {

}