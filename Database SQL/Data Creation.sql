/* 
UCCD2303/UCCD2203 Group script submission
Group number: G23
Group member 1 : Chen Si Yang 2101599 CS
Group member 2 : Goh Brian Joon Jian 2002750 CS
Group member 3 : Teo Teck Wan 2001750 CS
Group member 4 : Yeo Pei Han 2103940 CS
Submission date: 21-Apr-2023

Number of table: 10
Number of records in Person table: 21
Number of records in Invoice table: 10
Number of records in Room table: 10
Number of records in Admission table: 11
Number of records in Operation table: 6
Number of records in Appointment table: 15
Number of records in Shift table: 10
Number of records in Patient table: 10
Number of records in Staff table: 11
Number of records in Department table: 3

*/

DROP TABLE Person CASCADE CONSTRAINTS;
DROP TABLE Invoice CASCADE CONSTRAINTS;
DROP TABLE Room CASCADE CONSTRAINTS;
DROP TABLE Admission CASCADE CONSTRAINTS;
DROP TABLE Operation CASCADE CONSTRAINTS;
DROP TABLE Appointment CASCADE CONSTRAINTS;
DROP TABLE Shift CASCADE CONSTRAINTS;
DROP TABLE Patient CASCADE CONSTRAINTS;
DROP TABLE Staff CASCADE CONSTRAINTS;
DROP TABLE Department CASCADE CONSTRAINTS;

DROP ROLE hospital_staff;
DROP USER TeoTeckWan CASCADE;
DROP USER LeeYingJia CASCADE;
DROP USER LeeLeiEn CASCADE;
DROP USER ChinCheahChen CASCADE;
DROP USER LiewJinWei CASCADE;
DROP USER GohBrian CASCADE;
DROP USER LingXueEr CASCADE;
DROP USER LeeShawn CASCADE;
DROP USER TanZhiShan CASCADE;
DROP USER ChewWanXin CASCADE;
DROP USER CheahKaiJun CASCADE;

CREATE TABLE Person
(Person_ID VARCHAR2(6) CONSTRAINT person_person_id_pk PRIMARY KEY,
fName VARCHAR2(30) NOT NULL,
lName VARCHAR2(30) NOT NULL,
Gender CHAR(1) NOT NULL,
NRIC VARCHAR2(12) NOT NULL,
Email VARCHAR2(30),
Contact_No VARCHAR2(11) NOT NULL,
Street_Name VARCHAR2(50),
City VARCHAR2(20),
Zip_Code VARCHAR2(10),
State VARCHAR2(20),
Country VARCHAR2(20)
);

CREATE TABLE Room
(Room_No VARCHAR2(4) CONSTRAINT ward_ward_code_pk PRIMARY KEY,
Room_Type VARCHAR2(20) NOT NULL CONSTRAINT room_room_type_cc CHECK (Room_Type IN ('Normal','ICU', 'Deluxe', 'General', 'Burn')),
Daily_charge NUMBER(8,2) NOT NULL,
Bed_count NUMBER(3) NOT NULL,
Availability NUMBER(3) NOT NULL,
Gender CHAR(1) NOT NULL CONSTRAINT room_gender_cc CHECK (Gender IN ('F','M', 'U'))
);

CREATE TABLE Shift
(Shift_ID VARCHAR2(6) CONSTRAINT shift_shift_id_pk PRIMARY KEY,
Shift_day VARCHAR2(20) NOT NULL,
Start_time CHAR(4) NOT NULL,
End_time CHAR(4) NOT NULL
);

CREATE TABLE Patient
(Patient_ID VARCHAR2(6) CONSTRAINT patient_patient_id_pk PRIMARY KEY,
Blood_Type CHAR(8) NOT NULL,
Next_kin_fName VARCHAR2(30) NOT NULL,
Next_kin_lName VARCHAR2(30) NOT NULL,
Next_kin_contact_no VARCHAR2(11) NOT NULL,
Next_kin_relationship VARCHAR2(10),
CONSTRAINT patient_fk FOREIGN KEY(Patient_ID) REFERENCES Person(Person_ID)
);

