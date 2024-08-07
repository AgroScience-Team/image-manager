# Сервис менеджер растрового калькулятора
Осуществляет распределение задач, хранение метаинформации снимков,  
сбор результатов обработки и предоставляет метаинфоормацию для UI.

# Сценарии
1) [Внешняя система отправляет сообщения в топик new.photos](docs/External-service-send-new-photos.md)
2) [Воркеры отправляют результат своей деятельности в топик workers.results](docs/Workers-send-results.md)

# Важное
При последующем добавлении нового индекса в таблицу [indexes](src/main/resources/liquibase/1.0.0/2024-07-31_01-create-tables.sql)  
нужно продублировать его в списке класса [Initializer](src/test/java/com/github/agroscienceteam/imagemanager/Initializer.java). 

