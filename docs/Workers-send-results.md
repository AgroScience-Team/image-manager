# Workers-send-results
В топик agro.workers.results приходят сообщения вида:
```
key: string
{
    "photoId": "UUID",
    "jobId": "UUID",
    "workerName": "string",
    "path": "string",
    "success": "boolean",
    "type": "string"
}
```
Example
```
key: ndvi
{
	"photoId": "ec91e53d-d520-44b1-98cf-ff05bad15434",
	"jobId": "44d6872d-22ae-4ac8-9fbc-b3fc40adcc90",
	"workerName": "ndvi-worker",
	"path": "result/ec91e53d-d520-44b1-98cf-ff05bad15434-ndvi.tiff",
	"success": true,
	"type": "ndvi"
}
```

1) Сообщение читается слушателем событий [WorkersResultsListener](../src/main/java/com/github/agroscienceteam/imagemanager/infra/input/WorkersResultsListener.java)
2) В базу данных сохраняется сущность [WorkerResult](../src/main/java/com/github/agroscienceteam/imagemanager/domain/photo/WorkerResult.java)

## [Тест](../src/test/resources/features/workers-send-results.feature)
Получает сообщения, проверяет их успешность сохранения в бд.
