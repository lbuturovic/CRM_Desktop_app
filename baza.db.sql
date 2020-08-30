BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "users" (
    "id"    INTEGER PRIMARY KEY,
	"name"	TEXT,
	"surname"	TEXT,
	"username"	TEXT,
	"email"	TEXT,
	"password"	TEXT,
	"phoneNumber"  TEXT,
	"department" INTEGER ,
	FOREIGN KEY("department") REFERENCES "department"
);

CREATE TABLE IF NOT EXISTS "accounts" (
    "id"    INTEGER PRIMARY KEY AUTOINCREMENT,
	"name"	TEXT,
	"type"	INTEGER,
	"phone"	TEXT,
	"website"	TEXT,
	"initials"  INTEGER ,
	"updateBy" INTEGER ,
	FOREIGN KEY("initials") REFERENCES "users",
	FOREIGN KEY("type") REFERENCES "accountsType",
	FOREIGN KEY ("updateBy") REFERENCES "users"
);
CREATE TABLE IF NOT EXISTS "contacts" (
    "id"    INTEGER PRIMARY KEY AUTOINCREMENT,
	"name"	TEXT,
	"jobTitle"	TEXT,
	"account"	INTEGER,
	"email"	TEXT,
	"phone" TEXT,
	"initials"  INTEGER ,
	"updateBy" INTEGER ,
	FOREIGN KEY("initials") REFERENCES "users",
	FOREIGN KEY("account") REFERENCES "accounts",
	FOREIGN KEY("updateBy") REFERENCES "users"
);
CREATE TABLE IF NOT EXISTS "department" (
"id"    INTEGER ,
"name"  TEXT,
PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "accountsType" (
"id"    INTEGER ,
"type"  TEXT,
PRIMARY KEY("id")
);
INSERT INTO "department" VALUES(1, 'Marketing');
INSERT INTO "department" VALUES(2, 'Sales');
INSERT INTO "department" VALUES(3, 'Management');
INSERT INTO "accountsType" VALUES(1, 'Analyst');
INSERT INTO "accountsType" VALUES(2, 'Competitor');
INSERT INTO "accountsType" VALUES(3, 'Consultant');
INSERT INTO "accountsType" VALUES(4, 'Customer');
INSERT INTO "accountsType" VALUES(5, 'Dead');
INSERT INTO "accountsType" VALUES(6, 'Press');
INSERT INTO "accountsType" VALUES(7, 'Supplier');
INSERT INTO "accountsType" VALUES(8, 'Suspect');
INSERT INTO "accountsType" VALUES(9, 'Other');
INSERT INTO "users" VALUES (1,'Lejla','Buturović','lbuturovic1','rpr.projekat@gmail.com','leo123','062-138-648',1);
INSERT INTO "users" VALUES (2,'Mirjana','Mirić','leki','lejdi9990@gmail.com','password','000-000-000',1);
INSERT INTO "users" VALUES (3,'Niko','Nikić','niki','niki@gmail.com','password','061-160-300',1);
INSERT INTO "users" VALUES (4,'Selma','Selmić','sele','sele@gmail.com','password','000-000-000',2);
INSERT INTO "users" VALUES (5,'Alma','Almić','almica','almica@gmail.com','password','062-330-765',1);
INSERT INTO "users" VALUES (6,'Emir','Emirić','emirce','emir@gmail.com','password','000-000-000',1);
INSERT INTO "users" VALUES (7,'Marko','Markić','mario','markic@gmail.com','password','000-000-000',3);
INSERT INTO "accounts" VALUES (1,'Brigadier Software',1,'0420 200001','www.brigadiersoftware.co',1,1);
INSERT INTO "accounts" VALUES (2,'Bruton Industrial Metals',2,'0420 200001','www.brigadiersoftware.co',4,5);
INSERT INTO "accounts" VALUES (3,'Kompanija3',3,'0420 200001','www.brigadiersoftware.co',7,2);
INSERT INTO "accounts" VALUES (4,'Kompanija4',3,'0420 200001','www.brigadiersoftware.co',1,4);
INSERT INTO "accounts" VALUES (5,'Kompanija3',4,'0420 200001','www.brigadiersoftware.co',7,5);
INSERT INTO "accounts" VALUES (6,'Kompanija4',7,'0420 200001','www.brigadiersoftware.co',1,1);
INSERT INTO "accounts" VALUES (7,'Kompanija3',5,'0420 200001','www.brigadiersoftware.co',7,1);
INSERT INTO "accounts" VALUES (8,'Kompanija4',4,'0420 200001','www.brigadiersoftware.co',1,1);
INSERT INTO "contacts" VALUES (1,'Susan Jones','Owner',2, 'lejla_0303@hotmail.com','064-528-445',1,1);
INSERT INTO "contacts" VALUES (2,'Jonny Grade','Sales Manager',2, 'diana_buturovic@hotmail.com','064-528-445',1,2);
INSERT INTO "contacts" VALUES (3,'Adam Sandler','Owner',3, 'lejla_0303@hotmail.com','064-528-445',1,4);
INSERT INTO "contacts" VALUES (4,'Sandra Jones','Owner',5, 'lejla_0303@hotmail.com','064-528-445',1,1);
INSERT INTO "contacts" VALUES (5,'Simon Bergel','Sales Director',1, 'lejla_0303@hotmail.com','064-528-445',1,4);
INSERT INTO "contacts" VALUES (6,'Diana Stanton','Owner',1, 'diana_buturovic@hotmail.com','064-528-445',1,4);
INSERT INTO "contacts" VALUES (7,'Jennifer Aniston','Owner',4, 'lejla_0303@hotmail.com','064-528-445',1,4);
INSERT INTO "contacts" VALUES (8,'Brad Pitt','Owner',8, 'lejla_0303@hotmail.com','064-528-445',4,3);
COMMIT;
