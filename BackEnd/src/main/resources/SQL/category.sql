SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE if exists category;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE category (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(50) NOT NULL
);

ALTER TABLE category CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

INSERT INTO category (name) VALUES
                                ('엔터테인먼트'),
                                ('음식'),
                                ('스포츠'),
                                ('기술'),
                                ('패션'),
                                ('여행'),
                                ('라이프스타일'),
                                ('기타');