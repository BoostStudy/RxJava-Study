### 리액티브 프로그래밍

- 이벤트나 데이터가 극단적으로 증가하여 대용량 데이터의 저장, 업데이트, 실시간 반영을 효율적으로 해결할 방법으로 주목되었다
- 2013 년에 마이크로소프트에서 프로그래밍 모델을 비동기 및 이벤트 중심의 데이터 구조로 재정의한 Reactive Extensions 를 자바 진영으로 가져온것
    - NetFlix가 공개한 라이브러리

### Reactive Streams 규칙

- onSubscribe 는 해당 구독에서 한번 만 발생한다
- 통지는 순차적으로 이루어진다
- null을 통지하지 않는다
- Publisher의 처리는 onComplete 나 onError 를 통지해 종료한다

### RxJava 기본 구조

- 데이터를 만들고 통지하는 **생산자**
- 통지된 데이터를 받아 처리하는 **소비자**

### 생산자 - 소비자

#### Flowable - Subscriber
- Reactive Streams 지원
    - 기본적인 매커니즘이 Reactive Streams 와 같다
    - Subscription 으로 **데이터 개수 요청**과 **구독 해지**
- Flowable
    - Reactive Streams 의 생산자인 Publisher를 구현
- Subscriver
    - Reactive Streams 의 클래스

#### Observable - Observer
- RxJava 2.x 버전
- Reactive Streams 미지원
    - 기본적인 매커니즘은 Flowable - Subscriber 와 거의 같다
- 데이터 개수를 제어하는 **배압 기능이 없다**
    - 데이터 개수를 요청하지 않음
    - 구독 해지 메서드가 있는 인터페이스 **Disposable** 을 사용

### 연산자

- 소비자에게 데이터를 통지하기 전에 불필요한 데이터를 삭제한다
- 소비자가 사용하기 쉽게 데이터를 변환한다
- 연산자가 설정된 시점이 아닌 **데이터가 통지 받는 시점**에 처리가 실행된다
- 함수형 프로그래밍의 영향을 받아 **Side Effect** 를 피하는게 좋다
    - 체인 도중이 아닌 소비자 측에서 하는게 좋다
    - 여러 스레드에서 공유하는 객체가 없어져 쓰레드 안전을 보장할 수 있다

### 비동기 처리

- 개발자가 직접 스레드를 관리할 필요 없이 처리 목적에 맞춰 **스케줄러**를 설정
    - 데이터를 통지하는 부분과 처리하는 부분에 지정할 수 있다

### Cold 생산자와 Hot 생산자

#### Cold 생산자

- 1개의 소비자와 구독 관계를 맺는다
- 새로운 구독이 생기면 데이터를 처음부터 받는다
- RxJava 의 기본 생산자는 Cold

#### Hot 생산자

- 여러 소비자와 구독 관계를 맺는다
- 구독한 시점부터 생산된 데이터를 받게 된다

#### Hot 생산자 생성하기

- Cold 에서 Hot 으로 변환하는 메서드를 호출
- Processor 와 Subject 를 생성
