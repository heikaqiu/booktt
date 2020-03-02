-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: rm-bp1y804k92xq20m56yo.mysql.rds.aliyuncs.com    Database: booktt
-- ------------------------------------------------------
-- Server version	5.7.26-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED='5e8e3cdf-5aa0-11ea-b70a-00163e14471a:1-24130';

--
-- Table structure for table `advice`
--

DROP TABLE IF EXISTS `advice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `title` varchar(45) NOT NULL,
  `content` varchar(500) NOT NULL,
  `isread` bit(1) NOT NULL,
  `ishandle` bit(1) NOT NULL,
  `time` timestamp NULL DEFAULT NULL,
  `advice_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `advice_ur_idx` (`user_id`),
  CONSTRAINT `advice_ur` FOREIGN KEY (`user_id`) REFERENCES `ur` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advice`
--

LOCK TABLES `advice` WRITE;
/*!40000 ALTER TABLE `advice` DISABLE KEYS */;
INSERT INTO `advice` VALUES (1,1,'13123','124124','','\0','2020-02-25 05:09:45',NULL),(2,1,'1312351','6123151','','\0','2020-02-25 05:15:26',NULL),(3,1,'1','11','\0','\0','2020-02-25 10:12:23',NULL),(4,1,'2','22','\0','\0','2020-02-25 10:12:26',NULL),(5,1,'3','33','\0','\0','2020-02-25 10:12:30',NULL),(6,1,'4','44','\0','\0','2020-02-25 10:12:32',NULL),(7,1,'5','55','\0','\0','2020-02-25 10:12:34',NULL),(8,1,'6','66','\0','\0','2020-02-25 10:12:37',NULL),(9,1,'7','77','\0','\0','2020-02-25 10:12:39',NULL),(10,1,'8','88','\0','\0','2020-02-25 10:12:41',NULL),(11,1,'11','11','\0','\0','2020-02-25 10:12:45',NULL),(12,1,'111','111','\0','\0','2020-02-25 10:12:48',NULL),(13,1,'123','123','\0','\0','2020-02-25 10:12:50',NULL),(14,1,'14123','1412','\0','\0','2020-02-25 10:12:52',NULL),(15,1,'24123','123123','\0','\0','2020-02-25 10:12:54',NULL),(16,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(17,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(18,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(19,1,'1','2','','','2020-02-25 10:12:54','2020-02-25 10:15:24'),(20,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(21,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(22,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(23,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(24,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(25,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(26,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(27,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(28,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(29,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(30,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(31,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(32,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(33,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(34,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(35,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(36,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(37,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(38,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(39,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(40,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(41,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(42,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(43,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(44,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(45,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(46,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(47,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(48,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(49,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(50,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(51,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL),(52,1,'1','2','\0','\0','2020-02-25 10:12:54',NULL);
/*!40000 ALTER TABLE `advice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `author` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_bin NOT NULL,
  `synopsis` varchar(500) COLLATE utf8_bin NOT NULL,
  `nationality` varchar(45) CHARACTER SET utf8 DEFAULT '中国',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` VALUES (1,'埃克尔','Bruce Eckel是MindView公司的总裁，C++标准委员会拥有表决权的成员之一，拥有应用物理学学士和计算机工程硕士学位。Bruce Eckel从1984年至今，已经发表了超过150篇计算机技术文章，出版了6本书.他的《Thinking in C++》一本书在1995年被评为“最佳软件开发图书”，《Thinking in Java》被评为1999年Java World“最受读者欢迎图书”，并且赢得了编辑首选图书奖。','美国'),(2,'李刚','李刚从事8年的Java EE应用开发。曾任LITEON公司的J2EE技术主管，负责该公司的企业信息平台的架构设计。曾任广州电信、广东龙泉科技等公司的技术培训导师2007年3月26日的《电脑报》专访人物。现任新东方IT培训广州中心软件教学总监，曾兼任广东技术师范学院计算机科学系的兼职副教授。','中国'),(3,'周志明','周志明，1998年6月四川大学有机化工专业本科毕业，获工学学士学位；2001年6月四川大学化学工程专业硕士研究生毕业，获工学硕士学位；2004年6月华东理工大学化学工程专业博士研究生毕业，获工学博士学位。2004年7月至今华东理工大学化工学院工作，于2011年9月被聘为教授。2007年10月至2008年9月在比利时根特大学（Ghent University）Guy Marin教授课题组从事博士后研究工作。','中国'),(4,'东野圭吾','东野圭吾日本推理小说天王。1985年，凭借《放学后》获得第31回江户川乱步奖，从此成为职业作家，开始专职写作。早期作品多为精巧细致的本格推理，后期笔锋愈发老辣，文字鲜加雕琢，叙述简练凶狠，情节跌宕诡异，故事架构几至匪夷所思的地步，擅长从极不合理处写出极合理的故事，作风逐渐超越传统推理小说的框架。','日本'),(5,'郑渊洁','郑渊洁，1955年出生。1978年开始童话创作。一个人写一本月刊30年世界纪录保持者，月刊名为《童话大王》。他笔下的文学形象皮皮鲁、鲁西西、罗克、舒克和贝塔影响了中国三代孩子。汶川和玉树地震时，他用稿费向灾区孩子捐款150万元。国家民政部授予郑渊洁“中华慈善楷模”称号。','中国'),(6,'辛夷坞','辛夷坞，当下最炙手可热的80后女作家！青春文学领军人物！独创的“暖伤青春”系列女性情感小 说，本本长居销量排行榜冠军位置！《致我们终将逝去的青春》更开创了国内青春电影先河，成为内地被成功搬上大银幕的第一部青春小说。其所有作品均输出影视 版权，且由豪华一线阵容打造。','中国'),(7,'桐华','桐华：“大漠孤烟直，长河落日圆”是从小惯看的景色。向往着“小桥流水人家”，工作后索性跑到南方，领略一番芭蕉夜雨，薄暮昏冥。一直觉得人生不管是“大江东去，浪淘尽”，还是“杨柳岸，晓风残月”都该体会经历。','中国'),(8,'张嘉佳','张嘉佳，毕业于南京大学，出版小说《几乎成了英雄》《情人书》《刀见笑》。所著《小夫妻天天恶 战》《姐姐的故事》等文章也反响巨大。曾任电影《刀见笑》编剧，获2011年金马奖最佳改编剧本提名。《从你的全世界路过》是一本短篇集，发表后广为流传，总阅读量超过四亿，2013年引发风暴，成为奇迹。','中国'),(9,'刘同','光线传媒电视事业部副总裁，青年作者。历任《中国娱乐报道》《最佳现场》等多档王牌娱乐节目总监。曾出版畅销书《谁的青春不迷茫》，获2013年第八届中国作家榜年度最佳励志书。2012年以来，刘同在清华，北大，中传，武大等百所高校进行宣讲，每场爆满，一票难求。被中国关心下一代委员会聘为“青年榜样”。','中国'),(10,'村上春树','村上春树日本著名作家。生于1949年。29岁开始写作，处女作《且听风吟》获日本群像新人奖。1987年出版的《挪威的森林》，2009年出版的《1Q84》被誉为“新千年日本文学的里程碑”。2013年，《没有色彩的多崎作和他的巡礼之年》面世，七天突破100万册，创日本文学史上的最快突破100万册的纪录。','日本'),(11,'王小波','王小波（1952-1997），中国当代学者、作家。代表作品有《黄金时代》、《白银时代》、《青铜时代》、《黑铁时代》等。 [1] ','中国');
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_bin NOT NULL,
  `synopsis` varchar(500) COLLATE utf8_bin NOT NULL,
  `price` decimal(6,1) NOT NULL,
  `author_id` int(11) NOT NULL,
  `remainder` int(11) NOT NULL,
  `img` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `isshop` bit(1) NOT NULL,
  `booktype` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `book_author_idx` (`author_id`),
  CONSTRAINT `book_author` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'Java编程思想','本书赢得了全球程序员的广泛赞誉，即使是最晦涩的概念，在Bruce Eckel的文字亲和力和小而直接的编程示例面前也会化解于无形。从Java的基础语法到最高级特性（深入的面向对象概念、多线程、自动项目构建、单元测试和调试等），本书都能逐步指导你轻松掌握。 ',80.3,1,594,'img/java编程思想.jpg','',1),(7,' C++编程思想','本书是《C++编程思想》两卷的汇总。第1卷是在第1版的基础上进行了更加深入分析和修改后的第2版，其内容、讲授方法、选用实例以及配套的练习别具特色，可以供不同程度的读者选择阅读。第2卷介绍了C++实用的编程技术和*的实践方法，深入探究了异常处理方法和异常安全设计；介绍C++的字符串、输入输出流的现代用法；解释多重继承问题的难点，描述了典型的设计模式及其实现，特别介绍了多线程处理编程技术。',110.8,1,669,'img/C++编程思想.jpg','',1),(8,'Java编程思想 （第4版）英文原版','Thinking in Java has earned raves from programmers worldwide for its extraordinary clarity, careful organization, and small, direct programming examples. It\'s the definitive introduction to object-oriented programming in the language of the world wide web. From the fundamentals of Java syntax to its most advanced features, Thinking in Java is designed to teach, one simple step at a time. Fully updated for J2SE5 with many new examples and chapters.',385.5,1,526,'img/Java编程思想 （第4版）.jpg','',1),(9,'《疯狂Java讲义》的第5版','本书是《疯狂Java讲义》的第5版，第5版保持了前4版系统、全面、讲解浅显、细致的特性，全面新增介绍了Java 10、Java 11的新特性。本书深入介绍了Java编程的相关方面，全书内容覆盖了Java的基本语法结构、Java的面向对象特征、Java集合框架体系、Java泛型、异常处理、Java GUI编程、JDBC数据库编程、Java注释、Java的IO流体系、Java多线程编程、Java网络通信编程和Java反射机制。覆盖了java.lang、java.util、java.text、java.io和java.nio、java.sql、java.awt、javax.swing包下绝大部分类和接口',132.1,2,48,'img/《疯狂Java讲义》的第5版.jpg','',1),(10,'疯狂Python讲义','本书全面而深入介绍了Python编程的相关内容，全书内容大致可分为四个部分，*部分系统讲解了Python的基本语法结构、函数编程、类和对象、模块和包、异常处理等；第二部分主要介绍Python常用的内置模块和包，这部分包括正则表达式支持IO编程、数据库编程、并发编程、网络通信编程等内容；第三部分主要介绍Python开发工程化方面的内容；第四部分则属于“Python项目实战”，这部分通过项目介绍了Python游戏开发、大数据展示、网络爬虫等热门技能，尤其是网络爬虫和大数据展示，均是当下Python*热的就业岗位。',105.0,2,667,'img/疯狂Python讲义.jpg','',1),(11,'轻量级Java EE企业应用实战（第5版）','经过多年沉淀，Java EE平台已经成为电信、金融、电子商务、保险、证券等各行业的大型应用系统的首选开发平台。在企业级应用的开发选择上，.Net已趋式微，PHP通常只用于开发一些企业展示站点或小型应用，因此这些开发语言、开发平台基本上已无法与Java EE进行对抗了。',121.6,2,56,'img/轻量级Java EE企业应用实战（第5版）.jpg','',1),(12,'疯狂Android讲义（Kotlin版）','移动互联网热潮在全世界引起了巨大反响，移动互联网正在改变着传统互联网的格局，全世界的IT公司争相将业务重心向移动互联网转型，移动互联网业务也成为业内最大的利润增长点。',102.6,2,53,'img/疯狂Android讲义（Kotlin版）.jpg','',1),(13,'疯狂Android讲义（第3版）','移动互联网是当今世界发展快、市场潜力大、前景诱人的一项业务，而Android则是移动互联网上市场占有率很高的平台。',102.6,2,56,'img/疯狂Android讲义（第3版）.jpg','',1),(14,'疯狂Swift讲义','Swift正逐步进入iOS APP的实际应用开发，而Apple在WWDC2015上发布了Swift 2.0版本，这也表明了Apple对Swift的支持决心，不难预测，Swift的市场份额肯定会超过传统的Objective-C。本书是《疯狂Swift讲义》的第2版，本书以新的OS X 10.11为平台、以Xcode 7.1为开发工具，全面介绍了Swift 2.1的语法以及使用Swift开发iOS应用的知识。本书全面覆盖了Swift的基本语法结构、Swift函数式编程特征、Swift的面向对象特征、Foundation框架的核心类库用法等知识，',65.6,2,59,'img/疯狂Swift讲义.jpg','',1),(15,'深入理解Java虚拟机：JVM高级特性与最佳实践（第2版）','本书第1版两年内印刷近10次，4家网上书店的评论近4?000条，98%以上的评论全部为5星级的好评，是整个Java图书领域公认的经典著作和超级畅销书，繁体版在台湾也十分受欢迎。第2版在第1版的基础上做了很大的改进：根据*的JDK1.7对全书内容进行了全面的升级和补充；增加了大量处理各种常见JVM问题的技巧和*实践；增加了若干与生产环境相结合的实战案例；对第1版中的错误和不足之处的修正；等等。第2版不仅技术更新、内容更丰富，而且实战性更强。',75.1,3,40,'img/深入理解Java虚拟机.jpg','',1),(16,'材料成形原理','本书系统地阐述了材料液态成形、塑性成形和连接成形过程中的各种规律及其物理本质。全书不仅结合国内外*的研究进展，还分析和阐述了材料成形*的研究成果及应用实例，使学生了解*的研究进展及与本课程相关的知识。全书共分16章，其中第1章至第5章主要介绍了液态成形过程中的理论知识；第6、7章讲述了材料在各种成形过程中的冶金反应原理、成形缺陷的产生及控制；第8章介绍了特殊条件下的成形；第9章至第12章系统地阐述了金属塑性加工的力学基础知识；第13章至第16章结合工程实际应用，阐述金属塑性加工中的各种影响因素的特点及作用。',37.5,3,50,'img/材料成形原理.jpg','',1),(17,' 智慧的疆界：从图灵机到人工智能','本书深入介绍了人工智能六十余年发展里程中出现的重要历史事件、理论学说和所取得的激动人心的成果；也从科普的角度，尽可能以不依赖数学等专业知识的方式去介绍这些成果背后的理论与算法。读者可以通过本书对人工智能学科发展里程的解析体会到人工智能的创造者和推动者们所希望的智能理论和产品是如何工作的。无论是与人工智能产业相关的研发人员，还是这个领域的专业研究人员，或是信息科学和计算机科学的爱好者们，都能从本书中得到启发。',66.2,3,80,'img/智慧的疆界：从图灵机到人工智能.jpg','',1),(18,'特种铸造','本书坚持以“应用为主”为前提，从内容上兼顾理论基础和工艺设计两个方面，突出学生工程实践意识和创新能力的培养。全书共分为8章，第1~6章分别系统地介绍了熔模精密铸造、消失模铸造、金属型铸造、反重力铸造、压力铸造、离心铸造等特种铸造方法的工艺特点、基本原理、应用领域，并着重阐述特种铸造的生产流程、生产工序以及主要技术参数、铸件缺陷分析和铸件应用实例。第7章对石膏型精密铸造、陶瓷铸造、挤压铸造、半固态铸造、连续铸造、喷射成形和快速铸造等其他先进铸造技术进行了简要介绍。第8章简单介绍了计算机在铸造技术中的应用。本书取材经典而新颖，内容丰富和全面，突出应用实例，辅以大量数据图表，极富启发性和实用性。为方便教学，本书配套电子课件。',37.4,3,80,'img/特种铸造.jpg','',1),(19,'材料成形设备(周志明)','本书共分九章，章概述了材料成形设备的地位、发展概况及发展趋势,第二~九章分别系统地介绍了曲柄压力机、其他类型压力机(挤压机、双动拉深压力机、热模锻压力机、精冲压力机、高速压力机等)、螺旋压力机、液压机、锻锤、塑料挤出机、注射机和压铸机等材料成形设备的工作原理、典型结构、控制系统、性能特点、主要技术参数与使用等。本书内容深入浅出，图文并茂，为便于教学，并配套电子课件。',34.0,3,50,'img/材料成形设备(周志明).jpg','',1),(20,'非标专机设计制造实用技术','《非标专机设计制造实用技术》共分５章。第1章系统介绍了机械化生产铸钢车间的设计以及燃油燃气两用熔铝炉的设计等，第２章系统的介绍了活塞毛坯的铸造、液压浇铸机、隧道式活塞预热炉和石墨干燥固化炉、半精车车床、外圆及鼓包车床、粗镗活塞销孔等专机的设计与制造等；第３章讲述了桥式起重机和轻型塔式起重机的设计与制造；第４章介绍了桥式起重机的安装，弹性橡胶垫在模锻锤安装基座中的应用以及模锻锤锤头修复等；第５章主要介绍了大模数大直径齿轮和齿向变位圆锥齿轮的加工，大直径环形轴承辊道的磨削加工等特殊条件下的加工方法。',35.6,3,70,'img/非标专机设计制造实用技术.jpg','',1),(21,'白夜行','《白夜行》被普遍视为日本作家东野圭吾作品的无冕之王，一经推出即成为东野圭吾的长篇小说代表作，中文版销量超450万册，与《嫌疑人X的献身》《恶意》《解忧杂货店》并称为东野圭吾四大杰作。',59.6,4,60,'img/白夜行.jpg','',2),(22,'郑渊洁十二生肖童话（全12册）','郑渊洁十二生肖童话系列由《鼠王做寿》《牛王醉酒》《虎王出山》《兔王卖耳》《龙王闹海》《蛇王淘金》《马王登基》《羊王称霸》《猴王变形》《鸡王画虎》《狗王梦游》《猪王照相》共十二本组成。',134.4,5,45,'img/郑渊洁十二生肖童话（全12册）.jpg','',3),(23,'我在回忆里等你.白金纪念版','如果时光可以倒流，谁在回忆里等你？',30.4,6,100,'img/我在回忆里等你.白金纪念版.jpg','',2),(24,'长相思套装(全三册）','何谓相思？思而不得，*相思。',117.0,7,50,'img/长相思套装(全三册）.jpg','',2),(25,'让我留在你身边','张嘉佳*作品。新的暖心睡前故事来了！',32.0,8,60,'img/让我留在你身边.jpg','',2),(26,'我在未来等你','你会不会偶尔产生那种“如果能回到那一年就好了”的念头？觉得回到那一年，以我们当下的见识和心态，很多事都能被改变。',36.4,9,80,'img/我在未来等你.jpg','',2),(27,'挪威的森林','《挪威的森林》是日本作家村上春树所著的一部长篇爱情小说，影响了几代读者的青春名作。故事讲述主角渡边纠缠在情绪不稳定且患有精神疾病的直子和开朗活泼的小林绿子之间，苦闷彷徨，*终展开了自我救赎和成长的旅程。彻头彻尾的现实笔法，描绘了逝去的青春风景，小说中弥散着特有的感伤和孤独气氛。自1987年在日本问世后，该小说在年轻人中引起共鸣，风靡不息。上海译文出版社于2018年2月，推出该书的全新纪念版。',37.2,10,89,'img/挪威的森林.jpg','',2),(28,'一只特立独行的猪','??我觉得黑色幽默是我的气质，是天生的。——王小波?',35.0,11,150,'img/一只特立独行的猪.jpg','',2),(29,'黄金时代','写出《黄金时代》之前，我从未觉得自己写得好。——王小波',31.5,11,200,'img/黄金时代.jpg','',2),(30,'爱你就像爱生命','本书收录了王小波与李银河的“两地书”，以及李银河深情怀念王小波的三篇文章，并收录二人的“媒人”《绿毛水怪》。再现了他们的爱与生活，是一部感动国人的爱情绝唱。其中不仅有热切、坦诚的情感表白，还有彼此对于书籍、诗歌乃至社会的看法，闪耀着理想与爱情的火花，令人动容。',37.0,11,196,'img/爱你就像爱生命.jpg','',2),(31,'沉默的大多数','自从我辈成人以来，所见到的一切全是颠倒着的。在一个喧嚣的话语圈下面，始终有个沉默的大多数。——王小波',31.5,11,182,'img/沉默的大多数.jpg','',2),(32,'天黑以后','映入眼帘的，是都市的身姿。\r\n透过在夜空中高翔的飞鸟的眼睛，我们从天上俯瞰着这光景。\r\n都市望去仿佛一头巨大的活物，遵循其自身的原理运转。\r\n我们的视线选取尤为辉煌灿烂的一角，静静地降落……',28.2,10,9,'img/天黑以后.jpg','',2),(33,'获取放大器五点半群文件','123',123.0,6,123,'img/bj3.jpg','',2),(34,'粗去年为代价难为情记得开','123',123.0,9,123,'img/head.jpg','\0',3);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booktype`
