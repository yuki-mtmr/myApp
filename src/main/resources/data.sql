USE `myApp_db`;

INSERT INTO `myApp_Users` (`userName`, `firstName`, `lastName`, `email`, `password`, `phone`, `imageUrl`, `createdAt`, `updatedAt`)VALUES
('bbb', 'ccc', 'ddd', 'eee', 'fff', 'ggg', 'hhh', now(), now()),
('ww', 'cee', 'ee', 'rrr', 'frtf', 'ygg', 'iih', now(), now()),
('bbb', 'ccc', 'ddd', 'eee', 'fff', 'ggg', 'wrb', now(), now()),
('bbb', 'ccc', 'ddd', 'eee', 'fff', 'ggg', 'xxxxx', now(), now());

INSERT INTO `myApp_UserSkills` (`user_id`,`skillName`, `skillLevel`, `skillDetail`, `createdAt`, `updatedAt`)VALUES
(1, 'ccc', 88,'kkk', now(), now()),
(1, 'cee', 99,'yyy', now(), now()),
(2, 'ccc', 5,'ooo', now(), now()),
(2, 'ccc', 2,'ppp', now(), now());

INSERT INTO `myApp_UserStats` (`user_id`,`statusName`, `statusVolume`, `createdAt`, `updatedAt`)VALUES
(1, 'experienceTime(year)', '1', now(), now()),
(1, 'webSiteMade', '2', now(), now());

INSERT INTO `myApp_UserWorks` (`user_id`,`workThumbnail`, `workLink`, `workDetail`, `createdAt`, `updatedAt`)VALUES
(1, 'ccc', 'test', 'kkk', now(), now()),
(1, 'cee', 'test','yyy', now(), now()),
(2, 'ccc', 'test','ooo', now(), now()),
(2, 'ccc', 'test','ppp', now(), now());

INSERT INTO `myApp_Portfolios` (`user_id`,`portfolioName`, `portfolioPic`, `introduction`, `createdAt`, `updatedAt`)VALUES
(1, 'ccc', 'test', 'kkk', now(), now()),
(2, 'cee', 'test','yyy', now(), now()),
(1, 'ccc', 'test','ooo', now(), now()),
(2, 'ccc', 'test','ppp', now(), now());
