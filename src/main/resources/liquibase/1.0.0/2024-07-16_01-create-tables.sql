CREATE TABLE base
(
    id UUID NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY
);

COMMENT ON TABLE base IS 'Базовая таблица';
COMMENT ON COLUMN base.id IS 'Уникальный идентификаторz';

commit;