CREATE TABLE Invoice
(Invoice_No VARCHAR2(8) CONSTRAINT invoice_invoice_no_pk PRIMARY KEY,
Patient_ID VARCHAR2(6) NOT NULL CONSTRAINT invoice_patient_id_fk REFERENCES patient(Patient_ID),
Status CHAR(1) NOT NULL,
Issue_Date TIMESTAMP DEFAULT SYSDATE NOT NULL,
Payment_Date TIMESTAMP,
Total_Payment NUMBER(10,2) NOT NULL,
Payment_Method VARCHAR2(20)
);

CREATE TABLE Department
(Department_ID VARCHAR2(6) CONSTRAINT department_department_id_pk PRIMARY KEY,
HOD VARCHAR2(6),
Description VARCHAR2(100) NOT NULL
);

CREATE TABLE Staff
(Staff_ID VARCHAR2(6) CONSTRAINT staff_staff_id_pk PRIMARY KEY,
Hire_date DATE DEFAULT SYSDATE NOT NULL,
End_date DATE,
Salary NUMBER(10,2) NOT NULL,
Position VARCHAR2(20) NOT NULL,
Shift_ID VARCHAR2(6) NOT NULL CONSTRAINT staff_shift_id_fk REFERENCES Shift(Shift_ID),
Department_ID VARCHAR2(6) NOT NULL CONSTRAINT staff_department_id_fk REFERENCES Department(Department_ID)
);

ALTER TABLE Department
ADD (CONSTRAINT department_hod_fk FOREIGN KEY (HOD) REFERENCES Staff(Staff_ID));

CREATE TABLE Appointment
(Appointment_No VARCHAR2(6) CONSTRAINT appointment_appontment_no_pk PRIMARY KEY,
Doctor_ID VARCHAR2(6) NOT NULL CONSTRAINT appointment_doctor_id_fk REFERENCES Staff(Staff_ID),
Patient_ID VARCHAR2(6) NOT NULL CONSTRAINT appointment_patient_id_fk REFERENCES Patient(Patient_ID),
Appointment_Date DATE NOT NULL,
Appointment_Start_Time CHAR(4) NOT NULL,
Appointment_End_Time CHAR(4) NOT NULL
);

CREATE TABLE Admission
(Admission_ID VARCHAR2(6) CONSTRAINT admission_admission_id_pk PRIMARY KEY,
Room_No VARCHAR2(4) NOT NULL CONSTRAINT admission_room_no_fk REFERENCES Room(Room_No),
Patient_ID VARCHAR2(6) NOT NULL CONSTRAINT admission_patient_id_fk REFERENCES Patient(Patient_ID),
Doctor_ID VARCHAR2(6) NOT NULL CONSTRAINT admission_doctor_id_fk REFERENCES Staff(Staff_ID),
Nurse_ID VARCHAR2(6) NOT NULL CONSTRAINT admission_nurse_id_fk REFERENCES Staff(Staff_ID),
Disease VARCHAR2(30) NOT NULL,
Enrollment_Date TIMESTAMP DEFAULT SYSDATE NOT NULL ,
Discharge_Date TIMESTAMP
);

CREATE TABLE Operation
(Operation_ID VARCHAR2(6) CONSTRAINT operation_operation_id_pk PRIMARY KEY,
Start_Date TIMESTAMP DEFAULT SYSDATE NOT NULL,
End_Date TIMESTAMP, 
Operation_Charge NUMBER(8,2) NOT NULL,
Operation_Desc VARCHAR2(200) NOT NULL,
Admission_ID VARCHAR2(6) NOT NULL CONSTRAINT operation_admission_id_fk REFERENCES Admission(Admission_ID),
Surgeon_ID VARCHAR2(6) NOT NULL CONSTRAINT operation_surgeon_id_fk REFERENCES Staff(Staff_ID),
Anesthesiste_ID VARCHAR2(6) NOT NULL CONSTRAINT operation_anesthelist_id_fk REFERENCES Staff(Staff_ID)
);

