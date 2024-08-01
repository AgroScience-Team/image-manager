-- Таблица для хранения информации о фотографиях
CREATE TABLE photo
(
    photo_id       UUID PRIMARY KEY,
    photo_field_id BIGINT NOT NULL,
    photo_date     DATE   NOT NULL
);

COMMENT ON TABLE photo IS 'Таблица для хранения информации о фотографиях';
COMMENT ON COLUMN photo.photo_id IS 'Уникальный идентификатор фотографии';
COMMENT ON COLUMN photo.photo_field_id IS 'Идентификатор поля, к которому относится фотография';
COMMENT ON COLUMN photo.photo_date IS 'Дата, когда была сделана фотография';

CREATE TABLE index
(
    index_name VARCHAR PRIMARY KEY
);

COMMENT ON TABLE index IS 'Таблица для хранения индексов для фотографий';
COMMENT ON COLUMN index.index_name IS 'Название индекса';

CREATE TABLE photo_index
(
    photo_id   UUID,
    index_name VARCHAR,
    CONSTRAINT fk_photo_index_photo
        FOREIGN KEY (photo_id)
            REFERENCES photo (photo_id) ON UPDATE RESTRICT ON DELETE SET NULL,
    CONSTRAINT fk_photo_index_index
        FOREIGN KEY (index_name)
            REFERENCES index (index_name) ON UPDATE RESTRICT ON DELETE SET NULL,
    PRIMARY KEY (photo_id, index_name)
);

COMMENT ON TABLE photo_index IS 'Таблица для many-to-many связи';
COMMENT ON COLUMN photo_index.photo_id IS 'Ссылка на фотографию';
COMMENT ON COLUMN photo_index.index_name IS 'Название индекса';


commit;
