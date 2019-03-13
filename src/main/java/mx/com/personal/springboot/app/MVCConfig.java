package mx.com.personal.springboot.app;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
	/*public void addResourceHandlers(ResourceHandlerRegistration registry) {
		registry.addResourceLocations("/pictures/**")
				.addResourceLocations("file:///home/workspaces/spring/spring-boot-data-jpa4/uploads/");
	}*/
	
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController(Constants.ERROR_403).setViewName("error_403");
	}
}