---- inserting into PERSON table
INSERT INTO person VALUES ('PAT001', 'Tan', 'Hui En', 'F', '870912083246', 'huien0912@gmail.com', '0104517358', 'Batu Putih', 'Kampar', '31900', 'Perak', 'Malaysia');
INSERT INTO person VALUES ('PAT002', 'Wang', 'Ying Qi', 'M', '930714056755', 'yingqiw@hotmail.com', '0161259465', 'Benus', 'Bentong', '28700', 'Pahang', 'Malaysia');
INSERT INTO person VALUES ('PAT003', 'Khoo', 'Jun Kai', 'M', '000101670977', 'junkai101@gmail.com', '01194561237', 'Jalan Indah', 'Shah Alam', '72110', 'Selangor', 'Malaysia');
INSERT INTO person VALUES ('PAT004', 'Lee', 'Jia En', 'F', '030204011642', 'jiaen0204@hotmail.com', '0129456587', 'Rural Road', 'Song Phi Nong', '84000', 'Suphan Buri', 'Thailand');
INSERT INTO person VALUES ('PAT005', 'Gan', 'Wei Ming', 'M', '940816073149', 'weiming816@gmail.com', '0179563325', 'Cengal, Lorong', 'Butterworth', '13400', 'Penang', 'Malaysia');
INSERT INTO person VALUES ('PAT006', 'Loh', 'Han Wei', 'M', '880626742685', 'lohhw626@yahoo.com', '0184652915', 'Xihu Road', 'GuangZhou', '510030', 'Yue Xiu', 'China');
INSERT INTO person VALUES ('PAT007', 'Kwok', 'Shu Qi', 'F', '970529118312', 'shuqi0529@yahoo.com', '0195436616', 'Jalan Datok', 'Cukai', '24000', 'Terengganu', 'Malaysia');
INSERT INTO person VALUES ('PAT008', 'Teoh', 'Wen Chong', 'M', '791107120137', NULL, '01194568462', 'Bangunan Asia City', 'Kota Kinabalu', '88000', 'Sabah', 'Malaysia');
INSERT INTO person VALUES ('PAT009', 'Ng', 'Jia Yee', 'F', '840911136452', 'ngjyee911@gmail.com', '0186542210', 'Astana, Jalansa', 'Kuching', '93050', 'Sarawak', 'Malaysia');
INSERT INTO person VALUES ('PAT010', 'Chai', 'Wing Yan', 'F', '781214063378', NULL, '0169874655', 'Seri Damai Jaya, Perumahan', 'Kuantan', '25150', 'Pahang', 'Malaysia');
INSERT INTO person VALUES ('DST001', 'Teo', 'Teck Wan', 'M', '010827130655', 'tteckw0827@gmail.com', '01159570553', 'Lambir', 'Miri', '98000', 'Sarawak', 'Malaysia');
INSERT INTO person VALUES ('DST002', 'Lee', 'Ying Jia', 'F', '011105130842', 'tyingj1105@gmail.com', '0187993189', 'Batu Niah', 'Niah', '98200', 'Sarawak', 'Malaysia');
INSERT INTO person VALUES ('DST003', 'Lee', 'Lei En', 'F', '020702012708', 'leien0207@gmail.com', '0167953352', 'Parit Baru', 'Yong Peng', '83700', 'Johor', 'Malaysia');
INSERT INTO person VALUES ('DST004', 'Chin', 'Cheah Chen', 'M', '890215091321', 'ccc0215@hotmail.com', '0105875487', 'Guar Sanji', 'Arau', '2600', 'Perlis', 'Malaysia');
INSERT INTO person VALUES ('DST005', 'Liew', 'Jin Wei', 'M', '880513010743', 'jinwei513@yahoo.com', '01143965420', 'Parit Baru', 'Yong Peng', '83700', 'Johor', 'Malaysia');
INSERT INTO person VALUES ('NST001', 'Goh', 'Brian', 'M', '991018076573', 'briangh1018@gmail.com', '0126038397', 'Jabatan Kimia', 'Pulau Pinang', '10662', 'Penang', 'Malaysia');
INSERT INTO person VALUES ('NST002', 'Ling', 'Xue Er', 'F', '010704108846', 'xuer0704@hotmail.com', '0167030862', 'IOI', 'Puchong', '47170', 'Selangor', 'Malaysia');
INSERT INTO person VALUES ('SST001', 'Lee', 'Shawn', 'M', '870305071577', 'leeshawn35@yahoo.com', '0175201314', 'Bandar Sunway', 'Perai', 13700, 'Penang', 'Malaysia');
INSERT INTO person VALUES ('SST002', 'Tan', 'Zhi Shan', 'F', '940918106628', 'zhshan918@hotmail.com', '0186211549', 'USJ 18', 'Subang Jaya', '47630', 'Selangor', 'Malaysia');
INSERT INTO person VALUES ('AST001', 'Chew', 'Wan Xin', 'F', '790831016412', 'wxin0831@hotmail.com', '0176089981', 'Bukit Tinggi', 'Klang', '41200', 'Selangor', 'Malaysia');
INSERT INTO person VALUES ('AST002', 'Cheah', 'Kai Jun', 'M', '771227145961', 'kaijun127@gmail.com', '01157491315', 'Hussin Onn, Taman', 'Cheras', '43200', 'Kuala Lumpur', 'Malaysia');

