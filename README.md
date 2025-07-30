# Blog

개인 프로젝트로 개발한 **React + Spring Boot** 기반 블로그 웹 애플리케이션 입니다.
게시글 CRUD 기능을 중심으로 전체 개발 과정을 혼자서 설계 및 구현 하였습니다.

## 주요 기능 
- 게시글 작성 / 조회 / 수정 / 삭제 기능 (CRUD)
- 게시글 목록 페이지 / 상세 페이지 구현 / 프로필 페이지
- **1:1 채팅 기능** (WebSocket 기반 실시간 채팅)
- **단체 체팅 기능** (채팅방 생성 / 참여 / 메시지 전송)
- **사용자 인증 / 인가 (JWT 기반)** 로그인 / 회원가입 / 권한 관리
- REST API 설계 및 구현

## 기술 스택
- **Frontend**: React, React Router, Axios
- **Backend**: Spring Boot, Spring Data JPA, Spring Security, JWT WebSocket, MariaDB
- **Version Control**: Git, GitHub

## 프로젝트 후기
처음으로 프론트엔드와 백엔드 전반을 혼자서 개발한 프로젝트입니다.
백엔드는 Spring Boot 기반으로 직접 REST API 및 WebSocket 기반 채팅 기능을 설계하고 구현하였고,
프론트 엔드는 React가 처음이라 AI를 활용하여 구현하였습니다.

## ERD다이어그램
<img width="904" height="713" alt="스크린샷 2025-07-21 13 25 48" src="https://github.com/user-attachments/assets/c7b9f9b4-4021-4d36-84a8-e89fae2250ce" />


## 흐름도
<table>
  <tr>
    <td><img width="392" height="629" alt="스크린샷 2025-07-23 13 25 31" src="https://github.com/user-attachments/assets/67dfd1ad-45aa-4f4a-b3d0-50c81f991320" /></td>
    <td><img width="335" height="585" alt="스크린샷 2025-07-23 13 29 40" src="https://github.com/user-attachments/assets/c4b038e0-1cfb-4676-a365-07e188ed3832" /></td>
  </tr>
  <tr>
    <td align="center">Blog 작성</td>
    <td align="center">Blog 수정</td>
  </tr>

 <tr>
    <td><img width="316" height="560" alt="스크린샷 2025-07-23 13 32 29" src="https://github.com/user-attachments/assets/c9d52f11-5aa5-43ec-b40a-adaf687eb42a" /></td>
    <td><img width="667" height="663" alt="스크린샷 2025-07-30 17 10 41" src="https://github.com/user-attachments/assets/e40dbade-cbc1-4a31-9edb-82943cfbb021" /></td>
  </tr>
  <tr>
    <td align="center">Blog 삭제</td>
    <td align="center">Blog 댓글</td>
  </tr>


   <tr>
    <td><img width="316" height="560" alt="스크린샷 2025-07-23 13 51 24" src="https://github.com/user-attachments/assets/864c89e1-2195-45f3-a75b-2f708fa8d936" /></td>
    <td><img width="771" height="560" alt="스크린샷 2025-07-23 13 52 17" src="https://github.com/user-attachments/assets/328c9301-fb03-4b14-8a92-5811d274f381" /></td>
  </tr>
  <tr>
    <td align="center">친구 요청</td>
    <td align="center">친구 수락</td>
  </tr>

  
   <tr>
    <td><img width="729" height="609" alt="스크린샷 2025-07-23 14 52 34" src="https://github.com/user-attachments/assets/af2e9947-de1e-44e4-a92e-a05651f20a30" /></td>
    <td><img width="476" height="599" alt="스크린샷 2025-07-23 14 30 59" src="https://github.com/user-attachments/assets/de9c85a9-d24e-4f9a-a436-edba3f552b92" /></td>
  </tr>
  <tr>
    <td align="center">회원가입</td>
    <td align="center">로그인</td>
  </tr>
  
</table>

---

## 프로젝트 결과물
![스크린샷 2025-06-17 13 16 02](https://github.com/user-attachments/assets/8aec36de-f083-4ae5-8603-68b4d2a34651)
- **메인화면**
---
  
![스크린샷 2025-06-17 13 15 37](https://github.com/user-attachments/assets/b78b5676-c381-4e82-83c9-06f104d01796)
- **회원가입 화면**
---

![스크린샷 2025-06-17 13 17 31](https://github.com/user-attachments/assets/c3aacb6a-51e0-4d6b-bf1d-550d6536553f)
- **블로그 글 작성 화면**
---

![스크린샷 2025-06-17 13 18 10](https://github.com/user-attachments/assets/cb4ad88b-1159-4d57-83c3-478a27eb142e)
- **블로그 및 댓글 작성 화면**
---

![스크린샷 2025-06-17 13 19 33](https://github.com/user-attachments/assets/8a0c5fd6-6793-49b7-98c3-c15d092f8689)
- **친구 찾기 화면**
---

![스크린샷 2025-06-17 13 20 37](https://github.com/user-attachments/assets/a89e5fa2-223a-454d-8629-3dcfef898473)
- **친구 목록 및 요청 받으 친구 화면**
---

![스크린샷 2025-06-17 13 22 17](https://github.com/user-attachments/assets/6977a32e-e817-425a-9ab5-bd52cb4fb803)
- **개인 채팅 화면**
---

<img width="920" alt="스크린샷 2025-06-17 13 23 49" src="https://github.com/user-attachments/assets/0d3cf6f8-4f42-4599-ac83-a756e581a5f9" />


- **단체 채팅 화면**
---

![스크린샷 2025-06-17 13 22 30](https://github.com/user-attachments/assets/852390b2-b4e5-4bdb-a554-2f1f45ff8b0b)
- **채팅 목록**
---






