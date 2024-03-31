package utility;

import org.junit.jupiter.api.Test;
import workouts.Gym;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ParserTest {

    /**
     * Tests the behaviour of the parseDate function with a correctly formatted string.
     */
    @Test
    void parseDate_correctDateInput_returnDate() {
        LocalDate result = Parser.parseDate("08-03-2024");
        LocalDate expected = LocalDate.of(2024, 3, 8);
        assertEquals(expected, result);
    }

    /**
     * Tests the behaviour of the parseDate function with an incorrectly formatted string.
     * Expects null to be returned.
     */
    @Test
    void parseDate_incorrectDateInput_returnNull () {
        String input = "2024-03-08";
        LocalDate result = Parser.parseDate(input);
        assertEquals(null, result);
    }

    /**
     * Tests the behaviour of the validateDateInput function with a correctly formatted string.
     * Expects no exception to be thrown.
     */
    @Test
    public void validateDateInput_validDate_noExceptionThrown() {
        String validDate = "09-11-2024";
        assertDoesNotThrow(() -> {
            Parser.validateDateInput(validDate);
        });
    }

    /**
     * Tests the behaviour of the validateDateInput function when an invalid day in date string is passed.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    public void validateDateInput_invalidDayFormat_throwInvalidInputException() {
        String invalidDate = "9-11-2024";

        assertThrows(CustomExceptions.InvalidInput.class, () -> {
            Parser.validateDateInput(invalidDate);
        });
    }

    /**
     * Tests the behaviour of the validateDateInput function when an invalid month in date string is passed.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    public void validateDateInput_invalidMonthFormat_throwInvalidInputException() {
        String invalidDate = "9-1-2024";
        assertThrows(CustomExceptions.InvalidInput.class, () -> {
            Parser.validateDateInput(invalidDate);
        });
    }

    /**
     * Tests the behaviour of the validateDateInput function when an invalid year in date string is passed.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    public void validateDateInput_invalidYearFormat_throwInvalidInputException() {
        String invalidDate = "9-11-24";
        assertThrows(CustomExceptions.InvalidInput.class, () -> {
            Parser.validateDateInput(invalidDate);
        });
    }

    /**
     * Tests the behaviour of the validateDateInput function when an illegal day in date string is passed.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    public void validateDateInput_illegalDayNumber_throwInvalidInputException() {
        String invalidDate = "32-11-2024";
        assertThrows(CustomExceptions.InvalidInput.class, () -> {
            Parser.validateDateInput(invalidDate);
        });
    }

    /**
     * Tests the behaviour of the validateDateInput function when 0 is passed in as day for date string.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    public void validateDateInput_zeroDayNumber_throwInvalidInputException() {
        String invalidDate = "00-11-2024";
        assertThrows(CustomExceptions.InvalidInput.class, () -> {
            Parser.validateDateInput(invalidDate);
        });
    }

    /**
     * Tests the behaviour of the validateDateInput function when an illegal month in date string is passed.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    public void validateDateInput_illegalMonthNumber_throwInvalidInputException() {
        String invalidDate = "09-13-2024";
        assertThrows(CustomExceptions.InvalidInput.class, () -> {
            Parser.validateDateInput(invalidDate);
        });
    }

    /**
     * Tests the behaviour of the validateDateInput function when 0 is passed in as month for date string.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    public void validateDateInput_zeroMonthNumber_throwInvalidInputException() {
        String invalidDate = "09-00-2024";
        assertThrows(CustomExceptions.InvalidInput.class, () -> {
            Parser.validateDateInput(invalidDate);
        });
    }

    /**
     * Tests the behaviour of the validateDateInput function when wrong delimiter is used.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    public void validateDateInput_wrongDateDelimiter_throwInvalidInputException() {
        String invalidDate = "09/12/2024";
        assertThrows(CustomExceptions.InvalidInput.class, () -> {
            Parser.validateDateInput(invalidDate);
        });
    }

    /**
     * Tests the behaviour of the validateDateInput function when the year is left out in date string.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    public void validateDateInput_invalidDateParameters_throwInvalidInputException() {
        String invalidDate = "09-12";
        assertThrows(CustomExceptions.InvalidInput.class, () -> {
            Parser.validateDateInput(invalidDate);
        });
    }

    /**
     * Tests the behaviour of correct parameters being passed to validateDate.
     * Expects the correct details to be returned as a list of strings.
     */
    @Test
    public void splitDelete_correctInput_returnsCorrectDeleteValues() throws CustomExceptions.InsufficientInput {
        String input = "/item:appointment /index:1";
        String[] expected = {"appointment", "1"};
        String[] result = Parser.splitDeleteInput(input);
        assertArrayEquals(expected, result);
    }

    /**
     * Tests the behaviour of insufficient parameters being passed to validateDate.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    public void splitDelete_missingParameter_throwsInsufficientParameterException() {
        String input = "/item:appointment";
        assertThrows(CustomExceptions.InsufficientInput.class, () -> Parser.splitDeleteInput(input));
    }


    /**
     * Tests the behaviour of correct parameters being passed to validateDate.
     * Expects no exception to be thrown.
     */
    @Test
    void validateDeleteInput_correctInput_noExceptionThrown() {
        String[] input = {"appointment", "2"};
        assertDoesNotThrow(() -> Parser.validateDeleteInput(input));
    }

    /**
     * Tests the behaviour of an invalid item string being passed to validateDeleteInput.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateDeleteInput_invalidItem_expectInvalidInputException() {
        String[] input = {"free!", "2"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Parser.validateDeleteInput(input));
    }

    /**
     * Tests the behaviour of an invalid index string being passed to validateDeleteInput.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateDeleteInput_invalidIndex_expectInvalidInputException() {
        String[] input = {"item", "-a"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Parser.validateDeleteInput(input));
    }

    /**
     * Tests the behaviour of an empty string being passed to validateDeleteInput.
     * Expects InsufficientInput exception to be thrown.
     */
    @Test
    void validateDeleteInput_emptyString_expectInsufficientInputException() {
        String[] input = {"item", ""};
        assertThrows(CustomExceptions.InsufficientInput.class, () -> Parser.validateDeleteInput(input));
    }

    //@@author j013n3
    /**
     * Tests the behaviour of a correctly formatted user input being passed into splitBmi.
     * Expects no exception to be thrown.
     */
    @Test
    void splitBmi_correctInput_returnsCorrectBmiValues() throws CustomExceptions.InsufficientInput,
            CustomExceptions.InvalidInput {
        String input = "/h:bmi /height:1.71 /weight:60.50 /date:19-03-2024";
        String[] expected = {"1.71", "60.50", "19-03-2024"};
        String[] result = Parser.splitBmiInput(input);
        assertArrayEquals(expected, result);
    }


    /**
     * Tests the behaviour of a string with missing parameter being passed into splitBmi.
     * Expects InsufficientInput exception to be thrown.
     */
    @Test
    void splitBmi_missingParameter_throwsInsufficientInputException() {
        String input = "/h:bmi /height:1.71 /date:19-03-2024";
        assertThrows(CustomExceptions.InsufficientInput.class, () -> Parser.splitBmiInput(input));
    }
    //@@author

    /**
     * Tests the behaviour of correct parameters being passed into validateBmi.
     * Expects no exceptions to be thrown.
     */
    @Test
    void validateBmi_correctParameters_noExceptionThrown() {
        String[] input = {"1.71", "70.00", "22-02-2024"};
        assertDoesNotThrow(() -> Parser.validateBmiInput(input));
    }

    /**
     * Tests the behaviour of 1 decimal point weight number being passed into splitBmi.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateBmi_oneDecimalPointWeight_throwsInvalidInputException() {
        String[] input = {"1.71", "70.0", "29-04-2024"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Parser.validateBmiInput(input));
    }

    /**
     * Tests the behaviour of 1 decimal point height number being passed into splitBmi.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateBmi_oneDecimalPointHeight_throwsInvalidInputException() {
        String[] input = {"1.7", "70.03", "29-04-2024"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Parser.validateBmiInput(input));
    }

    /**
     * Tests the behaviour of a date far in the future is passed into splitBmi.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateBmi_dateAfterToday_throwsInvalidInputException() {
        String[] input = {"1.70", "70.03", "28-03-2025"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Parser.validateBmiInput(input));
    }

    /**
     * Tests the behaviour of a correctly formatted string being passed into splitPeriod.
     * Expects no exception to be thrown.
     */
    @Test
    void splitPeriod_correctInput_noExceptionThrown() throws CustomExceptions.InvalidInput,
            CustomExceptions.InsufficientInput {
        String input = "/h:period /start:29-04-2023 /end:30-04-2023";
        String[] expected = {"29-04-2023", "30-04-2023"};
        String[] result = Parser.splitPeriodInput(input);
        assertArrayEquals(expected, result);
    }

    /**
     * Tests the behaviour of a string with a missing parameter being passed into splitPeriod.
     * Expects InsufficientInput exception to be thrown.
     */
    @Test
    void splitPeriod_missingParameter_throwsInsufficientInputException() {
        String input = "/h:period /start:29-04-2023";
        assertThrows(CustomExceptions.InsufficientInput.class, () -> Parser.splitPeriodInput(input));
    }

    /**
     * Tests the behaviour of correct parameters being passed into validatePeriod.
     * Expects no exception to be thrown.
     */
    @Test
    void validatePeriod_correctParameters_noExceptionThrown()  {
        String [] input = {"23-03-2024", "30-03-2024"};
        assertDoesNotThrow(() -> Parser.validatePeriodInput(input));
    }

    /**
     * Tests the behaviour of a string with an empty string being passed into validatePeriod.
     * Expects InsufficientInput exception to be thrown.
     */
    @Test
    void validatePeriod_emptyParameter_throwsInsufficientInputException() {
        String [] input = {"", "29-03-2024"};
        assertThrows(CustomExceptions.InsufficientInput.class, () -> Parser.validatePeriodInput(input));
    }

    /**
     * Tests the behaviour of a string with invalid start date being passed into validatePeriod.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validatePeriod_invalidStartDate_throwsInvalidInputException() {
        String [] input = {"32-04-2024", "29-04-2024"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Parser.validatePeriodInput(input));
    }

    /**
     * Tests the behaviour of a string with invalid end date being passed into validatePeriod.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validatePeriod_invalidEndDate_throwsInvalidInputException() {
        String [] input = {"28-04-2024", "29-13-2024"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Parser.validatePeriodInput(input));
    }

    /**
     * Tests the behaviour of a start date far in the future being passed into validatePeriod.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validatePeriod_dateAfterToday_throwsInvalidInputException() {
        String [] input = {"28-04-2025", "29-13-2025"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Parser.validatePeriodInput(input));
    }

    /**
     * Tests the behaviour of an end date before the start date being passed into validatePeriod.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validatePeriod_endDateBeforeStartDate_throwsInvalidInputException() {
        String [] input = {"28-03-2024", "22-03-2024"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Parser.validatePeriodInput(input));
    }

    /**
     * Tests the behaviour of a correctly formatted string being passed into splitAppointment.
     * Expects no exception to be thrown.
     */
    @Test
    void splitAppointment_correctInput_noExceptionThrown() throws CustomExceptions.InsufficientInput {
        String input = "/h:appointment /date:30-03-2024 /time:19:30 /description:test";
        String[] expected = {"30-03-2024", "19:30", "test"};
        String[] result = Parser.splitAppointmentDetails(input);
        assertArrayEquals(expected, result);
    }

    /**
     * Tests the behaviour of a correctly formatted string being passed into splitAppointment.
     * Expects InsufficientInput exception to be thrown.
     */
    @Test
    void splitAppointment_missingParameter_throwsInsufficientInputException() {
        String input = "/h:appointment /date:30-03-2024 /description:test";
        assertThrows(CustomExceptions.InsufficientInput.class, () -> Parser.splitAppointmentDetails(input));
    }

    /**
     * Tests the behaviour of correct parameters being passed into validateAppointment.
     * Expects no exception to be thrown.
     */
    @Test
    void validateAppointment_correctParameters_noExceptionThrown() {
        String[] input = {"29-04-2024", "19:30", "test description"};
        assertDoesNotThrow(() -> Parser.validateAppointmentDetails(input));
    }

    /**
     * Tests the behaviour of an empty string being passed into validateAppointment.
     * Expects InsufficientInput exception to be thrown.
     */
    @Test
    void validateAppointment_emptyParameters_throwsInsufficientInputException() {
        String[] input = {"29-04-2024", "19:30", ""};
        assertThrows(CustomExceptions.InsufficientInput.class, () -> Parser.validateAppointmentDetails(input));
    }

    /**
     * Tests the behaviour of an incorrectly formatted date being passed into validateAppointment.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateAppointment_incorrectDateFormat_throwsInvalidInputException() {
        String[] input = {"32-04-2024", "19:30", "test description"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Parser.validateAppointmentDetails(input));
    }

    /**
     * Tests the behaviour of an incorrectly formatted time being passed into validateAppointment.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateAppointment_incorrectTimeFormat_throwsInvalidInputException() {
        String[] input = {"28-04-2024", "25:30", "test description"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Parser.validateAppointmentDetails(input));
    }

    /**
     * Tests the behaviour of a description being more than 100 characters being passed into validateAppointment.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateAppointment_descriptionTooLong_throwsInvalidInputException() {
        String[] input = {"28-04-2024", "22:30",
                          "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                          "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"};
        assertThrows(CustomExceptions.InvalidInput.class, () -> Parser.validateAppointmentDetails(input));
    }

    /**
     * Tests the behaviour of a correctly formatted time string being passed into validateTimeInput.
     * Expects no exception to be thrown.
     */
    @Test
    void validateTimeInput_correctInput_noExceptionThrown() {
        String input = "23:50";
        assertDoesNotThrow(() -> Parser.validateTimeInput(input));
    }

    /**
     * Tests the behaviour of an incorrect time with the wrong delimiter being passed into validateTimeInput.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateTimeInput_invalidDelimiter_throwsInvalidInputException() {
        String input = "23-50";
        assertThrows(CustomExceptions.InvalidInput.class, () -> Parser.validateTimeInput(input));
    }

    /**
     * Tests the behaviour of an incorrect time with invalid hours being passed into validateTimeInput.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateTimeInput_invalidHours_throwsInvalidInputException() {
        String input = "24:50";
        assertThrows(CustomExceptions.InvalidInput.class, () -> Parser.validateTimeInput(input));
    }

    /**
     * Tests the behaviour of an incorrect time with invalid minutes being passed into validateTimeInput.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateTimeInput_invalidMinutes_throwsInvalidInputException() {
        String input = "21:60";
        assertThrows(CustomExceptions.InvalidInput.class, () -> Parser.validateTimeInput(input));
    }

    /**
     * Tests the behaviour of an incorrect time with letters being passed into validateTimeInput.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateTimeInput_invalidTimeWithLetters_throwsInvalidInputException() {
        String input = "12:2a";
        assertThrows(CustomExceptions.InvalidInput.class, () -> Parser.validateTimeInput(input));
    }


    /**
     * Tests the behaviour of an incorrect time with seconds included being passed into validateTimeInput.
     * Expects InvalidInput exception to be thrown.
     */
    @Test
    void validateTimeInput_invalidTimeFormat_throwsInvalidInputException() {
        String input = "21:55:44";
        assertThrows(CustomExceptions.InvalidInput.class, () -> Parser.validateTimeInput(input));
    }

    /**
     * Tests the behaviour of the extractSubstringFromSpecificIndex with a correct flag.
     * Expects the 'bmi' string to be extracted.
     */
    @Test
    void extractSubstringFromSpecificIndex_correctFlag_returnsCorrectSubstring() {
        String test = "/h:bmi";
        String testDelimiter = "/h:";
        String result = Parser.extractSubstringFromSpecificIndex(test, testDelimiter);
        String expected = "bmi";
        assertEquals(expected, result);
    }

    @Test
    void parseGymFileInput_correctInput_returnsGymObject() {
        String input = "gym:2:11-11-1997:bench press:4:10:10,20,30,40:squats:2:5:20,30";
        String input2 = "gym:2:NA:bench press:4:10:10,20,30,40:squats:2:5:20,30";

        try{
            Gym gymOutput = Parser.parseGymFileInput(input);
            Gym gymOutput2 = Parser.parseGymFileInput(input2);
            // make sure that there is two gym station created
            assertEquals(2, gymOutput.getStations().size());
            // make sure that the date is correct
            assertEquals("1997-11-11", gymOutput.getDate().toString());
            assertEquals(null, gymOutput2.getDate());
            // make sure the gym exercise names are correct
            assertEquals("bench press", gymOutput.getStationByIndex(0).getStationName());
            assertEquals("squats", gymOutput.getStationByIndex(1).getStationName());
            // make sure the number of sets are correct
            assertEquals(4, gymOutput.getStationByIndex(0).getNumberOfSets());
            assertEquals(2, gymOutput.getStationByIndex(1).getNumberOfSets());
            // make sure the reps of each stations are correct
            assertEquals(10, gymOutput.getStationByIndex(0).getSets().get(0).getRepetitions());
            assertEquals(5, gymOutput.getStationByIndex(1).getSets().get(0).getRepetitions());
            // make sure te weights of each stations are correct
            assertEquals(10, gymOutput.getStationByIndex(0).getSets().get(0).getWeight());
            assertEquals(20, gymOutput.getStationByIndex(0).getSets().get(1).getWeight());
            assertEquals(30, gymOutput.getStationByIndex(0).getSets().get(2).getWeight());
            assertEquals(40, gymOutput.getStationByIndex(0).getSets().get(3).getWeight());
            assertEquals(20, gymOutput.getStationByIndex(1).getSets().get(0).getWeight());
            assertEquals(30, gymOutput.getStationByIndex(1).getSets().get(1).getWeight());



        } catch (Exception e) {
            fail("Should not throw an exception");
        }
    }



}
