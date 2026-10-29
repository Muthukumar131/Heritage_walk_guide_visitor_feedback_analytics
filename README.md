# Heritage Walk Guide & Visitor Feedback Analytics Dashboard

A full-stack enterprise web application designed for exploring historical monuments, following curated walking routes with ordered stops, analyzing visitor feedback in real-time, and generating official conservation reports for NGOs and municipal authorities.

Built with **Angular 17**, **Java 21**, **Spring Boot 3**, and **MySQL**.

---

## 🌟 Key Highlights & Dashboards

1. **Visitor Dashboard (`/visitor`)**:
   - Welcome banner with visitor's name.
   - Live metrics: Total Heritage Sites Available, Sites Explored, Reviews Submitted, Average Rating Given.
   - Featured & Recommended Monuments with historical background and cultural significance.
   - **"My Reviews" Management**: View personal submissions, edit review comments/ratings, and delete own reviews.
   - **Visitor Registration (`/register`)**: Self-service registration with BCrypt encrypted credentials.

2. **Admin Dashboard (`/admin`)**:
   - **Tab 1: Monuments Directory**: Full CRUD for monuments including category, historical period, cultural significance, timings, entry fees, and geographic coordinates.
   - **Tab 2: Walking Routes**: Create & manage walking trails with ordered stops and distance/duration estimates.
   - **Tab 3: Review Moderation**: Filter reviews by site, rating, date range, or keyword. Approve or flag reviews, and delete inappropriate content.
   - **Tab 4: NGO Reports & Export**: Filter by site, date range, category, minimum rating, and review logs. Real-time preview of conservation indicators, observations, and recommendations.

3. **Official NGO Report Generation**:
   - **Downloadable PDF Report**: Generated server-side using **OpenPDF** with cover page, executive summary, site evaluation matrix, visitor feedback sentiment breakdown, infrastructure indicators (Cleanliness, Signage, Accessibility, Atmosphere), and itemized review logs.
   - **Downloadable Excel Report**: Generated server-side using **Apache POI** featuring **5 distinct sheets**:
     1. `Executive Summary`: Core KPIs, scope parameters, and generation metadata.
     2. `Heritage Sites Evaluation`: Complete monument details including cultural significance and footfall.
     3. `Visitor Reviews`: Full audit log of visitor reviews, sentiments, and moderation status.
     4. `Rating Analytics`: Star rating distribution and percentages.
     5. `Feedback Insights`: Quantitative category mentions across key infrastructure domains.

4. **Visitor Feedback Analytics Dashboard (`/analytics`)**:
   - Chart.js visual distribution of 1–5 star ratings.
   - Per-monument average ratings comparison chart.
   - Community metrics: Total Registered Visitors, Highest & Lowest Rated Sites, Most & Least Reviewed Sites.
   - Recent reviews feed and top-visited monument rankings.

---

## 🏛️ Project Architecture & Structure

```
heritage-walk-guide/
├── start-fast.bat            # One-click Windows runner (Starts backend + frontend on port 8080)
├── start-fast.ps1            # PowerShell fast runner
├── database/
│   ├── schema.sql            # Complete MySQL tables, foreign keys, and indexes (6 tables)
│   └── data.sql              # 6 monuments, 2 walking routes, ordered stops, reviews, admin, visitors
│
├── backend/                  # Java 21 Spring Boot REST Application
│   ├── pom.xml               # Maven configuration (Spring Boot 3.2.4, OpenPDF, Apache POI, JPA, MySQL)
│   ├── maven/                # Bundled standalone Maven 3.9.6 (runs with zero installation)
│   └── src/main/
│       ├── resources/
│       │   ├── application.properties # H2 virtual database mode & MySQL configuration
│       │   └── static/       # Compiled Angular SPA production build served on port 8080
│       └── java/com/heritagewalk/
│           ├── HeritageWalkApplication.java
│           ├── config/       # Security, CORS, DataLoader, and SPA filter
│           ├── entity/       # HeritagePlace, WalkingRoute, RouteStop, VisitorFeedback, User
│           ├── repository/   # Spring Data JPA repositories
│           ├── service/      # AnalyticsService, NgoReportService, VisitorDashboardService, etc.
│           ├── controller/   # Places, Routes, Feedback, Analytics, Auth, Visitor, NgoReport
│           └── dto/          # Data Transfer Objects including NgoReportSummaryDTO & AnalyticsResponseDTO
│
├── frontend/                 # Standalone Angular 17 Application
│   ├── package.json          # Bootstrap 5, Bootstrap Icons, Chart.js, Angular 17
│   └── src/app/
│       ├── models/           # TypeScript models
│       ├── services/         # AuthService, FeedbackService, ReportService, VisitorService, etc.
│       └── components/
│           ├── home/         # Hero banner & featured sites
│           ├── places/       # Monument list & monument detail
│           ├── routes/       # Walking routes with ordered sequence stops
│           ├── feedback/     # 5-star rating submission form
│           ├── analytics/    # Live Chart.js analytics & extremes
│           ├── visitor/      # Visitor Dashboard & Visitor Registration
│           └── admin/        # Admin Login & Admin Dashboard (with NGO Reports tab)
└── README.md
```