---- inserting into ROOM table
INSERT INTO room VALUES ('R001', 'Normal', 20.00, 60, 32, 'M');
INSERT INTO room VALUES ('R002', 'Normal', 22.00, 40, 15, 'M');
INSERT INTO room VALUES ('R003', 'Normal', 20.00, 60, 35, 'F');
INSERT INTO room VALUES ('R004', 'Normal', 22.00, 40, 22, 'F');
INSERT INTO room VALUES ('R005', 'Deluxe', 30.00, 10, 2, 'U');
INSERT INTO room VALUES ('R006', 'General', 25.00, 20, 7, 'U');
INSERT INTO room VALUES ('R007', 'Burn', 25.00, 15, 11, 'U');
INSERT INTO room VALUES ('R008', 'ICU', 40.00, 20, 16, 'M');
INSERT INTO room VALUES ('R009', 'ICU', 40.00, 20, 17, 'F');
INSERT INTO room VALUES ('R010', 'ICU', 35.00, 30, 22, 'U');

---- inserting into SHIFT table
INSERT INTO shift VALUES ('SFT001', 'MTuWThF', '1200', '2200');
INSERT INTO shift VALUES ('SFT002', 'MTuWThF', '0800', '1800');
INSERT INTO shift VALUES ('SFT003', 'MTuWThFSaSu', '0800', '1800');
INSERT INTO shift VALUES ('SFT004', 'MTuWThFSa', '0800', '1800');
INSERT INTO shift VALUES ('SFT005', 'MTuWThFSaSu', '0800', '1800');
INSERT INTO shift VALUES ('SFT006', 'MTuWThFSaSu', '1200', '2200');
INSERT INTO shift VALUES ('SFT007', 'MTuWThF', '1400', '0000');
INSERT INTO shift VALUES ('SFT008', 'MTuWThFSa', '1200', '2200');
INSERT INTO shift VALUES ('SFT009', 'MTuWThFSaSu', '1400', '0000');
INSERT INTO shift VALUES ('SFT010', 'MTuWThFSa', '1400', '0000');

