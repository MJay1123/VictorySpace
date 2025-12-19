SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE if exists vote;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE vote (
      id INT AUTO_INCREMENT PRIMARY KEY,
      title VARCHAR(255) NOT NULL,
    category_id INT NOT NULL,
      member_id INT NOT NULL,
      content VARCHAR(255) NOT NULL,
      challenger_id INT NULL,
      challenger_content VARCHAR(255) NULL,
      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
      updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
      deleted_at DATETIME NULL,
    `duration` VARCHAR(20) NOT NULL,
    `ended_at` DATETIME NULL,
    CONSTRAINT fk_vote_category
                  FOREIGN KEY (category_id) REFERENCES category(id),
      CONSTRAINT fk_vote_member
          FOREIGN KEY (member_id) REFERENCES member(id),
      CONSTRAINT fk_vote_challenger
          FOREIGN KEY (challenger_id) REFERENCES member(id)
);

ALTER TABLE vote CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

INSERT INTO vote (title, category_id, member_id, content, challenger_id, challenger_content, duration) VALUES
    ('고양이가 더 귀엽다 vs 강아지가 더 귀엽다', 3,1, '고양이가 진리다!', 2, '강아지가 최고지!', '3h'),
    ('아침은 밥 vs 빵', 2,2, '밥 없으면 하루 못 버팀', 3, '커피와 빵이 국룰', '2d'),
    ('원피스 vs 나루토', 1,3, '원피스가 끝판왕', 4, '나루토의 감성 모름?', '5d'),
    ('야식은 치킨 vs 라면',2, 4, '치느님 영접해야지', NULL, NULL, '5h'),
    ('여름 vs 겨울',7, 5, '여름이 진짜 행복임', 1, '겨울 감성 몰라?', '5h'),
    ('알콜음료는 맥주 vs 소주',2, 1, '맥주는 분위기다', 5, '소주가 진짜다', '3h'),
    ('게임은 PC vs 콘솔', 7,3, 'PC 마스터 레이스', 2, '콘솔의 편안함 모름?', '4d'),
    ('카페에서는 아메리카노 vs 라떼', 2,4, '아메리카노가 국룰', NULL, NULL, '5d'),
    ('운동은 헬스 vs 러닝', 1,2, '헬스가 더 효율적임', 3, '러닝이 진짜 건강 챙기지', '4d'),
    ('피자는 파인애플 넣는다 vs 안 넣는다', 2,5, '파인애플 파치미아 갑니다', 1, '넣는 사람 이해 불가', '5d'),
    ('커피 vs 차', 2, 1, '커피 없으면 못 살아', 2, '차가 더 건강함', '3d'),
    ('아이폰 vs 갤럭시', 7, 2, '아이폰 감성', 3, '갤럭시 기능', '5d'),
    ('치킨 vs 피자', 2, 3, '치킨은 진리', 4, '피자는 완성형', '1d'),
    ('야구 vs 축구', 1, 4, '야구는 기록의 스포츠', 5, '축구가 더 박진감', '7d'),
    ('혼밥 vs 같이 먹기', 7, 5, '혼밥이 편함', 1, '같이 먹어야 맛있지', '2d'),
    ('출근 vs 재택', 3, 2, '출근이 루틴 유지됨', 3, '재택이 최고', '5d'),
    ('겨울 여행 vs 여름 여행', 7, 4, '겨울 감성', 5, '여름 바다', '4d'),
    ('고기 vs 해산물', 2, 1, '고기가 최고', 2, '해산물이 깔끔', '3d'),
    ('영화관 vs 집에서', 1, 3, '영화관이 몰입됨', 4, '집이 편함', '2d'),
    ('연애 vs 솔로', 7, 5, '연애가 삶의 활력', 1, '솔로가 자유롭다', '6d');