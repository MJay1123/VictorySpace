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

INSERT INTO comment (id, vote_id, member_id, content, created_at, updated_at, deleted_at) VALUES
  (11, 6, 7, '이 주제 은근 논쟁거리네요.', NOW(), NOW(), NULL),
  (12, 6, 12, '저는 개인적으로 찬성 쪽이에요.', NOW(), NOW(), NULL),
  (13, 7, 9, '댓글들 읽는 재미가 있네요.', NOW(), NOW(), NULL),
  (14, 7, 18, '생각보다 의견이 갈리네요.', NOW(), NOW(), NULL),
  (15, 8, 21, '다들 왜 이렇게 진지함 ㅋㅋ', NOW(), NOW(), NULL),

  (16, 8, 3, '전 반대 의견입니다.', NOW(), NOW(), NULL),
  (17, 9, 14, '이건 투표 결과가 궁금하네요.', NOW(), NOW(), NULL),
  (18, 9, 25, '저랑 같은 생각인 분 있나요?', NOW(), NOW(), NULL),
  (19, 10, 1, '이런 주제 자주 올라왔으면 좋겠어요.', NOW(), NOW(), NULL),
  (20, 10, 30, '고민하다가 투표했어요.', NOW(), NOW(), NULL),

  (21, 11, 16, '댓글 분위기 좋네요.', NOW(), NOW(), NULL),
  (22, 11, 33, '다른 관점도 이해는 됩니다.', NOW(), NOW(), NULL),
  (23, 12, 5, '저는 좀 애매하네요.', NOW(), NOW(), NULL),
  (24, 12, 19, '이건 상황마다 다를 듯?', NOW(), NOW(), NULL),
  (25, 13, 22, '경험상 이쪽이 맞는 것 같아요.', NOW(), NOW(), NULL),

  (26, 13, 8, '다들 의견 공유 감사요.', NOW(), NOW(), NULL),
  (27, 14, 27, '이런 토론 너무 좋음.', NOW(), NOW(), NULL),
  (28, 14, 4, '생각 정리하는 데 도움됐어요.', NOW(), NOW(), NULL),
  (29, 15, 35, '저랑 생각 비슷한 분 많네요.', NOW(), NOW(), NULL),
  (30, 15, 10, '이건 좀 더 지켜봐야 할 듯.', NOW(), NOW(), NULL),

  (31, 16, 6, '투표 참여 완료!', NOW(), NOW(), NULL),
  (32, 16, 28, '댓글 남기고 갑니다.', NOW(), NOW(), NULL),
  (33, 17, 11, '전혀 다른 의견도 존중합니다.', NOW(), NOW(), NULL),
  (34, 17, 38, '이런 주제 좋아요 👍', NOW(), NOW(), NULL),
  (35, 18, 2, '다음 투표도 기대할게요.', NOW(), NOW(), NULL),

  (36, 18, 24, '생각보다 깊은 주제네요.', NOW(), NOW(), NULL),
  (37, 19, 31, '이건 좀 어렵다...', NOW(), NOW(), NULL),
  (38, 19, 15, '저는 직관적으로 골랐어요.', NOW(), NOW(), NULL),
  (39, 20, 40, '결과 나오면 다시 올게요.', NOW(), NOW(), NULL),
  (40, 20, 13, '댓글 보고 생각 바뀜 ㅋㅋ', NOW(), NOW(), NULL);
