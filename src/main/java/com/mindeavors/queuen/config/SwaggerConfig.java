/*
 * Copyright 2016-2017 the original author or authors from the JHipster project.
 *
 * This file is part of the JHipster project, see https://jhipster.github.io/
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mindeavors.queuen.config;

import io.github.jhipster.config.JHipsterConstants;
import io.github.jhipster.config.apidoc.SwaggerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;

import static java.util.Collections.singletonList;

/**
 * Swagger config.
 * <p>
 * extending {@link SwaggerConfiguration} to add api-key for using token in swagger
 */
@Configuration
@ConditionalOnClass({ ApiInfo.class, BeanValidatorPluginsConfiguration.class })
@Profile(JHipsterConstants.SPRING_PROFILE_SWAGGER)
public class SwaggerConfig {

    @Value("${spring.application.name}")
    String appName;
    @Value("${management.context-path}")
    String managementContextPath;
    @Value("${info.project.version}")
    String appVersion;

    public SwaggerConfig(SwaggerConfiguration swaggerConfiguration) {
        swaggerConfiguration.swaggerSpringfoxApiDocket().securitySchemes(singletonList(apiKey()));
        swaggerConfiguration.swaggerSpringfoxManagementDocket(appName, managementContextPath, appVersion).securitySchemes(singletonList(apiKey()));
    }

    private ApiKey apiKey() {
        return new ApiKey("bearer ", "Authorization", "header");
    }
}