--

DROP TABLE IF EXISTS `booktype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `booktype` (
  `typeid` int(11) NOT NULL AUTO_INCREMENT,
  `typename` varchar(45) NOT NULL,
  PRIMARY KEY (`typeid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booktype`
--

LOCK TABLES `booktype` WRITE;
/*!40000 ALTER TABLE `booktype` DISABLE KEYS */;
INSERT INTO `booktype` VALUES (1,'计算机科学'),(2,'小说'),(3,'儿童读物'),(4,'历史人文');
/*!40000 ALTER TABLE `booktype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `collection`
--

DROP TABLE IF EXISTS `collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `collection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `collection_book_idx` (`book_id`),
  KEY `collection_ur_idx` (`user_id`),
  CONSTRAINT `collection_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `collection_ur` FOREIGN KEY (`user_id`) REFERENCES `ur` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `collection`
--

LOCK TABLES `collection` WRITE;
/*!40000 ALTER TABLE `collection` DISABLE KEYS */;
INSERT INTO `collection` VALUES (1,1,1),(21,2,7),(22,2,26),(37,6,1),(44,5,1),(45,45,9);
/*!40000 ALTER TABLE `collection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `content` varchar(500) COLLATE utf8_bin NOT NULL,
  `time` timestamp NULL DEFAULT NULL,
  `star` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `comment_idx` (`user_id`),
  KEY `comment_book_idx` (`book_id`),
  CONSTRAINT `comment_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `comment_user` FOREIGN KEY (`user_id`) REFERENCES `ur` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,1,1,'java编程的圣经','2019-10-29 12:15:47',5),(19,2,7,'	励志21天学会c++ 哈哈这就是个笑话','2019-11-09 08:01:10',4),(20,2,7,'	励志21天学会c++ 哈哈这就是个笑话','2019-11-09 08:02:06',4),(23,6,1,'','2019-11-09 22:22:07',3),(24,6,1,'','2019-11-09 22:25:05',3),(25,6,1,'','2019-11-09 22:42:03',3),(26,6,1,'565','2019-11-09 22:43:32',2),(27,6,1,'565','2019-11-09 22:44:23',2),(28,6,1,'565','2019-11-09 22:44:48',2),(29,6,1,'565','2019-11-09 22:45:22',2),(30,6,1,'565','2019-11-09 22:45:47',2),(31,6,1,'565','2019-11-09 22:46:38',2),(32,6,1,'565','2019-11-09 22:47:03',2),(33,6,1,'565','2019-11-09 22:47:13',2),(34,6,1,'565','2019-11-09 22:48:15',2),(35,5,8,'111','2019-11-09 23:17:09',2),(38,5,1,'99259588259','2019-12-16 19:45:41',5),(40,1,1,'12312','2020-02-27 05:08:32',5);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL,
  `user_id` int(11) NOT NULL,
  `submittime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `finishtime` timestamp NULL DEFAULT NULL,
  `state` int(1) NOT NULL,
  `express_number` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `paymentaTime` timestamp NULL DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `totalPrice` decimal(10,1) DEFAULT NULL,
  `isread` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `order_user_idx` (`user_id`),
  CONSTRAINT `order_user` FOREIGN KEY (`user_id`) REFERENCES `ur` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1581042320415,1,'2020-02-07 02:25:20',NULL,3,'432145','2020-02-07 02:55:20',4,507.1,''),(1581055975256,1,'2020-02-07 08:23:41',NULL,5,NULL,'2020-02-07 06:42:55',2,191.1,''),(1581064400230,1,'2020-02-07 09:03:20',NULL,5,NULL,'2020-02-07 09:03:20',1,385.5,''),(1581072026688,1,'2020-02-07 13:38:16',NULL,5,NULL,'2020-02-07 11:10:27',1,385.5,''),(1581082723301,1,'2020-02-07 14:10:53',NULL,5,NULL,'2020-02-07 14:08:43',1,132.1,''),(1581084690813,1,'2020-02-07 14:51:20',NULL,5,NULL,'2020-02-07 14:41:31',1,385.5,''),(1581087103247,1,'2020-02-07 14:51:57',NULL,2,NULL,'2020-02-07 15:21:43',1,385.5,''),(1581087199724,1,'2020-02-07 14:56:18',NULL,5,NULL,'2020-02-07 15:23:20',1,385.5,''),(1581430649276,1,'2020-02-11 14:17:29',NULL,3,'15613','2020-02-11 14:17:29',1,385.5,''),(1581496153786,1,'2020-02-12 08:29:45',NULL,2,NULL,'2020-02-12 08:59:14',1,385.5,''),(1581497106601,1,'2020-02-12 08:45:07',NULL,2,NULL,'2020-02-12 08:45:07',1,110.8,''),(1581497136137,1,'2020-02-12 08:45:48',NULL,3,'123124124','2020-02-12 08:45:48',1,102.6,''),(1581497179669,1,'2020-02-12 08:46:42',NULL,2,NULL,'2020-02-12 08:46:43',1,385.5,''),(1581497382087,1,'2020-02-12 08:49:42',NULL,2,NULL,'2020-02-12 08:49:53',1,385.5,'\0'),(1581497414796,1,'2020-02-12 08:50:15',NULL,2,NULL,'2020-02-12 08:50:15',1,385.5,'\0'),(1581497464086,1,'2020-02-12 08:51:04',NULL,5,NULL,'2020-02-12 09:21:04',1,132.1,''),(1581497508413,1,'2020-02-12 08:51:48',NULL,5,NULL,'2020-02-12 09:21:48',1,132.1,''),(1582629638327,1,'2020-02-25 11:20:38',NULL,2,NULL,'2020-02-25 11:20:38',1,385.5,'\0'),(1582812797758,1,'2020-02-27 14:13:18',NULL,5,NULL,'2020-02-27 14:43:18',2,488.1,'\0'),(1582815185205,1,'2020-02-27 14:53:05',NULL,5,NULL,'2020-02-27 15:23:05',1,102.6,'\0'),(1582816005328,1,'2020-02-27 15:06:45',NULL,2,NULL,'2020-02-27 15:36:45',1,35.6,'\0'),(1582817099780,1,'2020-02-27 15:25:00',NULL,2,NULL,'2020-02-27 15:55:00',1,102.6,'\0'),(1582817652155,1,'2020-02-27 15:34:12',NULL,2,NULL,'2020-02-27 16:04:12',1,385.5,'\0'),(1582817677257,1,'2020-02-27 15:34:37',NULL,2,NULL,'2020-02-27 16:04:37',1,80.3,'\0'),(1582817962392,1,'2020-02-27 15:39:22',NULL,2,NULL,'2020-02-27 16:09:22',1,102.6,'\0'),(1582882382107,1,'2020-02-28 09:33:02',NULL,2,NULL,'2020-02-28 09:39:05',1,110.8,'\0'),(1582882526237,1,'2020-02-28 09:35:26',NULL,5,NULL,'2020-02-28 10:05:26',1,385.5,'\0'),(1582882560374,1,'2020-02-28 09:36:00',NULL,2,NULL,'2020-02-28 10:06:00',2,496.3,'\0'),(1582886905125,1,'2020-02-28 10:48:25',NULL,5,NULL,'2020-02-28 11:18:25',1,102.6,'\0'),(1582887744084,1,'2020-02-28 11:02:24',NULL,5,NULL,'2020-02-28 11:32:24',1,105.0,'\0'),(1582889645465,1,'2020-02-28 11:34:05',NULL,5,NULL,'2020-02-28 12:04:05',1,102.6,'\0'),(1582894966444,1,'2020-02-28 13:02:46',NULL,5,NULL,'2020-02-28 13:32:46',1,102.6,'\0'),(1582895750988,1,'2020-02-28 13:15:51',NULL,5,NULL,'2020-02-28 13:45:51',1,385.5,'\0'),(1582898291569,1,'2020-02-28 13:58:12',NULL,5,NULL,'2020-02-28 14:28:12',1,0.1,'\0'),(1582898621469,1,'2020-02-28 14:03:41',NULL,5,NULL,'2020-02-28 14:33:41',1,0.1,'\0'),(1582900857676,1,'2020-02-28 14:40:58',NULL,5,NULL,'2020-02-28 15:10:58',1,80.3,'\0'),(1582901165351,1,'2020-02-28 14:46:05',NULL,5,NULL,'2020-02-28 15:16:05',1,132.1,'\0'),(1582987274031,1,'2020-02-29 14:41:14',NULL,5,NULL,'2020-02-29 15:11:14',1,385.5,'\0'),(1582993736030,1,'2020-02-29 16:28:56',NULL,5,NULL,'2020-02-29 16:58:56',1,80.3,'\0'),(1583033940339,1,'2020-03-01 03:39:00',NULL,5,NULL,'2020-03-01 04:09:00',1,80.3,'\0'),(1583117600020,1,'2020-03-02 02:53:20',NULL,5,NULL,'2020-03-02 03:23:20',1,385.5,'\0'),(1583124459767,1,'2020-03-02 04:47:40',NULL,2,NULL,'2020-03-02 04:56:33',1,102.6,'\0'),(1583125085734,1,'2020-03-02 04:58:06',NULL,5,NULL,'2020-03-02 05:28:06',1,110.8,'\0'),(1583128288520,1,'2020-03-02 05:51:29',NULL,1,NULL,'2020-03-02 06:21:29',1,102.6,'\0');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordercontent`
--

DROP TABLE IF EXISTS `ordercontent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ordercontent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  `price` decimal(6,1) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ordercontent_book_idx` (`book_id`),
  KEY `ordercontent_order_idx` (`order_id`),
  CONSTRAINT `ordercontent_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `ordercontent_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordercontent`
