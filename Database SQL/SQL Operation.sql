/* 
UCCD2303/UCCD2203 assignment
Individual script submission

Group number: G23
Group member 3 : Teo Teck Wan 2001750 CS
Submission date: 25-Apr-23

Number of working query without syntax error: 2
Number of working stored procedure without syntax error: 2
Number of working function without syntax error: 2
*/


/* Query 1: List out the late payment invoice */

SELECT p.patient_id "Patient ID", pat.fName || ' ' || pat.lName "Patient Full Name", TRUNC(SYSDATE - CAST(i.issue_date AS DATE)) "Delayed (Days)", 'RM ' ||  TO_CHAR(i.total_payment, 'fm99D00') "Total Payment", pat.email "Patient Email", SUBSTR(pat.contact_no,1,3) || '-' || SUBSTR(pat.contact_no,4,8) "Patient Contact No.", pat.street_name || ', ' || pat.zip_code || ' ' || pat.city || ', ' || pat.state || ', ' || pat.country "Patient Address", p.next_kin_fName || ' ' || p.next_kin_lName "Next Kin Full Name",  SUBSTR(p.next_kin_contact_no,1,3) || '-' || SUBSTR(p.next_kin_contact_no,4,8) "Next Kin Contact No.", p.next_kin_relationship
FROM Person pat, Patient p, Invoice i, Appointment a
WHERE pat.person_id = p.patient_id
AND p.patient_id = i.patient_id
AND p.patient_id = a.patient_id
AND SYSDATE > TO_CHAR(a.appointment_date, 'DD-MON-RR')
AND i.status = 'N'
AND SYSDATE > ADD_MONTHS(TO_CHAR(i.issue_date, 'DD-MON-RR'), 1)
ORDER BY p.patient_id;


/* Query 2: List out all the staffs that currently working in the hospital, working experience for this hospital (Seniority), and their department and shift respectively, and sorted in ascending order for staff id */

CREATE OR REPLACE PUBLIC SYNONYM sta FOR Staff;
CREATE OR REPLACE PUBLIC SYNONYM dep FOR Department;
CREATE OR REPLACE PUBLIC SYNONYM shi FOR Shift;

SELECT sta.staff_id, sta.position, dep.description, shi.shift_day || ' ' || shi.start_time || ' - ' || shi.end_time "Shift Time", TRUNC((SYSDATE - CAST(sta.hire_date AS DATE))/365.25) "Seniority in Year(s)"
FROM sta, dep, shi
WHERE sta.department_id = dep.department_id
AND sta.shift_id = shi.shift_id
AND sta.end_date IS NULL
ORDER BY sta.staff_id;


/* Stored procedure 1: Update Patient Room (For the patient who has not discharged yet) */

CREATE OR REPLACE PROCEDURE UpdateRoom(
	cAdmission_ID in VARCHAR2,
	currentRoom in VARCHAR2,
	cRoom_No in VARCHAR2
)
IS
cDischarge Admission.Discharge_Date%TYPE;
cGender Person.Gender%TYPE;
rGender Room.Gender%TYPE;
cAvailability Room.Availability%TYPE;
cCharge Room.daily_charge%TYPE;

BEGIN
	SELECT Discharge_Date INTO cDischarge
	FROM Admission
	Where Admission_ID = cAdmission_ID;

	SELECT Gender INTO cGender
	FROM Person
	Where Person_id = (SELECT Patient_ID from Admission where Admission_ID = cAdmission_ID);

	SELECT Availability, Gender, Daily_Charge INTO cAvailability, rGender, cCharge
	FROM Room
	WHERE Room_no = cRoom_no;
	
	-- Check if the patient has already discharge or not (NULL = Not Discharge Yet)
	IF cDischarge IS NULL THEN
		-- Check if the patient's gender is correct with room's gender or match 'U'
		IF rGender = cGender OR rGender = 'U' THEN
			-- Check if the bed availability in that room no.
			IF cAvailability <> 0 THEN
				UPDATE Room
					SET Availability = Availability + 1
					WHERE Room_no = currentRoom;

				UPDATE Room
					SET Availability = Availability - 1
					WHERE Room_no = cRoom_no;
	
				UPDATE Admission
					SET Room_no = cRoom_no
					WHERE Admission_ID = cAdmission_ID;
		
				DBMS_OUTPUT.PUT_LINE('');
				DBMS_OUTPUT.PUT_LINE('Patient''s request is updated successfully.');
				DBMS_OUTPUT.PUT_LINE('Room''s Daily Charged updated to RM' || to_char(cCharge, '99.99'));
			ELSE
				DBMS_OUTPUT.PUT_LINE('');
				DBMS_OUTPUT.PUT_LINE('Room''s Bed is not availability.');
			END IF;
		ELSE
			DBMS_OUTPUT.PUT_LINE('');
			DBMS_OUTPUT.PUT_LINE('Patient''s Gender is not match with the Room''s Gender.');
		END IF;
	ELSE
		DBMS_OUTPUT.PUT_LINE('');
		DBMS_OUTPUT.PUT_LINE('Patient has ALREADY discharged.');
	END IF;
