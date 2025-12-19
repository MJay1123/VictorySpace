SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE if exists comment;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE comment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    vote_id INT,
    member_id INT,
    content VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME DEFAULT NULL,
    CONSTRAINT fk_comment_vote
        FOREIGN KEY (vote_id) REFERENCES vote(id),
    CONSTRAINT fk_comment_member
        FOREIGN KEY (member_id) REFERENCES member(id)
);

ALTER TABLE comment CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

INSERT INTO comment (id, vote_id, member_id, content, created_at, updated_at, deleted_at) VALUES
  (1, 1, 1, '첫 번째 투표 재밌네요!', NOW(), NOW(), NULL),
  (2, 1, 2, '저도 참여했습니다 ㅎㅎ', NOW(), NOW(), NULL),
  (3, 2, 3, '이건 좀 의견이 갈리겠네요.', NOW(), NOW(), NULL),
  (4, 2, 1, '저는 반대합니다!', NOW(), NOW(), NULL),
  (5, 3, 4, '좋은 주제 감사합니다.', NOW(), NOW(), NULL),
  (6, 3, 2, '다른 분들 의견 궁금해요.', NOW(), NOW(), NULL),
  (7, 4, 5, '이건 무조건 찬성입니다.', NOW(), NOW(), NULL),
  (8, 4, 3, '저는 고민 좀 해봐야겠네요.', NOW(), NOW(), NULL),
  (9, 5, 1, '의견이 많네요 ㅋㅋ', NOW(), NOW(), NULL),
  (10, 5, 4, '투표 결과 기대됩니다!', NOW(), NOW(), NULL);
