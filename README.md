# Life-Simulator  ( Just for the final project of our homework )

Features 

1.LogInSystem 登入系統

輸入暱稱：使用者一開始只要需要輸入暱稱即可登入。

刪除帳號：使用者可以刪除該暱稱的帳號。

2. Memo　筆記本

新增：使用者可以新增項目供記載事項且選擇分類或新增分類，並且會記錄下新增日期。

刪除：點選按鈕刪除該項目。

搜尋：根據分類篩選顯示項目。


3. Schedule　行程表

行程表：提供ListView，至08:00AM開始列表，每小時為一個Item。

記錄行程：每個Item供使用者點選，並且輸入行程內容。

時段：讀取系統時間在相對應的時間Item上亮出背景。

刪除：一個按鍵清除所有行程。

儲存：供使用者儲存行程。

載入行程：儲存的項目供使用者載入使用。

是否通知：使用者決定是否要接收推播來預告下個行程。


4.CheckList　待辦事項

儲存待辦事項：為一個多選的ListView，各個Item記載事項以及Deadline。

新增：使用者新增一事項以及Deadline。

刪除：提供多選CheckBox一併刪除多個項目。

是否通知：使用者決定是否要接收推播來預告下個行程。


Regulations

1.@+id/命名規則： 命名規則為適宜名稱+該Activity名稱，筆記本的新增按鈕: @+id/inputBTN_Memo

2.常數規則：當需要傳入常數作為參數來連結各個功能，則先需要定義為類別變數public static final。


Progress


水: 

11/7 Log In System finished

11/10 Start Developing Memo

11/20 Start Developing Schedule


邱:

CheckList先交給你惹，慢慢鑽研xDD

When finished your task, you can add some new features like 倒數日

