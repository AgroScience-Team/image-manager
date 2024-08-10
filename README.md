# Сервис менеджер растрового калькулятора
Осуществляет распределение задач, хранение метаинформации снимков,  
сбор результатов обработки и предоставляет метаинфоормацию для UI.

# Сценарии
1) [Внешняя система отправляет сообщения в топик new.photos](docs/External-service-send-new-photos.md)
2) [Воркеры отправляют результат своей деятельности в топик workers.results](docs/Workers-send-results.md)
3) [UI делает запрос на получение метаинфорормации снимков](docs/Ui-try-to-get-photos-metainfo.md)

Во всех сценариях реализован аудит. В случае удачи отправляется сообщение с ключём SUCCESS и телом [AuditEntity](src/main/java/com/github/agroscienceteam/imagemanager/domain/audition/AuditEntity.java)  
или [AuditEntityWithResult](src/main/java/com/github/agroscienceteam/imagemanager/domain/audition/AuditEntityWithResult.java).  
В случае ошибки идёт аудит в сключём ERROR и телом [ErrorAudit](src/main/java/com/github/agroscienceteam/imagemanager/domain/audition/ErrorAudit.java).  
В случае возникновения ошибки во время аудита предусмотрен [FatalAudit](src/main/java/com/github/agroscienceteam/imagemanager/domain/audition/FatalAudit.java)
Топик для аудита "agroscienceteam.audit.messages".  
При ошибкам в слушателях помимо аудита идёт запись сообщения, которое вызвало ошибку, в топик вида исходныйТопик.dlq

# Важное
При последующем добавлении нового индекса в таблицу [indexes](src/main/resources/liquibase/1.0.0/2024-07-31_01-create-tables.sql)  
нужно продублировать его в списке класса [Initializer](src/test/java/com/github/agroscienceteam/imagemanager/Initializer.java). 

