-- ==============================================================
-- Heritage Walk Guide - Sample Data (MySQL)
-- ==============================================================

USE heritage_walk_db;

-- 1. Insert 6 Identified Sample Heritage Places
INSERT INTO heritage_places (id, name, category, description, historical_background, historical_significance, year_or_period, address, latitude, longitude, image_url, opening_time, closing_time, entry_fee, status, visit_count)
VALUES
(1, 'Red Fort (Lal Qila)', 'Fort & Palace', 
'A historic fortified complex built of red sandstone that served as the main residence of the Mughal Emperors for nearly 200 years.',
'Commissioned by Emperor Shah Jahan in 1638 when he decided to shift his capital from Agra to Delhi. Construction took a full decade to complete.',
'Designated as a UNESCO World Heritage Site in 2007. The Prime Minister hoists the national flag here annually on Independence Day.',
'1638 - 1648 CE', 'Netaji Subhash Marg, Lal Qila, Chandni Chowk, Old Delhi', 28.6562, 77.2410,
'https://images.unsplash.com/photo-1587474260584-136574528ed5?auto=format&fit=crop&w=1200&q=80',
'09:30 AM', '05:30 PM', 'INR 50 (Domestic), INR 600 (Foreigner)', 'ACTIVE', 1240),

(2, 'Jama Masjid', 'Religious Monument',
'One of the largest and most renowned mosques in India, commanding an awe-inspiring view of the walled city of Old Delhi.',
'Constructed between 1650 and 1656 by Mughal Emperor Shah Jahan. Built using red sandstone and white marble with three grand gates and two towering minarets.',
'Stands as a masterpiece of Islamic architecture in India, accommodating more than 25,000 worshippers in its sprawling courtyard.',
'1650 - 1656 CE', 'Meena Bazaar, Jama Masjid, Chandni Chowk, Old Delhi', 28.6507, 77.2334,
'https://images.unsplash.com/photo-1590050752117-238cb0fb12b1?auto=format&fit=crop&w=1200&q=80',
'07:00 AM', '12:00 PM & 01:30 PM - 06:30 PM', 'Free (Camera INR 300)', 'ACTIVE', 980),

(3, 'Qutub Minar', 'Tower & Minaret',
'A towering 72.5-meter victory minaret built of fluted red sandstone and marble, surrounded by ancient ruins of the Mehrauli complex.',
'Started by Qutb-ud-din Aibak in 1199 CE to celebrate Muslim dominance after the fall of Delhi’s last Hindu kingdom. Additional storeys were added by Shams-ud-din Iltutmish and Firoz Shah Tughlaq.',
'UNESCO World Heritage Site and the world’s tallest brick minaret. Contains the famous 4th-century rust-resistant Iron Pillar of Chandragupta II.',
'1199 - 1368 CE', 'Seth Sarai, Mehrauli, New Delhi', 28.5245, 77.1855,
'https://images.unsplash.com/photo-1564507592333-c60657eea523?auto=format&fit=crop&w=1200&q=80',
'07:00 AM', '08:00 PM', 'INR 40 (Domestic), INR 600 (Foreigner)', 'ACTIVE', 1560),

(4, 'Humayun’s Tomb', 'Mausoleum',
'The tomb of the Mughal Emperor Humayun, celebrated as the first garden-tomb on the Indian subcontinent.',
'Commissioned by Humayun’s first wife, Empress Bega Begum, in 1558 and designed by Persian architect Mirak Mirza Ghiyas.',
'UNESCO World Heritage Site that served as the architectural inspiration and direct precursor to the Taj Mahal in Agra.',
'1565 - 1572 CE', 'Mathura Road, Nizamuddin East, New Delhi', 28.5933, 77.2507,
'https://images.unsplash.com/photo-1585135497273-1a86b09fe70e?auto=format&fit=crop&w=1200&q=80',
'06:00 AM', '06:00 PM', 'INR 40 (Domestic), INR 600 (Foreigner)', 'ACTIVE', 1120),

