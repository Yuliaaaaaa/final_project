package repositories;

import models.PatientCard;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yullia Shcherbakova
 * @project final
 */
public class PatientCardsRepository implements Repository<PatientCard> {

    private static final Logger logger = Logger.getLogger(PatientCardsRepository.class);
    private static final PatientCardsRepository patientCardsRepository =
            new PatientCardsRepository();

    private PatientCardsRepository() {

    }

    /**
     * @return
     */
    public static PatientCardsRepository getPatientCardsRepository() {
        return patientCardsRepository;
    }

    /**
     * @param item
     * @param connection
     * @throws SQLException
     */
    @Override
    public void add(PatientCard item, Connection connection) throws SQLException {
        addItem(item, connection);
    }

    /**
     * @param items
     * @param connection
     * @throws SQLException
     */
    @Override
    public void add(Iterable<PatientCard> items, Connection connection) throws SQLException {
        for (PatientCard card : items) {
            addItem(card, connection);
        }
    }

    /**
     * @param item
     * @param connection
     * @throws SQLException
     */
    private void addItem(PatientCard item, Connection connection) throws SQLException {
        String sqlAddPatient = "INSERT INTO " +
                "patients_cards (patient_surname, patient_name, birthday_date, sex) " +
                "VALUES(?, ? , ?, ?);";
        PreparedStatement statement = connection.prepareStatement(sqlAddPatient);
        statement.setString(1, item.getSurname());
        statement.setString(2, item.getName());
        statement.setDate(3, new Date(item.getBirthday_date().getTime()));
        statement.setString(4, "" + item.getSex());
        statement.executeUpdate();
        logger.info("Added new patient card");
    }

    /**
     * @param item
     * @param connection
     * @throws SQLException
     */
    @Override
    public void update(PatientCard item, Connection connection) throws SQLException {
        String sqlUpdatePatient = "UPDATE patients_cards " +
                "SET patient_surname = ?, patient_name = ?, birthday_date = ?, sex = ? " +
                "where patient_card_id = ?;";
        PreparedStatement statement = connection.prepareStatement(sqlUpdatePatient);
        statement.setString(1, item.getSurname());
        statement.setString(2, item.getName());
        statement.setDate(3, new Date(item.getBirthday_date().getTime()));
        statement.setString(4, "" + item.getSex());
        statement.setInt(5, item.getId());
        statement.executeUpdate();
        logger.info("Updated patient card with id " + item.getId());
    }

    /**
     * @param item
     * @param connection
     * @throws SQLException
     */
    @Override
    public void remove(PatientCard item, Connection connection) throws SQLException {
        removeItem(item, connection);
    }

    /**
     * @param items
     * @param connection
     * @throws SQLException
     */
    @Override
    public void remove(Iterable<PatientCard> items, Connection connection) throws SQLException {
        for (PatientCard card : items) {
            removeItem(card, connection);
        }
    }

    /**
     * @param item
     * @param connection
     * @throws SQLException
     */
    private void removeItem(PatientCard item, Connection connection) throws SQLException {
        String sqlRemovePatient = "DELETE from patients_cards where patient_card_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlRemovePatient);
        preparedStatement.setInt(1, item.getId());
        preparedStatement.executeUpdate();
        logger.info("Removed patient card with id " + item.getId());
    }

    /**
     * @param query
     * @param connection
     * @return
     * @throws SQLException
     */
    @Override
    public List<PatientCard> query(String query, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        List<PatientCard> patientCards = getPatientCards(resultSet);
        return patientCards;
    }

    /**
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private List<PatientCard> getPatientCards(ResultSet resultSet) throws SQLException {
        List<PatientCard> patientCards = new ArrayList<>();

        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String surname = resultSet.getString(2);
            String name = resultSet.getString(3);
            char sex = resultSet.getString(4).charAt(0);
            java.util.Date birthday_date = resultSet.getDate(5);
            patientCards.add(new PatientCard(id, surname, name, sex, birthday_date));
        }
        return patientCards;
    }

    /**
     * @param connection
     * @return
     * @throws SQLException
     */
    @Override
    public List<PatientCard> getAll(Connection connection) throws SQLException {
        String sqlSelect = "SELECT * from patients_cards;";
        return query(sqlSelect, connection);
    }


    /**
     * @param id
     * @param connection
     * @return
     * @throws SQLException
     */
    public List<PatientCard> getAllByDoctorId(int id, Connection connection) throws SQLException {
        String sqlSearchPatients = "SELECT * FROM patients_cards WHERE patient_card_id in\n" +
                "(SELECT patient_card_id FROM diagnosis WHERE doctor_id = ? and diagnosis_id in (\n" +
                "SELECT MAX(diagnosis_id) FROM diagnosis WHERE doctor_id = ? GROUP BY(patient_card_id))\n" +
                "AND is_final_diagnosis=0);";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlSearchPatients);
        preparedStatement.setInt(1, id);
        preparedStatement.setInt(2, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<PatientCard> patientCards = getPatientCards(resultSet);
        return patientCards;
    }

    /**
     * @param id
     * @param connection
     * @return
     * @throws SQLException
     */
    public List<PatientCard> getAllExceptDoctorsPatients(int id, Connection connection) throws SQLException {
        String sqlSearchPatients = "SELECT * FROM patients_cards\n" +
                "WHERE patient_card_id NOT IN(SELECT patient_card_id FROM patients_cards WHERE patient_card_id in\n" +
                "(SELECT patient_card_id FROM diagnosis WHERE diagnosis_id in (\n" +
                "SELECT MAX(diagnosis_id) FROM diagnosis WHERE doctor_id = ? GROUP BY(patient_card_id))\n" +
                "AND is_final_diagnosis=0));";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlSearchPatients);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<PatientCard> patientCards = getPatientCards(resultSet);
        return patientCards;
    }


    /**
     * @param id
     * @param connection
     * @return
     * @throws SQLException
     */
    @Override
    public PatientCard getOneById(int id, Connection connection) throws SQLException {
        String sqlSelect = "SELECT * from patients_cards WHERE patient_card_id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<PatientCard> patientCards = getPatientCards(resultSet);
        if (patientCards.size() == 0)
            return null;
        return patientCards.get(0);
    }
}