---- inserting into PATIENT table
INSERT INTO patient VALUES ('PAT001', 'A+', 'Tan', 'Zhe Kai', '0125671245', 'Father');
INSERT INTO patient VALUES ('PAT002', 'O-', 'Lim', 'Jia Yi', '0186592354', 'Mother');
INSERT INTO patient VALUES ('PAT003', 'AB+', 'Khoo', 'Liu Sheng', '01154251010', 'Father');
INSERT INTO patient VALUES ('PAT004', 'AB-', 'Lau', 'Shu Fang', '0169676564', 'Mother');
INSERT INTO patient VALUES ('PAT005', 'B-', 'Gan', 'Wei Xian', '0181105956', NULL);
INSERT INTO patient VALUES ('PAT006', 'A+', 'Lee', 'Sin Hui', '0107652315', 'Daughter');
INSERT INTO patient VALUES ('PAT007', 'B-', 'Kwok', 'Teck You', '0175692112', 'Brother');
INSERT INTO patient VALUES ('PAT008', 'B+', 'Teoh', 'Wen Kai', '0175650039', 'Son');
INSERT INTO patient VALUES ('PAT009', 'O+', 'Ng', 'Zi En', '0167711512', 'Sister');
INSERT INTO patient VALUES ('PAT010', 'O-', 'Poh', 'Zen Yang', '01161452021', 'Son');

