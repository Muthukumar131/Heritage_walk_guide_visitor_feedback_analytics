package com.heritagewalk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

/**
 * Ensures Single Page Application (SPA) client-side routes
 * (like /places, /routes, /analytics) are routed to index.html while
 * keeping REST APIs (/api/**) completely untouched.
 */
@Configuration
public class SpaWebFilter implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        if (requestedResource.exists() && requestedResource.isReadable()) {
                            return requestedResource;
                        }
                        if (!resourcePath.startsWith("api") && !resourcePath.startsWith("h2-console")) {
                            return new ClassPathResource("/static/index.html");
                        }
                        return null;
                    }
                });
    }
}
