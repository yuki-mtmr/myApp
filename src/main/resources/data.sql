USE `myApp_db`;

INSERT INTO `myApp_Users` (`userName`, `firstName`, `lastName`, `email`, `password`, `phone`, `imageUrl`, `createdAt`, `updatedAt`)VALUES
('bbb', 'ccc', 'ddd', 'eee', 'fff', 'ggg', 'hhh', now(), now()),
('ww', 'cee', 'ee', 'rrr', 'frtf', 'ygg', 'iih', now(), now()),
('bbb', 'ccc', 'ddd', 'eee', 'fff', 'ggg', 'wrb', now(), now()),
('bbb', 'ccc', 'ddd', 'eee', 'fff', 'ggg', 'xxxxx', now(), now());

INSERT INTO `myApp_UserSkills` (`skill_id`,`skillName`, `skillLevel`, `skillDetail`, `createdAt`, `updatedAt`)VALUES
('bbb', 'ccc', 'ddd','kkk', now(), now()),
('ww', 'cee', 'ee','yyy', now(), now()),
('bbb', 'ccc', 'ddd','ooo', now(), now()),
('bbb', 'ccc', 'ddd','ppp', now(), now());