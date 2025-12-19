SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE if exists member;
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
        point INT NOT NULL DEFAULT 1000,
        role VARCHAR(50) NOT NULL
);

ALTER TABLE member CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

INSERT INTO member (name, gender, birthday, nickname, password, email, grade_id, role) VALUES
       ('김민준','M','1980-01-10','user1','pass','user1@test.com',1,'ROLE_USER'),
       ('이수영','F','1981-02-15','user2','pass','user2@test.com',2,'ROLE_USER'),
       ('박지수','F','1982-03-20','user3','pass','user3@test.com',3,'ROLE_USER'),
       ('최현우','M','1983-04-25','user4','pass','user4@test.com',4,'ROLE_USER'),
       ('정하은','F','1984-05-30','user5','pass','user5@test.com',5,'ROLE_USER'),

       ('테스트6','M','1985-06-10','user6','pass','user6@test.com',1,'ROLE_USER'),
       ('테스트7','F','1986-07-15','user7','pass','user7@test.com',2,'ROLE_USER'),
       ('테스트8','M','1987-08-20','user8','pass','user8@test.com',3,'ROLE_USER'),
       ('테스트9','F','1988-09-25','user9','pass','user9@test.com',4,'ROLE_USER'),
       ('테스트10','M','1989-10-30','user10','pass','user10@test.com',5,'ROLE_USER'),

       ('테스트11','F','1990-01-05','user11','pass','user11@test.com',1,'ROLE_USER'),
       ('테스트12','M','1991-02-10','user12','pass','user12@test.com',2,'ROLE_USER'),
       ('테스트13','F','1992-03-15','user13','pass','user13@test.com',3,'ROLE_USER'),
       ('테스트14','M','1993-04-20','user14','pass','user14@test.com',4,'ROLE_USER'),
       ('테스트15','F','1994-05-25','user15','pass','user15@test.com',5,'ROLE_USER'),

       ('테스트16','M','1995-06-30','user16','pass','user16@test.com',1,'ROLE_USER'),
       ('테스트17','F','1996-07-05','user17','pass','user17@test.com',2,'ROLE_USER'),
       ('테스트18','M','1997-08-10','user18','pass','user18@test.com',3,'ROLE_USER'),
       ('테스트19','F','1998-09-15','user19','pass','user19@test.com',4,'ROLE_USER'),
       ('테스트20','M','1999-10-20','user20','pass','user20@test.com',5,'ROLE_USER'),

       ('테스트21','F','2000-01-25','user21','pass','user21@test.com',1,'ROLE_USER'),
       ('테스트22','M','2000-02-28','user22','pass','user22@test.com',2,'ROLE_USER'),
       ('테스트23','F','2001-03-05','user23','pass','user23@test.com',3,'ROLE_USER'),
       ('테스트24','M','2001-04-10','user24','pass','user24@test.com',4,'ROLE_USER'),
       ('테스트25','F','2002-05-15','user25','pass','user25@test.com',5,'ROLE_USER'),

       ('테스트26','M','2002-06-20','user26','pass','user26@test.com',1,'ROLE_USER'),
       ('테스트27','F','2003-07-25','user27','pass','user27@test.com',2,'ROLE_USER'),
       ('테스트28','M','2003-08-30','user28','pass','user28@test.com',3,'ROLE_USER'),
       ('테스트29','F','2004-09-05','user29','pass','user29@test.com',4,'ROLE_USER'),
       ('테스트30','M','2004-10-10','user30','pass','user30@test.com',5,'ROLE_USER'),

       ('테스트31','F','2005-01-15','user31','pass','user31@test.com',1,'ROLE_USER'),
       ('테스트32','M','2005-02-20','user32','pass','user32@test.com',2,'ROLE_USER'),
       ('테스트33','F','1980-03-25','user33','pass','user33@test.com',3,'ROLE_USER'),
       ('테스트34','M','1981-04-30','user34','pass','user34@test.com',4,'ROLE_USER'),
       ('테스트35','F','1982-05-05','user35','pass','user35@test.com',5,'ROLE_USER'),

       ('테스트36','M','1983-06-10','user36','pass','user36@test.com',1,'ROLE_USER'),
       ('테스트37','F','1984-07-15','user37','pass','user37@test.com',2,'ROLE_USER'),
       ('테스트38','M','1985-08-20','user38','pass','user38@test.com',3,'ROLE_USER'),
       ('테스트39','F','1986-09-25','user39','pass','user39@test.com',4,'ROLE_USER'),
       ('테스트40','M','1987-10-30','user40','pass','user40@test.com',5,'ROLE_USER'),

       ('테스트41','F','1988-01-05','user41','pass','user41@test.com',1,'ROLE_USER'),
       ('테스트42','M','1989-02-10','user42','pass','user42@test.com',2,'ROLE_USER'),
       ('테스트43','F','1990-03-15','user43','pass','user43@test.com',3,'ROLE_USER'),
       ('테스트44','M','1991-04-20','user44','pass','user44@test.com',4,'ROLE_USER'),
       ('테스트45','F','1992-05-25','user45','pass','user45@test.com',5,'ROLE_USER'),

       ('테스트46','M','1993-06-30','user46','pass','user46@test.com',1,'ROLE_USER'),
       ('테스트47','F','1994-07-05','user47','pass','user47@test.com',2,'ROLE_USER'),
       ('테스트48','M','1995-08-10','user48','pass','user48@test.com',3,'ROLE_USER'),
       ('테스트49','F','1996-09-15','user49','pass','user49@test.com',4,'ROLE_USER'),
       ('테스트50','M','1997-10-20','user50','pass','user50@test.com',5,'ROLE_USER');
