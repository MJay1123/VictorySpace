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

ALTER TABLE `likes` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

INSERT INTO `likes` (member_id, vote_id) VALUES
    (1, 3),
    (2,4),
    (3,1);