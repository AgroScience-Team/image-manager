#noinspection NonAsciiCharacters
Feature: workers-send-results

  Scenario: Receive and save processed photos metainfo
    Given Table "photos" is empty
    And Table "job_uuid_registry" is empty
    And Db table "photos" contains data:
      | id                                   | contourId                            | date       | extension |
      | 00000000-0000-0000-0000-000000000001 | 00000000-0000-0000-0000-000000000001 | 2024-08-02 | tiff      |
    And Table "workers_results" is empty
    And The following indexes exist:
      | ndvi |
      | dvi  |

    When External system send message in topic "agro.workers.results"
    """
    {
      "photoId": "00000000-0000-0000-0000-000000000001",
      "jobId": "00000000-0000-0000-0000-000000000001",
      "workerName": "ndvi",
      "path": "some/path",
      "success": "true",
      "type": "ndvi"
    }
    """

    Then Table "workers_results" receive data in 3000 millis
      | photo_id                             | job_id                               | worker_name | type | success | path      |
      | 00000000-0000-0000-0000-000000000001 | 00000000-0000-0000-0000-000000000001 | ndvi        | ndvi | true    | some/path |

    Then Kafka topic "agro.audit.messages" receives audit message with key "SUCCESS" in 3000 millis

  Scenario: Receive duplicate message and audit error
    When External system send message in topic "agro.workers.results"
    """
    {
      "photoId": "00000000-0000-0000-0000-000000000001",
      "jobId": ""00000000-0000-0000-0000-000000000001",
      "workerName": "ndvi",
      "path": "some/path",
      "success": "true",
      "type": "ndvi"
    }
    """

    Then Kafka topic "agro.audit.messages" receives audit message with key "ERROR" in 10000 millis
