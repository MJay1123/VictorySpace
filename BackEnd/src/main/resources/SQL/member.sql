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

INSERT INTO member
(id, name, gender, birthday, nickname, password, email, grade_id, profile, point, role)
VALUES
    (1, '김하늘', 'F', '1996-03-14', '하늘조각', 'pw1!', 'sky1@test.com', 1, NULL, 1200, 'ROLE_USER'),
    (2, '이민수', 'M', '1993-11-02', '민수는민수', 'pw2@', 'minsoo@test.com', 2, 'profile2.png', 800, 'ROLE_USER'),
    (3, '박지은', 'F', '2000-07-21', '지은감성', 'pw3#', 'jieun@test.com', 3, NULL, 1500, 'ROLE_USER'),
    (4, '최준혁', 'M', '1991-01-09', '불꽃준혁', 'pw4$', 'junhyuk@test.com', 4, 'profile4.jpg', 3000, 'ROLE_USER'),
    (5, '정수빈', 'F', '1998-05-30', '수빈로그', 'pw5%', 'subin@test.com', 5, NULL, 950, 'ROLE_USER'),

    (6, '한도윤', 'M', '1995-08-18', '도윤이즘', 'pw6^', 'doyoon@test.com', 1, 'profile6.png', 1800, 'ROLE_USER'),
    (7, '오세린', 'F', '2001-12-11', '세린무드', 'pw7&', 'serin@test.com', 2, NULL, 500, 'ROLE_USER'),
    (8, '윤태호', 'M', '1994-04-04', '태호빌런', 'pw8*', 'taeho@test.com', 3, 'profile8.jpg', 2200, 'ROLE_USER'),
    (9, '임가은', 'F', '1999-09-09', '가은은행', 'pw9(', 'gaeun@test.com', 4, NULL, 1000, 'ROLE_USER'),
    (10, '신우진', 'M', '1989-06-26', '우진공장', 'pw10)', 'woojin@test.com', 5, 'profile10.png', 4000, 'ROLE_USER'),

    (11, '백서윤', 'F', '1997-02-17', '서윤픽', 'pw11!', 'seoyoon@test.com', 1, NULL, 700, 'ROLE_USER'),
    (12, '강현우', 'M', '1992-10-08', '현우의방', 'pw12@', 'hyunwoo@test.com', 2, NULL, 1300, 'ROLE_USER'),
    (13, '문채린', 'F', '2002-01-23', '채린라이트', 'pw13#', 'chaerin@test.com', 3, 'profile13.jpg', 600, 'ROLE_USER'),
    (14, '조성민', 'M', '1990-12-31', '성민주의', 'pw14$', 'sungmin@test.com', 4, NULL, 2100, 'ROLE_USER'),
    (15, '노아린', 'F', '1995-03-03', '아린별', 'pw15%', 'arin@test.com', 5, 'profile15.png', 1700, 'ROLE_USER'),

    (16, '유동현', 'M', '1998-07-07', '동현시대', 'pw16^', 'donghyun@test.com', 1, NULL, 900, 'ROLE_USER'),
    (17, '홍예린', 'F', '2000-11-19', '예린톤', 'pw17&', 'yerin@test.com', 2, 'profile17.jpg', 1400, 'ROLE_USER'),
    (18, '장시우', 'M', '1996-04-25', '시우노트', 'pw18*', 'siwoo@test.com', 3, NULL, 1100, 'ROLE_USER'),
    (19, '서다혜', 'F', '1994-08-01', '다혜주의보', 'pw19(', 'dahye@test.com', 4, 'profile19.png', 2500, 'ROLE_USER'),
    (20, '배진호', 'M', '1988-09-15', '진호랜드', 'pw20)', 'jinho@test.com', 5, NULL, 5000, 'ROLE_USER');

INSERT INTO member
(id, name, gender, birthday, nickname, password, email, grade_id, profile, point, role)
VALUES
    (21, '김도현', 'M', '1997-01-05', 'dohyun킴', 'pw21!', 'dohyun21@test.com', 1, NULL, 1100, 'ROLE_USER'),
    (22, '이서연', 'F', '2001-06-14', 'seoyeonDay', 'pw22@', 'seoyeon22@test.com', 2, 'profile22.png', 900, 'ROLE_USER'),
    (23, '박준영', 'M', '1994-09-27', 'JunZero', 'pw23#', 'junyoung23@test.com', 3, NULL, 1600, 'ROLE_USER'),
    (24, '최유진', 'F', '1999-12-03', 'yujin.log', 'pw24$', 'yujin24@test.com', 4, 'profile24.jpg', 700, 'ROLE_USER'),
    (25, '정태성', 'M', '1992-04-18', 'TSFactory', 'pw25%', 'taesung25@test.com', 5, NULL, 2800, 'ROLE_USER'),

    (26, '문하린', 'F', '2000-02-22', 'harinWave', 'pw26^', 'harin26@test.com', 1, 'profile26.png', 1300, 'ROLE_USER'),
    (27, '윤성호', 'M', '1996-10-10', 'SungHoX', 'pw27&', 'sungho27@test.com', 2, NULL, 1000, 'ROLE_USER'),
    (28, '장예원', 'F', '1998-08-08', 'yewon.dev', 'pw28*', 'yewon28@test.com', 3, 'profile28.jpg', 1900, 'ROLE_USER'),
    (29, '백승민', 'M', '1993-05-01', 'SeungMinLab', 'pw29(', 'seungmin29@test.com', 4, NULL, 2400, 'ROLE_USER'),
    (30, '한지우', 'F', '2002-11-30', 'jiwooMood', 'pw30)', 'jiwoo30@test.com', 5, 'profile30.png', 600, 'ROLE_USER'),

    (31, '임정훈', 'M', '1991-07-19', 'JHcore', 'pw31!', 'junghoon31@test.com', 1, NULL, 3200, 'ROLE_USER'),
    (32, '서유나', 'F', '1997-03-25', 'yunaPick', 'pw32@', 'yuna32@test.com', 2, 'profile32.jpg', 1400, 'ROLE_USER'),
    (33, '고민재', 'M', '1995-09-09', 'MinjaeRun', 'pw33#', 'minjae33@test.com', 3, NULL, 1700, 'ROLE_USER'),
    (34, '신다은', 'F', '1994-01-16', 'daeunNote', 'pw34$', 'daeun34@test.com', 4, 'profile34.png', 2100, 'ROLE_USER'),
    (35, '배현수', 'M', '1990-06-06', 'HS_Archive', 'pw35%', 'hyunsu35@test.com', 5, NULL, 3600, 'ROLE_USER'),

    (36, '오나래', 'F', '1999-10-02', 'naraeFlow', 'pw36^', 'narae36@test.com', 1, 'profile36.jpg', 800, 'ROLE_USER'),
    (37, '강민철', 'M', '1996-12-24', 'MCsquare', 'pw37&', 'minchul37@test.com', 2, NULL, 1500, 'ROLE_USER'),
    (38, '조은비', 'F', '2001-04-11', 'eunbi.zip', 'pw38*', 'eunbi38@test.com', 3, 'profile38.png', 950, 'ROLE_USER'),
    (39, '유승환', 'M', '1993-02-28', 'SeungHwanX', 'pw39(', 'seunghwan39@test.com', 4, NULL, 2700, 'ROLE_USER'),
    (40, '남소희', 'F', '1998-07-13', 'soheeVerse', 'pw40)', 'sohee40@test.com', 5, 'profile40.jpg', 1800, 'ROLE_USER');
