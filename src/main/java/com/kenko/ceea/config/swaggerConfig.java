//package com.kenko.ceea.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableOpenApi
//public class swaggerConfig {
//
//    /**
//     * 创建API应用
//     * apiInfo() 增加API相关信息
//     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
//     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
//     *
//     * @return
//     */
//    @Bean
//    public Docket restApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("标准接口")
//                .apiInfo(apiInfo("工程教育认证系统后端API", "1.0"))
//                .useDefaultResponseMessages(true)
//                .forCodeGeneration(false)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.kenko.ceea.controller"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    /**
//     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
//     * 访问地址：http://ip:port/swagger-ui/index.html
//     *
//     * @return
//     */
//    private ApiInfo apiInfo(String title, String version) {
//        return new ApiInfoBuilder()
//                .title(title)
//                .description("更多请关注: https://akagiyui.com")
//                .termsOfServiceUrl("https://akagiyui.com")
//                .contact(new Contact("AkagiYui", "https://akagiyui.com", "akagiyui@yeah.net"))
//                .version(version)
//                .build();
//    }
//}
