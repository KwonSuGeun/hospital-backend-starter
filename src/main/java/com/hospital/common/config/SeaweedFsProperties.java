package com.hospital.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "seaweedfs")
public class SeaweedFsProperties {

    private Filer filer = new Filer();

    @Getter
    @Setter
    public static class Filer {
        private String endpoint;
        private String bucket;
    }
}
