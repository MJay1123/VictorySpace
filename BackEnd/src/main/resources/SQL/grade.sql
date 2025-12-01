SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE grade;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE grade (
     id INT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(50) NOT NULL,
     symbol VARCHAR(255) NOT NULL
 );

ALTER TABLE member CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

INSERT INTO grade (id, name, symbol) VALUES
     (1, 'Bronze', '🥉'),
     (2, 'Silver', '🥈'),
     (3, 'Gold', '🥇'),
     (4, 'Platinum', '💎'),
     (5, 'Diamond', '✨');