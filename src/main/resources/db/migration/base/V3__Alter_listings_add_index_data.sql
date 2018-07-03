ALTER TABLE listings ALTER COLUMN data SET DATA TYPE jsonb USING data::jsonb;

CREATE INDEX ON listings USING gin ((data->'operation'->'put'->'publication'));
