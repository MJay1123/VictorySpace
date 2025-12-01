SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE member;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE member (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(50) NOT NULL,
        gender ENUM('M','F') NOT NULL,
        birthday DATE NOT NULL,
        nickname VARCHAR(50) NOT NULL,
        password VARCHAR(255) NOT NULL,
        email VARCHAR(100) NOT NULL UNIQUE,
        created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
        updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        deleted_at DATETIME DEFAULT NULL,
        grade_id INT NOT NULL,
        profile VARCHAR(255) NULL,
        point INT NOT NULL DEFAULT 1000
);

ALTER TABLE member CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

INSERT INTO member (name, gender, birthday, nickname, password, email, grade_id)
VALUES
    ('김민준', 'M', '1980-05-12', 'Min', 'pass1234', 'minjoon@example.com', 1),
    ('이수영', 'F', '1990-04-22', 'Soo', 'mypassword', 'sooyoung@example.com', 2),
    ('박지수', 'F', '2000-01-03', 'Jis', '12345678', 'jisoo@example.com', 3),
    ('최현우', 'M', '2004-05-05', 'Hyun', 'qwerty', 'hyunwoo@example.com', 4),
    ('정하은', 'F', '1990-02-03', 'Hae', 'abcdefg', 'haeun@example.com', 5),
    ('운영자', 'M', '1999-01-01', 'admin', 'admin', 'admin@email.com', 5),
    ('테스트1', 'M', '1990-01-01','test1', 'test1', 'test1@naver.com', 1),
    ('테스트2', 'M', '1990-02-02', 'test2', 'test2', 'test2@naver.com', 2),
     ('테스트3', 'M', '1990-03-03', 'test3', 'test3', 'test3@naver.com', 3),
     ('테스트4', 'M', '1990-04-04', 'test4', 'test4', 'test4@naver.com', 4);