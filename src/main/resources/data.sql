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

