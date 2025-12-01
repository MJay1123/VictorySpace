![VictorySpace Logo](https://github.com/user-attachments/assets/98e988e4-b422-4282-b06d-f1fd0e3f759c)


🏷️ Commit Types (추천)
Type	    설명	                                      예시
feat	    새로운 기능 추가	                            feat(auth): add jwt validation logic
fix  	    버그 수정	                                  fix(api): resolve null pointer error in login
docs	    문서 수정 (README, 주석)	                    docs(readme): update setup instructions
style	    코드 포맷팅, 세미콜론 등 — 기능 변화 없음	    style(front): apply eslint formatting
refactor	리팩토링 (기능 변화 없음)	                    refactor(service): optimize query logic
test	    테스트 코드 추가 / 수정	                      test(user): add unit tests for login service
chore	    빌드 설정, 패키지 설치 등 기능 변화 없는 작업	chore: update dependencies
perf	    성능 개선	                                  perf(query): improve pagination performance
ci	      CI/CD 관련 변경	                            ci: configure github action workflow
build	    빌드 시스템 수정 (webpack, vite 등)	        build(front): update vite config
revert	  커밋 되돌리기	                              revert: revert login validation logic

🔥 Branch Naming Rule (추천)
목적	    규칙      	          예시
기능 개발	feat/<feature-name>	  feat/user-auth
버그 수정	fix/<bug-name>	      fix/login-error
문서 작업	docs/<description>	  docs/setup
리팩토링	  refactor/<scope>	    refactor/user-service
배포	    release/<version>	    release/v1.0.0

📌 PR Title Rule
[feat] 사용자 로그인 기능
[fix] JWT 인증 오류 수정
[refactor] UserService 코드 정리
