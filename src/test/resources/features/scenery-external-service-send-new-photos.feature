#noinspection NonAsciiCharacters
Feature: scenery-external-service-send-new-photos

  Background:
    Given Table "photos" is empty
    And Kafka topic "agro.workers.ndvi" is clear
    And Kafka topic "agro.audit.messages" is clear
    And Table "workers_results" is empty
    And The following indexes exist:
      | ndvi |

  Scenario: Receive and distribute info for workers
    When External system send message in topic "agro.new.photos"
    """
    {
      "photoId": "00000000-0000-0000-0000-000000000001",
      "contourId": "00000000-0000-0000-0000-000000000001",
      "date": "2024-08-02T12:00:00Z",
      "extension": "tiff"
    }
    """

    Then Kafka topic "agro.workers.ndvi" receives message in 1000 millis
    """
    {
      "jobId": "00000000-0000-0000-0000-000000000001",
      "photoId": "00000000-0000-0000-0000-000000000001",
      "extension": "tiff"
    }
    """

    Then Table "photos" receive data in 1000 millis
      | id                                   | contour_id                           | date                 | extension |
      | 00000000-0000-0000-0000-000000000001 | 00000000-0000-0000-0000-000000000001 | 2024-08-02T12:00:00Z | tiff      |

    Then Kafka topic "agro.audit.messages" receives audit message with key "SUCCESS" in 3000 millis

  Scenario: Receives incorrect message and audit topic receives any error message
    When External system send message in topic "agro.new.photos"
    """
    {
      "fieldId": 0,
      "photoDate": "2024-08-02",
      "photoExtension": "tiff"
    }
    """

    Then Kafka topic "agro.audit.messages" receives audit message with key "ERROR" in 3000 millis
