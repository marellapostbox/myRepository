Yamin - Zumba Management Backend
================================

What I created
--------------
- Maven webapp project named `Yamin`
- Java model classes: `com.yamin.model.Batch`, `com.yamin.model.Participant`
- DAO classes: `com.yamin.dao.BatchDAO`, `com.yamin.dao.ParticipantDAO`
- `ConnectionManager` reads `src/main/resources/db.properties` for DB config
- Servlets: `BatchServlet`, `ParticipantServlet`, `AdminLoginServlet`, `ParticipantLoginServlet`, `LogoutServlet`
- JSP pages to list batches and participants in `src/main/webapp/WEB-INF/jsp/`
- HTML pages for login and add/update forms in `src/main/webapp/`
- SQL schema at `sql/schema.sql`
- `pom.xml` configured for servlet/jsp and MySQL driver

Checklist (what's done)
- [x] Project skeleton and Maven `pom.xml`
- [x] Models, DAOs, repository
- [x] Servlets and HTML/JSP pages
- [x] SQL schema

Checklist (what you need to do locally)
- [ ] Install MySQL and create a database and user (see below)
- [ ] Update `src/main/resources/db.properties` with your DB credentials
- [ ] Run the SQL script `sql/schema.sql` to create tables
- [ ] Build the project with Maven and deploy the generated WAR to Tomcat

Database setup
--------------
1. Edit `src/main/resources/db.properties` and set your MySQL username/password and host if different.

2. Run the SQL script using the MySQL client (PowerShell):

```powershell
mysql -u root -p < "C:\Projects\WorkSpace\Yamin\sql\schema.sql"
```

Replace `root` with your DB user if different.

Build and deploy
----------------
From PowerShell in the project directory `C:\Projects\WorkSpace\Yamin`:

```powershell
# Build the WAR
mvn clean package

# After build, deploy the WAR to Tomcat (adjust path to your Tomcat)
# Example: copy to Tomcat's webapps directory
Copy-Item -Path .\target\Yamin.war -Destination "C:\\path\\to\\tomcat\\webapps\\"
```

Alternatively, import the Maven project into Eclipse and run on a configured Tomcat server.

Default admin credentials (demo)
-------------------------------
- Username: admin
- Password: admin123

Testing endpoints (examples)
----------------------------
Assuming Tomcat serves at http://localhost:8080/Yamin

- View batches (GET):
  http://localhost:8080/Yamin/batches
- Add batch (POST form):
  POST http://localhost:8080/Yamin/batches (fields: name, timing)
- View participants (GET):
  http://localhost:8080/Yamin/participants
- Add participant (POST form):
  POST http://localhost:8080/Yamin/participants (fields: name, email, password, batchId)

Notes and next steps
--------------------
- The project currently stores participant passwords in plaintext for demonstration only — change to hashed passwords for production.
- The admin is hard-coded; you may want to add an `admins` table and DAO for real apps.
- If you want, I can:
  - run compile checks and fix any errors
  - add client-side JavaScript to make PUT/DELETE easier
  - switch packaging to a self-contained executable JAR with embedded server (Spring Boot)

If you want me to continue, tell me which of the next steps you'd like me to do now (run compile, fix errors, add more features, or package differently).
