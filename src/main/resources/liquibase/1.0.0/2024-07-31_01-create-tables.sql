CREATE TABLE photos
(
  id         UUID PRIMARY KEY,
  contour_id UUID    NOT NULL,
  date       DATE    NOT NULL,
  extension  VARCHAR NOT NULL
);

COMMENT ON TABLE photos IS 'Таблица для хранения информации о фотографиях';

COMMENT ON COLUMN photos.id IS 'Уникальный идентификатор фотографии';
COMMENT ON COLUMN photos.contour_id IS 'Идентификатор контура, к которому относится фотография';
COMMENT ON COLUMN photos.date IS 'Дата, когда была сделана фотография';
COMMENT ON COLUMN photos.extension IS 'Расширение снимка';

CREATE TABLE workers_results
(
  id          UUID DEFAULT gen_random_uuid() PRIMARY KEY,
  job_id      UUID NOT NULL,
  worker_name varchar,
  photo_id    UUID,
  type        VARCHAR,
  path        VARCHAR,
  success     BOOL,
  CONSTRAINT fk_workers_results_photos
    FOREIGN KEY (photo_id)
      REFERENCES photos (id) ON UPDATE RESTRICT ON DELETE SET NULL
);

COMMENT ON TABLE workers_results IS 'Таблица для many-to-many связи';

COMMENT ON COLUMN workers_results.id IS 'Служебное поле, идентификатор';
COMMENT ON COLUMN workers_results.job_id IS 'Идентификатор задачи';
COMMENT ON COLUMN workers_results.id IS 'Имя воркера';
COMMENT ON COLUMN workers_results.photo_id IS 'Ссылка на фотографию';
COMMENT ON COLUMN workers_results.type IS 'Название индекса';
COMMENT ON COLUMN workers_results.path IS 'Путь до файла в s3';
COMMENT ON COLUMN workers_results.success IS 'Успешная или нет попытка';


CREATE TABLE job_uuid_registry
(
  job_id UUID PRIMARY KEY
);

COMMENT ON TABLE job_uuid_registry IS 'Служебная таблица для хранения job_id';

COMMENT ON COLUMN job_uuid_registry.job_id IS 'Идентификатор задачи из таблицы workers_results';

commit;
