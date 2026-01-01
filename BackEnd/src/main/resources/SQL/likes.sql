SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE if exists `likes`;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `likes` (
      id INT AUTO_INCREMENT PRIMARY KEY,
      member_id INT NOT NULL,
      vote_id INT NOT NULL,
      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
      CONSTRAINT fk_like_member
          FOREIGN KEY (member_id) REFERENCES member(id),
      CONSTRAINT fk_like_vote
          FOREIGN KEY (vote_id) REFERENCES vote(id)
);

ALTER TABLE `likes` ADD UNIQUE (vote_id, member_id);

ALTER TABLE `likes` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

INSERT INTO likes (id, member_id, vote_id) VALUES
   (1, 3, 1),
   (2, 17, 1),
   (3, 25, 1),

   (4, 5, 2),
   (5, 11, 2),
   (6, 29, 2),

   (7, 8, 3),
   (8, 14, 3),
   (9, 35, 3),

   (10, 2, 4),
   (11, 18, 4),
   (12, 27, 4),

   (13, 6, 5),
   (14, 21, 5),
   (15, 33, 5),

   (16, 4, 6),
   (17, 19, 6),
   (18, 40, 6),

   (19, 9, 7),
   (20, 12, 7),
   (21, 26, 7),

   (22, 7, 8),
   (23, 16, 8),
   (24, 31, 8),

   (25, 10, 9),
   (26, 23, 9),
   (27, 37, 9),

   (28, 1, 10),
   (29, 15, 10),
   (30, 34, 10),

   (31, 13, 11),
   (32, 24, 11),
   (33, 39, 11),

   (34, 20, 12),
   (35, 28, 12),
   (36, 32, 12),

   (37, 6, 13),
   (38, 18, 13),
   (39, 25, 13),

   (40, 4, 14),
   (41, 30, 14),
   (42, 36, 14),

   (43, 2, 15),
   (44, 17, 15),
   (45, 38, 15),

   (46, 8, 16),
   (47, 21, 16),
   (48, 40, 16),

   (49, 5, 17),
   (50, 14, 17),
   (51, 33, 17),

   (52, 7, 18),
   (53, 19, 18),
   (54, 27, 18),

   (55, 3, 19),
   (56, 22, 19),
   (57, 31, 19),

   (58, 9, 20),
   (59, 28, 20),
   (60, 35, 20);
