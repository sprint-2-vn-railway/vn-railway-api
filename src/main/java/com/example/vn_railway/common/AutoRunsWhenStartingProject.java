package com.example.vn_railway.common;
import com.example.vn_railway.service.trip.ISeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AutoRunsWhenStartingProject implements ApplicationListener<ApplicationReadyEvent> {

    private final ISeatService seatService;
    private final EntityManager entityManager;

    private final String CREATE_PROCEDURE_GET_TRAIN_QUERY = 
        "CREATE PROCEDURE get_train_by_date_and_station(\n" +
        "    from_station_first VARCHAR(100),\n" +
        "    to_station_first VARCHAR(100),\n" +
        "    from_station_second VARCHAR(100),\n" +
        "    to_station_second VARCHAR(100),\n" +
        "    start_date DATE\n" +
        ") BEGIN\n" +
        "    SELECT\n" +
        "        t.`id` AS trainId,\n" +
        "        t.`code` AS trainCode,\n" +
        "        t.`name` AS trainName,\n" +
        "        tr.id AS tripId,\n" +
        "        tr.start_date AS startDate,\n" +
        "        tr.end_date AS endDate,\n" +
        "        d.`length` AS distanceLength,\n" +
        "        d.from_station AS fromStation,\n" +
        "        d.to_station AS toStation\n" +
        "    FROM\n" +
        "        train t\n" +
        "    JOIN\n" +
        "        trip tr ON t.id = tr.train_id\n" +
        "    JOIN\n" +
        "        distance d ON d.id = tr.distance_id\n" +
        "    WHERE\n" +
        "        tr.start_date >= CONCAT(start_date, ' 00:00:00')\n" +
        "        AND tr.end_date <= DATE_ADD(tr.start_date, INTERVAL 20 HOUR)\n" +
        "        AND deleted = false\n" +
        "        AND (d.id BETWEEN (\n" +
        "            SELECT MIN(id) FROM distance WHERE from_station = from_station_first AND to_station = to_station_first\n" +
        "        ) AND (\n" +
        "            SELECT MAX(id) FROM distance WHERE from_station = from_station_second AND to_station = to_station_second\n" +
        "        ))\n" +
        "    ORDER BY trainId;\n" +
        "END";

    private final String CREATE_PROCEDURE_GET_SEAT_QUERY = 
        "CREATE PROCEDURE get_seat(\n" +
        "    coachId BIGINT,\n" +
        "    first_trip_id BIGINT,\n" +
        "    last_trip_id BIGINT\n" +
        ") BEGIN\n" +
        "    SELECT\n" +
        "        seatCode,\n" +
        "        MAX(available) AS available,\n" +
        "        coachId\n" +
        "    FROM\n" +
        "    (\n" +
        "        SELECT\n" +
        "            s.`code` AS seatCode,\n" +
        "            s.available AS available,\n" +
        "            c.id AS coachId\n" +
        "        FROM\n" +
        "            seat s\n" +
        "        JOIN\n" +
        "            coach c ON c.id = s.coach_id\n" +
        "        JOIN\n" +
        "            trip tr ON tr.id = s.trip_id\n" +
        "        WHERE\n" +
        "            deleted = false\n" +
        "            AND c.id = coachId\n" +
        "            AND tr.id BETWEEN first_trip_id AND last_trip_id\n" +
        "    ) AS subquery\n" +
        "    GROUP BY\n" +
        "        seatCode, coachId;\n" +
        "END";

    private final String CREATE_PROCEDURE_GET_SEAT_BY_CODE = "CREATE PROCEDURE get_seat_by_code(IN coachId BIGINT, IN first_trip_id BIGINT, IN last_trip_id BIGINT, IN seat_code VARCHAR(20)) " +
        "BEGIN " +
        "SELECT " +
        "    seatCode, " +
        "    MAX(available) AS available, " +
        "    coachId, " +
        "    userName " +
        "FROM ( " +
        "    SELECT " +
        "        s.`code` AS seatCode, " +
        "        s.available AS available, " +
        "        c.id AS coachId, " +
        "        MAX(au.user_name) AS userName " +
        "    FROM seat s " +
        "    JOIN coach c ON c.id = s.coach_id " +
        "    JOIN trip tr ON tr.id = s.trip_id " +
        "    LEFT JOIN users u ON u.id = s.user_id " +
        "    LEFT JOIN app_user au ON au.id = u.app_user_id " +
        "    WHERE c.deleted = FALSE AND s.`code` = seat_code " +
        "    AND c.id = coachId " +
        "    AND tr.id BETWEEN first_trip_id AND last_trip_id " +
        ") AS subquery " +
        "GROUP BY seatCode, coachId, userName; " +
        "END \n;";

    private final String CREATE_PROCEDURE_GET_ALL_SEAT_TEMP_BY_USERNAME = "CREATE PROCEDURE get_all_seat_temporary_by_user_name(IN user_name VARCHAR(50)) " +
        "BEGIN " +
        "SELECT " +
        "    s.`code` AS seatCode, " +
        "    au.user_name AS userName, " +
        "    s.coach_id AS coachId, " +
        "    MAX(s.trip_id) AS lastTripId, " +
        "    MIN(s.trip_id) AS firstTripId, " +
        "    t.train_id AS trainId, " +
        "    tr.`name` AS trainName, " +
        "    c.`code` AS coachCode, " +
        "    tr.`code` AS trainCode, " +
        "    MIN(t.start_date) AS startDate, " +
        "    SUM(d.length) AS totalDistance, " +
        "    toc.price AS price " +
        "FROM seat s " +
        "JOIN users u ON s.user_id = u.id " +
        "JOIN app_user au ON au.id = u.app_user_id " +
        "JOIN trip t ON t.id = s.trip_id " +
        "JOIN coach c ON c.id = s.coach_id " +
        "JOIN train tr ON tr.id = t.train_id " +
        "JOIN type_of_coach toc ON toc.id = c.type_of_coach_id " +
        "JOIN distance d ON d.id = t.distance_id " +
        "WHERE au.user_name = user_name " +
        "GROUP BY seatCode, trainId, coachId; " +
        "END \n;";

    private final String CREATE_PROCEDURE_INSERT_TICKET = "CREATE PROCEDURE insert_ticket(" +
        "    IN user_name VARCHAR(50), " +
        "    IN current_price DOUBLE, " +
        "    IN train_id BIGINT, " +
        "    IN coach_id BIGINT, " +
        "    IN seat_code VARCHAR(20), " +
        "    IN first_trip_id BIGINT, " +
        "    IN last_trip_id BIGINT) " +
        "BEGIN " +
        "START TRANSACTION; " +
        " " +
        "SET @user_id = ( " +
        "    SELECT u.id " +
        "    FROM users u " +
        "    JOIN app_user au ON au.id = u.app_user_id " +
        "    WHERE au.user_name = user_name " +
        "); " +
        " " +
        "INSERT INTO ticket(current_price, user_id) " +
        "VALUES (current_price, @user_id); " +
        " " +
        "SET @ticket_id = LAST_INSERT_ID(); " +
        " " +
        "UPDATE seat s " +
        "JOIN coach c ON s.coach_id = c.id " +
        "JOIN train tr ON tr.id = c.train_id " +
        "JOIN trip t ON t.id = s.trip_id " +
        "SET s.`available` = TRUE, s.`ticket_id` = @ticket_id, s.`user_id` = NULL " +
        "WHERE `user_id` = @user_id " +
        "AND (t.id BETWEEN first_trip_id AND last_trip_id) " +
        "AND c.id = coach_id " +
        "AND tr.id = train_id " +
        "AND s.`code` = seat_code; " +
        " " +
        "COMMIT; " +
        " " +
        "SELECT " +
        "    tr.`code` AS trainCode, " +
        "    tr.`name` AS trainName, " +
        "    c.`code` AS coachCode, " +
        "    toc.`name` AS typeOfCoachName, " +
        "    s.`code` AS seatCode, " +
        "    t.start_date AS startDate, " +
        "    t.end_date AS endDate, " +
        "    u.`name` AS customerName, " +
        "    tk.current_price AS price, " +
        "    d.from_station AS fromStation, " +
        "    d.to_station AS toStation, " +
        "    u.mail AS mail " +
        "FROM ticket tk " +
        "JOIN seat s ON tk.id = s.ticket_id " +
        "JOIN users u ON tk.user_id = u.id " +
        "JOIN trip t ON t.id = s.trip_id " +
        "JOIN coach c ON c.id = s.coach_id " +
        "JOIN train tr ON tr.id = t.train_id " +
        "JOIN type_of_coach toc ON toc.id = c.type_of_coach_id " +
        "JOIN distance d ON d.id = t.distance_id " +
        "WHERE s.ticket_id = @ticket_id " +
        "ORDER BY d.id; " +
        "END \n;";

    @Transactional
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Create Procedure Get Train By Date And Station Begin");
        createProcedureIfNotExists("get_train_by_date_and_station", CREATE_PROCEDURE_GET_TRAIN_QUERY);
        log.info("Create Procedure Get Train By Date And Station End");

        log.info("Create Procedure Get Seat Begin");
        createProcedureIfNotExists("get_seat", CREATE_PROCEDURE_GET_SEAT_QUERY);
        log.info("Create Procedure Get Seat End");

        log.info("Create Procedure Get Seat By Code Begin");
        createProcedureIfNotExists("get_seat_by_code", CREATE_PROCEDURE_GET_SEAT_BY_CODE);
        log.info("Create Procedure Get Seat By Code End");

        log.info("Create Procedure Get All Seat Temp By Username Begin");
        createProcedureIfNotExists("get_all_seat_temporary_by_user_name", CREATE_PROCEDURE_GET_ALL_SEAT_TEMP_BY_USERNAME);
        log.info("Create Procedure Get All Seat Temp By Username End");

        log.info("Create Procedure Insert Ticket Begin");
        createProcedureIfNotExists("insert_ticket", CREATE_PROCEDURE_INSERT_TICKET);
        log.info("Create Procedure Insert Ticket End");

        log.info("Clear all user IDs in Seat Entity");
        seatService.clearAllUserIdInSeatEntity();
        log.info("Clear success");
    }

    private void createProcedureIfNotExists(String procedureName, String procedureQuery) {
        // Check if the procedure exists
        Query checkProcedureQuery = entityManager.createNativeQuery(
            "SELECT COUNT(*) FROM information_schema.ROUTINES WHERE ROUTINE_SCHEMA = DATABASE() AND ROUTINE_NAME = :procedureName"
        );

        checkProcedureQuery.setParameter("procedureName", procedureName);
        long count = ((Number) checkProcedureQuery.getSingleResult()).longValue();

        if (count == 0) {
            entityManager.createNativeQuery(procedureQuery).executeUpdate();
        }
    }
}
