package com.hivescm.codeid.service;

import com.hivescm.common.domain.DataResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient用于通知Feign组件对该接口进行代理
//相当于webservice，dubbo 就是暴露这个接口。说明这个接口是一个服务 提供给外部进行访问
//调用的客户端，Feign组件会对该接口进行代理，通过动态代理机制生成接口实现完成方法的调用
/*
 *                webservice 发布服务的过程
 * ----------------------------------------------------

  package me.gacl.ws;

  import javax.jws.WebService;

  定义SEI(WebService EndPoint Interface(终端))

   //使用 @WebService注解标注WebServiceI接口
   @WebService
   public interface WebServiceI {

       //使用@WebMethod注解标注WebServiceI接口中的方法
       @WebMethod
       String sayHello(String name);
   }


  package me.gacl.ws;

  import javax.jws.WebService;

 SEI的具体实现

   //使用@WebService注解标注WebServiceI接口的实现类WebServiceImpl
   @WebService
  public class WebServiceImpl implements WebServiceI {

     @Override
     public String sayHello(String name) {
         System.out.println("WebService sayHello "+name);
         return "sayHello "+name;
     }
  }

 package me.gacl.ws.test;

 import javax.xml.ws.Endpoint;

 import me.gacl.ws.WebServiceImpl;

 发布Web Service

 public class WebServicePublish {

     public static void main(String[] args) {
         //定义WebService的发布地址，这个地址就是提供给外界访问Webervice的URL地址，
         //URL地址格式为：http://ip:端口号/xxxx

         //String address = "http://192.168.1.100:8989/";这个WebService发布地址的写法是合法的
         //String address = "http://192.168.1.100:8989/Webservice";这个WebService发布地址的是合法的

         //使用Endpoint类提供的publish方法发布WebService，
         //发布时要保证使用的端口号没有被其他应用程序占用

         重点 在发布服务的时候，会提供一个对外访问的地址，还有该接口的实现类
         这样调用的客户端，在通过接口代理生成实现类的时候，其实使用的被代理类就是
         我们发布的这个代理     new WebServiceImpl()

         Endpoint.publish(address , new WebServiceImpl());
         System.out.println("发布webservice成功!");
     }
 }
-----------------------------------------------------------------------
       dubbo   发布服务的过程

      一、dubbo-server  发布服务

             为注册服务起一个名称，便于在zk注册中心区分不同的服务
      <dubbo:application name="formater_date_provider"/>

              指定dubbo的注册中心，使用zk作为注册中心来发现服务
      <dubbo:registry protocol="zookeeper" address="127.0.0.1:2181"/>

              指定接口的实现类
      <bean id="formatDateStr" class="com.dubbo.service.impl.ProviderImpl"/>

              指定该接口作为一个服务暴露出去对外提供服务，并指定了该接口的具体实现  一同发布到注册中心
      <dubbo:service interface="com.dubbo.service.FormatDate" ref="formatDateStr"/>


       二、dubbo-client  调用服务

                 为该服务起一个名字，也注册到zk注册中心
       <dubbo:application name="formater_date_consumer"/>

                指定该服务也发送到zk注册中心
       <dubbo:registry protocol="zookeeper" address="127.0.0.1:2181"/>

                指定要调用zk注册中心的那个服务，dubbo会对该代理接口，通过动态代理自动生成接口实现
                其中代理对象就是    <bean id="formatDateStr" class="com.dubbo.service.impl.ProviderImpl"/>
       <dubbo:reference id="formatDate" interface="com.dubbo.service.FormatDate"/>
 */
/*
 *               spring cloud 通过 Feign 实现 服务发布也是一样的
 *
 *  一、FeignClient(name="codeID-server")
 *
 *     1.指定该接口 作为一个服务发布到 Eureka 注册中心
 *     2.name="codeID-server" 为该服务起一个名字，用于区分注册到注册中心的不同服务
 *
 *  二、@RequestMapping("/getID")
    public abstract String generateID(int businessCode, String orgId,String json)

        codeID-server/getID   服务名/URI  完成对服务的调用


 */

//其中@FeignClient中指定需要调用的微服务的名称，@RequestMapping中指定访问微服务响应接口的路径
@FeignClient(name="generatedCodeId-server",path="generatedCodeId-server")
public interface CodeIdService {

    @RequestMapping(value = "/getID", method = RequestMethod.GET)
    public DataResult<String> generatorId(@RequestParam("businessCode") String
                                                     businessCode,
                                         @RequestParam("orgId")String orgId,
                                         @RequestParam("json")String json) throws Exception;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public DataResult<String> initCodeIDTemplate();
}
