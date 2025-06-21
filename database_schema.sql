Jalankan skrip ini di database PostgreSQL Anda (trip_planner_db) untuk membuat tabel yang diperlukan.
CREATE TABLE trips (
    id SERIAL PRIMARY KEY,
    destination VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE day_plans (
                           id SERIAL PRIMARY KEY,
                           trip_id INTEGER NOT NULL REFERENCES trips(id) ON DELETE CASCADE,
                           plan_date DATE NOT NULL
);

CREATE TABLE activities (
                            id SERIAL PRIMARY KEY,
                            day_plan_id INTEGER NOT NULL REFERENCES day_plans(id) ON DELETE CASCADE,
                            start_time TIME NOT NULL,
                            description TEXT NOT NULL,
                            activity_type VARCHAR(50) NOT NULL,
                            location VARCHAR(255),
                            ticket_price NUMERIC(10, 2),
                            restaurant_name VARCHAR(255),
                            cuisine_type VARCHAR(100)
);