SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE if exists voter;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE voter (
      id INT AUTO_INCREMENT PRIMARY KEY,
      vote_id INT NOT NULL,
      member_id INT NOT NULL,
      content VARCHAR(255) NOT NULL,
      CONSTRAINT fk_voter_vote
          FOREIGN KEY (vote_id) REFERENCES vote(id),
      CONSTRAINT fk_voter_member
          FOREIGN KEY (member_id) REFERENCES member(id)
);

ALTER TABLE voter ADD UNIQUE (vote_id, member_id);

ALTER TABLE vote CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

INSERT INTO voter (id, vote_id, member_id, content)
VALUES (1, 1, 3, 'home'),
       (2, 1, 17, 'away'),
       (3, 1, 22, 'neutral'),

       (4, 2, 5, 'home'),
       (5, 2, 11, 'home'),
       (6, 2, 29, 'away'),

       (7, 3, 8, 'neutral'),
       (8, 3, 14, 'away'),
       (9, 3, 35, 'home'),

       (10, 4, 2, 'home'),
       (11, 4, 18, 'neutral'),
       (12, 4, 27, 'away'),

       (13, 5, 6, 'home'),
       (14, 5, 21, 'home'),
       (15, 5, 33, 'neutral'),

       (16, 6, 4, 'away'),
       (17, 6, 19, 'home'),
       (18, 6, 40, 'neutral'),

       (19, 7, 9, 'home'),
       (20, 7, 12, 'away'),
       (21, 7, 26, 'neutral'),

       (22, 8, 7, 'neutral'),
       (23, 8, 16, 'home'),
       (24, 8, 31, 'away'),

       (25, 9, 10, 'home'),
       (26, 9, 23, 'away'),
       (27, 9, 37, 'neutral'),

       (28, 10, 1, 'away'),
       (29, 10, 15, 'home'),
       (30, 10, 34, 'neutral'),

       (31, 11, 13, 'home'),
       (32, 11, 24, 'away'),
       (33, 11, 39, 'neutral'),

       (34, 12, 20, 'home'),
       (35, 12, 28, 'away'),
       (36, 12, 32, 'neutral'),

       (37, 13, 6, 'home'),
       (38, 13, 18, 'away'),
       (39, 13, 25, 'neutral'),

       (40, 14, 4, 'neutral'),
       (41, 14, 30, 'home'),
       (42, 14, 36, 'away'),

       (43, 15, 2, 'home'),
       (44, 15, 17, 'away'),
       (45, 15, 38, 'neutral'),

       (46, 16, 8, 'home'),
       (47, 16, 21, 'neutral'),
       (48, 16, 40, 'away'),

       (49, 17, 5, 'away'),
       (50, 17, 14, 'home'),
       (51, 17, 33, 'neutral'),

       (52, 18, 7, 'home'),
       (53, 18, 19, 'neutral'),
       (54, 18, 27, 'away'),

       (55, 19, 3, 'home'),
       (56, 19, 22, 'away'),
       (57, 19, 31, 'neutral'),

       (58, 20, 9, 'away'),
       (59, 20, 28, 'home'),
       (60, 20, 35, 'neutral');
