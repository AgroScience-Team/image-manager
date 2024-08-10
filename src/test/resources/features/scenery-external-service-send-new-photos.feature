#noinspection NonAsciiCharacters
Feature: scenery-external-service-send-new-photos

  Background:
    Given Table "photos" is empty
    And Kafka topic "ndvi" is clear
    And Table "photos_indexes" is empty
    And Db table "indexes" contains data:
      | indexName |
      | ndvi      |

  Scenario: Receive and distribute info for workers
    When External system send message in topic "new.photos"
    """
    {
      "photoId": "f128f7c6-1972-4a15-99b7-bca1e1675fdf",
      "fieldId": 0,
      "photoDate": "2024-08-02",
      "photoExtension": "TIFF"
    }
    """

    Then Kafka topic "ndvi" receives message with key "TIFF" in 3000 millis
    """
    f128f7c6-1972-4a15-99b7-bca1e1675fdf
    """

    Then Table "photos" receive data in 1000 millis
      | photoId                              | fieldId | photoDate  | photoExtension |
      | f128f7c6-1972-4a15-99b7-bca1e1675fdf | 0       | 2024-08-02 | TIFF           |

    Then Kafka topic "agroscienceteam.audit.messages" receives audit message with key "SUCCESS" in 3000 millis

  Scenario: Receives incorrect message and audit topic receives any error message
    When External system send message in topic "new.photos"
    """
    {
      "fieldId": 0,
      "photoDate": "2024-08-02",
      "photoExtension": "TIFF"
    }
    """

    Then Kafka topic "agroscienceteam.audit.messages" receives audit message with key "ERROR" in 3000 millis
