whistle
=======
whistle是为微信公众平台设计的开发框架。之所以取名whistle，是大部分是因为whistle和微信有点谐音，另外，我也希望他成为一个轻快的开发框架。 目前完成的功能是通过一键配置，完成微信公众平台上线，实现一个方法，实现响应简单内容。 第一版本，功能还需进一步加强，结构、命名也非常不合理，希望得到您的意见和反馈。

下载sample项目，打开微信后台<br/>
1、打开项目中的whistle.xml，把微信后台的的AppID(应用ID)、AppSecret(应用密钥)、Token(令牌)拷贝到whistle.xml的对应位置；<br/>
2、在web容器中运行项目；<br/>
3、假设您的项目url根是 http://yourhost.com/yourproject， 则URL(服务器地址)填写:http://yourhost.com/yourproject/wxmpGate<br/>
4、保存即可<br/>

由于微信后台的要求，您必须一个打开了80端口的服务器才行，如果无法提供，可以到一些云平台申请也可以。
