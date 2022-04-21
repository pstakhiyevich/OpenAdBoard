package com.stakhiyevich.openadboard.service.impl;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.exception.TransactionException;
import com.stakhiyevich.openadboard.model.dao.AbstractDao;
import com.stakhiyevich.openadboard.model.dao.TransactionManager;
import com.stakhiyevich.openadboard.model.dao.UserDao;
import com.stakhiyevich.openadboard.model.dao.impl.UserDaoImpl;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.entity.UserRole;
import com.stakhiyevich.openadboard.model.entity.UserStatus;
import com.stakhiyevich.openadboard.service.UploadService;
import com.stakhiyevich.openadboard.service.UserService;
import com.stakhiyevich.openadboard.util.hasher.PasswordHashGenerator;
import com.stakhiyevich.openadboard.util.hasher.UserHashGenerator;
import com.stakhiyevich.openadboard.util.hasher.impl.PasswordHashGeneratorImpl;
import com.stakhiyevich.openadboard.util.hasher.impl.UserHashGeneratorImpl;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final String DEFAULT_AVATAR = "defaultAvatar24fd9b42s3.png";

    private static final Logger logger = LogManager.getLogger();
    private static UserService instance;

    private final PasswordHashGenerator passwordHashGenerator = PasswordHashGeneratorImpl.getInstance();
    private final UserHashGenerator userHashGenerator = UserHashGeneratorImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public List<User> findAll() {
        AbstractDao userDao = new UserDaoImpl();
        List<User> users = new ArrayList<>();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(userDao);
            try {
                users = ((UserDaoImpl) userDao).findAllUsers();
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to find users");
        }
        return users;
    }

    @Override
    public Optional<User> findUserById(Long id) {
        AbstractDao userDao = new UserDaoImpl();
        Optional<User> user = Optional.empty();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(userDao);
            try {
                user = ((UserDaoImpl) userDao).findById(id);
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to perform a transaction", e);
        }
        return user;
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        String hashedPassword = passwordHashGenerator.generatePasswordHash(password).get();
        AbstractDao userDao = new UserDaoImpl();
        Optional<User> user = Optional.empty();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(userDao);
            try {
                user = ((UserDao) userDao).findUserByEmailAndPassword(email, hashedPassword);
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to find user by email and password");
        }
        return user;
    }

    @Override
    public boolean isEmailExist(String email) {
        AbstractDao userDao = new UserDaoImpl();
        boolean result = false;
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(userDao);
            try {
                result = ((UserDaoImpl) userDao).isEmailExist(email);
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to check if user with {} exists", email, e);
        }
        return result;
    }

    @Override
    public boolean createUser(String name, String email, String password) {
        AbstractDao userDao = new UserDaoImpl();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(userDao);
            try {
                UserService userService = UserServiceImpl.getInstance();
                User user = createUserObject(name, email);
                String hashedPassword = passwordHashGenerator.generatePasswordHash(password).get();
                String userHash = userHashGenerator.generateUserHash(email).get();
                boolean result = ((UserDaoImpl) userDao).createUser(user, hashedPassword, userHash);
                if (result) {
                    //todo send activation email here
                    transactionManager.commit();
                    return true;
                }
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("can't perform a transaction", e);
        }
        return false;
    }

    @Override
    public int countAllUsers() {
        AbstractDao userDao = new UserDaoImpl();
        int numberOfUsers = 0;
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(userDao);
            try {
                numberOfUsers = ((UserDaoImpl) userDao).countAllUsers();
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("can't perform a transaction", e);
        }
        return numberOfUsers;
    }

    @Override
    public List<User> findAllPaginatedUsers(int currentPage, int usersPerPage) {
        AbstractDao userDao = new UserDaoImpl();
        List<User> users = new ArrayList<>();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(userDao);
            try {
                users = ((UserDaoImpl) userDao).findAllPaginatedUsers(currentPage, usersPerPage);
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("can't find users");
        }
        return users;
    }

    @Override
    public boolean activateUserByHash(String hash) {
        if (hash != null && !hash.isEmpty()) {
            AbstractDao userDao = new UserDaoImpl();
            try (TransactionManager transactionManager = new TransactionManager()) {
                transactionManager.beginTransaction(userDao);
                try {
                    boolean isActivated = ((UserDaoImpl) userDao).activateUserByHash(hash);
                    if (isActivated) {
                        transactionManager.commit();
                        return true;
                    }
                } catch (DaoException e) {
                    transactionManager.rollback();
                }
            } catch (TransactionException e) {
                logger.error("failed to activate user");
            }
        }
        return false;
    }

    @Override
    public boolean changePassword(User user, String newPassword) {
        AbstractDao userDao = new UserDaoImpl();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(userDao);
            try {
                Optional<User> resul = ((UserDaoImpl) userDao).changePassword(user, passwordHashGenerator.generatePasswordHash(newPassword).get());
                if (resul.isPresent()) {
                    transactionManager.commit();
                    return true;
                }
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to perform a transaction", e);
        }
        return false;
    }

    @Override
    public boolean updateUser(User user, String userName, String userEmail, List<Part> parts) {
        AbstractDao userDao = new UserDaoImpl();
        UploadService uploadService = new UploadServiceImpl();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(userDao);
            try {
                String avatarName;
                if (parts.isEmpty()) {
                    avatarName = user.getAvatar();
                } else {
                    avatarName = uploadService.uploadFile(parts, parts.get(0).getSubmittedFileName()).get();
                    if (!user.getAvatar().equals(DEFAULT_AVATAR)) {
                        uploadService.deleteFile(user.getAvatar());
                    }
                }
                User updatedUserObject = updateUserObject(user, userName, userEmail, avatarName);
                Optional<User> result = userDao.update(updatedUserObject);
                if (result.isPresent()) {
                    transactionManager.commit();
                    return true;
                }
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to perform a transaction", e);
        }
        return false;
    }

    private Optional<User> findUserByEmail(String email) {
        AbstractDao userDao = new UserDaoImpl();
        Optional<User> user = Optional.empty();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(userDao);
            try {
                user = ((UserDao) userDao).findByEmail(email);
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to find user by email");
        }
        return user;
    }

    private User createUserObject(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRegistrationDate(LocalDateTime.now());
        user.setAvatar(DEFAULT_AVATAR);
        user.setStatus(UserStatus.INACTIVATED);
        user.setRole(UserRole.USER);
        return user;
    }

    private User updateUserObject(User user, String userName, String userEmail, String avatarFileName) {
        user.setName(userName);
        user.setEmail(userEmail);
        user.setAvatar(avatarFileName);
        return user;
    }
}
