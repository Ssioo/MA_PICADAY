# MA_PICADAY
pick a day

하루를 시작하다 Pick up day
바쁜 매일매일을 살아 가고 있는 우리,
오늘 무엇을 해야 하는지 이번 주 무엇을 시작해야 하는지
이번달 무엇을 끝내야 하는지 잊어버리진 않았나요?

TEAM12 - 정제일, 조연우

02 CONCEPT
 - 1. 직관적인 Daily 일정관리
 - 2. 하루 중 자투리 시간 표시

03 PAGE
 - HOME : WEEKLY, DAILY(MAIN), MONTHLY
  - WEEKLY : 주간일정을 관리합니다.하단엔 이번주 사용할 수 있는 남은 시간을 보여줍니다.
  - DAILY(MAIN) : 오늘 일정을 관리합니다. 간단한 픽토그램과 함께 상세한 스케쥴링으로 직관성을 추구합니다. 하단엔 오늘 사용할 수 있는 남은 시간을 보여줍니다.
  - MONTHLY : 월간일정을 관리합니다. 하단엔 이번 달 사용할 수 있는 남은 시간을 보여줍니다.
 - ADDING
  - ON WEEK : 주간 일정을 추가합니다. 월간 일정과 동일한 방식으로 추가하되, 설정한 시작시간과 종료시간이 즉각적으로 시간표에 워터마크로 피드백됩니다.
  - ON DAY : 오늘 일정을 추가합니다. 직접입력을 지원합니다. 간편일정 추가에 최적화합니다.
  - ON MONTH : 월간일정을 추가합니다. 텍스트 직접 입력만을 지원합니다. 상세 일정 추가에 최적화합니다.
 - SETTINGS
  - 앱의 전반적인 부분을 조정할 수 있습니다.

04 Detail
 1) Layout
    Layout은 크게
    - MainAcitivy Content
    - Navigation Bar
    - Fragment - Daily, Monthly, Weekly - Main
    - Fragment - Daily, Monthly, Weekly - Add
    - Fragment - Daily, Monthly, Weekly - list형
    - Custom - calender
    - Other - App bar, Mypage, setting, etc..
    로 구성되어 있습니다.

 2) Fragment
    이번 프로젝트에서 가장 많이 사용한 클래스로, Main page 내의 스케줄을 명시하고 있는 페이지는 모두 Fragment로 구성되어 있습니다.
    Fragment는 크게
    - AddActiviy_Fragment 패키지 - 패키지 내 fragment를 통해 각 일정을 기입하고, 'v'버튼을 클릭 시 DB 상에 기입한 일정이 저장됩니다.
        - Add_fragment는 각 daily, monthly, weekly로 구성되어 있으며, 오직 monthly를 통해서 상세한 일정 기입이 가능합니다.
    - MainActivity_Fragment 패키지- 패키지 내 fragment는 크게 main과 list로 구분됩니다.
        - main_fragment는 각 정보를 표현할 수 있는 세 가지의 화면 구성을 Canvas와 bitmap을 활용해 구현합니다.
        - list 형의 경우 일정 정보를 시간 순서에 따라 확인할 수 있으며, Custom ListView를 통해 각 페이지 내 정보들을 빠르게 삭제할 수 있으며, 클릭 시  EditActivity로 이동합니다.

 3) Activity
    - Activity는 크게 Main과 Setting, EditActivity로 구분할 수 있습니다.

 4) Database
    - SQLite를 사용하여, table 내 총 10 column의 data를 저장합니다.
    - 저장 Data를 바탕으로 Layout을 구현하고, 각 정보들을 연동하여 수정 및 삭제가 가능합니다.
