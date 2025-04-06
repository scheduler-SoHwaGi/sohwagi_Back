ALTER TABLE schedule ADD COLUMN year INT;
ALTER TABLE schedule ADD COLUMN am_pm VARCHAR(10);
ALTER TABLE schedule ADD COLUMN hour INT;
ALTER TABLE schedule ADD COLUMN minute INT;
ALTER TABLE schedule ADD COLUMN created_at DATETIME DEFAULT CURRENT_TIMESTAMP;

UPDATE schedule
SET year = 2025,
    am_pm = '오전',
    hour = 0,
    minute = 0
WHERE year IS NULL;