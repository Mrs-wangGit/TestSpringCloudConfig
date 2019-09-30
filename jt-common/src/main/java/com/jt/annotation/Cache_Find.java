package com.jt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 1.用户没有赋值
 * key有默认值,如果key为"",表示用户使用自动生成的key
 * key:包名+类名+方法名+拼接第一个参数
 * @author tarena
 *2.如果用户赋值
 *key就使用用户的数据
 */
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache_Find {

  String key()  default "";
  int seconds() default 0;//当前数据不会定时删除,如果时间不为0,表示用户需要设定超时时间
}