---

## 🚀 How to Run the Application

### Option A: The Fast 1-Click Launch (Recommended)
Double-click `start-fast.bat` or run in PowerShell:
```powershell
Set-Location 'C:\Users\rajkumar\.gemini\antigravity\scratch\heritage-walk-guide'
.\start-fast.ps1
```
The entire application (Spring Boot backend + Angular SPA + In-Memory MySQL database) starts unified on **`http://localhost:8080`** and opens automatically in your browser!

### Option B: Separate Development Terminals
If you wish to run backend and Angular frontend separately:

1. **Terminal 1: Start Backend**
   ```powershell
   Set-Location 'C:\Users\rajkumar\.gemini\antigravity\scratch\heritage-walk-guide\backend'
   .\maven\bin\mvn.cmd spring-boot:run
   ```
   *Runs on `http://localhost:8080`*

2. **Terminal 2: Start Angular Frontend**
   ```powershell
   Set-Location 'C:\Users\rajkumar\.gemini\antigravity\scratch\heritage-walk-guide\frontend'
   npm start
   ```
   *Runs on `http://localhost:4200`*

---

## 🔑 Demo Accounts

| Role | Username | Password | Access Rights |
| :--- | :--- | :--- | :--- |
| **Administrator** | `admin` | `admin123` | Full Admin Hub: Monuments, Routes, Review Moderation, NGO PDF & Excel Reports |
| **Visitor** | `visitor1` | `visitor123` | Visitor Dashboard, Submit Reviews, Edit/Delete Own Reviews |
| **New Visitor** | *Self-Register* | *Your choice* | Create a new visitor account at `/register` |

---

## 📊 Testing End-to-End Features

1. **Visitor Flow**:
   - Navigate to `http://localhost:8080/register` and create an account.
   - Log in to access the **Visitor Dashboard** (`/visitor`).
   - Click **Write Review** (`/feedback`), rate a monument 5 stars, and submit.
   - Return to your Visitor Dashboard: your review is immediately listed with the option to **Edit** or **Delete** it.

2. **Admin & NGO Report Flow**:
   - Sign in as `admin` / `admin123` at `http://localhost:8080/admin/login`.
   - In the **Admin Hub** (`/admin`), switch to the **NGO Reports & Export** tab.
   - Adjust filters (Site, Date Range, Category, Minimum Rating).
   - Click **Download Official PDF Report** to download the OpenPDF conservation report.
   - Click **Download 5-Sheet Excel Workbook** to download the structured spreadsheet.
   - In the **Reviews & Moderation** tab, filter reviews and test **Approve**, **Flag**, or **Delete** actions.

3. **Analytics Flow**:
   - Navigate to `http://localhost:8080/analytics` to view live calculations:
     - Rating Distribution chart (1–5 stars)
     - Average Rating per Monument chart
     - Registered Visitors metric
     - Highest and lowest rated monuments
     - Most and least reviewed monuments

---

## 🚀 Step-by-Step Setup Guide (Windows)

### Prerequisites Check
Before running, verify your installed tools in Windows PowerShell:

```powershell
# 1. Check Java JDK 21
java -version

# 2. Check Node.js and npm
node -v
npm -v
```

If Java JDK 21 is installed at `C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot`:
```powershell
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
```

If Angular CLI is not installed globally:
```powershell
npm install -g @angular/cli
```

If Maven is not installed in PATH:
```powershell
choco install maven -y
# OR: winget install Apache.Maven
```

---

### Step 1: Initialize MySQL Database
1. Open **MySQL Workbench** or your MySQL command-line client.
2. Open the file `database/schema.sql` and execute it to create the database and tables:
   ```sql
   CREATE DATABASE IF NOT EXISTS heritage_walk_db;
   ```
3. Open `database/data.sql` and execute it to populate sample heritage places, walking routes, stops, visitor feedback, and the default admin user.

> **💡 Zero-Config Fallback Note:**
> If you don't have MySQL Server running immediately, the backend includes an embedded `DataLoader.java` and an in-memory **H2 virtual database fallback** configured in `application.properties`. It will auto-seed the identical 6 monuments, 2 trails, and sample reviews on startup with zero configuration!

---

### Step 2: Start the Spring Boot Backend
Open PowerShell in the `backend/` directory:

```powershell
cd C:\Users\rajkumar\.gemini\antigravity\scratch\heritage-walk-guide\backend

# Run with Maven
mvn spring-boot:run
```
*(Or simply double-click `run-backend.bat` / run `.\run-backend.ps1`)*

When started, you will see:
```
==================================================================
  Heritage Walk Guide Backend is RUNNING on http://localhost:8080
  API Base URL: http://localhost:8080/api
==================================================================
```

---

### Step 3: Start the Angular Frontend
Open a second PowerShell window in the `frontend/` directory:

