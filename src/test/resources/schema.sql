DROP TABLE IF EXISTS `myApp_Users`;

CREATE TABLE IF NOT EXISTS `myApp_Users` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `userName` varchar(255) DEFAULT NULL,
    `firstName` varchar(255) DEFAULT NULL,
    `lastName` varchar(255) DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `phone` varchar(255) DEFAULT NULL,
    `imageUrl` varchar(255) DEFAULT NULL,
    `createdAt` TIMESTAMP NOT NULL,
    `updatedAt` TIMESTAMP NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `myApp_UserSkills` (
    `skill_id` int(11) NOT NULL AUTO_INCREMENT,
    `user_id` int(11) DEFAULT NULL,
    `skillName` varchar(255) DEFAULT NULL,
    `skillLevel` int(11) DEFAULT NULL,
    `skillDetail` varchar(255) DEFAULT NULL,
    `createdAt` TIMESTAMP NOT NULL,
    `updatedAt` TIMESTAMP NOT NULL,
    PRIMARY KEY (`skill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
