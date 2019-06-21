CREATE DATABASE  IF NOT EXISTS `proyecto_final` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `proyecto_final`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: proyecto_final
-- ------------------------------------------------------
-- Server version	5.7.19-log

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
-- Table structure for table `alumno`
--

DROP TABLE IF EXISTS `alumno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alumno` (
  `nombre_login` varchar(25) NOT NULL,
  `usuario_git` varchar(39) DEFAULT NULL,
  PRIMARY KEY (`nombre_login`),
  CONSTRAINT `fk_usuario_alumno` FOREIGN KEY (`nombre_login`) REFERENCES `usuario` (`nombre_login`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `alumno_asignacion`
--

DROP TABLE IF EXISTS `alumno_asignacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alumno_asignacion` (
  `cod_alumno` varchar(25) NOT NULL,
  `cod_asignacion` int(11) NOT NULL,
  `ruta_archivo` varchar(100) DEFAULT NULL,
  `comentario` varchar(255) DEFAULT NULL,
  `comentario_profesor` varchar(255) DEFAULT NULL,
  `nota_autoevaluacion` int(2) DEFAULT NULL,
  `nota_evaluacion` int(2) DEFAULT NULL,
  PRIMARY KEY (`cod_alumno`,`cod_asignacion`),
  KEY `fk_asignacion_alumno_idx` (`cod_asignacion`),
  CONSTRAINT `fk_alumno_asignacion` FOREIGN KEY (`cod_alumno`) REFERENCES `alumno` (`nombre_login`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_asignacion_alumno` FOREIGN KEY (`cod_asignacion`) REFERENCES `asignacion` (`cod_asignacion`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `alumno_asignatura`
--

DROP TABLE IF EXISTS `alumno_asignatura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alumno_asignatura` (
  `cod_alumno` varchar(25) NOT NULL,
  `cod_asignatura` varchar(4) NOT NULL,
  PRIMARY KEY (`cod_alumno`,`cod_asignatura`),
  KEY `fk_asignatura_alumno_idx` (`cod_asignatura`),
  CONSTRAINT `fk_alumno_asignatura` FOREIGN KEY (`cod_alumno`) REFERENCES `alumno` (`nombre_login`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_asignatura_alumno` FOREIGN KEY (`cod_asignatura`) REFERENCES `asignatura` (`cod_asig`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `alumno_criterio`
--

DROP TABLE IF EXISTS `alumno_criterio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alumno_criterio` (
  `cod_alumno` varchar(25) NOT NULL,
  `cod_asignacion` int(11) NOT NULL,
  `cod_criterio` int(11) NOT NULL,
  `nota_evaluacion` int(2) DEFAULT NULL,
  `nota_autoevaluacion` int(2) DEFAULT NULL,
  PRIMARY KEY (`cod_alumno`,`cod_asignacion`,`cod_criterio`),
  KEY `fk_criterio_alumno_idx` (`cod_asignacion`,`cod_criterio`),
  KEY `fk_criterioalumno_alumno_idx` (`cod_asignacion`,`cod_criterio`),
  CONSTRAINT `fk_alumno_criterio` FOREIGN KEY (`cod_alumno`) REFERENCES `alumno` (`nombre_login`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_criterioalumno_alumno` FOREIGN KEY (`cod_asignacion`, `cod_criterio`) REFERENCES `criterio_evaluacion` (`cod_asignacion`, `cod_criterio`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asignacion`
--

DROP TABLE IF EXISTS `asignacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asignacion` (
  `cod_asignacion` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(40) DEFAULT NULL,
  `nombre_git` varchar(45) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_entrega` datetime DEFAULT NULL,
  `fecha_creacion` date DEFAULT NULL,
  PRIMARY KEY (`cod_asignacion`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asignacion_tema`
--

DROP TABLE IF EXISTS `asignacion_tema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asignacion_tema` (
  `cod_asignatura` varchar(4) NOT NULL,
  `cod_temario` varchar(11) NOT NULL,
  `cod_asignacion` int(11) NOT NULL,
  PRIMARY KEY (`cod_temario`,`cod_asignatura`,`cod_asignacion`),
  KEY `fk_asignacion_idx` (`cod_asignacion`),
  CONSTRAINT `fk_asignacion` FOREIGN KEY (`cod_asignacion`) REFERENCES `asignacion` (`cod_asignacion`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_tema` FOREIGN KEY (`cod_temario`, `cod_asignatura`) REFERENCES `tema` (`cod_temario`, `cod_asignatura`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asignatura`
--

DROP TABLE IF EXISTS `asignatura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asignatura` (
  `cod_asig` varchar(5) NOT NULL,
  `nombre` varchar(65) DEFAULT NULL,
  `cod_prof` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`cod_asig`),
  KEY `fk_impartir_asignatura_idx` (`cod_prof`),
  CONSTRAINT `fk_impartir_asignatura` FOREIGN KEY (`cod_prof`) REFERENCES `profesor` (`nombre_login`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `criterio_evaluacion`
--

DROP TABLE IF EXISTS `criterio_evaluacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `criterio_evaluacion` (
  `cod_asignacion` int(11) NOT NULL,
  `cod_criterio` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(40) DEFAULT NULL,
  `porcentaje` int(3) DEFAULT NULL,
  PRIMARY KEY (`cod_criterio`,`cod_asignacion`),
  KEY `fk_criterio_asignacion_idx` (`cod_asignacion`),
  CONSTRAINT `fk_criterio_asignacion` FOREIGN KEY (`cod_asignacion`) REFERENCES `asignacion` (`cod_asignacion`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `enlace`
--

DROP TABLE IF EXISTS `enlace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enlace` (
  `cod_asignatura` varchar(4) NOT NULL,
  `cod_temario` varchar(11) NOT NULL,
  `cod_enlace` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(100) DEFAULT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`cod_enlace`,`cod_asignatura`,`cod_temario`),
  KEY `fk_enlace_tema_idx` (`cod_temario`,`cod_asignatura`),
  CONSTRAINT `fk_enlace_tema` FOREIGN KEY (`cod_temario`, `cod_asignatura`) REFERENCES `tema` (`cod_temario`, `cod_asignatura`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `profesor`
--

DROP TABLE IF EXISTS `profesor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profesor` (
  `nombre_login` varchar(25) NOT NULL,
  PRIMARY KEY (`nombre_login`),
  CONSTRAINT `fk_usuario_profesor` FOREIGN KEY (`nombre_login`) REFERENCES `usuario` (`nombre_login`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `profesor_asignatura`
--

DROP TABLE IF EXISTS `profesor_asignatura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profesor_asignatura` (
  `nombre_login` varchar(25) NOT NULL,
  `cod_asig` varchar(4) NOT NULL,
  PRIMARY KEY (`nombre_login`,`cod_asig`),
  KEY `fk_asignatura_observada` (`cod_asig`),
  CONSTRAINT `fk_asignatura_observada` FOREIGN KEY (`cod_asig`) REFERENCES `asignatura` (`cod_asig`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_observa_asignatura` FOREIGN KEY (`nombre_login`) REFERENCES `profesor` (`nombre_login`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tema`
--

DROP TABLE IF EXISTS `tema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tema` (
  `cod_asignatura` varchar(4) NOT NULL,
  `cod_temario` varchar(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `descripcion` varchar(400) DEFAULT NULL,
  `fecha_comienzo` date DEFAULT NULL,
  PRIMARY KEY (`cod_temario`,`cod_asignatura`),
  KEY `fk_asignatura_tema_idx` (`cod_asignatura`),
  CONSTRAINT `fk_ tema_ asignatura` FOREIGN KEY (`cod_asignatura`) REFERENCES `asignatura` (`cod_asig`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `nombre_login` varchar(25) NOT NULL,
  `clave` varchar(25) DEFAULT NULL,
  `nombre` varchar(20) DEFAULT NULL,
  `apellidos` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`nombre_login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-21 17:25:04
