    CREATE TABLE IF NOT EXISTS crypto_rates(
        name TEXT NOT NULL PRIMARY KEY UNIQUE,
        price REAL NOT NULL,
        day_volume REAL NOT NULL,
        day_change REAL NOT NULL,
        last_checked_time TEXT NOT NULL
    );