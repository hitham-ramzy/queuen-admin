package com.mindeavors.queuen.web.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/swagger")
@ApiIgnore
public class SwaggerResource {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @GetMapping
    public void handleFoo(HttpServletResponse response) throws IOException {
        if (!StringUtils.isEmpty(activeProfile) && !activeProfile.contains("prod")) {
            response.sendRedirect("/swagger-ui/index.html");
        }
    }
}
