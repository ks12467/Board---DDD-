# DDD 기반 인증/유저 시스템

모놀리식 구조에서 **MSA 전환을 고려한 설계**를 적용한 백엔드 구조입니다

## 📁 디렉토리 구조
<img width="482" height="1587" alt="스크린샷 2025-08-01 18 20 15" src="https://github.com/user-attachments/assets/712cd4cc-8457-4444-8619-6c9e3d8c1770" />

## 🔐 Auth 도메인

### 📌 주요 기능
- 회원가입 (`createUser`)
- 로그인 (`login`)
- 비밀번호 암호화
- JWT 토큰 발급

### ✅ 책임 분리
- `AuthController`: 인증 요청 처리
- `AuthService`: 유즈케이스 처리, FeignClient 호출
- `dto.request`: `LoginRequest`, `CreateUserRequest`
- `dto.response`: `LoginResponse`, `CreateUserRespons

### 🔄 흐름 예시 – 로그인
  1.	AuthController → login()
  2.	AuthService → userClient.findByLoginId()
  3.	비밀번호 확인 + 상태(WITHDRAW 등) 체크
  4.	JwtUtil을 통해 AccessToken 발급

## 👤 User 도메인

### 📌 주요 기능
- 사용자 생성, 조회
- 유저 상태 관리 (`ACTIVE`, `WITHDRAW`, `BLOCKED`)
- 로그인 ID 중복 확인

### ✅ 책임 분리
- `UserController`: 유저 API
- `UserService`: 유저 비즈니스 처리
- `Users`: Entity
- `UserRepository`: JPA 저장소
- `dto.request`, `dto.response`: 요청/응답 DTO
- `enums`: `UserStatus`, `UserRole`

### 🔄 회원가입 흐름
	[AuthController]                  ← 사용자가 회원가입 요청
   	 ↓
	[AuthService]
   	 └─ userClient.existsByLoginId()
            ↓
        [UserClientImpl]
            ↓
        [UserService]
            ↓
        [UserRepository] → boolean 반환 (중복 체크)

   	 ↓

   	 └─ userClient.createUser(CreateUserDto)
        	    ↓
        	[UserClientImpl]
         	   ↓
       	 [UserService]
        	    ↓
	        [Users Entity 생성 + 저장]


### 🔄 로그인 흐름 
	[AuthController]                  ← 사용자가 로그인 요청
    		↓
	[AuthService]
    	└─ userClient.findByLoginId()
            ↓
        [UserClientImpl]
            ↓
        [UserService]
            ↓
        [UserRepository]
            ↓
        Users → UserDto 변환

    	↓
	[AuthService]
   	 └─ 비밀번호 일치 확인
    	└─ 탈퇴 상태(WITHDRAW) 여부 확인
    	└─ JWT 토큰 생성 → LoginResponse 생성

---

## 🌐 Global 구성

### 📌 공통 책임
- 예외 처리 (`GlobalExceptionHandler`)
- 커스텀 예외 (`BaseException`, `ErrorStatus`)
- 공통 응답 포맷 (`BaseResponse`)
- 보안 설정 (`SecurityConfig`, `JwtUtil`, `JwtFilter`)
- 유틸리티 (`TimeStamped`, `PasswordEncoder` 등)

---

## 🔁 도메인 간 통신 (Feign 스타일)

### ✅ 구조
- `UserClient`: 도메인 간 인터페이스
- `UserClientImpl`: 실제 내부 구현
- `CreateUserDto`, `UserDto`: 전용 통신 DTO
