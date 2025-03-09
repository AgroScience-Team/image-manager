# External-service-send-new-photos
В топик agro.new.photos приходят сообщения вида:
```
{
    "id": "UUID",
    "contourId": UUID,
    "date": "LocalDate",
    "extension": "string"
}
```
Example
```
{
	"id": "f938f7c6-1972-4a15-99b7-bca1e1675fdf",
	"contourId": "f938f7c6-1972-4a15-99b7-bca1e1675fdf",
	"date": "2024-08-02",
	"extension": "tiff"
}
```
1) Сообщение читается слушателем событий [NewPhotoListener](../src/main/java/com/github/agroscienceteam/imagemanager/infra/input/NewPhotoListener.java)
2) В базу данных сохраняется сущность [Phtoto](../src/main/java/com/github/agroscienceteam/imagemanager/domain/photo/Photo.java)
3) Дистрибьютор [PhotoDistributorImpl](../src/main/java/com/github/agroscienceteam/imagemanager/domain/photo/PhotoSaverImpl.java)
   распространяет информацию о полученном снимке воркерам в топики.

Пример сообщения воркерам
```
{
    "jobId": "44d6872d-22ae-4ac8-9fbc-b3fc40adcc90",
    "photoId": "ec91e53d-d520-44b1-98cf-ff05bad15434",
    "extension": "tiff"
}
```

## [Тест](../src/test/resources/features/scenery-external-service-send-new-photos.feature)
Получает сообщения, проверяет их успешность сохранения в бд и успешную отправку воркерам. 