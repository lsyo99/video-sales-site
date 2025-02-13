package org.ItBridge.Config.MultipartConfig;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;


@Configuration
    public class FileUploadConfig {

        @Bean
        public MultipartConfigElement multipartConfigElement() {
            MultipartConfigFactory factory = new MultipartConfigFactory();
            // 단일 파일 최대 크기 (10MB)
            factory.setMaxFileSize(DataSize.ofMegabytes(100));
            // 전체 요청 데이터 크기 제한 (10MB)
            factory.setMaxRequestSize(DataSize.ofMegabytes(100));
            return factory.createMultipartConfig();
        }
    }

