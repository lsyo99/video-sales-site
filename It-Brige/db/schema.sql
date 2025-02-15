-- MySQL dump 10.13  Distrib 8.0.33, for macos13 (arm64)
--
-- Host: 127.0.0.1    Database: ItBridge
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `ItBridge`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `ItBridge` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `ItBridge`;

--
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authority` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `level` tinyint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authority`
--

LOCK TABLES `authority` WRITE;
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;
INSERT INTO `authority` VALUES (1,'ADMIN',1),(2,'USER',2);
/*!40000 ALTER TABLE `authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `authority_id` bigint DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `authority_id` (`authority_id`),
  CONSTRAINT `board_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
  CONSTRAINT `board_ibfk_2` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES (1,'공지사항',1,1,'2025-01-29 01:43:57','2025-01-29 01:43:57');
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `post_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `body` text NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `post_id` (`post_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,1,1,'1st comment','2025-01-30 22:04:11','2025-01-30 22:04:11'),(2,2,1,'2st comments','2025-01-30 22:05:10','2025-01-30 22:05:10'),(3,2,1,'3st comment','2025-01-30 22:05:10','2025-01-30 22:05:10'),(4,1,1,'4st comments','2025-01-30 22:05:10','2025-01-30 22:05:10'),(5,1,1,'직접 작성 댓글','2025-02-01 21:14:16',NULL),(6,14,1,'..........','2025-02-05 00:42:47','2025-02-05 00:42:47');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `files`
--

DROP TABLE IF EXISTS `files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `files` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `post_id` bigint NOT NULL,
  `file_name` varchar(255) NOT NULL,
  `file_url` text NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_files_post` (`post_id`),
  CONSTRAINT `fk_files_post` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `files`
--

LOCK TABLES `files` WRITE;
/*!40000 ALTER TABLE `files` DISABLE KEYS */;
INSERT INTO `files` VALUES (1,14,'스크린샷 2024-12-08 오후 3.55.13.png','/Users/iseoyeong/Desktop/It-bridge/It-Brige/src/main/resources/static/savefile/2ae0a4e4-d292-4065-97e6-67cb6dc290a2_스크린샷 2024-12-08 오후 3.55.13.png','2025-02-02 18:53:08'),(2,14,'스크린샷 2024-12-08 오후 6.02.01.png','/Users/iseoyeong/Desktop/It-bridge/It-Brige/src/main/resources/static/savefile/c801bdbd-a899-4e68-95d9-959c852a22ae_스크린샷 2024-12-08 오후 6.02.01.png','2025-02-02 18:53:08'),(3,15,'스크린샷 2024-12-08 오후 3.55.13.png','/Users/iseoyeong/Desktop/It-bridge/It-Brige/src/main/resources/static/savefile/52fa25aa-51e8-4552-9af9-3a824e678f90_스크린샷 2024-12-08 오후 3.55.13.png','2025-02-02 18:53:50'),(4,15,'스크린샷 2024-12-08 오후 6.02.01.png','/Users/iseoyeong/Desktop/It-bridge/It-Brige/src/main/resources/static/savefile/c02f7857-2e17-4a31-823a-f4ad20dd3199_스크린샷 2024-12-08 오후 6.02.01.png','2025-02-02 18:53:50'),(5,16,'스크린샷 2024-12-11 오전 9.52.46.png','/Users/iseoyeong/Desktop/It-bridge/It-Brige/src/main/resources/static/savefile/28ae1d98-aeea-4815-8494-5cdf6e8264ad_스크린샷 2024-12-11 오전 9.52.46.png','2025-02-02 18:58:13');
/*!40000 ALTER TABLE `files` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lecture`
--

DROP TABLE IF EXISTS `lecture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lecture` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `category` varchar(50) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `sales` bigint DEFAULT NULL,
  `likes` int DEFAULT '0',
  `thumbnail_url` varchar(1024) NOT NULL,
  `tags` json DEFAULT NULL,
  `upload_at` datetime DEFAULT NULL,
  `ranking` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecture`
--

LOCK TABLES `lecture` WRITE;
/*!40000 ALTER TABLE `lecture` DISABLE KEYS */;
INSERT INTO `lecture` VALUES (1,'테디노트의 딥러닝 비법노트','딥러닝',10.00,65,1200,'/image/lectureThumbnail/1.webp','[\"BEST\", \"FC AWARD\"]','2024-12-01 10:00:00',1),(2,'머신러닝 기초','머신러닝',400000.00,50,1590,'/image/lectureThumbnail/2.webp','[\"BEST\", \"FC AWARD\"]','2024-12-01 11:00:00',2),(3,'하이퍼리얼리티 인물 제작','딥러닝',353000.00,65,570,'/image/lectureThumbnail/4.webp','[\"BEST\", \"NEW\", \"온라인\"]','2024-11-01 12:00:00',3),(4,'정보처리기사 아카데미','자격증',304500.00,30,2930,'/image/lectureThumbnail/5.webp','[\"BEST\", \"온라인\", \"자격증\", \"정보처리기사\"]','2024-10-01 13:00:00',4),(5,'LangGraph로 끝내는 멀티 AI Agent','딥러닝',283500.00,65,1030,'/image/lectureThumbnail/6.webp','[\"BEST\"]','2024-10-01 14:00:00',5),(6,'백엔드개발','백엔드',275.00,40,1200,'/image/lectureThumbnail/7.webp','[\"BEST\", \"FC AWARD\"]','2025-02-01 15:00:00',1),(7,'디자인','디자인',400000.00,65,1590,'/image/lectureThumbnail/8.webp','[\"BEST\", \"FC AWARD\"]','2025-02-01 16:00:00',1),(8,'빅데이터분석','데이터분석',353000.00,65,570,'/image/lectureThumbnail/9.webp','[\"BEST\", \"NEW\", \"온라인\"]','2025-01-01 17:00:00',1),(9,'앱개발','앱개발',304500.00,70,2930,'/image/lectureThumbnail/3.webp','[\"BEST\", \"온라인\", \"자격증\", \"정보처리기사\"]','2025-02-01 18:00:00',1),(10,'프론트엔드개발','프론트엔드',283500.00,65,1030,'/image/lectureThumbnail/10.webp','[\"BEST\"]','2025-02-01 19:00:00',1),(11,'test','앱개발',100.00,1,0,'/static/thumbnails/d54ae1d5-4bed-4f13-b8bf-a33d37bb0894_스크린샷 2024-12-08 오후 3.55.13.png','[\"test\"]','2025-02-08 02:16:18',NULL);
/*!40000 ALTER TABLE `lecture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lecture_detail`
--

DROP TABLE IF EXISTS `lecture_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lecture_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `lecture_id` bigint NOT NULL,
  `url` varchar(1024) NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `sort_img` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `lecture_id` (`lecture_id`),
  CONSTRAINT `lecture_detail_ibfk_1` FOREIGN KEY (`lecture_id`) REFERENCES `lecture` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecture_detail`
--

LOCK TABLES `lecture_detail` WRITE;
/*!40000 ALTER TABLE `lecture_detail` DISABLE KEYS */;
INSERT INTO `lecture_detail` VALUES (1,1,'/image/back1/1.png','2024-08-06 12:00:00','2024-12-08 00:53:42',1),(2,1,'/image/back1/2.png','2024-08-06 12:00:00','2024-12-08 00:53:42',2),(3,1,'/image/back1/3.png','2024-08-06 12:00:00','2024-12-08 00:53:42',3),(4,1,'/image/back1/4.jpeg','2024-08-06 12:00:00','2024-12-08 00:53:42',4),(5,1,'/image/back1/5.jpeg','2024-08-06 12:00:00','2024-12-08 00:53:42',5),(6,1,'/image/back1/6.jpeg','2024-08-06 12:00:00','2024-12-08 00:53:42',6),(7,1,'/image/back1/7.jpeg','2024-08-06 12:00:00','2024-12-08 00:53:42',7),(8,1,'/image/back1/8.jpeg','2024-08-06 12:00:00','2024-12-08 00:53:42',8),(10,11,'/static/details/6bbafb42-4890-46fb-ba5c-91d139f85031_스크린샷 2024-12-08 오후 3.55.13.png','2025-02-08 02:16:18','2025-02-08 02:16:18',1),(11,11,'/static/details/52dfcdb0-385b-4b90-a172-7b26b9f1bfa8_스크린샷 2024-12-08 오후 6.02.01.png','2025-02-08 02:16:18','2025-02-08 02:16:18',2),(12,11,'/static/details/76c7c215-e302-4434-9cb8-4deb9fa454e6_스크린샷 2024-12-09 오후 12.46.58.png','2025-02-08 02:16:18','2025-02-08 02:16:18',3),(13,11,'/static/details/f25a157c-c9d7-41b0-90c7-77b27e355531_스크린샷 2024-12-11 오전 9.52.46.png','2025-02-08 02:16:18','2025-02-08 02:16:18',4),(14,11,'/static/details/0aa01424-96f5-4e68-9c81-f1d427a94f8b_스크린샷 2024-12-11 오후 4.31.02.png','2025-02-08 02:16:18','2025-02-08 02:16:18',5),(15,11,'/static/details/96e2f97b-2029-4d5e-afb9-6d5a2f8bb882_스크린샷 2024-12-14 오전 2.39.47.png','2025-02-08 02:16:18','2025-02-08 02:16:18',6);
/*!40000 ALTER TABLE `lecture_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `lecture_id` bigint NOT NULL,
  `payed_date` datetime NOT NULL,
  `payed_method` varchar(40) NOT NULL,
  `salse` int DEFAULT NULL,
  `account` decimal(10,2) NOT NULL,
  `first_price` decimal(10,2) DEFAULT NULL,
  `merchant_uid` varchar(255) NOT NULL,
  `imp_uid` varchar(255) NOT NULL COMMENT '결제 고유번호',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `lecture_id` (`lecture_id`),
  CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
  CONSTRAINT `payment_ibfk_2` FOREIGN KEY (`lecture_id`) REFERENCES `lecture` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,1,1,'2024-01-01 00:00:00','카드결제',20,180000.00,200000.00,'',''),(2,1,2,'2024-01-02 00:00:00','카드결제',20,180000.00,200000.00,'',''),(3,1,3,'2024-01-03 00:00:00','계좌이체',20,180000.00,200000.00,'',''),(4,1,1,'2025-02-03 22:17:52','html5_inicis',NULL,10.00,10.00,'O1738588652111','imp_806652531463'),(5,1,1,'2025-02-03 22:20:45','html5_inicis',NULL,10.00,10.00,'O1738588823608','imp_509824092071');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `body` text,
  `board_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `board_id` (`board_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`board_id`) REFERENCES `board` (`id`),
  CONSTRAINT `post_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,'first notice','첫계시글 입니다.',1,1,'2025-01-29 01:44:51'),(2,'2nd post','2nd post',1,1,NULL),(3,'3nd post','i update to date auto',1,1,'2025-01-29 02:19:04'),(4,'dsadsa','dsadsad',1,1,'2025-02-02 18:28:49'),(5,'dsadsadsad','dsadsad',1,2,'2025-02-02 18:29:40'),(6,'dsadsadsad','dsadsad\n<img src=\"https://www.google.co.kr/url?sa=i&url=https%3A%2F%2Fwww.istockphoto.com%2Fkr%2F%25EC%259D%25B4%25EB%25AF%25B8%25EC%25A7%2580%2F%25EA%25B0%259C-%25EC%259D%25B4%25EB%25AF%25B8%25EC%25A7%2580&psig=AOvVaw3sQ71U-OsP8YXAJolFRL83&ust=1738523369590000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCIDGmbGWo4sDFQAAAAAdAAAAABAE\" alt=\"이미지\" style=\"max-width:100%;\">\n',1,2,'2025-02-02 18:32:37'),(7,'dsadsadsad','dsadsad\n<img src=\"https://www.google.co.kr/url?sa=i&url=https%3A%2F%2Fwww.istockphoto.com%2Fkr%2F%25EC%259D%25B4%25EB%25AF%25B8%25EC%25A7%2580%2F%25EA%25B0%259C-%25EC%259D%25B4%25EB%25AF%25B8%25EC%25A7%2580&psig=AOvVaw3sQ71U-OsP8YXAJolFRL83&ust=1738523369590000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCIDGmbGWo4sDFQAAAAAdAAAAABAE\" alt=\"이미지\" style=\"max-width:100%;\">\n',1,2,'2025-02-02 18:33:12'),(8,'파일 테스트','파일 테스트\n<img src=\"https://www.google.co.kr/url?sa=i&url=https%3A%2F%2Fwww.istockphoto.com%2Fkr%2F%25EC%259D%25B4%25EB%25AF%25B8%25EC%25A7%2580%2F%25EA%25B0%259C-%25EC%259D%25B4%25EB%25AF%25B8%25EC%25A7%2580&psig=AOvVaw3sQ71U-OsP8YXAJolFRL83&ust=1738523369590000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCIDGmbGWo4sDFQAAAAAdAAAAABAE\" alt=\"이미지\" style=\"max-width:100%;\">\n',1,2,'2025-02-02 18:38:22'),(9,'파일 테스트','파일 테스트\n<img src=\"https://www.google.co.kr/url?sa=i&url=https%3A%2F%2Fwww.istockphoto.com%2Fkr%2F%25EC%259D%25B4%25EB%25AF%25B8%25EC%25A7%2580%2F%25EA%25B0%259C-%25EC%259D%25B4%25EB%25AF%25B8%25EC%25A7%2580&psig=AOvVaw3sQ71U-OsP8YXAJolFRL83&ust=1738523369590000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCIDGmbGWo4sDFQAAAAAdAAAAABAE\" alt=\"이미지\" style=\"max-width:100%;\">\n',1,2,'2025-02-02 18:38:37'),(10,'dsadsa','dsadsa',1,2,'2025-02-02 18:46:06'),(11,'dsadsad','dsadsadadsad\n\n<img src=\"https://www.google.co.kr/url?sa=i&url=https%3A%2F%2Fwww.istockphoto.com%2Fkr%2F%25EC%259D%25B4%25EB%25AF%25B8%25EC%25A7%2580%2F%25EA%25B0%259C-%25EC%259D%25B4%25EB%25AF%25B8%25EC%25A7%2580&psig=AOvVaw3sQ71U-OsP8YXAJolFRL83&ust=1738523369590000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCIDGmbGWo4sDFQAAAAAdAAAAABAE\" alt=\"이미지\" style=\"max-width:100%;\">\n',1,2,'2025-02-02 18:46:46'),(12,'테스트','테스트\n<img src=\"https://www.google.co.kr/url?sa=i&url=https%3A%2F%2Fwww.istockphoto.com%2Fkr%2F%25EC%259D%25B4%25EB%25AF%25B8%25EC%25A7%2580%2F%25EA%25B0%259C-%25EC%259D%25B4%25EB%25AF%25B8%25EC%25A7%2580&psig=AOvVaw3sQ71U-OsP8YXAJolFRL83&ust=1738523369590000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCIDGmbGWo4sDFQAAAAAdAAAAABAE\" alt=\"이미지\" style=\"max-width:100%;\">\n',1,2,'2025-02-02 18:47:35'),(13,'1','1',1,2,'2025-02-02 18:51:59'),(14,'2','2',1,2,'2025-02-02 18:53:08'),(15,'3','3',1,2,'2025-02-02 18:53:50'),(16,'ㅇㄴㅁㅇㄴㅁ','ㅇㄴㅁㅇㄴㅁㅇㅁㄴㅇ',1,2,'2025-02-02 18:58:13');
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `birthday` date NOT NULL,
  `sign_date` date NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `auth_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'김선환','kimsun@gmail.com','1234','010-1234-2345','2000-02-23','2024-12-07','2024-12-07 00:57:19','2025-01-28 23:41:39',2),(2,'이서영','lsyo990806@gmail.com','1234','010-1234-1234','1999-08-06','2024-12-07','2024-12-07 00:57:19','2025-01-28 23:41:39',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `video`
--

DROP TABLE IF EXISTS `video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `url` varchar(1024) NOT NULL,
  `lecture_id` bigint NOT NULL,
  `sort_id` int NOT NULL,
  `title` varchar(255) NOT NULL,
  `duration` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `video_ibfk1` (`lecture_id`),
  CONSTRAINT `video_ibfk1` FOREIGN KEY (`lecture_id`) REFERENCES `lecture` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video`
--

LOCK TABLES `video` WRITE;
/*!40000 ALTER TABLE `video` DISABLE KEYS */;
INSERT INTO `video` VALUES (4,'/savevideo/test1',1,1,'Test Video 1','00:07'),(5,'/savevideo/test4',1,2,'Test Video 2','00:08'),(6,'/savevideo/test2',1,3,'Test Video 3','00:09'),(7,'/savevideo/test13',1,4,'Test Video 4','00:07'),(8,'/static/videos/ab0ba5fc-f353-4898-a335-804b3f381d63_스크린샷 2024-12-08 오후 3.55.13.png',11,1,'강의 영상 2','00:00'),(9,'/static/videos/94e5170d-4e8f-47f7-9715-31329b92b21a_스크린샷 2024-12-08 오후 6.02.01.png',11,2,'강의 영상 3','00:00'),(10,'/static/videos/fe5aceec-92de-4dc3-989b-649dd2d88350_스크린샷 2024-12-09 오후 12.46.58.png',11,3,'강의 영상 4','00:00'),(11,'/static/videos/8ac23fce-a796-4b6b-95c4-e468ee2d3bfc_스크린샷 2024-12-11 오전 9.52.46.png',11,4,'강의 영상 5','00:00'),(12,'/static/videos/f732c8be-793f-4705-b8d3-d48d16b62fae_스크린샷 2024-12-11 오후 4.31.02.png',11,5,'강의 영상 6','00:00'),(13,'/static/videos/57450d44-a345-4993-b6a9-d371a3f39cba_스크린샷 2024-12-14 오전 2.39.47.png',11,6,'강의 영상 7','00:00');
/*!40000 ALTER TABLE `video` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-12  4:36:00