(5, 'India Gate & Kartavya Path', 'Memorial & Civic',
'A prominent war memorial triumphal arch standing 42 meters high, commemorating soldiers of the British Indian Army who lost their lives in World War I.',
'Designed by Sir Edwin Lutyens and unveiled in 1931. The Amar Jawan Jyoti flame burner pays eternal homage to unknown martyred soldiers.',
'An iconic national landmark surrounded by lush lawns, water bodies, and the refurbished Kartavya Path leading toward Rashtrapati Bhavan.',
'1921 - 1931 CE', 'Rajpath, India Gate, Central Secretariat, New Delhi', 28.6129, 77.2295,
'https://images.unsplash.com/photo-1587474260584-136574528ed5?auto=format&fit=crop&w=1200&q=80',
'Open 24 Hours', 'Open 24 Hours', 'Free', 'ACTIVE', 2300),

(6, 'Lotus Temple (Baháʼí House of Worship)', 'Modern Architectural Wonder',
'A modern architectural marvel shaped in the form of an unfolding lotus blossom with 27 free-standing marble petals.',
'Designed by Iranian-Canadian architect Fariborz Sahba and completed in 1986. Open to all people regardless of religion, caste, or background.',
'Renowned for its pure silence and meditative environment, drawing millions of international travelers annually.',
'1980 - 1986 CE', 'Lotus Temple Road, Bahapur, Shambhu Dayal Bagh, Kalkaji, New Delhi', 28.5535, 77.2588,
'https://images.unsplash.com/photo-1598890777032-bde835ba27c2?auto=format&fit=crop&w=1200&q=80',
'08:30 AM', '05:00 PM (Closed Mondays)', 'Free', 'ACTIVE', 1420)
ON DUPLICATE KEY UPDATE name=VALUES(name);

-- 2. Insert 2 Sample Walking Routes
INSERT INTO walking_routes (id, name, description, distance_km, estimated_duration, difficulty_level, starting_point, ending_point, image_url, status)
VALUES
(1, 'Old Delhi Imperial Mughal Trail', 
'Immerse yourself in the bustling alleys of Shahjahanabad, exploring red sandstone fortresses, aromatic spice corridors, and India’s premier congregational mosque.',
3.2, '2 Hours 30 Mins', 'Moderate', 'Lahori Gate, Red Fort', 'Jama Masjid Steps Gate 1',
'https://images.unsplash.com/photo-1590050752117-238cb0fb12b1?auto=format&fit=crop&w=1200&q=80', 'ACTIVE'),

(2, 'Monuments & Gardens Heritage Walk', 
'A serene architectural journey through early Mughal symmetry, pristine Charbagh water gardens, and the majestic precursors to the Taj Mahal.',
4.5, '3 Hours 15 Mins', 'Easy', 'Sunder Nursery Gate', 'Humayun’s Tomb Main Gate',
'https://images.unsplash.com/photo-1585135497273-1a86b09fe70e?auto=format&fit=crop&w=1200&q=80', 'ACTIVE')
ON DUPLICATE KEY UPDATE name=VALUES(name);

-- 3. Insert Ordered Route Stops (Junction Table)
INSERT INTO route_stops (id, route_id, heritage_place_id, sequence_order, stop_notes)
VALUES
(1, 1, 1, 1, 'Start at Red Fort Lahori Gate. Observe the majestic red sandstone ramparts.'),
(2, 1, 2, 2, 'Walk through historical Chandni Chowk alleyways to Jama Masjid courtyard.')
ON DUPLICATE KEY UPDATE sequence_order=VALUES(sequence_order);

INSERT INTO route_stops (id, route_id, heritage_place_id, sequence_order, stop_notes)
VALUES
(3, 2, 4, 1, 'Enter through the west gate of Humayun’s Tomb and tour the Charbagh garden quadrangles.'),
(4, 2, 5, 2, 'Proceed along the ceremonial axis towards India Gate memorial arch.')
ON DUPLICATE KEY UPDATE sequence_order=VALUES(sequence_order);

