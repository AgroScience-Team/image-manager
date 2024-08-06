# External-service-send-new-photos
В топик new.photos приходят сообщения вида:
```
{
    "photoId": "UUID",
    "fieldId": Long,
    "photoDate": "LocalDate",
    "photoExtension": "string"
}
```
Example
```
{
	"photoId": "f938f7c6-1972-4a15-99b7-bca1e1675fdf",
	"fieldId": 0,
	"photoDate": "2024-08-02",
	"photoExtension": "UI"
}
```
1) Сообщение читается слушателем событий [NewPhotoListener](../src/main/java/com/github/agroscienceteam/imagemanager/infra/input/NewPhotoListener.java)
2) В базу данных сохраняется сущность [Phtoto](../src/main/java/com/github/agroscienceteam/imagemanager/domain/Photo.java)
3) Дистрибьютор [PhotoDistributorImpl](../src/main/java/com/github/agroscienceteam/imagemanager/domain/PhotoSaverImpl.java)
   распространяет информацию о полученном снимке воркерам в топики из таблицы [indexes](../src/main/resources/liquibase/1.0.0/2024-07-31_01-create-tables.sql)

Пример сообщения воркерам
```
   key: photoExtension
   value: photoId
```

## [Тест](../src/test/resources/features/scenery-external-service-send-new-photos.feature)
Получает сообщения, проверяет их успешность сохранения в бд и успешную отправку воркерам. 