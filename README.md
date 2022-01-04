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

<table border="0" cellpadding="0" cellspacing="0" align="center">
    <tr style="text-align: center;">
      <td style="text-align: center;"><img src="https://user-images.githubusercontent.com/96403983/148029930-3d14a78a-f337-484d-9687-0764e09136fd.gif" width="200" height="400"></td>
      <td style="text-align: center;"><img src="https://user-images.githubusercontent.com/96403983/148029923-352f85c4-011a-4508-802f-5d99227809d9.gif" width="200" height="400"></td>
      <td style="text-align: center;"><img src="https://user-images.githubusercontent.com/96403983/148029905-c36b01fc-553f-4f0a-9696-e16b1574f4c4.gif" width="200" height="400"></td>
    </tr>
</table>


## Tab2: Image Gallery
#### 기기 내 이미지 업로드
  - Step1. "Press to load image" 버튼을 누릅니다.
  - Step2. 기기 내 갤러리 이미지를 선택하면, 선택된 이미지가 어플리케이션에 업로드됩니다.
<p align="center">
  <img src="https://user-images.githubusercontent.com/78015565/148030273-5a82aa90-5c94-4b11-bc36-e68bab0e5c5b.gif" width="200" height="400"/> </p>

#### Focus On/Off 기능
  - Focus On : Focused View(가로 scroll)
    - LinearLayoutManager를 통해 한 이미지를 집중적으로 불 수 있습니다.
  - Focus Off : Grid View(세로 scroll)
    - GridLayoutManager를 통해 여러 이미지를 한 눈에 볼 수 있습니다.

#### 이미지 확대/축소
  - Focus On 모드 시 작동되며, 한 이미지를 대상으로 Zoom In/Out 기능을 실행합니다.
<p align="center">
  <img src="https://user-images.githubusercontent.com/78015565/148030979-c65b5e62-d7af-4973-9f94-09827ab574fe.gif" width="200" height="400"/> </p>


## Tab3: 오구 with Weather
#### 기기 Ram Usage Percentage
  - 기기 내 램 사용량을 측정해 숫자로 직접 확인할 수 있습니다.
  - 램 사용량 크기에 비례해 오구(캐릭터)의 속도가 변경됩니다.
<p align="center">
  <img src="https://user-images.githubusercontent.com/78015565/148020133-94ec9e7b-1157-4cf9-931e-405c54a0a55d.gif" width="200" height="400"/> </p>

#### 날씨 정보
  - 현위치에 따른 기온 및 간단한 날씨(맑음, 흐림, 눈, 천둥번개, 비)를 아이콘 및 텍스트로 확인할 수 있습니다.
<div>
  <table border=0 align="center" style="border-collapse: collapse;">
      <tr style="text-align: center;">
        <td style="text-align: center;"><img src="https://user-images.githubusercontent.com/78015565/148025045-1e865b40-ac93-4e85-9a29-b1345e5716eb.jpg" width="200" height="400"/></td>
        <td style="text-align: center;"><img src="https://user-images.githubusercontent.com/78015565/148022237-20701f4f-984c-40b3-aded-1770b01a2c1a.jpg" width="200" height="400"/></td>
      </tr>
  </table>
</div>

<table border="0" cellpadding="0" cellspacing="0" align="center">
    <tr style="text-align: center;">
      <td style="text-align: center;"><img src="https://user-images.githubusercontent.com/78015565/148022355-962022d1-f56c-453f-8b5c-0aa869170ea9.jpg" width="200" height="400"/></td>
      <td style="text-align: center;"><img src="https://user-images.githubusercontent.com/78015565/148023405-3cd4698b-5014-4270-bc7c-8245dc05b9bf.jpg" width="200" height="400"/></td>
      <td style="text-align: center;"><img src="https://user-images.githubusercontent.com/78015565/148022299-dfd6ef82-f92d-41c0-9340-8db7ebf604bd.jpg" width="200" height="400"/></td>
    </tr>
</table>


#### Light/Dark Mode
  - 실시간 시간을 반영해 오전은 Light Mode, 오후는 Dark Mode로 자동 변환됩니다.
<table border="0" cellpadding="0" cellspacing="0" align="center"">
	<tr style="text-align: center;">
      <td style="text-align: center;"><img src="https://user-images.githubusercontent.com/78015565/148025045-1e865b40-ac93-4e85-9a29-b1345e5716eb.jpg" width="200" height="400"/></td> 
      <td style="text-align: center;"><img src="https://user-images.githubusercontent.com/78015565/148023257-c8d88e92-c36f-4e4b-a194-eca8ff85aede.jpg" width="200" height="400"/></td>
    </tr>
</table>

#### 새로고침 기능
  - 새로고침 버튼 클릭 시 램 사용량, 날씨, 시간 정보를 실시간으로 업데이트 합니다.

#### 오구 반응
  - 오구를 터치하면 반응합니다.
<p align="center">
  <img src="https://user-images.githubusercontent.com/78015565/148029869-9702f7ca-27f6-45e7-bd24-fe7a7d01e98f.gif" width="200" height="400"/> </p>

## Required Permission
##### WRITE_CONTACTS, READ_CONTACTS, READ_EXTERNAL_STORAGE, CALL_PHONE, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, INTERNET, ACCESS_NETWORK_STATE
