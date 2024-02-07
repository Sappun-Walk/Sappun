# 👟 Sappun (2024.1.4 ~ 2024.2.8)

## INDEX

[1. Introduce](#Introduce) <br>
[2. Team Introduce](#Team-Introduce) <br>
[3. Develop Environment](#Develop-Environment) <br>
[4. Collaborative Strategy](#Collaborative-Strategy) <br>
[5. Service Architecture](#Service-Architecture) <br>
[6. ERD](#ERD) <br>
[7. Technical Decision](#Technical-Decision) <br>
[8. Troble Shoting](#Troble-Shoting) <br>
[9. Test](#Test)

## Introduce

본인의 산책 경로와 사이 스팟을 지도와 사진을 사용하여 공유하는 사이트<br>

### Deploy URL: https://www.sappun.shop

나만의 산책로를 지도에 그림을 그리고, 이미지 및 글을 통해 공유하고<br>
다른 사람들이 공유한 산책로를 지역 별로 구경하며 소통할 수 있는 사이트입니다!

## Team Introduce


|             <img src ="https://avatars.githubusercontent.com/u/146843126?v=4" width="160px" height="160px">             | <img src ="https://avatars.githubusercontent.com/u/72595935?v=4" width="160px" height="160px"> | <img src ="https://avatars.githubusercontent.com/u/99391320?v=4" width="160px" height="160px"> | <img src ="https://avatars.githubusercontent.com/u/139609206?v=4" width="160px" height="160px"> | <img src ="https://avatars.githubusercontent.com/u/146907732?v=4" width="160px" height="160px"> |
|:-----------------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------:|
|                                                           팀장                                                            |                                              부팀장                                               |                                               팀원                                               |                                               팀원                                                |                                               팀원                                                |
|                                  홍정욱<br>[@peter202597](https://github.com/peter202597)                                  |                      김재한<br>[@rlawogks22](https://github.com/rlawogks22)                       |                        이예진<br>[@dlwls423](https://github.com/dlwls423)                         |                          박상율<br>[@hayul93](https://github.com/hayul93)                          |                         김진환<br>[@jump6923](https://github.com/jump6923)                         |
|                                    -프로필 기능<br>-아이디/이름<br>중복 확인<br>-[카카오, 네이버] 소셜로그인                                     |                           -[게시글, 댓글]<br>좋아요, 신고 <br>관련 기능<br>-페이징 처리                           |                                    -인증/인가<br>-회원가입, <br>회원탈퇴<br>-로그인, 로그아웃(인증인가)<br>-CI/CD(배포)                                   |                          -댓글 기능 <br>(사진첨부)<br>-S3 <br>-이미지 <br>리사이징<br>-CSS                     |                                   -게시글 기능<br>-지도 API 연동<br>-NGINX, HTTPS                                |


## Develop Environment

### Backend

- Java 17
- Spring Boot 3.2.0
    - Validation
    - thymeleaf
- Spring Data JPA
- Spring Security 6.2.0
- OAuth 2 (소셜 로그인)

### Frontend

- HTML
- CSS
- JavaScript

### DB

- MySQL 8.0.33
- Redis 6.0.16 (토큰)
- H2DB 2.1.214 (Test용)

### Infra

- AWS
    - EC2
    - S3
    - CloudFront
    - RDS
    - Route53

### Docs

- Jacoco

## Collaborative Strategy

### Git flow

- `main`
- `develop (default)`
- `feature/`
    - ex. `feature/login`

### Commit Convention

- `Feat`: 새로운 기능 추가
- `Fix`: 버그 수정
- `Docs`: 문서 수정
- `Style`: 코드 스타일 변경 (코드 포매팅, 세미콜론 누락 등),
  기능 수정이 없는 경우
- `design` : 사용자 UI 디자인 변경 (CSS 등)
- `test`: 테스트 코드, 리팩토링 테스트 코드 추가
- `refactor`: 코드 리팩토링
- `build`: 빌드 파일 수정
- `ci`: CI 설정 파일 수정
- `perf`: 성능 개선
- `chore`: 빌드 업무 수정, 패키지 매니저 수정 (gitignore 수정 등)
- `rename`: 파일 혹은 폴더명을 수정만 한 경우
- `remove`: 파일을 삭제만 한 경우

### Issue, PR Convention

- 개발할 기능을 `Issue`에 등록한다. + 담당자, 라벨 추가
- 커밋 메세지에 `#(이슈넘버)`로 함께 적는다.
- 깃허브 프로젝트를 이용하여 todo를 공유한다.
    - 이슈와 연결한다.
- PR을 올릴 때 관련 이슈를 등록한다. + 담당자, 라벨 추가
    - 내용에 `close #(이슈넘버)`를 함께 적는다.
- PR을 머지하는 경우 최소 1개의 approve가 필요하다.

### Code Style

- Spotless 적용

### Code Convention

- Entity: `setter`와 `method` 사용 x, `private Builder` 사용
- 객체 간 변환: `org.mapstruct.Mapper` 사용
- api 1개마다 req, res dto 1개씩 사용
- Custom `ResultCode`, `ResponseEntity` 사용
- `Optional` 제외: validator file로 검증 로직을 분리
- Service 인터페이스와 impl로 분리
- Repository 기능을 제한: `@RepositoryDefinition`
- 네이밍 규칙
    - dto: 도메인 + 기능 + Req/Res
    - Entity: 도메인명 그대로
    - 테이블명: tb_도메인명
    - id: 도메인명 + id

## Service Architecture

<img width="725" alt="아키텍처" src="https://github.com/Sappun-Walk/Sappun/assets/99391320/86c7ce5f-cbb4-4399-b734-d377137ca977">

## ERD

<img src="src/main/resources/static/images/ERD.png" width="900px" height="500px">

## Technical Decision

<details>
<summary> Refresh Token 및 로그아웃 처리</summary>

- Refresh Token 사용
    - 토큰 방식을 사용하는 이유
        - 상태를 서버에 저장하지 않으므로 확장성이 좋음
        - 서버 간의 데이터 동기화 문제를 해결하기 쉬움
    - RefreshToken을 사용하는 이유
        - 만료기한이 짧은 access token만 사용하는 경우 사용자가 토큰이 만료될 때마다 로그인해야 하는 불편함을 겪게 됨
            - 자동 로그인 구현으로 편의성 증가
        - Access token의 수명을 짧게 하여 accesstoken 노출되더라도 짧은 시간 내에 만료되게 함으로써 보안성을 높일 수 있음<br><br>

- Redis 사용 이유

  Refresh Token을 서버에서 저장하기 위해 Redis를 사용

    - 인메모리 DB로 빠른 응답이 가능
    - TTL을 지원하여 토큰의 만료시간을 지정하고 관리하는데 유용함
    - Key-Value 구조로 refresh token을 쉽게 저장하고 검색할 수 있음<br><br>

- 토큰 작동 방식
    - 로그인한 경우
        - 고유한 사용자 정보인 user id를 access token에 담아 발급
        - refresh token에 만료 기한만 담아 발급한 뒤 Redis에 저장
            - refresh token를 key값으로, 사용자 id를 value값으로
        - 쿠키에 access token, refresh token 값을 담아 반환
    - 사용자가 만료된 access token으로 접근한 경우
        - refresh token 값이 존재하지 않는 경우 refresh token이 유효하지 않다는 예외처리
        - 유효한 refresh token이 존재하는 경우 새로 access token을 발급
        - refresh token이 만료된 경우 재로그인을 요청
    - 사용자가 로그아웃한 경우
        - access token의 남은 유효기간만큼 Redis TTL을 설정하여 블랙리스트로 저장
        - refresh token을 삭제


- 로그아웃 처리에 대한 고민
  access token을 서버에서 저장한다면 토큰 인증 방식의 이점이 사라지는 것이 아닌지에 대한 고민<br>
  세션 방식보다 서버에 저장하는 데이터의 양이 적고, 로그아웃하는 사용자의 토큰만 저장하기 때문에 로그아웃 시에 쿠키만 삭제하는 것보다 토큰을 블랙리스트 처리하여 보안성을 높이는 것이 더 좋다고 판단

</details>

<details>
<summary> CI/CD</summary>

- Github Actions 및 Docker를 통해 CI/CD를 적용하여 빌드와 테스트 및 배포 자동화
- **Github Actions 및 Docker 사용 이유**
    - Github Actions 사용 이유:
        1. GitHub Actions는 GitHub과 긴밀하게 통합되어 있으므로 코드 변경 사항에 대한 자동화된 작업을 쉽게 구성 가능
        2. YAML 구문을 사용한 간단하고 명확한 구성 파일을 통해 워크플로우를 설정 가능하며 참고할 수 있는 자료가 풍부함
    - Docker 사용 이유:
        1. 같은 환경으로 구성된 컨테이너로 동작하기 때문에 표준화된 배포를 구성할 수 있음
        2. 여러 애플리케이션의 독립성과 확장성이 높아짐
- **CI**
    - Github Actions를 사용하여 팀원들이 PR을 올릴 때마다 빌드 및 테스트 과정을 자동으로 거치도록 자동화 하였음
    - 빌드 및 테스트가 실패한다면 develop 저장소에 코드가 병합되지 못하도록 설정하여 협업에서의 코드 충돌을 방지함
    - 더 효과적인 CI를 위하여 기능을 추가할 때마다 단위 테스트 코드를 작성하는 것을 컨벤션으로 추가하였으며, 팀원들은 이를 준수하기 위해 노력함
    - 그 결과 Jacoco 커버리지 60%에 도달하였으며 프로젝트 기간 동안 높은 코드 품질을 유지할 수 있었음
- **CD**
    - Github Actions, Docker, EC2를 사용하여 코드가 develop에 병합될 때마다 빌드된 jar 파일을 도커 이미지로 빌드
    - 해당 이미지를 도커 허브에 push하고 AWS의 EC2 서버에서 pull 받는 과정을 자동화

</details>

<details>
<summary> S3</summary>

- **[배경과 요구사항]**<br>
  산책로 공유시 스팟을 이미지로 공유하는 기능으로 이미지 업로드 구현이 필요했음.
  50MB정도의 이미지파일을 업로드할수있어야함
  고려해할사항으로 업로드 속도, 저장공간, 실패시 재시도용이성, 안전성, 비용등이있다.
  현재 우리 서비스는 클라이언트에서 최대 45MB 크기의 파일을 업로드하는 기능이 필요합니다.
  이를 가능하게 하는 효율적이고 안정적인 방법을 찾아야 합니다.
  이 때 고려해야하는 사항은 업로드 속도, 실패 시 재시도 용이성, 코드 복잡성, 그리고 비용이다.<br><br>

- **[선택지]**

    - **AWS S3 Multipart 업로드**<br>
      장점: 대용량 파일을 빠르게 업로드하거나, 네트워크 연결이 불안정한 환경에서도 안정적으로 파일을 업로드할 수 있음. 만약 업로드 도중에 실패하더라도 해당 부분만 다시 업로드하면 되기 때문에 효율적.<br>
      단점: 구현이 복잡하고, 업로드를 시작하고 완료하는 등의 추가적인 작업이 필요.

    - **MultipartFile 업로드**<br>
      장점: 스프링 프레임워크에서 제공하는 기능을 사용하므로, 구현이 쉽고 편리해. 클라이언트가 업로드한 파일을 쉽게 처리할 수 있다.<br>
      단점: 대용량 파일을 업로드하는 데에는 비효율적일 수 있다. 또한, 스프링 프레임워크에 의존적이라는 점이 단점으로 작용할 수 있다.

    - **Stream 업로드**<br>
      장점: 대용량 파일을 처리하는 데에 유용하며, 파일을 스트림으로 변환해 네트워크를 통해 전송하므로 효율적.<br>
      단점: 스트림을 사용하므로 구현이 복잡하고, 스트림 처리에 대한 이해가 필요.

    - **AWS SDK 업로드**<br>
      장점: AWS 서비스에 대한 직접적인 접근과 제어를 가능하게 해줌. 상세한 설정이나 복잡한 작업을 수행하려는 경우에 유용.<br>
      단점: 사용 방법이나 설정 방법이 다소 복잡하고, AWS 서비스의 세부적인 기능을 이해하고 있어야 함.

    - **Spring Cloud AWS 업로드**<br>
      장점: 스프링 환경에서 AWS 서비스를 더 쉽고 간편하게 사용할 수 있게 도와줘. 스프링의 기능(예: 빈 주입)과 잘 통합됨.<br>
      단점: AWS SDK보다 상세한 설정이나 제어가 어려울 수 있음. 또한, Spring Cloud AWS가 지원하는 AWS 서비스나 기능이 AWS SDK보다 제한적이다..<br><br>

- **[의사결정]**<br>
    - 위의 선택지 중 AWS SDK를 이용한 업로드 방식이 우리의 요구사항과 가장 잘 맞는다고 판단하였습니다. 이 방식은 직접적인 접근과 유연성, 그리고 강력한 기능을 제공하므로, 우리의 상황에서 가장 적합한
      방법이라고 결정하였습니다. 다른 방식들의 복잡성, 비효율성, 제한적인 기능 등의 단점을 고려했을 때, AWS SDK를 사용하는 것이 가장 최적의 선택이라고 생각합니다.

</details>

<details>
<summary> Nginx (무중단 배포 및 https)</summary>

- 문제 상황
    - GitHub Action을 통해 자동 배포를 구성하였지만 배포 시에 서버의 downtime이 발생하는 문제
    - Google Maps API에서 위치 권한을 필요로 하는데 HTTP에서는 위치 권한이 제한되어 현재 위치를 가져올 수 없는 문제
- 도입 이유
    - Nginx는 리버스 프록시 서버와 HTTPS를 적용하기 위한 웹 서버로서 적은 메모리 사용량으로 높은 성능과 효율을 내고 추후 HTML, CSS, 이미지 파일 등 정적 파일 처리에 적절한 기능을 갖추고
      있기에 선택
- 해결 방안
    - NGINX로 Blue-Green배포를 활용하여 포트가 다른 두 개의 배포 환경을 병렬로 구현
    - 이중 환경 구조를 통해 downtime이 없고 리스크가 낮은 배포구성
    - Nginx에서 Certbot 플러그인을 사용하여 빠르게 Let’s Encrypt에서 SSL인증서를 발급 받아 HTTPS 환경 구성
- 의견 결정
    - 무중단 배포와 HTTPS 구성을 NGINX에서 함께 구현하면 유연성 및 편의성의 이점이 커서 선택함

</details>

<details>
<summary> Kakao Maps 정책 문제로 인한 Maps Platform 변경</summary>

- 문제 상황
    - Kakao Maps API를 활용해 프로젝트에 지도 활용 및 PolyLine, 검색 및 마커 기능을 이용할 예정이였으나 산책로를 제공 및 관리하기 위해 Kakao Maps의 정적지도의 이미지화 및 캡쳐가
      Kakao Maps 정책상 저작권 관련으로 지도의 캡쳐가 불가능 했고 Kakao Maps내의 내보내기 기능을 이용하면 산책로를 나타낸 PolyLine 및 경유지를 나타낸 마커 기능을 활용하지 못하는 상황
- 도입 이유
    - Kakao Maps가 간편한 API 연동과 UI/UX의 우수함 및 무료 제공이라는 측면에서 이점이 있지만 저작권 문제로 인한 치명적인 기능제한이 있기에 차선책으로 Google Maps Platform을
      도입하게 되었음. 비용이 부과되는 단점이 있지만 안정성과 보안, 풍부한 데이터와 커버리지를 가지고 있고 유용한 API가 많기 때문에 도입을 결정
- 해결 방안
    - 필요로 했던 산책로를 도식한 이미지 캡처는 동적 지도 화면에서는 불가능 했지만 동적 지도를 기준으로 정적 지도를 재생성하여 해당 지도를 이미지화 시켜 저장 및 활용하는 방안으로 해결하였고 산책로를 나타내기
      위한 PolyLine 또한 google maps에서 제공하는 API인 drawingManager를 활용하여 지도에 도식하여 사용자들에게 산책로를 편하고 쉽게 제공 및 공유할 수 있게 하였음
- 의견 결정
    - Google Maps Platform은 안정성과 보안 측면에서 강력하며 다양한 API를 제공하여 원하는 기능을 유연하게 구현할 수 있음 또한, 동적 지도를 기반으로 정적 지도를 생성하여 이미지로 저장하는
      방법은 Kakao Maps의 제한을 극복하는 좋은 대안으로 생각함 PolyLine을 그리는데 drawingManager를 활용하는 것은 사용자가 산책로를 편리하게 나타내고 공유할 수 있도록 하는데
      효과적이라고 생각하고 Google Maps Platform으로 결정한것은 프로젝트의 목표를 달성하면서도 저작권 문제를 준수하고 안정성 및 다양한 기능을 고려하여 결정했다고 생각함 결론적으론 사용자들에게 더
      나은 서비스를 제공할 수 있을 것으로 예상함

</details>

## Troble Shoting

<details>
<summary> 이미지 업로드 및 Resize</summary>

- 이미지 업로드 용량제한 이슈

    - 문제 : 게시글 , 댓글, 프로필의 이미지파일이 1MB이상일때 업로드가 되지않는 이슈발생.

    - 원인 : nginx의 업로드 size의 디폴트 설정값이 1MB로 설정되어있었음.

    - 해결 : nginx의 업로드 size 값을 50MB로 설정해주어 1MB이상으로 업로드 되는 것을 확인.
      50MB로 설정한 이유는 게시글 작성시 사진을 최대 4장 업로드할수있는데 사진당 10MB를 업로드할수있게 해놓고 맵이미지 및 text 용량을 고려하여 여유있게 50MB로 설정

    - 고려해야 할 사항 : 기존 1MB이하의 사진만 업로드 되었던 상황과 달리 클라이언트 측에서 10MB이상의 파일을 업로드하는 요청이 많아지게되면 서버의 과부하가 될수있는 상황이 발생될수있음.

    - nginx 업로드 용량 설정

 <pre><code>http {
    # 전역 설정
    client_max_body_size 50M;
    }</code></pre>

- 이미지 업로드 속도 이슈로 리사이징

    - 문제 : 이미지 파일 업로드 용량을 사진당 10MB로 설정후 9mb정도의 사진을 업로드시 업로드속도가 느려지는 이슈발생<br>
      (기존 1mb이하의 사진에선 속도저하 없었음)<br>
      큰 파일의 요청이 많아지면 서버의 과부하가 발생될 수 있는 우려.

    - 원인 : 업로드 제한 용량이 증가하면서 큰용량의 사진을 업로드하는경우, 파일의 크기가 크기때문에 업로드속도가 인터넷 속도, 서버의 처리속도 등 여러 요인에 의해 느려질수있음.

    - 해결 : 업로드 속도가 느려진 이유가 이미지의 용량이 크기때문에 발생한다는 근본적인 이슈를 해결하고자 이미지를 리사이즈하여 용량을 줄여 서버에 보내는 방법을 선택
      <br>프런트를 구현한 js의 canvas 라이브러리를 이용해 이미지 리사이징을 하여 이미지용량을 줄여 서버로 보내줌
      <br>9MB의 사진을 업로드했을때 157kb의 사진으로 리사이징됨
      <br>리사이징된 이미지를 저장하고 사용하여 게시글 조회등 서비스이용에 처리속도가 느려지지않음.
      업로드 속도도 빨라져 사용자의 이용에도 문제가 없음.<br><br>

- TEST 결과 보고

    - 주제 : 이미지 리사이징 전후 성능 비교

    - 목표 :

    1. 이미지 리사이징을 통해 서버 응답시간과 이미지 로딩 시간의 개선 여부를 확인한다.
    2. 각 케이스별로 세 번의 테스트를 통해 결과를 일반화한다.<br>

    - 테스트 환경 :

        - 이미지 원본: 9,548 kb, 네 장 (동일 이미지를 사용)
        - 서버 환경: 리사이징 전에는 로컬 서버, 리사이징 후에는 배포된 서버 사용
        - 테스트 도구: 크롬 브라우저의 개발자 도구를 통한 응답시간 측정<br>

    - 테스트 결과:

        - 리사이징 전 :

        1. 게시글 작성 (서버 응답시간)

        - a. 3.82초
        - b. 3.91초
        - c. 4.25초
          평균 응답시간: 3.99초

            2. 게시글 조회 (이미지 용량 및 로딩시간)

        - a. 크기 9.8MB*4, 3.87초
        - b. 크기 9.8MB*4, 4.58초
        - c. 크기 9.8MB*4, 3.92초
          평균 로딩시간: 4.12초

    - 리사이징 후 :

        1. 게시글 작성 (서버 응답시간)

        - a. 880.30 밀리초
        - b. 671.93 밀리초
        - c. 460.15 밀리초
          평균 응답시간: 670.46 밀리초

        2. 게시글 조회 (이미지 용량 및 로딩시간)

        - a. 크기 161kb, 124밀리초
        - b. 크기 161kb, 134밀리초
        - c. 크기 161kb, 146밀리초
          평균 로딩시간: 134.67밀리초

    - 결과 분석 :

    1. 서버 응답시간:

    - 리사이징 전 평균 응답시간: 3.99초
    - 리사이징 후 평균 응답시간: 670.46 밀리초
      리사이징 후에는 평균 응답시간이 이전에 비해 약 6배 이상 개선.

    2. 이미지 로딩시간:

    - 리사이징 전 평균 로딩시간: 4.12초
    - 리사이징 후 평균 로딩시간: 134.67밀리초
      리사이징 후에는 평균 로딩시간이 이전에 비해 약 30배 이상 개선.

    - 결론 :
      이미지 리사이징을 통해 서버 응답시간과 이미지 로딩시간이 현저하게 개선됨.<br>
      이를 통해 사용자 경험을 향상시키고 서버 부하를 줄일 수 있다는 것을 확인.

업로드전 이미지리사이징을 해서 더욱 서버의 부담도 줄고 사용자가 사용하는데 있어서도 불편함이 덜 할 수 있었다.

</details>

<details>
<summary> N+1 문제 해결 및 QueryDSL 적용</summary>

- N+1 문제

    - 문제: 게시글을 조회하는 경우 연관 관계에 있는 다른 테이블(사용자, 첨부파일, 경유지 등)에 대한 select문이 N번 추가 발생함.

    - 원인: JPQL을 통해 쿼리를 작성할 때 N+1문제를 고려하지 않았으며 기능 구현에만 집중하여 쿼리의 개수를 살펴보지 않음.

    - 결과: 기존에 사용하던 문자열 기반의 쿼리 언어인 JPQL을 사용하는 것보다 컴파일 시점에 오류를 감지할 수 있고<br>
      동적 쿼리를 작성하기에 적합하여 유지보수성이 좋은 QueryDSL을 사용하는 것이 더
      좋다고 판단하여
      리팩토링하였으며 그 과정에서 join을 통해 N+1문제를 해결함.

- QueryDSL 적용
    - 문자열 기반의 쿼리 언어인 JPQL의 단점
        - 컴파일 시점에 쿼리의 문법적 오류나 타입 불일치를 감지하지 못하고, 런타임 시점에서야 오류를 발견할 수 있음
        - 동적 쿼리를 작성하기 어려움
        - 쿼리 변경 시 관련된 모든 문자열을 찾아 수정해야 하므로 이는 유지보수를 어렵게 만들 수 있음

    - QueryDSL 단점
        - 초기 학습 과정이 필요
        - QueryDSL을 사용하기 위한 초기 설정과 구성이 필요
        - QueryDSL은 별도의 라이브러리이므로, 프로젝트에 추가적인 종속성이 생김

    - 그럼에도 QueryDSL을 사용하게 된 이유
        - 타입 안전성: 컴파일 시점에 오류를 미리 잡을 수 있음
        - 메서드 체인 방식: 동적 쿼리에 적합, 가독성 및 유지보수성 증가
        - IDE 지원: 자동완성, 리팩토링, 오류 강조 등 IDE의 기능을 쿼리 작성 시 활용 가능

  <Repository 구조>
    - Repository가 인터페이스인 JPARepository와 QueryDsl을 사용하는 RepositoryCustom을 상속
        - @RepositoryDefinition(domainClass = Board.class, idClass = long.class) 을 통해 무분별한 매서드 사용을 제한함
        - 프로젝트에서 사용하고 있는 JPA 쿼리 메소드를 확인하고 테스트하기에 용이함
    - RepositoryCustomImpl을 통해 RepositoryCustom을 구현

```
@RequiredArgsConstructor
@Import(QuerydslConfig.class)
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {
private final JPAQueryFactory queryFactory;
private final QBoard qBoard = QBoard.board;
private final QLikeBoard qLikeBoard = QLikeBoard.likeBoard;
private final QUser qUser = QUser.user;
private final QImage qImage = QImage.image;

    @Override
    public Board findById(Long boardId) {
        return queryFactory
                .selectFrom(qBoard)
                .join(qBoard.user, qUser)
                .fetchJoin()
                .leftJoin(qBoard.images, qImage)
                .fetchJoin()
                .where(qBoard.id.eq(boardId))
                .fetchOne();
    }

    @Override
    @Transactional
    public void deleteAllByUser(User user) {
        queryFactory.delete(qBoard).where(qBoard.user.eq(user)).execute();
    }

    @Override
    public Page<Board> findAllByReportCountLessThanAndRegion(
            int reportCount, RegionEnum region, Pageable pageable) {
        JPAQuery<Board> query =
                queryFactory
                        .selectFrom(qBoard)
                        .join(qBoard.user, qUser)
                        .fetchJoin()
                        .where(qBoard.region.eq(region))
                        .where(qBoard.reportCount.lt(reportCount));

        // Pageable의 Sort 정보를 사용하여 정렬 조건 추가
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(qBoard.getType(), qBoard.getMetadata());
            query.orderBy(
                    new OrderSpecifier<>(
                            o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }

        List<Board> boards = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

        Long total =
                queryFactory
                        .select(qBoard.count())
                        .from(qBoard)
                        .where(qBoard.region.eq(region))
                        .where(qBoard.reportCount.lt(reportCount))
                        .fetchOne();

        // null 체크 후 기본값 제공
        long totalCount = (total != null) ? total : 0;

        return new PageImpl<>(boards, pageable, totalCount);
    }

		// ...생략...
}
```

</details>

<details>
<summary> Redis 데이터 삭제 문제</summary>

- 문제 1.
    - **Access token이 만료되었을 때 refresh token을 통해 access token을 자동 재발급되는 과정에서 문제가 발생하여 자동 로그인하지 못하는 문제가 발생함**
    - Refresh token을 redis에 저장할 때 `refresh token`을 `key`값으로, 변하지 않는 사용자 정보인 `userId`를 `value` 값으로 저장함
    - 이때 Long 타입인 userId를 String으로 변환하여 저장
    - 그러나 redis에서 key값인 refreshToken으로 userId 값을 가져올 때 `“2”`가 아니라 `“”2””`으로 반환되어 해당 String으로 Long으로 변환하는 과정에서 오류가 발생하여
      userId에 해당하는 유저 정보를 가져오지 못함

- 해결 1. 자동 로그인 로직 수정

  따라서 redis를 조회하여 refresh token에 해당하는 value인 userId에 “이 있으면 제거하는 로직을 `UserDetailsServiceImpl`의 `loadUserByUsername` 매서드에
  추가

    ```java
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.contains("\"")) {
            username = username.replace("\"", "");
        }
        User user = userRepository.findById(Long.parseLong(username));
        UserValidator.validate(user);
        return new UserDetailsImpl(user);
    }
    ```

- 문제 2.

  access token이 만료되었을 때 자동 로그인에 문제가 다시 발생

  콘솔 로그를 살펴보니 `refresh token`을 redis에서 조회하였을 때 `null`값을 가져옴

  **즉, refresh token이 redis에 존재하지 않음**

- 해결 2. Redis 비밀번호 설정

  우선 redis에 refresh token이 잘 저장되고 있는지 확인

  로그인 후 redis의 키 조회

  `TTL`도 `2주`, `value`도 `“”userId””`임을 확인 → 잘 저장되고 있음을 확인

  레디스에 잘 저장된 값이 삭제되는 원인을 몰라 다양한 시도를 해봄

    - 시도1. `RedisConfig`에서 `StringRedisTemplate` 사용

    ```java
        @Bean
        public StringRedisTemplate stringRedisTemplate() {
            StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
            stringRedisTemplate.setConnectionFactory(redisConnectionFactory());
            return stringRedisTemplate;
        }
    ```

  key와 value가 모두 String이므로 StringRedisTemplate을 사용해보라는 조언을 듣고 변경해보았지만 여전히 refresh token이 사라지는 문제가 발생

    - 시도2. ec2 서버에 redis를 직접 설치하지 않고 docker container로 띄움

    ```java
    sudo docker pull redis
    
    sudo docker run -d -p 6379:6379 redis
    ```

  **문제 원인: Redis에 비밀번호를 설정하지 않아 외부에서 다른 용도로 사용됨**

  Redis에 비밀번호를 설정할 수 있다는 것을 알지 못하였는데 로그 내역을 조회해보니 Redis가 외부에서 타 용도로 사용되고 있었음을 확인함

  Redis의 키 값을 조회하였을 때 “backup”이라는 단어가 같이 조회됨

    ```java
    // 데이터 결과
     1) "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc4OTYwNzcsImlhdCI6MTcwNjY4NjQ3N30.4T26IY-v95a7WlmQ-WWxTOSw_vul-aOwYaCDCRwOwuc"
     2) "backup3"
     3) "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiYXV0aCI6IuyCrOyaqeyekCIsImV4cCI6MTcwNjY4OTkzMiwiaWF0IjoxNzA2Njg2MzMyfQ.PFXTVTs1XW192Btqif414tY4vig8MBDlOo9ViEjPR-U"
     4) "backup4"
     5) "backup2"
     6) "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc4OTU0NTQsImlhdCI6MTcwNjY4NTg1NH0.oSo4dwUxteiBpp7P-neA2w6iJuPYdgWlotlHJXPmX6A"
     7) "backup1"
     8) "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwiYXV0aCI6IuyCrOyaqeyekCIsImV4cCI6MTcwNjY4OTgyMSwiaWF0IjoxNzA2Njg2MjIxfQ.5WPlyS1C5jK3OcFOeSnoYOmboUKIOxnZZATbaUnoKMc"
     9) "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc4OTU3MTYsImlhdCI6MTcwNjY4NjExNn0.qpP5t8_wZB11JoceDFnLzjonhPajC1IQmQFdywujTj8"
    10) "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc4OTYxMTMsImlhdCI6MTcwNjY4NjUxM30.2Bko04GX2d7mDWpRJ1JrrbnfJN6UTM9HiYsRBVBjvwQ"
    11) "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyNSIsImF1dGgiOiJVU0VSIiwiZXhwIjoxNzA2NjkwNzcyLCJpYXQiOjE3MDY2ODcxNzJ9.9N1L_xCVGtlL1PS7NCkE9EMEn2Y769RVu34Mmd7Krls"
    12) "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc4OTU1NjksImlhdCI6MTcwNjY4NTk2OX0.g-Q7-zvsXPsMJrosw1bOc7cjESv_KaGy_m-F1eDvbEg"
    13) "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc4OTY4NzEsImlhdCI6MTcwNjY4NzI3MX0.xxWZuU0ISgjnbtwIfwOBlOjwYyujtVynr4RRStVlazM"
    14) "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc4OTYxNDEsImlhdCI6MTcwNjY4NjU0MX0.9dsFxET20w-PbY61u8IMQUMynuwk_pQE9uP5CP_k8pg"
    ```

    - Redis 컨테이너에 비밀번호를 설정하여 run

    ```java
    docker run -v /path/on/host:/data -d --name my-redis -p 6379:6379 redis redis-server --appendonly yes --requirepass [password]
    ```

    - 이전과 달리 레디스에 접속하였을 때 비밀번호 입력을 요청

    ```java
    sudo docker exec -it my-redis redis-cli
    # 비밀번호 입력 요청
    auth [password]
    ```

    - 이전과 달리 키값을 조회하였을 때 “backup”이라는 단어가 보이지 않음

    ```java
    127.0.0.1:6379> keys *
    1) "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc5OTU2NzgsImlhdCI6MTcwNjc4NjA3OH0.QpMcjtkizOh6UAiR5wE7aqORiPff_LIFOtZZTC3AEaM"
    2) "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc5OTYxMTAsImlhdCI6MTcwNjc4NjUxMH0.HttjGP7mFUq2INohAcLYMHYvHxqLX3faX6hNcBnmKBI"
    3) "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc5OTYxMjEsImlhdCI6MTcwNjc4NjUyMX0.j2zzjDW0flYCQ2khsDY61N2koCiwl58hMFwPefZj1fY"
    4) "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc5OTYxMTEsImlhdCI6MTcwNjc4NjUxMX0.7Zg16Jqj5hnkD1C-_VQ03-Cv5U3BmvS8_MNIXqzMMfU"
    5) "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc5OTYxMTUsImlhdCI6MTcwNjc4NjUxNX0.v8qzZ7BN5bhioCn2hFn-AgIhUplQGp75fjq50nW9FoQ"
    6) "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc5OTYxMTYsImlhdCI6MTcwNjc4NjUxNn0.QzMCSqGW5ns81zQtxkBTIXwPxOM7vssrX2QDy3OfeuQ"
    ```

    - yml 파일 수정

    ```java
    data:
        redis:
          host: 3.36.206.196
          port: 6379
          password: ${REDIS_PASSWORD}
    ```

    - RedisConfig 수정

    ```java
        @Bean
        public RedisConnectionFactory redisConnectionFactory() {
            RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
            config.setHostName(host);
            config.setPort(port);
            config.setPassword(password);
    
            return new LettuceConnectionFactory(config);
        }
    ```

    - ec2 서버의 docker-compose.yml에도 환경변수 REDIS_PASSWORD 값을 추가

- 결과.
    - 로그인 직후

  `Access token:`

  Bearer%20eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwiYXV0aCI6IlJPTEVfQURNSU4iLCJleHAiOjE3MDY3OTEyMzIsImlhdCI6MTcwNjc4NzYzMn0.HgAXQXAgnkZ5r6brKL8X2hgrBA5HLtzP-p-EE_OXr_A

  `Refresh token:`

  Bearer%20eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc5OTYxMTUsImlhdCI6MTcwNjc4NjUxNX0.v8qzZ7BN5bhioCn2hFn-AgIhUplQGp75fjq50nW9FoQ

    - Redis 키 값 조회 : `10번`에 `refresh token`이 잘 저장된 것을 확인

    ```java
     1) "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc5OTkwNjUsImlhdCI6MTcwNjc4OTQ2NX0.jUsUnE89I9L5YNEVFy3h5QpNUm4ffQcMmy0RhAeTJyQ"
     2) "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc5OTkwNzcsImlhdCI6MTcwNjc4OTQ3N30.hoN3xQFYuS1zSLwrsxOFLjpQWy2QkMHjSNjbiXhvz18"
     3) "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc5OTYxMTEsImlhdCI6MTcwNjc4NjUxMX0.7Zg16Jqj5hnkD1C-_VQ03-Cv5U3BmvS8_MNIXqzMMfU"
     4) "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwiYXV0aCI6IuyCrOyaqeyekCIsImV4cCI6MTcwNjc5MjI3NiwiaWF0IjoxNzA2Nzg4Njc2fQ.RS2YvT35J8iUJdy9ccldLBRuudbT-5ylKE9MQpn4E1Y"
     5) "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc5OTYxMTYsImlhdCI6MTcwNjc4NjUxNn0.QzMCSqGW5ns81zQtxkBTIXwPxOM7vssrX2QDy3OfeuQ"
     6) "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc5OTkxMjIsImlhdCI6MTcwNjc4OTUyMn0.XjxDeZyCsWLLsO7-072B47WEFzGiQeuNCOJV-mUSvTQ"
     7) "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc5OTU2NzgsImlhdCI6MTcwNjc4NjA3OH0.QpMcjtkizOh6UAiR5wE7aqORiPff_LIFOtZZTC3AEaM"
     8) "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc5OTYxMTAsImlhdCI6MTcwNjc4NjUxMH0.HttjGP7mFUq2INohAcLYMHYvHxqLX3faX6hNcBnmKBI"
     9) "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc5OTgwOTQsImlhdCI6MTcwNjc4ODQ5NH0.crcu3sfA-xRNUtMkXnNO8Dh9_FMGafZOnDsqgDalQzs"
    10) "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDc5OTYxMTUsImlhdCI6MTcwNjc4NjUxNX0.v8qzZ7BN5bhioCn2hFn-AgIhUplQGp75fjq50nW9FoQ"
    ```

- 테스트: `만료된 access token`을 사이트의 쿠키에 저장하여 새로고침

```bash
# 만료된 토큰
Bearer%20eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwiYXV0aCI6Iuq0gOumrOyekCIsImV4cCI6MTc
wNjY5MjYyNSwiaWF0IjoxNzA2Njg5MDI1fQ.01FI_Sdve4FzST8xgR7vtJCZYFPHlDbTYi72eaPXUm8
```

- 쿠키를 확인했을 때 `access token`이 새로 발급된 것을 확인 가능

```bash
Bearer%20eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwiYXV0aCI6IlJPTEVfQURNSU4iLCJleHAiOjE3
MDY3OTM5NDUsImlhdCI6MTcwNjc5MDM0NX0.mzW4oBI0gIx3hHELmVYWZWjLcf8p_8EAaYeq0hebcKU
```

</details>

## Test

### Jacoco 테스트 문서

<img width="616" alt="jacoco" src="https://github.com/Sappun-Walk/Sappun/assets/99391320/de730c22-8621-4709-8f3e-fcdd31f02417">
