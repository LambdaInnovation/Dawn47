package cn.dawn47.core.register;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * **Inspired by HyperX，将一个变量标记为可以Config设置。 需要在GeneralRegistry中加载标记过的类。
 * 当前只支持static域。
 * 
 * @see GeneralRegistry#loadConfigurableClass
 * @author WeAthFolD
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Configurable {

	/**
	 * 配置的分类。
	 */
	String category() default "general";

	/**
	 * 该配置在Config中显示名称。
	 */
	String key();

	/**
	 * 为该配置规定一个默认值。
	 */
	String defValue();

	/**
	 * 该comment的注释。
	 */
	String comment() default "";
}