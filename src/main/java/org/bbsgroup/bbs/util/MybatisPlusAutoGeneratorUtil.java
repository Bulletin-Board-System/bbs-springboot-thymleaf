//package org.bbsgroup.bbs.util;
//
//import com.baomidou.mybatisplus.generator.FastAutoGenerator;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.TemplateType;
//
//public class MybatisPlusAutoGeneratorUtil {
//
//    public static void execute() {
//        //配置数据库参数
//        DataSourceConfig.Builder config = new DataSourceConfig.Builder(
//                "jdbc:mysql://localhost:3306/bbs",
//                "root",
//                "admin123");
//
//        FastAutoGenerator.create(config)
//                //全局配置
//                .globalConfig(builder -> builder
//                        .author("https://github.com/lukrisum")
//                        .outputDir("D:\\javaweb_home\\project\\bbs\\src\\main\\java")
//                        .disableOpenDir()
//                )
//                //包配置
//                .packageConfig(builder -> builder
//                        .parent("org.bbsgroup.bbs")
//                        .entity("entity")
//                )
//                //策略配置
//                .strategyConfig(builder -> builder
//                                // 设置需要生成的表名
//                                .addInclude("user", "category", "comment", "post", "admin")
//                                //配置模型
//                                .entityBuilder().enableFileOverride()
////                        //配置控制器
////                        .controllerBuilder().enableRestStyle().enableFileOverride()
////                        //配置服务
////                        .serviceBuilder().enableFileOverride()
////                        //配置Mapper
////                        .mapperBuilder().enableFileOverride()
//                )
//                //模板配置
//                .templateConfig((scanner, builder) -> {
//                    //配置不需要生成的文件
//                    builder.disable(TemplateType.CONTROLLER, TemplateType.MAPPER, TemplateType.XML, TemplateType.SERVICE, TemplateType.SERVICE_IMPL);
//                })
//                //注入配置
////                .injectionConfig((scanner,builder)  ->{})
//                //模板引擎配置
////                .templateConfig(builder->{})
//                .execute();
//    }
//
//    public static void main(String[] args) {
//        MybatisPlusAutoGeneratorUtil.execute();
//    }
//}
