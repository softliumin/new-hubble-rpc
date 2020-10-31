//package cc.zody.hubble.rpc.starter;
//
//import java.lang.annotation.Documented;
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
///**
// * rpc服务调用者
// *
// * @author zody
// * @since 2020-10-02 11:50
// */
//@Documented
//@Retention(RetentionPolicy.RUNTIME)
//@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
//public @interface HubbleConsumer {
//
//    Class<?> interfaceClass() default void.class;
//
//    String interfaceName() default "";
//
//    String version() default "";
//
//    String group() default "";
//
//    String url() default "";
//
//    String client() default "";
//
//    boolean generic() default false;
//
//    boolean injvm() default false;
//
//    boolean check() default true;
//
//    boolean init() default false;
//
//    boolean lazy() default false;
//
//    boolean stubevent() default false;
//
//    String reconnect() default "";
//
//    boolean sticky() default false;
//
//    String proxy() default "";
//
//    String stub() default "";
//
//    String cluster() default "";
//
//    int connections() default 0;
//
//    int callbacks() default 0;
//
//    String onconnect() default "";
//
//    String ondisconnect() default "";
//
//    String owner() default "";
//
//    String layer() default "";
//
//    int retries() default 2;
//
//    String loadbalance() default "";
//
//    boolean async() default false;
//
//    int actives() default 0;
//
//    boolean sent() default false;
//
//    String mock() default "";
//
//    String validation() default "";
//
//    int timeout() default 0;
//
//    String cache() default "";
//
//    String[] filter() default {};
//
//    String[] listener() default {};
//
//    String[] parameters() default {};
//
//    String application() default "";
//
//    String module() default "";
//
//    String consumer() default "";
//
//    String monitor() default "";
//
//    String[] registry() default {};
//
//    /**
//     * The communication protocol of Dubbo Service
//     *
//     * @return the default value is ""
//     * @since 2.6.6
//     */
//    String protocol() default "";
//
//}
