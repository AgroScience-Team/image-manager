# Workers-send-results
В топик workers.results приходят сообщения вида:
```
key: string
{
    "photoId": "UUID",
    "result": "string"
}
```
Example
```
key: ndvi
{
	"photoId": "f938f7c6-1972-4a15-99b7-bca1e1675fdf",
	"result": "success"
}
```

То есть key содержит название индекса/топика,  
а value несёт нужную метаинфу

1) Сообщение читается слушателем событий [WorkersResultsListener](../src/main/java/com/github/agroscienceteam/imagemanager/infra/input/WorkersResultsListener.java)
2) В базу данных сохраняется сущность [ProcessedPhoto](../src/main/java/com/github/agroscienceteam/imagemanager/domain/photo/ProcessedPhoto.java)

## [Тест](../src/test/resources/features/workers-send-results.feature)
Получает сообщения, проверяет их успешность сохранения в бд.
