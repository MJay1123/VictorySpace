<p align="center">
  <img src="https://github.com/user-attachments/assets/98e988e4-b422-4282-b06d-f1fd0e3f759c" width="280px" />
</p>

---

## 🏷️ Commit Convention
### 🏷️ Commit Types

| Type | 설명 | 예시 |
|------|------|------|
| feat | 새로운 기능 추가 | `feat(auth): add jwt validation logic` |
| fix | 버그 수정 | `fix(api): resolve null pointer error in login` |
| docs | 문서 수정 (README, 주석 포함) | `docs(readme): update setup instructions` |
| style | 코드 포맷팅, 네이밍 변경 등 (기능 변화 없음) | `style(front): apply eslint formatting` |
| refactor | 코드 리팩토링 (동작 변경 없음) | `refactor(service): optimize query logic` |
| test | 테스트 코드 추가 및 수정 | `test(user): add unit tests for login service` |
| chore | 빌드 설정, 패키지 설치 등 유지 작업 | `chore: update dependencies` |
| perf | 성능 개선 | `perf(query): improve pagination performance` |
| ci | CI/CD 관련 수정 | `ci: configure github action workflow` |
| build | 빌드 시스템 수정 (webpack, vite 등) | `build(front): update vite config` |
| revert | 이전 커밋 되돌리기 | `revert: revert login validation logic` |

### 🔥 Branch Naming Rule

| 목적 | 규칙 | 예시 |
|------|------|------|
| 기능 개발 | `feat/<feature-name>` | `feat/user-auth` |
| 버그 수정 | `fix/<bug-name>` | `fix/login-error` |
| 문서 작업 | `docs/<description>` | `docs/setup` |
| 리팩토링 | `refactor/<scope>` | `refactor/user-service` |
| 배포 | `release/<version>` | `release/v1.0.0` |

### 📌 PR Title Rule

- `[feat] 사용자 로그인 기능`
- `[fix] JWT 인증 오류 수정`
- `[refactor] UserService 코드 정리`

---

## terminal로 project 실행

build/libs/

myapp-0.0.1-SNAPSHOT.jar

cd 프로젝트경로/build/libs

java -jar myapp-0.0.1-SNAPSHOT.jar

netstat -ano | findstr :8080

TCP    0.0.0.0:8080     0.0.0.0:0      LISTENING     12345

taskkill /PID 12345 /F
