#noinspection NonAsciiCharacters
Feature: scenery-external-service-send-new-photos

  Background:
    Given Table "photos" is empty
    And Kafka topic "ndvi" is clear

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

    Then Workers kafka topics receives message with key "TIFF" in 3000 millis
    """
    f128f7c6-1972-4a15-99b7-bca1e1675fdf
    """

    And Table "photos" contains data
    """
    [
      {
        "photoId": "f128f7c6-1972-4a15-99b7-bca1e1675fdf",
        "fieldId": 0,
        "photoDate": "2024-08-02",
        "photoExtension": "TIFF"
      }
    ]
    """
