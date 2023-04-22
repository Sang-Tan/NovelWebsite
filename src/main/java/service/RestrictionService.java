package service;

import model.intermediate.Restriction;
import repository.RestrictionRepository;
import repository.UserRepository;

import java.sql.SQLException;
import java.sql.Timestamp;

public class RestrictionService {

    public static Restriction getUnexpiredRestriction(int userId, String restrictionType) throws SQLException {
        Restriction temp = new Restriction();
        temp.setRestrictedUserId(userId);
        temp.setRestrictedType(restrictionType);
        Restriction existing = RestrictionRepository.getInstance().getByPrimaryKey(temp);
        if (existing != null) {
            long timeToExpire = existing.getDueTime().getTime() - System.currentTimeMillis();
            if (timeToExpire > 0) {
                return existing;
            }
        }
        return null;
    }

    private static String validateAddRestriction(int userId, String restrictedType, String reason, long restrictTime) throws SQLException {
        if (UserRepository.getInstance().getById(userId) == null) {
            return "User does not exist";
        }


        if (reason == null || reason.isEmpty()) {
            return "Reason cannot be empty";
        }
        if (restrictTime <= 0) {
            return "Restrict time must be greater than 0";
        }
        if (!restrictedType.equals(Restriction.TYPE_COMMENT) && !restrictedType.equals(Restriction.TYPE_NOVEL)) {
            return "Invalid restriction type";
        }

        if (getUnexpiredRestriction(userId, restrictedType) != null) {
            return "User already has this restriction";
        }

        return null;
    }

    /**
     * Add a restriction to a user
     *
     * @param userId
     * @param restrictedType
     * @param restrictTime   in milliseconds
     * @return null if success, error message otherwise
     * @throws SQLException
     */
    public static String addRestriction(int userId, String restrictedType, String reason, long restrictTime, int executorId) throws SQLException {
        String error = validateAddRestriction(userId, restrictedType, reason, restrictTime);
        if (error != null) {
            return error;
        }

        Restriction newRestriction = new Restriction();
        newRestriction.setRestrictedUserId(userId);
        newRestriction.setRestrictedType(restrictedType);
        newRestriction.setReason(reason);
        newRestriction.setDueTime(new Timestamp(System.currentTimeMillis() + restrictTime));
        newRestriction.setExecutorId(executorId);

        RestrictionRepository.getInstance().insert(newRestriction, true);

        return null;
    }

    /**
     * Remove a restriction from a user
     *
     * @param userId
     * @param restrictedType
     * @return null if success, error message otherwise
     * @throws SQLException
     */
    public static String removeRestriction(int userId, String restrictedType) throws SQLException {
        if (getUnexpiredRestriction(userId, restrictedType) == null) {
            return "User does not have this restriction";
        }

        Restriction restrictionToRemove = new Restriction();
        restrictionToRemove.setRestrictedUserId(userId);
        restrictionToRemove.setRestrictedType(restrictedType);
        RestrictionRepository.getInstance().delete(restrictionToRemove);
        return null;
    }
    
}
