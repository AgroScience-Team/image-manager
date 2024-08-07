#noinspection NonAsciiCharacters
Feature: ui-try-to-get-photos-metainfo

  Background:
    Given Db table "indexes" contains data:
      | indexName |
      | ndvi      |
      | dvi       |
    And Db table "photos" contains data:
      | photoId                              | fieldId | photoDate  | photoExtension |
      | f128f7c6-1972-4a15-99b7-bca1e1675fdf | 0       | 2024-08-02 | TIFF           |
      | f128f6c6-1972-4a15-99b7-bca1e1675fdf | 0       | 2024-08-03 | TIFF           |
    And Db table "photos_indexes" contains data:
      | photoId                              | indexName | result  | extension |
      | f128f7c6-1972-4a15-99b7-bca1e1675fdf | ndvi      | success | TIFF      |
      | f128f6c6-1972-4a15-99b7-bca1e1675fdf | ndvi      | success | TIFF      |
      | f128f7c6-1972-4a15-99b7-bca1e1675fdf | dvi       | fail    | TIFF      |
      | f128f6c6-1972-4a15-99b7-bca1e1675fdf | dvi       | fail    | TIFF      |

  Scenario: UI try to get photos metainfo
    When UI send get request with url: "http://localhost:8083/image-manager/0?from=2000-01-01&to=2024-12-12"
    Then UI receive response
    """
    [
      {
        "photo": {
          "photoId":"f128f6c6-1972-4a15-99b7-bca1e1675fdf",
          "fieldId":0,
          "photoDate":"2024-08-03",
          "photoExtension":"TIFF"
        },
        "processedPhotos": [
          {
            "indexName":"ndvi",
            "photoId":"f128f6c6-1972-4a15-99b7-bca1e1675fdf",
            "result":"success",
            "extension":"TIFF"
          },
          {
            "indexName":"dvi",
            "photoId":"f128f6c6-1972-4a15-99b7-bca1e1675fdf",
            "result":"fail",
            "extension":"TIFF"
          }
        ]
      },
      {
        "photo": {
          "photoId":"f128f7c6-1972-4a15-99b7-bca1e1675fdf",
          "fieldId":0,
          "photoDate":"2024-08-02",
          "photoExtension":"TIFF"
        },
        "processedPhotos": [
          {
            "indexName":"ndvi",
            "photoId":"f128f7c6-1972-4a15-99b7-bca1e1675fdf",
            "result":"success",
            "extension":"TIFF"
          },
          {
            "indexName":"dvi",
            "photoId":"f128f7c6-1972-4a15-99b7-bca1e1675fdf",
            "result":"fail",
            "extension":"TIFF"
          }
        ]
      }
    ]
    """