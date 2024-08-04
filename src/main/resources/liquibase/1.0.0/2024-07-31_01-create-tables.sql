-- Таблица для хранения информации о фотографиях
CREATE TABLE photos
(
    photo_id        UUID PRIMARY KEY,
    field_id        BIGINT  NOT NULL,
    photo_date      DATE    NOT NULL,
    photo_extension VARCHAR NOT NULL
);

COMMENT ON TABLE photos IS 'Таблица для хранения информации о фотографиях';
COMMENT ON COLUMN photos.photo_id IS 'Уникальный идентификатор фотографии';
COMMENT ON COLUMN photos.field_id IS 'Идентификатор поля, к которому относится фотография';
COMMENT ON COLUMN photos.photo_date IS 'Дата, когда была сделана фотография';
COMMENT ON COLUMN photos.photo_extension IS 'Расширение снимка';

CREATE TABLE indexes
(
    index_name VARCHAR PRIMARY KEY
);

COMMENT ON TABLE indexes IS 'Таблица для хранения индексов для фотографий';
COMMENT ON COLUMN indexes.index_name IS 'Название индекса';

CREATE TABLE photos_indexes
(
    photo_id   UUID,
    index_name VARCHAR,
    status     VARCHAR,
    CONSTRAINT fk_photos_indexes_photos
        FOREIGN KEY (photo_id)
            REFERENCES photos (photo_id) ON UPDATE RESTRICT ON DELETE SET NULL,
    CONSTRAINT fk_photo_indexes_indexes
        FOREIGN KEY (index_name)
            REFERENCES indexes (index_name) ON UPDATE RESTRICT ON DELETE SET NULL
);

COMMENT ON TABLE photos_indexes IS 'Таблица для many-to-many связи';
COMMENT ON COLUMN photos_indexes.photo_id IS 'Ссылка на фотографию';
COMMENT ON COLUMN photos_indexes.index_name IS 'Название индекса';
COMMENT ON COLUMN photos_indexes.status IS 'Статус индекса: успешный или завершён с ошибкой';

commit;
