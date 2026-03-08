-- Book-My-Show Database Schema
-- PostgreSQL Database Scripts
-- This schema is designed for the Book-My-Show application

-- Drop tables if they exist (in reverse order of dependencies)
DROP TABLE IF EXISTS ticket CASCADE;
DROP TABLE IF EXISTS seat CASCADE;
DROP TABLE IF EXISTS screen CASCADE;
DROP TABLE IF EXISTS theatre CASCADE;
DROP TABLE IF EXISTS movie_schedule CASCADE;
DROP TABLE IF EXISTS movie CASCADE;
DROP TABLE IF EXISTS "user" CASCADE;

-- ============================================================================
-- USER TABLE
-- ============================================================================
-- Stores user account information
CREATE TABLE "user" (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(15) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email_id VARCHAR(100) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_user_phone ON "user"(phone_number);
CREATE INDEX idx_user_email ON "user"(email_id);

-- ============================================================================
-- MOVIE TABLE
-- ============================================================================
-- Stores movie information
CREATE TABLE movie (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    rating FLOAT,
    votes BIGINT DEFAULT 0,
    language VARCHAR(20),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_movie_status ON movie(status);
CREATE INDEX idx_movie_language ON movie(language);

-- ============================================================================
-- THEATRE TABLE
-- ============================================================================
-- Stores theatre information
CREATE TABLE theatre (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(200) NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_theatre_status ON theatre(status);
CREATE INDEX idx_theatre_location ON theatre(location);

-- ============================================================================
-- SCREEN TABLE
-- ============================================================================
-- Stores screen information for each theatre
CREATE TABLE screen (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    theatre_id BIGINT NOT NULL,
    movie_id BIGINT,
    show_time VARCHAR(10),
    show_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_screen_theatre FOREIGN KEY (theatre_id) REFERENCES theatre(id) ON DELETE CASCADE,
    CONSTRAINT fk_screen_movie FOREIGN KEY (movie_id) REFERENCES movie(id) ON DELETE SET NULL
);

CREATE INDEX idx_screen_theatre ON screen(theatre_id);
CREATE INDEX idx_screen_movie ON screen(movie_id);
CREATE INDEX idx_screen_show_date ON screen(show_date);

-- ============================================================================
-- MOVIE_SCHEDULE TABLE
-- ============================================================================
-- Stores movie schedule/show information
CREATE TABLE movie_schedule (
    id BIGSERIAL PRIMARY KEY,
    theatre_id BIGINT NOT NULL,
    screen_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    show_time VARCHAR(10) NOT NULL,
    show_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_schedule_theatre FOREIGN KEY (theatre_id) REFERENCES theatre(id) ON DELETE CASCADE,
    CONSTRAINT fk_schedule_screen FOREIGN KEY (screen_id) REFERENCES screen(id) ON DELETE CASCADE,
    CONSTRAINT fk_schedule_movie FOREIGN KEY (movie_id) REFERENCES movie(id) ON DELETE CASCADE
);

CREATE INDEX idx_schedule_theatre ON movie_schedule(theatre_id);
CREATE INDEX idx_schedule_screen ON movie_schedule(screen_id);
CREATE INDEX idx_schedule_movie ON movie_schedule(movie_id);
CREATE INDEX idx_schedule_date ON movie_schedule(show_date);

-- ============================================================================
-- SEAT TABLE
-- ============================================================================
-- Stores seat information for each screen/theatre
CREATE TABLE seat (
    id BIGSERIAL PRIMARY KEY,
    screen_id BIGINT NOT NULL,
    movie_schedule_id BIGINT NOT NULL,
    name VARCHAR(10) NOT NULL,
    status VARCHAR(20) DEFAULT 'AVAILABLE',
    seating_type VARCHAR(20),
    ticket_category VARCHAR(20),
    rate FLOAT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_seat_screen FOREIGN KEY (screen_id) REFERENCES screen(id) ON DELETE CASCADE,
    CONSTRAINT fk_seat_schedule FOREIGN KEY (movie_schedule_id) REFERENCES movie_schedule(id) ON DELETE CASCADE
);

CREATE INDEX idx_seat_screen ON seat(screen_id);
CREATE INDEX idx_seat_schedule ON seat(movie_schedule_id);
CREATE INDEX idx_seat_status ON seat(status);

-- ============================================================================
-- TICKET TABLE
-- ============================================================================
-- Stores ticket booking information
CREATE TABLE ticket (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    movie_schedule_id BIGINT NOT NULL,
    theatre_id BIGINT NOT NULL,
    seat_count INTEGER NOT NULL,
    amount_paid FLOAT NOT NULL,
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'CONFIRMED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_ticket_user FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE,
    CONSTRAINT fk_ticket_schedule FOREIGN KEY (movie_schedule_id) REFERENCES movie_schedule(id) ON DELETE CASCADE,
    CONSTRAINT fk_ticket_theatre FOREIGN KEY (theatre_id) REFERENCES theatre(id) ON DELETE CASCADE
);

CREATE INDEX idx_ticket_user ON ticket(user_id);
CREATE INDEX idx_ticket_schedule ON ticket(movie_schedule_id);
CREATE INDEX idx_ticket_theatre ON ticket(theatre_id);
CREATE INDEX idx_ticket_booking_date ON ticket(booking_date);

-- ============================================================================
-- TICKET_SEAT MAPPING TABLE
-- ============================================================================
-- Maps tickets to booked seats
CREATE TABLE ticket_seat (
    id BIGSERIAL PRIMARY KEY,
    ticket_id BIGINT NOT NULL,
    seat_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_ticket_seat_ticket FOREIGN KEY (ticket_id) REFERENCES ticket(id) ON DELETE CASCADE,
    CONSTRAINT fk_ticket_seat_seat FOREIGN KEY (seat_id) REFERENCES seat(id) ON DELETE CASCADE,
    CONSTRAINT unique_ticket_seat UNIQUE(ticket_id, seat_id)
);

CREATE INDEX idx_ticket_seat_ticket ON ticket_seat(ticket_id);
CREATE INDEX idx_ticket_seat_seat ON ticket_seat(seat_id);

-- ============================================================================
-- SAMPLE DATA
-- ============================================================================

-- Insert sample users
INSERT INTO "user" (name, phone_number, password, email_id) VALUES
('John Doe', '9876543210', 'Password@123', 'john@example.com'),
('Jane Smith', '9876543211', 'Password@456', 'jane@example.com'),
('Bob Johnson', '9876543212', 'Password@789', 'bob@example.com');

-- Insert sample movies
INSERT INTO movie (name, description, rating, votes, language, status) VALUES
('Movie 1', 'Action packed adventure', 8.5, 1200, 'English', 'ACTIVE'),
('Movie 2', 'Romantic comedy', 7.8, 950, 'English', 'ACTIVE'),
('Movie 3', 'Thriller mystery', 8.2, 1500, 'Hindi', 'ACTIVE');

-- Insert sample theatres
INSERT INTO theatre (name, location, status) VALUES
('Cinema Palace', '123 Main Street, City Center', 'ACTIVE'),
('Star Theatre', '456 Oak Avenue, Downtown', 'ACTIVE'),
('Regal Cinema', '789 Pine Road, Mall', 'ACTIVE');

-- Insert sample screens
INSERT INTO screen (name, theatre_id, movie_id, show_time, show_date) VALUES
('Screen 1', 1, 1, '10:00', '2026-02-20'),
('Screen 2', 1, 2, '14:00', '2026-02-20'),
('Screen 1', 2, 3, '19:00', '2026-02-20');

-- Insert sample movie schedules
INSERT INTO movie_schedule (theatre_id, screen_id, movie_id, show_time, show_date, status) VALUES
(1, 1, 1, '10:00', '2026-02-20', 'ACTIVE'),
(1, 2, 2, '14:00', '2026-02-20', 'ACTIVE'),
(2, 3, 3, '19:00', '2026-02-20', 'ACTIVE');

-- Insert sample seats for screen 1
INSERT INTO seat (screen_id, movie_schedule_id, name, status, seating_type, ticket_category, rate) VALUES
(1, 1, 'A1', 'AVAILABLE', 'STANDARD', 'GENERAL', 150.0),
(1, 1, 'A2', 'AVAILABLE', 'STANDARD', 'GENERAL', 150.0),
(1, 1, 'A3', 'AVAILABLE', 'STANDARD', 'GENERAL', 150.0),
(1, 1, 'B1', 'AVAILABLE', 'PREMIUM', 'PREMIUM', 250.0),
(1, 1, 'B2', 'AVAILABLE', 'PREMIUM', 'PREMIUM', 250.0),
(1, 1, 'B3', 'AVAILABLE', 'PREMIUM', 'PREMIUM', 250.0);

-- Insert sample seats for screen 2
INSERT INTO seat (screen_id, movie_schedule_id, name, status, seating_type, ticket_category, rate) VALUES
(2, 2, 'A1', 'AVAILABLE', 'STANDARD', 'GENERAL', 150.0),
(2, 2, 'A2', 'AVAILABLE', 'STANDARD', 'GENERAL', 150.0),
(2, 2, 'B1', 'AVAILABLE', 'PREMIUM', 'PREMIUM', 250.0),
(2, 2, 'B2', 'AVAILABLE', 'PREMIUM', 'PREMIUM', 250.0);

-- Insert sample seats for screen 3
INSERT INTO seat (screen_id, movie_schedule_id, name, status, seating_type, ticket_category, rate) VALUES
(3, 3, 'A1', 'AVAILABLE', 'STANDARD', 'GENERAL', 200.0),
(3, 3, 'A2', 'AVAILABLE', 'STANDARD', 'GENERAL', 200.0),
(3, 3, 'C1', 'AVAILABLE', 'LUXURY', 'LUXURY', 350.0),
(3, 3, 'C2', 'AVAILABLE', 'LUXURY', 'LUXURY', 350.0);

