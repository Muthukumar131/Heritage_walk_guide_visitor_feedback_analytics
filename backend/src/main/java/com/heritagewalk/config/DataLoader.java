package com.heritagewalk.config;

import com.heritagewalk.entity.*;
import com.heritagewalk.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final HeritagePlaceRepository placeRepository;
    private final WalkingRouteRepository routeRepository;
    private final RouteStopRepository routeStopRepository;
    private final VisitorFeedbackRepository feedbackRepository;
    private final AdminUserRepository adminUserRepository;
    private final UserRepository userRepository;

    @Autowired
    public DataLoader(HeritagePlaceRepository placeRepository,
                      WalkingRouteRepository routeRepository,
                      RouteStopRepository routeStopRepository,
                      VisitorFeedbackRepository feedbackRepository,
                      AdminUserRepository adminUserRepository,
                      UserRepository userRepository) {
        this.placeRepository = placeRepository;
        this.routeRepository = routeRepository;
        this.routeStopRepository = routeStopRepository;
        this.feedbackRepository = feedbackRepository;
        this.adminUserRepository = adminUserRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Seed default Admin User if not present
        if (adminUserRepository.count() == 0) {
            AdminUser admin = new AdminUser();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("admin123"));
            admin.setFullName("Heritage Administrator");
            admin.setEmail("admin@heritagewalk.org");
            admin.setRole("ROLE_ADMIN");
            adminUserRepository.save(admin);
            System.out.println(">> Initialized Default Admin: admin / admin123");
        }

        // Seed Users table with Admin & Visitors
        if (userRepository.count() == 0) {
            User admin = new User(null, "admin", encoder.encode("admin123"), "Heritage Administrator", "admin@heritagewalk.org", "ROLE_ADMIN");
            User v1 = new User(null, "visitor1", encoder.encode("visitor123"), "Aarav Sharma", "aarav.sharma@example.com", "ROLE_VISITOR");
            User v2 = new User(null, "visitor2", encoder.encode("visitor123"), "Sarah Jenkins", "sarah.j@example.com", "ROLE_VISITOR");
            userRepository.saveAll(Arrays.asList(admin, v1, v2));
            System.out.println(">> Initialized Users: visitor1 / visitor123, visitor2 / visitor123");
        }

        // Seed Heritage Places if empty
        if (placeRepository.count() == 0) {
            HeritagePlace p1 = new HeritagePlace(
                    null,
                    "Red Fort (Lal Qila)",
                    "Fort & Palace",
                    "A historic fortified complex built of red sandstone that served as the main residence of the Mughal Emperors for nearly 200 years.",
                    "Commissioned by Emperor Shah Jahan in 1638 when he decided to shift his capital from Agra to Delhi. Construction took a full decade to complete.",
                    "Designated as a UNESCO World Heritage Site in 2007. The Prime Minister hoists the national flag here annually on Independence Day.",
                    "1638 - 1648 CE",
                    "Netaji Subhash Marg, Lal Qila, Chandni Chowk, Old Delhi",
                    28.6562, 77.2410,
                    "https://images.unsplash.com/photo-1587474260584-136574528ed5?auto=format&fit=crop&w=1200&q=80",
                    "09:30 AM", "05:30 PM", "INR 50 (Domestic), INR 600 (Foreigner)",
                    "ACTIVE", 1240
            );

            HeritagePlace p2 = new HeritagePlace(
                    null,
                    "Jama Masjid",
                    "Religious Monument",
                    "One of the largest and most renowned mosques in India, commanding an awe-inspiring view of the walled city of Old Delhi.",
                    "Constructed between 1650 and 1656 by Mughal Emperor Shah Jahan. Built using red sandstone and white marble with three grand gates and two towering minarets.",
                    "Stands as a masterpiece of Islamic architecture in India, accommodating more than 25,000 worshippers in its sprawling courtyard.",
                    "1650 - 1656 CE",
                    "Meena Bazaar, Jama Masjid, Chandni Chowk, Old Delhi",
                    28.6507, 77.2334,
                    "https://images.unsplash.com/photo-1590050752117-238cb0fb12b1?auto=format&fit=crop&w=1200&q=80",
                    "07:00 AM", "06:30 PM", "Free (Camera INR 300)",
                    "ACTIVE", 980
            );

            HeritagePlace p3 = new HeritagePlace(
                    null,
                    "Qutub Minar",
                    "Tower & Minaret",
                    "A towering 72.5-meter victory minaret built of fluted red sandstone and marble, surrounded by ancient ruins of the Mehrauli complex.",
                    "Started by Qutb-ud-din Aibak in 1199 CE to celebrate Muslim dominance after the fall of Delhi’s last Hindu kingdom. Additional storeys were added by Shams-ud-din Iltutmish and Firoz Shah Tughlaq.",
                    "UNESCO World Heritage Site and the world’s tallest brick minaret. Contains the famous 4th-century rust-resistant Iron Pillar of Chandragupta II.",
                    "1199 - 1368 CE",
                    "Seth Sarai, Mehrauli, New Delhi",
                    28.5245, 77.1855,
                    "https://images.unsplash.com/photo-1564507592333-c60657eea523?auto=format&fit=crop&w=1200&q=80",
                    "07:00 AM", "08:00 PM", "INR 40 (Domestic), INR 600 (Foreigner)",
                    "ACTIVE", 1560
            );

            HeritagePlace p4 = new HeritagePlace(
                    null,
                    "Humayun’s Tomb",
                    "Mausoleum",
                    "The tomb of the Mughal Emperor Humayun, celebrated as the first garden-tomb on the Indian subcontinent.",
                    "Commissioned by Humayun’s first wife, Empress Bega Begum, in 1558 and designed by Persian architect Mirak Mirza Ghiyas.",
                    "UNESCO World Heritage Site that served as the architectural inspiration and direct precursor to the Taj Mahal in Agra.",
                    "1565 - 1572 CE",
                    "Mathura Road, Nizamuddin East, New Delhi",
                    28.5933, 77.2507,
                    "https://images.unsplash.com/photo-1585135497273-1a86b09fe70e?auto=format&fit=crop&w=1200&q=80",
                    "06:00 AM", "06:00 PM", "INR 40 (Domestic), INR 600 (Foreigner)",
                    "ACTIVE", 1120
            );

            HeritagePlace p5 = new HeritagePlace(
                    null,
                    "India Gate & Kartavya Path",
                    "Memorial & Civic",
                    "A prominent war memorial triumphal arch standing 42 meters high, commemorating soldiers of the British Indian Army who lost their lives in World War I.",
                    "Designed by Sir Edwin Lutyens and unveiled in 1931. The Amar Jawan Jyoti flame burner pays eternal homage to unknown martyred soldiers.",
                    "An iconic national landmark surrounded by lush lawns, water bodies, and the refurbished Kartavya Path leading toward Rashtrapati Bhavan.",
                    "1921 - 1931 CE",
                    "Rajpath, India Gate, Central Secretariat, New Delhi",
                    28.6129, 77.2295,
                    "https://images.unsplash.com/photo-1587474260584-136574528ed5?auto=format&fit=crop&w=1200&q=80",
                    "Open 24 Hours", "Open 24 Hours", "Free",
                    "ACTIVE", 2300
            );

            HeritagePlace p6 = new HeritagePlace(
                    null,
                    "Lotus Temple",
                    "Modern Architectural Wonder",
                    "A modern architectural marvel shaped in the form of an unfolding lotus blossom with 27 free-standing marble petals.",
                    "Designed by Iranian-Canadian architect Fariborz Sahba and completed in 1986. Open to all people regardless of religion, caste, or background.",
                    "Renowned for its pure silence and meditative environment, drawing millions of international travelers annually.",
                    "1980 - 1986 CE",
                    "Lotus Temple Road, Bahapur, Kalkaji, New Delhi",
                    28.5535, 77.2588,
                    "https://images.unsplash.com/photo-1598890777032-bde835ba27c2?auto=format&fit=crop&w=1200&q=80",
                    "08:30 AM", "05:00 PM (Closed Mondays)", "Free",
                    "ACTIVE", 1420
            );

            placeRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6));
            System.out.println(">> Seeded 6 Heritage Places.");

            // Seed Walking Routes
            WalkingRoute r1 = new WalkingRoute(
                    null,
                    "Old Delhi Imperial Mughal Trail",
                    "Immerse yourself in the bustling alleys of Shahjahanabad, exploring red sandstone fortresses, aromatic spice corridors, and India’s premier congregational mosque.",
                    3.2,
                    "2 Hours 30 Mins",
                    "Moderate",
                    "Lahori Gate, Red Fort",
                    "Jama Masjid Steps Gate 1",
                    "https://images.unsplash.com/photo-1590050752117-238cb0fb12b1?auto=format&fit=crop&w=1200&q=80",
                    "ACTIVE"
            );

            WalkingRoute r2 = new WalkingRoute(
                    null,
                    "Monuments & Gardens Heritage Walk",
                    "A serene architectural journey through early Mughal symmetry, pristine Charbagh water gardens, and the majestic precursors to the Taj Mahal.",
                    4.5,
                    "3 Hours 15 Mins",
                    "Easy",
                    "Sunder Nursery Gate",
                    "Humayun’s Tomb Main Gate",
                    "https://images.unsplash.com/photo-1585135497273-1a86b09fe70e?auto=format&fit=crop&w=1200&q=80",
                    "ACTIVE"
            );

            routeRepository.saveAll(Arrays.asList(r1, r2));

            // Seed Route Stops
            RouteStop s1 = new RouteStop(null, r1, p1, 1, "Start at Red Fort Lahori Gate. Observe the majestic red sandstone ramparts.");
            RouteStop s2 = new RouteStop(null, r1, p2, 2, "Walk through historical Chandni Chowk alleyways to Jama Masjid courtyard.");
            RouteStop s3 = new RouteStop(null, r2, p4, 1, "Enter through the west gate of Humayun’s Tomb and tour the Charbagh garden quadrangles.");
            RouteStop s4 = new RouteStop(null, r2, p5, 2, "Proceed along the ceremonial axis towards India Gate memorial arch.");

            routeStopRepository.saveAll(Arrays.asList(s1, s2, s3, s4));
            System.out.println(">> Seeded Walking Routes and Sequence Stops.");

            // Seed Visitor Feedback with explicit moderation statuses
            VisitorFeedback f1 = new VisitorFeedback(null, p1, "Aarav Sharma", "aarav.sharma@example.com", 5, "Magnificent fortress with breathtaking Mughal architecture! Evening light and sound show was spellbinding.", LocalDate.of(2026, 10, 12), "POSITIVE");
            f1.setUserId(2L);
            f1.setStatus("APPROVED");
            f1.setModeratedBy("admin");
            f1.setModeratedAt(java.time.LocalDateTime.now().minusDays(3));

            VisitorFeedback f2 = new VisitorFeedback(null, p1, "Sarah Jenkins", "sarah.j@example.com", 4, "Very rich historical insights. Can get crowded during peak afternoon hours, so visit early in the morning.", LocalDate.of(2026, 10, 15), "POSITIVE");
            f2.setUserId(3L);
            f2.setStatus("APPROVED");
            f2.setModeratedBy("admin");
            f2.setModeratedAt(java.time.LocalDateTime.now().minusDays(2));

            VisitorFeedback f3 = new VisitorFeedback(null, p2, "Mohit Verma", "mohit.v@example.com", 5, "Peaceful spiritual sanctuary right in the heart of Old Delhi. Climb the southern minaret for panoramic city views!", LocalDate.of(2026, 10, 18), "POSITIVE");
            f3.setUserId(2L);
            f3.setStatus("APPROVED");
            f3.setModeratedBy("admin");
            f3.setModeratedAt(java.time.LocalDateTime.now().minusDays(2));

            VisitorFeedback f4 = new VisitorFeedback(null, p3, "Elena Rostova", "elena.rostova@example.com", 5, "The architectural precision and intricate stone carvings on the Qutub Minar are astonishing. Truly a must-visit UNESCO site.", LocalDate.of(2026, 10, 20), "POSITIVE");
            f4.setStatus("APPROVED");
            f4.setModeratedBy("admin");
            f4.setModeratedAt(java.time.LocalDateTime.now().minusDays(1));

            VisitorFeedback f5 = new VisitorFeedback(null, p4, "Priya Nair", "priya.nair@example.com", 5, "A masterpiece of Persian and Mughal craftsmanship. Beautifully restored gardens and reflecting pools.", LocalDate.of(2026, 10, 21), "POSITIVE");
            f5.setStatus("APPROVED");
            f5.setModeratedBy("admin");
            f5.setModeratedAt(java.time.LocalDateTime.now().minusDays(1));

            // PENDING reviews waiting for admin moderation in the new Admin Moderation Hub
            VisitorFeedback f6 = new VisitorFeedback(null, p5, "Vikram Malhotra", "vikram.m@example.com", 4, "Kartavya Path is well-maintained and vibrant in the evenings. A solemn tribute to brave soldiers.", LocalDate.of(2026, 10, 22), "POSITIVE");
            f6.setStatus("PENDING");

            VisitorFeedback f7 = new VisitorFeedback(null, p6, "David Chen", "david.chen@example.com", 4, "Unique petal geometry and completely serene atmosphere. Ideal spot for quiet contemplation.", LocalDate.of(2026, 10, 23), "POSITIVE");
            f7.setStatus("PENDING");

            // REJECTED review demonstrating rejection tracking and moderation reasons
            VisitorFeedback f8 = new VisitorFeedback(null, p2, "Emily Watson", "emily.w@example.com", 3, "Beautiful architecture, but visitor guidance and shoe storage could be organized better.", LocalDate.of(2026, 10, 24), "NEUTRAL");
            f8.setStatus("REJECTED");
            f8.setModeratedBy("admin");
            f8.setModeratedAt(java.time.LocalDateTime.now().minusHours(12));
            f8.setModerationReason("Review requires constructive specifics per community guidelines.");

            feedbackRepository.saveAll(Arrays.asList(f1, f2, f3, f4, f5, f6, f7, f8));
            System.out.println(">> Seeded 8 Visitor Feedback Reviews with Moderation Workflows (5 Approved, 2 Pending, 1 Rejected).");
        }
    }
}