---- inserting into INVOICE table
INSERT INTO invoice VALUES ('INV00001', 'PAT001', 'P', TO_DATE('30-10-2018 19:40:49', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('30-10-2018 19:43:42', 'DD-MM-YYYY HH24:MI:SS'), 151.29, 'Credit Card');
INSERT INTO invoice VALUES ('INV00002', 'PAT002', 'P', TO_DATE('12-11-2018 09:30:05', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('12-11-2018 09:32:52', 'DD-MM-YYYY HH24:MI:SS'), 67.45, 'Cash');
INSERT INTO invoice VALUES ('INV00003', 'PAT003', 'P', TO_DATE('16-01-2019 14:12:00', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('16-01-2019 14:14:26', 'DD-MM-YYYY HH24:MI:SS'), 785.00, 'Online Banking');
INSERT INTO invoice VALUES ('INV00004', 'PAT004', 'P', TO_DATE('03-03-2019 16:40:20', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('03-03-2019 16:43:00', 'DD-MM-YYYY HH24:MI:SS'), 50.00, 'Cash');
INSERT INTO invoice VALUES ('INV00005', 'PAT005', 'N', TO_DATE('29-04-2019 20:00:00', 'DD-MM-YYYY HH24:MI:SS'), NULL, 25.90, NULL);
INSERT INTO invoice VALUES ('INV00006', 'PAT006', 'P', TO_DATE('27-12-2019 21:49:56', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('27-12-2019 21:51:23', 'DD-MM-YYYY HH24:MI:SS'), 1505.00, 'Credit Card');
INSERT INTO invoice VALUES ('INV00007', 'PAT007', 'N', TO_DATE('04-04-2020 10:12:26', 'DD-MM-YYYY HH24:MI:SS'), NULL, 19.90, NULL);
INSERT INTO invoice VALUES ('INV00008', 'PAT008', 'P', TO_DATE('19-06-2020 08:37:49', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('19-06-2020 08:41:00', 'DD-MM-YYYY HH24:MI:SS'), 49.90, 'Cash');
INSERT INTO invoice VALUES ('INV00009', 'PAT009', 'P', TO_DATE('26-08-2020 18:26:32', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('26-08-2020 18:29:07', 'DD-MM-YYYY HH24:MI:SS'), 35.00, 'Online Banking');
INSERT INTO invoice VALUES ('INV00010', 'PAT010', 'P', TO_DATE('05-12-2020 17:34:29', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('05-12-2020 17:36:25', 'DD-MM-YYYY HH24:MI:SS'), 1030.00, 'Credit Card');

---- inserting into DEPARTMENT table
INSERT INTO department VALUES ('DPT001', NULL, 'General Surgery Department');
INSERT INTO department VALUES ('DPT002', NULL, 'Physiotherapy Department');
INSERT INTO department VALUES ('DPT003', NULL, 'Emergency Department');

---- inserting into STAFF table
INSERT INTO staff VALUES ('DST001', TO_DATE('12-08-2009', 'DD-MM-YYYY'), NULL, 7000.00, 'Doctor', 'SFT004', 'DPT003');
INSERT INTO staff VALUES ('DST002', TO_DATE('05-05-2008', 'DD-MM-YYYY'), NULL, 8500.00, 'Doctor (HOD)', 'SFT007', 'DPT002');
INSERT INTO staff VALUES ('DST003', TO_DATE('12-07-2009', 'DD-MM-YYYY'), TO_DATE('03-06-2015', 'DD-MM-YYYY'), 6900.00, 'Doctor', 'SFT001', 'DPT001');
INSERT INTO staff VALUES ('DST004', TO_DATE('03-03-1999', 'DD-MM-YYYY'), NULL, 8300.00, 'Doctor (HOD)', 'SFT002', 'DPT003');
INSERT INTO staff VALUES ('DST005', TO_DATE('27-12-1998', 'DD-MM-YYYY'), NULL, 8700.00, 'Doctor (HOD)', 'SFT007', 'DPT001');
INSERT INTO staff VALUES ('NST001', TO_DATE('01-02-2014', 'DD-MM-YYYY'), NULL, 6000.00, 'Nurse', 'SFT003', 'DPT003');
INSERT INTO staff VALUES ('NST002', TO_DATE('26-04-2012', 'DD-MM-YYYY'), NULL, 5750.00, 'Nurse', 'SFT008', 'DPT002');
INSERT INTO staff VALUES ('SST001', TO_DATE('27-09-2005', 'DD-MM-YYYY'), NULL, 11550.00, 'Surgeon', 'SFT003', 'DPT001');
INSERT INTO staff VALUES ('SST002', TO_DATE('02-08-2006', 'DD-MM-YYYY'), NULL, 12150.00, 'Surgeon', 'SFT009', 'DPT003');
INSERT INTO staff VALUES ('AST001', TO_DATE('27-09-2005', 'DD-MM-YYYY'), NULL, 8250.00, 'Anesthetist', 'SFT005', 'DPT003');
INSERT INTO staff VALUES ('AST002', TO_DATE('16-10-2010', 'DD-MM-YYYY'), NULL, 8425.00, 'Anesthetist', 'SFT006', 'DPT001');

---- update record into DEPARTMENT table
UPDATE department SET HOD = 'DST005' WHERE Department_id = 'DPT001';
UPDATE department SET HOD = 'DST002' WHERE Department_id = 'DPT002';
UPDATE department SET HOD = 'DST004' WHERE Department_id = 'DPT003';

ALTER TABLE Department MODIFY (HOD NOT NULL);

---- inserting into APPOINTMENT table
INSERT INTO appointment VALUES ('AP0001', 'DST004', 'PAT001', TO_DATE('30-10-2018', 'DD-MM-YYYY'), '1921', '1935');
INSERT INTO appointment VALUES ('AP0002', 'DST001', 'PAT002', TO_DATE('12-11-2018', 'DD-MM-YYYY'), '0900', '0905');
INSERT INTO appointment VALUES ('AP0003', 'DST005', 'PAT003', TO_DATE('16-01-2019', 'DD-MM-YYYY'), '1400', '1405');
INSERT INTO appointment VALUES ('AP0004', 'DST004', 'PAT004', TO_DATE('03-03-2019', 'DD-MM-YYYY'), '1629', '1636');
INSERT INTO appointment VALUES ('AP0005', 'DST002', 'PAT005', TO_DATE('29-04-2019', 'DD-MM-YYYY'), '1930', '1935');
INSERT INTO appointment VALUES ('AP0006', 'DST005', 'PAT006', TO_DATE('27-12-2019', 'DD-MM-YYYY'), '2144', '2148');
INSERT INTO appointment VALUES ('AP0007', 'DST002', 'PAT007', TO_DATE('04-04-2020', 'DD-MM-YYYY'), '1005', '1010');
INSERT INTO appointment VALUES ('AP0008', 'DST001', 'PAT008', TO_DATE('19-06-2020', 'DD-MM-YYYY'), '0830', '0835');
INSERT INTO appointment VALUES ('AP0009', 'DST004', 'PAT009', TO_DATE('26-08-2020', 'DD-MM-YYYY'), '1756', '1809');
INSERT INTO appointment VALUES ('AP0010', 'DST005', 'PAT010', TO_DATE('05-12-2020', 'DD-MM-YYYY'), '1728', '1733');
INSERT INTO appointment VALUES ('AP0011', 'DST005', 'PAT010', TO_DATE('23-05-2021', 'DD-MM-YYYY'), '1005', '1020');
INSERT INTO appointment VALUES ('AP0012', 'DST001', 'PAT008', TO_DATE('23-06-2022', 'DD-MM-YYYY'), '1800', '1810');
INSERT INTO appointment VALUES ('AP0013', 'DST004', 'PAT009', TO_DATE('11-09-2022', 'DD-MM-YYYY'), '2100', '2105');
INSERT INTO appointment VALUES ('AP0014', 'DST002', 'PAT004', TO_DATE('14-03-2023', 'DD-MM-YYYY'), '1415', '1420');
INSERT INTO appointment VALUES ('AP0015', 'DST005', 'PAT003', TO_DATE('24-04-2023', 'DD-MM-YYYY'), '0915', '0925');
INSERT INTO appointment VALUES ('AP0016', 'DST005', 'PAT006', TO_DATE('28-04-2023', 'DD-MM-YYYY'), '1600', '1620');


---- inserting into ADMISSION table
INSERT INTO admission VALUES ('AD0001', 'R007', 'PAT001', 'DST004', 'NST001', 'Third-Degree Sunburn', TO_DATE('30-10-2018 19:46:00', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('30-10-2018 21:00:06', 'DD-MM-YYYY HH24:MI:SS'));
INSERT INTO admission VALUES ('AD0002', 'R010', 'PAT006', 'DST005', 'NST001', 'Heart Attack', TO_DATE('27-12-2019 21:55:11', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('12-01-2020 09:15:11', 'DD-MM-YYYY HH24:MI:SS'));
INSERT INTO admission VALUES ('AD0003', 'R002', 'PAT006', 'DST002', 'NST002', 'Anal Cancer', TO_DATE('12-01-2019 09:18:00', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('20-01-2019 13:12:56', 'DD-MM-YYYY HH24:MI:SS'));
INSERT INTO admission VALUES ('AD0004', 'R004', 'PAT004', 'DST004', 'NST002', 'Knee Injury', TO_DATE('03-03-2019 16:50:00', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('04-03-2019 21:43:19', 'DD-MM-YYYY HH24:MI:SS'));
INSERT INTO admission VALUES ('AD0005', 'R001', 'PAT008', 'DST001', 'NST002', 'Diabetes', TO_DATE('19-06-2020 08:50:23', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('19-06-2020 19:21:47', 'DD-MM-YYYY HH24:MI:SS'));
INSERT INTO admission VALUES ('AD0006', 'R004', 'PAT009', 'DST004', 'NST002', 'Chickenpox', TO_DATE('26-08-2020 18:35:00', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('26-08-2020 20:12:07', 'DD-MM-YYYY HH24:MI:SS'));
INSERT INTO admission VALUES ('AD0007', 'R010', 'PAT010', 'DST005', 'NST001', 'Heart Attack', TO_DATE('05-12-2020 17:40:01', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('21-12-2020 14:21:26', 'DD-MM-YYYY HH24:MI:SS'));
INSERT INTO admission VALUES ('AD0008', 'R006', 'PAT002', 'DST001', 'NST001', 'Cholera', TO_DATE('12-11-2018 09:35:42', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('12-11-2018 22:45:12', 'DD-MM-YYYY HH24:MI:SS'));
INSERT INTO admission VALUES ('AD0009', 'R009', 'PAT009', 'DST002', 'NST002', 'Fever', TO_DATE('26-08-2020 20:15:00', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('27-08-2020 07:23:53', 'DD-MM-YYYY HH24:MI:SS'));
INSERT INTO admission VALUES ('AD0010', 'R005', 'PAT003', 'DST005', 'NST001', 'Pancreatic Cancer', TO_DATE('16-01-2019 14:16:13', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('28-02-2019 10:56:34', 'DD-MM-YYYY HH24:MI:SS'));
INSERT INTO admission VALUES ('AD0011', 'R001', 'PAT003', 'DST005', 'NST001', 'Seafood Allergy', TO_DATE('24-04-2023 11:17:20', 'DD-MM-YYYY HH24:MI:SS'), NULL);

---- inserting into OPERATION table
INSERT INTO operation VALUES ('OPT001', TO_DATE('30-10-2018 19:50:00', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('30-10-2018 20:54:23', 'DD-MM-YYYY HH24:MI:SS'), 125.49, 'Perform a skin graft for the Third-Degree Sunburn, Successful in a result', 'AD0001', 'SST001', 'AST001');
INSERT INTO operation VALUES ('OPT002', TO_DATE('27-12-2019 22:00:10', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('28-12-2019 01:12:26', 'DD-MM-YYYY HH24:MI:SS'), 429.79, 'Perform a heart attack surgery, Successful in a result, Stay for observation', 'AD0002', 'SST001', 'AST002');
INSERT INTO operation VALUES ('OPT003', TO_DATE('15-01-2019 09:20:16', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('15-01-2019 12:06:43', 'DD-MM-YYYY HH24:MI:SS'), 314.99, 'Perform a colon and rectal surgery, Successful in a result, Stay for observation', 'AD0003', 'SST002', 'AST002');
INSERT INTO operation VALUES ('OPT004', TO_DATE('19-06-2020 08:51:19', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('19-06-2020 11:08:01', 'DD-MM-YYYY HH24:MI:SS'), 250.00, 'Perform a diabetes surgery, Successful in a result', 'AD0005', 'SST001', 'AST002');
INSERT INTO operation VALUES ('OPT005', TO_DATE('05-12-2020 17:41:10', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('05-12-2020 20:51:28', 'DD-MM-YYYY HH24:MI:SS'), 429.79, 'Perform a heart attack surgery, Successful in a result, Stay for observation', 'AD0007', 'SST002', 'AST001');
INSERT INTO operation VALUES ('OPT006', TO_DATE('16-01-2019 15:01:07', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('16-01-2019 18:26:43', 'DD-MM-YYYY HH24:MI:SS'), 299.99, 'Perform a Palliative surgery, Successful in a result, Stay for observation', 'AD0010', 'SST002', 'AST001');

CREATE ROLE hospital_staff;
GRANT GRANT ANY PRIVILEGE TO hospital_staff;
GRANT CREATE SESSION TO hospital_staff;

CREATE USER TeoTeckWan IDENTIFIED BY 010827130655;
GRANT hospital_staff TO TeoTeckWan;
CREATE USER LeeYingJia IDENTIFIED BY 011105130842;
GRANT hospital_staff TO LeeYingJia;
CREATE USER LeeLeiEn IDENTIFIED BY 020702012708;
GRANT hospital_staff TO LeeLeiEn;
CREATE USER ChinCheahChen IDENTIFIED BY 890215091321;
GRANT hospital_staff TO ChinCheahChen;
CREATE USER LiewJinWei IDENTIFIED BY 880513010743;
GRANT hospital_staff TO LiewJinWei;
CREATE USER GohBrian IDENTIFIED BY 991018076573;
GRANT hospital_staff TO GohBrian;
CREATE USER LingXueEr IDENTIFIED BY 010704108846;
GRANT hospital_staff TO LingXueEr;
CREATE USER LeeShawn IDENTIFIED BY 870305071577;
GRANT hospital_staff TO LeeShawn;
CREATE USER TanZhiShan IDENTIFIED BY 940918106628;
GRANT hospital_staff TO TanZhiShan;
CREATE USER ChewWanXin IDENTIFIED BY 790831016412;
GRANT hospital_staff TO ChewWanXin;
CREATE USER CheahKaiJun IDENTIFIED BY 771227145961;
GRANT hospital_staff TO CheahKaiJun;

COMMIT;
