package com.nines.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.nines.generator.base.BaseController;
import com.nines.generator.base.BaseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author tanyujie
 * @classname CodeGenerator
 * @description 代码生成
 * @date 2022/10/9 14:57
 * @since 1.0
 */
public class CodeGenerator {

    public static void main(String[] args) {
        FastAutoGenerator.create(
                        "jdbc:mysql://127.0.0.1:3306/test_users?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useLegacyDatetimeCode=false&zeroDateTimeBehavior=convertToNull",
                        "root",
                        "root"
                )
                // 全局配置
                .globalConfig((scanner, builder) ->
                        builder
                                //.author(scanner.apply("请输入作者名称？"))
                                .author("NINES")
                                // 开启Swagger
                                .enableSwagger()
                )
                // 包配置
                .packageConfig((scanner, builder) ->
                        builder
                                //.parent(scanner.apply("请输入包名？"))
                                .parent("com.nines")
                                .moduleName(scanner.apply("请输入模块名？"))
                )
                // 策略配置
                .strategyConfig((scanner, builder) ->
                        builder
                                .addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                                // 过滤表前缀
                                .addTablePrefix("t_", "sys_", "system")
                                // 实体配置
                                .entityBuilder()
                                // 开启lombok
                                .enableLombok()
                                // 开启链式写法
                                .enableChainModel()
                                // 启用字段注解
                                .enableTableFieldAnnotation()
                                // 父类
                                .superClass(BaseEntity.class)
                                // 在父类中的字段
                                .addSuperEntityColumns("id", "created_by", "created_time", "updated_by", "updated_time")
                                .idType(IdType.ASSIGN_ID)
                                // 自动填充 配合 MetaObjectHandler类使用
                                .addTableFills(
                                        new Column("create_time", FieldFill.INSERT),
                                        new Column("update_time", FieldFill.INSERT_UPDATE)
                                )
                                // 类名格式
                                .formatFileName("%s")
                                .controllerBuilder()
                                // 父类
                                .superClass(BaseController.class)
                                // 开启rest风格
                                .enableRestStyle()
                                // 开启驼峰转连字符
                                .enableHyphenStyle()
                                // 类名格式
                                .formatFileName("%sController")
                                .serviceBuilder()
                                //.superServiceClass(BaseService.class)
                                //.superServiceImplClass(BaseServiceImpl.class)
                                // 类名格式
                                .formatServiceFileName("%sService")
                                .formatServiceImplFileName("%sServiceImp")
                                .build())
                /*
                    模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                   .templateEngine(new BeetlTemplateEngine())
                   .templateEngine(new FreemarkerTemplateEngine())
                 */
                .execute();
    }

    /**
     * 处理 all 情况
     */
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

}
