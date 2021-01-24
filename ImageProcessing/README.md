# MyImage 

## Files
* Crop.java
  * 這個是用來invoke *ImageGallery* 的
* *MyImage*
  * 這個是用來打包我們的class的package directory，沒有特別意義
* *MyImage/ImageGallery.java*
  * 這個是我們主要處理大圖、儲存小圖的class
  
## ImageGallery class
* `partition(BufferedImage image)`
  * 處理大圖割成小圖，同時initialize小圖的status
* `adminPartitionsUpdate(String filename)`
  * 讓我們自訂那些沒有要被使用者印出來的圖
  * 傳入的是filename，該file裡面用 \[x座標\] \[y座標\] 格式存那些小圖的座標（詳細可看1.txt）
  