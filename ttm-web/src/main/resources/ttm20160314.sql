CREATE DATABASE  IF NOT EXISTS `schedule` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `schedule`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: schedule
-- ------------------------------------------------------
-- Server version	5.6.22-log

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

--
-- Table structure for table `sd_action_result`
--

DROP TABLE IF EXISTS `sd_action_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_action_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT '组名',
  `action` varchar(100) DEFAULT NULL,
  `action_no` varchar(50) DEFAULT NULL COMMENT '动作编号',
  `ip` varchar(2000) DEFAULT NULL COMMENT '操作IP',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `simple_trigger_json` varchar(200) DEFAULT NULL COMMENT 'simple json字符串',
  `only_run_once` tinyint(4) DEFAULT NULL,
  `dist_ips` varchar(2000) DEFAULT NULL COMMENT '运行节点IP列表',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB AUTO_INCREMENT=172 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_action_result`
--

LOCK TABLES `sd_action_result` WRITE;
/*!40000 ALTER TABLE `sd_action_result` DISABLE KEYS */;
INSERT INTO `sd_action_result` VALUES (42,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','623AD3CBEF544ABFB90FB82FE866BA83','[172.16.4.248]','','2015-04-07 15:12:44','{repeatInterval=0, startDelay=2000, repeatCount=0}',1,'[172.16.4.248]'),(43,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','E62C3F60D1D34E00BB7A4C29B1AC5EC8','[172.16.4.248]','0/10 * * * * ?','2015-04-07 15:35:05','',0,'[172.16.4.248]'),(44,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','5417EFF0E201478DBD080E70258D648F','[172.16.4.248]','0/10 * * * * ?','2015-04-07 15:49:08','',0,'[172.16.4.248]'),(45,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','CD93DD6B09C84372AE38A8FCBC9FF0A6','[172.16.4.248]','','2015-04-07 16:20:38','{repeatInterval=0, startDelay=1000, repeatCount=0}',1,'[172.16.4.248]'),(46,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','85A6BFE1916C4EC0B8B8239D3D6DB510','[172.16.4.248]','','2015-04-07 16:55:47','{repeatInterval=0, startDelay=2000, repeatCount=0}',1,'[172.16.4.248]'),(47,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','10A879938AA04909907F0944D7AB2419','[172.16.4.248]','0/10 * * * * ?','2015-04-07 16:58:53','',0,'[172.16.4.248]'),(48,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','01C937B7B360441AADAB218CAD2FE425','[172.16.4.248]','','2015-04-07 17:37:03','{repeatInterval=0, startDelay=2000, repeatCount=0}',1,'[172.16.4.248]'),(49,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','2C0776D9164941A7BCE3446B4F7EDCAD','[172.16.4.248]','','2015-04-07 18:03:26','{repeatInterval=0, startDelay=2000, repeatCount=0}',1,'[172.16.4.248]'),(50,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','C9E0D621F6AC416D9735A3A1E39EBC06','[172.16.4.248]','','2015-04-07 18:07:00','{repeatInterval=0, startDelay=2000, repeatCount=0}',1,'[172.16.4.248]'),(51,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','3DE03467763B49A99D72B51E53B49429','[172.16.4.248]','0/10 * * * * ?','2015-04-07 18:13:38','',0,'[172.16.4.248]'),(52,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','2DFE878518884B6C81465993CA704413','[172.16.4.248]','','2015-04-07 18:15:10','{repeatInterval=0, startDelay=2000, repeatCount=0}',1,'[172.16.4.248]'),(53,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','53B5C6B7906A4A47AFA7E4891CD271CB','[172.16.4.248]','','2015-04-07 18:40:46','{repeatInterval=0, startDelay=2000, repeatCount=0}',1,'[172.16.4.248]'),(54,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','2747905925A14F5492CE7FECEB116627','[172.16.4.248]','','2015-04-07 18:43:47','{repeatInterval=0, startDelay=10000, repeatCount=0}',1,'[172.16.4.248]'),(55,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','1BDF9C5696614E7696F839EA7E8A3514','[172.16.4.248]','','2015-04-07 19:16:35','{repeatInterval=0, startDelay=2000, repeatCount=0}',1,'[172.16.4.248]'),(56,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','8424F5E4118E4378BF089FF71831122D','[172.16.4.248]','','2015-04-07 19:23:57','{repeatInterval=0, startDelay=2000, repeatCount=0}',1,'[172.16.4.248]'),(57,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','091E70F8C39F49D4A8AA0010EE62D3D7','[172.16.4.248]','','2015-04-07 19:25:23','{repeatInterval=0, startDelay=2000, repeatCount=0}',1,'[172.16.4.248]'),(58,'test_0002','saleOrder_job_group_name','update_cron_expression','637A8A9E48384FEC93B6D504017F399E','172.16.4.248','0/10 * * * * ?','2015-04-07 19:25:47',NULL,NULL,NULL),(59,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','8796E9B0656A4967BB1EEA606DA65AD2','[172.16.4.248]','0/10 * * * * ?','2015-04-08 09:38:15','',0,'[172.16.4.248]'),(60,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','CE6DFA6311194A71A9F0356AE38F9AFE','[172.16.4.248]','0/10 * * * * ?','2015-04-08 09:47:51','',0,'[172.16.4.248]'),(61,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','3375C24EA2E6426BADF9FD55ABF072DB','[172.16.4.248]','0/10 * * * * ?','2015-04-08 09:52:01','',0,'[172.16.4.248]'),(62,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','EB0E6E26876D43CAAA78B8F11A826040','[172.16.4.248]','0/10 * * * * ?','2015-04-08 10:13:36','',0,'[172.16.4.248]'),(63,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','A164CD24B96C43DF941AD562657EB448','[172.16.4.248]','0/10 * * * * ?','2015-04-08 14:00:54','',0,'[172.16.4.248]'),(64,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','0966112CDE214EDC87CCEE5045EF7382','[172.16.4.248]','0/10 * * * * ?','2015-04-08 14:34:11','',0,'[172.16.4.248]'),(65,'test_dynamic','saleOrder_job_group_name','update_cron_expression','057B5C15B3C24C52A3F7FC0A80775BBE','172.16.4.248','0/15 * * * * ?','2015-04-08 16:23:14',NULL,NULL,NULL),(66,'test_0002','saleOrder_job_group_name','update_cron_expression','2F5522D885244B01B0307BD91F5AAB21','172.16.4.248','0/15 * * * * ?','2015-04-08 16:23:23',NULL,NULL,NULL),(67,'test_0002','saleOrder_job_group_name','pause','9742DE3AC9744AEA9233BFA4580D10C6','172.16.4.248','','2015-04-14 15:37:48',NULL,NULL,NULL),(68,'test_0002','saleOrder_job_group_name','normal','9AACF3CD39D0474B80788A6FA4729F87','172.16.4.248','','2015-04-14 15:38:51',NULL,NULL,NULL),(69,'test_0002','saleOrder_job_group_name','pause','1BCE0D193FDE46429A66C1631FE07400','172.16.4.244','','2015-04-17 15:21:01',NULL,NULL,NULL),(70,'test_0002','saleOrder_job_group_name','pause','E364FC9E06AA40AAAA05483C604A4509','172.16.4.244','','2015-04-17 15:38:47',NULL,NULL,NULL),(71,'test_0002','saleOrder_job_group_name','pause','3779963FEA3B4A4FA6499298DA7BF29C','172.16.4.244','','2015-04-17 15:39:23',NULL,NULL,NULL),(72,'test_0002','saleOrder_job_group_name','normal','09ABD1B42D2243258F3AF92F371BC0F0','172.16.4.244','','2015-04-17 15:45:29',NULL,NULL,NULL),(73,'test_0002','saleOrder_job_group_name','pause','2C81845B2B5B47E7BF9A93FA35E6F9C2','172.16.4.244','','2015-04-17 15:47:32',NULL,NULL,NULL),(74,'test_0002','saleOrder_job_group_name','normal','E398276540BC42798BE0E0F6AF711352','172.16.4.244','','2015-04-17 15:47:41',NULL,NULL,NULL),(75,'test_0002','saleOrder_job_group_name','pause','4FB8333D722B4E4CAAA97D6C89891C8C','172.16.4.244','','2015-04-17 15:49:16',NULL,NULL,NULL),(76,'test_0002','saleOrder_job_group_name','normal','BBD1060A4839447EABE2B876BF15745C','172.16.4.244','','2015-04-17 15:49:22',NULL,NULL,NULL),(77,'test_0002','saleOrder_job_group_name','pause','FB098211B8FF404AAEDDA4E1341ED429','172.16.4.244','','2015-04-17 15:50:57',NULL,NULL,NULL),(78,'test_0002','saleOrder_job_group_name','normal','79B088A6318E4119B92553850C35A969','172.16.4.244','','2015-04-17 15:51:05',NULL,NULL,NULL),(79,'test_0002','saleOrder_job_group_name','trigger_at_once','AB39F96FD2B045519FC238394C0CAE7F','172.16.4.244','','2015-04-17 15:54:59',NULL,NULL,NULL),(80,'test_0002','saleOrder_job_group_name','trigger_at_once','54908EF45E0E4157AA081594E2FE1441','172.16.4.244','','2015-04-17 15:55:19',NULL,NULL,NULL),(81,'test_0002','saleOrder_job_group_name','trigger_at_once','A4F1E5C696474323B2A642B2D0AC5470','172.16.4.244','','2015-04-17 15:55:47',NULL,NULL,NULL),(82,'test_0002','saleOrder_job_group_name','remove','2BF75076DC1047088A223D671A71932B','172.16.4.244','','2015-04-17 15:59:05',NULL,NULL,NULL),(83,'test_0002','saleOrder_job_group_name','remove','C4F57595F4E141A381C2581498124D70','172.16.4.244','','2015-04-17 15:59:19',NULL,NULL,NULL),(84,'test_dynamic','saleOrder_job_group_name','pause','501C5041306D46B193A0C7C7A9F46F86','172.16.4.244','','2015-04-17 17:02:23',NULL,NULL,NULL),(85,'test_dynamic','saleOrder_job_group_name','pause','BCDCA82BEE8C4EA08115A12F11097CE4','172.16.4.244','','2015-04-17 17:04:07',NULL,NULL,NULL),(86,'test_dynamic','saleOrder_job_group_name','normal','8A0F477C6DCF4743A5B156ED6FBB13A4','172.16.4.244','','2015-04-17 17:04:15',NULL,NULL,NULL),(87,'test_0002','saleOrder_job_group_name','add_from_remove','CCB7962BF6104E83AF10B3D888FE7846','172.16.4.244','','2015-04-17 17:43:42',NULL,NULL,NULL),(88,'test_0002','saleOrder_job_group_name','remove','682A1C89EF4944B6A186302C5F6A6B02','172.16.4.244','','2015-04-17 17:46:09',NULL,NULL,NULL),(89,'test_0002','saleOrder_job_group_name','add_from_remove','5BB377F9D0DA4459B4F2528CD739A42E','172.16.4.244','','2015-04-17 17:46:36',NULL,NULL,NULL),(90,'test_0002','saleOrder_job_group_name','remove','912860D5A44243A6BE66165A69BBC65B','172.16.4.244','','2015-04-17 17:50:13',NULL,NULL,NULL),(91,'test_0002','saleOrder_job_group_name','add_from_remove','04B43AF3325549109B1033E5C548A5D8','172.16.4.244','','2015-04-17 17:50:21',NULL,NULL,NULL),(92,'test_0002','saleOrder_job_group_name','pause','E26D1EC23BEC4E34A514850141A8A801','172.16.4.244','','2015-04-17 17:58:49',NULL,NULL,NULL),(93,'test_0002','saleOrder_job_group_name','normal','A3FB36850C064322B6A2E5BD6676EAE7','172.16.4.244','','2015-04-17 17:58:57',NULL,NULL,NULL),(94,'test_0002','saleOrder_job_group_name','update_cron_expression','E0C34CA0FABB48BBA1EF1D73EADC0AB3','172.16.4.244','0/15 * * * * ?','2015-04-17 18:56:31',NULL,NULL,NULL),(95,'test_0002','saleOrder_job_group_name','update_cron_expression','AEB633EF7E45416EA19C307522A6E99D','172.16.4.244','0/15 * * * * ?','2015-04-17 18:58:38',NULL,NULL,NULL),(96,'test_0002','saleOrder_job_group_name','update_cron_expression','E05162BCAC0B4C2D934640310994E626','172.16.4.244','0/30 * * * * ?','2015-04-17 18:58:47',NULL,NULL,NULL),(97,'test_0002','saleOrder_job_group_name','update_cron_expression','EA143E0919154972B824F4906BCA3BED','172.16.4.244','0/30 * * * * ?','2015-04-17 19:03:36',NULL,NULL,NULL),(98,'test_0002','saleOrder_job_group_name','update_cron_expression','46A0D0ADD6D74BEC8F705BD08ADAF166','172.16.4.244','0/20 * * * * ?','2015-04-17 19:03:54',NULL,NULL,NULL),(99,'test_0002','saleOrder_job_group_name','pause','A5115CC10ACD46A38FC645338F8ED647','172.16.4.244','','2015-04-17 19:04:54',NULL,NULL,NULL),(100,'test_0002','saleOrder_job_group_name','pause','087D6AD4C6ED4804A9A575EDAD31C6E6','172.16.4.244','','2015-04-17 19:05:08',NULL,NULL,NULL),(101,'test_0002','saleOrder_job_group_name','normal','34F528FDA47245DC8035B003D54561BE','172.16.4.244','','2015-04-17 19:05:15',NULL,NULL,NULL),(102,'test_0002','saleOrder_job_group_name','pause','5AE9554856904F0BA35A41FDCC762009','172.16.4.245','','2015-04-18 10:01:41',NULL,NULL,NULL),(103,'test_0002','saleOrder_job_group_name','pause','E609C54BDC2D4F2C835C7262A8B88067','172.16.4.245','','2015-04-18 10:04:53',NULL,NULL,NULL),(104,'test_0002','saleOrder_job_group_name','normal','BF2CD310B4094409B685DAA05BADC6CC','172.16.4.245','','2015-04-18 10:05:02',NULL,NULL,NULL),(105,'test_0002','saleOrder_job_group_name','remove','F7AED14B69AA457DB94D5B50F5B910BA','172.16.4.245','','2015-04-18 10:05:11',NULL,NULL,NULL),(106,'test_0002','saleOrder_job_group_name','add_from_remove','A687F51FDC9749639F44326A2082C59C','172.16.4.245','','2015-04-18 10:05:18',NULL,NULL,NULL),(107,'test_0002','saleOrder_job_group_name','pause','3CCF059F09B64D0B8D2AD16DAF31F959','172.16.4.245','','2015-04-18 10:05:35',NULL,NULL,NULL),(108,'test_0002','saleOrder_job_group_name','normal','3E6B2D56AE8F4251A480741E43A26A73','172.16.4.245','','2015-04-18 10:05:42',NULL,NULL,NULL),(109,'test_0002','saleOrder_job_group_name','update_cron_expression','8832556F40294228A0571467C126EA6C','172.16.4.245','0/15 * * * * ?','2015-04-18 10:37:14',NULL,NULL,NULL),(110,'test_0002','saleOrder_job_group_name','pause','3E2517C6776B49A8BBB43E05AB856BE6','172.16.4.245','','2015-04-18 13:33:15',NULL,NULL,NULL),(111,'test_0002','saleOrder_job_group_name','normal','91E65B0960C34480AD34908F013321C9','172.16.4.245','','2015-04-18 13:33:21',NULL,NULL,NULL),(112,'test_0002','saleOrder_job_group_name','pause','AF41230B31374E84AA69F41EF4269C6F','172.16.4.245','','2015-04-18 13:33:43',NULL,NULL,NULL),(113,'test_0002','saleOrder_job_group_name','normal','DB64311D581A44DEB24FCD0E0B84632E','172.16.4.245','','2015-04-18 13:33:52',NULL,NULL,NULL),(114,'test_0002','saleOrder_job_group_name','update_cron_expression','D54690BB29604A9DA26D3E66B2FA9663','172.16.4.245','0/15 * * * * ?','2015-04-18 13:35:10',NULL,NULL,NULL),(115,'test_0002','saleOrder_job_group_name','pause','660B14E011DF4225AD7DD71A7FE3DD5C','172.16.4.245','','2015-04-18 13:35:18',NULL,NULL,NULL),(116,'test_0002','saleOrder_job_group_name','normal','39675218BD3745D9A624AEA1799F1579','172.16.4.245','','2015-04-18 13:35:24',NULL,NULL,NULL),(117,'test_0002','saleOrder_job_group_name','update_cron_expression','E8247F2F212443E49422A517D9FC0D8A','172.16.4.245','0/15 * * * * ?','2015-04-18 13:35:46',NULL,NULL,NULL),(118,'test_0002','saleOrder_job_group_name','pause','D49760EC8AAD4EB483EBE6853B52DDBF','172.16.4.245','','2015-04-18 13:35:53',NULL,NULL,NULL),(119,'test_0002','saleOrder_job_group_name','normal','2C13017B8AC04F1ABCCF1E3B49CB7602','172.16.4.245','','2015-04-18 13:36:01',NULL,NULL,NULL),(120,'test_0002','saleOrder_job_group_name','pause','44DADFFF9FD1475598A31350F1A36B32','172.16.4.245','','2015-04-18 14:22:52',NULL,NULL,NULL),(121,'test_0002','saleOrder_job_group_name','normal','947BA87204454ED4B7EFDABF01DF78A1','172.16.4.245','','2015-04-18 14:22:57',NULL,NULL,NULL),(122,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','26CDEF8E4A0145F493D58FE9C17019E4','[172.16.4.245]','0/15 * * * * ?','2015-04-18 15:39:29','',0,'[172.16.4.245]'),(123,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','AF8EA46197A546DE92AC39416D568471','[172.16.4.245]','0/15 * * * * ?','2015-04-18 15:50:30','',0,'[172.16.4.245]'),(124,'test_dynamic','saleOrder_job_group_name','update_cron_expression','5CA47A2E31FC4C07ACFB2023C02532D4','172.16.4.245','0/15 * * * * ?','2015-04-18 16:35:13',NULL,NULL,NULL),(125,'test_dynamic','saleOrder_job_group_name','update_cron_expression','C46A775EF958473692796FB721F54E89','172.16.4.245','0/15 * * * * ?','2015-04-18 16:40:32',NULL,NULL,NULL),(126,'test_dynamic','saleOrder_job_group_name','update_cron_expression','F3E3B168059546B0A74DE9C52C64BFE1','172.16.4.245','0/15 * * * * ?','2015-04-18 17:00:28',NULL,NULL,NULL),(127,'test_dynamic','saleOrder_job_group_name','pause','0B3C9CBD0F6743EDB6113E92D8A18D65','172.16.4.245','','2015-04-18 17:00:35',NULL,NULL,NULL),(128,'test_dynamic','saleOrder_job_group_name','normal','D98E3FFBD444498A8F93BD6B752281EF','172.16.4.245','','2015-04-18 17:00:43',NULL,NULL,NULL),(129,'test_dynamic','saleOrder_job_group_name','pause','83BCD0A0B3DD46E1B43FF97148565446','172.16.4.245','','2015-04-18 17:22:39',NULL,NULL,NULL),(130,'test_dynamic','saleOrder_job_group_name','normal','539DB2A9443E4F47A7C157EDB5A7FDA7','172.16.4.245','','2015-04-18 17:22:46',NULL,NULL,NULL),(131,'test_dynamic','saleOrder_job_group_name','update_cron_expression','9D949557153A496EADFB117DC08BCE5A','172.16.4.245','0/15 * * * * ?','2015-04-18 18:58:53',NULL,NULL,NULL),(132,'test_dynamic','saleOrder_job_group_name','remove','19BF575A50CE4B4D9E35802B86AB65DE','172.16.4.245','','2015-04-18 18:59:58',NULL,NULL,NULL),(133,'test_dynamic','saleOrder_job_group_name','add_from_remove','1ADE472EDB764E25A855A4DB29768035','172.16.4.245','','2015-04-18 19:00:23',NULL,NULL,NULL),(134,'test_dynamic','saleOrder_job_group_name','update_cron_expression','08709DE87DC94F509DFF37D6BB7316B0','172.16.4.245','0/14 * * * * ?','2015-04-18 19:26:41',NULL,NULL,NULL),(135,'test_dynamic','saleOrder_job_group_name','update_cron_expression','C66BC8B20C1E40F5833E91EC96A847B1','172.16.4.245','0/15 * * * * ?','2015-04-18 19:26:52',NULL,NULL,NULL),(136,'test_0002','saleOrder_job_group_name','update_cron_expression','B2A8DCA85B5D43309B24636F6A3B29DA','172.16.4.245','0/13 * * * * ?','2015-04-18 19:27:09',NULL,NULL,NULL),(137,'test_0002','saleOrder_job_group_name','update_cron_expression','17F43E2559374CEE85541111BF6892EC','172.16.4.245','0/15 * * * * ?','2015-04-18 19:27:17',NULL,NULL,NULL),(138,'test_0002','saleOrder_job_group_name','pause','47150184A34C4AE0BF41F72659FE1F5D','172.16.4.247','','2015-04-19 11:46:21',NULL,NULL,NULL),(139,'test_0002','saleOrder_job_group_name','normal','BA3564FA5E8A46D59AF54EEBF5F421D9','172.16.4.247','','2015-04-19 11:47:07',NULL,NULL,NULL),(140,'test_0002','saleOrder_job_group_name','pause','14E7553D002B4A56B43D212AA74AF371','172.16.4.247','','2015-04-19 11:48:42',NULL,NULL,NULL),(141,'test_0002','saleOrder_job_group_name','normal','EFC31901C46F4E48B5D07E476C8A6739','172.16.4.247','','2015-04-19 11:48:51',NULL,NULL,NULL),(142,'test_0002','saleOrder_job_group_name','pause','AB3B32540B274CADB04AB06B4CC91B86','172.16.4.247','','2015-04-19 11:49:49',NULL,NULL,NULL),(143,'test_0002','saleOrder_job_group_name','normal','2DF590AEB7D84949A88A24A90D164A66','172.16.4.247','','2015-04-19 11:49:56',NULL,NULL,NULL),(144,'test_0002','saleOrder_job_group_name','pause','590D5264BBFD4948932054DA11D2069D','172.16.4.247','','2015-04-19 11:51:54',NULL,NULL,NULL),(145,'test_0002','saleOrder_job_group_name','normal','4FB91F4A4AA142EDB4544BC2A1DC85CE','172.16.4.247','','2015-04-19 11:52:00',NULL,NULL,NULL),(146,'test_0002','saleOrder_job_group_name','pause','CB1C7CD5ED494A0EB1C321ADF3DEFA5F','172.16.4.247','','2015-04-19 13:33:30',NULL,NULL,NULL),(147,'test_0002','saleOrder_job_group_name','normal','7FEC464AD2EA4593984811D2CCD9FC3E','172.16.4.247','','2015-04-19 13:34:50',NULL,NULL,NULL),(148,'test_0002','saleOrder_job_group_name','pause','E4F4F3C2D2F84B438AD09012B89A8ED6','172.16.4.247','','2015-04-19 14:17:09',NULL,NULL,NULL),(149,'test_0002','saleOrder_job_group_name','normal','09F2CD4E5B0A433BA822DD4D0211A9C1','172.16.4.247','','2015-04-19 14:17:17',NULL,NULL,NULL),(150,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','F04E518CFDA74A9A9C37256D1D6A0802','[172.16.4.247]','0/15 * * * * ?','2015-04-19 14:28:50','',0,'[172.16.4.247]'),(151,'test_dynamic','saleOrder_job_group_name','pause','7884A17FFAAC48ACA5CFEC262E4D7896','172.16.4.247','','2015-04-19 17:09:30',NULL,NULL,NULL),(152,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','BF900C5537B54381A241A7BCF9559D1C','[172.16.4.247]','0/15 * * * * ?','2015-04-19 17:13:26','',0,'[172.16.4.247]'),(153,'test_0002','saleOrder_job_group_name','update_cron_expression','FC9E9FEAB57146D28A7656CC523D12D6','172.16.4.247','0/15 * * * * ?','2015-04-19 17:13:42',NULL,NULL,NULL),(154,'test_dynamic','saleOrder_job_group_name','pause','12320A1A7727446B9EF39DDEF6D60E9B','172.16.4.247','','2015-04-19 17:16:04',NULL,NULL,NULL),(155,'test_dynamic','saleOrder_job_group_name','normal','382C175E74264A6AA1F4868D0161F1F2','172.16.4.247','','2015-04-19 17:16:13',NULL,NULL,NULL),(156,'test_dynamic','saleOrder_job_group_name','pause','34CFDBA3C2AD437791D386EC0AB3FFA0','172.16.4.247','','2015-04-19 17:16:49',NULL,NULL,NULL),(157,'test_dynamic','saleOrder_job_group_name','normal','D53BFF84B2D444098B86D6C2BD39C4D8','172.16.4.247','','2015-04-19 17:17:17',NULL,NULL,NULL),(158,'test_dynamic','saleOrder_job_group_name','remove','91B25A67AFD94FE2991957857B84B899','172.16.4.247','','2015-04-19 17:26:46',NULL,NULL,NULL),(159,'test_dynamic','saleOrder_job_group_name','add_from_remove','4F6288EF711C402DB486A2A9506018F2','172.16.4.247','','2015-04-19 17:27:08',NULL,NULL,NULL),(160,'test_dynamic','saleOrder_job_group_name','pause','C284CAB389564A43AAA919E533CC3F81','172.16.4.247','','2015-04-15 19:31:19',NULL,NULL,NULL),(161,'test_dynamic','saleOrder_job_group_name','normal','35C2668125F243379A182AAAC46B15DA','172.16.4.247','','2015-04-15 19:31:27',NULL,NULL,NULL),(162,'test_dynamic','saleOrder_job_group_name','remove','539049AC62EA4A188215ECE2888289E5','172.16.4.247','','2015-04-15 19:32:09',NULL,NULL,NULL),(163,'test_dynamic','saleOrder_job_group_name','add_from_remove','EF66100E7D9247C98261EAD310BC7C3E','172.16.4.247','','2015-04-15 19:32:16',NULL,NULL,NULL),(164,'test_0002','saleOrder_job_group_name','pause','6E800D46E94D43E4967E430615386274','172.16.4.247','','2015-04-15 19:41:15',NULL,NULL,NULL),(165,'test_0002','saleOrder_job_group_name','normal','030C928125CA47268397BF7BE371FF24','172.16.4.247','','2015-04-15 19:42:20',NULL,NULL,NULL),(166,'test_0002','saleOrder_job_group_name','remove','51F8ED8E1698458DB8AE19F30003C0AD','172.16.4.247','','2015-04-15 19:42:48',NULL,NULL,NULL),(167,'test_0002','saleOrder_job_group_name','add_from_remove','C6753EFB434E4144B84BE72561C02917','172.16.4.247','','2015-04-15 19:43:15',NULL,NULL,NULL),(168,'test_0002','saleOrder_job_group_name','trigger_at_once','B739C3D212694E9F8B3FB61E8B6E7C10','172.16.4.247','','2015-04-15 19:45:07',NULL,NULL,NULL),(169,'test_0002','saleOrder_job_group_name','trigger_at_once','4E9C5A87D4074CF981E3236354304881','172.16.4.247','','2015-04-15 19:45:22',NULL,NULL,NULL),(170,'test_dynamic','saleOrder_job_group_name','dynamic_add_job','AC77B4072BF64AE288A02A009F1DEF4F','[172.16.4.247]','0/15 * * * * ?','2015-04-16 15:35:58','',0,'[172.16.4.247]'),(171,'test_0002','saleOrder_job_group_name','update_cron_expression','29672BB9D33B41708989C3E519F50CEE','172.16.4.247','0/15 * * * * ?','2015-04-16 15:49:11',NULL,NULL,NULL);
/*!40000 ALTER TABLE `sd_action_result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_all_tasks`
--

DROP TABLE IF EXISTS `sd_job_all_tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_all_tasks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名称',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job组名',
  `trigger_name` varchar(100) DEFAULT NULL COMMENT 'trigger名称',
  `trigger_group_name` varchar(100) DEFAULT NULL COMMENT 'trigger组名',
  `job_memo` varchar(500) DEFAULT NULL COMMENT 'job介绍',
  `job_time_type` varchar(50) DEFAULT NULL COMMENT 'simple:cron',
  `job_environment_type` varchar(100) DEFAULT NULL COMMENT 'single:distribute',
  `simple_trigger_json` varchar(500) DEFAULT NULL COMMENT 'simple_trigger配置 json方式',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `only_run_once` tinyint(4) DEFAULT NULL COMMENT '任务只运行一次',
  `target_object` varchar(500) DEFAULT NULL,
  `target_method` varchar(200) DEFAULT NULL,
  `arguments` varchar(5000) DEFAULT NULL,
  `dist_ips` varchar(1000) DEFAULT NULL COMMENT '多节点任务IPS',
  `run_status` varchar(45) DEFAULT NULL,
  `last_action_no` varchar(45) DEFAULT NULL,
  `last_action_result` varchar(45) DEFAULT NULL,
  `last_run_time` datetime DEFAULT NULL COMMENT '上一次运行时间',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `modify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改时间',
  `start_delay` int(11) DEFAULT NULL,
  `repeat_count` int(11) DEFAULT NULL,
  `repeat_interval` int(11) DEFAULT NULL,
  `is_dynamic_add` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`),
  KEY `index_jobGroupName` (`job_group_name`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_all_tasks`
--

LOCK TABLES `sd_job_all_tasks` WRITE;
/*!40000 ALTER TABLE `sd_job_all_tasks` DISABLE KEYS */;
INSERT INTO `sd_job_all_tasks` VALUES (51,'clearTriggerLog_schedule','ttmServer_job_group_name','clearTriggerLog_schedule','ttmServer_trigger_group_name','delete triggerLog more than 300W','cron','single',NULL,'0/30 * * * * ?',NULL,NULL,NULL,NULL,NULL,'normal',NULL,NULL,NULL,NULL,NULL,'2015-05-11 14:10:27','2015-05-11 14:10:27',NULL,NULL,NULL,NULL),(52,'cancleItemTaskJob','showcase_job_group_name','cancleItemTaskJob','showcase_trigger_group_name','cancel_items','cron','single',NULL,'0/30 * * * * ?',NULL,NULL,NULL,NULL,NULL,'normal',NULL,NULL,NULL,NULL,NULL,'2015-05-11 14:52:12','2015-05-11 14:52:12',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `sd_job_all_tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_operator_log`
--

DROP TABLE IF EXISTS `sd_job_operator_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_operator_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operator` varchar(45) DEFAULT NULL COMMENT '操作人',
  `content` longtext COMMENT '操作内容',
  `type` varchar(45) DEFAULT NULL COMMENT '类型：pause resume remove run_at_once add_from_remove_list',
  `job_name` varchar(100) DEFAULT NULL,
  `job_group_name` varchar(100) DEFAULT NULL,
  `action_no` varchar(45) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `success` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`),
  KEY `index_actionNo` (`action_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_operator_log`
--

LOCK TABLES `sd_job_operator_log` WRITE;
/*!40000 ALTER TABLE `sd_job_operator_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_operator_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log`
--

DROP TABLE IF EXISTS `sd_job_trigger_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL,
  `run_memo` longtext,
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `ip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB AUTO_INCREMENT=11574 DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log`
--

LOCK TABLES `sd_job_trigger_log` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log` DISABLE KEYS */;
INSERT INTO `sd_job_trigger_log` VALUES (11534,'clearTriggerLog_schedule','ttmServer_job_group_name','0/30 * * * * ?','class=>class com.elsa.ttm.quartz.ao.ClearTriggerLogAO;method=>deleteTriggerLog;params=>[];result=>true;get lock start=>1431324630020;end=>1431324630048;hasLock=>true',1431324630054,1431324630064,1,'2015-05-11 14:10:30','10.10.10.165'),(11535,'clearTriggerLog_schedule','ttmServer_job_group_name','0/30 * * * * ?','class=>class com.elsa.ttm.quartz.ao.ClearTriggerLogAO;method=>deleteTriggerLog;params=>[];result=>true;get lock start=>1431326340036;end=>1431326340063;hasLock=>true',1431326340071,1431326340076,1,'2015-05-11 14:39:00','10.10.10.165'),(11536,'clearTriggerLog_schedule','ttmServer_job_group_name','0/30 * * * * ?','class=>class com.elsa.ttm.quartz.ao.ClearTriggerLogAO;method=>deleteTriggerLog;params=>[];result=>true;get lock start=>1431326370004;end=>1431326370041;hasLock=>true',1431326370042,1431326370046,1,'2015-05-11 14:39:30','10.10.10.165'),(11537,'clearTriggerLog_schedule','ttmServer_job_group_name','0/30 * * * * ?','class=>class com.elsa.ttm.quartz.ao.ClearTriggerLogAO;method=>deleteTriggerLog;params=>[];result=>true;get lock start=>1431326400002;end=>1431326400040;hasLock=>true',1431326400041,1431326400045,1,'2015-05-11 14:40:00','10.10.10.165'),(11538,'clearTriggerLog_schedule','ttmServer_job_group_name','0/30 * * * * ?','class=>class com.elsa.ttm.quartz.ao.ClearTriggerLogAO;method=>deleteTriggerLog;params=>[];result=>true;get lock start=>1431326430004;end=>1431326430031;hasLock=>true',1431326430032,1431326430036,1,'2015-05-11 14:40:30','10.10.10.165'),(11539,'clearTriggerLog_schedule','ttmServer_job_group_name','0/30 * * * * ?','class=>class com.elsa.ttm.quartz.ao.ClearTriggerLogAO;method=>deleteTriggerLog;params=>[];result=>true;get lock start=>1431326460004;end=>1431326460022;hasLock=>true',1431326460023,1431326460027,1,'2015-05-11 14:41:00','10.10.10.165'),(11540,'cancleItemTaskJob','showcase_job_group_name','0/30 * * * * ?','class=>class com.elsa.quartz.ao.CancleItemAO;method=>execute;params=>[];result=>true;get lock start=>1431327150027;end=>1431327150047;hasLock=>true',1431327150058,1431327150059,1,'2015-05-11 14:52:30','10.10.10.165'),(11541,'cancleItemTaskJob','showcase_job_group_name','0/30 * * * * ?','class=>class com.elsa.quartz.ao.CancleItemAO;method=>execute;params=>[];result=>true;get lock start=>1431327180004;end=>1431327180026;hasLock=>true',1431327180027,1431327180028,1,'2015-05-11 14:53:00','10.10.10.165'),(11542,'cancleItemTaskJob','showcase_job_group_name','0/30 * * * * ?','class=>class com.elsa.quartz.ao.CancleItemAO;method=>execute;params=>[];result=>true;get lock start=>1431327210002;end=>1431327210024;hasLock=>true',1431327210025,1431327210025,1,'2015-05-11 14:53:30','10.10.10.165'),(11543,'cancleItemTaskJob','showcase_job_group_name','0/30 * * * * ?','class=>class com.elsa.quartz.ao.CancleItemAO;method=>execute;params=>[];result=>true;get lock start=>1431327450006;end=>1431327450032;hasLock=>true',1431327450036,1431327450036,1,'2015-05-11 14:57:30','10.10.10.165'),(11544,'cancleItemTaskJob','showcase_job_group_name','0/30 * * * * ?','class=>class com.elsa.quartz.ao.CancleItemAO;method=>execute;params=>[];result=>true;get lock start=>1431327480003;end=>1431327480025;hasLock=>true',1431327480026,1431327480027,1,'2015-05-11 14:58:00','10.10.10.165'),(11545,'clearTriggerLog_schedule','ttmServer_job_group_name','0/30 * * * * ?','class=>class com.elsa.ttm.quartz.ao.ClearTriggerLogAO;method=>deleteTriggerLog;params=>[];result=>true;get lock start=>1431336270006;end=>1431336270040;hasLock=>true',1431336270043,1431336270049,1,'2015-05-11 17:24:30','10.10.10.165'),(11546,'clearTriggerLog_schedule','ttmServer_job_group_name','0/30 * * * * ?','class=>class com.elsa.ttm.quartz.ao.ClearTriggerLogAO;method=>deleteTriggerLog;params=>[];result=>true;get lock start=>1431336990038;end=>1431336990061;hasLock=>true',1431336990066,1431336990069,1,'2015-05-11 17:36:30','10.10.10.165'),(11547,'cancleItemTaskJob','showcase_job_group_name','0/30 * * * * ?','class=>class com.elsa.quartz.ao.CancleItemAO;method=>execute;params=>[];result=>true;get lock start=>1431340020020;end=>1431340020051;hasLock=>true',1431340020061,1431340020063,1,'2015-05-11 18:27:00','10.10.10.165'),(11548,'cancleItemTaskJob','showcase_job_group_name','0/30 * * * * ?','class=>class com.elsa.quartz.ao.CancleItemAO;method=>execute;params=>[];result=>true;get lock start=>1431341010021;end=>1431341010051;hasLock=>true',1431341010062,1431341010063,1,'2015-05-11 18:43:30','10.10.10.165'),(11549,'cancleItemTaskJob','showcase_job_group_name','0/30 * * * * ?','class=>class com.elsa.quartz.ao.CancleItemAO;method=>execute;params=>[];result=>true;get lock start=>1431341040002;end=>1431341040076;hasLock=>true',1431341040076,1431341040077,1,'2015-05-11 18:44:00','10.10.10.165'),(11550,'cancleItemTaskJob','showcase_job_group_name','0/30 * * * * ?','class=>class com.elsa.quartz.ao.CancleItemAO;method=>execute;params=>[];result=>true;get lock start=>1431345150008;end=>1431345150059;hasLock=>true',1431345150062,1431345150062,1,'2015-05-11 19:52:30','10.10.10.165'),(11551,'cancleItemTaskJob','showcase_job_group_name','0/30 * * * * ?','class=>class com.elsa.quartz.ao.CancleItemAO;method=>execute;params=>[];result=>true;get lock start=>1431345180004;end=>1431345180034;hasLock=>true',1431345180035,1431345180037,1,'2015-05-11 19:53:00','10.10.10.165'),(11552,'cancleItemTaskJob','showcase_job_group_name','0/30 * * * * ?','class=>class com.elsa.quartz.ao.CancleItemAO;method=>execute;params=>[];result=>true;get lock start=>1431345210002;end=>1431345210029;hasLock=>true',1431345210030,1431345210031,1,'2015-05-11 19:53:30','10.10.10.165'),(11553,'cancleItemTaskJob','showcase_job_group_name','0/30 * * * * ?','class=>class com.elsa.quartz.ao.CancleItemAO;method=>execute;params=>[];result=>true;get lock start=>1431345240003;end=>1431345240029;hasLock=>true',1431345240029,1431345240031,1,'2015-05-11 19:54:00','10.10.10.165'),(11554,'cancleItemTaskJob','showcase_job_group_name','0/30 * * * * ?','class=>class com.elsa.quartz.ao.CancleItemAO;method=>execute;params=>[];result=>true;get lock start=>1431345270002;end=>1431345270028;hasLock=>true',1431345270029,1431345270030,1,'2015-05-11 19:54:30','10.10.10.165'),(11555,'cancleItemTaskJob','showcase_job_group_name','0/30 * * * * ?','class=>class com.elsa.quartz.ao.CancleItemAO;method=>execute;params=>[];result=>true;get lock start=>1431345300002;end=>1431345300026;hasLock=>true',1431345300027,1431345300028,1,'2015-05-11 19:55:00','10.10.10.165'),(11556,'cancleItemTaskJob','showcase_job_group_name','0/30 * * * * ?','class=>class com.elsa.quartz.ao.CancleItemAO;method=>execute;params=>[];result=>true;get lock start=>1431345330003;end=>1431345330037;hasLock=>true',1431345330038,1431345330039,1,'2015-05-11 19:55:30','10.10.10.165'),(11557,'cancleItemTaskJob','showcase_job_group_name','0/30 * * * * ?','class=>class com.elsa.quartz.ao.CancleItemAO;method=>execute;params=>[];result=>true;get lock start=>1431345360002;end=>1431345360028;hasLock=>true',1431345360028,1431345360029,1,'2015-05-11 19:56:00','10.10.10.165'),(11558,'cancleItemTaskJob','showcase_job_group_name','0/30 * * * * ?','class=>class com.elsa.quartz.ao.CancleItemAO;method=>execute;params=>[];result=>true;get lock start=>1431345390003;end=>1431345390028;hasLock=>true',1431345390029,1431345390030,1,'2015-05-11 19:56:30','10.10.10.165'),(11559,'cancleItemTaskJob','showcase_job_group_name','0/30 * * * * ?','class=>class com.elsa.quartz.ao.CancleItemAO;method=>execute;params=>[];result=>true;get lock start=>1431345420002;end=>1431345420026;hasLock=>true',1431345420027,1431345420028,1,'2015-05-11 19:57:00','10.10.10.165'),(11560,'cancleItemTaskJob','showcase_job_group_name','0/30 * * * * ?','class=>class com.elsa.quartz.ao.CancleItemAO;method=>execute;params=>[];result=>true;get lock start=>1431345750012;end=>1431345750041;hasLock=>true',1431345750047,1431345750047,1,'2015-05-11 20:02:30','10.10.10.165'),(11561,'clearTriggerLog_schedule','ttmServer_job_group_name','0/30 * * * * ?','class=>class com.elsa.ttm.quartz.ao.ClearTriggerLogAO;method=>deleteTriggerLog;params=>[];result=>true;get lock start=>1431399720020;end=>1431399720045;hasLock=>true',1431399720057,1431399720064,1,'2015-05-12 11:02:00','10.10.10.165'),(11562,'clearTriggerLog_schedule','ttmServer_job_group_name','0/30 * * * * ?','class=>class com.elsa.ttm.quartz.ao.ClearTriggerLogAO;method=>deleteTriggerLog;params=>[];result=>true;get lock start=>1431399750003;end=>1431399750031;hasLock=>true',1431399750031,1431399750034,1,'2015-05-12 11:02:30','10.10.10.165'),(11563,'clearTriggerLog_schedule','ttmServer_job_group_name','0/30 * * * * ?','class=>class com.elsa.ttm.quartz.ao.ClearTriggerLogAO;method=>deleteTriggerLog;params=>[];result=>true;get lock start=>1431399780002;end=>1431399780021;hasLock=>true',1431399780021,1431399780024,1,'2015-05-12 11:03:00','10.10.10.165'),(11564,'clearTriggerLog_schedule','ttmServer_job_group_name','0/30 * * * * ?','class=>class com.elsa.ttm.quartz.ao.ClearTriggerLogAO;method=>deleteTriggerLog;params=>[];result=>true;get lock start=>1431401040035;end=>1431401040068;hasLock=>true',1431401040074,1431401040078,1,'2015-05-12 11:24:00','10.10.10.165'),(11565,'clearTriggerLog_schedule','ttmServer_job_group_name','0/30 * * * * ?','class=>class com.elsa.ttm.quartz.ao.ClearTriggerLogAO;method=>deleteTriggerLog;params=>[];result=>true;get lock start=>1431401460022;end=>1431401460048;hasLock=>true',1431401460055,1431401460058,1,'2015-05-12 11:31:00','10.10.10.165'),(11566,'clearTriggerLog_schedule','ttmServer_job_group_name','0/30 * * * * ?','class=>class com.elsa.ttm.quartz.ao.ClearTriggerLogAO;method=>deleteTriggerLog;params=>[];result=>true;get lock start=>1431419790028;end=>1431419790058;hasLock=>true',1431419790063,1431419790066,1,'2015-05-12 16:36:30','10.10.10.165'),(11567,'clearTriggerLog_schedule','ttmServer_job_group_name','0/30 * * * * ?','class=>class com.elsa.ttm.quartz.ao.ClearTriggerLogAO;method=>deleteTriggerLog;params=>[];result=>true;get lock start=>1431419820002;end=>1431419820016;hasLock=>true',1431419820016,1431419820017,1,'2015-05-12 16:37:00','10.10.10.165'),(11568,'clearTriggerLog_schedule','ttmServer_job_group_name','0/30 * * * * ?','class=>class com.elsa.ttm.quartz.ao.ClearTriggerLogAO;method=>deleteTriggerLog;params=>[];result=>true;get lock start=>1431419850002;end=>1431419850031;hasLock=>true',1431419850032,1431419850035,1,'2015-05-12 16:37:30','10.10.10.165'),(11569,'clearTriggerLog_schedule','ttmServer_job_group_name','0/30 * * * * ?','class=>class com.elsa.ttm.quartz.ao.ClearTriggerLogAO;method=>deleteTriggerLog;params=>[];result=>true;get lock start=>1431419880002;end=>1431419880026;hasLock=>true',1431419880027,1431419880029,1,'2015-05-12 16:38:00','10.10.10.165'),(11570,'clearTriggerLog_schedule','ttmServer_job_group_name','0/30 * * * * ?','class=>class com.elsa.ttm.quartz.ao.ClearTriggerLogAO;method=>deleteTriggerLog;params=>[];result=>true;get lock start=>1431419910003;end=>1431419910025;hasLock=>true',1431419910026,1431419910029,1,'2015-05-12 16:38:30','10.10.10.165'),(11571,'clearTriggerLog_schedule','ttmServer_job_group_name','0/30 * * * * ?','class=>class com.elsa.ttm.quartz.ao.ClearTriggerLogAO;method=>deleteTriggerLog;params=>[];result=>true;get lock start=>1431483690030;end=>1431483690063;hasLock=>true',1431483690068,1431483690070,1,'2015-05-13 10:21:30','10.10.10.165'),(11572,'clearTriggerLog_schedule','ttmServer_job_group_name','0/30 * * * * ?','class=>class com.elsa.ttm.quartz.ao.ClearTriggerLogAO;method=>deleteTriggerLog;params=>[];result=>true;get lock start=>1431505680025;end=>1431505680053;hasLock=>true',1431505680060,1431505680063,1,'2015-05-13 16:28:00','10.10.10.165'),(11573,'clearTriggerLog_schedule','ttmServer_job_group_name','0/30 * * * * ?','class=>class com.elsa.ttm.quartz.ao.ClearTriggerLogAO;method=>deleteTriggerLog;params=>[];result=>true;get lock start=>1431505710205;end=>1431505710227;hasLock=>true',1431505710230,1431505710231,1,'2015-05-13 16:28:30','10.10.10.165');
/*!40000 ALTER TABLE `sd_job_trigger_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201501`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201501`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201501` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201501`
--

LOCK TABLES `sd_job_trigger_log_201501` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201501` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201501` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201502`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201502`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201502` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201502`
--

LOCK TABLES `sd_job_trigger_log_201502` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201502` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201502` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201503`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201503`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201503` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201503`
--

LOCK TABLES `sd_job_trigger_log_201503` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201503` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201503` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201504`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201504`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201504` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201504`
--

LOCK TABLES `sd_job_trigger_log_201504` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201504` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201504` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201505`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201505`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201505` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201505`
--

LOCK TABLES `sd_job_trigger_log_201505` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201505` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201505` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201506`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201506`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201506` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201506`
--

LOCK TABLES `sd_job_trigger_log_201506` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201506` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201506` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201507`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201507`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201507` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201507`
--

LOCK TABLES `sd_job_trigger_log_201507` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201507` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201507` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201508`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201508`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201508` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201508`
--

LOCK TABLES `sd_job_trigger_log_201508` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201508` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201508` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201509`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201509`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201509` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201509`
--

LOCK TABLES `sd_job_trigger_log_201509` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201509` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201509` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201510`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201510`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201510` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201510`
--

LOCK TABLES `sd_job_trigger_log_201510` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201510` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201510` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201511`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201511`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201511` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201511`
--

LOCK TABLES `sd_job_trigger_log_201511` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201511` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201511` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201512`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201512`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201512` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201512`
--

LOCK TABLES `sd_job_trigger_log_201512` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201512` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201512` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201601`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201601`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201601` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201601`
--

LOCK TABLES `sd_job_trigger_log_201601` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201601` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201601` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201602`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201602`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201602` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201602`
--

LOCK TABLES `sd_job_trigger_log_201602` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201602` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201602` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201603`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201603`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201603` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201603`
--

LOCK TABLES `sd_job_trigger_log_201603` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201603` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201603` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201604`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201604`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201604` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201604`
--

LOCK TABLES `sd_job_trigger_log_201604` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201604` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201604` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201605`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201605`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201605` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201605`
--

LOCK TABLES `sd_job_trigger_log_201605` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201605` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201605` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201606`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201606`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201606` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201606`
--

LOCK TABLES `sd_job_trigger_log_201606` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201606` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201606` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201607`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201607`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201607` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201607`
--

LOCK TABLES `sd_job_trigger_log_201607` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201607` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201607` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201608`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201608`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201608` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201608`
--

LOCK TABLES `sd_job_trigger_log_201608` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201608` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201608` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201609`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201609`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201609` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201609`
--

LOCK TABLES `sd_job_trigger_log_201609` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201609` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201609` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201610`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201610`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201610` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201610`
--

LOCK TABLES `sd_job_trigger_log_201610` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201610` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201610` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201611`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201611`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201611` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201611`
--

LOCK TABLES `sd_job_trigger_log_201611` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201611` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201611` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201612`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201612`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201612` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201612`
--

LOCK TABLES `sd_job_trigger_log_201612` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201612` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201612` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201701`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201701`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201701` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201701`
--

LOCK TABLES `sd_job_trigger_log_201701` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201701` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201701` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201702`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201702`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201702` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201702`
--

LOCK TABLES `sd_job_trigger_log_201702` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201702` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201702` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201703`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201703`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201703` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201703`
--

LOCK TABLES `sd_job_trigger_log_201703` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201703` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201703` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201704`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201704`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201704` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201704`
--

LOCK TABLES `sd_job_trigger_log_201704` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201704` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201704` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201705`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201705`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201705` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201705`
--

LOCK TABLES `sd_job_trigger_log_201705` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201705` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201705` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201706`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201706`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201706` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201706`
--

LOCK TABLES `sd_job_trigger_log_201706` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201706` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201706` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201707`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201707`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201707` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201707`
--

LOCK TABLES `sd_job_trigger_log_201707` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201707` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201707` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201708`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201708`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201708` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201708`
--

LOCK TABLES `sd_job_trigger_log_201708` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201708` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201708` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201709`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201709`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201709` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201709`
--

LOCK TABLES `sd_job_trigger_log_201709` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201709` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201709` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201710`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201710`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201710` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201710`
--

LOCK TABLES `sd_job_trigger_log_201710` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201710` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201710` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201711`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201711`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201711` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201711`
--

LOCK TABLES `sd_job_trigger_log_201711` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201711` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201711` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201712`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201712`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201712` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201712`
--

LOCK TABLES `sd_job_trigger_log_201712` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201712` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201712` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201801`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201801`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201801` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201801`
--

LOCK TABLES `sd_job_trigger_log_201801` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201801` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201801` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201802`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201802`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201802` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201802`
--

LOCK TABLES `sd_job_trigger_log_201802` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201802` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201802` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201803`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201803`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201803` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201803`
--

LOCK TABLES `sd_job_trigger_log_201803` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201803` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201803` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201804`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201804`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201804` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201804`
--

LOCK TABLES `sd_job_trigger_log_201804` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201804` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201804` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201805`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201805`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201805` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201805`
--

LOCK TABLES `sd_job_trigger_log_201805` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201805` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201805` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201806`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201806`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201806` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201806`
--

LOCK TABLES `sd_job_trigger_log_201806` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201806` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201806` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201807`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201807`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201807` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201807`
--

LOCK TABLES `sd_job_trigger_log_201807` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201807` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201807` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201808`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201808`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201808` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201808`
--

LOCK TABLES `sd_job_trigger_log_201808` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201808` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201808` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201809`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201809`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201809` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201809`
--

LOCK TABLES `sd_job_trigger_log_201809` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201809` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201809` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201810`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201810`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201810` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201810`
--

LOCK TABLES `sd_job_trigger_log_201810` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201810` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201810` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201811`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201811`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201811` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201811`
--

LOCK TABLES `sd_job_trigger_log_201811` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201811` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201811` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_trigger_log_201812`
--

DROP TABLE IF EXISTS `sd_job_trigger_log_201812`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_trigger_log_201812` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名',
  `job_group_name` varchar(100) DEFAULT NULL COMMENT 'job所属组',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT '时间表达式',
  `run_memo` longtext COMMENT '运行信息备注',
  `start_time` bigint(20) DEFAULT NULL COMMENT '方法运行开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '方法运行结束时间',
  `run_status` int(11) DEFAULT NULL COMMENT 'job运行是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `index_jobName_jobGroupName` (`job_name`,`job_group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务运行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_trigger_log_201812`
--

LOCK TABLES `sd_job_trigger_log_201812` WRITE;
/*!40000 ALTER TABLE `sd_job_trigger_log_201812` DISABLE KEYS */;
/*!40000 ALTER TABLE `sd_job_trigger_log_201812` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_job_users`
--

DROP TABLE IF EXISTS `sd_job_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sd_job_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) NOT NULL,
  `pass_word` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL COMMENT 'admin tl',
  `group_names` varchar(300) NOT NULL,
  `is_delete` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `index_userName_passWord` (`user_name`,`pass_word`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_job_users`
--

LOCK TABLES `sd_job_users` WRITE;
/*!40000 ALTER TABLE `sd_job_users` DISABLE KEYS */;
INSERT INTO `sd_job_users` VALUES (1,'admin','8cc4f5175431de46d0b9bd080e929edc','admin','saleOrder_job_group_name',0,'2015-04-19 10:44:54','2015-04-19 11:22:48'),(2,'test001','8cc4f5175431de46d0b9bd080e929edc','tl','saleOrder_job_group_name',0,'2015-04-19 14:22:44','2015-04-19 14:22:44');
/*!40000 ALTER TABLE `sd_job_users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-14 17:41:40
