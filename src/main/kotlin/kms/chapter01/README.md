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

### ConnectableFlowable/ ConnectableObservable
- **Hot 생산자**
- Cold 를 Hot으로 변환하는 연산자로 생성할 수 있다
- subscribe 를 호출해도 처리를 시작하지 않고 **connect 를 호출해야 처리 시작**
- 처음 부터 여러 구독자에게 데이터를 통지할 수 있음
- refCount 를 이용해서 일반 Flowable/Observable 을 반환 할 수 있음
  - 같은 timeline 에서 생성되는 데이터 통지
  - Connectable 이 아니기 때문에 connect 가 아닌 subscribe 로 데이터 처리 시작

### Flowable/Observable 을 Cold 에서 Hot 으로 변환

- publish()
- replay() / replay(int buffsize) / replay(long time, TimeUnit unit)
- share()

#### publish

- Cold 생산자에서 Connectable 을 생성하는 연산자
- 처리를 시작한 뒤에 구독하면 **구독한 이후에 생성된 데이터 부터 통지**

#### replay

- Cold 생산자에서 Connectable 을 생성하는 연산자
- 통지한 데이터를 캐시하여 처리를 시작한 뒤에 구독하면 캐시된 데이터를 먼저 통지
- 인자가 없으면 **모든 데이터를 캐시**

#### share

- 여러 소비자가 구독할 수 있는 Flowable/Observable(Hot) 을 생성
- **Connectable 을 생성하지 않는다**
- 실질적으로는 flowable.publish().refCount() 와 같다

>[!note] 소비자의 처리 속도가 느린 경우
>- 소비자들이 같은 데이터를 같은 시점에 받지 않을 수 있습니다.
>- 이미 구독하고 있던 소비자는 버퍼에 있는데이터를 통지 받음
>- 새로 구독한 소비자는 최신 데이터를 통지 받음

### Flowable 시퀀스

1. Subscriber 가 Flowable을 구독한다(**Subscribe**)
2. Flowable이 **Subscription**을 생성한다
3. Flowable이 Subscriber에 구독 시작(**onSubscribe**)을 통지하여 Subscription을 전달
4. Subscriber는 Subscription에 데이터를 통지하게 요청
5. Flowable은 데이터를 Subscriber에게 통지
6. Subscriber는 받은 데이터를 처리한다
7. 처리한 후 Subscriber는 Subscription에게 데이터 통지를 요청
8. Flowable은 데이터가 있다면 그 데이터를 Subscriber에게 통지
9. Subscriber는 받은 데이터를 처리한다
10. 처리한 후 Subscriber는 Subscription에게 데이터 통지를 요청
11. Flowable 은 데이터가 없다면 완료(onComplete)를 통지
12. Subscriber는 완료를 처리한다

### BackpressureStrategy 종류

- **BUFFER**
  - 통지할 수 있을 때까지 모든 데이터를 버퍼링 한다
- **DROP**
  - 통지할 수 있을 때까지 새로 생성한 데이터를 삭제한다
- **LATEST**
  - 생성한 최신 데이터만 버퍼링하고 생성할 때마다 버퍼링하는 데이터를 교환한다
- **ERROR**
  - 버퍼 크기를 초과하면 MissingBackPressureException 에러를 통지
- **NONE**
  - 특정 처리를 수행하지 않는다.
  - onBackPressure로 시작하는 메서드로 배압 모드를 설정할 때 사용

### Observable

- Observable 과 Observer 의 관계와 Flowable 과 Subscriber 의 관계의 차이점
  - Reactive Streams 사양을 구현하지 않았다
  - 배압 기능이 없다
    - 배압이 없기 때문에 오버헤드가 적다
    - 성능이 중요한 경우 사용

### Flowable vs Observable

- **Flowable 사용**
  - 대량 데이터(10,000건)를 처리할 때
  - 네트워크 통신이나 데이터베이스 등의  I/O 처리를 할 때
- **Observable 사용**
  - GUI 이벤트
  - 소량 데이터(1,000건)를 처리할 때
  - 데이터 처리가 기본으로 동기 방식이며, 자바 표준의 Stream 대신 사용할 때

#### 사용위치

- **서버**
  - 메모리가 부족하거나 처리 대기 중인 데이터가 쌓이게 되면 서버 전체에 영향
    - 버퍼링의 상한을 정해 **MissingBackpressureException** 을 발생 시킴
- **클라이언트**
  - **MissingBackpressureException** 이 발생하지 않는게 좋음

#### 데이터의 일부만 사용하는 경우

- BackpressureStrategy.DROP 을 설정하면 처리할 수 없는 데이터를 삭제 가능
- Observable 의 throttle 계열의 메서드로 특정 시점의 데이터만을 사용