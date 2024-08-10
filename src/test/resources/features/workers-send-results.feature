#noinspection NonAsciiCharacters
Feature: workers-send-results

  Scenario: Receive and save processed photos metainfo
    Given Table "photos" is empty
    And Db table "photos" contains data:
      | photoId                              | fieldId | photoDate  | photoExtension |
      | f128f7c6-1972-4a15-99b7-bca1e1675fdf | 0       | 2024-08-02 | TIFF           |
    And Table "photos_indexes" is empty
    And Db table "indexes" contains data:
      | indexName |
      | ndvi      |
    When External system send message in topic "workers.results" with key "ndvi"
    """
    {
      "photoId": "f128f7c6-1972-4a15-99b7-bca1e1675fdf",
      "result": "success",
      "extension": "TIFF"
    }
    """

    Then Table "photos_indexes" receive data in 3000 millis
      | photoId                              | indexName | result  | extension |
      | f128f7c6-1972-4a15-99b7-bca1e1675fdf | ndvi      | success | TIFF      |

    Then Kafka topic "agroscienceteam.audit.messages" receives audit message with key "SUCCESS" in 3000 millis

  Scenario: Receive duplicate message and audit error
    When External system send message in topic "workers.results" with key "ndvi"
    """
    {
      "photoId": "f128f7c6-1972-4a15-99b7-bca1e1675fdf",
      "result": "success",
      "extension": "TIFF"
    }
    """

    Then Kafka topic "agroscienceteam.audit.messages" receives audit message with key "ERROR" in 10000 millis
