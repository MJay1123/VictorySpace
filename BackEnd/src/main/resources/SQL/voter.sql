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
VALUES
#     (1, 1, 3, 'home'),
#        (2, 1, 17, 'away'),
#        (3, 1, 22, 'neutral'),
#
#        (4, 2, 5, 'home'),
#        (5, 2, 11, 'home'),
#        (6, 2, 29, 'away'),
#
#        (7, 3, 8, 'neutral'),
#        (8, 3, 14, 'away'),
#        (9, 3, 35, 'home'),
#
#        (10, 4, 2, 'home'),
#        (11, 4, 18, 'neutral'),
#        (12, 4, 27, 'away'),
#
#        (13, 5, 6, 'home'),
#        (14, 5, 21, 'home'),
#        (15, 5, 33, 'neutral'),

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

INSERT INTO voter (member_id, vote_id, content) VALUES
-- vote 1
(1,1,'home'), (2,1,'away'), (3,1,'home'), (4,1,'away'), (5,1,'home'),
(6,1,'away'), (7,1,'home'), (8,1,'away'), (9,1,'home'), (10,1,'away'),
(11,1,'home'), (12,1,'away'), (13,1,'home'), (14,1,'away'), (15,1,'home'),
(16,1,'away'), (17,1,'home'), (18,1,'away'), (19,1,'home'), (20,1,'away'),
(21,1,'home'), (22,1,'away'), (23,1,'home'), (24,1,'away'), (25,1,'home'),
(26,1,'away'), (27,1,'home'), (28,1,'away'), (29,1,'home'), (30,1,'away'),

-- vote 2
(11,2,'away'), (12,2,'home'), (13,2,'away'), (14,2,'home'), (15,2,'away'),
(16,2,'home'), (17,2,'away'), (18,2,'home'), (19,2,'away'), (20,2,'home'),
(21,2,'away'), (22,2,'home'), (23,2,'away'), (24,2,'home'), (25,2,'away'),
(26,2,'home'), (27,2,'away'), (28,2,'home'), (29,2,'away'), (30,2,'home'),
(31,2,'away'), (32,2,'home'), (33,2,'away'), (34,2,'home'), (35,2,'away'),
(36,2,'home'), (37,2,'away'), (38,2,'home'), (39,2,'away'), (40,2,'home'),

-- vote 3
(5,3,'home'), (6,3,'home'), (7,3,'away'), (8,3,'away'), (9,3,'home'),
(10,3,'away'), (11,3,'home'), (12,3,'away'), (13,3,'home'), (14,3,'away'),
(15,3,'home'), (16,3,'away'), (17,3,'home'), (18,3,'away'), (19,3,'home'),
(20,3,'away'), (21,3,'home'), (22,3,'away'), (23,3,'home'), (24,3,'away'),
(25,3,'home'), (26,3,'away'), (27,3,'home'), (28,3,'away'), (29,3,'home'),
(30,3,'away'), (31,3,'home'), (32,3,'away'), (33,3,'home'), (34,3,'away'),

-- vote 4
(1,4,'away'), (3,4,'home'), (5,4,'away'), (7,4,'home'), (9,4,'away'),
(11,4,'home'), (13,4,'away'), (15,4,'home'), (17,4,'away'), (19,4,'home'),
(21,4,'away'), (23,4,'home'), (25,4,'away'), (27,4,'home'), (29,4,'away'),
(31,4,'home'), (33,4,'away'), (35,4,'home'), (37,4,'away'), (39,4,'home'),
(2,4,'home'), (4,4,'away'), (6,4,'home'), (8,4,'away'), (10,4,'home'),
(12,4,'away'), (14,4,'home'), (16,4,'away'), (18,4,'home'), (20,4,'away'),

-- vote 5
(40,5,'home'), (39,5,'away'), (38,5,'home'), (37,5,'away'), (36,5,'home'),
(35,5,'away'), (34,5,'home'), (33,5,'away'), (32,5,'home'), (31,5,'away'),
(30,5,'home'), (29,5,'away'), (28,5,'home'), (27,5,'away'), (26,5,'home'),
(25,5,'away'), (24,5,'home'), (23,5,'away'), (22,5,'home'), (21,5,'away'),
(20,5,'home'), (19,5,'away'), (18,5,'home'), (17,5,'away'), (16,5,'home'),
(15,5,'away'), (14,5,'home'), (13,5,'away'), (12,5,'home'), (11,5,'away');

