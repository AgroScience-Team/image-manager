# Ui-try-to-get-photos-metainfo
1) В сервис поступает get запрос формата:  
/image-manager/contours/{contourId}/photos?from=LocalDate&to=LocalDate  
2) На UI приходит ответ, содержащий все фото и его подсчитанные индексы  
удовлетворяющие условию поиска. Тело ответа строится как список некоторого [класса](../src/main/java/com/github/agroscienceteam/imagemanager/domain/photo/PhotoWithWorkersResults.java)

## [Тест](../src/test/resources/features/ui-try-to-get-photos-metainfo.feature)
Делается запрос к сервису, ожидая необходимый ответ.