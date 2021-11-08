CREATE TABLE `coach` (
  `coach_id` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `lastname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`coach_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `keyword` (
  `label` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`label`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `dish` (
  `dish_id` int NOT NULL AUTO_INCREMENT,
  `label` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `recipe` varchar(10000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `link` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`dish_id`),
  UNIQUE KEY `label_UNIQUE` (`label`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ingredient` (
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `calories` double NOT NULL,
  `proteins` double NOT NULL,
  `carbohydrates` double NOT NULL,
  `lipids` double NOT NULL,
  PRIMARY KEY (`name`),
  CONSTRAINT `calories` CHECK ((`calories` >= 0)),
  CONSTRAINT `carbohydrates` CHECK ((`carbohydrates` >= 0)),
  CONSTRAINT `lipids` CHECK ((`lipids` >= 0)),
  CONSTRAINT `proteins` CHECK ((`proteins` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `person` (
  `pseudo` varchar(50)CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `lastname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `firstname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `nationality` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `birthday` date NOT NULL,
  `gender` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `height` int NOT NULL,
  `weight` double(5,2) NOT NULL,
  `is_high_level_athlete` tinyint NOT NULL,
  `coach_id` int DEFAULT NULL,
  PRIMARY KEY (`pseudo`),
  KEY `coach_id_idx` (`coach_id`),
  CONSTRAINT `coach_id` FOREIGN KEY (`coach_id`) REFERENCES `coach` (`coach_id`),
  CONSTRAINT `height` CHECK ((`height` between 100 and 250)),
  CONSTRAINT `weight` CHECK ((`weight` between 30 and 170))
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `nutrition` (
  `person_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `label` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`person_id`,`label`),
  KEY `label_idx` (`label`),
  CONSTRAINT `label_nutition` FOREIGN KEY (`label`) REFERENCES `keyword` (`label`),
  CONSTRAINT `person_id_nutrition` FOREIGN KEY (`person_id`) REFERENCES `person` (`pseudo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `information` (
  `dish_id` int NOT NULL,
  `keyword` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`dish_id`,`keyword`),
  KEY `keayword_information_idx` (`keyword`),
  CONSTRAINT `dish_id_information` FOREIGN KEY (`dish_id`) REFERENCES `dish` (`dish_id`),
  CONSTRAINT `keayword_information` FOREIGN KEY (`keyword`) REFERENCES `keyword` (`label`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `quantity` (
  `volume` double NOT NULL,
  `unit` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dish_id` int NOT NULL,
  `ingredient` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`volume`,`unit`,`dish_id`,`ingredient`),
  KEY `dish_id_idx` (`dish_id`),
  KEY `ingregient_quantity_idx` (`ingredient`),
  CONSTRAINT `dish_id_quantity` FOREIGN KEY (`dish_id`) REFERENCES `dish` (`dish_id`),
  CONSTRAINT `ingregient_quantity` FOREIGN KEY (`ingredient`) REFERENCES `ingredient` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `menu` (
  `code` int NOT NULL AUTO_INCREMENT,
  `moment` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `date` date NOT NULL,
  `person_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`code`),
  KEY `person_id_menu_idx` (`person_id`),
  CONSTRAINT `person_id_menu` FOREIGN KEY (`person_id`) REFERENCES `person` (`pseudo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `composition` (
  `dish_code` int NOT NULL,
  `code_menu` int NOT NULL,
  PRIMARY KEY (`dish_code`,`code_menu`),
  CONSTRAINT `code_menu` FOREIGN KEY (`code_menu`) REFERENCES `menu` (`code`),
  CONSTRAINT `dish_code` FOREIGN KEY (`dish_code`) REFERENCES `dish` (`dish_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;









