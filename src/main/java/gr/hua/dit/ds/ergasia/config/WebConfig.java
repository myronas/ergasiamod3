//package gr.hua.dit.ds.ergasia.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // Handle CSS files
//        registry.addResourceHandler("/css/**")
//                .addResourceLocations("classpath:/static/css/")
//                .setCachePeriod(3600)
//                .resourceChain(true);
//
//        // If you have admin-specific CSS:
//        registry.addResourceHandler("/admin/css/**")
//                .addResourceLocations("classpath:/admin/static/css/")
//                .setCachePeriod(3600)
//                .resourceChain(true);
//
//        // Add handlers for JavaScript, images, etc. if necessary
//    }
//}