COMMIT;
END;
/


/* Stored procedure 2: Insert New Patient Invoice (If patient has left payment before, cannot insert new invoice for that patient) */

CREATE OR REPLACE PROCEDURE InsertInvoice(
	cPatient_ID in VARCHAR2,
	cTotalPay in NUMBER
)
IS
	cInvoice_no Invoice.invoice_no%TYPE;
	cStatus Invoice.status%TYPE;
	cIussue_date Invoice.issue_date%TYPE;
	CURSOR pointer IS SELECT Invoice.status, Invoice.patient_ID FROM Invoice;
	vStatus Invoice.Status%TYPE;
	vPatient_ID Invoice.Patient_ID%TYPE;
	counter NUMBER;
	invoiceError NUMBER;

BEGIN
	SELECT COUNT(*) INTO counter
	FROM Invoice;

	invoiceError:= 0;

	OPEN pointer;
	LOOP
		FETCH pointer INTO vStatus, vPatient_ID;
		EXIT WHEN pointer%NOTFOUND;	

		-- Check if the patient has left payment before (Have debt)
		IF vStatus = 'N' AND cPatient_ID = vPatient_ID THEN
			invoiceError := 1;
		END IF;
	END LOOP;
	CLOSE pointer;

	IF invoiceError = 1 THEN
		DBMS_OUTPUT.PUT_LINE('');
		DBMS_OUTPUT.PUT_LINE('Insertion Failed due to left payment before.');

	ELSE
		counter:= counter + 1;
		INSERT INTO invoice VALUES ('INV' || LPAD(counter,5,'0') , cPatient_ID, 'N', TO_DATE(SYSDATE, 'DD-MM-YYYY HH24:MI:SS'), NULL, cTotalPay, NULL);
		DBMS_OUTPUT.PUT_LINE('');
		DBMS_OUTPUT.PUT_LINE('Insertion Completed. New record is updated.');
	END IF;

COMMIT;
END;
/


/* Function 1 : Calculate the availability of staff on a certain shift based on staff type input */

CREATE OR REPLACE FUNCTION staff_Count(
	cStaff_Type Staff.Position%TYPE,
	cShift_ID Shift.Shift_ID%TYPE
)
RETURN NUMBER
IS
	counter NUMBER;
BEGIN
	SELECT (
		SELECT COUNT(*)
		FROM Staff
		WHERE End_Date IS NULL
		AND UPPER(Position) LIKE UPPER(cStaff_Type)
		AND UPPER(Shift_ID) LIKE cShift_ID) INTO counter 
	FROM DUAL;

RETURN counter;
END;
/


/* Function 2 : Check the percentage of diseases that patients are admitted to the hospital */

CREATE OR REPLACE FUNCTION disease_percent(
	cDisease Admission.Disease%TYPE
)
RETURN NUMBER
IS
	counter NUMBER;
	totalRecord NUMBER;
	percentage NUMBER;
	CURSOR pointer IS SELECT Admission.Disease FROM Admission;
	vDisease Admission.Disease%TYPE;
BEGIN
	counter := 0;

	SELECT COUNT(*) INTO totalRecord
	FROM Admission;

	OPEN pointer;
	LOOP
		FETCH pointer INTO vDisease;
		EXIT WHEN pointer%NOTFOUND;	

		-- Count the given disease based on admission table
		IF UPPER(cDisease) = UPPER(vDisease) THEN
			counter := counter + 1;
		END IF;
	END LOOP;
	CLOSE pointer;

	percentage := TO_CHAR((counter / totalRecord)*100, 'fm99D00');
	
RETURN percentage;
END;
/

