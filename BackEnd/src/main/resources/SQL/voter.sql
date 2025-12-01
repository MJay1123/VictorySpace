SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE voter;
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

ALTER TABLE vote CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

INSERT INTO voter (vote_id, member_id, content) VALUES
        (1, 1, 'home'),
        (1, 2, 'away'),
        (1, 3, 'neutral'),
        (2, 1, 'away'),
        (2, 2, 'home'),
        (2, 3, 'neutral'),
        (3, 1, 'neutral'),
        (3, 2, 'home'),
        (3, 3, 'away'),
        (1, 1, 'home');