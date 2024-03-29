﻿rapid-lang-mask
================
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
<a href="">
  <img alt="Coverity Scan Build Status" src="https://img.shields.io/coverity/scan/8244.svg"/>
</a>

已迁移至gitee， 地址：https://gitee.com/whosly/rapid-lang-mask/tree/4.1.1/


已迁移至gitee， 地址：https://gitee.com/whosly/rapid-lang-mask/tree/4.1.1/

## 一、日志脱敏组件
**最新依赖**
```
<dependency>
    <groupId>com.whosly</groupId>
    <artifactId>rapid-lang-mask</artifactId>
    <version>4.0.0-RELEASE</version>
</dependency>
```

项目已上传Maven中央仓库， 可以直接下载。。
> https://search.maven.org/search?q=whosly

>https://mvnrepository.com/search?q=whosly

## 二、背景和服务需求
**概述**
   本方案主要是为了解决日志文档输出中关于敏感信息的处理方法，其中包括针对不同类型数据的分类、脱敏规则的制定以及脱敏方案的实施。
   本标准规定了日志数据的脱敏原则、脱敏方法和脱敏过程，可为数据脱敏工作的规划、实施和管理提供指导。
   
   本标准适用于非结构化和结构化数据的脱敏工作，包括但不限于数据脱敏的提供商、用户、评测机构和监管机构。

**背景**
   随着公司业务的快速发展，业务生产系统的打印日志中积累了大量包含账户等敏感信息的数据，如果这些数据发生泄漏、损害，不仅会给公司带来经济上的损失，而且会给公司的声誉带来负面影响。目前，在业务分析、开发测试、审计监管等使用过程中如何保证生产数据安全已经成为一个重要的问题。同时在加强金融行业监管的大背景下，随着数据安全要求的不断提高以及银监会《银行金融机构信息系统风险管理指引》等明确要求金融机构规避信息风险，对客户等资产安全，敏感信息提供完善的保护，需要对客户资料信息等敏感信息进行脱敏、变形、实现有效保护。

**目标**
   首先依据客户敏感信息屏蔽规则屏蔽业务数据中的敏感信息，保证数据的安全使用，防止敏感信息泄露。脱敏后的数据主要用于测试、开发、培训、外包、数据挖掘/研究等不同的使用场景。其次在借助数据脱敏技术，屏蔽敏感信息的同时，还应使屏蔽的信息保留其原始数据的格式和属性，以确保应用程序在使用脱敏数据的开发与测试过程中正常进行。
   
**原则**
   数据脱敏工作不仅要确保敏感信息被去除，还需要尽可能的平衡脱敏所花费的代价、使用方的业务需求等多个因素。因此，为了确保数据脱敏的过程、代价可控，得到的结果正确且满足业务需要，在实施数据脱敏时，应从技术和管理两方面出发，符合以下原则。
   
**有效性**
   数据脱敏的最基本原则就是要去掉数据中的敏感信息，保证数据安全，这是对数据脱敏工作最基本的要求。有效性要求经过数据脱敏处理后，原始信息中包含的敏感信息已被移除，无法通过处理后的数据得到敏感信息；或者需通过巨大经济代价、时间代价才能得到敏感信息，其成本已远远超过数据本身的价值。此外，在处理敏感信息时，应注意根据原始数据的特点和应用场景，选择合适的脱敏方法。


## 三、Java 客户端使用指南
* 引入依赖包
* 将数据实体对象继承 com.whosly.rapid.lang.mask.instance.AbstractMaskBo
  + 大部分场景直接继承即可
  + 直接继承后，会对接口 IWatchServiceConfiguration 中定义的 getEmailFields 字段和 getMaskFields 字段自动进行脱敏
  + 如果需要对配置外字段进行脱敏，则直接在bo/vo/co/ro/pojo 的field字段上增加注解 @Maskble 即可。
* Demo
  + test测试用例中，com.whosly.rapid.lang.mask.MaskBoTest
  
### 输出结果
> MaskDemoBo[orderNo=a65d41*********30f8,mobile=186*****458]


## 四、配置中心的扩展实现
> 接口定义
> com.whosly.rapid.lang.mask.spi.IWatchServiceConfiguration

* 接口
  + 默认脱敏规则: com.whosly.rapid.lang.mask.internals.tacitly.DefaultWatchServiceConfiguration
  + diamond 脱敏样例: com.whosly.rapid.lang.mask.internals.DiamondWatchServiceConfiguration (位于test下)
  + ......
  
* 实现
> 目录  META-INF/services 下文件 com.whosly.rapid.lang.mask.spi.IWatchServiceConfiguration


## 五、版本发布历史
### 1.0.0-SNAPSHOT/RELEASE
* 增加bo输出对象的掩码功能

### 1.0.1-SNAPSHOT/RELEASE
* rapid-lang-resp依赖升级为 1.2.0-RELEASE；
* 允许忽略某一个指定字段的掩码处理;
* 增加掩码的装饰者;

### 2.0.0-SNAPSHOT/RELEASE
* 掩码迁移;
* 增加 lombok 依赖;
* 增加配置中心依赖;
* 借助 PrmoRespConfiger 实现掩码值配置化， 更新实时;
* 配置中心依赖调整为 provided

```
装饰器语法
# 原样显示1位
* 脱敏显示一位
? 原样显示N位

譬如##**? 头两位原样显示，其后两位脱敏，再其后原样显示
```

### 3.0.0-SNAPSHOT/RELEASE
* 增加脱敏规则 Desensitized
* yueny-parent升级为1.2.0-RELEASE

### 4.0.0-SNAPSHOT/RELEASE
* 依赖升级为 中央仓库基础包 com.whosly。后续计划每月迭代一次更新版本和需求。

### 4.1.0-SNAPSHOT/RELEASE
* jdk 要求 1.8+ 
* 实现 Apollo 配置中心实现 ApolloWatchServiceConfiguration。见文档 《used-apollo.md》
* diamond 因涉及公共包 diamond-client 不同，不做统一实现。可以参考测试用例 com.whosly.rapid.lang.mask.internals.tacitly.DiamondWatchServiceConfiguration。


## 开源地址
[rapid-lang-mask](https://github.com/yueny/rapid-lang-mask)

## 其他
一个快速配置发送邮件的小工具，与大家分享：
[rapid-ok-email](https://github.com/yueny/rapid-ok-email)