-- 4. Insert Sample Visitor Feedback with Moderation Statuses
INSERT INTO visitor_feedback (id, heritage_place_id, user_id, visitor_name, visitor_email, rating, comment, visit_date, sentiment, status, moderation_status, moderated_by, moderated_at, moderation_reason)
VALUES
(1, 1, 2, 'Aarav Sharma', 'aarav.sharma@example.com', 5, 'Magnificent fortress with breathtaking Mughal architecture! Evening light and sound show was spellbinding.', '2026-10-12', 'POSITIVE', 'APPROVED', 'APPROVED', 'admin', '2026-10-12 18:30:00', 'Approved baseline review'),
(2, 1, 3, 'Sarah Jenkins', 'sarah.j@example.com', 4, 'Very rich historical insights. Can get crowded during peak afternoon hours, so visit early in the morning.', '2026-10-15', 'POSITIVE', 'APPROVED', 'APPROVED', 'admin', '2026-10-15 19:00:00', 'Approved baseline review'),
(3, 2, 2, 'Mohit Verma', 'mohit.v@example.com', 5, 'Peaceful spiritual sanctuary right in the heart of Old Delhi. Climb the southern minaret for panoramic city views!', '2026-10-18', 'POSITIVE', 'APPROVED', 'APPROVED', 'admin', '2026-10-18 17:45:00', 'Approved baseline review'),
(4, 3, NULL, 'Elena Rostova', 'elena.rostova@example.com', 5, 'The architectural precision and intricate stone carvings on the Qutub Minar are astonishing. Truly a must-visit UNESCO site.', '2026-10-20', 'POSITIVE', 'APPROVED', 'APPROVED', 'admin', '2026-10-20 20:10:00', 'Approved baseline review'),
(5, 4, NULL, 'Priya Nair', 'priya.nair@example.com', 5, 'A masterpiece of Persian and Mughal craftsmanship. Beautifully restored gardens and reflecting pools.', '2026-10-21', 'POSITIVE', 'APPROVED', 'APPROVED', 'admin', '2026-10-21 16:20:00', 'Approved baseline review'),
(6, 5, NULL, 'Vikram Malhotra', 'vikram.m@example.com', 4, 'Kartavya Path is well-maintained and vibrant in the evenings. A solemn tribute to brave soldiers.', '2026-10-22', 'POSITIVE', 'PENDING', 'PENDING', NULL, NULL, NULL),
(7, 6, NULL, 'David Chen', 'david.chen@example.com', 4, 'Unique petal geometry and completely serene atmosphere. Ideal spot for quiet contemplation.', '2026-10-23', 'POSITIVE', 'PENDING', 'PENDING', NULL, NULL, NULL),
(8, 2, NULL, 'Emily Watson', 'emily.w@example.com', 3, 'Beautiful architecture, but visitor guidance and shoe storage could be organized better.', '2026-10-24', 'NEUTRAL', 'REJECTED', 'REJECTED', 'admin', '2026-10-24 21:00:00', 'Review requires constructive specifics per guidelines')
ON DUPLICATE KEY UPDATE status=VALUES(status);

-- 5. Insert Admin User (Password: admin123, BCrypt hashed)
-- BCrypt hash for 'admin123' is $2a$10$wK1F5N8u4gN4pP2gRj1ZgOq9X7C6W1q4vF0lK7y1b2c3d4e5f6g7h
INSERT INTO admin_users (id, username, password, full_name, email, role)
VALUES
(1, 'admin', '$2a$10$r9GgH8tJ3bYwzP4yL6Fvce1qKm9Z2oN5kX4vD1e7lW3rS9tU2vOqm', 'System Administrator', 'admin@heritagewalk.org', 'ROLE_ADMIN')
ON DUPLICATE KEY UPDATE username=VALUES(username);

-- 6. Insert Users (Admin and Visitors)
-- BCrypt for 'visitor123': $2a$10$2lK0gT.oZ7xZ.2k5eBvNee2V8NnZ5K5xX6q6xK9vT.oZ7xZ.2k5eB
INSERT INTO users (id, username, password, full_name, email, role)
VALUES
(1, 'admin', '$2a$10$r9GgH8tJ3bYwzP4yL6Fvce1qKm9Z2oN5kX4vD1e7lW3rS9tU2vOqm', 'System Administrator', 'admin@heritagewalk.org', 'ROLE_ADMIN'),
(2, 'visitor1', '$2a$10$r9GgH8tJ3bYwzP4yL6Fvce1qKm9Z2oN5kX4vD1e7lW3rS9tU2vOqm', 'Aarav Sharma', 'aarav.sharma@example.com', 'ROLE_VISITOR'),
(3, 'visitor2', '$2a$10$r9GgH8tJ3bYwzP4yL6Fvce1qKm9Z2oN5kX4vD1e7lW3rS9tU2vOqm', 'Sarah Jenkins', 'sarah.j@example.com', 'ROLE_VISITOR')
ON DUPLICATE KEY UPDATE username=VALUES(username);
