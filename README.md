# MadCamp 2021 Winter: "Ori59 Application"
### Contact, Image Gallery, 오구 with Weather

## Team Heading
##### [김재우](https://github.com/jjwwk0), [한수민](https://github.com/Hans-0101)

## Tab1: Contact

#### 연락처 관리 기능

  - 연락처 로드
      - Content Proivder를 통해 기기 내 연락처 정보를 불러옵니다
      - 불러온 연락처 데이터를 Recycler View를 통해 화면에 출력합니다.
  - 연락처 추가
      - 우측 하단의 "+" 버튼을 누르면, 연락처 추가화면으로 이동합니다.
      - 이름과 전화번호를 입력하여 새로운 연락처를 추가할 수 있습니다.
      - 추가한 연락처는 자동으로 기기 내 연락처 데이터와 연동됩니다.  
#### 스와이프 시 통화 연결 기능     
  - 특정 연락처를 좌측으로 swipe 하면, call 버튼이 나타납니다.
  - call 버튼을 눌러, 해당 연락처로 전화를 연결할 수 있습니다.


## Tab2: Image Gallery
#### 기기 내 이미지 업로드
  - Step1. "Press to load image" 버튼을 누릅니다.
  - Step2. 기기 내 갤러리 이미지를 선택하면, 선택된 이미지가 어플리케이션에 업로드됩니다.
#### Focus On/Off 기능
  - Focus On : Focused View(가로 scroll)
    - LinearLayoutManager를 통해 한 이미지를 집중적으로 불 수 있습니다.
  - Focus Off : Grid View(세로 scroll)
    - GridLayoutManager를 통해 여러 이미지를 한 눈에 볼 수 있습니다.
#### 이미지 확대/축소
  - Focus On 모드 시 작동되며, 한 이미지를 대상으로 Zoom In/Out 기능을 실행합니다.
    
## Tab3: 오구 with Weather
#### 기기 Ram Usage Percentage
  - 기기 내 램 사용량을 측정해 숫자로 직접 확인할 수 있습니다.
  - 램 사용량 크기에 비례해 오구(캐릭터)의 속도가 변경됩니다.
 <img src="https://user-images.githubusercontent.com/78015565/148020133-94ec9e7b-1157-4cf9-931e-405c54a0a55d.gif" width="200" height="400" align="cneter">

#### 날씨 정보
  - 현위치에 따른 기온 및 간단한 날씨(맑음, 흐림, 비, 천둥번개, 눈)를 아이콘 및 텍스트로 확인할 수 있습니다.
#### Light/Dark Mode
  - 실시간 시간을 반영해 오전은 Light Mode, 오후는 Dark Mode로 자동 변환됩니다.
#### 새로고침 기능
  - 새로고침 버튼 클릭 시 램 사용량, 날씨, 시간 정보를 실시간으로 업데이트 합니다.
#### 오구 반응
  - 오구를 터치하면 반응합니다.

## Required Permission
##### WRITE_CONTACTS, READ_CONTACTS, READ_EXTERNAL_STORAGE, CALL_PHONE, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, INTERNET, ACCESS_NETWORK_STATE
