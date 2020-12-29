USE `myApp_db`;

INSERT INTO `myApp_Users` (`userName`, `firstName`, `lastName`, `email`, `password`, `phone`, `imageUrl`, `createdAt`, `updatedAt`)VALUES
('bbb', 'ccc', 'ddd', 'eee', 'fff', 'ggg', 'hhh', '2020-12-06 10:10:59', '2020-12-06 10:10:59'),
('ww', 'cee', 'ee', 'rrr', 'frtf', 'ygg', 'iih', '2020-12-06 10:10:59', '2020-12-06 10:10:59'),
('bbb', 'ccc', 'ddd', 'eee', 'fff', 'ggg', 'wrb', '2020-12-06 10:10:59', '2020-12-06 10:10:59'),
('bbb', 'ccc', 'ddd', 'eee', 'fff', 'ggg', 'xxxxx', '2020-12-06 10:10:59', '2020-12-06 10:10:59');

INSERT INTO `myApp_UserSkills` (`user_id`,`skillName`, `skillLevel`, `skillDetail`, `createdAt`, `updatedAt`)VALUES
(1, 'ccc', 88,'kkk', now(), now()),
(1, 'cee', 99,'yyy', now(), now()),
(2, 'ccc', 5,'ooo', now(), now()),
(2, 'ccc', 2,'ppp', now(), now());
