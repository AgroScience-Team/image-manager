#noinspection NonAsciiCharacters
Feature: workers-send-results

  Background:
    Given Table "photos" is empty
    And Db table "photos" contains data:
      | photoId                              | fieldId | photoDate  | photoExtension |
      | f128f7c6-1972-4a15-99b7-bca1e1675fdf | 0       | 2024-08-02 | TIFF           |
    And Table "photos_indexes" is empty

  Scenario: Receive and save processed photos metainfo
    When External system send message in topic "workers.results" with key "ndvi"
    """
    {
      "photoId": "f128f7c6-1972-4a15-99b7-bca1e1675fdf",
      "result": "success"
    }
    """

    Then Table "photos_indexes" receive data in 3000 millis
      | photoId                              | indexName | result  |
      | f128f7c6-1972-4a15-99b7-bca1e1675fdf | ndvi      | success |
