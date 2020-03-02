# booktt
在线图书销售 

使用了 SpringBoot SSM框架 Thymeleaf模版引擎

实现了登录、注册、购物车、收藏、评论、回复、建议、图书详情、订单详情、支付宝支付、微信支付、短信服务等

前台 http://118.178.190.156/

后台 http://118.178.190.156/admin  

前后台测试账号heikaqiu 密码123 支付密码123456

下面三处需要修改为自己的信息

cn.heikaqiu.booktt.config.AlipayConfig
cn.heikaqiu.booktt.controller.ShortMessageController
cn.heikaqiu.booktt.controller.WxpayController

application.yml 需要配置数据库信息
