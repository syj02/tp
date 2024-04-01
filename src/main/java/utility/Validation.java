package utility;

import constants.ErrorConstant;
import constants.HealthConstant;
import constants.UiConstant;
import constants.WorkoutConstant;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Represents the validation class used to validate all inputs for PulsePilot.
 */
public class Validation {
    /**
     * Validates that the input date string is correctly formatted in DD-MM-YYYY.
     *
     * @param date The string date from user input.
     * @throws CustomExceptions.InvalidInput If there are invalid date inputs.
     */
    public static void validateDateInput(String date) throws CustomExceptions.InvalidInput {
        if (!date.matches(UiConstant.VALID_DATE_REGEX)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_DATE_ERROR);
        }
        String[] parts = date.split(UiConstant.DASH);
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);

        if (day < UiConstant.MIN_DAY || day > UiConstant.MAX_DAY) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_DAY_ERROR);
        }

        if (month < UiConstant.MIN_MONTH || month > UiConstant.MAX_MONTH) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_MONTH_ERROR);
        }
    }

    /**
     * Validates the delete input details.
     *
     * @param deleteDetails A list containing the details for the delete command.
     * @throws CustomExceptions.InvalidInput If the details specified are invalid.
     * @throws CustomExceptions.InsufficientInput If empty strings are used.
     */
    public static void validateDeleteInput(String[] deleteDetails) throws CustomExceptions.InvalidInput,
            CustomExceptions.InsufficientInput {
        if (deleteDetails[UiConstant.DELETE_ITEM_STRING_INDEX].isEmpty()
                || deleteDetails[UiConstant.DELETE_ITEM_NUMBER_INDEX].isEmpty()) {
            throw new CustomExceptions.InsufficientInput(ErrorConstant.INSUFFICIENT_DELETE_PARAMETERS_ERROR);
        }
        validateFilter(deleteDetails[UiConstant.DELETE_ITEM_STRING_INDEX].toLowerCase());

        if (!deleteDetails[UiConstant.DELETE_ITEM_NUMBER_INDEX].matches(UiConstant.VALID_POSITIVE_INTEGER_REGEX)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_INDEX_ERROR);
        }
    }

    /**
     * Validates whether the filter string is either 'run', 'gym', 'bmi', 'period' or 'appointment'.
     *
     * @param filter The filter string to be checked.
     * @throws CustomExceptions.InvalidInput If the filter string is none of them.
     */
    public static void validateFilter(String filter) throws CustomExceptions.InvalidInput {
        if (filter.equals(WorkoutConstant.RUN)
                || filter.equals(WorkoutConstant.GYM)
                || filter.equals(HealthConstant.BMI)
                || filter.equals(HealthConstant.PERIOD)
                || filter.equals(HealthConstant.APPOINTMENT)
                || filter.equals(WorkoutConstant.ALL)) {
            return;
        }
        throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_ITEM_ERROR
                + System.lineSeparator()
                + ErrorConstant.CORRECT_FILTER_ITEM_FORMAT);
    }

    /**
     * Validates Bmi details entered.
     *
     * @param bmiDetails List of strings representing BMI details.
     * @throws CustomExceptions.InvalidInput If there are any errors in the details entered.
     */
    public static void validateBmiInput(String[] bmiDetails) throws CustomExceptions.InvalidInput,
            CustomExceptions.InsufficientInput {
        if (bmiDetails[HealthConstant.BMI_HEIGHT_INDEX].isEmpty()
                || bmiDetails[HealthConstant.BMI_WEIGHT_INDEX].isEmpty()
                || bmiDetails[HealthConstant.BMI_DATE_INDEX].isEmpty()) {
            throw new CustomExceptions.InsufficientInput(ErrorConstant.INSUFFICIENT_BMI_PARAMETERS_ERROR);
        }

        if (!bmiDetails[HealthConstant.BMI_HEIGHT_INDEX].matches(UiConstant.VALID_TWO_DP_NUMBER_REGEX)
                || !bmiDetails[HealthConstant.BMI_WEIGHT_INDEX].matches(UiConstant.VALID_TWO_DP_NUMBER_REGEX)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.HEIGHT_WEIGHT_INPUT_ERROR);
        }
        validateDateInput(bmiDetails[HealthConstant.BMI_DATE_INDEX]);
        LocalDate date = Parser.parseDate(bmiDetails[HealthConstant.BMI_DATE_INDEX]);
        if (date.isAfter(LocalDate.now())) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.DATE_IN_FUTURE_ERROR);
        }

    }

    /**
     * Validates Period details entered.
     *
     * @param periodDetails List of strings representing Period details.
     * @throws CustomExceptions.InvalidInput If there are any errors in the details entered.
     */
    public static void validatePeriodInput(String[] periodDetails) throws CustomExceptions.InvalidInput,
            CustomExceptions.InsufficientInput {
        if (periodDetails[HealthConstant.PERIOD_START_DATE_INDEX].isEmpty() ||
                periodDetails[HealthConstant.PERIOD_END_DATE_INDEX].isEmpty()) {
            throw new CustomExceptions.InsufficientInput(ErrorConstant.INSUFFICIENT_PERIOD_PARAMETERS_ERROR);
        }

        try {
            validateDateInput(periodDetails[HealthConstant.PERIOD_START_DATE_INDEX]);
        } catch (CustomExceptions.InvalidInput e) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_START_DATE_ERROR
                    + e.getMessage());
        }
        try {
            validateDateInput(periodDetails[HealthConstant.PERIOD_END_DATE_INDEX]);
        } catch (CustomExceptions.InvalidInput e) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_END_DATE_ERROR
                    + e.getMessage());
        }

        LocalDate startDate = Parser.parseDate(periodDetails[HealthConstant.PERIOD_START_DATE_INDEX]);
        LocalDate endDate = Parser.parseDate(periodDetails[HealthConstant.PERIOD_END_DATE_INDEX]);
        if (startDate.isAfter(LocalDate.now())) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.START_DATE_IN_FUTURE_ERROR);
        }
        if (startDate.isAfter(endDate)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.PERIOD_END_BEFORE_START_ERROR);
        }
    }

    //@@author JustinSoh
    /**
     * Validates the details for adding a Run.
     *
     * @param runDetails A list containing Run details.
     * @throws CustomExceptions.InvalidInput If the details specified are invalid.
     * @throws CustomExceptions.InsufficientInput If empty strings are used.
     */
    public static void validateRunInput(String[] runDetails) throws CustomExceptions.InvalidInput,
            CustomExceptions.InsufficientInput {
        if (runDetails[WorkoutConstant.RUN_TIME_INDEX].isEmpty() ||
                runDetails[WorkoutConstant.RUN_DISTANCE_INDEX].isEmpty()) {
            throw new CustomExceptions.InsufficientInput(ErrorConstant.INSUFFICIENT_RUN_PARAMETERS_ERROR);
        }
        validateRunTimeInput(runDetails[WorkoutConstant.RUN_TIME_INDEX]);
        if (!runDetails[WorkoutConstant.RUN_DISTANCE_INDEX].matches(UiConstant.VALID_TWO_DP_NUMBER_REGEX)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_RUN_DISTANCE_ERROR);
        }

        if (runDetails[WorkoutConstant.RUN_DATE_INDEX] != null) {
            validateDateInput(runDetails[WorkoutConstant.RUN_DATE_INDEX]);
            LocalDate date = Parser.parseDate(runDetails[WorkoutConstant.RUN_DATE_INDEX]);
            if (date.isAfter(LocalDate.now())) {
                throw new CustomExceptions.InvalidInput(ErrorConstant.DATE_IN_FUTURE_ERROR);
            }
        }

    }

    /**
     * Validates the details for adding a Gym.
     *
     * @param gymDetails A list containing Gym details.
     * @throws CustomExceptions.InvalidInput If the details specified are invalid.
     * @throws CustomExceptions.InsufficientInput If empty strings are used.
     */
    public static void validateGymInput(String[] gymDetails) throws CustomExceptions.InvalidInput,
            CustomExceptions.InsufficientInput {
        if (gymDetails[WorkoutConstant.GYM_NUMBER_OF_STATIONS_INDEX].isEmpty()) {
            throw new CustomExceptions.InsufficientInput(ErrorConstant.INSUFFICIENT_GYM_PARAMETERS_ERROR);
        }

        if (!gymDetails[WorkoutConstant.GYM_NUMBER_OF_STATIONS_INDEX]
                .matches(UiConstant.VALID_POSITIVE_INTEGER_REGEX)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_NUMBER_OF_STATIONS_ERROR);
        }

        if (gymDetails[WorkoutConstant.GYM_DATE_INDEX] != null) {
            validateDateInput(gymDetails[WorkoutConstant.GYM_DATE_INDEX]);
            LocalDate date = Parser.parseDate(gymDetails[WorkoutConstant.GYM_DATE_INDEX]);
            if (date.isAfter(LocalDate.now())) {
                throw new CustomExceptions.InvalidInput(ErrorConstant.DATE_IN_FUTURE_ERROR);
            }
        }
    }

    //@@author

    /**
     * Validates the time used in HH:MM format.
     *
     * @param time String representing the time to check.
     * @throws CustomExceptions.InvalidInput If time is formatted wrongly.
     */
    public static void validateTimeInput(String time) throws CustomExceptions.InvalidInput {
        if (!time.matches(UiConstant.VALID_TIME_REGEX)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_TIME_ERROR);
        }
        String [] parts = time.split(UiConstant.SPLIT_BY_COLON);
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        if (hours < UiConstant.MIN_HOURS || hours > UiConstant.MAX_HOURS) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_HOURS_ERROR);
        }
        if (minutes < UiConstant.MIN_MINUTES || minutes > UiConstant.MAX_MINUTES) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_MINUTES_ERROR);
        }
    }

    //@@author JustinSoh
    /**
     * Validates the time used in HH:MM format.
     *
     * @param time String representing the time to check.
     * @throws CustomExceptions.InvalidInput If time is formatted wrongly.
     */
    public static void validateRunTimeInput(String time) throws CustomExceptions.InvalidInput {
        if (!time.matches(UiConstant.VALID_TIME_REGEX) &&
                !time.matches(UiConstant.VALID_TIME_WITH_HOURS_REGEX)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_RUN_TIME_ERROR);
        }
        String [] parts = time.split(UiConstant.SPLIT_BY_COLON);
        int hours = WorkoutConstant.NO_HOURS_PRESENT;
        int minutes;
        int seconds;

        if (parts.length == WorkoutConstant.NUMBER_OF_PARTS_FOR_RUN_TIME) {
            minutes = Integer.parseInt(parts[0]);
            seconds = Integer.parseInt(parts[1]);
        } else if (parts.length == WorkoutConstant.NUMBER_OF_PARTS_FOR_RUN_TIME_WITH_HOURS) {
            hours = Integer.parseInt(parts[0]);
            minutes = Integer.parseInt(parts[1]);
            seconds = Integer.parseInt(parts[2]);
        } else {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_RUN_TIME_ERROR);
        }
        if (minutes <= UiConstant.MIN_MINUTES || minutes >= UiConstant.MAX_MINUTES) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_MINUTE_ERROR);
        }

        if (seconds <= UiConstant.MIN_SECONDS || seconds >= UiConstant.MAX_SECONDS) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_SECOND_ERROR);
        }

        if (hours == UiConstant.MIN_HOURS) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_HOUR_ERROR);
        }
    }
    //@@author
    /**
     * Validates Appointment details entered.
     *
     * @param appointmentDetails List of strings representing Appointment details.
     * @throws CustomExceptions.InvalidInput If there are any errors in the details entered.
     */
    public static void validateAppointmentDetails(String[] appointmentDetails)
            throws CustomExceptions.InvalidInput, CustomExceptions.InsufficientInput {
        if (appointmentDetails[HealthConstant.APPOINTMENT_DATE_INDEX].isEmpty()
                || appointmentDetails[HealthConstant.APPOINTMENT_TIME_INDEX].isEmpty()
                || appointmentDetails[HealthConstant.APPOINTMENT_DESCRIPTION_INDEX].isEmpty()) {
            throw new CustomExceptions.InsufficientInput( ErrorConstant
                    .INSUFFICIENT_APPOINTMENT_PARAMETERS_ERROR);
        }
        validateDateInput(appointmentDetails[HealthConstant.APPOINTMENT_DATE_INDEX]);
        validateTimeInput(appointmentDetails[HealthConstant.APPOINTMENT_TIME_INDEX]);

        if (appointmentDetails[HealthConstant.APPOINTMENT_DESCRIPTION_INDEX].length()
                > HealthConstant.MAX_DESCRIPTION_LENGTH) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.DESCRIPTION_LENGTH_ERROR);
        }
        if (!appointmentDetails[HealthConstant.APPOINTMENT_DESCRIPTION_INDEX]
                .matches(UiConstant.VALID_APPOINTMENT_DESCRIPTION_REGEX)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_DESCRIPTION_ERROR);
        }
    }


    /**
     * Validates the string for an exercise name, and that it has no special characters.
     * Only alphanumeric and space characters can be in the name.
     *
     * @param exerciseName The exercise name string.
     * @throws CustomExceptions.InvalidInput If the details specified are invalid.
     * @throws CustomExceptions.InsufficientInput If empty strings are used.
     */
    //@@author JustinSoh
    public static void validateExerciseName(String exerciseName) throws CustomExceptions.InvalidInput,
            CustomExceptions.InsufficientInput {
        if (exerciseName.isEmpty()) {
            throw new CustomExceptions.InsufficientInput(ErrorConstant.EMPTY_EXERCISE_NAME_ERROR);
        }
        if (!exerciseName.matches(UiConstant.VALID_EXERCISE_NAME_REGEX)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_EXERCISE_NAME_ERROR);
        }

        if (exerciseName.length() > WorkoutConstant.MAX_GYM_STATION_NAME_LENGTH) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.EXERCISE_NAME_LENGTH_ERROR);
        }
    }

    /**
     * Validates the weight string such that it only has numbers.
     *
     * @param weightsString The string representing the weights in the format "weight1,weight2,weight3..."
     * @return ArrayList of integers representing the weights in the format [weight1, weight2, weight3 ...]
     * @throws CustomExceptions.InvalidInput If an invalid weights string is passed in.
     */
    public static ArrayList<Integer> validateWeightsArray(String weightsString)
            throws CustomExceptions.InvalidInput {
        String[] weightsArray = weightsString.split(UiConstant.SPLIT_BY_COMMAS);
        ArrayList<Integer> validatedWeightsArray = new ArrayList<>();

        try{
            for(String weight: weightsArray){
                int weightInteger = Integer.parseInt(weight);
                if (weightInteger < WorkoutConstant.MIN_WEIGHT){
                    throw new CustomExceptions.InvalidInput(ErrorConstant.GYM_WEIGHT_POSITIVE_ERROR);
                }
                validatedWeightsArray.add(weightInteger);
            }
        } catch (NumberFormatException e){
            throw new CustomExceptions.InvalidInput(ErrorConstant.GYM_WEIGHT_DIGIT_ERROR);
        }
        return validatedWeightsArray;
    }

    /**
     * Splits and validates the user input for adding a station to a Gym object.
     *
     * @param input The user input string.
     * @return A list of validated parameters for adding a GymStation.
     * @throws CustomExceptions.InvalidInput If the details specified are invalid.
     * @throws CustomExceptions.InsufficientInput If empty strings are used.
     */
    public static String[] splitAndValidateGymStationInput(String input) throws CustomExceptions.InvalidInput,
            CustomExceptions.InsufficientInput {
        String exerciseName = input.split(UiConstant.SPLIT_BY_SLASH)[WorkoutConstant.STATION_NAME_INDEX].trim();
        validateExerciseName(exerciseName);

        String sets = Parser.extractSubstringFromSpecificIndex(input, WorkoutConstant.SETS_FLAG).trim();
        if (!sets.matches(UiConstant.VALID_POSITIVE_INTEGER_REGEX)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_SETS_ERROR);
        }

        String reps = Parser.extractSubstringFromSpecificIndex(input, WorkoutConstant.REPS_FLAG).trim();
        if (!reps.matches(UiConstant.VALID_POSITIVE_INTEGER_REGEX)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_REPS_ERROR);
        }

        String weights = Parser.extractSubstringFromSpecificIndex(input, WorkoutConstant.WEIGHTS_FLAG).trim();
        if (!weights.contains(UiConstant.SPLIT_BY_COMMAS)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_WEIGHTS_ERROR);
        }

        if (!weights.matches(UiConstant.VALID_WEIGHTS_ARRAY_REGEX)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.INVALID_WEIGHTS_ARRAY_FORMAT_ERROR);
        }

        String[] weightsArray = weights.split(UiConstant.SPLIT_BY_COMMAS);
        if (weightsArray.length < WorkoutConstant.MIN_GYM_STATION_WEIGHTS_ARRAY_LENGTH) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.EMPTY_WEIGHTS_ARRAY_ERROR);
        }

        if (weightsArray.length != Integer.parseInt(sets)) {
            throw new CustomExceptions.InvalidInput(ErrorConstant.GYM_WEIGHTS_INCORRECT_NUMBER_ERROR);
        }

        String[] results = new String[WorkoutConstant.NUMBER_OF_GYM_STATION_PARAMETERS];
        results[WorkoutConstant.GYM_STATION_NAME_INDEX] = exerciseName;
        results[WorkoutConstant.GYM_STATION_SET_INDEX] = sets;
        results[WorkoutConstant.GYM_STATION_REPS_INDEX] = reps;
        results[WorkoutConstant.GYM_STATION_WEIGHTS_INDEX] = weights;
        return results;
    }
}
