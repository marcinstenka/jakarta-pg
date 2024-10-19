package stenka.marcin.heroes.controller.servlet;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import stenka.marcin.heroes.controller.servlet.exception.AlreadyExistsException;
import stenka.marcin.heroes.controller.servlet.exception.BadRequestException;
import stenka.marcin.heroes.controller.servlet.exception.NotFoundException;
import stenka.marcin.heroes.fraction.controller.api.FractionController;
import stenka.marcin.heroes.fraction.dto.PatchFractionRequest;
import stenka.marcin.heroes.fraction.dto.PutFractionRequest;
import stenka.marcin.heroes.unit.controller.api.UnitController;
import stenka.marcin.heroes.unit.dto.PatchUnitRequest;
import stenka.marcin.heroes.unit.dto.PutUnitRequest;
import stenka.marcin.heroes.user.controller.api.UserController;
import stenka.marcin.heroes.user.dto.PatchUserRequest;
import stenka.marcin.heroes.user.dto.PutUserRequest;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {
    private UserController userController;

    private UnitController unitController;

    private FractionController fractionController;

    private String avatarPath;

    public static final class Paths {
        public static final String API = "/api";
    }

    public static final class Patterns {
        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

        public static final Pattern USER = Pattern.compile("/users/(%s)".formatted(UUID.pattern()));

        public static final Pattern USERS = Pattern.compile("/users/?");

        public static final Pattern UNIT = Pattern.compile("/units/(%s)".formatted(UUID.pattern()));

        public static final Pattern UNITS = Pattern.compile("/units/?");

        public static final Pattern FRACTION = Pattern.compile("/fractions/(%s)".formatted(UUID.pattern()));

        public static final Pattern FRACTIONS = Pattern.compile("/fractions/?");

        public static final Pattern FRACTION_UNITS = Pattern.compile("/fractions/(%s)/units/?".formatted(UUID.pattern()));

        public static final Pattern USER_UNITS = Pattern.compile("/users/(%s)/units/?".formatted(UUID.pattern()));

        public static final Pattern USER_AVATAR = Pattern.compile("/users/(%s)/avatar".formatted(UUID.pattern()));
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        userController = (UserController) getServletContext().getAttribute("userController");
        unitController = (UnitController) getServletContext().getAttribute("unitController");
        fractionController = (FractionController) getServletContext().getAttribute("fractionController");
        avatarPath = (String) getServletContext().getInitParameter("avatars-upload");
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USERS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(userController.getUsers()));
                return;
            } else if (path.matches(Patterns.USER.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.USER, path);
                try {
                    response.getWriter().write(jsonb.toJson(userController.getUser(uuid)));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.USER_UNITS.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.USER_UNITS, path);
                try {
                    response.getWriter().write(jsonb.toJson(unitController.getUserUnits(uuid)));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                response.setContentType("image/png");
                try {
                    byte[] avatar = userController.getUserAvatar(uuid, avatarPath);
                    response.setContentLength(avatar.length);
                    response.getOutputStream().write(avatar);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.UNITS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(unitController.getUnits()));
                return;
            } else if (path.matches(Patterns.UNIT.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.UNIT, path);
                try {
                    response.getWriter().write(jsonb.toJson(unitController.getUnit(uuid)));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.FRACTIONS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(fractionController.getFractions()));
                return;
            } else if (path.matches(Patterns.FRACTION.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.FRACTION, path);
                try {
                    response.getWriter().write(jsonb.toJson(fractionController.getFraction(uuid)));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.FRACTION_UNITS.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.FRACTION_UNITS, path);
                try {
                    response.getWriter().write(jsonb.toJson(unitController.getFractionUnits(uuid)));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Get method bad request");
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = extractUuid(Patterns.USER, path);
                try {
                    userController.putUser(uuid, jsonb.fromJson(request.getReader(), PutUserRequest.class));
                    response.addHeader("Location", createUrl(request, Paths.API, "users", uuid.toString()));
                    response.setStatus(HttpServletResponse.SC_CREATED);
                } catch (AlreadyExistsException ex) {
                    response.sendError(HttpServletResponse.SC_CONFLICT, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                response.setContentType("image/png");
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                try {
                    userController.putUserAvatar(uuid, request.getPart("avatar").getInputStream(), avatarPath);
                    response.setStatus(HttpServletResponse.SC_CREATED);
                } catch (AlreadyExistsException ex) {
                    response.sendError(HttpServletResponse.SC_CONFLICT, ex.getMessage());
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.UNIT.pattern())) {
                UUID uuid = extractUuid(Patterns.UNIT, path);
                try {
                    unitController.putUnit(uuid, jsonb.fromJson(request.getReader(), PutUnitRequest.class));
                    response.addHeader("Location", createUrl(request, Paths.API, "units", uuid.toString()));
                    response.setStatus(HttpServletResponse.SC_CREATED);
                } catch (BadRequestException ex) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
                return;
            } else if (path.matches(Patterns.FRACTION.pattern())) {
                UUID uuid = extractUuid(Patterns.FRACTION, path);
                try {
                    fractionController.putFraction(uuid, jsonb.fromJson(request.getReader(), PutFractionRequest.class));
                    response.addHeader("Location", createUrl(request, Paths.API, "fractions", uuid.toString()));
                    response.setStatus(HttpServletResponse.SC_CREATED);
                } catch (BadRequestException ex) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = extractUuid(Patterns.USER, path);
                try {
                    userController.deleteUser(uuid);
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            } else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                try {
                    userController.deleteUserAvatar(uuid, avatarPath);
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.UNIT.pattern())) {
                UUID uuid = extractUuid(Patterns.UNIT, path);
                try {
                    unitController.deleteUnit(uuid);
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            } else if (path.matches(Patterns.FRACTION.pattern())) {
                UUID uuid = extractUuid(Patterns.FRACTION, path);
                try {
                    fractionController.deleteFraction(uuid);
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = extractUuid(Patterns.USER, path);
                try {
                    userController.patchUser(uuid, jsonb.fromJson(request.getReader(), PatchUserRequest.class));
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                response.setContentType("image/png");
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                try {
                    userController.patchUserAvatar(uuid, request.getPart("avatar").getInputStream(), avatarPath);
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                }
                return;
            } else if (path.matches(Patterns.UNIT.pattern())) {
                UUID uuid = extractUuid(Patterns.UNIT, path);
                try {
                    unitController.patchUnit(uuid, jsonb.fromJson(request.getReader(), PatchUnitRequest.class));
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            } else if (path.matches(Patterns.FRACTION.pattern())) {
                UUID uuid = extractUuid(Patterns.FRACTION, path);
                try {
                    fractionController.patchFraction(uuid, jsonb.fromJson(request.getReader(), PatchFractionRequest.class));
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }


    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }


    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    public static String createUrl(HttpServletRequest request, String... paths) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        for (String path : paths) {
            builder.append("/")
                    .append(path, path.startsWith("/") ? 1 : 0, path.endsWith("/") ? path.length() - 1 : path.length());
        }
        return builder.toString();
    }


}
