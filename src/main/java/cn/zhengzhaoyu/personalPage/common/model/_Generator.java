/*
 * Copyright (c) 2017 JavaDog
 */
package cn.zhengzhaoyu.personalPage.common.model;

import cn.zhengzhaoyu.personalPage.common.Config;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;

/**
 * 自动生成数据库中的类的生成器
 *
 * @author Rain
 * @version 1.0
 * @since 1.0
 */
public class _Generator {

    public static void main(String[] args) {

        String baseModelPackageName = "cn.zhengzhaoyu.personalPage.common.model.base";
        String baseModelOutputDir = "src/main/java/cn/zhengzhaoyu/personalPage/common/model/base";
        String modelPackageName = "cn.zhengzhaoyu.personalPage.common.model";
        String modelOutputDir = "src/main/java/cn/zhengzhaoyu/personalPage/common/model";

        DruidPlugin dp = new Config().getDruidPlugin();
        dp.start();

        Generator generator = new Generator(dp.getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);

        generator.setDialect(new MysqlDialect());
        generator.setGenerateChainSetter(true);
        generator.setGenerateDaoInModel(false);
        generator.setRemovedTableNamePrefixes("p_");
        generator.setGenerateDataDictionary(false);

        generator.generate();

        dp.stop();

    }

}
