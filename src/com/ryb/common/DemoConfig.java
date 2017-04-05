package com.ryb.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Properties;

import net.dreamlu.event.EventPlugin;

import com.dw.base.interceptor.OprationLogInterceptor;
import com.dw.base.utils.file.FileUtils;
import com.dw.base.utils.ref.RefUtil;
import com.dw.base.utils.string.StringUtil;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.Const;
import com.jfinal.core.JFinal;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log4jLoggerFactory;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.ioc.IocInterceptor;
import com.jfinal.plugin.ioc.IocPlugin;
import com.jfinal.plugin.ioc.Service;
import com.jfinal.render.ViewType;
import com.jfinalext.annotation.ControllerAnno;
import com.jfinalext.annotation.ModelAnno;

/**
 * API引导式配
 */
public class DemoConfig extends JFinalConfig {
	public static Properties p;
	public static Properties dbConfig;
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		dbConfig=	FileUtils.getPropertys("/datasource.properties");
		p=FileUtils.getPropertys("/globlesetting.properties");
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("a_little_config.txt");
		me.setMaxPostSize(200*Const.DEFAULT_MAX_POST_SIZE);//最大上传：10G
		me.setDevMode(PropKit.getBoolean("devMode", false));
		me.setViewType(ViewType.JSP); 							// 设置视图类型为Jsp，否则默认为FreeMarker
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		List<Class> controllerClasses=RefUtil.getClassesByAnnotation(p.getProperty("controller_package_root"), ControllerAnno.class, true);
		for(Class controllerClass:controllerClasses){
			ControllerAnno controller=(ControllerAnno)controllerClass.getAnnotation(ControllerAnno.class);
			me.add(controller.controllerkey(), controllerClass);
		}
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置ActiveRecord插件*/
//    	ActiveRecordPlugin arp = new ActiveRecordPlugin(DataSourceC3p0Map.getDataSourceC3p0Map(me).get(CustomerContextHolder.getCustomerType()));
//		me.add(arp);
		
//		List<Class> modelClasses=RefUtil.getClassesByAnnotation(p.getProperty("model_package_root"), ModelAnno.class, true);
//		for(Class modelClass:modelClasses){
//			ModelAnno model=(ModelAnno)modelClass.getAnnotation(ModelAnno.class);
//			arp.addMapping(model.tableName(),model.primaryKey(),modelClass);
//		}
		// mysql 数据源
		C3p0Plugin dsMysql = new C3p0Plugin(dbConfig.getProperty("master_jdbcUrl"),dbConfig.getProperty("master_user"),dbConfig.getProperty("master_password"));
		me.add(dsMysql);
		// mysql ActiveRecrodPlugin 实例，并指定configName为 mysql
		ActiveRecordPlugin arpMysql = new ActiveRecordPlugin("master", dsMysql);
		me.add(arpMysql);
		List<Class> modelClasses=RefUtil.getClassesByAnnotation(p.getProperty("model_package_root"), ModelAnno.class, true);
		for(Class modelClass:modelClasses){
			ModelAnno model=(ModelAnno)modelClass.getAnnotation(ModelAnno.class);
			arpMysql.addMapping(model.tableName(),model.primaryKey(),modelClass);
		}
		
		IocPlugin iocPlugin=new IocPlugin();
		//配置Ioc
		List<Class> serviceClasses=RefUtil.getClassesByAnnotation(p.getProperty("service_package_root"), Service.BY_NAME.class, true);
		for(Class serviceClass:serviceClasses){
			Service.BY_NAME service=(Service.BY_NAME)serviceClass.getAnnotation(Service.BY_NAME.class);
			String name=StringUtil.stringIsNull(service.value())?StringUtil.toLowerCaseFirstLetter(serviceClass.getName()):service.value();
			try {
				iocPlugin.getBindByName().put(name, serviceClass.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		serviceClasses=RefUtil.getClassesByAnnotation(p.getProperty("service_package_root"), Service.BY_TYPE.class, true);
		for(Class serviceClass:serviceClasses){
			Service.BY_TYPE service=(Service.BY_TYPE)serviceClass.getAnnotation(Service.BY_TYPE.class);
			try {
				iocPlugin.getBindByType().put(serviceClass, serviceClass.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		me.add(iocPlugin);
		
		// 初始化插件
		EventPlugin eventPlugin = new EventPlugin();
		// 开启全局异步
		eventPlugin.async();

		// 设置扫描jar包，默认不扫描
		eventPlugin.scanJar();
		// 设置监听器默认包，默认全扫描
		eventPlugin.scanPackage("com.ryb");
		me.add(eventPlugin);
	}
	
	/**
	 * 配置全局拦截
	 */
	public void configInterceptor(Interceptors me) {
		me.add(new OprationLogInterceptor());
		me.add(new SessionInViewInterceptor());
		me.add(new IocInterceptor());
	}
	
	/**
	 * 配置处理
	 */
	public void configHandler(Handlers me) {
//		me.add(new DruidStatViewHandler("/xhmonitor"));
	}
	
	/**
	 * 运行main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}
}
