一个基于SAE Java平台的微信平台公众帐号应用例子。

应用基于SAE Java平台开发，使用（Spring/SpringMVC/Mybatis）框架开发，有相同需求（在SAE Java平台搭建微信公众帐号后台）的同学可用拿去作为参考，当然也可用作为在SAE Java平台中使用框架的例子程序作为参考。

微信的验证和回复的入口程序在com.aixuexiao.web.controller.WeixinController.java中。 其中initWeixinURL（对应URL:/weixin GET）方法为验证方法。 replyMessage（对应URL:/weixin POST）方法则是回复方法。

直接下载本项目在SAE Java平台上搭建需要改动地方： 1.在你创建SAE应用的MySQL中创建应用所需数据； 2.修改src下db.properties文件中的数据库信息ak、sk分别设置为你SAE应用中的ak和sk即可。

本应用集成百度天气、影片、交通、有道翻译、小黄鸡Simsimi以及爱帮的公交接口。

其中包括JSON解析以及XML解析的方法可以用作参考。

JSON使用Gson
XML使用Xstream