# **Spring Boot 添加Bean**

```
注：上节我们利用@Bean或者其他注解往Spring容器中添加一个Bean，下面我们看一下向Spring容器中添加Bean的几种方式。
```



## @Bean

```java
 @Bean
 public User user(){        
 		return new User();
 }
```

通过**@Bean**注解可往容器中添加Bean组件，默认方法名为bean的Id,如果要特殊指定其名称，可以使用@Bean("user")，详细参考上一节



## 包扫描+@Component与其延伸注解

其实在项目中多用包扫描的方式，向容器中添加组件，可以在主配置类上，@Configuration和@ComponentScan，@ComponentScan默认扫描当前类所在的包，所以在Spring Boot程序中，一般主程序类会建立在项目的根包中，然后利用@SpringBootApplication标注，@SpringBootApplication是一个组合注解包括@SpringBootConfiguration@EnableAutoConfiguration、@ComponentScan三个注解，其中就包含@ComponentScan注解，@ComponentScan还有其他属性，详细查看上一小节。



**@Conditional**
@Conditional注解是根据条件往容器中注册Bean,它可以注册在方法和类上，在SpringBoot自动配置中，广泛利用@Conditional注解和它的延伸注解，往Spring容器中进行动态注册。**@ConditionalOnClass**如果有这个类才注册，**@ConditionalOnMissingBean**如果没有这个Bean组件才注册等等。



## @Import

在容器中导入组件还可以通过@Import导入组建，例如：

```
@Configuration
@ComponentScan
@Import(User.class)
public class MainConfig {

}
```

如果使用@Import导入组件，导入的组件ID为其全类名。



## 2) ImportSelector

@Import组件的value如果是实现了**ImportSelector**接口，可以通过该类批量导入组件，该类的**selectImports**方法的返回值，将会被注册到Spring容器中，但该类本身并不会被导入到组件中。



## 3) ImportBeanDefinitionRegistrar

通过实现该类往容器中注册一个Bean,例如

```java
public class CustomImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar{    			        @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {        
        boolean b1 = beanDefinitionRegistry.containsBeanDefinition("mother");        
        boolean b2 = beanDefinitionRegistry.containsBeanDefinition("father");        
        if(b1&&b2){            
          	//获取bean的定义信息
            RootBeanDefinition beanDefinition = new RootBeanDefinition(Child.class);
            beanDefinitionRegistry.registerBeanDefinition("child",beanDefinition);
        }
    }
}
```



## 实现FactoryBean

```java
public class UserFactoryBean implements FactoryBean<User>{    
		//通过getObject()方法创建对象，如果是非单例，则在每次获取时执行该方法
    @Nullable
    @Override
    public User getObject() throws Exception {        
    		return new User();
    }    
    //类型
    @Nullable
    @Override
    public Class<?> getObjectType() {        
    		return User.class;
    }    
    //是否单例
    @Override
    public boolean isSingleton() {        
    		return true;
    }
}
```

该方式默认获取到的工厂Bean调用getObject创建的对象；
如果获取工厂Bean本身需要在ID前加&标识，例如&userFactoryBean 。





## 小结：

本节课主要学习了如何往Spring容器中注册Bean的4中方式，
1、通过@Bean
2、通过包扫描+@Component及其延伸组建
3、通过@Import(ImportSelector、ImportBeanDefinitionRegistrar)
4、通过实现工厂Bean(FactoryBean)





