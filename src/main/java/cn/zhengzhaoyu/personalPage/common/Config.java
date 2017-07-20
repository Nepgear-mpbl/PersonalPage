package cn.zhengzhaoyu.personalPage.common;

import cn.zhengzhaoyu.personalPage.common.interceptor.GuestInterceptor;
import cn.zhengzhaoyu.personalPage.common.model._MappingKit;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.IDataSourceProvider;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.template.Engine;

/**
 * 配置文件
 *
 * @author Rain
 * @version 1.0
 * @since 1.0
 */
public class Config extends JFinalConfig {

    private static final Prop prop = loadConfig();

    //加载配置
    private static Prop loadConfig() {
        try {
            return PropKit.use("config-dev.properties");
        } catch (RuntimeException e) {
            LogKit.logNothing(e);
            return PropKit.use("config.properties");
        }
    }

    public static void main(String[] args) {
        JFinal.start("src/main/webapp", 80, "/");
    }

    @Override
    public void configConstant(Constants me) {
        me.setBaseDownloadPath(PathKit.getWebRootPath() + "/fileResource");
        me.setBaseUploadPath(PathKit.getWebRootPath() + "/fileResource");
        me.setDevMode(prop.getBoolean("devMode"));
        me.setJsonFactory(MixedJsonFactory.me());
    }

    @Override
    public void configRoute(Routes me) {
        me.add(new FrontRoute());
    }

    @Override
    public void configEngine(Engine me) {
        me.addSharedFunction("/_view/common/__layout.html");
    }

    @Override
    public void configPlugin(Plugins me) {
        DruidPlugin dp = getDruidPlugin();
        me.add(dp);
        ActiveRecordPlugin arp = getActiveRecordPlugin(dp);
        me.add(arp);
        me.add(getRedisPlugin());
    }

    @Override
    public void configInterceptor(Interceptors me) {
        me.add(new GuestInterceptor());
    }

    @Override
    public void configHandler(Handlers me) {

    }

    public DruidPlugin getDruidPlugin() {
        String url = prop.get("druid.url");
        String username = prop.get("druid.username");
        String password = prop.get("druid.password");
        String driverClass = prop.get("druid.driverClass");
        return new DruidPlugin(url, username, password, driverClass);
    }

    private RedisPlugin getRedisPlugin() {
        String host = prop.get("redis.host");
        int port = prop.getInt("redis.port");
        int timeout = ElKit.eval(prop.get("redis.timeout"));
        String password = prop.get("redis.password");
        int database = prop.getInt("redis.tokenDatabase");
        return new RedisPlugin("token", host, port, timeout, password, database);
    }

    private ActiveRecordPlugin getActiveRecordPlugin(IDataSourceProvider provider) {
        ActiveRecordPlugin arp = new ActiveRecordPlugin(provider);
        arp.setDialect(new MysqlDialect());
        _MappingKit.mapping(arp);
        arp.setBaseSqlTemplatePath(PathKit.getWebRootPath() + "/_sql");
        arp.addSqlTemplate("all.sql");
        return arp;
    }
}
