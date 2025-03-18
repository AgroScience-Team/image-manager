#noinspection NonAsciiCharacters
Feature: ui-try-to-get-photos-metainfo

  Background:
    Given The following indexes exist:
      | ndvi |
      | dvi  |
    And Db table "photos" contains data:
      | id                                   | contourId                            | date                      | extension |
      | 00000000-0000-0000-0000-000000000001 | 00000000-0000-0000-0000-000000000001 | 2024-08-02T12:00:00+00:00 | tiff      |
      | 00000000-0000-0000-0000-000000000002 | 00000000-0000-0000-0000-000000000001 | 2024-08-03T12:00:00+00:00 | tiff      |
    And Db table "workers_results" contains data:
      | id                                   | jobId                                | workerName | photoId                              | type | path      | success |
      | 00000000-0000-0000-0000-000000000001 | 00000000-0000-0000-0000-000000000001 | ndvi       | 00000000-0000-0000-0000-000000000001 | ndvi | some/path | true    |
      | 00000000-0000-0000-0000-000000000002 | 00000000-0000-0000-0000-000000000002 | ndvi       | 00000000-0000-0000-0000-000000000002 | ndvi | some/path | true    |
      | 00000000-0000-0000-0000-000000000003 | 00000000-0000-0000-0000-000000000003 | dvi        | 00000000-0000-0000-0000-000000000001 | dvi  |           | false   |
      | 00000000-0000-0000-0000-000000000004 | 00000000-0000-0000-0000-000000000004 | dvi        | 00000000-0000-0000-0000-000000000002 | dvi  |           | false   |
    And Kafka topic "agro.audit.messages" is clear

  Scenario: UI try to get photos metainfo
    When UI send get request with url: "http://localhost:8083/image-manager/contours/00000000-0000-0000-0000-000000000001/photos?from=2000-01-01T00:00:00Z&to=2024-12-12T00:00:00Z"
    Then UI receive response
    """
    [
      {
        "id": "00000000-0000-0000-0000-000000000002",
        "contourId": "00000000-0000-0000-0000-000000000001",
        "date": "2024-08-03T12:00:00Z",
        "extension": "tiff",
        "workerResults": [
          {
            "id": "00000000-0000-0000-0000-000000000002",
            "photoId": "00000000-0000-0000-0000-000000000002",
            "jobId": "00000000-0000-0000-0000-000000000002",
            "workerName": "ndvi",
            "type": "ndvi",
            "path": "some/path",
            "success": true
          },
          {
            "id": "00000000-0000-0000-0000-000000000004",
            "photoId": "00000000-0000-0000-0000-000000000002",
            "jobId": "00000000-0000-0000-0000-000000000004",
            "workerName": "dvi",
            "type": "dvi",
            "path": null,
            "success": false
          }
        ]
      },
      {
        "id": "00000000-0000-0000-0000-000000000001",
        "contourId": "00000000-0000-0000-0000-000000000001",
        "date": "2024-08-02T12:00:00Z",
        "extension": "tiff",
        "workerResults": [
          {
            "id": "00000000-0000-0000-0000-000000000001",
            "photoId": "00000000-0000-0000-0000-000000000001",
            "jobId": "00000000-0000-0000-0000-000000000001",
            "workerName": "ndvi",
            "type": "ndvi",
            "path": "some/path",
            "success": true
          },
          {
            "id": "00000000-0000-0000-0000-000000000003",
            "photoId": "00000000-0000-0000-0000-000000000001",
            "jobId": "00000000-0000-0000-0000-000000000003",
            "workerName": "dvi",
            "type": "dvi",
            "path": null,
            "success": false
          }
        ]
      }
    ]
    """

    Then Kafka topic "agro.audit.messages" receives audit message with key "SUCCESS" in 3000 millis