--

LOCK TABLES `ordercontent` WRITE;
/*!40000 ALTER TABLE `ordercontent` DISABLE KEYS */;
INSERT INTO `ordercontent` VALUES (96,7,1,110.8,1581042320415),(97,9,3,132.1,1581042320415),(98,7,1,110.8,1581055975256),(99,1,1,80.3,1581055975256),(100,8,1,385.5,1581064400230),(101,8,1,385.5,1581072026688),(102,9,1,132.1,1581082723301),(103,8,1,385.5,1581084690813),(104,8,1,385.5,1581087103247),(105,8,1,385.5,1581087199724),(106,8,1,385.5,1581430649276),(107,8,1,385.5,1581496153786),(108,7,1,110.8,1581497106601),(109,12,1,102.6,1581497136137),(110,8,1,385.5,1581497179669),(111,8,1,385.5,1581497382087),(112,8,1,385.5,1581497414796),(113,9,1,132.1,1581497464086),(114,9,1,132.1,1581497508413),(115,8,1,385.5,1582629638327),(116,8,1,385.5,1582812797758),(117,13,1,102.6,1582812797758),(118,13,1,102.6,1582815185205),(119,20,1,35.6,1582816005328),(120,12,1,102.6,1582817099780),(121,8,1,385.5,1582817652155),(122,1,1,80.3,1582817677257),(123,13,1,102.6,1582817962392),(124,7,1,110.8,1582882382107),(125,8,1,385.5,1582882526237),(126,8,1,385.5,1582882560374),(127,7,1,110.8,1582882560374),(128,13,1,102.6,1582886905125),(129,10,1,105.0,1582887744084),(130,13,1,102.6,1582889645465),(131,12,1,102.6,1582894966444),(132,8,1,385.5,1582895750988),(133,1,1,80.3,1582898291569),(134,1,1,80.3,1582898621469),(135,1,1,80.3,1582900857676),(136,9,1,132.1,1582901165351),(137,8,1,385.5,1582987274031),(138,1,1,80.3,1582993736030),(139,1,1,80.3,1583033940339),(140,8,1,385.5,1583117600020),(141,12,1,102.6,1583124459767),(142,7,1,110.8,1583125085734),(143,13,1,102.6,1583128288520);
/*!40000 ALTER TABLE `ordercontent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reply`
--

DROP TABLE IF EXISTS `reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `content` varchar(500) COLLATE utf8_bin NOT NULL,
  `time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `reply_comment_idx` (`comment_id`),
  KEY `reply_user_idx` (`user_id`),
  CONSTRAINT `reply_comment` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`),
  CONSTRAINT `reply_user` FOREIGN KEY (`user_id`) REFERENCES `ur` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reply`
--

LOCK TABLES `reply` WRITE;
/*!40000 ALTER TABLE `reply` DISABLE KEYS */;
INSERT INTO `reply` VALUES (1,1,2,'你说的不错，我非常赞同','2019-10-29 12:59:48'),(3,1,5,'		我也非常赞同','2019-11-09 07:49:42'),(5,23,2,'		哈哈哈','2019-11-09 19:36:27'),(7,33,2,'		嘿嘿','2019-11-09 19:37:23'),(8,33,2,'		嘿嘿','2019-11-09 19:37:46'),(9,27,5,'		ok牛皮','2019-11-09 22:13:51'),(10,33,5,'		我也来回复下','2019-11-09 22:39:04'),(12,19,1,'		有趣有趣','2019-11-20 21:10:32'),(15,23,1,'42wdqwd','2020-02-27 04:52:40');
/*!40000 ALTER TABLE `reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shopcart`
--

DROP TABLE IF EXISTS `shopcart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shopcart` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cart_user_idx` (`user_id`),
  KEY `cart_book_idx` (`book_id`),
  CONSTRAINT `cart_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `cart_user` FOREIGN KEY (`user_id`) REFERENCES `ur` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shopcart`
--

LOCK TABLES `shopcart` WRITE;
/*!40000 ALTER TABLE `shopcart` DISABLE KEYS */;
/*!40000 ALTER TABLE `shopcart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ur`
--

DROP TABLE IF EXISTS `ur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) COLLATE utf8_bin NOT NULL,
  `password` varchar(45) COLLATE utf8_bin NOT NULL,
  `balance` decimal(10,1) NOT NULL DEFAULT '0.0',
  `isadmin` bit(1) NOT NULL DEFAULT b'0',
  `address` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `gender` bit(1) NOT NULL DEFAULT b'1',
  `time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `paypassword` varchar(45) COLLATE utf8_bin NOT NULL,
  `province` varchar(45) COLLATE utf8_bin NOT NULL,
  `city` varchar(45) COLLATE utf8_bin NOT NULL,
  `telephone` varchar(45) COLLATE utf8_bin NOT NULL,
  `lastusetime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `img` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ur`
--

LOCK TABLES `ur` WRITE;
/*!40000 ALTER TABLE `ur` DISABLE KEYS */;
INSERT INTO `ur` VALUES (1,'heikaqiu','123',1231468.1,'','xxxx','\0','2019-11-17 03:41:35','123456','浙江省','杭州','15257186541','2020-03-02 05:51:17','head/1582714671806.jpg'),(2,'sjf','123',5000.0,'\0','葫芦岛','','2019-12-27 04:12:28','123456','浙江省','杭州','1','2020-02-17 03:43:47','boyhead.jpg'),(3,'12412355','123456',200.0,'','112312','','2020-01-07 01:12:28','123456','浙江省','杭州','2','2020-01-30 10:25:29','boyhead.jpg'),(5,'1241235','123456',2446450.0,'\0','xxxx','\0','2020-02-17 03:12:28','333333','浙江省','杭州','3','2020-02-03 14:15:57','girlhead.jpg'),(6,'555123','123123',0.0,'\0','xxxx','','2020-02-17 03:12:28','123456','浙江省','杭州','4','2020-01-30 10:25:29','boyhead.jpg'),(7,'124213','123123',0.0,'\0','123','','2020-02-17 03:12:28','123456','浙江省','杭州','5','2020-01-30 10:25:29','boyhead.jpg'),(8,'12123123','123123',0.0,'\0','123','','2020-02-17 03:12:28','123456','浙江省','杭州','6','2020-01-30 10:25:29','boyhead.jpg'),(9,'123124','123123',0.0,'\0','123','','2020-02-17 03:12:28','123456','浙江省','杭州','7','2020-01-30 10:25:29','boyhead.jpg'),(10,'123123','123123',0.0,'\0','123','','2020-02-17 03:12:28','123456','浙江省','杭州','8','2020-01-30 10:25:29','boyhead.jpg'),(11,'54343412','123123',0.0,'\0','123','','2020-02-17 03:12:28','123123','浙江省','杭州','9','2020-01-30 10:25:29','boyhead.jpg'),(18,'141231','123123',0.0,'\0','xxxx','','2020-02-17 03:12:28','666666','浙江省','杭州','10','2020-01-30 10:25:06','boyhead.jpg'),(19,'heikaqiu1','1',0.0,'\0','123','','2020-02-17 03:12:28','1234','广东省','江门','12','2020-01-30 04:41:06','boyhead.jpg'),(20,'heikaqiu2','1',0.0,'\0','1','','2020-02-17 03:12:28','1','江苏省','南京','11','2020-01-30 04:45:38','boyhead.jpg'),(21,'heikaqiu3','1',0.0,'\0','1','','2020-02-17 03:12:28','1','福建省','福州','13','2020-01-30 12:53:22','boyhead.jpg'),(22,'125125124','14123123',0.0,'\0','b','\0','2020-02-17 03:12:28','123456','浙江省','台州','14','2020-02-03 02:05:15','girlhead.jpg'),(23,'heikaqiu123','123',0.0,'\0','123','','2020-02-17 03:12:28','123','江苏省','扬州','15','2020-02-05 06:49:27','boyhead.jpg'),(24,'13123','124214',0.0,'\0','124123','','2020-02-17 15:56:48','12412','安徽省','合肥','16','2020-02-17 15:56:48','boyhead.jpg'),(25,'124214','124123',0.0,'\0','1422143','','2020-02-17 15:57:00','124123','浙江省','杭州','17','2020-02-17 15:57:00','boyhead.jpg'),(26,'1241254124','142123',0.0,'\0','124123','','2020-02-17 15:57:14','124213','山东省','济南','18','2020-02-17 15:57:14','boyhead.jpg'),(27,'15412421','124213',0.0,'\0','124123','','2020-02-17 15:57:26','12412','江苏省','南京','19','2020-02-17 15:57:26','boyhead.jpg'),(28,'151243124','123123',0.0,'\0','124123','','2020-02-17 15:57:35','14124','浙江省','杭州','20','2020-02-17 15:57:35','boyhead.jpg'),(29,'hi','123456',0.0,'\0','xx街道','','2020-02-17 15:57:35','123','浙江省','杭州','21','2020-02-17 15:57:35','boyhead.jpg'),(35,'hi1','123456',0.0,'\0','xx街道','','2020-02-17 15:57:35','123','浙江省','杭州','22','2020-02-17 15:57:35','boyhead.jpg'),(36,'hi2','123456',0.0,'\0','xx街道','','2020-02-17 15:57:35','123','浙江省','杭州','23','2020-02-17 15:57:35','boyhead.jpg'),(38,'hi4','123456',0.0,'\0','xx街道','','2020-02-17 15:57:35','123','浙江省','杭州','24','2020-02-17 15:57:35','boyhead.jpg'),(39,'hi5','123456',0.0,'\0','xx街道','','2020-02-17 15:57:35','123','浙江省','杭州','25','2020-02-17 15:57:35','boyhead.jpg'),(40,'hi6','123456',0.0,'\0','xx街道','','2020-02-17 15:57:35','123','浙江省','杭州','26','2020-02-17 15:57:35','boyhead.jpg'),(41,'hi7','123456',0.0,'\0','xx街道','','2020-02-17 15:57:35','123','浙江省','杭州','27','2020-02-17 15:57:35','boyhead.jpg'),(42,'hi9','123456',0.0,'\0','xx街道','','2020-02-17 15:57:35','123','浙江省','杭州','28','2020-02-17 15:57:35','boyhead.jpg'),(43,'hi10','123456',0.0,'\0','xx街道','','2020-02-17 15:57:35','123','浙江省','杭州','29','2020-02-17 15:57:35','boyhead.jpg'),(44,'hi11','123456',0.0,'\0','xx街道','','2020-02-17 15:57:35','123','浙江省','杭州','30','2020-02-17 15:57:35','boyhead.jpg'),(45,'1242141231','123',0.0,'\0','14123123','','2020-02-19 03:53:44','123456','浙江省','杭州','31','2020-03-01 13:26:22','boyhead.jpg'),(46,'111','111',0.0,'\0','111','\0','2020-02-20 05:35:20','111','北京市','北京市','111','2020-02-20 05:35:20','girlhead.jpg'),(47,'222','222',0.0,'\0','222','\0','2020-02-20 06:33:31','222','上海市','上海市','222','2020-02-20 06:33:31','girlhead.jpg'),(48,'333','333',0.0,'\0','333','','2020-02-20 06:35:33','333','北京市','北京市','333','2020-02-20 06:35:33','boyhead.jpg'),(49,'tcgvkjb','123123',0.0,'\0','123','\0','2020-02-26 05:14:12','123','福建省','泉州','444','2020-02-26 05:14:12','girlhead.jpg'),(50,'1412','124123',0.0,'\0','1421','\0','2020-02-26 05:15:35','12312','广东省','广州','555','2020-02-26 05:15:36','girlhead.jpg'),(51,'heika','124123124',0.0,'\0','12321','','2020-02-29 03:35:00','4123','江苏省','南京','666','2020-02-29 03:35:00',NULL),(52,'14131','112233',0.0,'\0','14124','','2020-03-01 13:17:57','123456','山东省','聊城','777','2020-03-01 13:17:57','boyhead.jpg'),(53,'12345','123123',0.0,'\0','123','\0','2020-03-01 13:29:50','123123','重庆市','重庆市','888','2020-03-01 15:17:10','head/1583070466093.jpg'),(54,'123456','112233',0.0,'\0','124123','','2020-03-01 13:36:11','123456','山东省','德州','999','2020-03-01 13:36:12','head/1583069807312.jpg');
/*!40000 ALTER TABLE `ur` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-02 13:53:12
