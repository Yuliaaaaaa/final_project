package transactions;

import commonlyUsedStrings.CommonlyUsedStrings;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class DischargePatientTransactionTest {
    private static final DischargePatientTransaction transactionApplier = DischargePatientTransaction.getInstance();

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private Statement statement;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void checkIfAddAppointmentTransactionWorksCorrectly() {
        try {
            when(connection.createStatement()).thenReturn(statement);
            when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
            when(statement.executeQuery(anyString())).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);
            when(resultSet.getInt(1)).thenReturn(1);
            when(resultSet.getInt(2)).thenReturn(1);
            when(resultSet.getString(3)).thenReturn("type");
            when(resultSet.getString(4)).thenReturn("details");
            transactionApplier.execute(1, connection);
        } catch (SQLException e) {
            fail(CommonlyUsedStrings.TESTING_SQL_EXCEPTION);
        } catch (NullPointerException e) {
            fail(CommonlyUsedStrings.TESTING_NPE);
        }
    }
}