```powershell
cd C:\Users\rajkumar\.gemini\antigravity\scratch\heritage-walk-guide\frontend

# Install dependencies (first time only)
npm install

# Start Angular development server
ng serve --open
```
*(Or double-click `run-frontend.bat` / run `.\run-frontend.ps1`)*

The frontend will open automatically in your browser at:
**http://localhost:4200**

---

## 🧪 Postman API Testing Collection

You can test every endpoint directly using Postman:

### 1. Heritage Places Endpoints
| Method | Endpoint | Description | Expected Status |
| :--- | :--- | :--- | :--- |
| `GET` | `http://localhost:8080/api/places` | Retrieve all heritage monuments | `200 OK` |
| `GET` | `http://localhost:8080/api/places?category=Fort & Palace` | Filter by category | `200 OK` |
| `GET` | `http://localhost:8080/api/places?search=Qutub` | Search by query keyword | `200 OK` |
| `GET` | `http://localhost:8080/api/places/1` | Get single monument details (increments visitCount) | `200 OK` |
| `POST` | `http://localhost:8080/api/places` | Create new monument (Admin) | `201 Created` |
| `PUT` | `http://localhost:8080/api/places/1` | Update monument details | `200 OK` |
| `DELETE` | `http://localhost:8080/api/places/1` | Delete monument | `200 OK` |

**Sample POST /api/places Body (JSON):**
```json
{
  "name": "Safdarjung Tomb",
  "category": "Mausoleum",
  "description": "The last monumental garden tomb of the Mughal empire built in 1754.",
  "historicalBackground": "Built for Nawab Safdarjung, prime minister of the Mughal Empire under Ahmad Shah Bahadur.",
  "historicalSignificance": "Represents the twilight era of Mughal architecture.",
  "yearOrPeriod": "1754 CE",
  "address": "Airforce Golf Course, Delhi Race Club, New Delhi",
  "latitude": 28.5893,
  "longitude": 77.2106,
  "imageUrl": "https://images.unsplash.com/photo-1587474260584-136574528ed5?w=800",
  "openingTime": "07:00 AM",
  "closingTime": "06:00 PM",
  "entryFee": "INR 25",
  "status": "ACTIVE"
}
```

---

### 2. Walking Routes Endpoints
| Method | Endpoint | Description | Expected Status |
| :--- | :--- | :--- | :--- |
| `GET` | `http://localhost:8080/api/routes` | Get all walking routes with stops | `200 OK` |
| `GET` | `http://localhost:8080/api/routes/1` | Get route details | `200 OK` |
| `GET` | `http://localhost:8080/api/routes/1/stops` | Get ordered sequence stops | `200 OK` |
| `POST` | `http://localhost:8080/api/routes/1/stops` | Add a stop to the route | `201 Created` |
| `DELETE` | `http://localhost:8080/api/routes/stops/1` | Remove a stop from the route | `200 OK` |

---

### 3. Visitor Feedback Endpoints
| Method | Endpoint | Description | Expected Status |
| :--- | :--- | :--- | :--- |
| `GET` | `http://localhost:8080/api/feedback` | Retrieve all public visitor reviews | `200 OK` |
| `GET` | `http://localhost:8080/api/feedback/place/1` | Reviews for specific place (Red Fort) | `200 OK` |
| `POST` | `http://localhost:8080/api/feedback` | Submit 5-star rating & comment | `201 Created` |

**Sample POST /api/feedback Body (JSON):**
```json
{
  "heritagePlaceId": 1,
  "visitorName": "Ananya Sharma",
  "visitorEmail": "ananya@example.com",
  "rating": 5,
  "comment": "Breathtaking historical monument! The red sandstone ramparts are awe-inspiring in the golden sunset hour.",
  "visitDate": "2026-10-25"
}
```

---

### 4. Real-Time Analytics Endpoint
| Method | Endpoint | Description | Expected Status |
| :--- | :--- | :--- | :--- |
| `GET` | `http://localhost:8080/api/analytics/overview` | Total places, routes, reviews, avg rating, rating distribution & rankings | `200 OK` |

---

### 5. Admin Authentication
| Method | Endpoint | Description | Expected Status |
| :--- | :--- | :--- | :--- |
| `POST` | `http://localhost:8080/api/auth/login` | Authenticate administrator credentials | `200 OK` |

**Sample POST /api/auth/login Body (JSON):**
```json
{
  "username": "admin",
  "password": "admin123"
}
```

---

## 🔒 Security & Verification Notice

- **BCrypt Password Hashing**: Administrator credentials use industry-standard BCrypt hashing with salt rounds. Real passwords are never stored in plaintext.
- **Input Validation**: Backend validates `@NotNull`, `@NotBlank`, `@Min(1)`, and `@Max(5)` on all submitted feedback and DTOs.
- **Local CORS Protection**: The Spring Boot backend only permits cross-origin requests from authenticated local client addresses (`http://localhost:4200`).
- **SQL Injection Prevention**: Uses Spring Data JPA parameterized queries and Hibernate ORM.
