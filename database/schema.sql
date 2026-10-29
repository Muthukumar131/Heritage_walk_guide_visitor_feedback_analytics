-- ==============================================================
-- Heritage Walk Guide and Visitor Feedback Analytics Dashboard
-- Database Schema Definition (MySQL)
-- Database Name: heritage_walk_db
-- ==============================================================

CREATE DATABASE IF NOT EXISTS heritage_walk_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE heritage_walk_db;

-- Table 1: Heritage Places
CREATE TABLE IF NOT EXISTS heritage_places (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    historical_background TEXT,
    historical_significance TEXT,
    year_or_period VARCHAR(100),
    address VARCHAR(255) NOT NULL,
    latitude DOUBLE,
    longitude DOUBLE,
    image_url VARCHAR(500),
    opening_time VARCHAR(50),
    closing_time VARCHAR(50),
    entry_fee VARCHAR(100) DEFAULT 'Free',
    status VARCHAR(50) DEFAULT 'ACTIVE',
    visit_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table 2: Walking Routes
CREATE TABLE IF NOT EXISTS walking_routes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    distance_km DOUBLE NOT NULL,
    estimated_duration VARCHAR(100) NOT NULL,
    difficulty_level VARCHAR(50) NOT NULL, -- Easy, Moderate, Challenging
    starting_point VARCHAR(255) NOT NULL,
    ending_point VARCHAR(255) NOT NULL,
    image_url VARCHAR(500),
    status VARCHAR(50) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table 3: Ordered Route Stops (Junction Table with Sequence)
CREATE TABLE IF NOT EXISTS route_stops (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    route_id BIGINT NOT NULL,
    heritage_place_id BIGINT NOT NULL,
    sequence_order INT NOT NULL,
    stop_notes VARCHAR(255),
    CONSTRAINT fk_route_stops_route FOREIGN KEY (route_id) REFERENCES walking_routes(id) ON DELETE CASCADE,
    CONSTRAINT fk_route_stops_place FOREIGN KEY (heritage_place_id) REFERENCES heritage_places(id) ON DELETE CASCADE,
    CONSTRAINT uq_route_sequence UNIQUE (route_id, sequence_order)
);

-- Table 4: Visitor Feedback & Moderation Workflow
CREATE TABLE IF NOT EXISTS visitor_feedback (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    heritage_place_id BIGINT NOT NULL,
    user_id BIGINT NULL,
    visitor_name VARCHAR(150) NOT NULL,
    visitor_email VARCHAR(150),
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    visit_date DATE,
    sentiment VARCHAR(50) DEFAULT 'NEUTRAL',
    status VARCHAR(50) DEFAULT 'PENDING',
    moderation_status VARCHAR(50) DEFAULT 'PENDING',
    moderated_by VARCHAR(100),
    moderated_at TIMESTAMP NULL,
    moderation_reason VARCHAR(500),
    flagged_content BOOLEAN DEFAULT FALSE,
    flagged_reason VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_feedback_heritage_place FOREIGN KEY (heritage_place_id) REFERENCES heritage_places(id) ON DELETE CASCADE
);

-- Table 5: Administrator Accounts (Legacy Compatibility)
CREATE TABLE IF NOT EXISTS admin_users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    role VARCHAR(50) DEFAULT 'ROLE_ADMIN',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table 6: Application Users (Visitors and Administrators)
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    role VARCHAR(50) NOT NULL DEFAULT 'ROLE_VISITOR', -- ROLE_VISITOR or ROLE_ADMIN
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
